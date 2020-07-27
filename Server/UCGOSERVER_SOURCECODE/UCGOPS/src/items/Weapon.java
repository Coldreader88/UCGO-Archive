package items;

import gameData.*;
import packetHandler.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere et våpen.
 */
public class Weapon extends Item {

    private static final long serialVersionUID = 1L;
    /**
     * Angir hitpoints og max hitpoints for et våpen.
     */
    private int hitpoints;
    private int max_hitpoints;
    /**
     * Angir hvor mye ammo det er i våpenet og hvor mye det kan ha max.
     */
    private int ammo;
    private int max_ammo;

    public Weapon(int item_id) {
        super(item_id);

        //Sett stats for våpen.
        StatWeapon stat = StatManager.getWeaponStat(item_id);

        if (stat == null) {
            System.out.println("Error, missing stats weapon ID: " + item_id);
            return;
        }

        this.max_hitpoints = stat.getHitpoints();
        this.hitpoints = this.max_hitpoints;
        this.max_ammo = stat.getAmmoClip();
        this.ammo = this.max_ammo;
    }

    /**
     * Returnerer hitpoints våpen har igjen.
     *
     * @return Hitpoints.
     */
    public int getHitpoints() {
        return this.hitpoints;
    }

    /**
     * Returnerer max hitpoints våpen kan ha.
     *
     * @return Max hitpoints.
     */
    public int getMaxHitpoints() {
        return this.max_hitpoints;
    }

    /**
     * Legger til ammo i våpenet.
     *
     * @param amount Mengde ammo som legges til.
     */
    public void addAmmo(int amount) {

        //Sjekk at vi ikke går over tillatt verdi.
        if ((this.ammo + amount) > this.max_ammo) {
            this.ammo = this.max_ammo;
        } else {
            this.ammo += amount;
        }
    }

    /**
     * Bruker ammo i våpenet.
     *
     * @param amount Mengde ammo som brukes.
     */
    public void useAmmo(int amount) {

        this.ammo -= amount;
        if (this.ammo < 0) {
            this.ammo = 0;
        }
    }

    /**
     * Påfører skade på våpenet.
     *
     * @param hitpoints Antall hitpoints i skade.
     */
    public void applyDamage(int hitpoints) {

        if (hitpoints > this.hitpoints) {
            this.hitpoints = 0;
        } else {
            this.hitpoints -= hitpoints;
        }
    }

    /**
     *
     * @return True dersom våpenet har 100% damage.
     */
    public boolean isDestroyed() {
        if (this.hitpoints <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public byte[] getData() {

        PacketData svar = new PacketData();

        svar.writeIntBE(0x80808080);
        svar.writeByte((byte) 0x81);

        svar.writeIntBE(this.ammo);

        svar.writeByte((byte) 0x87);

        svar.writeIntBE(this.hitpoints);
        svar.writeIntBE(this.max_hitpoints);

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x3E8); //RANGE! Men trolig ikke brukt.
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x3E8);

        return svar.getData();
    }
}
