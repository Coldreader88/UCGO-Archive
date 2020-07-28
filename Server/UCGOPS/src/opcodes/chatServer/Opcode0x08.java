package opcodes.chatServer;

import chatServer.Team;
import opcodes.Opcode;
import packetHandler.Packet;
import packetHandler.PacketData;
import players.Player;

public class Opcode0x08 implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int team_id = pd.readIntBE();

        Team lag = chatServer.TeamManagement.getTeam(team_id);

        if (lag != null) {
            //Send svar tilbake.
            PacketData svar = new PacketData();

            svar.writeByte((byte) (lag.getNavn().length() + 0x80));
            svar.writeStringUTF16LE(lag.getNavn());

            svar.writeIntBE(lag.getTeamID());

            svar.writeShortBE((short) 0x2); //Faction?

            Packet svar_pakke = new Packet(0x8007, svar.getData());

            p.sendPacket(svar_pakke);
        } else {
            //Sjekk om det er NPC team.
            String team = npc.Team.getTeam(team_id);

            if (team == null) {
                return; //Ugyldig team ID.
            }
            PacketData svar = new PacketData();

            svar.writeByte((byte) (team.length() + 0x80));
            svar.writeStringUTF16LE(team);
            svar.writeIntBE(team_id);
            svar.writeShortBE((short) 0x2); //Faction?

            Packet svar_pakke = new Packet(0x8007, svar.getData());

            p.sendPacket(svar_pakke);

        }

    }

}
