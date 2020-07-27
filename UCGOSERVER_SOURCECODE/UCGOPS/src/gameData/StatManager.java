package gameData;

import java.util.HashMap;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å håndtere stats.
 */
public class StatManager {

    /**
     * Brukes til å holde alle MS og vehicle stats i spillet.
     */
    private static HashMap<Integer, StatMS> msStats = new HashMap<Integer, StatMS>();
    /**
     * Brukes til å holde alle weapon og shield stats i spillet.
     */
    private static HashMap<Integer, StatWeapon> weaponStats = new HashMap<Integer, StatWeapon>();
    /**
     * Brukes til å holde stats for GQ klær.
     */
    private static HashMap<Integer, StatGQclothes> GQclothesStats = new HashMap<Integer, StatGQclothes>();
    /**
     * Brukes til å holde stats for engines. Kun engines som gir bonus til MS er
     * lagret her.
     */
    private static HashMap<Integer, StatEngine> engineStats = new HashMap<Integer, StatEngine>();

    /**
     * Setter stats for MS/vehicle og våpen.
     */
    public static void execute() {

        setGQstats();
        setMsStats();
        setVehicleStats();
        setTaxiTransportStats();
        setWeaponStats();
        setEngineStats();
        setAttackBonus();
        setVisualBonus();
    }

    /**
     * Returnerer stats for MS/vehicle.
     *
     * @param id Item ID til MS/vehicle.
     *
     * @return Stats for oppgitt ID, eller null hvis ikke finnes.
     */
    public static StatMS getMsVehicleStat(int id) {
        return msStats.get(id);
    }

    /**
     * Returnerer stats for våpen.
     *
     * @param id Item ID for våpen.
     *
     * @return Stats for oppgitt ID, eller null hvis ikke finnes.
     */
    public static StatWeapon getWeaponStat(int id) {
        return weaponStats.get(id);
    }

    /**
     * Returnerer stats for GQ klær.
     *
     * @param id Item ID for klesplagget.
     *
     * @return Stats for oppgitt ID, eller null hvis ikke finnes.
     */
    public static StatGQclothes getGQclothesStat(int id) {
        return GQclothesStats.get(id);
    }

    /**
     * Returnerer stats for en engine som kan gi bonus til MS.
     *
     * @param id Item ID for engine.
     *
     * @return Stats for oppgitt ID, eller null dersom vi ikke har stats for
     * engine.
     */
    public static StatEngine getEngineStat(int id) {
        return engineStats.get(id);
    }

    /**
     * Setter for GQ klær.
     */
    private static void setGQstats() {

        //EF Uniformer
        GQclothesStats.put(0x3AAAE, new StatGQclothes(10, 0, 0)); //SR
        GQclothesStats.put(0x3AAAF, new StatGQclothes(10, 0, 0)); //SA
        GQclothesStats.put(0x3AAB0, new StatGQclothes(10, 0, 0)); //SN
        GQclothesStats.put(0x3AAB1, new StatGQclothes(10, 10, 0)); //PO
        GQclothesStats.put(0x3AAB2, new StatGQclothes(10, 10, 0)); //CPO
        GQclothesStats.put(0x3AAB3, new StatGQclothes(10, 10, 0)); //SCPO
        GQclothesStats.put(0x3AAB4, new StatGQclothes(10, 10, 10)); //ENS
        GQclothesStats.put(0x3AAB5, new StatGQclothes(15, 15, 10)); //LTJG
        GQclothesStats.put(0x3AAB6, new StatGQclothes(15, 15, 15)); //LT
        GQclothesStats.put(0x3AAB7, new StatGQclothes(20, 20, 15)); //LCDR
        GQclothesStats.put(0x3AAB8, new StatGQclothes(20, 20, 20)); //CDR
        GQclothesStats.put(0x3AAB9, new StatGQclothes(25, 25, 25)); //CAPT
        GQclothesStats.put(0x3AABA, new StatGQclothes(30, 30, 30)); //RDML 
        GQclothesStats.put(0x3AABB, new StatGQclothes(30, 30, 30)); //RADM
        GQclothesStats.put(0x3AABC, new StatGQclothes(30, 30, 30)); //VADM
        GQclothesStats.put(0x3AABD, new StatGQclothes(40, 40, 40)); //ADM

        //EF Overalls
        GQclothesStats.put(0x3AAEE, new StatGQclothes(-1, 5, 0)); //SR
        GQclothesStats.put(0x3AAF5, new StatGQclothes(-1, 5, 0)); //SA
        GQclothesStats.put(0x3AAF6, new StatGQclothes(-1, 5, 0)); //SN
        GQclothesStats.put(0x3AAF7, new StatGQclothes(-1, 5, 0)); //PO
        GQclothesStats.put(0x3AAF8, new StatGQclothes(-1, 5, 0)); //CPO
        GQclothesStats.put(0x3AAF9, new StatGQclothes(-1, 5, 0)); //SCPO
        GQclothesStats.put(0x3AAFA, new StatGQclothes(-1, 5, 0)); //ENS
        GQclothesStats.put(0x3AAFB, new StatGQclothes(-1, 5, 0)); //LTJG
        GQclothesStats.put(0x3AAFC, new StatGQclothes(-1, 5, 0)); //LT
        GQclothesStats.put(0x3AAFD, new StatGQclothes(-1, 5, 0)); //LCR
        GQclothesStats.put(0x3AAFE, new StatGQclothes(-1, 5, 0)); //CDR
        GQclothesStats.put(0x3AAFF, new StatGQclothes(-1, 5, 0)); //CAPT
        GQclothesStats.put(0x3AB00, new StatGQclothes(-1, 5, 0)); //RDML
        GQclothesStats.put(0x3AB01, new StatGQclothes(-1, 5, 0)); //RADM
        GQclothesStats.put(0x3AB02, new StatGQclothes(-1, 5, 0)); //VADM 
        GQclothesStats.put(0x3AB03, new StatGQclothes(-1, 5, 0)); //ADM

        //EF Spacesuits
        GQclothesStats.put(0x3AACE, new StatGQclothes(5, -1, 0)); //SR
        GQclothesStats.put(0x3AACF, new StatGQclothes(5, -1, 0)); //SA 
        GQclothesStats.put(0x3AAD0, new StatGQclothes(5, -1, 0)); //SN
        GQclothesStats.put(0x3AAD1, new StatGQclothes(5, -1, 0)); //PO
        GQclothesStats.put(0x3AAD2, new StatGQclothes(5, -1, 0)); //CPO
        GQclothesStats.put(0x3AAD3, new StatGQclothes(5, -1, 0)); //SCPO
        GQclothesStats.put(0x3AAD4, new StatGQclothes(5, -1, 0)); //ENS
        GQclothesStats.put(0x3AAD5, new StatGQclothes(5, -1, 0)); //LTJG
        GQclothesStats.put(0x3AAD6, new StatGQclothes(5, -1, 0)); //LT
        GQclothesStats.put(0x3AAD7, new StatGQclothes(5, -1, 0)); //LCDR
        GQclothesStats.put(0x3AAD8, new StatGQclothes(5, -1, 0)); //CDR
        GQclothesStats.put(0x3AAD9, new StatGQclothes(5, -1, 0)); //CAPT
        GQclothesStats.put(0x3AADA, new StatGQclothes(5, -1, 0)); //RDML
        GQclothesStats.put(0x3AADB, new StatGQclothes(5, -1, 0)); //RADM
        GQclothesStats.put(0x3AADC, new StatGQclothes(5, -1, 0)); //VADM
        GQclothesStats.put(0x3AADD, new StatGQclothes(5, -1, 0)); //ADM

        //Zeon overalls
        GQclothesStats.put(0x3AAEF, new StatGQclothes(-1, 5, 0)); //SR
        GQclothesStats.put(0x3AB04, new StatGQclothes(-1, 5, 0)); //SA
        GQclothesStats.put(0x3AB05, new StatGQclothes(-1, 5, 0)); //SN
        GQclothesStats.put(0x3AB06, new StatGQclothes(-1, 5, 0)); //PO
        GQclothesStats.put(0x3AB07, new StatGQclothes(-1, 5, 0)); //CPO
        GQclothesStats.put(0x3AB08, new StatGQclothes(-1, 5, 0)); //SCPO
        GQclothesStats.put(0x3AB09, new StatGQclothes(-1, 5, 0)); //ENS
        GQclothesStats.put(0x3AB0A, new StatGQclothes(-1, 5, 0)); //LTJG
        GQclothesStats.put(0x3AB0B, new StatGQclothes(-1, 5, 0)); //LT
        GQclothesStats.put(0x3AB0C, new StatGQclothes(-1, 5, 0)); //LCR
        GQclothesStats.put(0x3AB0D, new StatGQclothes(-1, 5, 0)); //CDR
        GQclothesStats.put(0x3AB0E, new StatGQclothes(-1, 5, 0)); //CAPT
        GQclothesStats.put(0x3AB0F, new StatGQclothes(-1, 5, 0)); //RDML
        GQclothesStats.put(0x3AB10, new StatGQclothes(-1, 5, 0)); //RADM
        GQclothesStats.put(0x3AB11, new StatGQclothes(-1, 5, 0)); //VADM 
        GQclothesStats.put(0x3AB12, new StatGQclothes(-1, 5, 0)); //ADM

        //Zeon Uniformer
        GQclothesStats.put(0x3AABE, new StatGQclothes(10, 0, 0)); //SR
        GQclothesStats.put(0x3AABF, new StatGQclothes(10, 0, 0)); //SA
        GQclothesStats.put(0x3AAC0, new StatGQclothes(10, 0, 0)); //SN
        GQclothesStats.put(0x3AAC1, new StatGQclothes(10, 10, 0)); //PO
        GQclothesStats.put(0x3AAC2, new StatGQclothes(10, 10, 0)); //CPO
        GQclothesStats.put(0x3AAC3, new StatGQclothes(10, 10, 0)); //SCPO
        GQclothesStats.put(0x3AAC4, new StatGQclothes(10, 10, 10)); //ENS
        GQclothesStats.put(0x3AAC5, new StatGQclothes(15, 15, 10)); //LTJG
        GQclothesStats.put(0x3AAC6, new StatGQclothes(15, 15, 15)); //LT
        GQclothesStats.put(0x3AAC7, new StatGQclothes(20, 20, 15)); //LCDR
        GQclothesStats.put(0x3AAC8, new StatGQclothes(20, 20, 20)); //CDR
        GQclothesStats.put(0x3AAC9, new StatGQclothes(25, 25, 25)); //CAPT
        GQclothesStats.put(0x3AACA, new StatGQclothes(30, 30, 30)); //RDML 
        GQclothesStats.put(0x3AACB, new StatGQclothes(30, 30, 30)); //RADM
        GQclothesStats.put(0x3AACC, new StatGQclothes(30, 30, 30)); //VADM
        GQclothesStats.put(0x3AACD, new StatGQclothes(40, 40, 40)); //ADM

        //Zeon Spacesuits
        GQclothesStats.put(0x3AADE, new StatGQclothes(5, -1, 0)); //SR
        GQclothesStats.put(0x3AADF, new StatGQclothes(5, -1, 0)); //SA 
        GQclothesStats.put(0x3AAE0, new StatGQclothes(5, -1, 0)); //SN
        GQclothesStats.put(0x3AAE1, new StatGQclothes(5, -1, 0)); //PO
        GQclothesStats.put(0x3AAE2, new StatGQclothes(5, -1, 0)); //CPO
        GQclothesStats.put(0x3AAE3, new StatGQclothes(5, -1, 0)); //SCPO
        GQclothesStats.put(0x3AAE4, new StatGQclothes(5, -1, 0)); //ENS
        GQclothesStats.put(0x3AAE5, new StatGQclothes(5, -1, 0)); //LTJG
        GQclothesStats.put(0x3AAE6, new StatGQclothes(5, -1, 0)); //LT
        GQclothesStats.put(0x3AAE7, new StatGQclothes(5, -1, 0)); //LCDR
        GQclothesStats.put(0x3AAE8, new StatGQclothes(5, -1, 0)); //CDR
        GQclothesStats.put(0x3AAE9, new StatGQclothes(5, -1, 0)); //CAPT
        GQclothesStats.put(0x3AAEA, new StatGQclothes(5, -1, 0)); //RDML
        GQclothesStats.put(0x3AAEB, new StatGQclothes(5, -1, 0)); //RADM
        GQclothesStats.put(0x3AAEC, new StatGQclothes(5, -1, 0)); //VADM
        GQclothesStats.put(0x3AAED, new StatGQclothes(5, -1, 0)); //ADM

        //Diverse uniformer
        GQclothesStats.put(0x3AB28, new StatGQclothes(40, 0, 0)); //EF combat uniform
        GQclothesStats.put(0x3AB2B, new StatGQclothes(40, 0, 0)); //Zeon combat uniform

        //Hats
        GQclothesStats.put(0x3AAF3, new StatGQclothes(10, 10, 10)); //EF Hat
        GQclothesStats.put(0x3AAAC, new StatGQclothes(10, 10, 10)); //EF Helmet
        GQclothesStats.put(0x3AAAD, new StatGQclothes(10, 10, 10)); //Zeon helmet
        GQclothesStats.put(0x3AB21, new StatGQclothes(10, 10, 10)); //EF work cap
        GQclothesStats.put(0x3AB22, new StatGQclothes(10, 10, 10)); //Zeon work cap
        GQclothesStats.put(0x3AAF4, new StatGQclothes(10, 10, 10)); //Zeon hat
        GQclothesStats.put(0x3AB13, new StatGQclothes(30, 0, 0)); //Zeon hat S
        GQclothesStats.put(0x3AB54, new StatGQclothes(0, 30, 0)); //Bandana 3 color
        GQclothesStats.put(0x3AB53, new StatGQclothes(0, 30, 0)); //Bandana tiger stipe
        GQclothesStats.put(0x3AB55, new StatGQclothes(0, 30, 0)); //Bandana urban camo
        GQclothesStats.put(0x3AB26, new StatGQclothes(30, 0, 0)); //EF Combat helmet
        GQclothesStats.put(0x3AB2A, new StatGQclothes(10, 10, 10)); //Zeon overseas cap
        GQclothesStats.put(0x3AB1F, new StatGQclothes(0, 15, 15)); //Headset mathum sonic
        GQclothesStats.put(0x3AB20, new StatGQclothes(0, 15, 15)); //Headset felipe
        GQclothesStats.put(0x3AB24, new StatGQclothes(0, 15, 15)); //Headset suze
        GQclothesStats.put(0x3AB25, new StatGQclothes(0, 15, 15)); //Headset gramonica

        //Glasses
        GQclothesStats.put(0x3AB56, new StatGQclothes(0, 30, 10)); //Eye patch
        GQclothesStats.put(0x3AB57, new StatGQclothes(40, 0, 0)); //Eye patch skull
        GQclothesStats.put(0x3AB15, new StatGQclothes(0, 20, 20)); //Wire rim
        GQclothesStats.put(0x3AB1C, new StatGQclothes(10, 10, 20)); //Round glasses
        GQclothesStats.put(0x3AB17, new StatGQclothes(0, 15, 15)); //Black frame
        GQclothesStats.put(0x3AB23, new StatGQclothes(15, 25, 0)); //Goggles
        GQclothesStats.put(0x3AB16, new StatGQclothes(0, 25, 15)); //Half rim
        GQclothesStats.put(0x3AB14, new StatGQclothes(20, 10, 10)); //Sports
        GQclothesStats.put(0x3AAF2, new StatGQclothes(10, 20, 10)); //Sunglasses
        GQclothesStats.put(0x3AA41, new StatGQclothes(0, 0, 40)); //Protective Sunglasses (ikke GQ versjon)

        //Gloves
        GQclothesStats.put(0x3AAF0, new StatGQclothes(30, 0, 0)); //EF gloves
        GQclothesStats.put(0x3AAF1, new StatGQclothes(30, 0, 0)); //Zeon gloves
        GQclothesStats.put(0x3AB29, new StatGQclothes(0, 15, 15)); //EF  fingerless gloves
        GQclothesStats.put(0x3AB4D, new StatGQclothes(0, 10, 20)); //Stud wristband gold
        GQclothesStats.put(0x3AB4C, new StatGQclothes(20, 0, 10)); //Stud wristband silver
        GQclothesStats.put(0x3AB2C, new StatGQclothes(0, 15, 15)); //Zeon long gloves
        GQclothesStats.put(0x3AB1A, new StatGQclothes(10, 10, 10)); //Sportswatch

        //T-shirt
        GQclothesStats.put(0x3AA30, new StatGQclothes(10, 0, 0)); //EF limited edition
        GQclothesStats.put(0x3AA31, new StatGQclothes(10, 0, 0)); //Zeon limited edition
        GQclothesStats.put(0x3AA39, new StatGQclothes(5, 0, 0)); //Limited edition

    }

    /**
     * Setter stats for EF og Zeon MS.
     */
    private static void setMsStats() {
        //NB! Anti beam coating for Hyaku Shiki settes i slutten av metoden.

        //Variabler brukt for å angi faction lock.
        int EF = 1;
        int ZEON = 2;
        int NONE = -1;

        //Stats for battleships.
        msStats.put(0xFB77E, new StatMS(ZEON, 40000, 30, 20, 20, 0x046D07, 0)); //Fat Uncle hangar
        msStats.put(0xF424A, new StatMS(EF, 40000, 30, 20, 20, 0x046D07, 0)); //Midea hangar
        msStats.put(0xF4241, new StatMS(ZEON, 120000, 30, 20, 60, 0x046CDC, 0)); //Gaw
        msStats.put(0xF4242, new StatMS(EF, 65000, 30, 20, 60, 0x046CDC, 0)); //Midea gray
        msStats.put(0xFB773, new StatMS(ZEON, 65000, 20, 80, 40, 0x046CDC, 0)); //Gallop
        msStats.put(0xFB774, new StatMS(ZEON, 250000, 20, 80, 40, 0x046CDC, 0)); //Dobday
        msStats.put(0xFB771, new StatMS(EF, 250000, 20, 80, 40, 0x046CDC, 0)); //Bigtray

        msStats.put(1030005, new StatMS(EF, 200000, 0, 0, 0, 0x046CDC, 0)); //Pegasus
        msStats.put(1030006, new StatMS(ZEON, 200000, 0, 0, 0, 0x046CDC, 0)); //Zanzibar
        msStats.put(1030015, new StatMS(EF, 120000, 20, 0, 40, 0x046CDC, 0)); //Salamis
        msStats.put(1030016, new StatMS(EF, 200000, 20, 100, 50, 0x046CDC, 0)); //Magellan
        msStats.put(1030017, new StatMS(ZEON, 120000, 20, 0, 40, 0x046CDC, 0)); //Musai
        msStats.put(1030018, new StatMS(ZEON, 200000, 20, 100, 50, 0x046CDC, 0)); //Chibe

        //TEST MS
        msStats.put(1030000, new StatMS(NONE, 300000, 140, 90, 100, 0x046D11, 0)); //Zeta
        msStats.put(400004, new StatMS(NONE, 10000, 10, 20, 30, 0x046CDC, 0)); //Zarathustra


        /*
         * Definer MS bonuser.
         */
 /*MsBonus guncannonBonus = new MsBonus();
        guncannonBonus.setGuncannonBonus(true);
        guncannonBonus.setGuncannonAceBonus(true);

        MsBonus guntankBonus = new MsBonus();
        guntankBonus.setGuntankBonus(true);

        MsBonus sniperBonus = new MsBonus();
        sniperBonus.setSniperBonus(true);

        MsBonus marineLowBonus = new MsBonus();
        marineLowBonus.setMarineLowEndBonus(true);

        MsBonus marineHighBonus = new MsBonus();
        marineHighBonus.setMarineHighEndBonus(true);

        MsBonus domBonus = new MsBonus();
        domBonus.setDomBonus(true);

        MsBonus gmLowEndBonus = new MsBonus();
        gmLowEndBonus.setGmLowEndBonus(true);

        MsBonus gmHighEndBonus = new MsBonus();
        gmHighEndBonus.setGmHighEndBonus(true);

        MsBonus zakuBonus = new MsBonus();
        zakuBonus.setZakuBonus(true);

        MsBonus goufBonus = new MsBonus();
        goufBonus.setGoufBonus(true);

        MsBonus zannyBonus = new MsBonus();
        zannyBonus.setZakuBonus(true);
        zannyBonus.setGmLowEndBonus(true);
         */
 /*
         * Default engine typer brukt.
         * 0x046CDC = L1C TR
         * 0x046CE9 = L1C TJ
         * 0x046CE0 = L2C TR
         * 0x046D07 = L1C HB MS engine Lvl 1 etter 2019 oppdatering
         * 0x046D0C = L2C HB
         * 0x046D11 = L3C HB
         * 0x046CFD = L2C HJ
         * 0x046CF8 = L1C HJ
         * 
         */
        //EF MS.
        msStats.put(0x641BA, new StatMS(EF, 15000, 70, 60, 70, 0x46D07, 0)); //GM Trainer
        msStats.put(0x641CE, new StatMS(EF, 18000, 40, 30, 70, 0x46D07, 0)); //Zanny
        msStats.put(0x64190, new StatMS(EF, 20000, 70, 60, 70, 0x46D07, 0)); //RGM-79
        msStats.put(0x64193, new StatMS(EF, 25000, 70, 70, 70, 0x46D07, 0)); //RGC-80 GM cannon
        msStats.put(0x6419B, new StatMS(EF, 35000, 40, 70, 70, 0x46D07, 0)); //RX-75R
        msStats.put(0x641BB, new StatMS(EF, 15000, 100, 60, 70, 0x46D07, 0)); //RGM-79L
        msStats.put(0x641C4, new StatMS(EF, 24000, 70, 60, 90, 0x46D07, 40)); //RGM-79D
        msStats.put(0x641C8, new StatMS(EF, 40000, 40, 140, 70, 0x46D07, 40)); //RMV-1 Guntank 2
        msStats.put(0x64194, new StatMS(EF, 28000, 70, 60, 90, 0x46D07, 80)); //RX-77D
        msStats.put(0x641B4, new StatMS(EF, 30000, 70, 60, 70, 0x46D07, 60)); //GM sniper
        msStats.put(0x6419C, new StatMS(EF, 40000, 40, 150, 90, 0x46D07, 100)); //RX-75
        msStats.put(0x641A7, new StatMS(EF, 30000, 70, 60, 70, 0x46D07, 60)); //RGM-79(G)
        msStats.put(0x641A8, new StatMS(EF, 24000, 90, 90, 90, 0x46D07, 100)); //RGM-79C ground
        msStats.put(0x641D7, new StatMS(EF, 24000, 90, 90, 90, 0x46D07, 100)); //RGM-79C space
        msStats.put(0x64192, new StatMS(EF, 28000, 90, 60, 90, 0x46D07, 100)); //RGM-79G, ground
        msStats.put(0x641C5, new StatMS(EF, 28000, 90, 60, 90, 0x46D07, 100)); //RGM-79G, space
        msStats.put(0x641B6, new StatMS(EF, 24000, 70, 150, 90, 0x46D07, 100)); //RGM-79SC
        msStats.put(0x641CC, new StatMS(EF, 30000, 90, 70, 90, 0x46D07, 160)); //RX-77-4
        msStats.put(0x64196, new StatMS(EF, 40000, 70, 70, 90, 0x46D07, 120)); //RX 77-2
        msStats.put(0xF6968, new StatMS(EF, 30000, 90, 30, 70, 0x46D07, 160)); //GM HEAD
        msStats.put(0x641AD, new StatMS(EF, 30000, 90, 60, 90, 0x46D07, 180)); //RX-79G
        msStats.put(0x641B9, new StatMS(EF, 60000, 40, 70, 90, 0x46D07, 140)); //RX 77-3
        msStats.put(0x641B5, new StatMS(EF, 30000, 100, 150, 90, 0x46D07, 180)); //RGM-79SP
        msStats.put(0x641AE, new StatMS(EF, 32000, 130, 100, 90, 0x46D07, 200)); //RX-79G Ez8
        msStats.put(0x641AF, new StatMS(EF, 30000, 175, 50, 90, 0x46D07, 200)); //RX 78-1
        msStats.put(0x64195, new StatMS(EF, 32000, 200, 100, 110, 0x46D07, 200)); //RX 78-2
        msStats.put(0x6423C, new StatMS(EF, 32000, 225, 100, 110, 0x46D07, 250)); //RX 78-2MC
        msStats.put(0x641D9, new StatMS(EF, 32000, 300, 100, 110, 0x46D07, 300)); //G-3
        msStats.put(0x641DA, new StatMS(EF, 60000, 140, 100, 110, 0x46D07, 300)); //FA Gundam
        msStats.put(0x64245, new StatMS(EF, 32000, 300, 100, 140, 0x046D07, 400)); //RX-78NT-1

        msStats.put(0x6421D, new StatMS(EF, 18000, 40, 30, 140, 0x046CDC, 0)); //Zanny farget		        
        msStats.put(0x6421B, new StatMS(EF, 18000, 90, 150, 70, 0x046CE0, 0)); //RGM-79L Brun
        msStats.put(0x64207, new StatMS(EF, 18000, 90, 150, 70, 0x046CE0, 0)); //RGM-79L Rosa
        msStats.put(0x641EF, new StatMS(EF, 18000, 90, 150, 70, 0x046CE0, 0)); //RGM-79L Blå
        msStats.put(0x64213, new StatMS(EF, 18000, 90, 150, 70, 0x046CE0, 0)); //RGM-79L Grønn        
        msStats.put(0xF6957, new StatMS(EF, 20000, 70, 60, 70, 0x046CDC, 0)); //RGM-79 Black
        msStats.put(0xF695F, new StatMS(EF, 20000, 70, 60, 70, 0x046CDC, 0)); //RGM-79 Farget		        
        msStats.put(0x64206, new StatMS(EF, 20000, 70, 60, 70, 0x046CDC, 0)); //GM cannon Svart        
        msStats.put(0x64208, new StatMS(EF, 20000, 70, 200, 70, 0x046CDC, 0)); //RGM-79D rød                       
        msStats.put(0xF6960, new StatMS(EF, 25000, 90, 60, 90, 0x046CDC, 0)); //RGM-79G, ground Farget                
        msStats.put(0x64209, new StatMS(EF, 25000, 90, 150, 90, 0x046CE0, 120)); //Sniper custom Lilla
        msStats.put(0x641DD, new StatMS(EF, 25000, 90, 150, 90, 0x046CE0, 120)); //Sniper custom orange		       
        msStats.put(0x6421A, new StatMS(EF, 20000, 90, 60, 90, 0x046CE0, 20)); //RGM-79C ground grå/svart
        msStats.put(0x641F0, new StatMS(EF, 20000, 90, 60, 90, 0x046CE0, 20)); //RGM-79C ground rød/gul
        msStats.put(0x641DE, new StatMS(EF, 20000, 90, 60, 90, 0x046CE0, 20)); //RGM-79C ground farget        
        msStats.put(0x641E6, new StatMS(EF, 25000, 70, 60, 70, 0x046CDC, 0)); //RX-77D farget        
        msStats.put(0x64223, new StatMS(EF, 25000, 70, 100, 70, 0x046CE0, 40)); //RGM-79(G) blå
        msStats.put(0x64215, new StatMS(EF, 25000, 70, 100, 70, 0x046CE0, 40)); //RGM-79(G) grønn/gul
        msStats.put(0x641F1, new StatMS(EF, 25000, 70, 100, 70, 0x046CE0, 40)); //RGM-79(G) grå/rød
        msStats.put(0x641EA, new StatMS(EF, 25000, 70, 100, 70, 0x046CE0, 40)); //RGM-79(G) grå/sølv
        msStats.put(0x6422F, new StatMS(EF, 25000, 70, 100, 70, 0x046CE0, 40)); //RGM-79(G)
        msStats.put(0x64247, new StatMS(EF, 25000, 70, 60, 90, 0x046CDC, 40)); //RMS-179 GM                
        msStats.put(0x641F5, new StatMS(EF, 25000, 90, 150, 90, 0x046CE0, 150)); //Sniper 2 rød/svart
        msStats.put(0x641DC, new StatMS(EF, 25000, 90, 150, 90, 0x046CE0, 150)); //Sniper 2 Farget
        msStats.put(0x6420A, new StatMS(EF, 25000, 90, 150, 90, 0x046CE0, 150)); //Sniper 2 Sand/brun
        msStats.put(0x64230, new StatMS(EF, 25000, 90, 150, 90, 0x046CE0, 150)); //Sniper 2        
        msStats.put(0x641FC, new StatMS(EF, 30000, 90, 60, 90, 0x046D0C, 170)); //Guncannon 2 Hvit/Rød
        msStats.put(0x6423B, new StatMS(EF, 30000, 90, 60, 90, 0x046D0C, 170)); //Guncannon 2                
        msStats.put(0x64248, new StatMS(EF, 30000, 130, 30, 110, 0x046D07, 180)); //MSA-003 Nemo        
        msStats.put(0xF6958, new StatMS(EF, 30000, 175, 60, 90, 0x046D0C, 0)); //RX 78-1 Gold        
        msStats.put(0x64224, new StatMS(EF, 25000, 90, 60, 90, 0x046D0C, 180)); //RX-79G grå
        msStats.put(0x64216, new StatMS(EF, 25000, 90, 60, 90, 0x046D0C, 180)); //RX-79G hvit
        msStats.put(0x641F2, new StatMS(EF, 25000, 90, 60, 90, 0x046D0C, 180)); //RX-79G lilla/rosa
        msStats.put(0xF695D, new StatMS(EF, 25000, 90, 60, 90, 0x046D0C, 180)); //RX-79G Farget
        msStats.put(0xF6966, new StatMS(EF, 25000, 90, 60, 90, 0x046D0C, 180)); //RX-79G Farget
        msStats.put(0x6422B, new StatMS(EF, 25000, 90, 60, 90, 0x046D0C, 180)); //RX-79G Farget
        msStats.put(0x6422C, new StatMS(EF, 25000, 90, 60, 90, 0x046D0C, 180)); //RX-79G Farget
        msStats.put(0x6422D, new StatMS(EF, 25000, 90, 60, 90, 0x046D0C, 180)); //RX-79G Farget
        msStats.put(0x6422E, new StatMS(EF, 25000, 90, 60, 90, 0x046D0C, 180)); //RX-79G Farget
        msStats.put(0x641EB, new StatMS(EF, 25000, 90, 60, 90, 0x046D0C, 180)); //RX-79G Blå        
        msStats.put(0x64225, new StatMS(EF, 25000, 90, 30, 70, 0x046D0C, 0)); //GM HEAD Blue destiny        
        msStats.put(0x64217, new StatMS(EF, 30000, 130, 150, 90, 0x046D0C, 200)); //RX-79G Ez8 brun/sand
        msStats.put(0x641F3, new StatMS(EF, 30000, 130, 150, 90, 0x046D0C, 200)); //RX-79G Ez8 grå/rød
        msStats.put(0x641EC, new StatMS(EF, 30000, 130, 150, 90, 0x046D0C, 200)); //RX-79G Ez8 Gul
        msStats.put(0xF6967, new StatMS(EF, 30000, 130, 150, 90, 0x046D0C, 200)); //RX-79G Ez8 Farget        
        msStats.put(0xF6954, new StatMS(EF, 30000, 70, 60, 90, 0x046D0C, 150)); //RX 77-2 Farget
        msStats.put(0xF695C, new StatMS(EF, 30000, 70, 60, 90, 0x046D0C, 150)); //RX 77-2 Farget        
        msStats.put(0x6421E, new StatMS(EF, 40000, 40, 150, 90, 0x046D0C, 40)); //RX-75 farget
        msStats.put(0x64249, new StatMS(EF, 32000, 130, 30, 110, 0x046CDC, 200)); //RMS-108 Marasai
        msStats.put(0x64257, new StatMS(EF, 25000, 200, 50, 110, 0x046D07, 200)); //RGM-89 Jegan        
        msStats.put(0x6420E, new StatMS(EF, 32000, 200, 100, 110, 0x046D0C, 300)); //RX 78-2 Blå/Titans
        msStats.put(0x6420F, new StatMS(EF, 32000, 200, 100, 110, 0x046D0C, 300)); //RX 78-2 Zeon Gundam
        msStats.put(0x641F9, new StatMS(EF, 32000, 200, 100, 110, 0x046D0C, 300)); //RX 78-2 Char Aznable
        msStats.put(0x64233, new StatMS(EF, 32000, 200, 100, 110, 0x046D0C, 300)); //RX 78-2
        msStats.put(0x64234, new StatMS(EF, 30000, 200, 100, 110, 0x046D0C, 300)); //RX 78-2
        msStats.put(0x64235, new StatMS(EF, 32000, 200, 100, 110, 0x046D0C, 300)); //RX 78-2       
        msStats.put(0x6423D, new StatMS(EF, 32000, 210, 100, 110, 0x046D0C, 300)); //RX 78-2MC
        msStats.put(0x6423E, new StatMS(EF, 32000, 210, 100, 110, 0x046D0C, 300)); //RX 78-2MC
        msStats.put(0x6423F, new StatMS(EF, 32000, 210, 100, 110, 0x046D0C, 300)); //RX 78-2MC
        msStats.put(0x64240, new StatMS(EF, 32000, 210, 100, 110, 0x046D0C, 300)); //RX 78-2MC
        msStats.put(0x64241, new StatMS(EF, 32000, 210, 100, 110, 0x046D0C, 300)); //RX 78-2MC
        msStats.put(0x64242, new StatMS(EF, 32000, 210, 100, 110, 0x046D0C, 300)); //RX 78-2MC                                        
        msStats.put(0x64219, new StatMS(EF, 32000, 300, 100, 110, 0x046D0C, 400)); //G-3 Grå/svart
        msStats.put(0x6420D, new StatMS(EF, 32000, 300, 100, 110, 0x046D0C, 400)); //G-3 Hvit/Blå
        msStats.put(0x641F4, new StatMS(EF, 32000, 300, 100, 110, 0x046D0C, 400)); //G-3 rød
        msStats.put(0x641ED, new StatMS(EF, 32000, 300, 100, 110, 0x046D0C, 400)); //G-3 Farget
        msStats.put(0x64231, new StatMS(EF, 32000, 300, 100, 110, 0x046D0C, 400)); //G-3        
        msStats.put(0x6424B, new StatMS(EF, 35000, 225, 100, 110, 0x046D0C, 350)); //RX-78GP01
        msStats.put(0x64251, new StatMS(EF, 35000, 250, 100, 140, 0x046D0C, 400)); //RX-178 AUEG
        msStats.put(0x64252, new StatMS(EF, 35000, 250, 100, 140, 0x046D0C, 400)); //RX-178 TITANS
        msStats.put(0x64258, new StatMS(EF, 50000, 250, 100, 140, 0x046D0C, 400)); //RX-178 Super Gundam AEUG
        msStats.put(0x6425B, new StatMS(EF, 50000, 250, 100, 140, 0x046D0C, 400)); //RX-178 Super Gundam TITANS
        msStats.put(0x6425C, new StatMS(EF, 35000, 250, 100, 140, 0x046D0C, 400)); //MSN-00100 Hyaku Shiki
        msStats.put(0x64260, new StatMS(EF, 40000, 250, 100, 140, 0x046D0C, 400)); //MSZ-006 Zeta Gundam
        msStats.put(0x64253, new StatMS(EF, 50000, 300, 90, 110, 0x046D0C, 400)); //RX-160 Byarlant EF versjon
        msStats.put(0x64255, new StatMS(EF, 32000, 200, 150, 140, 0x046D0C, 300)); //PMX-002 Bolinoak EF versjon       
        msStats.put(0x6424E, new StatMS(EF, 40000, 200, 100, 110, 0x046D0C, 300)); //RMS-099 Rick Dias EF versjon
        msStats.put(0x6424C, new StatMS(EF, 50000, 200, 100, 110, 0x046D0C, 400)); //PMX-001 Palace Athene EF versjon        
        msStats.put(0x641DB, new StatMS(EF, 60000, 140, 100, 110, 0x046D0C, 300)); //FA Gundam Blå
        msStats.put(0x641FA, new StatMS(EF, 60000, 140, 100, 110, 0x046D0C, 300)); //FA Gundam Hvit
        msStats.put(0x641FB, new StatMS(EF, 60000, 140, 100, 110, 0x046D0C, 300)); //FA Gundam Brun
        msStats.put(0x64250, new StatMS(EF, 60000, 90, 150, 110, 0x046D0C, 400)); //RX-78GP02
        msStats.put(0x668A7, new StatMS(EF, 30000, 40, 60, 70, 0x046D11, 0)); //Core Booster        

        //Zeon MS stats.
        msStats.put(0x64191, new StatMS(ZEON, 15000, 70, 30, 70, 0x46D07, 0)); //MS-05B
        msStats.put(0x641A4, new StatMS(ZEON, 24000, 40, 100, 70, 0x46D07, 0)); //MS-12 Gigan
        msStats.put(0xF6964, new StatMS(ZEON, 15000, 100, 30, 70, 0x46D07, 0)); //MS-05S Ramba Ral
        msStats.put(0x64197, new StatMS(ZEON, 20000, 70, 30, 70, 0x46D07, 0)); //MS-06F
        msStats.put(0x6419E, new StatMS(ZEON, 20000, 50, 30, 70, 0x46D07, 0)); //MS-06K Zaku Cannon
        msStats.put(0x641AB, new StatMS(ZEON, 20000, 50, 30, 70, 0x46D07, 0)); //MS-06K Zaku Cannon Rabbit Ear
        msStats.put(0x6419F, new StatMS(ZEON, 20000, 70, 60, 70, 0x46D07, 0)); //MS-06D Desert type
        msStats.put(0x641AC, new StatMS(ZEON, 20000, 70, 60, 70, 0x46D07, 0)); //MS-06D Karakal Custom
        msStats.put(0x641BE, new StatMS(ZEON, 24000, 70, 60, 90, 0x46D07, 40)); //MS-06F2 type A
        msStats.put(0x641B0, new StatMS(ZEON, 24000, 70, 60, 90, 0x46D07, 40)); //MS-06F2 type B
        msStats.put(0x641B1, new StatMS(ZEON, 24000, 70, 60, 90, 0x46D07, 40)); //MS-06F2 Neuen Bitter
        msStats.put(0x641B2, new StatMS(ZEON, 32000, 70, 30, 70, 0x46D07, 40)); //MSM-04 Acguy
        msStats.put(0x641C0, new StatMS(ZEON, 40000, 90, 30, 90, 0x46D07, 60)); //MSM-04N Agguguy
        msStats.put(0x641A3, new StatMS(ZEON, 40000, 90, 30, 90, 0x46D07, 60)); //MSM-04N heatrod
        msStats.put(0x641A2, new StatMS(ZEON, 40000, 70, 60, 70, 0x46D07, 60)); //MSM-04G Juaggu
        msStats.put(0x641D2, new StatMS(ZEON, 20000, 90, 30, 70, 0x46D07, 60)); //MS-06RP
        msStats.put(0x641A0, new StatMS(ZEON, 24000, 70, 60, 70, 0x46D07, 60)); //YMS-09 Prototype DOM
        msStats.put(0x641B3, new StatMS(ZEON, 24000, 70, 60, 70, 0x46D07, 60)); //YMS-09D DOM TTT        
        msStats.put(0x641D3, new StatMS(ZEON, 24000, 90, 30, 70, 0x46D07, 80)); //MS-06R-1     
        msStats.put(0xF696C, new StatMS(ZEON, 24000, 90, 60, 70, 0x46D07, 80)); //MS-06R-1A        
        msStats.put(0x6419A, new StatMS(ZEON, 20000, 90, 30, 70, 0x46D07, 80)); //MS-06S                 
        msStats.put(0xF6950, new StatMS(ZEON, 20000, 100, 30, 70, 0x46D07, 80)); //MS-06S Char Aznable
        msStats.put(0x641D4, new StatMS(ZEON, 24000, 90, 60, 70, 0x46D07, 100)); //MS-06R-2P
        msStats.put(0x641D8, new StatMS(ZEON, 24000, 90, 60, 90, 0x46D07, 100)); //MS-06R-2
        msStats.put(0x64198, new StatMS(ZEON, 28000, 90, 60, 90, 0x46D07, 100)); //MS-06FZ type A
        msStats.put(0x64199, new StatMS(ZEON, 28000, 90, 60, 90, 0x46D07, 100)); //MS-06FZ type B                
        msStats.put(0x6419D, new StatMS(ZEON, 24000, 90, 90, 90, 0x46D07, 100)); //MS-07B
        msStats.put(0x641AA, new StatMS(ZEON, 50000, 70, 100, 90, 0x46D07, 100)); //MSM-03 Gogg
        msStats.put(0x641A9, new StatMS(ZEON, 40000, 100, 90, 90, 0x46D07, 120)); //MSM-07 Z'Gok
        msStats.put(0x641A1, new StatMS(ZEON, 28000, 90, 90, 90, 0x46D07, 120)); //MS-09B
        msStats.put(0x641D5, new StatMS(ZEON, 28000, 90, 90, 90, 0x46D07, 120)); //MS-09R
        msStats.put(0x641C3, new StatMS(ZEON, 50000, 100, 90, 90, 0x46D07, 140)); //MSM-07E Z'Gok
        msStats.put(0x64203, new StatMS(ZEON, 28000, 130, 100, 90, 0x46D07, 160)); //MS-09RS
        msStats.put(0xF695B, new StatMS(ZEON, 45000, 130, 90, 90, 0x46D07, 160)); //MSM-07S        
        msStats.put(0x641A5, new StatMS(ZEON, 60000, 100, 90, 90, 0x46D07, 180)); //MS-13 Gasshia        
        msStats.put(0x641A6, new StatMS(ZEON, 40000, 100, 90, 90, 0x46D07, 180)); //MSM-08 ZoGok        
        msStats.put(0x641C9, new StatMS(ZEON, 32000, 100, 100, 100, 0x46D07, 200)); //MS-14A        
        msStats.put(0x641CA, new StatMS(ZEON, 28000, 200, 100, 110, 0x46D07, 200)); //MS-14S
        msStats.put(0x64246, new StatMS(ZEON, 30000, 130, 100, 100, 0x046D0C, 200)); //YMS-15 Gyan
        msStats.put(0xF695E, new StatMS(ZEON, 28000, 200, 100, 110, 0x46D07, 250)); //MS-14S Char Aznable
        msStats.put(0x641CB, new StatMS(ZEON, 32000, 175, 100, 100, 0x46D07, 250)); //MS-14B
        msStats.put(0x64243, new StatMS(ZEON, 40000, 300, 150, 100, 0x046D07, 400)); //MS-18E Kampfer

        msStats.put(0xF6952, new StatMS(ZEON, 18000, 40, 150, 40, 0x046CDC, 0)); //ZakuI Black Tristars        
        msStats.put(0xF6961, new StatMS(ZEON, 20000, 70, 60, 70, 0x046CDC, 0)); //Zaku2 Farget
        msStats.put(0xF696A, new StatMS(ZEON, 20000, 70, 60, 70, 0x046CDC, 0)); //Zaku2 Farget                
        msStats.put(0xF6959, new StatMS(ZEON, 20000, 70, 60, 70, 0x046CDC, 0)); //Zaku desert A Green                
        msStats.put(0xF6956, new StatMS(ZEON, 25000, 70, 60, 70, 0x046CDC, 0)); //Zaku F2 type A Farget
        msStats.put(0xF6962, new StatMS(ZEON, 25000, 70, 60, 70, 0x046CDC, 0)); //Zaku F2 type A Farget                
        msStats.put(0xF6975, new StatMS(ZEON, 20000, 90, 60, 70, 0x046CDC, 0)); //Zaku2 R1 Shin Matsunaga                        
        msStats.put(0xF696D, new StatMS(ZEON, 20000, 90, 60, 70, 0x046CDC, 0)); //Zaku2 R2 Johnny Ridden              
        msStats.put(0x6421C, new StatMS(ZEON, 25000, 70, 100, 90, 0x046CE0, 10)); //Zaku2 S	svart        
        msStats.put(0xF695A, new StatMS(ZEON, 25000, 70, 100, 90, 0x046CE0, 10)); //Zaku2 S Silver        
        msStats.put(0x641E0, new StatMS(ZEON, 20000, 70, 60, 70, 0x046CE0, 10)); //Prototype DOM Farget        
        msStats.put(0x641E1, new StatMS(ZEON, 25000, 70, 60, 70, 0x046CE0, 30)); //DOM TTT Farget        
        msStats.put(0x641DF, new StatMS(ZEON, 25000, 90, 60, 90, 0x046CE0, 30)); //Gouf Farget hvit
        msStats.put(0x641EE, new StatMS(ZEON, 25000, 90, 60, 90, 0x046CE0, 30)); //Gouf Farget gul
        msStats.put(0xF6963, new StatMS(ZEON, 25000, 90, 60, 90, 0x046CE0, 30)); //Gouf Farget svart
        msStats.put(0x6420B, new StatMS(ZEON, 25000, 90, 60, 90, 0x046CE0, 30)); //Gouf Lysblå
        msStats.put(0x64236, new StatMS(ZEON, 25000, 90, 60, 90, 0x046CE0, 30)); //Gouf
        msStats.put(0x64237, new StatMS(ZEON, 25000, 90, 60, 90, 0x046CE0, 30)); //Gouf
        msStats.put(0x64238, new StatMS(ZEON, 25000, 90, 60, 90, 0x046CE0, 30)); //Gouf        
        msStats.put(0x641FF, new StatMS(ZEON, 30000, 70, 60, 70, 0x046CF8, 0)); //Acguy rød                
        msStats.put(0x64222, new StatMS(ZEON, 30000, 90, 150, 90, 0x046CFD, 50)); //Z'gok Gul
        msStats.put(0x64214, new StatMS(ZEON, 30000, 90, 150, 90, 0x046CFD, 50)); //Z'gok Lilla
        msStats.put(0x64200, new StatMS(ZEON, 30000, 90, 150, 90, 0x046CFD, 50)); //Z'gok Svart					        
        msStats.put(0x64202, new StatMS(ZEON, 30000, 90, 100, 80, 0x046CFD, 30)); //ZoGok Svart/Hvit				        
        msStats.put(0x64226, new StatMS(ZEON, 25000, 90, 60, 90, 0x046CE0, 40)); //DOM Svart
        msStats.put(0xF6953, new StatMS(ZEON, 25000, 90, 60, 90, 0x046CE0, 40)); //DOM Farget
        msStats.put(0x64232, new StatMS(ZEON, 25000, 90, 60, 90, 0x046CE0, 40)); //DOM                
        msStats.put(0x64210, new StatMS(ZEON, 25000, 130, 150, 90, 0x046CE0, 100)); //MS-09RS DOM Gato        
        msStats.put(0x641FE, new StatMS(ZEON, 40000, 90, 200, 90, 0x046CE0, 50)); //Gasshia Svart/HVit        
        msStats.put(0x64239, new StatMS(ZEON, 40000, 70, 150, 70, 0x046CFD, 0)); //Gogg rosa
        msStats.put(0x641E2, new StatMS(ZEON, 40000, 70, 150, 70, 0x046CFD, 0)); //Gogg Farget        
        msStats.put(0x6425D, new StatMS(ZEON, 40000, 130, 150, 90, 0x046CFD, 150)); //Hygogg        
        msStats.put(0x6421F, new StatMS(ZEON, 25000, 90, 60, 90, 0x046D0C, 200)); //MS-14A grønn
        msStats.put(0x64218, new StatMS(ZEON, 25000, 90, 60, 90, 0x046D0C, 200)); //MS-14A rød/sand
        msStats.put(0x641E3, new StatMS(ZEON, 25000, 90, 60, 90, 0x046D0C, 200)); //MS-14A Farget
        msStats.put(0x641F6, new StatMS(ZEON, 25000, 90, 60, 90, 0x046D0C, 200)); //MS-14A mørk grå
        msStats.put(0x641E7, new StatMS(ZEON, 25000, 90, 60, 90, 0x046D0C, 200)); //MS-14A Farget 2
        msStats.put(0xF6969, new StatMS(ZEON, 25000, 90, 60, 90, 0x046D0C, 200)); //MS-14A Anavel Gato
        msStats.put(0x64228, new StatMS(ZEON, 25000, 90, 60, 90, 0x046D0C, 200)); //MS-14A Farget        
        msStats.put(0x64227, new StatMS(ZEON, 40000, 90, 100, 90, 0x046CFD, 70)); //Z'gok E brun
        msStats.put(0x6420C, new StatMS(ZEON, 40000, 90, 100, 90, 0x046CFD, 70)); //Z'gok E blå
        msStats.put(0x64201, new StatMS(ZEON, 40000, 90, 100, 90, 0x046CFD, 70)); //Z'gok E orange
        msStats.put(0x6423A, new StatMS(ZEON, 40000, 90, 100, 90, 0x046CFD, 70)); //Z'gok E
        msStats.put(0x6424A, new StatMS(ZEON, 32000, 130, 30, 110, 0x046CDC, 200)); //RMS-108 Marasai
        msStats.put(0x6425E, new StatMS(ZEON, 25000, 200, 50, 110, 0x046D07, 200)); //Geara Doga
        msStats.put(0x6425F, new StatMS(ZEON, 25000, 200, 100, 140, 0x046D07, 200)); //Geara Doga Rezin Schnyder        
        msStats.put(0x64229, new StatMS(ZEON, 32000, 200, 100, 110, 0x046D11, 300)); //MS-14S
        msStats.put(0x64220, new StatMS(ZEON, 32000, 200, 100, 110, 0x046D11, 300)); //MS-14S Hvit
        msStats.put(0x64211, new StatMS(ZEON, 32000, 200, 100, 110, 0x046D11, 300)); //MS-14S Erik Blanke
        msStats.put(0x641F7, new StatMS(ZEON, 32000, 200, 100, 110, 0x046D11, 300)); //MS-14S grå/hvit
        msStats.put(0x641E4, new StatMS(ZEON, 32000, 200, 100, 110, 0x046D11, 300)); //MS-14S Farget
        msStats.put(0x641E8, new StatMS(ZEON, 32000, 200, 100, 110, 0x046D11, 300)); //MS-14S Farget 2        
        msStats.put(0x641FD, new StatMS(ZEON, 32000, 200, 100, 110, 0x046D11, 300)); //MS-14S Lilla/Brun        
        msStats.put(0x6422A, new StatMS(ZEON, 32000, 250, 100, 110, 0x046D11, 400)); //MS-14B
        msStats.put(0x64221, new StatMS(ZEON, 32000, 250, 100, 110, 0x046D11, 400)); //MS-14B Gato
        msStats.put(0x64212, new StatMS(ZEON, 32000, 250, 100, 110, 0x046D11, 400)); //MS-14B 78-2 farge
        msStats.put(0x641F8, new StatMS(ZEON, 32000, 250, 100, 110, 0x046D11, 400)); //MS-14B grå/rød
        msStats.put(0x641E5, new StatMS(ZEON, 32000, 250, 100, 110, 0x046D11, 400)); //MS-14B Farget
        msStats.put(0x641E9, new StatMS(ZEON, 32000, 250, 100, 110, 0x046D11, 400)); //MS-14B Farget 2
        msStats.put(0xF696B, new StatMS(ZEON, 32000, 250, 100, 110, 0x046D11, 400)); //MS-14B Johnny Ridden
        msStats.put(0x64204, new StatMS(ZEON, 32000, 250, 100, 110, 0x046D11, 400)); //MS-14B Gul
        msStats.put(0x64205, new StatMS(ZEON, 32000, 250, 100, 110, 0x046D11, 400)); //MS-14B Lilla        
        msStats.put(0x64244, new StatMS(ZEON, 25000, 130, 100, 90, 0x046D0C, 200)); //MS-08TX Efreet        
        msStats.put(0x6424D, new StatMS(ZEON, 50000, 200, 100, 110, 0x046D0C, 400)); //PMX-001 Palace Athene Zeon versjon
        msStats.put(0x6424F, new StatMS(ZEON, 40000, 200, 100, 110, 0x046D0C, 300)); //RMS-099 Rick Dias Zeon versjon
        msStats.put(0x64254, new StatMS(ZEON, 50000, 300, 90, 110, 0x046D0C, 400)); //RX-160 Byarlant Zeon versjon
        msStats.put(0x64256, new StatMS(ZEON, 32000, 200, 150, 140, 0x046D0C, 300)); //PMX-002 Bolinoak Zeon versjon
        msStats.put(0x64259, new StatMS(ZEON, 30000, 130, 30, 110, 0x046D07, 200)); //RMS-106 HiZack
        msStats.put(0x6425A, new StatMS(ZEON, 25000, 200, 30, 110, 0x046D07, 200)); //Gaza C
        msStats.put(0x64261, new StatMS(ZEON, 40000, 300, 100, 140, 0x046D0C, 400)); //Bawoo, Glemy
        msStats.put(0x64262, new StatMS(ZEON, 40000, 300, 100, 140, 0x046D0C, 400)); //Bawoo, Glemy Faction
        msStats.put(0x64263, new StatMS(ZEON, 40000, 300, 100, 140, 0x046D0C, 400)); //Bawoo, Axis MP
        msStats.put(0x668A2, new StatMS(ZEON, 30000, 40, 60, 70, 0x046D11, 0)); //Apsalus
        msStats.put(0x668A1, new StatMS(ZEON, 40000, 40, 150, 70, 0x046D11, 0)); //Adzam       

        //Hyaku Shiki anti beam coating
        getMsVehicleStat(0x6425C).setAntiBeamCoating(30);
    }

    /**
     * Setter stats for EF og Zeon vehicles, fighters og MA.
     */
    private static void setVehicleStats() {
        //Variabel for å sette faction lock.
        //Ingen vehicles har faction lock.
        int NONE = -1;

        //Sett stats for vehicles felles for EF og Zeon.
        msStats.put(0x61A80, new StatMS(NONE, 1000, 0, 0, 0, 0x046D7A, 0)); //Elecar Aaron
        msStats.put(0x61A81, new StatMS(NONE, 1000, 0, 0, 0, 0x046D7A, 0)); //Elecar Baal
        msStats.put(0x61A82, new StatMS(NONE, 1000, 0, 0, 0, 0x046D7A, 0)); //Elecar Cabot
        msStats.put(0x61A83, new StatMS(NONE, 1000, 0, 0, 0, 0x046D7A, 0)); //Elecar Dada
        msStats.put(0x61A8B, new StatMS(NONE, 1500, 0, 0, 0, 0x046D7A, 0)); //Truck Earp
        msStats.put(0x61A8A, new StatMS(NONE, 1500, 0, 0, 0, 0x046D7A, 0)); //Truck Fahrenheit
        msStats.put(0x61A8C, new StatMS(NONE, 1500, 0, 0, 0, 0x046D7A, 0)); //Truck Gaea
        msStats.put(0x61A91, new StatMS(NONE, 30000, 0, 0, 0, 0x046D15, 0)); //Thundergoliat
        msStats.put(0x61A96, new StatMS(NONE, 1500, 0, 0, 0, 0x046D31, 0)); //Habakuk
        msStats.put(0x61A97, new StatMS(NONE, 1500, 0, 0, 0, 0x046D31, 0)); //Icarus

        //Sett stats for EF vehicles.
        msStats.put(0x68FC0, new StatMS(NONE, 10000, 20, 30, 50, 0x046D2C, 0)); //Attack helicopter
        msStats.put(0x68FB9, new StatMS(NONE, 10000, 20, 30, 50, 0x046D2C, 0)); //Fanfan
        msStats.put(0x68FBC, new StatMS(NONE, 10000, 25, 30, 50, 0x046D3B, 0)); //FF-4
        msStats.put(0x68FB7, new StatMS(NONE, 10000, 25, 30, 50, 0x046D2C, 0)); //Flymanta
        msStats.put(0x68FB5, new StatMS(NONE, 12000, 35, 60, 50, 0x046D2C, 0)); //FF-6
        msStats.put(0x68FBB, new StatMS(NONE, 12000, 35, 60, 50, 0x046D3B, 0)); //Saberfish
        msStats.put(0x68FB0, new StatMS(NONE, 10000, 20, 30, 50, 0x046D31, 0)); //Public
        msStats.put(0x704E4, new StatMS(NONE, 10000, 10, 60, 50, 0x046D15, 0)); //Hovertruck
        msStats.put(0x704E0, new StatMS(NONE, 10000, 10, 30, 50, 0x046D15, 0)); //Type-61 MBT
        msStats.put(0x704E2, new StatMS(NONE, 12000, 20, 60, 50, 0x046D15, 0)); //Type-61 MBT custom

        //Sett stats for Zeon vehicles.
        msStats.put(0x68FB6, new StatMS(NONE, 12000, 35, 30, 50, 0x046D2C, 0)); //Dopp
        msStats.put(0x68FBA, new StatMS(NONE, 10000, 35, 30, 50, 0x046D2C, 0)); //Luggun
        msStats.put(0x704E1, new StatMS(NONE, 10000, 10, 30, 50, 0x046D15, 0)); //Magella Attack
        msStats.put(0x704E3, new StatMS(NONE, 12000, 20, 60, 50, 0x046D15, 0)); //Magella Attack custom
        msStats.put(0x68FB8, new StatMS(NONE, 10000, 20, 30, 50, 0x046D2C, 0)); //Zeon Attack Helicopter
        msStats.put(0x704E5, new StatMS(NONE, 18000, 40, 150, 90, 0x046D15, 0)); //Zaku tank
        msStats.put(0x68FB2, new StatMS(NONE, 10000, 30, 30, 50, 0x046D31, 0)); //Gattle
        msStats.put(0x68FB1, new StatMS(NONE, 10000, 30, 30, 50, 0x046D31, 0)); //Jicco

        //Sett stats for EF MA
        msStats.put(0x668A0, new StatMS(NONE, 20000, 40, 200, 70, 0x046CDC, 0)); //RB-79
        msStats.put(0x668A5, new StatMS(NONE, 20000, 70, 40, 70, 0x046CDC, 0)); //RB-79C
        msStats.put(0x668A4, new StatMS(NONE, 20000, 40, 60, 100, 0x046CDC, 0)); //RB-79K

        //Sett stats for Zeon MA
        msStats.put(0x668A6, new StatMS(NONE, 20000, 40, 200, 70, 0x046CDC, 0)); //Oggo

    }

    /**
     * Setter stats for taxi og transport.
     */
    private static void setTaxiTransportStats() {

        int NONE = -1; //Ingen faction lock her.

        msStats.put(0x61A90, new StatMS(NONE, 1, 0, 0, 0, 0x46CD8, 0)); //Taxi
        msStats.put(0x61A85, new StatMS(NONE, 80000, 0, 0, 0, 0x46DB7, 0)); //Midea
        msStats.put(0x61A95, new StatMS(NONE, 1, 0, 0, 0, 0x46D31, 0)); //Space taxi
        msStats.put(0x61A98, new StatMS(NONE, 80000, 0, 0, 0, 0x46DB8, 0)); //Space transport EF
        msStats.put(0x61A89, new StatMS(NONE, 80000, 0, 0, 0, 0x46DB7, 0)); //Fat uncle
        msStats.put(0x61A99, new StatMS(NONE, 80000, 0, 0, 0, 0x46DB8, 0)); //Space transport Zeon
        msStats.put(0x61A93, new StatMS(NONE, 1000000, 0, 0, 0, 0x46DB7, 0)); //Space shuttle EF
        msStats.put(0x61A94, new StatMS(NONE, 1000000, 0, 0, 0, 0x46DB7, 0)); //Space shuttle Zeon

    }

    /**
     * Setter stats for alle våpen og shields.
     */
    private static void setWeaponStats() {

        //Battleship våpen.
        weaponStats.put(0x445D1, new StatWeapon(1000, 40, 1700, 1000, 200, 4)); //BB MPBG
        //weaponStats.put(0x445E4, new StatWeapon(1000,60,1400,1000,500,8)); //BB MG

        //attack,accuracy,range,hitpoints,ammo_clip,ammo_use
        //Stats for EF våpen. Etter reboot 2019.
        weaponStats.put(0x4465D, new StatWeapon(300, 50, 400, 1000, 200, 4)); //Chest vulcan
        weaponStats.put(0x4468F, new StatWeapon(300, 50, 500, 1000, 200, 4)); //Chest vulcan EX
        weaponStats.put(0x44642, new StatWeapon(300, 100, 400, 200, 200, 4)); //Chest vulcan HG
        weaponStats.put(0x44665, new StatWeapon(500, 50, 450, 1000, 5, 1)); //EF throwing device
        weaponStats.put(0x44697, new StatWeapon(500, 50, 450, 500, 10, 1)); //EF throwing device EX 
        weaponStats.put(0x445D0, new StatWeapon(1000, 50, 500, 1000, 120, 4)); //Gun launcher RX-75
        weaponStats.put(0x44602, new StatWeapon(1000, 50, 600, 1000, 120, 2)); //Gun launcher RX-75 EX
        weaponStats.put(0x44626, new StatWeapon(1000, 150, 600, 100, 80, 4)); //Gun launcher RX-75 HG        
        weaponStats.put(0x44656, new StatWeapon(850, 70, 500, 1000, 100, 4)); //Beam spray gun
        weaponStats.put(0x44688, new StatWeapon(850, 70, 500, 1000, 100, 2)); //Beam spray gun EX
        weaponStats.put(0x445EE, new StatWeapon(400, 100, 800, 1000, 30, 1)); //GM rifle
        weaponStats.put(0x44620, new StatWeapon(400, 100, 800, 1000, 500, 1)); //GM rifle EX
        weaponStats.put(0x445CF, new StatWeapon(900, 70, 1450, 1000, 30, 1)); //Long rifle        
        weaponStats.put(0x44601, new StatWeapon(900, 70, 1450, 1000, 500, 1)); //Long rifle EX
        weaponStats.put(0x446BE, new StatWeapon(900, 100, 1450, 400, 30, 1)); //Long rifle SP
        weaponStats.put(0x445E7, new StatWeapon(900, 90, 70, 100, 0, 0)); //RGM-79G beam saber
        weaponStats.put(0x44619, new StatWeapon(900, 90, 70, 300, 0, 0)); //RGM-79G beam saber EX
        weaponStats.put(0x44639, new StatWeapon(1500, 90, 70, 20, 0, 0)); //RGM-79G beam saber HG        
        weaponStats.put(0x44658, new StatWeapon(800, 120, 70, 100, 0, 0)); //RGM-79C beam saber
        weaponStats.put(0x4468A, new StatWeapon(800, 120, 70, 300, 0, 0)); //RGM-79C beam saber EX
        weaponStats.put(0x445DB, new StatWeapon(1600, 100, 1500, 200, 180, 12)); //Longrange beam rifle
        weaponStats.put(0x4460D, new StatWeapon(1600, 100, 1700, 200, 180, 6)); //Longrange beam rifle EX
        weaponStats.put(0x44634, new StatWeapon(1600, 200, 2000, 50, 216, 18)); //Longrange beam rifle HG
        weaponStats.put(0x445ED, new StatWeapon(1300, 70, 800, 1000, 20, 1)); //Hyper bazooka
        weaponStats.put(0x4461F, new StatWeapon(1300, 70, 1001, 500, 40, 1)); //Hyper bazooka EX              
        weaponStats.put(0x445C1, new StatWeapon(1300, 70, 840, 1000, 20, 1)); //Hyper bazooka ground type
        weaponStats.put(0x445F3, new StatWeapon(1300, 70, 924, 1000, 40, 1)); //Hyper bazooka ground type EX
        weaponStats.put(0x445C5, new StatWeapon(800, 70, 700, 1000, 180, 6)); //HFW-GMG
        weaponStats.put(0x445F7, new StatWeapon(800, 70, 700, 1000, 180, 3)); //HFW-GMG EX
        weaponStats.put(0x4462B, new StatWeapon(800, 140, 700, 200, 180, 6)); //HFW-GMG HG                
        weaponStats.put(0x4466F, new StatWeapon(450, 100, 770, 1000, 150, 5)); //NFHI GMG
        weaponStats.put(0x446A1, new StatWeapon(450, 100, 900, 1000, 150, 5)); //NFHI GMG EX                                     
        weaponStats.put(0x445C7, new StatWeapon(800, 70, 770, 1000, 150, 6)); //RGM-79 beam gun
        weaponStats.put(0x445F9, new StatWeapon(800, 70, 900, 1000, 300, 6)); //RGM-79 beam gun EX
        weaponStats.put(0x4462D, new StatWeapon(800, 140, 847, 200, 175, 7)); //RGM-79 beam gun HG
        weaponStats.put(0x445DD, new StatWeapon(1700, 120, 1700, 200, 210, 14)); //Sniper beam rifle        
        weaponStats.put(0x4460F, new StatWeapon(1700, 120, 1900, 200, 210, 10)); //Sniper beam rifle EX
        weaponStats.put(0x4465F, new StatWeapon(1300, 100, 1400, 1000, 320, 8)); //MS beam cannon RX-77-4        
        weaponStats.put(0x44691, new StatWeapon(1300, 100, 1450, 1000, 240, 6)); //MS beam cannon EX
        weaponStats.put(0x44644, new StatWeapon(1300, 200, 1250, 100, 320, 8)); //MS beam cannon HG        
        weaponStats.put(0x445C2, new StatWeapon(1000, 120, 70, 100, 0, 0)); //RX-78 beam saber
        weaponStats.put(0x445F4, new StatWeapon(1000, 120, 80, 300, 0, 0)); //RX-78 beam saber EX                        
        weaponStats.put(0x445EC, new StatWeapon(1100, 100, 70, 100, 0, 0)); //RX-79G beam saber
        weaponStats.put(0x4461E, new StatWeapon(1100, 100, 70, 300, 0, 0)); //RX-79G beam saber EX
        weaponStats.put(0x445E0, new StatWeapon(700, 90, 600, 1000, 175, 7)); //RGM-79L beam rifle
        weaponStats.put(0x44612, new StatWeapon(700, 90, 715, 1000, 175, 5)); //RGM-79L beam rifle EX
        weaponStats.put(0x44636, new StatWeapon(700, 150, 715, 120, 230, 10)); //RGM-79L beam rifle HG        

        weaponStats.put(0x445D2, new StatWeapon(550, 50, 700, 1000, 150, 6)); //100mm MG
        weaponStats.put(0x4465E, new StatWeapon(1300, 90, 1000, 1000, 25, 1)); //Ball cannon                

        weaponStats.put(0x445D5, new StatWeapon(1300, 70, 1100, 1000, 30, 1)); //180mm cannon        
        weaponStats.put(0x445D6, new StatWeapon(1000, 100, 700, 1000, 240, 8)); //RX-79G beam rifle
        weaponStats.put(0x445D9, new StatWeapon(1800, 70, 700, 1000, 200, 10)); //RX-78 prototype beam rifle
        weaponStats.put(0x445C8, new StatWeapon(1200, 100, 700, 1000, 160, 8)); //RX-78-2 Beam Rifle
        weaponStats.put(0x445C9, new StatWeapon(1200, 90, 1100, 1000, 210, 7)); //RX-77 beam rifle
        weaponStats.put(0x445DE, new StatWeapon(1800, 120, 1800, 200, 240, 16)); //Sniper beam rifle custom

        weaponStats.put(0x446FB, new StatWeapon(2000, 100, 1500, 300, 200, 20)); //MSZ-006 HML
        weaponStats.put(0x446FA, new StatWeapon(1400, 100, 800, 350, 300, 10)); //MSZ-006 Beam Rifle
        weaponStats.put(0x446F7, new StatWeapon(1400, 100, 900, 350, 300, 10)); //MSN-00100 Beam Rifle
        weaponStats.put(0x446F6, new StatWeapon(2000, 120, 1500, 300, 500, 20)); //RX-178 Super Gundam Long Rifle
        weaponStats.put(0x446F5, new StatWeapon(1200, 90, 700, 350, 300, 10)); //RGM-89 Jegan Beam Rifle
        weaponStats.put(0x446F4, new StatWeapon(1200, 90, 70, 100, 0, 0)); //RX-178 Beam Saber        
        weaponStats.put(0x446F1, new StatWeapon(1400, 90, 750, 350, 300, 10)); //RX-178 Beam Rifle TITANS
        weaponStats.put(0x446F0, new StatWeapon(1400, 90, 750, 350, 300, 10)); //RX-178 Beam Rifle AEUG
        weaponStats.put(0x446ED, new StatWeapon(1200, 90, 700, 350, 200, 8)); //RX-78GP01 Beam Rifle
        weaponStats.put(0x446EE, new StatWeapon(1600, 40, 1500, 250, 200, 20)); //RX-78GP01 Blash Experimental
        weaponStats.put(0x446EF, new StatWeapon(2000, 90, 1700, 200, 200, 25)); //RX-78GP02 Beam Bazooka        
        weaponStats.put(0x446BA, new StatWeapon(1000, 80, 770, 250, 160, 8)); //Blash SP
        weaponStats.put(0x445FA, new StatWeapon(1100, 80, 770, 350, 160, 8)); //Blash EX
        weaponStats.put(0x44625, new StatWeapon(1600, 70, 700, 80, 160, 20)); //Blash HG
        weaponStats.put(0x446CA, new StatWeapon(1000, 70, 700, 350, 160, 8)); //Blash Hvit
        weaponStats.put(0x446CB, new StatWeapon(1000, 80, 870, 350, 160, 10)); //Alex Gundam Beam Rifle
        weaponStats.put(0x446CC, new StatWeapon(1000, 70, 700, 350, 160, 8)); //Blash Blå
        weaponStats.put(0x446CD, new StatWeapon(1100, 80, 770, 350, 160, 8)); //Blash EX Hvit
        weaponStats.put(0x446CE, new StatWeapon(800, 90, 1050, 350, 12, 1)); //6-Tube Missile Launcher
        weaponStats.put(0x446CF, new StatWeapon(1100, 80, 770, 350, 160, 8)); //Blash EX Blå        
        weaponStats.put(0x44608, new StatWeapon(900, 90, 792, 350, 168, 8)); //RX-79G beam rifle EX
        weaponStats.put(0x446E2, new StatWeapon(800, 90, 720, 350, 168, 8)); //RX-79G beam rifle Farget
        weaponStats.put(0x446E3, new StatWeapon(900, 90, 792, 350, 168, 8)); //RX-79G beam rifle EX Farget
        weaponStats.put(0x446EA, new StatWeapon(800, 70, 1000, 350, 168, 8)); //RMS-179 Beam Rifle        
        weaponStats.put(0x4460B, new StatWeapon(1100, 70, 770, 350, 250, 10)); //RX-78 prototype beam rifle EX        

        weaponStats.put(0x446E4, new StatWeapon(1000, 50, 650, 350, 175, 7)); //RGM-79L beam rifle Farget
        weaponStats.put(0x446E5, new StatWeapon(1000, 60, 715, 350, 175, 7)); //RGM-79L beam rifle EX Farget

        weaponStats.put(0x445FB, new StatWeapon(700, 80, 1155, 350, 176, 8)); //RX-77 beam rifle EX                               
        weaponStats.put(0x445C0, new StatWeapon(400, 70, 700, 500, 100, 4)); //A-E-Br.G-Sc-L
        weaponStats.put(0x445F2, new StatWeapon(450, 70, 770, 500, 100, 4)); //A-E-Br.G-Sc-L EX        
        weaponStats.put(0x446BC, new StatWeapon(1000, 120, 1700, 150, 180, 12)); //Longrange beam rifle SP        
        weaponStats.put(0x446E6, new StatWeapon(1000, 120, 1500, 200, 180, 12)); //Longrange beam rifle Farget
        weaponStats.put(0x446E7, new StatWeapon(1000, 130, 1700, 200, 180, 12)); //Longrange beam rifle EX Farget        

        weaponStats.put(0x44610, new StatWeapon(700, 160, 2000, 200, 240, 16)); //Sniper beam rifle custom EX        
        weaponStats.put(0x44604, new StatWeapon(800, 50, 770, 1000, 150, 6)); //100mm MG EX
        weaponStats.put(0x4462F, new StatWeapon(1000, 70, 770, 100, 150, 6)); //100mm MG HG        
        weaponStats.put(0x446BF, new StatWeapon(500, 110, 770, 400, 180, 6)); //HFW-GMG SP

        weaponStats.put(0x446BD, new StatWeapon(900, 70, 1001, 300, 18, 1)); //Hyper bazooka SP        
        weaponStats.put(0x446C1, new StatWeapon(900, 70, 924, 400, 20, 1)); //Hyper bazooka ground SP        
        weaponStats.put(0x44629, new StatWeapon(1000, 100, 924, 100, 20, 1)); //Hyper bazooka ground HG        
        weaponStats.put(0x446BB, new StatWeapon(1000, 90, 1078, 200, 30, 1)); //180mm cannon SP
        weaponStats.put(0x44607, new StatWeapon(1100, 100, 1078, 400, 30, 1)); //180mm cannon EX
        weaponStats.put(0x44632, new StatWeapon(1300, 100, 1078, 40, 30, 1)); //180mm cannon HG
        weaponStats.put(0x446D8, new StatWeapon(1000, 90, 980, 400, 30, 1)); //180mm cannon Mørkgrønn
        weaponStats.put(0x446D9, new StatWeapon(1000, 90, 980, 400, 30, 1)); //180mm cannon Lysgrå
        weaponStats.put(0x446DA, new StatWeapon(1100, 100, 1078, 400, 30, 1)); //180mm cannon EX Mørkgrønn
        weaponStats.put(0x446Db, new StatWeapon(1100, 100, 1078, 400, 30, 1)); //180mm cannon EX Lysgrå        
        weaponStats.put(0x446C0, new StatWeapon(1000, 120, 1000, 200, 25, 1)); //Ball cannon SP
        weaponStats.put(0x44690, new StatWeapon(1100, 90, 1000, 400, 25, 1)); //Ball cannon EX
        weaponStats.put(0x44643, new StatWeapon(1300, 90, 1000, 30, 30, 1)); //Ball cannon HG        

        //Sett stats for Zeon våpen. 
        weaponStats.put(0x445C4, new StatWeapon(650, 70, 680, 1000, 90, 3)); //ZMP-47D
        weaponStats.put(0x445F6, new StatWeapon(650, 70, 750, 2000, 90, 2)); //ZMP-47D EX
        weaponStats.put(0x4462A, new StatWeapon(650, 200, 748, 200, 90, 3)); //ZMP-47D HG
        weaponStats.put(0x445F0, new StatWeapon(700, 90, 500, 1000, 100, 5)); //75mm MG
        weaponStats.put(0x44622, new StatWeapon(700, 90, 550, 1000, 500, 5)); //75mm MG EX
        weaponStats.put(0x445CA, new StatWeapon(700, 50, 700, 1000, 150, 5)); //ZMP-50D
        weaponStats.put(0x445FC, new StatWeapon(700, 50, 847, 2000, 150, 3)); //ZMP-50D EX                
        weaponStats.put(0x446C2, new StatWeapon(700, 110, 847, 500, 150, 5)); //ZMP-50D SP
        weaponStats.put(0x445E5, new StatWeapon(900, 90, 70, 100, 0, 0)); //Heat hawk
        weaponStats.put(0x44617, new StatWeapon(900, 90, 70, 300, 0, 0)); //Heat hawk EX
        weaponStats.put(0x446C5, new StatWeapon(1000, 90, 70, 40, 0, 0)); //Heat hawk SP        
        weaponStats.put(0x44627, new StatWeapon(1300, 90, 70, 20, 0, 0)); //Heat hawk HG                        
        weaponStats.put(0x44666, new StatWeapon(500, 50, 450, 1000, 5, 1)); //Zeon throwing device
        weaponStats.put(0x44698, new StatWeapon(500, 50, 450, 500, 10, 1)); //Zeon throwing device EX
        weaponStats.put(0x445EF, new StatWeapon(1000, 90, 100, 100, 0, 0)); //Heat rod
        weaponStats.put(0x44621, new StatWeapon(1000, 90, 100, 300, 0, 0)); //Heat rod EX
        weaponStats.put(0x4463F, new StatWeapon(1200, 90, 100, 40, 0, 0)); //Heat rod HG        
        weaponStats.put(0x44661, new StatWeapon(800, 70, 770, 1000, 150, 6)); //MS-06R Zaku beam rifle
        weaponStats.put(0x44693, new StatWeapon(800, 70, 850, 1000, 160, 4)); //MS-06R Zaku beam rifle EX
        weaponStats.put(0x446C9, new StatWeapon(800, 90, 770, 500, 150, 6)); //MS-06R Zaku beam rifle SP        
        weaponStats.put(0x445E9, new StatWeapon(1000, 70, 800, 1000, 25, 1)); //Zaku bazooka
        weaponStats.put(0x4461B, new StatWeapon(1000, 70, 1000, 1000, 25, 1)); //Zaku bazooka EX
        weaponStats.put(0x446C7, new StatWeapon(1000, 90, 1100, 300, 25, 1)); //Zaku bazooka SP        
        weaponStats.put(0x4463A, new StatWeapon(1000, 200, 924, 100, 25, 1)); //Zaku bazooka HG        
        weaponStats.put(0x445E6, new StatWeapon(800, 120, 80, 100, 0, 0)); //MS-09 Heat saber
        weaponStats.put(0x44618, new StatWeapon(800, 120, 80, 300, 0, 0)); //MS-09 Heat saber EX
        weaponStats.put(0x44638, new StatWeapon(1200, 90, 70, 20, 0, 0)); //MS-09 Heat saber HG                        
        weaponStats.put(0x445F1, new StatWeapon(500, 90, 900, 1000, 155, 5)); //M-120AS (Tidligere ZMP-50D custom)
        weaponStats.put(0x44623, new StatWeapon(500, 90, 950, 2000, 200, 4)); //M-120AS EX                
        weaponStats.put(0x44655, new StatWeapon(1000, 70, 700, 1000, 40, 2)); //MS-14B Rocket Launcher
        weaponStats.put(0x44654, new StatWeapon(1250, 90, 1100, 1000, 20, 1)); //Giant bazooka II        
        weaponStats.put(0x44660, new StatWeapon(1000, 90, 700, 100, 0, 0)); //Hammer gun
        weaponStats.put(0x44692, new StatWeapon(1000, 90, 770, 200, 0, 0)); //Hammer gun EX
        weaponStats.put(0x44645, new StatWeapon(1000, 200, 770, 30, 0, 0)); //Hammer gun HG        
        weaponStats.put(0x445EA, new StatWeapon(1000, 100, 70, 100, 0, 0)); //Heat sword
        weaponStats.put(0x4461C, new StatWeapon(1000, 100, 70, 300, 0, 0)); //Heat sword EX
        weaponStats.put(0x4463B, new StatWeapon(1300, 100, 70, 20, 0, 0)); //Heat sword HG        
        weaponStats.put(0x445D8, new StatWeapon(1000, 100, 90, 100, 0, 0)); //MS claw
        weaponStats.put(0x4460A, new StatWeapon(1000, 120, 90, 300, 0, 0)); //MS claw EX
        weaponStats.put(0x44633, new StatWeapon(1500, 100, 90, 20, 0, 0)); //MS claw HG
        weaponStats.put(0x44657, new StatWeapon(600, 50, 750, 1000, 162, 6)); //MMP-78
        weaponStats.put(0x44689, new StatWeapon(600, 50, 825, 1000, 162, 3)); //MMP-78 EX
        weaponStats.put(0x44640, new StatWeapon(600, 100, 770, 100, 180, 6)); //MMP-78 HG
        weaponStats.put(0x445DC, new StatWeapon(1000, 90, 150, 100, 0, 0)); //MSM-08 Arm punch        
        weaponStats.put(0x4460E, new StatWeapon(1000, 90, 200, 200, 0, 0)); //MSM-08 Arm punch EX
        weaponStats.put(0x4463E, new StatWeapon(1300, 90, 70, 30, 0, 0)); //MSM-08 Arm punch HG                
        weaponStats.put(0x4465C, new StatWeapon(900, 70, 1200, 1000, 30, 2)); //MS torpedo
        weaponStats.put(0x4468E, new StatWeapon(900, 70, 1300, 1000, 30, 1)); //MS torpedo EX
        weaponStats.put(0x44641, new StatWeapon(900, 150, 900, 100, 30, 2)); //MS torpedo HG
        weaponStats.put(0x445DF, new StatWeapon(700, 70, 600, 1000, 20, 2)); //MSM-08 Boomerang cutter
        weaponStats.put(0x44611, new StatWeapon(700, 70, 650, 1000, 50, 1)); //MSM-08 Boomerang cutter EX
        weaponStats.put(0x44635, new StatWeapon(700, 120, 440, 30, 30, 2)); //MSM-08 Boomerang cutter HG                
        weaponStats.put(0x445CB, new StatWeapon(450, 70, 700, 1000, 175, 7)); //MMP-80
        weaponStats.put(0x445FD, new StatWeapon(450, 70, 770, 1000, 350, 7)); //MMP-80 EX
        weaponStats.put(0x446C6, new StatWeapon(450, 90, 770, 1000, 210, 7)); //MMP-80 SP
        weaponStats.put(0x445D7, new StatWeapon(1100, 90, 1000, 1000, 20, 1)); //Giant bazooka
        weaponStats.put(0x44609, new StatWeapon(1100, 90, 1200, 1000, 20, 1)); //Giant bazooka EX
        weaponStats.put(0x446C4, new StatWeapon(1100, 120, 1001, 200, 20, 1)); //Giant bazooka SP        
        weaponStats.put(0x44646, new StatWeapon(1100, 160, 924, 100, 25, 1)); //Giant bazooka HG
        weaponStats.put(0x445D4, new StatWeapon(1100, 70, 1100, 1000, 250, 10)); //MS bias mega particle beam gun        
        weaponStats.put(0x44606, new StatWeapon(1100, 70, 1075, 1000, 250, 5)); //MS bias mega particle beam gun EX
        weaponStats.put(0x44631, new StatWeapon(1100, 150, 924, 200, 275, 11)); //MS bias mega particle beam gun HG        

        weaponStats.put(0x445C6, new StatWeapon(1250, 70, 1300, 1000, 25, 1)); //Magella top cannon
        weaponStats.put(0x445E8, new StatWeapon(1100, 120, 70, 100, 0, 0)); //MS-14 Beam naginata
        weaponStats.put(0x445E3, new StatWeapon(1100, 90, 700, 1000, 160, 8)); //MS-14 beam rifle
        weaponStats.put(0x446E8, new StatWeapon(1700, 90, 600, 1000, 20, 1)); //Raketen bazooka
        weaponStats.put(0x44662, new StatWeapon(1200, 90, 1500, 1000, 225, 15)); //Beam bazooka
        weaponStats.put(0x4465A, new StatWeapon(1300, 100, 900, 1000, 162, 9)); //MS-14 prototype rifle

        weaponStats.put(0x446FC, new StatWeapon(1400, 100, 1200, 350, 450, 15)); //AMX-107 Bawoo Beam Rifle
        weaponStats.put(0x446F9, new StatWeapon(800, 70, 700, 350, 300, 5)); //Geara Doga beam MG #2
        weaponStats.put(0x446F8, new StatWeapon(800, 70, 700, 350, 300, 5)); //Geara Doga beam MG        
        weaponStats.put(0x44615, new StatWeapon(1100, 80, 770, 350, 160, 8)); //MS-14 beam rifle EX
        weaponStats.put(0x4463D, new StatWeapon(1600, 70, 700, 80, 160, 20)); //MS-14 beam rifle HG
        weaponStats.put(0x446D0, new StatWeapon(1000, 70, 700, 350, 160, 8)); //MS-14 beam rifle Hvit
        weaponStats.put(0x446D1, new StatWeapon(1000, 70, 700, 350, 160, 8)); //MS-14 beam rifle Blå
        weaponStats.put(0x446D2, new StatWeapon(1000, 70, 700, 350, 160, 8)); //MS-14 beam rifle Rød
        weaponStats.put(0x446D3, new StatWeapon(1100, 80, 770, 350, 160, 8)); //MS-14 beam rifle EX Hvit
        weaponStats.put(0x446D4, new StatWeapon(1100, 80, 770, 350, 160, 8)); //MS-14 beam rifle EX Blå
        weaponStats.put(0x446D5, new StatWeapon(1100, 80, 770, 350, 160, 8)); //MS-14 beam rifle EX Rød        
        weaponStats.put(0x446C3, new StatWeapon(900, 90, 950, 400, 162, 9)); //MS-14 prototype rifle SP
        weaponStats.put(0x4468C, new StatWeapon(900, 80, 1000, 350, 162, 9)); //MS-14 prototype rifle EX        
        weaponStats.put(0x44694, new StatWeapon(1100, 100, 1500, 350, 225, 15)); //Beam bazooka EX               

        weaponStats.put(0x446E9, new StatWeapon(1250, 90, 700, 250, 20, 1)); //Raketen bazooka EX        
        weaponStats.put(0x446D6, new StatWeapon(800, 70, 910, 250, 20, 1)); //Giant bazooka Hvit
        weaponStats.put(0x446D7, new StatWeapon(900, 70, 1001, 250, 20, 1)); //Giant bazooka EX Hvit                           

        weaponStats.put(0x446DC, new StatWeapon(800, 50, 700, 1000, 175, 7)); //MMP-80 Hvit
        weaponStats.put(0x446DD, new StatWeapon(800, 50, 700, 1000, 175, 7)); //MMP-80 Rød
        weaponStats.put(0x446DE, new StatWeapon(800, 50, 700, 1000, 175, 7)); //MMP-80 Blå
        weaponStats.put(0x446DF, new StatWeapon(800, 70, 770, 1000, 210, 7)); //MMP-80 EX Hvit
        weaponStats.put(0x446E0, new StatWeapon(800, 70, 770, 1000, 210, 7)); //MMP-80 EX Rød
        weaponStats.put(0x446E1, new StatWeapon(800, 70, 770, 1000, 210, 7)); //MMP-80 EX Blå        

        weaponStats.put(0x445EB, new StatWeapon(400, 90, 500, 1000, 100, 4)); //MS arm MG
        weaponStats.put(0x4461D, new StatWeapon(450, 90, 600, 1000, 100, 4)); //MS arm MG EX
        weaponStats.put(0x4463C, new StatWeapon(500, 90, 600, 400, 100, 4)); //MS arm MG HG        
        weaponStats.put(0x446C8, new StatWeapon(800, 110, 1400, 400, 25, 1)); //Magella top cannon SP
        weaponStats.put(0x445F8, new StatWeapon(1100, 90, 1250, 400, 25, 1)); //Magella top cannon EX
        weaponStats.put(0x4462C, new StatWeapon(1200, 90, 1155, 30, 25, 1)); //Magella top cannon HG                     

        weaponStats.put(0x4461A, new StatWeapon(1100, 120, 70, 80, 0, 0)); //Beam naginata EX                

        //Sett stats for våpen felles for Zeon og EF.
        //attack,accuracy,range,hitpoints,ammo_clip,ammo_use
        weaponStats.put(0x445D3, new StatWeapon(1000, 90, 700, 1000, 160, 8)); //MS mega particle beam gun
        weaponStats.put(0x44605, new StatWeapon(1000, 80, 770, 350, 176, 8)); //MS mega particle beam gun EX
        weaponStats.put(0x44630, new StatWeapon(1400, 80, 770, 50, 200, 10)); //MS mega particle beam gun HG    
        weaponStats.put(0x445E1, new StatWeapon(1100, 70, 800, 1000, 60, 3)); //MS rocket launcher
        weaponStats.put(0x44613, new StatWeapon(675, 70, 924, 500, 60, 3)); //MS rocket launcher EX
        weaponStats.put(0x44637, new StatWeapon(800, 70, 924, 80, 60, 3)); //MS rocket launcher HG        
        weaponStats.put(0x445CC, new StatWeapon(800, 70, 1300, 1000, 40, 1)); //MS cannon
        weaponStats.put(0x445FE, new StatWeapon(800, 70, 1400, 500, 40, 1)); //MS cannon EX
        weaponStats.put(0x44624, new StatWeapon(800, 200, 1000, 100, 10, 1)); //MS cannon HG        
        weaponStats.put(0x445C3, new StatWeapon(300, 50, 400, 1000, 200, 4)); //MS head vulcan
        weaponStats.put(0x445F5, new StatWeapon(300, 50, 500, 1000, 200, 4)); //MS head vulcan EX
        weaponStats.put(0x44628, new StatWeapon(300, 100, 500, 400, 200, 4)); //MS head vulcan HG        
        weaponStats.put(0x445E2, new StatWeapon(600, 90, 70, 100, 0, 0)); //MS grapple
        weaponStats.put(0x44614, new StatWeapon(600, 90, 90, 100, 0, 0)); //MS grapple EX
        weaponStats.put(0x445CE, new StatWeapon(300, 50, 500, 500, 240, 4)); //Tank/Fighter MG
        weaponStats.put(0x44600, new StatWeapon(300, 50, 550, 500, 240, 4)); //Tank/Fighter MG EX
        weaponStats.put(0x4462E, new StatWeapon(450, 50, 550, 150, 240, 4)); //Tank/Fighter MG HG
        weaponStats.put(0x44664, new StatWeapon(600, 50, 600, 500, 40, 1)); //Tank/Fighter Missile
        weaponStats.put(0x44696, new StatWeapon(600, 50, 650, 500, 40, 1)); //Tank/Fighter Missile EX
        weaponStats.put(0x445CD, new StatWeapon(800, 90, 1200, 500, 40, 1)); //Tank/Fighter cannon
        weaponStats.put(0x445FF, new StatWeapon(800, 90, 1400, 500, 40, 1)); //Tank/Fighter cannon EX
        weaponStats.put(0x44653, new StatWeapon(0, 0, 0, 20, 0, 0)); //Mining drill

        //Sett stats for shields.
        weaponStats.put(0x57E46, new StatWeapon(3000, 70)); //MS-14 Shield
        weaponStats.put(0x57E5A, new StatWeapon(4000, 100)); //MS-14 Shield EX
        weaponStats.put(0x57E6E, new StatWeapon(8000, 120)); //MS-14 Shield HG        
        weaponStats.put(0x57E45, new StatWeapon(3000, 70)); //MS-07 shield
        weaponStats.put(0x57E59, new StatWeapon(4000, 100)); //MS-07 shield EX
        weaponStats.put(0x57E6D, new StatWeapon(8000, 120)); //MS-07 shield HG        
        weaponStats.put(0x57E43, new StatWeapon(3000, 70)); //RX-79G shield
        weaponStats.put(0x57E57, new StatWeapon(4000, 100)); //RX-79G shield EX        
        weaponStats.put(0x57E44, new StatWeapon(3000, 70)); //Ez8 shield
        weaponStats.put(0x57E58, new StatWeapon(4000, 100)); //Ez8 shield EX
        weaponStats.put(0x57E42, new StatWeapon(3000, 70)); //RGM-79C shield
        weaponStats.put(0x57E56, new StatWeapon(4000, 100)); //RGM-79C shield EX
        weaponStats.put(0x57E41, new StatWeapon(3000, 70)); //RGM-79G shield
        weaponStats.put(0x57E55, new StatWeapon(4000, 100)); //RGM-79G shield EX
        weaponStats.put(0x57E69, new StatWeapon(8000, 120)); //RGM-79G shield HG        
        weaponStats.put(0x57E40, new StatWeapon(3000, 70)); //RX-78 shield
        weaponStats.put(0x57E54, new StatWeapon(4000, 100)); //RX-78 shield EX

        //Sett stats for MR kits.
        weaponStats.put(0x44667, new StatWeapon(0, 0, 70, 4, 0, 0)); //MS/MA MR L1
        weaponStats.put(0x44668, new StatWeapon(0, 0, 70, 3, 0, 0)); //MS/MA MR L2
        weaponStats.put(0x44669, new StatWeapon(0, 0, 70, 2, 0, 0)); //MS/MA MR L3
        weaponStats.put(0x4466B, new StatWeapon(0, 0, 70, 4, 0, 0)); //T/F MR L1
        weaponStats.put(0x4466C, new StatWeapon(0, 0, 70, 3, 0, 0)); //T/F MR L2
        weaponStats.put(0x4466D, new StatWeapon(0, 0, 70, 2, 0, 0)); //T/F MR L3

        //Sett stats for spesielle våpen.
        weaponStats.put(0x44659, new StatWeapon(1400, 70, 70, 50, 0, 0)); //Giant Heat Hawk 
        weaponStats.put(0x4465B, new StatWeapon(1600, 70, 70, 50, 0, 0)); //Great Heat Hawk

        //GAMLE STATS FRA FØR REBOOT 2019
        weaponStats.put(0x446F3, new StatWeapon(1000, 90, 1300, 300, 20, 1)); //RX-178 Hyper Bazooka
        weaponStats.put(0x446F2, new StatWeapon(1400, 70, 70, 50, 0, 0)); //RX-160 Byarlant Beam Saber        
        weaponStats.put(0x446EC, new StatWeapon(2000, 70, 700, 200, 200, 20)); //PMX-001 Palace Athene Dual Beam Gun
        weaponStats.put(0x446EB, new StatWeapon(800, 70, 1000, 350, 168, 8)); //RMS-108 Marasai Beam Rifle        

        weaponStats.put(0x57E6F, new StatWeapon(3000, 100)); //MS-14 Shield Johnny Ridden
        weaponStats.put(0x57E70, new StatWeapon(4000, 100)); //MS-14 Shield EX Johnny Ridden
        weaponStats.put(0x57E71, new StatWeapon(3000, 100)); //MS-14 Shield
        weaponStats.put(0x57E72, new StatWeapon(4000, 100)); //MS-14 Shield EX 
        weaponStats.put(0x57E73, new StatWeapon(3000, 100)); //MS-14 Shield
        weaponStats.put(0x57E74, new StatWeapon(4000, 100)); //MS-14 Shield EX
        weaponStats.put(0x57E79, new StatWeapon(3000, 80)); //RX-78NT-1 shield
        weaponStats.put(0x57E7A, new StatWeapon(3000, 80)); //MSA-003 Nemo shield
        weaponStats.put(0x57E7B, new StatWeapon(9000, 100)); //PMX-001 Palace Athene shield
        weaponStats.put(0x57E7C, new StatWeapon(6000, 100)); //RX-78GP01 Shield
        weaponStats.put(0x57E7D, new StatWeapon(18000, 100)); //RX-78GP02 Shield
        weaponStats.put(0x57E7E, new StatWeapon(9000, 100)); //RX-178 Shield AEUG
        weaponStats.put(0x57E7F, new StatWeapon(9000, 100)); //RX-178 Shield TITANS
        weaponStats.put(0x57E80, new StatWeapon(9000, 100)); //PMX-002 Shield
        weaponStats.put(0x57E81, new StatWeapon(6000, 100)); //RGM-89 Jegan Shield
        weaponStats.put(0x57E82, new StatWeapon(6000, 100)); //Geara Doga Shield
        weaponStats.put(0x57E83, new StatWeapon(6000, 100)); //Geara Doga Shield, Rezin Schnyder
        weaponStats.put(0x57E84, new StatWeapon(12000, 100)); //MSZ-006 Shield
        weaponStats.put(0x57E85, new StatWeapon(9000, 100)); //AMX-107 Shield
        weaponStats.put(0x57E75, new StatWeapon(3000, 60)); //RX-78 shield blå
        weaponStats.put(0x57E76, new StatWeapon(3000, 80)); //RX-79G shield
        weaponStats.put(0x57E77, new StatWeapon(3000, 80)); //RX-79G shield
        weaponStats.put(0x57E78, new StatWeapon(3000, 80)); //RX-79G shield
    }

    /**
     * Setter stats for engines. Dette gjelder kun engines som gir bonus til MS.
     */
    private static void setEngineStats() {
        //TR Engines
        engineStats.put(0x46CD0, new StatEngine(1));
        engineStats.put(0x46CDB, new StatEngine(1));
        engineStats.put(0x46CDC, new StatEngine(1));
        engineStats.put(0x46CDD, new StatEngine(1));
        engineStats.put(0x46CDE, new StatEngine(1));
        engineStats.put(0x46CD2, new StatEngine(2));
        engineStats.put(0x46CDF, new StatEngine(2));
        engineStats.put(0x46CE0, new StatEngine(2));
        engineStats.put(0x46CE1, new StatEngine(2));
        engineStats.put(0x46CE2, new StatEngine(2));
        engineStats.put(0x46CD3, new StatEngine(3));
        engineStats.put(0x46CE3, new StatEngine(3));
        engineStats.put(0x46CE4, new StatEngine(3));
        engineStats.put(0x46CE5, new StatEngine(3));
        engineStats.put(0x46CE6, new StatEngine(3));
        engineStats.put(0x46D80, new StatEngine(4));
        engineStats.put(0x46D81, new StatEngine(4));
        engineStats.put(0x46D82, new StatEngine(4));
        engineStats.put(0x46D83, new StatEngine(4));
        engineStats.put(0x46D84, new StatEngine(4));

        //TJ Engines
        engineStats.put(0x46CE7, new StatEngine(1));
        engineStats.put(0x46CE8, new StatEngine(1));
        engineStats.put(0x46CE9, new StatEngine(1));
        engineStats.put(0x46CEA, new StatEngine(1));
        engineStats.put(0x46CEB, new StatEngine(1));
        engineStats.put(0x46CEC, new StatEngine(2));
        engineStats.put(0x46CED, new StatEngine(2));
        engineStats.put(0x46CEE, new StatEngine(2));
        engineStats.put(0x46CEF, new StatEngine(2));
        engineStats.put(0x46CF0, new StatEngine(2));
        engineStats.put(0x46CF1, new StatEngine(3));
        engineStats.put(0x46CF2, new StatEngine(3));
        engineStats.put(0x46CF3, new StatEngine(3));
        engineStats.put(0x46CF4, new StatEngine(3));
        engineStats.put(0x46CF5, new StatEngine(3));
        engineStats.put(0x46D85, new StatEngine(4));
        engineStats.put(0x46D86, new StatEngine(4));
        engineStats.put(0x46D87, new StatEngine(4));
        engineStats.put(0x46D88, new StatEngine(4));
        engineStats.put(0x46D89, new StatEngine(4));

        //HJ Engines
        engineStats.put(0x46CF6, new StatEngine(1));
        engineStats.put(0x46CF7, new StatEngine(1));
        engineStats.put(0x46CF8, new StatEngine(1));
        engineStats.put(0x46CF9, new StatEngine(1));
        engineStats.put(0x46CFA, new StatEngine(1));
        engineStats.put(0x46CFB, new StatEngine(2));
        engineStats.put(0x46CFC, new StatEngine(2));
        engineStats.put(0x46CFD, new StatEngine(2));
        engineStats.put(0x46CFE, new StatEngine(2));
        engineStats.put(0x46CFF, new StatEngine(2));
        engineStats.put(0x46D00, new StatEngine(3));
        engineStats.put(0x46D01, new StatEngine(3));
        engineStats.put(0x46D02, new StatEngine(3));
        engineStats.put(0x46D03, new StatEngine(3));
        engineStats.put(0x46D04, new StatEngine(3));
        engineStats.put(0x46D8A, new StatEngine(4));
        engineStats.put(0x46D8B, new StatEngine(4));
        engineStats.put(0x46D8C, new StatEngine(4));
        engineStats.put(0x46D8D, new StatEngine(4));
        engineStats.put(0x46D8E, new StatEngine(4));

        //HB Engines
        engineStats.put(0x46D05, new StatEngine(1));
        engineStats.put(0x46D06, new StatEngine(1));
        engineStats.put(0x46D07, new StatEngine(1));
        engineStats.put(0x46D08, new StatEngine(1));
        engineStats.put(0x46D09, new StatEngine(1));
        engineStats.put(0x46D0A, new StatEngine(2));
        engineStats.put(0x46D0B, new StatEngine(2));
        engineStats.put(0x46D0C, new StatEngine(2));
        engineStats.put(0x46D0D, new StatEngine(2));
        engineStats.put(0x46D0E, new StatEngine(2));
        engineStats.put(0x46D0F, new StatEngine(3));
        engineStats.put(0x46D10, new StatEngine(3));
        engineStats.put(0x46D11, new StatEngine(3));
        engineStats.put(0x46D12, new StatEngine(3));
        engineStats.put(0x46D13, new StatEngine(3));
        engineStats.put(0x46D8F, new StatEngine(4));
        engineStats.put(0x46D90, new StatEngine(4));
        engineStats.put(0x46D91, new StatEngine(4));
        engineStats.put(0x46D92, new StatEngine(4));
        engineStats.put(0x46D93, new StatEngine(4));

    }

    /**
     * Setter attack bonus for MS/vehicles.
     */
    private static void setAttackBonus() { //BONUS FOR EX og HG VERSJON OGSÅ!!!!!

        //Guncannons skal få bonus for MS cannon, ettersom de har 2 cannons.
        getMsVehicleStat(0x64194).setAttackBonus(0x445CC, 30); //Guncannon MP
        getMsVehicleStat(0x641E6).setAttackBonus(0x445CC, 30); //Guncannon MP
        getMsVehicleStat(0x64196).setAttackBonus(0x445CC, 40); //Guncannon 
        getMsVehicleStat(0xF6954).setAttackBonus(0x445CC, 40); //Guncannon
        getMsVehicleStat(0xF695C).setAttackBonus(0x445CC, 40); //Guncannon
        getMsVehicleStat(0x641B9).setAttackBonus(0x445CC, 50); //Guncannon Heavy
        //Bonus for EX MS Cannon
        getMsVehicleStat(0x64194).setAttackBonus(0x445FE, 30); //Guncannon MP
        getMsVehicleStat(0x641E6).setAttackBonus(0x445FE, 30); //Guncannon MP
        getMsVehicleStat(0x64196).setAttackBonus(0x445FE, 40); //Guncannon 
        getMsVehicleStat(0xF6954).setAttackBonus(0x445FE, 40); //Guncannon
        getMsVehicleStat(0xF695C).setAttackBonus(0x445FE, 40); //Guncannon
        getMsVehicleStat(0x641B9).setAttackBonus(0x445FE, 50); //Guncannon Heavy
        //Bonus for HG MS Cannon
        getMsVehicleStat(0x64194).setAttackBonus(0x44624, 30); //Guncannon MP
        getMsVehicleStat(0x641E6).setAttackBonus(0x44624, 30); //Guncannon MP
        getMsVehicleStat(0x64196).setAttackBonus(0x44624, 40); //Guncannon
        getMsVehicleStat(0xF6954).setAttackBonus(0x44624, 40); //Guncannon
        getMsVehicleStat(0xF695C).setAttackBonus(0x44624, 40); //Guncannon
        getMsVehicleStat(0x641B9).setAttackBonus(0x44624, 50); //Guncannon Heavy

        //Cannon bonus til Zaku Cannon Rabbit Ear typen
        getMsVehicleStat(0x641AB).setAttackBonus(0x445CC, 30); //Vanlig cannon
        getMsVehicleStat(0x641AB).setAttackBonus(0x445FE, 30); //EX cannon
        getMsVehicleStat(0x641AB).setAttackBonus(0x44624, 30); //HG cannon

        //RX-75 får bonus for MS cannon.
        getMsVehicleStat(0x6419C).setAttackBonus(0x445CC, 60);
        getMsVehicleStat(0x6419C).setAttackBonus(0x445FE, 60);
        getMsVehicleStat(0x6419C).setAttackBonus(0x44624, 60);
        //RX-75 farget version
        getMsVehicleStat(0x6421E).setAttackBonus(0x445CC, 60);
        getMsVehicleStat(0x6421E).setAttackBonus(0x445FE, 60);
        getMsVehicleStat(0x6421E).setAttackBonus(0x44624, 60);

        //RX-75R får bonus for MS cannon.
        getMsVehicleStat(0x6419B).setAttackBonus(0x445CC, 60);
        getMsVehicleStat(0x6419B).setAttackBonus(0x445FE, 60);
        getMsVehicleStat(0x6419B).setAttackBonus(0x44624, 60);

        //RMV-1 får bonus for MS cannon.
        getMsVehicleStat(0x641C8).setAttackBonus(0x445CC, 60);
        getMsVehicleStat(0x641C8).setAttackBonus(0x445FE, 60);
        getMsVehicleStat(0x641C8).setAttackBonus(0x44624, 60);

        //FA Gundam og Z'Gok S, Hygogg skal få bonus for MPBG.
        getMsVehicleStat(0x641DA).setAttackBonus(0x445D3, 30); //FA
        getMsVehicleStat(0x641DB).setAttackBonus(0x445D3, 30); //FA Blå 
        getMsVehicleStat(0x641FA).setAttackBonus(0x445D3, 30); //FA Hvit
        getMsVehicleStat(0x641FB).setAttackBonus(0x445D3, 30); //FA Brun
        getMsVehicleStat(0xF695B).setAttackBonus(0x445D3, 20); //Z'Gok S
        getMsVehicleStat(0x6425D).setAttackBonus(0x445D3, 20); //Hygogg

        //Bonus for EX mega PBG
        getMsVehicleStat(0x641DA).setAttackBonus(0x44605, 30); //FA
        getMsVehicleStat(0x641DB).setAttackBonus(0x44605, 30); //FA Blå
        getMsVehicleStat(0x641FA).setAttackBonus(0x44605, 30); //FA Hvit
        getMsVehicleStat(0x641FB).setAttackBonus(0x44605, 30); //FA Brun
        getMsVehicleStat(0xF695B).setAttackBonus(0x44605, 20); //Z'Gok S
        getMsVehicleStat(0x6425D).setAttackBonus(0x44605, 20); //Hygogg

        //Bonus for HG mega PBG
        getMsVehicleStat(0x641DA).setAttackBonus(0x44630, 40); //FA
        getMsVehicleStat(0x641DB).setAttackBonus(0x44630, 40); //FA Blå
        getMsVehicleStat(0x641FA).setAttackBonus(0x44630, 40); //FA Hvit
        getMsVehicleStat(0x641FB).setAttackBonus(0x44630, 40); //FA Brun
        getMsVehicleStat(0xF695B).setAttackBonus(0x44630, 20); //Z'Gok S
        getMsVehicleStat(0x6425D).setAttackBonus(0x44630, 20); //Hygogg

        //Z'Gok S og Gogg, Hygogg skal få bonus for claws.
        getMsVehicleStat(0xF695B).setAttackBonus(0x445D8, 30); //Z'Gok S
        getMsVehicleStat(0x6425D).setAttackBonus(0x445D8, 30); //Hygogg
        getMsVehicleStat(0x641AA).setAttackBonus(0x445D8, 50); //Gogg
        getMsVehicleStat(0x641E2).setAttackBonus(0x445D8, 50); //Gogg Farget
        getMsVehicleStat(0x64239).setAttackBonus(0x445D8, 50); //Gogg Farget

        //Bonus for EX claws
        getMsVehicleStat(0xF695B).setAttackBonus(0x4460A, 30); //Z'Gok S
        getMsVehicleStat(0x6425D).setAttackBonus(0x4460A, 30); //Hygogg
        getMsVehicleStat(0x641AA).setAttackBonus(0x4460A, 50); //Gogg
        getMsVehicleStat(0x641E2).setAttackBonus(0x4460A, 50); //Gogg Farget
        getMsVehicleStat(0x64239).setAttackBonus(0x4460A, 50); //Gogg Farget

        //Bonus for HG claws
        getMsVehicleStat(0x641AA).setAttackBonus(0x44633, 40); //Gogg
        getMsVehicleStat(0x641E2).setAttackBonus(0x44633, 40); //Gogg Farget
        getMsVehicleStat(0x64239).setAttackBonus(0x44633, 50); //Gogg Farget

        //Gallop, Adzam, Dobday og Bigtray skal få bonus for Fighter MG og Missile. 
        getMsVehicleStat(0xFB773).setAttackBonus(0x445CE, 100); //Gallop Fighter MG
        getMsVehicleStat(0xFB771).setAttackBonus(0x445CE, 200); //Bigtray Fighter MG
        getMsVehicleStat(0xFB774).setAttackBonus(0x445CE, 200); //Dobday Fighter MG
        getMsVehicleStat(0x668A1).setAttackBonus(0x445CE, 100); //Adzam Fighter MG
        getMsVehicleStat(0xFB773).setAttackBonus(0x44664, 50); //Gallop Fighter Missile
        getMsVehicleStat(0xFB771).setAttackBonus(0x44664, 150); //Bigtray Fighter Missile
        getMsVehicleStat(0xFB774).setAttackBonus(0x44664, 150); //Dobday Fighter Missile
        getMsVehicleStat(0x668A1).setAttackBonus(0x44664, 150); //Adzam Fighter Missile
        //Bonus for EX MG og Missile.
        getMsVehicleStat(0xFB773).setAttackBonus(0x44600, 100); //Gallop Fighter MG
        getMsVehicleStat(0xFB771).setAttackBonus(0x44600, 200); //Bigtray Fighter MG
        getMsVehicleStat(0xFB774).setAttackBonus(0x44600, 200); //Dobday Fighter MG
        getMsVehicleStat(0x668A1).setAttackBonus(0x44600, 100); //Adzam Fighter MG
        getMsVehicleStat(0xFB773).setAttackBonus(0x44696, 50); //Gallop Fighter Missile
        getMsVehicleStat(0xFB771).setAttackBonus(0x44696, 150); //Bigtray Fighter Missile
        getMsVehicleStat(0xFB774).setAttackBonus(0x44696, 150); //Dobday Fighter Missile
        getMsVehicleStat(0x668A1).setAttackBonus(0x44696, 150); //Gallop Fighter Missile
        //Bonus for HG MG.
        getMsVehicleStat(0xFB773).setAttackBonus(0x4462E, 100); //Gallop Fighter MG
        getMsVehicleStat(0xFB771).setAttackBonus(0x4462E, 200); //Bigtray Fighter MG
        getMsVehicleStat(0xFB774).setAttackBonus(0x4462E, 200); //Dobday Fighter MG
        getMsVehicleStat(0x668A1).setAttackBonus(0x4462E, 100); //Adzam Fighter MG

        //Core Booster skal få bonus for Fighter MG
        getMsVehicleStat(0x668A7).setAttackBonus(0x445CE, 200);
        getMsVehicleStat(0x668A7).setAttackBonus(0x44600, 200);
        getMsVehicleStat(0x668A7).setAttackBonus(0x4462E, 200);

        //Kampfer får bonus til Raketen Bazooka, Giant Bazooka og Giant Bazooka II.
        getMsVehicleStat(0x64243).setAttackBonus(0x446E8, 20); //Raketen Bazooka
        getMsVehicleStat(0x64243).setAttackBonus(0x446E9, 20); //Raketen Bazooka EX
        getMsVehicleStat(0x64243).setAttackBonus(0x445D7, 20); //Giant Bazooka
        getMsVehicleStat(0x64243).setAttackBonus(0x44609, 20); //Giant Bazooka EX
        getMsVehicleStat(0x64243).setAttackBonus(0x44654, 20); //Giant Bazooka II	

        //Efreet får bonus til Heat Sword.
        getMsVehicleStat(0x64244).setAttackBonus(0x445EA, 30);
        getMsVehicleStat(0x64244).setAttackBonus(0x4461C, 30);
        getMsVehicleStat(0x64244).setAttackBonus(0x4463B, 30);

        //RX-78NT-1 får bonus til NT-1 rifle.
        getMsVehicleStat(0x64245).setAttackBonus(0x446CB, 20);

        //RX-160 Byarlant får bonus til MPBG
        getMsVehicleStat(0x64253).setAttackBonus(0x445D3, 40); //MPBG
        getMsVehicleStat(0x64253).setAttackBonus(0x44605, 40); //EX MPBG
        getMsVehicleStat(0x64253).setAttackBonus(0x44630, 50); //HG MPBG
        getMsVehicleStat(0x64254).setAttackBonus(0x445D3, 40); //MPBG
        getMsVehicleStat(0x64254).setAttackBonus(0x44605, 40); //EX MPBG
        getMsVehicleStat(0x64254).setAttackBonus(0x44630, 50); //HG MPBG

    }

    /**
     * Setter visual bonus for MS.
     */
    private static void setVisualBonus() {

        //2500m visual radius for Palace Athene
        getMsVehicleStat(0x6424C).setVisualRadius(10000); //Palace Athene EF versjon.
        getMsVehicleStat(0x6424D).setVisualRadius(10000); //Palace Athene Zeon versjon.

        //2500m visual radius for RX-178 Mk-II
        getMsVehicleStat(0x64251).setVisualRadius(10000);
        getMsVehicleStat(0x64252).setVisualRadius(10000);

        //2500m visual radius for Super Gundam
        getMsVehicleStat(0x64258).setVisualRadius(10000);

        //2500m visual radius for Byarlant
        getMsVehicleStat(0x64253).setVisualRadius(10000);
        getMsVehicleStat(0x64254).setVisualRadius(10000);

        //3000m visual radius for Bolinoak Sammahn
        getMsVehicleStat(0x64255).setVisualRadius(12000);
        getMsVehicleStat(0x64256).setVisualRadius(12000);

        //2500m visual radius for Hyaku Shiki
        getMsVehicleStat(0x6425C).setVisualRadius(10000);

        //2500m visual radius for Zeta Gundam
        getMsVehicleStat(0x64260).setVisualRadius(10000);

        //2500m visual radius for Bawoo
        getMsVehicleStat(0x64261).setVisualRadius(10000);
        getMsVehicleStat(0x64262).setVisualRadius(10000);
        getMsVehicleStat(0x64263).setVisualRadius(10000);
    }
}
