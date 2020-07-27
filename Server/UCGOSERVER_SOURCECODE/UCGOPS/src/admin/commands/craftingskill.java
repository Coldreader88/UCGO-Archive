package admin.commands;

import players.PlayerChat;
import userDB.GameCharacter;
import userDB.ManageCharacters;

public class craftingskill implements AdminCommand {

    public boolean checkGMrights(PlayerChat p) {
        if (p.getUCGM() != 0x9) {
            return false;
        } else {
            return true;
        }
    }

    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått 3 argumenter og at de er INTs.
        if (args.length < 4) {
            return;
        }

        int cid, skillID, skillLevel;

        try {

            cid = Integer.parseInt(args[1]);
            skillID = Integer.parseInt(args[2]);
            skillLevel = Integer.parseInt(args[3]);
        } catch (Exception e) {
            return;
        }

        //Hent ut character hvis skill vi skal sette.
        GameCharacter chara = ManageCharacters.getGameCharacter(cid);

        if (chara == null) {
            return;
        }

        chara.getSkills().setCraftingSkill(skillID, skillLevel);

        chatServer.MultiClient.sendSystemMessage("OK! Skill set.", p.getCharacter().getCharacterID());
    }

}
