package items;

import gameData.StatMS;
import gameData.StatManager;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere et /taxi eller /transport kjøretøy.
 */
public class TaxiTransport extends Item {

    private static final long serialVersionUID = 1L;

    private int motor_type;

    //Hitpoints vehicle har og max hitpoints det kan ha.
    private int hitpoints;
    private int max_hitpoints;

    public TaxiTransport(int item_id) {
        super(item_id);

        //Sett stats.
        StatMS stat = StatManager.getMsVehicleStat(item_id);

        if (stat == null) {
            System.out.println("Error, missing stats for taxi/transport ID: " + item_id);
            return;
        }

        this.motor_type = stat.getDefaultEngine();
        this.max_hitpoints = stat.getArmor();
        this.hitpoints = this.max_hitpoints;
    }

    /**
     * Returnerer hitpoints taxi/transport har igjen.
     *
     * @return Hitpoints.
     */
    public int getHitpoints() {
        return this.hitpoints;
    }

    /**
     * Returnerer max hitpoints taxi/transport kan ha.
     *
     * @return Max hitpoints.
     */
    public int getMaxHitpoints() {
        return this.max_hitpoints;
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

        int[] hardcode = {0x80, 0x80, 0x80, 0x80, 0x86, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04, 0x60, 0xBA, 0x40, 0xFC, 0xA5, 0x1D, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0x93,
            this.hitpoints >> 24, (this.hitpoints >> 16) & 0xFF, (this.hitpoints >> 8) & 0xFF, this.hitpoints & 0xFF,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00,
            this.hitpoints >> 24, (this.hitpoints >> 16) & 0xFF, (this.hitpoints >> 8) & 0xFF, this.hitpoints & 0xFF,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0xE8,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            (this.motor_type >> 24), (this.motor_type >> 16) & 0xFF, (this.motor_type >> 8) & 0xFF, this.motor_type & 0xFF,
            0x00, 0x00, 0x00, 0x00, 0x82, 0x00, 0x0, 0x0, 0x0, 0x00, 0x00, 0x00, 0x0, 0xff, 0xff, 0xff,
            0xff, 0x00, 0x0, 0x0, 0x0, 0x00, 0x00, 0x00, 0x0, 0xff, 0xff, 0xff, 0xff};

        byte[] data = new byte[hardcode.length];

        for (int c = 0; c < data.length; c++) {
            data[c] = (byte) hardcode[c];
        }

        return data;
    }

}
