package players;

import java.util.Hashtable;
import opcodes.*;
import packetHandler.PlayerConnection;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes av loginserver til å representere en tilkoblet bruker.
 */
public class PlayerLogin extends Player {

    /**
     *
     * @param op Liste over tilgjengelige opcodes vi kan bruke.
     * @param tilkobling PlayerConnection objektet vi skal bruke for å
     * kommunisere med bruker.
     */
    public PlayerLogin(Hashtable<Integer, Opcode> op, PlayerConnection tilkobling) {
        super(op, tilkobling);
    }

    /**
     * Denne metoden returnerer unix timestamp for når spiller koblet til
     * server.
     *
     * @return Unix timestamp som angir når spiller koblet til server.
     */
    public int getConnectTime() {
        return this.creationTime;
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub

    }

}
