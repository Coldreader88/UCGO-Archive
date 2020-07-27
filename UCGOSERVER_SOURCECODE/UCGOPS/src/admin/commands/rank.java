package admin.commands;

import players.PlayerChat;
import userDB.GameCharacter;
import userDB.ManageCharacters;

public class rank implements AdminCommand {

    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått 2 argumenter og at de er INTs.
        if (args.length < 3) {
            return;
        }

        int cid, rank;

        try {

            cid = Integer.parseInt(args[1]);
            rank = Integer.parseInt(args[2]);
        } catch (Exception e) {
            return;
        }

        //Hent ut character hvis rank vi skal sette.
        GameCharacter chara = ManageCharacters.getGameCharacter(cid);

        if (chara == null) {
            return;
        }

        chara.setRank(rank);

        chatServer.MultiClient.sendSystemMessage("OK! Rank set.", p.getCharacter().getCharacterID());
    }

}
