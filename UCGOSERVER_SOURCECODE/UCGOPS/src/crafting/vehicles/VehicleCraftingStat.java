package crafting.vehicles;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dennne klassen inneholder info om hva som kreves for crafting av en
 * MS/vehicle.
 *
 * NB! Alle MS/Vehicle krever enten ett materiale+engine eller MS+upgrade part.
 *
 */
public class VehicleCraftingStat {

    /**
     * Hvor lang tid, i sekunder, det tar å lage vehicle i factory.
     */
    private int timeUsed;

    /**
     * Item ID til materialet MS/Vehicle lages av.
     *
     * Dette er også materiale returnert ved dismantle.
     */
    private int materialID;

    /**
     * Dersom dette er stat for MS/Vehicle som skal oppgraderes (G3/FA)
     * inneholder dette feltet ID for MS/Vehicle som skal brukes til
     * oppgradering.
     */
    private int upgradeVehicleID;

    /**
     * Item ID til eventuell oppgraderings del, FA Parts, Magnetic Coating.
     */
    private int upgradePartID;

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
     * Crafting skill krevet for MS/vehicle.
     */
    private int minSkill;

    /**
     * Angir hvor vanskelig det er å crafte MS/Vehicle.
     */
    private int difficulty;

    /**
     * Angir maksimal sjanse for vellykket crafting en spiller kan oppnå.
     */
    private int maxSuccess;

    /**
     * Constructor for MS/Vehicle som bruker material+engine og ingen upgrade
     * parts.
     *
     * @param minSkill
     * @param difficulty
     * @param maxSuccess
     * @param materialID
     * @param materialAmount
     * @param materialLoss
     */
    public VehicleCraftingStat(int minSkill, int difficulty, int maxSuccess, int materialID, int materialAmount, int materialLoss) {

        this.materialID = materialID;
        this.materialAmount = materialAmount;
        this.materialLoss = materialLoss;
        this.minSkill = minSkill;
        this.difficulty = difficulty;
        this.maxSuccess = maxSuccess;
    }

    /**
     * Constructor for MS/Vehicle som bruker material+engine og ingen upgrade
     * parts.
     *
     * @param timeUsed
     * @param minSkill
     * @param difficulty
     * @param maxSuccess
     * @param materialID
     * @param materialAmount
     * @param materialLoss
     */
    public VehicleCraftingStat(int timeUsed, int minSkill, int difficulty, int maxSuccess, int materialID, int materialAmount, int materialLoss) {

        this.timeUsed = timeUsed;
        this.materialID = materialID;
        this.materialAmount = materialAmount;
        this.materialLoss = materialLoss;
        this.minSkill = minSkill;
        this.difficulty = difficulty;
        this.maxSuccess = maxSuccess;
    }

    /**
     * Constructor for MS/Vehicle som bruker MS+upgrade part.
     *
     * @param timeUsed
     * @param minSkill
     * @param difficulty
     * @parma maxSuccess
     * @param materialID
     * @param materialAmount
     * @param materialLoss
     * @param upgradeVehicleID
     * @param upgradePartID
     */
    public VehicleCraftingStat(int timeUsed, int minSkill, int difficulty, int maxSuccess, int materialID, int materialAmount, int materialLoss, int upgradeVehicleID, int upgradePartID) {

        this.timeUsed = timeUsed;
        this.materialID = materialID;
        this.materialAmount = materialAmount;
        this.materialLoss = materialLoss;
        this.minSkill = minSkill;
        this.difficulty = difficulty;
        this.maxSuccess = maxSuccess;
        this.upgradeVehicleID = upgradeVehicleID;
        this.upgradePartID = upgradePartID;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public int getMaterialID() {
        return materialID;
    }

    public int getUpgradeVehicleID() {
        return upgradeVehicleID;
    }

    public int getUpgradePartID() {
        return upgradePartID;
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

    public int getDifficulty() {
        return difficulty;
    }

    public int getMaxSuccess() {
        return maxSuccess;
    }

}
