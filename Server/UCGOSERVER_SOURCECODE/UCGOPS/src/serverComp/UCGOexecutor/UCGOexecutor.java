package serverComp.UCGOexecutor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen implementerer en executor service beregnet for bruk med UCGO.
 *
 * NB! KUN execute() METODEN ER IMPLEMENTERT. ALLE ANDRE METODER GJØR INGENTING.
 */
public class UCGOexecutor implements ExecutorService {

    /**
     * Alle oppgaver som mottaes og skal håndteres lagres her. Worker threads
     * henter oppgaver fra denne listen.
     */
    private ConcurrentLinkedQueue<Runnable> taskList = new ConcurrentLinkedQueue<Runnable>();

    /**
     * Oppretter ny UCGO executor service.
     *
     * @param wt Antall worker threads vi skal bruke.
     */
    public UCGOexecutor(int wt) {

        for (int i = 0; i < wt; i++) {
            new Thread(new UCGOworker(taskList)).start();
        }
    }

    /**
     * Tar imot en oppgave som skal utføres og lagrer den i intern liste som
     * worker threads henter sine oppgaver fra.
     */
    public void execute(Runnable task) {

        this.taskList.add(task);
    }

//******************************************************************************
//METODENE HER GJØR INGENTING.
//KUN EXECUTE METODEN ER IMPLEMENTERT.
    @Override
    public boolean awaitTermination(long arg0, TimeUnit arg1)
            throws InterruptedException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> arg0)
            throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(
            Collection<? extends Callable<T>> arg0, long arg1, TimeUnit arg2)
            throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> arg0)
            throws InterruptedException, ExecutionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> arg0, long arg1,
            TimeUnit arg2) throws InterruptedException, ExecutionException,
            TimeoutException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isShutdown() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isTerminated() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void shutdown() {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Runnable> shutdownNow() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> Future<T> submit(Callable<T> arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Future<?> submit(Runnable arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> Future<T> submit(Runnable arg0, T arg1) {
        // TODO Auto-generated method stub
        return null;
    }

}
