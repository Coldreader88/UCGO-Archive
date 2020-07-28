package admin.commands;

import java.util.Iterator;
import players.PlayerChat;
import userDB.*;

public class lockedaccountlist implements AdminCommand {

    public boolean checkGMrights(PlayerChat p) {
        if (p.getUCGM() == 0x9) {
            return true;
        } else {
            return false;
        }
    }

    public void execute(PlayerChat p, String[] args) {

        String s = "";

        //Gå gjennom alle kontoer og finn de som er stengt.
        Iterator<Account> kontoer = ManageAccounts.getAllAccounts();

        while (kontoer.hasNext()) {

            Account k = kontoer.next();

            if (k.isLocked()) {
                //Konto er stengt. Legg den til listen.
                s += "Account ID: " + k.getAccountID() + " Username: " + k.getUsername() + "\n";
            }
        }

        chatServer.MultiClient.sendSystemMessage("Closed accounts", s, p.getCharacter().getCharacterID());
    }

}
