package gameData;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde stats for et GQ klesplagg.
 */
public class StatGQclothes {

    //Holder hvor mye hver character attribute endres av klesplagget.
    private int strength, spirit, luck;

    public StatGQclothes(int strength, int spirit, int luck) {
        this.strength = strength;
        this.spirit = spirit;
        this.luck = luck;
    }

    public int getStrength() {
        return strength;
    }

    public int getSpirit() {
        return spirit;
    }

    public int getLuck() {
        return luck;
    }

}
