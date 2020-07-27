package opcodes.chatServer;

import chatServer.Team;
import chatServer.TeamMember;
import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x0F implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int sender = pd.readIntBE();

        int nytt_medlem = pd.readIntBE();

        int team_id = pd.readIntBE();

        //Sjekk at sender er eier av laget og at laget er gyldig.
        Team lag = chatServer.TeamManagement.getTeam(team_id);
        if (lag == null) {
            return;
        }

        if (sender != lag.getOwner()) {
            return;
        }

        //Sjekk at det nye medlemmet er gyldig og er online.
        PlayerChat medlem = chatServer.MultiClient.getPlayer(nytt_medlem);
        if (medlem == null) {
            return;
        }

        //OK, legg det nye medlemmet til i laget.
        lag.addMember(medlem.getCharacter());

        //Send 0x800E til alle medlemmene.
        PacketData svar_800E = new PacketData();

        svar_800E.writeIntBE(0x001D0002);

        svar_800E.writeIntBE(lag.getTeamID());

        svar_800E.writeIntBE(0x0);

        svar_800E.writeIntBE(medlem.getCharacter().getCharacterID());

        svar_800E.writeIntBE(0x0);
        svar_800E.writeIntBE(0x0);
        svar_800E.writeIntBE(0x0);

        svar_800E.writeByte((byte) (medlem.getCharacter().getNavn().length() + 0x80));
        svar_800E.writeStringUTF16LE(medlem.getCharacter().getNavn());

        Packet svar_800E_pakke = new Packet(0x800E, svar_800E.getData());

        //Send til alle medlemmene.
        Iterator<TeamMember> medlemmer = lag.getTeamMembers();
        while (medlemmer.hasNext()) {

            TeamMember tm = medlemmer.next();
            chatServer.MultiClient.sendPacketToPlayer(svar_800E_pakke, tm.getCharacterID());
        }

    }

}
