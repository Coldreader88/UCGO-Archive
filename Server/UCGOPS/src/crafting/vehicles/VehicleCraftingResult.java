package crafting.vehicles;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere resultatet etter MS/Vehicle crafting.
 */
public class VehicleCraftingResult {

    /**
     * Resultatet av MS/vehicle crafting.
     *
     * true=vellykket.
     */
    private boolean success;

    /**
     * Item ID til resulterende item. Ved vellykket crafting er dette ID til
     * MS/vehicle, dersom mislykket er det ID til materialet som skal plasseres
     * i factory.
     */
    private int itemID;

    /**
     * Antall av resulterende item som skal plasseres i factory. Ved vellykket
     * crafting vil dette alltid være 1, men hvis mislykket er dette hvor mye av
     * brukt materiale som returneres.
     */
    private int antall;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getAntall() {
        return antall;
    }

    public void setAntall(int antall) {
        this.antall = antall;
    }

}
