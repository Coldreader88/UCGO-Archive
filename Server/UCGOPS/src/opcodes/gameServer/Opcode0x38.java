package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x38 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        //Sjekk at Character ID er rett.
        PlayerGame player = (PlayerGame) p;

        if (pd.readIntBE() == player.getCharacter().getCharacterID()) {
            //OK! Send svar.
            PacketData svar = new PacketData();

            svar.writeIntBE(0x000A0002);
            svar.writeIntBE(0x0);
            svar.writeIntBE(0x0);
            svar.writeIntBE(player.getCharacter().getCharacterID());
            svar.writeIntBE(0x0);
            svar.writeIntBE(0x0);
            svar.writeIntBE(0x0);

            Packet svar_pakke = new Packet(0x8038, svar.getData());

            p.sendPacket(svar_pakke);
        } else {
            //Feil!
            PacketData svar = new PacketData();

            svar.writeIntBE(-1);

            Packet svar_pakke = new Packet(0x8038, svar.getData());

            p.sendPacket(svar_pakke);
        }

    }

}
