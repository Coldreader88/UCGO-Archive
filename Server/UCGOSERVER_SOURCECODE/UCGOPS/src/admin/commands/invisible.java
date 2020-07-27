package admin.commands;

import characters.CharacterGame;
import players.PlayerChat;

public class invisible implements AdminCommand {

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

        c.setVisible(false);

        chatServer.MultiClient.sendSystemMessage("You are now invisible.", p.getCharacter().getCharacterID());
    }

}
