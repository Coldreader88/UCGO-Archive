package occupationCity;

import java.util.Iterator;
import java.util.LinkedList;
import npc.*;
import packetHandler.PacketData;
import players.PlayerGame;

/**
 * Denne klassen brukes til å representere en PVP by/område. Klassen inneholder
 * info om når neste angrep skal begynne, ICF status og hvem som eier
 * byen/området.
 *
 * For PVP områder som ikke har ICF f.eks Helenes, vil denne klassen ha 5 NPC
 * bosses istedenfor som fungerer som ICFs.
 *
 * @author UCGOSERVER.COM
 *
 */
public class City {

    /**
     * Dette er ID som brukes til å identifisere byen. Samme ID som brukes
     * internt i UCGO klienten.
     */
    private int cityID;

    /**
     * Hvilken faction byen/området tilhører, 1=EF, 2=Zeon.
     */
    private int faction;

    /**
     * Holder status for ICFs. Dvs hvilken faction de tilhører.
     *
     * Array skal ha 5 elementer, settes i contructor.
     */
    private int[] ICF;

    /**
     * For steder som ikke har fysiske ICFs f.eks Helenes, vil dette arrayet
     * holde 5 EF Boss NPCs som tilsvarer de fem ICF-ene.
     */
    private NPCboss[] EfICFnpc = new NPCboss[5];

    /**
     * For steder som ikke har fysiske ICFs f.eks Helenes, vil dette arrayet
     * holde 5 Zeon Boss NPCs som tilsvarer de fem ICF-ene.
     */
    private NPCboss[] ZeonICFnpc = new NPCboss[5];

    /**
     * Angir når neste angrep skal begynne eller når aktivt angrep er over. Unix
     * timestamp i sekunder.
     */
    private int countdownTimer;

    /**
     * Status for byen.
     *
     * 0 = Cease fire 1 = Treaty 2 = War
     */
    private int status;

    /**
     * Inneholder en liste over alle spillere som deltar i kampen om en PVP by.
     */
    private LinkedList<PlayerGame> participants = new LinkedList<PlayerGame>();

    /**
     * Hver gang status, icf, timer for en by oppdateres økes denne med 1.
     */
    private int oppdateringsTeller;

    /**
     * Oppretter en ny by og setter faction. Status settes til treaty.
     *
     * @param ID til byen.
     * @param faction Faction byen tilhører.
     */
    public City(int cityID, int faction) {

        this.cityID = cityID;
        this.faction = faction;
        this.ICF = new int[]{faction, faction, faction, faction, faction};
        this.status = 1;
    }

    /**
     * Setter status for en ICF. Dvs hvilken faction den tilhører.
     *
     * @param icf Hvilken ICF vi skal sette status for. 0-4.
     * @param status Faction for ICF.
     */
    public void setICFstatus(int icf, int status) {

        if (icf < 0 || icf > 4) {
            return;
        }

        this.ICF[icf] = status;
        this.oppdateringsTeller++;
    }

    /**
     * For bruk i PVP områder som ikke har ICFs. Setter en NPCboss som skal
     * representere en ICF.
     *
     * Denne vil sette NPC til å være ikke aktiv.
     *
     * @param n NPC Boss
     * @param icf ICF nummer, 0-4.
     * @param faction 1=EF, 2=Zeon.
     */
    public void setICFnpc(NPCboss n, int icf, int faction) {

        n.setActive(false);

        if (faction == 1) {
            this.EfICFnpc[icf] = n;
        } else if (faction == 2) {
            this.ZeonICFnpc[icf] = n;
        }
    }

    /**
     * Setter status for byen/området.
     *
     * @param status Ny status.
     */
    public void setStatus(int status) {

        this.status = status;
        this.oppdateringsTeller++;
    }

    /**
     *
     * @return Status for byen.
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Setter faction byen/området tilhører.
     *
     * @param faction Faction.
     */
    public void setFaction(int faction) {

        this.faction = faction;
        this.ICF[0] = faction;
        this.ICF[1] = faction;
        this.ICF[2] = faction;
        this.ICF[3] = faction;
        this.ICF[4] = faction;
        this.oppdateringsTeller++;
    }

    public int getFaction() {
        return this.faction;
    }

    /**
     * Setter countdown telleren for byen. Denne metoden vil IKKE endre på
     * oppdateringsTeller.
     *
     * @param countDown Countdown verdi. Unix timestamp.
     */
    public void setCountDown(int countDown) {
        this.countdownTimer = countDown;
    }

    /**
     *
     * @return Countdown timer for byen. Unix timestamp.
     */
    public int getCountDownTimer() {
        return this.countdownTimer;
    }

    public int getOppdateringsTeller() {
        return this.oppdateringsTeller;
    }

    public int getCityID() {
        return this.cityID;
    }

    /**
     * Registrerer en spiller som deltaker i kampen om byen.
     *
     * @param p Spiller.
     */
    public void registerPlayerToBattle(PlayerGame p) {
        this.participants.add(p);
    }

    /**
     * Fjerner en spiller fra listen over deltakere i kampen om byen.
     *
     * @param p Spiller
     */
    public void unregisterPlayerFromBattle(PlayerGame p) {
        this.participants.remove(p);
    }

    /**
     * Tømmer listen over alle deltakere i angrepet.
     *
     */
    public void clearParticipantList() {
        this.participants.clear();
    }

    /**
     *
     * @return Iterator over alle deltakerene i angrepet på en by. Iterator er
     * basert på en ny liste og er threadsafe.
     */
    public Iterator<PlayerGame> getParticipants() {
        return this.participants.iterator();
    }

    /**
     * Brukes til å sjekke resultatet når byen er i WAR mode.
     *
     * @return Hvilken faction som eier byen, BASERT PÅ ICF STATUS! Dersom
     * resultatet er forskjellig fra faction (getFaction) som eide byen før WAR
     * har byen blitt overtatt av angriperene.
     */
    public int getWarResult() {

        if (ICF[0] != this.faction && ICF[0] == ICF[1] && ICF[1] == ICF[2] && ICF[2] == ICF[3]
                && ICF[3] == ICF[4] && ICF[4] == ICF[0]) {
            return ICF[0];
        } else {
            return this.faction;
        }
    }

    /**
     * Går gjennom alle Boss NPCs som brukes til å representere ICFs og
     * respawner dem og setter dem som aktiv.
     */
    public void startICFnpcs() {

        //Finn hvilken faction sine ICF NPCs som skal startes.
        NPCboss[] ICFnpc = null;

        if (this.faction == 1) {
            ICFnpc = this.EfICFnpc;
        } else if (this.faction == 2) {
            ICFnpc = this.ZeonICFnpc;
        }

        for (int i = 0; i < ICFnpc.length; i++) {
            if (ICFnpc[i] != null) {
                ICFnpc[i].respawn();
                ICFnpc[i].setActive(true);
            }
        }
    }

    /**
     * Går gjennom alle Boss NPCs som brukes til å reprensentere ICFs og gjør
     * dem inaktive.
     */
    public void stopICFnpcs() {

        for (int i = 0; i < this.EfICFnpc.length; i++) {
            if (this.EfICFnpc[i] != null) {
                this.EfICFnpc[i].setActive(false);
            }
        }

        for (int i = 0; i < this.ZeonICFnpc.length; i++) {
            if (this.ZeonICFnpc[i] != null) {
                this.ZeonICFnpc[i].setActive(false);
            }
        }
    }

    /**
     * Går gjennom alle Boss NPCs som representerer ICFs og sjekker statusen
     * deres. Dersom en Boss NPC har blitt "killed" vil ICF bli "captured",
     * forutsatt at ICF ikke har blitt "captured" tidligere.
     *
     */
    public void checkICFnpcs() {

        //Finn hvilken faction sine ICF NPCs som skal sjekkes.
        NPCboss[] ICFnpc = null;

        if (this.faction == 1) {
            ICFnpc = this.EfICFnpc;
        } else if (this.faction == 2) {
            ICFnpc = this.ZeonICFnpc;
        }

        for (int i = 0; i < ICFnpc.length; i++) {
            if (ICFnpc[i] != null && !ICFnpc[i].isActive()) {
                //NPC er ikke lengre aktiv, dvs den har blitt "killed". Sjekk om ICF status skal endres.
                if (this.ICF[i] == ICFnpc[i].getFaction()) {
                    //ICF har blitt overtatt.
                    //Bytt faction for ICF.
                    int faction;
                    if (this.ICF[i] == 1) {
                        faction = 2;
                    } else {
                        faction = 1;
                    }
                    OccupationCity.captureICF(cityID, i, faction);
                }
            }
        }

    }

    /**
     *
     * @return Data om byen/området som skal settes inn i opcode 0x70.
     */
    public byte[] getOpcode70data() {

        PacketData pd = new PacketData();

        pd.writeIntBE(this.cityID);
        pd.writeShortBE((short) this.faction);
        pd.writeIntBE(this.status);
        pd.writeIntBE(this.countdownTimer);
        pd.writeByte((byte) 0x85);
        pd.writeShortBE((short) this.ICF[0]);
        pd.writeShortBE((short) this.ICF[1]);
        pd.writeShortBE((short) this.ICF[2]);
        pd.writeShortBE((short) this.ICF[3]);
        pd.writeShortBE((short) this.ICF[4]);
        pd.writeIntBE(this.oppdateringsTeller);

        return pd.getData();
    }

}
