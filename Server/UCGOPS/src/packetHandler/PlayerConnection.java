package packetHandler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen håndterer tilkoblingen til en klient. Den håndterer alt som
 * trengs for å sende og motta pakker, inkludert kryptering/dekryptering.
 */
public class PlayerConnection {

    /**
     * Socket tilkobling til klienten. Hvis satt til NULL er vi ikke tilkoblet.
     */
    private Socket tilkobling;

    /**
     * Sequence nummer til neste pakke som sendes.
     */
    private int sequence = 1;

    /**
     * Streams for socketen.
     */
    private InputStream in;
    private OutputStream ut;

    /**
     * Objektet vi skal bruke for å håndtere kryptering/dekryptering.
     */
    private UCGOcrypto crypt;

    /**
     * Pakker som skal sendes til klienten lagres her.
     */
    private ConcurrentLinkedQueue<Packet> ut_buffer = new ConcurrentLinkedQueue<Packet>();

    /**
     * Pakker vi har mottatt fra klienten lagres her.
     */
    private LinkedList<Packet> in_buffer = new LinkedList<Packet>();

    /**
     * Data lest inn men som ennå ikke har blitt dekryptert og gjort om til
     * pakker lagres her.
     */
    private byte[] dataBuffer = new byte[0];

    /**
     *
     * @param s Hvilken socket vi skal bruke for å kommunisere med klienten.
     * Socket må allerede være tilkoblet.
     * @param crypt Objektet vi skal bruke for kryptering/dekryptering.
     */
    public PlayerConnection(Socket s, UCGOcrypto crypt) {

        this.crypt = crypt;

        this.tilkobling = s;

        try {

            this.in = s.getInputStream();
            this.ut = s.getOutputStream();
        } catch (IOException e) {
            //Socket fungerer tydeligvis ikke.
            this.tilkobling = null;
        }
    }

    /**
     * Returnerer IP adressen til spilleren.
     *
     * @return IP adresse som en string.
     */
    public String getIP() {
        return this.tilkobling.getInetAddress().getHostAddress();
    }

    /**
     * Denne metoden sjekker om det er pakker som skal tolkes eller sendes og om
     * det er innkommende data i socket.
     *
     * @return True hvis det er noe å gjøre, false hvis ikke.
     */
    public boolean checkTransfer() {

        boolean resultat = false;

        int mengde;
        //Sjekk om det er data i socket.
        try {
            mengde = this.in.available();
        } catch (IOException e) {
            mengde = 0;
        }

        if (mengde != 0) {
            resultat = true;
        }

        //Sjekk om vi har pakker som skal sendes.
        if (this.ut_buffer.peek() != null) {
            resultat = true;
        }

        //Sjekk om vi har pakker som skal tolkes.
        if (this.in_buffer.size() > 0) {
            resultat = true;
        }

        return resultat;
    }

    /**
     * Stenger socket-en.
     */
    public void disconnect() {

        try {
            this.tilkobling.close();
        } catch (IOException e) {
        }

        //Slett eventuelle innkommende og utgående data.
        ut_buffer.clear();
        in_buffer.clear();
        dataBuffer = new byte[0];
    }

    /**
     * Legger en pakke til i output bufferen. Den vil ikke bli sendt til socket.
     *
     * @param pakke Pakke som skal sendes.
     */
    public void sendPacket(Packet pakke) {
        this.ut_buffer.add(pakke);
    }

    /**
     * Returnerer en pakke fra input bufferen. Den vil ikke lese fra socket.
     *
     * @return Neste pakke sendt fra klienten. NULL hvis det ikke er noen
     * pakker.
     */
    public synchronized Packet getPacket() {

        try {
            if (!this.in_buffer.isEmpty()) {
                return this.in_buffer.remove();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("getPacket() exception. in_buffer.size() = " + this.in_buffer.size());
            return null;
        }
    }

    /**
     * Håndterer overføring av data til/fra klient.
     *
     * Alle pakker i utbufferen vil bli sendt til socket. Eventuelle in-data i
     * socket vil bli lest og gjort om til pakker.
     */
    public synchronized void transfer() {

        //Send alle pakkene til klienten.
        this.sendAllePakker();

        //Sjekk om klienten har sendt data til oss.
        this.mottaData();

        //Håndter eventuelle data vi har lest inn.
        this.lagPakker();

        //Dersom det er mer enn 4KB med data i bufferen som ikke har blitt gjort om til pakker
        //indikerer dette at noen sender ugyldige pakker til server. Ingen pakker sent fra klienten er 4KB eller mer.
        if (this.dataBuffer.length > 4096) {
            this.ugyldigeData();
        }
    }

    /**
     * Alle pakker i utbufferen vil bli sendt til klienten.
     *
     * Denne metoden vil også sette sequence nummer for pakken.
     */
    private void sendAllePakker() {

        while (this.ut_buffer.peek() != null) {

            Packet p = this.ut_buffer.poll();

            p.setSequence(this.sequence);
            this.sequence++;

            byte[] data = this.crypt.Encrypt(p.makePacket());

            try {
                this.ut.write(data);
            } catch (IOException e) {
                //Data ble ikke sendt til klienten. Har kanskje mistet tilkoblingen.
                //Ignorer det for nå.
            }
        }

        //Alle pakker har blitt sendt, flush bufferen.
        try {
            this.ut.flush();
        } catch (IOException e) {/*Drit i det.*/
        }

    }

    /**
     * Leser data fra socket og lagrer det i this.dataBuffer.
     */
    private void mottaData() {

        int mengde;

        try {
            mengde = this.in.available();
        } catch (IOException e) {
            mengde = 0;
        }

        if (mengde == 0) {
            return; //Det er ikke noe data å lese.
        }
        byte[] inData = new byte[mengde];

        try {
            mengde = this.in.read(inData, 0, mengde);
        } catch (IOException e) {
            mengde = 0;
        } //Ingen data ble lest inn, så mengde skal være 0.

        if (mengde == 0) {
            return; //Det er ikke noe data å håndtere.
        }
        //Legg til innleste data i this.dataBuffer
        byte[] tmpdata = new byte[this.dataBuffer.length + mengde];

        for (int c = 0; c < this.dataBuffer.length; c++) {
            tmpdata[c] = this.dataBuffer[c];
        }
        for (int c = 0; c < mengde; c++) {
            tmpdata[this.dataBuffer.length + c] = inData[c];
        }

        this.dataBuffer = Arrays.copyOf(tmpdata, tmpdata.length);
        //Innleste data og data fra tidligere har nå blitt satt sammen.

    }

    /**
     * Dekrypterer eventuelle data i this.dataBuffer og lagrer pakkene i
     * in_buffer.
     */
    private void lagPakker() {

        //Sjekk først at vi har minst 64 byte med data.
        if (this.dataBuffer.length < 64) {
            return;
        }

        int antall = Decryptor.UCGOdecrypt(this.crypt, this.dataBuffer, this.in_buffer);

        //Sjekk om alle dataene ble gjort om til pakker eller om det er mer igjen.
        if (antall >= this.dataBuffer.length) {
            //Det er ikke mer data igjen, så bare tøm bufferen.
            this.dataBuffer = new byte[0];
        } else if (antall < this.dataBuffer.length) {
            //Det er mer data igjen. La disse være igjen i bufferen mens resten fjernes.
            byte[] tmpdata = Arrays.copyOfRange(this.dataBuffer, antall, this.dataBuffer.length);
            this.dataBuffer = Arrays.copyOf(tmpdata, tmpdata.length);
        }

    }

    /**
     * Denne metoden skal kalles når noen sender ugyldige data til server.
     *
     * Stenger tilkoblingen, blokkerer IP og skriver log melding.
     */
    private void ugyldigeData() {

        String msg = "PlayerConnection.java: Invalid data received: IP:" + this.getIP();
        /*
		admin.ipblock.blockIP(this.getIP());
		admin.ipblock.saveToFile();
         */
        admin.logging.globalserverMsg(msg);

        this.disconnect();

        this.dataBuffer = new byte[0];
        this.ut_buffer.clear();
        this.in_buffer.clear();
    }

}
