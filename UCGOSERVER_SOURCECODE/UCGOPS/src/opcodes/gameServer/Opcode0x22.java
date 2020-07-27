package opcodes.gameServer;

import containers.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;
import validItems.*;

public class Opcode0x22 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PlayerGame player = (PlayerGame) p;

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();
        pd.readIntBE();
        pd.readIntBE();
        pd.readIntBE();

        int containerID = pd.readIntBE();
        pd.readIntBE();

        int parentID = pd.readIntBE();
        pd.readIntBE();

        pd.readIntBE();
        pd.readIntBE();

        long antall = pd.readLongBE();

        int cityID = pd.readIntBE();
        int shopID = pd.readIntBE();
        int lastInt = pd.readIntBE();

        //Sjekk at containere er gyldige.
        Container con = null;
        ItemContainer container = null;
        HovedContainer parent = null;

        con = ContainerList.getContainer(containerID);
        if (con instanceof ItemContainer) {
            container = (ItemContainer) con;
        } else {
            return;
        }

        con = ContainerList.getContainer(parentID);
        if (con instanceof HovedContainer) {
            parent = (HovedContainer) con;
        } else {
            return;
        }

        //Sjekk at oppgitt item finnes i HovedContainer.
        if (!parent.containsItemContainer(container)) {
            return;
        }

        //Sjekk at item er gyldig og kan selges.
        GameItem item = ItemList.getItem(container.getItem().getID());
        if (item == null) {
            return;
        }

        //Sjekk at item kan selges.
        if (!item.canSell()) {
            //Item kan ikke selges, send feilmelding tilbake.
            PacketData svar = new PacketData();
            svar.writeIntBE(0x00010000);
            Packet svarPakke = new Packet(0x8022, svar.getData());
            p.sendPacket(svarPakke);
            return;
        }

        //Sjekk at antall er gyldig.
        if (antall <= 0 || antall > container.getAntall()) {
            //Prøver å selge mer av item enn det som finnes.
            System.out.println("OPCODE 0x22: ATTEMPT TO SELL INVALID AMOUNT OF ITEMS = " + antall);
            System.out.println("OPCODE 0x22: NUMBER OF ITEMS IN CONTAINER = " + container.getAntall());
            System.out.println("OPCODE 0x22: IITEM ID:" + container.getItem().getID());
            System.out.println("OPCODE 0x22: CHARACTER ID:" + player.getCharacter().getCharacterID() + ", IP:" + player.getIP());
            PacketData svar = new PacketData();
            svar.writeIntBE(0x00010000);
            Packet svar_pakke = new Packet(0x8022, svar.getData());
            p.sendPacket(svar_pakke);
            return;
        }

        //Dersom item ikke er stackable sjekk at vi kun selger 1stk av den.
        if (!item.isStackable() && antall != 1) {
            System.out.println("OPCODE 0x22: Attempt to sell non-stackable item as stackable.");
            System.out.println("OPCODE 0x22: Item ID:" + container.getItem().getID());
            System.out.println("Character ID:" + player.getCharacter().getCharacterID() + ", IP:" + player.getIP());
            PacketData svar = new PacketData();
            svar.writeIntBE(0x00010000);
            Packet svar_pakke = new Packet(0x8022, svar.getData());
            p.sendPacket(svar_pakke);
            return;
        }

        //Gi spilleren penger for items solgt. Hvis solgt i Newman/Richmond gi 10% mindre.
        long salgsPris = 0; //Salgs pris pr stk.
        if (cityID == 0x3A || cityID == 0x3B) {
            salgsPris = item.getSellingPrice() - Math.round(((double) item.getSellingPrice() / 100) * 10);
        } else {
            salgsPris = item.getSellingPrice();
        }

        long sum = antall * salgsPris;
        player.getCharacter().getMoneyContainer().settAntall(player.getCharacter().getMoneyContainer().getAntall() + sum);

        //Fjern items fra container.
        if (!item.isStackable() || antall == container.getAntall()) {
            //Alle items solgt. Fjern hele containeren.
            parent.removeItemContainer(container);
            container.settAntall(0);
            ContainerList.removeContainer(container.getID());
        } else {
            //Ikke alt ble solgt.
            container.settAntall(container.getAntall() - antall);
        }

        //Send svar tilbake.
        PacketData svar = new PacketData();
        svar.writeIntBE(0x00010002);
        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(0x8);
        svar.writeIntBE(container.getItem().getID());
        svar.writeIntBE(container.getID());
        svar.writeIntBE(container.getContainerTail());
        svar.writeIntBE(parent.getID());
        svar.writeIntBE(parent.getContainerTail());
        svar.writeIntBE(parent.getStatiskID());
        svar.writeIntBE(0xB);
        svar.writeLongBE(antall);
        svar.writeLongBE(sum);
        svar.writeIntBE(cityID);
        svar.writeIntBE(shopID);
        svar.writeIntBE(lastInt);

        Packet svarPakke = new Packet(0x8022, svar.getData());
        player.sendPacket(svarPakke);
    }
}
