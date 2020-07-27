package opcodes.infoServer;

import opcodes.Opcode;
import packetHandler.*;
import players.Player;

public class Opcode0x00 implements Opcode {

    public void execute(Player p, Packet pakke) {

        //Send svar med en gang, ingen vits i å sjekke mottatte data.
        PacketData svar = new PacketData();

        svar.writeLongBE(0x0); //0x0=Online, 0x1=Offline

        Packet svar_pakke = new Packet(0x8000, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
