package admin.commands;

import players.PlayerChat;
import userDB.*;

public class unlockaccount implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        if (p.getUCGM() == 0x9) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått ID for spiller hvis konto skal åpnes.
        if (args.length < 2) {
            return;
        }

        int id;

        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            return;
        }

        //Finn kontoen.
        Account k = ManageAccounts.getAccount(id);

        if (k == null) {
            //Konto finnes ikke.
            chatServer.MultiClient.sendSystemMessage("Invalid account ID", p.getCharacter().getCharacterID());
        } else {

            k.setLocked(false);

            chatServer.MultiClient.sendSystemMessage("Account unlocked", p.getCharacter().getCharacterID());
        }

    }

}
