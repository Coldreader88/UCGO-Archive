/*
 * The MIT License
 *
 * Copyright 2015 UCGOSERVER.COM.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
 * This class holds everything for compress UCGO files.
 *
 * @author UCGOSERVER
 */
public class CompressFile {

    /**
     * Will compress a single file. Existing file is replaced.
     *
     * @param file Name of file to be compressed.
     */
    public static void compressSingleFile(String file) throws FileNotFoundException, IOException {

        FileInputStream input = new FileInputStream(file);
        ByteArrayOutputStream data = new ByteArrayOutputStream();

        for (int i = input.read(); i != -1; i = input.read()) {
            data.write(i);
        }

        input.close();

        FileOutputStream output = new FileOutputStream(file);

        compress(data, output);
        output.close();
    }

    /**
     * Will compress files stored in a folder into an RFI+RFP archive. Which
     * files are included in the archive is read from the file header.txt
     *
     * @param file Name på RFI+RFP arkiv. No extension.
     * @param folder Folder to read files from.
     */
    public static void compressRfiRfpFile(String file, String folder) throws FileNotFoundException, IOException {

        //UCGO archive header i.e the RFI file.
        ByteArrayOutputStream rfiHeader = new ByteArrayOutputStream();

        //UCGO archive contents i.e the RFP file.
        FileOutputStream rfpFile = new FileOutputStream(file + ".RFP");

        //Read all the file names from header.txt
        BufferedReader headerInfo = new BufferedReader(new FileReader(folder + "/header.txt"));

        LinkedList<String> filer = new LinkedList<>();

        while (true) {
            String s = headerInfo.readLine();
            if (s == null) {
                break;
            }

            filer.add(s);
        }

        writeInt(rfiHeader, filer.size()); //Number of files in the archive.
        System.out.println("Writing files to " + file + ".RFP");

        //Offset into the RFP file where comress data is currently being written.
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

        rfpFile.flush();
        rfpFile.close();

        FileOutputStream rfiFile = new FileOutputStream(file + ".RFI");
        compress(rfiHeader, rfiFile);
        rfiFile.close();

    }

    /**
     * Will compress every single file in given folder. Existing files are
     * replaced.
     *
     * @param path Folder to compress.
     * @param recursive Include sub folders.
     */
    public static void compressDirectory(String path, boolean recursive) throws IOException {

        File[] filListe = new File(path).listFiles();

        for (int i = 0; i < filListe.length; i++) {
            if (recursive && filListe[i].isDirectory()) {
                compressDirectory(filListe[i].getPath(), true);
            } else {
                compressSingleFile(filListe[i].getPath());
                System.out.println(filListe[i].getPath());
            }
        }

    }

    /**
     * Will compress a file and output compress data and footer to given stream.
     *
     * @param file Name of file to compress.
     * @param outputFile Compress file is output here.
     *
     * @return Number of compressed bytes written to stream.
     *
     */
    private static int compress(String file, FileOutputStream outputFile) throws FileNotFoundException, IOException {

        FileInputStream input = new FileInputStream(file);
        ByteArrayOutputStream data = new ByteArrayOutputStream();

        for (int i = input.read(); i != -1; i = input.read()) {
            data.write(i);
        }

        input.close();

        return compress(data, outputFile);
    }

    /**
     * Compresses given ByteArrayOutputStream and outputs to file together with
     * footer.
     *
     * The output file will not be closed.
     *
     * @param data Data to compress.
     * @param outputFile Compressed data is output to this file.
     *
     * @return Number of compressed bytes written.
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

        outputFile.write(compressedData.toByteArray());

        //Footer.
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
     * @param data
     * @param n
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
     * @param data
     * @param v
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
     * @param output
     * @param v
     */
    private static void writeChar(ByteArrayOutputStream output, char v) {

        output.write(v & 0xFF);
        v >>= 8;
        output.write(v & 0xFF);
    }

    /**
     * @param output
     * @param s
     */
    private static void writeString(ByteArrayOutputStream output, String s) {

        for (int c = 0; c < s.length(); c++) {

            writeChar(output, s.charAt(c));
        }
    }

}
