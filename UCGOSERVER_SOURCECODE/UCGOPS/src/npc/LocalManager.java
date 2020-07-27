package npc;

import characters.Posisjon;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å håndtere alle NPC områdene. Klassen holder
 * oversikt over alle områder, henter ut lister over hvilken NPCs en spiller
 * skal se o.l
 *
 */
public class LocalManager {

    /**
     * Alle vanlige NPC områder i ground zone lagres her. KEY=Area ID
     */
    private static HashMap<Integer, AreaManagerGround> groundArea = new HashMap<Integer, AreaManagerGround>();
    /**
     * Alle vanlige NPC områder i space zone lagres her.
     */
    private static HashMap<Integer, AreaManagerSpace> spaceArea = new HashMap<Integer, AreaManagerSpace>();
    /**
     * Alle NPC vs NPC områder i ground zone lagres her.
     */
    private static HashMap<Integer, AreaManagerGround> groundAreaNpcBattlefield = new HashMap<>();
    /**
     * Alle NPC vs NPC områder i space zone lagres her.
     */
    private static HashMap<Integer, AreaManagerSpace> spaceAreaNpcBattlefield = new HashMap<>();
    /**
     * Neste area ID som skal brukes, 1-4095.
     */
    private static int areaID = 0;

    /**
     * Oppretter et nytt ground area og lagrer det i listen over områder.
     *
     * @param faction Faction til området.
     * @param posX X posisjon
     * @param posY Y posisjon
     * @param iX Indre X størrelse
     * @param iY Indre Y størrelse
     * @param yX Ytre X størrelse
     * @param yY Ytre Y størrelse
     *
     * @return Det nye området.
     */
    public static AreaManagerGround addGroundArea(int faction, int posX, int posY, int iX, int iY, int yX, int yY) {

        int areaID = nextAreaID();

        AreaManagerGround a = new AreaManagerGround(areaID, faction, posX, posY, iX, iY, yX, yY);

        groundArea.put(areaID, a);

        return a;
    }

    public static AreaManagerGround addNpcBattlefieldGroundArea(int posX, int posY, int iX, int iY, int yX, int yY) {

        int areaID = nextAreaID();

        AreaManagerGround a = new AreaManagerGround(areaID, posX, posY, iX, iY, yX, yY);

        groundAreaNpcBattlefield.put(areaID, a);

        return a;
    }

    /**
     * Oppretter et nytt space area og lagrer det i listen over områder.
     *
     * @param faction Faction til området.
     * @param posX X posisjon
     * @param posY Y posisjon
     * @param posZ Z posisjon
     * @param iX Indre X størrelse
     * @param iY Indre Y størrelse
     * @param iZ Indre Z størrelse
     * @param yX Ytre X størrelse
     * @param yY Ytre Y størrelse
     * @param yZ Ytre Z størrelse
     *
     * @return Det nye området.
     */
    public static AreaManagerSpace addSpaceArea(int faction, int posX, int posY, int posZ, int iX, int iY, int iZ, int yX, int yY, int yZ) {

        int areaID = nextAreaID();

        AreaManagerSpace a = new AreaManagerSpace(areaID, faction, posX, posY, posZ, iX, iY, iZ, yX, yY, yZ);

        spaceArea.put(areaID, a);

        return a;
    }

    public static AreaManagerSpace addNpcBattlefieldSpaceArea(int posX, int posY, int posZ, int iX, int iY, int iZ, int yX, int yY, int yZ) {

        int areaID = nextAreaID();

        AreaManagerSpace a = new AreaManagerSpace(areaID, posX, posY, posZ, iX, iY, iZ, yX, yY, yZ);

        spaceAreaNpcBattlefield.put(areaID, a);

        return a;
    }

    /**
     *
     * @return Neste ledige AreaID
     */
    private synchronized static int nextAreaID() {

        areaID++;
        return areaID;
    }

    /**
     * Returnerer en liste over alle NPCs innen gitt radius fra oppgitt
     * posisjon.
     *
     * @param pos Sentrum, spillerens posisjon.
     *
     * @return Liste over alle NPCs spilleren kan se.
     */
    public static LinkedList<NPC> getNpcList(Posisjon pos, long radius) {

        //Alle NPCs som skal returneres lagres her.
        LinkedList<NPC> npcListe = new LinkedList<NPC>();

        Iterator<? extends AreaManager> areas;

        if (pos.getZone() == 1) {
            areas = groundArea.values().iterator();
        } else {
            areas = spaceArea.values().iterator();
        }

        while (areas.hasNext()) {

            AreaManager a = areas.next();

            //Hent ut NPCs dersom oppgitt posisjon er innenfor omådet.
            if (pos.getZone() == 1 && a.isWithinArea(pos.getX(), pos.getY())) {
                npcListe.addAll(a.getNpcList(pos, radius));
            } else if (pos.getZone() == 2 && a.isWithinArea(pos.getX(), pos.getY(), pos.getZ())) {
                npcListe.addAll(a.getNpcList(pos, radius));
            }

        }

        //Gå gjennom alle NPC vs NPC områder.
        if (pos.getZone() == 1) {
            areas = groundAreaNpcBattlefield.values().iterator();
        } else {
            areas = spaceAreaNpcBattlefield.values().iterator();
        }

        while (areas.hasNext()) {

            AreaManager a = areas.next();

            //Hent ut NPCs dersom oppgitt posisjon er innenfor omådet.
            if (pos.getZone() == 1 && a.isWithinArea(pos.getX(), pos.getY())) {
                npcListe.addAll(a.getNpcList(pos, radius));
            } else if (pos.getZone() == 2 && a.isWithinArea(pos.getX(), pos.getY(), pos.getZ())) {
                npcListe.addAll(a.getNpcList(pos, radius));
            }

        }

        return npcListe;
    }

    /**
     * Returnerer et område.
     *
     * @param areaID ID for området.
     *
     * @return Området eller NULL hvis området ikke finnes.
     */
    public static AreaManager getArea(int areaID) {

        AreaManager a = groundArea.get(areaID);

        if (a == null) {
            a = spaceArea.get(areaID);
        }

        if (a == null) {
            a = groundAreaNpcBattlefield.get(areaID);
        }

        if (a == null) {
            a = spaceAreaNpcBattlefield.get(areaID);
        }

        return a;
    }

    /**
     *
     * @return Iterator over alle ground areas registrert.
     */
    public static Iterator<AreaManagerGround> getGroundAreas() {
        return groundArea.values().iterator();
    }

    /**
     *
     * @return Iterator over alle NPC vs NPC ground areas registrert.
     */
    public static Iterator<AreaManagerGround> getNpcBattlefieldGroundAreas() {
        return groundAreaNpcBattlefield.values().iterator();
    }

    /**
     *
     * @return Iterator over alle space areas registrert.
     */
    public static Iterator<AreaManagerSpace> getSpaceAreas() {
        return spaceArea.values().iterator();
    }

    /**
     *
     * @return Iterator over all NPC vs NPC space areas registrert.
     */
    public static Iterator<AreaManagerSpace> getNpcBattlefieldSpaceAreas() {
        return spaceAreaNpcBattlefield.values().iterator();
    }

    /**
     * Returnerer en NPC som håndteres av en AreaManager.
     *
     * @param npcID ID til NPC. Fullt ID på formatet 0x05xxxyyy.
     *
     * @return NPC med oppgitt ID eller NULL hvis ikke funnet.
     */
    public static NPC getNpc(int npcID) {

        int areaID = (npcID >> 12) & 0xFFF;

        AreaManager a = getArea(areaID);

        if (a == null) {
            return null;
        }

        NPC n = a.getNpc(npcID);

        return n;
    }
}
