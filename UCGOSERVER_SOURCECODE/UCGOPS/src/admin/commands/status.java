package admin.commands;

import java.util.Date;
import players.PlayerChat;

public class status implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        String str = "Players on server: " + chatServer.MultiClient.numberOfPlayers() + "\n";

        if (config.Server.server_is_closed) {
            str += "Server is closed\n";
        } else {
            str += "Server is open\n";
        }

        str += "Server uptime: ";

        Date d = new Date();

        int uptime = (int) ((d.getTime() - config.Server.start_time.getTime()) / 1000);

        str += uptime / 3600 + "H " + (uptime - ((uptime / 3600) * 3600)) / 60 + "M\n";

        chatServer.MultiClient.sendSystemMessage("Server status", str, p.getCharacter().getCharacterID());
    }

}
