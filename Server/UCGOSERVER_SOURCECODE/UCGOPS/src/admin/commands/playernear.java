package admin.commands;

import characters.*;
import java.util.Iterator;
import players.*;

public class playernear implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        String str = new String();

        Iterator<CharacterGame> spillere = gameServer.MultiClient.getCharacterList(gameServer.MultiClient.getCharacter(p.getCharacter().getCharacterID())).iterator();

        while (spillere.hasNext()) {

            CharacterGame player = spillere.next();          
        }

        chatServer.MultiClient.sendSystemMessage("Nearby players", str, p.getCharacter().getCharacterID());

    }

}
