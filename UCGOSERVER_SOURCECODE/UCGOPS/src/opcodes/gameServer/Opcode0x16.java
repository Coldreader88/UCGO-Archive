package opcodes.gameServer;

import containers.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x16 implements Opcode {

    public void execute(Player p, Packet pakke) {

        int character_id;
        int parent_container_id;
        int parent_container_tail;
        int parent_statisk_item_id;
        int container_id;
        int container_tail;
        int statisk_item_id;

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        character_id = pd.readIntBE();
        container_id = pd.readIntBE();
        container_tail = pd.readIntBE();

        parent_container_id = pd.readIntBE();
        parent_container_tail = pd.readIntBE();

        statisk_item_id = pd.readIntBE();

        parent_statisk_item_id = pd.readIntBE();

        //Resten av pakken ignorerer vi.
        //Sjekk at character ID er gyldig og at oppgitte containere eksisterer.
        PlayerGame player = (PlayerGame) p;
        int cid = player.getCharacter().getCharacterID();

        if (character_id == cid && ContainerList.getContainer(container_id) != null) {
            //Character ID og oppgitte containere stemmer.
            //Send svar tilbake.
            PacketData svar = new PacketData();

            svar.writeIntBE(0x00020002);
            svar.writeIntBE(character_id);
            svar.writeIntBE(container_id);
            svar.writeIntBE(container_tail);
            svar.writeIntBE(parent_container_id);
            svar.writeIntBE(parent_container_tail);
            svar.writeIntBE(statisk_item_id);
            svar.writeIntBE(parent_statisk_item_id);
            svar.writeIntBE(0xB);

            //Headeren er ferdig. Hent ut data fra containeren.
            byte[] c_data = ContainerList.getContainer(container_id).getData();

            //Skriv teller.
            svar.writeByteArray(helpers.UCGOcounter.getCounter(c_data.length));

            //Skriv container data.
            svar.writeByteArray(c_data);

            //Skriv slutt på pakke.
            svar.writeByte((byte) 0xFF);

            Packet svar_pakke = new Packet(0x8016, svar.getData());
            p.sendPacket(svar_pakke);
        } else {
            //Feil.
            PacketData svar = new PacketData();

            svar.writeIntBE(-1);
            Packet svar_pakke = new Packet(0x8016, svar.getData());

            p.sendPacket(svar_pakke);
        }
    }

}
