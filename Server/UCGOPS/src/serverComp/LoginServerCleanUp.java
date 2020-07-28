package serverComp;

import java.util.Date;
import java.util.Iterator;
import players.PlayerLogin;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen er ansvarlig for å fjerne "gamle" spillere fra login serveren.
 * Alle spillere som har vært logget inn i mer enn 30 minutter vil bli fjernet,
 * ingen trenger å være logget inn så lenge som 30 minutter og det burde være
 * trygt å fjerne dem.
 *
 * Dette må gjøres på denne måten fordi på login server sender ikke klienten
 * noen form for keep alive.
 */
public class LoginServerCleanUp implements Runnable {

    /**
     * Hvor lenge en spiller må ha vært innlogget før han fjernes.
     */
    private final int cleanUpTime = 30 * 60;

    private PlayerHandler<PlayerLogin> playerlist;

    /**
     *
     * @param playerlist Link til listen over spillere tilkoblet login server.
     */
    public LoginServerCleanUp(PlayerHandler<PlayerLogin> playerlist) {
        this.playerlist = playerlist;
    }

    public void run() {

        while (true) {
            //Nå værende tidspunkt.
            int time = (int) ((new Date()).getTime() / 1000);

            Iterator<PlayerLogin> spillere = this.playerlist.iterator();

            //Gå gjennom alle spillere på login server.
            while (spillere.hasNext()) {

                PlayerLogin spiller = spillere.next();

                //Sjekk om spiller har vært tilkoblet lengre enn tillat.
                if ((time - spiller.getConnectTime()) >= this.cleanUpTime) {
                    //Fjern spiller fra login server.
                    spiller.save();
                    this.playerlist.remove(spiller);
                    spiller.cleanUp();
                }
            }

            //Vent 5 minutter til neste sjekk.
            try {
                Thread.sleep(5 * 60 * 1000);
            } catch (Exception e) {
            }
        }

    }

}
