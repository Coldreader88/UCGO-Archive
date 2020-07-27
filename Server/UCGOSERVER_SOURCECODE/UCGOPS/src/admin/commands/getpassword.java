package admin.commands;

import players.PlayerChat;
import userDB.Account;
import userDB.ManageAccounts;

/**
 * Henter ut passordet til en konto.
 *
 * #getpassword brukernavn
 *
 */
public class getpassword implements AdminCommand {

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått 1 argument.
        if (args.length != 2) {
            return;
        }

        Account konto = ManageAccounts.getAccount(args[1]);

        if (konto != null) {
            //Hent ut passord for konto.			
            chatServer.MultiClient.sendSystemMessage("Password is: " + konto.getPassword(), p.getCharacter().getCharacterID());
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
