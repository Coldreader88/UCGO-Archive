package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x0D implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {
        /*
		PlayerGame player = (PlayerGame)p;

		PacketData pd = new PacketData(pakke.getData());
		
		short n = pd.readShortBE();
		
		//Første short skal alltid være 0? Legg inn sjekk her i tilfelle opcode 0xD sendes for attributes også.
		if ( n != 0 ) {
			System.out.println("Opcode 0xD: Første short er ikke 0x0, verdi:"+n);
			helpers.PrintPacket.print(pakke);
			return;
		}
		
		byte skillID = pd.readByte();
		
		/*
		 * skillID er ID for skill som skal økes. Denne skal være 0,1 eller 3.
		 * 
		 * 0=MS, 1=MA, 3=Fighter.
         */
 /*
		if ( skillID != 0 && skillID != 1 && skillID != 3 ) {
			System.out.println("Opcode 0xD: Invalid skill ID:"+skillID);
			helpers.PrintPacket.print(pakke);
			return;
		}
		
		//Alt ser ut til å være OK, øk skill.
		gameServer.SkillTraining.increaseCombatSkill(player.getCharacter(),skillID);*/
    }

}
