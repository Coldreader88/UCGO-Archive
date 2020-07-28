package characters;

import java.io.Serializable;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere en skill.
 */
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Skill level.
     */
    private int level;

    /**
     * Hvilken retning skill beveger seg i. 0=Øker, 1=Synker, 2=Stå i ro.
     */
    private int retning;

    /**
     * Max skill level.
     */
    private int maxLevel;

    /**
     * Oppretter ny skill satt til level 0 og skal øke.
     *
     * @param maxLevel Max skill level.
     */
    public Skill(int maxLevel) {
        this.level = 0;
        this.retning = 0;
        this.maxLevel = maxLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        //Sørg for at vi ikke går over max level.
        if (this.level > this.maxLevel) {
            this.level = this.maxLevel;
        }
    }

    public void forceSetLevel(int level) {
        this.level = level;
    }

    public int getRetning() {
        return retning;
    }

    public void setRetning(int retning) {
        this.retning = retning;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /**
     * Øker skill med oppgitt mengde, men ikke mer enn max level.
     *
     * @param amount Hvor mye skill skal økes.
     */
    public void growSkill(int amount) {
        this.level += amount;
        if (this.level > this.maxLevel) {
            this.level = this.maxLevel;
        }
    }

    /**
     * Minsker skill med oppgitt mengde.
     *
     * @param amount Hvor mye skill skal minskes.
     */
    public void decreaseSkill(int amount) {
        this.level -= amount;
        if (this.level < 0) {
            this.level = 0;
        }
    }
}
