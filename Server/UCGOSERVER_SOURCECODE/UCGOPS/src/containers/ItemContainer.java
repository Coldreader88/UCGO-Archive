package containers;

import items.*;
import java.util.Date;
import packetHandler.PacketData;
import validItems.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere en item container. En container som
 * inneholder EN item.
 */
public class ItemContainer implements Container {

    private static final long serialVersionUID = 1L;
    //Container ID.
    private int container_id;
    //Container tail. Delen som følger container ID, vanligvis 0x13 eller 0x14 (default).
    private int container_tail = 0x14;
    //Antall. Hvor mange av item container inneholder.
    private long antall = 1;
    //Timestamp, når containeren ble opprettet.
    private int create_time;
    //Timestamp, sist gang containeren ble endret på.
    private int modify_time = -1;
    //Item container inneholder.
    private Item innhold;

    /**
     *
     * @param container_id Container ID som skal brukes.
     */
    public ItemContainer(int container_id) {
        this.container_id = container_id;
        this.create_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Legger inn en item i containeren.
     *
     * @param i Item som skal legges inn.
     */
    public void addItem(Item i) {
        this.innhold = i;
        this.modify_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Returnerer item containeren inneholder.
     *
     * @return Item i containeren.
     */
    public Item getItem() {
        return this.innhold;
    }

    /**
     * Setter antall for item.
     *
     * @param antall Antall.
     */
    public void settAntall(long antall) {
        //Med unntak av penger nekter vi å sette antall over 2000 eller under 0.
        GameItem item = null;

        if (getItem() != null) {
            item = ItemList.getItem(getItem().getID());
            if (item == null) {
                //Item eksisterer ikke. Skal egentlig ikke være mulig.
                System.out.println("ERROR: ItemContainer.java, invalid item in container. Item ID:" + getItem().getID());
                return;
            }
        }

        if ((antall < 0 || antall > 2000) && item != null && item.getItemSubType() != ItemSubType.Money) {
            //Forsøk på å sette ugyldig antall i ItemContainer. Dump stack trace for å finne bug.
            //Thread.dumpStack();
            return;
        }

        this.antall = antall;
        this.modify_time = (int) (new Date().getTime() / 1000);
    }

    /**
     * Returnerer antall for item.
     *
     * @return Antall.
     */
    public long getAntall() {
        return this.antall;
    }

    /**
     * Setter container tail.
     *
     * @param tail Container tail.
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

    public byte[] getData() {

        PacketData pd = new PacketData();

        pd.writeIntBE(this.container_id);
        pd.writeIntBE(this.container_tail);
        pd.writeLongBE(this.antall);

        int item_id = 0;
        if (this.innhold != null) {
            item_id = this.innhold.getID();
        }

        pd.writeIntBE(item_id);
        pd.writeIntBE(this.create_time);
        pd.writeIntBE(this.modify_time);

        //Legg til data fra item.
        if (this.innhold != null) {
            pd.writeByteArray(this.innhold.getData());
        } else {
            pd.writeByteMultiple((byte) 0x80, 6); //Har ingen item, skriv tomme data.
        }
        return pd.getData();
    }

    public int getID() {
        return this.container_id;
    }

    public int getCreateTime() {
        return this.create_time;
    }

    public int getModifyTime() {
        return this.modify_time;
    }

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

        this.innhold = null;
    }
}
