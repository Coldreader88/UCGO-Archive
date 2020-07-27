package npc.locations;

import npc.spawn.EFsupply;
import npc.spawn.ZeonSupply;

/**
 * Plasserer NPC guards i Newman.
 *
 * @author UCGOSERVER.COM
 */
public class Newman {

    public static void execute() {

        //EF default Camp guards
        EFsupply.campGuards(56912438, -54484057, 2150, "Bright Slap Guard Team");

        //ZEON default camp guards
        ZeonSupply.campGuards(56952331, -54444245, 2650, "Kill EF");
    }
}
