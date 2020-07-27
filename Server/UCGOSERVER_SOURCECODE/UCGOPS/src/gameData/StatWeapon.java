package gameData;

import java.util.LinkedList;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde stats for våpen og shields.
 *
 * block_chance brukes kun av shields. Et shield bruker kun feltene hitpoints og
 * block_chance.
 */
public class StatWeapon {

    /**
     * Attack power.
     */
    private int attack;
    /**
     * Treffsikkerhet.
     */
    private int accuracy;
    /**
     * Rekkevidde.
     */
    private int range;
    /**
     * Hitpoints til våpen (durability).
     */
    private int hitpoints;
    /**
     * Hvor mye ammo våpenet kan inneholde.
     */
    private int ammoClip;
    /**
     * Hvor mye ammo våpenet bruker pr skudd.
     */
    private int ammoUse;
    /**
     * Hvis dette er et shield, sjanse til å blokkere angrep.
     */
    private int blockChance;
    /**
     * Rate of fire for våpen oppgitt i millisekund.
     */
    private int rateOfFire;
    /**
     * Dette er en liste over ID nummere til MS/Vehicles. Et våpen kan være
     * "låst" til en spesifikk MS.
     *
     * Denne listen forteller hvilke MS/Vehicles et våpen kan brukes med. Dersom
     * tom vil server ikke sette noen begrensninger og dette håndteres da av
     * klienten.
     */
    private LinkedList<Integer> msLock = new LinkedList<Integer>();

    /**
     * Constructor for et våpen.
     *
     * @param attack
     * @param accuracy
     * @param range
     * @param hitpoints
     * @param ammo_clip
     * @param ammo_use
     */
    public StatWeapon(int attack, int accuracy, int range, int hitpoints, int ammo_clip, int ammo_use, int rof) {

        this.attack = attack;
        this.accuracy = accuracy;
        this.range = range;
        this.hitpoints = hitpoints;
        this.ammoClip = ammo_clip;
        this.ammoUse = ammo_use;
        rateOfFire = rof;
    }

    public StatWeapon(int attack, int accuracy, int range, int hitpoints, int ammo_clip, int ammo_use) {
        this(attack, accuracy, range, hitpoints, ammo_clip, ammo_use, 1000);
    }

    /**
     * Constructor for et våpen.
     *
     * @param hitpoints
     * @param block_chance
     */
    public StatWeapon(int hitpoints, int block_chance) {

        this.hitpoints = hitpoints;
        this.blockChance = block_chance;
    }

    /**
     * Setter at dette våpenet er låst til oppgitt MS/Vehicle.
     *
     * @param vehicleID ID til MS/Vehicle.
     */
    public void setMSlock(int vehicleID) {
        msLock.add(vehicleID);
    }

    /**
     * Sjekker om oppgitt MS/Vehicle can utstyre dette våpenet.
     *
     * @param vehicleID ID til MS/Vehicle.
     *
     * @return TRUE dersom våpenet IKKE er låst eller er låst til oppgitt
     * MS/Vehicle. FALSE dersom våpenet er låst til andre MS/Vehicle enn
     * oppgitt.
     */
    public boolean canEquip(int vehicleID) {
        if (msLock.size() == 0) {
            return true;
        }

        if (msLock.contains(vehicleID)) {
            return true;
        } else {
            return false;
        }
    }

    public int getAttack() {
        return attack;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getRange() {
        return range;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public int getAmmoClip() {
        return ammoClip;
    }

    public int getAmmoUse() {
        return ammoUse;
    }

    public int getBlockChance() {
        return blockChance;
    }

    public int getRateOfFire() {
        return rateOfFire;
    }
}
