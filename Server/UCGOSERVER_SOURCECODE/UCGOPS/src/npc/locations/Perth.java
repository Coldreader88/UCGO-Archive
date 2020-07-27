package npc.locations;

import npc.*;
import npc.spawn.EFsupply;
import npc.spawn.ZeonGrunts;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs utenfor Perth.
 */
public class Perth {

    public static void execute() {

        PerthSupply2();

        PerthSupply41();

    }

    private static void PerthSupply2() {

        //Perth Supply 2
        EFsupply.standardGroundSupply(55490189, -58540238, "Perth Supply 2");

        AreaManagerGround a;

        //NPC område nord for supply. 20x10KM. Vanskelig.
        a = LocalManager.addGroundArea(2, 55490189, -58540238 + 32000, 40000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.DOM_FAST_MG, 10);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 10);
        a.addNpcGrunts(ZeonGrunts.JUAGG_ROCKET, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_DESERT_FAST_MG, 10);
        a.addNpcGrunts(ZeonGrunts.GOGG, 10);

        //NPC område sør for supply. 20x10KM. Vanskelig.
        a = LocalManager.addGroundArea(2, 55490189, -58540238 - 32000, 40000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.DOM_FAST_MG, 20);
        a.addNpcGrunts(ZeonGrunts.GOUF_FAST_MMP80, 10);
        a.addNpcGrunts(ZeonGrunts.JUAGG_ROCKET, 20);
        a.addNpcGrunts(ZeonGrunts.ZAKU_F2_STRONG_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.PROTOTYPE_DOM_FAST_GB, 10);
    }

    private static void PerthSupply41() {

        //Perth Supply 41
        EFsupply.standardGroundSupply(55510921, -58146472, "Perth Supply 41");

        AreaManagerGround a;

        //NPC område nord for supply. 20x10KM. Enkelt, stort sett.
        a = LocalManager.addGroundArea(2, 55510921, -58146472 + 32000, 40000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU1_FAST_MG, 20);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 30);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU_CANNON_STRONG, 10);
        a.addNpcGrunts(ZeonGrunts.JUAGG_BEAM, 10);

        //NPC område sør for supply. 20x10KM. Enkelt.
        a = LocalManager.addGroundArea(2, 55510921, -58146472 - 32000, 40000, 20000, 8000, 8000);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_BAZOOKA, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2_MG, 10);
        a.addNpcGrunts(ZeonGrunts.ZAKU2S_MTC, 10);
        a.addNpcGrunts(ZeonGrunts.MAGELLA_ATTACK, 30);
        a.addNpcGrunts(ZeonGrunts.JUAGG_ROCKET, 10);
        a.addNpcGrunts(ZeonGrunts.GOUF_MMP80, 10);

    }
}
