package admin.commands;

import characters.CharacterGame;
import items.Clothing;
import players.PlayerChat;
import validItems.GameItem;
import validItems.ItemList;
import validItems.ItemType;

public class spawnc implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått to argumenter og at de er INT.
        if (args.length < 3) {
            return;
        }

        int itemID;
        int color;

        try {
            itemID = Integer.parseInt(args[1]);
            color = Integer.parseInt(args[2]);
        } catch (Exception e) {
            return;
        }

        //Sjekk at oppgitt item ID er riktig.
        GameItem g = ItemList.getItem(itemID);
        if (g == null || g.getItemType() != ItemType.Clothes) {
            return;
        }

        Clothing i = new Clothing(itemID, color);

        CharacterGame c = gameServer.MultiClient.getCharacter(p.getCharacter().getCharacterID());
        if (c == null) {
            return;
        }

        gameServer.ItemHandler.spawnItem8035(i, c.getPosisjon().lagKopi(), 0);

    }

}
