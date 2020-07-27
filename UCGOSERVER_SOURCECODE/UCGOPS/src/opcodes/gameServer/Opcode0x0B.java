package opcodes.gameServer;

import characters.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x0B implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        PlayerGame player = (PlayerGame) p;

        int cid = pd.readIntBE();

        if (cid != player.getCharacter().getCharacterID()) {
            return;
        }

        int antall = (int) pd.readByte() & 0x7F; //Antall skills som endres på.

        /*
		 * Vi skal lese tre forskjellige typer data fra pakken.
		 * 
		 * 1 - Hvilken skill type det er.
		 * 2 - Skill ID
		 * 3 - Ny status for skill.
		 * 
		 * Dette lagres i en array på formatet [n][3]
         */
        byte[][] skillData = new byte[antall][3];

        //Les inn info om hvilken skill type det gjelder.
        //0 = Battle skill, 1 = Crafting skill
        for (int c = 0; c < antall; c++) {
            skillData[c][0] = pd.readByte();
        }

        pd.readByte(); //Hopp over teller. Den er riktig uansett.

        //Les inn skill ID.
        for (int c = 0; c < antall; c++) {
            skillData[c][1] = pd.readByte();
        }

        pd.readByte(); //Hopp over teller. Den er riktig uansett.

        //Les inn ny status for skill.
        //0 = stiger, 1 = Synker, 2 = Stå i ro.
        for (int c = 0; c < antall; c++) {
            skillData[c][2] = pd.readByte();
        }

        //Gå gjennom alle innleste data og oppdater skills.
        SkillManager skills = player.getCharacter().getSkills();

        for (int c = 0; c < antall; c++) {

            //Sjekk om det er battle eller crafting skill.
            if (skillData[c][0] == 0) {
                skills.setCombatSkillRetning(skillData[c][1], skillData[c][2]);
            } else if (skillData[c][0] == 1 || skillData[c][0] == 2) {
                skills.setCraftingSkillRetning(skillData[c][1], skillData[c][2]);
            }
        }

        //Send svar tilbake. Endringer blir alltid godtatt.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x2);

        Packet svar_pakke = new Packet(0x800B, svar.getData());

        p.sendPacket(svar_pakke);

    }

}
