package chatServer;

import characters.CharacterChat;
import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere et team.
 */
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    //ID for team.
    private int team_id;

    //Navnet på team.
    private String navn;

    //Character ID til eier av laget.
    private int owner;

    //Liste over medlemmene i teamet.
    //Key = character ID.
    private ConcurrentHashMap<Integer, TeamMember> medlemmer = new ConcurrentHashMap<Integer, TeamMember>();

    public Team(CharacterChat creator, String navn, int id) {
        this.team_id = id;
        this.navn = navn;
        this.owner = creator.getCharacterID();

        TeamMember spiller = new TeamMember(creator.getCharacterID(), creator.getNavn(), creator.getRank(), creator.getGender());

        this.medlemmer.put(creator.getCharacterID(), spiller);
        //Registrer at spilleren har blitt medlem av et lag.
        TeamManagement.registerMember(this.owner, this.team_id);
    }

    /**
     * Returnerer eieren av et team.
     *
     * @return Character ID til eier.
     */
    public int getOwner() {
        return this.owner;
    }

    /**
     * @return Team navnet.
     */
    public String getNavn() {
        return this.navn;
    }

    /**
     * Returnerer ID for team.
     *
     * @return Team ID.
     */
    public int getTeamID() {
        return this.team_id;
    }

    /**
     * Returnerer liste over medlemmene i teamet.
     *
     * @return Iterator over medlemmene.
     */
    public Iterator<TeamMember> getTeamMembers() {
        return this.medlemmer.values().iterator();
    }

    /**
     * Returnerer antall medlemmer i team.
     *
     * @return Antall medlemmer.
     */
    public int getNumberOfMembers() {
        return this.medlemmer.size();
    }

    /**
     * Legger til et nytt medlem i laget.
     *
     * @param medlem Spilleren som skal legges til.
     */
    public void addMember(CharacterChat medlem) {

        TeamMember tm = new TeamMember(medlem.getCharacterID(), medlem.getNavn(), medlem.getRank(), medlem.getGender());

        this.medlemmer.put(medlem.getCharacterID(), tm);
        //Registrer at spilleren har blitt medlem av et lag.
        TeamManagement.registerMember(medlem.getCharacterID(), this.team_id);
    }

    /**
     * Returnerer info om et medlem i laget.
     *
     * @param character_id Character ID for medlem vi skal returnere info om.
     *
     * @return TeamMember objekt, eller NULL hvis medlemmet ikke finnes.
     */
    public TeamMember getMember(int character_id) {
        return this.medlemmer.get(character_id);
    }

    /**
     * Fjerner et medlem fra laget.
     *
     * @param character_id Medlem som skal fjernes.
     */
    public void removeMember(int character_id) {
        this.medlemmer.remove(character_id);
        //Registrer at spilleren er meldt ut av et lag.
        TeamManagement.unregisterMember(character_id);
    }
}
