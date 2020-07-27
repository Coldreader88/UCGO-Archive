package serverComp;

import java.io.IOException;
import java.net.*;
import java.util.Hashtable;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å lytte etter innkommende tilkoblinger.
 *
 */
public class ServerListener<E extends Player> implements Runnable {

    /**
     * Synchronized Set som holder alle tilkoblede brukere. Når brukere kobler
     * til blir de lagret her.
     */
    private PlayerHandler<E> playerList;

    /**
     * Tilsvarer config.Server.ServerListenerXXXX. Forteller hvilken server type
     * dette er.
     */
    int mode;

    /**
     * Port vi skal lytte på.
     */
    int port;

    /**
     * Socketen vi lytter på.
     */
    ServerSocket socket;

    /**
     * Crypto objektet serveren bruker for kryptering/dekryptering.
     */
    UCGOcrypto crypt;

    /**
     * Liste over opcodes serveren støtter.
     */
    Hashtable<Integer, Opcode> op;

    /**
     * @param port Port vi skal lytte på
     * @param crypt UCGOcrypto objektet serveren bruker.
     * @param playerList Set der nye tilkoblede brukere skal lagres.
     * @param op Opcode listen for å håndtere pakker.
     * @param mode Hvilken modus vi skal operere i, tilsvarer
     * config.Server.ServerListenerXXXX.
     */
    public ServerListener(int mode, int port, UCGOcrypto crypt, PlayerHandler<E> playerList, Hashtable<Integer, Opcode> op) {

        this.port = port;
        this.playerList = playerList;
        this.crypt = crypt;
        this.op = op;
        this.mode = mode;
    }

    public void run() {

        this.createListener();

        if (this.socket == null) {
            System.out.println("serverListener.run(): Can not listen on port " + this.port);
            return;
        }

        while (true) {
            try {

                //Ta imot nye brukere.
                Socket s = this.socketListen();

                //Sjekk at socket er OK og at IP ikke er blokkert.
                if (s != null && !admin.ipblock.isBlocked(s.getInetAddress().getHostAddress())) {
                    //Registrer nye spillere.
                    this.registerPlayer(s);
                } else if (s != null) {
                    //Bruker er blokkert, log IP.
                    admin.logging.globalserverMsg("Client connection refused. IP:" + s.getInetAddress().getHostAddress());
                } else {
                    Thread.sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Lager en ny serversocket vi kan lytte på.
     *
     * @return True hvis OK og serversocket lagret i this.socket. False hvis
     * feil.
     */
    private boolean createListener() {

        ServerSocket ss;

        try {
            ss = new ServerSocket(this.port);
        } catch (IOException e) {
            //Kunne ikke sette opp en socket server.
            return false;
        }

        this.socket = ss;

        return true;
    }

    /**
     * Lytter på this.socket etter innkommende tilkobling.
     *
     * @return Socket for en ny bruker, eller null hvis problemer.
     */
    private Socket socketListen() {

        Socket s;

        try {
            s = this.socket.accept();
            s.setTcpNoDelay(true);
        } catch (Exception e) {
            return null;
        }

        return s;
    }

    /**
     * Registerer en ny bruker i playerList.
     *
     * @param s Hvilken socket brukeren er tilkoblet.
     */
    @SuppressWarnings("unchecked")
    private void registerPlayer(Socket s) {

        PlayerConnection t = new PlayerConnection(s, this.crypt);

        //Basert på servertype, registrer et Player objekt.
        switch (this.mode) {

            case config.Server.serverListenerLogin:

                this.playerList.add((E) new PlayerLogin(this.op, t));
                break;

            case config.Server.serverListenerChat:

                this.playerList.add((E) new PlayerChat(this.op, t));
                break;

            case config.Server.serverListenerGame:

                this.playerList.add((E) new PlayerGame(this.op, t));
                break;

            case config.Server.serverListenerInfo:

                this.playerList.add((E) new PlayerInfo(this.op, t));
                break;
        }
    }

}
