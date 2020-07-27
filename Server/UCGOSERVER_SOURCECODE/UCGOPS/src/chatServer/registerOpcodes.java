package chatServer;

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

        op.put(0x1, new opcodes.chatServer.Opcode0x01());
        op.put(0x2, new opcodes.chatServer.Opcode0x02());
        op.put(0x3, new opcodes.chatServer.Opcode0x03());
        op.put(0x4, new opcodes.chatServer.Opcode0x04());
        op.put(0x7, new opcodes.chatServer.Opcode0x07());
        op.put(0x8, new opcodes.chatServer.Opcode0x08());
        op.put(0xA, new opcodes.chatServer.Opcode0x0A());
        op.put(0xB, new opcodes.chatServer.Opcode0x0B());
        op.put(0xD, new opcodes.chatServer.Opcode0x0D());
        op.put(0xF, new opcodes.chatServer.Opcode0x0F());
        op.put(0xE, new opcodes.chatServer.Opcode0x0E());
        op.put(0x10, new opcodes.chatServer.Opcode0x10());
        op.put(0x11, new opcodes.chatServer.Opcode0x11());
        op.put(0x12, new opcodes.chatServer.Opcode0x12());
        op.put(0x13, new opcodes.chatServer.Opcode0x13());
        op.put(0x14, new opcodes.chatServer.Opcode0x14());
        op.put(0x17, new opcodes.chatServer.Opcode0x17());
        op.put(0x18, new opcodes.chatServer.Opcode0x18());
        op.put(0x19, new opcodes.chatServer.Opcode0x19());
        op.put(0x1A, new opcodes.chatServer.Opcode0x1A());
        op.put(0x1B, new opcodes.chatServer.Opcode0x1B());
        op.put(0x20, new opcodes.chatServer.Opcode0x20());
        op.put(0x21, new opcodes.chatServer.Opcode0x21());
        op.put(0x22, new opcodes.chatServer.Opcode0x22());
        op.put(0x24, new opcodes.chatServer.Opcode0x24());
    }
}
