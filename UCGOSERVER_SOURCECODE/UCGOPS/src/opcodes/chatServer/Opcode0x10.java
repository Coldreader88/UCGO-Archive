package opcodes.chatServer;

import chatServer.Team;
import chatServer.TeamMember;
import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.Player;

public class Opcode0x10 implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int character_id = pd.readIntBE();

        int team_id = pd.readIntBE();

        //Sjekk at oppgitt team id er gyldig.
        Team lag = chatServer.TeamManagement.getTeam(team_id);
        if (lag == null) {
            return;
        }

        //Send opcode 0x800F til alle medlemmene.
        PacketData svar_800F = new PacketData();

        svar_800F.writeIntBE(0x00200002);
        svar_800F.writeIntBE(lag.getTeamID());
        svar_800F.writeIntBE(0x0);
        svar_800F.writeIntBE(character_id);
        svar_800F.writeIntBE(0x0);
        svar_800F.writeIntBE(0x0);
        svar_800F.writeIntBE(0x0);

        TeamMember tm = lag.getMember(character_id);
        if (tm == null) {
            return;
        }

        svar_800F.writeByte((byte) (tm.getNavn().length() + 0x80));
        svar_800F.writeStringUTF16LE(tm.getNavn());

        Packet svar_800F_pakke = new Packet(0x800F, svar_800F.getData());

        Iterator<TeamMember> medlemmer = lag.getTeamMembers();

        while (medlemmer.hasNext()) {

            TeamMember m = medlemmer.next();
            chatServer.MultiClient.sendPacketToPlayer(svar_800F_pakke, m.getCharacterID());
        }

        //Fjern medlemmet fra laget.
        lag.removeMember(character_id);
    }

}
