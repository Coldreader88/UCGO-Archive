package opcodes.gameServer;

import containers.*;
import items.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;
import userDB.GameCharacter;
import userDB.ManageCharacters;
import validItems.ItemType;

public class Opcode0x21 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PlayerGame player = (PlayerGame) p;

        PacketData pd = new PacketData(pakke.getData());

        int buy_type = pd.readIntBE(); //Indikerer om det er butikk kjøp eller /taxi og /transport

        int character_id = pd.readIntBE();

        pd.readLongBE();

        int parent_container = pd.readIntBE(); //Container der item skal plasseres.
        int parent_container_tail = pd.readIntBE(); //Container tail.

        long antall = pd.readLongBE(); //Antall kjøpt.

        int item_id = pd.readIntBE(); //ID for item kjøpt.

        //Sjekk at antall kjøpt er gyldig.
        if (antall < 0 || antall > 2000) {
            //Finn kontoen spiller tilhører og steng den.
            GameCharacter chara = ManageCharacters.getGameCharacter(player.getCharacter().getCharacterID());
            chara.getKonto().setLocked(true);
            gameServer.MultiClient.kickPlayer(player.getCharacter().getCharacterID());

            System.out.println("OPCODE 0x21:ATTEMPT TO BUY INVALID AMOUNT. CHARACTER ID:" + player.getCharacter().getCharacterID() + " ANTALL:" + antall + " ITEM ID:" + item_id);
            System.out.println("OPCODE 0x21:ACCOUNT CLOSED. ACCOUNT ID:" + chara.getKonto().getAccountID());
            return;
        }

        //Sjekk at kjøpt item er gyldig.
        validItems.GameItem vare = validItems.ItemList.getItem(item_id);
        if (vare == null) {
            System.out.println("OPCODE 0x21: ATTEMPT TO BUY INVALID ITEM. CHARACTER ID:" + character_id + " ITEM ID:" + item_id);

            //Ugyldig item. Send feil tilbake.
            PacketData svar_feil = new PacketData();
            svar_feil.writeIntBE(buy_type);
            Packet svar_feil_pakke = new Packet(0x8021, svar_feil.getData());
            p.sendPacket(svar_feil_pakke);
            return;
        }

        //Sjekk at item faktisk kan kjøpes i butikk.
        if (!vare.canBuy() && (buy_type == 0x00010000 || buy_type == 0x00020000)) {
            System.out.println("OPCODE 0x21: ATTEMPT TO BUY INVALID ITEM. CHARACTER ID:" + character_id + " ITEM ID:" + item_id);

            //Ugyldig item. Send feil tilbake.
            PacketData svar_feil = new PacketData();
            svar_feil.writeIntBE(buy_type);
            Packet svar_feil_pakke = new Packet(0x8021, svar_feil.getData());
            p.sendPacket(svar_feil_pakke);
            return;
        }

        pd.readIntBE();
        pd.readIntBE();

        int cityID = pd.readIntBE(); //By item kjøpes i. 0x3A/0x3B=Richmond/Newman

        pd.readIntBE(); //Shop ID.
        pd.readIntBE();
        pd.readIntBE();
        pd.readIntBE();

        pd.readByte();
        int color = (int) pd.readByte(); //Farge hvis kjøp av klær.

        //Regn ut pris for kjøpt item. Hvis kjøpt i Newman/Richmond gi 10% rabatt.
        long varePris = 0; //Dette er pris pr stk for vare.

        if (cityID == 0x3A || cityID == 0x3B) {
            varePris = vare.getPrice() - Math.round(((double) vare.getPrice() / 100) * 10);
        } else {
            varePris = vare.getPrice();
        }

        long pris = antall * varePris;
        //Sjekk at vi har nok penger i kontanter eller bank og ta betalt.
        if (pris <= player.getCharacter().getMoneyContainer().getAntall()) {
            //Ta penger fra backpack.
            Container con = player.getCharacter().getMoneyContainer();
            con.settAntall(con.getAntall() - pris);
        } else if (pris <= player.getCharacter().getCreditContainer().getAntall()) {
            //Ta penger fra bank.
            Container con = player.getCharacter().getCreditContainer();
            con.settAntall(con.getAntall() - pris);
        } else {
            //FEIL. Ikke nok penger, dette skal ikke kunne skje fordi klienten sjekker dette på forhånd.
            //Kan tyde på feil i registrert item, eller modifiert klient.
            System.out.println("Opcode 0x21: invalid price for item " + item_id);
            return;
        }

        //Opprett ny item og plasser den i en item container.
        Item ting;
        ItemContainer ic = ContainerList.newItemContainer();

        //Sjekk om spiller har kjøpt MS/vehicle i butikk.
        if (vare.getItemType() == ItemType.Vehicle && (buy_type == 0x00010000 || buy_type == 0x00020000)) {
            ting = new Vehicle(item_id);
            ic.settContainerTail(0x14); //Tail må være 0x14 fordi vehicles inneholder flere containere.
        }//Sjekk om spiller har kjøpt /taxi eller /transport.
        else if (vare.getItemType() == ItemType.Vehicle && (buy_type == 0x00030000 || buy_type == 0x00040000)) {
            ting = new TaxiTransport(item_id);
            ic.settContainerTail(0x14);
        }//Sjekk om spiller har kjøpt våpen.
        else if (vare.getItemType() == ItemType.Weapon && (buy_type == 0x00010000 || buy_type == 0x00020000)) {
            ting = new Weapon(item_id);
            ic.settContainerTail(0x13);
        }//Sjekk om spilleren har kjøpt en general item.
        else if (vare.getItemType() == ItemType.General && (buy_type == 0x00010000 || buy_type == 0x00020000)) {
            ting = new GeneralItem(item_id);
            ic.settContainerTail(0x13);
        }//Sjekk om spiller har kjøpt klær.
        else if (vare.getItemType() == ItemType.Clothes && (buy_type == 0x00010000 || buy_type == 0x00020000)) {
            ting = new Clothing(item_id, color);
            ic.settContainerTail(0x13);
        }//Sjekk om spille har kjøpt camp.
        else if (vare.getItemType() == ItemType.Building) {
            ting = new Camp(item_id);
            ic.settContainerTail(0x13);
        } else {
            System.out.println("Opcode 0x21: Unsupported item type. Buytype = " + buy_type);
            ContainerList.removeContainer(ic.getID());
            helpers.PrintPacket.print(pakke);
            return;
        }

        //Plasser item i container.
        ic.addItem(ting);
        ic.settAntall(antall);

        //Sjekk om kjøpt item er stackable og om vi allerede har slike items i inventory.
        boolean finnes_allerede = false; //Indikerer om stackable item finnes fra før.
        if (vare.isStackable()) {

            Container con = ContainerList.getContainer(parent_container);

            if (con instanceof HovedContainer) {
                //Sjekk om containeren der item skal plasseres allerede inneholder item.
                HovedContainer hc = (HovedContainer) con;
                ItemContainer ic2 = hc.getItemContainer(item_id);
                if (ic2 != null) {
                    //Item finnes allerede og skal stackes.
                    ic2.settAntall(ic2.getAntall() + ic.getAntall()); //Sett rett antall items.
                    ContainerList.removeContainer(ic.getID()); //Fjern den nye containeren, trenger ikke den.
                    ic = ic2; //Bruker den gamle containeren.
                    finnes_allerede = true;
                }
            }
        }

        //Send svar tilbake.
        PacketData svar = new PacketData();

        //Sjekk om det er butikk kjøp eller taxi/transport.
        if (buy_type == 0x00010000 || buy_type == 0x00020000 && vare.canBuy()) {
            //Butikk kjøp.
            if (!finnes_allerede) {
                svar.writeIntBE(buy_type | 0x02000002);
            } else {
                svar.writeIntBE(buy_type | 0x01000002);
            }
        } else if (buy_type == 0x00030000 || buy_type == 0x00040000) {
            //Taxi/transport.
            svar.writeIntBE(buy_type | 0x02000002);
        } else {
            //Ugyldig/ikke støttet ennå.
            System.out.println("Opcode 0x21: invalid transaction: " + ((buy_type >> 16) & 0xFF));
            return;
        }

        svar.writeIntBE(character_id);

        svar.writeIntBE(ic.getID());
        svar.writeIntBE(ic.getContainerTail());

        svar.writeIntBE(parent_container);
        svar.writeIntBE(parent_container_tail);

        svar.writeLongBE(pris);

        svar.writeIntBE(item_id);

        svar.writeIntBE(0x1);
        svar.writeIntBE((int) antall);

        byte[] data = ic.getData();

        svar.writeByteArray(helpers.UCGOcounter.getCounter(data.length));
        svar.writeByteArray(data);

        Packet svar_pakke = new Packet(0x8021, svar.getData());

        p.sendPacket(svar_pakke);

        //Svar har blitt sendt til spiller. Dersom spilleren kjøpte en ms/vehicle eller taxi/transport oppdater
        //character objektet med denne informasjonen.
        if (vare.getItemType() == ItemType.Vehicle) {
            //Spiller kjøpte vehicle/transport.
            player.getCharacter().appearanceChange(); //Utseendet har blitt endret.

            if (ting instanceof Vehicle) {
                player.getCharacter().setVehicle((Vehicle) ting, ic);
            } else if (ting instanceof TaxiTransport) {
                player.getCharacter().setTaxiTransport((TaxiTransport) ting, ic);
            }
        }

        //Plasser kjøpt item i parent container, kun dersom den ikke finnes fra før.
        Container con = ContainerList.getContainer(parent_container);

        if (con instanceof HovedContainer && !finnes_allerede) {

            HovedContainer hc = (HovedContainer) con;
            hc.addItemContainer(ic);
        }

    }

}
