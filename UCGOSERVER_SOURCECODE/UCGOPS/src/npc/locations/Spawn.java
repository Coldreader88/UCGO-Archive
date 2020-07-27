package npc.locations;

import npc.*;
import npc.spawn.EFgrunts;
import npc.spawn.ZeonGrunts;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette er hovedklassen som sørger for at alle NPCs i spillet blir opprettet
 * ved server start.
 */
public class Spawn {

    public static void execute() {

        Richmond.execute();
        Newman.execute();
        Tasmania.execute();
        AyersRock.execute();
        Melbourne.execute();
        Adelaide.execute();
        Darwin.execute();
        Brisbane.execute();
        Sydney.execute();
        Canberra.execute();
        SouthernCross.execute();
        Perth.execute();
        Isaeo29.execute();
        EFFSS.execute();
        Helenes.execute();
        ZSSAEO.execute();
        Isaeo28z.execute();
    }

}
