package gameServer;

import characters.*;
import containers.*;
import gameData.*;
import items.*;
import java.util.Random;
import npc.*;
import packetHandler.*;
import players.PlayerGame;
import userDB.Account;
import validItems.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å beregne resultater fra PVP og PVE.
 *
 * Metodene her kaller SkillTraining.java for å bestemme om skills skal økes
 * eller ikke.
 */
public class Combat {

    //Neste attack ID som skal returneres.
    private static int next_attack_id = 1;
    //Holder oversikten over hvor mange prosent en upgrade påvirker MS/Vehicle stats.
    //[0]=ingen upgrade, [1]=1 upgrade, [2]=2 upgrades osv.
    private static int[] upgrades = {0, 2, 4, 7, 11, 14, 17, 21, 35};

    /**
     * Beregner resultatet av PVP angrep. Oppdaterer også lastTimeAttacked for
     * målet. NB! Resultatet blir ikke påført spillerene.
     *
     * @param attacker Spiller som angriper.
     * @param target Spiller som blir angrepet.
     *
     * @return Resultatet, eller NULL hvis feil.
     */
    public static CombatResult attack(CharacterGame attacker, CharacterGame target) {

        target.setLastTimeAttacked();

        CombatResult r = new CombatResult();

        //Sjekk at begge spillerene ikke er i human mode.
        if (attacker.humanForm() || target.humanForm()) {
            return null;
        }

        //Hent ut stats for MS/vehicles og våpen brukt.
        StatMS aMS; //MS/Vehicle stats for angriper.
        StatMS tMS; //MS/Vehicle stats for målet.
        StatWeapon aW; //Våpen stats for angriper.

        //Angriper må være i et vehicle, hent ut stats for det.
        if (attacker.getVehicle() != null) {
            aMS = StatManager.getMsVehicleStat(attacker.getVehicle().getID());
        } else {
            System.out.println("Combat.java: Player attempted to attack but is not in vehicle.");
            return null;
        }

        //Hent stats for målet sin MS/Vehicle eller taxi/transport.
        if (target.getVehicle() != null) {
            tMS = StatManager.getMsVehicleStat(target.getVehicle().getID());
        } else if (target.getTaxiTransport() != null) {
            tMS = StatManager.getMsVehicleStat(target.getTaxiTransport().getID());
        } else {
            System.out.println("Combat.java: Target is not in vehicle or transport.");
            return null;
        }

        //Hent ut stats for angriper sitt våpen.
        Vehicle v = attacker.getVehicle();
        if (v.getEquippedItem(v.getActiveSlot()) != null) {
            aW = StatManager.getWeaponStat(v.getEquippedItem(v.getActiveSlot()).getItem().getID());
        } else {
            System.out.println("Combat.java: Player has no weapon in active slot.");
            return null;
        }

        //Sjekk at vi har fått alle stats.
        if (aMS == null || tMS == null || aW == null) {
            System.out.println("Combat.java: Missing stats.");
            return null;
        }

        //wID = Item ID til utstyrt våpen.
        int wID = v.getEquippedItem(v.getActiveSlot()).getItem().getID();

        GameItem weapon = ItemList.getItem(wID);
        if (weapon == null || weapon.getItemType() != ItemType.Weapon) {
            return null;
        }

        //Hent ut info om spillerens MS/vehicle.
        GameItem vehicle = ItemList.getItem(v.getID());

        //Beregn angripers mulighet for å treffe. Sett verdier for formelen.
        int msHit = (int) (aMS.getAccuracy() * msStatBonus(attacker, aMS) * aMS.getMsBonus().getAccuracy(attacker));
        int wHit = aW.getAccuracy();
        int S = attacker.getSkills().getCharacterAttribute(SkillList.Spirit.key()) + spiritBonus(attacker);
        S += attacker.getClothing().getGQspirit();

        int e = attacker.getSkills().getCombatSkill(SkillList.Engagement.key());

        int mR = aW.getRange();
        int R = (int) (attacker.getPosisjon().distance(target.getPosisjon()) / 4); //4 game units = 1 meter.

        int uHit = upgrades[attacker.getVehicle().getUpgradeHit() % 9];

        int x; //Våpen skill: Beam,shell,sniping,cqb,h2h.

        if (weapon.getItemSubType() == ItemSubType.Beam) {
            x = attacker.getSkills().getCombatSkill(SkillList.BeamCartridgeWeapon.key());
        } else if (weapon.getItemSubType() == ItemSubType.Shell) {
            x = attacker.getSkills().getCombatSkill(SkillList.ShellFiringWeapon.key());
        } else if (weapon.getItemSubType() == ItemSubType.Sniping) {
            x = attacker.getSkills().getCombatSkill(SkillList.Sniping.key());
        } else if (weapon.getItemSubType() == ItemSubType.CQB) {
            x = attacker.getSkills().getCombatSkill(SkillList.CQB.key());
        } else if (weapon.getItemSubType() == ItemSubType.H2H) {
            x = attacker.getSkills().getCombatSkill(SkillList.HandToHandCombat.key());
        } else {
            x = 0;
        }

        //Bestem om vi skal bruke Ranged eller Near engagement skill.
        int g = 0;
        if (R <= 700) {
            g = attacker.getSkills().getCombatSkill(SkillList.NearEngagement.key());
        } else {
            g = attacker.getSkills().getCombatSkill(SkillList.RangedEngagement.key());
        }

        //HIT = Sjansen til å treffe målet.
        int HIT = hit(msHit, wHit, S, e, g, x, mR, R, uHit);

        //Hvis spiller bruker CQB eller H2H våpen gi 60% bonus til accuracy.
        if (weapon.getItemSubType() == ItemSubType.CQB || weapon.getItemSubType() == ItemSubType.H2H) {
            HIT = (int) (HIT * 1.6);
        }

        //Beregn målet sin mulighet til å dodge.
        int msDodge = (int) (tMS.getDodge() * msStatBonus(target, tMS) * engineBonus(target.getVehicle(), tMS) * tMS.getMsBonus().getDodge(target));
        int L = target.getSkills().getCharacterAttribute(SkillList.Luck.key()) + luckBonus(target);
        L += target.getClothing().getGQluck();

        //Sett X til AMBAC.
        x = target.getSkills().getCombatSkill(SkillList.AMBAC.key());

        //DODGE = Sjansen for målet til å dodge angrep.
        int DODGE = dodge(msDodge, L, x, R);

        //Avgjør om angrepet bommet, traff eller ble dodget.
        //Hvis nærmere enn 2/3 av rekkevidde til våpen .
        int MISS;

        int rR = 2 * aW.getRange() / 3;
        if (R < rR) {
            MISS = 0;
        } else {
            MISS = 20; //Sjansen til å bomme er alltid 20.
        }
        int N;

        try {
            N = new Random().nextInt(MISS + HIT + DODGE);
        } catch (Exception f) {
            N = 0;
        }

        if (N < MISS) {
            r.setHit(false);
        } else if (N < (MISS + HIT)) {
            //Angrepet traff.
            r.setHit(true);
            r.setDodged(false);
        } else {
            //Angrepet traff ikke og bommet ikke, altså det ble dodge.
            r.setHit(true);
            r.setDodged(true);
        }

        //Sjekk her for faction LOCK. Spiller som bruker MS fra feil faction skal aldri kunne treffe
        //en annen spiller eller dodge angrep fra en annen spiller.
        if (attacker.getFaction() != aMS.getFaction() && aMS.getFaction() != -1) {
            r.setHit(false);
        }

        if (target.getFaction() != tMS.getFaction() && tMS.getFaction() != -1) {
            r.setDodged(false);
        }

        //Hvis vi traff, beregn mulighet for critical hit.
        int msCritical = (int) (aMS.getCritical() * aMS.getMsBonus().getCritical(attacker));
        S = attacker.getSkills().getCharacterAttribute(SkillList.Spirit.key()) + spiritBonus(attacker);
        S += attacker.getClothing().getGQspirit();

        if (criticalHit(msCritical, wHit, S)) {
            r.setCritical(true);
        } else {
            r.setCritical(false);
        }

        //Hvis vi traff og målet har shield utstyrt og ikke er taxi/transport, beregn mulighet for shield block.
        if (r.isHit() && !r.isDodged() && target.getVehicle() != null) {

            Vehicle vt = target.getVehicle();

            //Sjekk at målet har et shield utstyrt.
            Container sCon = vt.getEquippedItem(1);
            ItemContainer sIC = null;
            Weapon sW = null;

            if (sCon instanceof ItemContainer) {
                sIC = (ItemContainer) sCon;
            }
            if (sIC != null && sIC.getItem() instanceof Weapon) {
                sW = (Weapon) sIC.getItem();
            }

            if (sW != null && validItems.ItemList.getItem(sW.getID()).getItemSubType() == ItemSubType.Shield) {

                //Hent ut stats for shield.
                int shieldID = vt.getEquippedItem(1).getItem().getID();
                StatWeapon tShield = StatManager.getWeaponStat(shieldID);

                x = target.getSkills().getCombatSkill(SkillList.Defence.key());

                int tS = target.getSkills().getCharacterAttribute(SkillList.Spirit.key()) + spiritBonus(target);
                tS += target.getClothing().getGQspirit();

                if (shieldBlock(tShield.getBlockChance(), tS, x)) {
                    r.setShieldBlock(true);
                } else {
                    r.setShieldBlock(false);
                }
            }
        }

        //Hvis vi traff beregn skade og pengeverdi av angrepet her.
        if (r.isHit() && !r.isDodged()) {

            //Sjekk om målet er i ms/vehicle eller taxi/transport.
            if (target.getVehicle() != null || target.getTaxiTransport() != null) {

                int atkPwr;

                if (target.getVehicle() != null) {
                    atkPwr = calculateAttackVehicle(attacker, target.getVehicle());
                } else {
                    atkPwr = calculateAttackTaxiTransport(attacker, target.getTaxiTransport());
                }

                //Hvis critical hit gi attack bonus.
                if (r.isCritical()) {
                    int bonus = 20 + (int) (10 * Math.random()); //20-30% bonus for critical.
                    atkPwr += (atkPwr / 100) * bonus;
                }

                r.setDamage(atkPwr);
                r.setMoney(atkPwr); //PVP skal ikke gi noe særlig penger.
            } else {
                return null;
            }

            //Hvis målet er UCGM sett skade til 0.
            if (target.getUCGM() == 0x3 || target.getUCGM() == 0x4 || target.getUCGM() == 0x5) {
                r.setDamage(0);
            }

        }

        //Sjekk om angrepet skal resultere i criminal merking. UCGM kan aldri bli criminal.
        if (config.Server.criminal_system) {

            if (attacker.getFaction() == target.getFaction() && !target.isCriminal() && !attacker.isUCGM()) {
                r.setCriminal(true);
            }
        }

        //SKILL TRAINING.
        //SLÅTT AV FOR PVP ETTER 2019 RESET.
        //KUN SKILL TRAINING FOR PVE. HINDRER BRUK AV DUMMY CHARACTERS OG REDUSERER FOKUSET PÅ AT ALLE HAR 700/130 SKILLS.
        /*
        //Weapon Manipulation skill kan økes når et våpen brukes.
        SkillTraining.trainWeaponManipulation(attacker, true);

        //Tactics skill kan økes hvis vi traff målet.
        if (r.isHit()) {
            SkillTraining.trainTactics(attacker, true);
        }

        //Hvis vi bruker beam våpen kan Beam cartridge skill øke.
        if (weapon.getItemSubType() == ItemSubType.Beam) {
            SkillTraining.trainBeamCartridgeWeapon(attacker, DODGE, true);
        }

        //Hvis vi bruker shell våpen kan shell firing skill øke.
        if (weapon.getItemSubType() == ItemSubType.Shell) {
            SkillTraining.trainShellFiringWeapon(attacker, DODGE, true);
        }

        //Hvis vi bruker sniping våpen kan sniping skill øke.
        if (weapon.getItemSubType() == ItemSubType.Sniping) {
            SkillTraining.trainSniping(attacker, DODGE, true);
        }

        //Hvis vi bruker beam/shell våpen kan shooting skill øke.
        if (weapon.getItemSubType() == ItemSubType.Beam || weapon.getItemSubType() == ItemSubType.Shell) {

            SkillTraining.trainShooting(attacker, true);
        }

        //Hvis vi bruker CQB våpen kan CQB skill øke.
        if (weapon.getItemSubType() == ItemSubType.CQB) {
            SkillTraining.trainCQB(attacker, DODGE, true, r.isHit());
        }

        //Hvis vi bruker H2H våpen kan H2H skill øke.
        if (weapon.getItemSubType() == ItemSubType.H2H) {
            SkillTraining.trainH2H(attacker, DODGE, true, r.isHit());
        }

        //Dersom målet blokkerte angrepet med et shield kan defence skill øke.
        if (r.isShieldBlock()) {
            SkillTraining.trainDefence(target, true);
        }

        //Dersom målet dodget angrepet kan ambac øke.
        if (r.isDodged()) {
            SkillTraining.trainAmbac(target, HIT, true);
        }

        //Øk Engagement skill. 
        SkillTraining.trainEngagement(attacker, DODGE, true);

        //Avhengig av avstand til målet kan vi øke Near/Ranged engagement skill.
        if (R <= 700) {
            SkillTraining.trainNearEngagement(attacker, DODGE, true);
        } else {
            SkillTraining.trainRangedEngagement(attacker, DODGE, true);
        }

        //Dersom vi traff målet kan strength øke.
        if (r.isHit()) {
            //Hvis spiller bruker CQB eller H2H gi bonus til strength.
            double M = 1.0;
            if (weapon.getItemSubType() == ItemSubType.CQB || weapon.getItemSubType() == ItemSubType.H2H) {
                M = 0.7;
            }

            SkillTraining.trainStrength(attacker, M, true);
        }

        //Hvis spiller fikk critical hit gi bonus til spirit.
        double spirit = 1.0;
        if (r.isCritical()) {
            spirit = 0.8;
        }

        SkillTraining.trainSpirit(attacker, spirit, true);

        //Dersom målet blokkerte angrepet med et shield kan målets spirit øke.
        if (r.isShieldBlock()) {
            SkillTraining.trainSpirit(target, 2.0, true);
        }

        //Dersom målet dodget angrepet kan målets luck øke.
        if (r.isDodged()) {

            //Sett M basert på angripers treffsikkerhet.
            double M = 1.7 - (HIT / 200.0);

            if (M < 0.5) {
                M = 0.5;
            }

            SkillTraining.trainLuck(target, M, true);
        }
         */
        return r;
    }

    /**
     * Beregner resultatet av angrep på en parkert MS/Vehicle. NB! Resultatet
     * blir ikke påført MS/Vehicle. NB! Et angrep på en item vil ALLTID treffe.
     *
     * @param attacker Spiller som angriper.
     * @param target MS/Vehicle som blir angrepet.
     *
     * @return Resultatet, eller NULL hvis feil.
     */
    public static CombatResult attack(CharacterGame attacker, Vehicle target) {

        CombatResult r = new CombatResult();

        r.setHit(true);

        int atkPwr = calculateAttackVehicle(attacker, target);

        r.setDamage(atkPwr);
        r.setMoney(atkPwr * 5); //Penger bør beregnes på en bedre måte.

        return r;
    }

    /**
     * Beregner resultatet av et angrep på en NPC. NB! Resultatet blir ikke
     * påført NPC.
     *
     * @param attacker Spiller som angriper.
     * @param target NPC som blir angrepet.
     *
     * @return Resultat fra angrepet eller NULL hvis feil.
     */
    public static CombatResult attack(CharacterGame attacker, NPCvehicle target) {

        CombatResult res = new CombatResult();

        //Dersom målet er en supply NPC som  hangar, bank osv skal vi bare returnere.
        if (target.getTemplate().getBehavior() == Template.npcType.Supply) {
            res.setHit(false);
            return res;
        }

        //Sjekk at spilleren ikke er i human mode.
        if (attacker.humanForm()) {
            return null;
        }

        //Hent ut stats for MS/vehicle og våpen brukt.
        StatMS aMS; //MS/Vehicle stats for angriper.
        StatWeapon aW; //Våpen stats for angriper.

        //Angriper må være i et vehicle, hent ut stats for det.
        if (attacker.getVehicle() != null) {
            aMS = StatManager.getMsVehicleStat(attacker.getVehicle().getID());
        } else {
            System.out.println("Combat.java (NPC): Player attempted to attack but is not in vehicle.");
            return null;
        }

        //Hent ut stats for angriper sitt våpen.
        Vehicle v = attacker.getVehicle();
        if (v.getEquippedItem(v.getActiveSlot()) != null) {
            aW = StatManager.getWeaponStat(v.getEquippedItem(v.getActiveSlot()).getItem().getID());
        } else {
            System.out.println("Combat.java (NPC): Player has no weapon in active slot.");
            return null;
        }

        //Sjekk at vi har fått alle stats.
        if (aMS == null || aW == null) {
            System.out.println("Combat.java (NPC): Missing stats.");
            return null;
        }

        //wID = Item ID til utstyrt våpen.
        int wID = v.getEquippedItem(v.getActiveSlot()).getItem().getID();

        GameItem weapon = ItemList.getItem(wID);
        if (weapon == null || weapon.getItemType() != ItemType.Weapon) {
            return null;
        }

        //Hent ut info om spiller MS/vehicle.
        GameItem vehicle = ItemList.getItem(v.getID());

        //Beregn angripers mulighet for å treffe. Sett verdier for formelen.
        int msHit = (int) (aMS.getAccuracy() * msStatBonus(attacker, aMS) * aMS.getMsBonus().getAccuracy(attacker));
        int wHit = aW.getAccuracy();
        int S = attacker.getSkills().getCharacterAttribute(SkillList.Spirit.key()) + spiritBonus(attacker);
        S += attacker.getClothing().getGQspirit();

        int e = attacker.getSkills().getCombatSkill(SkillList.Engagement.key());

        int mR = aW.getRange();
        int R; //Avstand til NPC i meter.

        //Hvordan avstand regnes ut avhenger av zone. Space=x,y,z Ground=x,y
        if (attacker.getPosisjon().getZone() == 1) {
            //Ground. Bruk kun X,Y aksene.
            int dX = Math.abs(attacker.getPosisjon().getX() - target.getPosisjon().getX());
            int dY = Math.abs(attacker.getPosisjon().getY() - target.getPosisjon().getY());

            R = (int) (Math.sqrt(dX * dX + dY * dY));
            R = R / 4;
        } else {
            //Space. Bruk X,Y,Z aksene.
            R = (int) (attacker.getPosisjon().distance(target.getPosisjon()) / 4); //4 game units = 1 meter.
        }

        int uHit = upgrades[attacker.getVehicle().getUpgradeHit() % 9];

        int x; //Våpen skill: Beam,shell,sniping,cqb,h2h.

        if (weapon.getItemSubType() == ItemSubType.Beam) {
            x = attacker.getSkills().getCombatSkill(SkillList.BeamCartridgeWeapon.key());
        } else if (weapon.getItemSubType() == ItemSubType.Shell) {
            x = attacker.getSkills().getCombatSkill(SkillList.ShellFiringWeapon.key());
        } else if (weapon.getItemSubType() == ItemSubType.Sniping) {
            x = attacker.getSkills().getCombatSkill(SkillList.Sniping.key());
        } else if (weapon.getItemSubType() == ItemSubType.CQB) {
            x = attacker.getSkills().getCombatSkill(SkillList.CQB.key());
        } else if (weapon.getItemSubType() == ItemSubType.H2H) {
            x = attacker.getSkills().getCombatSkill(SkillList.HandToHandCombat.key());
        } else {
            x = 0;
        }

        //Bestem om vi skal bruke Ranged eller Near engagement skill.
        int g = 0;
        if (R <= 700) {
            g = attacker.getSkills().getCombatSkill(SkillList.NearEngagement.key());
        } else {
            g = attacker.getSkills().getCombatSkill(SkillList.RangedEngagement.key());
        }

        //HIT = Sjansen til å treffe målet.
        int HIT = hit(msHit, wHit, S, e, g, x, mR, R, uHit);

        //Hvis spiller bruker CQB eller H2H våpen gi 60% bonus til accuracy.
        if (weapon.getItemSubType() == ItemSubType.CQB || weapon.getItemSubType() == ItemSubType.H2H) {
            HIT = (int) (HIT * 1.6);
        }

        //DODGE = Sjansen for målet til å dodge angrep.
        int DODGE = target.getTemplate().getDodgeChance();

        //Avgjør om angrepet bommet, traff eller ble dodget.
        //Hvis nærmere enn 200 meter og vi ikke bruker CQB/H2H kan vi ikke bomme.
        int MISS;

        if (R < 200 && weapon.getItemSubType() != ItemSubType.CQB && weapon.getItemSubType() != ItemSubType.H2H) {
            MISS = 0;
        } else {
            MISS = 20; //Sjansen til å bomme er alltid 20.
        }
        //BUG Sjekk
        if (MISS + HIT + DODGE < 1) {
            System.out.println("combat.java:attack()BUG: MISS:" + MISS + " HIT:" + HIT + " DODGE:" + DODGE);
            if (HIT < 0) {
                System.out.println("combat.java:attack()BUG: msHit:" + msHit + " wHit:" + wHit + " S:" + S + " e:" + e + " x:" + x + " mR;" + mR + " R:" + R + " uHit:" + uHit);
            }
            return null;
        }

        int N = new Random().nextInt(MISS + HIT + DODGE);

        if (N < MISS) {
            res.setHit(false);
        } else if (N < (MISS + HIT)) {
            //Angrepet traff.
            res.setHit(true);
            res.setDodged(false);
        } else {
            //Angrepet traff ikke og bommet ikke, altså det ble dodge.
            res.setHit(true);
            res.setDodged(true);
        }

        //Hvis vi traff, beregn mulighet for critical hit.
        int msCritical = (int) (aMS.getCritical() * aMS.getMsBonus().getCritical(attacker));
        S = attacker.getSkills().getCharacterAttribute(SkillList.Spirit.key()) + spiritBonus(attacker);
        S += attacker.getClothing().getGQspirit();

        if (criticalHit(msCritical, wHit, S)) {
            res.setCritical(true);
        } else {
            res.setCritical(false);
        }

        //Hvis vi traff og målet har shield utstyrt beregn mulighet for shield block.
        if (res.isHit() && !res.isDodged()) {

            int[] shield = target.getShield();

            //shield[0] = hitpoints til shield, 0 hvis NPC ikke har shield.
            if (shield[1] != 0) {
                //NPC har shield, beregn om det ble block eller ikke.
                int block = shield[2];

                N = new Random().nextInt(50 + block);

                if (N >= 50) {
                    res.setShieldBlock(true);
                } else {
                    res.setShieldBlock(false);
                }

            }
        }

        //Hvis vi traff beregn skade og pengeverdi av angrepet her.
        if (res.isHit() && !res.isDodged()) {

            int atkPwr = calculateAttackNPC(attacker);

            //Hvis critical hit gi attack bonus.
            if (res.isCritical()) {
                int bonus = 20 + (int) (10 * Math.random()); //20-30% bonus for critical.
                atkPwr += (atkPwr / 100) * bonus;
            }

            res.setDamage(atkPwr);
            res.setMoney(atkPwr * (target.getRank()));

            //Gi litt penger for NPCs med rank 0, som tanks.
            if (target.getRank() == 0) {
                res.setMoney(atkPwr / 3);
            }

            //Hvis NPC har rank 4 eller høyere gi litt mindre penger. Eller blir det for lett å tjene penger.
            if (target.getRank() >= 4) {
                res.setMoney((int) (res.getMoney() * 0.5));
            }
        }

        //Sjekk om angrepet skal resultere i criminal merking. UCGM kan ikke bli criminal.
        if (attacker.getFaction() == target.getFaction() && !attacker.isUCGM()) {
            res.setCriminal(true);
        }

        //SKILL TRAINING
        //Weapon Manipulation skill kan økes når et våpen brukes.
        SkillTraining.trainWeaponManipulation(attacker, false);

        //Tactics skill kan økes hvis vi traff målet.
        if (res.isHit()) {
            SkillTraining.trainTactics(attacker, false);
        }

        //Hvis vi bruker beam våpen kan Beam cartridge skill øke.
        if (weapon.getItemSubType() == ItemSubType.Beam) {
            SkillTraining.trainBeamCartridgeWeapon(attacker, DODGE, false);
        }

        //Hvis vi bruker shell våpen kan shell firing skill øke.
        if (weapon.getItemSubType() == ItemSubType.Shell) {
            SkillTraining.trainShellFiringWeapon(attacker, DODGE, false);
        }

        //Hvis vi bruker sniping våpen kan sniping skill øke.
        if (weapon.getItemSubType() == ItemSubType.Sniping) {
            SkillTraining.trainSniping(attacker, DODGE, false);
        }

        //Hvis vi bruker beam/shell/sniping våpen kan shooting skill øke.
        if (weapon.getItemSubType() == ItemSubType.Beam || weapon.getItemSubType() == ItemSubType.Shell) {

            SkillTraining.trainShooting(attacker, false);
        }

        //Hvis vi bruker CQB våpen kan CQB skill øke.
        if (weapon.getItemSubType() == ItemSubType.CQB) {
            SkillTraining.trainCQB(attacker, DODGE, false, res.isHit());
        }

        //Hvis vi bruker H2H våpen kan H2H skill øke.
        if (weapon.getItemSubType() == ItemSubType.H2H) {
            SkillTraining.trainH2H(attacker, DODGE, false, res.isHit());
        }

        SkillTraining.trainEngagement(attacker, DODGE, false);

        //Avhengig av avstand til målet kan vi øke Near/Ranged engagement skill.
        if (R <= 700) {
            SkillTraining.trainNearEngagement(attacker, DODGE, true);
        } else {
            SkillTraining.trainRangedEngagement(attacker, DODGE, true);
        }

        //Dersom vi traff målet kan strength øke.
        if (res.isHit()) {
            //Hvis spiller bruker CQB eller H2H gi bonus til strength.
            double M = 1.0;
            if (weapon.getItemSubType() == ItemSubType.CQB || weapon.getItemSubType() == ItemSubType.H2H) {
                M = 0.7;
            }

            SkillTraining.trainStrength(attacker, M, false);
        }

        //Hvis spiller fikk critical hit gi bonus til spirit.
        double spirit = 1.0;
        if (res.isCritical()) {
            spirit = 0.8;
        }

        SkillTraining.trainSpirit(attacker, spirit, false);

        return res;
    }

    /**
     * Beregner resultatet når en NPC angriper spiller. Oppdaterer også
     * lastTimeAttacked for målet. NB! Resultatet blir ikke påført spilleren.
     *
     * @param npc NPC som angriper.
     * @param target Spiller som blir angrepet.
     *
     * @return Resultatet, eller NULL hvis feil.
     */
    public static CombatResult attack(NPCvehicle npc, CharacterGame target) {

        target.setLastTimeAttacked();

        CombatResult r = new CombatResult();

        //Sjekk at målet er i et vehicle.
        if (target.getVehicle() == null) {
            return null;
        }

        //Stats for NPC.
        Template t = npc.getTemplate();

        //Hent stats for målet sitt MS/Vehicle.
        StatMS tMS = StatManager.getMsVehicleStat(target.getVehicle().getID());

        //Sjekk at vi har fått stats.
        if (tMS == null) {
            System.out.println("Combat.java: Missing stats.");
            return null;
        }

        //HIT = Sjansen til å treffe målet.
        int HIT = t.getHitChance();

        //Beregn målet sin mulighet til å dodge.
        int msDodge = (int) (tMS.getDodge() * msStatBonus(target, tMS) * engineBonus(target.getVehicle(), tMS) * tMS.getMsBonus().getDodge(target));
        int L = target.getSkills().getCharacterAttribute(SkillList.Luck.key()) + luckBonus(target);
        L += target.getClothing().getGQluck();

        //Sett X til AMBAC.
        int x = target.getSkills().getCombatSkill(SkillList.AMBAC.key());

        int R = (int) (npc.getPosisjon().distance(target.getPosisjon()) / 4); //4 game units = 1 meter.

        //DODGE = Sjansen for målet til å dodge angrep.
        int DODGE = dodge(msDodge, L, x, R);

        //Avgjør om angrepet bommet, traff eller ble dodget.
        int MISS = 20; //Sjansen til å bomme er alltid 20.

        int N = new Random().nextInt(MISS + HIT + DODGE);

        if (N < MISS) {
            r.setHit(false);
        } else if (N < (MISS + HIT)) {
            //Angrepet traff.
            r.setHit(true);
            r.setDodged(false);
        } else {
            //Angrepet traff ikke og bommet ikke, altså det ble dodge.
            r.setHit(true);
            r.setDodged(true);
        }

        //Hvis vi traff og målet har shield utstyrt beregn mulighet for shield block.
        if (r.isHit() && !r.isDodged()) {

            Vehicle vt = target.getVehicle();

            //Sjekk at målet har et shield utstyrt.
            Container sCon = vt.getEquippedItem(1);
            ItemContainer sIC = null;
            Weapon sW = null;

            if (sCon instanceof ItemContainer) {
                sIC = (ItemContainer) sCon;
            }
            if (sIC != null && sIC.getItem() instanceof Weapon) {
                sW = (Weapon) sIC.getItem();
            }

            if (sW != null && validItems.ItemList.getItem(sW.getID()).getItemSubType() == ItemSubType.Shield) {

                //Hent ut stats for shield.
                int shieldID = vt.getEquippedItem(1).getItem().getID();
                StatWeapon tShield = StatManager.getWeaponStat(shieldID);

                x = target.getSkills().getCombatSkill(SkillList.Defence.key());

                int tS = target.getSkills().getCharacterAttribute(SkillList.Spirit.key()) + spiritBonus(target);
                tS += target.getClothing().getGQspirit();

                if (shieldBlock(tShield.getBlockChance(), tS, x)) {
                    r.setShieldBlock(true);
                } else {
                    r.setShieldBlock(false);
                }
            }
        }

        //Hvis vi traff beregn skade og pengeverdi av angrepet her.
        if (r.isHit() && !r.isDodged()) {

            int atkPwr;

            //Våpen NPC har utstyrt.
            int w[] = npc.getWeapon();

            atkPwr = w[1];

            //Gi +/- 10% skade.
            int bonus = (int) ((Math.random() * 20) - 10);
            bonus = (atkPwr * bonus) / 100;

            r.setDamage(atkPwr + bonus);

            //Hvis målet er UCGM sett skade til 0.
//if ( target.getUCGM() == 0x3 || target.getUCGM() == 0x4 || target.getUCGM() == 0x5 ) r.setDamage(0);
        }

        //Dersom målet blokkerte angrepet med et shield kan defence skill øke.
        if (r.isShieldBlock()) {
            SkillTraining.trainDefence(target, false);
        }

        //Dersom målet dodget angrepet kan ambac.
        if (r.isDodged()) {
            SkillTraining.trainAmbac(target, HIT, true);
        }

        //Dersom målet blokkerte angrepet med et shield kan målets spirit øke.
        if (r.isShieldBlock()) {
            SkillTraining.trainSpirit(target, 2.0, false);
        }

        //Dersom målet dodget angrepet kan målets luck øke.
        if (r.isDodged()) {

            //Sett M basert på angripers treffsikkerhet.
            double M = 1.7 - (HIT / 200.0);

            if (M < 0.5) {
                M = 0.5;
            }

            SkillTraining.trainLuck(target, M, false);
        }

        return r;
    }

    /**
     * Denne metoden benyttes når en NPC angriper en annen NPC. NB! Resultatet
     * blir ikke påført NPC.
     *
     * @param attacker NPC som angriper.
     * @param target NPC som blir angrepet.
     * @return Resultat av angrepet eller NULL dersom angrepet ikke ble utført.
     */
    public static CombatResult attack(NPCvehicle attacker, NPCvehicle target) {

        CombatResult r = new CombatResult();

        //Sjekk at målet er gyldig dvs fremdeles i live.
        if (!target.isActive()) {
            return null;
        }

        //Hent ut stats for NPCs.
        Template attackerTemplate = attacker.getTemplate();
        Template targetTemplate = target.getTemplate();

        //HIT = Sjansen til å treffe målet.
        int HIT = attackerTemplate.getHitChance();

        //DODGE = Sjansen for målet til å dodge angrep.
        int DODGE = targetTemplate.getDodgeChance();

        //Avgjør om angrepet bommet, traff eller ble dodget.
        int MISS = 20; //Sjansen til å bomme er alltid 20.

        int N = new Random().nextInt(MISS + HIT + DODGE);

        if (N < MISS) {
            r.setHit(false);
        } else if (N < (MISS + HIT)) {
            //Angrepet traff.
            r.setHit(true);
            r.setDodged(false);
        } else {
            //Angrepet traff ikke og bommet ikke, altså det ble dodge.
            r.setHit(true);
            r.setDodged(true);
        }

        //Hvis vi traff og målet har shield utstyrt beregn mulighet for shield block.
        if (r.isHit() && !r.isDodged()) {

            int[] shield = target.getShield();

            //shield[0] = hitpoints til shield, 0 hvis NPC ikke har shield.
            if (shield[1] != 0) {
                //NPC har shield, beregn om det ble block eller ikke.
                int block = shield[2];

                N = new Random().nextInt(50 + block);

                if (N >= 50) {
                    r.setShieldBlock(true);
                } else {
                    r.setShieldBlock(false);
                }
            }
        }

        //Beregn attack power til NPC.
        if (r.isHit() && !r.isDodged()) {

            int atkPwr;

            //Våpen NPC har utstyrt.
            int w[] = attacker.getWeapon();
            atkPwr = w[1];

            //Gi +/- 10% skade.
            int bonus = (int) ((Math.random() * 20) - 10);
            bonus = (atkPwr * bonus) / 100;

            r.setDamage(atkPwr + bonus);
        }

        return r;
    }

    /**
     *
     * @return Neste attack ID som kan brukes.
     */
    public static int getAttackID() {
        return next_attack_id++;
    }

    /**
     * Denne metoden kalles når spiller utfører en criminal handling.
     *
     * @param player Spiller som skal merkes criminal.
     * @param kill Indikerer om criminal angrepet resulterte i kill shot eller
     * ikke.
     */
    public static void criminal(PlayerGame player, boolean kill) {

        player.getCharacter().setCriminal(true);

        //Oppdater rank.
        if (kill) {
            player.getCharacter().loseRank(1000);
        } else {
            player.getCharacter().loseRank(500);
        }

        int time = (int) (System.currentTimeMillis() / 1000); //Nåværende tidspunkt.

        //Kill shot = 2 timer ban. Angrep = 1 time ban.
        if (kill) {
            time += 2 * config.Server.criminal_timeout;
        } else {
            time += config.Server.criminal_timeout;
        }

        admin.ipblock.temporaryBlockIP(player.getIP(), time);

        //Merk kontoen som criminal.
        Account konto = userDB.ManageAccounts.getAccount(player.getAccountID());

        if (konto != null) {
            konto.setCriminal();
        }

        //Send opcode 0x8 til spiller som ble merket criminal.
        PacketData pd = new PacketData();

        pd.writeIntBE(0x2);
        pd.writeIntBE(player.getCharacter().getCharacterID());
        pd.writeByte((byte) 0x1);

        Packet svar8008 = new Packet(0x8008, pd.getData());

        player.sendPacket(svar8008);
    }

    /**
     * Beregner attack power for et angrep på en MS/Vehicle. Målet kan være
     * MS/vehicle som er i bruk eller parkert.
     *
     * @param attacker Character som angriper.
     * @param v MS/vehicle som blir angrepet.
     *
     * @return Attack power. -1 hvis feil.
     */
    private static int calculateAttackVehicle(CharacterGame attacker, Vehicle v) {

        //Sjekk at angriper er i et vehicle og har et våpen utstyrt.
        Vehicle av = attacker.getVehicle();
        if (av == null) {
            return -1;
        }
        if (av.getEquippedItem(av.getActiveSlot()) == null) {
            return -1;
        }

        //wID = Item ID til utstyrt våpen.
        int wID = av.getEquippedItem(av.getActiveSlot()).getItem().getID();

        GameItem weapon = ItemList.getItem(wID);
        if (weapon == null || weapon.getItemType() != ItemType.Weapon) {
            return -1;
        }

        StatWeapon stat = StatManager.getWeaponStat(wID);
        if (stat == null) {
            return -1;
        }

        StatMS msStat = StatManager.getMsVehicleStat(av.getID());
        if (msStat == null) {
            return -1;
        }

        //Verdier som skal brukes i attack power formelene lagres her.
        int atkBase, T, S, x, atkUpgrade, defUpgrade;

        atkBase = stat.getAttack();
        T = attacker.getSkills().getCombatSkill(SkillList.Tactics.key());
        S = attacker.getSkills().getCharacterAttribute(SkillList.Strength.key()) + strengthBonus(attacker);
        S += attacker.getClothing().getGQstrength();
        atkUpgrade = upgrades[av.getUpgradePower() % 9]; //%9 for å hindre out of bounds, i tilfelle.
        defUpgrade = upgrades[v.getUpgradeDefence() % 9];

        //x skal settes til en skill avhengig av våpen type.
        if (weapon.getItemSubType() == ItemSubType.Beam || weapon.getItemSubType() == ItemSubType.Shell) {
            //Shooting
            x = attacker.getSkills().getCombatSkill(SkillList.Shooting.key());
        } else if (weapon.getItemSubType() == ItemSubType.Sniping) {
            //Sniping
            x = attacker.getSkills().getCombatSkill(SkillList.Sniping.key());
        } else if (weapon.getItemSubType() == ItemSubType.CQB) {
            //CQB
            x = attacker.getSkills().getCombatSkill(SkillList.CQB.key());
        } else if (weapon.getItemSubType() == ItemSubType.H2H) {
            //Hand to Hand
            x = attacker.getSkills().getCombatSkill(SkillList.HandToHandCombat.key());
        } else {
            x = 0;
        }

        //Påfør eventuell attack bonus for brukt våpen til atkUpgrade.
        //Dette er det samme som å skulle påføre bonus etter at attack power er beregnet.
        atkUpgrade += msStat.getAttackBonus(wID);

        //Dersom angriper bruker beam weapon og målet har anti beam coating skal den legges til defUpgrade
        if (weapon.getItemSubType() == ItemSubType.Beam) {
            StatMS targetStat = StatManager.getMsVehicleStat(v.getID());
            if (targetStat == null) {
                return -1;
            }

            if (targetStat.isAntiBeamCoating()) {
                defUpgrade += targetStat.getAntiBeamCoating();
            }
        }

        //Da er alle verdier satt til å kunne beregne attack power.
        return attackPower(atkBase, T, S, x, atkUpgrade, defUpgrade);

    }

    /**
     * Beregner attack power for et angrep på taxi/transport.
     *
     * @param attacker Spiller som angriper.
     * @param t Taxi/transport som blir angrepet.
     *
     * @return Attack power for angrepet.
     */
    private static int calculateAttackTaxiTransport(CharacterGame attacker, TaxiTransport t) {

        //OK å gjøre det slik som dette. Vehicle har de samme attributtene som taxi/transport
        //og resultatet er gyldig.
        Vehicle v = new Vehicle(t.getID());

        int atkPwr = calculateAttackVehicle(attacker, v);

        ContainerList.removeContainer(v.getInventory().getID());
        ContainerList.removeContainer(v.getWeaponsRoom().getID());

        return atkPwr;
    }

    /**
     * Beregner attack power for et angrep på en NPC.
     *
     *
     * @param attacker Character som angriper.
     *
     * @return Attack power. -1 hvis feil.
     */
    private static int calculateAttackNPC(CharacterGame attacker) {

        //Bruk metoden calculateAttackVehicle til å beregne resultatet. Vi bare "faker" et vehicle.
        Vehicle dummyV = new Vehicle(0x64190); //Dummy er RGM-79

        int atkPwr = calculateAttackVehicle(attacker, dummyV);

        //Fjern dummy vehicle fra spillet.
        ContainerList.removeContainer(dummyV.getInventory().getID());
        ContainerList.removeContainer(dummyV.getWeaponsRoom().getID());

        return atkPwr;
    }

    /**
     * Beregner attack power for et angrep, som definert i skills.doc
     * dokumentet.
     *
     * @param atkBase Base attack power til våpen brukt.
     * @param T Tactics skill til angriper.
     * @param S Strength til angriper.
     * @param x Attack skill som er brukt.
     * @param atkUpgrade Antall PROSENT i angripers attack upgrade.
     * @param defUpgrade Antall PROSENT i målets defense upgrade.
     *
     * @return Total attack power
     *
     * Følgende gjelder for bonus ved 130 skill.
     *
     * Tactics = +5% Attack skill = +10%
     */
    private static int attackPower(int atkBase, int T, int S, int x, int atkUpgrade, int defUpgrade) {

        int atkPower = (int) ((double) atkBase * f(T)) / 2;

        atkPower += (int) ((double) atkBase * f(x));

        atkPower += atkBase * S / 700;

        atkPower += atkBase;

        //Påfør ATK/DEF upgrade.
        atkPower = atkPower + atkPower / 100 * atkUpgrade - atkPower / 100 * defUpgrade;

        // +/- 1~5% attack power.
        if (Math.random() < 0.5) {
            //Reduser attack power.
            atkPower -= atkPower / 100 * 5 * Math.random();
        } else {
            //Øk attack power.
            atkPower += atkPower / 100 * 5 * Math.random();
        }

        //Påfør eventuell 130.0 skill bonus.
        if (T >= 1300) {
            atkPower += (atkPower / 100) * 5;
        }
        if (x >= 1300) {
            atkPower += (atkPower / 100) * 10;
        }

        return atkPower;
    }

    /**
     * Bereger om det ble critcal hit eller ikke.
     *
     * @param msCritical MS/Vehicle sin sjanse til å få critical hit.
     * @param wHit Våpenet sin treffsikkerhet.
     * @param S Spirit
     *
     * @return TRUE hvis critical hit, FALSE hvis ikke.
     */
    private static boolean criticalHit(int msCritical, int wHit, int S) {

        int critical = (int) Math.sqrt((double) (msCritical * wHit));

        critical = (critical * S) / 700;

        /*
         * critical = sjanse til å få critical hit.
         * Sjanse til å ikke få critical hit er alltid 100.
         * 
         * Trekk et tilfeldig nummer mellom 0 til 100+critical.
         */
        int N = new Random().nextInt(100 + critical);

        if (N >= 100) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Beregner om det ble shield block eller ikke.
     *
     * @param sBLK Shield sin sjanse for å blokkere.
     * @param S Spirit
     * @param x Defense skill.
     *
     * @return true hvis blokkert, false hvis ikke.
     */
    private static boolean shieldBlock(int sBLK, int S, int x) {

        int block = (sBLK * S) / 700;

        block += ((double) sBLK * f(x));

        /*
         * block = Sjanse for å blokkere med shield.
         * Sjansen for å ikke blokkere er alltid 50.
         * 
         * Trekk et tilfeldig nummer fra 0 til 50+block for å avgjøre resultat.
         */
        int N = new Random().nextInt(50 + block);

        if (N >= 50) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Beregner sjansen for å dodge et angrep.
     *
     * @param msDodge MS/Vehicle sin dodge sjanse.
     * @param L Luck
     * @param x AMBAC skill
     * @param R Avstand til angriper, i meter.
     *
     * @return Sjanse for å dodge angrep.
     *
     * AMBAC = 130.0 gir +10% bonus.
     */
    private static int dodge(int msDodge, int L, int x, int R) {

        int d = (int) (Math.sqrt((1 + f(x)) * msDodge * L) + msDodge);

        int bonus = R / 100; //1% bonus pr 100 meter avstand.
        if (x >= 1300) {
            bonus += 10;
        }

        d += (d / 100) * bonus;

        return d;
    }

    /**
     * Beregner sjanse for å treffe et mål.
     *
     * @param msHit MS sin accuracy.
     * @param wHit Våpen sin accuracy.
     * @param S Spirit
     * @param e Engagement.
     * @param g Near/Ranged engagement.
     * @param x Skills avhengig av våpen brukt. Beam, shell...osv.
     * @param mR Max Range til våpen brukt, i meter.
     * @param R Avstand til målet, i meter.
     * @param uHit = Hit upgrade, antall PROSENT.
     *
     * @return Sjanse til å treffe målet.
     *
     * Følgende regler gjelder for bonus ved 130.0 skill. Engagement = + 5%
     * bonus beam/shell etc = +10% bonus
     */
    private static int hit(int msHit, int wHit, int S, int e, int g, int x, int mR, int R, int uHit) {

        int hit = (int) (Math.sqrt(msHit * S) * f(e));
        hit += (int) (Math.sqrt(wHit * S) * f(x + g));

        /*
         * hit = sjanse for å treffe.
         * Gi 2% bonus for hver 1/10 av max range som er dekket inn.
         */
        if (mR < 10) {
            mR = 10; //For sikkerhets skyld, unngår division by zero.
        }
        int bonus = ((mR - R) / (mR / 10)) * 2;
        //130.0 skill bonus
        if (e >= 1300) {
            bonus += 5;
        }
        if (g >= 1300) {
            bonus += 5;
        }
        if (x >= 1300) {
            bonus += 10;
        }

        hit += (hit * bonus) / 100;

        //Påfør Hit upgrade bonus.
        hit += (hit * uHit) / 100;

        //Dersom angriper NPC/spiller utenfor rekkevidde pga f.eks LAG, kan hit bli negativ. Isåfall sett til 0.
        if (hit > 0) {
            return hit;
        } else {
            return 0;
        }
    }

    /**
     * Beregner hvor mange prosent bonus spiller skal få til MS sin
     * accuracy/dodge stat. Denne metoden sjekker også for faction lock. Dersom
     * spiller bruker MS fra feil faction vil han få null bonus, samme som når
     * spiller ikke har nok MS skill.
     *
     * @param chara Spiller
     * @param stat Stat for spillers MS.
     *
     * @return 1.0 til 1.3 som angir antall prosent bonus spiller får. Hvis
     * spiller ikke har nok MS skill til å bruke oppgitt MS returneres 0.0
     */
    private static double msStatBonus(CharacterGame chara, StatMS stat) {

        //Sjekk faction lock.
        //Faction satt til -1 betyr ingen faction lock.
        if (chara.getFaction() != stat.getFaction() && stat.getFaction() != -1) {
            return 0;
        }

        //Differansen mellom spillers MS skill og krevet MS skill.
        int diff = chara.getSkills().getCombatSkill(SkillList.MobileSuit.key()) - stat.getSkill();

        //Sjekk om spiller har høy nok MS skill.
        if (diff < 0) {
            return 0;
        }

        double bonus = 1.0 + 0.3 * f(diff);

        return bonus;
    }

    /**
     * Beregner hvor mye bonus spiller skal få til DODGE stat basert på engine
     * type. Denne bonus KOMBINERES med MS STAT BONUS!
     *
     * @param v Vehicle til spiller.
     * @param stat Stats for spillers vehicle.
     *
     * @return 1.0 og oppover, dersom ingen bonus returneres 1.0.
     */
    private static double engineBonus(Vehicle v, StatMS stat) {

        double bonus = 1.0;

        //Sjek Vehicle != null. Dersom spiller er i taxi/transport vil Vehicle være null.
        if (v == null || stat == null) {
            return bonus;
        }

        //Sjekk at spiller er i MS. MA/BB får ingen bonus.
        GameItem gi = ItemList.getItem(v.getID());
        if (gi.getItemSubType() != ItemSubType.MS) {
            return 1.0;
        }

        StatEngine engine = StatManager.getEngineStat(v.getEngine());
        if (engine == null) {
            return 1.0; //Ingen bonus for denne engine.
        }
        if (engine.getLevel() >= 2) {
            //Gi LVL2 bonus.
            if (stat.getDodge() < 70) {
                bonus += 0.4;
            } else if (stat.getDodge() < 90) {
                bonus += 0.2;
            } else if (stat.getDodge() < 100) {
                bonus += 0.15;
            } else if (stat.getDodge() >= 100) {
                bonus += 0.1;
            }
        }

        if (engine.getLevel() >= 3) {
            //Gi LVL3 bonus.
            if (stat.getArmor() < 25000) {
                bonus += 0.2;
            } else if (stat.getArmor() < 30000) {
                bonus += 0.15;
            } else if (stat.getArmor() >= 30000) {
                bonus += 0.1;
            }
        }

        return bonus;
    }

    /**
     * Beregner hvor mye strength bonus en spiller skal få.
     *
     * @param c Spiller dette gjelder.
     *
     * @return Strength bonus. Oppgitt i skillpoints.
     */
    private static int strengthBonus(CharacterGame c) {

        //Strength bonus for luck.
        if (c.getSkills().getCharacterAttribute(SkillList.Luck.key()) >= 700) {
            return 150;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Luck.key()) >= 600) {
            return 100;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Luck.key()) >= 500) {
            return 50;
        }

        //Strength bonus for spirit.
        if (c.getSkills().getCharacterAttribute(SkillList.Spirit.key()) >= 700) {
            return 150;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Spirit.key()) >= 600) {
            return 150;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Spirit.key()) >= 500) {
            return 50;
        }

        return 0;
    }

    /**
     * Beregner hvor mye spirit bonus en spiller skal få.
     *
     * @param c Spiller dette gjelder.
     *
     * @return Spirit bonus. Oppgitt i skillpoints.
     */
    private static int spiritBonus(CharacterGame c) {

        //Spirit bonus for luck.
        if (c.getSkills().getCharacterAttribute(SkillList.Luck.key()) >= 700) {
            return 250;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Luck.key()) >= 600) {
            return 150;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Luck.key()) >= 500) {
            return 50;
        }

        //Spirit bonus for strength.
        if (c.getSkills().getCharacterAttribute(SkillList.Strength.key()) >= 700) {
            return 100;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Strength.key()) >= 600) {
            return 75;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Strength.key()) >= 500) {
            return 50;
        }

        return 0;
    }

    /**
     * Beregner hvor mye luck bonus en spiller skal få.
     *
     * @param c Spiller dette gjelder.
     *
     * @return Luck bonus. Oppgitt i skillpoints.
     */
    private static int luckBonus(CharacterGame c) {

        //Luck bonus for strength.
        if (c.getSkills().getCharacterAttribute(SkillList.Strength.key()) >= 700) {
            return 0;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Strength.key()) >= 600) {
            return 0;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Strength.key()) >= 500) {
            return 0;
        }

        //Luck bonus for spirit.
        if (c.getSkills().getCharacterAttribute(SkillList.Spirit.key()) >= 700) {
            return 250;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Spirit.key()) >= 600) {
            return 100;
        }
        if (c.getSkills().getCharacterAttribute(SkillList.Spirit.key()) >= 500) {
            return 100;
        }

        return 0;
    }

    /**
     * Funksjonen f som definert i skills.doc dokumentet.
     *
     * @param x
     * @return
     */
    private static double f(int x) {
        return Math.sqrt((double) x) / Math.sqrt(1300);
    }
}
