package helpers;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å konvertere byte array til tekst og andre veien.
 * Nyttig for å lagre binær data i db.
 *
 */
public class byteArrayString {

    /**
     * Konverterer et byte array til en string bestående at tall på formatet: 23
     * 45 -35.....
     *
     * @param data Byte array
     *
     * @return String som representerer dataene.
     */
    public static String byteArrayToString(byte[] data) {

        String str = new String();

        for (int c = 0; c < data.length; c++) {
            //Det skal ikke være + " " på siste verdi i string.
            if (c == (data.length - 1)) {
                str += data[c];
            } else {
                str += data[c] + " ";
            }
        }

        return str;
    }

    /**
     * Konverterer en string til byte array. String skal være på formatet: 23 45
     * -35.....
     *
     * @param data String som inneholder tall -128 til 127.
     *
     * @return Byte array, NULL hvis string inneholder ugyldige verdier.
     */
    public static byte[] stringToByteArray(String data) {

        packetHandler.PacketData ba = new packetHandler.PacketData();

        String[] data_liste = data.split(" ");

        for (int c = 0; c < data_liste.length; c++) {

            try {
                ba.writeByte(Byte.parseByte(data_liste[c]));
            } catch (Exception e) {
                //Feil, ugyldig tall i data.
                return null;
            }
        }

        return ba.getData();
    }

}
