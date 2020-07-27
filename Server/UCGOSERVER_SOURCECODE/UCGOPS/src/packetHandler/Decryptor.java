package packetHandler;

import java.util.LinkedList;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes som en wrapper for UCGOcrypto slik at det blir mulig å
 * dekryptere en data stream som inneholder flere pakker.
 *
 */
public class Decryptor {

    /**
     * Dekrypterer pakke data rekursivt. Kan ta flere pakker på en gang.
     *
     * @param crypt UCGOcrypto objektet som skal brukes for dekryptering
     * @param data Pakke data. Disse dataene vil IKKE bli endret på.
     * @param pakker En liste der pakkene skal lagres.
     *
     * @return Hvor mange byte av data som ble gjort om til pakker. Rundet opp
     * til nærmeste 8-byte block.
     */
    public static int UCGOdecrypt(UCGOcrypto crypt, byte[] data, LinkedList<Packet> pakker) {

        byte[] resultat = crypt.Decrypt(data);

        if (resultat == null) {
            return 0; //Sjekk at data ble dekryptert.
        }
        Packet pakke = new Packet(resultat);

        if (pakke.getOpcode() == -1) {
            return 0; //Sjekk at dekrypterte data var gyldige.
        }
        //Gyldig pakke.
        pakker.add(pakke);

        //Beregn hvor mange krypterte byte med data som ble gjort om til pakke data.
        int data_size = 0;
        if (resultat.length % 8 != 0) {
            data_size = ((resultat.length + 8) / 8) * 8;
        } else {
            data_size = resultat.length;
        }

        //Sjekk om det er mer data igjen å dekryptere eller om vi er ferdige. 
        if (data_size >= data.length) {
            return data_size;
        }

        //Resten av dataene som skal dekrypteres kopieres over her.
        byte[] nydata = new byte[data.length - data_size];

        System.arraycopy(data, data_size, nydata, 0, data.length - data_size);

        return data_size + UCGOdecrypt(crypt, nydata, pakker);
    }

}
