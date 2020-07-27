package players;

import java.util.Hashtable;
import opcodes.Opcode;
import packetHandler.PlayerConnection;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes av infoserver til å representere en tilkoblet bruker.
 */
public class PlayerInfo extends Player {

    /**
     *
     * @param op Liste over tilgjengelige opcodes vi kan bruke.
     * @param tilkobling PlayerConnection objektet vi skal bruke for å
     * kommunisere med bruker.
     */
    public PlayerInfo(Hashtable<Integer, Opcode> op, PlayerConnection tilkobling) {
        super(op, tilkobling);
    }

    public void save() { //Infoserveren trenger ikke å save noe som helst.
    }

}
