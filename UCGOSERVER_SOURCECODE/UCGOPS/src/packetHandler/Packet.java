package packetHandler;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere en UCGO pakke. Dataene den inneholder
 * er ukrypterte.
 */
public class Packet {

    /**
     * Holder opcode for pakken. Dersom satt til -1 er pakken ugyldig.
     */
    private int opcode = -1;

    /**
     * Holder sequence nummeret for pakken.
     */
    private int sequence;

    /**
     * Holder dataene i pakken. Ukryptert.
     */
    private byte[] innhold;

    /**
     * Leser inn en pakke, ukryptert, og henter ut data og lagrer dette.
     *
     * @param raw Pakken vi mottar.
     */
    public Packet(byte[] raw) {

        //Sjekk først at pakken er minst 64 byte.
        if (raw.length < 64) {
            return;
        }

        //Sjekk at vi har en gyldig pakke.
        int head;

        head = getInt(raw, 0);

        if (head != 0x64616568) {
            return;
        }

        //Ser ut som vi har en gyldig pakke, hent ut størrelsen på dataene.
        int size;

        size = getInt(raw, 16);

        //Sjekk at størrelsen på dataene+header ikke er større en pakken vi har fått.
        if (size > (raw.length - 64)) {
            return;
        }

        //Hent ut dataene.
        this.innhold = new byte[size];

        for (int c = 0; c < size; c++) {
            this.innhold[c] = raw[64 + c];
        }

        //Hent ut opcode.
        this.opcode = getInt(raw, 24);

        //Hent ut sequence number.
        this.sequence = getInt(raw, 12);
    }

    /**
     * Tar imot info om pakke og lagrer dette.
     *
     * @param opcode Opcode for pakken.
     * @param data Data innholdet i pakken, ukryptert.
     */
    public Packet(int opcode, byte[] data) {

        this.opcode = opcode;

        //Kopier alt innholdet over istedenfor å bare linke, er sikrere.
        this.innhold = new byte[data.length];

        for (int c = 0; c < data.length; c++) {
            this.innhold[c] = data[c];
        }
    }

    /**
     *
     * @return Pakkens opcode.
     */
    public int getOpcode() {
        return this.opcode;
    }

    /**
     *
     * @return Pakkens sequence number.
     */
    public int getSequence() {
        return this.sequence;
    }

    /**
     * Setter sequence nummer for pakken.
     *
     * @param seq Sequence nummer.
     */
    public void setSequence(int seq) {
        this.sequence = seq;
    }

    /**
     * @return En kopi av denne pakken.
     */
    public Packet clone() {

        byte[] b = new byte[this.innhold.length];

        for (int i = 0; i < b.length; i++) {
            b[i] = this.innhold[i];
        }

        Packet p = new Packet(this.opcode, b);
        p.setSequence(this.sequence);

        return p;
    }

    /**
     *
     * @return Pakkens data, ukryptert.
     */
    public byte[] getData() {
        return this.innhold;
    }

    /**
     * Lager en ny pakke, header + data. Metoden setter følgende felter i
     * headeren, HEAD, TAIL, Sequence, XORSize, Blowfish size og Opcode.
     *
     * @return Byte array som inneholder pakken, ukryptert.
     */
    public byte[] makePacket() {

        int xorsize = this.innhold.length;
        int blowfishsize = xorsize;

        //Sjekk om blowfishsize må rundes opp til nærmeste 8 pga Blowfish blocksize.
        if ((xorsize & 0x7) != 0) {
            blowfishsize = ((xorsize + 8) / 8) * 8;
        }

        byte[] data = new byte[64 + blowfishsize];

        //Fyll inn de forskjellige verdiene i headeren.
        //Sett HEAD
        settInt(data, 0, 0x64616568);
        //Sett TAIL
        settInt(data, 60, 0x6C696174);
        //Sett Sequence
        settInt(data, 12, this.sequence);
        //Sett XORSize
        settInt(data, 16, xorsize);
        //Sett Blowfish size
        settInt(data, 20, blowfishsize);
        //Sett Opcode
        settInt(data, 24, this.opcode);

        //Skriv data.
        for (int c = 0; c < xorsize; c++) {
            data[64 + c] = this.innhold[c];
        }

        return data;
    }

    /**
     * Leser en Int, 4 byte fra en byte array. Leser i Little endian format.
     *
     * @param data Byte array vi skal lese fra.
     * @param offset Hvor i byte arrayet vi skal begynne.
     * @return Int lest fra data.
     */
    private static int getInt(byte[] data, int offset) {

        int tall;

        tall = (data[offset] & 0xFF) | (data[offset + 1] & 0xFF) << 8 | (data[offset + 2] & 0xFF) << 16 | (data[offset + 3] & 0xFF) << 24;

        return tall;
    }

    /**
     * Skriver en int, 4 byte, til et byte array. I little endian format.
     *
     * @param data Byte array vi skal skrive til.
     * @param offset Hvor i arrayet vi skal begynne.
     * @param value Verdien vi skal skrive.
     */
    private static void settInt(byte[] data, int offset, int value) {

        data[offset] = (byte) (value & 0xFF);
        data[offset + 1] = (byte) ((value >> 8) & 0xFF);
        data[offset + 2] = (byte) ((value >> 16) & 0xFF);
        data[offset + 3] = (byte) ((value >> 24) & 0xFF);
    }

    protected void finalize() throws Throwable {

        this.innhold = null;
    }

}
