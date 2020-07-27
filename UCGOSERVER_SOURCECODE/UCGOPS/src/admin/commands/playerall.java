package admin.commands;

import characters.CharacterGame;
import java.util.Iterator;
import players.*;

public class playerall implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        String str = new String();

        Iterator<PlayerGame> spillere = gameServer.MultiClient.getAllPlayers();

        while (spillere.hasNext()) {

            PlayerGame pl = spillere.next();
            CharacterGame player = pl.getCharacter();         
        }

        chatServer.MultiClient.sendSystemMessage("All players on server", str, p.getCharacter().getCharacterID());
    }

}
