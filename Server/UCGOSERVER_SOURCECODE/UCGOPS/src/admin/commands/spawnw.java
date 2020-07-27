package admin.commands;

import characters.CharacterGame;
import items.Weapon;
import players.PlayerChat;
import validItems.*;

public class spawnw implements AdminCommand {

    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått et argument og at det er en INT.
        if (args.length < 2) {
            return;
        }

        int weapon_id;

        try {
            weapon_id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            return;
        }

        //Sjekk at oppgitt weapon id er riktig.
        GameItem g = ItemList.getItem(weapon_id);
        if (g == null || g.getItemType() != ItemType.Weapon) {
            return;
        }

        //OK, opprett nytt våpen.
        Weapon w = new Weapon(weapon_id);

        CharacterGame c = gameServer.MultiClient.getCharacter(p.getCharacter().getCharacterID());
        if (c == null) {
            return;
        }

        gameServer.ItemHandler.spawnItem8035(w, c.getPosisjon().lagKopi(), c.getCharacterID());

    }

}
