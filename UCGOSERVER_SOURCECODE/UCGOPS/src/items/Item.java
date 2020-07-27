package items;

import java.io.Serializable;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Alle items som kan legges i containere arver denne klassen.
 *
 */
public abstract class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID for item.
     */
    private int item_id;

    /**
     *
     * @param item_id ID for item.
     */
    public Item(int item_id) {
        this.item_id = item_id;
    }

    /**
     * Denne metoden returnerer item ID.
     *
     * @return Item ID.
     */
    public int getID() {
        return this.item_id;
    }

    /**
     * Denne metoden returnerer et byte array med pakke data angående denne
     * item-en.
     *
     * @return Data for item. Klar til å settes inn i pakke.
     */
    public abstract byte[] getData();
}
