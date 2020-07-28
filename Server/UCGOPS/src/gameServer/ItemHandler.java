package gameServer;

import characters.*;
import containers.ContainerList;
import containers.ItemContainer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import packetHandler.*;
import validItems.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde oversikt over alle items som er plassert på
 * bakken eller i verdensrommet.
 */
public class ItemHandler {

    /**
     * Disse to hashmap-ene holder oversikt over MS/Vehicles plassert i ground
     * zone og space zone. Key = ItemContainer ID.
     */
    private static ConcurrentHashMap<Integer, ItemCapsule> vehicles_ground = new ConcurrentHashMap<Integer, ItemCapsule>();
    private static ConcurrentHashMap<Integer, ItemCapsule> vehicles_space = new ConcurrentHashMap<Integer, ItemCapsule>();

    /**
     * Disse to hashmap-ene holder oversikt over items som ikke er ms/vehicles
     * plassert i ground zone og space zone. Key = ItemContainer ID.
     */
    private static ConcurrentHashMap<Integer, ItemCapsule> items_ground = new ConcurrentHashMap<Integer, ItemCapsule>();
    private static ConcurrentHashMap<Integer, ItemCapsule> items_space = new ConcurrentHashMap<Integer, ItemCapsule>();

    /**
     * Dette hashmap-et holder oversikt over alle camp items (hangar,machine
     * store osv) som er plassert i ground zone.
     */
    private static ConcurrentHashMap<Integer, ItemCapsule> camp_ground = new ConcurrentHashMap<Integer, ItemCapsule>();

    /**
     * Registrerer en item som plassert et sted i spillet.
     *
     * @param item Container der item er plassert.
     * @param pos Posisjon der item er plassert.
     * @param owner Hvem som eier/plasserer item, null hvis ingen eier.
     * @param tid_owner_expire Timestamp, når eierskap for item går ut.
     */
    public static void registerItem(ItemContainer item, Posisjon pos, CharacterGame owner, int tid_owner_expire) {

        //Sjekk at vi har fått en item og posisjon den er plassert på.
        if (item == null || pos == null) {
            return;
        }

        int owner_id = 0; //Hent ut character ID hvis item har en eier.
        if (owner != null) {
            owner_id = owner.getCharacterID();
        }

        ItemCapsule capsule = new ItemCapsule(item, pos, owner_id, tid_owner_expire);

        //Sjekk at item er gyldig.
        GameItem gi = ItemList.getItem(item.getItem().getID());
        if (gi == null) {
            return; //Ugyldig item.
        }
        //Finn ut hvilken zone item skal plasseres i og hvilken type item dette er.
        if (pos.getZone() == 1) {
            //Ground zone.
            //Sjekk om dette er MS/vehicle, camp item (plassert av spiller ELLER spawnet) eller annen item.
            if (gi.getItemType() == ItemType.Vehicle || (gi.getItemType() == ItemType.Building && owner != null)) {
                vehicles_ground.put(item.getID(), capsule);
            } else if (gi.getItemType() == ItemType.Building && owner == null) {
                camp_ground.put(item.getID(), capsule); //Spawnet av server, ikke plassert av spiller.
            } else {
                items_ground.put(item.getID(), capsule);
            }
        } else if (pos.getZone() == 2) {
            //Space zone.
            //Sjekk om dette er MS/vehicle eller annen item.
            if (gi.getItemType() == ItemType.Vehicle) {
                vehicles_space.put(item.getID(), capsule);
            } else {
                items_space.put(item.getID(), capsule);
            }

        } else {
            return; //Ugyldig zone.
        }
    }

    /**
     * Fjerner en item fra oversikten.
     *
     * @param item ItemContainer som skal fjernes.
     * @param zone Hvilken zone item befinner seg i.
     */
    public static void unregisterItem(ItemContainer item, int zone) {

        if (item == null) {
            return;
        }

        if (zone == 1) {
            if (vehicles_ground.remove(item.getID()) == null) {
                items_ground.remove(item.getID());
            }
        } else if (zone == 2) {
            if (vehicles_space.remove(item.getID()) == null) {
                items_space.remove(item.getID());
            }
        }
    }

    /**
     * Returnerer en liste over items i nærheten av spilleren.
     *
     * @param chara Spilleren.
     * @param item_kategori Hvilken type item vi skal returnere 2=MS/vehicles,
     * 1=Andre items.
     * @param radius Max avstand til en item.
     *
     * @return Liste over alle items i området, eller null hvis problemer.
     */
    public static ArrayList<ItemCapsule> getItemList(CharacterGame chara, int item_kategori, int radius) {

        ArrayList<ItemCapsule> resultat = new ArrayList<ItemCapsule>();

        if (chara == null || item_kategori < 1 || item_kategori > 2) {
            return null;
        }

        ConcurrentHashMap<Integer, ItemCapsule> liste = new ConcurrentHashMap<Integer, ItemCapsule>();
        //Sjekk hvilken zone og type item som skal returneres.
        if (chara.getPosisjon().getZone() == 1) {
            //Ground zone.
            if (item_kategori == 1) {
                liste.putAll(items_ground);
            } else {
                //Returner camp items sammen med vehicles.
                liste.putAll(vehicles_ground);
                liste.putAll(camp_ground);
            }
        } else if (chara.getPosisjon().getZone() == 2) {
            //Space zone.
            if (item_kategori == 1) {
                liste.putAll(items_space);
            } else {
                liste.putAll(vehicles_space);
            }
        } else {
            return null; //Ugyldig zone.
        }
        Iterator<ItemCapsule> i = liste.values().iterator();

        //Gå gjennom alle items i zone og rett kategori.
        while (i.hasNext()) {

            ItemCapsule item = i.next();

            //Regn ut avstanden fra spiller til item.
            long avstand = chara.getPosisjon().distance(item.getPosisjon());

            if (avstand <= radius) {
                resultat.add(item);
            }
        }

        return resultat;
    }

    /**
     * Returnerer info om en item som er plassert på bakken/verdensrommet.
     *
     * @param ic Itemcontainer item ligger i.
     *
     * @return Info om item, eller NULL hvis item ikke er plassert på
     * bakken/verdensrommet.
     */
    public static ItemCapsule getItem(ItemContainer ic) {

        ItemCapsule item;

        item = vehicles_ground.get(ic.getID());

        if (item == null) {
            item = items_ground.get(ic.getID());
        }
        if (item == null) {
            item = vehicles_space.get(ic.getID());
        }
        if (item == null) {
            item = items_space.get(ic.getID());
        }
        if (item == null) {
            item = camp_ground.get(ic.getID());
        }

        return item;
    }

    /**
     * @return Liste over alle vehicles som er plassert på bakken/space.
     */
    public static ConcurrentHashMap<Integer, ItemCapsule> getAllVehicles() {

        ConcurrentHashMap<Integer, ItemCapsule> liste = new ConcurrentHashMap<Integer, ItemCapsule>();

        liste.putAll(vehicles_ground);
        liste.putAll(vehicles_space);

        return liste;
    }

    /**
     * @return Liste over alle items som er plassert på bakken/space.
     */
    public static ConcurrentHashMap<Integer, ItemCapsule> getAllItems() {

        ConcurrentHashMap<Integer, ItemCapsule> liste = new ConcurrentHashMap<Integer, ItemCapsule>();

        liste.putAll(items_ground);
        liste.putAll(items_space);

        return liste;
    }

    /**
     * Sletter alle items, vehicles og camps som er plassert på bakken.
     */
    public static void cleanAllItems() {

        vehicles_ground.clear();
        vehicles_space.clear();
        items_ground.clear();
        items_space.clear();
        camp_ground.clear();

    }

    /**
     * Plasserer en ny item i spillet på oppgitt posisjon. Item vil ikke ha noen
     * eier. Kun 1stk item vil bli plassert, item vil ikke stackes.
     *
     * @param item Item som skal plasseres.
     * @param pos Hvor item skal plasseres.
     */
    public static void spawnItem(items.Item item, Posisjon pos) {

        //Plasser item i en ItemContainer.
        ItemContainer ic = ContainerList.newItemContainer();
        ic.addItem(item);

        Date t = new Date();

        registerItem(ic, pos, null, (int) (t.getTime() / 1000L));
    }

    /**
     * Spawner en ny item på oppgitt posisjon og sender opcode 0x8035 til alle
     * spillere i nærheten.
     *
     * @param item Item som skal spawnes.
     * @param pos Posisjon til item.
     * @param owner Character ID for eier av item. Sett til 0 for ingen owner.
     */
    public static void spawnItem8035(items.Item item, Posisjon pos, int owner) {
        spawnItem8035(item, pos, owner, 1);
    }

    /**
     * Spawner en ny item på oppgitt posisjon og sender opcode 0x8035 til alle
     * spillere i nærheten.
     *
     * @param item Item som skal spawnes.
     * @param pos Posisjon til item.
     * @param owner Character ID for eier av item. Sett til 0 for ingen owner.
     * @param antall Antall av item som skal plasseres i item container.
     */
    public static void spawnItem8035(items.Item item, Posisjon pos, int owner, int antall) {

        //Plasser item i en ItemContainer.
        ItemContainer ic = ContainerList.newItemContainer();
        ic.addItem(item);
        ic.settAntall(antall);

        CharacterGame c = MultiClient.getCharacter(owner);

        int ownership_expires = (int) (new Date().getTime() / 1000);
        ownership_expires += config.Server.ownership_expires;

        registerItem(ic, pos, c, ownership_expires);

        //Send 0x8035 til alle spillere i nærheten.
        PacketData svar8035 = new PacketData();

        svar8035.writeIntBE(0x1);
        svar8035.writeIntBE(ic.getID());
        svar8035.writeIntBE(ic.getContainerTail());
        svar8035.writeIntBE(item.getID());
        svar8035.writeIntBE(pos.getX());
        svar8035.writeIntBE(pos.getY());
        svar8035.writeIntBE(pos.getZ());
        svar8035.writeShortBE((short) pos.getTilt());
        svar8035.writeShortBE((short) pos.getRoll());
        svar8035.writeShortBE((short) pos.getDirection());
        svar8035.writeLongBE((long) ic.getAntall());
        svar8035.writeIntBE(owner);
        svar8035.writeByte((byte) 0x80);
        svar8035.writeIntBE((int) (new Date().getTime() / 1000));
        svar8035.writeIntBE(1); //Hitpoints, egentlig ikke viktig akkurat her.
        svar8035.writeIntBE(1); //Max hitpoints, samme som over.
        svar8035.writeIntBE(ownership_expires);
        svar8035.writeShortBE((short) 0xB);
        svar8035.writeByte((byte) 0x80);
        svar8035.writeIntBE(owner);
        svar8035.writeShortBE((short) -1);
        svar8035.writeByte((byte) 0x1);

        Packet svar8035_pakke = new Packet(0x8035, svar8035.getData());

        gameServer.MultiClient.broadcastPacket(svar8035_pakke, pos);
    }

}
