package npc;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde info om et område, slikt som posisjon og
 * størrelse. Klassen skal ikke brukes på egenhånd men være del av en
 * AreaManager.
 *
 * NB! Total størrelse på et område beregnes som følger:
 *
 * posX,posY,posZ er sentrum. indreX osv..er hvor langt ut området strekker seg
 * langs hver akse, i begge retninger. Dvs: indrex=4000 betyr området er 8000
 * langs X aksen, +4000 for sentrum og -4000 for sentrum. ytreX osv kommer på
 * toppen av indreX Dvs: ytreX=8000, total størrelse er da ytreX+indreX=12000 i
 * hver retning.
 *
 */
public abstract class Area {

    /**
     * ID nummer til area, 0-4095.
     */
    protected int areaID;
    /**
     * X,Y,Z posisjon til området, i game units. Dette er sentrum.
     */
    protected int posX, posY, posZ;
    /**
     * X,Y,Z størrelse på det indre området.
     */
    protected int indreX, indreY, indreZ;
    /**
     * X,Y,Z størrelse på det ytre området.
     */
    protected int ytreX, ytreY, ytreZ;
    /**
     * Størrelsen på safe zone. Dette er et område på midten der NPCs ikke har
     * tilgang. Safe zone vil bli regnet som utenfor området av metoder som
     * isWithinArea()
     *
     * Safe zone er hovedsakelig beregnet for bruk i byer for å hindre at NPCs
     * kommer inn i byen. Foreløpig har vi ikke safe zone i space.
     *
     * Oppgitt størrelse er i hver retning.
     */
    private int safeZoneX, safeZoneY;
    /**
     * Hvilken faction området tilhører. 1=EF, 2=Zeon
     */
    protected int faction;
    /**
     * Hvilken zone området er i. 1=Ground, 2=Space.
     */
    protected int zone;
    /**
     * Angir om area er beregnet for NPC vs NPC kamper.
     */
    private boolean npcBattleground = false;

    /**
     *
     * @param areaID
     * @param faction
     * @param zone
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
    public Area(int areaID, int faction, int zone, int posX, int posY, int posZ, int iX, int iY, int iZ, int yX, int yY, int yZ) {

        this.areaID = areaID;
        this.faction = faction;
        this.zone = zone;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.indreX = iX;
        this.indreY = iY;
        this.indreZ = iZ;
        this.ytreX = yX;
        this.ytreY = yY;
        this.ytreZ = yZ;
    }

    /**
     * Oppretter et nytt area beregnet for NPC vs NPC kamper.
     *
     * @param areaID
     * @param npcBattleground
     * @param zone
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
    public Area(int areaID, boolean npcBattleground, int zone, int posX, int posY, int posZ, int iX, int iY, int iZ, int yX, int yY, int yZ) {

        this.areaID = areaID;
        this.faction = -1;
        this.zone = zone;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.indreX = iX;
        this.indreY = iY;
        this.indreZ = iZ;
        this.ytreX = yX;
        this.ytreY = yY;
        this.ytreZ = yZ;
        this.npcBattleground = npcBattleground;
    }

    public int getFaction() {
        return this.faction;
    }

    public int getZone() {
        return this.zone;
    }

    /**
     * Setter størrelsen på eventuell safe zone.
     *
     * @param x X størrelse, i game units.
     * @param y Y størrelse, i game units.
     */
    public void setSafeZone(int x, int y) {
        safeZoneX = x;
        safeZoneY = y;
    }

    public boolean isNpcBattleground() {
        return this.npcBattleground;
    }

    /**
     * Beregner om oppgitt X,Y posisjon er innenfor dette området. Dette er for
     * bruk hovedsaklig i ground zone. Z aksen taes ikke hensyn til.
     *
     * @param x
     * @param y
     * @return TRUE hvis x,y er innenfor området, FALSE hvis ikke.
     */
    public boolean isWithinArea(int x, int y) {
        if (x < posX - indreX - ytreX || x > posX + indreX + ytreX) {
            return false;
        }
        if (y < posY - indreY - ytreY || y > posY + indreY + ytreY) {
            return false;
        }

        return true;
    }

    /**
     * Beregner om oppgitt X,Y,Z posisjon er innenfor dette området. Dette er
     * for bruk hovedsaklig i space zone.
     *
     * NB! Dersom Area er i ground zone (1) vil denne metoden kalle
     * isWithinArea(x,y) istedenfor.
     *
     * @param x
     * @param y
     * @param z
     * @return TRUE hvis x,y,z er innenfor området, FALSE hvis ikke.
     */
    public boolean isWithinArea(int x, int y, int z) {
        if (zone == 1) {
            isWithinArea(x, y);
        }

        if (x < posX - indreX - ytreX || x > posX + indreX + ytreX) {
            return false;
        }
        if (y < posY - indreY - ytreY || y > posY + indreY + ytreY) {
            return false;
        }
        if (z < posZ - indreZ - ytreZ || z > posZ + indreZ + ytreZ) {
            return false;
        }

        return true;
    }

    /**
     * Beregner om oppgitt X,Y posisjon er innenfor det indre området der NPCs
     * skal bevege seg. Dette er for bruk hovedsaklig i ground zone.
     *
     * @param x
     * @param y
     *
     * @return TRUE hvis x,y er innenfor området, FALSE hvis ikke.
     */
    public boolean isWithinInnerArea(int x, int y) {
        if (x < posX - indreX || x > posX + indreX) {
            return false;
        }
        if (y < posY - indreY || y > posY + indreY) {
            return false;
        }

        return true;
    }

    /**
     * Beregner om oppgitt X,Y,Z posisjon er innenfor det indre området der NPCs
     * kan bevege seg. Dette er for bruk hovedsaklig i space zone.
     *
     * NB! Dersom Area er i ground zone (1) vil denne metoden kalle
     * isWithingInnerArea(x,y) istedenfor.
     *
     * @param x
     * @param y
     * @param z
     * @return TRUE hvis x,y,z er innenfor området, FALSE hvis ikke.
     */
    public boolean isWithinInnerArea(int x, int y, int z) {
        if (zone == 1) {
            return isWithinInnerArea(x, y);
        }

        if (x < posX - indreX || x > posX + indreX) {
            return false;
        }
        if (y < posY - indreY || y > posY + indreY) {
            return false;
        }
        if (z < posZ - indreZ || z > posZ + indreZ) {
            return false;
        }

        return true;
    }

    /**
     * Beregner om oppgit X,Y posisjon er innenfor safe zone.
     *
     * @param x
     * @param y
     * @return TRUE hvis x,y er innenfor safe zone.
     */
    protected boolean isWithinSafeZone(int x, int y) {
        if (safeZoneX <= 0 || safeZoneY <= 0) {
            return false;
        }

        if (x > (posX - safeZoneX) && x < (posX + safeZoneX) && y > (posY - safeZoneY) && y < (posY + safeZoneY)) {
            return true;
        } else {
            return false;
        }
    }

}
