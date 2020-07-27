package npc;

import characters.Posisjon;
import java.util.Random;

public class AreaManagerSpace extends AreaManager {

    /**
     *
     * @param areaID
     * @param faction
     * @param posX
     * @param posY
     * @param posZ
     * @param iX
     * @param iY
     * @param iZ
     * @param yX
     * @param yY
     * @param yZ
     */
    public AreaManagerSpace(int areaID, int faction, int posX, int posY, int posZ, int iX, int iY, int iZ, int yX, int yY, int yZ) {
        super(areaID, faction, 2, posX, posY, posZ, iX, iY, iZ, yX, yY, yZ);
    }

    /**
     * For bruk med NPC vs NPC områder.
     */
    public AreaManagerSpace(int areaID, int posX, int posY, int posZ, int iX, int iY, int iZ, int yX, int yY, int yZ) {
        super(areaID, 2, posX, posY, posZ, iX, iY, iZ, yX, yY, yZ);
    }

    public void addNpcGrunts(Template t, int n) {
        addNpcGrunts(t, n, faction);
    }

    public void addNpcGrunts(Template t, int n, int faction) {

        //Finn øverste venstre hjørne av området.
        int startX = this.posX - this.indreX;
        int startY = this.posY - this.indreY;
        int startZ = this.posZ - this.indreZ;

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
                z = startZ + r.nextInt(this.indreZ * 2);

                ledig = this.posisjonLedig(x, y, z, null);
            }

            //OK, da har vi en ledig plass til NPC.
            Posisjon pos = new Posisjon(x, y, z, r.nextInt(65535), r.nextInt(65535), r.nextInt(65535), 2);

            this.spawnNpc(t, pos, "", -1, faction);
        }

    }

    protected void respawnGruntNPC(NPCvehicle n) {

        //Finn øverste venstre hjørne av området.
        int startX = this.posX - this.indreX;
        int startY = this.posY - this.indreY;
        int startZ = this.posZ - this.indreZ;

        Random r = new Random();

        int x = 0;
        int y = 0;
        int z = 0;

        boolean ledig = false;

        while (!ledig) {

            //Velg en tilfeldig posisjon der NPC skal plasseres.	
            x = startX + r.nextInt(this.indreX * 2);
            y = startY + r.nextInt(this.indreY * 2);
            z = startZ + r.nextInt(this.indreZ * 2);

            ledig = this.posisjonLedig(x, y, z, null);
        }

        //OK, da har vi en ledig plass, respawn NPC.
        Posisjon pos = new Posisjon(x, y, z, r.nextInt(65535), r.nextInt(65535), r.nextInt(65535), 2);

        n.respawn(pos);

    }
}
