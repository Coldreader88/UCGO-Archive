package gameServer;

import characters.Posisjon;
import containers.ItemContainer;
import java.util.Date;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde informasjon om en item. Brukes av
 * ItemHandler klassen.
 */
public class ItemCapsule {

    //Container der item er plassert.
    private ItemContainer item;

    //Plassering for item.
    private Posisjon pos;

    //Character ID for eier av item eller hvem som plasserte item. -1= ingen eier (dvs /release brukt)
    //Satt til 0 hvis ingen eier, f.eks spawned item.
    private int owner = 0;

    //Timestamp når item ble plassert.
    private int tid_plassert;

    //Timestamp når eierskap for item går ut.
    private int tid_owner_expire;

    //Dersom item er wreckage container angir dette hvem som sist åpnet den og tidspunket.
    private int wreckCharacterID, wreckTime;

    /**
     * Alle parametere tilsvarer de private feltene. tid_plassert settes
     * automatisk av constructor.
     *
     * @param item
     * @param pos
     * @param owner
     * @param tid_owner_expire
     */
    public ItemCapsule(ItemContainer item, Posisjon pos, int owner, int tid_owner_expire) {

        this.item = item;
        this.pos = pos;
        this.owner = owner;
        this.tid_owner_expire = tid_owner_expire;

        Date t = new Date();

        this.tid_plassert = (int) (t.getTime() / 1000L);
    }

    public ItemContainer getItem() {
        return this.item;
    }

    public Posisjon getPosisjon() {
        return this.pos;
    }

    public int getOwner() {
        return this.owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getTidPlassert() {
        return this.tid_plassert;
    }

    public void setTidPlasser(int tid_plassert) {
        this.tid_plassert = tid_plassert;
    }

    public int getTidOwnerExpire() {
        return this.tid_owner_expire;
    }

    public void setTidOwnerExpire(int tid_owner_expire) {
        this.tid_owner_expire = tid_owner_expire;
    }

    /**
     * Forsøker å åpne wreckage container. Hvis OK blir wreckage låst til
     * oppgitt spiller slik at ingen andre kan åpne wreckage.
     *
     * @param characterID Spiller som ønsker å åpne wreckage container.
     *
     * @return TRUE hvis OK og spiller kan få åpne den, FALSE hvis noen andre
     * har åpnet wreckage.
     */
    public synchronized boolean lock(int characterID) {

        int time = (int) (System.currentTimeMillis() / 1000);

        /*
		 * Sjekk om spiller allerede har åpnet wreckage, eller at ingen andre har åpnet wreckage.
         */
        if (this.wreckCharacterID == characterID || (this.wreckTime + config.Server.wreckage_lock_timeout) < time) {
            //OK, spiller kan åpne wreckage.
            this.wreckCharacterID = characterID;
            this.wreckTime = time;
            return true;
        } else {
            return false;
        }
    }

}
