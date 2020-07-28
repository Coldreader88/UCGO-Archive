
package ucgozlib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

/**
 * Inneholder diverse hjelpefunksjoner for å "(de)scramble" data som skal
 * komprimeres eller dekomprimeres.
 *
 * @author UCGOSERVER
 */
public class Scrambler {

    /**
     * Leser komprimerte data og descrambler dem slik at de blir riktige. Sjekk
     * modifisert klient docs for hvordan dette fungerer.
     *
     * @param input Data leses herfra.
     * @return Dekomprimerte og descrambled data.
     */
    public static ByteArrayOutputStream descrambleData(InflaterInputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        for (int n = input.read(); n != -1; n = input.read()) {
            output.write(descrambleByte(n));
        }

        return output;
    }

    /**
     * Leser data og scrambler dem. Sjekk modifisert klient docs for hvordan
     * dette fungerer.
     *
     * @param input Data leses herfra.
     * @return Scrambled data.
     */
    public static ByteArrayOutputStream scrambleData(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        for (int n = input.read(); n != -1; n = input.read()) {
            output.write(scrambleByte(n));
        }

        return output;
    }

    /**
     * Scrambler mottatte data og returnerer dem.
     *
     * @param data Data som skal scrambles.
     *
     * @return Scrambled data.
     */
    public static ByteArrayOutputStream scrambleData(ByteArrayOutputStream data) {

        byte[] d = data.toByteArray();
        ByteArrayOutputStream m = new ByteArrayOutputStream();

        for (int i = 0; i < d.length; i++) {
            m.write(scrambleByte((int) d[i] & 0xFF));
        }

        return m;
    }

    /**
     * Leser en byte fra input og descrambler den.
     *
     * @param input Input vi leser fra.
     * @return Descrambled byte.
     */
    public static int descrambleByte(InflaterInputStream input) throws IOException {
        int n = input.read();
        return descrambleByte(n);
    }

    /**
     * Descrambler en enkelt lest byte. Sjekk modifisert klient docs for hvordan
     * dette fungerer.
     *
     * @param n Byte som skal fikses.
     * @return Byte som er riktig.
     */
    private static int descrambleByte(int n) {

        int count = 0;
        if ((n & 0x1) != 0) {
            count++;
        }
        if ((n & 0x2) != 0) {
            count++;
        }
        if ((n & 0x4) != 0) {
            count++;
        }
        if ((n & 0x8) != 0) {
            count++;
        }
        if ((n & 0x10) != 0) {
            count++;
        }
        if ((n & 0x20) != 0) {
            count++;
        }
        if ((n & 0x40) != 0) {
            count++;
        }
        if ((n & 0x80) != 0) {
            count++;
        }

        switch (count) {
            case 1:
                return rorByte(n, 5);
            case 2:
                return rorByte(n, 3);
            case 3:
                return rorByte(n, 4);
            case 4:
                return rorByte(n, 1);
            case 5:
                return rorByte(n, 2);
            case 6:
                return rorByte(n, 6);
            case 7:
                return rorByte(n, 1);
            default:
                return n;
        }
    }

    /**
     * Scrambler en enkelt byte. Sjekk modifisert klient docs for hvordan dette
     * fungerer.
     *
     * @param n Byte som skal scrambles.
     * @return Byte som er scrambled.
     */
    public static int scrambleByte(int n) {
        
        int count = 0;
        if ((n & 0x1) != 0) {
            count++;
        }
        if ((n & 0x2) != 0) {
            count++;
        }
        if ((n & 0x4) != 0) {
            count++;
        }
        if ((n & 0x8) != 0) {
            count++;
        }
        if ((n & 0x10) != 0) {
            count++;
        }
        if ((n & 0x20) != 0) {
            count++;
        }
        if ((n & 0x40) != 0) {
            count++;
        }
        if ((n & 0x80) != 0) {
            count++;
        }

        switch (count) {
            case 1:
                return rolByte(n, 5);
            case 2:
                return rolByte(n, 3);
            case 3:
                return rolByte(n, 4);
            case 4:
                return rolByte(n, 1);
            case 5:
                return rolByte(n, 2);
            case 6:
                return rolByte(n, 6);
            case 7:
                return rolByte(n, 1);
            default:
                return n;
        }
    }

    /**
     * Utfører ROR, Rotate Right, operasjon på en byte.
     *
     * @param n Byte vi utfører ROR på.
     * @param count Antall ROR operasjoner vi skal utføre.
     *
     * @return Byte etter ROR operasjon er påført.
     */
    private static int rorByte(int n, int count) {

        int m = n;
        m <<= 8;
        m >>= count;

        int d = m & 0xFF;
        m >>= 8;
        d |= m;

        return d&0xFF;
    }

    /**
     * Utfører ROL, Rotate Left, operasjon på en byte.
     *
     * @param n Byte vi utfører ROL på.
     * @param count Antall ROL operasjoner vi skal utføre.
     *
     * @return Byte etter ROR operasjon er påført.
     */
    private static int rolByte(int n, int count) {

        int m = n;
        m <<= count;
        m >>= 8;

        int d = n << count;
        d |= m;

        return d&0xFF;
    }
}
