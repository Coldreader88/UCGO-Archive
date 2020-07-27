package packetHandler;

import java.nio.charset.Charset;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å skrive og lese pakkedata. Pakkeheader er ikke
 * inkludert.
 *
 * Klassen bruker et byte array som blir automatisk resized i intervaller på 512
 * byte etterhvert som mer data blir lagt til.
 *
 */
public class PacketData {

    /**
     * Hvor mye internt byte array skal økes med hver gang størrelsen justeres.
     */
    private final int blockSize = 512;

    /**
     * Data lagres i dette arrayet.
     */
    private byte[] data = new byte[blockSize];

    /**
     * Hvor i byte array vi befinner oss ved LESING, data blir LEST fra denne
     * index.
     */
    private int readIndex = 0;

    /**
     * Hvor mye data som er lagret i byte array. Dette er IKKE størrelsen på
     * byte arrayet men hvor mange byte med pakke data som er lagret i det. Data
     * SKRIVES på slutten av pakken, altså neste byte skrives på [dataSize]
     */
    private int dataSize = 0;

    /**
     * Default constructor trenger ikke å gjøre noe.
     */
    public PacketData() {
    }

    /**
     * Denne constructoren brukes når vi har en eksisterende pakke som vi ønsker
     * å lese fra, eller legge til data.
     *
     * @param pakke Eksisterende pakkedata.
     */
    public PacketData(byte[] pakke) {

        //Flytt en byte av gangen fra eksisterende data, da blir internt array automatisk resized.
        for (int i = 0; i < pakke.length; i++) {
            this.add(pakke[i]);
        }
    }

    /**
     *
     * @return Antall byte vi har med data.
     */
    public int size() {

        return this.dataSize;
    }

    /**
     *
     * @return Antall byte vi har igjen å lese.
     */
    public int bytesLeft() {

        return this.dataSize - this.readIndex;
    }

    /**
     *
     * @return Innholdet i pakken i form av et byte array.
     */
    public byte[] getData() {

        byte[] resultat = new byte[this.dataSize];

        System.arraycopy(this.data, 0, resultat, 0, this.dataSize);

        return resultat;
    }

    /**
     * Hopper over gitt antall byte. Dvs index flyttes fram, brukes kun ved
     * lesing.
     *
     * @param lengde Antall byte vi skal flytte fram.
     */
    public void skipAhead(int lengde) {

        //Sjekk at vi har nok data til at vi kan hoppe frem.
        if (this.dataSize < (this.readIndex + lengde)) {
            return;
        }

        this.readIndex += lengde;
    }

    /**
     *
     * @return Byte lest fra pakke data.
     */
    public byte readByte() {

        return this.get();
    }

    /**
     * Leser flere bytes fra dataene.
     *
     * @param size Antall byte vi skal lese.
     * @return Bytearray som inneholder dataene. NULL hvis det ikke er nok data
     * å lese.
     */
    public byte[] readByteArray(int size) {

        if (this.dataSize < this.readIndex + size) {
            return null;
        }

        byte[] resultat = new byte[size];

        for (int c = 0; c < size; c++) {
            resultat[c] = this.get();
        }

        return resultat;
    }

    /**
     *
     * @return Short i Big Endian format lest fra pakke data.
     */
    public short readShortBE() {

        short r;

        r = (short) (this.get());
        r <<= 8;
        r |= (short) (this.get() & 0xFF);

        return r;
    }

    /**
     *
     * @return Int i Big Endian format lest fra pakke data.
     */
    public int readIntBE() {

        int r;

        r = (int) (this.readShortBE()) << 16;
        r |= (int) (this.readShortBE() & 0xFFFF);

        return r;
    }

    /**
     *
     * @return Long i Big Endian format lest fra pakke data.
     */
    public long readLongBE() {

        long r;

        r = (long) (this.readIntBE()) << 32;
        r |= (long) (this.readIntBE() & 0xFFFFFFFF);

        return r;
    }

    /**
     * Leser en string i UTF-16LE format.
     *
     * @param antall Antall tegn som skal leses (ikke byte).
     *
     * @return String. Null dersom det ikke er nok data til å lese antall tegn.
     */
    public String readStringUTF16LE(int antall) {

        if (this.dataSize < (this.readIndex + antall * 2)) {
            return null; //Sjekk at vi har nok data til å lese stringen.
        }
        byte[] resultat = new byte[antall * 2];

        for (int c = 0; c < antall * 2; c++) {
            resultat[c] = this.get();
        }

        return new String(resultat, Charset.forName("UTF-16LE"));
    }

    /**
     * Leser en string i ASCII format.
     *
     * @param antall Antall tegn som skal leses.
     *
     * @return String. Null dersom det ikke er nok data til å lese antall tegn.
     */
    public String readStringASCII(int antall) {

        if (this.dataSize < (this.readIndex + antall)) {
            return null; //Sjekk at vi har nok data til å lese stringen.
        }
        byte[] resultat = new byte[antall];

        for (int c = 0; c < antall; c++) {
            resultat[c] = this.get();
        }

        return new String(resultat, Charset.forName("US-ASCII"));
    }

    /**
     * Skriver en byte på slutten av pakke data.
     *
     * @param v
     */
    public void writeByte(byte v) {

        this.add(v);
    }

    /**
     * Skriver et byte array på slutten av pakke data.
     *
     * @param v
     */
    public void writeByteArray(byte[] v) {

        for (int i = 0; i < v.length; i++) {
            this.add(v[i]);
        }
    }

    /**
     * Skriver en byte til pakken count antall ganger.
     *
     * @param tall Byte som skal skrives.
     * @param count Antall ganger byte skal skrives.
     */
    public void writeByteMultiple(byte tall, int count) {

        for (int c = 0; c < count; c++) {
            this.add(tall);
        }
    }

    /**
     * Skriver en short i Big Endian på slutten av pakke data.
     *
     * @param v
     */
    public void writeShortBE(short v) {

        this.add((byte) (v >> 8));
        this.add((byte) (v & 0xFF));
    }

    /**
     * Skriver en int i Big Endian på slutten av pakke data.
     *
     * @param v
     */
    public void writeIntBE(int v) {

        this.writeShortBE((short) (v >> 16));
        this.writeShortBE((short) (v & 0xFFFF));
    }

    /**
     * Skriver en long i Big Endian på slutten av pakke data.
     *
     * @param v
     */
    public void writeLongBE(long v) {

        this.writeIntBE((int) (v >> 32));
        this.writeIntBE((int) (v & 0xFFFFFFFF));
    }

    /**
     * Skriver en string i UTF-16LE format.
     *
     * @param tekst Tekst som skal skrives.
     */
    public void writeStringUTF16LE(String tekst) {

        char[] t = tekst.toCharArray();

        for (int c = 0; c < t.length; c++) {

            this.add((byte) (t[c] & 0xFF));
            this.add((byte) (t[c] >> 8));
        }
    }

    /**
     * Skriver en string i UTF-16LE format. Med mulighet for å velge font.
     *
     * @param tekst Tekst som skal skrives.
     * @param font Verdi
     */
    public void writeStringUTF16LE(String tekst, int font) {

        char[] t = tekst.toCharArray();

        for (int c = 0; c < t.length; c++) {

            this.add((byte) (t[c] & 0xFF));
            if (t[c] == '\n') {
                this.add((byte) 0); //Hvis newline skal ikke font skrives.
            } else {
                this.add((byte) (font));
            }
        }
    }

    /**
     * Skriver en string i ASCII format.
     *
     * @param tekst Tekst som skal skrives.
     */
    public void writeStringASCII(String tekst) {

        char[] t = tekst.toCharArray();

        for (int c = 0; c < t.length; c++) {

            this.add((byte) (t[c] & 0xFF));
        }
    }

    /**
     * Leser en byte fra internt array.
     *
     * @return Byte lest, hvis ikke mer å lese returneres 0x0 og feil printes
     * ut.
     */
    private byte get() {

        try {
            //Sjekk at vi ikke prøver å lese data som ikke finnes.
            if (this.readIndex > this.dataSize) {
                return 0;
            }

            byte v = this.data[this.readIndex];
            this.readIndex++;

            return v;
        } catch (Exception e) {
            System.out.println("PACKET DATA ERROR:");
            System.out.println("readIndex = " + this.readIndex + " dataSize = " + this.dataSize + " data array size = " + this.data.length);
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * Skriver en byte til internt byte array. Internt array blir resized hvis
     * nødvendig.
     *
     * @param v Byte som skal skrives.
     */
    private void add(byte v) {

        //Sjekk om vi må resize.
        if (this.dataSize == this.data.length) {
            this.resize();
        }

        this.data[dataSize] = v;
        this.dataSize++;
    }

    /**
     * Øker størrelsen på internt byte array.
     */
    private void resize() {

        //Opprett nytt array som er større.
        byte[] a = new byte[this.data.length + this.blockSize];

        //Kopier gamle data til nytt array
        System.arraycopy(this.data, 0, a, 0, this.data.length);

        //Ferdig.
        this.data = a;
    }

}
