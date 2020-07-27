package admin.commands;

import players.PlayerChat;
import userDB.*;

public class makegm implements AdminCommand {

    public boolean checkGMrights(PlayerChat p) {
        if (p.getUCGM() == 0x9) {
            return true;
        } else {
            return false;
        }
    }

    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått 2 argumenter.
        if (args.length != 3) {
            return;
        }

        //Finn oppgitt konto.
        int accountID = ManageAccounts.authenticateUser(args[1], args[2]);

        Account konto = ManageAccounts.getAccount(accountID);

        if (konto != null) {
            //Sett UCGM status for konto.
            konto.setUcgmTag(0x3);
            chatServer.MultiClient.sendSystemMessage("OK! Account is now UCGM.", p.getCharacter().getCharacterID());
        } else {
            //Ugyldig konto.
            chatServer.MultiClient.sendSystemMessage("Invalid account.", p.getCharacter().getCharacterID());
        }
    }

}
