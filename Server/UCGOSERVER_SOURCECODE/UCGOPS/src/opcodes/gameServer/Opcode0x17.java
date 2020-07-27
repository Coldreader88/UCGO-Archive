package opcodes.gameServer;

import containers.*;
import items.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;
import validItems.GameItem;

public class Opcode0x17 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PlayerGame player = (PlayerGame) p;
        PacketData pd = new PacketData(pakke.getData());

        int moveType = pd.readIntBE(); //0x00070000=item flyttes, 0x0008..=MS ride in, 0x0009..=MS put in

        pd.readIntBE();

        int containerID = pd.readIntBE(); //Itemcontainer der item er.
        pd.readIntBE();

        int parentID = pd.readIntBE(); //Hovedcontainer item er i.
        pd.readIntBE();

        int targetID = pd.readIntBE(); //Hovedcontainer der item skal plasseres.
        pd.readIntBE();

        pd.readIntBE();
        pd.readIntBE();
        pd.readIntBE();

        long antall = pd.readLongBE(); //Antal items som slal flyttes.

        short sluttShort = pd.readShortBE();

        //Sjekk om item skal flyttes fra/til money/credit container. I så fall er dette en trade transaksjon
        //og håndteres av en egen metode.
        if (containerID == player.getCharacter().getMoneyContainer().getID()
                || containerID == player.getCharacter().getCreditContainer().getID()
                || targetID == player.getCharacter().getMoneyContainer().getID()
                || targetID == player.getCharacter().getCreditContainer().getID()) {
            //Penger skal flyttes, bruk egen metode for det.
            this.flyttPenger(player, new PacketData(pakke.getData()));
            return;
        }

        //Sjekk at de oppgitte containere er gyldige.
        ItemContainer ic;
        HovedContainer parentContainer;
        HovedContainer targetContainer;

        Container con = ContainerList.getContainer(containerID);
        if (con instanceof ItemContainer) {
            ic = (ItemContainer) con;
        } else {
            return;
        }

        con = ContainerList.getContainer(parentID);
        if (con instanceof HovedContainer) {
            parentContainer = (HovedContainer) con;
        } else {
            return;
        }

        con = ContainerList.getContainer(targetID);
        if (con instanceof HovedContainer) {
            targetContainer = (HovedContainer) con;
        } else {
            return;
        }

        //Sjekk at item er gyldig.
        validItems.GameItem item = validItems.ItemList.getItem(ic.getItem().getID());

        if (item == null) {
            System.out.println("Opcode 0x17: Invalid item in ItemContainer. ID for item = " + ic.getItem().getID());
            return;
        }

        //Sjekk at antallet ikke er under null.
        if (antall < 0) {
            System.out.println("OPCODE 0x17: Attempt to move negative number of items.");
            System.out.println("Amount = " + antall + " Items in container = " + ic.getAntall());
            System.out.println("Item ID = " + ic.getItem().getID());
            System.out.println("IP: " + player.getIP() + " Character ID: " + player.getCharacter().getCharacterID());
            gameServer.MultiClient.kickPlayer(player.getCharacter().getCharacterID());
            return;
        }

        //Sjekk at antallet er gyldig.        
        if (antall > ic.getAntall()) {
            System.out.println("OPCODE 0x17: Attempt to move more items than available in container.");
            System.out.println("Amount = " + antall + " Items in container = " + ic.getAntall());
            System.out.println("Item ID = " + ic.getItem().getID());
            System.out.println("IP: " + player.getIP() + " Character ID: " + player.getCharacter().getCharacterID());
            gameServer.MultiClient.kickPlayer(player.getCharacter().getCharacterID());
            return;
        }

        //Oppgitt antall og containere er gyldig. Flytt item.
        //Basert på item og moveType bruker vi forskjellige metoder.
        if (moveType == 0x00090000) {
            moveMStoHangar(player, parentContainer, targetContainer, ic, item, sluttShort);
        } else if (moveType == 0x00080000) {
            moveMSfromHangar(player, parentContainer, targetContainer, ic, item, sluttShort);
        } else if (moveType == 0x00070000 && item.isStackable()) {
            moveStackable(player, parentContainer, targetContainer, ic, item, antall, sluttShort);
        } else if (moveType == 0x00070000 && !item.isStackable()) {
            moveNonStackable(player, parentContainer, targetContainer, ic, item, antall, sluttShort);
        } else {
            //Feil! Vet ikke hva som skal gjøres.
            System.out.println("Opcode 0x17: ERROR. Unknown move type: " + moveType);
            return;
        }

    }

    /**
     * Denne metoden brukes når penger skal flyttes i en trade transaksjon.
     *
     * @param player Spilleren som flytter pengene.
     * @param pd Innholdet i opcode 0x17 pakken.
     */
    private void flyttPenger(PlayerGame player, PacketData pd) {

        HovedContainer trade_hc = null; //Hovedcontainer for trade vinduet, ikke alltid brukt.

        pd.readIntBE();
        pd.readIntBE();

        int container = pd.readIntBE(); //Container der pengene er.
        pd.readIntBE();

        int trade_vindu = pd.readIntBE(); //Hvis penger flyttes fra trade vinduet, er det container for det.
        pd.readIntBE();

        int target_container = pd.readIntBE(); //Der pengene skal plasseres.
        pd.readIntBE();

        pd.readIntBE();
        pd.readIntBE();
        pd.readIntBE();

        long antall = pd.readLongBE(); //Antall som skal flyttes.

        short slutt_short = pd.readShortBE(); //Short som skal sendes tilbake i svaret.

        //Sjekk at begge containerene er hovedcontainere.
        Container con_a = ContainerList.getContainer(container);
        Container con_b = ContainerList.getContainer(target_container);

        if (!(con_a instanceof HovedContainer) || !(con_b instanceof HovedContainer)) {
            System.out.println("Opcode 0x17:flyttPenger(): Invalid container type.");
            return;
        }

        HovedContainer hc_fra = (HovedContainer) con_a;
        HovedContainer hc_til = (HovedContainer) con_b;

        //Sjekk at det er nok penger i container.
        if (hc_fra.getAntall() < antall) {
            System.out.println("Opcode 0x17:flyttPenger(): Invalid amount to move.");
            System.out.println("Amount in con:" + hc_fra.getAntall());
            return;
        }

        HovedContainer hc_penger = null; //Containeren pengene skal lagres i.

        /*Sjekk om vi skal flytte penger til trade vinduet, pengene må i så fall plasseres i en egen
         hovedcontainer i trade vinduet. Sjekk om det finnes en slik container eller opprett en ny.*/
        if (hc_til.getStatiskID() == 0x1ADB5) {//0x1ADB5=Trade vinduet

            if (hc_til.getContentsHC().size() == 1) {
                //Trade vinduet kan kun inneholde en hoved container og det er pengene.
                hc_penger = hc_til.getContentsHC().iterator().next();
            } else {
                //Opprett ny hovedcontainer for pengene.
                hc_penger = ContainerList.newHovedContainer();
                hc_penger.settStatiskID(0x7A120);
                hc_penger.settAntall(0);
                hc_penger.settContainerTail(0x13);
                hc_til.addHovedContainer(hc_penger);
            }
        }//Sjekk om penger skal flyttes til money/credit container.
        else if (hc_til == player.getCharacter().getMoneyContainer()
                || hc_til == player.getCharacter().getCreditContainer()) {
            //Vi skal flytte penger fra trade vinduet.

            hc_penger = hc_til;

            //Hent ut container for trade vinduet.
            Container con = ContainerList.getContainer(trade_vindu);
            if (!(con instanceof HovedContainer)) {
                System.out.println("Opcode 0x17:flyttPenger(): Invalid trade window container.");
                return;
            }

            trade_hc = (HovedContainer) con;

            //Fjern container som inneholder penger fra trade vinduet.
            trade_hc.removeHovedContainer(hc_fra);

            //Fjern penge containeren fullstendig fra server.
            ContainerList.removeContainer(hc_fra.getID());
        }

        //Dette er første INT i svar pakken. Verdien avhenger av mengde innhold i containerene.
        int flytt_type = -1;

        if (hc_fra.getAntall() == antall && hc_penger.getAntall() > 0) {
            flytt_type = 0x03010002;
        }

        if (hc_fra.getAntall() > antall && hc_penger.getAntall() > 0) {
            flytt_type = 0x04010002;
        }

        if (hc_fra.getAntall() > antall && hc_penger.getAntall() == 0) {
            flytt_type = 0x02010002;
        }

        if (hc_fra.getAntall() == antall && hc_penger.getAntall() == 0
                && hc_penger == player.getCharacter().getMoneyContainer()) {
            flytt_type = 0x03010002;
        }

        if (hc_fra.getAntall() == antall
                && (hc_fra == player.getCharacter().getMoneyContainer() || hc_fra == player.getCharacter().getCreditContainer())) {
            flytt_type = 0x02010002;
        }

        //Oppdater antall penger i containerene.
        hc_fra.settAntall(hc_fra.getAntall() - antall);
        hc_penger.settAntall(hc_penger.getAntall() + antall);

        //OK, send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeIntBE(flytt_type);
        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(hc_fra.getID());
        svar.writeIntBE(hc_fra.getContainerTail());
        if (trade_hc != null) {//Sjekk om vi skal skrive info for trade vinduet.
            svar.writeIntBE(trade_hc.getID());
            svar.writeIntBE(trade_hc.getContainerTail());
        } else {
            svar.writeLongBE(0x0);
        }
        svar.writeIntBE(hc_til.getID());
        svar.writeIntBE(hc_til.getContainerTail());

        //Skriv neste container avhengig av om vi flytter til eller fra trade vinduet.
        if (hc_til.getStatiskID() == 0x1ADB5) {
            svar.writeIntBE(hc_penger.getID());
            svar.writeIntBE(hc_penger.getContainerTail());
        } else {
            svar.writeIntBE(hc_til.getID());
            svar.writeIntBE(hc_til.getContainerTail());
        }

        svar.writeIntBE(0x7A120); //Item ID for penger.
        svar.writeIntBE(hc_fra.getStatiskID());
        svar.writeIntBE(hc_til.getStatiskID());
        svar.writeLongBE(antall);
        svar.writeShortBE(slutt_short);

        //Skriv slutt data avhengig av flytt type.
        if (flytt_type == 0x02010002) {

            byte[] hc_data = hc_penger.getData();
            svar.writeByteArray(helpers.UCGOcounter.getCounter(hc_data.length));
            svar.writeByteArray(hc_data);

        } else {
            svar.writeByte((byte) 0x80);
        }

        Packet svar_pakke = new Packet(0x8017, svar.getData());

        player.sendPacket(svar_pakke);
    }

    /**
     * Denne metoden brukes når vi plasserer MS i hangar.
     *
     * @param player Spilleren
     * @param parent Container der item er plassert.
     * @param target Container der item skal plasseres.
     * @param ic Item som skal flyttes.
     * @param item Item info.
     */
    private void moveMStoHangar(PlayerGame player, HovedContainer parent, HovedContainer target, ItemContainer ic, GameItem item, short sluttShort) {

        //Sjekk at oppgitt item stemmer med MS vi har.
        if (player.getCharacter().getVehicleContainer() != ic) {
            return;
        }

        //OK! Plasser spillers MS i hangar.
        player.getCharacter().getHangarContainer().addItemContainer(ic);
        player.getCharacter().setHumanForm();
        player.getCharacter().appearanceChange();

        //Send svar tilbake
        PacketData pd = new PacketData();
        pd.writeIntBE(0x01030002);
        pd.writeIntBE(player.getCharacter().getCharacterID());

        //Container info
        pd.writeIntBE(ic.getID());
        pd.writeIntBE(ic.getContainerTail());
        pd.writeIntBE(player.getCharacter().getWearedContainer().getID());
        pd.writeIntBE(player.getCharacter().getWearedContainer().getContainerTail());
        pd.writeIntBE(player.getCharacter().getHangarContainer().getID());
        pd.writeIntBE(player.getCharacter().getHangarContainer().getContainerTail());
        pd.writeIntBE(ic.getID());
        pd.writeIntBE(ic.getContainerTail());

        pd.writeIntBE(-1);
        pd.writeIntBE(player.getCharacter().getWearedContainer().getStatiskID());
        pd.writeIntBE(player.getCharacter().getHangarContainer().getStatiskID());
        pd.writeLongBE(1);
        pd.writeShortBE(sluttShort);
        pd.writeByte((byte) 0x80);

        Packet p = new Packet(0x8017, pd.getData());
        player.sendPacket(p);
    }

    /**
     * Denne metoden brukes når vi tar MS ut av hangar, dvs RIDE IN MS.
     *
     * @param player Spilleren
     * @param parent Container der item er plassert.
     * @param target Container der item skal plasseres.
     * @param ic Item som skal flyttes.
     * @param item Item info.
     */
    private void moveMSfromHangar(PlayerGame player, HovedContainer parent, HovedContainer target, ItemContainer ic, GameItem item, short sluttShort) {

        //Sjekk at containere er OK.
        if (parent != player.getCharacter().getHangarContainer() || target != player.getCharacter().getWearedContainer()) {
            return;
        }

        //Sjekk at opggitt item finnes i hangar.
        if (!player.getCharacter().getHangarContainer().containsItemContainer(ic)) {
            return;
        }

        //Sjekk at spiller allerede ikke er i et vehicle.
        if (player.getCharacter().getVehicle() != null) {
            return;
        }

        //Sjekk at oppgitt container virkelig inneholder et vehicle.
        if (!(ic.getItem() instanceof Vehicle)) {
            return;
        }

        //OK! Fjern fra hangar og plasser spiller i vehicle.
        player.getCharacter().getHangarContainer().removeItemContainer(ic);
        player.getCharacter().setVehicle((Vehicle) ic.getItem(), ic);
        player.getCharacter().appearanceChange();

        //Send svar tilbake.
        PacketData pd = new PacketData();

        pd.writeIntBE(0x01020002);
        pd.writeIntBE(player.getCharacter().getCharacterID());

        //Container info.
        pd.writeIntBE(ic.getID());
        pd.writeIntBE(ic.getContainerTail());
        pd.writeIntBE(player.getCharacter().getHangarContainer().getID());
        pd.writeIntBE(player.getCharacter().getHangarContainer().getContainerTail());
        pd.writeIntBE(player.getCharacter().getWearedContainer().getID());
        pd.writeIntBE(player.getCharacter().getWearedContainer().getContainerTail());
        pd.writeIntBE(ic.getID());
        pd.writeIntBE(ic.getContainerTail());

        pd.writeIntBE(-1);
        pd.writeIntBE(player.getCharacter().getHangarContainer().getStatiskID());
        pd.writeIntBE(player.getCharacter().getWearedContainer().getStatiskID());
        pd.writeLongBE(1);
        pd.writeShortBE(sluttShort);
        pd.writeByte((byte) 0x80);

        Packet p = new Packet(0x8017, pd.getData());
        player.sendPacket(p);
    }

    /**
     * Denne metoden brukes når vi flytter stackable items, f.eks materialer.
     *
     * @param player Spilleren
     * @param parent Container der item er plassert.
     * @param target Container der item skal plasseres.
     * @param ic Item som skal flyttes.
     * @param item Item info.
     * @param antall Antall som flyttes.
     */
    private void moveStackable(PlayerGame player, HovedContainer parent, HovedContainer target, ItemContainer ic, GameItem item, long antall, short sluttShort) {
        //NB!! Ved et senere tidspunkt burde vi kanskje sjekke at spiller faktisk
        //har tilgang til oppgitt parent og target containere.

        //Sjekk at item finnes i parent container.
        if (!parent.containsItemContainer(ic)) {
            return;
        }

        //Sjekk om vi allerede har en stackable item av samme type i target container.
        //Hvis ikke må vi opprette en ny item container i target.
        ItemContainer targetIc;
        boolean alreadyExist = false; //Angir om item allerede eksisterte i target.
        boolean moveAll = false; //Angir om hele ic ble flyttet over dvs targetIc = ic

        if (target.getItemContainer(ic.getItem().getID()) != null) {
            //Bruk eksisterende item container.
            alreadyExist = true;
            targetIc = target.getItemContainer(ic.getItem().getID());
        } else if (antall == ic.getAntall() && target.getItemContainer(ic.getItem().getID()) == null) {
            //Item finnes ikke allerede i target og alle items skal flyttes over.
            //Da flytter vi bare hele item containeren istedenfor å opprette ny.
            alreadyExist = false;
            moveAll = true;
            targetIc = ic;

            parent.removeItemContainer(ic);
            target.addItemContainer(targetIc);
        } else {
            //Opprett ny item container.
            alreadyExist = false;
            targetIc = ContainerList.newItemContainer();

            //Alle stackable item er GeneralItem uten noen stats eller attributter.
            GeneralItem g = new GeneralItem(ic.getItem().getID());
            targetIc.addItem(g);
            targetIc.settAntall(0);

            target.addItemContainer(targetIc);
        }

        //targetIc representerer nå ItemContainer i target vi skal benytte.
        //FORUTSATT! at moveAll == false skal vi nå oppdatere antallet i ic og targetIc.
        if (!moveAll) {
            //Fjern items fra ic        
            ic.settAntall(ic.getAntall() - antall);
            if (ic.getAntall() < 0) {
                //Skal egentlig ikke kunne skje ettersom vi sjekker dette tidligere.
                //Men vi gjør dette for ekstra sikkerhet.                
                return;
            }

            //Legg til item i targetIc
            targetIc.settAntall(targetIc.getAntall() + antall);

            //Dersom ic er tom skal den fjernes.
            if (ic.getAntall() == 0) {
                parent.removeItemContainer(ic);
                ContainerList.removeContainer(ic.getID());
            }
        }

        //Send svar tilbake.
        PacketData pd = new PacketData();

        //Skriv første INT basert på hvordan items ble flyttet.
        if (moveAll) {
            pd.writeIntBE(0x01010002);
        } else if (ic.getAntall() == 0 && alreadyExist) {
            pd.writeIntBE(0x03010002);
        } else if (ic.getAntall() == 0 && !alreadyExist) {
            pd.writeIntBE(0x01010002);
        } else if (ic.getAntall() > 0 && alreadyExist) {
            pd.writeIntBE(0x04010002);
        } else if (ic.getAntall() > 0 && !alreadyExist) {
            pd.writeIntBE(0x02010002);
        }

        pd.writeIntBE(player.getCharacter().getCharacterID());

        //Container info.
        pd.writeIntBE(ic.getID());
        pd.writeIntBE(ic.getContainerTail());
        pd.writeIntBE(parent.getID());
        pd.writeIntBE(parent.getContainerTail());
        pd.writeIntBE(target.getID());
        pd.writeIntBE(target.getContainerTail());
        pd.writeIntBE(targetIc.getID());
        pd.writeIntBE(targetIc.getContainerTail());

        pd.writeIntBE(targetIc.getItem().getID());
        pd.writeIntBE(parent.getStatiskID());
        pd.writeIntBE(target.getStatiskID());
        pd.writeLongBE(antall);
        pd.writeShortBE(sluttShort);

        //Dersom 0x02010002 move type skal vi sette inn container data her.
        if (ic.getAntall() > 0 && !alreadyExist) {

            byte[] cd = targetIc.getData();
            pd.writeByteArray(helpers.UCGOcounter.getCounter(cd.length));
            pd.writeByteArray(cd);
        } else {
            pd.writeByte((byte) 0x80);
        }

        Packet p = new Packet(0x8017, pd.getData());
        player.sendPacket(p);
    }

    /**
     * Denne metoden brukes når vi flytter items som ikke stackes f.eks våpen og
     * engines.
     *
     * @param player Spilleren
     * @param parent Container der item er plassert.
     * @param target Container der item skal plasseres.
     * @param ic Item som skal flyttes.
     * @param item Item info.
     * @param antall Antall som flyttes.
     */
    private void moveNonStackable(PlayerGame player, HovedContainer parent, HovedContainer target, ItemContainer ic, GameItem item, long antall, short sluttShort) {
        //NB!! Ved et senere tidspunkt burde vi kanskje sjekke at spiller faktisk
        //har tilgang til oppgitt parent og target containere.

        //Sjekk at item finnes i parent container.
        if (!parent.containsItemContainer(ic)) {
            return;
        }

        //Sjekk at antall er 1. Kan aldri flytt mer enn "1" av nonstackable item.
        if (antall != 1) {
            return;
        }

        //OK! Fjern item fra parent og flytt over i target.
        parent.removeItemContainer(ic);
        target.addItemContainer(ic);

        //Send svar tilbake.
        PacketData pd = new PacketData();

        pd.writeIntBE(0x01010002);
        pd.writeIntBE(player.getCharacter().getCharacterID());

        //Container info.
        pd.writeIntBE(ic.getID());
        pd.writeIntBE(ic.getContainerTail());
        pd.writeIntBE(parent.getID());
        pd.writeIntBE(parent.getContainerTail());
        pd.writeIntBE(target.getID());
        pd.writeIntBE(target.getContainerTail());
        pd.writeIntBE(ic.getID());
        pd.writeIntBE(ic.getContainerTail());

        pd.writeIntBE(ic.getItem().getID());
        pd.writeIntBE(parent.getStatiskID());
        pd.writeIntBE(target.getStatiskID());
        pd.writeLongBE(1);
        pd.writeShortBE(sluttShort);
        pd.writeByte((byte) 0x80);

        Packet p = new Packet(0x8017, pd.getData());
        player.sendPacket(p);
    }
}
