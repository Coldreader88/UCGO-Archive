package admin.commands;

import characters.CharacterGame;
import items.GeneralItem;
import players.PlayerChat;
import validItems.GameItem;
import validItems.ItemList;
import validItems.ItemType;

public class spawns implements AdminCommand {

    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått 2 argumenter og at de er INTs.
        if (args.length < 3) {
            return;
        }

        int itemID, antall;

        try {

            itemID = Integer.parseInt(args[1]);
            antall = Integer.parseInt(args[2]);
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

        gameServer.ItemHandler.spawnItem8035(i, c.getPosisjon().lagKopi(), 0, antall);
    }

}
