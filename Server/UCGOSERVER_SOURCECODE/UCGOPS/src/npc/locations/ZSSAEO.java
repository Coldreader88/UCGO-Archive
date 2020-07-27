package npc.locations;

import npc.AreaManager;
import npc.LocalManager;
import npc.spawn.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs i space ved ZSSAEO 3.
 *
 * Koordinater for ZSSAEO 3: 3394543, -2240120, 933261
 */
public class ZSSAEO {

    public static void execute() {

        SupplyFleet003S();
        SupplyFleet012S();
        SupplyFleet054S();
        SupplyFleet058S();
        SupplyFleet071S();
        SupplyFleet093S();
        SupplyFleet103S();
        SupplyFleet122S();

        //EF Supply 0373S
        EFsupply.spaceGuardedSupply(3391625, -2077474, 928085, "ZSSAEO 3 Fleet 0373S");

        //EF Supply 0374S
        EFsupply.spaceGuardedSupply(3472327, -2239764, 788235, "ZSSAEO 3 Fleet 0374S");
    }

    private static void SupplyFleet003S() {

        ZeonSupply.standardSpaceSupply(3375023, -2280039, 936000, "ZSSAEO 3 Fleet 003S");

        AreaManager a;

        //NPC Supply 10x10x10KM. Plassert 8KM bortenfor langs Y aksen.
        a = LocalManager.addSpaceArea(1, 3375000, -2312000, 936000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMCOMMANDS_BEAMRIFLE, 20);
        a.addNpcGrunts(EFgrunts.GM79_BLACK, 20);
        a.addNpcGrunts(EFgrunts.RB79K, 20);
        a.addNpcGrunts(EFgrunts.RMS179, 20);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO, 5);
        a.addNpcGrunts(EFgrunts.GMCANNON, 10);
    }

    private static void SupplyFleet012S() {

        ZeonSupply.standardSpaceSupply(3375000, -2344000, 936000, "ZSSAEO 3 Fleet 012S");

        AreaManager a;

        //NPC Supply 10x10x10KM. Plasert 8KM bortenfor langs Y aksen.
        a = LocalManager.addSpaceArea(1, 3375000, -2376000, 936000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.RMS179, 20);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO, 10);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO_STRONG, 10);
        a.addNpcGrunts(EFgrunts.RGM79CS, 20);
        a.addNpcGrunts(EFgrunts.GMCOMMANDS_BEAMRIFLE, 30);

    }

    private static void SupplyFleet054S() {

        ZeonSupply.standardSpaceSupply(3401055, -2228013, 890876, "ZSSAEO 3 Fleet 054S");

        AreaManager a;

        //NPC Supply 10x10x10KM. Plassert 8 KM bortenfor langs X akse.
        a = LocalManager.addSpaceArea(1, 3433000, -2228000, 890000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.RGM79CS, 20);
        a.addNpcGrunts(EFgrunts.RMS179, 20);
        a.addNpcGrunts(EFgrunts.GMCOMMANDS_BEAMRIFLE, 20);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO, 20);
        a.addNpcGrunts(EFgrunts.GM79_BSG_SHIELD, 20);
    }

    private static void SupplyFleet058S() {

        ZeonSupply.standardSpaceSupply(3465000, -2228000, 890000, "ZZSAEO 3 Fleet 058S");

        AreaManager a;

        //NPC Supply 10x10x10KM. Plassert 8KM bortenfor langs X akse.
        a = LocalManager.addSpaceArea(1, 3497000, -2228000, 890000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 20);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO, 10);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO_STRONG, 20);
        a.addNpcGrunts(EFgrunts.GMCOMMANDS_BEAMRIFLE, 10);
        a.addNpcGrunts(EFgrunts.PROTOTYPE_GUNDAM_GOLD, 3);
        a.addNpcGrunts(EFgrunts.RGM79CS, 20);
    }

    private static void SupplyFleet071S() {

        ZeonSupply.standardSpaceSupply(3433000, -2228000, 922000, "ZSSAEO 3 Fleet 071S");

        AreaManager a;

        //NPC Supply 10x10x10KM. Plassert 8KM bortenfor langs Z akse.
        a = LocalManager.addSpaceArea(1, 3433000, -2228000, 954000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO_STRONG, 20);
        a.addNpcGrunts(EFgrunts.PROTOTYPE_GUNDAM_GOLD, 5);
        a.addNpcGrunts(EFgrunts.RGM79CS, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMANDS_BEAMRIFLE, 10);
        a.addNpcGrunts(EFgrunts.MARASAI, 10);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO, 10);

    }

    private static void SupplyFleet093S() {

        ZeonSupply.standardSpaceSupply(3343000, -2232000, 935000, "ZSSAEO 3 Fleet 103S");

        AreaManager a;

        a = LocalManager.addSpaceArea(1, 3320000, -2264000, 935000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO_STRONG, 20);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 15);
        a.addNpcGrunts(EFgrunts.RGM79CS, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMANDS_BEAMRIFLE, 10);
        a.addNpcGrunts(EFgrunts.MARASAI, 20);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO, 10);
    }

    private static void SupplyFleet103S() {

        ZeonSupply.standardSpaceSupply(3375085, -2200032, 935765, "ZSSAEO 3 Fleet 103S");

        AreaManager a;

        //NPC Supply 10x10x10KM. Plassert 8KM bortenfor langs X akse.
        a = LocalManager.addSpaceArea(1, 3343000, -2200000, 935000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO, 20);
        a.addNpcGrunts(EFgrunts.RGM79CS, 20);
        a.addNpcGrunts(EFgrunts.RMS179, 40);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 20);

    }

    private static void SupplyFleet122S() {

        ZeonSupply.standardSpaceSupply(3311000, -2200000, 935000, "ZSSAEO 3 Fleet 122S");

        AreaManager a;

        //NPC Supply 10x10x10KM. Plassert 8KM bortenfor langs X akse.
        a = LocalManager.addSpaceArea(1, 3279000, -2200000, 935000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO_STRONG, 20);
        a.addNpcGrunts(EFgrunts.PROTOTYPE_GUNDAM_GOLD, 5);
        a.addNpcGrunts(EFgrunts.RGM79CS, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMANDS_BEAMRIFLE, 10);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 20);
        a.addNpcGrunts(EFgrunts.MSA003_NEMO, 10);
    }
}
