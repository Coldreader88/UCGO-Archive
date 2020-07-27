package chatServer;

import characters.CharacterChat;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å håndtere alle teams på serveren.
 */
public class TeamManagement {

    //Holder en liste over alle teams som finnes.
    //Key = Team ID.
    private static ConcurrentHashMap<Integer, Team> team_list = new ConcurrentHashMap<Integer, Team>();

    //Holder en liste over alle spillere som er med i et team. Denne listen må oppdateres når spillere
    //fjernes eller blir med i et lag.
    //Key = Character ID for medlem, Value = Team ID.
    private static ConcurrentHashMap<Integer, Integer> team_members = new ConcurrentHashMap<Integer, Integer>();

    /**
     * Oppretter et nytt team.
     *
     * @param navn Navn på team.
     * @param creator Spilleren som oppretter team.
     *
     * @return Team objekt, eller NULL hvis team ikke kunne opprettes, f.eks
     * finnes fra før.
     */
    public static synchronized Team createTeam(String navn, CharacterChat creator) {

        Team nytt_team = null;

        boolean nytt_navn = true;
        boolean ny_creator = true;

        //Sjekk at navnet ikke er i bruk fra før og at spilleren ikke er satt opp som eier av et annet lag.
        Iterator<Team> liste = team_list.values().iterator();

        while (liste.hasNext()) {
            Team lag = liste.next();
            if (lag.getOwner() == creator.getCharacterID()) {
                ny_creator = false;
            }
            if (lag.getNavn() == navn) {
                nytt_navn = false;
            }
        }

        if (!nytt_navn || !ny_creator) {
            return null;
        }

        //OK, opprett nytt team.
        int id_nummer;

        while (true) {
            id_nummer = 0x07000000 + (int) (Math.random() * 0xFFFFFF);
            if (team_list.get(id_nummer) == null) {
                break; //Gå til vi har et ID som er ledig.
            }
        }

        nytt_team = new Team(creator, navn, id_nummer);
        team_list.put(id_nummer, nytt_team);

        return nytt_team;
    }

    /**
     * Registrerer en spiller som medlem av et lag. Spiller blir ikke meldt inn
     * i selve laget.
     *
     * @param character_id Character ID for spilleren.
     *
     * @param team_id Lag spilleren meldes inn i.
     */
    public static void registerMember(int character_id, int team_id) {
        team_members.put(character_id, team_id);
    }

    /**
     * Fjerner en spiller fra oversikten over hvem som er med i et lag.
     * Spilleren blir ikke meldt ut av selve laget.
     *
     * @param character_id Character ID for spillerne.
     */
    public static void unregisterMember(int character_id) {
        team_members.remove(character_id);
    }

    /**
     * Returnerer hvilket team en spiller tilhører.
     *
     * @param character_id Character ID for spilleren.
     *
     * @return Team ID spilleren tilhører eller -1 hvis ikke med i et lag.
     */
    public static int getTeamMembership(int character_id) {

        Integer team_id = team_members.get(character_id);

        if (team_id == null) {
            return -1;
        } else {
            return team_id.intValue();
        }
    }

    /**
     * Returnerer et team.
     *
     * @param team_id ID for team som skal returneres.
     *
     * @return Team objekt eller NULL hvis team ikke finnes.
     */
    public static Team getTeam(int team_id) {
        return team_list.get(team_id);
    }

    /**
     * Sletter et team.
     *
     * @param team_id Team som skal slettes.
     */
    public static void removeTeam(int team_id) {
        team_list.remove(team_id);
    }

    /**
     * Denne metoden lagrer team data til fil.
     */
    public static synchronized void save() {

        String team_fil = config.Server.serverdata_file_location + "/teams.ser";
        String team_backup = config.Server.serverdata_file_location + "/teams";

        Calendar dato = new GregorianCalendar();

        team_backup += dato.get(Calendar.YEAR) + "-" + dato.get(Calendar.DAY_OF_MONTH) + "-" + dato.get(Calendar.MONTH) + "-";
        team_backup += dato.get(Calendar.HOUR) + "-" + dato.get(Calendar.MINUTE) + ".ser";

        admin.logging.globalserverMsg("Saving team data to file.");

        try {
            FileOutputStream fil = new FileOutputStream(team_fil);
            FileOutputStream fil_backup = new FileOutputStream(team_backup);

            //Skriv til teams.txt
            ObjectOutputStream out = new ObjectOutputStream(fil);
            out.writeObject(team_list);
            out.flush();
            out.close();
            fil.close();

            //Skriv til backup fil.
            out = new ObjectOutputStream(fil_backup);
            out.writeObject(team_list);
            out.flush();
            out.close();
            fil.close();
        } catch (Exception e) {
            e.printStackTrace();
            admin.logging.globalserverMsg("Error saving team data to file.");
        }
    }

    /**
     * Leser inn team data fra fil. Denne metoden SKAL kun brukes ved server
     * start.
     */
    @SuppressWarnings("unchecked")
    public static void load() {

        System.out.print("Loading team data from file...");

        try {

            String team_fil = config.Server.serverdata_file_location + "/teams.ser";

            FileInputStream fil = new FileInputStream(team_fil);
            ObjectInputStream in = new ObjectInputStream(fil);

            team_list = (ConcurrentHashMap<Integer, Team>) in.readObject();
            cleanTeams();

            fil.close();
            in.close();

            //Gå gjennom alle lagene og registrer medlemmene i team_members.
            Iterator<Team> lag = team_list.values().iterator();

            while (lag.hasNext()) {

                Team laget = lag.next();

                Iterator<TeamMember> medlemmer = laget.getTeamMembers();
                while (medlemmer.hasNext()) {
                    registerMember(medlemmer.next().getCharacterID(), laget.getTeamID());
                }

            }

            System.out.println("OK! " + team_list.size() + " teams loaded.");

        } catch (Exception e) {
            System.out.println("Error loading team data from file.");
        }

    }

    /**
     * Går gjennom team_list og fjerner alle medlemmer som ikke lengre
     * eksisterer. I tillegg vil gamle teams uten medlemmer bli fjernet.
     */
    private static void cleanTeams() {

        Iterator<Team> teams = team_list.values().iterator();

        while (teams.hasNext()) {

            Team t = teams.next();

            if (t.getNumberOfMembers() > 0) {
                //Laget har medlemmer. Sjekk om noen av dem er gamle og skal fjernes.
                Iterator<TeamMember> tm = t.getTeamMembers();

                //Gå gjennom alle medlemmene i laget.
                while (tm.hasNext()) {
                    //Sjekk om dette medlemmet fremdeles eksisterer eller om character er slettet.
                    TeamMember member = tm.next();

                    if (userDB.ManageCharacters.getGameCharacter(member.getCharacterID()) == null) {
                        //Medlemmet eksisterer ikke lengre, dvs trolig slettet pga utgått på dato. Fjern fra team.
                        t.removeMember(member.getCharacterID());
                    }

                }
            } else {
                //Laget har ingen medlemmer. Fjern det.
                team_list.remove(t.getTeamID());
            }

        }
    }

}
