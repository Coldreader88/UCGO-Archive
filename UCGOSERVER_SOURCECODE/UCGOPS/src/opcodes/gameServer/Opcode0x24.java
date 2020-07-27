package opcodes.gameServer;

import containers.*;
import items.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;
import validItems.*;

public class Opcode0x24 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        //0x00030000=ms/vehicle, 0x00010000=plukk opp item, 0x00020000=plukk opp penger, 0x00040000=wreckage.
        int moveType = pd.readIntBE();

        //Sjekk om vi skal plukke opp penger, bruker egen metode for det.
        if (moveType == 0x00020000) {
            this.plukkOppPenger((PlayerGame) p, pd);
            return;
        }

        //Sjekk om vi tar item fra wreckage container, bruker egen metode for det.
        if (moveType == 0x00040000) {
            this.itemFraWreckage((PlayerGame) p, pd);
            return;
        }

        pd.readIntBE();

        //Container der item er.
        int containerID = pd.readIntBE();
        pd.readIntBE();

        //Container der item skal plasseres.
        int targetContainerID = pd.readIntBE();
        pd.readIntBE();

        pd.readLongBE();
        pd.readLongBE();
        pd.readLongBE();

        //Antall som plukkes opp.
        long antall = pd.readLongBE();

        pd.readIntBE();
        pd.readIntBE();
        pd.readIntBE();

        byte sluttByte = pd.readByte(); //Trengs til 0x8024.

        //Sjekk at containerene eksisterer og at de er rett type.
        //target = Container item skal flyttes til. container = Item som skal flyttes.
        HovedContainer target;
        ItemContainer container;

        Container con = ContainerList.getContainer(containerID);
        if (con instanceof ItemContainer) {
            container = (ItemContainer) con;
        } else {
            return;
        }

        con = ContainerList.getContainer(targetContainerID);
        if (con instanceof HovedContainer) {
            target = (HovedContainer) con;
        } else {
            return;
        }

        //Denne brukes når opcode 0x8024 sendes.
        boolean itemStacked = false;

        //Hent ut info om item. Brukes når opcode 0x8035 sendes.
        gameServer.ItemCapsule itemc = gameServer.ItemHandler.getItem(container);

        //Sjekk at item er gyldig.
        GameItem item = ItemList.getItem(container.getItem().getID());
        if (item == null) {
            return;
        }

        //Sjekk at item faktisk finnes på bakken/verdensrommet.
        if (itemc == null) {
            return;
        }

        //Dersom event item sjekk at kun Admin/PSGM characters kan plukke opp.
        if (item.getItemSubType() == ItemSubType.Event && p.getUCGM() == 0xA) {
            return;
        }

        PlayerGame player = (PlayerGame) p;

        //Sjekk at itemcontainer inneholder riktig antall items.
        if (antall > container.getAntall() || antall < 0) {
            System.out.println("OPCODE 0x24: Attempt to pick up more items than available.");
            System.out.println("OPCODE 0x24: Character ID: " + player.getCharacter().getCharacterID() + ", IP: " + player.getCharacter().getIP());
            System.out.println("Item ID: " + container.getItem().getID() + ", Antall i container: " + container.getAntall() + ", Antall plukket opp: " + antall);
            return;
        }

        if (!item.isStackable() && moveType == 0x00010000) {
            //Plukker opp nonstackable item fra bakken.
            plukkOppNonStackable(player, container, target, item, sluttByte);
        } else if (item.getItemType() == ItemType.Vehicle && moveType == 0x00030000) {
            //Går inn i vehicle.
            enterVehicle(player, container, sluttByte);
        } else if (item.isStackable() && moveType == 0x00010000) {
            //Plukker opp stackable item fra bakken.
            plukkOppStackable(player, container, target, sluttByte, antall);
        }

        //Send opcode 0x8035.
        PacketData svar8035 = new PacketData();

        //Sjekk om spilleren gikk inn i ms/vehicle eller plukket opp item.
        if (item.getItemType() == ItemType.Vehicle && moveType == 0x00030000) {
            svar8035.writeIntBE(0x4);
        } else {
            //Plukket opp item.
            //Sjekk om spilleren plukket opp alt eller om noe ligger igjen.
            if (container.getAntall() <= 0 || !item.isStackable()) {
                //Alle items ble plukket opp.
                svar8035.writeIntBE(0x2);
            } else {
                //Ikke alle items ble plukket opp.
                svar8035.writeIntBE(0x1);
            }
        }

        svar8035.writeIntBE(container.getID());
        svar8035.writeIntBE(container.getContainerTail());
        svar8035.writeIntBE(container.getItem().getID());

        svar8035.writeIntBE(itemc.getPosisjon().getX());
        svar8035.writeIntBE(itemc.getPosisjon().getY());
        svar8035.writeIntBE(itemc.getPosisjon().getZ());
        svar8035.writeIntBE(0x0);
        svar8035.writeShortBE((short) itemc.getPosisjon().getDirection());

        svar8035.writeLongBE(container.getAntall());

        svar8035.writeIntBE(player.getCharacter().getCharacterID());

        svar8035.writeByte((byte) 0x80);

        svar8035.writeIntBE(itemc.getTidPlassert());
        svar8035.writeIntBE(0x0);
        svar8035.writeIntBE(0x0);
        svar8035.writeIntBE(itemc.getTidOwnerExpire());

        svar8035.writeShortBE((short) 0xC);

        svar8035.writeByte((byte) 0x80);

        svar8035.writeIntBE(player.getCharacter().getCharacterID());

        svar8035.writeShortBE((short) 0xFFFF);

        //Sjekk om spilleren gikk inn i ms/vehicle eller plukket opp item.
        if (item.getItemType() == ItemType.Vehicle) {
            svar8035.writeByte((byte) 0x2);
        } else {
            svar8035.writeByte((byte) 0x1);
        }

        Packet svar8035Pakke = new Packet(0x8035, svar8035.getData());

        //Send opcode 0x8035 til alle spillere i området.
        gameServer.MultiClient.broadcastPacket(svar8035Pakke, player.getCharacter());
    }

    /**
     * Denne metoden benyttes når spiller plukker opp en non-stackable item fra
     * bakken. Dette er ting som MS, våpen, engines osv...
     *
     * Denne metoden sender opcode 0x8024 til spiller men ikke opcode 0x8035.
     *
     * @param player Spiller dette gjelder.
     * @param container Item som plukkes opp.
     * @param target Hvor item skal plasseres.
     * @param item Info om item.
     * @param sluttByte Byte som sendes i opcode 0x24 og skal settes inn i
     * 0x8024.
     */
    private void plukkOppNonStackable(PlayerGame player, ItemContainer container, HovedContainer target, GameItem item, Byte sluttByte) {

        //Ettersom item ikke er stackable kan vi bare flytte hele itemcontaineren.
        target.addItemContainer(container);

        //Fjern item fra bakken.
        gameServer.ItemHandler.unregisterItem(container, player.getCharacter().getPosisjon().getZone());

        //Send 0x8024 tilbake.
        PacketData svar = new PacketData();
        svar.writeIntBE(0x00010002);
        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(container.getID());
        svar.writeIntBE(container.getContainerTail());
        svar.writeIntBE(target.getID());
        svar.writeIntBE(target.getContainerTail());
        svar.writeLongBE(0x0);
        svar.writeLongBE(0x0);
        svar.writeLongBE(0x0);
        svar.writeLongBE(0x1);
        svar.writeIntBE(container.getItem().getID());
        svar.writeIntBE(target.getStatiskID());

        if (target.getStatiskID() == 0x1ADB5 && item.getItemType() == ItemType.Vehicle) {
            svar.writeIntBE(0x9);
        } else {
            svar.writeIntBE(0x1); //Ingenting med trade å gjøre.

        }

        svar.writeByte(sluttByte);
        svar.writeIntBE(container.getID());
        svar.writeIntBE(container.getContainerTail());
        svar.writeLongBE(0x1);
        svar.writeIntBE(container.getItem().getID());
        svar.writeIntBE(container.getCreateTime());
        svar.writeIntBE(container.getModifyTime());
        svar.writeByteArray(container.getItem().getData());

        Packet svarPakke = new Packet(0x8024, svar.getData());
        player.sendPacket(svarPakke);

    }

    /**
     * Denne metoden kalles når spiller plukker opp en stackable item fra
     * bakken.
     *
     * @param player Spiller dette gjelder.
     * @param container Item vi plukker opp.
     * @param target Hvor item skal plasseres.
     * @param sluttByte Byte sendt i opcode 0x24 og settes inn i 0x8024.
     * @param antall Antall vi plukker opp.
     */
    private void plukkOppStackable(PlayerGame player, ItemContainer container, HovedContainer target, Byte sluttByte, long antall) {

        //Sjekk at antall er gyldig. Skal egentlig ikke kunne skje på dette punktet
        //men vi bare sjekker for å være 100% sikker uansett.
        if (antall < 0 || antall > container.getAntall()) {
            return;
        }

        //Sjekk om vi allerede har item i inventory.
        ItemContainer ic = target.getItemContainer(container.getItem().getID());
        boolean hasItem;

        if (ic != null) {
            //Vi har item i inventory fra før.
            ic.settAntall(ic.getAntall() + antall);
            hasItem = true;
        } else {
            //Vi har ikke item i inventory fra før.
            ic = ContainerList.newItemContainer();
            ic.addItem(new GeneralItem(container.getItem().getID()));
            ic.settAntall(antall);
            target.addItemContainer(ic);
            hasItem = false;
        }

        //Fjern items fra container
        container.settAntall(container.getAntall() - antall);
        if (container.getAntall() <= 0) {
            //Container er tom. Så den skal fjernes fra spillet.
            gameServer.ItemHandler.unregisterItem(container, player.getCharacter().getPosisjon().getZone());
            ContainerList.removeContainer(container.getID());
        }

        //Send svar tilbake.
        PacketData svar = new PacketData();
        if (!hasItem) {
            svar.writeIntBE(0x00010002);
        } else {
            svar.writeIntBE(0x02010002);
        }

        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(ic.getID());
        svar.writeIntBE(ic.getContainerTail());
        svar.writeIntBE(target.getID());
        svar.writeIntBE(target.getContainerTail());
        svar.writeLongBE(0x0);
        svar.writeLongBE(0x0);

        if (hasItem) {
            svar.writeIntBE(container.getID());
            svar.writeIntBE(container.getContainerTail());
        } else {
            svar.writeLongBE(0x0);
        }

        svar.writeLongBE(antall);
        svar.writeIntBE(ic.getItem().getID());
        svar.writeIntBE(target.getStatiskID());
        svar.writeIntBE(0x1); //Ingenting med trade å gjøre.
        svar.writeByte(sluttByte);
        svar.writeIntBE(ic.getID());
        svar.writeIntBE(ic.getContainerTail());
        svar.writeLongBE(antall);
        svar.writeIntBE(ic.getItem().getID());
        svar.writeIntBE(ic.getCreateTime());
        svar.writeIntBE(ic.getModifyTime());
        svar.writeByteArray(ic.getItem().getData());

        Packet svarPakke = new Packet(0x8024, svar.getData());
        player.sendPacket(svarPakke);
    }

    /**
     * Denne metoden benyttes når spiller går inn i et vehicle plassert på
     * bakken. Sender opcode 0x8024 til spiller, men ikke opcode 0x8035.
     *
     * @param player Spiller dette gjelder.
     * @param container ItemContainer til vehicle.
     * @param sluttByte Byte sendt i opcode 0x24 og settes inn i 0x8024.
     */
    private void enterVehicle(PlayerGame player, ItemContainer container, Byte sluttByte) {

        //Sjekk at item container virkelig inneholder et vehicle.
        if (!(container.getItem() instanceof Vehicle)) {
            return;
        }

        //Fjern vehicle fra bakken.
        gameServer.ItemHandler.unregisterItem(container, player.getCharacter().getPosisjon().getZone());

        Vehicle v = (Vehicle) container.getItem();
        player.getCharacter().setVehicle(v, container);
        player.getCharacter().appearanceChange();

        //Send svar tilbake.
        PacketData svar = new PacketData();
        svar.writeIntBE(0x00030002);
        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(container.getID());
        svar.writeIntBE(container.getContainerTail());

        //Vehicle plasseres alltid i weared container.
        svar.writeIntBE(player.getCharacter().getWearedContainer().getID());
        svar.writeIntBE(player.getCharacter().getWearedContainer().getContainerTail());
        svar.writeLongBE(0x0);
        svar.writeLongBE(0x0);
        svar.writeLongBE(0x0);
        svar.writeLongBE(0x1);
        svar.writeIntBE(container.getItem().getID());
        svar.writeIntBE(player.getCharacter().getWearedContainer().getStatiskID());
        svar.writeIntBE(0x1);
        svar.writeByte(sluttByte);
        svar.writeIntBE(container.getID());
        svar.writeIntBE(container.getContainerTail());
        svar.writeLongBE(0x1);
        svar.writeIntBE(container.getItem().getID());
        svar.writeIntBE(container.getCreateTime());
        svar.writeIntBE(container.getModifyTime());
        svar.writeByteArray(container.getItem().getData());

        Packet svarPakke = new Packet(0x8024, svar.getData());
        player.sendPacket(svarPakke);
    }

    /**
     * Håndterer opcode 0x24 når penger plukkes opp. Denne metoden sender også
     * opcode 0x8035 og 0x8024 tilbake.
     *
     * @param player Spilleren som plukker opp penger.
     * @param pd Innholdet i opcode 0x24. NB! Første INT antas lest.
     */
    private void plukkOppPenger(PlayerGame player, PacketData pd) {

        pd.readIntBE();

        //Der pengene ligger.
        int containerID = pd.readIntBE();
        pd.readIntBE();

        //Der pengene skal plasseres.
        int targetID = pd.readIntBE();
        pd.readIntBE();

        pd.readLongBE();
        pd.readLongBE();
        pd.readLongBE();

        //Hvor mye penger som skal plukkes opp.
        long antall = pd.readLongBE();

        pd.readIntBE();
        pd.readIntBE();
        pd.readIntBE();

        //Slutt byte som brukes i opcode 0x8024.
        byte sluttByte = pd.readByte();

        //Sjekk at target container er gyldig.
        Container con = ContainerList.getContainer(targetID);

        if (!(con instanceof HovedContainer)) {
            return;
        }

        HovedContainer target = (HovedContainer) con;

        //Sjekk at pengene skal til en gyldig container.
        //NB! Backpack = Money container.  BUG i klienten.
        if (target != player.getCharacter().getBackpackContainer() && target != player.getCharacter().getCreditContainer()) {
            return;
        }

        //Hvis target = Backpack, rett den til money container.
        if (target == player.getCharacter().getBackpackContainer()) {
            target = player.getCharacter().getMoneyContainer();
        }

        //Sjekk at oppgitt item container er gyldig.
        con = ContainerList.getContainer(containerID);
        if (!(con instanceof ItemContainer)) {
            return;
        }

        ItemContainer container = (ItemContainer) con;

        //Sjekk at det finnes nok penger i item containeren.
        if (antall > container.getAntall() || antall <= 0) {
            System.out.println("Opcode 0x24: Error, attempt to pick up more money than exists in item container.");
            System.out.println("Opcode 0x24: Character ID: " + player.getCharacter().getCharacterID());
            return;
        }

        //Hent ut info om item. Brukes når opcode 0x8035 sendes.
        gameServer.ItemCapsule itemc = gameServer.ItemHandler.getItem(container);

        //Sjekk at item er gyldig.
        GameItem item = ItemList.getItem(container.getItem().getID());
        if (item == null) {
            return;
        }

        //Sjekk at item faktisk finnes på bakken/verdensrommet.
        if (itemc == null) {
            return;
        }

        //OK, flytt pengene.
        target.settAntall(target.getAntall() + antall);
        container.settAntall(container.getAntall() - antall);

        //Dersom alle pengene ble plukket opp, fjern item fra spillet.
        if (container.getAntall() <= 0) {
            gameServer.ItemHandler.unregisterItem(container, itemc.getPosisjon().getZone());
            ContainerList.removeContainer(container.getID());
        }

        //Send opcode 0x8024 tilbake.        
        PacketData svar = new PacketData();

        svar.writeIntBE(0x02020002);
        svar.writeIntBE(player.getCharacter().getCharacterID());

        svar.writeIntBE(container.getID());
        svar.writeIntBE(container.getContainerTail());
        svar.writeIntBE(target.getID());
        svar.writeIntBE(target.getContainerTail());
        svar.writeLongBE(0x0L);
        svar.writeLongBE(0x0L);
        svar.writeIntBE(container.getID());
        svar.writeIntBE(container.getContainerTail());
        svar.writeLongBE(antall);

        svar.writeIntBE(container.getItem().getID());
        svar.writeIntBE(target.getStatiskID());

        svar.writeIntBE(0x1);

        svar.writeByte(sluttByte);

        svar.writeIntBE(target.getID());
        svar.writeIntBE(target.getContainerTail());

        svar.writeLongBE(antall);

        svar.writeIntBE(container.getItem().getID());

        svar.writeIntBE(-1);
        svar.writeIntBE(-1);

        svar.writeByteArray(container.getItem().getData());

        Packet svar_pakke = new Packet(0x8024, svar.getData());

        player.sendPacket(svar_pakke);

        //Send opcode 0x8035 til de andre spillerene i området.
        PacketData svar8035 = new PacketData();

        //Sjekk om spilleren plukket opp alt eller om noe ligger igjen.
        if (container.getAntall() <= 0) {
            svar8035.writeIntBE(0x2);
        } else {
            svar8035.writeIntBE(0x1);
        }

        svar8035.writeIntBE(container.getID());
        svar8035.writeIntBE(container.getContainerTail());
        svar8035.writeIntBE(container.getItem().getID());

        svar8035.writeIntBE(itemc.getPosisjon().getX());
        svar8035.writeIntBE(itemc.getPosisjon().getY());
        svar8035.writeIntBE(itemc.getPosisjon().getZ());
        svar8035.writeIntBE(0x0);
        svar8035.writeShortBE((short) itemc.getPosisjon().getDirection());

        svar8035.writeLongBE(container.getAntall());

        svar8035.writeIntBE(player.getCharacter().getCharacterID());

        svar8035.writeByte((byte) 0x80);

        svar8035.writeIntBE(itemc.getTidPlassert());
        svar8035.writeIntBE(0x0);
        svar8035.writeIntBE(0x0);
        svar8035.writeIntBE(itemc.getTidOwnerExpire());

        svar8035.writeShortBE((short) 0xC);

        svar8035.writeByte((byte) 0x80);

        svar8035.writeIntBE(player.getCharacter().getCharacterID());

        svar8035.writeShortBE((short) 0xFFFF);

        svar8035.writeByte((byte) 0x1);

        Packet svar8035Pakke = new Packet(0x8035, svar8035.getData());

        //Send opcode 0x8035 til alle spillere i området.
        gameServer.MultiClient.broadcastPacket(svar8035Pakke, player.getCharacter());
    }

    /**
     * Denne metoden håndterer opcode 0x24 når spilleren tar en item ut av
     * wreckage container.
     *
     * @param player Spiller som tar item.
     * @param pd Pakke data, første INT antas lest.
     */
    private void itemFraWreckage(PlayerGame player, PacketData pd) {

        pd.readIntBE();

        int cid = pd.readIntBE(); //Container til item.
        pd.readIntBE();

        int tcid = pd.readIntBE(); //Container der item skal plasseres.
        pd.readIntBE();

        pd.readLongBE();

        int wreck = pd.readIntBE(); //Container til wreckage.
        pd.readIntBE();

        pd.readLongBE();

        long antall = pd.readLongBE(); //Antall plukket opp.

        pd.readIntBE();
        pd.readIntBE();
        pd.readIntBE();

        byte sisteByte = pd.readByte(); //Siste byte i 0x24 MÅ sendes tilbake i 0x8024.

        //Sjekk at alle containere er gyldige.
        Container con;
        ItemContainer ic; //Der item er.
        ItemContainer nyIc; //Eventuell ny item container til item etter flytting. Nødvendig dersom item er stackable og skal deles opp.
        ItemContainer icWreck; //Der wreckage er.
        HovedContainer hc; //Der item skal plasseres.

        con = ContainerList.getContainer(cid);
        if (con instanceof ItemContainer) {
            ic = (ItemContainer) con;
        } else {
            return;
        }

        con = ContainerList.getContainer(wreck);
        if (con instanceof ItemContainer) {
            icWreck = (ItemContainer) con;
        } else {
            return;
        }

        con = ContainerList.getContainer(tcid);
        if (con instanceof HovedContainer) {
            hc = (HovedContainer) con;
        } else {
            return;
        }

        //Sjekk at wreckage container virkelig er et wreckage.
        Vehicle v; //Vehicle som er i wreckage container.

        if (icWreck.getItem() instanceof Vehicle) {
            v = (Vehicle) icWreck.getItem();
        } else {
            return;
        }

        //Sjekk at oppgitt item faktiskt finnes i wreckage container.
        HovedContainer inventory = v.getInventory();
        if (!inventory.containsItemContainer(ic)) {
            return;
        }

        //Denne variabelen indikerer verdi på første INT som skal skrives i svar pakken.
        //true:0x01040002 = UNSTACKABLE ITEM eller NY STACKABLE ITEM, DVS NY ITEM CONTAINER!
        //false:0x02040002 = STACKABLE ITEM SOM EKSISTERER FRA FØR, OPPDATER ANTALLET!
        boolean svarInt;

        //Hent ut info om item som skal fjernes fra wreckage.
        GameItem item = ItemList.getItem(ic.getItem().getID());

        //Sjekk at itemcontainer inneholder riktig antall items.
        if (antall > ic.getAntall() || antall < 0) {
            System.out.println("OPCODE 0x24: Attempt to pick up more items than available from wreckage.");
            System.out.println("OPCODE 0x24: Character ID: " + player.getCharacter().getCharacterID() + ", IP: " + player.getCharacter().getIP());
            System.out.println("Item ID: " + ic.getItem().getID() + ", Antall i container: " + ic.getAntall() + ", Antall plukket opp: " + antall);
            return;
        }

        //Fremgangsmåte for å flytte item varier etter om item er stackable eller ikke.
        if (item.isStackable()) {
            //Item er stackable, sjekk om denne type item allerede finnes i inventory.
            if (hc.getItemContainer(ic.getItem().getID()) != null) {
                //Item finnes i inventory allerede, bruk eksisterende item container.
                nyIc = hc.getItemContainer(ic.getItem().getID());

                //Oppdater antall i begge containere.
                nyIc.settAntall(nyIc.getAntall() + antall);
                ic.settAntall(ic.getAntall() - antall);

                //Hvis gammel IC er tom fjern den.
                if (ic.getAntall() <= 0) {
                    ContainerList.removeContainer(ic.getID());
                    inventory.removeItemContainer(ic);
                }

                svarInt = false;
            } else {
                //Item finnes ikke i inventory fra før, flytt hele item containeren over eller del den opp
                //avhengig hvor mange items som skal flyttes.
                if (ic.getAntall() == antall) {
                    //Alle items skal flyttes over.
                    hc.addItemContainer(ic);
                    inventory.removeItemContainer(ic);

                    nyIc = ic;
                } else {
                    //Del opp item container, ikke alle items skal flyttes.
                    nyIc = ContainerList.newItemContainer();
                    hc.addItemContainer(nyIc);

                    //Alle items som er stackable er av type general.
                    GeneralItem i = new GeneralItem(ic.getItem().getID());
                    nyIc.addItem(i);
                    nyIc.settAntall(antall);

                    //Oppdater antall i gammel IC.
                    ic.settAntall(ic.getAntall() - antall);
                }

                svarInt = true;
            }
        } else {
            //Item er ikke stackable, da flyttes hele item containeren til ny plass.
            inventory.removeItemContainer(ic);
            hc.addItemContainer(ic);

            nyIc = ic;

            svarInt = true;
        }

        //OK, send svar tilbake.
        PacketData svar = new PacketData();

        //Skriv første int avhengig av om ny IC ble opprettet i inventory eller ikke.
        //0x01040002 = UNSTACKABLE ITEM eller NY STACKABLE ITEM, DVS NY CONTAINER I INVENTORY!
        //0x02040002 = STACKABLE ITEM SOM EKSISTERER FRA FØR, OPPDATER ANTALLET!
        if (svarInt) {
            svar.writeIntBE(0x01040002);
        } else {
            svar.writeIntBE(0x02040002);
        }

        svar.writeIntBE(player.getCharacter().getCharacterID());

        svar.writeIntBE(nyIc.getID());
        svar.writeIntBE(nyIc.getContainerTail());

        svar.writeIntBE(hc.getID());
        svar.writeIntBE(hc.getContainerTail());

        svar.writeLongBE(0x0);
        svar.writeLongBE(0x0);

        if (nyIc != ic) {
            //Stackable item flyttes til ny container, skriv ut ID til gammel container.
            svar.writeIntBE(ic.getID());
            svar.writeIntBE(ic.getContainerTail());
        } else {
            svar.writeLongBE(0x0);
        }

        svar.writeLongBE(antall);

        svar.writeIntBE(nyIc.getItem().getID());
        svar.writeIntBE(hc.getStatiskID());

        svar.writeIntBE(0x1);
        svar.writeByte(sisteByte);

        svar.writeIntBE(nyIc.getID());
        svar.writeIntBE(nyIc.getContainerTail());

        svar.writeLongBE(nyIc.getAntall());

        svar.writeIntBE(nyIc.getItem().getID());

        svar.writeIntBE(nyIc.getCreateTime());
        svar.writeIntBE(nyIc.getModifyTime());

        svar.writeByteArray(nyIc.getItem().getData());

        Packet svarPakke = new Packet(0x8024, svar.getData());

        player.sendPacket(svarPakke);
    }
}
