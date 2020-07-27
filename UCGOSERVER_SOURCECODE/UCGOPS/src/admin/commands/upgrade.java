package admin.commands;

import characters.CharacterGame;
import players.PlayerChat;

public class upgrade implements AdminCommand {

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått oppgitt stat som skal oppgraderes.
        if (args.length < 2) {
            return;
        }

        CharacterGame chara = gameServer.MultiClient.getCharacter(p.getCharacter().getCharacterID());

        if (chara.getVehicle() == null) {
            chatServer.MultiClient.sendSystemMessage("NOT IN VEHICLE", p.getCharacter().getCharacterID());
            return;
        }

        //Sjekk hvilken stat vi skal oppgradere.
        if (args[1].compareTo("pow") == 0) {
            chara.getVehicle().setUpgradePower(8);
        } else if (args[1].compareTo("hit") == 0) {
            chara.getVehicle().setUpgradeHit(8);
        } else if (args[1].compareTo("def") == 0) {
            chara.getVehicle().setUpgradeDefence(8);
        }
    }

    @Override
    public boolean checkGMrights(PlayerChat p) {
        // TODO Auto-generated method stub
        return true;
    }

}
