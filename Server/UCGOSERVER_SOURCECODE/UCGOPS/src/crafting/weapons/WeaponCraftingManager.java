package crafting.weapons;

import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen holder all crafting info for våpen i tillegg til å beregne
 * resultat av crafting.
 */
public class WeaponCraftingManager {

    /**
     * Holder en liste over crafting stats for alle våpen.
     *
     * KEY=Item ID for våpen.
     */
    private static HashMap<Integer, WeaponCraftingStat> craftingStats = new HashMap<Integer, WeaponCraftingStat>();

    public static void execute() {

        EFweaponStats();
        ZEONweaponStats();
        COMMONweaponStats();

    }

    /**
     * Beregner resultatet av våpen crafting.
     *
     * @param itemID ID til våpen som skal craftes.
     * @param skill Spillerens weapons production skill.
     *
     * @return Resultatet av crafting, null hvis feil.
     */
    public static WeaponCraftingResult crafting(int itemID, int skill) {

        WeaponCraftingResult res = new WeaponCraftingResult();

        WeaponCraftingStat w = getCraftingStat(itemID);

        if (w == null) {
            return null;
        }

        //Sjekk at spillers skill er høy nok.
        if (w.getMinSkill() > skill) {
            System.out.println("\nWeapons Crafting error: Insufficient skill for item ID:" + itemID);
            return null;
        }

        //Differansen mellom spillers skill og minimum skill.
        int dSkill = skill - w.getMinSkill();

        //Sjanse for å få standard versjon.
        int standardSjanse = (int) (75 * f(skill));

        //Sjanse for å få EX versjon.
        int ExSjanse = 0;

        //Beregn EX sjanse kun hvis EX versjon er tilgjengelig.
        if (w.getItemExID() != 0) {

            ExSjanse = (int) (60 * f(dSkill));
        }

        //Sjanse til å mislykkes med crafting.
        int mislykketSjanse = w.getDifficulty();

        Random r = new Random();

        int n = r.nextInt(mislykketSjanse + standardSjanse + ExSjanse);

        if (n <= mislykketSjanse) {
            //Crafting mislykkes.
            res.setSuccess(false);
            res.setItemID(w.getMaterialID());

            int antall = (w.getMaterialLoss() * w.getMaterialAmount()) / 100;
            antall = w.getMaterialAmount() - antall;

            res.setAntall(antall);
        } else if (n <= (mislykketSjanse + standardSjanse)) {
            //Fikk standard versjon av våpen.
            res.setSuccess(true);
            res.setAntall(1);
            res.setItemID(itemID);
        } else {
            //Fikk EX versjon.
            res.setSuccess(true);
            res.setAntall(1);
            res.setItemID(w.getItemExID());
        }

        return res;
    }

    /**
     * Returnerer crafting stats for oppgitt våpen.
     *
     * @param itemID Item ID til våpen.
     * @return Crafting stats for oppgitt våpen eller null hvis ugyldig ID.
     */
    public static WeaponCraftingStat getCraftingStat(int itemID) {
        return craftingStats.get(itemID);
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

    /**
     * Setter crafting stats for EF våpen.
     */
    private static void EFweaponStats() {

        /*
         * Material guide.
         *titanium alloy = 0x07C830
         *titanium ceramic composite = 0x07C832
         *super high tensile steel = 0x07C831 
         */
        //minSkill, difficulty, materialID, materialAmount, materialLoss, itemExID
        craftingStats.put(0x4465D, new WeaponCraftingStat(100, 1, 0x7C831, 2, 50, 0x4468F)); //MS Chest vulcan
        craftingStats.put(0x57E40, new WeaponCraftingStat(200, 10, 0x81652, 5, 50, 0x57E54)); //RX-78 Shield
        craftingStats.put(0x57E43, new WeaponCraftingStat(200, 10, 0x81652, 5, 50, 0x57E57)); //RX-79G Shield
        craftingStats.put(0x57E44, new WeaponCraftingStat(200, 10, 0x81652, 5, 50, 0x57E58)); //Ez8 Shield
        craftingStats.put(0x57E42, new WeaponCraftingStat(200, 10, 0x81651, 5, 50, 0x57E56)); //RGM-79C Shield
        craftingStats.put(0x57E41, new WeaponCraftingStat(200, 10, 0x81651, 5, 50, 0x57E55)); //RGM-79G Shield
        craftingStats.put(0x44665, new WeaponCraftingStat(250, 10, 0x7C831, 5, 50, 0x44697)); //EF Throwing device
        craftingStats.put(0x445D0, new WeaponCraftingStat(300, 10, 0x7C831, 10, 50, 0x44602)); //Gun launcher
        craftingStats.put(0x44656, new WeaponCraftingStat(350, 15, 0x07C831, 10, 50, 0x44688)); //Beam spray gun
        craftingStats.put(0x445EE, new WeaponCraftingStat(400, 15, 0x07C831, 15, 50, 0x44620)); //GM Rifle
        craftingStats.put(0x445CF, new WeaponCraftingStat(450, 15, 0x07C831, 15, 50, 0x44601)); //Long rifle 
        craftingStats.put(0x445E7, new WeaponCraftingStat(500, 15, 0x07C831, 20, 50, 0x44619)); //Beam saber RGM-79G  
        craftingStats.put(0x44658, new WeaponCraftingStat(500, 15, 0x07C831, 20, 50, 0x4468A)); //Beam saber RGM-79C
        craftingStats.put(0x445DB, new WeaponCraftingStat(550, 20, 0x07C832, 20, 50, 0x4460D)); //Longrange Beam Rifle
        craftingStats.put(0x445ED, new WeaponCraftingStat(600, 20, 0x07C831, 20, 50, 0x4461F)); //Hyper Bazooka
        craftingStats.put(0x445C1, new WeaponCraftingStat(600, 20, 0x07C831, 20, 50, 0x445F3)); //Hyper Bazooka ground type        
        craftingStats.put(0x445C5, new WeaponCraftingStat(650, 20, 0x07C831, 30, 50, 0x445F7)); //HFW-GMG
        craftingStats.put(0x4466F, new WeaponCraftingStat(650, 20, 0x07C831, 30, 50, 0x446A1)); //NFHI GMG
        craftingStats.put(0x445C7, new WeaponCraftingStat(700, 20, 0x07C832, 20, 50, 0x445F9)); //RGM-79 Beam gun
        craftingStats.put(0x445DD, new WeaponCraftingStat(750, 30, 0x07C832, 30, 50, 0x4460F)); //Sniping Beam Rifle
        craftingStats.put(0x4465F, new WeaponCraftingStat(800, 30, 0x07C832, 40, 50, 0x44691)); //MS Beam cannon
        craftingStats.put(0x445C2, new WeaponCraftingStat(850, 30, 0x07C832, 40, 50, 0x445F4)); //Beam saber RX-78
        craftingStats.put(0x445EC, new WeaponCraftingStat(850, 30, 0x07C832, 40, 50, 0x4461E)); //Beam saber RX-79G
        craftingStats.put(0x445E0, new WeaponCraftingStat(900, 40, 0x07C832, 60, 50, 0x44612)); //RGM-79L Beam rifle


        /*craftingStats.put(0x446FB, new WeaponCraftingStat(1000, 20, 0x07C84E, 100, 10, 0)); //MSZ-006 HML
        craftingStats.put(0x446FA, new WeaponCraftingStat(950, 20, 0x07C844, 100, 10, 0)); //MSZ-006 Beam Rifle
        craftingStats.put(0x446F7, new WeaponCraftingStat(950, 20, 0x07C844, 100, 10, 0)); //MSN-00100 Beam Rifle
        craftingStats.put(0x446F6, new WeaponCraftingStat(1000, 20, 0x07C844, 200, 10, 0)); //RX-178 Long Rifle
        craftingStats.put(0x57E7D, new WeaponCraftingStat(1000, 10, 0x07C833, 10, 50, 0)); //RX-78GP02A Shield
        craftingStats.put(0x446EF, new WeaponCraftingStat(1000, 20, 0x07C844, 200, 10, 0)); //RX-78GP02A Beam Bazooka
        craftingStats.put(0x446F4, new WeaponCraftingStat(950, 20, 0x07C844, 25, 10, 0)); //RX-178 Beam Saber
        craftingStats.put(0x446F1, new WeaponCraftingStat(950, 20, 0x07C844, 100, 10, 0)); //RX-178 Beam Rifle TITANS        
        craftingStats.put(0x446F0, new WeaponCraftingStat(950, 20, 0x07C844, 100, 10, 0)); //RX-178 Beam Rifle AEUG
        craftingStats.put(0x57E7E, new WeaponCraftingStat(950, 10, 0x07C832, 20, 50, 0)); //RX-178 AEUG Shield
        craftingStats.put(0x57E7F, new WeaponCraftingStat(950, 10, 0x07C832, 20, 50, 0)); //RX-178 TITAN Shield
        craftingStats.put(0x446ED, new WeaponCraftingStat(950, 100, 0x07C832, 300, 30, 0)); //RX-78GP01-Fb Beam Rifle
        craftingStats.put(0x446EE, new WeaponCraftingStat(950, 20, 0x07C844, 100, 10, 0)); //RX-78GP01-Fb Blash Experimental
        craftingStats.put(0x57E7C, new WeaponCraftingStat(950, 10, 0x07C832, 10, 50, 0)); //RX-78GP01-Fb Shield*/
        craftingStats.put(0x445C8, new WeaponCraftingStat(949, 260, 0x07C832, 300, 30, 0x445FA)); //Blash
        craftingStats.put(0x446CA, new WeaponCraftingStat(949, 260, 0x07C832, 300, 30, 0x446CD)); //Blash Hvit
        craftingStats.put(0x446CB, new WeaponCraftingStat(949, 20, 0x07C843, 10, 30, 0)); //Alex Gundam Beam Rifle
        craftingStats.put(0x446CC, new WeaponCraftingStat(949, 260, 0x07C832, 300, 30, 0x446CF)); //Blash Blå
        craftingStats.put(0x445D6, new WeaponCraftingStat(949, 200, 0x07C832, 200, 35, 0x44608)); //RX-79G Beam rifle
        craftingStats.put(0x446E2, new WeaponCraftingStat(949, 200, 0x07C832, 200, 35, 0x446E3)); //RX-79G Beam rifle Farget

        craftingStats.put(0x446E4, new WeaponCraftingStat(948, 170, 0x07C832, 100, 35, 0x446E5)); //RGM-79L Beam rifle Farget
        craftingStats.put(0x445D9, new WeaponCraftingStat(899, 150, 0x07C832, 95, 35, 0x4460B)); //RX-78-1 Beam rifle        
        craftingStats.put(0x445DE, new WeaponCraftingStat(802, 150, 0x07C832, 87, 45, 0x44610)); //Sniping Beam Rifle custom
        craftingStats.put(0x445D2, new WeaponCraftingStat(801, 80, 0x07C832, 40, 50, 0x44604)); //100mm Machinegun
        craftingStats.put(0x4465E, new WeaponCraftingStat(750, 80, 0x07C843, 2, 100, 0x44690)); //Ball cannon
        craftingStats.put(0x445D5, new WeaponCraftingStat(750, 100, 0x07C832, 56, 50, 0x44607)); //180mm Cannon
        craftingStats.put(0x446D8, new WeaponCraftingStat(750, 100, 0x07C832, 56, 50, 0x446DA)); //180mm Cannon Mørkgrønn
        craftingStats.put(0x446D9, new WeaponCraftingStat(750, 100, 0x07C832, 56, 50, 0x446DB)); //180mm Cannon Lysgrå        
        craftingStats.put(0x445C9, new WeaponCraftingStat(702, 110, 0x07C832, 34, 70, 0x445FB)); //RX-77 Beam rifle        
        craftingStats.put(0x446E6, new WeaponCraftingStat(699, 120, 0x07C832, 39, 70, 0x446E7)); //Longrange Beam Rifle Farget                  

        craftingStats.put(0x445C0, new WeaponCraftingStat(298, 30, 0x07C830, 3, 50, 0x445F2)); //A.E.Br.G-Sc-L

    }

    /**
     *
     * Setter crafting stats for Zeon våpen.
     */
    private static void ZEONweaponStats() {

        /*
         * Material guide.
         *titanium alloy = 0x07C830
         *titanium ceramic composite = 0x07C832
         *super high tensile steel = 0x07C831 
         */
        //minSkill, difficulty, materialID, materialAmount, materialLoss, itemExID
        craftingStats.put(0x57E46, new WeaponCraftingStat(200, 10, 0x81652, 5, 50, 0x57E5A)); //MS-14 Shield
        craftingStats.put(0x57E45, new WeaponCraftingStat(200, 10, 0x81651, 5, 50, 0x57E59)); //MS-07 Shield
        craftingStats.put(0x44666, new WeaponCraftingStat(250, 10, 0x07C831, 5, 50, 0x44698)); //Zeon throwing device
        craftingStats.put(0x44660, new WeaponCraftingStat(300, 10, 0x07C831, 10, 50, 0x44692)); //Hammer gun
        craftingStats.put(0x445C4, new WeaponCraftingStat(350, 10, 0x07C831, 10, 50, 0x445F6)); //ZMP-47D
        craftingStats.put(0x445CA, new WeaponCraftingStat(400, 10, 0x07C831, 15, 50, 0x445FC)); //ZMP-50D
        craftingStats.put(0x445E5, new WeaponCraftingStat(450, 15, 0x07C831, 15, 50, 0x44617)); //Heat hawk
        craftingStats.put(0x445EF, new WeaponCraftingStat(500, 15, 0x07C831, 15, 50, 0x44621)); //Heat rod
        craftingStats.put(0x445F0, new WeaponCraftingStat(500, 15, 0x07C831, 20, 50, 0x44622)); //75mm Machinegun
        craftingStats.put(0x445EA, new WeaponCraftingStat(500, 15, 0x07C831, 20, 50, 0x4461C)); //MS-07 Heat sword
        craftingStats.put(0x445F1, new WeaponCraftingStat(550, 20, 0x07C831, 30, 50, 0x44623)); //M-120AS
        craftingStats.put(0x445E9, new WeaponCraftingStat(600, 20, 0x07C831, 30, 50, 0x4461B)); //Zaku Bazooka
        craftingStats.put(0x44661, new WeaponCraftingStat(650, 20, 0x07C832, 20, 50, 0x44693)); //MS-06R beam rifle
        craftingStats.put(0x445E6, new WeaponCraftingStat(650, 20, 0x07C831, 35, 50, 0x44618)); //MS-09 Heat saber
        craftingStats.put(0x445D8, new WeaponCraftingStat(700, 20, 0x07C831, 40, 50, 0x4460A)); //MS Claw
        craftingStats.put(0x44657, new WeaponCraftingStat(750, 30, 0x07C831, 40, 50, 0x44689)); //MMP-78  
        craftingStats.put(0x445DC, new WeaponCraftingStat(800, 30, 0x07C832, 30, 50, 0x4460E)); //Arm Punch MSM-08
        craftingStats.put(0x4465C, new WeaponCraftingStat(800, 30, 0x07C832, 30, 50, 0x4468E)); //MS Torpedo 
        craftingStats.put(0x445DF, new WeaponCraftingStat(800, 30, 0x07C831, 50, 50, 0x44611)); //Boomerang cutter
        craftingStats.put(0x445CB, new WeaponCraftingStat(850, 30, 0x07C832, 30, 50, 0x445FD)); //MMP-80
        craftingStats.put(0x445D7, new WeaponCraftingStat(900, 40, 0x07C832, 60, 50, 0x44609)); //Giant bazooka
        craftingStats.put(0x445D4, new WeaponCraftingStat(900, 40, 0x07C832, 60, 50, 0x44606)); //MS bias MPBG

        //craftingStats.put(0x446FC, new WeaponCraftingStat(1000, 20, 0x07C84E, 100, 10, 0)); //AMX-107 Beam Rifle
        craftingStats.put(0x445E3, new WeaponCraftingStat(951, 260, 0x07C832, 250, 50, 0x44615)); //MS-14 Beam rifle
        craftingStats.put(0x446D0, new WeaponCraftingStat(951, 260, 0x07C832, 250, 50, 0x446D3)); //MS-14 Beam rifle Hvit
        craftingStats.put(0x446D1, new WeaponCraftingStat(951, 260, 0x07C832, 250, 50, 0x446D4)); //MS-14 Beam rifle Blå
        craftingStats.put(0x446D2, new WeaponCraftingStat(951, 260, 0x07C832, 250, 50, 0x446D5)); //MS-14 Beam rifle Rød
        craftingStats.put(0x4465A, new WeaponCraftingStat(904, 200, 0x07C832, 100, 50, 0x4468C)); //MS-14 Prototype Beam rifle
        craftingStats.put(0x445E8, new WeaponCraftingStat(901, 200, 0x07C832, 95, 50, 0x4461A)); //Beam naginata

        craftingStats.put(0x44662, new WeaponCraftingStat(750, 160, 0x07C832, 56, 60, 0x44694)); //Beam bazooka
        craftingStats.put(0x445C6, new WeaponCraftingStat(750, 90, 0x07C843, 2, 100, 0x445F8)); //Magella top cannon

        craftingStats.put(0x446D6, new WeaponCraftingStat(698, 150, 0x07C831, 78, 50, 0x446D7)); //Giant bazooka Hvit
        craftingStats.put(0x446E8, new WeaponCraftingStat(951, 200, 0x07C831, 78, 50, 0x446E9)); //Raketen Bazooka        

        craftingStats.put(0x446DC, new WeaponCraftingStat(501, 60, 0x07C832, 7, 50, 0x446DF)); //MMP-80 Hvit
        craftingStats.put(0x446DD, new WeaponCraftingStat(501, 60, 0x07C832, 7, 50, 0x446E0)); //MMP-80 Rød
        craftingStats.put(0x446DE, new WeaponCraftingStat(501, 60, 0x07C832, 7, 50, 0x446E1)); //MMP-80 Blå                                                    

        craftingStats.put(0x445EB, new WeaponCraftingStat(251, 40, 0x07C830, 2, 50, 0x4461D)); //MS Arm Machinegun               

        craftingStats.put(0x57E6F, new WeaponCraftingStat(102, 25, 0x07C832, 3, 50, 0x57E70)); //MS-14 Shield Farget
        craftingStats.put(0x57E71, new WeaponCraftingStat(102, 25, 0x07C832, 3, 50, 0x57E72)); //MS-14 Shield Farget
        craftingStats.put(0x57E73, new WeaponCraftingStat(102, 25, 0x07C832, 3, 50, 0x57E74)); //MS-14 Shield Farget

    }

    /**
     *
     * Registrerer crafting stats for våpen felles for EF og Zeon.
     */
    private static void COMMONweaponStats() {

        /*
         * Material guide.
         *titanium alloy = 0x07C830
         *titanium ceramic composite = 0x07C832
         *super high tensile steel = 0x07C831 
         */
        //minSkill, difficulty, materialID, materialAmount, materialLoss, itemExID                       
        craftingStats.put(0x445D3, new WeaponCraftingStat(848, 170, 0x07C832, 50, 50, 0x44605)); //MS Mega Particle Beam Gun
        craftingStats.put(0x445E1, new WeaponCraftingStat(599, 70, 0x07C832, 12, 75, 0x44613)); //MS Rocket Launcher
        craftingStats.put(0x445CC, new WeaponCraftingStat(150, 10, 0x07C831, 5, 50, 0x445FE)); //MS Cannon
        craftingStats.put(0x445C3, new WeaponCraftingStat(100, 1, 0x07C831, 2, 50, 0x445F5)); //Head vulcans
        craftingStats.put(0x445E2, new WeaponCraftingStat(100, 1, 0x07C831, 2, 50, 0x44614)); //MS Grapple        
        craftingStats.put(0x445CE, new WeaponCraftingStat(100, 1, 0x07C831, 1, 100, 0x44600)); //Tank/Fighter Machinegun
        craftingStats.put(0x44653, new WeaponCraftingStat(50, 1, 0x07C831, 1, 100, 0)); //Mining Drill parts
        craftingStats.put(0x44664, new WeaponCraftingStat(50, 1, 0x7C831, 1, 100, 0x44696)); //Tank/Fighter Missile
        craftingStats.put(0x445CD, new WeaponCraftingStat(0, 1, 0x7C831, 1, 100, 0x445FF)); //Tank/Fighter Cannon        

        craftingStats.put(0x445D1, new WeaponCraftingStat(900, 170, 0x07C832, 200, 50, 0)); //BB MPBG
        craftingStats.put(0x445E4, new WeaponCraftingStat(900, 170, 0x07C832, 200, 50, 0)); //BB MG

        /*craftingStats.put(0x446F3, new WeaponCraftingStat(900, 20, 0x07C832, 100, 50, 0)); //RX-178 Hyper Bazooka.
        craftingStats.put(0x446F2, new WeaponCraftingStat(1000, 20, 0x07C84E, 25, 10, 0)); //RX-160 Byarlant Beam Saber.
        craftingStats.put(0x446EC, new WeaponCraftingStat(1000, 20, 0x07C84E, 100, 10, 0)); //PMX-001 Dual Beam Gun.
        craftingStats.put(0x57E7B, new WeaponCraftingStat(1000, 20, 0x07C832, 20, 50, 0)); //PMX-001 Shield
        craftingStats.put(0x57E80, new WeaponCraftingStat(950, 20, 0x07C832, 20, 50, 0)); //PMX-002 Shield*/
    }
}
