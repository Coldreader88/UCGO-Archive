package opcodes.gameServer;

import containers.*;
import items.Weapon;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x1D implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        PlayerGame player = (PlayerGame) p;

        pd.readIntBE();

        if (player.getCharacter().getCharacterID() != pd.readIntBE()) {
            return;
        }

        int ammo_container = pd.readIntBE(); //Container der ammo er.
        pd.readIntBE();

        int ammo_hovedcontainer = pd.readIntBE(); //Container der ammoen blir oppbevart.
        pd.readIntBE();

        int weapon_container = pd.readIntBE(); //Container der våpenet er.
        pd.readIntBE();

        long antall = pd.readLongBE(); //Hvor mye ammo som brukes.

        Container con;
        ItemContainer ammo_ic; //Der ammo er.
        HovedContainer ammo_hc; //Der ammo oppbevares.
        ItemContainer weapon_ic; //Der våpenet er.

        //Sjekk at ammo container er gyldig.
        con = ContainerList.getContainer(ammo_container);
        if (con instanceof ItemContainer) {
            ammo_ic = (ItemContainer) con;
        } else {
            return;
        }

        //Sjekk at våpen container er gyldig.
        con = ContainerList.getContainer(weapon_container);
        if (con instanceof ItemContainer) {
            weapon_ic = (ItemContainer) con;
        } else {
            return;
        }

        //Sjekk at container der ammo oppbevares er gyldig.
        con = ContainerList.getContainer(ammo_hovedcontainer);
        if (con instanceof HovedContainer) {
            ammo_hc = (HovedContainer) con;
        } else {
            return;
        }

        //Hent ut våpenet fra container.
        if (!(weapon_ic.getItem() instanceof Weapon)) {
            return;
        }
        Weapon w = (Weapon) weapon_ic.getItem();

        //Fjern ammo.
        ammo_ic.settAntall(ammo_ic.getAntall() - antall);
        //Legg til i våpenet.
        w.addAmmo((int) antall);

        //Sjekk om ammo er brukt opp og skal fjernes fra inventory.
        if (ammo_ic.getAntall() <= 0) {
            ammo_hc.removeItemContainer(ammo_ic);
            //Slett ammo container fra server, den trengs ikke mer.
            ContainerList.removeContainer(ammo_container);
        }

        //Send svar tilbake, nesten identisk til data mottatt.
        byte[] svar = pakke.getData();
        svar[3] = 0x2;

        Packet svar_pakke = new Packet(0x801D, svar);

        p.sendPacket(svar_pakke);
    }

}
