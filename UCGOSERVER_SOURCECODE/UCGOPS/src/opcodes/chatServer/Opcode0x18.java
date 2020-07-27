package opcodes.chatServer;

import java.util.ArrayList;
import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x18 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int channel_id = pd.readIntBE(); //Chat channel

        //Hent ut alle spillere i channel.
        ArrayList<PlayerChat> liste = chatServer.MultiClient.getChatParticipants(channel_id);
        //Sjekk at channel er gyldig.
        if (liste == null) {
            return;
        }

        //Send svar.		
        PacketData svar = new PacketData();

        svar.writeIntBE(channel_id); //Channel ID.

        svar.writeIntBE(0x0); //Timestamp?

        svar.writeByte((byte) (liste.size() + 0x80)); //Spillere i channel.

        //Gå gjennom alle spillere i channel og skriv info om dem.
        Iterator<PlayerChat> i = liste.iterator();

        while (i.hasNext()) {

            PlayerChat player = i.next();

            svar.writeIntBE(player.getCharacter().getCharacterID()); //Character ID.
            svar.writeIntBE(channel_id); //Channel ID.
            svar.writeIntBE(0x0);
            svar.writeByte((byte) (player.getCharacter().getNavn().length() + 0x80));
            svar.writeStringUTF16LE(player.getCharacter().getNavn());
            svar.writeByte((byte) player.getCharacter().getFaction()); //Faction?
            svar.writeByte((byte) player.getCharacter().getRank()); //Rank?
        }

        Packet svar_pakke = new Packet(0x8016, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
