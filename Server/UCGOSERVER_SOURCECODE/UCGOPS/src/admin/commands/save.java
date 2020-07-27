package admin.commands;

import players.PlayerChat;

public class save implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        if (p.getUCGM() == 0x9) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        chatServer.MultiClient.sendSystemMessage("Saving server data...", p.getCharacter().getCharacterID());

        serverComp.Save.saveAllServerData();

        chatServer.MultiClient.sendSystemMessage("Saving completed!", p.getCharacter().getCharacterID());
    }

}
