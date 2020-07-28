package admin.commands;

import players.PlayerChat;

public class criminal implements AdminCommand {

    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    public void execute(PlayerChat p, String[] args) {

        if (config.Server.criminal_system) {
            config.Server.criminal_system = false;
        } else {
            config.Server.criminal_system = true;
        }

        if (config.Server.criminal_system) {
            chatServer.MultiClient.sendSystemMessage("Criminal system ON", p.getCharacter().getCharacterID());
        } else {
            chatServer.MultiClient.sendSystemMessage("Criminal system OFF", p.getCharacter().getCharacterID());
        }

    }

}
