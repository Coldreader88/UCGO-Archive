package gameData;

import java.util.HashMap;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde stats for MS/vehicles.
 */
public class StatMS {

    private int armor;
    private int dodge;
    private int critical;
    private int accuracy;
    //Påkrevd MS skill.
    private int skill;
    //Faction MS tilhører, 0x1=EF, 0x2=Zeon, -1=Tilhører både EF og Zeon.
    //Brukt til faction lock av MS.
    private int faction = -1;
    /**
     * Eventuell skill bonus MS kan få. Settes til ingen bonus som default.
     */
    private MsBonus msBonus = new MsBonus();
    /**
     * Dersom MS skal ha lengre visual radius enn normalt settes dette her.
     */
    private int visualRadius = 0;
    /**
     * Eventuell anti beam coating for MS. Dersom satt til en verdi større enn
     * null har MS anti beam coating.
     *
     * 40 = 40% anti beam coating bonus, 100 = 100% anti beam coating bonus.
     */
    private int antiBeamCoating = -1;

    /**
     * Brukes til å holde hvor mange % attackpower bonus MS/Vehicle får for et
     * spesielt våpen.
     *
     * Key = Item ID for våpen, Value = Antall % i bonus.
     */
    private HashMap<Integer, Integer> attackBonus = new HashMap<Integer, Integer>();
    //Default engine når kjøpes i butikk, item ID.
    private int defaultEngine;

    /**
     *
     * @param armor Armor stat
     * @param dodge Dodge stat
     * @param critical Critical stat
     * @param accuracy Accuracy stat
     * @param bonus MS bonus MS kan få.
     */
    public StatMS(int armor, int dodge, int critical, int accuracy, int skill, MsBonus bonus) {

        this.armor = armor;
        this.dodge = dodge;
        this.critical = critical;
        this.accuracy = accuracy;
        this.skill = skill;
        this.msBonus = bonus;
    }

    /**
     * @param faction Faction MS tilhører, 1=EF, 2=Zeon, -1=Ingen faction lock.
     * @param armor Armor stat
     * @param dodge Dodge stat
     * @param critical Critical stat
     * @param accuracy Accuracy stat
     * @param engine Default engine når kjøpes i butikk.
     * @param skill Påkrevd skill.
     */
    public StatMS(int faction, int armor, int dodge, int critical, int accuracy, int engine, int skill) {

        this.faction = faction;
        this.armor = armor;
        this.dodge = dodge;
        this.critical = critical;
        this.accuracy = accuracy;
        this.defaultEngine = engine;
        this.skill = skill;
    }

    /**
     *
     * @param armor Armor stat
     * @param dodge Dodge stat
     * @param critical Critical stat
     * @param accuracy Accuracy stat
     * @param engine Default engine når kjøpes i butikk.
     * @param skill Påkrevd skill.
     * @param msBonus Skill bonus MS får.
     */
    public StatMS(int armor, int dodge, int critical, int accuracy, int engine, int skill, MsBonus msBonus) {

        this.armor = armor;
        this.dodge = dodge;
        this.critical = critical;
        this.accuracy = accuracy;
        this.defaultEngine = engine;
        this.skill = skill;
        this.msBonus = msBonus;
    }

    public int getSkill() {
        return skill;
    }

    public int getArmor() {
        return armor;
    }

    public int getDodge() {
        return dodge;
    }

    public int getCritical() {
        return critical;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public MsBonus getMsBonus() {
        return msBonus;
    }

    public int getDefaultEngine() {
        return defaultEngine;
    }

    public int getFaction() {
        return faction;
    }

    /**
     * Setter attack bonus for et våpen.
     *
     * @param wID Item ID for våpen.
     * @param n Antall % i bonus.
     *
     */
    public void setAttackBonus(int wID, int n) {
        this.attackBonus.put(wID, n);
    }

    /**
     * Returnerer attack power bonus for et våpen.
     *
     * @param wID Item ID for våpen.
     * @return Attack bonus i % MS skal få for dette våpenet.
     */
    public int getAttackBonus(int wID) {
        if (this.attackBonus.get(wID) != null) {
            return this.attackBonus.get(wID);
        } else {
            return 0;
        }
    }

    public int getVisualRadius() {
        return visualRadius;
    }

    public void setVisualRadius(int visualRadius) {
        this.visualRadius = visualRadius;
    }

    public void setAntiBeamCoating(int bonus) {
        this.antiBeamCoating = bonus;
    }

    public int getAntiBeamCoating() {
        return this.antiBeamCoating;
    }

    /**
     *
     * @return TRUE dersom MS har anti beam coating.
     */
    public boolean isAntiBeamCoating() {
        if (this.antiBeamCoating > 0) {
            return true;
        } else {
            return false;
        }
    }
}
