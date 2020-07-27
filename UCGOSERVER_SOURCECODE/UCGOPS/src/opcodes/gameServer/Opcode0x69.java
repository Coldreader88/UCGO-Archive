package opcodes.gameServer;

import containers.*;
import items.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x69 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        PlayerGame player = (PlayerGame) p;

        int cid = pd.readIntBE();

        int targetId = pd.readIntBE();

        int icID = pd.readIntBE();

        int slot = pd.readByte();

        //Sjekk at oppgitt container til vehicle er gyldig.
        Container con = containers.ContainerList.getContainer(icID);
        if (!(con instanceof ItemContainer)) {
            sendTom8069(p);
            return;
        }

        ItemContainer ic = (ItemContainer) con;

        //Sjekk at vehicle er gyldig.
        if (!(ic.getItem() instanceof Vehicle)) {
            sendTom8069(p);
            return;
        }

        Vehicle targetVehicle = (Vehicle) ic.getItem();

        //Beregn resultat av reparering.
        //MR kits kan ikke mislykkes, de vil alltid reparere 10*level prosent + 5% random.
        int lvl = 0;

        //Finn ut hvilket level vi har.
        switch (player.getCharacter().getVehicle().getEquippedItem(slot).getItem().getID()) {

            case 0x44667: //L1
            case 0x4466B:

                lvl = 1;
                break;

            case 0x44668: //L2
            case 0x4466C:

                lvl = 2;
                break;

            case 0x44669: //L3
            case 0x4466D:

                lvl = 3;
                break;

            default:

                System.out.println("Invalid ID for MR kit, item ID: " + player.getCharacter().getVehicle().getEquippedItem(slot).getItem().getID());

        }

        //Antall prosent som blir reparert.
        double prosent = 10 * lvl + Math.random() * 5;

        //Hitpoints reparert.
        int hitpoints = (int) (targetVehicle.getMaxHitpoints() * prosent / 100);

        //Sjekk at vi ikke prøver å reparere mer enn det som er skadet.
        if ((targetVehicle.getHitpoints() + hitpoints) > targetVehicle.getMaxHitpoints()) {
            //Endre hvor mye som blir reparert.
            hitpoints = targetVehicle.getMaxHitpoints() - targetVehicle.getHitpoints();

            prosent = 100.0 / targetVehicle.getMaxHitpoints() * hitpoints;
        }

        //Oppdater vehicle.
        targetVehicle.repairDamage(hitpoints);

        //Send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeIntBE(cid);
        svar.writeIntBE(targetId);

        svar.writeIntBE(Float.floatToIntBits((float) prosent));
        svar.writeIntBE(hitpoints);
        svar.writeByte((byte) 0x0);

        svar.writeShortBE((short) -1);

        svar.writeIntBE(0x0);
        svar.writeByte((byte) 0x1); //Damage til MR kit.
        svar.writeByte((byte) 0x0);

        svar.writeIntBE(player.getCharacter().getVehicle().getEquippedItem(slot).getID());
        svar.writeIntBE(0x13);

        svar.writeIntBE(player.getCharacter().getVehicle().getEquippedItem(slot).getItem().getID()); //Item ID til MR

        svar.writeIntBE(ic.getID());
        svar.writeIntBE(ic.getContainerTail());
        svar.writeIntBE(targetVehicle.getID()); //Item ID til MS

        svar.writeByte((byte) 0x0);

        Packet svarPakke = new Packet(0x8069, svar.getData());

        p.sendPacket(svarPakke);

        //Send også 0x8069 til spiller som blir reparert.
        gameServer.MultiClient.sendPacketToPlayer(svarPakke, targetId);

        //Send opcode 0x806A til alle spillere i området.
        PacketData svar806A = new PacketData();

        svar806A.writeIntBE(cid);
        svar806A.writeIntBE(targetId);
        svar806A.writeByte((byte) 0x0);
        svar806A.writeShortBE((short) -1);
        svar806A.writeIntBE(-1);
        svar806A.writeIntBE(player.getCharacter().getVehicle().getEquippedItem(slot).getItem().getID());
        svar806A.writeIntBE(ic.getID());
        svar806A.writeIntBE(ic.getContainerTail());
        svar806A.writeByte((byte) 0x0);

        Packet svar806Apakke = new Packet(0x806A, svar806A.getData());

        gameServer.MultiClient.broadcastPacket(svar806Apakke, player.getCharacter());

    }

    /**
     * Sender en tom 0x8069 pakke til spiller.
     *
     * @param p Spiller som sendte 0x69.
     */
    private void sendTom8069(Player p) {

        PacketData svar = new PacketData();

        svar.writeIntBE(-1);
        svar.writeIntBE(-1);

        Packet svarPakke = new Packet(0x8069, svar.getData());

        p.sendPacket(svarPakke);
    }

}
