package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x3A implements Opcode {

    public void execute(Player p, Packet pakke) {

        PlayerGame player = (PlayerGame) p;

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();
        pd.readIntBE();
        pd.readShortBE();
        pd.readShortBE();
        pd.readIntBE();

        pd.readIntBE();
        pd.readIntBE();
        pd.readIntBE();
        pd.readShortBE();
        pd.readShortBE();
        pd.readShortBE();

        pd.readShortBE();

        int action_type = pd.readIntBE(); //0x1=Gesture, 0x2=Attack.

        pd.skipAhead(16);

        PacketData svar = new PacketData();

        //Sett sammen svar avhenging av hva som blir gjort.
        if (action_type == 0x1) {
            //Gesture.
            pd.readIntBE();
            int gesture_type = pd.readIntBE();

            svar.writeIntBE(0x1);
            svar.writeIntBE(player.getAccountID());
            svar.writeIntBE(player.getCharacter().getCharacterID());
            svar.writeShortBE((short) player.getCharacter().getPosisjon().getZone());
            svar.writeShortBE((short) 0x0);
            svar.writeIntBE(0x0);
            svar.writeIntBE(0x1);
            svar.writeIntBE(gesture_type);
            svar.writeIntBE(-1);
        } else if (action_type == 0x2) {
            //Attack.
            int weapon = pd.readIntBE();
            int x_pos = pd.readIntBE();
            int y_pos = pd.readIntBE();
            int z_pos = pd.readIntBE();

            svar.writeIntBE(0x2);
            svar.writeIntBE(player.getAccountID());
            svar.writeIntBE(player.getCharacter().getCharacterID());
            svar.writeShortBE((short) player.getCharacter().getPosisjon().getZone());
            svar.writeShortBE((short) 0x0);
            svar.writeIntBE(0x0);
            svar.writeIntBE(weapon);
            svar.writeIntBE(x_pos);
            svar.writeIntBE(y_pos);
            svar.writeIntBE(z_pos);
        }

        Packet svar803B_pakke = new Packet(0x803B, svar.getData());

        //Send 0x803B til alle spillere i nærheten.
        gameServer.MultiClient.broadcastPacket(svar803B_pakke, player.getCharacter());

    }

}
