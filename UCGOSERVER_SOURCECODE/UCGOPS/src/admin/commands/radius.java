package admin.commands;

import characters.*;
import players.PlayerChat;

//TEST 
public class radius implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått radius.
        if (args.length < 2) {
            return;
        }

        int radius;

        try {
            radius = Integer.parseInt(args[1]);
        } catch (Exception e) {
            return;
        }

        CharacterGame chara = gameServer.MultiClient.getCharacter(p.getCharacter().getCharacterID());

        chara.radius = radius;
    }

}
