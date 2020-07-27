package serverComp;

import java.util.Iterator;
import java.util.LinkedList;
import players.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å håndtere alle tilkoblede spillere på server.
 *
 * @param <E> Et player objekt.
 */
public class PlayerHandler<E extends Player> implements Iterable<E> {

    /**
     * Alle spillere tilkoblet lagres her.
     */
    private LinkedList<E> spillere;

    public PlayerHandler() {
        this.spillere = new LinkedList<E>();
    }

    /**
     * Lagrer en spiller i listen over tilkoblede spillere.
     *
     * @param spiller Spiller som skal lagres.
     */
    public synchronized void add(E spiller) {
        spillere.add(spiller);
    }

    /**
     * Fjerner en spiller fra listen over tilkoblede spillere.
     *
     * @param spiller Spiller som skal fjernes.
     *
     * @return true hvis fjernet, false hvis ikke.
     */
    public synchronized boolean remove(E spiller) {
        return this.spillere.remove(spiller);
    }

    /**
     * Søker gjennom listen over tilkoblede spillere etter en spiller med
     * oppgitt account ID.
     *
     * @param account_id Account ID vi søker ettter.
     * @return Spiller som har oppgitt account ID, eller null hvis ikke funnet.
     */
    public synchronized E getPlayer(int account_id) {

        Iterator<E> liste = this.spillere.iterator();

        while (liste.hasNext()) {

            E spiller = liste.next();

            if (spiller.getAccountID() == account_id) {
                return spiller;
            }
        }

        return null;
    }

    /**
     * Returnerer en iterator over alle spillere tilkoblet. Iteratoren vil være
     * basert på en kopi av listen over spillere.
     */
    public synchronized Iterator<E> iterator() {

        LinkedList<E> kopi = new LinkedList<E>(this.spillere);

        return kopi.iterator();
    }

}
