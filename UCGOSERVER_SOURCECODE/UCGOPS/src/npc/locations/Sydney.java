package npc.locations;

import npc.*;
import npc.spawn.EFsupply;
import npc.spawn.ZeonBoss;
import npc.spawn.ZeonGrunts;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs utenfor Sydney.
 */
public class Sydney {

    public static void execute() {

        SydneySupply3();

        SydneySupply8();

        SydneySupply9();

        SydCanSupply12();

        SydCanSupply14();

        SydneySupply34();

        SydneyCity();
    }

    private static void SydneyCity() {
        AreaManagerGround a;

        //NPCs utenfor Sydney
        //20x20KM stort med 4KM safe zone i hver retning.
        a = LocalManager.addGroundArea(2, 72533968, -59314154, 40000, 40000, 8000, 8000);
        a.setSafeZone(16000, 16000);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK_WEAK, 10);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU1, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_MG, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_BAZOOKA, 10);
    }

    private static void SydneySupply3() {

        //Sydney supply 3.
        EFsupply.standardGroundSupply(70216537 - 240000, -58268704 + 240000, "Sydney Supply 3A"); //Øverste venstre hjørne
        EFsupply.standardGroundSupply(70216537 + 240000, -58268704 + 240000, "Sydney Supply 3B"); //Øverste høyre hjørne
        EFsupply.standardGroundSupply(70216537 - 240000, -58268704 - 240000, "Sydney Supply 3C"); //Nederste venstre hjørne
        EFsupply.standardGroundSupply(70216537 + 240000, -58268704 - 240000, "Sydney Supply 3D"); //Nederste høyre hjørne

        AreaManagerGround a;

        //NPC område 112X112KM.
        a = LocalManager.addGroundArea(2, 71896537 - 1680000, -58828704 + 560000, 224000, 224000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU_FZ, 150);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 100);
        a.addNpcGrunts(ZeonGrunts.ACGUY_STRONG, 100);
        a.addNpcGrunts(ZeonGrunts.DOM_BEAMBAZOOKA, 50);
        a.addNpcGrunts(ZeonGrunts.AGGUY, 100);
        a.addNpcGrunts(ZeonGrunts.GOGG, 100);
        a.addNpcGrunts(ZeonGrunts.GOGG_STRONG, 100);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 100);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG_SVART, 50);
        a.addNpcGrunts(ZeonGrunts.ZOGOK, 100);
        a.addNpcGrunts(ZeonGrunts.ZOGOK_MG, 100);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 100);
        a.addNpcGrunts(ZeonGrunts.ZGOKE, 100);
        a.addNpcGrunts(ZeonGrunts.ZGOKE_STRONG, 100);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_STRONG, 100);

        //NPC område 96X96KM. Kun spesielle NPCs.
        a = LocalManager.addGroundArea(2, 71896537 - 1680000, -58828704 + 560000, 192000, 192000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZGOKS, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14B, 10);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_EXTRA_STRONG, 10);

        //NPC Bosses
        GlobalManager.spawnNpcBoss(2, ZeonBoss.DOBDAY, "Turing", 71896537 - 1680000, -58828704 + 560000, 80000, 80000, 1);
        GlobalManager.spawnNpcBoss(2, ZeonBoss.GAW_STRONG, "NlogN", 71896537 - 1680000, -58828704 + 560000, 160000, 160000, 1);

    }

    private static void SydneySupply8() {

        //Sydney supply 8.
        EFsupply.standardGroundSupply(71896537 - 160000, -58828704 + 160000, "Sydney Supply 8A"); //Øverste venstre hjørne.
        EFsupply.standardGroundSupply(71896537 + 160000, -58828704, "Sydney Supply 8B"); //Høyre midten.
        EFsupply.standardGroundSupply(71896537, -58828704 - 160000, "Sydney Supply 8C"); //Nederst midten.

        AreaManagerGround a;

        //NPC område, 72x72KM
        a = LocalManager.addGroundArea(2, 72536537 - 640000, -59308704 + 480000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ACGUY_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.AGGUY, 25);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 25);
        a.addNpcGrunts(ZeonGrunts.DOM_CQB, 25);
        a.addNpcGrunts(ZeonGrunts.GOUF_HEAT_SWORD_STRONG, 25);
        a.addNpcGrunts(ZeonGrunts.GOGG_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOKE_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOKE, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.DOM_BEAMBAZOOKA, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_SNIPER, 25);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 100);

        //NPC område, 66x66KM. Kun spesielle Gelgoogs.
        a = LocalManager.addGroundArea(2, 72536537 - 640000, -59308704 + 480000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S_STRONG, 5);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14B, 5);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S, 5);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM_STRONG, 30);

        //NPC Boss
        GlobalManager.spawnNpcBoss(2, ZeonBoss.GALLOP, "Dijkstra", 72536537 - 640000, -59308704 + 480000, 80000, 80000, 1);
    }

    /**
     * Supply 9 er hovedsaklig for nybegynnere.
     *
     * Default supply.
     */
    private static void SydneySupply9() {

        EFsupply.standardGroundSupply(72336263, -59276762, "Sydney Supply 9");

        AreaManagerGround a;

        //NPC område sør for supply, 8x8KM.
        a = LocalManager.addGroundArea(2, 72336263, -59276762 - 26000, 16000, 16000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 30);
        a.addNpcGrunts(ZeonGrunts.ZAKU1, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON, 5);

        //NPC område nord for supply, 16x10KM.  
        a = LocalManager.addGroundArea(2, 72336263, -59276762 + 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 30);
        a.addNpcGrunts(ZeonGrunts.GIGAN, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU1, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_MG, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_BAZOOKA, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_MG, 2);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON, 2);

    }

    private static void SydCanSupply12() {

        //Sydney/Canberra Supply 12
        EFsupply.standardGroundSupply(71256537 - 160000, -59468704 + 160000, "Sydney Supply 12A"); //Øverste venstre hjørne
        EFsupply.standardGroundSupply(71256537 + 160000, -59468704, "Sydney Supply 12B"); //Høyre midten
        EFsupply.standardGroundSupply(71256537, -59468704 - 160000, "Canberra Supply 12");

        AreaManagerGround a;

        //NPC område, 72x72KM
        a = LocalManager.addGroundArea(2, 72536537 - 1280000, -59308704 - 160000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ACGUY_STRONG, 25);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 50);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 75);
        a.addNpcGrunts(ZeonGrunts.ZGOKE_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.GOGG_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MTC, 50);
        a.addNpcGrunts(ZeonGrunts.DOM_CQB, 25);
        a.addNpcGrunts(ZeonGrunts.GOUF_HEAT_SWORD_STRONG, 25);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_SNIPER, 50);
        a.addNpcGrunts(ZeonGrunts.GOGG_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 50);
        a.addNpcGrunts(ZeonGrunts.AGGUY, 50);

        //NPC område, 66x66KM. Kun spesielle NPCs.
        a = LocalManager.addGroundArea(2, 72536537 - 1280000, -59308704 - 160000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14B, 15);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S, 15);
        a.addNpcGrunts(ZeonGrunts.ZGOKS, 40);

    }

    private static void SydCanSupply14() {

        //Sydney/Canberra Supply 14
        EFsupply.standardGroundSupply(72056537 + 160000, -59788704 + 160000, "Sydney Supply 14"); //Høyre øverste hjørne
        EFsupply.standardGroundSupply(72056537 - 160000, -59788704, "Canberra Supply 14A"); //Venstre midten
        EFsupply.standardGroundSupply(72056537 + 160000, -59788704 - 160000, "Canberra Supply 14B"); //Høyre nederste hjørne

        AreaManagerGround a;

        //NPC område, 72x72KM
        a = LocalManager.addGroundArea(2, 72536537 - 480000, -59308704 - 480000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZOGOK, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOK_STRONG_SVART, 50);
        a.addNpcGrunts(ZeonGrunts.DOM_BEAMBAZOOKA, 25);
        a.addNpcGrunts(ZeonGrunts.ACGUY_STRONG, 25);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 50);
        a.addNpcGrunts(ZeonGrunts.GASSHIA, 50);
        a.addNpcGrunts(ZeonGrunts.ZGOKE_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.GOGG_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 50);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_MMP80, 50);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 50);
        a.addNpcGrunts(ZeonGrunts.JUAGG_ROCKET, 50);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 50);

        //NPC område, 66x66KM. Kun spesielle Gelgoogs.
        a = LocalManager.addGroundArea(2, 72536537 - 480000, -59308704 - 480000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_GATO, 20);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14S, 10);

        //NPC Boss.
        GlobalManager.spawnNpcBoss(2, ZeonBoss.FATUNCLE_STRONG, "Rozen", 72536537 - 480000, -59308704 - 480000, 80000, 80000, 1);
    }

    /**
     * Supply 34 for nybegynnere.
     *
     * Default supply.
     */
    private static void SydneySupply34() {

        //Sydney Supply 34
        EFsupply.standardGroundSupply(72735374, -59356874, "Sydney Supply 34");

        AreaManagerGround a;

        //NPC nord for supply, 16x16KM.
        a = LocalManager.addGroundArea(2, 72735374, -59356874 + 42000, 32000, 32000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 25);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK_WEAK, 25);
        a.addNpcGrunts(ZeonGrunts.ZAKU1, 10);

        //NPC område sør for supply, 16x16KM.
        a = LocalManager.addGroundArea(2, 72735374, -59356874 - 42000, 32000, 32000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 25);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK_WEAK, 25);
        a.addNpcGrunts(ZeonGrunts.GIGAN, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_MG, 5);
    }
}
