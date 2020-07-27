package crafting.clothes;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen holder info om hva som kreves til crafting av klær.
 */
public class ClothesCraftingStat {

    /**
     * Hvor mye materiale som tapes dersom crafting mislykkes, oppgitt i
     * prosent.
     */
    private int materialLoss;

    /**
     * Crafting skill krevet for dette klesplagget.
     */
    private int minSkill;

    /**
     * Item ID for GQ versjonen av dette klesplagget, hvis 0 er ikke GQ versjon
     * tilgjengelig.
     */
    private int itemGQid;

    /**
     * Angir hvor vanskelig det er å crafte klesplagget.
     */
    private int difficulty;

    /**
     * Constructor for klær som ikke har GQ versjon.
     *
     * @param minSkill
     * @param difficulty
     * @param materialLoss
     */
    public ClothesCraftingStat(int minSkill, int difficulty, int materialLoss) {
        this.minSkill = minSkill;
        this.difficulty = difficulty;
        this.materialLoss = materialLoss;
    }

    /**
     * Contructor for klær som har GQ versjon.
     *
     * @param minSkill
     * @param difficulty
     * @param materialLoss
     * @param itemGQid
     * @param GQstrength
     * @param GQspirit
     * @param GQluck
     */
    public ClothesCraftingStat(int minSkill, int difficulty, int materialLoss, int itemGQid) {
        this.minSkill = minSkill;
        this.difficulty = difficulty;
        this.materialLoss = materialLoss;
        this.itemGQid = itemGQid;
    }

    public int getMaterialLoss() {
        return materialLoss;
    }

    public int getMinSkill() {
        return minSkill;
    }

    public int getItemGQid() {
        return itemGQid;
    }

    public int getDifficulty() {
        return difficulty;
    }

}
