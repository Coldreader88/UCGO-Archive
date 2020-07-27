package admin.commands;

import players.PlayerChat;
import userDB.*;

public class lockaccount implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        /*if ( p.getUCGM() == 0x9 ) return true;
		else return false;*/
        return true; //Alle GMs kan stenge kontoer.
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått ID for spiller hvis konto skal stenges.
        if (args.length < 2) {
            return;
        }

        int id;

        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            return;
        }

        //Finn kontoen spiller tilhører.
        GameCharacter chara = ManageCharacters.getGameCharacter(id);

        if (chara == null) {

            chatServer.MultiClient.sendSystemMessage("Invalid character ID.", p.getCharacter().getCharacterID());
            return;
        }

        //Steng kontoen.
        chara.getKonto().setLocked(true);

        chatServer.MultiClient.sendSystemMessage("Account closed.", p.getCharacter().getCharacterID());

    }

}
