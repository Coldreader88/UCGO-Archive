package crafting.clothes;

import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen holder all crafting info for klær i tillegg til å beregne
 * resultat av crafting.
 */
public class ClothesCraftingManager {

    /**
     * Holder en liste over crafting stats for alle klær.
     *
     * KEY=Item ID for klesplagg.
     */
    private static HashMap<Integer, ClothesCraftingStat> craftingStats = new HashMap<Integer, ClothesCraftingStat>();

    public static void execute() {

        coatCraftingStats();
        shoesCraftingStats();
        glassesCraftingStats();
        glovesCraftingStats();
        bottomsCraftingStats();
        shirtCraftingStats();
        hatCraftingStats();
        EFuniformCraftingStats();
        ZEONuniformCraftingStats();
        dressCraftingStats();
    }

    /**
     * Beregner resultatet av clothes crafting.
     *
     * @param itemID ID til klesplagget som skal craftes.
     * @param skill Spillerens clothes production skill.
     *
     * @return Resultatet av crafting, null hvis feil.
     */
    public static ClothesCraftingResult crafting(int itemID, int skill) {

        ClothesCraftingResult res = new ClothesCraftingResult();

        ClothesCraftingStat c = getCraftingStat(itemID);

        if (c == null) {
            return null;
        }

        //Sjekk at spillers skill er høy nok.
        if (c.getMinSkill() > skill) {
            System.out.println("\nClothes Crafting error: Insufficient skill for item ID:" + itemID);
            return null;
        }

        //Differansen mellom spillers skill og minimum skill.
        int dSkill = skill - c.getMinSkill();

        //Sjanse for å få standard versjon.
        int standardSjanse = (int) (100 * f(skill));

        //Sjanse for å få GQ versjon.
        int GQsjanse = 0;

        //Beregn GQ sjanse kun hvis GQ versjon er tilgjengelig.
        if (c.getItemGQid() != 0) {

            GQsjanse = (int) (50 * f(dSkill));
        }

        //Sjanse til å mislykkes med crafting.
        int mislykketSjanse = c.getDifficulty();

        Random r = new Random();

        int n = r.nextInt(mislykketSjanse + standardSjanse + GQsjanse);

        if (n <= mislykketSjanse) {
            //Crafting mislykkes.
            res.setSuccess(false);
        } else if (n <= (mislykketSjanse + standardSjanse)) {
            //Fikk standard versjon.
            res.setSuccess(true);
            res.setGQ(false);
        } else {
            //Fikk GQ versjon.
            res.setSuccess(true);
            res.setGQ(true);
        }

        return res;
    }

    /**
     * Returnerer crafting stats for oppgitt item.
     *
     * @param itemID ID for item vi skal returnere stats for.
     *
     * @return Crafting stats for oppgitt item, null hvis ugyldig item.
     */
    public static ClothesCraftingStat getCraftingStat(int itemID) {
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

    private static void coatCraftingStats() {

        craftingStats.put(0x3A9D9, new ClothesCraftingStat(200, 25, 50)); //Leather jacket
        craftingStats.put(0x3AA6C, new ClothesCraftingStat(200, 25, 50)); //Plain turtleneck sweater
        craftingStats.put(0x3AA6B, new ClothesCraftingStat(200, 25, 50)); //Turtleneck sweater
        craftingStats.put(0x3AA1E, new ClothesCraftingStat(800, 50, 50)); //Short coat
        craftingStats.put(0x3AA6A, new ClothesCraftingStat(800, 50, 50)); //Fur trimmed parka
    }

    private static void shoesCraftingStats() {

        craftingStats.put(0x3A9D5, new ClothesCraftingStat(100, 10, 50)); //Sneakers
        craftingStats.put(0x3AA0C, new ClothesCraftingStat(200, 20, 50)); //Beach sandal
        craftingStats.put(0x3A9FE, new ClothesCraftingStat(200, 20, 50)); //Cotton sneakers
        craftingStats.put(0x3AA0E, new ClothesCraftingStat(200, 20, 50)); //Sabot sandal		
        craftingStats.put(0x3AA0D, new ClothesCraftingStat(200, 20, 50)); //Sports sandal
        craftingStats.put(0x3A9FF, new ClothesCraftingStat(500, 50, 50)); //Leather short boots
        craftingStats.put(0x3AA6F, new ClothesCraftingStat(500, 50, 50)); //Moccasin
        craftingStats.put(0x3AA70, new ClothesCraftingStat(620, 60, 50)); //Engineer boots
        craftingStats.put(0x3AA71, new ClothesCraftingStat(620, 60, 50));  //Fur trimmed boots

    }

    private static void glassesCraftingStats() {

        craftingStats.put(0x3AA77, new ClothesCraftingStat(300, 40, 50, 0x3AB56)); //Eye patch
        craftingStats.put(0x3AA78, new ClothesCraftingStat(300, 40, 50, 0x3AB57)); //Eye patch skull
        craftingStats.put(0x3AA12, new ClothesCraftingStat(500, 50, 50, 0x3AB15)); //Wire rim
        craftingStats.put(0x3AA19, new ClothesCraftingStat(500, 50, 50, 0x3AB1C)); //Round glasses
        craftingStats.put(0x3AA14, new ClothesCraftingStat(500, 50, 50, 0x3AB17)); //Black frame
        craftingStats.put(0x3AA13, new ClothesCraftingStat(500, 50, 50, 0x3AB16)); //Half rim
        craftingStats.put(0x3AA02, new ClothesCraftingStat(800, 70, 50, 0x3AB14)); //Sports
        craftingStats.put(0x3A9DC, new ClothesCraftingStat(800, 70, 50, 0x3AAF2)); //Sunglasses
        craftingStats.put(0x3AA41, new ClothesCraftingStat(800, 200, 50)); //Protective Sunglasses
        craftingStats.put(0x3AA15, new ClothesCraftingStat(600, 70, 50)); //Safety glasses A
        craftingStats.put(0x3AA16, new ClothesCraftingStat(600, 70, 50)); //Safety glasses B

    }

    private static void glovesCraftingStats() {

        craftingStats.put(0x3AA04, new ClothesCraftingStat(0, 5, 50)); //Wristband two-tone
        craftingStats.put(0x3AA03, new ClothesCraftingStat(0, 1, 50)); //Wristband
        craftingStats.put(0x3A9D6, new ClothesCraftingStat(200, 20, 50, 0x3AAF0)); //EF Gloves
        craftingStats.put(0x3A9D7, new ClothesCraftingStat(200, 20, 50, 0x3AAF1)); //Zeon gloves
        craftingStats.put(0x3AA2C, new ClothesCraftingStat(200, 20, 50, 0x3AB29)); //EF fingerless gloves
        craftingStats.put(0x3AA4F, new ClothesCraftingStat(200, 20, 50)); //Mittens
        craftingStats.put(0x3AA51, new ClothesCraftingStat(200, 20, 50, 0x3AB4D)); //Stud wristband gold
        craftingStats.put(0x3AA50, new ClothesCraftingStat(200, 20, 50, 0x3AB4C)); //Stud wristband silver
        craftingStats.put(0x3AA2F, new ClothesCraftingStat(200, 20, 50, 0x3AB2C)); //Zeon long gloves
        craftingStats.put(0x3AA17, new ClothesCraftingStat(900, 70, 50, 0x3AB1A)); //Sportswatch

    }

    private static void bottomsCraftingStats() {

        craftingStats.put(0x3AA6E, new ClothesCraftingStat(0, 1, 50)); //Sideline spats
        craftingStats.put(0x3AA6D, new ClothesCraftingStat(0, 1, 50)); //Spats
        craftingStats.put(0x3A9D3, new ClothesCraftingStat(100, 10, 50)); //Kneelength pants
        craftingStats.put(0x3AA0F, new ClothesCraftingStat(200, 20, 50)); //Chinos
        craftingStats.put(0x3A9D4, new ClothesCraftingStat(200, 20, 50)); //Jeans
        craftingStats.put(0x3AA10, new ClothesCraftingStat(200, 20, 50)); //Kneelength cargo pants
        craftingStats.put(0x3AA65, new ClothesCraftingStat(200, 20, 50)); //Bellbottom jeans
        craftingStats.put(0x3AA80, new ClothesCraftingStat(200, 20, 50)); //Check box pleats skirt
        craftingStats.put(0x3AA67, new ClothesCraftingStat(200, 20, 50)); //Cropped jeans
        craftingStats.put(0x3AA69, new ClothesCraftingStat(200, 20, 50)); //Sabrina pants
        craftingStats.put(0x3AA63, new ClothesCraftingStat(200, 20, 50)); //Sideline jeans
        craftingStats.put(0x3AA66, new ClothesCraftingStat(200, 20, 50)); //Hot pants
        craftingStats.put(0x3AA64, new ClothesCraftingStat(200, 20, 50)); //Loose fit jeans
        craftingStats.put(0x3AA68, new ClothesCraftingStat(200, 20, 50)); //Painter pants
        craftingStats.put(0x3AA7F, new ClothesCraftingStat(300, 30, 50)); //Box pleats skirt
        craftingStats.put(0x3AA81, new ClothesCraftingStat(420, 40, 50)); //Embroidered skirt
        craftingStats.put(0x3AA7E, new ClothesCraftingStat(540, 50, 50)); //Pants with buttons
        craftingStats.put(0x3AA7D, new ClothesCraftingStat(540, 50, 50)); //Chinese pattern pants
    }

    private static void shirtCraftingStats() {

        craftingStats.put(0x3AA07, new ClothesCraftingStat(0, 1, 50)); //L T-shirt border
        craftingStats.put(0x3AA06, new ClothesCraftingStat(0, 1, 50)); //L T-shirt two tone
        craftingStats.put(0x3AA05, new ClothesCraftingStat(0, 1, 50)); //L T-shirt
        craftingStats.put(0x3AA11, new ClothesCraftingStat(0, 1, 50)); //Polo shirt
        craftingStats.put(0x3A9D2, new ClothesCraftingStat(0, 1, 50)); //Shirt
        craftingStats.put(0x3A9DA, new ClothesCraftingStat(0, 1, 50)); //T-shirt two tone
        craftingStats.put(0x3A9D1, new ClothesCraftingStat(0, 1, 50)); //T-shirt	
        craftingStats.put(0x3AA62, new ClothesCraftingStat(0, 5, 50)); //Cleric shirt
        craftingStats.put(0x3AA5E, new ClothesCraftingStat(0, 1, 50)); //Flannel shirt
        craftingStats.put(0x3AA7A, new ClothesCraftingStat(0, 1, 50)); //No sleeve shirt
        craftingStats.put(0x3AA79, new ClothesCraftingStat(0, 1, 50)); //No sleeve sports shirt
        craftingStats.put(0x3AA60, new ClothesCraftingStat(0, 5, 50)); //Shirt with tie
        craftingStats.put(0x3AA5D, new ClothesCraftingStat(0, 1, 50)); //Sports T-shirt
        craftingStats.put(0x3AA5F, new ClothesCraftingStat(0, 1, 50)); //Striped shirt
        craftingStats.put(0x3AA61, new ClothesCraftingStat(0, 1, 50)); //White shirt with tie		
        craftingStats.put(0x3AA7C, new ClothesCraftingStat(540, 50, 50)); //Chinese no sleeves
        craftingStats.put(0x3AA7B, new ClothesCraftingStat(540, 50, 50)); //Chinese pattern no sleeves}
    }

    private static void hatCraftingStats() {

        craftingStats.put(0x3AA72, new ClothesCraftingStat(0, 1, 50)); //Hachimaki
        craftingStats.put(0x3A9DB, new ClothesCraftingStat(0, 1, 50)); //Cap two tone
        craftingStats.put(0x3A9D8, new ClothesCraftingStat(0, 1, 50)); //Cap
        craftingStats.put(0x3AA08, new ClothesCraftingStat(0, 1, 50)); //Strawhat
        craftingStats.put(0x3AA09, new ClothesCraftingStat(0, 1, 50)); //Boater
        craftingStats.put(0x3AA73, new ClothesCraftingStat(100, 10, 50)); //Bandanna
        craftingStats.put(0x3AA0A, new ClothesCraftingStat(100, 20, 50)); //De Gaulle cap
        craftingStats.put(0x3AA0B, new ClothesCraftingStat(100, 20, 50)); //De Gaulle cap two tone
        craftingStats.put(0x3A9DD, new ClothesCraftingStat(200, 20, 50, 0x3AAF3)); //EF hat
        craftingStats.put(0x3AA42, new ClothesCraftingStat(200, 20, 50)); //EF Beret A
        craftingStats.put(0x3AA43, new ClothesCraftingStat(200, 20, 50)); //EF Beret B
        craftingStats.put(0x3AA44, new ClothesCraftingStat(200, 20, 50)); //EF Beret S
        craftingStats.put(0x3A984, new ClothesCraftingStat(200, 20, 50, 0x3AAAC)); //EF helmet
        craftingStats.put(0x3A985, new ClothesCraftingStat(200, 20, 50, 0x3AAAD)); //Zeon helmet
        craftingStats.put(0x3AA1F, new ClothesCraftingStat(200, 20, 50, 0x3AB21)); //EF work cap
        craftingStats.put(0x3AA20, new ClothesCraftingStat(200, 20, 50, 0x3AB22)); //Zeon work cap
        craftingStats.put(0x3AA01, new ClothesCraftingStat(200, 20, 50)); //Hunting cap
        craftingStats.put(0x3A9DE, new ClothesCraftingStat(200, 20, 50, 0x3AAF4)); //Zeon hat
        craftingStats.put(0x3A9FD, new ClothesCraftingStat(200, 20, 50, 0x3AB13)); //Zeon S hat
        craftingStats.put(0x3AA75, new ClothesCraftingStat(200, 20, 50, 0x3AB54)); //Bandana three color
        craftingStats.put(0x3AA74, new ClothesCraftingStat(200, 20, 50, 0x3AB53)); //Bandana tiger stripe
        craftingStats.put(0x3AA76, new ClothesCraftingStat(200, 20, 50, 0x3AB55)); //Bandana urban camo
        craftingStats.put(0x3AA52, new ClothesCraftingStat(200, 20, 50)); //Beret
        craftingStats.put(0x3AA29, new ClothesCraftingStat(200, 20, 50, 0x3AB26)); //EF Combat helmet
        craftingStats.put(0x3AA54, new ClothesCraftingStat(200, 20, 50)); //Ski cap
        craftingStats.put(0x3AA55, new ClothesCraftingStat(200, 20, 50)); //Tibetan cap
        craftingStats.put(0x3AA53, new ClothesCraftingStat(200, 20, 50)); //Watch cap
        craftingStats.put(0x3AA2D, new ClothesCraftingStat(200, 20, 50, 0x3AB2A)); //Zeon overseas cap A
        craftingStats.put(0x3AA45, new ClothesCraftingStat(200, 20, 50)); //Zeon overseas cap B
        craftingStats.put(0x3AA46, new ClothesCraftingStat(200, 20, 50)); //Zeon overseas cap S
        craftingStats.put(0x3AA00, new ClothesCraftingStat(500, 50, 50)); //Cowboy hat
        craftingStats.put(0x3AA21, new ClothesCraftingStat(500, 50, 50, 0x3AB23)); //Goggles
        craftingStats.put(0x3AA1C, new ClothesCraftingStat(900, 70, 50, 0x3AB1F)); //Headset mathum sonic
        craftingStats.put(0x3AA1D, new ClothesCraftingStat(900, 70, 50, 0x3AB20)); //Headset felipe
        craftingStats.put(0x3AA27, new ClothesCraftingStat(900, 70, 50, 0x3AB24)); //Headset suze
        craftingStats.put(0x3AA28, new ClothesCraftingStat(900, 70, 50, 0x3AB25)); //Headset gramonica
    }

    private static void EFuniformCraftingStats() {

        //EF overalls
        craftingStats.put(0x3A9C6, new ClothesCraftingStat(220, 30, 80, 0x3AAEE)); //SR
        craftingStats.put(0x3A9DF, new ClothesCraftingStat(260, 40, 80, 0x3AAF5)); //SA
        craftingStats.put(0x3A9E0, new ClothesCraftingStat(300, 45, 80, 0x3AAF6)); //SN
        craftingStats.put(0x3A9E1, new ClothesCraftingStat(340, 45, 80, 0x3AAF7)); //PO
        craftingStats.put(0x3A9E2, new ClothesCraftingStat(380, 50, 80, 0x3AAF8)); //CPO
        craftingStats.put(0x3A9E3, new ClothesCraftingStat(420, 55, 80, 0x3AAF9)); //SCPO
        craftingStats.put(0x3A9E4, new ClothesCraftingStat(460, 60, 80, 0x3AAFA)); //ENS
        craftingStats.put(0x3A9E5, new ClothesCraftingStat(500, 60, 80, 0x3AAFB)); //LTJG
        craftingStats.put(0x3A9E6, new ClothesCraftingStat(540, 60, 80, 0x3AAFC)); //LT
        craftingStats.put(0x3A9E7, new ClothesCraftingStat(580, 65, 80, 0x3AAFD)); //LCDR
        craftingStats.put(0x3A9E8, new ClothesCraftingStat(620, 70, 80, 0x3AAFE)); //CDR
        craftingStats.put(0x3A9E9, new ClothesCraftingStat(660, 75, 80, 0x3AAFF)); //CAPT
        craftingStats.put(0x3A9EA, new ClothesCraftingStat(700, 80, 80, 0x3AB00)); //RDML
        craftingStats.put(0x3A9EB, new ClothesCraftingStat(740, 85, 80, 0x3AB01)); //RADM
        craftingStats.put(0x3A9EC, new ClothesCraftingStat(780, 90, 80, 0x3AB02)); //VADM
        craftingStats.put(0x3A9ED, new ClothesCraftingStat(820, 100, 80, 0x3AB03)); //ADM

        //EF uniform
        craftingStats.put(0x3A986, new ClothesCraftingStat(220, 30, 80, 0x3AAAE)); //SR
        craftingStats.put(0x3A987, new ClothesCraftingStat(260, 40, 80, 0x3AAAF)); //SA
        craftingStats.put(0x3A988, new ClothesCraftingStat(300, 45, 80, 0x3AAB0)); //SN
        craftingStats.put(0x3A989, new ClothesCraftingStat(340, 45, 80, 0x3AAB1)); //PO
        craftingStats.put(0x3A98A, new ClothesCraftingStat(380, 50, 80, 0x3AAB2)); //CPO
        craftingStats.put(0x3A98B, new ClothesCraftingStat(420, 55, 80, 0x3AAB3)); //SCPO
        craftingStats.put(0x3A98C, new ClothesCraftingStat(460, 60, 80, 0x3AAB4)); //ENS
        craftingStats.put(0x3A98D, new ClothesCraftingStat(500, 60, 80, 0x3AAB5)); //LTJG
        craftingStats.put(0x3A98E, new ClothesCraftingStat(540, 60, 80, 0x3AAB6)); //LT
        craftingStats.put(0x3A98F, new ClothesCraftingStat(580, 65, 80, 0x3AAB7)); //LCDR
        craftingStats.put(0x3A990, new ClothesCraftingStat(620, 70, 80, 0x3AAB8)); //CDR
        craftingStats.put(0x3A991, new ClothesCraftingStat(660, 75, 80, 0x3AAB9)); //CAPT
        craftingStats.put(0x3A992, new ClothesCraftingStat(700, 80, 80, 0x3AABA)); //RDML
        craftingStats.put(0x3A993, new ClothesCraftingStat(740, 85, 80, 0x3AABB)); //RADM
        craftingStats.put(0x3A994, new ClothesCraftingStat(780, 90, 80, 0x3AABC)); //VADM
        craftingStats.put(0x3A995, new ClothesCraftingStat(820, 100, 80, 0x3AABD)); //ADM

        //EF spacesuits
        craftingStats.put(0x3A9A6, new ClothesCraftingStat(500, 40, 80, 0x3AACE)); //SR
        craftingStats.put(0x3A9A7, new ClothesCraftingStat(530, 40, 80, 0x3AACF)); //SA
        craftingStats.put(0x3A9A8, new ClothesCraftingStat(560, 40, 80, 0x3AAD0)); //SN
        craftingStats.put(0x3A9A9, new ClothesCraftingStat(590, 50, 80, 0x3AAD1)); //PO
        craftingStats.put(0x3A9AA, new ClothesCraftingStat(620, 55, 80, 0x3AAD2)); //CPO
        craftingStats.put(0x3A9AB, new ClothesCraftingStat(650, 60, 80, 0x3AAD3)); //SCPO
        craftingStats.put(0x3A9AC, new ClothesCraftingStat(680, 65, 80, 0x3AAD4)); //ENS
        craftingStats.put(0x3A9AD, new ClothesCraftingStat(710, 70, 80, 0x3AAD5)); //LTJG
        craftingStats.put(0x3A9AE, new ClothesCraftingStat(740, 75, 80, 0x3AAD6)); //LT
        craftingStats.put(0x3A9AF, new ClothesCraftingStat(770, 80, 80, 0x3AAD7)); //LCDR
        craftingStats.put(0x3A9B0, new ClothesCraftingStat(800, 85, 80, 0x3AAD8)); //CDR
        craftingStats.put(0x3A9B1, new ClothesCraftingStat(830, 90, 80, 0x3AAD9)); //CAPT
        craftingStats.put(0x3A9B2, new ClothesCraftingStat(860, 95, 80, 0x3AADA)); //RDML
        craftingStats.put(0x3A9B3, new ClothesCraftingStat(890, 100, 80, 0x3AADB)); //RADM
        craftingStats.put(0x3A9B4, new ClothesCraftingStat(920, 100, 80, 0x3AADC)); //VADM
        craftingStats.put(0x3A9B5, new ClothesCraftingStat(950, 100, 80, 0x3AADD)); //ADM
    }

    private static void ZEONuniformCraftingStats() {

        //Zeon overalls
        craftingStats.put(0x3A9C7, new ClothesCraftingStat(220, 30, 80, 0x3AAEF)); //SR
        craftingStats.put(0x3A9EE, new ClothesCraftingStat(260, 40, 80, 0x3AB04)); //SA
        craftingStats.put(0x3A9EF, new ClothesCraftingStat(300, 45, 80, 0x3AB05)); //SN
        craftingStats.put(0x3A9F0, new ClothesCraftingStat(340, 45, 80, 0x3AB06)); //PO
        craftingStats.put(0x3A9F1, new ClothesCraftingStat(380, 50, 80, 0x3AB07)); //CPO
        craftingStats.put(0x3A9F2, new ClothesCraftingStat(420, 55, 80, 0x3AB08)); //SCPO
        craftingStats.put(0x3A9F3, new ClothesCraftingStat(460, 60, 80, 0x3AB09)); //ENS
        craftingStats.put(0x3A9F4, new ClothesCraftingStat(500, 60, 80, 0x3AB0A)); //LTJG
        craftingStats.put(0x3A9F5, new ClothesCraftingStat(540, 60, 80, 0x3AB0B)); //LT
        craftingStats.put(0x3A9F6, new ClothesCraftingStat(580, 65, 80, 0x3AB0C)); //LCDR
        craftingStats.put(0x3A9F7, new ClothesCraftingStat(620, 70, 80, 0x3AB0D)); //CDR
        craftingStats.put(0x3A9F8, new ClothesCraftingStat(660, 75, 80, 0x3AB0E)); //CAPT
        craftingStats.put(0x3A9F9, new ClothesCraftingStat(700, 80, 80, 0x3AB0F)); //RDML
        craftingStats.put(0x3A9FA, new ClothesCraftingStat(740, 85, 80, 0x3AB10)); //RADM
        craftingStats.put(0x3A9FB, new ClothesCraftingStat(780, 90, 80, 0x3AB11)); //VADM
        craftingStats.put(0x3A9FC, new ClothesCraftingStat(820, 100, 80, 0x3AB12)); //ADM

        //Zeon uniform
        craftingStats.put(0x3A996, new ClothesCraftingStat(220, 30, 80, 0x3AABE)); //SR
        craftingStats.put(0x3A997, new ClothesCraftingStat(260, 40, 80, 0x3AABF)); //SA
        craftingStats.put(0x3A998, new ClothesCraftingStat(300, 45, 80, 0x3AAC0)); //SN
        craftingStats.put(0x3A999, new ClothesCraftingStat(340, 45, 80, 0x3AAC1)); //PO
        craftingStats.put(0x3A99A, new ClothesCraftingStat(380, 50, 80, 0x3AAC2)); //CPO
        craftingStats.put(0x3A99B, new ClothesCraftingStat(420, 55, 80, 0x3AAC3)); //SCPO
        craftingStats.put(0x3A99C, new ClothesCraftingStat(460, 60, 80, 0x3AAC4)); //ENS
        craftingStats.put(0x3A99D, new ClothesCraftingStat(500, 60, 80, 0x3AAC5)); //LTJG
        craftingStats.put(0x3A99E, new ClothesCraftingStat(540, 60, 80, 0x3AAC6)); //LT
        craftingStats.put(0x3A99F, new ClothesCraftingStat(580, 65, 80, 0x3AAC7)); //LCDR
        craftingStats.put(0x3A9A0, new ClothesCraftingStat(620, 70, 80, 0x3AAC8)); //CDR
        craftingStats.put(0x3A9A1, new ClothesCraftingStat(660, 75, 80, 0x3AAC9)); //CAPT
        craftingStats.put(0x3A9A2, new ClothesCraftingStat(700, 80, 80, 0x3AACA)); //RDML
        craftingStats.put(0x3A9A3, new ClothesCraftingStat(740, 85, 80, 0x3AACB)); //RADM
        craftingStats.put(0x3A9A4, new ClothesCraftingStat(780, 90, 80, 0x3AACC)); //VADM
        craftingStats.put(0x3A9A5, new ClothesCraftingStat(820, 100, 80, 0x3AACD)); //ADM

        //Zeon spacesuits
        craftingStats.put(0x3A9B6, new ClothesCraftingStat(500, 40, 80, 0x3AADE)); //SR
        craftingStats.put(0x3A9B7, new ClothesCraftingStat(530, 40, 80, 0x3AADF)); //SA
        craftingStats.put(0x3A9B8, new ClothesCraftingStat(560, 40, 80, 0x3AAE0)); //SN
        craftingStats.put(0x3A9B9, new ClothesCraftingStat(590, 50, 80, 0x3AAE1)); //PO
        craftingStats.put(0x3A9BA, new ClothesCraftingStat(620, 55, 80, 0x3AAE2)); //CPO
        craftingStats.put(0x3A9BB, new ClothesCraftingStat(650, 60, 80, 0x3AAE3)); //SCPO
        craftingStats.put(0x3A9BC, new ClothesCraftingStat(680, 65, 80, 0x3AAE4)); //ENS
        craftingStats.put(0x3A9BD, new ClothesCraftingStat(710, 70, 80, 0x3AAE5)); //LTJG
        craftingStats.put(0x3A9BE, new ClothesCraftingStat(740, 75, 80, 0x3AAE6)); //LT
        craftingStats.put(0x3A9BF, new ClothesCraftingStat(770, 80, 80, 0x3AAE7)); //LCDR
        craftingStats.put(0x3A9C0, new ClothesCraftingStat(800, 85, 80, 0x3AAE8)); //CDR
        craftingStats.put(0x3A9C1, new ClothesCraftingStat(830, 90, 80, 0x3AAE9)); //CAPT
        craftingStats.put(0x3A9C2, new ClothesCraftingStat(860, 95, 80, 0x3AAEA)); //RDML
        craftingStats.put(0x3A9C3, new ClothesCraftingStat(890, 100, 80, 0x3AAEB)); //RADM
        craftingStats.put(0x3A9C4, new ClothesCraftingStat(920, 100, 80, 0x3AAEC)); //VADM
        craftingStats.put(0x3A9C5, new ClothesCraftingStat(950, 100, 80, 0x3AAED)); //ADM
    }

    private static void dressCraftingStats() {

        craftingStats.put(0x3AA82, new ClothesCraftingStat(660, 60, 80)); //Formal suit
        craftingStats.put(0x3AA2B, new ClothesCraftingStat(220, 30, 80, 0x3AB28)); //EF combat uniform
        craftingStats.put(0x3AA2E, new ClothesCraftingStat(220, 30, 80, 0x3AB2B)); //Zeon combat uniform	
    }

}
