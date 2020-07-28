package containers;

import items.*;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import packetHandler.PacketData;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen representerer en hovedcontainer. En container som inneholder
 * item containere.
 *
 */
public class HovedContainer implements Container {

    private static final long serialVersionUID = 1L;

    //Container ID.
    private int container_id;

    //INT som følger container ID, vanligvis 0x13 eller 0x14 (default).
    private int container_tail = 0x14;

    //Statisk (item) ID for container. 
    private int statisk_id;

    //Antall. Brukes kun av money og credit containere. For alle andre er den 1.
    private long antall = 1;

    //Timestamp, når containeren ble opprettet.
    private int create_time;

    //Timestamp, sist gang containeren ble endret på.
    private int modify_time = -1;

    //Crafting string, settes inn etter første 0x80 teller i pakke data. 
    private String craftingString = null;

    //Innhold i containeren lagres her.
    private ConcurrentLinkedQueue<ItemContainer> innhold = new ConcurrentLinkedQueue<ItemContainer>();

    //Hovedcontainere lagret i denne containeren. Brukes hovedsaklig av swappack/trade.
    private ConcurrentLinkedQueue<HovedContainer> hc_innhold = new ConcurrentLinkedQueue<HovedContainer>();

    /**
     *
     * @param container_id Container ID denne containeren skal bruke.
     */
    public HovedContainer(int container_id) {
        this.container_id = container_id;
        this.create_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Legger en item container inn i denne containeren.
     *
     * Hvis itemcontainer allerede finnes i denne containeren vil den ikke bli
     * lagt til.
     *
     * @param ic Item container.
     */
    public void addItemContainer(ItemContainer ic) {
        if (!this.innhold.contains(ic)) {
            this.innhold.add(ic);
        }
        this.modify_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Legger en hoved container inn i denne containeren.
     *
     * @param hc Hovedcontainer.
     */
    public void addHovedContainer(HovedContainer hc) {
        this.hc_innhold.add(hc);
        this.modify_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Fjerner en item container fra denne hovedcontaineren.
     *
     * Hvis hovedcontainer inneholder flere kopier av denne IC vil alle kopier
     * bli fjernet.
     *
     * @param ic Item container.
     */
    public void removeItemContainer(ItemContainer ic) {
        while (this.innhold.contains(ic)) {
            this.innhold.remove(ic);
        }
        this.modify_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Fjerner en hoved container fra denne containeren.
     *
     * @param hc Hoved container.
     */
    public void removeHovedContainer(HovedContainer hc) {
        this.hc_innhold.remove(hc);
        this.modify_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Setter innholdet i container. Eventuelle itemcontainere som allerede er
     * lagret vil bli fjernet fra innholdet og erstattet med nytt innhold.
     *
     * @param contents Nytt innhold for container.
     */
    public void setContents(ConcurrentLinkedQueue<ItemContainer> contents) {
        if (contents != null) {
            this.innhold = contents;
        }
        this.modify_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Setter innholdet av hovedcontainere i container. Eventuelle
     * hovedcontainere allerede lagret vil bli fjernet og erstattet med nytt
     * innhold.
     *
     * @param contents Nytt innhold.
     */
    public void setContentsHC(ConcurrentLinkedQueue<HovedContainer> contents) {
        if (contents != null) {
            this.hc_innhold = contents;
        }
        this.modify_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Returnerer alle item containere i denne containeren.
     *
     * @return Denne containerens inventory.
     */
    public ConcurrentLinkedQueue<ItemContainer> getContents() {
        return this.innhold;
    }

    /**
     * Returnerer alle hoved containere i denne containeren.
     *
     * @return Alle hoved containere lagret i denne containeren.
     */
    public ConcurrentLinkedQueue<HovedContainer> getContentsHC() {
        return this.hc_innhold;
    }

    /**
     * Sjekker om denne HovedContaineren inneholder oppgitt ItemContainer.
     *
     * @param ic ItemContainer vi skal se etter.
     *
     * @return true hvis inneholder oppgitt ItemContainer, false hvis ikke.
     */
    public boolean containsItemContainer(ItemContainer ic) {
        return this.innhold.contains(ic);
    }

    /**
     * Returnerer første itemcontainer som inneholder en item med oppgitt ID.
     *
     * @param item_id ID for item containeren skal inneholde.
     * @return ItemContainer eller null hvis item ikke finnes i innholdet.
     */
    public ItemContainer getItemContainer(int item_id) {

        ItemContainer ic = null;

        Iterator<ItemContainer> i = this.innhold.iterator();
        while (i.hasNext()) {
            ic = i.next();
            if (ic.getItem().getID() == item_id) {
                return ic;
            }
        }

        return null;
    }

    /**
     * Tømmer containerens inventory. Itemcontainere i inventory vil ikke bli
     * endret på og må eventuelt slettes før denne metoden kalles.
     *
     * HovedContainere lagret blir ikke fjernet.
     */
    public void emptyContainer() {
        this.innhold.clear();
        this.modify_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Setter antall for containeren.
     *
     * @param antall Antall.
     */
    public void settAntall(long antall) {
        this.antall = antall;
        this.modify_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Returnerer antall for containeren.
     *
     * @return Antall.
     */
    public long getAntall() {
        return this.antall;
    }

    /**
     * Setter statisk (item) ID for container.
     *
     * @param id Statisk ID.
     */
    public void settStatiskID(int id) {
        this.statisk_id = id;
    }

    /**
     * Returnerer item id for container.
     *
     * @return Item ID (statisk id)
     */
    public int getStatiskID() {
        return this.statisk_id;
    }

    /**
     * Setter container tail, delen som følger container ID.
     *
     * @param tail Container tail, vanligvis 0x13 eller 0x14.
     */
    public void settContainerTail(int tail) {
        this.container_tail = tail;
    }

    /**
     * Returnerer container tail.
     *
     * @return Container tail.
     */
    public int getContainerTail() {
        return this.container_tail;
    }

    /**
     * Setter string som skal skrives ut i første 0x80 teller. Brukes av
     * productive container.
     *
     * @param s String som skal skrives.
     */
    public void setCraftingString(String s) {
        this.craftingString = s;
    }

    public byte[] getData() {

        //Før vi forteller klienten om innholdet i containeren sjekk at den ikke inneholder
        //ugyldige item containere.
        this.cleanUp();

        PacketData pd = new PacketData();

        pd.writeIntBE(this.container_id);
        pd.writeIntBE(this.container_tail);
        pd.writeLongBE(this.antall);
        pd.writeIntBE(this.statisk_id);
        pd.writeIntBE(this.create_time);
        pd.writeIntBE(this.modify_time);

        //Sjekk om vi skal skrive ut crafting string.
        if (this.craftingString != null) {
            pd.writeByte((byte) (0x80 + this.craftingString.length()));
            pd.writeStringUTF16LE(this.craftingString);
        } else {
            pd.writeByte((byte) 0x80);
        }

        pd.writeByteMultiple((byte) 0x80, 5);

        //Skriv ut liste over innholdet.
        if (this.container_tail == 0x14) {
            pd.writeByte((byte) (0x80 + this.innhold.size() + this.hc_innhold.size()));
            //List opp alle hovedcontainere.
            Iterator<HovedContainer> h = this.hc_innhold.iterator();

            while (h.hasNext()) {
                HovedContainer hc = h.next();
                pd.writeIntBE(hc.getID());
                pd.writeIntBE(hc.getContainerTail());
                pd.writeIntBE(hc.getStatiskID());
            }

            //List opp alle itemcontainerene.
            Iterator<ItemContainer> i = this.innhold.iterator();

            while (i.hasNext()) {
                ItemContainer ic = i.next();
                Item ting = ic.getItem();
                int item_id = -1;
                if (ting != null) {
                    item_id = ting.getID();
                }

                pd.writeIntBE(ic.getID());
                pd.writeIntBE(ic.getContainerTail());
                pd.writeIntBE(item_id);
            }
        }

        return pd.getData();
    }

    public int getID() {
        return this.container_id;
    }

    /**
     * Denne metoden går gjennom alle item containere i this.innhold og sjekker
     * at antallet deres er gyldig. Item containere med antall <= 0 vil bli
     * fjernet.
     */
    private void cleanUp() {

        Iterator<ItemContainer> icListe = this.innhold.iterator();

        while (icListe.hasNext()) {

            ItemContainer ic = icListe.next();

            if ((ic.getAntall() <= 0 && ic.getItem() instanceof GeneralItem)) {
                ContainerList.removeContainer(ic.getID());
                this.removeItemContainer(ic);
            }

        }
    }

    /**
     * Sammenligner KUN basert på container ID. Innholdet i container er
     * uvesentlig.
     */
    public int compareTo(Container arg0) {

        if (this == arg0) {
            return 0;
        }

        if (this.container_id < arg0.getID()) {
            return -1;
        }
        if (this.container_id > arg0.getID()) {
            return 1;
        }

        return 0;
    }

    protected void finalize() throws Throwable {

        this.innhold.clear();
        this.hc_innhold.clear();
        this.innhold = null;
        this.hc_innhold = null;
    }

}
