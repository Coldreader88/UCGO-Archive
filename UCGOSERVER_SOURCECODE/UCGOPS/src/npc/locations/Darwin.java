package npc.locations;

import npc.AreaManagerGround;
import npc.LocalManager;
import npc.spawn.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs utenfor Darwin.
 */
public class Darwin {

    public static void execute() {

        DarwinSupply3();

        DarwinSupply34();
    }

    private static void DarwinSupply3() {

        //Darwin Supply 3
        ZeonSupply.standardGroundSupply(62434714, -48959233, "Darwin Supply 3");

        AreaManagerGround a;

        //NPC område nord for supply. 20x10KM. Vanskelig.
        a = LocalManager.addGroundArea(1, 62434714, -48959233 + 32000, 40000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.COLDTYPE_BEAM, 10);
        a.addNpcGrunts(EFgrunts.COLDTYPE_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_180MM, 5);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 10);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 20);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 10);

        //NPC område sør for supply. 20x10KM. Vanskelig.
        a = LocalManager.addGroundArea(1, 62434714, -48959233 - 32000, 40000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 30);
        a.addNpcGrunts(EFgrunts.COLDTYPE_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 10);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 10);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 5);

    }

    private static void DarwinSupply34() {

        //Darwin Supply 34
        ZeonSupply.standardGroundSupply(62822292, -49281204, "Darwin Supply 34");

        AreaManagerGround a;

        //NPC område nord for supply. 20x10KM. Medium vanskelighet.
        a = LocalManager.addGroundArea(1, 62822292, -49281204 + 32000, 40000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM79_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.COLDTYPE_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_180MM, 5);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 10);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 20);
        a.addNpcGrunts(EFgrunts.GUNTANK, 10);

        //NPC område sør for supply. 20x10KM. Vanskelig.
        a = LocalManager.addGroundArea(1, 62822292, -49281204 - 32000, 40000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 10);
        a.addNpcGrunts(EFgrunts.COLDTYPE_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 10);
        a.addNpcGrunts(EFgrunts.GMGROUND_MG, 20);
        a.addNpcGrunts(EFgrunts.GMGROUND_BEAM, 5);
    }
}
