package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.Packet;
import packetHandler.PacketData;
import players.Player;
import players.PlayerGame;

public class Opcode0x37 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PlayerGame player = (PlayerGame) p;

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();
        int size = pd.readByte();
        size &= 0x7F;

        String msg = pd.readStringASCII(size);

        admin.logging.globalserverMsg("Opcode 0x37: " + msg);

        //Ettersom denne pakken alltid sendes når spiller fusker så kick spiller fra server.
        gameServer.MultiClient.kickPlayer(player.getCharacter().getCharacterID());
    }

}
