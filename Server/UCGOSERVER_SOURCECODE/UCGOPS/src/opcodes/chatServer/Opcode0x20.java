package opcodes.chatServer;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x20 implements Opcode {

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
            String navn = friends.get(c_id);

            svar.writeIntBE(c_id);
            svar.writeByte((byte) (navn.length() + 0x80));
            svar.writeStringUTF16LE(navn);
            svar.writeByte((byte) 0x0);
            svar.writeIntBE(0x80);
        }

        Packet svar_pakke = new Packet(0x801D, svar.getData());

        p.sendPacket(svar_pakke);
    }
}
