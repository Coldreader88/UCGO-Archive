package crafting.refining;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere resultatet av refining.
 *
 */
public class RefiningCraftingResult {

    /**
     * Indikerer om refining var vellykket eller ikke.
     */
    boolean success;

    /**
     * Item ID til materialene som skal returneres etter crafting.
     *
     * Hvis ID = 0 skal ikke noe materiale returneres.
     */
    private int materialIdA, materialIdB;

    /**
     * Hvor mye som returneres av hvert materiale.
     */
    private int materialAmountA, materialAmountB;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getMaterialIdA() {
        return materialIdA;
    }

    public void setMaterialIdA(int materialIdA) {
        this.materialIdA = materialIdA;
    }

    public int getMaterialIdB() {
        return materialIdB;
    }

    public void setMaterialIdB(int materialIdB) {
        this.materialIdB = materialIdB;
    }

    public int getMaterialAmountA() {
        return materialAmountA;
    }

    public void setMaterialAmountA(int materialAmountA) {
        this.materialAmountA = materialAmountA;
    }

    public int getMaterialAmountB() {
        return materialAmountB;
    }

    public void setMaterialAmountB(int materialAmountB) {
        this.materialAmountB = materialAmountB;
    }

}
