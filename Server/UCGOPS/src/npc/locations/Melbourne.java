package npc.locations;

import npc.AreaManagerGround;
import npc.GlobalManager;
import npc.LocalManager;
import npc.spawn.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs utenfor Melbourne.
 */
public class Melbourne {

    public static void execute() {

        MelbourneSupply7();

        MelbourneSupply11();

        MelbourneSupply19();

        MelbourneSupply25();

        MelbourneSupply32();

        MelbourneSupply41();

        MelbourneSupply48();
    }

    private static void MelbourneSupply7() {

        //Melbourne supply 7
        ZeonSupply.standardGroundSupply(70204973 + 160000, -60714815 + 160000, "Melbourne Supply 7A"); //Øverste høyre hjørne
        ZeonSupply.standardGroundSupply(70204973 - 160000, -60714815 - 160000, "Melbourne Supply 7B"); //Nederste venstre hjørne
        ZeonSupply.standardGroundSupply(70204973 + 160000, -60714815 - 160000, "Melbourne Supply 7C"); //Nederste høyre hjørne

        AreaManagerGround a;

        //NPC område, 72x72KM
        a = LocalManager.addGroundArea(1, 69404973 + 800000, -61194815 + 480000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMGROUND_CF, 100);
        a.addNpcGrunts(EFgrunts.GUNCANNON, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_MG, 50);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMRIFLE, 50);
        a.addNpcGrunts(EFgrunts.GMSNIPER2, 25);
        a.addNpcGrunts(EFgrunts.GMSNIPER_CUSTOM, 25);
        a.addNpcGrunts(EFgrunts.COLDTYPE_BEAM_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 50);
        a.addNpcGrunts(EFgrunts.GMHEAD_MG, 50);
        a.addNpcGrunts(EFgrunts.GM79_BLACK, 50);

        //NPC område, 66x66KM, kun spesielle NPCs.
        a = LocalManager.addGroundArea(1, 69404973 + 800000, -61194815 + 480000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM_STRONG, 5);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM, 5);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_BEAM_STRONG, 40);

        //NPC Boss.
        GlobalManager.spawnNpcBoss(1, EfBoss.BIG_TRAY, "Pigeon", 69404973 + 800000, -61194815 + 480000, 80000, 80000, 1);
    }

    private static void MelbourneSupply11() {

        //Melbourne Supply 11
        ZeonSupply.standardGroundSupply(69564973, -60554815 + 160000, "Melbourne Supply 11A"); //Midten øverst	
        ZeonSupply.standardGroundSupply(69564973, -60554815 - 160000, "Melbourne Supply 11B"); //Midten nederst
        ZeonSupply.standardGroundSupply(69564973 + 160000, -60554815, "Melbourne Supply 11C"); //Høyre midten

        AreaManagerGround a;

        //NPC område, 72x72KM
        a = LocalManager.addGroundArea(1, 69404973 + 160000, -61194815 + 640000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.COLDTYPE_BEAM_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_CF, 100);
        a.addNpcGrunts(EFgrunts.GMLA_MG, 50);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GMSNIPER_CUSTOM, 25);
        a.addNpcGrunts(EFgrunts.GUNCANNON, 75);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 100);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 50);
        a.addNpcGrunts(EFgrunts.GMHEAD_MG, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON2, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 50);

        //NPC område, 66x66KM, kun spesielle NPCs.
        a = LocalManager.addGroundArea(1, 69404973 + 160000, -61194815 + 640000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.EZ8_RED, 20);
        a.addNpcGrunts(EFgrunts.PROTOTYPE_GUNDAM, 8);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_RED, 20);

        //NPC Boss.
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA_GRAY_STRONG, "Cactus", 69404973 + 160000, -61194815 + 640000, 80000, 80000, 1);
    }

    /**
     * Default supply beregnet for nybegynnere.
     */
    private static void MelbourneSupply19() {

        //Melbourne Supply 19
        ZeonSupply.standardGroundSupply(69542727, -61001391, "Melbourne Supply 19");

        AreaManagerGround a;

        //NPC område sør for supply, 8x8KM.
        a = LocalManager.addGroundArea(1, 69542727, -61001391 - 26000, 16000, 16000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.MBT, 30);
        a.addNpcGrunts(EFgrunts.GM_TRAINER, 10);
        a.addNpcGrunts(EFgrunts.GUNTANK, 5);

        //NPC område nord for supply, 16x10KM.
        a = LocalManager.addGroundArea(1, 69542727, -61001391 + 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.MBT, 30);
        a.addNpcGrunts(EFgrunts.GM_TRAINER, 10);
        a.addNpcGrunts(EFgrunts.GUNTANK, 10);
        a.addNpcGrunts(EFgrunts.GM79_BSG_SHIELD, 5);
        a.addNpcGrunts(EFgrunts.GMCANNON, 5);

    }

    private static void MelbourneSupply25() {

        //Melbourne Supply 25
        ZeonSupply.standardGroundSupply(69804973 - 240000, -59834815 + 240000, "Melbourne Supply 25A"); //Øverste venstre hjørne
        ZeonSupply.standardGroundSupply(69804973 + 240000, -59834815, "Melbourne Supply 25"); //Høyre midten 
        ZeonSupply.standardGroundSupply(69804973, -59834815 - 240000, "Melbourne Supply 25");  //Nederst midten

        AreaManagerGround a;

        //NPC område 112X112KM.
        a = LocalManager.addGroundArea(1, 69404973 + 400000, -61194815 + 1360000, 224000, 224000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GUNCANNON2, 100);
        a.addNpcGrunts(EFgrunts.GMGROUND_CF, 200);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM_STRONG, 100);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG_STRONG, 100);
        a.addNpcGrunts(EFgrunts.COLDTYPE_BEAM_STRONG, 200);
        a.addNpcGrunts(EFgrunts.GMSNIPER_CUSTOM, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON, 50);
        a.addNpcGrunts(EFgrunts.GMHEAD_MG, 100);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM_STRONG, 100);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 100);
        a.addNpcGrunts(EFgrunts.GMLA_MG, 100);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_180MM, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_MG, 50);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMRIFLE, 100);
        a.addNpcGrunts(EFgrunts.GMHEAD_BEAM, 100);

        //NPC område 96x96KM. Kun spesielle NPCs.
        a = LocalManager.addGroundArea(1, 69404973 + 400000, -61194815 + 1360000, 192000, 192000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.EZ8_RED, 30);
        a.addNpcGrunts(EFgrunts.PROTOTYPE_GUNDAM, 10);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM_STRONG, 10);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_BEAM_STRONG, 40);

        //NPC Boss.
        GlobalManager.spawnNpcBoss(1, EfBoss.PEGASUS, "Perseus", 69404973 + 400000, -61194815 + 1360000, 80000, 80000, 1);
        GlobalManager.spawnNpcBoss(1, EfBoss.BIG_TRAY, "Pistachio", 69404973 + 400000, -61194815 + 1360000, 160000, 160000, 1);
    }

    private static void MelbourneSupply32() {

        //Melbourne Supply 32
        ZeonSupply.standardGroundSupply(68924973 - 160000, -60234815, "Melbourne Supply 32A"); //Venstre midten
        ZeonSupply.standardGroundSupply(68924973 + 160000, -60234815 + 160000, "Melbourne Supply 32B"); //Øverste høyre hjørne
        ZeonSupply.standardGroundSupply(68924973 + 160000, -60234815 - 160000, "Melbourne Supply 32C"); //Nederste høyre hjørne

        AreaManagerGround a;

        //NPC område, 72x72KM
        a = LocalManager.addGroundArea(1, 69404973 - 480000, -61194815 + 960000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMLA_MG, 50);
        a.addNpcGrunts(EFgrunts.GMSNIPER_CUSTOM, 25);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER_STRONG, 25);
        a.addNpcGrunts(EFgrunts.COLDTYPE_BEAM_STRONG, 100);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_CF, 100);
        a.addNpcGrunts(EFgrunts.GUNCANNON, 50);
        a.addNpcGrunts(EFgrunts.GM79_BLACK, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_MG, 25);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_180MM, 25);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 50);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 50);

        //NPC område, 66x66KM, kun spesielle NPCs.
        a = LocalManager.addGroundArea(1, 69404973 - 480000, -61194815 + 960000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.EZ8_RED, 20);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM_STRONG, 15);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_RED, 30);

    }

    private static void MelbourneSupply41() {

        //Melbourne Supply 41
        ZeonSupply.standardGroundSupply(68604973 - 160000, -61034815 + 160000, "Melbourne Supply 41A"); //Øverste venstre hjørne
        ZeonSupply.standardGroundSupply(68604973 + 160000, -61034815, "Melbourne Supply 41B"); //Høyre midten 
        ZeonSupply.standardGroundSupply(68604973, -61034815 - 160000, "Melbourne Supply 41C"); //Midten nederst

        AreaManagerGround a;

        //NPC område, 72x72KM
        a = LocalManager.addGroundArea(1, 69404973 - 800000, -61194815 + 160000, 144000, 144000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_MG_STRONG, 100);
        a.addNpcGrunts(EFgrunts.GMMODIFIED_BEAM_STRONG, 100);
        a.addNpcGrunts(EFgrunts.GMLA_MG, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GMGROUND_CF, 100);
        a.addNpcGrunts(EFgrunts.GUNCANNON, 50);
        a.addNpcGrunts(EFgrunts.COLDTYPE_BEAM_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GM79_GREEN, 50);
        a.addNpcGrunts(EFgrunts.GMHEAD_MG, 50);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER_STRONG, 50);
        a.addNpcGrunts(EFgrunts.GUNCANNON2, 50);

        //NPC område, 66x66KM, kun spesielle NPCs.
        a = LocalManager.addGroundArea(1, 69404973 - 800000, -61194815 + 160000, 132000, 132000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.FA_GUNDAM_STRONG, 8);
        a.addNpcGrunts(EFgrunts.PROTOTYPE_GUNDAM, 8);

        //NPC Boss.
        GlobalManager.spawnNpcBoss(1, EfBoss.MIDEA_GRAY_STRONG, "Hector", 69404973 - 800000, -61194815 + 160000, 80000, 80000, 1);
    }

    /**
     * Default supply for nybegynnere.
     */
    private static void MelbourneSupply48() {

        //Melbourne Supply 48
        ZeonSupply.standardGroundSupply(69204560, -61320904, "Melbourne Supply 48");

        AreaManagerGround a;

        //NPC område nord for supply, 16x16KM.
        a = LocalManager.addGroundArea(1, 69204560, -61320904 + 42000, 32000, 32000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.MBT, 25);
        a.addNpcGrunts(EFgrunts.MBT_WEAK, 25);
        a.addNpcGrunts(EFgrunts.GM_TRAINER, 10);

        //NPC område sør for supply, 16x16KM.
        a = LocalManager.addGroundArea(1, 69204560, -61320904 - 42000, 32000, 32000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.MBT, 25);
        a.addNpcGrunts(EFgrunts.MBT_WEAK, 25);
        a.addNpcGrunts(EFgrunts.GUNTANK, 10);

    }
}
