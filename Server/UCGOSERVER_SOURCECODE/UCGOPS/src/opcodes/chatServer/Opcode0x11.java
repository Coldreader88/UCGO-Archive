package opcodes.chatServer;

import chatServer.Team;
import chatServer.TeamMember;
import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x11 implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int character_id = pd.readIntBE();

        pd.readIntBE();

        int team_id = pd.readIntBE();

        //Sjekk at oppgitt team id er gyldig.
        Team lag = chatServer.TeamManagement.getTeam(team_id);
        if (lag == null) {
            return;
        }

        //Sjekk at oppgitt character id er gyldig og riktig eier for laget.
        PlayerChat player = (PlayerChat) p;

        if (player.getCharacter().getCharacterID() != character_id || character_id != lag.getOwner()) {
            return;
        }

        //OK, spark alle medlemmene ut av laget.
        //Eneste måte å gjøre det på, korrekt fremgangsmåte er ukjent.
        Iterator<TeamMember> medlemmer = lag.getTeamMembers();
        PacketData svar_800F;

        while (medlemmer.hasNext()) {

            TeamMember m = medlemmer.next();

            svar_800F = new PacketData();

            svar_800F.writeIntBE(0x00200002);
            svar_800F.writeIntBE(lag.getTeamID());
            svar_800F.writeIntBE(0x0);
            svar_800F.writeIntBE(m.getCharacterID());
            svar_800F.writeIntBE(0x0);
            svar_800F.writeIntBE(0x0);
            svar_800F.writeIntBE(0x0);

            svar_800F.writeByte((byte) (m.getNavn().length() + 0x80));
            svar_800F.writeStringUTF16LE(m.getNavn());

            Packet svar_800F_pakke = new Packet(0x800F, svar_800F.getData());

            chatServer.MultiClient.sendPacketToPlayer(svar_800F_pakke, m.getCharacterID());

            //Fjern medlemmet fra laget.
            lag.removeMember(m.getCharacterID());
        }

        //Fjern hele laget.
        chatServer.TeamManagement.removeTeam(team_id);
    }

}
