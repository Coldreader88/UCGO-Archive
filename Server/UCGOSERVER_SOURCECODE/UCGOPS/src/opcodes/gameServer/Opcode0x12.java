package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x12 implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        PlayerGame player = (PlayerGame) p;

        int cid = pd.readIntBE();

        //Sjekk at character ID er riktig.
        if (player.getCharacter().getCharacterID() != cid) {
            return;
        }

        //Resten av dataene er ikke viktige foreløpig. Send svar.
        PacketData svar = new PacketData();

        svar.writeShortBE((short) 0x2);
        svar.writeIntBE(cid);
        svar.writeByte((byte) 0x0); //0=Ingen melding, 1=Sympatethic detonation melding på skjerm
        svar.writeIntBE(0); //Hitpoints i skade.
        svar.writeLongBE(pd.readLongBE());

        Packet svar_pakke = new Packet(0x8012, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
