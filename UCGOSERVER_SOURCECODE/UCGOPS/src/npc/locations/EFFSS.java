package npc.locations;

import npc.AreaManagerSpace;
import npc.LocalManager;
import npc.spawn.EFsupply;
import npc.spawn.ZeonGrunts;
import npc.spawn.ZeonSupply;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs i space ved EFFSS 0157.
 *
 * EFFSS 0157 koordinat: -2830460, 2842200, -841540
 */
public class EFFSS {

    public static void execute() {

        //Default supplies
        SupplyFleet004S();
        SupplyFleet054S();
        SupplyFleet103S();

        SupplyFleet042S();
        SupplyFleet059S();
        SupplyFleet063S();
        SupplyFleet074S();
        SupplyFleet105S();
        SupplyFleet107S();

        //Zeon fleet 0498S
        ZeonSupply.standardSpaceSupply(-2820363, 2954872, -962009, "EFFSS 0157 Fleet 0498S");

        //Zeon fleet 0499S
        ZeonSupply.spaceFleetSupply(-2658865, 2840161, -848713, "EFFSS 0157 Fleet 0499S");
    }

    private static void SupplyFleet004S() {
        //Supply fleet 004S
        EFsupply.standardSpaceSupply(-2820019, 2812259, -823743, "EFFSS 0157 Fleet 004S");

        AreaManagerSpace a;

        //NPC supply 004S. 10x10x10KM plassert 8KM bortenfor langs Y aksen.
        a = LocalManager.addSpaceArea(2, -2820000, 2780000, -823000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_STRONG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_STRONG_MG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1P_FAST_MG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_MG, 30);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_BAZOOKA, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_JR, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_FAST_MG, 20);
    }

    private static void SupplyFleet042S() {

        EFsupply.standardSpaceSupply(-2748000, 2840000, -819000, "EFFSS 0157 Fleet 042S");

        AreaManagerSpace a;

        //NPC supply 054S. 10x10x10KM plassert 8KM bortenfor langs X aksen.
        a = LocalManager.addSpaceArea(2, -2748000, 2840000, -787000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1P_BAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_BAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_MG, 10);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S_GUNDARIUM_ALLOY, 3);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_GUNDARIUM_ALLOY, 10);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_BEAMBAZOOKA, 20);
    }

    private static void SupplyFleet054S() {

        EFsupply.standardSpaceSupply(-2780048, 2840506, -851991, "EFFSS 0157 Fleet 054S");

        AreaManagerSpace a;

        //NPC supply 054S. 10x10x10KM plassert 8KM bortenfor langs X aksen.
        a = LocalManager.addSpaceArea(2, -2748000, 2840000, -851000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_JR, 20);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_STRONG, 20);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_BEAMBAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 30);
    }

    private static void SupplyFleet059S() {

        EFsupply.standardSpaceSupply(-2820000, 2780000, -791000, "EFFSS 0157 Fleet 059S");

        AreaManagerSpace a;

        //NPC supply 059S. 10x10x10KM plassert 8KM bortenfor langs Y aksen.
        a = LocalManager.addSpaceArea(2, -2820000, 2780000, -759000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_JR, 20);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_STRONG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1P_FAST_MG, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 10);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_GUNDARIUM_ALLOY, 20);
    }

    private static void SupplyFleet063S() {

        EFsupply.standardSpaceSupply(-2748000, 2872000, -851000, "EFFSS 0157 Fleet 063S");

        AreaManagerSpace a;

        //NPC supply 063S. 10x10x10KM plassert 8KM bortenfor langs Y aksen.
        a = LocalManager.addSpaceArea(2, -2748000, 2904000, -851000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1P_BAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_BAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_MG, 10);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S_GUNDARIUM_ALLOY, 3);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_GUNDARIUM_ALLOY, 10);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_BEAMBAZOOKA, 20);

    }

    private static void SupplyFleet074S() {

        EFsupply.standardSpaceSupply(-2820000, 2812000, -759000, "EFFSS 0157 Fleet 074S");

        AreaManagerSpace a;

        //NPC Supply 074S. 10x10x10KM plassert 8KM bortenfor langs Y aksen.
        a = LocalManager.addSpaceArea(2, -2820000, 2844000, -759000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_STRONG, 10);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_STRONG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_STRONG_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_JR, 20);
        a.addNpcGrunts(ZeonGrunts.MARASAI, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_GUNDARIUM_ALLOY, 10);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM, 10);

    }

    private static void SupplyFleet103S() {

        EFsupply.standardSpaceSupply(-2819999, 2868738, -880255, "EFFSS 0157 Fleet 103S");

        AreaManagerSpace a;

        //NPC Supply 103S. 10x10x10KM plassert 8KM bortenfor langs Y aksen.
        a = LocalManager.addSpaceArea(2, -2820000, 2901000, -880000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_STRONG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_STRONG_MG, 20);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_BEAMBAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_STRONG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_JR, 20);

    }

    private static void SupplyFleet105S() {

        EFsupply.standardSpaceSupply(-2820000, 2901000, -848000, "EFFSS 0157 Fleet 105S");

        AreaManagerSpace a;

        //NPC Supply 105S. 10x10x10KM plassert 8KM bortenfor langs Z aksen.
        a = LocalManager.addSpaceArea(2, -2820000, 2901000, -816000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_STRONG, 10);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_STRONG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_STRONG_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_JR, 20);
        a.addNpcGrunts(ZeonGrunts.MARASAI, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S_GUNDARIUM_ALLOY, 2);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM, 20);

    }

    private static void SupplyFleet107S() {

        EFsupply.standardSpaceSupply(-2820000, 2933000, -880000, "EFFSS 0157 Fleet 107S");

        AreaManagerSpace a;

        //NPC Supply 107S. 10x10x10KM plassert 8KM bortenfor langs Y aksen.
        a = LocalManager.addSpaceArea(2, -2820000, 2965000, -880000, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_STRONG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_STRONG_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R2_JR, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_GUNDARIUM_ALLOY, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S_GUNDARIUM_ALLOY, 2);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM, 20);
    }
}
