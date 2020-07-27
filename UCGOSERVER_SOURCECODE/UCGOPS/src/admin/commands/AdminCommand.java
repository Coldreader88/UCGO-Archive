package admin.commands;

import players.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette interface skal implementeres av alle klasser som håndterer kommandoer
 * mottatt via admin systemet.
 */
public interface AdminCommand {

    /**
     * Denne metoden inneholder koden for å håndtere en kommando.
     *
     * @param p Spilleren som sendte kommando.
     * @param args Kommandoen + eventuelle argumenter. args[0]=kommandoen,
     * args[1...n]=argumenter.
     */
    void execute(PlayerChat p, String[] args);

    /**
     * Sjekker om oppgitt spiller har rettighetene til å utføre kommandoen. NB!
     * Det antaes at oppgitt spiller er UCGM.
     *
     * @param p Spiller som sendte kommando.
     * @return TRUE hvis spilleren har rett til å utføre denne kommandoen, FALSE
     * hvis ikke.
     */
    boolean checkGMrights(PlayerChat p);
}
