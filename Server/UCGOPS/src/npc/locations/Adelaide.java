package npc.locations;

import npc.AreaManagerGround;
import npc.LocalManager;
import npc.spawn.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs utenfor Adelaide.
 */
public class Adelaide {

    public static void execute() {

        AdelaideSupply48();

        AdelaideSupply49();
    }

    private static void AdelaideSupply48() {

        //Adelaide Supply 48
        ZeonSupply.standardGroundSupply(66467590, -59986127, "Adelaide Supply 48");

        AreaManagerGround a;

        //NPC område nord for supply, 16x10KM. Litt vanskelig.
        a = LocalManager.addGroundArea(1, 66467590, -59986127 + 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.COLDTYPE_FAST_MG, 20);
        a.addNpcGrunts(EFgrunts.GM79_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.GMCANNON, 10);
        a.addNpcGrunts(EFgrunts.GUNCANNON_MP, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_180MM, 10);

        //NPC område sør for supply, 16x10KM. Litt vanskelig.
        a = LocalManager.addGroundArea(1, 66467590, -59986127 - 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.COLDTYPE_BEAM, 20);
        a.addNpcGrunts(EFgrunts.GM79_180MM, 10);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_BEAMGUN, 10);
        a.addNpcGrunts(EFgrunts.GM79_FAST_MG, 20);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 5);

    }

    private static void AdelaideSupply49() {

        //Adelaide Supply 49
        ZeonSupply.standardGroundSupply(66188535, -59587874, "Adelaide Supply 49");

        AreaManagerGround a;

        //NPC område nord for supply, 16x10KM. Enkelt, stort sett.
        a = LocalManager.addGroundArea(1, 66188535, -59587874 + 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM_TRAINER, 20);
        a.addNpcGrunts(EFgrunts.GM79_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.GMCANNON, 10);
        a.addNpcGrunts(EFgrunts.GUNCANNON_MP, 5);
        a.addNpcGrunts(EFgrunts.GMCOMMAND_180MM, 5);

        //NPC område sør for supply, 16x10KM. Enkelt, stort sett.
        a = LocalManager.addGroundArea(1, 66188535, -59587874 - 32000, 32000, 20000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.GM_TRAINER, 20);
        a.addNpcGrunts(EFgrunts.GM79_180MM, 10);
        a.addNpcGrunts(EFgrunts.COLDTYPE_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.GM79_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 5);

    }
}
