package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x70 implements Opcode {

    public void execute(Player p, Packet pakke) {

        p.sendPacket(occupationCity.OccupationCity.getOpcode70());

    }

}
