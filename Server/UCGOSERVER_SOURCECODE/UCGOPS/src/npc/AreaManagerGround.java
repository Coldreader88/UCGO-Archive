package npc;

import characters.Posisjon;
import java.util.Random;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å håndtere NPCs i et område på bakken.
 */
public class AreaManagerGround extends AreaManager {

    public AreaManagerGround(int areaID, int faction, int posX, int posY, int iX, int iY, int yX, int yY) {
        super(areaID, faction, 1, posX, posY, 0, iX, iY, 0, yX, yY, 0);
    }

    /**
     * Oppretter nytt area beregnet for NPC vs NPC bruk.
     */
    public AreaManagerGround(int areaID, int posX, int posY, int iX, int iY, int yX, int yY) {
        super(areaID, 1, posX, posY, 0, iX, iY, 0, yX, yY, 0);
    }

    public void addNpcGrunts(Template t, int n) {
        addNpcGrunts(t, n, faction);
    }

    public void addNpcGrunts(Template t, int n, int faction) {

        //Finn øverste venstre hjørne av området.
        int startX = this.posX - this.indreX;
        int startY = this.posY - this.indreY;

        Random r = new Random();

        int x = 0;
        int y = 0;
        int z = 0;

        for (int i = 0; i < n; i++) {

            boolean ledig = false;

            while (!ledig) {

                //Velg en tilfeldig posisjon der NPC skal plasseres.	
                x = startX + r.nextInt(this.indreX * 2);
                y = startY + r.nextInt(this.indreY * 2);

                z = gameServer.Heightmap.getZ(x, y);

                ledig = this.posisjonLedig(x, y, z, null);

                //Sjekk også at vi ikke plasserer NPC i en safe zone.
                if (isWithinSafeZone(x, y)) {
                    ledig = false;
                }
            }

            //OK, da har vi en ledig plass til NPC.
            Posisjon pos = new Posisjon(x, y, z, r.nextInt(65535), 1);

            this.spawnNpc(t, pos, "", -1, faction);
        }

    }

    protected void respawnGruntNPC(NPCvehicle n) {

        //Finn øverste venstre hjørne av området.
        int startX = this.posX - this.indreX;
        int startY = this.posY - this.indreY;

        Random r = new Random();

        int x = 0;
        int y = 0;
        int z = 0;

        boolean ledig = false;

        while (!ledig) {

            //Velg en tilfeldig posisjon der NPC skal plasseres.	
            x = startX + r.nextInt(this.indreX * 2);
            y = startY + r.nextInt(this.indreY * 2);

            z = gameServer.Heightmap.getZ(x, y);

            ledig = this.posisjonLedig(x, y, z, null);

            //Sjekk også at vi ikke plasserer NPC i en safe zone.
            if (isWithinSafeZone(x, y)) {
                ledig = false;
            }
        }

        //OK da har vi en ledig posisjon, respawn NPC.
        Posisjon pos = new Posisjon(x, y, z, r.nextInt(65535), 1);

        n.respawn(pos);
    }
}
