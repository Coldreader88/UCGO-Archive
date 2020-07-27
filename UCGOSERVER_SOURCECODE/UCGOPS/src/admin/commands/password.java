package admin.commands;

import players.PlayerChat;
import userDB.Account;
import userDB.ManageAccounts;

public class password implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått 3 argumenter.
        if (args.length != 4) {
            return;
        }

        //Finn oppgitt konto.
        int accountID = ManageAccounts.authenticateUser(args[1], args[2]);

        Account konto = ManageAccounts.getAccount(accountID);

        if (konto != null) {
            //Sett nytt passord for konto.
            konto.setPassword(args[3]);
            chatServer.MultiClient.sendSystemMessage("OK! Password changed.", p.getCharacter().getCharacterID());
        } else {
            //Ugyldig konto.
            chatServer.MultiClient.sendSystemMessage("Invalid account.", p.getCharacter().getCharacterID());
        }
    }

}
