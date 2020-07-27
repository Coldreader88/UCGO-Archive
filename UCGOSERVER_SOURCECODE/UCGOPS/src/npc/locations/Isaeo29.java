package npc.locations;

import npc.*;
import npc.spawn.EFsupply;
import npc.spawn.ZeonGrunts;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs i space ved ISAEO 29.
 *
 * ISAEO 29 koordinater: -3424500, 5688800, 300
 */
public class Isaeo29 {

    public static void execute() {

        SupplyFleet004();

        SupplyFleet054();

        SupplyFleet103();

    }

    /**
     * Denne metoden plasserer NPCs ved ISAEO supply 004.
     */
    private static void SupplyFleet004() {

        //Supply Fleet 004
        EFsupply.standardSpaceSupply(-3443966, 5688565, 0, "ISAEO 29 Fleet 004");

        AreaManagerSpace a;

        //NPC område, ligger i retning bort fra ISAEO langs X-aksen. 10x10x10KM
        a = LocalManager.addSpaceArea(2, -3443966 - 32000, 5688565, 0, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.JICCO, 30);
        a.addNpcGrunts(ZeonGrunts.GATTLE, 30);
        a.addNpcGrunts(ZeonGrunts.ZAKU1, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_BAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_MG, 15);

    }

    /**
     * Denne metoden plasserer NPCs ved ISAEO supply 054.
     */
    private static void SupplyFleet054() {

        //Supply Fleet 054
        EFsupply.standardSpaceSupply(-3424050, 5708499, 0, "ISAEO 29 Fleet 054");

        AreaManagerSpace a;

        //NPC område, ligger i retning bort fra ISAEO langs Y-aksen. 10x10x10KM
        a = LocalManager.addSpaceArea(2, -3424050, 5708499 + 32000, 0, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.JICCO, 20);
        a.addNpcGrunts(ZeonGrunts.GATTLE, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU1, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_MG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_STRONG_MG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_FAST_MG, 10);

    }

    /**
     * Denne metoden plasserer NPCs ved ISAEO supply 103.
     */
    private static void SupplyFleet103() {

        //Supply Fleet 103
        EFsupply.standardSpaceSupply(-3404004, 5688549, 0, "ISAEO 29 Fleet 103");

        AreaManagerSpace a;

        //NPC område, ligger i retning bort fra ISAEO langs X-aksen. 20x20x20KM
        a = LocalManager.addSpaceArea(2, -3404004 + 56000, 5688549, 0, 40000, 40000, 40000, 8000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_MG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_STRONG_MG, 30);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1_FAST_MG, 90);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1P_FAST_MG, 90);
        a.addNpcGrunts(ZeonGrunts.ZAKU2R1P_BAZOOKA, 90);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 40);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 20);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_MMP80, 40);
        a.addNpcGrunts(ZeonGrunts.RICK_DOM_BEAMBAZOOKA, 40);

    }

}
