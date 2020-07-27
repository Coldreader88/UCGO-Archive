package admin.commands;

import players.PlayerChat;
import userDB.GameCharacter;
import userDB.ManageCharacters;

public class rename implements AdminCommand {

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

        //Sjekk at vi har fått 2 argumenter.
        if (args.length < 3) {
            return;
        }

        int cid;
        String navn = args[2];

        try {
            cid = Integer.parseInt(args[1]);
        } catch (Exception e) {
            return;
        }

        //Hent ut character hvis navn vi skal endre.
        GameCharacter chara = ManageCharacters.getGameCharacter(cid);

        if (chara == null) {
            return;
        }

        chara.setNavn(navn);

        chatServer.MultiClient.sendSystemMessage("OK! Name changed.", p.getCharacter().getCharacterID());
    }

}
