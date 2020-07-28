package gameData;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere stats for en engine.
 */
public class StatEngine {

    //Hvilket engine level dette er.
    private int level;

    public StatEngine(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

}
