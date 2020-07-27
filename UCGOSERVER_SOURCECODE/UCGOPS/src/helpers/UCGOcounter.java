package helpers;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å lage en gyldig UCGO 0x80 teller.
 *
 */
public class UCGOcounter {

    /**
     * Gjør om en verdi til en gyldig 0x80 teller. NB! Denne metoden antar at
     * verdien er mindre enn 16510, ingen feilsjekking vil bli gjort.
     *
     * @param antall Verdi som skal gjøres.
     *
     * @return Byte array som inneholder teller som kan settes rett inn i
     * pakken.
     */
    public static byte[] getCounter(int antall) {

        byte[] data = null;

        //Sjekk om teller trenger 1 eller 2 byte.
        if (antall <= 0x7F) {
            //Kun 1 byte.
            data = new byte[1];
            data[0] = (byte) (antall | 0x80);
        } else {
            //2 byte.
            int rest = 0; //Rest, går i første byte.
            int runder = 1; //Runder telleren må gå, andre byte.

            int teller = antall - 0x80;

            while (teller >= 0x80) {
                runder++;
                teller -= 0x80;
            }

            rest = teller;

            data = new byte[2];

            data[0] = (byte) rest;
            data[1] = (byte) (runder | 0x80);
        }

        return data;
    }
}
