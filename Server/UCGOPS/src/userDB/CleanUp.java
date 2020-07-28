package userDB;

import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å fjerne gamle kontoer og characters fra server.
 */
public class CleanUp {

    public static void execute() {

        //Hent ut nåværende tid.
        int tid = (int) ((new Date()).getTime() / 1000);

        //Gå gjennom alle bruker kontoer.
        Iterator<Account> kontoer = ManageAccounts.getAllAccounts();

        while (kontoer.hasNext()) {

            Account konto = kontoer.next();

            if (tid - konto.getLastLogin() > config.Server.accountRemoveTime) {
                //Konto er utgått på dato.
                removeAccount(konto);
            }
        }

    }

    /**
     * Fjerner oppgitt konto og alle characters som tilhører den.
     *
     * @param konto Konto som skal fjernes.
     */
    private static void removeAccount(Account konto) {

        //Gå gjennom alle characters og finn de som tilhører oppgitt konto.
        Iterator<GameCharacter> characters = ManageCharacters.getAllGameCharacters();

        while (characters.hasNext()) {

            GameCharacter c = characters.next();

            if (c.getKonto() == konto) {
                ManageCharacters.deleteGameCharacter(c.getCharacterID());
            }
        }

        ManageAccounts.deleteAccount(konto.getAccountID());
    }

}
