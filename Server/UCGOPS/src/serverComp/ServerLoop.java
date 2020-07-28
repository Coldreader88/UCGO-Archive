package serverComp;

import java.util.*;
import java.util.concurrent.*;
import players.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen inneholder main loop for alle serverene.
 */
public class ServerLoop<E extends Player> implements Runnable {

    /**
     * Thread pool serveren bruker.
     */
    private ExecutorService pool;

    /**
     * Inneholder alle tilkoblede brukere.
     */
    private PlayerHandler<E> playerList;

    /**
     *
     * @param pool Thread pool vi skal bruke.
     * @param playerList Set som inneholder liste over alle tilkoblede brukere.
     */
    public ServerLoop(ExecutorService pool, PlayerHandler<E> playerList) {

        this.pool = pool;
        this.playerList = playerList;
    }

    public void run() {

        while (true) {

            //Sjekk om server er offline. Hvis den er bare vent og ikke ta imot data.
            while (config.Server.server_is_offline) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }

            //Hent ut alle elementene i playerList.
            Iterator<E> liste = this.playerList.iterator();

            //Gå gjennom alle elementene i playerList.
            while (liste.hasNext()) {

                E p = liste.next();

                if (p != null) {
                    //Sjekk at tidsfristen for å logge på ikke er utgått eller at spiller er logget på riktig.
                    if (p.checkIfLoggedIn()) {
                        //Sjekk om vi må håndtere noe data for brukeren.
                        try {
                            if (p.checkTransfer() && !p.isRunning()) {
                                this.pool.execute(p);
                            }
                        } catch (RejectedExecutionException e) {
                            //Thread pool full eller noe sånt. Bare skriv ut melding for nå.
                            e.printStackTrace();
                        }
                    } else {
                        //Denne brukeren koblet til men sendte aldri gyldig login info, fjern den.
                        playerList.remove(p);
                        p.cleanUp();
                    }

                }
            }

            //Da har vi gått gjennom alle tilkoblede brukere.
            //Ta en pause før vi begynner på nytt.
            try {
                Thread.sleep(config.Server.serverLoopSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
