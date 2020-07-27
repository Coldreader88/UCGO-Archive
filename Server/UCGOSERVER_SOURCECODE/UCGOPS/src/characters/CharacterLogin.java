package characters;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes for å representere en character i login serveren.
 */
public class CharacterLogin extends Character {

    /*Når denne characteren ble opprettet av brukeren. Unix timestamp.*/
    private int time;

    //Characters posisjon i spillet.
    private Posisjon pos;

    /**
     *
     * @param time Unix timestamp som angir når characteren ble opprettet.
     */
    public CharacterLogin(int id, String navn, int gender, int faction, int time) {
        super(id, navn, gender, faction);

        this.time = time;
    }

    /**
     *
     * @return Unix timestamp som forteller når denne characteren ble opprettet.
     */
    public int getTime() {

        return this.time;
    }

    /**
     * Setter posisjons objektet character skal bruke.
     *
     * @param p Posisjons objekt.
     */
    public void setPosisjon(Posisjon p) {
        this.pos = p;
    }

    /**
     * Returnerer posisjons objektet character bruker.
     *
     * @param p Posisjons objekt.
     */
    public Posisjon getPosisjon() {
        return this.pos;
    }
}
