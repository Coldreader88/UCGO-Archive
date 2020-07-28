package crafting.weapons;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen holder info om hva som kreves til crafting av et våpen.
 */
public class WeaponCraftingStat {

    /**
     * ID for materiale brukt til crafting.
     */
    private int materialID;

    /**
     * Hvor mye materiale som kreves.
     */
    private int materialAmount;

    /**
     * Hvor mye materiale som tapes dersom crafting mislykkes, oppgitt i
     * prosent.
     */
    private int materialLoss;

    /**
     * Crafting skill krevet for dette våpenet.
     */
    private int minSkill;

    /**
     * Item ID for EX versjonen av dette våpenet.
     */
    private int itemExID;

    /**
     * Angir hvor vanskelig det er å crafte våpenet.
     */
    private int difficulty;

    /**
     * Oppgitte parametere tilsvarer de private feltene.
     *
     * @param minSkill
     * @param difficulty
     * @param materialID
     * @param materialAmout
     * @param itemExID
     */
    public WeaponCraftingStat(int minSkill, int difficulty, int materialID, int materialAmount, int materialLoss, int itemExID) {

        this.materialID = materialID;
        this.materialAmount = materialAmount;
        this.materialLoss = materialLoss;
        this.minSkill = minSkill;
        this.itemExID = itemExID;
        this.difficulty = difficulty;
    }

    public int getMaterialID() {
        return materialID;
    }

    public int getMaterialAmount() {
        return materialAmount;
    }

    public int getMaterialLoss() {
        return materialLoss;
    }

    public int getMinSkill() {
        return minSkill;
    }

    public int getItemExID() {
        return itemExID;
    }

    public int getDifficulty() {
        return difficulty;
    }

}
