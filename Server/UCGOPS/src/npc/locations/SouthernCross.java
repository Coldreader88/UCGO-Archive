package npc.locations;

import npc.AreaManagerGround;
import npc.GlobalManager;
import npc.LocalManager;
import npc.spawn.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs utenfor Southern Cross.
 */
public class SouthernCross {

    public static void execute() {

        SCsupply4();
        SCsupply5();
        SCsupply8();
        SCsupply9();
        SCsupply13();
        SCsupply14();
        SCsupply16();
        SCsupply17();
        SCsupply19();
        SCsupply21();
        SCsupply25();
        SCsupply32();
        SCsupply35();
        SCsupply37();
        SCsupply41();
        SCsupply42();
        SCsupply44();
        SCsupply47();

        scCity();
    }

    /**
     * Definerer NPCs utenfor Southern Cross.
     */
    private static void scCity() {
        //NPC vs NPC område utenfor byen. 20x20KM stort med 4KM safe zone.
        AreaManagerGround a = LocalManager.addNpcBattlefieldGroundArea(57287961, -58206393, 40000, 40000, 8000, 8000);
        a.setSafeZone(16000, 16000);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 20, 2);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_MG, 20, 2);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 5, 2);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 10, 2);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 10, 2);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_MG, 20, 2);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_GB, 5, 2);
        a.addNpcGrunts(ZeonGrunts.ZAKU_FZ, 5, 2);

        a.addNpcGrunts(EFgrunts.GMCANNON, 10, 1);
        a.addNpcGrunts(EFgrunts.GMLA_MG, 10, 1);
        a.addNpcGrunts(EFgrunts.COLDTYPE_BEAM_STRONG, 20, 1);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_180MM, 10, 1);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 10, 1);
        a.addNpcGrunts(EFgrunts.GM79_BSG_SHIELD, 20, 1);
    }

    /**
     * Definerer NPCs rundt SC supply 4.
     */
    private static void SCsupply4() {

        //SC Supply 4
        EFsupply.standardGroundSupply(57289989 + 800000, -58213606, "Southern Cross Supply 4A"); //Nedre venstre hjørne
        EFsupply.standardGroundSupply(57289989 + 960000, -58213606 + 320000, "Southern Cross Supply 4B"); //Top midten

        AreaManagerGround a;

        //NPC område 72x72KM.
        a = LocalManager.addGroundArea(2, 57289989 + 960000, -58213606 + 160000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 40);
        a.addNpcGrunts(ZeonGrunts.GOUF_GB, 50);
        a.addNpcGrunts(ZeonGrunts.GOUF_HEAT_SWORD_STRONG, 5);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_SNIPER, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 60);
        a.addNpcGrunts(ZeonGrunts.GOGG, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 50);
        a.addNpcGrunts(ZeonGrunts.DOM_BEAMBAZOOKA, 40);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 60);
        a.addNpcGrunts(ZeonGrunts.DOM_FAST_MG, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 50);

        //NPC område 66x66KM. Inneholder kun spesielle Gelgoogs.
        a = LocalManager.addGroundArea(2, 57289989 + 960000, -58213606 + 160000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_EXTRA_STRONG, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_GATO, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S, 20);

        //NPC Bosses
        GlobalManager.spawnNpcBoss(2, ZeonBoss.FATUNCLE, "Santa", 57289989 + 960000 - 40000, -58213606 + 160000, 80000, 80000, 1);
        GlobalManager.spawnNpcBoss(2, ZeonBoss.GAW, "Salamander", 57289989 + 960000 + 120000, -58213606 + 160000, 40000, 120000, 1);
    }

    /**
     * Definerer NPCs rundt SC supply 5.
     */
    private static void SCsupply5() {

        //SC Supply 5
        EFsupply.standardGroundSupply(57289989 + 120000, -58213606 + 880000, "Southern Cross Supply 5");

        AreaManagerGround a;

        //NPC område nord for supply, 20KMx20KM.
        a = LocalManager.addGroundArea(2, 57409989, -57333606 + 52000, 40000, 40000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 30);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 30);
        a.addNpcGrunts(ZeonGrunts.ZGOKE, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 20);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 30);
        a.addNpcGrunts(ZeonGrunts.DOM_BEAMBAZOOKA, 30);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 30);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG, 20);
        a.addNpcGrunts(ZeonGrunts.GOGG, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM_STRONG, 40);

        //NPC område sør for supply, 20KMx20KM.
        a = LocalManager.addGroundArea(2, 57409989, -57333606 - 52000, 40000, 40000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.DOM_BEAMBAZOOKA, 20);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 30);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG, 20);
        a.addNpcGrunts(ZeonGrunts.GOGG, 20);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 30);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 30);
        a.addNpcGrunts(ZeonGrunts.ZGOKE, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM_STRONG, 20);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG_SVART, 20);
        a.addNpcGrunts(ZeonGrunts.DOM_CQB, 30);
    }

    /**
     * Definerer NPCs rundt SC supply 8.
     */
    private static void SCsupply8() {

        //SC Supply 8
        EFsupply.standardGroundSupply(57289989 + 800000, -58213606 - 480000, "Southern Cross Supply 8A"); //Øverste venstre hjørne
        EFsupply.standardGroundSupply(57289989 + 800000, -58213606 - 800000, "Southern Cross Supply 8B"); //Venstre midten
        EFsupply.standardGroundSupply(57289989 + 1120000, -58213606 - 480000, "Southern Cross Supply 8C"); //Top midten
        EFsupply.standardGroundSupply(57289989 + 960000, -58213606 - 960000, "Southern Cross Supply 8D"); //Bunn midten

        AreaManagerGround a;

        //NPC område 112X112KM.
        a = LocalManager.addGroundArea(2, 57289989 + 1040000, -58213606 - 720000, 224000, 224000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GOUF_GB, 200);
        a.addNpcGrunts(ZeonGrunts.DOM_FAST_MG, 200);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 200);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 200);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 200);
        a.addNpcGrunts(ZeonGrunts.DOM_CQB, 15);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 200);
        a.addNpcGrunts(ZeonGrunts.GIGAN, 200);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 200);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 200);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 200);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 200);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 600);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 200);

        //NPC Bosses.
        GlobalManager.spawnNpcBoss(2, ZeonBoss.GAW, "HotDog", 57289989 + 1040000 - 80000, -58213606 - 720000, 80000, 160000, 1);
        GlobalManager.spawnNpcBoss(2, ZeonBoss.GAW_STRONG, "Cake", 57289989 + 1040000 + 120000, -58213606 - 720000 - 80000, 80000, 120000, 1);
        GlobalManager.spawnNpcBoss(2, ZeonBoss.FATUNCLE_STRONG, "Salamander", 57289989 + 1040000, -58213606 - 720000, 120000, 80000, 1);
    }

    /**
     * Definerer NPCs rundt SC supply 9.
     */
    private static void SCsupply9() {

        //SC Supply 9
        EFsupply.standardGroundSupply(57289989 - 1120000, -58213606 - 800000, "Southern Cross Supply 9A"); //Top midten
        EFsupply.standardGroundSupply(57289989 - 960000, -58213606 - 960000, "Southern Cross Supply 9B"); //Høyre midten
        EFsupply.standardGroundSupply(57289989 - 1120000, -58213606 - 1120000, "Southern Cross Supply 9C"); //Bunn midten

        AreaManagerGround a;

        //NPC område 72x72KM.
        a = LocalManager.addGroundArea(2, 57289989 - 1120000, -58213606 - 960000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 100);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 100);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 100);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 100);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 100);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 100);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 100);
        a.addNpcGrunts(ZeonGrunts.GOUF_GB, 100);
        a.addNpcGrunts(ZeonGrunts.DOM_BEAMBAZOOKA, 100);
        a.addNpcGrunts(ZeonGrunts.GOUF_HEAT_SWORD, 100);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 300);
    }

    /**
     * Definerer NPCs rundt SC supply 13.
     */
    private static void SCsupply13() {

        //SC Supply 13
        EFsupply.standardGroundSupply(57289989 - 640000, -58213606 + 480000, "Southern Cross Supply 13A"); //Venstre midten
        EFsupply.standardGroundSupply(57289989 - 320000, -58213606 + 640000, "Southern Cross Supply 13B"); //Øverste høyre hjørne
        EFsupply.standardGroundSupply(57289989 - 320000, -58213606 + 320000, "Southern Cross Supply 13C"); //Nedre høyre hjørne

        AreaManagerGround a;

        //NPC område 72x72KM.
        a = LocalManager.addGroundArea(2, 57289989 - 480000, -58213606 + 480000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 50);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 40);
        a.addNpcGrunts(ZeonGrunts.GOUF_GB, 50);
        a.addNpcGrunts(ZeonGrunts.GOUF_HEAT_SWORD_STRONG, 5);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_SNIPER, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 60);
        a.addNpcGrunts(ZeonGrunts.GOGG, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 50);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 40);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 60);
        a.addNpcGrunts(ZeonGrunts.DOM_FAST_MG, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOKE, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 25);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 50);

        //NPC område 66x66KM. Inneholder kun spesielle Gelgoogs.
        a = LocalManager.addGroundArea(2, 57289989 - 480000, -58213606 + 480000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_GATO, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S, 3);

        //NPC Bosses
        GlobalManager.spawnNpcBoss(2, ZeonBoss.GAW_STRONG, "Spider", 57289989 - 480000, -58213606 + 480000 + 80000, 80000, 40000, 1);
        GlobalManager.spawnNpcBoss(2, ZeonBoss.FATUNCLE_STRONG, "Gnome", 57289989 - 480000 - 40000, -58213606 + 480000 - 80000, 40000, 80000, 1);
        GlobalManager.spawnNpcBoss(2, ZeonBoss.GAW, "Zorro", 57289989 - 480000 + 120000, -58213606 + 480000 - 40000, 80000, 40000, 1);
    }

    /**
     * Definerer NPCs rundt SC supply 14.
     */
    private static void SCsupply14() {

        //SC Supply 14
        EFsupply.standardGroundSupply(57289989 - 1120000, -58213606, "Southern Cross Supply 14A"); //Bunn midten
        EFsupply.standardGroundSupply(57289989 - 1120000, -58213606 + 320000, "Southern Cross Supply 14B"); //Top midten
        EFsupply.standardGroundSupply(57289989 - 960000, -58213606 + 320000, "Southern Cross Supply 14C"); //Øverste høyre hjørne

        AreaManagerGround a;

        //NPC område 72x72KM.
        a = LocalManager.addGroundArea(2, 57289989 - 1120000, -58213606 + 160000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 80);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 100);
        a.addNpcGrunts(ZeonGrunts.GOUF_GB, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 60);
        a.addNpcGrunts(ZeonGrunts.GOGG, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM, 50);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 140);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 60);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 100);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 50);

        //NPC område 66x66KM. Inneholder kun spesielle Gelgoogs.
        a = LocalManager.addGroundArea(2, 57289989 - 1120000, -58213606 + 160000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_EXTRA_STRONG, 30);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S, 5);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S_STRONG, 3);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14B, 3);
    }

    /**
     * Definerer NPCs rundt SC supply 16.
     */
    private static void SCsupply16() {

        //SC Supply 16
        EFsupply.standardGroundSupply(57289989 + 560000, -58213606 + 400000, "Southern Cross Supply 16");

        AreaManagerGround a;

        //NPC område nord for supply, 20KMx20KM.
        a = LocalManager.addGroundArea(2, 57849989, -57813606 + 52000, 40000, 40000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 20);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 20);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 40);
    }

    /**
     * Definerer NPCs rundt SC supply 17.
     */
    private static void SCsupply17() {

        //SC Supply 17
        EFsupply.standardGroundSupply(57289989 + 640000, -58213606 + 960000, "Southern Cross Supply 17A"); //Venstre midten
        EFsupply.standardGroundSupply(57289989 + 800000, -58213606 + 800000, "Southern Cross Supply 17B"); //Bunn midten
        EFsupply.standardGroundSupply(57289989 + 960000, -58213606 + 1120000, "Southern Cross Supply 17C"); //Øverste høyre hjørne

        AreaManagerGround a;

        //NPC område 72x72KM.
        a = LocalManager.addGroundArea(2, 57289989 + 800000, -58213606 + 960000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 40);
        a.addNpcGrunts(ZeonGrunts.GOUF_GB, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 60);
        a.addNpcGrunts(ZeonGrunts.JUAGG_ROCKET, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 50);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 40);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 60);
        a.addNpcGrunts(ZeonGrunts.DOM_FAST_MG, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOKE, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 50);

        //NPC område 66x66KM. Inneholder kun spesielle Gelgoogs.
        a = LocalManager.addGroundArea(2, 57289989 + 800000, -58213606 + 960000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM_STRONG, 100);

    }

    /**
     * Definerer NPCs rundt SC supply 19.
     */
    private static void SCsupply19() {

        //SC Supply 19
        EFsupply.standardGroundSupply(57289989 + 560000, -58213606 - 240000, "Southern Cross Supply 19");

        AreaManagerGround a;

        //NPC område vest for supply, 20KMx20KM.
        a = LocalManager.addGroundArea(2, 56729989 - 52000, -58293606, 40000, 40000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZGOKE, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.GOUF_HEAT_SWORD, 10);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 20);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 20);
        a.addNpcGrunts(ZeonGrunts.GOUF_GB, 20);

        //NPC område øst for supply, 20KMx20KM.
        a = LocalManager.addGroundArea(2, 56729989 + 52000, -58293606, 40000, 40000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZGOKE, 20);
        a.addNpcGrunts(ZeonGrunts.DOM_FAST_MG, 10);
        a.addNpcGrunts(ZeonGrunts.DOM_BEAMBAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 20);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 20);
        a.addNpcGrunts(ZeonGrunts.GOUF_GB, 20);
    }

    /**
     * Definerer NPCs rundt SC supply 21.
     */
    private static void SCsupply21() {

        //SC Supply 21
        EFsupply.standardGroundSupply(57289989 - 880000, -58213606 + 880000, "Southern Cross Supply 21");

        AreaManagerGround a;

        //NPC område nord for supply, 20KMx20KM.
        a = LocalManager.addGroundArea(2, 556409989, -57333606 + 52000, 40000, 40000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 25);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 25);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 10);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 10);

        //NPC område sør for supply, 20KMx20KM.
        a = LocalManager.addGroundArea(2, 556409989, -57333606 - 52000, 40000, 40000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 25);
        a.addNpcGrunts(ZeonGrunts.DOM_FAST_MG, 25);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 20);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 10);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 10);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 10);
    }

    /**
     * Definerer NPCs rundt SC supply 25.
     */
    private static void SCsupply25() {

        //SC Supply 25
        EFsupply.standardGroundSupply(57289989 + 400000, -58213606 - 560000, "Southern Cross Supply 25");

        AreaManagerGround a;

        //NPC område vest for supply, 20KMx20KM.
        a = LocalManager.addGroundArea(2, 56729989 - 52000, -58293606, 40000, 40000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 20);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 40);
        a.addNpcGrunts(ZeonGrunts.DOM_BEAMBAZOOKA, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 20);
        a.addNpcGrunts(ZeonGrunts.GIGAN, 30);
        a.addNpcGrunts(ZeonGrunts.ZGOKS, 10);
    }

    /**
     * Definerer NPCs rundt SC supply 32.
     */
    private static void SCsupply32() {

        //SC Supply 32
        EFsupply.standardGroundSupply(57289989 - 400000, -58213606 + 1040000, "Southern Cross Supply 32");

        AreaManagerGround a;

        //NPC område sør-øst for supply, 40KMx40KM.
        a = LocalManager.addGroundArea(2, 56889989 + 50000, -57173606 - 50000, 80000, 80000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 50);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 50);
        a.addNpcGrunts(ZeonGrunts.DOM_FAST_MG, 40);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 50);
        a.addNpcGrunts(ZeonGrunts.GOGG, 40);
        a.addNpcGrunts(ZeonGrunts.JUAGG_ROCKET, 40);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_MTC, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG, 20);
        //Område for spesielle NPCs.
        a = LocalManager.addGroundArea(2, 56889989 + 50000, -57173606 - 50000, 60000, 60000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S, 4);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM_STRONG, 15);

    }

    /**
     * Definerer NPCs rundt SC supply 35.
     */
    private static void SCsupply35() {

        //SC Supply 35
        EFsupply.standardGroundSupply(57289989 - 480000, -58213606 - 800000, "Southern Cross Supply 35A"); //Top midten
        EFsupply.standardGroundSupply(57289989 - 800000, -58213606 - 960000, "Southern Cross Supply 35B"); //Venstre midten
        EFsupply.standardGroundSupply(57289989 - 800000, -58213606 - 1280000, "Southern Cross Supply 35C"); //Nederste venstre hjørne
        EFsupply.standardGroundSupply(57289989 - 320000, -58213606 - 960000, "Southern Cross Supply 35D"); //Høyre midten
        EFsupply.standardGroundSupply(57289989 - 320000, -58213606 - 1280000, "Southern Cross Supply 35E"); //Nederste høyre hjørne

        AreaManagerGround a;

        //NPC område 112X112KM.
        a = LocalManager.addGroundArea(2, 57289989 - 560000, -58213606 - 1040000, 224000, 224000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GOUF_GB, 200);
        a.addNpcGrunts(ZeonGrunts.DOM_FAST_MG, 200);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 200);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 200);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 200);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 200);
        a.addNpcGrunts(ZeonGrunts.GIGAN, 200);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 200);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 200);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 200);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 200);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 600);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 200);
        a.addNpcGrunts(ZeonGrunts.ZGOKS, 20);

    }

    /**
     * Definerer NPCs rundt SC supply 37.
     */
    private static void SCsupply37() {

        //SC Supply 37
        EFsupply.standardGroundSupply(57289989, -58213606 - 800000, "Southern Cross Supply 37A"); //Top midten
        EFsupply.standardGroundSupply(57289989, -58213606 - 1120000, "Southern Cross Supply 37B"); //Bunn midten
        EFsupply.standardGroundSupply(57289989 - 160000, -58213606 - 1120000, "Southern Cross Supply 37C"); //Nederste venstre hjørne
        EFsupply.standardGroundSupply(57289989 + 160000, -58213606 - 960000, "Southern Cross Supply 37D"); //Høyre midten

        AreaManagerGround a;

        //NPC område 72x72KM.
        a = LocalManager.addGroundArea(2, 57289989, -58213606 - 960000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 100);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 100);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 100);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 100);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 100);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 100);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 100);
        a.addNpcGrunts(ZeonGrunts.GOUF_GB, 100);
        a.addNpcGrunts(ZeonGrunts.DOM_BEAMBAZOOKA, 100);
        a.addNpcGrunts(ZeonGrunts.GOGG, 100);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 300);

        //Område med spesielle NPCs.
        a = LocalManager.addGroundArea(2, 57289989, -58213606 - 960000, 114000, 114000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S, 5);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S_STRONG, 10);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14B, 5);

    }

    /**
     * Definerer NPCs rundt SC supply 41.
     */
    private static void SCsupply41() {

        //SC Supply 41
        EFsupply.standardGroundSupply(57289989 - 800000, -58213606 - 320000, "Southern Cross Supply 41A"); //Øverste venstre hjørne
        EFsupply.standardGroundSupply(57289989 - 800000, -58213606 - 640000, "Southern Cross Supply 41B"); //Nederste venstre hjørne
        EFsupply.standardGroundSupply(57289989 - 480000, -58213606 - 320000, "Southern Cross Supply 41C"); //Øverste høyre hjørne
        EFsupply.standardGroundSupply(57289989 - 480000, -58213606 - 480000, "Southern Cross Supply 41D"); //Høyre midten

        AreaManagerGround a;

        //NPC område 72x72KM.
        a = LocalManager.addGroundArea(2, 57289989 - 640000, -58213606 - 480000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 80);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 100);
        a.addNpcGrunts(ZeonGrunts.GOUF_GB, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_SNIPER, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 60);
        a.addNpcGrunts(ZeonGrunts.GOGG, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOKE, 50);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 140);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 60);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 100);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 50);

        //NPC område 66x66KM. Inneholder kun spesielle Gelgoogs.
        a = LocalManager.addGroundArea(2, 57289989 - 640000, -58213606 - 480000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S, 6);

        //NPC Bosses.
        GlobalManager.spawnNpcBoss(2, ZeonBoss.GAW_STRONG, "Adam West", 57289989 - 640000 + 40000, -58213606 - 480000 - 40000, 40000, 80000, 1);
        GlobalManager.spawnNpcBoss(2, ZeonBoss.FATUNCLE_STRONG, "Atari", 57289989 - 640000 + 40000, -58213606 - 480000, 80000, 80000, 1);
    }

    /**
     * Definerer NPCs rundt SC supply 42.
     */
    private static void SCsupply42() {

        //SC Supply 42
        EFsupply.standardGroundSupply(57370489, -58403305, "Southern Cross Supply 42");

        AreaManagerGround a;

        //NPC område nord for supply, 16x10KM. Litt vanskelig.
        a = LocalManager.addGroundArea(2, 57370489, -58403305 + 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_FAST_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_BAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 10);

        //NPC område sør for supply, 16x10KM. Litt vanskelig.
        a = LocalManager.addGroundArea(2, 57370489, -58403305 - 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 5);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 10);

    }

    /**
     * Definerer NPCs rundt SC supply 44.
     */
    private static void SCsupply44() {

        //SC Supply 44
        EFsupply.standardGroundSupply(57090219, -58122459, "Southern Cross Supply 44");

        AreaManagerGround a;

        //NPC område nord for supply, 16x10KM. Enkelt.
        a = LocalManager.addGroundArea(2, 57090219, -58122459 + 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 30);
        a.addNpcGrunts(ZeonGrunts.ZAKU1, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_BAZOOKA, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_FAST_MG, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON, 5);

        //NPC område sør for supply, 16x10KM. Litt vanskelig.
        a = LocalManager.addGroundArea(2, 57090219, -58122459 - 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 5);
        a.addNpcGrunts(ZeonGrunts.DOM_FAST_MG, 10);

    }

    /**
     * Definerer NPCs rundt SC supply 47.
     */
    private static void SCsupply47() {

        //SC Supply 47
        EFsupply.standardGroundSupply(57289989 - 560000, -58213606 - 80000, "Southern Cross Supply 47");

        AreaManagerGround a;

        //NPC område nord for supply, 20KMx20KM.
        a = LocalManager.addGroundArea(2, 56729989, -58293606 + 52000, 40000, 40000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 80);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 30);

    }
}
