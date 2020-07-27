package opcodes.gameServer;

import characters.SkillManager;
import opcodes.Opcode;
import packetHandler.Packet;
import packetHandler.PacketData;
import players.Player;
import players.PlayerGame;

public class Opcode0x0C implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        PlayerGame player = (PlayerGame) p;

        int cid = pd.readIntBE();

        if (cid != player.getCharacter().getCharacterID()) {
            return;
        }

        int antall = (int) pd.readByte() & 0x7F; //Antall character attributes som endres på.

        /*
		 * Vi skal lese to forskjellige typer data fra pakken.
		 *
		 * 1 - Character attribute ID
		 * 2 - Ny status for character attribute.
		 * 
		 * Dette lagres i en array på formatet [n][2]
         */
        byte[][] skillData = new byte[antall][2];

        //Les inn attribute ID.
        for (int c = 0; c < antall; c++) {
            skillData[c][0] = pd.readByte();
        }

        pd.readByte(); //Hopp over teller. Den er riktig uansett.

        //Les inn ny status for attribute.
        //0 = stiger, 1 = Synker, 2 = Stå i ro.
        for (int c = 0; c < antall; c++) {
            skillData[c][1] = pd.readByte();
        }

        //Gå gjennom alle innleste data og oppdater skills.
        SkillManager skills = player.getCharacter().getSkills();

        for (int c = 0; c < antall; c++) {

            skills.setCharacterAttributeRetning(skillData[c][0], skillData[c][1]);
        }

        //Send svar tilbake. Endringer blir alltid godtatt.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x2);

        Packet svar_pakke = new Packet(0x800C, svar.getData());

        p.sendPacket(svar_pakke);

    }

}
