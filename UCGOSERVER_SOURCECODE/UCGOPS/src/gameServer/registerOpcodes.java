package gameServer;

import java.util.Hashtable;
import opcodes.Opcode;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dennne klassen vil registrere opcodes for game serveren i et hashtable.
 */
public class registerOpcodes {

    /**
     *
     * @param op Hashtable der opcodes skal registreres.
     */
    public static void register(Hashtable<Integer, Opcode> op) {

        op.put(0x0, new opcodes.gameServer.Opcode0x00());
        op.put(0x2, new opcodes.gameServer.Opcode0x02());
        op.put(0x3, new opcodes.gameServer.Opcode0x03());
        op.put(0x5, new opcodes.gameServer.Opcode0x05());
        op.put(0x6, new opcodes.gameServer.Opcode0x06());
        op.put(0x8, new opcodes.gameServer.Opcode0x08());
        op.put(0xA, new opcodes.gameServer.Opcode0x0A());
        op.put(0xB, new opcodes.gameServer.Opcode0x0B());
        op.put(0xC, new opcodes.gameServer.Opcode0x0C());
        op.put(0xD, new opcodes.gameServer.Opcode0x0D());
        op.put(0xF, new opcodes.gameServer.Opcode0x0F());
        op.put(0x11, new opcodes.gameServer.Opcode0x11());
        op.put(0x12, new opcodes.gameServer.Opcode0x12());
        op.put(0x13, new opcodes.gameServer.Opcode0x13());
        op.put(0x15, new opcodes.gameServer.Opcode0x15());
        op.put(0x16, new opcodes.gameServer.Opcode0x16());
        op.put(0x17, new opcodes.gameServer.Opcode0x17());
        op.put(0x18, new opcodes.gameServer.Opcode0x18());
        op.put(0x19, new opcodes.gameServer.Opcode0x19());
        op.put(0x1B, new opcodes.gameServer.Opcode0x1B());
        op.put(0x1C, new opcodes.gameServer.Opcode0x1C());
        op.put(0x1D, new opcodes.gameServer.Opcode0x1D());
        op.put(0x21, new opcodes.gameServer.Opcode0x21());
        op.put(0x22, new opcodes.gameServer.Opcode0x22());
        op.put(0x23, new opcodes.gameServer.Opcode0x23());
        op.put(0x24, new opcodes.gameServer.Opcode0x24());
        op.put(0x25, new opcodes.gameServer.Opcode0x25());
        op.put(0x26, new opcodes.gameServer.Opcode0x26());
        op.put(0x27, new opcodes.gameServer.Opcode0x27());
        op.put(0x28, new opcodes.gameServer.Opcode0x28());
        op.put(0x29, new opcodes.gameServer.Opcode0x29());
        op.put(0x30, new opcodes.gameServer.Opcode0x30());
        op.put(0x32, new opcodes.gameServer.Opcode0x32());
        op.put(0x37, new opcodes.gameServer.Opcode0x37());
        op.put(0x38, new opcodes.gameServer.Opcode0x38());
        op.put(0x39, new opcodes.gameServer.Opcode0x39());
        op.put(0x3A, new opcodes.gameServer.Opcode0x3A());
        op.put(0x40, new opcodes.gameServer.Opcode0x40());
        op.put(0x41, new opcodes.gameServer.Opcode0x41());
        op.put(0x42, new opcodes.gameServer.Opcode0x42());
        op.put(0x55, new opcodes.gameServer.Opcode0x55());
        op.put(0x5F, new opcodes.gameServer.Opcode0x5F());
        op.put(0x6F, new opcodes.gameServer.Opcode0x6F());
        op.put(0x67, new opcodes.gameServer.Opcode0x67());
        op.put(0x69, new opcodes.gameServer.Opcode0x69());
        op.put(0x70, new opcodes.gameServer.Opcode0x70());
        op.put(0x73, new opcodes.gameServer.Opcode0x73());
        op.put(0x74, new opcodes.gameServer.Opcode0x74());
        op.put(0x75, new opcodes.gameServer.Opcode0x75());

    }

}
