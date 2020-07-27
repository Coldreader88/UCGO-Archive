package opcodes.chatServer;

import chatServer.Team;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x0D implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int antall = pd.readByte() & 0x7F;

        String navn = pd.readStringUTF16LE(antall);

        if (navn == null) {
            return;
        }

        int character_id = pd.readIntBE();

        //Sjekk at character ID er gyldig.
        PlayerChat player = (PlayerChat) p;
        if (player.getCharacter().getCharacterID() != character_id) {
            return;
        }

        Team nytt_team = chatServer.TeamManagement.createTeam(navn, player.getCharacter());

        if (nytt_team != null) {
            //Nytt lag opprettet.
            PacketData svar = new PacketData();

            svar.writeIntBE(0x001C0002);
            svar.writeIntBE(nytt_team.getTeamID());
            svar.writeIntBE(0x0); //Timestamp. UNØDVENDIG.

            svar.writeIntBE(0x0);
            svar.writeIntBE(0x0);
            svar.writeIntBE(0x0);
            svar.writeIntBE(0x0);

            svar.writeByte((byte) (antall + 0x80));
            svar.writeStringUTF16LE(navn);

            Packet svar_pakke = new Packet(0x800C, svar.getData());

            p.sendPacket(svar_pakke);
        } else {
            //Feil.
            PacketData svar = new PacketData();

            svar.writeIntBE(0x001C0000);

            Packet svar_pakke = new Packet(0x800C, svar.getData());

            p.sendPacket(svar_pakke);
        }

    }

}
