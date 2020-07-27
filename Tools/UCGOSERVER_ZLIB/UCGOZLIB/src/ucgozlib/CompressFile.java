/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucgozlib;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * Denne klassen brukes til å komprimere en enkeltstående fil.
 *
 * @author UCGOSERVER
 */
public class CompressFile {

    /**
     * Komprimerer en fil. Eksisterende fil overskrives med komprimerte data.
     *
     * @param file Fil som skal komprimeres.
     */
    public static void compressSingleFile(String file) throws FileNotFoundException, IOException {

        FileInputStream input = new FileInputStream(file);
        ByteArrayOutputStream data = Scrambler.scrambleData(input);

        input.close();

        FileOutputStream output = new FileOutputStream(file);

        compress(data, output);
        output.close();
    }

    /**
     * Komprimerer en rekke filer inn i et RFI+RFP arkiv. Hvilke filer som skal
     * komprimeres leses fra filen "folder"/header.txt feks DAT_0008/header.txt
     *
     * Header filen er kun en liste over filene som skal være med. Ett filnavn
     * pr linje.
     *
     * @param file Navn på RFI+RFP arkiv, uten extension.
     * @param folder Hvilken folder filene hentes fra.
     */
    public static void compressRfiRfpFile(String file, String folder) throws FileNotFoundException, IOException {

        //Innholdet i RFI header filen lagres her.
        ByteArrayOutputStream rfiHeader = new ByteArrayOutputStream();

        //RFP fil.
        FileOutputStream rfpFile = new FileOutputStream(file + ".RFP");

        //Les liste over filer fra header.txt
        BufferedReader headerInfo = new BufferedReader(new FileReader(folder + "/header.txt"));
        //Liste over alle filene + ID lagres her.
        LinkedList<String> filer = new LinkedList<>();

        //Les info fra header.txt
        while (true) {
            String s = headerInfo.readLine();
            if (s == null) {
                break;
            }

            //For bakover kompabilitet med gamle ucgoDecompress/Compress fjerner vi eventuell int på slutten av linjen.
            String[] z = s.split(" ");
            filer.add(z[0]);
        }

        writeInt(rfiHeader, filer.size()); //Header fil skal alltid begynne med antall filer i .RFP fil.
        System.out.println("Writing files to " + file + ".RFP");

        //Offset i .RFP der komprimerte data blir skrevet akkurat nå.
        int offset = 0;

        Iterator<String> ucgoFiler = filer.iterator();

        while (ucgoFiler.hasNext()) {

            String fil = ucgoFiler.next();
            System.out.println("Adding: " + fil);

            int size = compress(folder + "/" + fil, rfpFile);
            size += 32; //+32 For footer.

            writeInt(rfiHeader, fil.length());
            writeString(rfiHeader, fil);
            writeInt(rfiHeader, size);
            writeInt(rfiHeader, offset);

            offset += size;
        }

        //Da er RFP fil ferdig.
        rfpFile.flush();
        rfpFile.close();

        FileOutputStream rfiFile = new FileOutputStream(file + ".RFI");
        compress(Scrambler.scrambleData(rfiHeader), rfiFile);
        rfiFile.close();

    }

    /**
     * Komprimerer ABSOLUTT ALLE filer i oppgitt folder. Kan også gjøres
     * rekursivt.
     *
     * @param path Folder vi skal komprimere filer i.
     * @param recursive Angir om vi skal inkludere sub directories.
     */
    public static void compressDirectory(String path, boolean recursive) throws IOException {

        //Lag liste over allefiler som befinner seg i katalog.
        File[] filListe = new File(path).listFiles();

        for (int i = 0; i < filListe.length; i++) {
            if ( recursive && filListe[i].isDirectory() ) compressDirectory(filListe[i].getPath(),true);
            else {
                compressSingleFile(filListe[i].getPath());
                System.out.println(filListe[i].getPath());
            }
        }

    }

    /**
     * Komprimerer en fil og skriver til gitt output stream. Footer skriver
     * også.
     *
     * @param file Navn på fil som skal komprimeres.
     * @param outputFile Fil vi skriver til.
     *
     * @return Antall komprimerte bytes skrevet.
     *
     */
    private static int compress(String file, FileOutputStream outputFile) throws FileNotFoundException, IOException {

        FileInputStream input = new FileInputStream(file);
        ByteArrayOutputStream data = Scrambler.scrambleData(input);

        input.close();

        return compress(data, outputFile);
    }

    /**
     * Skriver komprimerte data til fil. Denne metoden skriver også footer som
     * skal følge etter data. Det er forventet at data allerede er scrambled.
     *
     * NB! Output fil blir ikke stengt.
     *
     * @param data Data som skal skrives.
     * @param outputFile Fil vi skal skrive til.
     *
     * @return Antall komprimerte bytes skrevet.
     */
    private static int compress(ByteArrayOutputStream data, FileOutputStream outputFile) throws IOException {

        Deflater def = new Deflater();
        def.setLevel(Deflater.BEST_COMPRESSION);
        def.setStrategy(Deflater.DEFAULT_STRATEGY);

        ByteArrayOutputStream compressedData = new ByteArrayOutputStream();
        DeflaterOutputStream output = new DeflaterOutputStream(compressedData, def);
        output.write(data.toByteArray());
        output.flush();
        output.close();

        //Skriv komprimerte data til fil.
        outputFile.write(compressedData.toByteArray());

        //Skriv footer.
        writeInt(outputFile, 0xFCC1F0C0);
        writeInt(outputFile, 0x0);
        writeInt(outputFile, compressedData.size());
        writeInt(outputFile, data.size());
        writeInt(outputFile, 0x0);
        writeInt(outputFile, 0x0);
        writeInt(outputFile, 0x0);
        writeInt(outputFile, 0x0);

        outputFile.flush();

        return compressedData.size();
    }

    /**
     * Skriver en INT i little endian format til fil.
     *
     * @param data Output file
     * @param n Int som skal skrives.
     */
    private static void writeInt(FileOutputStream output, int n) throws IOException {

        int m = n;
        m &= 0xFF;
        output.write(m);

        m = n;
        m >>= 8;
        m &= 0xFF;
        output.write(m);

        m = n;
        m >>= 16;
        m &= 0xFF;
        output.write(m);

        m = n;
        m >>= 24;
        m &= 0xFF;
        output.write(m);
    }

    /**
     * Skriver en INT i little endian format til fil.
     *
     * @param data Output file
     * @param v Int som skal skrives.
     */
    private static void writeInt(ByteArrayOutputStream output, int v) throws IOException {

        output.write(v & 0xFF);
        v >>= 8;
        output.write(v & 0xFF);
        v >>= 8;
        output.write(v & 0xFF);
        v >>= 8;
        output.write(v & 0xFF);
    }

    /**
     * Skriver en Character i little endian format til output.
     *
     * @param output Output Buffer
     * @param v Verdi som skal skrives.
     */
    private static void writeChar(ByteArrayOutputStream output, char v) {

        output.write(v & 0xFF);
        v >>= 8;
        output.write(v & 0xFF);
    }

    /**
     * Skriver en string i UTF-16LE format til output. NULL terminator skrives
     * ikke.
     *
     * @param output Output buffer.
     * @param s String som skal skrives.
     */
    private static void writeString(ByteArrayOutputStream output, String s) {

        for (int c = 0; c < s.length(); c++) {

            writeChar(output, s.charAt(c));
        }
    }

}
