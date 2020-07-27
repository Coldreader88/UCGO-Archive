package opcodes.gameServer;

import containers.*;
import gameServer.*;
import items.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;
import userDB.*;

public class Opcode0x26 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        PlayerGame player = (PlayerGame) p;

        //Sjekk at spiller er i MS/vehicle. Skal kun være mulig å åpne wreckage container når spiller er i MS/vehicle.
        if (player.getCharacter().getVehicle() == null) {
            return; //Trenger ikke å sende 0x8026 tilbake.
        }
        pd.readIntBE();

        int cid = pd.readIntBE(); //Container som skal åpnes.
        pd.readIntBE();

        Container con = ContainerList.getContainer(cid);

        //Sjekk at vi skal åpne en gyldig container.
        if (con == null || !(con instanceof ItemContainer)) {
            return; //Trenger ikke å sende 0x8026 tilbake.
        }
        //Sjekk at container faktiskt er plassert på bakken.
        ItemCapsule item = ItemHandler.getItem((ItemContainer) con);

        if (item == null) {
            return; //Trenger ikke å sende 0x8026 tilbake.
        }
        //Sjekk at dette er en wreckage container. Wreckage=Vehicle med 0 hitpoints.
        Item i = item.getItem().getItem();

        if (!(i instanceof Vehicle)) {
            //Alle wreckage containere SKAL inneholde et vehicle.
            System.out.println("Opcode 0x26: Attempt to open wreckage container not containing a vehicle, i:" + i);
            return;
        }

        Vehicle v = (Vehicle) i;

        if (v.getHitpoints() > 0) {
            //Vehicle har ikke 0 hitpoints, dette kan ikke være en wreckage container.
            System.out.println("Opcode 0x26: Container is not a wreckage.");
            return;
        }

        /*
		 * Sjekk eierskap for wreckage container. Kun spilleren som originalt eide vehicle eller 
		 * spillere fra motsatt faction har lov til å åpne wreckage. Spillere fra samme faction kan
		 * ikke åpne wreckage, dette for å hindre stjeling fra craftere/miners.
         */
        GameCharacter owner = ManageCharacters.getGameCharacter(item.getOwner());

        if (owner != null && player.getCharacter().getCharacterID() != item.getOwner()) {
            //Spiller som prøver å åpne wreckage er ikke eier av wreckage. Sjekk at spiller
            //tilhører motsatt faction.
            //Hvis samme faction bare returner, trenger ikke å sende svar tilbake.

            if (owner.getFaction() == player.getCharacter().getFaction()) {
                return;
            }
        }

        //OK, dette er en wreckage container. Sjekk om den allerede er i bruk eller ikke.
        if (item.lock(player.getCharacter().getCharacterID()) != true) {
            return; //Trenger ikke å sende svar hvis container er locked.
        }
        //Spiller kan åpne wreckage container.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x00010002);
        svar.writeIntBE(item.getItem().getID());
        svar.writeIntBE(item.getItem().getContainerTail());
        svar.writeIntBE(v.getID());
        svar.writeIntBE(player.getCharacter().getCharacterID());

        Packet svarPakke = new Packet(0x8026, svar.getData());

        p.sendPacket(svarPakke);

        /*
		 * Opcode 0x26 sendes hele tiden mens spiller har wreckage container åpen.
		 * For å hindre at wreckage container forsvinner mens spiller har den åpen sørg for at det alltid er
		 * minst 2 minutter igjen til wreckage skal fjernes fra spillet.
         */
        int time = (int) (System.currentTimeMillis() / 1000);

        int tidIgjen = item.getTidPlassert() + config.Server.wreckage_remove_time - time;

        //Sjekk om det er mindre enn 2 minutter igjen til wreckage skal fjernes.
        if (tidIgjen <= 120) {
            //Mindre enn 2 minutter igjen, legg til 120 sekunder ekstra på tiden som er igjen.
            item.setTidPlasser(item.getTidPlassert() + 120);
        }

    }

}
