package crafting.mining;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere en ting som spilleren kan få via
 * mining. Klassen forteller kravene for å få tingen.
 */
public class MiningStat {

    /**
     * Item ID.
     */
    private int itemID;

    /**
     * Spillerens sjanse til å ikke få item.
     */
    private int sjanse;

    /**
     * I opcode 0x32 sendes en verdi som angir hvor bra et område i mine er.
     * 0x0-0x1E i vanlige mines. Richmond og Newman har høyere verdier.
     *
     * minMineArea = Hvor bra mine område må være for å få item, minimum verdi.
     * maxMineArea = Hvor bra et område kan være maksimalt. Brukes til å hindre
     * Richmond/Newman mining i å gi gemstones og fossils.
     */
    private int minMineArea, maxMineArea;

    /**
     * Angir hvor stort antall, maksimalt, en spiller kan mine av item i hver
     * omgang.
     */
    private int antall;

    /**
     * Angir om item er stackable eller ikke.
     */
    private boolean isStackable;

    /**
     * All params tilsvarer private felter.
     *
     * @param itemID
     * @param sjanse
     * @param minMineArea
     * @param maxMineArea
     * @param antall
     * @param isStackable
     */
    public MiningStat(int itemID, int sjanse, int minMineArea, int maxMineArea, int antall, boolean isStackable) {

        this.itemID = itemID;
        this.sjanse = sjanse;
        this.minMineArea = minMineArea;
        this.maxMineArea = maxMineArea;
        this.antall = antall;
        this.isStackable = isStackable;
    }

    public int getItemID() {
        return itemID;
    }

    public int getSjanse() {
        return sjanse;
    }

    public int getMinMineArea() {
        return minMineArea;
    }

    public int getMaxMineArea() {
        return maxMineArea;
    }

    public int getAntall() {
        return antall;
    }

    public boolean isStackable() {
        return isStackable;
    }

}
