package validItems;

import java.util.HashMap;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde oversikt over ALLE items som kan kjøpes,
 * bygges og brukes i spillet.
 *
 * Alle items som skal kunne brukes i spillet må registreres her.
 */
public class ItemList {

    //Liste over ALLE gyldige items lagres her. <ITEM ID, GameItem>
    private static HashMap<Integer, GameItem> item_liste = new HashMap<Integer, GameItem>();

    /**
     * Registrerer en item i listen.
     *
     * @param item_id ID nummer for item.
     * @param item GameItem objekt som skal registreres.
     */
    public static void registerItem(int item_id, GameItem item) {

        item_liste.put(item_id, item);
    }

    /**
     * Returnerer en item.
     *
     * @param item_id ID for item.
     *
     * @return GameItem objekt for oppgitt item ID. NULL hvis item ikke finnes,
     * DVS item ID er UGYLDIG.
     */
    public static GameItem getItem(int item_id) {

        return item_liste.get(item_id);
    }

    /**
     * Returnerer hvor mye det koster å reparere en item.
     *
     * @param item_id Item ID for item.
     *
     * @return Prisen det koster å reparere, hvis item ikke finnes eller ikke
     * kan repareres returneres 0.
     */
    public static long getRepairPrice(int item_id) {

        GameItem i = getItem(item_id);

        if (i == null) {
            return 0;
        }

        return i.getRepairPrice();
    }

}
