package npc.locations;

import npc.*;
import npc.spawn.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPC i Tasmania.
 */
public class Tasmania {

    public static void execute() {

        //Default EF supply i Tasmania.
        EFsupply.standardGroundSupply(70036690, -63257400, "EF Tasmania Supply 1");

        //Default Zeon supply i Tasmania.
        ZeonSupply.standardGroundSupply(69616733, -63019640, "Zeon Tasmania Supply 1");

        ZeonNpcEF1();
        EfNpcZeon1();
    }

    /**
     * Denne metoden definerer Zeon NPCs som er plassert i nærheten av EF
     * Tasmania Supply 1.
     */
    private static void ZeonNpcEF1() {

        AreaManagerGround a;

        //NPC område plassert ca 10KM øst for Supply 1, 20x20KM stort.
        a = LocalManager.addGroundArea(2, 70036690 + 80000, -63257400, 40000, 40000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 5);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 5);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 10);
        a.addNpcGrunts(ZeonGrunts.GOGG, 20);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 10);
        a.addNpcGrunts(ZeonGrunts.JUAGG_ROCKET, 10);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM, 5);

        //NPC område plassert ca 10KM vest for Supply 1, 40x40KM stort.
        a = LocalManager.addGroundArea(2, 70036690 - 120000, -63257400, 80000, 80000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 30);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 30);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 30);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MG, 40);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_BLACK, 40);
        a.addNpcGrunts(ZeonGrunts.DOM_BEAMBAZOOKA, 30);
        a.addNpcGrunts(ZeonGrunts.ACGUY, 50);
        a.addNpcGrunts(ZeonGrunts.GOGG, 30);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 25);
        a.addNpcGrunts(ZeonGrunts.JUAGG_ROCKET, 25);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 20);
        a.addNpcGrunts(ZeonGrunts.GOUF_HEAT_SWORD, 10);
        //NPC område for "spesielle" NPCs.
        a = LocalManager.addGroundArea(2, 70036690 - 120000, -63257400, 70000, 70000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM, 15);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_BEAM_STRONG, 5);
        a.addNpcGrunts(ZeonGrunts.GELGOOG_14A_EXTRA_STRONG, 5);

        //NPC område plassert sør for Supply 1. 10x10KM stort. For nybegynnere.
        a = LocalManager.addGroundArea(2, 70036690, -63257400 - 30000, 20000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK_WEAK, 30);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU1, 2);

        //NPC område plassert nord for Supply 1. 10x10KM stort. For nybegynnere.
        a = LocalManager.addGroundArea(2, 70036690, -63257400 + 30000, 20000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK_WEAK, 30);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_MG, 2);

    }

    /**
     * Denne metoden definerer EF NPCs som er plassert i nærheten av Zeon
     * Tasmania Supply 1.
     */
    private static void EfNpcZeon1() {

        AreaManagerGround a;

        //NPC område plassert ca 10KM vest for Supply 1, 20x20KM stort.
        a = LocalManager.addGroundArea(1, 69616733 - 80000, -63019640, 40000, 40000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM79_180MM, 10);
        a.addNpcGrunts(EFgrunts.COLDTYPE_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.COLDTYPE_BEAM, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_180MM, 5);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 20);
        a.addNpcGrunts(EFgrunts.GUNCANNON_MP, 20);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 5);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 7);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 3);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_BEAM, 5);

        //NPC område plassert ca 10KM øst for Supply 1, 40x40KM stort.
        a = LocalManager.addGroundArea(1, 69616733 + 120000, -63019640, 80000, 80000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM79_FAST_MG, 50);
        a.addNpcGrunts(EFgrunts.GM79_BLACK, 40);
        a.addNpcGrunts(EFgrunts.COLDTYPE_FAST_MG, 40);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 40);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_180MM, 20);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 60);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM_STRONG, 20);
        a.addNpcGrunts(EFgrunts.GUNCANNON2, 30);
        a.addNpcGrunts(EFgrunts.GMLA_BEAM_SABER, 20);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 30);
        a.addNpcGrunts(EFgrunts.GMSNIPER2, 30);
        //NPC område for "spesielle" NPCs.
        a = LocalManager.addGroundArea(1, 69616733 + 120000, -63019640, 70000, 70000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_BEAM, 15);
        a.addNpcGrunts(EFgrunts.GROUNDGUNDAM_BEAM_STRONG, 5);
        a.addNpcGrunts(EFgrunts.EZ8, 5);

        //NPC område plasser sør for Supply 1. 10x10KM stort. For nybegynnere.
        a = LocalManager.addGroundArea(1, 69616733, -63019640 - 30000, 20000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.MBT_WEAK, 30);
        a.addNpcGrunts(EFgrunts.MBT, 10);
        a.addNpcGrunts(EFgrunts.GM_TRAINER, 2);

        //NPC område plasser nord for Supply 1. 10x10KM stort. For nybegynnere.
        a = LocalManager.addGroundArea(1, 69616733, -63019640 + 30000, 20000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.MBT_WEAK, 30);
        a.addNpcGrunts(EFgrunts.MBT, 10);
        a.addNpcGrunts(EFgrunts.GUNTANK, 2);
    }

}
