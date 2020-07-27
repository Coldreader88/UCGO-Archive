package opcodes.chatServer;

import characters.*;
import chatServer.Team;
import chatServer.TeamMember;
import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.*;
import userDB.GameCharacter;
import userDB.ManageAccounts;
import userDB.ManageCharacters;

public class Opcode0x01 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int character_id = pd.readIntBE();

        int teller = pd.readByte() & 0x7F;

        String navn = pd.readStringUTF16LE(teller);

        //Hent ut account ID til spiller.
        int account_id = this.getAccountID(character_id);

        //Opprett nytt CharacterGame objekt.
        PlayerChat player = (PlayerChat) p;
        player.setCharacter(this.loadCharacter(character_id, account_id));

        //Sjekk at character er gyldig.
        if (account_id == 0 || player.getCharacter() == null) {
            //Feil login. Skriv log melding.
            String msg = "Invalid login: IP:" + p.getIP() + " Supplied Character ID:" + character_id
                    + " Supplied name:" + navn;
            admin.logging.chatserverMsg(msg);
            return;
        }

        //OK. Send svar tilbake.
        p.setAccountID(account_id);

        //Registrer spiller som tilkoblet.
        chatServer.MultiClient.registerPlayer(player);

        //Sett UCGM tag.
        p.setUCGM(ManageAccounts.getAccount(account_id).getUcgmTag());

        //Send svar.
        PacketData svar = new PacketData();

        svar.writeByte((byte) (navn.length() + 0x80));
        svar.writeStringUTF16LE(navn);
        svar.writeIntBE(0x6B4);
        svar.writeIntBE(0x1308); //???
        svar.writeIntBE(0x1);//Må være 0x1 for at chat skal virke.
        svar.writeIntBE(0);
        svar.writeIntBE(0);
        svar.writeIntBE(chatServer.TeamManagement.getTeamMembership(character_id));
        svar.writeIntBE(-1); //Må være -1 ellers virker ikke community chat.
        svar.writeByte((byte) 0x1); //faction/zone?
        svar.writeShortBE((short) 0x0);
        svar.writeByte((byte) player.getCharacter().getRank());
        svar.writeByte((byte) 0x80);

        Packet svar_pakke = new Packet(0x8001, svar.getData());

        p.sendPacket(svar_pakke);

        //Sett at spiller har sendt gyldig login info.
        p.setLoginReceived();

        //Hvis spiller er med i et lag send beksjed om at spiller er online.
        int team_id = chatServer.TeamManagement.getTeamMembership(player.getCharacter().getCharacterID());
        if (team_id != -1) {
            //Medlem i team. Send 0x8013 til de andre medlemmene.
            PacketData svar_8013 = new PacketData();

            svar_8013.writeIntBE(0x0);
            svar_8013.writeIntBE(player.getCharacter().getCharacterID());
            svar_8013.writeIntBE(1);

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

        //Send beskjed til de andre i spillerens friendlist om at denne spilleren har logget på.
        PacketData svar_8021 = new PacketData();

        svar_8021.writeIntBE(0x2);
        svar_8021.writeIntBE(player.getCharacter().getCharacterID());
        svar_8021.writeIntBE(1);

        Packet svar_8021_pakke = new Packet(0x8021, svar_8021.getData());

        //Gå gjennom spillerens friendlist.
        Iterator<Integer> venner = player.getCharacter().getFriends().getFriends().keySet().iterator();
        while (venner.hasNext()) {
            int friend_id = venner.next();
            chatServer.MultiClient.sendPacketToPlayer(svar_8021_pakke, friend_id);
        }

        //Skriv log melding.
        String msg = "Logged in: Character ID:" + player.getCharacter().getCharacterID()
                + " IP:" + p.getIP() + " Name:" + player.getCharacter().getNavn();
        admin.logging.chatserverMsg(msg);
    }

    /**
     * Henter ut account ID for oppgitt character.
     *
     *
     * @param character_id Character ID til character hvis konto ID vi skal
     * returnere.
     *
     * @return Account ID. Eller 0 hvis ikke funnet.
     */
    private int getAccountID(int character_id) {

        int account = 0;

        GameCharacter c = ManageCharacters.getGameCharacter(character_id);

        if (c != null) {
            account = c.getKonto().getAccountID();
        }

        return account;
    }

    /**
     * Henter informasjon om character og returnerer det som et CharacterChat
     * objekt.
     *
     * @param character_id Character ID til character vi skal returnere info om.
     * @param account_id Konto character tilhører.
     *
     * @return Character informasjon som chat serveren trenger, eller NULL hvis
     * feil.
     */
    private CharacterChat loadCharacter(int character_id, int account_id) {

        CharacterChat character = null;

        GameCharacter c = ManageCharacters.getGameCharacter(character_id);

        //Sjekk at character tilhører oppgitt konto ID.
        if (account_id == c.getKonto().getAccountID()) {
            character = new CharacterChat(character_id, c.getNavn(), c.getGender(), c.getFaction());
            character.setAppearance(c.getAppearance());
            character.setRank(c.getRank());
            character.setSkills(c.getSkills());
            character.setFriends(c.getFriends());
        }

        return character;
    }
}
