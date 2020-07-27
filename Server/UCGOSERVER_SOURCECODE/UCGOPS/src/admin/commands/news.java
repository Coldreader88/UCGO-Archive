package admin.commands;

import players.PlayerChat;

/**
 * Sørger for at en nyhetsoppdatering vises i spillers journal vindu kort tid
 * etter innlogging.
 *
 * EKSEMPEL: #news melding her
 *
 * For å slå av nåværende melding utfør #news uten melding.
 *
 * @author UCGOSERVER.COM
 */
public class news implements AdminCommand {

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk om vi skal vise eller slå av melding.
        if (args.length > 1) {
            String msg = new String();
            for (int i = 1; i < args.length; i++) {
                msg += args[i] + " ";
            }

            config.Server.serverNews = msg;
        } else {
            config.Server.serverNews = null;
        }

    }

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

}
