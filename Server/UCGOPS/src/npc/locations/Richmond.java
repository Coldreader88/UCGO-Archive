package npc.locations;

import npc.spawn.EFsupply;
import npc.spawn.ZeonSupply;

/**
 * Oppretter camp guards i Richmond.
 *
 * @author UCGOSERVER.COM
 */
public class Richmond {

    public static void execute() {

        //EF default Camp guards
        EFsupply.campGuards(68951801, -52883913, 1650, "Puru Puru Guard Team");

        //Zeon camp guards
        ZeonSupply.campGuards(68911955, -52843966, 1800, "Char Fan Club");
    }
}
