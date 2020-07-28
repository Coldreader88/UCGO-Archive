package gameServer;

import java.io.FileInputStream;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å lese inn heightmap for Australia og finne riktig Z
 * koordinat for X,Y koordinater.
 *
 */
public class Heightmap {

    /**
     * Størrelsen på heightmap, i antall bytes.
     */
    private static final int HEIGHTMAP_SIZE = 54000000;

    /**
     * Antall enheter langs X aksen til heightmap.
     */
    private static final int SIZE_X = 6000;

    /**
     * Antall enheter langs Y aksen til heightmap.
     */
    private static final int SIZE_Y = 4500;

    /**
     * Størrelsen, i game units, på en verdi i heightmap.
     *
     * Dvs En verdi lest fra X,Y posisjon i heightmap tilsvarer 4000x4000 i
     * spillet.
     */
    private static final int XY_UNIT_SIZE = 4000;

    /**
     * Samme som for XY_UNIT_SIZE men for Z aksen.
     *
     * Dvs En verdi lest fra heightmap på posisjon X,Y ganges med 4 for å få
     * antall game units.
     */
    private static final int Z_UNIT_SIZE = 4;

    /**
     * Navn på filen som inneholder heightmap.
     */
    private static final String FILNAVN = "heightmap.bin";

    /**
     * Heightmap lest fra fil lagres her.
     */
    private static byte[] map = new byte[HEIGHTMAP_SIZE];

    public static void loadHeightmap() {

        System.out.print("Loading heightmap...");

        try {

            FileInputStream infil = new FileInputStream(FILNAVN);
            int size = infil.read(map);
            infil.close();

            if (size == HEIGHTMAP_SIZE) {
                System.out.println("OK!");
            } else {
                System.out.println("Error loading from file.");
            }

        } catch (Exception e) {

            System.out.println("ERROR!");
        }

    }

    /**
     * Returnerer heightmap verdien for oppgitt posisjon.
     *
     * NB! Returnert verdi er 16-bit og kan være negativ.
     *
     * @param x X posisjon i heightmap, < SIZE_X @pa ram y Y posisjon i
     * heightmap, < SIZE_Y
     *
     * @return Heightmap verdi på oppgitt X,Y posisjon.
     */
    private static double getHeight(int x, int y) {

        int v = 0;

        if (x < SIZE_X && x > 0 && y < SIZE_Y && y > 0) {

            int offset = 2 * (y * SIZE_X + x); //Ganger med 2 fordi map er byte[] mens heightmap bruker 16-bit verdier.

            v = map[offset] & 0xFF;
            v |= (map[offset + 1] << 8);

        }

        return v;
    }

    /**
     * Beregner Z koordinaten på punktet x,y
     *
     * x,y er koordinatene i spillet, ikke i heightmap.
     *
     * NB! Z koordinaten er ikke 100% nøyaktig. Da UCGO bruker kurver til
     * terrenget mens denne metoden baserer seg på rette linjer.
     *
     * @param x X posisjon i spillet.
     * @param y Y posisjon i spillet.
     *
     * @return Z koordinaten, i game units, på punktet x,y.
     */
    public static int getZ(int x, int y) {

        /*
		 * Heightmap er delt inn i "firkanter" på XY_UNIT_SIZE i størrelse.
		 * Reduser x,y slik at de er <XY_UNIT_SIZE og passer inn i en firkant.
		 *
		 * vx,vy = Posisjonen vi skal finne Z verdi for.
         */
        double vx = Math.abs(x % XY_UNIT_SIZE);
        double vy = Math.abs(y % XY_UNIT_SIZE);

        //Finn hvilket offset x,y er på i heightmap.
        int oX = (x - 53200000) / XY_UNIT_SIZE;
        int oY = (Math.abs(y) - 48000000) / XY_UNIT_SIZE;

        /*
		 * Beregning av Z verdi gjøres som følger:
		 * 
		 * Heightmap er delt inn i firkanter. La en linje fra punktet vx,vy krysse firkanten langs x-aksen i
		 * punktene 0,vy og XY_UNIT_SIZE,vy. Z verdien til disse punktene kan enkelt beregnes.
		 * 
		 * Trekk en linje fra punktet 0,vy,z til XY_UNIT_SIZE,vy,z. Denne linjen krysser punktet vx,vy og kan
		 * dermed brukes til å beregne Z verdi i vx,vy.
         */
        //Beregn stigningstallet til linje 0,0 - 0,XY_UNIT_SIZE
        double dL1 = (getHeight(oX, oY + 1) - getHeight(oX, oY)) / XY_UNIT_SIZE;

        //Beregn stigninstallet til linje XY_UNIT_SIZE,0 - XY_UNIT_SIZE,XY_UNIT_SIZE
        double dL2 = (getHeight(oX + 1, oY + 1) - getHeight(oX + 1, oY)) / XY_UNIT_SIZE;

        //Vi har nå stigningstallet til begge vertikale linjene som utgjør "firkanten".
        //En rett linje fra vx,vy krysser dem i punktet vy.
        //Beregn stigningstall for linjen gjennom vx,vy
        //Z verdiene for punktene der linjen fra vx,vy krysser.
        double zL1 = dL1 * vy + getHeight(oX, oY);
        double zL2 = dL2 * vy + getHeight(oX + 1, oY);

        double dL3 = (zL2 - zL1) / XY_UNIT_SIZE; //Stigningstall til linjen gjennom vx,vy

        double Zx = dL3 * vx + zL1; //Linjen krysser vx,vy gjennom X-aksen.

        //GJØR DET SAMME FOR Y AKSEN. KUN X-AKSEN ER LITT FOR UNØYAKTIG.
        //Beregn stigninstallet til linje 0,0 - XY_UNIT_SIZE,0
        double dL4 = (getHeight(oX + 1, oY) - getHeight(oX, oY)) / XY_UNIT_SIZE;

        //Beregn stigninstallet til linje 0,XY_UNIT_SIZE - XY_UNIT_SIZE,XY_UNIT_SIZE
        double dL5 = (getHeight(oX + 1, oY + 1) - getHeight(oX, oY + 1)) / XY_UNIT_SIZE;

        //Vi har nå stigninstallet til begge horisontale linjene som utgjør "firkanten"
        //En rett linje fra vx,vy krysser dem i punktet vx.
        //Beregn stigninstall for linjen gjennom vx,vy
        //Z verdiene for punktene der linjen fra vx,vy krysser.
        double zL4 = dL4 * vx + getHeight(oX, oY);
        double zL5 = dL5 * vx + getHeight(oX, oY + 1);

        double dL6 = (zL5 - zL4) / XY_UNIT_SIZE; //Stigninstall til linjen gjennom vx,vy

        double Zy = dL6 * vy + zL4; //Linjen krysser vx,vy gjennom Y-aksen.

        double Z = (Zx + Zy) / 2;

        return (int) (Z * Z_UNIT_SIZE) - 10; //-10 for å kompensere for unøyaktig beregning av Z koordinat.
    }

    /**
     * Beregner om det er frisikt fra punktet x1,y1,z1 til x2,y2,z2. Dvs at det
     * ikke er noe terreng mellom de to punktene.
     *
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     *
     * @return true dersom det er frisikt, false hvis ikke.
     */
    public static boolean friSikt(int x1, int y1, int z1, int x2, int y2, int z2) {

        int startX, startY, sluttX, sluttY;
        int Z; //Z verdi til sluttpunktet.

        /*
		 * Hvis nødvendig bytt om på punktene slik at vi starter på det LAVESTE punktet og beveger oss mot det høyeste.
		 * Ved å gå oppover langs en rett linje kan vi sjekke at Z verdien på et gitt punkt langs linjen ikke er høyere
		 * enn Z verdien til sluttpunktet.  Hvis Z verdien til et punkt langs linjen er høyere enn Z verdien til sluttpunktet
		 * er det terreng mellom x1,y1 og x2,y2 som sperrer sikten.
         */
        if (z1 <= z2) {
            startX = x1;
            startY = y1;
            sluttX = x2;
            sluttY = y2;
            Z = z2;
        } else {
            startX = x2;
            startY = y2;
            sluttX = x1;
            sluttY = y1;
            Z = z1;
        }

        //Beregn avstand mellom punktene langs X,Y aksene.
        int dx = Math.abs(startX - sluttX);
        int dy = Math.abs(startY - sluttY);

        int avstand = (int) Math.sqrt(dx * dx + dy * dy);

        //Dersom det er mindre enn 500 meter mellom punktene regnes det som frisikt.
        if (avstand < 2000) {
            return true; //2000= 500m i spillet.
        }
        //Vi sjekker Z verdien til punkter med et intervall på 50m langs linjen.
        int antallPunkter = avstand / 200;

        //Finn ut hvor mye X og Y skal endres for hvert punkt.
        int pX = dx / antallPunkter;
        int pY = dy / antallPunkter;

        if (startX > sluttX) {
            pX *= -1;
        }
        if (startY < sluttY) {
            pY *= -1;
        }

        //Sjekk punkt for punkt langs linjen helt til vi er innen 50m fra sluttpunktet.
        while (Math.abs(startX - sluttX) > 200 && Math.abs(startY - sluttY) > 200) {

            int pZ = getZ(startX, startY);

            if (pZ >= Z) {
                return false;
            }

            startX += pX;
            startY += pY;
        }

        return true;

    }

}
