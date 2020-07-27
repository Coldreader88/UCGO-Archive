package ucgozlib;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * Denne klassen brukes til å dekomprimere en enkeltstående fil.
 *
 * @author UCGOSERVER
 */
public class DecompressFile {

    /**
     * Dekomprimerer en fil. Eksisterende fil overskrives med dekomprimerte
     * data.
     *
     * @param file Navn på fil.
     */
    public static void decompressSingleFile(String file) throws FileNotFoundException, IOException {

        InflaterInputStream input = new InflaterInputStream(new FileInputStream(file));
        ByteArrayOutputStream data = Scrambler.descrambleData(input);

        input.close();

        FileOutputStream output = new FileOutputStream(file);
        output.write(data.toByteArray());
        output.flush();
        output.close();

    }

    /**
     * Dekomprimerer en RFI+RFP fil og lagrer alt innhold i en folder. Skriver
     * også en header.txt fil i folder.
     *
     * @param file Navn på RFI+RFP fil. Ingen extenstion.
     * @param folder Folder vi skal lagre innholdet i.
     */
    public static void decompressRfiRfpFile(String file, String folder) throws FileNotFoundException, IOException {
        //Åpne header filen.
        FileInputStream inputHeader = new FileInputStream(file + ".rfi");
        InflaterInputStream header = new InflaterInputStream(inputHeader);

        //Åpne filen for å skrive info til.
        BufferedWriter headerInfo = new BufferedWriter(new FileWriter(folder + "/header.txt"));

        System.out.println("Decompressing files...");

        //Antall filer som er i RFP filen.
        int antallFiler = readInt(header);

        for (int i = 0; i < antallFiler; i++) {
            int nameLength = readInt(header); //Antall tegn i filnavnet.
            String filnavn = readString(header, nameLength);
            readInt(header); //Størrelse på komprimerte data + footer
            int offset = readInt(header); //hvor i RFP fil data befinner seg.

            System.out.println(filnavn);
            decompressFromRFP(file + ".rfp", offset, folder + "/" + filnavn);

            //Skriv info til tekst filen.
            headerInfo.write(filnavn + "\n");
        }

        headerInfo.close();
        inputHeader.close();

        System.out.println("Done!");
    }

    /**
     * Dekomprimerer data fra en RFP og lagrer i oppgitt fil. Denne metoden
     * dekomprimerer en chunk med data fra RFP fil, angitt ved position.
     *
     * @param file Navn på RFP fil. Ingen extenstion f.eks DAT_0008
     * @param position Hvor i RFP fil data befinner seg.
     * @param outputFile Filen vi skal skrive dekomprimerte data til.
     */
    private static void decompressFromRFP(String file, int position, String outputFile) throws FileNotFoundException, IOException {

        Inflater inf = new Inflater();
        FileInputStream inputFile = new FileInputStream(file);

        inputFile.getChannel().position(position);

        InflaterInputStream inData = new InflaterInputStream(inputFile, inf);
        FileOutputStream output = new FileOutputStream(outputFile);

        output.write(Scrambler.descrambleData(inData).toByteArray());
        output.flush();
        output.close();
        inputFile.close();
    }

    /**
     * Leser en character fra input stream.
     *
     * @param input
     * @return Character lest.
     */
    private static char readChar(InflaterInputStream input) {

        int r = 0;

        try {
            r = Scrambler.descrambleByte(input);
            r |= Scrambler.descrambleByte(input) << 8;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (char) r;
    }

    /**
     * Leser en string fra input.
     *
     * @param input Input stream.
     * @param length Lengde på string.
     *
     * @return String lest.
     */
    private static String readString(InflaterInputStream input, int length) {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += readChar(input);
        }
        return s;
    }

    /**
     * Leser en integer fra input stream.
     *
     * @param input
     * @return Integer lest.
     */
    private static int readInt(InflaterInputStream input) {

        int r = 0;

        try {
            r = Scrambler.descrambleByte(input);
            r |= Scrambler.descrambleByte(input) << 8;
            r |= Scrambler.descrambleByte(input) << 16;
            r |= Scrambler.descrambleByte(input) << 24;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }
}
