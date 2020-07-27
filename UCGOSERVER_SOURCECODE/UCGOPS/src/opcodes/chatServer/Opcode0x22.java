package opcodes.chatServer;

import characters.FriendList;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x22 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int character_id = pd.readIntBE();

        int slett_friend = pd.readIntBE();

        //Sjekk at character ID er riktig.
        PlayerChat player = (PlayerChat) p;

        if (character_id != player.getCharacter().getCharacterID()) {
            return;
        }

        //Slett friend.
        player.getCharacter().getFriends().removeFriend(slett_friend);

        //Slett denne spilleren fra den andre listen.
        PlayerChat andre_spiller = chatServer.MultiClient.getPlayer(slett_friend);

        //Hvis spiller er tilkoblet oppdater ellers hent friendlist fra databasen.
        if (andre_spiller != null) {
            andre_spiller.getCharacter().getFriends().removeFriend(character_id);
        } else if (userDB.ManageCharacters.getGameCharacter(slett_friend) != null) {
            FriendList friends = userDB.ManageCharacters.getGameCharacter(slett_friend).getFriends();
            if (friends != null) {
                friends.removeFriend(character_id);
            }
        }

        //Send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x00320002);

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        svar.writeIntBE(slett_friend);

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        svar.writeByte((byte) 0x80);

        Packet svar_pakke = new Packet(0x801F, svar.getData());

        p.sendPacket(svar_pakke);

//Den andre spilleren bør også bli informert om sletting, men akkurat nå fremgangs måten ukjent.
    }

}
