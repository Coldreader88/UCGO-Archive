package ucgozlib;

/**
 * THIS VERSION OF UCGOZLIB IS MEANT TO BE USED WITH THE UCGOSERVER.COM GAME CLIENT AND NOT THE OFFICIAL SERVER CLIENT.
 * 
 * UCGOZLIB is used for compressing and decompressing UCGO files. It works with
 * individual files e.g gameclient.cfg and RFP+RFI files e.g DAT_0008.
 *
 * USAGE:
 *
 * UCGOZLIB decompress FILE FOLDER 
 * UCGOZLIB compress FILE FOLDER 
 * UCGOZLIB compress-recursive FOLDER
 *
 * FOLDER is optional and is only used when compressing/decompressing RFP+RFI
 * files.
 *
 * FILE is the file to be compress/decompressed. For RFI+RFP files the extension
 * should not be included.
 *
 * EXAMPLE USAGE:
 * java -jar UCGOZLIB.JAR decompress DAT_0008 DAT Will decompress
 * the file DAT_0008.RFI and DAT_0008.RFP into the folder name DAT. It is
 * expected the folder DAT already exists.
 *
 * java -jar UCGOZLIB.JAR compress DAT_0008 DAT Will compress the files in the
 * folder DAT into the files DAT_0008.RFI and DAT_0008.RFP Note that any
 * existing DAT_0008 files will be overwritten.
 *
 * java -jar UCGOZLIB.JAR decompress gameclient.cfg Will decompress the file
 * gameclient.cfg and overwrite it with the new decompressed file.
 *
 * java -jar UCGOZLIB.JAR compress gameclient.cfg Will compress the file
 * gameclient.cfg and overwrite it with the new compressed file.
 *
 * java -jar UCGOZLIB.JAR compress-recursive OBJECT Will compress and overwrite
 * every individual file in the folder OBJECT and any sub-folders.
 *
 *
 * NOTE: When compressing and decompressing RFI+RFP files e.g DAT_0008 there's also
 * a header.txt file in the folder with the files. For example if you do the
 * following: java -jar UCGOZLIB.JAR decompress DAT_0008 DAT There will now be a
 * file DAT/header.txt
 *
 * The header.txt file lists every file which is part of the archive and if you
 * wish to add new files or remove existing files you can edit the header.txt
 * file.
 *
 * @author UCGOSERVER
 */
public class UCGOZLIB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("\nUCGOZLIB - www.ucgoserver.com");
        
        if (args.length < 2) {
            System.out.println("Usage:");
            System.out.println("UCGOZLIB decompress <fil> <folder>");
            System.out.println("UCGOZLIB compress <fil> <folder>");
            System.out.println("UCGOZLIB compress-recursive <folder>");
            return;
        }
        
        try {
            if (args[0].compareToIgnoreCase("decompress") == 0) {
                if (args.length == 2) {
                    //Dekomprimer enkeltstående fil.
                    DecompressFile.decompressSingleFile(args[1]);
                } else {
                    //Dekomprimer RFI+RFP fil.
                    DecompressFile.decompressRfiRfpFile(args[1], args[2]);
                }
            } else if (args[0].compareToIgnoreCase("compress") == 0) {
                if (args.length == 2) {
                    //Komprimer enkeltstående fil.
                    CompressFile.compressSingleFile(args[1]);
                } else {
                    //Komprimer RFI+RFP fil.
                    CompressFile.compressRfiRfpFile(args[1], args[2]);
                }                
            } else if (args[0].compareToIgnoreCase("compress-recursive") == 0) {
                if (args.length == 2) {
                    CompressFile.compressDirectory(args[1], true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
