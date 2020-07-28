package admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å blokkere IP adresser.
 *
 * Eksempel: Å blokkere 190. vil blokkere alle IP-er som starter med 190.
 */
public class ipblock {

    //Blokkerte IP-er lagres her.
    private static LinkedList<String> blocked_ip = new LinkedList<String>();

    //Midlertidig blokkerte IP-er lagres her. Dette brukes av criminal systemet.
    //Key=IP, Value=Unix timestamp som angir hvor lenge IP er blokkert.
    private static ConcurrentHashMap<String, Integer> tempBlockIP = new ConcurrentHashMap<String, Integer>();

    private static String blocked_ip_fil = "ipblock.txt";

    static {
        loadFromFile(blocked_ip_fil);
    }

    /**
     * Sjekker om en IP er permanent blokkert eller midlertidig blokkert.
     *
     * @param ip IP som skal sjekkes.
     *
     * @return TRUE hvis blokkert, FALSE hvis ikke.
     */
    public static boolean isBlocked(String ip) {
        //Sjekk først for permanent blokkerte IP adresser.
        Iterator<String> ip_liste = blocked_ip.iterator();

        while (ip_liste.hasNext()) {
            if (ip.startsWith(ip_liste.next())) {
                return true;
            }
        }

        //Sjekk midlertidig blokkerte IP adresser.
        return temporaryIPisBlocked(ip);
    }

    /**
     * Blokkerer en IP.
     *
     * @param ip IP som skal blokkeres.
     */
    public static void blockIP(String ip) {        
        //Sjekk først at IP ikke er blokkert allerede.
        if (!isBlocked(ip)) {
            blocked_ip.add(ip);
        }
    }

    /**
     * Blokkerer en IP midlertidig.
     *
     * @param ip IP som skal blokkeres.
     * @param time Unix timestamp som angir når, i sekunder, ban utgår.
     */
    public static void temporaryBlockIP(String ip, int time) {        
        //Sjekk om IP er allerede blokkert, isåfall oppdater tiden for den.
        Iterator<String> ipListe = tempBlockIP.keySet().iterator();
        String t = null; //Dersom IP allerede er blokkert vil den (KEY) lagres her.

        while (ipListe.hasNext() && t == null) {

            String s = ipListe.next();

            if (s.equals(ip)) {
                t = s;
            }
        }

        if (t != null) {
            //IP er allerede blokkert. Sjekk om den nye tiden er lengre enn den forrige.
            int c = tempBlockIP.get(t);

            if (c < time) {
                tempBlockIP.put(t, time);
            }
        } else {
            tempBlockIP.put(ip, time);
        }

    }

    /**
     * Sjekker om en IP er midlertidig blokkert.
     *
     * @param ip IP som skal sjekkes.
     *
     * @return true hvis blokkert, false hvis ikke.
     */
    private static boolean temporaryIPisBlocked(String ip) {

        //Fjern først gamle IP adresser som ikke skal være blokkert lengre.
        cleanTemporaryBlockedIP();

        Iterator<String> ip_liste = tempBlockIP.keySet().iterator();

        while (ip_liste.hasNext()) {
            if (ip.startsWith(ip_liste.next())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Går gjennom listen over midlertidig blokkerte IP adresser og sjekker om
     * noen av dem ikke skal være blokkert lenger.
     */
    private static void cleanTemporaryBlockedIP() {

        int tid = (int) (System.currentTimeMillis() / 1000);

        Iterator<String> ip_liste = tempBlockIP.keySet().iterator();

        while (ip_liste.hasNext()) {

            String ip = ip_liste.next();

            int v = tempBlockIP.get(ip);

            //v = Timestamp når IP ikke lengre skal være blokkert.
            if (tid >= v) {
                tempBlockIP.remove(ip);
            }
        }

    }

    /**
     * Fjerner blokkering av en IP.
     *
     * @param ip IP som ikke lengre skal blokkeres.
     */
    public static void unblockIP(String ip) {
        blocked_ip.remove(ip);
    }

    /**
     * Lagrer listen over blokkerte IP-er i en tekst fil. En IP pr linje.
     */
    public static void saveToFile() {

        try {

            BufferedWriter ut = new BufferedWriter(new FileWriter(blocked_ip_fil, false));

            Iterator<String> ip_liste = blocked_ip.iterator();

            while (ip_liste.hasNext()) {
                ut.write(ip_liste.next() + "\n");
            }

            ut.close();

        } catch (Exception e) {
            System.out.println("ipblock.java: Error saving list of blocked IPs to file:" + blocked_ip_fil);
        }

    }

    /**
     * Leser inn en liste over blokkerte IP-er fra en tekst fil. En IP pr linje.
     *
     * NB! Kun en IP pr linje, eller del av en IP for å blokkere en range. Ingen
     * mellomrom, tab eller kommentarer.
     *
     * @param file Filen IP listen skal leses fra.
     */
    public static void loadFromFile(String file) {

        try {

            BufferedReader in = new BufferedReader(new FileReader(file));

            String ip = null;

            do {

                ip = in.readLine();
                if (ip != null) {
                    blockIP(ip);
                }

            } while (ip != null);

            in.close();

        } catch (Exception e) {
            System.out.println("ipblock.java: Error loading list of blocked IPs from file:" + file);
        }
    }

}
