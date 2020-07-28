package loginServer;

import java.util.Hashtable;
import opcodes.Opcode;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dennne klassen vil registrere opcodes for login serveren i et hashtable.
 */
public class registerOpcodes {

    /**
     *
     * @param op Hashtable der opcodes skal registreres.
     */
    public static void register(Hashtable<Integer, Opcode> op) {

        op.put(0x30000, new opcodes.loginServer.Opcode0x30000());
        op.put(0x30001, new opcodes.loginServer.Opcode0x30001());
        op.put(0x30002, new opcodes.loginServer.Opcode0x30002());
        op.put(0x30003, new opcodes.loginServer.Opcode0x30003());
        op.put(0x30004, new opcodes.loginServer.Opcode0x30004());
        op.put(0x30005, new opcodes.loginServer.Opcode0x30005());
    }
}
