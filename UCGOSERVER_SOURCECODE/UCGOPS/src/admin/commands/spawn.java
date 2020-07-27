package admin.commands;

import characters.CharacterGame;
import items.*;
import players.PlayerChat;
import validItems.*;

public class spawn implements AdminCommand {

    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått et argument og at det er en INT.
        if (args.length < 2) {
            return;
        }

        int itemID;

        try {
            itemID = Integer.parseInt(args[1]);
        } catch (Exception e) {
            return;
        }

        //Sjekk at oppgitt item ID er riktig.
        GameItem g = ItemList.getItem(itemID);
        if (g == null || g.getItemType() != ItemType.General) {
            return;
        }

        GeneralItem i = new GeneralItem(itemID);

        CharacterGame c = gameServer.MultiClient.getCharacter(p.getCharacter().getCharacterID());
        if (c == null) {
            return;
        }

        gameServer.ItemHandler.spawnItem8035(i, c.getPosisjon().lagKopi(), 0);
    }

}
