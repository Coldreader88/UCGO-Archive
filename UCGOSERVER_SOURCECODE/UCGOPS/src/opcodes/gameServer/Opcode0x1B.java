package opcodes.gameServer;

import containers.*;
import gameData.StatManager;
import gameData.StatWeapon;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x1B implements Opcode {

    public void execute(Player p, Packet pakke) {

        PlayerGame player = (PlayerGame) p;

        /*TEST PAKKE FOR Å NEKTE SPILLER Å UTSTYRE ITEM.
         if ( true) {
         PacketData d = new PacketData();
         d.writeIntBE(0x00020000);
         Packet q = new Packet(0x801B,d.getData());
         player.sendPacket(q);
         return;
         }*/
        PacketData pd = new PacketData(pakke.getData());

        int item_type = pd.readIntBE(); //0x00020000 hvis våpen, 0x00010000 hvis klær.
        pd.readIntBE();
        pd.readIntBE();
        pd.readIntBE();

        //Antall "endringer" i utstyr.
        int teller = pd.readByte() & 0x7F;

        for (int c = 0; c < teller; c++) {

            //Sjekk om det er våpen eller klær som skal utstyres.
            if (item_type == 0x00020000) {
                //Våpen.
                pd.readByte();
                pd.readByte();
                int slot = pd.readByte();
                pd.readByte();

                int container = pd.readIntBE();
                pd.readIntBE();

                //Alle data lest inn, sjekk om vi skal utstyre noe eller fjerne.
                if (container == 0x0 && player.getCharacter().getVehicle() != null) {
                    //Item skal fjernes.
                    player.getCharacter().getVehicle().equipItem(null, slot);
                } else if (container != 0x0 && player.getCharacter().getVehicle() != null) {
                    //Item skal utstyres.
                    Container con = ContainerList.getContainer(container);
                    ItemContainer ic;
                    //Sjekk at container er gyldig.
                    if (con instanceof ItemContainer) {
                        ic = (ItemContainer) con;
                    } else {
                        return; //Ugyldige data. Bare ignorer det.
                    }

                    //OK! Først fjern eventuelt våpen som allerede er i slot.
                    player.getCharacter().getVehicle().equipItem(null, slot);

                    //Gå gjennom alle slots 0-6 og sjekk om item finnes i en annen slot.
                    //Dette skjer når spiller flytter våpen fra en slot til en annen, da blir ikke våpenet
                    //unequipped før det blir equipped.  Derfor må våpenet først fjernes fra tidligere slot
                    //hvis allerede utstyrt.
                    for (int i = 0; i <= 6; i++) {
                        if (ic == player.getCharacter().getVehicle().getEquippedItem(i)) {
                            //Item finnes i slot, fjern den før den flyttes.
                            player.getCharacter().getVehicle().equipItem(null, i);
                        }
                    }

                    //Utstyr våpen.
                    player.getCharacter().getVehicle().equipItem(ic, slot);
                } else {
                    return; //Tydeligvis noe gale med data mottatt. Bare ignorer det.
                }
            } else if (item_type == 0x00010000) {
                //Klær.
                pd.readByte();
                pd.readByte();
                int slot = pd.readByte();
                pd.readByte();

                int container = pd.readIntBE();
                pd.readIntBE();

                //Alle data lest inn, sjekk om vi skal utstyre noe eller fjerne.
                if (container == 0x0) {
                    //Item skal fjernes.
                    player.getCharacter().getClothing().equipItem(null, slot);
                } else {
                    //Item skal utstyres.
                    Container con = ContainerList.getContainer(container);
                    ItemContainer ic;
                    //Sjekk at container er gyldig.
                    if (con instanceof ItemContainer) {
                        ic = (ItemContainer) con;
                    } else {
                        return; //Ugyldige data. Bare ignorer det.
                    }
                    //OK! Fjern først eventuelle klær som er i slot.
                    player.getCharacter().getClothing().equipItem(null, slot);
                    //Utstyr nye klær.
                    player.getCharacter().getClothing().equipItem(ic, slot);
                }

            }

        }

        //Oppdater spillerens appearance change.
        player.getCharacter().appearanceChange();

        //Send svar tilbake. 0x801B er nesten identisk til 0x1B.
        byte[] b = pakke.getData();

        b[3] = 0x2;

        Packet svar_pakke = new Packet(0x801B, b);

        p.sendPacket(svar_pakke);
    }
}
