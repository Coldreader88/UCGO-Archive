package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.Packet;
import players.*;

public class Opcode0x29 implements Opcode {

    public void execute(Player p, Packet pakke) {

        byte[] data = pakke.getData();

        data[3] = 0x2;

        Packet svar_pakke = new Packet(0x8029, data);

        p.sendPacket(svar_pakke);

        PlayerGame player = (PlayerGame) p;
        player.getCharacter().getProductiveContainer().setCraftingString(null);
    }

}
