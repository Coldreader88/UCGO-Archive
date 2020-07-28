package npc;

import containers.ContainerList;
import containers.ItemContainer;
import items.*;
import java.util.Random;
import validItems.GameItem;
import validItems.ItemList;
import validItems.ItemType;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere en ting som kan slippes av en NPC.
 *
 */
public class DropItem {

    /**
     * ID til item som NPC kan slippe i fra seg.
     */
    private int itemID;

    /**
     * Sjansen for at NPC slipper den tingen, 0-100.
     */
    private int dropChance;

    /**
     * Dersom item er stackable er dette minste og største antall av item som
     * skal slippes.
     *
     * Et tilfeldig antall velges i intervallet min-max.
     */
    private int minAntall, maxAntall;

    /**
     * GameItem objekt for item som skal slippes.
     */
    private GameItem item;

    /**
     * Constructor for items som ikke er stackable.
     *
     * @param itemID
     * @param dropChance
     */
    public DropItem(int itemID, int dropChance) {

        this.itemID = itemID;
        this.dropChance = dropChance;

        this.item = ItemList.getItem(itemID);

        if (this.item == null) {
            System.out.println("DropItem.java ERROR: Invalid item ID: " + itemID);
        }
    }

    /**
     * Constructor for items som er stackable.
     *
     * @param itemID
     * @param dropChance
     * @param minAntall
     * @param maxAntall
     */
    public DropItem(int itemID, int dropChance, int minAntall, int maxAntall) {

        this(itemID, dropChance);

        this.minAntall = minAntall;
        this.maxAntall = maxAntall;
    }

    /**
     * Angir om denne tingen ble sluppet av NPC.
     *
     * Denne metoden velger et tilfeldig nummer 0-100 og dersom det er mindre
     * enn dropChance vil NPC slippe denne tingen.
     *
     * @return true hvis NPC slipper denne tingen, false hvis ikke.
     */
    public boolean isDropped() {

        Random r = new Random();

        int n = r.nextInt(100);

        if (n < this.dropChance) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returnerer en item container som inneholder item NPC skal slippe, antall
     * for item er også satt.
     *
     * Item Container er klar til å legges i wreckage container.
     *
     * @return Item Container som inneholder item NPC skal slippe eller NULL
     * hvis feil.
     */
    public ItemContainer getItem() {

        /*
		 * NPC kan slippe items av typen Weapons, Clothes og General.
         */
        Item i; //Holder item NPC slipper.

        if (this.item.getItemType() == ItemType.Weapon) {
            i = new Weapon(this.itemID);
        } else if (this.item.getItemType() == ItemType.Clothes) {
            i = new Clothing(this.itemID, 0);
        } else if (this.item.getItemType() == ItemType.General) {
            i = new GeneralItem(this.itemID);
        } else {
            //Feil type item.
            System.out.println("DropItem.java ERROR: Invalid item type. ID:" + this.itemID + " ItemType:" + this.item.getItemType());
            return null;
        }

        ItemContainer ic = ContainerList.newItemContainer();

        ic.addItem(i);

        //Sjekk om item er stackable eller ikke.
        if (this.item.isStackable()) {
            //Item er stackable, beregn antall av item NPC skal slippe.
            Random r = new Random();

            int n = r.nextInt(this.maxAntall);

            //Sørg at vi oppfyller kravet for minste antall av item.
            if (n < this.minAntall) {
                n = this.minAntall;
            }

            if (n <= 0) {
                n = 1;
            }

            ic.settAntall(n);
        } else {
            ic.settAntall(1);
        }

        return ic;
    }

}
