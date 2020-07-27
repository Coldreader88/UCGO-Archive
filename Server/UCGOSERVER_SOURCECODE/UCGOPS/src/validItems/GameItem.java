package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å gi informasjon om en item i spillet. Den brukes
 * til å fortelle om en item basert på ID er gyldig og kan brukes, hvilken type
 * item det er og hvor mye den koster.
 *
 */
public class GameItem {

    /**
     * Hvilken type item dette er.
     */
    private ItemType type;

    /**
     * Hvilken under kategori dette er.
     */
    private ItemSubType sub_type;

    /**
     * Indikerer om item kan kjøpes i spillet.
     */
    private boolean canBuy = false;

    /**
     * Indikerer om item kan bygges.
     */
    private boolean canCraft = false;

    /**
     * Indikerer om item kan selges.
     */
    private boolean canSell = false;

    /**
     * Hvor mye det koster å kjøpe item i butikk.
     */
    private long price = 0;

    /**
     * Hvor mye item selges for i butikk.
     */
    private long sell = 0;

    /**
     * Angir om item kan stackes.
     */
    private boolean stackable = false;

    /**
     * Oppgitte parametre tilsvarer feltene i klassen. For items som kan selges.
     *
     * @param type
     * @param subtype
     * @param canbuy
     * @param canCraft
     * @param price
     * @param sell
     */
    public GameItem(ItemType type, ItemSubType subtype, boolean canBuy, boolean canCraft, long price, long sell) {
        this.type = type;
        this.sub_type = subtype;
        this.canBuy = canBuy;
        this.canCraft = canCraft;
        this.price = price;
        this.sell = sell;
        this.canSell = true;
    }

    /**
     * Oppgitte parametre tilsvarer feltene i klassen. For items som kan selges.
     *
     * @param type
     * @param sub_type
     * @param canbuy
     * @param canCraft
     * @param price
     * @param sell
     * @param stackable
     */
    public GameItem(ItemType type, ItemSubType subtype, boolean canBuy, boolean canCraft, long price, long sell, boolean stackable) {
        this.type = type;
        this.sub_type = subtype;
        this.canBuy = canBuy;
        this.canCraft = canCraft;
        this.price = price;
        this.sell = sell;
        this.canSell = true;
        this.stackable = stackable;
    }

    /**
     * Oppgitte parametre tilsvarer feltene i klassen.
     *
     * @param type
     * @param canbuy
     * @param canCraft
     * @param price
     */
    public GameItem(ItemType type, ItemSubType subtype, boolean canBuy, boolean canCraft, long price) {
        this.type = type;
        this.sub_type = subtype;
        this.canBuy = canBuy;
        this.canCraft = canCraft;
        this.price = price;
    }

    /**
     * Oppgitte parametre tilsvarer feltene i klassen.
     *
     * @param type
     * @param sub_type
     * @param canbuy
     * @param canCraft
     * @param price
     * @param stackable
     */
    public GameItem(ItemType type, ItemSubType subtype, boolean canBuy, boolean canCraft, long price, boolean stackable) {
        this.type = type;
        this.sub_type = subtype;
        this.canBuy = canBuy;
        this.canCraft = canCraft;
        this.price = price;
        this.stackable = stackable;
    }

    public ItemType getItemType() {
        return this.type;
    }

    public ItemSubType getItemSubType() {
        return this.sub_type;
    }

    public boolean canBuy() {
        return this.canBuy;
    }

    public boolean canCraft() {
        return this.canCraft;
    }

    public boolean canSell() {
        return this.canSell;
    }

    public long getPrice() {
        return this.price;
    }

    public long getSellingPrice() {
        return this.sell;
    }

    public long getRepairPrice() {
        return this.price / 250; //Prisen for å reparere er ALLTID kjøpe pris delt på 250.
    }

    public boolean isStackable() {
        return this.stackable;
    }
}
