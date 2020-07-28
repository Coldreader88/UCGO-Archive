package admin.commands;

import players.PlayerChat;
import userDB.GameCharacter;
import userDB.ManageCharacters;

public class score implements AdminCommand {

    public boolean checkGMrights(PlayerChat p) {
        if (p.getUCGM() != 0x9) {
            return false;
        } else {
            return true;
        }
    }

    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått 2 argumenter og at de er INTs.
        if (args.length < 3) {
            return;
        }

        int cid, score;;

        try {

            cid = Integer.parseInt(args[1]);
            score = Integer.parseInt(args[2]);
        } catch (Exception e) {
            return;
        }

        //Hent ut character hvis skill vi skal sette.
        GameCharacter chara = ManageCharacters.getGameCharacter(cid);

        if (chara == null) {
            return;
        }

        chara.setScore(score);

        chatServer.MultiClient.sendSystemMessage("OK! Score set.", p.getCharacter().getCharacterID());
    }

}
