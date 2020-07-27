package containers;

import items.Vehicle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;
import userDB.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette er hovedklassen for å håndtere containere. Denne klassen har en liste
 * over alle containere i spillet og har metoder for å opprette containere,
 * slette osv...
 *
 * NB! Alle metoder og felter skal være statiske. Det trengs kun en container
 * liste for serveren og det blir lettere for annen kode å få tilgang til
 * containere.
 */
public class ContainerList {

    //Alle containere lagres i dette settet. <ContainerID, Container>
    private static ConcurrentSkipListMap<Integer, Container> container_list = new ConcurrentSkipListMap<Integer, Container>();

    /**
     * Legger en allerede eksisterende container til i settet. Dersom container
     * allerede finnes i settet vil den nye containeren IKKE BLI LAGT TIL.
     *
     * NB! Viktig at ID til container er unikt. Denne metoden SKAL KUN BRUKES
     * FOR Å LEGGE INN HOVED CONTAINERE SOM BANK, HANGAR OSV.
     *
     * @param c Container.
     */
    public static void addContainer(Container c) {

        //Sjekk først at container ikke eksisterer.
        if (container_list.get(c.getID()) == null) {
            container_list.put(c.getID(), c);
        }
    }

    /**
     * Fjerner en container fra settet.
     *
     * NB! Eventuelle "child" containere vil ikke bli slettet.
     *
     * @param c Container ID for container som skal slettes.
     */
    public static void removeContainer(int c) {

        container_list.remove(c);
    }

    /**
     * Returnerer en container.
     *
     * @param c Container ID for container vi skal returnere.
     *
     * @return Container objekt, null hvis ikke funnet.
     */
    public static Container getContainer(int c) {
        return container_list.get(c);
    }

    /**
     * Oppretter en ny hoved container i settet og returner den. Containeren er
     * tom, kun container ID er satt. NB! MÅ IKKE BRUKES TIL
     * BANK,HANGAR,BACKPACK OSV...BRUKES FOR CONTAINERE SOM ms/vehicle inventory
     * o.l.
     *
     * @return Ny tom container.
     */
    public static HovedContainer newHovedContainer() {

        HovedContainer hc = null;
        int id;

        do {
            id = 0x10000000 + (int) (Math.random() * (double) 0xFFFFFFF);
            //Gå helt til vi har et unikt container ID.
        } while (container_list.get(id) != null);

        hc = new HovedContainer(id);

        container_list.put(id, hc);

        return hc;
    }

    /**
     * Oppretter en ny item container i settet og returnerer den. Containeren er
     * tom, kun container ID er satt.
     *
     * @return Ny tom container.
     */
    public static ItemContainer newItemContainer() {

        ItemContainer ic = null;
        int id;

        do {
            id = 0x10000000 + (int) (Math.random() * (double) 0xFFFFFFF);
            //Gå helt til vi har et unikt container ID.
        } while (container_list.get(id) != null);

        ic = new ItemContainer(id);

        container_list.put(id, ic);

        return ic;
    }

    /**
     * Lagrer container_list til fil.
     */
    public static synchronized void save() {

        saveCleanUp();

        String container_fil = config.Server.serverdata_file_location + "/containers.ser";
        String container_backup = config.Server.serverdata_file_location + "/containers";

        Calendar dato = new GregorianCalendar();

        container_backup += dato.get(Calendar.YEAR) + "-" + dato.get(Calendar.DAY_OF_MONTH) + "-" + dato.get(Calendar.MONTH) + "-";
        container_backup += dato.get(Calendar.HOUR) + "-" + dato.get(Calendar.MINUTE) + ".ser";

        admin.logging.globalserverMsg("Saving container data to file.");

        try {
            FileOutputStream fil = new FileOutputStream(container_fil);
            FileOutputStream fil_backup = new FileOutputStream(container_backup);

            //Skriv til containers.txt
            ObjectOutputStream out = new ObjectOutputStream(fil);
            out.writeObject(container_list);
            out.flush();
            out.reset();
            out.close();
            fil.close();

            //Skriv til backup fil.
            out = new ObjectOutputStream(fil_backup);
            out.writeObject(container_list);
            out.flush();
            out.reset();
            out.close();
            fil.close();
        } catch (Exception e) {
            e.printStackTrace();
            admin.logging.globalserverMsg("Error saving container data to file.");
        }
    }

    /**
     * Leser inn container data fra fil. Denne metoden SKAL kun brukes ved
     * server start.
     *
     * Denne metoden vil også rydde opp i innleste data. Gamle containere, dvs
     * items plassert på bakken og ting som ikke skal lagres vil bli fjernet.
     */
    @SuppressWarnings("unchecked")
    public static void load() {

        System.out.print("Loading container data from file...");

        try {

            //Innleste data lagres her midlertidig før de blir ryddet opp i.
            ConcurrentSkipListMap<Integer, Container> containere;

            String container_fil = config.Server.serverdata_file_location + "/containers.ser";

            FileInputStream fil = new FileInputStream(container_fil);
            ObjectInputStream in = new ObjectInputStream(fil);

            containere = (ConcurrentSkipListMap<Integer, Container>) in.readObject();

            fil.close();
            in.close();

            /*
             * Rydd opp i innleste container data. Containere som tilhører slettede characters eller items plassert
             * på bakken skal forkastes. Kun containere som tilhører eksisterende spillere skal beholdes.
             * 
             * Dette gjøres ved å gå gjennom alle eksisterende characters og registrere hovedcontainerene deres
             * i container_list.
             */
            Iterator<GameCharacter> characters = ManageCharacters.getAllGameCharacters();

            while (characters.hasNext()) {

                int cid = characters.next().getCharacterID();

                //Registrer alle hovedcontainere for character.
                HovedContainer hc;

                //Backpack
                hc = (HovedContainer) containere.get(cid + 1);
                flyttHovedContainer(hc);
                //Weared
                hc = (HovedContainer) containere.get(cid + 2);
                flyttHovedContainer(hc);
                //Bank
                hc = (HovedContainer) containere.get(cid + 3);
                flyttHovedContainer(hc);
                //Money
                hc = (HovedContainer) containere.get(cid + 4);
                flyttHovedContainer(hc);
                //Hangar
                hc = (HovedContainer) containere.get(cid + 5);
                flyttHovedContainer(hc);
                //Selfstorage
                hc = (HovedContainer) containere.get(cid + 6);
                flyttHovedContainer(hc);
                //Productive
                hc = (HovedContainer) containere.get(cid + 8);
                flyttHovedContainer(hc);
                //Swappack
                hc = (HovedContainer) containere.get(cid + 0xA);
                flyttHovedContainer(hc);
                //Credit
                hc = (HovedContainer) containere.get(cid + 0xB);
                flyttHovedContainer(hc);

                //Det er ikke nødvendig å flytte over trade vinduet, det ligger allerede i swappack.
            }

            containere.clear();
            containere = null;
            System.out.println("OK!");

        } catch (Exception e) {
            System.out.println("Error loading container data from file.");
        }

    }

    /**
     * Legger oppgitt hovedcontainer til i listen over registrerte containere. I
     * tillegg går metoden gjennom innholdet i container og registrerer enhver
     * item container og eventuelle hovedcontainere.
     *
     * @param hc Hovedcontainer som skal registreres.
     */
    private static void flyttHovedContainer(HovedContainer hc) {

        if (hc == null || !(hc instanceof HovedContainer)) {
            return;
        }

        addContainer(hc);

        //Gå gjennom alle items lagret i hovedcontaineren og registrer dem i listen.
        Iterator<ItemContainer> itemContainere = hc.getContents().iterator();

        while (itemContainere.hasNext()) {

            ItemContainer ic = itemContainere.next();

            addContainer(ic);

            //Dersom item i container er et vehicle skal også utstyrte items og inventory registreres.
            if (ic.getItem() instanceof Vehicle) {

                Vehicle v = (Vehicle) ic.getItem();

                //Inventory og utstyrte items lagres i hovedcontainere så her kan vi gjøre et rekursivt kall.
                flyttHovedContainer(v.getInventory());
                flyttHovedContainer(v.getWeaponsRoom());
            }
        }

        //Gå gjennom eventuelle hovedcontainere lagret i container.
        Iterator<HovedContainer> hovedContainere = hc.getContentsHC().iterator();

        while (hovedContainere.hasNext()) {

            //Dette kan gjøres rekursivt.
            flyttHovedContainer(hovedContainere.next());
        }

    }

    /**
     * Rydder opp i containere før de blir lagret til disk.
     *
     * Dette er samme prosessen som utføres ved loading. Alle containere untatt
     * de som tilhører characters blir fjernet.
     *
     */
    private static void saveCleanUp() {

        //Kopier eksisterende container liste.
        ConcurrentSkipListMap<Integer, Container> containere = container_list;

        //Tøm eksisterende container liste.
        container_list = new ConcurrentSkipListMap<Integer, Container>();

        /*
         * Rydd opp i container data. Containere som tilhører slettede characters eller items plassert
         * på bakken skal forkastes. Kun containere som tilhører eksisterende spillere skal beholdes.
         * 
         * Dette gjøres ved å gå gjennom alle eksisterende characters og registrere hovedcontainerene deres
         * i container_list.
         */
        Iterator<GameCharacter> characters = ManageCharacters.getAllGameCharacters();

        while (characters.hasNext()) {

            int cid = characters.next().getCharacterID();

            //Registrer alle hovedcontainere for character.
            HovedContainer hc;

            //Backpack
            hc = (HovedContainer) containere.get(cid + 1);
            flyttHovedContainer(hc);
            //Weared
            hc = (HovedContainer) containere.get(cid + 2);
            flyttHovedContainer(hc);
            //Bank
            hc = (HovedContainer) containere.get(cid + 3);
            flyttHovedContainer(hc);
            //Money
            hc = (HovedContainer) containere.get(cid + 4);
            flyttHovedContainer(hc);
            //Hangar
            hc = (HovedContainer) containere.get(cid + 5);
            flyttHovedContainer(hc);
            //Selfstorage
            hc = (HovedContainer) containere.get(cid + 6);
            flyttHovedContainer(hc);
            //Productive
            hc = (HovedContainer) containere.get(cid + 8);
            flyttHovedContainer(hc);
            //Swappack
            hc = (HovedContainer) containere.get(cid + 0xA);
            flyttHovedContainer(hc);
            //Credit
            hc = (HovedContainer) containere.get(cid + 0xB);
            flyttHovedContainer(hc);

            //Det er ikke nødvendig å flytte over trade vinduet, det ligger allerede i swappack.
        }

    }

    /**
     * Sjekker for ugyldige items o.l.
     *
     */
    public static void cheatCheck() {
        Iterator<Container> cl = container_list.values().iterator();

        while (cl.hasNext()) {

            Container c = cl.next();

            if (c instanceof ItemContainer) {
                ItemContainer ic = (ItemContainer) c;

                //Sjekk for 888 upgraded vehicles.
                if (ic.getItem() != null && ic.getItem() instanceof Vehicle) {
                    Vehicle v = (Vehicle) ic.getItem();
                    if (v.getTotalUpgrade() > 8) {
                        //v.applyDamage(1000000); //Mer enn 8 upgrades. Ødelegg vehicle.
                    }
                }

                //Sjekk at antall i container ikke overstiger 2000 eller er under 0.
                if (ic.getAntall() > 2000 || ic.getAntall() < 0) {
                    //Ugyldig antall.
                    System.out.println("Invalid amount of items in container. Amount:" + ic.getAntall() + ", Item ID:" + ic.getItem().getID());
                    ic.settAntall(0);
                    removeContainer(ic.getID());
                }

            }
        }
    }
}
