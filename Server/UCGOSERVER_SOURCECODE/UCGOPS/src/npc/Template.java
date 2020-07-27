package npc;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen representerer en NPC template.
 *
 * En NPC template holder info om hvordan en NPC er f.eks hitpoints, attack,
 * oppførsel, hvilken MS/vehicle o.l
 */
public class Template {

    /**
     * Item ID for MS/Vehicle NPC skal bruke.
     */
    private int vehicleID;

    /**
     * NPC sin rank.
     */
    private int rank;

    /**
     * Hitpoints til NPC.
     */
    private int hitpoints;

    /**
     * hitChance = NPC sin sjanse til å treffe et mål den angriper. dodgeChance
     * = NPC sin sjanse til å unngå å bli truffet når angrepet.
     */
    private int hitChance, dodgeChance;

    /**
     * walkSpeed = NPC sin gå hastighet, i game units pr sekund. runSpeed = NPC
     * sin løpe hastighet, i game units pr sekund.
     */
    private int walkSpeed, runSpeed;

    /**
     * Angir om NPC kan "strafe". Settes til false for ting som ikke er MS.
     */
    private boolean canStrafe;

    /**
     * Angir om NPC skal stå helt i ro eller ikke.
     *
     * Hvis true vil ikke NPC flytte på seg eller snu seg.
     */
    private boolean stationary = false;

    /**
     * Angir om NPC kan fly, brukt av f.eks Gaw BOSS NPC.
     */
    private boolean flyingType = false;

    /**
     * Dersom NPC kan fly er dette minimum høyde over bakken. Oppgitt i meter.
     */
    private int minAltitude = 0;

    /**
     * Dersom NPC kan fly er dette maximum høyde over bakken. Oppgitt i meter.
     */
    private int maxAltitude = 0;

    /**
     * Stats for NPC sitt første våpen. { Item ID, Attack power, Range, Rate of
     * fire }
     *
     * Hvis Item ID = 0 er ikke noe våpen utstyrt. Range er i meter. Rate of
     * fire = Antall sekunder mellom hver gang våpen kan brukes.
     */
    private int[] weaponA = new int[4];

    /**
     * Stats for NPC sitt andre våpen. Samme stats som for weaponA.
     */
    private int[] weaponB = new int[4];

    /**
     * Stats for shield NPC har utstyrt. { Item ID, Hitpoints, Block chance }
     *
     * Hvis Item ID = 0 er ikke noe shield utstyrt.
     */
    private int[] shield = new int[3];

    /**
     * ER kits NPC har utstyrt. { Antall ER kits, Repair % pr kit }
     */
    private int[] erKit = new int[2];

    /**
     * Angir hvordan NPC skal oppføre seg.
     */
    private npcType behavior;

    /**
     * Angir sjansen, 0-100, for at en NPC kan i fra seg items når den blir
     * ødelagt.
     *
     * Default er 100 og skal kun endres dersom det skal være litt vanskeligere
     * å få items fra NPC.
     */
    private int dropChance = 100;

    /**
     * Holder en liste over hvilke items NPC kan slippe når den blir ødelagt.
     */
    private LinkedList<DropItem> dropList = new LinkedList<DropItem>();

    /**
     * Dersom vi er i ground zone vil dette feltet angi at vi skal ignorere
     * terrain heightmap når NPC flytter seg og avgjør om den kan skyte på et
     * mål.
     *
     * For bruk i områder som Newman/Richmond der heightmap i brukes.
     */
    private boolean ignoreHeightmap = false;

    /**
     * Dersom satt til verdi > 0 vil NPC regenerere oppgitt antall hitpoints
     * hver gang performAI() metoden kalles.
     */
    private int healing = 0;

    /**
     * Angir de forskjellige rolletypene (oppførsel) til NPC.
     */
    public enum npcType {

        Guard, MovingGuard, Supply, Grunt, Boss
    }

    /**
     *
     * @param vehicleID Item ID for MS/vehicle.
     * @param t Oppførsel til NPC.
     * @param rank NPC sin rank.
     */
    public Template(int vehicleID, npcType t, int rank) {

        this.vehicleID = vehicleID;
        this.behavior = t;
        this.rank = rank;
    }

    /**
     * Setter stats for NPC.
     *
     * @param hitpoints
     * @param attackPower
     * @param hitChance
     * @param dodgeChance
     */
    public void setCombatStats(int hitpoints, int hitChance, int dodgeChance) {

        this.hitpoints = hitpoints;
        this.hitChance = hitChance;
        this.dodgeChance = dodgeChance;
    }

    /**
     * Setter hvor fort NPC can bevege seg og om den kan strafe.
     *
     * @param runSpeed
     * @param walkSpeed
     * @param turnSpeed
     * @param canStrafe
     */
    public void setMobility(int runSpeed, int walkSpeed, boolean canStrafe) {

        this.runSpeed = runSpeed;
        this.walkSpeed = walkSpeed;
        this.canStrafe = canStrafe;
    }

    /**
     * Setter stats for NPC sitt første våpen.
     *
     * @param itemID
     * @param atkPwr
     * @param range
     * @param rof
     */
    public void setWeaponA(int itemID, int atkPwr, int range, int rof) {

        this.weaponA[0] = itemID;
        this.weaponA[1] = atkPwr;
        this.weaponA[2] = range;
        this.weaponA[3] = rof;
    }

    /**
     * Setter stats for NPC sitt første våpen.
     *
     * @param itemID
     * @param atkPwr
     * @param range
     * @param rof
     */
    public void setWeaponB(int itemID, int atkPwr, int range, int rof) {

        this.weaponB[0] = itemID;
        this.weaponB[1] = atkPwr;
        this.weaponB[2] = range;
        this.weaponB[3] = rof;
    }

    /**
     * Setter stats for NPC sitt shield.
     *
     * @param itemID
     * @param hitpoints
     * @param blockChance
     */
    public void setShield(int itemID, int hitpoints, int blockChance) {

        this.shield[0] = itemID;
        this.shield[1] = hitpoints;
        this.shield[2] = blockChance;
    }

    /**
     * Setter stats for NPC sine ER kits.
     *
     * @param antall
     * @param repair Repair %
     */
    public void setErKit(int antall, int repair) {

        this.erKit[0] = antall;
        this.erKit[1] = repair;
    }

    /**
     *
     * @param flyingType Angir om NPC kan fly eller ikke.
     * @param minAltitude Minimum høyde.
     * @param maxAltitude Maximum høyde.
     */
    public void setFlying(boolean flyingType, int minAltitude, int maxAltitude) {

        this.flyingType = flyingType;
        this.minAltitude = minAltitude;
        this.maxAltitude = maxAltitude;
    }

    /**
     *
     * @return true hvis NPC kan fly.
     */
    public boolean isFlyingType() {
        return this.flyingType;
    }

    /**
     *
     * @return Minimum høyde over bakken hvis NPC kan fly.
     */
    public int getMinAltitude() {
        return this.minAltitude;
    }

    /**
     *
     * @return Maximum høyde over bakken hvis NPC kan fly.
     */
    public int getMaxAltitude() {
        return this.maxAltitude;
    }

    /**
     * Setter sjansen for at NPC skal slippe items.
     *
     * @param dropChance
     */
    public void setDropChance(int dropChance) {

        this.dropChance = dropChance;
    }

    public int getDropChance() {

        return this.dropChance;
    }

    /**
     * Legger til en item som NPC kan slippe.
     *
     * @param item Item NPC kan slippe.
     */
    public void addDropItem(DropItem item) {

        this.dropList.add(item);
    }

    /**
     *
     * @return Iterator over alle items NPC kan slippe.
     */
    public Iterator<DropItem> getDropItems() {

        return this.dropList.iterator();
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public int getRank() {
        return rank;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public int getHitChance() {
        return hitChance;
    }

    public int getDodgeChance() {
        return dodgeChance;
    }

    public int getWalkSpeed() {
        return walkSpeed;
    }

    public int getRunSpeed() {
        return runSpeed;
    }

    public boolean getCanStrafe() {
        return this.canStrafe;
    }

    public void setStationary(boolean stationary) {
        this.stationary = stationary;
    }

    public boolean isStationary() {
        return this.stationary;
    }

    public npcType getBehavior() {
        return behavior;
    }

    public int[] getWeaponA() {
        return weaponA;
    }

    public int[] getWeaponB() {
        return weaponB;
    }

    public int[] getShield() {
        return shield;
    }

    public int[] getErKit() {
        return erKit;
    }

    public boolean isIgnoreHeightmap() {
        return ignoreHeightmap;
    }

    public void setIgnoreHeightmap(boolean ignoreHeightmap) {
        this.ignoreHeightmap = ignoreHeightmap;
    }

    public int getHealing() {
        return healing;
    }

    public void setHealing(int healing) {
        this.healing = healing;
    }

}
