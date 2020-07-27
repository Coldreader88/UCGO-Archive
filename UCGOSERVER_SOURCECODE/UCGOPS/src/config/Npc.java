package config;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen inneholder diverse config info for NPCs.
 *
 */
public class Npc {

    /**
     * Hvor lang tid, i sekunder, det skal ta før en NPC respawnes.
     */
    public final static int respawnTime = 5 * 60;

    /**
     * Hvor lang tid, i sekunder, det skal ta før en boss NPC respawnes.
     */
    public final static int bossRespawnTime = 2 * 60 * 60;

    /**
     * Hvor lang tid mellom hver gang en NPC skal revurdere målet sitt. Når NPC
     * mottar en liste over fiendtlige spillere er dette hvor ofte den kan bytte
     * mål.
     */
    public final static int targetSwitchTime = 10;

    /**
     * Max avstand til et mål, i gameunits. Gjelder for guard og moving guard
     * NPCs.
     *
     * Dersom avstanden til målet er større vil ikke NPC angripe.
     */
    public final static long guardTargetRange = 8000;

    /**
     * Samme som guardTargetRange men gjelder for grunt NPCs.
     */
    public final static long gruntTargetRange = 12000;

    /**
     * Når en NPC slipper i fra seg wreckage container angir dette hvor lenge
     * wreckage tilhører spiller som ødela NPC.
     */
    public final static int itemDropOwnerExpire = 300;

}
