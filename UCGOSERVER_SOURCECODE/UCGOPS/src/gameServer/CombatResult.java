package gameServer;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde resultatet av et angrep.
 */
public class CombatResult {

    //Hvor mye penger angrepet var verdt, eller kostet hvis criminal flagging.
    private long money = 0;

    //Hvor mange hitpoints med skade som ble påført.
    private int damage = 0;

    //Angir om angrepet traff.
    private boolean hit = false;

    //Angir om det ble en critical hit, forutsetter at hit=true.
    private boolean critical_hit = false;

    //Angir om angrepet ble blokkert med et shield, forutsetter at hit=true.
    private boolean shield_block = false;

    //Angir om angrepet ble unngått (dodge), forutsetter at hit=true.
    private boolean dodged = false;

    //Angir om angrepet var en criminal handling.
    private boolean criminal = false;

    /**
     *
     * @param m Hvor mye angrepet er verdt/kostet.
     */
    public void setMoney(long m) {
        this.money = m;
    }

    public long getMoney() {
        return this.money;
    }

    /**
     *
     * @param d Hvor mange hitpoints skade som ble påført.
     */
    public void setDamage(int d) {
        this.damage = d;
    }

    public int getDamage() {
        return this.damage;
    }

    /**
     *
     * @param a Angir om det ble treff eller ikke.
     */
    public void setHit(boolean a) {
        this.hit = a;
    }

    public boolean isHit() {
        return this.hit;
    }

    /**
     *
     * @param a Angir om det ble critical hit.
     */
    public void setCritical(boolean a) {
        this.critical_hit = a;
    }

    public boolean isCritical() {
        return (this.hit && this.critical_hit);
    }

    /**
     *
     * @param a Angir om angrepet ble blokkert med et shield.
     */
    public void setShieldBlock(boolean a) {
        this.shield_block = a;
    }

    public boolean isShieldBlock() {
        return (this.hit && this.shield_block);
    }

    /**
     *
     * @param a Angir om angrepet ble ungått.
     */
    public void setDodged(boolean a) {
        this.dodged = a;
    }

    public boolean isDodged() {
        return (this.hit && this.dodged);
    }

    /**
     *
     * @param a Angir om angrepet var en criminal handling.
     */
    public void setCriminal(boolean a) {
        this.criminal = a;
    }

    public boolean isCriminal() {
        return this.criminal;
    }
}
