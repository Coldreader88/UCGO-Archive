package serverComp;

import chatServer.Team;
import chatServer.TeamMember;
import java.util.Iterator;
import packetHandler.Packet;
import packetHandler.PacketData;
import players.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes av serveren til å sjekke for spillere som enten har
 * logget ut eller blitt koblet fra.
 *
 */
public class GameServerLogoff implements Runnable {

    private PlayerHandler<PlayerGame> playerList;

    /**
     *
     * @param playerList Link til listen over spillere som er tilkoblet
     * gameserver.
     */
    public GameServerLogoff(PlayerHandler<PlayerGame> playerList) {
        this.playerList = playerList;
    }

    public void run() {

        while (true) {

            long sjekkStart = System.currentTimeMillis(); //BUG TEST.
            this.sjekkSpillere();
            long sjekkSlutt = System.currentTimeMillis();

            if ((sjekkSlutt - sjekkStart) > 10000) {
                System.out.println("GameServerLogoff: sjekkSpillere, tid brukt=" + (sjekkSlutt - sjekkStart));
            }

            try {
                Thread.sleep(10 * 1000); //Vent i 10 sekunder.
            } catch (InterruptedException e) {
            }
        }

    }

    /**
     * Leter etter spillere som er logget av eller frakoblet.
     */
    private void sjekkSpillere() {

        //Gå gjennom alle spillere på server og sjekk om noen av dem ikke har sendt opcode 0x3 innen tidsgrensen.
        Iterator<PlayerGame> spillere = this.playerList.iterator();

        int timestamp = (int) (System.currentTimeMillis() / 1000);

        while (spillere.hasNext()) {

            PlayerGame spiller = spillere.next();

            if (spiller.getCharacter() != null && spiller.getCharacter().getLastTime0x3Received() < (timestamp - config.Server.gameServerLogoutTime)) {
                //Tidsrammen for å motta opcode 0x3 fra denne spilleren er overskredet.
                //Spiller har logget av eller mistet tilkoblingen.
                this.fjernSpiller(spiller);
            }
        }
    }

    /**
     * Fjerner en spiller fra gameserveren og lagrer data, spiller fjernes også
     * fra chatserver.
     *
     * @param spiller Spiller som skal fjernes.
     */
    private void fjernSpiller(PlayerGame spiller) {

        //OK, spilleren skal fjernes.
        gameServer.MultiClient.unregisterCharacter(spiller.getCharacter());

        spiller.save();

        this.playerList.remove(spiller);

        spiller.cleanUp();

        //Fjern spilleren fra chat server.
        PlayerChat spiller_chat = chatServer.MultiClient.getPlayer(spiller.getCharacter().getCharacterID());

        //Er ikke sikkert at spiller klarte å koble til chat server.
        if (spiller_chat == null) {
            return;
        }

        chatServer.MultiClient.unregisterPlayer(spiller_chat);

        spiller_chat.save();

        this.sendMeldingTilFriends(spiller_chat);

        PlayerHandlerStatic.removePlayerChat(spiller_chat);

        spiller_chat.cleanUp();
    }

    /**
     * Gir beskjed til spillerens friends og lag medlemmer om at spilleren har
     * logget av.
     *
     * @param player
     */
    private void sendMeldingTilFriends(PlayerChat player) {

        //Hvis medlem i et team, send beskjed til de andre medlemmene.
        int team_id = chatServer.TeamManagement.getTeamMembership(player.getCharacter().getCharacterID());
        if (team_id != -1) {
            //Medlem i team. Send 0x8013 til de andre medlemmene.
            PacketData svar_8013 = new PacketData();

            svar_8013.writeIntBE(0x2);
            svar_8013.writeIntBE(player.getCharacter().getCharacterID());
            svar_8013.writeIntBE(-1);

            Packet svar_8013_pakke = new Packet(0x8013, svar_8013.getData());

            Team lag = chatServer.TeamManagement.getTeam(team_id);
            Iterator<TeamMember> medlemmer = lag.getTeamMembers();

            while (medlemmer.hasNext()) {
                TeamMember medlem = medlemmer.next();
                if (medlem.getCharacterID() != player.getCharacter().getCharacterID()) {
                    chatServer.MultiClient.sendPacketToPlayer(svar_8013_pakke, medlem.getCharacterID());
                }
            }
        }

        //Send beskjed til de andre i spillerens friendlist om at denne spilleren har logget av.
        PacketData svar_8021 = new PacketData();

        svar_8021.writeIntBE(0x2);
        svar_8021.writeIntBE(player.getCharacter().getCharacterID());
        svar_8021.writeIntBE(-1);

        Packet svar_8021_pakke = new Packet(0x8021, svar_8021.getData());

        //Gå gjennom spillerens friendlist.
        Iterator<Integer> friends = player.getCharacter().getFriends().getFriends().keySet().iterator();
        while (friends.hasNext()) {
            int friend_id = friends.next();
            chatServer.MultiClient.sendPacketToPlayer(svar_8021_pakke, friend_id);
        }
    }

}
