package admin.commands;

import characters.CharacterGame;
import items.Vehicle;
import players.PlayerChat;
import validItems.GameItem;
import validItems.ItemList;
import validItems.ItemSubType;
import validItems.ItemType;

public class zpawnv implements AdminCommand {

    public boolean checkGMrights(PlayerChat p) {
        if (p.getUCGM() == 0x4 || p.getUCGM() == 0x5) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått 2 argumenter og at de er INTs.
        if (args.length < 3) {
            return;
        }

        int vehicle_id, engine_id;

        try {

            vehicle_id = Integer.parseInt(args[1]);
            engine_id = Integer.parseInt(args[2]);
        } catch (Exception e) {
            return;
        }

        //Sjekk at oppgitt vehicle id er riktig.
        GameItem g = ItemList.getItem(vehicle_id);
        if (g == null || g.getItemType() != ItemType.Vehicle) {
            return;
        }

        //Sjekk at oppgitt engine id er riktig.
        g = ItemList.getItem(engine_id);
        if (g == null || g.getItemSubType() != ItemSubType.Engine) {
            return;
        }

        //OK, opprett nytt vehicle.
        Vehicle v = new Vehicle(vehicle_id, engine_id);

        CharacterGame c = gameServer.MultiClient.getCharacter(p.getCharacter().getCharacterID());
        if (c == null) {
            return;
        }

        gameServer.ItemHandler.spawnItem8035(v, c.getPosisjon().lagKopi(), 0);
    }

}
