package opcodes.gameServer;

import containers.*;
import gameData.StatManager;
import gameData.StatWeapon;
import gameServer.ItemCapsule;
import items.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x11 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());
        PlayerGame player = (PlayerGame) p;

        int character_id = pd.readIntBE();

        pd.readIntBE();

        int container_id = pd.readIntBE();
        pd.readIntBE();

        int item_id = pd.readIntBE();

        //Sjekk at character ID er gyldig.
        if (player.getCharacter().getCharacterID() != character_id) {
            return;
        }

        //Sjekk at oppgitt container er gyldig.
        Container con = ContainerList.getContainer(container_id);
        if (!(con instanceof ItemContainer)) {
            System.out.println("Opcode 0x11: Error, invalid container.");
            return;
        }

        ItemContainer ic = (ItemContainer) con;

        //Sjekk at oppgitt item er gyldig.
        if (ic.getItem().getID() != item_id) {
            System.out.println("Opcode 0x11: Error, invalid item.");
            return;
        }

        //Sjekk at spiller er i et vehicle og har våpen utstyrt.
        Vehicle v = player.getCharacter().getVehicle();
        if (v == null) {
            System.out.println("Opcode 0x11: Error, player not in ms/vehicle.");
            return;
        }

        //Sjekk at spiller har et våpen utstyrt.
        if (v.getEquippedItem(v.getActiveSlot()) == null) {
            System.out.println("Ocpode 0x11: Error, player attempted to attack without a weapon.");
            return;
        }

        //Angrepet item er enten et vehicle eller en bygning. Hent item og påfør resultatet av angrepet.
        Vehicle target_v = null;

        if (ic.getItem() instanceof Vehicle) {
            target_v = (Vehicle) ic.getItem();
        }
        //SJEKK HER OM DET ER BYGNING!

        //Beregn resultat av angrepet.
        gameServer.CombatResult resultat = null;

        if (target_v != null) {
            resultat = gameServer.Combat.attack(player.getCharacter(), target_v);
        }
        //BEREGN RESULTAT FOR BYGNING HER.

        //Send opcode 0x8011 tilbake.
        PacketData svar8011 = new PacketData();

        svar8011.writeIntBE(0x2);
        svar8011.writeIntBE(player.getCharacter().getCharacterID());

        //Skriv skade påført.
        if (resultat != null) {
            svar8011.writeIntBE(resultat.getDamage());
        } else {
            svar8011.writeIntBE(0x0);
        }

        svar8011.writeShortBE((short) 0x0);
        svar8011.writeShortBE((short) -1);
        svar8011.writeIntBE(0x0); //Attack ID.

        //wID = Item ID for utstyrt våpen.
        int wID = v.getEquippedItem(v.getActiveSlot()).getItem().getID();
        StatWeapon stat = StatManager.getWeaponStat(wID);

        if (stat != null) {
            //Bruk ammo og påfør weapon damage.
            Weapon w = (Weapon) v.getEquippedItem(v.getActiveSlot()).getItem();
            w.applyDamage(1);
            w.useAmmo(stat.getAmmoUse());

            svar8011.writeByte((byte) 0x1); //Weapon damage
            svar8011.writeByte((byte) stat.getAmmoUse()); //Ammo brukt.
        } else {
            svar8011.writeByte((byte) 0x0); //Weapon damage
            svar8011.writeByte((byte) 0x0); //Ammo brukt.
        }

        svar8011.writeIntBE(v.getEquippedItem(v.getActiveSlot()).getID());
        svar8011.writeIntBE(v.getEquippedItem(v.getActiveSlot()).getContainerTail());
        svar8011.writeIntBE(v.getEquippedItem(v.getActiveSlot()).getItem().getID());

        svar8011.writeIntBE(ic.getID());
        svar8011.writeIntBE(ic.getContainerTail());

        Packet svar8011_pakke = new Packet(0x8011, svar8011.getData());

        p.sendPacket(svar8011_pakke);

//MIDLERTIDIG. DERSOM ITEM ER CAMP TENT IKKE SEND 0x8035. SERVER STØTTER IKKE Å ANGRIPE BYGNINGER ENNÅ
        if (ic.getItem() instanceof Camp) {
            return;
        }

        //Sjekk at item er plassert på bakken. Dette må gjøres etter 0x8011 er sendt fordi dette problemet
        //kan oppstå som et resultat av lag. Hvis item er plukket opp skal vi ikke gjøre noe mer.
        ItemCapsule itemc = gameServer.ItemHandler.getItem(ic);
        if (itemc == null) {
            return;
        }

        //Criminal flag sjekk her.	
        //Påfør skade.
        if (target_v != null) {
            //Item som angripes er et vehicle.
            target_v.applyDamage(resultat.getDamage());
        }

        PacketData svar8035 = new PacketData();

        //Skriv ut første int avhengig av om item ble skadet eller ble ødelagt. SJEKK BYGNING OGSÅ!
        if (target_v != null && target_v.getHitpoints() <= 0) {
            svar8035.writeIntBE(0x7); //Vehicle ødelagt.
        } else {
            svar8035.writeIntBE(0x5); //Item skadet.
        }
        svar8035.writeIntBE(ic.getID());
        svar8035.writeIntBE(ic.getContainerTail());
        svar8035.writeIntBE(ic.getItem().getID());

        svar8035.writeIntBE(itemc.getPosisjon().getX());
        svar8035.writeIntBE(itemc.getPosisjon().getY());
        svar8035.writeIntBE(itemc.getPosisjon().getZ());
        svar8035.writeShortBE((short) itemc.getPosisjon().getTilt());
        svar8035.writeShortBE((short) itemc.getPosisjon().getRoll());
        svar8035.writeShortBE((short) itemc.getPosisjon().getDirection());

        svar8035.writeLongBE(ic.getAntall());

        svar8035.writeIntBE(itemc.getOwner());
        svar8035.writeByte((byte) 0x80);
        svar8035.writeIntBE(itemc.getTidPlassert());

        //Skriv hitpoints for item. LEGG INN SJEKK FOR BYGNING
        if (target_v != null) {
            svar8035.writeIntBE(target_v.getHitpoints());
            svar8035.writeIntBE(target_v.getMaxHitpoints());
        } else {
            svar8035.writeIntBE(0x0);
            svar8035.writeIntBE(0x0);
        }

        svar8035.writeIntBE(itemc.getTidOwnerExpire());
        svar8035.writeShortBE((short) 0x0);
        svar8035.writeByte((byte) 0x80);
        svar8035.writeIntBE(player.getCharacter().getCharacterID());
        svar8035.writeShortBE((short) -1);
        svar8035.writeByte((byte) 0x2);

        Packet svar8035_pakke = new Packet(0x8035, svar8035.getData());
        //Send pakken til alle spillere i nærheten av item som ble angrepet.
        gameServer.MultiClient.broadcastPacket(svar8035_pakke, itemc.getPosisjon());

        //Dersom angrepet item har 100% skade oppdater info slik at vi har en wreckage container.
        if (target_v != null && target_v.getHitpoints() <= 0) {

            int time = (int) (System.currentTimeMillis() / 1000);
            //Dersom ownership ikke har gått ut for item oppdater tiden slik at eier har tid til å hente wreckage.
            if (itemc.getTidOwnerExpire() > time) {
                itemc.setTidOwnerExpire(time + config.Server.wreckage_owner_expire);
            }

            //Wreckage container er på en måte ny item. Oppdater tiden når den ble plassert.
            itemc.setTidPlasser(time);
        }
    }

}
