package admin.commands;

import characters.CharacterGame;
import packetHandler.Packet;
import packetHandler.PacketData;
import players.PlayerChat;

public class ban implements AdminCommand {

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

        //OK, send force logout pakke til spiller.
        PacketData svar8052 = new PacketData();

        svar8052.writeIntBE(spiller.getCharacter().getCharacterID());
        svar8052.writeByteMultiple((byte) 0x0, 24);

        Packet svar8052_pakke = new Packet(0x8052, svar8052.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar8052_pakke, spiller.getCharacter().getCharacterID());

        //Gjør character usynlig, dermed vises han ikke til andre spillere mens vi venter på timeout.
        CharacterGame c = gameServer.MultiClient.getCharacter(spiller.getCharacter().getCharacterID());

        if (c != null) {
            c.setVisible(false);
        }

        //Blokker IP.
        admin.ipblock.blockIP(spiller.getIP());
        admin.ipblock.saveToFile();

        //Gi tilbakemelding til GM
        chatServer.MultiClient.sendSystemMessage("Player has been banned.", p.getCharacter().getCharacterID());

    }

}
