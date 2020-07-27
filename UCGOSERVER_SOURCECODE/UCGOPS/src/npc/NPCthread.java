package npc;

import characters.CharacterGame;
import characters.Posisjon;
import java.util.Iterator;
import java.util.LinkedList;
import players.PlayerGame;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen skal kjøres som en egen thread og er ansvarlig for å kjøre
 * diverse NPC relaterte metoder ved jevne mellomrom.
 *
 */
public class NPCthread implements Runnable {

    public void run() {

        boolean npcGrunts = false; //Når satt til true utføres AI for NPC grunts.

        while (true) {

            try {

                //Respawning og NPC AI for NPC grunts i vanlige områder utføres en gang hvert sekund.
                //For boss NPCs to ganger i sekundet.
                //For NPC vs NPC områder gjøres det to ganger i sekundet. Dette pga slike områder inneholder boss NPCs.
                Thread.sleep(500);

                npcGrunts ^= true;

                if (npcGrunts) {

                    //Gå gjennom alle ground areas.
                    Iterator<AreaManagerGround> gAreas = LocalManager.getGroundAreas();

                    while (gAreas.hasNext()) {

                        AreaManagerGround area = gAreas.next();

                        //Respawn NPCs
                        area.respawn();

                        //Hent ut liste over alle fiendtlige spillere som befinner seg innenfor området.
                        LinkedList<NPCtarget> liste = this.getPlayerList(area, 1);

                        //Hvis liste inneholder spillere send til areamanager slik at NPCs kan velge mål.
                        if (liste.size() > 0) {
                            area.chooseTargets(liste);
                        }

                        //La NPCs i område gjøre det de skal.
                        area.performAI();

                    }

                    //Gå gjennom alle space areas.
                    Iterator<AreaManagerSpace> sAreas = LocalManager.getSpaceAreas();

                    while (sAreas.hasNext()) {

                        AreaManagerSpace area = sAreas.next();

                        //Respawn NPCs
                        area.respawn();

                        //Hent ut liste over alle fiendtlige spillere som befinner seg innenfor området.
                        LinkedList<NPCtarget> liste = this.getPlayerList(area, 2);

                        //Hvis liste inneholder spillere send til areamanager slik at NPCs kan velge mål.
                        if (liste.size() > 0) {
                            area.chooseTargets(liste);
                        }

                        //La NPCs i område gjøre det de skal.
                        area.performAI();
                    }

                }

                //Gå gjennom alle NPC vs NPC ground areas.
                //NB! Husk at dette gjøres 2 ganger i sekundet. Hastighet og rate of fire
                //for grunt NPCs må dermed tilpasses!
                Iterator<AreaManagerGround> npcGroundAreas = LocalManager.getNpcBattlefieldGroundAreas();

                while (npcGroundAreas.hasNext()) {

                    AreaManagerGround area = npcGroundAreas.next();

                    area.respawn();

                    //Ettersom dette området støtter NPC vs NPC kamp henter vi ut liste over alle NPCs samt alle spillere.
                    LinkedList<NPCtarget> liste = this.getAllPlayerList(area, 1);
                    liste.addAll(area.getAllNpc());
                    if (liste.size() > 0) {
                        area.chooseTargets(liste);
                    }

                    area.performAI();
                }

                //Gå gjennom alle NPC vs NPC space areas.
                Iterator<AreaManagerSpace> npcSpaceAreas = LocalManager.getNpcBattlefieldSpaceAreas();

                while (npcSpaceAreas.hasNext()) {

                    AreaManagerSpace area = npcSpaceAreas.next();

                    area.respawn();

                    //Ettersom dette området støtter NPC vs NPC kamp henter vi ut liste over alle NPCs samt alle spillere.
                    LinkedList<NPCtarget> liste = this.getAllPlayerList(area, 2);
                    liste.addAll(area.getAllNpc());

                    if (liste.size() > 0) {
                        area.chooseTargets(liste);
                    }

                    area.performAI();
                }

                //Gå gjennom alle NPCs håndtert av GlobalManager.
                Iterator<NPC> npcs = GlobalManager.getNpcList().iterator();

                while (npcs.hasNext()) {

                    NPC n = npcs.next();

                    //Sjekk først om NPC er aktiv.
                    if (n.isActive() && n.getMachineDamage() < 100) {

                        //La NPC gjøre det den skal.
                        n.performAI();

                        //Sjekk for fiendtlige spillere.
                        LinkedList<NPCtarget> spillere = getPlayerList(n.getPosisjon(), n.getFaction());

                        if (spillere != null && spillere.size() > 0) {
                            n.chooseTarget(spillere.iterator(), null);
                        }
                    } else {
                        //NPC BOSS er ikke aktiv. Sjekk om den skal respawnes.
                        int time = (int) (System.currentTimeMillis() / 1000);

                        if (!n.isActive() && (n.getKillTime() + config.Npc.bossRespawnTime) < time) {
                            n.respawn();
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Lager en liste over alle fiendtlige spillere som befinner seg innenfor
     * oppgitt område. I tillegg må spiller være i MS/vehicle.
     *
     * KUN FOR BRUK I OMRÅDER DER NPCS ANGRIPER SPILLERE. SKAL IKKE BRUKES DER
     * NPCS KAN SLÅSS MOT NPCS OG SPILLERE.
     *
     * @param area Område vi skal sjekke.
     * @param zone Hvilken zone område er i, 1=ground 2=space.
     *
     * @return Liste over alle spillere som befinner seg innenfor oppgitt
     * område.
     */
    private LinkedList<NPCtarget> getPlayerList(Area area, int zone) {

        LinkedList<NPCtarget> liste = new LinkedList<NPCtarget>();

        Iterator<PlayerGame> spillere = gameServer.MultiClient.getAllPlayers();

        //Gå gjennom alle spillere og sjekk om noen av dem er innenfor oppgitt område.
        while (spillere.hasNext()) {

            PlayerGame p = spillere.next();

            if (p.getCharacter().getPosisjon().getZone() == zone && p.getCharacter().getVehicle() != null
                    && (p.getCharacter().getFaction() != area.getFaction() || p.getCharacter().isCriminal())) {

                if (zone == 1) {
                    //Spiller er i ground zone.

                    if (area.isWithinArea(p.getCharacter().getPosisjon().getX(), p.getCharacter().getPosisjon().getY())) {
                        liste.add(new NPCtarget(p.getCharacter()));
                    }

                } else {
                    //Spiller er i space zone.

                    if (area.isWithinArea(p.getCharacter().getPosisjon().getX(), p.getCharacter().getPosisjon().getY(), p.getCharacter().getPosisjon().getZ())) {
                        liste.add(new NPCtarget(p.getCharacter()));
                    }
                }
            }
        }

        return liste;
    }

    /**
     * Lager en liste over alle spillere som befinner seg innenfor oppgitt
     * område uansett faction. I tillegg må spiller være i MS/vehicle.
     *
     * LAGET FOR BRUK I OMRÅDER DER NPCS SLÅSS MOT NPCS OG ANDRE SPILLERE.
     *
     * @param area Område vi skal sjekke.
     * @param zone Hvilken zone område er i, 1=ground 2=space.
     *
     * @return Liste over alle spillere som befinner seg innenfor oppgitt
     * område.
     */
    private LinkedList<NPCtarget> getAllPlayerList(Area area, int zone) {

        LinkedList<NPCtarget> liste = new LinkedList<NPCtarget>();

        Iterator<PlayerGame> spillere = gameServer.MultiClient.getAllPlayers();

        //Gå gjennom alle spillere og sjekk om noen av dem er innenfor oppgitt område.
        while (spillere.hasNext()) {

            PlayerGame p = spillere.next();

            if (p.getCharacter().getPosisjon().getZone() == zone && p.getCharacter().getVehicle() != null) {

                if (zone == 1) {
                    //Spiller er i ground zone.
                    if (area.isWithinArea(p.getCharacter().getPosisjon().getX(), p.getCharacter().getPosisjon().getY())) {
                        liste.add(new NPCtarget(p.getCharacter()));
                    }

                } else {
                    //Spiller er i space zone.
                    if (area.isWithinArea(p.getCharacter().getPosisjon().getX(), p.getCharacter().getPosisjon().getY(), p.getCharacter().getPosisjon().getZ())) {
                        liste.add(new NPCtarget(p.getCharacter()));
                    }
                }
            }
        }

        return liste;
    }

    /**
     * For bruk med BOSS NPC.
     *
     * Lager en liste over alle fiendtlige spillere som befinner seg 2000m
     * innenfor oppgitt posisjon. I tillegg må spiller være i MS/vehicle.
     *
     * @param pos Posisjon som er sentrum.
     * @param faction Hvilken faction BOSS NPC tilhører.
     *
     * @return Liste over alle spillere som befinner seg innenfor oppgitt
     * område.
     */
    private LinkedList<NPCtarget> getPlayerList(Posisjon pos, int faction) {

        LinkedList<NPCtarget> liste = new LinkedList<NPCtarget>();

        Iterator<PlayerGame> spillere = gameServer.MultiClient.getAllPlayers();

        //Gå gjennom alle spillere og sjekk om noen av dem er innenfor oppgitt område.
        while (spillere.hasNext()) {

            PlayerGame p = spillere.next();

            if (p.getCharacter().getPosisjon().getZone() == pos.getZone() && p.getCharacter().getVehicle() != null
                    && (p.getCharacter().getFaction() != faction || p.getCharacter().isCriminal())) {

                //OK, spiller er fiendtlig. Sjekk om spiller er innenfor rekkevidde av 2000m.
                if (p.getCharacter().getPosisjon().distance(pos) < 8000) {
                    liste.add(new NPCtarget(p.getCharacter()));
                }
            }
        }

        return liste;
    }
}
