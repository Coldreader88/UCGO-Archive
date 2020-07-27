package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x40 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE(); //Security token?

        int character_id = pd.readIntBE();

        int zone = pd.readShortBE();

        //Oppdater spillerens zone.
        PlayerGame player = (PlayerGame) p;
        player.getCharacter().getPosisjon().setZone(zone);

        //Send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeShortBE((short) 0x2);

        svar.writeIntBE(character_id);

        svar.writeShortBE((short) zone);

        svar.writeShortBE((short) 0x17BF);

        svar.writeByte((byte) (config.Server.gameserver_ip.length() + 0x80));

        svar.writeStringASCII(config.Server.gameserver_ip);

        svar.writeShortBE((short) 0x5DCA);

        Packet svar_pakke = new Packet(0x8040, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
