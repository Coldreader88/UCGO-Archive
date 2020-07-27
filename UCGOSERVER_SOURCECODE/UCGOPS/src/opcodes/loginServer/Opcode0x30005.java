package opcodes.loginServer;

import opcodes.*;
import packetHandler.*;
import players.Player;

public class Opcode0x30005 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE(); //Security token?

        int account_id = pd.readIntBE();
        //int character_id = pd.readIntBE(); IKKE NØDVENDIG AKKURAT NÅ!
               
        if (p.getAccountID() == account_id) {
            //OK, send server informasjon.
            PacketData svar = new PacketData();

            svar.writeIntBE(0x1);
            svar.writeByte((byte) (config.Server.gameserver_ip.length() | 0x80));
            svar.writeStringASCII(config.Server.gameserver_ip);
            svar.writeShortBE((short) 0x5DCA);
            svar.writeIntBE(0x0);

            Packet svar_pakke = new Packet(0x38005, svar.getData());

            p.sendPacket(svar_pakke);
        } else {
            //Feil!
            PacketData svar = new PacketData();

            svar.writeIntBE(-1);

            Packet svar_pakke = new Packet(0x38005, svar.getData());

            p.sendPacket(svar_pakke);
        }
    }

}
