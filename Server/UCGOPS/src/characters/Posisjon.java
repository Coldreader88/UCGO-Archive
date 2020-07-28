package characters;

import java.io.Serializable;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til representere en posisjon i spillet.
 */
public class Posisjon implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //X,Y,Z posisjon.
    private int x, y, z;
    //Retning vi peker i, venstre/høyre.
    private int direction;
    //Opp/ned retning.
    private int tilt;
    //Roll
    private int roll;
    //Zone, space/ground.
    private int zone;

    public Posisjon(int x, int y, int z, int direction, int zone) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
        this.zone = zone;
    }

    public Posisjon(int x, int y, int z, int tilt, int roll, int direction, int zone) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.tilt = tilt;
        this.roll = roll;
        this.direction = direction;
        this.zone = zone;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setTilt(int tilt) {
        this.tilt = tilt;
    }

    public int getTilt() {
        return this.tilt;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getRoll() {
        return this.roll;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public int getZone() {
        return this.zone;
    }

    /**
     * Beregner avstanden mellom denne posisjonen og oppgitt posisjon. Det taes
     * ikke hensyn til rett zone.
     *
     * @param p Posisjon vi skal beregne avstand til.
     *
     * @return Avstanden til p.
     */
    public long distance(Posisjon p) {

        long x1 = this.x;
        long x2 = p.getX();
        long y1 = this.y;
        long y2 = p.getY();
        long z1 = this.z;
        long z2 = p.getZ();

        long x_diff = (long) Math.abs(x1 - x2);
        long y_diff = (long) Math.abs(y1 - y2);
        long z_diff = (long) Math.abs(z1 - z2);

        return (long) Math.sqrt(x_diff * x_diff + y_diff * y_diff + z_diff * z_diff);
    }

    /**
     * Beregner retningen, venstre/høyre, fra denne posisjonen til oppgitt
     * posisjon.
     *
     * @param p
     *
     * @return Retningen i gameunits, 0-65535.
     */
    public int beregnRetning(Posisjon p) {

        double dx = Math.abs(this.x - p.x);
        double dy = Math.abs(this.y - p.y);

        double h = Math.sqrt(dx * dx + dy * dy);

        double grader = Math.toDegrees(Math.asin(dy / h));

        double retning = (16384 / 90) * grader;

        /*
         * UCGO beregner retning fra -32768 til +32767. 0 er NORD, -16384 er VEST.
         * 
         * Finn ut hvilken kvadrant mål posisjonen befinner seg i og legg til eller trekk fra rett verdi
         * slik at retning blir riktig.
         */
        if (p.x >= this.x && p.y >= this.y) {
            retning -= 16384; //Første kvadrant.
        } else if (p.x <= this.x && p.y <= this.y) {
            retning += 16384; //Tredje kvadrant.
        } else if (p.x >= this.x & p.y <= this.y) {
            retning = -32768 + (16384 - retning); //Fjerde kvadrant.
        } else {
            retning = 16384 - retning; //Andre kvadrant.
        }
        return (int) retning;
    }

    /**
     * Beregner retningen, opp/ned, fra denne posisjonen til oppgitt posisjon.
     *
     * @param p
     *
     * @return Retningen, opp/ned, i gameunits.
     */
    public int beregnRetningOppNed(Posisjon p) {

        /*
         * For å beregne opp/ned finner vi vinkelen av en trekant der
         * motstående katet er Z aksen, mens hosliggende er hypotenusen av X,Y aksene.
         * 
         * Avstand til målet er hypotenusen av X,Y.
         */
        double dx = Math.abs(this.x - p.x);
        double dy = Math.abs(this.y - p.y);
        double dz = Math.abs(this.z - p.z);

        //h=Hypotenus, avstand til målet langs X,Y,Z aksene.
        double h = Math.sqrt(dx * dx + dy * dy + dz * dz);

        double grader = Math.toDegrees(Math.asin(dz / h));

        double retning = (16384 / 90) * grader;

        /*
         * UCGO beregner retning fra -32768 til +32767. 0=Rett fram, 16384=Rett opp, 32767=opp ned.
         * 
         * Men det er godt nok å bruke området -16384 til +16384.
         * 
         * +16384 = 90 grader opp, -16384 = 90 grader ned. 
         */
        //Dersom vi befinner oss lavere enn målet, skal retning være negativ. 
        //NB! Større Z = under
        if (this.z > p.z) {
            retning = -retning;
        }

        return (int) retning;
    }

    /**
     *
     * @return Kopi av dette objektet.
     */
    public Posisjon lagKopi() {
        return new Posisjon(this.x, this.y, this.z, this.tilt, this.roll, this.direction, this.zone);
    }
}
