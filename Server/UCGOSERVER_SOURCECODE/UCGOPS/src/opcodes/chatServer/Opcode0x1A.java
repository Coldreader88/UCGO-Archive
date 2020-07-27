package opcodes.chatServer;

import java.util.ArrayList;
import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x1A implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int character_id = pd.readIntBE();

        int channel_id = pd.readIntBE();

        //Sjekk at character ID er gyldig.
        PlayerChat player = (PlayerChat) p;

        if (player.getCharacter().getCharacterID() != character_id) {
            return;
        }

        //Send beskjed til alle spillere i channel.
        PacketData svar8018 = new PacketData();

        svar8018.writeIntBE(0x002D0002);
        svar8018.writeIntBE(channel_id);
        svar8018.writeIntBE(0x0);
        svar8018.writeIntBE(character_id);
        svar8018.writeIntBE(0x0);
        svar8018.writeIntBE(0x0);
        svar8018.writeIntBE(0x0);
        svar8018.writeByte((byte) (player.getCharacter().getNavn().length() + 0x80));
        svar8018.writeStringUTF16LE(player.getCharacter().getNavn());

        Packet svar8018_pakke = new Packet(0x8018, svar8018.getData());

        ArrayList<PlayerChat> spiller_liste = chatServer.MultiClient.getChatParticipants(channel_id);
        if (spiller_liste == null) {
            return;
        }
        Iterator<PlayerChat> spillere = spiller_liste.iterator();

        while (spillere.hasNext()) {
            chatServer.MultiClient.sendPacketToPlayer(svar8018_pakke, spillere.next().getCharacter().getCharacterID());
        }

        //Fjern spilleren.
        chatServer.MultiClient.removePlayerFromChat(player, channel_id);
    }

}
