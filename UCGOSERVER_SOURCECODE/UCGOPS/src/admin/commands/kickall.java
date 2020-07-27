package admin.commands;

import players.PlayerChat;

public class kickall implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        gameServer.MultiClient.kickAllPlayers();

        //NB! Spillere vil ikke bli "fjernet" med en gang. Dvs de vil være synlige frem til de blir logget ut
        //av serveren selv.
        //#kickall bør kun brukes når server er stengt og alle spillere må ut pga oppdatering.
    }

}
