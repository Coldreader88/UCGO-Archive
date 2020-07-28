package opcodes.chatServer;

import opcodes.Opcode;
import packetHandler.*;
import players.Player;

public class Opcode0x21 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int character_id = pd.readIntBE();

        //Send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeShortBE((short) 0x30);
        svar.writeShortBE((short) 0x2); //???  online/offline?

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        svar.writeIntBE(character_id);

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        svar.writeByte((byte) 0x80);

        Packet svar_pakke = new Packet(0x801E, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
