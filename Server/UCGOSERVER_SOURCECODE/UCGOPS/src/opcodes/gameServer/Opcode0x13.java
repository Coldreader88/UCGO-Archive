package opcodes.gameServer;

import java.util.Date;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x13 implements Opcode {

    public void execute(Player p, Packet pakke) {

        //Send svar med en gang. Ingen vits i å sjekke mottatte data.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x00270002);
        svar.writeIntBE((int) (new Date().getTime() / 1000));
        svar.writeByteMultiple((byte) 0, 20);

        Packet svar_pakke = new Packet(0x8013, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
