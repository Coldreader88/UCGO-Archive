package opcodes.chatServer;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import opcodes.Opcode;
import packetHandler.Packet;
import packetHandler.PacketData;
import players.Player;
import players.PlayerChat;

public class Opcode0x0B implements Opcode {

    public void execute(Player p, Packet pakke) {

        //Send svar med engang, ingen vits å lese mottatte data.
        PlayerChat player = (PlayerChat) p;
        ConcurrentHashMap<Integer, String> friends = player.getCharacter().getFriends().getFriends();
        Set<Integer> character_ids = friends.keySet();
        Iterator<Integer> ids = character_ids.iterator();

        PacketData svar = new PacketData();

        svar.writeByteArray(helpers.UCGOcounter.getCounter(character_ids.size())); //Antall i friendlist.

        //Gå gjennom alle i friendlist.
        while (ids.hasNext()) {

            int c_id = ids.next();

            svar.writeIntBE(c_id);
            if (chatServer.MultiClient.getPlayer(c_id) != null) {
                svar.writeIntBE(0x1); //Online.
            } else {
                svar.writeIntBE(0x0); //Offline.
            }
        }

        Packet svar_pakke = new Packet(0x800A, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
