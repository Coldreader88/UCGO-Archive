package players;

import java.util.Date;
import java.util.Hashtable;
import opcodes.*;
import packetHandler.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette er superklassen for objektene som representerer en bruker. Player
 * objektet inneholder alt serveren trenger å vite om en bruker.
 *
 */
public abstract class Player implements Runnable {

    /**
     * Tilkoblingen til klienten.
     */
    private PlayerConnection tilkobling;

    /*Account ID.*/
    private int accountID;

    /*Liste over alle Opcodes tilgjengelig som dette Player objektet kan bruke.*/
    private Hashtable<Integer, Opcode> opcodeList;

    //Settes til true når run() metoden kjører, og false når den er slutt.
    private boolean isRunning = false;

    //Angir UCGM tag for en spiller.
    private int ucgm = 0xA;

    //Timestamp som angir når dette player objektet ble opprettet.
    protected int creationTime;

    //Angir om klienten har sendt gyldig login info til server. Opcode 0x30001 for login, 0x41 for game, 0x01 for chat.
    private boolean loginReceived = false;

    //Timestamp, angir når klienten sendte gyldig login info til server.
    private int loginTime;

    //Angir om vi har sendt config.Server.serverNews melding til denne spilleren.
    private boolean newsMsgSent = false;

    /**
     *
     * @param op Liste over tilgjengelige opcodes vi kan bruke.
     * @param tilkobling PlayerConnection objektet vi skal bruke for å
     * kommunisere med bruker.
     */
    public Player(Hashtable<Integer, Opcode> op, PlayerConnection tilkobling) {

        this.opcodeList = op;
        this.tilkobling = tilkobling;
        this.creationTime = (int) ((new Date()).getTime() / 1000);
    }

    /**
     * Metoden som blir kjørt i en thread.
     */
    public void run() {

        this.isRunning = true;

        //Send og motta eventuelle pakker.
        this.tilkobling.transfer();

        //Hvis vi har mottatt pakker, gå gjennom alle sammen.
        while (true) {

            Packet p = this.tilkobling.getPacket();

            if (p == null) {
                break; //Ingen flere pakker å håndtere.
            }
            //Hent ut objektet for å håndtere denne pakken.
            Opcode o = this.opcodeList.get(p.getOpcode());

            //Sjekk at vi har støtte for denne opcode-en.
            if (o != null) {
                //Midlertidig testing. Ta tiden på hvor lenge det tar å utføre koden.
                long startTid = System.currentTimeMillis();

                o.execute(this, p); //Utfør koden for å håndtere denne pakken.

                long sluttTid = System.currentTimeMillis();

                //TEST
                /*if ((sluttTid - startTid) > 100) {
                    System.out.println("WARNING: Opcode:" + p.getOpcode() + "(decimal) TOOK " + (sluttTid - startTid)
                            + "ms TO EXECUTE.");
                }*/
            } else { //Vi har ikke støtte for denne opcode-en. 
                System.out.println("\nUnknown Opcode from IP:" + this.tilkobling.getIP() + "\n");
                helpers.PrintPacket.print(p);
                //Dersom vi fikk en opcode annet en 0x3E, trade item quest tingen, kicker vi spiiler.
                if (p.getOpcode() != 0x3E) {
                    this.tilkobling.disconnect();
                }
            }

        }

        //Send eventuelle pakker.
        this.tilkobling.transfer();

        this.isRunning = false;
    }

    /**
     * Sjekker om det er data som må leses/sendes fra/til klienten.
     *
     * @return True hvis det er, false hvis ikke.
     */
    public boolean checkTransfer() {

        return this.tilkobling.checkTransfer();
    }

    /**
     * Stenger tilkoblingen til spiller.
     */
    public void disconnect() {
        tilkobling.disconnect();
    }

    /**
     * Setter Account ID.
     *
     * @param id Accout ID
     */
    public void setAccountID(int id) {

        this.accountID = id;
    }

    /**
     * @return Spillerens Account ID.
     */
    public int getAccountID() {

        return this.accountID;
    }

    /**
     * Setter UCGM tag for spiller.
     *
     * @param ucgm UCGM tag.
     */
    public void setUCGM(int ucgm) {
        this.ucgm = ucgm;
    }

    /**
     *
     * @return UCGM tag til spiller.
     */
    public int getUCGM() {
        return this.ucgm;
    }

    /**
     *
     * @return TRUE hvis spiller er 0x3,0x4 eller 0x5 UCGM.
     */
    public boolean isUCGM() {
        if (this.ucgm == 0x3 || this.ucgm == 0x4 || this.ucgm == 0x5) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return IP addressen til spiller.
     */
    public String getIP() {
        return this.tilkobling.getIP();
    }

    /**
     * Setter at server har mottatt godkjent login pakke fra klient.
     */
    public void setLoginReceived() {
        this.loginReceived = true;
        this.loginTime = (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * Sjekker om spiller/klienten har logget seg på server innen en viss tid.
     *
     * @return TRUE hvis alt er ok, FALSE hvis ikke logget på server innen
     * oppgitt tid. NB! Hvis mindre tid enn tidsfristen er gått vil metoden
     * returnere TRUE, selvom spiller ikke er logget på.
     */
    public boolean checkIfLoggedIn() {

        //Nåværende tidspunkt.
        int time = (int) ((new Date()).getTime() / 1000);

        //Hvis det er mer enn 30 sekunder siden klienten koblet til sjekk at gyldig login info er sendt.
        if (this.loginReceived || (this.creationTime + 30) >= time) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return Timestamp som angir når spiller logget inn.
     */
    public int getLoginTime() {
        return this.loginTime;
    }

    /**
     * Sender en pakke.
     *
     * NB! Denne metoden vil ALLTID overskrive eksisterende sequence nummer i
     * pakken. Dette har blitt lagt til i ettertid.
     *
     * @param p Pakke
     */
    public void sendPacket(Packet p) {

        this.tilkobling.sendPacket(p);

    }

    /**
     *
     * @return TRUE hvis run() metoden kjører.
     */
    public boolean isRunning() {
        return this.isRunning;
    }

    /**
     * Denne metoden skal kalles når spiller logger ut og kan brukes til å lagre
     * data for character eller oppdatere info i userDB.
     */
    public abstract void save();

    /**
     * Utfører diverse clean up ting som å koble fra socket og frigjøre minne.
     */
    public void cleanUp() {
        this.tilkobling.disconnect();
    }

    protected void finalize() throws Throwable {

        this.tilkobling = null;
    }

    public boolean isNewsMsgSent() {
        return newsMsgSent;
    }

    public void setNewsMsgSent(boolean newsMsgSent) {
        this.newsMsgSent = newsMsgSent;
    }

}
