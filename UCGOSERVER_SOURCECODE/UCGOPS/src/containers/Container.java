package containers;

import java.io.Serializable;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Alle container objekter skal implementere dette interfacet.
 *
 */
public interface Container extends Comparable<Container>, Serializable {

    /**
     * Denne metoden returnerer et byte array med pakke data angående innholdet
     * i denne containeren.
     *
     * @return Data for container. Klar til å settes inn i pakke.
     */
    byte[] getData();

    /**
     * Denne metoden returnerer container ID.
     *
     * @return Container ID.
     */
    int getID();

    /**
     * Setter antall for containeren.
     *
     * @param antall Antall.
     */
    void settAntall(long antall);

    /**
     * Returnerer antall for containeren.
     *
     * @return Antall.
     */
    long getAntall();
}
