package opcodes.gameServer;

import characters.CharacterGame;
import containers.*;
import gameServer.ItemCapsule;
import gameServer.ItemHandler;
import java.util.Date;
import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x25 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        PlayerGame player = (PlayerGame) p;

        int eier = pd.readIntBE();
        int ny_eier = pd.readIntBE();

        pd.readIntBE();

        int container_id = pd.readIntBE();

        //Sjekk at oppgitt container er gyldig.
        Container con = ContainerList.getContainer(container_id);

        if (!(con instanceof ItemContainer)) {
            System.out.println("Opcode 0x25: Invalid container ID.");
            return;
        }

        ItemContainer ic = (ItemContainer) con;
        ItemCapsule item = ItemHandler.getItem(ic);

        int current_time = (int) (new Date().getTime() / 1000);

        //Sjekk at oppgitt item faktisk er plassert på bakken.
        if (item == null) {
            //Feil, dette kan forårsakes av lag hvis flere spillere forsøker å kapre samme item samtidig.
            return;
        }

        //Sjekk om det var /release, /owner eller /transfer som ble utført.
        if ((eier == player.getCharacter().getCharacterID() && ny_eier == -1)
                || (eier == player.getCharacter().getCharacterID() && ny_eier != eier)) {
            //Det er /release eller /transfer
            //Sjekk at eier faktisk er eier av item.
            if (eier != item.getOwner()) {
                //Feil, kan være fusk.
                System.out.println("Opcode 0x25: Player attempted to release/transfer item he doesn't own.");
                return;
            }
        } else if (eier == player.getCharacter().getCharacterID() && ny_eier == eier) {
            //Det er /owner
            //Sjekk at item ikke har en eier allerede eller at ownership har gått ut.
            if (item.getOwner() != -1 && item.getTidOwnerExpire() > current_time) {
                //Item tilhører noen andre, dette skal ikke være mulig.
                System.out.println("Opcode 0x25: Player attempted to /owner an item belonging to someone else.");
                return;
            }
        } else {
            //Feil.
            System.out.println("Opcode 0x25: Invalid owner for item.");
            return;
        }

        //Sjekk at oppgitt container faktisk inneholder ms/vehicle.
        if (!(ic.getItem() instanceof items.Vehicle)) {
            System.out.println("Opcode 0x25: Attempt to change ownership for an invalid item.");
            return;
        }

        //OK, alt ser ut til å være i orden. Oppdater eierskap for item.
        item.setOwner(ny_eier);

        item.setTidPlasser(current_time);
        item.setTidOwnerExpire(current_time + config.Server.ownership_expires);

        //OK, send opcode 0x8035 til alle spillere i området.
        PacketData svar8035 = new PacketData();

        svar8035.writeIntBE(0x0);
        svar8035.writeIntBE(ic.getID());
        svar8035.writeIntBE(ic.getContainerTail());
        svar8035.writeIntBE(ic.getItem().getID());
        svar8035.writeIntBE(item.getPosisjon().getX());
        svar8035.writeIntBE(item.getPosisjon().getY());
        svar8035.writeIntBE(item.getPosisjon().getZ());
        svar8035.writeShortBE((short) item.getPosisjon().getTilt());
        svar8035.writeShortBE((short) item.getPosisjon().getRoll());
        svar8035.writeShortBE((short) item.getPosisjon().getDirection());
        svar8035.writeLongBE(ic.getAntall());
        svar8035.writeIntBE(item.getOwner());
        svar8035.writeByte((byte) 0x80);
        svar8035.writeIntBE(item.getTidPlassert());
        svar8035.writeIntBE(((items.Vehicle) ic.getItem()).getHitpoints());
        svar8035.writeIntBE(((items.Vehicle) ic.getItem()).getMaxHitpoints());
        svar8035.writeIntBE(item.getTidOwnerExpire());
        svar8035.writeShortBE((short) 0xA3);
        svar8035.writeByte((byte) 0x80);
        svar8035.writeIntBE(item.getOwner());
        svar8035.writeShortBE((short) -1);
        svar8035.writeByte((byte) 0x2);

        Packet svar8035_pakke = new Packet(0x8035, svar8035.getData());

        //Send 0x8035 til alle spillere i nærheten.
        Iterator<CharacterGame> i = gameServer.MultiClient.getCharacterList(player.getCharacter()).iterator();

        while (i.hasNext()) {
            CharacterGame chara = i.next();
            gameServer.MultiClient.sendPacketToPlayer(svar8035_pakke, chara.getCharacterID());
        }
    }

}
