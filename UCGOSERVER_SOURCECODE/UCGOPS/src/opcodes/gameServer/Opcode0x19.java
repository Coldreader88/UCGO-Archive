package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x19 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PlayerGame player = (PlayerGame) p;

        PacketData pd = new PacketData(pakke.getData());

        int type = pd.readShortBE();
        pd.readShortBE();

        int character_id = pd.readIntBE();

        pd.readLongBE();
        pd.readLongBE();

        long amount = pd.readLongBE();

        if (type == 0x4 && character_id == player.getCharacter().getCharacterID()) {
            //Backpack til bank.
            //Sjekk at backpack inneholder minst oppgitt sum.
            if (player.getCharacter().getMoneyContainer().getAntall() < amount) {
                System.out.println("Opcode 0x19: Attempted to move invalid amount of money from backpack to bank.");
                return;
            }

            //OK, flytt pengene.
            player.getCharacter().getCreditContainer().settAntall(amount + player.getCharacter().getCreditContainer().getAntall());
            player.getCharacter().getMoneyContainer().settAntall(player.getCharacter().getMoneyContainer().getAntall() - amount);

        } else if (type == 0x5 && character_id == player.getCharacter().getCharacterID()) {
            //Bank til backpack.
            //Sjekk at bank inneholder minst oppgitt sum.
            if (player.getCharacter().getCreditContainer().getAntall() < amount) {
                System.out.println("Opcode 0x19: Attempted to move invalid amount of money from bank to backpack.");
                return;
            }

            //OK, flytt pengene.
            player.getCharacter().getMoneyContainer().settAntall(amount + player.getCharacter().getMoneyContainer().getAntall());
            player.getCharacter().getCreditContainer().settAntall(player.getCharacter().getCreditContainer().getAntall() - amount);

        } else {
            //Feil.
            System.out.println("Opcode 0x19: Unsupported move type.");
            return;
        }

        PacketData svar = new PacketData();

        svar.writeShortBE((short) type);
        svar.writeShortBE((short) 0x2);

        svar.writeIntBE(player.getCharacter().getCharacterID());

        svar.writeLongBE(0x0);
        svar.writeLongBE(0x0);

        svar.writeLongBE(amount);

        Packet svar_pakke = new Packet(0x8019, svar.getData());

        p.sendPacket(svar_pakke);
    }

}
