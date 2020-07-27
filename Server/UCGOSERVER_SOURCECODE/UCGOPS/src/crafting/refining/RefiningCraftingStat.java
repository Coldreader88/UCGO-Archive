package crafting.refining;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen holder info om hva som kreves til crafting av materialer.
 */
public class RefiningCraftingStat {

    /**
     * ID for materiale brukt til crafting.
     *
     * Refining kan bruke ett eller to materialer. A=Første material, B=Andre
     * material hvis brukt.
     */
    private int materialIdA, materialIdB;

    /**
     * Hvor mye materiale som kreves for å lage 1stk av det nye materialet.
     */
    private int materialAmountA, materialAmountB;

    /**
     * Hvor mye materiale som tapes dersom crafting mislykkes.
     *
     * Dette er antall enheter tapt. Hvis f.eks 3stk kreves og tapet er 1,
     * returnes 2stk ved mislykket crafting. Gjelder for både material A og B.
     */
    private int materialLoss;

    /**
     * Crafting skill krevet for dette materialet.
     */
    private int minSkill;

    /**
     * Angir hvor vanskelig det er å crafte materialet.
     */
    private int difficulty;

    /**
     * Oppgitte parametre tilsvarer private felter.
     *
     * For refining som bruker ett materiale.
     *
     * @param materialIdA
     * @param materialAmountA
     * @param materialLoss
     * @param minSkill
     * @param difficulty
     */
    public RefiningCraftingStat(int materialIdA, int materialAmountA, int materialLoss, int minSkill, int difficulty) {

        this.materialIdA = materialIdA;
        this.materialAmountA = materialAmountA;
        this.materialLoss = materialLoss;
        this.minSkill = minSkill;
        this.difficulty = difficulty;
    }

    /**
     * Oppgitte parametre tilsvarer private felter.
     *
     * For refining som bruker to materialer.
     *
     * @param materialIdA
     * @param materialAmountA
     * @param materialIdB
     * @param materialAmountB
     * @param materialLoss
     * @param minSkill
     * @param difficulty
     */
    public RefiningCraftingStat(int materialIdA, int materialAmountA, int materialIdB, int materialAmountB, int materialLoss, int minSkill, int difficulty) {

        this.materialIdA = materialIdA;
        this.materialAmountA = materialAmountA;
        this.materialIdB = materialIdB;
        this.materialAmountB = materialAmountB;
        this.materialLoss = materialLoss;
        this.minSkill = minSkill;
        this.difficulty = difficulty;
    }

    public int getMaterialIdA() {
        return materialIdA;
    }

    public int getMaterialIdB() {
        return materialIdB;
    }

    public int getMaterialAmountA() {
        return materialAmountA;
    }

    public int getMaterialAmountB() {
        return materialAmountB;
    }

    public int getMaterialLoss() {
        return materialLoss;
    }

    public int getMinSkill() {
        return minSkill;
    }

    public int getDifficulty() {
        return difficulty;
    }

}
