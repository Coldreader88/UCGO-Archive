package crafting.clothes;

/**
 *
 * @author UCGOSERVER.COM
 *
 *
 * Denne klassen brukes til å representere resultatet etter clothes crafting.
 */
public class ClothesCraftingResult {

    /**
     * Resultatet av crafting.
     *
     * true = vellykket.
     */
    private boolean success;

    /**
     * Dersom crafting var vellykket indikerer dette feltet om vi fikk GQ
     * versjon.
     *
     * true = GQ versjon.
     */
    private boolean GQ;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isGQ() {
        return GQ;
    }

    public void setGQ(boolean gQ) {
        GQ = gQ;
    }

}
