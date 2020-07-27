package admin.commands;

import characters.CharacterGame;
import players.PlayerChat;

/**
 * Skriver ut posisjonen til character.
 *
 * @author UCGOSERVER.COM
 */
public class position implements AdminCommand {

    @Override
    public void execute(PlayerChat p, String[] args) {
        CharacterGame c = gameServer.MultiClient.getCharacter(p.getCharacter().getCharacterID());
        if (c == null) {
            return;
        }

        chatServer.MultiClient.sendSystemMessage("X: " + c.getPosisjon().getX() + " Y: " + c.getPosisjon().getY() + " Z: " + c.getPosisjon().getZ(), p.getCharacter().getCharacterID());
    }

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

}
