package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x74 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int city_id = pd.readIntBE();

        //Registrer spilleren som deltaker i angrepet.
        PlayerGame player = (PlayerGame) p;
        occupationCity.OccupationCity.registerPlayerToBattle(player, city_id);

        PacketData svar = new PacketData();

        svar.writeIntBE(0x0);
        svar.writeIntBE(city_id);

        Packet svar_pakke = new Packet(0x8074, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
