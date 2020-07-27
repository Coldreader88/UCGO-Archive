package players;

import characters.*;
import java.util.Hashtable;
import opcodes.Opcode;
import packetHandler.PlayerConnection;

/**
 *
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes av gameserver til å representere en tilkoblet bruker.
 */
public class PlayerGame extends Player {

    //Character spilleren bruker lagres her.
    private CharacterGame character;

    /**
     *
     * @param op Liste over tilgjengelige opcodes vi kan bruke.
     * @param tilkobling PlayerConnection objektet vi skal bruke for å
     * kommunisere med bruker.
     */
    public PlayerGame(Hashtable<Integer, Opcode> op, PlayerConnection tilkobling) {
        super(op, tilkobling);
    }

    /**
     * Setter character spilleren bruker.
     *
     * @param c Character for game serveren.
     */
    public void setCharacter(CharacterGame c) {
        this.character = c;
        this.character.setUCGM(this.getUCGM());
        this.character.setIP(this.getIP());
    }

    /**
     * Returnerer character spilleren bruker.
     *
     * @return CharacterGame objekt.
     */
    public CharacterGame getCharacter() {
        return this.character;
    }

    @Override
    public synchronized void save() {

        try {
            //Lagre active vehicle.
            int vehicle = 0;

            if (this.character.getVehicle() != null) {
                vehicle = this.character.getVehicleContainer().getID();
            } else if (this.character.getTaxiTransport() != null) {
                vehicle = this.character.getTaxiTransportContainer().getID();
            }

            userDB.GameCharacter GC = userDB.ManageCharacters.getGameCharacter(this.character.getCharacterID());

            if (GC != null) {
                GC.setActiveVehicle(vehicle);
                GC.setScore(this.character.getScore());
                GC.setLosses(this.character.getLosses());
                GC.setRank(this.character.getRank());
                GC.setRankScore(this.character.getRankScore());
                GC.setNewman(this.character.getNewmanMedal());
                GC.setRichmond(this.character.getRichmondMedal());
            }

            //Lagre WeaponsRoom hvis i ms/vehicle.
            if (this.getCharacter().getVehicle() != null) {
                this.getCharacter().getVehicle().saveWeaponsRoom();
            }

            //Lagre Weared container.
            this.getCharacter().getClothing().saveWearedContainer();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("\nError saving character data. ");
            if (this.getCharacter() != null) {
                System.out.println("Character ID: " + this.getCharacter().getCharacterID());
            } else {
                System.out.println("PlayerGame.character=null");
            }
        }
    }
}
