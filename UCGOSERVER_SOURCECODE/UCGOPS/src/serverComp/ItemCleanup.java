package serverComp;

import gameServer.ItemCapsule;
import gameServer.ItemHandler;
import items.Vehicle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import validItems.GameItem;
import validItems.ItemList;
import validItems.ItemSubType;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen rydder opp items i spillet. Fjerner gamle items og sørger for
 * at spillere ikke plasserer flere items enn tillatt på bakken/space.
 */
public class ItemCleanup implements Runnable {

    @Override
    public void run() {

        while (true) {

            try {
                Thread.sleep(60 * 1000); //Vent i ett minutt.
            } catch (InterruptedException e) {
            }

            this.expiredItemsCleanup();

            this.checkItemLimits(ItemHandler.getAllVehicles(), config.Server.max_vehicles_on_ground);

            this.checkItemLimits(ItemHandler.getAllItems(), config.Server.max_items_on_ground);
        }
    }

    /**
     * Fjerner vehicles og items som er utgått på dato.
     */
    private void expiredItemsCleanup() {

        //Hent ut nåværende tid som unix timestamp
        Date tid = new Date();
        final int timestamp = (int) (tid.getTime() / 1000L);

        //Hent ut alle vehicles og fjern dem som er utgått på dato.
        Iterator<ItemCapsule> vehicles = ItemHandler.getAllVehicles().values().iterator();
        while (vehicles.hasNext()) {

            ItemCapsule item = vehicles.next();

            //Sjekk om vehicle har 0 hitpoints, isåfall er det en wreckage container og da gjelder andre regler.
            if (item.getItem().getItem() instanceof Vehicle) {
                Vehicle v = (Vehicle) item.getItem().getItem();
                if (v.getHitpoints() <= 0) {
                    //Wreckage container, sjekk om den er utgått på dato.
                    if ((item.getTidPlassert() + config.Server.wreckage_remove_time) < timestamp) {
                        //OK, fjern wreckage.
                        ItemHandler.unregisterItem(item.getItem(), item.getPosisjon().getZone());
                        containers.ContainerList.removeContainer(item.getItem().getID());
                        containers.ContainerList.removeContainer(v.getInventory().getID());
                        containers.ContainerList.removeContainer(v.getWeaponsRoom().getID());
                    }
                }
            }

            if (item.getTidOwnerExpire() < timestamp && item.getTidOwnerExpire() + config.Server.remove_vehicles_time < timestamp) {
                //Vehicle er utgått på dato, fjern det.
                ItemHandler.unregisterItem(item.getItem(), item.getPosisjon().getZone());
                //Slett også itemcontaineren.
                containers.ContainerList.removeContainer(item.getItem().getID());
            }
        }

        //Hent ut alle items og fjern dem som er utgått på dato.
        Iterator<ItemCapsule> items = ItemHandler.getAllItems().values().iterator();
        while (items.hasNext()) {

            ItemCapsule item = items.next();

            if (item.getTidPlassert() + config.Server.remove_items_time < timestamp) {

                //Event items skal aldri kunne gå ut på dato.
                GameItem gi = ItemList.getItem(item.getItem().getID());

                if (gi != null && gi.getItemSubType() != ItemSubType.Event) {
                    //Item er utgått på dato, fjern den.
                    ItemHandler.unregisterItem(item.getItem(), item.getPosisjon().getZone());
                    //Slett også itemcontaineren.
                    containers.ContainerList.removeContainer(item.getItem().getID());
                }

            }
        }
    }

    /**
     * Sjekker at spillere ikke har plassert flere items enn tillatt.
     *
     * @param item_liste Liste over items vi skal undersøke. Denne stammer fra
     * ItemHandler klassen og inneholder enten alle vehicles eller alle andre
     * items.
     * @param max_items Max antall items en spiller har lov til å plassere på
     * bakken.
     */
    private void checkItemLimits(ConcurrentHashMap<Integer, ItemCapsule> item_liste, int max_items) {

        //Key=Character ID for en spiller, Value=Liste over alle items spilleren har plassert.
        HashMap<Integer, ArrayList<ItemCapsule>> items = new HashMap<Integer, ArrayList<ItemCapsule>>();

        //For hver spiller som har plassert items bygg en liste og sjekk om den inneholder mer
        //enn max tillatt items.
        Iterator<ItemCapsule> liste = item_liste.values().iterator();
        while (liste.hasNext()) {

            ItemCapsule item = liste.next();
            if (item.getOwner() != 0) { //Sjekk at item stammer fra en spiller.

                //Sjekk om vi allerede har registrert denne spilleren i listen.
                ArrayList<ItemCapsule> spiller_items = items.get(item.getOwner());
                if (spiller_items != null) {
                    //Vi har allerede registrert spilleren, legg til en ny item for spiller.
                    spiller_items.add(item);
                } else {
                    //Første item vi finner for denne spilleren, registrer ny spiller.
                    items.put(item.getOwner(), new ArrayList<ItemCapsule>());
                    //Legg til item for spilleren.
                    ArrayList<ItemCapsule> nyspiller_items = items.get(item.getOwner());
                    if (nyspiller_items != null) { //Best å sjekke for å være sikker.
                        nyspiller_items.add(item);
                    }
                }

            }
        }

        //Nå har vi en liste over hvilke spillere som har plassert hvilke items.
        //Sjekk for spillere som har plassert mer enn tillatt antall items.
        Iterator<ArrayList<ItemCapsule>> spiller_item_liste = items.values().iterator();
        while (spiller_item_liste.hasNext()) {
            ArrayList<ItemCapsule> spiller_items = spiller_item_liste.next();
            //Sjekk at en spiller ikke har plassert flere items enn tillatt.
            if (spiller_items.size() > max_items) {
                //Fjern items som er for mye.
                this.trimItemList(spiller_items, max_items);
            }
        }
    }

    /**
     * Fjerner overflødige items fra en liste. Dvs items som er over max tillatt
     * grense vil bli fjernet fra spillet. Eldste item vil bli fjernet først.
     *
     * @param item_list Liste over items vi skal sjekke.
     * @param max_items Max antall items som er tillatt.
     */
    private void trimItemList(ArrayList<ItemCapsule> item_list, int max_items) {

        while (item_list.size() > max_items) { //Fjern items helt til vi er under grensen.

            int eldst_item_index = 0; //Hvor i listen eldste item befinner seg.
            int eldst_item_timestamp = Integer.MAX_VALUE; //Timestamp for eldste item funnet hittil. 

            for (int c = 0; c < item_list.size(); c++) {
                if (item_list.get(c).getTidPlassert() < eldst_item_timestamp) {
                    //Vi har funnet hittil eldste item. Registrer det.
                    eldst_item_index = c;
                    eldst_item_timestamp = item_list.get(c).getTidPlassert();
                }
            }

            //OK, fjern eldste item funnet.
            ItemCapsule item = item_list.get(eldst_item_index);
            ItemHandler.unregisterItem(item.getItem(), item.getPosisjon().getZone());
            containers.ContainerList.removeContainer(item.getItem().getID());
            item_list.remove(eldst_item_index);
        }

    }

}
