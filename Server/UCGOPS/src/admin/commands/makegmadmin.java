package admin.commands;

import players.PlayerChat;
import userDB.*;

/**
 * Setter en spiller som 0x9 UCGM. Server admin.
 *
 * @author UCGOSERVER.COM
 *
 */
public class makegmadmin implements AdminCommand {

    public boolean checkGMrights(PlayerChat p) {
        if (p.getUCGM() == 0x4 || p.getUCGM() == 0x5) {
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
            konto.setUcgmTag(0x9);
            chatServer.MultiClient.sendSystemMessage("OK! Account is now Admin.", p.getCharacter().getCharacterID());
        } else {
            //Ugyldig konto.
            chatServer.MultiClient.sendSystemMessage("Invalid account.", p.getCharacter().getCharacterID());
        }
    }

}
