package opcodes.chatServer;

import chatServer.Team;
import chatServer.TeamMember;
import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.Player;

public class Opcode0x0E implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int team_id = pd.readIntBE();

        Team lag = chatServer.TeamManagement.getTeam(team_id);

        if (lag == null) {
            return;
        }

        //OK, send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeIntBE(lag.getTeamID());
        svar.writeIntBE(0x0); //Timestamp, UNØDVENDIG.
        svar.writeIntBE(lag.getOwner());

        svar.writeByte((byte) (lag.getNavn().length() + 0x80));
        svar.writeStringUTF16LE(lag.getNavn());

        svar.writeByte((byte) (lag.getNumberOfMembers() + 0x80));

        Iterator<TeamMember> medlemmer = lag.getTeamMembers();

        while (medlemmer.hasNext()) {
            TeamMember medlem = medlemmer.next();

            svar.writeIntBE(medlem.getCharacterID());
            svar.writeIntBE(lag.getTeamID());
            svar.writeIntBE(0x0);

            svar.writeByte((byte) (medlem.getNavn().length() + 0x80));
            svar.writeStringUTF16LE(medlem.getNavn());

            svar.writeByte((byte) medlem.getGender());
            svar.writeByte((byte) medlem.getRank());
        }

        Packet svar_pakke = new Packet(0x800D, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
