package userDB;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å lese inn lagrede data om kontoer og characters.
 *
 */
public class Load {

    /**
     * Leser inn lagrede data om kontoer og characters.
     *
     * @return true hvis OK, false hvis feil.
     */
    public static boolean loadAll() {

        System.out.print("Loading account data from file...");

        String account_fil = config.Server.userDB_file_location + "/accounts.txt";

        boolean resultat = loadAccounts(account_fil);

        if (resultat) {
            System.out.println("OK! " + ManageAccounts.getNumberOfAccounts() + " accounts loaded.");
        } else {
            return false;
        }

        System.out.print("Loading character data from file...");

        String character_fil = config.Server.userDB_file_location + "/characters.txt";

        resultat = loadGameCharacters(character_fil);

        if (resultat) {
            System.out.println("OK! " + ManageCharacters.getNumberOfCharacters() + " characters loaded.");
        } else {
            return false;
        }

        return true;
    }

    /**
     * Leser inn brukerkonto data fra oppgitt fil og sender det videre til
     * ManageAccounts.
     *
     * @param fil Fil vi skal lese fra.
     *
     * @return true hvis OK, false hvis feil.
     */
    private static boolean loadAccounts(String fil) {

        boolean resultat;

        try {
            InputStreamReader in_stream = new InputStreamReader(new FileInputStream(fil), "UTF-16");
            BufferedReader in_fil = new BufferedReader(in_stream);

            while (true) {
                String data = in_fil.readLine();
                if (data != null) {
                    ManageAccounts.interpretString(data);
                } else {
                    break;
                }
            }

            in_stream.close();
            in_fil.close();
            resultat = true;
        } catch (Exception e) {
            System.out.println("Error loading account data from file: " + fil);
            resultat = false;
        }

        return resultat;
    }

    /**
     * Leser inn character data fra oppgitt fil og sender det videre til
     * ManageCharacters.
     *
     * @param fil Fil vi skal lese fra.
     *
     * @return true hvis OK, false hvis feil.
     */
    private static boolean loadGameCharacters(String fil) {

        boolean resultat;

        try {
            InputStreamReader in_stream = new InputStreamReader(new FileInputStream(fil), "UTF-16");
            BufferedReader in_fil = new BufferedReader(in_stream);

            while (true) {
                String data = in_fil.readLine();

                if (data != null) {
                    //Fjern eventuelt UTF-16 byte order mark i string.
                    data = data.replace("\uFFFE", "");
                    data = data.replace("\uFEFF", "");
                    ManageCharacters.interpretString(data);
                } else {
                    break;
                }
            }

            in_stream.close();
            in_fil.close();
            resultat = true;
        } catch (Exception e) {
            System.out.println("Error loading character data from file: " + fil);
            resultat = false;
        }

        return resultat;
    }
}
