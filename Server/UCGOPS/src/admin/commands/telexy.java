package admin.commands;

import packetHandler.*;
import players.*;

public class telexy implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi fått minst to argumenter og at det er en int.
        if (args.length < 3) {
            return;
        }

        int x, y;
        int z = 0;

        try {
            x = Integer.parseInt(args[1]);
            y = Integer.parseInt(args[2]);

            //Sjekk om vi også fikk en Z verdi.
            if (args.length == 4) {
                z = Integer.parseInt(args[3]);
            }
        } catch (Exception e) {
            return;
        }

        //Send teleport pakke til spilleren.
        PacketData pd = new PacketData();

        pd.writeIntBE(p.getCharacter().getCharacterID());
        pd.writeIntBE(x);
        pd.writeIntBE(y);
        pd.writeIntBE(z);
        pd.writeShortBE((short) 0);
        pd.writeShortBE((short) 0);
        pd.writeShortBE((short) 0);

        Packet svar803C = new Packet(0x803C, pd.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar803C, p.getCharacter().getCharacterID());
    }
}
