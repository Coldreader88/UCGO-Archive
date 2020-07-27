package opcodes.gameServer;

import containers.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import opcodes.Opcode;
import packetHandler.*;
import players.Player;

public class Opcode0x30 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int spiller_A = pd.readIntBE(); //Begge spillerene i transaksjonen.
        int spiller_B = pd.readIntBE();

        int trade_A = pd.readIntBE(); //Container ID for A sitt trade vindu.
        pd.readIntBE();

        pd.readLongBE();

        int trade_B = pd.readIntBE(); //Container ID for B sitt trade vindu.

        //Sjekk gyldigheten av trade vinduene.
        //ID skal være character ID+0xC pga fast ID for hovedcontainere.
        if ((trade_A - spiller_A) != 0xC || (trade_B - spiller_B) != 0xC) {
            System.out.println("Opcode 0x30: Invalid container ID.");
            return;
        }

        //Hent ut containerene til trade vinduene.
        Container con_A;
        Container con_B;

        con_A = ContainerList.getContainer(trade_A);
        con_B = ContainerList.getContainer(trade_B);

        if (!(con_A instanceof HovedContainer) || !(con_B instanceof HovedContainer)) {
            System.out.println("Opcode 0x30: Invalid container type.");
            return;
        }

        HovedContainer hc_A = (HovedContainer) con_A;
        HovedContainer hc_B = (HovedContainer) con_B;

        //Bytt om på innholdet i trade vinduene.
        ConcurrentLinkedQueue<ItemContainer> innhold_A = hc_A.getContents();
        ConcurrentLinkedQueue<ItemContainer> innhold_B = hc_B.getContents();
        hc_A.setContents(innhold_B);
        hc_B.setContents(innhold_A);

        ConcurrentLinkedQueue<HovedContainer> hc_innhold_A = hc_A.getContentsHC();
        ConcurrentLinkedQueue<HovedContainer> hc_innhold_B = hc_B.getContentsHC();
        hc_A.setContentsHC(hc_innhold_B);
        hc_B.setContentsHC(hc_innhold_A);

        //OK, send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x00010002);
        svar.writeIntBE(spiller_A);
        svar.writeIntBE(spiller_B);

        svar.writeIntBE(hc_A.getID());
        svar.writeIntBE(hc_A.getContainerTail());
        svar.writeIntBE(spiller_A + 0xA); //Swappack er alltid +0xA pga fast ID.
        svar.writeIntBE(0x14); //Tail er alltid 0x14 for swappack, så det er OK å gjøre det slik som dette.

        svar.writeIntBE(hc_B.getID());
        svar.writeIntBE(hc_B.getContainerTail());
        svar.writeIntBE(spiller_B + 0xA); //Swappack er alltid +0xA pga fast ID.
        svar.writeIntBE(0x14); //Tail er alltid 0x14 for swappack, så det er OK å gjøre det slik som dette.

        pd.writeIntBE(0x1ADB5); //Item ID for trade vinduene.
        pd.writeIntBE(0x1ADB5);

        pd.writeByte((byte) 0x0);

        Packet svar_pakke = new Packet(0x8030, svar.getData());

        p.sendPacket(svar_pakke);

        //Send også pakken til den andre spilleren.
        gameServer.MultiClient.sendPacketToPlayer(svar_pakke, spiller_B);
    }

}
