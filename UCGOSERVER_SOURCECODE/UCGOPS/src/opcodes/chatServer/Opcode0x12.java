package opcodes.chatServer;

import chatServer.Team;
import chatServer.TeamMember;
import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x12 implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int eier = pd.readIntBE();

        int medlem = pd.readIntBE();

        int team_id = pd.readIntBE();

        PlayerChat player = (PlayerChat) p;

        //Sjekk at oppgitt team id er gyldig.
        Team lag = chatServer.TeamManagement.getTeam(team_id);
        if (lag == null) {
            return;
        }

        //Sjekk at eier ID er gyldig og at eier faktisk er leder for laget.
        if (eier != player.getCharacter().getCharacterID() || eier != lag.getOwner()) {
            return;
        }

        //OK, send svar tilbake til eier.
        PacketData svar_8011 = new PacketData();

        svar_8011.writeIntBE(0x001F0002);
        svar_8011.writeIntBE(lag.getTeamID());
        svar_8011.writeIntBE(0x0);
        svar_8011.writeIntBE(medlem);
        svar_8011.writeIntBE(0x0);
        svar_8011.writeIntBE(0x0);
        svar_8011.writeIntBE(0x0);

        TeamMember tm = lag.getMember(medlem);
        if (tm == null) {
            return;
        }

        svar_8011.writeByte((byte) (tm.getNavn().length() + 0x80));
        svar_8011.writeStringUTF16LE(tm.getNavn());

        Packet svar_8011_pakke = new Packet(0x8011, svar_8011.getData());

        p.sendPacket(svar_8011_pakke);

        //Send opcode 0x800F til resten.
        PacketData svar_800F = new PacketData();

        svar_800F.writeIntBE(0x00200002);
        svar_800F.writeIntBE(lag.getTeamID());
        svar_800F.writeIntBE(0x0);
        svar_800F.writeIntBE(medlem);
        svar_800F.writeIntBE(0x0);
        svar_800F.writeIntBE(0x0);
        svar_800F.writeIntBE(0x0);

        tm = lag.getMember(medlem);
        if (tm == null) {
            return;
        }

        svar_800F.writeByte((byte) (tm.getNavn().length() + 0x80));
        svar_800F.writeStringUTF16LE(tm.getNavn());

        Packet svar_800F_pakke = new Packet(0x800F, svar_800F.getData());

        Iterator<TeamMember> medlemmer = lag.getTeamMembers();

        while (medlemmer.hasNext()) {

            TeamMember m = medlemmer.next();
            //Skal ikke sendes til eier av laget.
            if (m.getCharacterID() != player.getCharacter().getCharacterID()) {
                chatServer.MultiClient.sendPacketToPlayer(svar_800F_pakke, m.getCharacterID());
            }
        }

        //Fjern medlemmet fra laget.
        lag.removeMember(medlem);
    }

}
