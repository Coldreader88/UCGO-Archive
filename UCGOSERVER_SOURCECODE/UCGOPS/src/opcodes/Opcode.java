package opcodes;

import packetHandler.*;
import players.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette interfacet SKAL brukes av alle klasser som håndterer UCGO pakker.
 *
 */
public interface Opcode {

    /**
     * Utfører nødvendige operasjoner for å håndtere en mottatt UCGO pakke.
     *
     * @param p Brukeren som mottok denne pakken.
     * @param pakke Pakken.
     */
    public void execute(Player p, Packet pakke);
}
