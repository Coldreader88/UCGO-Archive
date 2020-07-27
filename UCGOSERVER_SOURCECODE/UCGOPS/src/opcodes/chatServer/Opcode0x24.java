package opcodes.chatServer;

import opcodes.Opcode;
import packetHandler.*;
import players.Player;

public class Opcode0x24 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int character_id = pd.readIntBE();

        //Send svar tilbake. Ingen vits i å sjekke at character id er rett.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x2);
        svar.writeIntBE(character_id);
        svar.writeIntBE(0x1);

        Packet svar_pakke = new Packet(0x8021, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
