package admin.commands;

import players.*;

public class sysmsg implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        String msg = new String();

        for (int i = 1; i < args.length; i++) {
            msg += args[i] + " ";
        }

        chatServer.MultiClient.sendGlobalSystemMessage(msg);
    }

}
