package npc;

import characters.CharacterGame;
import characters.Posisjon;
import java.util.Iterator;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen representerer grunnleggende info for NPCs.
 *
 *
 */
public abstract class NPC {

    /**
     * ID til NPC,
     */
    private int npcID;

    /**
     * Team NPC er med i.
     */
    protected int teamID = -1;

    /**
     * Hver gang NPC endrer utseende øker denne med 1.
     */
    protected int appearanceCounter = 0;

    /**
     * pos = Posisjonen til NPC. initPos = Start posisjon til NPC. Nyttig dersom
     * NPC skal spawnes på samme sted hver gang.
     */
    protected Posisjon pos, initPos;

    /**
     * Navn til NPC. Kan være tomt.
     */
    protected String navn = "";

    /**
     * Faction NPC tilhører. 1=EF, 2=Zeon.
     */
    protected int faction;

    /**
     * Rank til NPC.
     */
    protected int rank;

    /**
     *
     * @param id ID nummer til NC.
     */
    public NPC(int id) {
        this.npcID = id;
    }

    public int getID() {
        return this.npcID;
    }

    public int getFaction() {
        return this.faction;
    }

    public void setFaction(int faction) {
        this.faction = faction;
    }

    public int getRank() {
        return this.rank;
    }

    public String getNavn() {
        return this.navn;
    }

    public Posisjon getPosisjon() {
        return this.pos;
    }

    /**
     *
     * @return Machine damage til NPC, 0-100.
     */
    public abstract byte getMachineDamage();

    /**
     * Respawner NPC.
     */
    public abstract void respawn();

    /**
     *
     * @return Tidspunktet, unix timestamp, når NPC ble sist destroyed.
     */
    public abstract int getKillTime();

    /**
     *
     * @return TRUE dersom NPC er aktiv og skal vises til andre spillere.
     */
    public abstract boolean isActive();

    /**
     * Utfører NPC AI.
     */
    public abstract void performAI();

    /**
     * Tar imot en liste over mulige mål NPC kan angripe og prøver å velge et av
     * dem.
     *
     * @param targets Fiendtlige spillere innenfor området.
     * @param area Eventuelt area NPC befinner seg innenfor. Eller NULL dersom
     * styrt av GlobalManager.
     */
    public abstract void chooseTarget(Iterator<NPCtarget> targets, Area area);

    /**
     *
     * @return Data som settes inn i opcode 0x800A.
     *
     * NB! Denne metoden returnerer HELE opcode 0x800A.
     */
    public abstract byte[] getData800A();

    /**
     * @param playerZ Spiller sin Z posisjon. På bakken bruker vi den verdien
     * som er størst av spiller Z og heightmap Z. Dette for å unngå at NPCs
     * flyter over bakken. For space zone setter vi bare playerZ til MaxValue.
     * @return Data som settes inn i opcode 0x8003.
     */
    public abstract byte[] getData8003(int playerZ);

}
