package admin.commands;

import players.PlayerChat;
import userDB.GameCharacter;
import userDB.ManageCharacters;

/**
 * Denne admin kommandoen setter faction for en character.
 *
 * @author UCGOSERVER
 */
public class faction implements AdminCommand {

    @Override
    public void execute(PlayerChat p, String[] args) {
        //Sjekk at vi har fått 2 argumenter.
        if (args.length < 3) {
            return;
        }

        int cid, faction;
        String navn = args[2];

        try {
            cid = Integer.parseInt(args[1]);
            faction = Integer.parseInt(args[2]);
        } catch (Exception e) {
            return;
        }

        //Hent ut character hvis faction vi skal endre.
        GameCharacter chara = ManageCharacters.getGameCharacter(cid);

        if (chara == null) {
            return;
        }

        chara.setFaction(faction);

        chatServer.MultiClient.sendSystemMessage("OK! Faction changed.", p.getCharacter().getCharacterID());
    }

    @Override
    public boolean checkGMrights(PlayerChat p) {
        if (p.getUCGM() == 0x9) {
            return true;
        } else {
            return false;
        }
    }

}
