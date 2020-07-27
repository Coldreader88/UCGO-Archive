package chatServer;

import java.io.Serializable;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere et medlem av et team og inneholder
 * alle data som skal deles med de andre medlemmene.
 */
public class TeamMember implements Serializable {

    private static final long serialVersionUID = 1L;

    //Character ID for medlem.
    private int character_id;

    //Navn på medlemmet.
    private String navn;

    //Rank til medlemmet.
    private int rank;

    //Gender.
    private int gender;

    public TeamMember(int character_id, String navn, int rank, int gender) {
        this.character_id = character_id;
        this.navn = navn;
        this.rank = rank;
    }

    public int getCharacterID() {
        return this.character_id;
    }

    public String getNavn() {
        return this.navn;
    }

    public int getGender() {
        return this.gender;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
