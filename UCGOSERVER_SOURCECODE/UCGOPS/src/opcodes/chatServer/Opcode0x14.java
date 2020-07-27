package opcodes.chatServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x14 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int character_id = pd.readIntBE();

        //Send svar med en gang, ingen vits i å sjekke character id.
        PacketData svar = new PacketData();
        svar.writeIntBE(0x0);
        svar.writeIntBE(character_id);
        svar.writeIntBE(0x1);

        Packet svar_pakke = new Packet(0x8013, svar.getData());

        p.sendPacket(svar_pakke);

        //Send MOTD til spilleren.
        PlayerChat player = (PlayerChat) p;
        chatServer.MultiClient.sendSystemMessage(config.Server.motd, player.getCharacter().getCharacterID());
    }

}
