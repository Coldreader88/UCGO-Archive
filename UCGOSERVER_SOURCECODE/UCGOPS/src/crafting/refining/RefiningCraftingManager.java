package crafting.refining;

import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen holder all crafting info for refining.
 */
public class RefiningCraftingManager {

    /**
     * Holder en liste over crafting stats for refining.
     *
     * KEY=Material ID
     */
    private static HashMap<Integer, RefiningCraftingStat> craftingStats = new HashMap<Integer, RefiningCraftingStat>();

    public static void execute() {

        setRefiningStats();
    }

    /**
     * Beregner resultatet av refining.
     *
     * NB! Denne metoden beregner resultatet av 1 STK material. Dersom spiller
     * f.eks lager 200 av et material må resultatet av crafting ganges opp med
     * 200.
     *
     * @param itemID ID til material som skal craftes.
     * @param skill Spillerens refinery skill.
     *
     * @return Resultatet av crafting, null hvis feil.
     */
    public static RefiningCraftingResult crafting(int itemID, int skill) {

        RefiningCraftingResult res = new RefiningCraftingResult();

        RefiningCraftingStat r = getCraftingStat(itemID);

        if (r == null) {
            return null;
        }

        //Sjekk at spillers skill er høy nok.
        if (r.getMinSkill() > skill) {
            System.out.println("\nRefining error: Insufficient skill for item ID:" + itemID);
            return null;
        }

        //Differansen mellom spillers skill og minimum skill.
        int dSkill = skill - r.getMinSkill();

        //Sjanse for å lykkes med refining.
        int refiningSjanse = 10 + (int) (100 * f(dSkill));

        //Sjanse til å mislykkes med crafting.
        int mislykketSjanse = r.getDifficulty();

        Random rnd = new Random();

        int n = rnd.nextInt(mislykketSjanse + refiningSjanse);

        if (n <= mislykketSjanse) {
            //Refining mislykkes.
            res.setSuccess(false);

            //Returner gjenværende materialer.
            //Material A er alltid brukt.
            res.setMaterialIdA(r.getMaterialIdA());
            int aRest = r.getMaterialAmountA() - r.getMaterialLoss();
            res.setMaterialAmountA(aRest);

            //Hvis material B er brukt returner rest for det.
            if (r.getMaterialIdB() != 0) {

                res.setMaterialIdB(r.getMaterialIdB());
                int bRest = r.getMaterialAmountB() - r.getMaterialLoss();
                res.setMaterialAmountB(bRest);

            }

        } else {
            //Refining lykkes.
            res.setSuccess(true);
            res.setMaterialIdA(itemID);
            res.setMaterialAmountA(1);
        }

        return res;
    }

    /**
     * Returnerer crafting stats for oppgitt material.
     *
     * @param itemID Item ID til material.
     * @return Crafting stats for oppgitt material eller null hvis ugyldig ID.
     */
    public static RefiningCraftingStat getCraftingStat(int itemID) {
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

    private static void setRefiningStats() {

        //Crafting stats etter reboot 2019.
        craftingStats.put(0x7C831, new RefiningCraftingStat(0x81651, 2, 0x81654, 2, 1, 0, 10)); //Super high tensile steel
        craftingStats.put(0x7C830, new RefiningCraftingStat(0x81652, 2, 0x81654, 2, 1, 0, 10)); //Titanium alloy
        craftingStats.put(0x7C832, new RefiningCraftingStat(0x81652, 2, 0x81654, 4, 1, 200, 10)); //Titanium ceramic composite
        craftingStats.put(0x7C833, new RefiningCraftingStat(0x81655, 2, 0x81654, 4, 1, 200, 10)); //Lunatitanium alloy
        craftingStats.put(0x7C849, new RefiningCraftingStat(0x7C84A, 200, 0x7C84B, 200, 25, 600, 10)); //Magnetic coating (Tem+Mosk parts)

        //Gamle crafting stats fra før 2019 reboot.
        /*craftingStats.put(0x7C836, new RefiningCraftingStat(0x81651,2,1,0,10)); //Steel
		craftingStats.put(0x7C837, new RefiningCraftingStat(0x81652,2,1,0,10)); //Alumina 
		craftingStats.put(0x7C83E, new RefiningCraftingStat(0x81654,3,1,398,10)); //Titanium steel
		craftingStats.put(0x7C83A, new RefiningCraftingStat(0x7C837,2,1,399,10)); //Fine ceramic*/
    }
}
