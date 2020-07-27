package opcodes.gameServer;

import characters.*;
import containers.*;
import items.*;
import java.util.Date;
import opcodes.Opcode;
import packetHandler.*;
import players.*;
import validItems.*;

public class Opcode0x23 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PlayerGame player = (PlayerGame) p;
        PacketData pd = new PacketData(pakke.getData());

        int plasseringType = pd.readIntBE(); //0x00010000=item plasseres, 0x00020000=/getoff

        pd.readIntBE(); //Character ID.

        int containerID = pd.readIntBE();  //Container der item er plassert.
        pd.readIntBE();

        int parentID = pd.readIntBE(); //Hovedcontainer item ligger i.
        pd.readIntBE();

        pd.readLongBE();
        pd.readLongBE();

        long antall = pd.readLongBE(); //Hvor mange som skal plasseres.

        int itemID = pd.readIntBE(); //ID for item som skal plasseres.
        pd.readIntBE(); //ID for parent container.
        pd.readIntBE(); //Ukjent

        //Hvor item skal plasseres.
        int posX = pd.readIntBE();
        int posY = pd.readIntBE();
        int posZ = pd.readIntBE();

        int tilt = pd.readShortBE();
        int roll = pd.readShortBE();
        int direction = pd.readShortBE();

        //Hent ut container som inneholder item.
        Container con = ContainerList.getContainer(containerID);

        //Sjekk om vi skal plassere penger.
        if ((con instanceof HovedContainer) && (con == player.getCharacter().getMoneyContainer() || con == player.getCharacter().getCreditContainer())) {
            boolean r = this.plasserPenger(player, (HovedContainer) con, antall, itemID);
            //Sjekk at pengene ble plassert.
            if (r) {
                //OK! Send 0x8023 tilbake til spilleren.
                //Svar er nesten identisk til pakke fra klient.
                byte[] svarData = pd.getData();
                svarData[3] = 0x2;

                Packet svarPakke = new Packet(0x8023, svarData);
                p.sendPacket(svarPakke);
                return;
            } else {
                //Feil. Send ingenting tilbake.
                return;
            }
        }

        //Det er ikke penger, sjekk at vi har en item container.
        ItemContainer ic;
        if (con instanceof ItemContainer) {
            ic = (ItemContainer) con;
        } else {
            return;
        }

        //Hent ut parent container.
        con = ContainerList.getContainer(parentID);
        HovedContainer parent;
        if (con instanceof HovedContainer) {
            parent = (HovedContainer) con;
        } else {
            return;
        }

        //Sjekk at item faktisk befinner seg i parent container.
        //Men ikke dersom dette er /getoff  Ettersom noen ganger kan det få
        //spillet til å henge seg opp.
        if (!parent.containsItemContainer(ic) && plasseringType != 0x00020000) {
            return;
        }

        //Sjekk at antall items som plasseres på bakken er gyldig.
        if (antall > ic.getAntall() || antall < 0) {
            //Prøver å plassere ugyldig antall items. TROLIG FUSK!
            //KICK spiller fra server.
            gameServer.MultiClient.kickPlayer(player.getCharacter().getCharacterID());

            System.out.println("OPCODE 0x23: ATTEMPT TO DROP INVALID NUMBER OF ITEMS.");
            System.out.println("OPCODE 0x23: CHARACTER ID:" + player.getCharacter().getCharacterID());
            System.out.println("OPCODE 0x23: ITEM ID:" + ic.getItem().getID() + " ANTALL I CONTAINER:" + ic.getAntall());
            System.out.println("OPCODE 0x23: ANTALL FORSØKT PLASSERT:" + antall);
            return;
        }

        //Sjekk at item er gyldig.
        GameItem item = ItemList.getItem(ic.getItem().getID());
        if (item == null) {
            return;
        }

        //Posisjon der item plasseres.
        Posisjon pos = new Posisjon(posX, posY, posZ, tilt, roll, direction, player.getCharacter().getPosisjon().getZone());

        //Sett når eierskap for item går ut.
        int tidOwnerExpire = (int) (new Date().getTime() / 1000L);
        tidOwnerExpire += config.Server.ownership_expires;

        //Plasser item på bakken/verdensrommet.
        if (plasseringType == 0x00020000) {
            getoffMS(player, pos, tidOwnerExpire);
        } else if (plasseringType == 0x00010000 && !item.isStackable() && antall == 1) {
            dropNonStackable(player, pos, tidOwnerExpire, ic, parent);
        } else if (plasseringType == 0x00010000 && item.isStackable() && antall == ic.getAntall()) {
            //Slipper alt innholdet i en stack.
            dropNonStackable(player, pos, tidOwnerExpire, ic, parent);
        } else if (plasseringType == 0x00010000 && item.isStackable() && antall <= ic.getAntall()) {
            dropStackable(player, pos, tidOwnerExpire, ic, parent, antall);
        } else {
            System.out.println("OPCODE 0x23: Ugyldig PlasseringType, " + plasseringType);
        }

        //Send 0x8023 tilbake. Svar er nesten identisk til pakke fra klient.
        byte[] svarData = pd.getData();
        svarData[3] = 0x2;

        Packet svarPakke = new Packet(0x8023, svarData);
        p.sendPacket(svarPakke);

        //Plasser itemcontainer på bakken/verdensrommet.
        //Fremgangsmåten er avhengig av om alle eller bare noen items i container skal plasseres på bakken.
        /* if (antall == ic.getAntall()) {
         //Alle items i container skal plasseres på bakken.
         //Flytt hele itemcontaineren.
         gameServer.ItemHandler.registerItem(ic, pos, player.getCharacter(), tid_owner_expire);
         Container pc = ContainerList.getContainer(parent_container);

         //Fjern itemcontainer fra parent container.
         if (pc instanceof HovedContainer) {
         HovedContainer hc = (HovedContainer) pc;
         hc.removeItemContainer(ic);
         }
         } else {
         //Bare noe av innholdet skal plasseres på bakken.
         //Sjekk først at item er stackable.
         if (!item.isStackable()) {
         System.out.println("Opcode 0x23: Attempt to stack non-stackable item.");
         return;
         }

         //Opprett ny itemcontainer som kan plasseres på bakken.
         ItemContainer ic2 = ContainerList.newItemContainer();
         //Siden item er stackable er den en GeneralItem, kun disse kan være stackable.
         //Derfor er det ikke nødvendig å kopiere attributer eller noe som helst fra eksiterende item.
         ic2.addItem(new GeneralItem(ic.getItem().getID()));
         ic2.settAntall(antall);
         ic2.settContainerTail(0x13);
         ic.settAntall(ic.getAntall() - antall);
         gameServer.ItemHandler.registerItem(ic2, pos, player.getCharacter(), tid_owner_expire);
         ic8035 = ic2; //Brukes av opcode 0x8035.
         }

         //Sjekk om spilleren brukte /getoff. I så fall sett tilbake til humanform og oppdater inventory.
         if (plassering_type == 0x00020000) {
         player.getCharacter().setHumanForm();
         player.getCharacter().appearanceChange();
         }

         //Send opcode 0x8035 tilbake.
         PacketData svar8035 = new PacketData();

         if (plassering_type == 0x00020000) {
         svar8035.writeIntBE(0x3); //Brukte getoff.
         } else {
         svar8035.writeIntBE(0x1); //Plasserte item.
         }
         svar8035.writeIntBE(ic8035.getID());
         svar8035.writeIntBE(ic8035.getContainerTail());
         svar8035.writeIntBE(ic8035.getItem().getID());
         svar8035.writeIntBE(pos_x);
         svar8035.writeIntBE(pos_y);
         svar8035.writeIntBE(pos_z);
         svar8035.writeShortBE((short) tilt);
         svar8035.writeShortBE((short) roll);
         svar8035.writeShortBE((short) direction);
         svar8035.writeLongBE(ic8035.getAntall());
         svar8035.writeIntBE(player.getCharacter().getCharacterID());
         svar8035.writeByte((byte) 0x80);
         svar8035.writeIntBE((int) (new Date().getTime() / 1000L));
         //Hvis item er vehicle eller våpen skriv hitpoints.
         if (ic8035.getItem() instanceof Vehicle) {
         Vehicle v = (Vehicle) ic8035.getItem();
         svar8035.writeIntBE(v.getHitpoints());
         svar8035.writeIntBE(v.getMaxHitpoints());

         } else if (ic8035.getItem() instanceof Weapon) {
         Weapon v = (Weapon) ic8035.getItem();
         svar8035.writeIntBE(v.getHitpoints());
         svar8035.writeIntBE(v.getMaxHitpoints());
         } else if (ic8035.getItem() instanceof Camp) {
         //Camp må ha hitpoints eller er den usynlig.
         svar8035.writeIntBE(0x1);
         svar8035.writeIntBE(0x1);
         } else {
         //Ingen hitpoints.
         svar8035.writeIntBE(0x0);
         svar8035.writeIntBE(0x0);
         }

         svar8035.writeIntBE(tid_owner_expire);
         svar8035.writeShortBE((short) 0xB);
         svar8035.writeByte((byte) 0x80);
         svar8035.writeIntBE(player.getCharacter().getCharacterID());
         svar8035.writeShortBE((short) 0xFFFF);
         if (plassering_type == 0x00020000) {
         svar8035.writeByte((byte) 0x2); //Brukte getoff.
         } else {
         svar8035.writeByte((byte) 0x1); //Plasserte vanlig item.
         }

         Packet svar8035_pakke = new Packet(0x8035, svar8035.getData());

         //Send opcode 0x8035 til alle spillere i området.
         gameServer.MultiClient.broadcastPacket(svar8035_pakke, player.getCharacter());


         //Send 0x8023 tilbake. Svar er nesten identisk til pakke fra klient.
         byte[] svar_data = pd.getData();
         svar_data[3] = 0x2;

         Packet svar_pakke = new Packet(0x8023, svar_data);

         p.sendPacket(svar_pakke);
         */
    }

    /**
     * Denne metoden håndterer plassering av penger på bakken. Det er nødvendig
     * å dele det opp på denne måten ellers blier hoved metoden rotet.
     *
     * @param player Spilleren som plasserer pengene.
     * @param con Container der pengene kommer fra.
     * @param antall Hvor mye penger som skal plasseres.
     * @param item_id Hvilket item ID som skal brukes for pengene.
     *
     * @return true hvis alt ok og opcode 0x8023 skal sendes tilbake til
     * klienten. Eller false.
     */
    private boolean plasserPenger(PlayerGame player, HovedContainer con, long antall, int item_id) {

        //Sjekk at oppgitt container tilsvarer spillerens credit/money container.
        if (con != player.getCharacter().getCreditContainer() && con != player.getCharacter().getMoneyContainer()) {
            System.out.println("Opcode 0x23: Player ID: " + player.getCharacter().getCharacterID()
                    + " attempted to remove money from invalid container: " + con.getID());
            return false;
        }

        //Sjekk at det er nok penger i containeren.
        if (con.getAntall() < antall) {
            System.out.println("Opcode 0x23: Player ID: " + player.getCharacter().getCharacterID()
                    + " attempted to remove invalid amount of money from money/credit container.");
            System.out.println("Amount in container:" + con.getAntall());
            System.out.println("Amount attempted to remove:" + antall);
            return false;
        }

        //Fjern pengene fra container.
        con.settAntall(con.getAntall() - antall);

        //Sett når eierskap for item går ut.
        int tid_owner_expire = (int) (new Date().getTime() / 1000L);
        tid_owner_expire += config.Server.ownership_expires;

        //Opprett ny itemcontainer som kan plasseres på bakken.
        ItemContainer ic = ContainerList.newItemContainer();
        //Opprett ny penge item
        ic.addItem(new GeneralItem(item_id));
        ic.settAntall(antall);
        ic.settContainerTail(0x13);
        //Plasser pengene på bakken.
        gameServer.ItemHandler.registerItem(ic, player.getCharacter().getPosisjon().lagKopi(), player.getCharacter(), tid_owner_expire);

        //Alt OK, send opcode 0x8035 tilbake.
        PacketData svar8035 = new PacketData();

        svar8035.writeIntBE(0x1); //Plasserte item.
        svar8035.writeIntBE(ic.getID());
        svar8035.writeIntBE(ic.getContainerTail());
        svar8035.writeIntBE(ic.getItem().getID());
        svar8035.writeIntBE(player.getCharacter().getPosisjon().getX());
        svar8035.writeIntBE(player.getCharacter().getPosisjon().getY());
        svar8035.writeIntBE(player.getCharacter().getPosisjon().getZ());
        svar8035.writeShortBE((short) player.getCharacter().getPosisjon().getTilt());
        svar8035.writeShortBE((short) player.getCharacter().getPosisjon().getRoll());
        svar8035.writeShortBE((short) player.getCharacter().getPosisjon().getDirection());
        svar8035.writeLongBE(antall);
        svar8035.writeIntBE(player.getCharacter().getCharacterID());
        svar8035.writeByte((byte) 0x80);
        svar8035.writeIntBE((int) (new Date().getTime() / 1000L));
        svar8035.writeIntBE(0x0);
        svar8035.writeIntBE(0x0);
        svar8035.writeIntBE(tid_owner_expire);
        svar8035.writeShortBE((short) 0xB);
        svar8035.writeByte((byte) 0x80);
        svar8035.writeIntBE(player.getCharacter().getCharacterID());
        svar8035.writeShortBE((short) 0xFFFF);
        svar8035.writeByte((byte) 0x1);

        Packet svar8035_pakke = new Packet(0x8035, svar8035.getData());

        //Send 0x8035 til alle spillere i området.
        gameServer.MultiClient.broadcastPacket(svar8035_pakke, player.getCharacter());

        return true;
    }

    /**
     * Denne metoden kalles når spiller bruker /getoff på MS.
     *
     * @param player
     * @param pos
     * @param tidOwnerExpire
     */
    private void getoffMS(PlayerGame player, Posisjon pos, int tidOwnerExpire) {

        //Sjekk at spiller befinner seg i et vehicle.
        if (player.getCharacter().getVehicle() == null) {
            return;
        }

        //Plasser item.
        ItemContainer con = player.getCharacter().getVehicleContainer();
        Vehicle v = player.getCharacter().getVehicle();
        gameServer.ItemHandler.registerItem(player.getCharacter().getVehicleContainer(), pos, player.getCharacter(), tidOwnerExpire);

        player.getCharacter().setHumanForm();
        player.getCharacter().appearanceChange();

        //Send opcode 0x8035 til alle spillere i nærheten.
        PacketData pd = new PacketData();

        pd.writeIntBE(0x3);
        pd.writeIntBE(con.getID());
        pd.writeIntBE(con.getContainerTail());
        pd.writeIntBE(con.getItem().getID());
        pd.writeIntBE(pos.getX());
        pd.writeIntBE(pos.getY());
        pd.writeIntBE(pos.getZ());
        pd.writeShortBE((short) pos.getTilt());
        pd.writeShortBE((short) pos.getRoll());
        pd.writeShortBE((short) pos.getDirection());
        pd.writeLongBE(1);
        pd.writeIntBE(player.getCharacter().getCharacterID());
        pd.writeByte((byte) 0x80);
        pd.writeIntBE((int) (new Date().getTime() / 1000L));
        pd.writeIntBE(v.getHitpoints());
        pd.writeIntBE(v.getMaxHitpoints());
        pd.writeIntBE(tidOwnerExpire);
        pd.writeShortBE((short) 0xB);
        pd.writeByte((byte) 0x80);
        pd.writeIntBE(player.getCharacter().getCharacterID());
        pd.writeShortBE((short) -1);
        pd.writeByte((byte) 0x2);

        Packet p = new Packet(0x8035, pd.getData());
        gameServer.MultiClient.broadcastPacket(p, player.getCharacter());

    }

    /**
     * Plasserer en nonstackable item på bakken. Denne metoden brukes også
     * dersom vi slipper ALT innholdet i en stack f.eks dersom vi har 2000
     * ENERGYPACKS og slipper ALLE på bakken kan denne metoden benyttes.
     *
     * @param player Spiller dette gjelder.
     * @param pos Posisjonen.
     * @param tidOwnerExpire Owner expire feltet.
     * @param ic Container der item er plassert.
     * @param parent Parent container for item.
     */
    private void dropNonStackable(PlayerGame player, Posisjon pos, int tidOwnerExpire, ItemContainer ic, HovedContainer parent) {

        //Fjern item fra parent container og plasser den.
        parent.removeItemContainer(ic);
        gameServer.ItemHandler.registerItem(ic, pos, player.getCharacter(), tidOwnerExpire);

        //Send opcode 0x8035 til alle spillere i nærheten.
        PacketData pd = new PacketData();

        pd.writeIntBE(0x1);
        pd.writeIntBE(ic.getID());
        pd.writeIntBE(ic.getContainerTail());
        pd.writeIntBE(ic.getItem().getID());
        pd.writeIntBE(pos.getX());
        pd.writeIntBE(pos.getY());
        pd.writeIntBE(pos.getZ());
        pd.writeShortBE((short) pos.getTilt());
        pd.writeShortBE((short) pos.getRoll());
        pd.writeShortBE((short) pos.getDirection());
        pd.writeLongBE(ic.getAntall());
        pd.writeIntBE(player.getCharacter().getCharacterID());
        pd.writeByte((byte) 0x80);
        pd.writeIntBE((int) (new Date().getTime() / 1000L));

        //Dersom dette er et vehicle, våpen eller camp skal vi skrive hitpoint info.
        if (ic.getItem() instanceof Vehicle) {
            Vehicle v = (Vehicle) ic.getItem();
            pd.writeIntBE(v.getHitpoints());
            pd.writeIntBE(v.getMaxHitpoints());
        } else if (ic.getItem() instanceof Weapon) {
            Weapon w = (Weapon) ic.getItem();
            pd.writeIntBE(w.getHitpoints());
            pd.writeIntBE(w.getMaxHitpoints());
        } else if (ic.getItem() instanceof Camp) {
            //Camp må ha hitpoints ellers er den usynlig.
            pd.writeIntBE(1);
            pd.writeIntBE(1);
        } else {
            pd.writeIntBE(0);
            pd.writeIntBE(0);
        }

        pd.writeIntBE(tidOwnerExpire);
        pd.writeShortBE((short) 0xB);
        pd.writeByte((byte) 0x80);
        pd.writeIntBE(player.getCharacter().getCharacterID());
        pd.writeShortBE((short) -1);
        pd.writeByte((byte) 0x2);

        Packet p = new Packet(0x8035, pd.getData());
        gameServer.MultiClient.broadcastPacket(p, player.getCharacter());

    }

    /**
     * Plasserer en stackable item på bakken. NB! Dersom vi plasserer alt
     * innholdet i en stack skal vi kalle metoden dropNonStackable()
     *
     * @param player Spiller dette gjelder.
     * @param pos Posisjonen.
     * @param tidOwnerExpire Owner expire feltet.
     * @param ic Container der item er plassert.
     * @param parent Parent container for item.
     * @param antall Antall av item som skal slippes.
     */
    private void dropStackable(PlayerGame player, Posisjon pos, int tidOwnerExpire, ItemContainer ic, HovedContainer parent, long antall) {

        //Sjekk at antall er gyldig.
        if (antall < 0 || antall > ic.getAntall()) {
            return;
        }

        if (antall == ic.getAntall()) {
            //Kall dropNonStackable() istedenfor.
            dropNonStackable(player, pos, tidOwnerExpire, ic, parent);
            return;
        }

        //Opprett en ny ItemContainer som skal holde items plassert på bakken.
        ItemContainer newIc = ContainerList.newItemContainer();
        newIc.addItem(new GeneralItem(ic.getItem().getID()));

        //Flytt items over.
        ic.settAntall(ic.getAntall() - antall);
        if (ic.getAntall() < 0) {
            return;
        }

        newIc.settAntall(antall);

        //Plasser ny ItemContainer.
        gameServer.ItemHandler.registerItem(newIc, pos, player.getCharacter(), tidOwnerExpire);

        //Send opcode 0x8035 til alle spillere i nærheten.
        PacketData pd = new PacketData();

        pd.writeIntBE(0x1);
        pd.writeIntBE(newIc.getID());
        pd.writeIntBE(newIc.getContainerTail());
        pd.writeIntBE(newIc.getItem().getID());
        pd.writeIntBE(pos.getX());
        pd.writeIntBE(pos.getY());
        pd.writeIntBE(pos.getZ());
        pd.writeShortBE((short) pos.getTilt());
        pd.writeShortBE((short) pos.getRoll());
        pd.writeShortBE((short) pos.getDirection());
        pd.writeLongBE(antall);
        pd.writeIntBE(player.getCharacter().getCharacterID());
        pd.writeByte((byte) 0x80);
        pd.writeIntBE((int) (new Date().getTime() / 1000L));
        pd.writeIntBE(0);
        pd.writeIntBE(0);
        pd.writeIntBE(tidOwnerExpire);
        pd.writeShortBE((short) 0xB);
        pd.writeByte((byte) 0x80);
        pd.writeIntBE(player.getCharacter().getCharacterID());
        pd.writeShortBE((short) -1);
        pd.writeByte((byte) 0x2);

        Packet p = new Packet(0x8035, pd.getData());
        gameServer.MultiClient.broadcastPacket(p, player.getCharacter());
    }
}
