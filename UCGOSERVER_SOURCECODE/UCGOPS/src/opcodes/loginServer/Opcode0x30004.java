package opcodes.loginServer;

import opcodes.*;
import packetHandler.*;
import players.Player;
import userDB.GameCharacter;

public class Opcode0x30004 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int character_id = pd.readIntBE();

        //Sjekk at account ID stemmer før vi fortsetter.
        if (p.getAccountID() == pd.readIntBE()) {
            //Sjekk at oppgitt character tilhører denne kontoen.
            GameCharacter gc = userDB.ManageCharacters.getGameCharacter(character_id);
            if (gc != null && gc.getKonto().getAccountID() != p.getAccountID()) {
                //Character tilhører ikke denne kontoen.
                System.out.println("OPCODE 0x30004: Attempt to delete invalid character.");
                return;
            }

            //Slett character.
            userDB.ManageCharacters.deleteGameCharacter(character_id);

            //Send svar tilbake.
            PacketData svar = new PacketData();

            svar.writeIntBE(0x050002);
            svar.writeByteMultiple((byte) 0x0, 24);

            Packet svar_pakke = new Packet(0x38004, svar.getData());

            p.sendPacket(svar_pakke);
        } else {
            //Feil.
            PacketData svar = new PacketData();

            svar.writeIntBE(-1);

            Packet svar_pakke = new Packet(0x38004, svar.getData());

            p.sendPacket(svar_pakke);
        }

    }

}
