package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.Packet;
import packetHandler.PacketData;
import players.*;

public class Opcode0x08 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PlayerGame player = (PlayerGame) p;

        PacketData svar = new PacketData();

        svar.writeIntBE(0x2);
        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeByte((byte) 0x0);

        Packet svarPakke = new Packet(0x8008, svar.getData());

        p.sendPacket(svarPakke);

    }

}
