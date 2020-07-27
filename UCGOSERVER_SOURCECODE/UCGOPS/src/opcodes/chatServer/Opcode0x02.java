package opcodes.chatServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x02 implements Opcode {

    public void execute(Player p, Packet pakke) {

        //Logout. Lagring av data osv håndteres av serverComp.GameServerLogoff.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x0);
        svar.writeIntBE(-1);
        svar.writeIntBE(-1);

        Packet svar_pakke = new Packet(0x8002, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
