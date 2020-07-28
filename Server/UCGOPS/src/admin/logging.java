package admin;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å logge ting. En seperat logfil for hver server.
 */
public class logging {

    //Plassering til de forskjellige log filene.
    private static String info_file = "logs/logfile_infoserver.txt";

    private static String login_file = "logs/logfile_loginserver.txt";

    private static String chat_file = "logs/logfile_chatserver.txt";

    private static String game_file = "logs/logfile_gameserver.txt";

    private static String global_file = "logs/logfile.txt"; //Ting ikke relatert til en spesiell server.

    /**
     * Skriver log melding til info serverens logfil.
     *
     * @param msg Melding som skal skrives.
     */
    public static void infoserverMsg(String msg) {
        //writeMsg(info_file,msg);
    }

    /**
     * Skriver log melding til login serverens logfil.
     *
     * @param msg Melding som skal skrives.
     */
    public static void loginserverMsg(String msg) {
        //writeMsg(login_file,msg);
    }

    /**
     * Skriver log melding til chat serverens logfil.
     *
     * @param msg Melding som skal skrives.
     */
    public static void chatserverMsg(String msg) {
        //writeMsg(chat_file,msg);
    }

    /**
     * Skriver log melding til game serverens logfil.
     *
     * @param msg Melding som skal skrives.
     */
    public static void gameserverMsg(String msg) {
        writeMsg(game_file, msg);
    }

    /**
     * Skriver log melding til serverens globale logfil.
     *
     * @param msg Melding som skal skrives.
     */
    public static void globalserverMsg(String msg) {
        writeMsg(global_file, msg);
    }

    /**
     * Skriver ut en log melding.
     *
     * NB! Etter meldingen vil denne metoden legge til TID+DATO.
     *
     * @param out Fil der meldingen skal skrives.
     *
     * @param msg Meldingen som skal skrives.
     */
    private static void writeMsg(String fil, String msg) {

        try {
            OutputStreamWriter ut_fil = new OutputStreamWriter(new FileOutputStream(fil, true));
            ut_fil.write(msg + " (" + new Date() + ")\r\n");
            ut_fil.close();
        } catch (Exception e) {
        }
    }

}
