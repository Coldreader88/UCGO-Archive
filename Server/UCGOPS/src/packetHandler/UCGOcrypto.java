package packetHandler;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.Random;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen utfører XORmask + Blowfish for UCGO data.
 *
 * NB! Denne klassen inneholder kode for å virke sammen med MODIFISERT VERSJON
 * AV UCGO KLIENTEN og STANDARD VERSJON.
 */
public class UCGOcrypto {

    //Blowfish passord brukt. ORIGINAL ER chrTCPPassword, dette er for modifisert klient.
    private final String bfPassword = "QQzXzQnpzTpnXz";

    //Max størrelse for en pakke som skal dekrypteres. Brukes for å hindre "malformed" pakker
    //fra å gi datasize/blowsize som er veldig stor og resulterer i enormt minneforbruk.
    private final static int max_packet_size = 8 * 1024;

    private byte[] xortable = new byte[131072];

    private byte[] blowfish_key;

    private UCGOblowfish blowfish;

    private Random xori = new Random();

    public UCGOcrypto() {

        try {
            FileInputStream f = new FileInputStream("xortable.dat");
            f.read(xortable);
            f.close();
        } catch (Exception e) {
            System.out.println("Can not read from the file xortable.dat");
            return;
        }

        try {
            this.blowfish_key = this.bfPassword.getBytes(Charset.forName("US-ASCII"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.blowfish = new UCGOblowfish(this.blowfish_key);
    }

    /**
     * Dekrypterer data mottatt fra klienten. Denne metoden forventer at data
     * inneholder en gyldig UCGO pakke. Kun en UCGO pakke vil bli dekryptert,
     * hvis data inneholder flere pakker vil de bli ignorert.
     *
     * @param data Data mottatt fra klienten.
     * @return Dekrypterte data som inneholder en komplett UCGO pakke, NULL hvis
     * data ikke inneholdt en komplett/gyldig pakke.
     */
    public byte[] Decrypt(byte[] data) {

        //Sjekk at dataene vi mottar er minst 64 byte, minste størrelse for en UCGO pakke.
        if (data.length < 64) {
            return null; //Ugyldige data mottatt, ignorer.
        }
        //Header for UCGO pakken lagres her.
        byte[] header = new byte[64];

        System.arraycopy(data, 0, header, 0, 64);

        header = this.blowfish.Decrypt(header);

        //Hent ut index til XOR key.
        int index = (int) (header[5] & 0xFF);
        index <<= 8;
        index |= (int) (header[4] & 0xFF);

        byte[] xor_key = this.xorKey(index);

        header = this.xorCrypt(header, xor_key, 64);

        //Header er nå ferdig dekryptert.
        //Hent ut størrelse på data etter header.
        int data_size = 0;
        data_size |= (int) (header[19] & 0xFF);
        data_size <<= 8;
        data_size |= (int) (header[18] & 0xFF);
        data_size <<= 8;
        data_size |= (int) (header[17] & 0xFF);
        data_size <<= 8;
        data_size |= (int) (header[16] & 0xFF);

        //Hent ut størrelsen på blowfish krypterte data.
        int blowfish_size = 0;
        blowfish_size |= (int) (header[23] & 0xFF);
        blowfish_size <<= 8;
        blowfish_size |= (int) (header[22] & 0xFF);
        blowfish_size <<= 8;
        blowfish_size |= (int) (header[21] & 0xFF);
        blowfish_size <<= 8;
        blowfish_size |= (int) (header[20] & 0xFF);

        //Sjekk at datasize og blowfish er innenfor gyldige grenser.
        if (data_size < 0 || data_size > max_packet_size
                || blowfish_size < 0 || blowfish_size > max_packet_size) {
            return null;
        }

        //Sjekk at vi har hele pakken i data[] og blowfish size er gyldig.
        if (data_size > blowfish_size || blowfish_size + 64 > data.length) {
            return null; //FIKS. blowfish_zie+64 > data.length
        }
        //OK, hent ut pakke data og dekrypter dem.
        byte[] bf_pakke_data = new byte[blowfish_size];
        byte[] xor_pakke_data = new byte[data_size];

        System.arraycopy(data, 64, bf_pakke_data, 0, blowfish_size);

        bf_pakke_data = this.blowfish.Decrypt(bf_pakke_data);

        //Hent ut data som skal XOR dekrypteres, ignorer blowfish padding.
        System.arraycopy(bf_pakke_data, 0, xor_pakke_data, 0, data_size);

        xor_pakke_data = this.xorCrypt(xor_pakke_data, xor_key, data_size);

        byte[] resultat = new byte[64 + data_size];

        System.arraycopy(header, 0, resultat, 0, 64);
        System.arraycopy(xor_pakke_data, 0, resultat, 64, data_size);

        return resultat;
    }

    /**
     * Krypterer oppgitte data. Denne metoden antar at data er en komplett UCGO
     * pakke, header+data, med blocksize 8.
     *
     * @param data UCGO pakke som skal krypteres.
     *
     * @return UCGO pakke som har blitt kryptert, NULL hvis feil.
     */
    public byte[] Encrypt(byte[] data) {

        if (data.length < 64) {
            return null; //64 byte=minimum pakke størrelse.
        }
        //Finn ut hvor mye av pakken som skal XOR krypteres.
        int data_size = (int) (data[19] & 0xFF);
        data_size <<= 8;
        data_size |= (int) (data[18] & 0xFF);
        data_size <<= 8;
        data_size |= (int) (data[17] & 0xFF);
        data_size <<= 8;
        data_size |= (int) (data[16] & 0xFF);

        int xorindex = this.xori.nextInt(65535);

        byte[] resultat = this.xorCrypt(data, this.xorKey(xorindex), 64 + data_size);

        resultat[4] = (byte) (xorindex & 0xFF);
        xorindex >>= 8;
        resultat[5] = (byte) (xorindex & 0xFF);
        resultat[6] = (byte) 0x0;
        resultat[7] = (byte) 0x0;

        resultat = this.blowfish.Encrypt(resultat);

        return resultat;
    }

    /**
     * Lager en gyldig XOR key som kan brukes til XOR (de)kryptering.
     *
     * @param i Index inn i XOR table.
     *
     * @return XOR key i form av et byte array med fire elementer, NULL hvis
     * ugyldig index.
     */
    private byte[] xorKey(int i) {

        if (i >= this.xortable.length) {
            return null;
        }

        byte[] key = new byte[4];

        key[0] = this.xortable[i * 2]; //XOR table består av 16-bit verdier, gang opp med to.
        key[1] = this.xortable[i * 2 + 1];
        key[2] = (byte) (i & 0xFF);
        key[3] = (byte) ((i & 0xFF00) >> 8);
        return key;
    }

    /**
     * Utfører XOR (de)kryptering av data.
     *
     * @param data Data som skal dekrypteres.
     * @param key XOR key som skal brukes, byte array med fire elementer.
     * @param size Hvor mye av data som skal (de)krypteres.
     *
     * @return data arrayet der length bytes har blitt (de)kryptert.
     */
    private byte[] xorCrypt(byte[] data, byte[] key, int length) {

        byte[] resultat = new byte[data.length];
        System.arraycopy(data, 0, resultat, 0, data.length);

        //Utfør XOR masking på blokker av 4 byte.
        int block_size = (length / 4) * 4;

        //(De)krypter data.
        for (int i = 0; i < block_size; i++) {
            resultat[i] ^= key[i % 4];
        }

        //Hvis data ikke er delelig på 4 utfør "spesiell" XOR masking av først byte som følger.
        //Kun på første byte, byte 2&3 skal ikke XOR maskes.
        switch (length % 4) {

            case 3:
                resultat[block_size] ^= key[2];
            case 2:
                resultat[block_size] ^= key[1];
            case 1:
                resultat[block_size] ^= key[0];

        }

        return resultat;
    }

}
