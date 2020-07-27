package gameData;

import characters.CharacterGame;
import characters.SkillList;
import characters.SkillManager;

/**
 * Denne klassen brukes til å fortelle hvilke skill bonuser en MS kan få og til
 * å beregne hvor mye bonus en spiller får.
 *
 * @author UCGOSERVER.COM
 *
 */
public class MsBonus {

    /**
     * Følgende felter avgjør om en MS kan få en bonus eller ikke. Felt navnene
     * tilsvarer bonus navnene i skills.doc dokumentet.
     */
    private boolean guncannonBonus;
    private boolean guncannonAceBonus;
    private boolean sniperBonus;
    private boolean marineLowEndBonus;
    private boolean marineHighEndBonus;
    private boolean domBonus;
    private boolean guntankBonus;
    private boolean gmLowEndBonus;
    private boolean gmHighEndBonus;
    private boolean zakuBonus;
    private boolean goufBonus;

    /**
     * @return Accuracy bonus i prosent, 1.0 = 0%, 2.0=100%.
     */
    public double getAccuracy(CharacterGame chara) {
        return calculateBonus(chara)[0];
    }

    /**
     * @return Dodge bonus i prosent, 1.0 = 0%, 2.0=100%.
     */
    public double getDodge(CharacterGame chara) {
        return calculateBonus(chara)[1];
    }

    /**
     * @return Critical bonus i prosent, 1.0 = 0%, 2.0=100%.
     */
    public double getCritical(CharacterGame chara) {
        return calculateBonus(chara)[2];
    }

    /**
     * Beregner hvor mye bonus en spiller skal få.
     *
     * @param chara Character dette gjelder.
     * @return Array der [0]=Accuracy bonus, [1]=Dodge bonus og [2]=Critical
     * bonus. Bonus verdiene er fra 1.0 og oppover der 1.0=Ingen bonus, 2.0=100%
     * bonus.
     */
    private double[] calculateBonus(CharacterGame chara) {

        //Skill bonus lagres her.
        double[] bonus = {1.0, 1.0, 1.0};

        //Hent ut spiller sine skills og score. Vi lagrer dem i variabler slik at
        //koden blir mer ryddig istedenfor å stadig skulle kalle metoder.
        SkillManager skills = chara.getSkills();

        int ambac = skills.getCombatSkill(SkillList.AMBAC.key());
        int beam = skills.getCombatSkill(SkillList.BeamCartridgeWeapon.key());
        int shell = skills.getCombatSkill(SkillList.ShellFiringWeapon.key());
        int h2h = skills.getCombatSkill(SkillList.HandToHandCombat.key());
        int sniping = skills.getCombatSkill(SkillList.Sniping.key());
        int wm = skills.getCombatSkill(SkillList.WeaponManipulation.key());
        int ms = skills.getCombatSkill(SkillList.MobileSuit.key());
        int cqb = skills.getCombatSkill(SkillList.CQB.key());
        int shooting = skills.getCombatSkill(SkillList.Shooting.key());
        int ground = skills.getCombatSkill(SkillList.Engagement.key());

        //Sjekk om spiller kvalifiserer til bonus.
        //NB! Husk at spiller kan få flere bonuser og at de stackes.
        if (guncannonBonus && shell >= 1000 && beam >= 1000 && h2h >= 300) {
            bonus[0] += 0.15;
            bonus[1] += 0.30;
            bonus[2] += 0.30;
        }

        if (guncannonAceBonus && shell >= 1300 && beam >= 1300 && h2h >= 300) {
            bonus[0] += 0.10;
            bonus[1] += 0.30;
        }

        if (sniperBonus && sniping >= 1300 && wm >= 1300) {
            bonus[2] += 1.0;
        }

        if (marineLowEndBonus && ambac >= 1000 && h2h >= 700 && beam >= 1000) {
            bonus[0] += 0.30;
            bonus[1] += 0.50;
        }

        if (marineHighEndBonus && ambac >= 1300 && h2h >= 1300 && beam >= 1300 && ms >= 300) {
            bonus[0] += 0.30;
            bonus[1] += 0.30;
            bonus[2] += 0.20;
        }

        if (domBonus && shell >= 1000 && beam >= 1000 && ambac >= 1000 && cqb >= 700 & ms >= 300) {
            bonus[0] += 0.20;
            bonus[1] += 0.40;
            bonus[2] += 1.0;
        }

        if (guntankBonus && shell >= 1100 && ground >= 1100 & shooting >= 1100) {
            bonus[0] += 0.30;
            bonus[1] += 0.30;
            bonus[2] += 2.0;
        }

        if (gmLowEndBonus && ms >= 300 && ambac >= 1000 && (beam >= 1000 || shell >= 1000)
                && shooting >= 1000 && ground >= 1000) {
            bonus[0] += 0.20;
            bonus[1] += 0.50;
            bonus[2] += 0.50;
        }

        if (gmHighEndBonus && ms >= 600 && ambac >= 1300 && (beam >= 1000 || shell >= 1000)
                && shooting >= 1000 && ground >= 1000) {
            bonus[0] += 0.20;
            bonus[1] += 0.40;
            bonus[2] += 0.25;
        }

        if (zakuBonus && ms >= 300 && shell >= 1000 && ambac >= 1000 && shooting >= 1000) {
            bonus[0] += 0.20;
            bonus[1] += 0.40;
            bonus[2] += 0.50;
        }

        if (goufBonus && ms >= 600 && ambac >= 1300 && ground >= 1000 && shell >= 1000) {
            bonus[1] += 0.50;
            bonus[2] += 1.0;
        }

        return bonus;
    }

    public void setGuncannonBonus(boolean guncannonBonus) {
        this.guncannonBonus = guncannonBonus;
    }

    public void setGuncannonAceBonus(boolean guncannonAceBonus) {
        this.guncannonAceBonus = guncannonAceBonus;
    }

    public void setSniperBonus(boolean sniperBonus) {
        this.sniperBonus = sniperBonus;
    }

    public void setMarineLowEndBonus(boolean marineLowEndBonus) {
        this.marineLowEndBonus = marineLowEndBonus;
    }

    public void setMarineHighEndBonus(boolean marineHighEndBonus) {
        this.marineHighEndBonus = marineHighEndBonus;
    }

    public void setDomBonus(boolean domBonus) {
        this.domBonus = domBonus;
    }

    public void setGuntankBonus(boolean guntankBonus) {
        this.guntankBonus = guntankBonus;
    }

    public void setGmLowEndBonus(boolean gmLowEndBonus) {
        this.gmLowEndBonus = gmLowEndBonus;
    }

    public void setGmHighEndBonus(boolean gmHighEndBonus) {
        this.gmHighEndBonus = gmHighEndBonus;
    }

    public void setZakuBonus(boolean zakuBonus) {
        this.zakuBonus = zakuBonus;
    }

    public void setGoufBonus(boolean goufBonus) {
        this.goufBonus = goufBonus;
    }

}
