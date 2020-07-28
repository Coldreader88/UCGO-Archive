package loginServer;

import java.util.Hashtable;
import java.util.concurrent.*;
import opcodes.*;
import packetHandler.UCGOcrypto;
import players.*;
import serverComp.*;
import serverComp.UCGOexecutor.UCGOexecutor;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette er hovedfilen til UCGO Loginserver.
 */
public class start {

    public static void execute() {

        UCGOcrypto crypt = new UCGOcrypto();

        PlayerHandler<PlayerLogin> playerList = new PlayerHandler<PlayerLogin>();
        PlayerHandlerStatic.registerPlayerHandlerLogin(playerList); //Slik at vi kan få statisk tilgang til spillere.

        //ExecutorService pool = Executors.newFixedThreadPool(2);
        ExecutorService pool = new UCGOexecutor(2);

        Hashtable<Integer, Opcode> op = new Hashtable<Integer, Opcode>();

        //Registrer opcodes her.
        registerOpcodes.register(op);

        ServerLoop<PlayerLogin> sloop = new ServerLoop<PlayerLogin>(pool, playerList);

        ServerListener<PlayerLogin> slistener = new ServerListener<PlayerLogin>(
                config.Server.serverListenerLogin, 24018, crypt, playerList, op);

        LoginServerCleanUp cleanup = new LoginServerCleanUp(playerList);

        new Thread(sloop).start();

        new Thread(slistener).start();

        new Thread(cleanup).start();

    }

}
