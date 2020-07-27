package npc;

import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde oversikt over NPC teams.
 */
public class Team {

    /**
     * Alle NPC teams lagres her.
     *
     * KEY=Team ID, Value=Team navn
     */
    private static HashMap<Integer, String> npcTeam = new HashMap<Integer, String>();

    /**
     * Oppretter et nytt NPC team.
     *
     * @param team Navn på team.
     *
     * @return Team ID.
     */
    public synchronized static int createTeam(String team) {

        int teamID;

        do {

            Random r = new Random();
            teamID = 0x04000000 + r.nextInt(0xFFFFFF);

        } while (npcTeam.get(teamID) != null);

        npcTeam.put(teamID, team);

        return teamID;
    }

    /**
     *
     * @param teamID ID til NPC team.
     *
     * @return Navn på team, NULL hvis ugyldig team.
     */
    public static String getTeam(int teamID) {
        return npcTeam.get(teamID);
    }

}
