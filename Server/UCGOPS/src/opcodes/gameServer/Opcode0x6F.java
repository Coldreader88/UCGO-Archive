package opcodes.gameServer;

import characters.CharacterGame;
import items.*;
import opcodes.Opcode;
import packetHandler.*;
import players.Player;

public class Opcode0x6F implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int character_id = pd.readIntBE();

        //Hent ut character vi skal returnere info om.
        CharacterGame chara = gameServer.MultiClient.getCharacter(character_id);

        if (chara == null) {
            return;
        }

        //OK, send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeIntBE(chara.getCharacterID());

        svar.writeShortBE((short) chara.getFaction());

        svar.writeByte((byte) chara.getRank());

        svar.writeByte((byte) (chara.getNavn().length() + 0x80));
        svar.writeStringUTF16LE(chara.getNavn());

        svar.writeIntBE(chara.getScore()); //Score
        svar.writeIntBE(0x0); //Score
        svar.writeIntBE(0x0); //Score

        svar.writeIntBE(0x0); //Score
        svar.writeIntBE(0x0); //??
        svar.writeIntBE(0x0); //??

        //SKriv machine info.
        if (chara.humanForm()) {
            svar.writeIntBE(0x0);
            svar.writeIntBE(0x0);
        } else {
            Item i = chara.getVehicle();
            if (i != null) {
                //Er i MS/vehicle, skriv machine info.
                svar.writeIntBE(chara.getVehicle().getID());
                svar.writeIntBE(chara.getVehicle().getEngine());
            } else {
                i = chara.getTaxiTransport();
                if (i != null) {
                    //Er i taxi/transport, skriv machine info.
                    svar.writeIntBE(chara.getTaxiTransport().getID());
                    svar.writeIntBE(chara.getTaxiTransport().getEngine());
                }
            }
        }

        svar.writeShortBE((short) -1); //??
        svar.writeByte((byte) -1); //??

        svar.writeByte((byte) 0x82); //Richmond/Newman
        svar.writeIntBE(chara.getRichmondMedal());
        svar.writeIntBE(chara.getNewmanMedal());

        svar.writeIntBE(chara.getCharacterID());

        svar.writeShortBE((short) 0x9); //??

        svar.writeByte((byte) chara.getGender());

        svar.writeByte((byte) 0x94);

        //Klesdata
        svar.writeByteArray(chara.getClothing().getData());

        svar.writeIntBE(0x0100);

        svar.writeByte((byte) chara.getAppearance().getFaceType());
        svar.writeShortBE((short) 0x0);
        svar.writeByte((byte) chara.getAppearance().getHairStyle());
        svar.writeIntBE(chara.getAppearance().getHairColor() << 24);

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        Packet svar_pakke = new Packet(0x806F, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
