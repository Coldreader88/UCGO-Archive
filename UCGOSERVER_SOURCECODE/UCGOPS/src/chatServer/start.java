package chatServer;

import java.util.Hashtable;
import java.util.concurrent.*;
import opcodes.*;
import packetHandler.*;
import players.*;
import serverComp.*;
import serverComp.UCGOexecutor.UCGOexecutor;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette er hovedfilen til UCGO chat server.
 */
public class start {

    public static void execute() {

        UCGOcrypto crypt = new UCGOcrypto();

        PlayerHandler<PlayerChat> playerList = new PlayerHandler<PlayerChat>();
        PlayerHandlerStatic.registerPlayerHandlerChat(playerList); //Slik at vi kan få statisk tilgang til spillere.

        //ExecutorService pool = Executors.newFixedThreadPool(2);
        ExecutorService pool = new UCGOexecutor(2);

        Hashtable<Integer, Opcode> op = new Hashtable<Integer, Opcode>();

        //Registrer opcodes her.
        registerOpcodes.register(op);

        ServerLoop<PlayerChat> sloop = new ServerLoop<PlayerChat>(pool, playerList);

        ServerListener<PlayerChat> slistener = new ServerListener<PlayerChat>(
                config.Server.serverListenerChat, 24016, crypt, playerList, op);

        new Thread(sloop).start();

        new Thread(slistener).start();

    }
}
