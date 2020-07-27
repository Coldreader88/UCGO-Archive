package admin.commands;

import players.PlayerChat;
import userDB.GameCharacter;
import userDB.ManageCharacters;

/**
 * Låser opp en stengt konto basert på character ID.
 *
 * @author UCGOSERVER.COM
 *
 */
public class unlockCharacter implements AdminCommand {

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

        //Finn kontoen spiller tilhører.
        GameCharacter chara = ManageCharacters.getGameCharacter(id);

        if (chara == null) {

            chatServer.MultiClient.sendSystemMessage("Invalid character ID.", p.getCharacter().getCharacterID());
            return;
        }

        //Steng kontoen.
        chara.getKonto().setLocked(false);

        chatServer.MultiClient.sendSystemMessage("Account open.", p.getCharacter().getCharacterID());

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
