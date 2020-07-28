package opcodes.chatServer;

import java.util.Date;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x04 implements Opcode {

    public void execute(Player p, Packet pakke) {

        //Oppdater keep alive status.
        PlayerChat player = (PlayerChat) p;
        player.getCharacter().setLastTimeOpcode0x4Received((int) (new Date().getTime() / 1000));

        PacketData svar = new PacketData();

        svar.writeIntBE(0x0);
        svar.writeLongBE(0xFFFFFFFFFFFFFFFFL);

        Packet svar_pakke = new Packet(0x8005, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
