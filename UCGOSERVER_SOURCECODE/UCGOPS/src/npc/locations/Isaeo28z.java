package npc.locations;

import npc.*;
import npc.spawn.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen oppretter alle NPCs i space ved ISAEO 28Z.
 *
 * ISAEO 28Z koordinater er: 6267275, -2815317, 0
 */
public class Isaeo28z {

    public static void execute() {

        ZeonSupply004();

        ZeonSupply054();

        ZeonSupply103();
    }

    /**
     * Definerer alle NPCs rundt Isaeo 28Z fleet 004.
     */
    private static void ZeonSupply004() {

        //Isaeo 28z fleet 004
        ZeonSupply.standardSpaceSupply(6248535, -2816235, -300, "ISAEO 28Z Fleet 004");

        AreaManagerSpace a;

        //NPC område, ligger i retning bort fra ISAEO langs X-aksen. 10x10x10KM
        a = LocalManager.addSpaceArea(1, 6248535 - 32000, -2816235, 0, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.RB79, 20);
        a.addNpcGrunts(EFgrunts.RB79C, 20);
        a.addNpcGrunts(EFgrunts.RB79K, 20);
        a.addNpcGrunts(EFgrunts.GM_TRAINER, 20);
        a.addNpcGrunts(EFgrunts.GM79_BSG_SHIELD, 10);
        a.addNpcGrunts(EFgrunts.GMCANNON, 10);

    }

    /**
     * Definerer alle NPCs rundt Isaeo 28Z fleet 054.
     */
    private static void ZeonSupply054() {

        //Isaeo 28z fleet 054
        ZeonSupply.standardSpaceSupply(6267253, -2796016, -300, "ISAEO 28Z Fleet 054");

        AreaManagerSpace a;

        //NPC område, ligger i retning bort fra ISAEO langs Y-aksen. 10x10x10KM
        a = LocalManager.addSpaceArea(1, 6267253, -2796016 + 32000, 0, 20000, 20000, 20000, 8000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.RB79, 20);
        a.addNpcGrunts(EFgrunts.RB79C, 10);
        a.addNpcGrunts(EFgrunts.RB79K, 10);
        a.addNpcGrunts(EFgrunts.GM_TRAINER, 20);
        a.addNpcGrunts(EFgrunts.GM79_BSG_SHIELD, 20);
        a.addNpcGrunts(EFgrunts.GM79_FAST_MG, 10);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 5);

    }

    /**
     * Definerer alle NPCs rundt Isaeo 28Z fleet 103.
     */
    private static void ZeonSupply103() {

        //Isaeo 28z fleet 103
        ZeonSupply.standardSpaceSupply(6287399, -2815351, -300, "ISAEO 28Z Fleet 103");

        AreaManagerSpace a;

        //NPC område, ligger i retning bort fra ISAEO langs X-aksen. 20x20x20KM
        a = LocalManager.addSpaceArea(1, 6287399 + 52000, -2815351, 0, 40000, 40000, 40000, 8000, 8000, 8000);
        a.addNpcGrunts(EFgrunts.RB79K, 40);
        a.addNpcGrunts(EFgrunts.GM_TRAINER, 40);
        a.addNpcGrunts(EFgrunts.GM79_BSG_SHIELD, 40);
        a.addNpcGrunts(EFgrunts.GMLA_FAST_BEAM, 40);
        a.addNpcGrunts(EFgrunts.GMCOMMANDS_MG, 40);
        a.addNpcGrunts(EFgrunts.GMCOMMANDS_BAZOOKA, 40);
        a.addNpcGrunts(EFgrunts.GUNCANNON_MP, 20);
        a.addNpcGrunts(EFgrunts.GUNCANNON_HEAVY, 40);

    }

}
