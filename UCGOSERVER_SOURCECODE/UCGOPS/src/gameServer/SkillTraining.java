package gameServer;

import characters.*;
import items.Vehicle;
import java.util.ArrayList;
import java.util.Random;
import packetHandler.*;
import validItems.*;
import userDB.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen håndterer alt som har med å øke/redusere skills.
 */
public class SkillTraining {

    /**
     * Beregner om spillers AMBAC skill skal øke eller ikke.
     *
     * @param chara Spiller
     * @param A Angriper sin sjanse til å treffe. 1 <= A <= 200. @p aram isPVP
     * true dette er PVP, false hvis PVE.
     */
    public static void trainAmbac(CharacterGame chara, int A, boolean isPVP) {

        //Sørg for at målet sin dodge er OK.
        int acc = A;

        if (acc < 1) {
            acc = 1;
        } else if (acc > 200) {
            acc = 200;
        }

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (isPVP) {
            chanceIncrease = 90;
        } else {
            chanceIncrease = 60;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.AMBAC.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) ((g(x) * 800000) / acc);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.AMBAC.key());
        }
    }

    /**
     * Beregner om spillers CQB skill skal øke eller ikke.
     *
     * @param chara Spiller
     * @param D Målet sin sjanse til å dodge angrep. 1 <= D <= 200. @p aram
     * targetIsHuman true dette er PVP, false hvis PVE. @param isHit true hvis
     * angrepet traff målet, false hvis ikke.
     */
    public static void trainCQB(CharacterGame chara, int D, boolean targetIsHuman, boolean isHit) {

        //Sørg for at målet sin dodge er OK.
        int dodge = D;

        if (dodge < 1) {
            dodge = 1;
        } else if (dodge > 200) {
            dodge = 200;
        }

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (targetIsHuman) {
            chanceIncrease = 45;
        } else {
            chanceIncrease = 30;
        }

        if (isHit) {
            chanceIncrease *= 2;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.CQB.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) ((g(x) * 200000) / dodge);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.CQB.key());
        }
    }

    /**
     * Beregner om spillers H2H skill skal øke eller ikke.
     *
     * @param chara Spiller
     * @param D Målet sin sjanse til å dodge angrep. 1 <= D <= 200. @p aram
     * targetIsHuman true dette er PVP, false hvis PVE. @param isHit true hvis
     * angrepet traff målet, false hvis ikke.
     */
    public static void trainH2H(CharacterGame chara, int D, boolean targetIsHuman, boolean isHit) {

        //Sørg for at målet sin dodge er OK.
        int dodge = D;

        if (dodge < 1) {
            dodge = 1;
        } else if (dodge > 200) {
            dodge = 200;
        }

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (targetIsHuman) {
            chanceIncrease = 45;
        } else {
            chanceIncrease = 30;
        }

        if (isHit) {
            chanceIncrease *= 2;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.HandToHandCombat.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) ((g(x) * 200000) / dodge);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.HandToHandCombat.key());
        }
    }

    /**
     * Beregner om spillers defence skill skal øke eller ikke.
     *
     * @param chara Spiller
     * @param isPVP true dette er PVP, false hvis PVE.
     */
    public static void trainDefence(CharacterGame chara, boolean isPVP) {

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (isPVP) {
            chanceIncrease = 45;
        } else {
            chanceIncrease = 35;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.Defence.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) (g(x) * 1000);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.Defence.key());
        }
    }

    /**
     * Beregner om spillers emergency repair skill skal økes.
     *
     * @param chara Spiller.
     * @param lvl Hvilket ER kit level som brukes, 1-4.
     *
     */
    public static void trainEmergencyRepair(CharacterGame chara, int lvl) {

        //Sjansen for at skill øker.
        int chanceIncrease = 40;

        if (lvl < 1 || lvl > 4) {
            lvl = 1;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.EmergencyRepair.key());

        int chanceNotIncrease = (int) ((200.0 * g(x)) / lvl);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.EmergencyRepair.key());
        }

    }

    /**
     * Beregner om spillers weapon manipulation skill skal økes.
     *
     * @param chara Spiller
     * @param targetIsHuman true hvis spiller angrep en annen spiller, false
     * hvis angrep NPC.
     */
    public static void trainWeaponManipulation(CharacterGame chara, boolean targetIsHuman) {
        return; //Ikke lengre brukt.
        /*
		//Sjansen for at skill øker.
		int chanceIncrease;
		
		if ( targetIsHuman ) chanceIncrease = 30;
		else chanceIncrease = 20;
		
		int x = chara.getSkills().getCombatSkill(SkillList.WeaponManipulation.key());
		
		//Sjansen for at skill ikke øker.
		int chanceNotIncrease = (int)(3000.0*g(x));
		
		Random r = new Random();
		
		int n = r.nextInt(chanceIncrease+chanceNotIncrease);

		if ( n < chanceIncrease ) {
			//Skill skal øke.
			increaseCombatSkill(chara,SkillList.WeaponManipulation.key());			
		}
         */
    }

    /**
     * Beregner om spillers tactics skill skal økes.
     *
     * @param chara Spiller
     * @param targetIsHuman true hvis spiller angrep en annen spiller, false
     * hvis angrep NPC.
     */
    public static void trainTactics(CharacterGame chara, boolean targetIsHuman) {

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (targetIsHuman) {
            chanceIncrease = 45;
        } else {
            chanceIncrease = 35;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.Tactics.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) (5000.0 * g(x));

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.Tactics.key());
        }
    }

    /**
     * Beregner om spillers shooting skill skal økes.
     *
     * @param chara Spiller
     * @param targetIsHuman true hvis spiller angrep en annen spiller, false
     * hvis angrep NPC.
     */
    public static void trainShooting(CharacterGame chara, boolean targetIsHuman) {

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (targetIsHuman) {
            chanceIncrease = 45;
        } else {
            chanceIncrease = 35;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.Shooting.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) (4000.0 * g(x));

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.Shooting.key());
        }
    }

    /**
     * Beregner om spillers sniping skill skal øke eller ikke.
     *
     * @param chara Spiller
     * @param D Målet sin sjanse til å dodge angrep. 1 <= D <= 200. @p aram
     * targetIsHuman true dette er PVP, false hvis PVE.
     */
    public static void trainSniping(CharacterGame chara, int D, boolean targetIsHuman) {

        //Sørg for at målet sin dodge er OK.
        int dodge = D;

        if (dodge < 1) {
            dodge = 1;
        } else if (dodge > 200) {
            dodge = 200;
        }

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (targetIsHuman) {
            chanceIncrease = 300;
        } else {
            chanceIncrease = 150;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.Sniping.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) ((g(x) * 100000) / dodge);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.Sniping.key());
        }
    }

    /**
     * Beregner om spillers Beam cartridge skill skal øke eller ikke.
     *
     * @param chara Spiller
     * @param D Målet sin sjanse til å dodge angrep. 1 <= D <= 200. @p aram
     * targetIsHuman true dette er PVP, false hvis PVE.
     */
    public static void trainBeamCartridgeWeapon(CharacterGame chara, int D, boolean targetIsHuman) {

        //Sørg for at målet sin dodge er OK.
        int dodge = D;

        if (dodge < 1) {
            dodge = 1;
        } else if (dodge > 200) {
            dodge = 200;
        }

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (targetIsHuman) {
            chanceIncrease = 45;
        } else {
            chanceIncrease = 35;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.BeamCartridgeWeapon.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) ((g(x) * 300000) / dodge);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.BeamCartridgeWeapon.key());
        }
    }

    /**
     * Beregner om spillers shell firing skill skal øke eller ikke.
     *
     * @param chara Spiller
     * @param D Målet sin sjanse til å dodge angrep. 1 <= D <= 200. @p aram
     * targetIsHuman true dette er PVP, false hvis PVE.
     */
    public static void trainShellFiringWeapon(CharacterGame chara, int D, boolean targetIsHuman) {

        //Sørg for at målet sin dodge er OK.
        int dodge = D;

        if (dodge < 1) {
            dodge = 1;
        } else if (dodge > 200) {
            dodge = 200;
        }

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (targetIsHuman) {
            chanceIncrease = 45;
        } else {
            chanceIncrease = 35;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.ShellFiringWeapon.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) ((g(x) * 300000) / dodge);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.ShellFiringWeapon.key());
        }
    }

    /**
     * Beregner om spillers engagement skill skal øke eller ikke.
     *
     * @param chara Spiller
     * @param D Målet sin sjanse til å dodge angrep. 1 <= D <= 200. @p aram
     * targetIsHuman true dette er PVP, false hvis PVE.
     */
    public static void trainEngagement(CharacterGame chara, int D, boolean targetIsHuman) {

        //Sørg for at målet sin dodge er OK.
        int dodge = D;

        if (dodge < 1) {
            dodge = 1;
        } else if (dodge > 200) {
            dodge = 200;
        }

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (targetIsHuman) {
            chanceIncrease = 45;
        } else {
            chanceIncrease = 35;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.Engagement.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) ((g(x) * 400000) / dodge);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.Engagement.key());
        }
    }

    /**
     * Beregner om spillers Near engagement skill skal øke eller ikke.
     *
     * @param chara Spiller
     * @param D Målet sin sjanse til å dodge angrep. 1 <= D <= 200. @p aram
     * targetIsHuman true dette er PVP, false hvis PVE.
     */
    public static void trainNearEngagement(CharacterGame chara, int D, boolean targetIsHuman) {

        //Sørg for at målet sin dodge er OK.
        int dodge = D;

        if (dodge < 1) {
            dodge = 1;
        } else if (dodge > 200) {
            dodge = 200;
        }

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (targetIsHuman) {
            chanceIncrease = 45;
        } else {
            chanceIncrease = 35;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.NearEngagement.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) ((g(x) * 400000) / dodge);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.NearEngagement.key());
        }
    }

    /**
     * Beregner om spillers Ranged engagement skill skal øke eller ikke.
     *
     * @param chara Spiller
     * @param D Målet sin sjanse til å dodge angrep. 1 <= D <= 200. @p aram
     * targetIsHuman true dette er PVP, false hvis PVE.
     */
    public static void trainRangedEngagement(CharacterGame chara, int D, boolean targetIsHuman) {

        //Sørg for at målet sin dodge er OK.
        int dodge = D;

        if (dodge < 1) {
            dodge = 1;
        } else if (dodge > 200) {
            dodge = 200;
        }

        //Sjansen for at skill øker.
        int chanceIncrease;

        if (targetIsHuman) {
            chanceIncrease = 45;
        } else {
            chanceIncrease = 35;
        }

        int x = chara.getSkills().getCombatSkill(SkillList.RangedEngagement.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) ((g(x) * 400000) / dodge);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCombatSkill(chara, SkillList.RangedEngagement.key());
        }
    }

    /**
     * Beregner om spillers mining skill skal økes.
     *
     * @param chara Spiller
     */
    public static void trainMining(CharacterGame chara) {

        //Sjansen for at skill øker.
        int chanceIncrease = 30;

        int x = chara.getSkills().getCraftingSkill(SkillList.Mining.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) (200.0 * g(x));

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCraftingSkill(chara, SkillList.Mining.key());
        }

    }

    /**
     * Beregner om spillers MS/MA Construction skill skal økes.
     *
     * @param chara Spiller
     * @param D Hvor vanskelig det er å lage MS/Vehicle.
     * @param L Skill level som kreves for å lage MS/vehicle.
     * @param upgrade true hvis dette er oppgradering av MS/vehicle.
     * @param dismantle true hvis dette er dismantle av MS/vehicle.
     * @param materialCount Hvor mye FL, FTCC, JP, GA som ble brukt til
     * crafting. Gir skill bonus.
     */
    public static void trainMsConstruction(CharacterGame chara, int D, int L, boolean upgrade, boolean dismantle, int materialCount) {
        /**
         * RESET 2016 REGLER: Dismantle og upgrade gir ingen skill training.
         * Sjansen for skill increase reduseres med ca 1.5% pr 0.1 skill level.
         * Men aldri under 5%. EKSEMPEL: Om vi har 1.0 skill mer enn krevet er
         * sjansen for skill increase circa 100-1.5*10 = 85% Vanskelighets
         * graden (D) og materialCount er ikke lengre brukt.
         *
         * RESET 2019 REGLER: Kan få max 3,0 skillpoints hver dag.
         */
        if (dismantle || upgrade) {
            return;
        }

        //Sjekk at spiller ikke har gått over daglig grense for skillpoints.
        GameCharacter gc = ManageCharacters.getGameCharacter(chara.getCharacterID());
        if (gc.dailyMScrafting >= 30) {
            return;
        }

        int skillDelta = chara.getSkills().getCraftingSkill(SkillList.MSMAConstruction.key()) - L;
        if (skillDelta < 0) {
            System.out.println("MS Crafting skill training error, skillDelta=" + skillDelta);
            return;
        }

        //Sjansen for at skill skal øke.
        //66 = ca 1.5% per steg.
        int chanceIncrease = 66 - skillDelta;
        if (chanceIncrease < 4) {
            chanceIncrease = 4; //Aldri under 5% sjanse.
        }
        int chanceNotIncrease = 66 - chanceIncrease;

        Random r = new Random();
        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCraftingSkill(chara, SkillList.MSMAConstruction.key());
            gc.dailyMScrafting++;
        }
        /*
		//Sjansen for at skill skal øke.
		int chanceIncrease = (int)(10+Math.sqrt(D*L));
		
                //Gi bonus dersom vi brukte FL, FTCC, JP eller GA.
                if ( materialCount > 0 ) {
                    chanceIncrease *= (int)(Math.sqrt((float)materialCount));
                }
                
		if ( upgrade ) chanceIncrease /= 3;
		else if ( dismantle ) chanceIncrease /= 2;
		
		int x = chara.getSkills().getCraftingSkill(SkillList.MSMAConstruction.key());
		
		double m = 400; //Konstant brukt for å beregne sjansen til å ikke øke.
		
		//Basert på skill level sett m høyere.
		if ( x >= 300 && x < 400 ) m = 800;
		if ( x >= 400 && x < 500 ) m = 1500;
		if ( x >= 500 && x < 600 ) m = 2000;
		if ( x >= 600 && x < 700 ) m = 3000;
		if ( x >= 700 && x < 800 ) m = 4000;
		if ( x >= 800 && x < 900 ) m = 6000;
		if ( x >= 900 && x < 950 ) m = 8000;
		if ( x >= 950 && x < 999 ) m = 10000;
		else if ( x >= 1000 ) m = 12000;
		
		//Sjansen for at skill ikke øker.
		int chanceNotIncrease = (int)(m*g(x));
		
		Random r = new Random();
		
		int n = r.nextInt(chanceIncrease+chanceNotIncrease);

		if ( n < chanceIncrease ) {
			//Skill skal øke.
			increaseCraftingSkill(chara,SkillList.MSMAConstruction.key());			
		}
         */
    }

    /**
     * Beregner om spillers Battleship Construction skill skal økes.
     *
     * @param chara Spiller
     * @param D Hvor vanskelig det er å lage MS/Vehicle.
     * @param L Skill level som kreves for å lage MS/vehicle.
     * @param upgrade true hvis dette er oppgradering av MS/vehicle.
     * @param dismantle true hvis dette er dismantle av MS/vehicle.
     */
    public static void trainBattleshipConstruction(CharacterGame chara, int D, int L, boolean upgrade, boolean dismantle) {

        //Sjansen for at skill skal øke.
        int chanceIncrease = (int) (10 + Math.sqrt(D * L));

        if (upgrade) {
            chanceIncrease /= 4;
        } else if (dismantle) {
            chanceIncrease /= 2;
        }

        int x = chara.getSkills().getCraftingSkill(SkillList.BattleshipConstruction.key());

        double m = 10000; //Konstant brukt for å beregne sjansen til å ikke øke.

        //Basert på skill level sett m høyere.
        if (x >= 500 && x < 600) {
            m = 15000;
        }
        if (x >= 600 && x < 700) {
            m = 35000;
        }
        if (x >= 700 && x < 800) {
            m = 50000;
        }
        if (x >= 800 && x < 900) {
            m = 60000;
        }
        if (x >= 900 && x < 950) {
            m = 70000;
        }
        if (x >= 950) {
            m = 120000;
        }

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) (m * g(x));

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCraftingSkill(chara, SkillList.BattleshipConstruction.key());
        }
    }

    /**
     * Beregner om spillers Arms development skill skal økes.
     *
     * @param chara Spiller
     * @param D Hvor vanskelig det er å lage våpenet.
     * @param L Skill level som kreves for å lage våpenet.
     */
    public static void trainArmsDevelopment(CharacterGame chara, int D, int L) {

        //Sjekk at spiller ikke har gått over daglig grense for 5,0 skillpoints.
        GameCharacter gc = ManageCharacters.getGameCharacter(chara.getCharacterID());
        if (gc.dailyWeaponsCrafting >= 50) {
            return;
        }

        //Sjansen for at skill skal øke.
        int chanceIncrease = (int) (10 + Math.sqrt(D * L));

        int x = chara.getSkills().getCraftingSkill(SkillList.ArmsConstruction.key());

        double m = 100; //Konstant brukt for å beregne sjansen til å ikke øke.

        //Basert på skill level sett m høyere.
        if (x >= 500 && x < 600) {
            m = 500;
        }
        if (x >= 600 && x < 700) {
            m = 600;
        }
        if (x >= 700 && x < 800) {
            m = 700;
        }
        if (x >= 800 && x < 900) {
            m = 800;
        }
        if (x >= 900 && x < 950) {
            m = 900;
        }
        if (x >= 950) {
            m = 1000;
        }

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) (m * g(x));

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCraftingSkill(chara, SkillList.ArmsConstruction.key());
            gc.dailyWeaponsCrafting++;
        }
    }

    /**
     * Beregner om spillers Clothes manufacturing skill skal økes.
     *
     * @param chara Spiller
     * @param D Hvor vanskelig det er å lage klesplagget.
     * @param L Skill level som kreves for å lage klesplagget.
     */
    public static void trainClothesManufacturing(CharacterGame chara, int D, int L) {

        //Sjansen for at skill skal øke.
        int chanceIncrease = (int) (10 + Math.sqrt(D * L));

        int x = chara.getSkills().getCraftingSkill(SkillList.ClothingManufacturing.key());

        double m = 500; //Konstant brukt for å beregne sjansen til å ikke øke.

        //Basert på skill level sett m høyere.
        if (x >= 500 && x < 600) {
            m = 1000;
        }
        if (x >= 600 && x < 700) {
            m = 2000;
        }
        if (x >= 700 && x < 800) {
            m = 3000;
        }
        if (x >= 800 && x < 900) {
            m = 4000;
        }
        if (x >= 900 && x < 950) {
            m = 5000;
        }
        if (x >= 950) {
            m = 6000;
        }

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) (m * g(x));

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCraftingSkill(chara, SkillList.ClothingManufacturing.key());
        }
    }

    /**
     * Beregner om spillers refining skill skal økes.
     *
     * @param chara Spiller
     * @param D Hvor vanskelig det er å lage materialet.
     * @param L Skill level som kreves for å lage materialet.
     * @param m Mengde materialer som lages delt på 100 plus 1. 1000 gir
     * (1000/100)+1 = 11.
     */
    public static void trainRefining(CharacterGame chara, int D, int L, int m) {

        //Sjekk at spiller ikke har gått over daglig grense for 5,0 skillpoints.
        GameCharacter gc = ManageCharacters.getGameCharacter(chara.getCharacterID());
        if (gc.dailyRefinery >= 50) {
            return;
        }

        //Sjansen for at skill skal øke.
        int chanceIncrease = (int) (10 + Math.sqrt(D * L));

        int x = chara.getSkills().getCraftingSkill(SkillList.Refinery.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) ((1000.0 * g(x)) / m);

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCraftingSkill(chara, SkillList.Refinery.key());
            gc.dailyRefinery++;
        }
    }

    /**
     * Beregner om spillers strength skal øke.
     *
     * @param chara Spiller
     * @param M Modifier verdi.
     * @param isPVP true hvis PVP, false hvis ikke.
     */
    public static void trainStrength(CharacterGame chara, double M, boolean isPVP) {

        //Sjansen for at skill skal øke.
        int chanceIncrease;

        if (isPVP) {
            chanceIncrease = 90;
        } else {
            chanceIncrease = 60;
        }

        int x = chara.getSkills().getCharacterAttribute(SkillList.Strength.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) (M * (Math.sqrt(x) * 7000.0 / Math.sqrt(700)));

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCharacterAttribute(chara, SkillList.Strength.key());
        }
    }

    /**
     * Beregner om spillers spirit skal øke.
     *
     * @param chara Spiller
     * @param M Modifier verdi.
     * @param isPVP true hvis PVP, false hvis ikke.
     */
    public static void trainSpirit(CharacterGame chara, double M, boolean isPVP) {

        //Sjansen for at skill skal øke.
        int chanceIncrease;

        if (isPVP) {
            chanceIncrease = 90;
        } else {
            chanceIncrease = 60;
        }

        int x = chara.getSkills().getCharacterAttribute(SkillList.Spirit.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) (M * (Math.sqrt(x) * 7000.0 / Math.sqrt(700)));

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCharacterAttribute(chara, SkillList.Spirit.key());
        }
    }

    /**
     * Beregner om spillers luck skal øke.
     *
     * @param chara Spiller
     * @param M Modifier verdi.
     * @param isPVP true hvis PVP, false hvis ikke.
     */
    public static void trainLuck(CharacterGame chara, double M, boolean isPVP) {

        //Sjansen for at skill skal øke.
        int chanceIncrease;

        if (isPVP) {
            chanceIncrease = 90;
        } else {
            chanceIncrease = 60;
        }

        int x = chara.getSkills().getCharacterAttribute(SkillList.Luck.key());

        //Sjansen for at skill ikke øker.
        int chanceNotIncrease = (int) (M * (Math.sqrt(x) * 7000.0 / Math.sqrt(700)));

        Random r = new Random();

        int n = r.nextInt(chanceIncrease + chanceNotIncrease);

        if (n < chanceIncrease) {
            //Skill skal øke.
            increaseCharacterAttribute(chara, SkillList.Luck.key());
        }
    }

    /**
     * Øker enten MS,MA eller Fighter skill for spiller. Skill økes ALLTID når
     * metoden kalles. DETTE AVGJØRES I OPCODE 0x2/CharacterGame!
     *
     * @param chara Spiller hvis skill skal økes.
     */
    public static void trainMsMaFighter(CharacterGame chara) {

        //Sjekk at spiller er i MS, MA eller Fighter.
        Vehicle v = chara.getVehicle();

        if (v == null) {
            return;
        }

        GameItem i = ItemList.getItem(v.getID());

        if (i == null) {
            return;
        }

        if (i.getItemSubType() == ItemSubType.MS) {
            increaseCombatSkill(chara, SkillList.MobileSuit.key());
        }
        //else if ( i.getItemSubType() == ItemSubType.MA ) increaseCombatSkill(chara,SkillList.MobileArmour.key()); //SKILL EKSITERER IKKE LENGRE
        //else if ( i.getItemSubType() == ItemSubType.Fighter ) increaseCombatSkill(chara,SkillList.Fighter.key());
    }

    /**
     * Øker en combat skill med ett poeng, hvis mulig.
     *
     * Denne metoden sender også melding til klienten om endring i skill.
     *
     * @param chara Character hvis skill skal økes.
     * @param skillID Skill ID for skill som skal økes.
     */
    public static void increaseCombatSkill(CharacterGame chara, int skillID) {

        SkillManager skills = chara.getSkills();

        //Sjekk at skill ikke har nådd maks grense.
        int level = skills.getCombatSkill(skillID);

        if (level == -1) {
            //Ugyldig skill ID mottatt.
            System.out.println("SkillTraining.java:increaseCombatSkill, ERROR: Invalid skill ID:" + skillID);
            return;
        } else if (level >= config.Server.maxSkillLevel) {
            //Skill har nådd maks grense, den skal ikke økes.
            return;
        }

        //Sjekk om character har oppnådd maks antall skillpoints.
        if (skills.getTotalCombatSkill() + skills.getTotalCraftingSkill() >= config.Server.maxSkillPoints) {
            //Character har ikke flere skillpoints igjen. Sjekk om det er en annen skill som kan reduseres
            //slik at vi kan øke oppgitt skill.

            //Sjekk først at skill skal øke og ikke skal stå i ro eller synke.
            if (skills.getCombatSkillRetning(skillID) != 0) {
                return;
            }

            //Sjekk om det er noen combat/crafting skills som kan reduseres.
            ArrayList<Integer> combatSkills = skills.getCombatSkillReducible(skillID);
            ArrayList<Integer> craftingSkills = skills.getCraftingSkillReducible(-1); //-1 for dette gjelder combat skills.

            if (combatSkills.size() > 0) {
                //Det finnes minst en combat skill som kan reduseres.
                Random r = new Random();

                //Velg en tilfeldig combat skill som kan reduseres.
                int n = r.nextInt(combatSkills.size());

                //Nytt level for skill som skal reduseres.
                int lv = skills.getCombatSkill(combatSkills.get(n)) - 1;

                skills.setCombatSkill(combatSkills.get(n), lv);

                //Send opcode 0x8034 for å informere om endring.
                send8034CombatCrafting(chara, combatSkills.get(n), 0, -1);
            } else if (craftingSkills.size() > 0) {
                //Det finnes minst en crafting skill som kan reduseres.
                Random r = new Random();

                //Velg en tilfeldig crafting skill som kan reduseres.
                int n = r.nextInt(craftingSkills.size());

                //Nytt level for skill som skal reduseres.
                int lv = skills.getCraftingSkill(craftingSkills.get(n)) - 1;

                skills.setCraftingSkill(craftingSkills.get(n), lv);

                //Set skill type, 1=Vanlige crafting skill, 2=Clothes.
                int skillType = 1;
                if (craftingSkills.get(n) == 5) {
                    skillType = 2; //ID 5 = Clothes manufacturing.
                }
                //Send opcode 0x8034 for å informere om endring.
                send8034CombatCrafting(chara, craftingSkills.get(n), skillType, -1);
            } else {
                //Det finnes ingen skills som kan reduseres så oppgitt skill kan ikke økes.
                return;
            }

        }

        /*
		 * OK, skill har ikke nådd maks grense og vi har flere skillpoints igjen å bruke.
		 * 
		 * Øk skill med ett poeng og send melding til spiller.
         */
        boolean r = skills.increaseSkill(skillID, true);

        if (!r) {
            //Skill økte ikke. Dette skal egentlig ikke kunne skje.
            System.out.println("SkillTraining.java:increaseCombatSkill, ERROR: r=false, BUG?");
            return;
        }

        send8034CombatCrafting(chara, skillID, 0, 1);

        //Gi rank poeng.
        chara.gainRank(1);
    }

    /**
     * Øker en crafting skill med ett poeng, hvis mulig.
     *
     * Denne metoden sender også melding til klienten om endring i skill.
     *
     * @param chara Character hvis skill skal økes.
     * @param skillID Skill ID for skill som skal økes.
     */
    public static void increaseCraftingSkill(CharacterGame chara, int skillID) {

        SkillManager skills = chara.getSkills();

        //Sjekk at skill ikke har nådd maks grense.
        int level = skills.getCraftingSkill(skillID);

        if (level == -1) {
            //Ugyldig skill ID mottatt.
            System.out.println("SkillTraining.java:increaseCraftingSkill, ERROR: Invalid skill ID:" + skillID);
            return;
        } else if (level >= config.Server.maxSkillLevel) {
            //Skill har nådd maks grense, den skal ikke økes.
            return;
        }

        //Sjekk om character har oppnådd maks antall skillpoints.
        if (skills.getTotalCombatSkill() + skills.getTotalCraftingSkill() >= config.Server.maxSkillPoints) {
            //Character har ikke flere skillpoints igjen. Sjekk om det er en annen skill som kan reduseres
            //slik at vi kan øke oppgitt skill.

            //Sjekk først at skill skal øke og ikke skal stå i ro eller synke.
            if (skills.getCraftingSkillRetning(skillID) != 0) {
                return;
            }

            //Sjekk om det er noen combat/crafting skills som kan reduseres.
            ArrayList<Integer> combatSkills = skills.getCombatSkillReducible(-1); //-1 for dette gjelder crafting skills.
            ArrayList<Integer> craftingSkills = skills.getCraftingSkillReducible(skillID);

            if (combatSkills.size() > 0) {
                //Det finnes minst en combat skill som kan reduseres.
                Random r = new Random();

                //Velg en tilfeldig combat skill som kan reduseres.
                int n = r.nextInt(combatSkills.size());

                //Nytt level for skill som skal reduseres.
                int lv = skills.getCombatSkill(combatSkills.get(n)) - 1;

                skills.setCombatSkill(combatSkills.get(n), lv);

                //Send opcode 0x8034 for å informere om endring.
                send8034CombatCrafting(chara, combatSkills.get(n), 0, -1);
            } else if (craftingSkills.size() > 0) {
                //Det finnes minst en crafting skill som kan reduseres.
                Random r = new Random();

                //Velg en tilfeldig crafting skill som kan reduseres.
                int n = r.nextInt(craftingSkills.size());

                //Nytt level for skill som skal reduseres.
                int lv = skills.getCraftingSkill(craftingSkills.get(n)) - 1;

                skills.setCraftingSkill(craftingSkills.get(n), lv);

                //Set skill type, 1=Vanlige crafting skill, 2=Clothes.
                int skillType = 1;
                if (craftingSkills.get(n) == 5) {
                    skillType = 2; //ID 5 = Clothes manufacturing.
                }
                //Send opcode 0x8034 for å informere om endring.
                send8034CombatCrafting(chara, craftingSkills.get(n), skillType, -1);
            } else {
                //Det finnes ingen skills som kan reduseres så oppgitt skill kan ikke økes.
                return;
            }

        }

        /*
		 * OK, skill har ikke nådd maks grense og vi har flere skillpoints igjen å bruke.
		 * 
		 * Øk skill med ett poeng og send melding til spiller.
         */
        boolean r = skills.increaseSkill(skillID, false);

        if (!r) {
            //Skill økte ikke. Dette skal egentlig ikke kunne skje.
            System.out.println("SkillTraining.java:increaseCraftingSkill, ERROR: r=false, BUG?");
            return;
        }

        int skillType = 1; //1=Crafting skill.
        //Men hvis det er clothes manufacturing som øker skal skillType være 2.
        if (skillID == SkillList.ClothingManufacturing.key()) {
            skillType = 2;
        }

        send8034CombatCrafting(chara, skillID, skillType, 1);

        //Gi rank poeng.
        chara.gainRank(4);
    }

    /**
     * Øker en character attribute med ett poeng, hvis mulig.
     *
     * Denne metoden sender også melding til klienten om endring i skill.
     *
     * @param chara Character hvis skill skal økes.
     * @param skillID Skill ID for skill som skal økes.
     */
    public static void increaseCharacterAttribute(CharacterGame chara, int skillID) {

        SkillManager skills = chara.getSkills();

        //Sjekk at skill ikke har nådd maks grense.
        int level = skills.getCharacterAttribute(skillID);

        if (level == -1) {
            //Ugyldig skill ID mottatt.
            System.out.println("SkillTraining.java:increaseCharacterAttribute, ERROR: Invalid skill ID:" + skillID);
            return;
        } else if (level >= config.Server.maxCharacterAttributePoints) {
            //Skill har nådd maks grense, den skal ikke økes.
            return;
        }

        //Sjekk om character har oppnådd maks antall skillpoints.
        if (skills.getTotalCharacterAttributes() >= config.Server.maxCharacterAttributePoints) {
            //Character har ikke flere attribute points igjen. Sjekk om det er en annen skill som kan reduseres
            //slik at vi kan øke oppgitt skill.

            //Sjekk først at skill skal øke og ikke skal stå i ro eller synke.
            if (skills.getCharacterAttributeRetning(skillID) != 0) {
                return;
            }

            //Sjekk om det er noen character attributes som kan reduseres.
            ArrayList<Integer> attributes = skills.getCharacterAttributeReducible(skillID);

            if (attributes.size() > 0) {
                //Det finnes minst en attribute som kan reduseres.
                Random r = new Random();

                //Velg en tilfeldig combat skill som kan reduseres.
                int n = r.nextInt(attributes.size());

                //Nytt level for skill som skal reduseres.
                int lv = skills.getCharacterAttribute(attributes.get(n)) - 1;

                skills.setCharacterAttribute(attributes.get(n), lv);

                //Send opcode 0x8034 for å informere om endring.
                send8034CharacterAttribute(chara, attributes.get(n), -1);
            } else {
                //Det finnes ingen skills som kan reduseres så oppgitt skill kan ikke økes.
                return;
            }

        }

        /*
		 * OK, skill har ikke nådd maks grense og vi har flere skillpoints igjen å bruke.
		 * 
		 * Øk skill med ett poeng og send melding til spiller.
         */
        boolean r = skills.increaseCharacterAttribute(skillID);

        if (!r) {
            //Skill økte ikke. Dette skal egentlig ikke kunne skje.
            System.out.println("SkillTraining.java:increaseCharacterAttribute, ERROR: r=false, BUG?");
            return;
        }

        send8034CharacterAttribute(chara, skillID, 1);

        //Gi rank poeng.
        chara.gainRank(2);
    }

    /**
     * Sender opcode 0x8034 til oppgitt spiller og informerer om at en
     * combat/crafting skill har blir øket eller redusert.
     *
     * @param chara Character hvis skill endres.
     * @param skillID ID for combat/crafting skill.
     * @param skillType Skill type, 0-2.
     * @param endring Hvor mye skill endres. Kan være negativ for å redusere
     * skill.
     *
     */
    private static void send8034CombatCrafting(CharacterGame chara, int skillID, int skillType, int endring) {

        PacketData svar = new PacketData();

        svar.writeIntBE(chara.getCharacterID());
        svar.writeByte((byte) 0x80);
        svar.writeByte((byte) 0x80);
        svar.writeByte((byte) 0x81);
        svar.writeByte((byte) skillType);
        svar.writeByte((byte) skillID);
        svar.writeIntBE(endring);
        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x80);

        Packet svar8034 = new Packet(0x8034, svar.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar8034, chara.getCharacterID());
    }

    /**
     * Sender opcode 0x8034 til oppgitt spiller og informerer om at en character
     * attribute har blir øket eller redusert.
     *
     * @param chara Character hvis attribute endres.
     * @param skillID ID for attribute.
     * @param endring Hvor mye skill endres. Kan være negativ for å redusere
     * skill.
     *
     */
    private static void send8034CharacterAttribute(CharacterGame chara, int skillID, int endring) {

        PacketData svar = new PacketData();

        svar.writeIntBE(chara.getCharacterID());
        svar.writeByte((byte) 0x80);
        svar.writeByte((byte) 0x81);
        svar.writeByte((byte) skillID);
        svar.writeIntBE(endring);
        svar.writeByte((byte) 0x80);
        svar.writeIntBE(endring);
        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x80);

        Packet svar8034 = new Packet(0x8034, svar.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar8034, chara.getCharacterID());
    }

    /**
     * Funksjonen g som definert i skillTraining.doc dokumentet.
     *
     * @param x
     * @return
     */
    private static double g(int x) {
        return 1.0 - Math.cos((double) x * (Math.PI / 2600));
    }

}
