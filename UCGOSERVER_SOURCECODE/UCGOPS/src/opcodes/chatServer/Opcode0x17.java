package opcodes.chatServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x17 implements Opcode {

    public void execute(Player p, Packet pakke) {

        //Ignorer mottatte data, er uviktig.
        //Send svar med en gang.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x00290002);

        int channel_id = chatServer.MultiClient.newChatChannel((PlayerChat) p);
        svar.writeIntBE(channel_id); //Channel ID

        svar.writeIntBE(0x66666666); //Timestamp?

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        Packet svar_pakke = new Packet(0x8015, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
