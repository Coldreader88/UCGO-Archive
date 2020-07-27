package crafting.mining;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class MiningManager {

    /**
     * Stats for alle items som kan "mines" lagres her.
     */
    private static LinkedList<MiningStat> miningStats = new LinkedList<MiningStat>();

    /**
     * Initialiserer data for mining.
     */
    public static void execute() {

        //Sett mining stats for raw materials.
        miningStats.add(new MiningStat(0x81651, 4, 0x4, 0xFF, 30, true)); //Iron ore
        miningStats.add(new MiningStat(0x81652, 4, 0x4, 0xFF, 30, true)); //Bauxite
        miningStats.add(new MiningStat(0x81654, 2, 0x0, 0xFF, 30, true)); //Rutile		
        miningStats.add(new MiningStat(0x81655, 8, 0x4, 0xFF, 30, true)); //Lunatitanium
        //miningStats.add(new MiningStat(0x81653,20,0xD,0xFF,7,true)); //Fine Rutile. Fjernet etter reboot 2019.

        //Sett mining stats for gemstones.
        miningStats.add(new MiningStat(0x8647D, 250, 0x14, 0x1F, 1, false)); //Opal
        miningStats.add(new MiningStat(0x8647E, 350, 0x14, 0x1F, 1, false)); //Topaz
        miningStats.add(new MiningStat(0x8647F, 700, 0x15, 0x1F, 1, false)); //Amethyst
        miningStats.add(new MiningStat(0x86480, 1000, 0x18, 0x1F, 1, false)); //Ruby
        miningStats.add(new MiningStat(0x86620, 1200, 0x18, 0x1F, 1, false)); //Saphire
        miningStats.add(new MiningStat(0x86481, 1500, 0x19, 0x1F, 1, false)); //Emerald
        miningStats.add(new MiningStat(0x86621, 1800, 0x1B, 0x1F, 1, false)); //Diamond

        //Sett mining stats for fossils.
        miningStats.add(new MiningStat(0x8647A, 1200, 0x19, 0x1F, 1, false)); //Lizzard
        miningStats.add(new MiningStat(0x8647B, 1300, 0x1A, 0x1F, 1, false)); //Skull
        miningStats.add(new MiningStat(0x8661E, 1500, 0x1B, 0x1F, 1, false)); //Trilobite
        miningStats.add(new MiningStat(0x8647C, 1600, 0x1C, 0x1F, 1, false)); //Ammonite
        miningStats.add(new MiningStat(0x8661F, 1800, 0x1D, 0x1F, 1, false)); //Apeman

        //Sett mining stats for upgrade part. Kan kun fåes i Newman/Richmond.
        miningStats.add(new MiningStat(0x07C84A, 600, 0x20, 0xFF, 3, true)); //Ginius
        miningStats.add(new MiningStat(0x07C84B, 600, 0x20, 0xFF, 3, true)); //Mosk
        miningStats.add(new MiningStat(0x07C84C, 600, 0x20, 0xFF, 3, true)); //Tem
    }

    /**
     * Beregner resultatet av mining.
     *
     * @param skill Spillerens mining skill.
     * @param area Verdi sendt i opcode 0x32, angir hvor bra område er.
     *
     * @return Liste over alle items som ble "minet".
     */
    public static LinkedList<MiningResult> mining(int skill, int area) {

        LinkedList<MiningResult> resultat = new LinkedList<MiningResult>();

        //Gå gjennom alle mining stats og beregn resultat for dem.
        Iterator<MiningStat> stats = miningStats.iterator();

        while (stats.hasNext()) {

            MiningResult r = getResult(stats.next(), skill, area);

            if (r != null) {
                resultat.add(r);
            }
        }

        return resultat;
    }

    /**
     * Beregner resultatet av mining for en item. Denne regner ut om spilleren
     * fikk oppgitt item eller ikke.
     *
     * @param stat Item som skal sjekkes.
     * @param skill Spillerens mining skill.
     * @param area Verdi sendt i opcode 0x32.
     *
     * @return Dersom spill fikk item returneres et MiningResult, eller
     * returneres NULL.
     */
    private static MiningResult getResult(MiningStat stat, int skill, int area) {

        MiningResult resultat = null;

        //Sjekk at area er riktig.
        if (area < stat.getMinMineArea() || area > stat.getMaxMineArea()) {
            return null;
        }

        //Beregn spillers sjanse til å få item.
        double x = Math.sqrt((double) skill) / Math.sqrt(1300);
        int sjanse = (int) (100 * x);

        //Avgjør om spiller får item eller ikke.
        Random r = new Random();

        int i = r.nextInt(sjanse + stat.getSjanse());

        //Sjekk om "i" havnet i området som gir item eller ikke.
        if (i >= sjanse) {
            return null;
        }

        //OK vi fikk item. Beregn antall vi fikk. 
        int antall;

        if (!stat.isStackable()) {
            antall = 1;
        } else {
            //Item er stackable.
            antall = r.nextInt(stat.getAntall()) + 1;
            //Gi bonus for stackable items.
            double bonus = Math.sqrt((double) skill) / Math.sqrt(1300);
            antall += (double) antall * bonus;
        }

        resultat = new MiningResult(stat.getItemID(), stat.isStackable(), antall);

        return resultat;
    }
}
