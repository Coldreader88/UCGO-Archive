package opcodes.gameServer;

import npc.*;
import opcodes.Opcode;
import packetHandler.*;
import players.Player;
import userDB.*;

public class Opcode0x06 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();
        int character_id = pd.readIntBE();

        String navn = null; //Navn til PC/NPC lagres her.
        int faction = 0; //Faction til PC/NPC lagres her

        if ((character_id & 0xFF000000) == 0x06000000) {
            //Global NPC.
            NPC n = GlobalManager.getNpc(character_id);
            if (n != null) {
                navn = n.getNavn();
                faction = n.getFaction();
            }
        } else if ((character_id & 0xFF000000) == 0x05000000) {
            //Local NPC.
            NPC n = LocalManager.getNpc(character_id);
            if (n != null) {
                navn = n.getNavn();
                faction = n.getFaction();
            }
        } else {
            //Ikke NPC.
            GameCharacter chara = ManageCharacters.getGameCharacter(character_id);

            if (chara != null) {
                navn = chara.getNavn();
                faction = chara.getFaction();
            }
        }

        if (navn != null) {

            PacketData svar = new PacketData();

            svar.writeIntBE(-1); //Account ID.
            svar.writeIntBE(character_id);
            svar.writeByte((byte) 0x0);
            svar.writeByte((byte) faction);
            svar.writeByte((byte) (0x80 + navn.length()));
            svar.writeStringUTF16LE(navn);

            Packet svar_pakke = new Packet(0x8006, svar.getData());

            p.sendPacket(svar_pakke);
        } else {
            //Feil. Character finnes ikke.
            PacketData svar = new PacketData();

            svar.writeIntBE(-1);
            svar.writeIntBE(-1);

            Packet svar_pakke = new Packet(0x8006, svar.getData());

            p.sendPacket(svar_pakke);
        }
    }

}
