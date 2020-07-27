package gameServer;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere resultatet når et ER eller MR kit
 * brukes.
 */
public class RepairResult {

    /**
     * TRUE = Bruk av ER/MR kit gikk OK.
     */
    private boolean repairOK = false;

    /**
     * Antall hitpoints som ble reparert.
     */
    private int repairHitpoints = 0;

    public boolean isRepairOK() {
        return repairOK;
    }

    public void setRepairOK(boolean repairOK) {
        this.repairOK = repairOK;
    }

    public int getRepairHitpoints() {
        return repairHitpoints;
    }

    public void setRepairHitpoints(int repairHitpoints) {
        this.repairHitpoints = repairHitpoints;
    }

}
