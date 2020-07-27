package admin.commands;

import players.PlayerChat;
import userDB.Account;
import userDB.ManageAccounts;

/**
 * Oppdaterer en konto slik at last login tid er +365 dager.
 *
 * #extendtime username password
 *
 */
public class extendTime implements AdminCommand {

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått 2 argumenter.
        if (args.length != 3) {
            return;
        }

        //Finn oppgitt konto.
        int accountID = ManageAccounts.authenticateUser(args[1], args[2]);

        Account konto = ManageAccounts.getAccount(accountID);

        if (konto != null) {
            //Oppdater tid for konto.
            konto.setLastLogin(konto.getLastLogin() + (365 * 24 * 60 * 60));
            chatServer.MultiClient.sendSystemMessage("OK! Last login time updated.", p.getCharacter().getCharacterID());
        } else {
            //Ugyldig konto.
            chatServer.MultiClient.sendSystemMessage("Invalid account.", p.getCharacter().getCharacterID());
        }
    }

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

}
