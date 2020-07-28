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

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

/**
 * This class holds everything for decompressing UCGO files.
 *
 * @author UCGOSERVER
 */
public class DecompressFile {

    /**
     * Will decompress a single file. Existing file will be replaced.
     *
     * @param file Name of file.
     */
    public static void decompressSingleFile(String file) throws FileNotFoundException, IOException {

        InflaterInputStream input = new InflaterInputStream(new FileInputStream(file));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        for (int i = input.read(); i != -1; i = input.read()) {
            output.write(i);
        }

        input.close();

        FileOutputStream outputFile = new FileOutputStream(file);
        outputFile.write(output.toByteArray());
        output.flush();
        output.close();
    }

    /**
     * Will decompress a RFI+RFP achive and output to folder.
     *
     * @param file Name of RFI+RFP archive. No extension.
     * @param folder Output folder.
     */
    public static void decompressRfiRfpFile(String file, String folder) throws FileNotFoundException, IOException {
        //RFI Header file.
        FileInputStream inputHeader = new FileInputStream(file + ".rfi");
        InflaterInputStream header = new InflaterInputStream(inputHeader);

        //header.txt file stored in output folder.
        BufferedWriter headerInfo = new BufferedWriter(new FileWriter(folder + "/header.txt"));

        System.out.println("Decompressing files...");

        //Number files in this archive.
        int antallFiler = readInt(header);

        for (int i = 0; i < antallFiler; i++) {
            int nameLength = readInt(header); //Number of character in file name.
            String filnavn = readString(header, nameLength);
            readInt(header); //Size of compressed data + footer
            int offset = readInt(header); //Where in the RFP file data is located.            

            System.out.println(filnavn);
            decompressFromRFP(file + ".rfp", offset, folder + "/" + filnavn);

            headerInfo.write(filnavn + "\n");
        }

        headerInfo.close();
        inputHeader.close();

        System.out.println("Done!");
    }

    /**
     * Will decompress data from an RFP file and ouput to given file.
     *
     * @param file Name of RFP fil. No extension.
     * @param position Offset into RFP file where data is located.
     * @param outputFile Name of output file.
     */
    private static void decompressFromRFP(String file, int position, String outputFile) throws FileNotFoundException, IOException {

        FileInputStream inputFile = new FileInputStream(file);

        inputFile.getChannel().position(position);

        InflaterInputStream inData = new InflaterInputStream(inputFile);
        FileOutputStream output = new FileOutputStream(outputFile);

        for (int i = inData.read(); i != -1; i = inData.read()) {
            output.write(i);
        }

        output.flush();
        output.close();
        inputFile.close();
    }

    /**
     * @param input
     * @return
     */
    private static char readChar(InflaterInputStream input) throws IOException {

        int r;

        r = input.read();
        r |= input.read() << 8;

        return (char) r;
    }

    /**
     * @param input
     * @param length
     * @return
     */
    private static String readString(InflaterInputStream input, int length) throws IOException {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += readChar(input);
        }
        return s;
    }

    /**
     * @param input
     * @return
     */
    private static int readInt(InflaterInputStream input) throws IOException {

        int r;

        r = input.read();
        r |= input.read() << 8;
        r |= input.read() << 16;
        r |= input.read() << 24;

        return r;
    }
}
