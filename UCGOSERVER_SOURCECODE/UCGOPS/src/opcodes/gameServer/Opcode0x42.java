package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.Player;
import players.PlayerGame;

public class Opcode0x42 implements Opcode {

    public void execute(Player p, Packet pakke) {

        //Lagre spillerens status i databasen.
        PlayerGame player = (PlayerGame) p;
        player.save();

        //Fjern character fra nåværende zone.
        gameServer.MultiClient.unregisterCharacter(player.getCharacter());

        //Koble spiller i fra server.
        serverComp.PlayerHandlerStatic.removePlayerGame(player);

        //Send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x00110002);
        svar.writeByteMultiple((byte) 0, 24);

        Packet svar_pakke = new Packet(0x8042, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
