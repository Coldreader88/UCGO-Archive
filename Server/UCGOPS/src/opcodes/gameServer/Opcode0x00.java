package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.Player;

public class Opcode0x00 implements Opcode {

    public void execute(Player p, Packet pakke) {

        //Send svar tilbake. Mottatte data kan trygt ignoreres.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x00120002);
        svar.writeByteMultiple((byte) 0, 24);

        Packet svar_pakke = new Packet(0x8000, svar.getData());

        p.sendPacket(svar_pakke);

    }

}
