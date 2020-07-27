package admin.commands;

import characters.CharacterGame;
import players.PlayerChat;

public class kick implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi har fått ID for spiller som skal kickes.
        if (args.length < 2) {
            return;
        }

        int id;

        try {
            id = Integer.parseInt(args[1]);
        } catch (Exception e) {
            return;
        }

        PlayerChat spiller = chatServer.MultiClient.getPlayer(id);

        //Sjekk at ID er gyldig.
        if (spiller == null) {
            return;
        }

        gameServer.MultiClient.kickPlayer(spiller.getCharacter().getCharacterID());

        //Gjør character usynlig, dermed vises han ikke til andre spillere mens vi venter på timeout.
        CharacterGame c = gameServer.MultiClient.getCharacter(spiller.getCharacter().getCharacterID());

        if (c != null) {
            c.setVisible(false);
        }

        //Gi tilbakemelding til GM
        chatServer.MultiClient.sendSystemMessage("Player has been kicked.", p.getCharacter().getCharacterID());
    }

}
