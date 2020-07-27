package opcodes.gameServer;

import containers.Container;
import items.Vehicle;
import opcodes.Opcode;
import packetHandler.*;
import players.*;
import validItems.ItemList;

public class Opcode0x18 implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int cid = pd.readIntBE();

        PlayerGame player = (PlayerGame) p;

        //Sjekk at oppgitt character ID er gyldig.
        if (cid != player.getCharacter().getCharacterID()) {
            return;
        }

        //Ingen vits i å sjekke resten av dataene. Det er alltid MS/vehicle spilleren er i
        //som skal repareres uansett.
        //Sjekk at spilleren er i et vehicle.
        Vehicle v = player.getCharacter().getVehicle();

        if (v == null) {
            //Spiller er ikke i vehicle, dette skjer pga lag.
            this.sendTom8018(player);
            return;
        }

        //Dersom det er mindre enn 5 sekunder siden spilleren ble angrepet skal repair feile.
        int time = (int) (System.currentTimeMillis() / 1000);

        if ((5 + player.getCharacter().getLastTimeAttacked()) >= time) {

            this.sendTom8018(player);
            return;
        }

        //Hent ut prisen for å reparere vehicle.
        long pris = ItemList.getRepairPrice(v.getID());

        //Hvis er -1 kan ikke item repareres.
        if (pris == -1) {
            this.sendTom8018(player);
            return;
        }

        short result_code; //Første short i svaret, forteller hvor penger skal taes fra.

        //OK, sjekk at spilleren har nok penger i backpack eller bank.
        if (player.getCharacter().getMoneyContainer().getAntall() >= pris) {
            //Betal med penger fra backpack.
            Container con = player.getCharacter().getMoneyContainer();
            con.settAntall(con.getAntall() - pris);
            result_code = 0x5;
        } else if (player.getCharacter().getCreditContainer().getAntall() >= pris) {
            //Betal med penger fra banken.
            Container con = player.getCharacter().getCreditContainer();
            con.settAntall(con.getAntall() - pris);
            result_code = 0x6;
        } else {
            //Spiller har ikke penger.
            System.out.println("Opcode 0x8018: Error, player attempted to repair but has insufficient funds.");
            return;
        }

        //Reparer item.
        v.repairDamage(v.getMaxHitpoints());

        //Send svar tilbake.
        PacketData svar8018 = new PacketData();

        svar8018.writeShortBE(result_code);
        svar8018.writeShortBE((short) 0x2);
        svar8018.writeIntBE(player.getCharacter().getCharacterID());
        svar8018.writeIntBE(player.getCharacter().getVehicleContainer().getID());
        svar8018.writeIntBE(player.getCharacter().getVehicleContainer().getContainerTail());
        svar8018.writeIntBE(player.getCharacter().getWearedContainer().getID());
        svar8018.writeIntBE(player.getCharacter().getWearedContainer().getContainerTail());
        svar8018.writeLongBE(pris);
        svar8018.writeIntBE(-1);
        svar8018.writeIntBE(0x1);
        svar8018.writeIntBE(0x0);
        svar8018.writeByte((byte) 0x80);

        Packet svar8018_pakke = new Packet(0x8018, svar8018.getData());

        p.sendPacket(svar8018_pakke);

        player.getCharacter().setOccupancyTag(6);
    }

    /**
     * Sender en tom opcode 0x8018 pakke tilbake til spiller.
     *
     * @param player Spiller som skal få 0x8018 pakke.
     */
    private void sendTom8018(PlayerGame player) {

        PacketData svar8018 = new PacketData();

        svar8018.writeIntBE(0x0005000C);
        svar8018.writeIntBE(player.getCharacter().getCharacterID());
        svar8018.writeLongBE(0x0);
        svar8018.writeLongBE(0x0);
        svar8018.writeLongBE(0x0);
        svar8018.writeIntBE(-1);
        svar8018.writeIntBE(1);
        svar8018.writeIntBE(0);
        svar8018.writeByte((byte) 0x80);

        Packet svar8018_pakke = new Packet(0x8018, svar8018.getData());

        player.sendPacket(svar8018_pakke);
    }
}
