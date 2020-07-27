package crafting.vehicles;

import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen holder all crafting info for våpen i tillegg til å beregne
 * resultat av crafting.
 */
public class VehicleCraftingManager {

    /**
     *
     * Crafting stats for alle MS/Vehicles lagres her.
     *
     * Key = Item ID for MS/Vehicle.
     */
    private static HashMap<Integer, VehicleCraftingStat> craftingStats = new HashMap<Integer, VehicleCraftingStat>();

    /**
     * Setter crafting stats.
     */
    public static void execute() {

        EFvehicleStats();
        ZEONvehicleStats();
        COMMONvehicleStats();

    }

    /**
     * Beregner resultatet av vehicle crafting.
     *
     * @param itemID ID til MS/Vehicle som skal lages.
     * @param skill Spillers MS/MA Construction skill
     *
     * @return Resultatet av crafting, eller null hvis feil.
     */
    public static VehicleCraftingResult crafting(int itemID, int skill) {

        VehicleCraftingResult res = new VehicleCraftingResult();

        VehicleCraftingStat vStat = getCraftingStat(itemID);

        if (vStat == null) {
            return null;
        }

        //Sjekk at spillers skill er høy nok.
        if (skill < vStat.getMinSkill()) {

            System.out.println("\nVehicle Crafting error: Insufficient skill for item ID:" + itemID);
            return null;
        }

        //Differansen mellom spillers skill og minimum skill.
        int dSkill = skill - vStat.getMinSkill();

        //Beregn sjanse for å lykkes med crafting.
        int suksessSjanse = (int) (10 + 100 * f(dSkill));

        //Sjekk at vi ikke går over maksimal sjanse for å lykkes.
        if (suksessSjanse > vStat.getMaxSuccess()) {
            suksessSjanse = vStat.getMaxSuccess();
        }

        //Sjanse til å mislykkes
        int mislykketSjanse = vStat.getDifficulty();

        Random r = new Random();

        int n = r.nextInt(mislykketSjanse + suksessSjanse);

        //Sjekk om crafting lykkes eller mislykkes.
        if (n <= mislykketSjanse) {
            //Mislykket.
            res.setSuccess(false);

            //Hvis MS/Vehicle bruker upgrade parts skal ingenting returneres.
            if (vStat.getUpgradePartID() != 0) {

                res.setAntall(0);
                res.setItemID(0);
            } else {
                //Returner noe av brukt materiale.
                int antall = (vStat.getMaterialLoss() * vStat.getMaterialAmount()) / 100;
                antall = vStat.getMaterialAmount() - antall;

                res.setAntall(antall);
                res.setItemID(vStat.getMaterialID());
            }
        } else {
            //Vellykket crafting.
            res.setSuccess(true);
            res.setAntall(1); //Kan aldri få mer enn 1 MS/Vehicle.
            res.setItemID(itemID);
        }

        return res;
    }

    /**
     * Beregner resultatet av vehicle upgrade.
     *
     * @param itemID ID til MS/Vehicle som skal oppgraderes.
     * @param skill Spillers MS/MA Construction skill
     * @param level Hvilket level vehicle oppgraderes til.
     *
     * @return true hvis oppgradering gikk bra, false hvis ikke.
     */
    public static boolean vehicleUpgrade(int itemID, int skill, int level) {

        VehicleCraftingStat vStat = getCraftingStat(itemID);

        if (vStat == null) {
            return false;
        }

        //Beregn sjanse for å lykkes med oppgradering.
        int suksessSjanse = (int) (100 * f(skill));

        //Sjanse til å mislykkes
        //int mislykketSjanse = (int)(100*f(vStat.getMinSkill()));
        //mislykketSjanse += (level*mislykketSjanse)/16;
        int mislykketSjanse = 10;

        Random r = new Random();

        int n = r.nextInt(mislykketSjanse + suksessSjanse);

        //Sjekk om crafting lykkes eller mislykkes.
        if (n <= mislykketSjanse) {
            //Mislykket.
            return false;
        } else {
            //Vellykket.
            return true;
        }

    }

    /**
     * Returnerer crafting stats for oppgitt MS/Vehicle.
     *
     * @param itemID Item ID til MS/Vehicle.
     * @return Crafting stats for oppgitt MS/Vehicle eller null hvis ugyldig ID.
     */
    public static VehicleCraftingStat getCraftingStat(int itemID) {
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
     * Setter crafting stats for alle EF MS/Vehicles.
     *
     */
    private static void EFvehicleStats() {

        /*
         * Material guide.
         *titanium alloy = 0x07C830
         *titanium ceramic composite = 0x07C832
         *super high tensile steel = 0x07C831
         *luna alloy = 0x07C833
         *
         * Difficulty guide.
         * 10=50% sjanse ved 0 skill, 66% sjanse ved +2.0 skill, 75% sjanse ved +5.0 skill
         * 20=33% sjanse ved 0 skill, 50% sjanse ved +2.0 skill, 60% sjanse ved +5.0 skill
         * 30=25% sjanse ved 0 skill, 40% sjanse ved +2.0 skill, 50% sjanse ved +5.0 skill
         * 50=16% sjanse ved 0 skill, 28% sjanse ved +2.0 skill, 37% sjanse ved +5.0 skill
         * 90=10% sjanse ved 0 skill, 18% sjanse ved +2.0 skill, 25% sjanse ved +5.0 skill
         * 
         */
        //minSkill,difficulty,maxSjanse,materialID,materialAmount,materialLoss
        //Crafting stats etter 2019 reboot.
        craftingStats.put(0x641BA, new VehicleCraftingStat(10, 100, 5, 110, 0x07C830, 15, 25)); //TGM-79 GM Trainer
        craftingStats.put(0x641CE, new VehicleCraftingStat(10, 150, 5, 110, 0x07C843, 5, 25)); //RRF-06 Zanny
        craftingStats.put(0x64190, new VehicleCraftingStat(10, 200, 5, 110, 0x07C830, 25, 25)); //RGM-79 GM
        craftingStats.put(0x64193, new VehicleCraftingStat(10, 250, 5, 110, 0x07C830, 30, 25)); //RGC-80
        craftingStats.put(0x6419B, new VehicleCraftingStat(10, 300, 5, 110, 0x07C830, 50, 25)); //RX-75R
        craftingStats.put(0x641BB, new VehicleCraftingStat(10, 350, 5, 110, 0x07C830, 50, 25)); //RGM-79L
        craftingStats.put(0x641C4, new VehicleCraftingStat(10, 400, 5, 110, 0x07C832, 25, 25)); //RGM-79D
        craftingStats.put(0x641C8, new VehicleCraftingStat(10, 450, 5, 110, 0x07C832, 25, 25)); //RMV-1
        craftingStats.put(0x64194, new VehicleCraftingStat(10, 500, 5, 110, 0x07C832, 50, 25)); //RX-77D
        craftingStats.put(0x641B4, new VehicleCraftingStat(10, 550, 5, 110, 0x07C833, 150, 25)); //RGM-79(G)s GM Sniper
        craftingStats.put(0x6419C, new VehicleCraftingStat(10, 600, 5, 110, 0x07C833, 100, 25)); //RX-75
        craftingStats.put(0x641A7, new VehicleCraftingStat(10, 650, 5, 110, 0x07C833, 150, 25)); //RGM-79(G) Ground GM
        craftingStats.put(0x641D7, new VehicleCraftingStat(10, 700, 5, 110, 0x07C832, 150, 25)); //RGM-79CS
        craftingStats.put(0x641A8, new VehicleCraftingStat(10, 700, 5, 110, 0x07C832, 150, 25)); //RGM-79C
        craftingStats.put(0x64192, new VehicleCraftingStat(10, 700, 5, 110, 0x07C832, 150, 25)); //RGM-79G
        craftingStats.put(0x641C5, new VehicleCraftingStat(10, 700, 5, 110, 0x07C832, 150, 25)); //RGM-79GS
        craftingStats.put(0x641B6, new VehicleCraftingStat(10, 750, 5, 110, 0x07C830, 200, 25)); //RGM-79SC
        craftingStats.put(0x641CC, new VehicleCraftingStat(10, 800, 5, 110, 0x07C830, 300, 25)); //RX-77-4
        craftingStats.put(0x64196, new VehicleCraftingStat(10, 850, 5, 110, 0x07C833, 150, 25)); //RX-77
        craftingStats.put(0x641B9, new VehicleCraftingStat(10, 900, 5, 110, 0x07C833, 250, 25)); //RX-77-3   
        craftingStats.put(0xF6968, new VehicleCraftingStat(10, 900, 5, 110, 0x07C843, 50, 25)); //GM Head
        craftingStats.put(0x641AD, new VehicleCraftingStat(10, 950, 5, 110, 0x07C833, 200, 25)); //RX-79G
        craftingStats.put(0x641B5, new VehicleCraftingStat(10, 1000, 5, 110, 0x07C832, 300, 25)); //RGM-79SP
        craftingStats.put(0x641AE, new VehicleCraftingStat(10, 1000, 5, 110, 0x07C843, 100, 25)); //RX-79G Ez-8
        craftingStats.put(0x641AF, new VehicleCraftingStat(10, 1000, 25, 110, 0x07C844, 200, 50)); //RX-78-1
        craftingStats.put(0x64195, new VehicleCraftingStat(10, 1050, 25, 110, 0x07C844, 400, 50)); //RX-78-2
        craftingStats.put(0x6423C, new VehicleCraftingStat(10, 1100, 5, 45, 0x07C844, 400, 100, 0x64195, 0x07C849)); //RX-78-2MC versjon av 0x64195
        craftingStats.put(0x641D9, new VehicleCraftingStat(10, 1150, 25, 110, 0x07C844, 800, 50)); //RX-78-3 G3
        craftingStats.put(0x641DA, new VehicleCraftingStat(10, 1150, 25, 110, 0x07C844, 800, 50)); //RX-78-FA
        craftingStats.put(0x64245, new VehicleCraftingStat(10, 1200, 25, 110, 0x07C844, 1000, 50)); //RX-78-NT-1
        craftingStats.put(0xF424A, new VehicleCraftingStat(10, 700, 5, 110, 0x07C831, 200, 25)); //Midea Hangar

        //Sett crafting stats for EF MS + Midea		
        /*
        craftingStats.put(0x6421D, new VehicleCraftingStat(15, 50, 5, 110, 0x07C843, 5, 25)); //Zanny hvit/blå                
        craftingStats.put(0xF6957, new VehicleCraftingStat(30, 100, 5, 110, 0x07C830, 40, 25)); //RGM-79 GM Black
        craftingStats.put(0xF695F, new VehicleCraftingStat(30, 100, 5, 110, 0x07C830, 40, 25)); //RGM-79 GM Farget        
        craftingStats.put(0x64206, new VehicleCraftingStat(60, 150, 10, 110, 0x07C830, 60, 25)); //RGC-80 Svart/Hvit				        
        craftingStats.put(0x6421B, new VehicleCraftingStat(30, 200, 10, 110, 0x07C832, 50, 25)); //RGM-79L Brun
        craftingStats.put(0x64213, new VehicleCraftingStat(30, 200, 10, 110, 0x07C832, 50, 25)); //RGM-79L Grønn
        craftingStats.put(0x64207, new VehicleCraftingStat(30, 200, 10, 110, 0x07C832, 50, 25)); //RGM-79L Rosa
        craftingStats.put(0x641EF, new VehicleCraftingStat(30, 200, 10, 110, 0x07C832, 50, 25)); //RGM-79L Blå                
        craftingStats.put(0x64208, new VehicleCraftingStat(50, 300, 10, 110, 0x07C832, 40, 25)); //RGM-79D Rød                
        craftingStats.put(0x641E6, new VehicleCraftingStat(50, 400, 15, 110, 0x07C832, 70, 50)); //RX-77D Farget                
        craftingStats.put(0xF6960, new VehicleCraftingStat(50, 500, 15, 110, 0x07C832, 70, 25)); //RGM-79G Farget                
        craftingStats.put(0x6421A, new VehicleCraftingStat(50, 500, 15, 110, 0x07C832, 70, 50)); //RGM-79C Grå/svart
        craftingStats.put(0x641F0, new VehicleCraftingStat(50, 500, 15, 110, 0x07C832, 70, 50)); //RGM-79C rød/gul
        craftingStats.put(0x641DE, new VehicleCraftingStat(50, 500, 15, 110, 0x07C832, 70, 50)); //RGM-79C Farget      
        craftingStats.put(0x64223, new VehicleCraftingStat(70, 500, 15, 110, 0x07C833, 50, 50)); //RGM-79(G) sand/blå
        craftingStats.put(0x64215, new VehicleCraftingStat(70, 500, 15, 110, 0x07C833, 50, 50)); //RGM-79(G) grønn/gul
        craftingStats.put(0x641F1, new VehicleCraftingStat(70, 500, 15, 110, 0x07C833, 50, 50)); //RGM-79(G) grå/rød
        craftingStats.put(0x641EA, new VehicleCraftingStat(70, 500, 15, 110, 0x07C833, 50, 50)); //RGM-79(G) Farget
        craftingStats.put(0x6422F, new VehicleCraftingStat(70, 500, 15, 110, 0x07C833, 50, 50)); //RGM-79(G)        
        craftingStats.put(0x64209, new VehicleCraftingStat(70, 550, 15, 110, 0x07C830, 100, 50)); //RGM-79SC Lilla
        craftingStats.put(0x641DD, new VehicleCraftingStat(70, 550, 15, 110, 0x07C830, 100, 50)); //RGM-79SC Farget
        craftingStats.put(0x64247, new VehicleCraftingStat(70, 550, 15, 110, 0x07C830, 200, 50)); //RMS-179 GM        
        craftingStats.put(0x6421E, new VehicleCraftingStat(50, 600, 10, 110, 0x07C833, 60, 25)); //RX-75 svart/blå												        
        craftingStats.put(0xF6954, new VehicleCraftingStat(60, 600, 15, 110, 0x07C833, 60, 50)); //RX-77 Farget
        craftingStats.put(0xF695C, new VehicleCraftingStat(60, 600, 15, 110, 0x07C833, 60, 50)); //RX-77 Farget        
        craftingStats.put(0x64225, new VehicleCraftingStat(80, 600, 15, 100, 0x07C833, 80, 50)); //GM Head blue destiny             
        craftingStats.put(0x641FC, new VehicleCraftingStat(60, 650, 15, 110, 0x07C833, 70, 50)); //RX-77-4 Rød/Hvit
        craftingStats.put(0x6423B, new VehicleCraftingStat(60, 650, 15, 110, 0x07C833, 70, 50)); //RX-77-4		        
        craftingStats.put(0x6420A, new VehicleCraftingStat(60, 650, 15, 110, 0x07C832, 95, 70)); //RGM-79SP Sand/brun
        craftingStats.put(0x641F5, new VehicleCraftingStat(60, 650, 15, 110, 0x07C832, 95, 70)); //RGM-79SP rød/svart
        craftingStats.put(0x641DC, new VehicleCraftingStat(60, 650, 15, 110, 0x07C832, 95, 70)); //RGM-79SP Farget
        craftingStats.put(0x64230, new VehicleCraftingStat(60, 650, 15, 110, 0x07C832, 95, 70)); //RGM-79SP        
        craftingStats.put(0x64224, new VehicleCraftingStat(80, 650, 15, 110, 0x07C833, 80, 60)); //RX-79G svart/rød
        craftingStats.put(0x64216, new VehicleCraftingStat(80, 650, 15, 110, 0x07C833, 80, 60)); //RX-79G hvit
        craftingStats.put(0x641F2, new VehicleCraftingStat(80, 650, 15, 110, 0x07C833, 80, 60)); //RX-79G lilla/rosa
        craftingStats.put(0x641EB, new VehicleCraftingStat(80, 650, 15, 110, 0x07C833, 80, 60)); //RX-79G Blå
        craftingStats.put(0xF695D, new VehicleCraftingStat(80, 650, 15, 110, 0x07C833, 80, 60)); //RX-79G Farget
        craftingStats.put(0xF6966, new VehicleCraftingStat(80, 650, 15, 110, 0x07C833, 80, 60)); //RX-79G Farget
        craftingStats.put(0x6422B, new VehicleCraftingStat(80, 650, 15, 110, 0x07C833, 80, 60)); //RX-79G Farget
        craftingStats.put(0x6422C, new VehicleCraftingStat(80, 650, 15, 110, 0x07C833, 80, 60)); //RX-79G Farget
        craftingStats.put(0x6422D, new VehicleCraftingStat(80, 650, 15, 110, 0x07C833, 80, 60)); //RX-79G Farget
        craftingStats.put(0x6422E, new VehicleCraftingStat(80, 650, 15, 110, 0x07C833, 80, 60)); //RX-79G Farget        
        craftingStats.put(0x64217, new VehicleCraftingStat(80, 700, 15, 35, 0x07C843, 60, 25)); //Ez-8 brun/sand
        craftingStats.put(0x641EC, new VehicleCraftingStat(80, 700, 15, 35, 0x07C843, 60, 25)); //Ez-8 Gul
        craftingStats.put(0x641F3, new VehicleCraftingStat(80, 700, 15, 35, 0x07C843, 60, 25)); //Ez-8 hvit/rød
        craftingStats.put(0xF6967, new VehicleCraftingStat(80, 700, 15, 35, 0x07C843, 60, 25)); //Ez-8 Farget        
        craftingStats.put(0xF6958, new VehicleCraftingStat(120, 700, 15, 35, 0x07C844, 150, 25)); //RX-78-1 Gold
        craftingStats.put(0x64248, new VehicleCraftingStat(70, 700, 15, 110, 0x07C833, 200, 50)); //MSA-003 Nemo
        craftingStats.put(0x64249, new VehicleCraftingStat(70, 750, 15, 35, 0x07C84E, 100, 10)); //RMS-108 Marasai        
        craftingStats.put(0x6420E, new VehicleCraftingStat(150, 750, 60, 35, 0x07C844, 190, 25)); //RX-78-2 Titans farge
        craftingStats.put(0x6420F, new VehicleCraftingStat(150, 750, 60, 35, 0x07C844, 190, 25)); //RX-78-2 Zeon Gundam
        craftingStats.put(0x641F9, new VehicleCraftingStat(150, 750, 60, 35, 0x07C844, 190, 25)); //RX-78-2 Char Aznable
        craftingStats.put(0x64233, new VehicleCraftingStat(150, 750, 60, 35, 0x07C844, 190, 25)); //RX-78-2
        craftingStats.put(0x64234, new VehicleCraftingStat(150, 750, 60, 35, 0x07C844, 190, 25)); //RX-78-2
        craftingStats.put(0x64235, new VehicleCraftingStat(150, 750, 60, 35, 0x07C844, 190, 25)); //RX-78-2
        craftingStats.put(0x64257, new VehicleCraftingStat(70, 800, 15, 110, 0x07C832, 1000, 100)); //RGM-89 Jegan        
        craftingStats.put(0x6423D, new VehicleCraftingStat(80, 800, 5, 45, 0x07C844, 190, 100, 0x6420E, 0x07C849)); //RX-78-2MC versjon av 0x6420E
        craftingStats.put(0x6423E, new VehicleCraftingStat(80, 800, 5, 45, 0x07C844, 190, 100, 0x6420F, 0x07C849)); //RX-78-2MC versjon av 0x6420F
        craftingStats.put(0x6423F, new VehicleCraftingStat(80, 800, 5, 45, 0x07C844, 190, 100, 0x641F9, 0x07C849)); //RX-78-2MC versjon av 0x641F9
        craftingStats.put(0x64240, new VehicleCraftingStat(80, 800, 5, 45, 0x07C844, 190, 100, 0x64233, 0x07C849)); //RX-78-2MC versjon av 0x64233
        craftingStats.put(0x64241, new VehicleCraftingStat(80, 800, 5, 45, 0x07C844, 190, 100, 0x64234, 0x07C849)); //RX-78-2MC versjon av 0x64234
        craftingStats.put(0x64242, new VehicleCraftingStat(80, 800, 5, 45, 0x07C844, 190, 100, 0x64235, 0x07C849)); //RX-78-2MC versjon av 0x64235		        
        craftingStats.put(0x64219, new VehicleCraftingStat(150, 800, 60, 35, 0x07C844, 350, 25)); //G-3 hvit/svart
        craftingStats.put(0x6420D, new VehicleCraftingStat(150, 800, 60, 35, 0x07C844, 350, 25)); //G-3 hvit/blå
        craftingStats.put(0x641F4, new VehicleCraftingStat(150, 800, 60, 35, 0x07C844, 350, 25)); //G-3 rød
        craftingStats.put(0x641ED, new VehicleCraftingStat(150, 800, 60, 35, 0x07C844, 350, 25)); //G-3 Farget
        craftingStats.put(0x64231, new VehicleCraftingStat(150, 800, 60, 35, 0x07C844, 350, 25)); //G-3                
        craftingStats.put(0x641DB, new VehicleCraftingStat(150, 800, 60, 35, 0x07C844, 300, 25)); //FA Blå
        craftingStats.put(0x641FA, new VehicleCraftingStat(150, 800, 60, 35, 0x07C844, 300, 25)); //FA Hvit
        craftingStats.put(0x641FB, new VehicleCraftingStat(150, 800, 60, 35, 0x07C844, 300, 25)); //FA Brun
        craftingStats.put(0x6424E, new VehicleCraftingStat(70, 800, 60, 35, 0x07C84E, 150, 10)); //RMS-099 Rick Dias EF versjon        
        craftingStats.put(0x6424B, new VehicleCraftingStat(70, 850, 60, 35, 0x07C84E, 250, 20)); //RX-78GP01-Fb
        craftingStats.put(0x64250, new VehicleCraftingStat(70, 850, 5, 35, 0x07C852, 100, 20)); //RX-78GP02A
        craftingStats.put(0x64255, new VehicleCraftingStat(70, 850, 100, 35, 0x07C84E, 200, 20)); //PMX-002 Bolinoak EF versjon
        craftingStats.put(0x6424C, new VehicleCraftingStat(70, 900, 150, 35, 0x07C84E, 500, 20)); //PMX-001 Palace Athene EF versjon
        craftingStats.put(0x64251, new VehicleCraftingStat(70, 900, 100, 35, 0x07C84E, 400, 20)); //RX-178 Gundam Mk-II AEUG
        craftingStats.put(0x64252, new VehicleCraftingStat(70, 900, 100, 35, 0x07C84E, 400, 20)); //RX-178 Gundam Mk-II TITANS
        craftingStats.put(0x6425C, new VehicleCraftingStat(70, 950, 150, 35, 0x07C84E, 500, 20)); //MSN-00100 Hyaku Shiki
        craftingStats.put(0x64258, new VehicleCraftingStat(80, 900, 5, 45, 0x07C853, 100, 20)); //RX-178 AEUG Super Gundam
        craftingStats.put(0x6425B, new VehicleCraftingStat(80, 900, 5, 45, 0x07C853, 100, 20)); //RX-178 TITANS Super Gundam
        craftingStats.put(0x64253, new VehicleCraftingStat(70, 900, 150, 35, 0x07C84E, 300, 20)); //RX-160 Byarlant EF versjon
        craftingStats.put(0x64260, new VehicleCraftingStat(80, 1000, 5, 45, 0x07C850, 100, 20)); //Zeta Gundam
        craftingStats.put(0x668A7, new VehicleCraftingStat(200, 750, 60, 35, 0x07C844, 100, 25)); //Core Booster        
        craftingStats.put(0xF4242, new VehicleCraftingStat(180, 700, 5, 110, 0x07C832, 600, 50)); //Midea Gray
        craftingStats.put(0xFB77F, new VehicleCraftingStat(750, 100, 100, 0x07C832, 700, 70)); //Salamis
        craftingStats.put(0xFB780, new VehicleCraftingStat(750, 100, 100, 0x07C843, 200, 10)); //Magellan
        craftingStats.put(0xFB771, new VehicleCraftingStat(750, 100, 100, 0x07C832, 700, 70)); //Bigtray                
         */
        //Crafting stats for MA NB!
        craftingStats.put(0x668A0, new VehicleCraftingStat(50, 5, 110, 0x07C831, 10, 50)); //RB-79 
        craftingStats.put(0x668A5, new VehicleCraftingStat(50, 5, 110, 0x07C831, 10, 50)); //RB-79C
        craftingStats.put(0x668A4, new VehicleCraftingStat(50, 5, 110, 0x07C831, 10, 50)); //RB-79K

        //Crafting stats for EF spesifikke vehicles.
        craftingStats.put(0x704E4, new VehicleCraftingStat(50, 5, 110, 0x07C831, 10, 50)); //Hovertruck
        craftingStats.put(0x68FB0, new VehicleCraftingStat(0, 5, 110, 0x07C831, 7, 50)); //Public
        craftingStats.put(0x704E2, new VehicleCraftingStat(0, 5, 110, 0x07C831, 10, 25)); //Type-61 MBT Custom
    }

    /**
     *
     * Setter crafting stats for Zeon MS/Vehicles.
     *
     */
    private static void ZEONvehicleStats() {

        /*
         * Material guide.
         *titanium alloy = 0x07C830
         *titanium ceramic composite = 0x07C832
         *super high tensile steel = 0x07C831
         *luna alloy = 0x07C833
         *
         * Difficulty guide.
         * 10=50% sjanse ved 0 skill, 66% sjanse ved +2.0 skill, 75% sjanse ved +5.0 skill
         * 20=33% sjanse ved 0 skill, 50% sjanse ved +2.0 skill, 60% sjanse ved +5.0 skill
         * 30=25% sjanse ved 0 skill, 40% sjanse ved +2.0 skill, 50% sjanse ved +5.0 skill
         * 50=16% sjanse ved 0 skill, 28% sjanse ved +2.0 skill, 37% sjanse ved +5.0 skill
         * 90=10% sjanse ved 0 skill, 18% sjanse ved +2.0 skill, 25% sjanse ved +5.0 skill
         * 
         */
        //minSkill,difficulty,maxSjanse,materialID,materialAmount,materialLoss
        //Crafting stats etter 2019 reboot.
        craftingStats.put(0x64191, new VehicleCraftingStat(10, 100, 5, 110, 0x07C831, 15, 25)); //MS-05B Zaku I
        craftingStats.put(0x641A4, new VehicleCraftingStat(10, 150, 5, 110, 0x07C831, 25, 25)); //MS-12 Gigan
        craftingStats.put(0xF6964, new VehicleCraftingStat(10, 200, 5, 110, 0x07C843, 5, 25)); //Zaku I Ramba Ral
        craftingStats.put(0x64197, new VehicleCraftingStat(10, 250, 5, 110, 0x07C831, 25, 25)); //MS-06F Zaku II        
        craftingStats.put(0x6419E, new VehicleCraftingStat(10, 300, 5, 110, 0x07C831, 30, 25)); //MS-06K Zaku Cannon
        craftingStats.put(0x641AB, new VehicleCraftingStat(10, 300, 5, 110, 0x07C843, 5, 25)); //MS-06K Rabbit Zaku Cannon
        craftingStats.put(0x6419F, new VehicleCraftingStat(10, 350, 5, 110, 0x07C831, 50, 25)); //MS-06D Zaku Desert type
        craftingStats.put(0x641AC, new VehicleCraftingStat(10, 400, 5, 110, 0x07C843, 5, 25)); //MS-06D Zaku Desert Karakal Custom
        craftingStats.put(0x641BE, new VehicleCraftingStat(10, 450, 5, 110, 0x07C831, 50, 25)); //ZakuF2 type A
        craftingStats.put(0x641B0, new VehicleCraftingStat(10, 450, 5, 110, 0x07C831, 50, 25)); //ZakuF2 type B		
        craftingStats.put(0x641D2, new VehicleCraftingStat(10, 500, 5, 110, 0x07C831, 80, 25)); //MS-06RP
        craftingStats.put(0x641D3, new VehicleCraftingStat(10, 550, 5, 110, 0x07C831, 100, 25)); //MS-06R1 Zaku
        craftingStats.put(0x641A0, new VehicleCraftingStat(10, 600, 5, 110, 0x07C832, 50, 25)); //YMS-09 DOM
        craftingStats.put(0x641B2, new VehicleCraftingStat(10, 600, 5, 110, 0x07C832, 50, 25)); //MSM-04 Acguy
        craftingStats.put(0x6419A, new VehicleCraftingStat(10, 650, 5, 110, 0x07C831, 100, 25)); //MS-06S Zaku
        craftingStats.put(0xF6950, new VehicleCraftingStat(10, 650, 5, 110, 0x07C843, 20, 25)); //MS-06S Char Zaku
        craftingStats.put(0x641B1, new VehicleCraftingStat(10, 650, 5, 110, 0x07C843, 5, 25)); //MS-06F2 Neuen Bitter
        craftingStats.put(0x641C0, new VehicleCraftingStat(10, 700, 5, 110, 0x07C832, 50, 25)); //MSM-04N Aggguy
        craftingStats.put(0x641A3, new VehicleCraftingStat(10, 700, 5, 110, 0x07C832, 50, 25)); //MSM-04N Agguguy heatrod
        craftingStats.put(0xF696C, new VehicleCraftingStat(10, 700, 5, 110, 0x07C831, 150, 25)); //MS-06R1A
        craftingStats.put(0x641A2, new VehicleCraftingStat(10, 700, 5, 110, 0x07C832, 50, 25)); //MSM-04G Juaggu
        craftingStats.put(0x641B3, new VehicleCraftingStat(10, 700, 5, 110, 0x07C843, 10, 25)); //YMS-09D DOM TTT
        craftingStats.put(0x6419D, new VehicleCraftingStat(10, 700, 5, 110, 0x07C831, 300, 25)); //MS-07B Gouf
        craftingStats.put(0x641D4, new VehicleCraftingStat(10, 750, 5, 110, 0x07C831, 200, 25)); //MS06-R2P
        craftingStats.put(0x64198, new VehicleCraftingStat(10, 750, 5, 110, 0x07C832, 150, 25)); //ZakuFZ type A
        craftingStats.put(0x64199, new VehicleCraftingStat(10, 750, 5, 110, 0x07C832, 150, 25)); //ZakuFZ type B			
        craftingStats.put(0x641D8, new VehicleCraftingStat(10, 800, 5, 110, 0x07C831, 300, 25)); //MS-06R2
        craftingStats.put(0x641AA, new VehicleCraftingStat(10, 800, 5, 110, 0x07C832, 200, 25)); //MSM-03 Gogg
        craftingStats.put(0x641A9, new VehicleCraftingStat(10, 850, 5, 110, 0x07C831, 300, 25)); //MSM-07 Z'Gok
        craftingStats.put(0x641A1, new VehicleCraftingStat(10, 850, 5, 110, 0x07C831, 350, 25)); //MS-09 DOM
        craftingStats.put(0x641D5, new VehicleCraftingStat(10, 850, 5, 110, 0x07C831, 350, 25)); //MS-09R DOM	
        craftingStats.put(0x641C3, new VehicleCraftingStat(10, 900, 5, 110, 0x07C832, 250, 25)); //MSM-07E Z'Gok E
        craftingStats.put(0x641A5, new VehicleCraftingStat(10, 950, 5, 110, 0x07C831, 400, 25)); //MS-13 Gasshia
        craftingStats.put(0x641A6, new VehicleCraftingStat(10, 950, 5, 110, 0x07C831, 400, 25)); //MSM-08 ZoGok
        craftingStats.put(0x64203, new VehicleCraftingStat(10, 1000, 5, 110, 0x07C843, 100, 25)); //MS-09RS
        craftingStats.put(0xF695B, new VehicleCraftingStat(10, 1000, 25, 110, 0x07C84D, 200, 50)); //MSM-07S Z'Gok S
        craftingStats.put(0x641C9, new VehicleCraftingStat(10, 1000, 5, 110, 0x07C832, 300, 25)); //MS-14A
        craftingStats.put(0x641CA, new VehicleCraftingStat(10, 1050, 25, 110, 0x07C84D, 400, 50)); //MS-14S
        craftingStats.put(0x64246, new VehicleCraftingStat(10, 1050, 5, 110, 0x07C843, 100, 25)); //YMS-15 Gyan
        craftingStats.put(0xF695E, new VehicleCraftingStat(10, 1100, 25, 110, 0x07C84D, 500, 50)); //YMS-14 Char Aznable
        craftingStats.put(0x641CB, new VehicleCraftingStat(10, 1150, 25, 110, 0x07C84D, 500, 50)); //MS-14B
        craftingStats.put(0x64243, new VehicleCraftingStat(10, 1200, 25, 110, 0x07C84D, 1000, 50)); //MS-18E    
        craftingStats.put(0xFB77E, new VehicleCraftingStat(10, 700, 5, 110, 0x07C831, 200, 25)); //Fat Uncle Hangar

        //Sett crafting stats for Zeon MS.
        /*
        craftingStats.put(0xF6952, new VehicleCraftingStat(15, 50, 5, 110, 0x07C831, 25, 50)); //Zaku I Black Tristars        	        
        craftingStats.put(0xF6961, new VehicleCraftingStat(30, 100, 10, 110, 0x07C831, 40, 25)); //Zaku II Farget
        craftingStats.put(0xF696A, new VehicleCraftingStat(30, 100, 10, 110, 0x07C831, 40, 25)); //Zaku II Farget	               
        craftingStats.put(0xF6959, new VehicleCraftingStat(30, 200, 10, 110, 0x07C831, 60, 25)); //Zaku Desert type A Green                                		                
        craftingStats.put(0x641E0, new VehicleCraftingStat(50, 350, 15, 110, 0x07C831, 100, 50)); //YMS-09 DOM Farget		        
        craftingStats.put(0x641FF, new VehicleCraftingStat(30, 350, 10, 110, 0x07C831, 120, 50)); //MSM-04 Acguy Rød/Hvit        				        
        craftingStats.put(0xF6956, new VehicleCraftingStat(30, 400, 10, 110, 0x07C832, 70, 30)); //ZakuFZ type A Farget
        craftingStats.put(0xF6962, new VehicleCraftingStat(30, 400, 10, 110, 0x07C832, 70, 30)); //ZakuFZ type A Farget               
        craftingStats.put(0xF6975, new VehicleCraftingStat(30, 400, 15, 110, 0x07C831, 80, 50)); //Zaku R-1 Shin Matsunaga		        	        
        craftingStats.put(0xF696D, new VehicleCraftingStat(30, 450, 15, 110, 0x07C831, 85, 50)); //Zaku R-2 Johhny Ridden		        				        
        craftingStats.put(0x641E1, new VehicleCraftingStat(50, 450, 15, 110, 0x07C831, 100, 50)); //YMS-09D DOM Farget				        
        craftingStats.put(0x6421C, new VehicleCraftingStat(50, 500, 15, 110, 0x07C831, 70, 50)); //Zaku 2S Svart        
        craftingStats.put(0xF695A, new VehicleCraftingStat(50, 500, 15, 110, 0x07C831, 70, 50)); //Zaku 2S Silver														        
        craftingStats.put(0x641FE, new VehicleCraftingStat(70, 550, 20, 110, 0x07C832, 80, 50)); //Gasshia Svart/Hvit		        
        craftingStats.put(0x64202, new VehicleCraftingStat(50, 600, 20, 110, 0x07C832, 90, 50)); //ZoGok Farget        
        craftingStats.put(0x64222, new VehicleCraftingStat(70, 600, 20, 110, 0x07C832, 85, 50)); //Z'Gok Gul
        craftingStats.put(0x64214, new VehicleCraftingStat(70, 600, 20, 110, 0x07C832, 85, 50)); //Z'Gok Lilla
        craftingStats.put(0x64200, new VehicleCraftingStat(70, 600, 20, 110, 0x07C832, 85, 50)); //Z'Gok Svart				        
        craftingStats.put(0x64239, new VehicleCraftingStat(60, 650, 20, 110, 0x07C832, 100, 50)); //Gogg rosa
        craftingStats.put(0x641E2, new VehicleCraftingStat(60, 650, 20, 110, 0x07C832, 100, 50)); //Gogg Farget        
        craftingStats.put(0x64228, new VehicleCraftingStat(180, 650, 20, 110, 0x07C832, 400, 70)); //MS-14 
        craftingStats.put(0x6421F, new VehicleCraftingStat(180, 650, 20, 110, 0x07C832, 400, 70)); //MS-14 grønn
        craftingStats.put(0x64218, new VehicleCraftingStat(180, 650, 20, 110, 0x07C832, 400, 70)); //MS-14 rød/sand
        craftingStats.put(0x641F6, new VehicleCraftingStat(180, 650, 20, 110, 0x07C832, 400, 70)); //MS-14 mørk grå
        craftingStats.put(0x641E3, new VehicleCraftingStat(180, 650, 20, 110, 0x07C832, 400, 70)); //MS-14 Farget
        craftingStats.put(0x641E7, new VehicleCraftingStat(180, 650, 20, 110, 0x07C832, 400, 70)); //MS-14 Farget
        craftingStats.put(0xF6969, new VehicleCraftingStat(180, 650, 20, 110, 0x07C832, 400, 70)); //MS-14 Anavel Gato				        
        craftingStats.put(0x6420B, new VehicleCraftingStat(50, 650, 20, 110, 0x07C831, 130, 50)); //Gouf Lysblå
        craftingStats.put(0x641DF, new VehicleCraftingStat(50, 650, 20, 110, 0x07C831, 130, 50)); //Gouf Hvit
        craftingStats.put(0x641EE, new VehicleCraftingStat(50, 650, 20, 110, 0x07C831, 130, 50)); //Gouf Gul
        craftingStats.put(0xF6963, new VehicleCraftingStat(50, 650, 20, 110, 0x07C831, 130, 50)); //Gouf Svart
        craftingStats.put(0x64236, new VehicleCraftingStat(50, 650, 20, 110, 0x07C831, 130, 50)); //Gouf
        craftingStats.put(0x64237, new VehicleCraftingStat(50, 650, 20, 110, 0x07C831, 130, 50)); //Gouf
        craftingStats.put(0x64238, new VehicleCraftingStat(50, 650, 20, 110, 0x07C831, 130, 50)); //Gouf					        
        craftingStats.put(0x64226, new VehicleCraftingStat(50, 650, 20, 110, 0x07C831, 130, 50)); //MS-09 DOM svart/lilla
        craftingStats.put(0xF6953, new VehicleCraftingStat(50, 650, 20, 110, 0x07C831, 130, 50)); //MS-09 DOM Farget
        craftingStats.put(0x64232, new VehicleCraftingStat(50, 650, 20, 110, 0x07C831, 130, 50)); //MS-09 DOM        				        
        craftingStats.put(0x64227, new VehicleCraftingStat(60, 650, 20, 110, 0x07C832, 200, 50)); //Z'Gok E Brun
        craftingStats.put(0x6420C, new VehicleCraftingStat(60, 650, 20, 110, 0x07C832, 200, 50)); //Z'Gok E Blå
        craftingStats.put(0x64201, new VehicleCraftingStat(60, 650, 20, 110, 0x07C832, 200, 50)); //Z'Gok E Orange
        craftingStats.put(0x6423A, new VehicleCraftingStat(60, 650, 20, 110, 0x07C832, 200, 50)); //Z'Gok E        
        craftingStats.put(0x6425D, new VehicleCraftingStat(70, 700, 60, 35, 0x07C84D, 150, 25)); //MSM-03C Hygogg        
        craftingStats.put(0x64210, new VehicleCraftingStat(80, 700, 60, 35, 0x07C843, 60, 25)); //MS-09RS Gato        
        craftingStats.put(0x64244, new VehicleCraftingStat(80, 700, 60, 35, 0x07C843, 150, 25)); //MS-08TX Efreet
        craftingStats.put(0x6424A, new VehicleCraftingStat(70, 750, 15, 35, 0x07C84E, 100, 10)); //RMS-108 Marasai        
        craftingStats.put(0x64229, new VehicleCraftingStat(200, 750, 60, 35, 0x07C84D, 190, 25)); //MS-14S
        craftingStats.put(0x64220, new VehicleCraftingStat(200, 750, 60, 35, 0x07C84D, 190, 25)); //MS-14S Hvit
        craftingStats.put(0x64211, new VehicleCraftingStat(200, 750, 60, 35, 0x07C84D, 190, 25)); //MS-14S Erik Blanke
        craftingStats.put(0x641FD, new VehicleCraftingStat(200, 750, 60, 35, 0x07C84D, 190, 25)); //MS-14S Lilla/Brun
        craftingStats.put(0x641F7, new VehicleCraftingStat(200, 750, 60, 35, 0x07C84D, 190, 25)); //MS-14S grå/hvit
        craftingStats.put(0x641E4, new VehicleCraftingStat(200, 750, 60, 35, 0x07C84D, 190, 25)); //MS-14S Farget
        craftingStats.put(0x641E8, new VehicleCraftingStat(200, 750, 60, 35, 0x07C84D, 190, 25)); //MS-14S Farget                
        craftingStats.put(0x6422A, new VehicleCraftingStat(200, 800, 60, 35, 0x07C84D, 300, 25)); //MS-14B
        craftingStats.put(0x64221, new VehicleCraftingStat(200, 800, 60, 35, 0x07C84D, 300, 25)); //MS-14B Gato
        craftingStats.put(0x64212, new VehicleCraftingStat(200, 800, 60, 35, 0x07C84D, 300, 25)); //MS-14B 78-2 farget
        craftingStats.put(0x641F8, new VehicleCraftingStat(200, 800, 60, 35, 0x07C84D, 300, 25)); //MS-14B grå/rød
        craftingStats.put(0x641E5, new VehicleCraftingStat(200, 800, 60, 35, 0x07C84D, 300, 25)); //MS-14B Farget
        craftingStats.put(0x641E9, new VehicleCraftingStat(200, 800, 60, 35, 0x07C84D, 300, 25)); //MS-14B Farget
        craftingStats.put(0xF696B, new VehicleCraftingStat(200, 800, 60, 35, 0x07C84D, 300, 25)); //MS-14B Johnny Ridden
        craftingStats.put(0x64204, new VehicleCraftingStat(200, 800, 60, 35, 0x07C84D, 300, 25)); //MS-14B Gul
        craftingStats.put(0x64205, new VehicleCraftingStat(200, 800, 60, 35, 0x07C84D, 300, 25)); //MS-14B Lilla
        
        craftingStats.put(0x6425E, new VehicleCraftingStat(70, 800, 15, 110, 0x07C832, 1000, 100)); //Geara Doga
        craftingStats.put(0x6425F, new VehicleCraftingStat(70, 800, 60, 35, 0x07C84D, 150, 25)); //Geara Doga Rezin Schnyder
        craftingStats.put(0x6424F, new VehicleCraftingStat(70, 800, 60, 35, 0x07C84E, 150, 10)); //RMS-099 Rick Dias Zeon versjon
        craftingStats.put(0x64256, new VehicleCraftingStat(70, 850, 100, 35, 0x07C84E, 200, 20)); //PMX-002 Bolinoak Zeon versjon
        craftingStats.put(0x6424D, new VehicleCraftingStat(70, 900, 150, 35, 0x07C84E, 500, 20)); //PMX-001 Palace Athene Zeon versjon
        craftingStats.put(0x64254, new VehicleCraftingStat(70, 900, 150, 35, 0x07C84E, 300, 20)); //RX-160 Byarlant Zeon versjon
        craftingStats.put(0x64259, new VehicleCraftingStat(70, 800, 15, 110, 0x07C832, 800, 50)); //RMS-106 Hizack
        craftingStats.put(0x6425A, new VehicleCraftingStat(70, 800, 15, 110, 0x07C832, 800, 50)); //AMX-003 Gaza C
        craftingStats.put(0x64261, new VehicleCraftingStat(80, 1000, 5, 45, 0x07C851, 100, 20)); //Bawoo Glemy
        craftingStats.put(0x64262, new VehicleCraftingStat(80, 1000, 5, 45, 0x07C851, 100, 20)); //Bawoo, Glemy Faction
        craftingStats.put(0x64263, new VehicleCraftingStat(80, 1000, 5, 45, 0x07C851, 100, 20)); //Bawoo, Axis MP
         */
 /* craftingStats.put(0x668A1, new VehicleCraftingStat(700, 60, 50, 0x07C832, 700, 70)); //Adzam
        craftingStats.put(0x668A2, new VehicleCraftingStat(200, 750, 100, 35, 0x07C84D, 100, 50)); //Apsalus                
        craftingStats.put(0xF4241, new VehicleCraftingStat(180, 750, 40, 110, 0x07C832, 600, 50)); //Gaw                   
        craftingStats.put(0xFB773, new VehicleCraftingStat(750, 100, 100, 0x07C832, 700, 70)); //Gallop                
        craftingStats.put(0xFB774, new VehicleCraftingStat(750, 100, 100, 0x07C832, 700, 70)); //Dobday        
        craftingStats.put(0xFB781, new VehicleCraftingStat(750, 100, 100, 0x07C832, 700, 70)); //Musai        
        craftingStats.put(0xFB782, new VehicleCraftingStat(750, 100, 100, 0x07C843, 200, 10)); //Chibe
         */
        //Sett crafting stats for ZEON spesifikke vehicles.
        craftingStats.put(0x704E3, new VehicleCraftingStat(0, 5, 110, 0x07C831, 10, 25)); //Magella attack custom
        craftingStats.put(0x68FB1, new VehicleCraftingStat(0, 5, 110, 0x07C831, 7, 30)); //Jicco
        craftingStats.put(0x68FB2, new VehicleCraftingStat(0, 5, 110, 0x07C831, 7, 30)); //Gattle
        craftingStats.put(0x668A6, new VehicleCraftingStat(50, 5, 110, 0x07C831, 10, 30)); //Oggo
        craftingStats.put(0x704E5, new VehicleCraftingStat(111, 5, 110, 0x07C831, 40, 25)); //Zaku tank

    }

    /**
     *
     * Setter crafting stats for vehicles som kan lages av både EF og Zeon.
     *
     */
    private static void COMMONvehicleStats() {

        craftingStats.put(0x61A80, new VehicleCraftingStat(0, 1, 110, 0x07C831, 3, 50)); //Elecar Aaron
        craftingStats.put(0x61A81, new VehicleCraftingStat(0, 1, 110, 0x07C831, 3, 50)); //Elecar Baal
        craftingStats.put(0x61A82, new VehicleCraftingStat(0, 1, 110, 0x07C831, 3, 50)); //Elecar Cabot
        craftingStats.put(0x61A83, new VehicleCraftingStat(0, 1, 110, 0x07C831, 3, 50)); //Elecar Dada
        craftingStats.put(0x61A8B, new VehicleCraftingStat(0, 1, 110, 0x07C831, 10, 50)); //Earp
        craftingStats.put(0x61A8C, new VehicleCraftingStat(0, 1, 110, 0x07C831, 10, 50)); //Gaea
        craftingStats.put(0x61A8A, new VehicleCraftingStat(0, 1, 110, 0x07C831, 10, 50)); //Fahrenheit
        craftingStats.put(0x61A97, new VehicleCraftingStat(0, 1, 110, 0x07C831, 10, 50)); //Icarus
        craftingStats.put(0x61A96, new VehicleCraftingStat(0, 1, 110, 0x07C831, 10, 50)); //Habakuk
        craftingStats.put(0x61A91, new VehicleCraftingStat(0, 1, 110, 0x07C831, 10, 25)); //Thundergoliat

    }
}
