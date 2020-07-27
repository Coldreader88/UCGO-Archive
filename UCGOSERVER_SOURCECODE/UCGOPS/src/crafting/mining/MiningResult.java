package crafting.mining;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen holder et resultat av mining. Kun ETT resultat dvs en item og
 * antall av den.
 */
public class MiningResult {

    /**
     * Item ID
     */
    private int itemID;

    /**
     * Angir om item er stackable eller ikke.
     */
    private boolean isStackable;

    /**
     * Antall mottatt av item. SKAL være 1 dersom item ikke er stackable.
     */
    private int antall;

    /**
     *
     * @param itemID
     * @param isStackable
     * @param antall
     */
    public MiningResult(int itemID, boolean isStackable, int antall) {

        this.itemID = itemID;
        this.isStackable = isStackable;
        this.antall = antall;
    }

    public int getItemID() {
        return itemID;
    }

    public boolean isStackable() {
        return isStackable;
    }

    public int getAntall() {
        return antall;
    }

    public void setAntall(int antall) {
        this.antall = antall;
    }

}
