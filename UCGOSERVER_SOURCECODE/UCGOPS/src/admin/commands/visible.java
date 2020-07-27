package admin.commands;

import characters.CharacterGame;
import players.PlayerChat;

public class visible implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        CharacterGame c = gameServer.MultiClient.getCharacter(p.getCharacter().getCharacterID());

        if (c == null) {
            return;
        }

        c.setVisible(true);

        chatServer.MultiClient.sendSystemMessage("You are now visible.", p.getCharacter().getCharacterID());
    }

}
