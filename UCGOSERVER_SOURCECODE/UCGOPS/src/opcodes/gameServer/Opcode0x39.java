package opcodes.gameServer;

import containers.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x39 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readShortBE();

        int msg_type = (int) (pd.readShortBE() & 0xFFFF);

        //Finn ut hva som skal gjøres.
        switch (msg_type) {

            case 0x8010: //Lock-on pakke.
                this.opcode8010(pd, (PlayerGame) p);
                break;

            case 0x802A: //Invitasjon til trade.
                this.opcode802A(pd, (PlayerGame) p);
                break;

            case 0x802B: //Trade invitasjon akseptert.
                this.opcode802B(pd, (PlayerGame) p);
                break;

            case 0x802C: //Item flyttet inn i trade vinduet.
                this.opcode802C(pd, (PlayerGame) p);
                break;

            case 0x802D: //Trade transaksjon over.
            case 0x802E:
            case 0x802F:
                this.opcode802D_F(pd, (PlayerGame) p, msg_type);
                break;

            case 0x8031: //Trade cancel.
                this.opcode8031(pd, (PlayerGame) p);
                break;
        }

    }

    /**
     * Sender opcode 0x8010, Lock-on pakke, til en spiller.
     *
     * @param pd Pakkedata, første INT antas lest.
     *
     * @param player Spiller som sendte pakken.
     */
    private void opcode8010(PacketData pd, PlayerGame player) {

        pd.readByte();

        int target = pd.readIntBE();

        //Send lock-on pakke til mottaker.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x0);
        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeByte((byte) 0x1);

        Packet svar_pakke = new Packet(0x8010, svar.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar_pakke, target);
    }

    /**
     * Sender opcode 0x802A, trade invitasjon, til en spiller.
     *
     * @param pd Pakkedata, første INT antas lest.
     *
     * @param player Spiller som sendte trade invitasjon.
     */
    private void opcode802A(PacketData pd, PlayerGame player) {

        pd.readByte();

        int target = pd.readIntBE(); //Spiller som inviteres til trade.

        pd.readIntBE(); //Status??

        pd.readIntBE();
        pd.readIntBE();

        pd.readLongBE(); //Spillerens swappack.

        int container = pd.readIntBE(); //Den tomme container i swappack aka trade vinduet.
        pd.readIntBE();

        //Sjekk at den "tomme" container er gyldig.
        Container con = ContainerList.getContainer(container);
        if (con == null || !(con instanceof HovedContainer)) {
            System.out.println("Opcode 0x39->0x802A: Invalid container in trade.");
            return;
        }

        HovedContainer tomme_ic = (HovedContainer) con;

        pd.readIntBE(); //Swappack ID
        pd.readIntBE(); //Tomme container ID

        int resultat = pd.readIntBE(); //Resultat/svar fra sender???

        //OK, send opcode 0x802A til mottaker.
        PacketData svar802A = new PacketData();

        svar802A.writeIntBE(0x00010002);
        svar802A.writeIntBE(player.getCharacter().getCharacterID());
        svar802A.writeIntBE(target);

        svar802A.writeIntBE(player.getCharacter().getSwappackContainer().getID());
        svar802A.writeIntBE(player.getCharacter().getSwappackContainer().getContainerTail());

        svar802A.writeIntBE(tomme_ic.getID());
        svar802A.writeIntBE(tomme_ic.getContainerTail());

        svar802A.writeIntBE(player.getCharacter().getSwappackContainer().getStatiskID());
        svar802A.writeIntBE(tomme_ic.getStatiskID());

        svar802A.writeIntBE(resultat);

        Packet svar802A_pakke = new Packet(0x802A, svar802A.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar802A_pakke, target);
    }

    /**
     * Sender opcode 0x802B, trade invite accepted, til en spiller.
     *
     * @param pd Pakkedata fra opcode 0x39, første int antas lest.
     *
     * @param player Spilleren som sendte opcode 0x39.
     */
    private void opcode802B(PacketData pd, PlayerGame player) {

        pd.readByte();

        int target = pd.readIntBE(); //Spiller som skal motta svar.

        pd.readIntBE(); //Status??

        pd.readIntBE();
        pd.readIntBE();

        long swappack = pd.readLongBE(); //Swappack som skal brukes til trade.

        int container = pd.readIntBE(); //Den tomme container i swappack.
        pd.readIntBE();

        //Sjekk at den "tomme" container er gyldig.
        Container con = ContainerList.getContainer(container);
        if (con == null || !(con instanceof HovedContainer)) {
            System.out.println("Opcode 0x39->0x802B: Invalid container in trade.");
            return;
        }

        HovedContainer tomme_ic = (HovedContainer) con;

        int swappack_id = pd.readIntBE(); //Swappack ID
        pd.readIntBE(); //Tomme container ID

        int resultat = pd.readIntBE(); //Resultat/svar fra sender???

        //OK, send opcode 0x802B til mottaker.
        PacketData svar802B = new PacketData();

        svar802B.writeIntBE(0x00010002);
        svar802B.writeIntBE(player.getCharacter().getCharacterID());
        svar802B.writeIntBE(target);

        svar802B.writeLongBE(swappack);

        svar802B.writeIntBE(tomme_ic.getID());
        svar802B.writeIntBE(tomme_ic.getContainerTail());

        svar802B.writeIntBE(swappack_id);
        svar802B.writeIntBE(tomme_ic.getStatiskID());

        svar802B.writeIntBE(resultat);

        Packet svar802B_pakke = new Packet(0x802B, svar802B.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar802B_pakke, target);
    }

    /**
     * Sender opcode 0x802C til en spiller. Denne pakken forteller mottaker om
     * en item som har blitt plassert i trade vinduet.
     *
     * @param pd Pakkedata fra opcode 0x39, første int antas lest.
     *
     * @param player Spilleren som sendte opcode 0x39.
     */
    private void opcode802C(PacketData pd, PlayerGame player) {

        pd.readByte();

        int target = pd.readIntBE(); //Spiller som skal få 0x802C

        pd.readIntBE();
        pd.readIntBE();

        int container = pd.readIntBE();

        //Sjekk at containeren er gyldig.
        Container con = ContainerList.getContainer(container);

        if (con == null) {
            System.out.println("Opcode 0x39->0x802C: Invalid container.");
            return;
        }

        //OK, send 0x802C til den andre spilleren.
        PacketData svar802C = new PacketData();

        svar802C.writeIntBE(player.getCharacter().getCharacterID());
        svar802C.writeIntBE(target);
        svar802C.writeByteArray(con.getData());

        Packet svar802C_pakke = new Packet(0x802C, svar802C.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar802C_pakke, target);
    }

    /**
     * Sender opcode 0x802D, 0x802E eller 0x802F til en spiller. Innholder i
     * disse pakkene er stort sett det samme og kan derfor håndteres av samme
     * metode.
     *
     * @param pd Pakkedata for opcode 0x39, første int antas lest.
     *
     * @param player Spilleren som sendte opcode 0x39.
     *
     * @param opcode Hvilken opcode vi skal sende, 0x802D-0x802F.
     */
    private void opcode802D_F(PacketData pd, PlayerGame player, int opcode) {

        pd.readByte();

        int target = pd.readIntBE(); //Spiller som skal få pakken.

        PacketData svar = new PacketData();

        switch (opcode) {

            case 0x802D:
                svar.writeIntBE(0x00020002);
                break;
            case 0x802E:
                svar.writeIntBE(0x00030002);
                break;
            case 0x802F:
                svar.writeIntBE(0x00040002);
                break;

            default:
                System.out.println("Opcode 0x39->0x802D_F: Invalid opcode argument: " + opcode);
                return;
        }

        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(target);

        svar.writeLongBE(0x0);
        svar.writeLongBE(0x0);
        svar.writeLongBE(-1);

        svar.writeIntBE(0xB);

        Packet svar_pakke = new Packet(opcode, svar.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar_pakke, target);
    }

    /**
     * Sender opcode 0x8031, trade cancel, til en spiller.
     *
     * @param pd Pakkedata fra opcode 0x39, første int antas lest.
     *
     * @param player Spilleren som sendte opcode 0x39.
     */
    private void opcode8031(PacketData pd, PlayerGame player) {

        pd.readByte();

        int target = pd.readIntBE(); //Spiller som skal motta beskjed.

        PacketData svar8031 = new PacketData();

        svar8031.writeIntBE(0x00050002);
        svar8031.writeIntBE(player.getCharacter().getCharacterID());
        svar8031.writeIntBE(target);
        svar8031.writeLongBE(0x0);
        svar8031.writeLongBE(0x0);
        svar8031.writeIntBE(-1);
        svar8031.writeIntBE(-1);
        svar8031.writeIntBE(0xB);

        Packet svar8031_pakke = new Packet(0x8031, svar8031.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar8031_pakke, target);
    }

}
