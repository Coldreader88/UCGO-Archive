package infoServer;

import java.util.Hashtable;
import opcodes.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dennne klassen vil registrere opcodes for info serveren i et hashtable.
 */
public class registerOpcodes {

    /**
     *
     * @param op Hashtable der opcodes skal registreres.
     */
    public static void register(Hashtable<Integer, Opcode> op) {

        op.put(0x00, new opcodes.infoServer.Opcode0x00());

    }
}
