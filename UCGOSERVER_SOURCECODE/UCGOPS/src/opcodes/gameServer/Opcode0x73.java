package opcodes.gameServer;

import occupationCity.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x73 implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PlayerGame player = (PlayerGame) p;

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();
        int characterID = pd.readIntBE();
        int cityID = pd.readIntBE();
        byte icf = pd.readByte();

        //Hent ut byen og sjekk at den er gyldig.
        City c = OccupationCity.getCity(cityID);
        if (c == null) {
            return;
        }

        OccupationCity.captureICF(cityID, icf, player.getCharacter().getFaction());

        PacketData svar = new PacketData();

        svar.writeIntBE(0x2);
        svar.writeIntBE(characterID);
        svar.writeIntBE(cityID);
        svar.writeByte(icf);
        svar.writeShortBE((short) c.getFaction());
        svar.writeIntBE(0x0);
        svar.writeIntBE(c.getOppdateringsTeller());

        Packet svarPakke = new Packet(0x8076, svar.getData());

        p.sendPacket(svarPakke);

        //Dersom cityID er Newman/Richmond oppdater medaljer.
        if (cityID == 0x3A || cityID == 0x3B) {
            occupationCity.OccupationCity.updateMedalScore(player, cityID);
        }
    }

}
