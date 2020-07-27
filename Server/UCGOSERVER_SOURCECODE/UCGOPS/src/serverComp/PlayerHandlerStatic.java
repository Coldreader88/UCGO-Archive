package serverComp;

import players.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Ikke verdens beste løsning, men dette var nødvendig å gjøre når serverene ble
 * slått sammen til en.
 *
 * Denne klassen gir statisk tilgang til data lagret i en instance av
 * PlayerHandler. Det er mulig å få statisk tilgang til PlayerHandler for login,
 * chat og gameserver.
 *
 * Ved oppstart av server må instance av PlayerHandler for hver server type
 * registreres i denne klassen.
 */
public class PlayerHandlerStatic {

    private static PlayerHandler<PlayerLogin> loginPlayerHandler;
    private static PlayerHandler<PlayerChat> chatPlayerHandler;
    private static PlayerHandler<PlayerGame> gamePlayerHandler;

    /**
     * Registrerer en instance av PlayerHandler.
     *
     * @param p PlayerHandler objektet som brukes til å representere spillere
     * tilkoblet serveren.
     */
    public static void registerPlayerHandlerLogin(PlayerHandler<PlayerLogin> p) {
        loginPlayerHandler = p;
    }

    /**
     * Registrerer en instance av PlayerHandler.
     *
     * @param p PlayerHandler objektet som brukes til å representere spillere
     * tilkoblet serveren.
     */
    public static void registerPlayerHandlerChat(PlayerHandler<PlayerChat> p) {
        chatPlayerHandler = p;
    }

    /**
     * Registrerer en instance av PlayerHandler.
     *
     * @param p PlayerHandler objektet som brukes til å representere spillere
     * tilkoblet serveren.
     */
    public static void registerPlayerHandlerGame(PlayerHandler<PlayerGame> p) {
        gamePlayerHandler = p;
    }

    /**
     * Fjerner en spiller fra listen over tilkoblede spillere.
     *
     * @param spiller Spiller som skal fjernes.
     *
     * @return true hvis fjernet, false hvis ikke.
     */
    public static synchronized boolean removePlayerLogin(PlayerLogin spiller) {
        return loginPlayerHandler.remove(spiller);
    }

    /**
     * Fjerner en spiller fra listen over tilkoblede spillere.
     *
     * @param spiller Spiller som skal fjernes.
     *
     * @return true hvis fjernet, false hvis ikke.
     */
    public static synchronized boolean removePlayerChat(PlayerChat spiller) {
        return chatPlayerHandler.remove(spiller);
    }

    /**
     * Fjerner en spiller fra listen over tilkoblede spillere.
     *
     * @param spiller Spiller som skal fjernes.
     *
     * @return true hvis fjernet, false hvis ikke.
     */
    public static synchronized boolean removePlayerGame(PlayerGame spiller) {
        return gamePlayerHandler.remove(spiller);
    }

    /**
     * Søker gjennom listen over tilkoblede spillere etter en spiller med
     * oppgitt account ID.
     *
     * @param account_id Account ID vi søker ettter.
     * @return Spiller som har oppgitt account ID, eller null hvis ikke funnet.
     */
    public static synchronized PlayerLogin getPlayerLogin(int account_id) {
        return loginPlayerHandler.getPlayer(account_id);
    }

    /**
     * Søker gjennom listen over tilkoblede spillere etter en spiller med
     * oppgitt account ID.
     *
     * @param account_id Account ID vi søker ettter.
     * @return Spiller som har oppgitt account ID, eller null hvis ikke funnet.
     */
    public static synchronized PlayerChat getPlayerChat(int account_id) {
        return chatPlayerHandler.getPlayer(account_id);
    }

    /**
     * Søker gjennom listen over tilkoblede spillere etter en spiller med
     * oppgitt account ID.
     *
     * @param account_id Account ID vi søker ettter.
     * @return Spiller som har oppgitt account ID, eller null hvis ikke funnet.
     */
    public static synchronized PlayerGame getPlayerGame(int account_id) {
        return gamePlayerHandler.getPlayer(account_id);
    }

}
