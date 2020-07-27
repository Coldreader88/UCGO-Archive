package npc.locations;

import npc.spawn.EFsupply;
import npc.spawn.ZeonSupply;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs ved Ayers Rock.
 */
public class AyersRock {

    public static void execute() {

        //Default EF supply ved Ayers Rock.
        EFsupply.standardGroundSupply(62898851, -55383164, "EF Ayers Rock Supply");

        //Default Zeon supply ved Ayers Rock.
        ZeonSupply.standardGroundSupply(62925971, -55340776, "Zeon Ayers Rock Supply");

    }

}
