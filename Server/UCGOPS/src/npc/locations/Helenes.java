package npc.locations;

import npc.*;
import npc.spawn.EFnpcVSnpc;
import npc.spawn.EFsupply;
import npc.spawn.ZeonGrunts;
import npc.spawn.ZeonNpcVsNpc;
import npc.spawn.ZeonSupply;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs i space ved Helenes.
 *
 * Helenes Koordinater: 0, 0, 0
 */
public class Helenes {

    public static void execute() {

        //Helenes EF fleet
        EFsupply.spaceFleetSupply(-38085, 37008, -10451, "Helenes EF Fleet");

        //Helenes Zeon fleet
        ZeonSupply.spaceFleetSupply(52008, -23007, 0, "Helenes Zeon Fleet");

        //Helenes NPC vs NPC battlefield.
        //VI KAN LEGGE INN ET NPC vs NPC MED OYW MS SENERE
        /*
        AreaManagerSpace a = LocalManager.addNpcBattlefieldSpaceArea(0, 0, 0, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(EFnpcVSnpc.RMS179, 20, 1);
        a.addNpcGrunts(EFnpcVSnpc.RMS179_STRONG, 20, 1);
        a.addNpcGrunts(EFnpcVSnpc.NEMO, 40, 1);
        a.addNpcGrunts(EFnpcVSnpc.NEMO_STRONG, 20, 1);
        a.addNpcGrunts(EFnpcVSnpc.JEGAN, 40, 1);
        a.addNpcGrunts(EFnpcVSnpc.JEGAN_STRONG, 20, 1);
        a.addNpcGrunts(EFnpcVSnpc.MARASAI, 10, 1);
        a.addNpcGrunts(EFnpcVSnpc.MARASAI_STRONG, 10, 1);
        a.addNpcGrunts(EFnpcVSnpc.RICKDIAS, 10, 1);
        a.addNpcGrunts(EFnpcVSnpc.RICKDIAS_STRONG, 10, 1);
        a.addNpcGrunts(EFnpcVSnpc.GP01_GUNDAM, 3, 1);
        a.addNpcGrunts(EFnpcVSnpc.RX178, 3, 1);

        a.addNpcGrunts(ZeonNpcVsNpc.ZAKUR1, 20, 2);
        a.addNpcGrunts(ZeonNpcVsNpc.ZAKUR1_STRONG, 20, 2);
        a.addNpcGrunts(ZeonNpcVsNpc.HIZACK, 40, 2);
        a.addNpcGrunts(ZeonNpcVsNpc.HIZACK_STRONG, 20, 2);
        a.addNpcGrunts(ZeonNpcVsNpc.GAZAC, 40, 2);
        a.addNpcGrunts(ZeonNpcVsNpc.GAZAC_STRONG, 20, 2);
        a.addNpcGrunts(ZeonNpcVsNpc.MARASAI, 10, 2);
        a.addNpcGrunts(ZeonNpcVsNpc.MARASAI_STRONG, 10, 2);
        a.addNpcGrunts(ZeonNpcVsNpc.RICKDIAS, 10, 2);
        a.addNpcGrunts(ZeonNpcVsNpc.RICKDIAS_STRONG, 10, 2);
        a.addNpcGrunts(ZeonNpcVsNpc.GEARA_DOGA, 3, 2);
        a.addNpcGrunts(ZeonNpcVsNpc.BLUE_GEARA_DOGA, 3, 2);
         */
    }
}
