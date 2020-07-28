package infoServer;

import java.util.*;
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
 * Dette er hovedfilen til UCGO Infoserver.
 */
public class start {

    public static void execute() {

        UCGOcrypto crypt = new UCGOcrypto();

        PlayerHandler<PlayerInfo> playerList = new PlayerHandler<PlayerInfo>();

        //ExecutorService pool = Executors.newFixedThreadPool(2);
        ExecutorService pool = new UCGOexecutor(2);

        Hashtable<Integer, Opcode> op = new Hashtable<Integer, Opcode>();

        //Registrer opcodes her.
        registerOpcodes.register(op);

        ServerLoop<PlayerInfo> sloop = new ServerLoop<PlayerInfo>(pool, playerList);

        ServerListener<PlayerInfo> slistener = new ServerListener<PlayerInfo>(
                config.Server.serverListenerLogin, 24012, crypt, playerList, op);

        new Thread(sloop).start();

        new Thread(slistener).start();

    }

}
