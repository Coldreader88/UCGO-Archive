package opcodes.gameServer;

import containers.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x1C implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        PlayerGame player = (PlayerGame) p;

        pd.readIntBE();

        int cid = pd.readIntBE();

        //Container der ER kit er oppbevart.
        int erkit_con = pd.readIntBE();
        pd.readIntBE();

        //Sjekk at character ID er gyldig.
        if (cid != player.getCharacter().getCharacterID()) {
            return;
        }

        //Sjekk at oppgitt container for ER kit er gyldig.
        Container con = ContainerList.getContainer(erkit_con);
        ItemContainer er_ic;

        if (con != null && con instanceof ItemContainer) {
            er_ic = (ItemContainer) con;
        } else {
            System.out.println("Opcode 0x1C: Error, invalid container ID for ER kit");
            return;
        }

        //Sjekk at spiller er i et vehicle.
        if (player.getCharacter().getVehicle() == null) {
            //Dette skjer pga lag.
            this.sendTom801C(player);
            return;
        }

        //Sjekk at vehicle har minst 50% damage.
        if (player.getCharacter().getVehicle().getMachineDamage() < 50) {
            this.sendTom801C(player);
            return;
        }

        //Sjekk at det finnes minst ett ER kit i container.
        if (er_ic.getAntall() < 1) {
            System.out.println("Opcode 0x1C: Error, attempt use ER kit but there are none left in container.");
            return;
        }

        int lvl; //Hvilket ER kit LVL som brukes.

        //Finn ER level basert på item ID. HUSK Å LEGG TIL LEVEL 4 ER KITS.
        switch (er_ic.getItem().getID()) {

            case 0x4BAF9:
            case 0x4BAF6:
            case 0x4BAF0:
            case 0x4BAF3: //LVL 1
                lvl = 1;
                break;

            case 0x4BAFA:
            case 0x4BAF7:
            case 0x4BAF1:
            case 0x4BAF4: //LVL 2
                lvl = 2;
                break;

            case 0x4BAFB:
            case 0x4BAF8:
            case 0x4BAF2:
            case 0x4BAF5://LVL 3
                lvl = 3;
                break;

            default: //Ugyldig ID nummer for ER kit.
                System.out.println("Opcode 0x1C: Error, invalid ID number for ER kit. ID:" + er_ic.getItem().getID());
                return;
        }

        //OK, reparer MS/vehicle.
        gameServer.RepairResult res = gameServer.Repair.ERkit(player.getCharacter(), lvl);

        //Sjekk for fusking.        
        if (player.getCharacter().ErKitDeltaTime() <= 9000) {
            res.setRepairOK(false);
        }

        player.getCharacter().setErKitTimer();

        if (res.isRepairOK()) {
            player.getCharacter().getVehicle().repairDamage(res.getRepairHitpoints());
        }

        //Fjern ER kit fra container.
        er_ic.settAntall(er_ic.getAntall() - 1);

        //Hvis container er tom fjern den.
        if (er_ic.getAntall() < 1) {
            player.getCharacter().getVehicle().getInventory().removeItemContainer(er_ic);
            ContainerList.removeContainer(er_ic.getID());
        }

        //Send svar tilbake.
        PacketData svar801C = new PacketData();

        if (res.isRepairOK()) {
            svar801C.writeIntBE(0x00060002);
        } else {
            svar801C.writeIntBE(0x0006000C);
        }

        svar801C.writeIntBE(player.getCharacter().getCharacterID());
        svar801C.writeIntBE(player.getCharacter().getVehicleContainer().getID());
        svar801C.writeIntBE(player.getCharacter().getVehicleContainer().getContainerTail());
        svar801C.writeIntBE(er_ic.getID());
        svar801C.writeIntBE(er_ic.getContainerTail());
        svar801C.writeIntBE(player.getCharacter().getVehicle().getInventory().getID());
        svar801C.writeIntBE(player.getCharacter().getVehicle().getInventory().getContainerTail());
        svar801C.writeLongBE(0x1);

        if (res.isRepairOK()) {

            svar801C.writeByte((byte) 0x86);
            svar801C.writeIntBE(res.getRepairHitpoints());
            svar801C.writeIntBE(0x0);
            svar801C.writeIntBE(0x0);
            svar801C.writeIntBE(0x0);
            svar801C.writeIntBE(0x0);
            svar801C.writeIntBE(0x0);
        } else {
            svar801C.writeByte((byte) 0x80);
        }

        Packet svar801C_pakke = new Packet(0x801C, svar801C.getData());

        p.sendPacket(svar801C_pakke);

        player.getCharacter().setOccupancyTag(9);
    }

    /**
     * Sender en tom opcode 0x801C tilbake til klienten. Dette gjøres når klient
     * sender 0x1C men spiller ikke er i ms/vehicle, det skjer pga lag. Tom
     * 0x801C må sendes tilbake for å unngå bugs.
     *
     *
     * @param player Spiller som sendte 0x1C.
     */
    private void sendTom801C(PlayerGame player) {

        PacketData svar801C = new PacketData();

        svar801C.writeIntBE(0x0006000C);
        svar801C.writeIntBE(player.getCharacter().getCharacterID());
        svar801C.writeLongBE(0x0);
        svar801C.writeLongBE(0x0);
        svar801C.writeLongBE(0x0);
        svar801C.writeLongBE(0x0);
        svar801C.writeByte((byte) 0x80);

        Packet svar801C_pakke = new Packet(0x801C, svar801C.getData());

        player.sendPacket(svar801C_pakke);
    }

}
