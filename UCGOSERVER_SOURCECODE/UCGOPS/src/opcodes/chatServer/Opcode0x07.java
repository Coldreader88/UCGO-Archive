package opcodes.chatServer;

import chatServer.Team;
import java.util.ArrayList;
import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x07 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        //Les inn første INT og finn ut hva som skal gjøres.
        int action = pd.readIntBE();

        switch (action) {

            case 0x1E: //Inviterer til community chat.
                this.InviterCommunityChat(pd, (PlayerChat) p);
                break;

            case 0x1F: //Invitasjon til community chat avbrytes.
                this.CommunityChatInvitasjonAvbrytes(pd, (PlayerChat) p);
                break;

            case 0x801C: //Mottok svar fra invitasjon til community chat.
                this.CommunityChatInvitasjonSvar(pd, (PlayerChat) p);
                break;

            case 0x25: //Add friend request.
                this.AddFriendRequest(pd, (PlayerChat) p);
                break;

            case 0x26: //Avbryt add friend request.
                this.AddFriendRequestAvbryt(pd, (PlayerChat) p);
                break;

            case 0x8022: //Svar på add friend request.
                this.AddFriendRequestSvar(pd, (PlayerChat) p);
                break;

            case 0x15: //Team invitasjon.
                this.TeamInvitasjon(pd, (PlayerChat) p);
                break;

            case 0x8014: //Svar på team invitasjon.
                this.TeamInvitasjonSvar(pd, (PlayerChat) p);
                break;

            case 0x16: //Team invitasjon avbrutt.
                this.TeamInvitasjonAvbryt(pd, (PlayerChat) p);
                break;
        }

    }

    /**
     * Håndterer opcode 0x7 når chat invitasjon sendes.
     *
     * @param pd Data sendt fra klient, første int antas allerede lest.
     * @param p Spilleren som sendte invitasjonen.
     */
    private void InviterCommunityChat(PacketData pd, PlayerChat player) {

        pd.readByte();
        int mottaker = pd.readIntBE();

        pd.readIntBE();

        int channel_id = pd.readIntBE();

        pd.readIntBE();

        int avsender = pd.readIntBE();

        //Sjekk at avsender er gyldig.
        if (avsender != player.getCharacter().getCharacterID()) {
            return;
        }

        //Send invitasjon til mottaker.
        PacketData svar1E = new PacketData();

        svar1E.writeIntBE(0x0);
        svar1E.writeIntBE(channel_id);
        svar1E.writeIntBE(0x0);
        svar1E.writeIntBE(avsender);
        svar1E.writeIntBE(0x0);
        svar1E.writeIntBE(0x0);
        svar1E.writeIntBE(0x0);

        Packet svar1E_pakke = new Packet(0x1E, svar1E.getData());

        chatServer.MultiClient.sendPacketToPlayer(svar1E_pakke, mottaker);
    }

    /**
     * Håndterer opcode 0x7 når sender avbryter en invitasjon til commnity chat.
     *
     * @param pd Pakkedata, første INT antas lest.
     * @param player Spiller som avbryter invitasjon.
     */
    private void CommunityChatInvitasjonAvbrytes(PacketData pd, PlayerChat player) {

        pd.readByte();

        int mottaker = pd.readIntBE();

        int sender = pd.readIntBE();

        int channel_id = pd.readIntBE();

        //Sjekk at sender er gyldig.
        if (sender != player.getCharacter().getCharacterID()) {
            return;
        }

        //OK, send beskjed til mottaker.
        PacketData svar1F = new PacketData();

        svar1F.writeIntBE(sender);
        svar1F.writeIntBE(channel_id);
        svar1F.writeByte((byte) 0xFF);

        Packet svar1F_pakke = new Packet(0x1F, svar1F.getData());

        chatServer.MultiClient.sendPacketToPlayer(svar1F_pakke, mottaker);
    }

    /**
     * Håndterer opcode 0x7 når mottaker aksepterer (eller ikke) community chat
     * invitasjon.
     *
     * @param pd Pakkedata, første INT antas lest.
     * @param player Spiller som avbryter invitasjon.
     */
    private void CommunityChatInvitasjonSvar(PacketData pd, PlayerChat player) {

        pd.readByte();

        int mottaker = pd.readIntBE();

        pd.readShortBE();

        int resultat = pd.readShortBE(); //0x2=Invitasjon aksepter, 0x1=Ikke akseptert.

        int channel_id = pd.readIntBE();
        pd.readIntBE();

        int sender = pd.readIntBE();

        //Sjekk at sender er gyldig.
        if (sender != player.getCharacter().getCharacterID()) {
            return;
        }

        //Meld spiller inn i chat channel hvis invitasjon ble godtatt.
        if (resultat == 0x2) {
            chatServer.MultiClient.addPlayerToChat(player, channel_id);
        }

        PacketData svar801C = new PacketData();

        svar801C.writeShortBE((short) 0x2B);
        svar801C.writeShortBE((short) resultat);
        svar801C.writeIntBE(channel_id);
        svar801C.writeIntBE(0x0);
        svar801C.writeIntBE(sender);
        svar801C.writeIntBE(0x0);
        svar801C.writeIntBE(0x0);
        svar801C.writeIntBE(0x0);

        Packet svar801C_pakke = new Packet(0x801C, svar801C.getData());

        chatServer.MultiClient.sendPacketToPlayer(svar801C_pakke, mottaker);

        //Send opcode 0x8017 tilbake til alle spillere i channel, med unntak av spiller som sendte invitasjon.
        //Kun hvis invitasjon ble akseptert.
        if (resultat != 0x2) {
            return;
        }

        PacketData svar8017 = new PacketData();

        svar8017.writeIntBE(0x002A0002);
        svar8017.writeIntBE(channel_id);
        svar8017.writeIntBE(0x0);
        svar8017.writeIntBE(sender);
        svar8017.writeIntBE(0x0);
        svar8017.writeIntBE(0x0);
        svar8017.writeIntBE(0x0);

        svar8017.writeByte((byte) (player.getCharacter().getNavn().length() + 0x80));
        svar8017.writeStringUTF16LE(player.getCharacter().getNavn());

        Packet svar8017_pakke = new Packet(0x8017, svar8017.getData());

        //Hent ut alle spillere i channel.
        ArrayList<PlayerChat> channel_liste = chatServer.MultiClient.getChatParticipants(channel_id);
        if (channel_liste == null) {
            return;
        }
        Iterator<PlayerChat> spillere = channel_liste.iterator();

        //Gå gjennom alle spillere i channel og informer dem om ny deltaker.
        while (spillere.hasNext()) {

            PlayerChat spiller = spillere.next();

            //Ikke send pakken til spilleren som sendte invitasjonen.
            if (spiller.getCharacter().getCharacterID() != mottaker) {
                chatServer.MultiClient.sendPacketToPlayer(svar8017_pakke, spiller.getCharacter().getCharacterID());
            }
        }

        //Send også melding til spiller som mottok invitasjon.
        player.sendPacket(svar8017_pakke);

    }

    /**
     * Håndterer opcode 0x7 når en spiller sender en add friend request.
     *
     * @param pd Pakkedata, første INT antas lest.
     * @param player Spiller som sender add friend request.
     */
    private void AddFriendRequest(PacketData pd, PlayerChat player) {

        pd.readByte();

        int mottaker = pd.readIntBE();

        pd.readIntBE();
        pd.readIntBE();
        pd.readIntBE();

        int sender = pd.readIntBE();

        //Sjekk at sender er gyldig.
        if (sender != player.getCharacter().getCharacterID()) {
            return;
        }

        //Send opcode 0x25 til mottaker.
        PacketData svar25 = new PacketData();

        svar25.writeIntBE(0x0);
        svar25.writeIntBE(0x0);
        svar25.writeIntBE(0x0);

        svar25.writeIntBE(sender);

        svar25.writeIntBE(0x0);
        svar25.writeIntBE(0x0);
        svar25.writeIntBE(0x0);

        Packet svar25_pakke = new Packet(0x25, svar25.getData());

        chatServer.MultiClient.sendPacketToPlayer(svar25_pakke, mottaker);
    }

    /**
     * Håndterer opcode 0x7 når en spiller avbryter en add friend request.
     *
     * @param pd Pakkedata, første INT antas lest.
     * @param player Spiller som sender add friend request.
     */
    private void AddFriendRequestAvbryt(PacketData pd, PlayerChat player) {

        pd.readByte();

        int mottaker = pd.readIntBE();

        int sender = pd.readIntBE();

        //Sjekk at sender er gyldig.
        if (sender != player.getCharacter().getCharacterID()) {
            return;
        }

        //Send opcode 0x26 til mottaker.
        PacketData svar26 = new PacketData();

        svar26.writeIntBE(sender);
        svar26.writeIntBE(-1);
        svar26.writeByte((byte) -1);

        Packet svar26_pakke = new Packet(0x26, svar26.getData());

        chatServer.MultiClient.sendPacketToPlayer(svar26_pakke, mottaker);
    }

    /**
     * Håndterer opcode 0x7 når en spiller sender svar på en add friend request.
     *
     * @param pd Pakkedata, første INT antas lest.
     * @param player Spiller som sender add friend request.
     */
    private void AddFriendRequestSvar(PacketData pd, PlayerChat player) {

        pd.readByte();

        int mottaker = pd.readIntBE();

        pd.readShortBE();

        int resultat = pd.readShortBE(); //0x2=OK, 0x1=Nei

        pd.readIntBE();
        pd.readIntBE();

        int sender = pd.readIntBE();

        //Sjekk at sender er gyldig.
        if (sender != player.getCharacter().getCharacterID()) {
            return;
        }

        //Send svar til mottaker.
        PacketData svar8022 = new PacketData();

        svar8022.writeShortBE((short) 0x31);
        svar8022.writeShortBE((short) resultat);

        svar8022.writeIntBE(0x0);
        svar8022.writeIntBE(0x0);

        svar8022.writeIntBE(sender);

        svar8022.writeIntBE(0x0);
        svar8022.writeIntBE(0x0);
        svar8022.writeIntBE(0x0);

        Packet svar8022_pakke = new Packet(0x8022, svar8022.getData());

        chatServer.MultiClient.sendPacketToPlayer(svar8022_pakke, mottaker);

        //Dersom invitasjon ble akseptert, send også svar til spiller som godtok add friend request.
        if (resultat != 0x2) {
            return;
        }

        PacketData svar801E = new PacketData();

        svar801E.writeIntBE(0x00300002);

        svar801E.writeIntBE(0x0);
        svar801E.writeIntBE(0x0);

        svar801E.writeIntBE(sender);

        svar801E.writeIntBE(0x0);
        svar801E.writeIntBE(0x0);
        svar801E.writeIntBE(0x0);

        svar801E.writeByte((byte) 0x80);

        Packet svar801E_pakke = new Packet(0x801E, svar801E.getData());

        player.sendPacket(svar801E_pakke);

        //Oppdater friend list for begge spillerene.
        PlayerChat playerA = chatServer.MultiClient.getPlayer(sender);
        PlayerChat playerB = chatServer.MultiClient.getPlayer(mottaker);

        if (playerA == null || playerB == null) {
            return;
        }

        playerA.getCharacter().getFriends().addFriend(playerB.getCharacter().getCharacterID(), playerB.getCharacter().getNavn());
        playerB.getCharacter().getFriends().addFriend(playerA.getCharacter().getCharacterID(), playerA.getCharacter().getNavn());
    }

    /**
     * Håndterer opcode 0x7 når en spiller sender team invitasjon til en annen.
     *
     * @param pd Pakkedata, første INT antas lest.
     * @param player Spiller som sender team invitasjon.
     */
    private void TeamInvitasjon(PacketData pd, PlayerChat player) {

        pd.readByte();

        int mottaker = pd.readIntBE();

        int antall = pd.readByte() & 0x7F;
        pd.readStringUTF16LE(antall);

        int team_id = pd.readIntBE();

        int sender = pd.readIntBE();

        //Sjekk at oppgitt team id er gyldig.
        Team lag = chatServer.TeamManagement.getTeam(team_id);
        if (lag == null) {
            return;
        }

        //Sjekk at sender ID er gyldig og at sender er leder for laget.
        if (sender != player.getCharacter().getCharacterID() || sender != lag.getOwner()) {
            return;
        }

        //OK, send invitasjon til mottaker.
        PacketData svar_15 = new PacketData();

        svar_15.writeByte((byte) (lag.getNavn().length() + 0x80));
        svar_15.writeStringUTF16LE(lag.getNavn());

        svar_15.writeIntBE(lag.getTeamID());

        svar_15.writeIntBE(sender);

        Packet svar_15_pakke = new Packet(0x15, svar_15.getData());

        chatServer.MultiClient.sendPacketToPlayer(svar_15_pakke, mottaker);
    }

    /**
     * Håndterer opcode 0x7 når en spiller sender svar på team invitasjon.
     *
     * @param pd Pakkedata, første INT antas lest.
     * @param player Spiller som sender svaret.
     */
    private void TeamInvitasjonSvar(PacketData pd, PlayerChat player) {

        pd.readByte();

        int mottaker = pd.readIntBE();

        pd.readShortBE();
        short invitasjon_svar = pd.readShortBE(); //0x2=ja, 0x1=nei.

        int team_id = pd.readIntBE();

        pd.readIntBE();

        int sender = pd.readIntBE();

        //Sjekk at oppgitt team id er gyldig.
        Team lag = chatServer.TeamManagement.getTeam(team_id);
        if (lag == null) {
            return;
        }

        //Sjekk at sender ID er gyldig og at mottaker er leder for laget.
        if (sender != player.getCharacter().getCharacterID() || mottaker != lag.getOwner()) {
            return;
        }

        //OK, send svar til mottaker.
        PacketData svar_8014 = new PacketData();

        svar_8014.writeShortBE((short) 0x1E);
        svar_8014.writeShortBE(invitasjon_svar);

        svar_8014.writeIntBE(lag.getTeamID());

        svar_8014.writeIntBE(0x0);

        svar_8014.writeIntBE(player.getCharacter().getCharacterID());

        svar_8014.writeIntBE(0x0);
        svar_8014.writeIntBE(0x0);
        svar_8014.writeIntBE(0x0);

        Packet svar_8014_pakke = new Packet(0x8014, svar_8014.getData());

        chatServer.MultiClient.sendPacketToPlayer(svar_8014_pakke, mottaker);
    }

    /**
     * Håndterer opcode 0x7 når en spiller avbryter en team invitasjon.
     *
     * @param pd Pakkedata, første INT antas lest.
     * @param player Spiller som avbryter invitasjon.
     */
    private void TeamInvitasjonAvbryt(PacketData pd, PlayerChat player) {

        pd.readByte();

        int mottaker = pd.readIntBE();

        int sender = pd.readIntBE();

        int team_id = pd.readIntBE();

        //Sjekk at oppgitt team id er gyldig.
        Team lag = chatServer.TeamManagement.getTeam(team_id);
        if (lag == null) {
            return;
        }

        //Sjekk at sender ID er gyldig og at sender er leder for laget.
        if (sender != player.getCharacter().getCharacterID() || sender != lag.getOwner()) {
            return;
        }

        //OK, send beskjed til mottaker.
        PacketData svar_16 = new PacketData();

        svar_16.writeIntBE(sender);
        svar_16.writeIntBE(team_id);
        svar_16.writeByte((byte) 0xFF);

        Packet svar_16_pakke = new Packet(0x16, svar_16.getData());

        chatServer.MultiClient.sendPacketToPlayer(svar_16_pakke, mottaker);
    }

}
