package npc.locations;

import npc.AreaManagerGround;
import npc.GlobalManager;
import npc.LocalManager;
import npc.spawn.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs utenfor Brisbane.
 */
public class Brisbane {

    public static void execute() {

        BrisbaneSupply5();
        BrisbaneSupply7();
        BrisbaneSupply12();
        BrisbaneSupply13();
        BrisbaneSupply16();
        BrisbaneSupply18();
        BrisbaneSupply19();
        BrisbaneSupply23();
        BrisbaneSupply25();
        BrisbaneSupply26();
        BrisbaneSupply27();
        BrisbaneSupply31();
        BrisbaneSupply34();
        BrisbaneSupply35();
        BrisbaneSupply39();
        BrisbaneSupply40();
    }

    private static void BrisbaneSupply5() {

        //Brisbane supply 5
        ZeonSupply.standardGroundSupply(73308333, -56086596, "Brisbane Supply 5");

        AreaManagerGround a;

        //NPC område nord for supply, 16x10KM. Vanskelig.
        a = LocalManager.addGroundArea(1, 73308333, -56086596 + 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.COLDTYPE_FAST_MG, 25);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 10);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 5);
        a.addNpcGrunts(EFgrunts.GUNCANNON_MP, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_180MM, 5);

        //NPC område sør for supply, 16x10KM. Litt vanskelig.
        a = LocalManager.addGroundArea(1, 73308333, -56086596 - 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.COLDTYPE_BEAM, 20);
        a.addNpcGrunts(EFgrunts.GUNCANNON_MP, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 10);
        a.addNpcGrunts(EFgrunts.GM79_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 5);

    }

    private static void BrisbaneSupply7() {

        //Brisbane supply 7
        ZeonSupply.standardGroundSupply(73447582 + 160000, -56285669 + 480000, "Brisbane Supply 7");

        AreaManagerGround a;

        //NPC område plassert nord vest for supply. 40x40KM.
        a = LocalManager.addGroundArea(1, 73447582 + 160000 - 100000, -56285669 + 480000 + 100000, 80000, 80000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 40);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 40);
        a.addNpcGrunts(EFgrunts.GUNCANNON2, 30);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_180MM, 40);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG, 60);
        a.addNpcGrunts(EFgrunts.ZANNY, 100);
        a.addNpcGrunts(EFgrunts.RMV1, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_MG, 40);

    }

    private static void BrisbaneSupply12() {

        //Brisbane supply 12
        ZeonSupply.standardGroundSupply(73447582 - 640000, -56285669 + 800000, "Brisbane Supply 12A");
        ZeonSupply.standardGroundSupply(73447582 - 320000, -56285669 + 640000, "Brisbane Supply 12B");

        AreaManagerGround a;
        //NPC område mellom supplies, 72x72KM.
        a = LocalManager.addGroundArea(1, 73447582 - 480000, -56285669 + 800000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMRIFLE, 100);
        a.addNpcGrunts(EFgrunts.RMV1, 100);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_180MM, 50);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GMSNIPER2, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY_BR, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 50);
        a.addNpcGrunts(EFgrunts.ZANNY, 100);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER_STRONG, 15);

        //Område for spesielle NPCs.
        a = LocalManager.addGroundArea(1, 73447582 - 480000, -56285669 + 800000, 128000, 128000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_BEAM_STRONG, 40);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM, 5);

        //NPC Bosses
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA_GRAY_STRONG, "Torpedo", 73447582 - 480000, -56285669 + 800000 + 120000, 80000, 40000, 1);
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA_STRONG, "Tortilla", 73447582 - 480000, -56285669 + 800000 - 40000, 80000, 80000, 1);
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA_GRAY, "Taco", 73447582 - 480000 + 40000, -56285669 + 800000 - 40000, 80000, 40000, 1);
    }

    private static void BrisbaneSupply13() {

        //Brisbane supply 13
        ZeonSupply.standardGroundSupply(73447582 - 1120000, -56285669 - 800000, "Brisbane Supply 13A");
        ZeonSupply.standardGroundSupply(73447582 - 960000, -56285669 - 960000, "Brisbane Supply 13B");

        AreaManagerGround a;

        //NPC område mellom supplies, 72x72KM.
        a = LocalManager.addGroundArea(1, 73447582 - 1120000, -56285669 - 960000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMRIFLE, 50);
        a.addNpcGrunts(EFgrunts.RMV1, 50);
        a.addNpcGrunts(EFgrunts.GM79_BLACK, 50);
        a.addNpcGrunts(EFgrunts.GMHEAD_MG, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON2, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 100);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GMHEAD_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 50);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_MG, 100);

    }

    private static void BrisbaneSupply16() {

        //Brisbane supply 16
        ZeonSupply.standardGroundSupply(73447582 - 960000, -56285669 + 640000, "Brisbane Supply 16");

        AreaManagerGround a;

        //NPC område plassert nord for supply. 40x40KM.
        a = LocalManager.addGroundArea(1, 73447582 - 960000, -56285669 + 640000 + 100000, 80000, 80000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM79_BLACK, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY_BR, 40);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_180MM, 40);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GMHEAD_MG, 50);
        a.addNpcGrunts(EFgrunts.GMSNIPER2, 20);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 50);
        a.addNpcGrunts(EFgrunts.COLDTYPE_FAST_MG, 50);
        //NPC område med "spesielle" NPCs.
        a = LocalManager.addGroundArea(1, 73447582 - 960000, -56285669 + 640000 + 100000, 60000, 60000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.EZ8_STRONG, 10);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_BEAM_STRONG, 20);

    }

    private static void BrisbaneSupply18() {

        //Brisbane supply 18
        ZeonSupply.standardGroundSupply(73447582 - 640000, -56285669 - 480000, "Brisbane Supply 18");

        AreaManagerGround a;

        //NPC område plassert vest for supply. 40x40KM.
        a = LocalManager.addGroundArea(1, 73447582 - 640000 - 100000, -56285669 - 480000, 80000, 80000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM79_BLACK, 30);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 30);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 40);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY_BR, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 40);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM_STRONG, 10);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_MG, 50);
        //Område med spesielle NPCs.
        a = LocalManager.addGroundArea(1, 73447582 - 640000 - 100000, -56285669 - 480000, 60000, 60000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.EZ8_STRONG, 10);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM, 4);

    }

    private static void BrisbaneSupply19() {

        //Brisbane supply 19
        ZeonSupply.standardGroundSupply(73447582 - 160000, -56285669 + 1120000, "Brisbane Supply 19A");
        ZeonSupply.standardGroundSupply(73447582, -56285669 + 960000, "Brisbane Supply 19B");

        AreaManagerGround a;

        //NPC område plassert mellom supplies. 72x72KM.
        a = LocalManager.addGroundArea(1, 73447582, -56285669 + 1120000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_180MM, 40);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 60);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_180MM, 30);
        a.addNpcGrunts(EFgrunts.ZANNY, 70);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY_BR, 50);
        a.addNpcGrunts(EFgrunts.GMSNIPER2, 40);
        a.addNpcGrunts(EFgrunts.GM79_BLACK, 60);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_MG, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 100);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER, 20);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 80);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER_STRONG, 10);
        a.addNpcGrunts(EFgrunts.GMSNIPER_CUSTOM, 10);

        //Spesielle NPC plasser i området.
        a = LocalManager.addGroundArea(1, 73447582, -56285669 + 1120000, 128000, 128000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_BEAM_STRONG, 30);
        a.addNpcGrunts(EFgrunts.EZ8_STRONG, 10);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM, 3);

        //NPC Bosses
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA_STRONG, "Water", 73447582 + 80000, -56285669 + 1120000 - 40000, 40000, 40000, 1);
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA_GRAY_STRONG, "Tentacle", 73447582, -56285669 + 1120000 + 120000, 80000, 40000, 1);
    }

    private static void BrisbaneSupply23() {

        //Brisbane supply 23
        ZeonSupply.standardGroundSupply(73447582 - 1120000, -56285669 + 960000, "Brisbane Supply 23A");
        ZeonSupply.standardGroundSupply(73447582 - 960000, -56285669 + 960000, "Brisbane Supply 23B");

        AreaManagerGround a;

        //NPC område mellom supplies, 72x72KM.
        a = LocalManager.addGroundArea(1, 73447582 - 1120000, -56285669 + 1120000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMRIFLE, 100);
        a.addNpcGrunts(EFgrunts.RMV1, 100);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_180MM, 50);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GMSNIPER2, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY_BR, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 50);
        a.addNpcGrunts(EFgrunts.ZANNY, 100);

        //NPC område med spesielle NPCs.
        a = LocalManager.addGroundArea(1, 73447582 - 1120000, -56285669 + 1120000, 114000, 114000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.PROTOTYPE_GUNDAM, 10);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM_STRONG, 10);
    }

    private static void BrisbaneSupply25() {

        //Brisbane supply 25
        ZeonSupply.standardGroundSupply(73447582 - 320000, -56285669 - 160000, "Brisbane Supply 25");

        AreaManagerGround a;

        //NPC område plassert sør-vest for supply. 40x40KM.
        a = LocalManager.addGroundArea(1, 73447582 - 320000 - 100000, -56285669 - 160000 - 100000, 80000, 80000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.ZANNY, 100);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_180MM, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG, 50);
    }

    private static void BrisbaneSupply26() {

        //Brisbane supply 26
        ZeonSupply.standardGroundSupply(73447582 - 640000, -56285669 - 1120000, "Brisbane Supply 26A"); //Venstre midten
        ZeonSupply.standardGroundSupply(73447582 - 480000, -56285669 - 800000, "Brisbane Supply 26B"); //Top midten
        ZeonSupply.standardGroundSupply(73447582 - 160000, -56285669 - 800000, "Brisbane Supply 26C"); //Øverste høyre hjørne
        ZeonSupply.standardGroundSupply(73447582 - 160000, -56285669 - 1120000, "Brisbane Supply 26D"); //Høyre midten

        AreaManagerGround a;

        //NPC område mellom supplies, 112x112KM.
        a = LocalManager.addGroundArea(1, 73447582 - 400000, -56285669 - 1040000, 224000, 224000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 200);
        a.addNpcGrunts(EFgrunts.GM79_BLACK, 200);
        a.addNpcGrunts(EFgrunts.ZANNY, 400);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMRIFLE, 600);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY_BR, 200);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM, 200);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_180MM, 200);
        a.addNpcGrunts(EFgrunts.GMSNIPER2, 100);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 100);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_MG, 200);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_180MM, 100);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 200);

        //Område med spesielle NPCs.
        a = LocalManager.addGroundArea(1, 73447582 - 400000, -56285669 - 1040000, 204000, 204000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.EZ8_STRONG, 40);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_BEAM_STRONG, 20);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM, 10);
        a.addNpcGrunts(EFgrunts.PROTOTYPE_GUNDAM, 10);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM_STRONG, 10);

    }

    private static void BrisbaneSupply27() {

        //Brisbane supply 27
        ZeonSupply.standardGroundSupply(73447582 - 800000, -56285669 + 320000, "Brisbane Supply 27A");
        ZeonSupply.standardGroundSupply(73447582 - 640000, -56285669 + 160000, "Brisbane Supply 27B");

        AreaManagerGround a;

        //NPC område mellom supplies, 72x72KM.
        a = LocalManager.addGroundArea(1, 73447582 - 800000, -56285669 + 160000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMRIFLE, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_MG, 100);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG, 100);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_180MM, 50);
        a.addNpcGrunts(EFgrunts.GMHEAD_BEAM, 50);
        a.addNpcGrunts(EFgrunts.COLDTYPE_FAST_MG, 50);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY_BR, 100);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER, 10);
        a.addNpcGrunts(EFgrunts.COLDTYPE_BEAM, 50);
        a.addNpcGrunts(EFgrunts.ZANNY, 50);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER_STRONG, 10);
        a.addNpcGrunts(EFgrunts.GMSNIPER_CUSTOM, 5);

        //Område med spesielle NPCs.
        a = LocalManager.addGroundArea(1, 73447582 - 800000, -56285669 + 160000, 114000, 114000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.EZ8, 20);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM, 4);

        //NPC Bosses
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA, "Bug", 73447582 - 800000 - 80000, -56285669 + 160000 + 40000, 80000, 80000, 1);
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA_GRAY_STRONG, "Maggot", 73447582 - 800000 + 40000, -56285669 + 160000, 80000, 80000, 1);
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA_GRAY, "Snake", 73447582 - 800000 - 80000, -56285669 + 160000 - 80000, 80000, 80000, 1);
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA_STRONG, "Pizza", 73447582 - 800000 + 80000, -56285669 + 160000 - 80000, 40000, 80000, 1);
    }

    private static void BrisbaneSupply31() {

        //Brisbane supply 31
        ZeonSupply.standardGroundSupply(73447582 - 1120000, -56285669 + 160000, "Brisbane Supply 31");

        AreaManagerGround a;

        //NPC område plassert vest for supply. 40x40KM.
        a = LocalManager.addGroundArea(1, 73447582 - 1120000 - 100000, -56285669 + 160000, 80000, 80000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY_BR, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GMHEAD_MG, 50);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 50);
        a.addNpcGrunts(EFgrunts.ZANNY, 50);
    }

    private static void BrisbaneSupply34() {

        //Brisbane supply 34
        ZeonSupply.standardGroundSupply(73447582 + 160000, -56285669 - 800000, "Brisbane Supply 34");

        AreaManagerGround a;

        //NPC område sør-vest for supply, 40x40KM.
        a = LocalManager.addGroundArea(1, 73447582 + 160000 - 100000, -56285669 - 800000 - 100000, 80000, 80000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM, 40);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY_BR, 40);
        a.addNpcGrunts(EFgrunts.GUNCANNON2, 20);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 20);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_180MM, 30);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_MG, 30);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER, 20);
        a.addNpcGrunts(EFgrunts.GMSNIPER2, 10);
        a.addNpcGrunts(EFgrunts.RMV1, 40);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG, 50);
    }

    private static void BrisbaneSupply35() {

        //Brisbane supply 35
        ZeonSupply.standardGroundSupply(73646816, -56407965, "Brisbane Supply 35");

        AreaManagerGround a;

        //NPC område nord for supply, 16x10KM. Enkelt.
        a = LocalManager.addGroundArea(1, 73646816, -56407965 + 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.MBT, 20);
        a.addNpcGrunts(EFgrunts.GM_TRAINER, 10);
        a.addNpcGrunts(EFgrunts.GM79_BSG_SHIELD, 10);
        a.addNpcGrunts(EFgrunts.GUNCANNON_MP, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_180MM, 5);

        //NPC område sør for supply, 16x10KM. Enkelt.
        a = LocalManager.addGroundArea(1, 73646816, -56407965 - 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM_TRAINER, 20);
        a.addNpcGrunts(EFgrunts.GMCANNON, 10);
        a.addNpcGrunts(EFgrunts.GUNTANK, 10);
        a.addNpcGrunts(EFgrunts.GM79_FAST_MG, 5);
        a.addNpcGrunts(EFgrunts.GM79_BSG_SHIELD, 10);
    }

    private static void BrisbaneSupply39() {

        //Brisbane supply 39
        ZeonSupply.standardGroundSupply(73447582 - 960000, -56285669 - 160000, "Brisbane Supply 39A");
        ZeonSupply.standardGroundSupply(73447582 - 960000, -56285669 - 320000, "Brisbane Supply 39B");

        AreaManagerGround a;

        //NPC område mellom supplies, 72x72KM.
        a = LocalManager.addGroundArea(1, 73447582 - 1120000, -56285669 - 320000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM79_BLACK, 50);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 50);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMRIFLE, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY_BR, 50);
        a.addNpcGrunts(EFgrunts.GMHEAD_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_180MM, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG, 100);
        a.addNpcGrunts(EFgrunts.GUNCANNON2, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_MG, 100);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER_STRONG, 10);
        a.addNpcGrunts(EFgrunts.GMSNIPER_CUSTOM, 5);

        //Område med spesielle NPCs.
        a = LocalManager.addGroundArea(1, 73447582 - 1120000, -56285669 - 320000, 128000, 128000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM_STRONG, 70);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_BEAM, 20);

        //NPC Bosses.
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA, "Water", 73447582 - 1120000 + 40000, -56285669 - 320000, 40000, 80000, 1);
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA_GRAY_STRONG, "Sandwich", 73447582 - 1120000 - 40000, -56285669 - 320000 + 40000, 80000, 80000, 1);
    }

    private static void BrisbaneSupply40() {

        //Brisbane supply 40
        ZeonSupply.standardGroundSupply(73447582, -56285669 - 320000, "Brisbane Supply 40");

        AreaManagerGround a;

        //NPC område plassert sør-vest for supply. 40x40KM.
        a = LocalManager.addGroundArea(1, 73447582 - 100000, -56285669 - 320000 - 100000, 80000, 80000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 50);
        a.addNpcGrunts(EFgrunts.GMSNIPER2, 30);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY_BR, 20);
        a.addNpcGrunts(EFgrunts.GMHEAD_MG, 50);
        a.addNpcGrunts(EFgrunts.GM79_BLACK, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 50);
        a.addNpcGrunts(EFgrunts.RMV1, 100);
        //NPC område med "spesielle" NPCs.
        a = LocalManager.addGroundArea(1, 73447582 - 100000, -56285669 - 320000 - 100000, 60000, 60000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.EZ8_STRONG, 10);
        a.addNpcGrunts(EFgrunts.EZ8, 10);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_BEAM_STRONG, 20);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM, 5);

    }
}
