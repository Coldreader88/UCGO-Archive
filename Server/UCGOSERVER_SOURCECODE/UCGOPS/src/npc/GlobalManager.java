package npc;

import characters.Posisjon;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette er GlobalManager som håndterer alle NPCs som ikke tilhører et område.
 */
public class GlobalManager {

    /**
     * Alle NPCs i ground zone lagres her. KEY = NPC ID.
     */
    private static HashMap<Integer, NPC> npcGround = new HashMap<Integer, NPC>();

    /**
     * Alle NPCs i space zone lagres her. KEY = NPC ID.
     */
    private static HashMap<Integer, NPC> npcSpace = new HashMap<Integer, NPC>();

    /**
     * Oppretter og REGISTRERER en ny NPC i MS/vehicle basert på template.
     *
     * @param t Template NPC skal baseres på.
     * @param pos Hvor NPC skal plasseres.
     * @param navn Navn på NPC.
     *
     * @return NPC som ble opprettet.
     */
    public static NPCvehicle addNpcVehicle(Template t, Posisjon pos, String navn) {

        int id; //NPC ID.

        //Finn et ledig ID for NPC.
        Random r = new Random();

        do {
            id = 0x06000000 + r.nextInt(0xFFFFFF);
        } while (npcGround.get(id) != null || npcSpace.get(id) != null);

        NPCvehicle n = new NPCvehicle(id, t, pos, navn);

        registerNpc(n);

        return n;
    }

    /**
     * Oppretter og REGISTRERER en ny NPC Boss basert på template.
     *
     * @param faction Faction NPC tilhører.
     * @param t Template NPC skal baseres på.
     * @param waypoints Waypoint liste NPC skal følge.
     * @param navn Navn på NPC.
     *
     * @return NPC som ble opprettet.
     */
    public static NPCboss addNpcBoss(int faction, Template t, Posisjon waypoints[], String navn) {

        int id; //NPC ID.

        //Finn et ledig ID for NPC.
        Random r = new Random();

        do {
            id = 0x06000000 + r.nextInt(0xFFFFFF);
        } while (npcGround.get(id) != null || npcSpace.get(id) != null);

        NPCboss n = new NPCboss(id, faction, t, waypoints, navn);

        registerNpc(n);

        return n;
    }

    /**
     * Oppretter og REGISTRERER ny NPC i human form.
     *
     * NB! Metoden returnerer opprettet NPC, dette objektet må brukes til å
     * sette utseende og andre instillinger for NPC.
     *
     * @param navn Navn til NPC
     * @param rank Rank til NPC
     * @param faction Faction NPC tilhører
     * @param pos Hvor NPC skal plasseres
     *
     * @return NPC som ble opprettet. Bruk dette til å endre instillinger for
     * NPC.
     */
    public static NPChuman addNpcHuman(String navn, int rank, int faction, Posisjon pos) {

        int id; //NPC ID.

        //Finn et ledig ID for NPC.
        Random r = new Random();

        do {
            id = 0x06000000 + r.nextInt(0xFFFFFF);
        } while (npcGround.get(id) != null || npcSpace.get(id) != null);

        NPChuman n = new NPChuman(id, navn, rank, faction, pos);

        registerNpc(n);

        return n;
    }

    /**
     * Registrerer en NPC enten i npcGround eller npcSpace, avhengig av zone i
     * NPC sin posisjon.
     *
     * @param n NPC som skal registreres.
     */
    private static synchronized void registerNpc(NPC n) {

        if (n.getPosisjon().getZone() == 1) {
            npcGround.put(n.getID(), n);
        } else if (n.getPosisjon().getZone() == 2) {
            npcSpace.put(n.getID(), n);
        }
    }

    /**
     * Returnerer en liste over alle NPCs innen gitt radius fra oppgitt
     * posisjon.
     *
     * @param pos
     * @param radius
     *
     * @return NPCs innenfor radius.
     */
    public static LinkedList<NPC> getNpcList(Posisjon pos, long radius) {

        LinkedList<NPC> resultat = new LinkedList<NPC>();

        Iterator<NPC> npcs;

        if (pos.getZone() == 1) {
            npcs = npcGround.values().iterator();
        } else {
            npcs = npcSpace.values().iterator();
        }

        while (npcs.hasNext()) {

            NPC n = npcs.next();

            long avstand = beregnAvstand(n.getPosisjon(), pos);

            if (avstand <= radius && n.isActive()) {
                resultat.add(n);
            }
        }

        return resultat;
    }

    /**
     *
     * @return Liste over alle NPC som håndteres av GlobalManager.
     */
    public static LinkedList<NPC> getNpcList() {

        LinkedList<NPC> npcListe = new LinkedList<NPC>(npcGround.values());
        npcListe.addAll(npcSpace.values());

        return npcListe;
    }

    /**
     * Returnerer NPC med oppgitt ID.
     *
     * @param id ID til NPC.
     *
     * @return NPC eller NULL dersom NPC ikke funnet.
     */
    public static NPC getNpc(int id) {

        NPC n;

        n = npcGround.get(id);

        if (n != null) {
            return n;
        } else {
            return npcSpace.get(id);
        }
    }

    /**
     * Beregner avstanden mellom to posisjoner. Framgangsmåte avhenger av zone.
     * Ground zone bruker kun X,Y aksene, space zone bruker X,Y,Z.
     *
     * @param pA
     * @param pB
     *
     * @return Avstand mellom pA og pB
     */
    private static long beregnAvstand(Posisjon pA, Posisjon pB) {

        long resultat;

        if (pA.getZone() == 2) {
            resultat = pA.distance(pB);
        } else {
            long dx = Math.abs(pA.getX() - pB.getX());
            long dy = Math.abs(pA.getY() - pB.getY());

            resultat = (long) Math.sqrt(dx * dx + dy * dy);
        }

        return resultat;
    }

    /**
     * Spawner en NPC boss som beveger seg langs en "rektangulær path". Denne
     * metoden oppretter fire waypoints som NPC boss vil følge og de vil forme
     * et rektangel. Oppgitt x,y er sentrum for rektangelet og waypointene vil
     * plasseres på
     * (x-sizeX,y-sizeY),(x+sizeX,y-sizeY),(x-sizeX,y+sizeY),(x+sizeX,y+sizeY).
     *
     * NPC vil bevege seg rundt rektangelet fra øverste venstre hjørne, til
     * høyre hjørne, ned til høyre hjørne, bort til nederte venstre hjørne og
     * opp til venstre hjørne igjen.
     *
     * @param faction Faction NPC tilhører.
     * @param t NPC template som skal brukes.
     * @param navn Navn på NPC.
     * @param x Senter for rektangelet som utgjør NPC sin path, X posisjon.
     * @param y Senter for rektangelet som utgjør NPC sin path, Y posisjon.
     * @param sizeX Størrelsen på rektangelet langs X, i BEGGE RETNINGER!
     * @param sizeY Størrelsen på rektangelet langs Y, i BEGGE RETNINGER!
     * @param zone = Hvilken zone NPC er i.
     */
    public static void spawnNpcBoss(int faction, Template t, String navn, int x, int y, int sizeX, int sizeY, int zone) {

        Posisjon[] wp = new Posisjon[4];

        //Øverste venstre hjørne.
        wp[0] = new Posisjon(x - sizeX, y + sizeY, 0, 0, zone);
        //Øverste høyre hjørne.
        wp[1] = new Posisjon(x + sizeX, y + sizeY, 0, 0, zone);
        //Nederste høyre hjørne.
        wp[2] = new Posisjon(x + sizeX, y - sizeY, 0, 0, zone);
        //Nederste venstre hjørne.
        wp[3] = new Posisjon(x - sizeX, y - sizeY, 0, 0, zone);

        addNpcBoss(faction, t, wp, navn);
    }

}
