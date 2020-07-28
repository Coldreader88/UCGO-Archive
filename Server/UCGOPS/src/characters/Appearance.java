package characters;

import java.io.Serializable;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen holder informasjon en characters utseende.
 */
public class Appearance implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //Gyldige verdier for disse er de samme som UCGO klienten bruker.
    private int hair_color;
    private int hair_style;
    private int face_type;
    private int skin_color;

    public Appearance(int hair_color, int hair_style, int face_type, int skin_color) {
        this.hair_color = hair_color;
        this.hair_style = hair_style;
        this.face_type = face_type;
        this.skin_color = skin_color;
    }

    public int getHairColor() {
        return this.hair_color;
    }

    public int getHairStyle() {
        return this.hair_style;
    }

    public int getFaceType() {
        return this.face_type;
    }

    public int getSkinColor() {
        return this.skin_color;
    }
}
