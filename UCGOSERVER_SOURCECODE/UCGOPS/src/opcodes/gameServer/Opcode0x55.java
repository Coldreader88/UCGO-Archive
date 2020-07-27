package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x55 implements Opcode {

    public void execute(Player p, Packet pakke) {

        //Logout. Den egentlige logout prosedyren håndteres av serverComp.GameServerLogoff
        PacketData pd = new PacketData(pakke.getData());
        int character_id = pd.readIntBE();

        PacketData svar = new PacketData();
        svar.writeIntBE(character_id);
        svar.writeByteMultiple((byte) 0, 24);

        Packet svar_pakke = new Packet(0x8055, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
