package characters;

import containers.*;
import gameData.*;
import items.*;
import java.util.Iterator;
import packetHandler.PacketData;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen holder orden på hvilke klær en character har på seg.
 *
 */
public class Clothing {

    //Dette er link tilbake til character som "eier" disse klærene.
    private CharacterGame character;

    /**
     * Alle klær en character har på seg lagres her. Hvert element tilsvarer en
     * slot i fitting room. [0] = vehicle spilleren er i.
     */
    private ItemContainer[] slots = new ItemContainer[9];

    public Clothing(CharacterGame character) {
        this.character = character;
    }

    /**
     * Utstyrer character med en item i oppgitt slot, eller fjerner item fra
     * oppgitt slot.
     *
     * Når item skal utstyres forutsettes det at den finnes i Backpack, den vil
     * da bli fjernet derfra. Når item skal fjernes fra slot flyttes den til
     * Backpack. Ved utstyring av klær forutsettes det at slot er tom.
     *
     * @param item Item container som inneholder item vi skal utstyre. NULL hvis
     * item skal fjernes.
     * @param slot Hvilken slot som skal brukes.
     */
    public void equipItem(ItemContainer item, int slot) {

        //Sjekk at slot er gyldig.
        if (slot < 0 || slot > this.slots.length) {
            return;
        }

        if (item != null && slot != 0) {
            //Klær skal utstyres. Hent fra Backpack.
            //Sjekk at item finnes i Backpack.
            if (!this.character.getBackpackContainer().containsItemContainer(item)) {
                return; //Item finnes ikke i Backpack.
            }
            this.character.getBackpackContainer().removeItemContainer(item);
            this.slots[slot] = item;
        } else if (item != null && slot == 0) {
            //Spilleren har gått inn ms/vehicle. Alltid ms/vehicle i slot 0.
            this.slots[0] = item;
        } else if (item == null && slot != 0) {
            //Klær skal fjernes fra slot. Flytt til Backpack.
            //Sjekk først at det finnes en item i slot.
            if (this.slots[slot] == null) {
                return;
            }

            this.character.getBackpackContainer().addItemContainer(this.slots[slot]);
            this.slots[slot] = null;
        } else if (item == null && slot == 0) {
            //MS/Vehicle skal fjernes.
            this.slots[0] = null;
        }
    }

    /**
     * Alle items utstyrt vil bli lagret i Weared containeren. En item container
     * for hver slot vil bli lagret, slots som er tomme vil få en tom
     * ItemContainer. ItemContainere lagres i slot rekkefølge.
     *
     * Tidligere innhold i Weared container blir fjernet.
     */
    public void saveWearedContainer() {

        this.character.getWearedContainer().emptyContainer();

        for (int c = 0; c < this.slots.length; c++) {
            if (this.slots[c] != null) {
                this.character.getWearedContainer().addItemContainer(this.slots[c]);
            } else {
                //Character har ingen ting utstyrt i slot. Sett inn tom dummy container i weared container.
                ItemContainer dummy_ic = new ItemContainer(0);
                dummy_ic.settAntall(0);
                dummy_ic.settContainerTail(0x0);
                this.character.getWearedContainer().addItemContainer(dummy_ic);
            }
        }

    }

    /**
     * Items lagret i Weared container blir flyttet inn i slots.
     */
    public void restoreWearedContainer() {

        ItemContainer ic;

        Iterator<ItemContainer> items = this.character.getWearedContainer().getContents().iterator();

        for (int c = 0; c < this.slots.length; c++) {

            if (items.hasNext()) {
                //Hent ut ItemContainer og sjekk om den inneholder en item.
                ic = items.next();
                if (ic.getID() != 0) {
                    this.slots[c] = ic;
                } else {
                    this.slots[c] = null;
                }
            } else {
                //Ikke flere ItemContainere. Sett slot til tom.
                this.slots[c] = null;
            }
        }

        //Dersom vehicle i slot 0 er en TaxiTransport fjern den. TaxiTransport må lagres i weared container
        //ellers vil ikke klienten fungere rett, men den må fjernes igjen senere.
        if (this.slots[0] != null) {
            if (this.slots[0].getItem() instanceof TaxiTransport) {
                this.slots[0] = null;
            }
        }

    }

    /**
     *
     * @return Strength bonus spiller får fra GQ klær.
     */
    public int getGQstrength() {

        int bonus = 0;

        for (int i = 0; i < this.slots.length; i++) {

            if (this.slots[i] != null) {
                //Hent ut stat for klesplagget.
                int itemID = this.slots[i].getItem().getID();

                StatGQclothes gqStat = StatManager.getGQclothesStat(itemID);

                //Hvis GQ stat finnes legg til bonus.
                if (gqStat != null) {
                    bonus += gqStat.getStrength();
                }
            }
        }

        return bonus;
    }

    /**
     *
     * @return Spirit bonus spiller får fra GQ klær.
     */
    public int getGQspirit() {

        int bonus = 0;

        for (int i = 0; i < this.slots.length; i++) {

            if (this.slots[i] != null) {
                //Hent ut stat for klesplagget.
                int itemID = this.slots[i].getItem().getID();

                StatGQclothes gqStat = StatManager.getGQclothesStat(itemID);

                //Hvis GQ stat finnes legg til bonus.
                if (gqStat != null) {
                    bonus += gqStat.getSpirit();
                }
            }
        }

        return bonus;
    }

    /**
     *
     * @return Luck bonus spiller får fra GQ klær.
     */
    public int getGQluck() {

        int bonus = 0;

        for (int i = 0; i < this.slots.length; i++) {

            if (this.slots[i] != null) {
                //Hent ut stat for klesplagget.
                int itemID = this.slots[i].getItem().getID();

                StatGQclothes gqStat = StatManager.getGQclothesStat(itemID);

                //Hvis GQ stat finnes legg til bonus.
                if (gqStat != null) {
                    bonus += gqStat.getLuck();
                }
            }
        }

        return bonus;
    }

    /**
     * Returnerer byte array som kan settes rett inn i pakker. Forteller hvilke
     * klær character har på seg.
     *
     * @return Kles data som kan settes rett inn i pakker. 6 INTs, 24 Byte.
     */
    public byte[] getData() {

        PacketData pd = new PacketData();

        pd.writeByte((byte) 0);

        //Uniform.
        if (slots[1] != null && slots[1].getItem() instanceof items.Clothing) {
            pd.writeByte(gameData.ClothingLookID.getLookID(slots[1].getItem().getID()));
            items.Clothing i = (items.Clothing) slots[1].getItem();
            pd.writeByte((byte) i.getColor());
        } else {
            pd.writeShortBE((short) -1);
        }

        pd.writeByte((byte) 0);

        //Skjorte/top.
        if (slots[2] != null && slots[2].getItem() instanceof items.Clothing) {
            pd.writeByte(gameData.ClothingLookID.getLookID(slots[2].getItem().getID()));
            items.Clothing i = (items.Clothing) slots[2].getItem();
            pd.writeByte((byte) i.getColor());
        } else {
            pd.writeShortBE((short) -1);
        }

        pd.writeByte((byte) 0);

        //Jakke.
        if (slots[3] != null && slots[3].getItem() instanceof items.Clothing) {
            pd.writeByte(gameData.ClothingLookID.getLookID(slots[3].getItem().getID()));
            items.Clothing i = (items.Clothing) slots[3].getItem();
            pd.writeByte((byte) i.getColor());
        } else {
            pd.writeShortBE((short) -1);
        }

        pd.writeByte((byte) 0);

        //Bukser.
        if (slots[4] != null && slots[4].getItem() instanceof items.Clothing) {
            pd.writeByte(gameData.ClothingLookID.getLookID(slots[4].getItem().getID()));
            items.Clothing i = (items.Clothing) slots[4].getItem();
            pd.writeByte((byte) i.getColor());
        } else {
            pd.writeShortBE((short) -1);
        }

        pd.writeByte((byte) 0);

        //Sko.
        if (slots[5] != null && slots[5].getItem() instanceof items.Clothing) {
            pd.writeByte(gameData.ClothingLookID.getLookID(slots[5].getItem().getID()));
            items.Clothing i = (items.Clothing) slots[5].getItem();
            pd.writeByte((byte) i.getColor());
        } else {
            pd.writeShortBE((short) -1);
        }

        pd.writeByte((byte) 0);

        //Hansker.
        if (slots[6] != null && slots[6].getItem() instanceof items.Clothing) {
            pd.writeByte(gameData.ClothingLookID.getLookID(slots[6].getItem().getID()));
            items.Clothing i = (items.Clothing) slots[6].getItem();
            pd.writeByte((byte) i.getColor());
        } else {
            pd.writeShortBE((short) -1);
        }

        pd.writeByte((byte) 0);

        //Hatt.
        if (slots[7] != null && slots[7].getItem() instanceof items.Clothing) {
            pd.writeByte(gameData.ClothingLookID.getLookID(slots[7].getItem().getID()));
            items.Clothing i = (items.Clothing) slots[7].getItem();
            pd.writeByte((byte) i.getColor());
        } else {
            pd.writeShortBE((short) -1);
        }

        pd.writeByte((byte) 0);

        //Briller.
        if (slots[8] != null && slots[8].getItem() instanceof items.Clothing) {
            pd.writeByte(gameData.ClothingLookID.getLookID(slots[8].getItem().getID()));
        } else {
            pd.writeByte((byte) -1);
        }

        pd.writeByte((byte) 0);

        return pd.getData();
    }
}
