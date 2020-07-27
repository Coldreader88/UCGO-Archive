package opcodes.chatServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x13 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int character_id = pd.readIntBE();

        pd.readIntBE();
        pd.readIntBE();

        int teller = pd.readByte();

        String navn = pd.readStringUTF16LE(teller & 0x7F);

        //Send svar tilbake med en gang, ingen vits å sjekke mottatte data.
        PlayerChat player = (PlayerChat) p;

        PacketData svar = new PacketData();

        svar.writeIntBE(character_id);
        svar.writeIntBE(chatServer.TeamManagement.getTeamMembership(player.getCharacter().getCharacterID()));
        svar.writeIntBE(0x2);
        svar.writeByte((byte) (navn.length() + 0x80));
        svar.writeStringUTF16LE(player.getCharacter().getNavn());
        svar.writeByte((byte) player.getCharacter().getGender());
        svar.writeByte((byte) player.getCharacter().getRank());

        Packet svar_pakke = new Packet(0x8012, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
