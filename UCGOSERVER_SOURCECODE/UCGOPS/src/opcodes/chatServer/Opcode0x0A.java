package opcodes.chatServer;

import opcodes.Opcode;
import packetHandler.*;
import players.Player;

public class Opcode0x0A implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int antall = pd.readByte() & 0x7F;

        PacketData svar = new PacketData();

        svar.writeByte((byte) (antall + 0x80));

        for (; antall > 0; antall--) {

            int character_id = pd.readIntBE();

            svar.writeIntBE(character_id);

            if (chatServer.MultiClient.getPlayer(character_id) == null) {
                svar.writeIntBE(0x0);
            } else {
                svar.writeIntBE(0x1);
            }
        }

        Packet svar_pakke = new Packet(0x8009, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
