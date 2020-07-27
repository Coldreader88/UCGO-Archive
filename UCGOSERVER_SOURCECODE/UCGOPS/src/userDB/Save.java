package userDB;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å lagre konto og character data til filer.
 *
 */
public class Save {

    /**
     * Lagrer konto og character info til fil.
     */
    public static void saveAll() {

        StringBuilder account_data = new StringBuilder();

        Iterator<Account> kontoer = ManageAccounts.getAllAccounts();

        //Gå gjennom alle kontoer og gjør dem om til strings.
        while (kontoer.hasNext()) {
            account_data.append(kontoer.next());
        }

        //Hent ut hvor kontodata skal lagres. 
        String account_fil = config.Server.userDB_file_location + "/accounts.txt";
        String account_backup = config.Server.userDB_file_location + "/accounts";

        Calendar dato = new GregorianCalendar();

        account_backup += dato.get(Calendar.YEAR) + "-" + dato.get(Calendar.DAY_OF_MONTH) + "-" + dato.get(Calendar.MONTH) + "-";
        account_backup += dato.get(Calendar.HOUR) + "-" + dato.get(Calendar.MINUTE) + ".txt";

        admin.logging.globalserverMsg("Saving account data to file.");

        try {

            OutputStreamWriter accountOut = new OutputStreamWriter(new FileOutputStream(account_fil, false), "UTF-16");
            OutputStreamWriter accountOutBackup = new OutputStreamWriter(new FileOutputStream(account_backup, false), "UTF-16");

            skrivTilFil(accountOut, account_data.toString());
            skrivTilFil(accountOutBackup, account_data.toString());

            accountOut.close();
            accountOutBackup.close();
        } catch (Exception e) {
            e.printStackTrace();
            admin.logging.globalserverMsg("userDB.Save.saveAll(): Error saving account data to file.");
        }

        //Hent ut hvor character data skal lagres. 
        String character_fil = config.Server.userDB_file_location + "/characters.txt";
        String character_backup = config.Server.userDB_file_location + "/characters";

        character_backup += dato.get(Calendar.YEAR) + "-" + dato.get(Calendar.DAY_OF_MONTH) + "-" + dato.get(Calendar.MONTH) + "-";
        character_backup += dato.get(Calendar.HOUR) + "-" + dato.get(Calendar.MINUTE) + ".txt";

        admin.logging.globalserverMsg("Saving character data to file.");

        Iterator<GameCharacter> characters = ManageCharacters.getAllGameCharacters();

        //Gå gjennom alle characters, gjør dem om til strings og lagre til fil.
        try {
            OutputStreamWriter characterOut = new OutputStreamWriter(new FileOutputStream(character_fil, false), "UTF-16");
            OutputStreamWriter characterOutBackup = new OutputStreamWriter(new FileOutputStream(character_backup, false), "UTF-16");

            while (characters.hasNext()) {

                String character_data = characters.next().toString();
                //Skriv en og en character til fil i append mode, ellers kan string data bli for mye
                //og vi får out of memory exception.
                skrivTilFil(characterOut, character_data);
                skrivTilFil(characterOutBackup, character_data);

            }

            characterOut.close();
            characterOutBackup.close();
        } catch (Exception e) {
            e.printStackTrace();
            admin.logging.globalserverMsg("userDB.Save.saveAll(): Error saving character data to file.");
        }

    }

    /**
     * Skriver data til fil.
     *
     * @param ut_fil Stream data skrives til.
     * @param str Tekst som skal skrives.
     */
    private static void skrivTilFil(OutputStreamWriter ut_fil, String str) {

        try {
            ut_fil.write(str);
        } catch (Exception e) {
            admin.logging.globalserverMsg("Exception userDB.Save.skrivTilFil():" + e.toString());
        }
    }
}
