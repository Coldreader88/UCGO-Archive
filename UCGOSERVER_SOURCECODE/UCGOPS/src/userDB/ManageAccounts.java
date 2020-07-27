package userDB;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å håndtere bruker kontoer.
 */
public class ManageAccounts {

    /*
	 * Alle brukerkontoer lagres her.
	 * 
	 * Key = Account ID.
	 * Value = Objekt som holder konto informasjon.
     */
    private static ConcurrentHashMap<Integer, Account> kontoer = new ConcurrentHashMap<Integer, Account>();

    /**
     * Oppretter en ny konto med oppgitt brukernavn og passord, konto blir også
     * registrert i listen.
     *
     * @param username Brukernavn for konto.
     * @param password Passord for konto.
     *
     * @return Account objektet som tilsvarer konto.
     */
    public static synchronized Account newAccount(String username, String password) {

        Account konto = null;

        int account_id;

        //Lag et unikt konto ID.
        do {
            account_id = 0x01000000 + (int) (Math.random() * (double) 0xFFFFFF);
        } while (kontoer.get(account_id) != null);

        konto = new Account(account_id, username, password);

        kontoer.put(account_id, konto);

        return konto;
    }

    /**
     * Sjekker om oppgitt brukernavn og passord er gyldig.
     *
     * @param username
     * @param password
     *
     * @return Account ID dersom det finnes en konto med oppgitt brukernavn og
     * passord er gyldig. -1 dersom passord er ugyldig, 0 dersom brukernavn ikke
     * finnes.
     */
    public static int authenticateUser(String username, String password) {

        Iterator<Account> accounts = kontoer.values().iterator();

        while (accounts.hasNext()) {

            Account konto = accounts.next();
            if (konto.getUsername().compareTo(username) == 0) {
                if (konto.getPassword().compareTo(password) == 0) {
                    return konto.getAccountID();
                } else {
                    return -1;
                }
            }
        }

        return 0; //Fant ingen konto med oppgitt brukernavn.
    }

    /**
     * Returnerer Account objektet for oppgitt konto ID.
     *
     * @param account_id Konto ID
     *
     * @return Account objekt eller null hvis kontoen ikke finnes.
     */
    public static Account getAccount(int account_id) {
        return kontoer.get(account_id);
    }

    /**
     *
     * @param username Brukernavn til en konto.
     *
     * @return Account objektet for denne brukeren eller null dersom konto ikke
     * finnes.
     */
    public static Account getAccount(String username) {

        Iterator<Account> accounts = kontoer.values().iterator();

        while (accounts.hasNext()) {
            Account konto = accounts.next();
            if (konto.getUsername().compareTo(username) == 0) {
                return konto;
            }
        }

        return null;
    }

    /**
     * Sletter oppgitt konto.
     *
     * @param accountID ID til konto som skal slettes.
     */
    public static void deleteAccount(int accountID) {

        kontoer.remove(accountID);
    }

    /**
     *
     * @return Iterator over alle kontoene.
     */
    public static Iterator<Account> getAllAccounts() {
        return kontoer.values().iterator();
    }

    /**
     *
     * @return Antall registrerte kontoer.
     */
    public static int getNumberOfAccounts() {
        return kontoer.size();
    }

    /**
     * Tar imot en string på formatet account id:property:value og tolker den.
     *
     * @param str String som inneholder konto info.
     */
    public static void interpretString(String str) {

        String[] data = str.split(":", 3);

        //Sjekk at vi har fått et array med 3 elementer, ellers er det feil i formatet til string.
        if (data.length != 3) {
            admin.logging.globalserverMsg("Invalid format for account info in the string: " + str);
            return;
        }

        try {

            int account_id;

            account_id = Integer.parseInt(data[0]);

            Account konto = getCreateAccount(account_id);

            //Finn hvilken verdi som skal settes.
            if (data[1].compareTo("username") == 0) {
                //Sett brukernavn.
                konto.setUsername(data[2]);
            } else if (data[1].compareTo("password") == 0) {
                //Sett passord.
                konto.setPassword(data[2]);
            } else if (data[1].compareTo("ucgmTag") == 0) {
                //Sett UCGM tag.
                konto.setUcgmTag(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("createTime") == 0) {
                //Sett når konto ble opprettet.
                konto.setCreateTime(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("lastLogin") == 0) {
                //Sett når konto ble sist brukt.
                konto.setLastLogin(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("locked") == 0) {
                //Sett om konto er stengt eller ikke.
                int lock = Integer.parseInt(data[2]);
                if (lock == 0) {
                    konto.setLocked(false);
                } else if (lock == 1) {
                    konto.setLocked(true);
                }
            }
        } catch (Exception e) {
            admin.logging.globalserverMsg("Error interpreting account info from the string: " + str);
        }

    }

    /**
     * Returnerer Account objektet for en konto. Dersom oppgitt konto ikke
     * finnes vil den bli opprettet. Denne metoden SKAL KUN brukes ved innlesing
     * av kontoer fra fil, dvs ved server oppstart.
     *
     * @param account_id Account ID for konto.
     *
     * @return Konto med oppgitt ID.
     */
    private static Account getCreateAccount(int account_id) {

        Account konto;

        konto = kontoer.get(account_id);

        if (konto == null) {
            //Konto fantes ikke, opprett ny.
            konto = new Account(account_id);
            kontoer.put(account_id, konto);
        }

        return konto;
    }

}
