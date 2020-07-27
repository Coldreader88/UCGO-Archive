package players;

import characters.CharacterChat;
import java.util.Hashtable;
import opcodes.Opcode;
import packetHandler.PlayerConnection;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes av chatserver til å representere en tilkoblet bruker.
 */
public class PlayerChat extends Player {

    private CharacterChat character;

    /**
     *
     * @param op Liste over tilgjengelige opcodes vi kan bruke.
     * @param tilkobling PlayerConnection objektet vi skal bruke for å
     * kommunisere med bruker.
     */
    public PlayerChat(Hashtable<Integer, Opcode> op, PlayerConnection tilkobling) {
        super(op, tilkobling);
    }

    /**
     * Setter character objektet for å representere spilleren ingame.
     *
     * @param c Character objekt for spiller.
     */
    public void setCharacter(CharacterChat c) {
        this.character = c;
        this.character.setIP(this.getIP());
    }

    /**
     * Returnerer objektet som representerer spilleren ingame.
     *
     * @return CharacterChat objekt.
     */
    public CharacterChat getCharacter() {
        return this.character;
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub

    }

}
