package npc;

import characters.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen inneholder metoder for å håndtere NPC i et area. Metodene her
 * kan brukes både i space og ground.
 */
public abstract class AreaManager extends Area {

    //LinkedList nedenfor brukes til å holde oversikt over hvilke NPCs som finnes i Area.
    //En liste for hver type NPC oppførsel.
    //KEY=NPC ID, uten area ID!
    protected HashMap<Integer, NPCvehicle> npcGuard = new HashMap<Integer, NPCvehicle>();
    protected HashMap<Integer, NPCvehicle> npcMovingGuard = new HashMap<Integer, NPCvehicle>();
    protected HashMap<Integer, NPCvehicle> npcSupply = new HashMap<Integer, NPCvehicle>();
    protected HashMap<Integer, NPCvehicle> npcGrunt = new HashMap<Integer, NPCvehicle>();
    /**
     * Teller som holder neste npc ID som skal brukes, 0-4095.
     */
    private int npcID = 0;

    public AreaManager(int areaID, int faction, int zone, int posX, int posY, int posZ, int iX, int iY, int iZ, int yX, int yY, int yZ) {
        super(areaID, faction, zone, posX, posY, posZ, iX, iY, iZ, yX, yY, yZ);
    }

    /**
     * Oppretter nytt area beregnet for NPC vs NPC bruk.
     */
    public AreaManager(int areaID, int zone, int posX, int posY, int posZ, int iX, int iY, int iZ, int yX, int yY, int yZ) {
        super(areaID, true, zone, posX, posY, posZ, iX, iY, iZ, yX, yY, yZ);
    }

    /**
     * Oppretter en ny NPC i området med default faction.
     *
     * Denne metoden skal ikke brukes til å respawne NPCs.
     *
     * @param t Template NPC er basert på
     * @param pos Posisjon til NPC. Må være innenfor området.
     * @param navn Navn til NPC. Kan være blank, "".
     * @param teamID Team NPC er med i, -1=No team.
     *
     * @return NPC som ble opprettet eller NULL hvis feil.
     */
    public NPCvehicle addNpc(Template t, Posisjon pos, String navn, int teamID) {
        return this.spawnNpc(t, pos, navn, teamID, faction);
    }

    /**
     * Oppretter en ny NPC i området.
     *
     * Denne metoden skal ikke brukes til å respawne NPCs.
     *
     * @param t Template NPC er basert på
     * @param pos Posisjon til NPC. Må være innenfor området.
     * @param navn Navn til NPC. Kan være blank, "".
     * @param teamID Team NPC er med i, -1=No team.
     * @param faction Faction NPC tilhører.
     *
     * @return NPC som ble opprettet eller NULL hvis feil.
     */
    public NPCvehicle addNpc(Template t, Posisjon pos, String navn, int teamID, int faction) {
        return this.spawnNpc(t, pos, navn, teamID, faction);
    }

    /**
     * Spawner grunt NPCs i området på tilfeldige posisjoner med default
     * faction.
     *
     * @param t Template som skal brukes av alle grunt NPC som spawnes.
     * @param n Antall grunt NPCs som skal spawnes.
     */
    public abstract void addNpcGrunts(Template t, int n);

    /**
     * Spawner grunt NPCs i området på tilfeldige posisjoner.
     *
     * @param t Template som skal brukes av alle grunt NPC som spawnes.
     * @param n Antall grunt NPCs som skal spawnes.
     * @param faction Faction NPCs skal tilhøre.
     */
    public abstract void addNpcGrunts(Template t, int n, int faction);

    /**
     * Tar imot en liste over fiendtlige spillere i området og gir dette videre
     * til NPCs i området.
     *
     * @param spillere Liste over fiendtlige spillere som er innenfor området.
     */
    public void chooseTargets(LinkedList<NPCtarget> spillere) {

        Iterator<NPCvehicle> npcs;

        npcs = this.npcGuard.values().iterator();
        this.chooseTargets(npcs, spillere);

        npcs = this.npcMovingGuard.values().iterator();
        this.chooseTargets(npcs, spillere);

        npcs = this.npcGrunt.values().iterator();
        this.chooseTargets(npcs, spillere);

        //Supply NPC skal ikke angripe så bare ignorer dem.
    }

    /**
     * Tar imot liste over fiendtlige spillere og sender den til oppgitte NPCs.
     *
     * @param npcs NPCs som skal motta liste over fiendtlige spillere.
     * @param spillere Fiendtlige spillere.
     */
    private void chooseTargets(Iterator<NPCvehicle> npcs, LinkedList<NPCtarget> spillere) {

        while (npcs.hasNext()) {

            NPCvehicle n = npcs.next();

            if (n.isActive()) {
                n.chooseTarget(spillere.iterator(), this);
            }
        }

    }

    /**
     * Går gjennom alle NPCs i området og lar dem gjøre det de skal.
     *
     */
    public void performAI() {

        Iterator<NPCvehicle> npcs;

        npcs = this.npcGuard.values().iterator();
        this.performAI(npcs);

        npcs = this.npcMovingGuard.values().iterator();
        this.performAI(npcs);

        npcs = this.npcGrunt.values().iterator();
        this.performAI(npcs);

        //Supply NPCs skal ikke gjøre noe som helst.
    }

    /**
     * Går gjennom mottatte NPC og lar dem gjøre det de skal.
     *
     * @param npcs NPCs som skal gjøre noe.
     */
    private void performAI(Iterator<NPCvehicle> npcs) {

        while (npcs.hasNext()) {

            NPCvehicle n = npcs.next();

            if (n.isActive() && n.getMachineDamage() < 100) {
                n.performAI(this);
            }
        }
    }

    /**
     * Spawner en NPC uten navn og team med default faction.
     *
     * @param t
     * @param pos
     * @return
     */
    protected NPCvehicle spawnNpc(Template t, Posisjon pos) {
        return this.spawnNpc(t, pos, "", -1, faction);
    }

    /**
     * Spawner en NPC uten team med default faction.
     *
     * @param t
     * @param pos
     * @return
     */
    protected NPCvehicle spawnNpc(Template t, Posisjon pos, String navn) {
        return this.spawnNpc(t, pos, navn, -1, faction);
    }

    /**
     * Oppretter en ny NPC basert på oppgitt template og plasserer NPC på
     * oppgitt posisjon.
     *
     * NPC vil ikke bli spawnet dersom posisjon er utenfor area.
     *
     * NB! Denne metoden skal kun brukes på nye NPCs som opprettes ved server
     * start. Den skal IKKE brukes til å respawne NPCs.
     *
     * @param t Template NPC skal baseres på.
     * @param pos Hvor NPC skal spawnes.
     * @param navn Navn på NPC.
     * @param teamID Team NPC er med i, -1=No team.
     * @param faction Faction NPC skal tilhøre.
     *
     * @return NPC som ble opprettet, NULL hvis feil.
     */
    protected NPCvehicle spawnNpc(Template t, Posisjon pos, String navn, int teamID, int faction) {

        boolean a;

        //Sjekk at oppgitt posisjon er innenfor området.
        if (pos.getZone() == 1) {
            a = this.isWithinArea(pos.getX(), pos.getY());
        } else {
            a = this.isWithinArea(pos.getX(), pos.getY(), pos.getZ());
        }

        if (!a) {
            System.out.println("AreaManager Error: Given position outside area. Area ID:" + this.areaID);
            return null;
        }

        int npcID = this.getNpcID();

        NPCvehicle n = new NPCvehicle(npcID, t, pos, navn, teamID);
        n.setFaction(faction);

        //Plasser NPC i rett liste avhengig av oppførsel.
        switch (t.getBehavior()) {

            case Guard:
                this.npcGuard.put(npcID, n);
                break;

            case MovingGuard:
                this.npcMovingGuard.put(npcID, n);
                break;

            case Supply:
                this.npcSupply.put(npcID, n);
                break;

            case Grunt:
                this.npcGrunt.put(npcID, n);
                break;

            default:
                System.out.println("AreaManager Error: Invalid NPC behavior type: " + t.getBehavior());
                return null;
        }

        return n;
    }

    /**
     *
     * @return Neste NPC ID som skal brukes.
     */
    private synchronized int getNpcID() {

        int nID = 0x05000000;

        nID |= this.areaID << 12;

        nID |= this.npcID & 0xFFF;

        this.npcID++;

        return nID;
    }

    /**
     * Returnerer en liste over alle NPCs innenfor radius fra oppgitt posisjon.
     *
     * @param pos
     * @param radius
     * @return NPCs innenfor oppgitt radius.
     */
    public LinkedList<NPC> getNpcList(Posisjon pos, long radius) {

        LinkedList<NPC> resultat = new LinkedList<NPC>();

        //Samle alle NPC i en liste.
        LinkedList<NPC> npcListe = new LinkedList<NPC>();
        npcListe.addAll(this.npcGuard.values());
        npcListe.addAll(this.npcMovingGuard.values());
        npcListe.addAll(this.npcSupply.values());
        npcListe.addAll(this.npcGrunt.values());

        Iterator<NPC> npcs = npcListe.iterator();

        while (npcs.hasNext()) {

            NPC n = npcs.next();

            long avstand = n.getPosisjon().distance(pos);

            if (avstand <= radius && n.isActive()) {
                resultat.add(n);
            }
        }

        return resultat;
    }

    /**
     *
     * @return En liste over alle mulige NPC mål innenfor området.
     */
    public LinkedList<NPCtarget> getAllNpc() {

        LinkedList<NPCtarget> npcList = new LinkedList<>();

        //Gå gjennom alle NPCs i området og legg dem til i listen.
        Iterator<NPCvehicle> npcs = npcGuard.values().iterator();
        while (npcs.hasNext()) {
            npcList.add(new NPCtarget(npcs.next()));
        }

        npcs = npcMovingGuard.values().iterator();
        while (npcs.hasNext()) {
            npcList.add(new NPCtarget(npcs.next()));
        }

        npcs = npcSupply.values().iterator();
        while (npcs.hasNext()) {
            npcList.add(new NPCtarget(npcs.next()));
        }

        npcs = npcGrunt.values().iterator();
        while (npcs.hasNext()) {
            npcList.add(new NPCtarget(npcs.next()));
        }

        return npcList;
    }

    /**
     *
     * @param npcID ID til en NPC. Fullt ID på format 0x05xxxyyy
     *
     * @return NPC med oppgitt ID, eller NULL hvis ikke funnet.
     */
    public NPC getNpc(int npcID) {

        NPC n = this.npcGrunt.get(npcID);

        if (n == null) {
            n = this.npcGuard.get(npcID);
        }
        if (n == null) {
            n = this.npcMovingGuard.get(npcID);
        }
        if (n == null) {
            n = this.npcSupply.get(npcID);
        }

        return n;
    }

    /**
     * Denne metoden går gjennom alle NPCs og sjekker om noen av dem skal
     * repawnes.
     *
     * NB! VANLIG NPCs, dvs npcGrunt, BLIR IKKE RESPAWNET ENNÅ!
     *
     */
    public void respawn() {

        //Gå gjennom alle Guard, MovingGuard og Supply NPCs og sjekk om noen av dem skal respawnes.
        this.respawn(this.npcGuard.values().iterator());
        this.respawn(this.npcSupply.values().iterator());
        this.respawn(this.npcMovingGuard.values().iterator());

        this.respawnGrunts();
    }

    /**
     * Går gjennom gitte NPCs og sjekker om noen skal respawnes.
     *
     * NB! Alle NPCs respawnes på start posisjonen.
     *
     * @param npcs
     */
    private void respawn(Iterator<NPCvehicle> npcs) {

        int time = (int) (System.currentTimeMillis() / 1000);

        while (npcs.hasNext()) {

            NPCvehicle n = npcs.next();

            if (!n.isActive() && (n.getKillTime() + config.Npc.respawnTime) < time) {
                n.respawn();
            }

        }

    }

    /**
     * Går gjennom listen over grunt NPCs og sjekke om noen skal repawnes.
     *
     */
    private void respawnGrunts() {

        int time = (int) (System.currentTimeMillis() / 1000);

        Iterator<NPCvehicle> npcs = this.npcGrunt.values().iterator();

        while (npcs.hasNext()) {

            NPCvehicle n = npcs.next();

            if (!n.isActive() && (n.getKillTime() + config.Npc.respawnTime) < time) {
                this.respawnGruntNPC(n);
            }
        }
    }

    /**
     * Respawner oppgitt NPC på en tilfeldig posisjon innenfor området.
     *
     * @param n
     */
    protected abstract void respawnGruntNPC(NPCvehicle n);

    /**
     * Sjekker om oppgitt x,y,z posisjon er ledig eller om en NPC er der.
     *
     * x,y,z er spill koordinater og er absolutt. De er ikke relativ til sentrum
     * av området.
     *
     * En posisjon er ledig dersom det ikke er en NPC innenfor en radius av 100
     * game units.
     *
     * @param x
     * @param y
     * @param z
     * @param npc Ignorer denne NPCen. Dersom den er på posisjonen vil
     * posisjonen likevel regnes som ledig. Kan være null.
     *
     * @return true dersom ledig, false hvis ikke.
     */
    protected boolean posisjonLedig(int x, int y, int z, NPC npc) {

        if (!posisjonLedigSjekk(npcGuard.values().iterator(), x, y, z, npc)) {
            return false;
        }
        if (!posisjonLedigSjekk(npcMovingGuard.values().iterator(), x, y, z, npc)) {
            return false;
        }
        if (!posisjonLedigSjekk(npcGrunt.values().iterator(), x, y, z, npc)) {
            return false;
        }

        //Supply NPCs er ikke tatt med fordi det kan lage problemer for vaktene i supplyet.
        return true;
    }

    /**
     * Brukes av metoden posisjonLedig(). Går gjennom listen av NPCs og sjekker
     * om posisjonen x,y,z er ledig.
     *
     * En posisjon er ledig dersom det ikke finnes NPCs innenfor en radius av 50
     * game units.
     *
     * @param npcListe
     * @param x
     * @param y
     * @param z
     * @param npc Ignorer denne NPCen. Dersom den er på posisjonen vil
     * posisjonen likevel regnes som ledig. Kan være null.
     *
     * @return true dersom ledig, false hvis ikke.
     */
    private boolean posisjonLedigSjekk(Iterator<NPCvehicle> npcListe, int x, int y, int z, NPC npc) {

        Posisjon pos = new Posisjon(x, y, z, 0, 0);

        while (npcListe.hasNext()) {

            NPC n = npcListe.next();

            if (npc != n) {

                long avstand = n.getPosisjon().distance(pos);

                if (avstand <= 50 && n.isActive()) {
                    return false;
                }
            }

        }

        return true;
    }
}
