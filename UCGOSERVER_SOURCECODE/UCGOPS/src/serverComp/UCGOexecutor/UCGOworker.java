package serverComp.UCGOexecutor;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen representerer en worker thread som brukes av UCGOexecutor.
 */
public class UCGOworker implements Runnable {

    private ConcurrentLinkedQueue<Runnable> taskList;

    /**
     *
     * @param taskList Liste der vi skal hente oppgavene som skal utføres.
     */
    public UCGOworker(ConcurrentLinkedQueue<Runnable> taskList) {

        this.taskList = taskList;
    }

    /**
     * Denne metoden vil sjekke oppgavelisten om det er noe som skal gjøres.
     * Dersom det ikke er noe å gjøre vil den vente 1ms før den sjekke igjen.
     */
    public void run() {

        //Utfør oppgaver helt til server blir slått av.
        while (true) {
            //Bruk try catch blokk for å hindre at thread kan krasje.
            try {
                //Utfør oppgaver i køen helt til den er tom.
                while (!taskList.isEmpty()) {

                    //Hent ut og utfør oppgaven.
                    Runnable r = taskList.poll();
                    if (r != null) {
                        r.run();
                    }
                }

                //Ingen oppgaver igjen å utføre. Vent 1ms før vi sjekker igjen.
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
