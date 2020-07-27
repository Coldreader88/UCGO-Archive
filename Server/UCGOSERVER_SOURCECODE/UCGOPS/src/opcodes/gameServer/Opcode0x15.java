package opcodes.gameServer;

import containers.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x15 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();
        pd.readIntBE();

        int container = pd.readIntBE();
        pd.readIntBE();

        int parent = pd.readIntBE();
        pd.readIntBE();

        //Sjekk at oppgitte containere er gyldige.
        ItemContainer ic;
        HovedContainer hc;

        Container con = ContainerList.getContainer(container);
        if (con instanceof ItemContainer) {
            ic = (ItemContainer) con;
        } else {
            System.out.println("Opcode 0x15: Invalid container type, expected ItemContainer.");
            return;
        }

        con = ContainerList.getContainer(parent);
        if (con instanceof HovedContainer) {
            hc = (HovedContainer) con;
        } else {
            System.out.println("Opcode 0x15: Invalid container type, expected HovedContainer.");
            return;
        }

        //Fjern itemcontainer fra hovedcontainer.
        hc.removeItemContainer(ic);

        //Hvis vi fjernet fra weared containeren oppdater spillerens utseende.
        PlayerGame player = (PlayerGame) p;
        if (parent == player.getCharacter().getWearedContainer().getID()) {
            player.getCharacter().setHumanForm();
            player.getCharacter().appearanceChange();
        }

        //Send svar tilbake.
        //Svaret er nesten identisk til data mottatt.
        byte[] svar = pd.getData();
        svar[3] = 0x2;

        Packet svar_pakke = new Packet(0x8015, svar);

        p.sendPacket(svar_pakke);
    }

}
