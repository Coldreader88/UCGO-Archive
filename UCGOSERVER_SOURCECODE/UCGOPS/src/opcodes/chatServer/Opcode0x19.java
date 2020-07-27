package opcodes.chatServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x19 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int character_id = pd.readIntBE();
        int channel_id = pd.readIntBE();

        PlayerChat spiller = chatServer.MultiClient.getChatParticipant(channel_id, character_id);

        //Sjekk at spiller faktisk er med i channel.
        if (spiller == null) {
            return;
        }

        PacketData svar = new PacketData();

        svar.writeIntBE(0x002A0002);

        svar.writeIntBE(channel_id);

        svar.writeIntBE(0x0);

        svar.writeIntBE(character_id);

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        svar.writeByte((byte) (spiller.getCharacter().getNavn().length() + 0x80));
        svar.writeStringUTF16LE(spiller.getCharacter().getNavn());

        Packet svar_pakke = new Packet(0x8017, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
