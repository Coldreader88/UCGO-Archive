package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.Packet;
import packetHandler.PacketData;
import players.Player;
import players.PlayerGame;

public class Opcode0x75 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int city_id = pd.readIntBE();

        //Avregistrer spilleren som deltaker i angrepet.
        PlayerGame player = (PlayerGame) p;
        occupationCity.OccupationCity.unregisterPlayerFromBattle(player, city_id);

        PacketData svar = new PacketData();

        svar.writeIntBE(0x0);
        svar.writeIntBE(city_id);

        Packet svar_pakke = new Packet(0x8075, svar.getData());

        p.sendPacket(svar_pakke);

    }

}
