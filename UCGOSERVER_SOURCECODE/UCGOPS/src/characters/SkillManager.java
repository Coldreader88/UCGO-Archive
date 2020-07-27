package characters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å håndtere alle skills og character attributes for
 * en character.
 */
public class SkillManager implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Maks grense for en combat eller crafting skill.
     */
    private int maxSkillLevel = config.Server.maxSkillLevel;

    /**
     * Max grense for en character attribute.
     */
    private int maxCharacterAttributeLevel = config.Server.maxCharacterAttributePoints;

    /**
     * Maks grense for totalt antall skillpoints.
     */
    private int maxTotalSkillPoints = config.Server.maxSkillPoints;

    /**
     * Holder alle character attributes. Key=Skill ID
     */
    private HashMap<Integer, Skill> characterAttributes = new HashMap<Integer, Skill>();

    /**
     * Holder alle combat skills for character. Key=Skill ID
     */
    private HashMap<Integer, Skill> combatSkills = new HashMap<Integer, Skill>();

    /**
     * Holder alle crafting skills for character. Key=Skill ID
     */
    private HashMap<Integer, Skill> craftingSkills = new HashMap<Integer, Skill>();

    public SkillManager() {

        //Opprett nye attributes for character.
        this.characterAttributes.put(SkillList.Strength.key(), new Skill(this.maxCharacterAttributeLevel));
        this.characterAttributes.put(SkillList.Spirit.key(), new Skill(this.maxCharacterAttributeLevel));
        this.characterAttributes.put(SkillList.Luck.key(), new Skill(this.maxCharacterAttributeLevel));

        //Opprett nye combat skills for character.
        this.combatSkills.put(SkillList.MobileSuit.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.Critical.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.NearDodge.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.RangedEngagement.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.Engagement.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.NearEngagement.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.Shooting.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.Sniping.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.CQB.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.Defence.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.WeaponManipulation.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.HandToHandCombat.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.Tactics.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.ShellFiringWeapon.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.BeamCartridgeWeapon.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.AMBAC.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.FarDodge.key(), new Skill(this.maxSkillLevel));
        this.combatSkills.put(SkillList.EmergencyRepair.key(), new Skill(this.maxSkillLevel));

        //Opprett nye crafting skills for character.
        this.craftingSkills.put(SkillList.Refinery.key(), new Skill(this.maxSkillLevel));
        this.craftingSkills.put(SkillList.MSMAConstruction.key(), new Skill(this.maxSkillLevel));
        this.craftingSkills.put(SkillList.BattleshipConstruction.key(), new Skill(this.maxSkillLevel));
        this.craftingSkills.put(SkillList.ArmsConstruction.key(), new Skill(this.maxSkillLevel));
        this.craftingSkills.put(SkillList.Mining.key(), new Skill(this.maxSkillLevel));
        this.craftingSkills.put(SkillList.ClothingManufacturing.key(), new Skill(this.maxSkillLevel));

    }

    /**
     * Returnerer skill level for en character attribute.
     *
     * @param id Skill ID
     *
     * @return Level for oppgitt attribute, -1 hvis ugyldig.
     */
    public int getCharacterAttribute(int id) {

        Skill s = this.characterAttributes.get(id);
        if (s != null) {
            return s.getLevel();
        } else {
            return -1;
        }
    }

    /**
     * Returnerer skill level for en combat skill.
     *
     * @param id Skill ID
     *
     * @return Level for oppgitt skill, -1 hvis ugyldig.
     */
    public int getCombatSkill(int id) {
        Skill s = this.combatSkills.get(id);
        if (s != null) {
            return s.getLevel();
        } else {
            return -1;
        }
    }

    /**
     * Returnerer skill level for en crafting skill.
     *
     * @param id Skill ID
     *
     * @return Level for oppgitt skill, -1 hvis ugyldig.
     */
    public int getCraftingSkill(int id) {
        Skill s = this.craftingSkills.get(id);
        if (s != null) {
            return s.getLevel();
        } else {
            return -1;
        }
    }

    /**
     * Setter en character attribute til oppgitt level.
     *
     * @param id Skill ID.
     * @param level Skill level.
     */
    public void setCharacterAttribute(int id, int level) {
        Skill s = this.characterAttributes.get(id);
        if (s != null) {
            s.setLevel(level);
        }
    }

    /**
     * Setter en combat skill til oppgitt level.
     *
     * @param id Skill ID.
     * @param level Skill level.
     */
    public void setCombatSkill(int id, int level) {
        Skill s = this.combatSkills.get(id);
        if (s != null) {
            s.setLevel(level);
        }
    }

    public void forceSetCombatSkill(int id, int level) {
        Skill s = this.combatSkills.get(id);
        if (s != null) {
            s.forceSetLevel(level);
        }
    }

    /**
     * Setter en crafting skill til oppgitt level.
     *
     * @param id Skill ID.
     * @param level Skill level.
     */
    public void setCraftingSkill(int id, int level) {
        Skill s = this.craftingSkills.get(id);
        if (s != null) {
            s.setLevel(level);
        }
    }

    /**
     * Setter retning en combat skill skal bevege seg i.
     *
     * @param id Skill ID.
     * @param retning Ny retning til skill.
     */
    public void setCombatSkillRetning(int id, int retning) {
        Skill s = this.combatSkills.get(id);
        if (s != null) {
            s.setRetning(retning);
        }
    }

    /**
     * Setter retning en crafting skill skal bevege seg i.
     *
     * @param id Skill ID.
     * @param retning Ny retning til skill.
     */
    public void setCraftingSkillRetning(int id, int retning) {
        Skill s = this.craftingSkills.get(id);
        if (s != null) {
            s.setRetning(retning);
        }
    }

    /**
     * Setter retning en character attribute skal bevege seg i.
     *
     * @param id Skill ID.
     * @param retning Ny retning til skill.
     */
    public void setCharacterAttributeRetning(int id, int retning) {
        Skill s = this.characterAttributes.get(id);
        if (s != null) {
            s.setRetning(retning);
        }
    }

    /**
     *
     * @param id Combat skill ID
     *
     * @return Retning til oppgitt skill, -1 hvis ugyldig skill.
     */
    public int getCombatSkillRetning(int id) {
        Skill s = this.combatSkills.get(id);
        if (s != null) {
            return s.getRetning();
        } else {
            return -1;
        }
    }

    /**
     *
     * @param id Crafting skill ID
     *
     * @return Retning til oppgitt skill, -1 hvis ugyldig skill.
     */
    public int getCraftingSkillRetning(int id) {
        Skill s = this.craftingSkills.get(id);
        if (s != null) {
            return s.getRetning();
        } else {
            return -1;
        }
    }

    /**
     *
     * @param id Character attribute skill ID
     *
     * @return Retning til oppgitt character attribute, -1 hvis ugyldig ID.
     */
    public int getCharacterAttributeRetning(int id) {
        Skill s = this.characterAttributes.get(id);
        if (s != null) {
            return s.getRetning();
        } else {
            return -1;
        }
    }

    /**
     *
     * @return Hvor mange skillpoints spiller har i combat skills.
     */
    public int getTotalCombatSkill() {

        Iterator<Skill> skills = this.combatSkills.values().iterator();

        int v = 0;

        while (skills.hasNext()) {
            v += skills.next().getLevel();
        }

        return v;
    }

    /**
     *
     * @return Hvor mange skillpoints spiller har i crafting skills.
     */
    public int getTotalCraftingSkill() {

        Iterator<Skill> skills = this.craftingSkills.values().iterator();

        int v = 0;

        while (skills.hasNext()) {
            v += skills.next().getLevel();
        }

        return v;
    }

    /**
     *
     * @return Hvor mange skillpoints spiller har i character attributes.
     */
    public int getTotalCharacterAttributes() {

        Iterator<Skill> skills = this.characterAttributes.values().iterator();

        int v = 0;

        while (skills.hasNext()) {
            v += skills.next().getLevel();
        }

        return v;
    }

    /**
     *
     * @param ignoreSkillID Dersom en skill har oppgitt ID vil den ikke bli
     * inkludert, -1=Inkluder alle skills.
     *
     * @return ArrayList som inneholder skill ID for alle combat skills som kan
     * synke (retning).
     */
    public ArrayList<Integer> getCombatSkillReducible(int ignoreSkillID) {

        ArrayList<Integer> skillList = new ArrayList<Integer>();

        Iterator<Integer> skills;

        skills = this.combatSkills.keySet().iterator();
        while (skills.hasNext()) {

            int skillID = skills.next();

            Skill s = this.combatSkills.get(skillID);

            if (s.getRetning() == 1 && skillID != ignoreSkillID && s.getLevel() > 0) {
                skillList.add(skillID); //1=skill synker.
            }
        }

        //SkillList inneholder nå alle combat skills som kan synke.
        return skillList;
    }

    /**
     *
     * @param ignoreSkillID Dersom en skill har oppgitt ID vil den ikke bli
     * inkludert, -1=Inkluder alle skills.
     *
     * @return ArrayList som inneholder skill ID for alle crafting skills som
     * kan synke (retning).
     */
    public ArrayList<Integer> getCraftingSkillReducible(int ignoreSkillID) {

        ArrayList<Integer> skillList = new ArrayList<Integer>();

        Iterator<Integer> skills;

        skills = this.craftingSkills.keySet().iterator();
        while (skills.hasNext()) {

            int skillID = skills.next();

            Skill s = this.craftingSkills.get(skillID);

            if (s.getRetning() == 1 && skillID != ignoreSkillID && s.getLevel() > 0) {
                skillList.add(skillID); //1=skill synker.
            }
        }

        //SkillList inneholder nå alle combat skills som kan synke.
        return skillList;
    }

    /**
     *
     * @param ignoreSkillID Dersom en skill har oppgitt ID vil den ikke bli
     * inkludert, -1=Inkluder alle skills.
     *
     * @return ArrayList som inneholder ALLE character attributes som kan synke
     * (retning).
     */
    public ArrayList<Integer> getCharacterAttributeReducible(int ignoreSkillID) {

        ArrayList<Integer> skillList = new ArrayList<Integer>();

        Iterator<Integer> skills;

        skills = this.characterAttributes.keySet().iterator();
        while (skills.hasNext()) {

            int skillID = skills.next();

            Skill s = this.characterAttributes.get(skillID);

            if (s.getRetning() == 1 && skillID != ignoreSkillID && s.getLevel() > 0) {
                skillList.add(skillID); //1=skill synker.
            }
        }

        //SkillList inneholder nå alle combat skills som kan synke.
        return skillList;
    }

    /**
     * Øker en skill med ett poeng. Forutsatt at maks grense for totale
     * skillpoeng og skill i seg selv ikke er nådd.
     *
     * @param id Skill ID for skill som skal økes.
     * @param isCombatSkill true=Skill er combat skill, false=crafting skill.
     *
     * @return true dersom skill økte, false hvis ikke.
     */
    public synchronized boolean increaseSkill(int id, boolean isCombatSkill) {

        //Sjekk at character har flere skill points igjen som kan brukes.
        if (this.getTotalCombatSkill() + this.getTotalCraftingSkill() >= this.maxTotalSkillPoints) {
            return false;
        }
        if (isCombatSkill) {
            return this.increaseCombatSkill(id);
        } else {
            return this.increaseCraftingSkill(id);
        }
    }

    /**
     * Øker en combat skill med ett poeng, forutsatt at skill ikke har nådd maks
     * grense.
     *
     * @param id Skill ID for combat skill som skal økes.
     *
     * @return true hvis skill økte, false hvis ikke.
     */
    private boolean increaseCombatSkill(int id) {

        Skill s = this.combatSkills.get(id);

        if (s == null) {
            System.out.println("SkillManager.java: increaseCombatSkill: Invalid skill.");
            return false;
        }

        if (s.getLevel() >= this.maxSkillLevel) {
            System.out.println("SkillManager.java: increaseCombatSkill: Invalid skilllevel.");
            return false;
        }

        s.setLevel(s.getLevel() + 1);

        return true;
    }

    /**
     * Øker en crafting skill med ett poeng, forutsatt at skill ikke har nådd
     * maks grense.
     *
     * @param id Skill ID for crafting skill som skal økes.
     *
     * @return true hvis skill økte, false hvis ikke.
     */
    private boolean increaseCraftingSkill(int id) {

        Skill s = this.craftingSkills.get(id);

        if (s == null) {
            return false;
        }

        if (s.getLevel() >= this.maxSkillLevel) {
            return false;
        }

        s.setLevel(s.getLevel() + 1);

        return true;
    }

    /**
     * Øker en character attribute med ett poeng, forutsatt at maks antall
     * skillpoints ikke er nådd.
     *
     * @param id Skill ID for character attribute som skal økes.
     *
     * @return true hvis attribute økte, false hvis ikke.
     */
    public synchronized boolean increaseCharacterAttribute(int id) {

        if (this.getTotalCharacterAttributes() >= this.maxCharacterAttributeLevel) {
            return false;
        }

        Skill s = this.characterAttributes.get(id);

        if (s == null) {
            return false;
        }

        s.setLevel(s.getLevel() + 1);

        return true;
    }

    /**
     * FIKS FOR BUG DER SKILLS KAN GÅ UNDER 0.
     */
    public void bugFix() {
        maxSkillLevel = 8000; //Var 9000 fra reset 2016. Hardcode max 800.0
        this.bugFix2(this.characterAttributes.values().iterator());
        this.bugFix2(this.combatSkills.values().iterator());
        this.bugFix2(this.craftingSkills.values().iterator());
    }

    /**
     * BUG FIX
     *
     * @param s Iterator over skills. Hvis skills er < 0 setter skill til 0.
     */
    private void bugFix2(Iterator<Skill> s) {

        while (s.hasNext()) {

            Skill sk = s.next();

            if (sk.getLevel() < 0) {
                sk.setLevel(0);
            }
        }
    }

}
