package opcodes.gameServer;

import containers.*;

import gameServer.*;
import items.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x27 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int cid = pd.readIntBE(); //Container som skal åpnes.
        pd.readIntBE();

        Container con = ContainerList.getContainer(cid);

        //Sjekk at vi skal åpne en gyldig container.
        if (con == null || !(con instanceof ItemContainer)) {
            return;
        }

        //Sjekk at container faktiskt er plassert på bakken.
        ItemCapsule item = ItemHandler.getItem((ItemContainer) con);

        if (item == null) {
            return;
        }

        //Sjekk at dette er en wreckage container. Wreckage=Vehicle med 0 hitpoints.
        Item i = item.getItem().getItem();

        if (!(i instanceof Vehicle)) {
            //Alle wreckage containere SKAL inneholde et vehicle.
            System.out.println("Opcode 0x27: Attempt to open wreckage container not containing a vehicle, i:" + i);
            return;
        }

        Vehicle v = (Vehicle) i;

        if (v.getHitpoints() > 0) {
            //Vehicle har ikke 0 hitpoints, dette kan ikke være en wreckage container.
            System.out.println("Opcode 0x27: Container is not a wreckage.");
            return;
        }

        //OK, skriv ut innholdet i wreckage container.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x00010002);
        svar.writeIntBE(item.getItem().getID());
        svar.writeIntBE(item.getItem().getContainerTail());
        svar.writeIntBE(v.getID());

        ConcurrentLinkedQueue<ItemContainer> inventory = v.getInventory().getContents();
        svar.writeByte((byte) (0x80 + inventory.size()));

        Iterator<ItemContainer> items = inventory.iterator();

        while (items.hasNext()) {

            ItemContainer ic = items.next();

            svar.writeIntBE(ic.getID());
            svar.writeIntBE(ic.getContainerTail());
            svar.writeIntBE(ic.getItem().getID());
            svar.writeByteMultiple((byte) 0x0, 18);
            svar.writeLongBE(ic.getAntall());
            svar.writeIntBE(ic.getCreateTime());
            svar.writeByte((byte) 0x80);
            svar.writeIntBE(ic.getModifyTime());
            svar.writeByteMultiple((byte) 0x0, 13);
            svar.writeByte((byte) 0x3);
            svar.writeByte((byte) 0x80);
        }

        Packet svarPakke = new Packet(0x8027, svar.getData());

        p.sendPacket(svarPakke);

    }

}
