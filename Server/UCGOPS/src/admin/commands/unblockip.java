package admin.commands;

import players.PlayerChat;

public class unblockip implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått en IP
        if (args.length < 2) {
            return;
        }

        admin.ipblock.unblockIP(args[1]);
        admin.ipblock.saveToFile();

        String str = args[1] + " is not blocked.";
        chatServer.MultiClient.sendSystemMessage(str, p.getCharacter().getCharacterID());

    }

}
