package npc.locations;

import npc.*;
import npc.spawn.EFsupply;
import npc.spawn.ZeonBoss;
import npc.spawn.ZeonGrunts;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs utenfor Canberra.
 */
public class Canberra {

    public static void execute() {

        CanberraSupply5();

        CanberraSupply23();

        CanberraSupply24();
    }

    private static void CanberraSupply5() {

        //Canberra supply 5.
        EFsupply.standardGroundSupply(70772874 - 160000, -60258021, "Canberra Supply 5A"); //Venstre midten
        EFsupply.standardGroundSupply(70772874 + 160000, -60258021 + 160000, "Canberra Supply 5B"); //Høyre øverste hjørne
        EFsupply.standardGroundSupply(70772874 + 160000, -60258021 - 160000, "Canberra Supply 5C"); //Nederste høyre hjørne

        AreaManagerGround a;

        //NPC område, 72x72KM
        a = LocalManager.addGroundArea(2, 71572874 - 800000, -60098021 - 160000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.DOM_CQB, 25);
        a.addNpcGrunts(ZeonGrunts.AGGUY, 25);
        a.addNpcGrunts(ZeonGrunts.GOUF_HEAT_SWORD_STRONG, 25);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_SNIPER, 25);
        a.addNpcGrunts(ZeonGrunts.GOGG_STRONG, 25);
        a.addNpcGrunts(ZeonGrunts.GOGG, 75);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 100);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG_SVART, 50);
        a.addNpcGrunts(ZeonGrunts.ACGUY_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 50);
        a.addNpcGrunts(ZeonGrunts.JUAGG_ROCKET, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 50);
        a.addNpcGrunts(ZeonGrunts.ZOGOK, 50);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 50);

        //Kun spesielle NPCs.
        a = LocalManager.addGroundArea(2, 71572874 - 800000, -60098021 - 160000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14B, 5);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S_STRONG, 5);
        a.addNpcGrunts(ZeonGrunts.ZGOKS, 50);

        //NPC Boss.
        GlobalManager.spawnNpcBoss(2, ZeonBoss.GALLOP, "Cappuccino", 71572874 - 800000, -60098021 - 160000, 80000, 80000, 1);
    }

    /**
     * Definerer NPCs rundt supply 23.
     */
    private static void CanberraSupply23() {

        //Canberra Supply 23
        EFsupply.standardGroundSupply(71766982, -59995714, "Canberra Supply 23");

        AreaManagerGround a;

        //NPC område nord for supply, 16x10KM. Beregnet for begynnere.
        a = LocalManager.addGroundArea(2, 71766982, -59995714 + 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU1, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_BAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_MG, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON, 5);

        //NPC område sør for supply, 16x10KM. For litt mer enn begynnere.
        a = LocalManager.addGroundArea(2, 71766982, -59995714 - 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU1, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 15);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 5);

    }

    /**
     * Definerer NPCs rundt supply 24.
     */
    private static void CanberraSupply24() {

        //Canberra Supply 24
        EFsupply.standardGroundSupply(71365855, -60155773, "Canberra Supply 24");

        AreaManagerGround a;

        //NPC område nord for supply, 16x10KM. Litt vanskelig.
        a = LocalManager.addGroundArea(2, 71365855, -60155773 + 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 10);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_BAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 5);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 10);

        //NPC område sør for supply, 16x10KM. Litt vanskelig.
        a = LocalManager.addGroundArea(2, 71365855, -60155773 - 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 10);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_BAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 20);
        a.addNpcGrunts(ZeonGrunts.JUAGG_ROCKET, 20);

    }

}
