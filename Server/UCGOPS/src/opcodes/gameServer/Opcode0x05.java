package opcodes.gameServer;

import items.*;
import java.util.ArrayList;
import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x05 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE(); //X posisjon.
        pd.readIntBE(); //Y posisjon.
        pd.readIntBE(); //Z posisjon.

        pd.readShortBE(); //Retning
        pd.readShortBE(); //Retning
        pd.readShortBE(); //Retning

        int item_type = pd.readShortBE(); //Item type vi skal returnere i denne pakken.

        PacketData svar = new PacketData();

        svar.writeShortBE((short) 0x0002);
        svar.writeByte((byte) item_type);

        //Sett radius for item søk avhengig av item type.
        int radius = 0;
        if (item_type == 1) {
            radius = 5000; //1250m
        } else if (item_type == 2) {
            radius = 8000; //2000m
        }
        PlayerGame player = (PlayerGame) p;
        ArrayList<gameServer.ItemCapsule> item_list = gameServer.ItemHandler.getItemList(player.getCharacter(), item_type, radius);
        if (item_list == null) {
            return;
        }

        //Nå har vi liste over alle items i nærheten av spilleren.
        svar.writeByteArray(helpers.UCGOcounter.getCounter(item_list.size()));

        Iterator<gameServer.ItemCapsule> i = item_list.iterator();
        while (i.hasNext()) {
            //Skriv data for hver item.
            gameServer.ItemCapsule itemc = i.next();

            svar.writeIntBE(itemc.getItem().getID());
            svar.writeIntBE(itemc.getItem().getContainerTail());
            svar.writeIntBE(itemc.getItem().getItem().getID());
            svar.writeIntBE(itemc.getPosisjon().getX());
            svar.writeIntBE(itemc.getPosisjon().getY());
            svar.writeIntBE(itemc.getPosisjon().getZ());
            svar.writeShortBE((short) itemc.getPosisjon().getTilt());
            svar.writeShortBE((short) itemc.getPosisjon().getRoll());
            svar.writeShortBE((short) itemc.getPosisjon().getDirection());

            svar.writeLongBE(itemc.getItem().getAntall());

            svar.writeIntBE(itemc.getOwner());

            svar.writeByte((byte) 0x80); //??

            svar.writeIntBE(itemc.getTidPlassert());

            //Sett hitpoints. Sjekk først om dette er ms/vehicle.
            if (itemc.getItem().getItem() instanceof Vehicle) {
                Vehicle v = (Vehicle) itemc.getItem().getItem();
                svar.writeIntBE(v.getHitpoints());
                svar.writeIntBE(v.getMaxHitpoints());
            } else if (itemc.getItem().getItem() instanceof Camp) {
                //Camp item. Disse kan ikke bli skadet med trenger hitpoints for å vises riktig.
                svar.writeIntBE(0x1);
                svar.writeIntBE(0x1);
            } else {
                //Trenger ikke hitpoints.
                svar.writeIntBE(0x0);
                svar.writeIntBE(0x0);
            }

            svar.writeIntBE(itemc.getTidOwnerExpire());

            svar.writeShortBE((short) 0x0);

            svar.writeByte((byte) 0x80);
        }

        Packet svar_pakke = new Packet(0x8005, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
