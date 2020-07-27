package items;

import containers.*;
import gameData.*;
import java.util.Iterator;
import packetHandler.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere MS,MA,Fighter og kjøretøy.
 *
 */
public class Vehicle extends Item {

    private static final long serialVersionUID = 1L;

    //Hitpoints.  Max verdi er 10000 og setter hitpoints til max for nye vehicles.
    private int MAX_HITPOINTS;
    private int hitpoints;

    //Upgrades for vehicle.
    private int upgrade_power = 0;
    private int upgrade_hit = 0;
    private int upgrade_defence = 0;

    //Motor type for vehicle.
    private int motor_type;

    //Inventory lagres her.  Dette er aktivt inventory, spillerens selfstorage.
    private HovedContainer inventory;

    //Weapons room. Klienten ber om denne kontaineren, men usikkert om vi trenger å lagre våpen i den.
    private HovedContainer weapons;

    //Liste som holder oversikt over våpen/items utstyrt. Weaponsroom skal IKKE brukes til det.
    //Index: 0=Slot#1, 1=Shield, 2=Slot#2, 3=Slot#3, 4=Slot#4, 5=Slot#5, 6=Slot#6
    //Hvis en slot ikke innholder noe skal den være null.
    private ItemContainer[] equipped_items = new ItemContainer[7];

    //Hvilken slot som er i bruk.
    private int aktiv_slot = 0;

    /**
     *
     * @param item_id Item ID.
     */
    public Vehicle(int item_id) {
        super(item_id);
        //Opprett ny container for selfstorage. Alle vehicles må ha egen selfstorage container.
        this.inventory = ContainerList.newHovedContainer();
        this.inventory.settStatiskID(0x1ADBA);
        //Alle vehicles må ha eget weaponsroom container.
        this.weapons = ContainerList.newHovedContainer();
        this.weapons.settStatiskID(0x1D4C2);

        //Sett stats.
        StatMS stat = StatManager.getMsVehicleStat(item_id);

        if (stat == null) {
            System.out.println("Error, missing stats for vehicle ID: " + item_id);
            return;
        }

        this.motor_type = stat.getDefaultEngine();
        this.MAX_HITPOINTS = stat.getArmor();
        this.hitpoints = this.MAX_HITPOINTS;
    }

    /**
     *
     * @param item_id
     * @param engine Engine vehicle skal ha.
     */
    public Vehicle(int item_id, int engine) {
        this(item_id);
        this.motor_type = engine;
    }

    /**
     * Oppretter nytt vehicle men overfører inventory fra allerede eksiterende
     * vehicle. Weapons room blir IKKE overført.
     *
     * @param item_id
     * @param engine Engine vehicle skal ha.
     * @param v Eksisterende vehicle hvis inventory skal overføres til nytt
     * vehicle.
     */
    public Vehicle(int item_id, int engine, Vehicle v) {
        super(item_id);

        this.inventory = v.inventory;

        //Alle vehicles må ha eget weaponsroom container.
        this.weapons = ContainerList.newHovedContainer();
        this.weapons.settStatiskID(0x1D4C2);

        //Sett stats.
        StatMS stat = StatManager.getMsVehicleStat(item_id);

        if (stat == null) {
            System.out.println("Error, missing stats for vehicle ID: " + item_id);
            return;
        }

        this.motor_type = engine;
        this.MAX_HITPOINTS = stat.getArmor();
        this.hitpoints = this.MAX_HITPOINTS;
    }

    /**
     * Returnerer hitpoints vehicle har igjen.
     *
     * @return Hitpoints.
     */
    public int getHitpoints() {
        return this.hitpoints;
    }

    /**
     * Returnerer max hitpoints vehicle kan ha.
     *
     * @return Max hitpoints.
     */
    public int getMaxHitpoints() {
        return this.MAX_HITPOINTS;
    }

    /**
     * Påfører skade på vehicle.
     *
     * @param damage Antall hitpoints i skade.
     */
    public void applyDamage(int damage) {

        if (this.hitpoints < damage) {
            this.hitpoints = 0;
        } else {
            this.hitpoints -= damage;
        }
    }

    /**
     * Reparerer skade på vehicle.
     *
     * @param damage Antall hitpoints som skal repareres.
     */
    public void repairDamage(int damage) {

        if (this.hitpoints + damage > this.MAX_HITPOINTS) {
            this.hitpoints = this.MAX_HITPOINTS;
        } else {
            this.hitpoints += damage;
        }

        //Ekstra sjekk her.
        if (this.hitpoints > this.MAX_HITPOINTS) {
            this.hitpoints = this.MAX_HITPOINTS;
        }
    }

    /**
     * Setter Power upgrade for vehicle.
     *
     * @param level
     */
    public void setUpgradePower(int level) {
        this.upgrade_power = level;
    }

    /**
     *
     * @return Power upgrade level for vehicle.
     */
    public int getUpgradePower() {
        return this.upgrade_power;
    }

    /**
     * Setter Hit upgrade for vehicle.
     *
     * @param level
     */
    public void setUpgradeHit(int level) {
        this.upgrade_hit = level;
    }

    /**
     *
     * @return Hit upgrade level for vehicle.
     */
    public int getUpgradeHit() {
        return this.upgrade_hit;
    }

    /**
     * Settter Defence upgrade for vehicle.
     *
     * @param level
     */
    public void setUpgradeDefence(int level) {
        this.upgrade_defence = level;
    }

    /**
     *
     * @return Defence upgrade level for vehicle.
     */
    public int getUpgradeDefence() {
        return this.upgrade_defence;
    }

    /**
     *
     * @return Totalt antall upgrades på MS/Vehicle.
     */
    public int getTotalUpgrade() {
        return this.upgrade_defence + this.upgrade_hit + this.upgrade_power;
    }

    /**
     * Returnerer item ID for engine.
     *
     * @return Engine ID.
     */
    public int getEngine() {
        return this.motor_type;
    }

    /**
     * Utstyrer vehicle med en item i oppgitt slot, eller fjerner item fra
     * oppgitt slot.
     *
     * Når item skal utstyres forutsettes det at den finnes i SelfStorage, den
     * vil da bli fjernet derfra. Når item skal fjernes fra slot flyttes den til
     * SelfStorage. Ved utstyring av item forutsettes det at slot er tom.
     *
     * @param item Item container som inneholder item vi skal utstyre. NULL hvis
     * item skal fjernes.
     * @param slot Hvilken slot som skal brukes, 0-6.
     */
    public void equipItem(ItemContainer item, int slot) {

        //Sjekk at slot er gyldig.
        if (slot < 0 || slot > 6) {
            return;
        }

        if (item != null) {
            //Item skal utstyres. Hent fra SelfStorage.
            //Sjekk at item finnes i SelfStorage.
            if (!this.inventory.containsItemContainer(item)) {
                return; //Item finnes ikke i SelfStorage.
            }
            this.inventory.removeItemContainer(item);
            this.equipped_items[slot] = item;
        } else {
            //Item skal fjernes fra slot. Flytt til SelfStorage.
            //Sjekk først at det finnes en item i slot.
            if (this.equipped_items[slot] == null) {
                return;
            }

            this.inventory.addItemContainer(this.equipped_items[slot]);
            this.equipped_items[slot] = null;
        }

    }

    /**
     * Returnerer utstyrt item i oppgitt slot.
     *
     * @param slot Slot vi skal returnere info om.
     *
     * @return Item container som inneholder item i slot, eller null hvis ikke
     * noe i slot.
     */
    public ItemContainer getEquippedItem(int slot) {

        //Sjekk at slot er gyldig.
        if (slot < 0 || slot > 6) {
            return null;
        }

        return this.equipped_items[slot];
    }

    /**
     * @return Hvilken slot er i bruk.
     */
    public int getActiveSlot() {
        return this.aktiv_slot;
    }

    /**
     * Setter hvilken slot som er aktiv.
     *
     * @param slot Slot som er aktiv.
     */
    public void setActiveSlot(int slot) {

        //Sjekk at slot er gyldig.
        if (slot < 0 || slot > 6) {
            return;
        }

        this.aktiv_slot = slot;
    }

    /**
     * Alle items utstyrt vil bli lagret i WeaponsRoom. En item container for
     * hver slot vil bli lagret, slots som er tomme vil få en tom ItemContainer.
     * ItemContainere lagres i slot rekkefølge.
     *
     * Tidligere innhold i WeaponsRoom blir fjernet.
     */
    public void saveWeaponsRoom() {

        this.weapons.emptyContainer();

        for (int c = 0; c < this.equipped_items.length; c++) {
            if (this.equipped_items[c] != null) {
                this.weapons.addItemContainer(this.equipped_items[c]);
            } else {
                //Slot er tom. Plasser tom container i weapons room.
                ItemContainer dummy_ic = new ItemContainer(0);
                dummy_ic.settAntall(0);
                dummy_ic.settContainerTail(0x0);
                this.weapons.addItemContainer(dummy_ic);
            }
        }

    }

    /**
     * Items lagret i WeaponsRoom blir flyttet inn i slots.
     */
    public void restoreWeaponsRoom() {

        ItemContainer ic;

        Iterator<ItemContainer> items = this.weapons.getContents().iterator();

        for (int c = 0; c < this.equipped_items.length; c++) {

            if (items.hasNext()) {
                //Hent ut ItemContainer og sjekk om den inneholder våpen/item.
                ic = items.next();
                if (ic.getID() != 0) {
                    this.equipped_items[c] = ic;
                } else {
                    this.equipped_items[c] = null;
                }
            } else {
                //Ikke flere ItemContainere. Sett slot til tom.
                this.equipped_items[c] = null;
            }
        }

    }

    /**
     *
     * @return Hovedcontaineren som fungerer som selfstorage.
     */
    public HovedContainer getInventory() {
        return this.inventory;
    }

    /**
     *
     * @return Hovedcontaineren som fungerer som weaponsroom.
     */
    public HovedContainer getWeaponsRoom() {
        return this.weapons;
    }

    /**
     * Fjerner shield fra slot #1 og fra containerList. Denne metoden skal
     * brukes når shield får 100% skade.
     */
    public void destroyShield() {

        if (this.equipped_items[1] == null) {
            return;
        }

        ContainerList.removeContainer(this.equipped_items[1].getID());

        this.equipped_items[1] = null;
    }

    /**
     *
     * @return Machine damage i prosent.
     */
    public byte getMachineDamage() {

        int machineDamage = 100 - ((100 * this.getHitpoints()) / this.getMaxHitpoints());

        //Hvis machine damage er 100% sjekk at hitpoints er null, hvis ikke sett damage til 99%.
        if (machineDamage == 100 && this.getHitpoints() != 0) {
            machineDamage = 99;
        }

        return (byte) machineDamage;
    }

    public byte[] getData() {

        PacketData svar = new PacketData();

        svar.writeIntBE(0x80808080);
        svar.writeShortBE((short) 0x8093);

        svar.writeIntBE(this.hitpoints);

        svar.writeIntBE(0x3E8);
        svar.writeIntBE(0x3E8);
        svar.writeIntBE(0x3E8);
        svar.writeIntBE(0x3E8);
        svar.writeIntBE(0x3E8);

        svar.writeIntBE(this.MAX_HITPOINTS);

        svar.writeIntBE(0x3E8);
        svar.writeIntBE(0x3E8);
        svar.writeIntBE(0x3E8);
        svar.writeIntBE(0x3E8);
        svar.writeIntBE(0x3E8);

        svar.writeIntBE(0x26);
        svar.writeIntBE(0x3E8);
        svar.writeIntBE(0xC);

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        svar.writeIntBE(this.motor_type);

        int upgrade = (this.upgrade_power & 0xF) | (this.upgrade_defence & 0xF) << 4 | (this.upgrade_hit & 0xF) << 8;
        svar.writeIntBE(upgrade);

        svar.writeByte((byte) 0x82);

        svar.writeIntBE(this.weapons.getID());
        svar.writeIntBE(this.weapons.getContainerTail());
        svar.writeIntBE(0x1D4C2);

        svar.writeIntBE(this.inventory.getID());
        svar.writeIntBE(this.inventory.getContainerTail());
        svar.writeIntBE(0x1ADBA);

        return svar.getData();
    }

    protected void finalize() throws Throwable {

        this.inventory = null;
        this.weapons = null;
        this.equipped_items = null;
    }

}
