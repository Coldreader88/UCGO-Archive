package characters;

import characters.Character;
import containers.*;
import items.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere en character i game serveren.
 *
 */
public class CharacterGame extends Character {

    //Hoved containere brukt av character. House og realestate er ikke tatt med.
    private HovedContainer backpack, weared, bank, money, hangar, selfstorage, productive, swappack, credit;

    //Characters posisjon i spillet.
    private Posisjon pos;

    //Hvis character er i en MS/vehicle lagres link til vehicle her. Gjelder ikke taxi/transport.
    private Vehicle active_vehicle;
    //Hvis character er i MS/vehicle lagres itemcontainer for vehicle her.
    private ItemContainer active_vehicle_ic;

    //Hvis character er i en taxi eller transport lagres link til den her.
    private TaxiTransport active_transport;
    //Samme som for vehicle.
    private ItemContainer active_transport_ic;

    //Denne øker med en her gang character endrer på utseendet. Brukes i opcode 0x8003.
    private int appearance_change = 0;

    //Team spiller er med i. -1 = ikke medlem.
    private int team_id = -1;

    //Occupancy tag. Settes i opcode 0x3. Avgjør ting som R,ER osv tags + mining animasjon.
    private int occupancy_tag;

    //Action. Hva spiller gjør, f.eks sitter,står osv.
    private int action;

    //Angir UCGM tag for en spiller. Denne vises til andre spillere i opcode 0x3.
    private int ucgm = 0xA;

    //Timestamp, angir sist gang opcode 0x3 ble mottatt. Brukes til å finne ut om spiller er koblet fra server.
    //Start tid er nåværende tidspunkt. Oppgitt i sekunder.
    private int lastTime0x3Received = (int) (System.currentTimeMillis() / 1000);

    //Timestamp, angir sist gang opcode 0x3 ble mottatt fra gameserver. 
    //Brukes til å finne ut om spiller fusker ved å bruke eksternt program til å øke farten på UCGO.
    //Oppgitt i millisekunder. IKKE FORVEKSLE DETTE MED 0x3 TIMESTAMO OVENFOR.
    private long lastTimeOpcode0x3Received = System.currentTimeMillis();

    /**
     * Dersom vi får en opcode 0x3 som ankommer for tidlig vil dette feltet bli
     * inkrementert. Når dette feltet har nådd et visst antall vil dette bli
     * tolket som at spiller fusker. Feltet vil bli nullstilt hver gang vi
     * mottar en opcode 0x3 som ikke ankommer for tidlig.
     */
    private int opcode0x3counter = 0;

    //Attack ID som settes inn i opcode 0x3.
    private int attack_id = 0;

    //Indikerer om character er synlig for andre spillere.
    private boolean visible = true;

    //Timestamp som angir når character ble sist angrepet.
    private int lastTimeAttacked = 0;

    //Indikerer om spiller er merket criminal eller ikke.
    private boolean criminal = false;

    //Denne telleren oppdateres hver gang spiller beveger seg, brukes til å avgjøre om MS/MA/Fighter skill skal øke.
    private int movementUpdate = 0;

    //Brukt av #radius admin kommandoen.
    public long radius = 0;

    //Angir når vi forventer å motta opcode 0x28 neste gang. Når spiller crafter et vehicle settes dette feltet til 
    //nåværende tidspunkt + hvor lang tid vehicle crafting SKAL TA, oppgitt i sekunder.
    //DVS at dersom vi mottar opcode 0x28 FØR tiden angitt her er det trolig at spiller driver med speed cheating.
    private int opcode28timer = 0;

    //Brukes til å holde orden på når spiller sist utførte et angrep. 
    //Lar oss unngå CQB/H2H bug med doble angrep samt speed cheating.
    //Oppgitt i millisekund, System.CurrentTimeMillis
    private long attackTimer = 0;

    //Brukes til å holde orden på når spilleren siste brukte en ER kit.
    //Lar oss oppdage speed cheating. Oppgitt i millisekund, System.CurrentTimeMillis
    private long erKitTimer = 0;

    public CharacterGame(int id, String navn, int gender, int faction) {
        super(id, navn, gender, faction);
    }

    /**
     * Forteller om character er i humanform eller i en ms/vehicle.
     *
     * @return True hvis i human form, false hvis ikke.
     */
    public boolean humanForm() {
        if (this.active_transport == null && this.active_vehicle == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Setter character til å være i humanform.
     */
    public void setHumanForm() {

        //Hvis i vehicle lagre WeaponsRoom.
        if (this.active_vehicle != null && this.active_vehicle_ic != null) {
            this.active_vehicle.saveWeaponsRoom();
        }

        //Vehicle til spilleren lagres i slot 0. Sett til å være tom.
        this.getClothing().equipItem(null, 0);

        this.active_transport = null;
        this.active_transport_ic = null;
        this.active_vehicle = null;
        this.active_vehicle_ic = null;
    }

    /**
     * Returnerer vehicle character befinner seg i.
     *
     * @return Vehicle objekt, eller null hvis character ikke er i en
     * MS/vehicle.
     */
    public Vehicle getVehicle() {
        return this.active_vehicle;
    }

    /**
     * Returnerer itemcontainer til characters vehicle.
     *
     * @return ItemContainer, eller null hvis character ikke er i en ms/vehicle.
     */
    public ItemContainer getVehicleContainer() {
        return this.active_vehicle_ic;
    }

    /**
     * Setter vehicle character bruker.
     *
     * @param v Vehicle.
     * @param ic Itemcontainer vehicle er i.
     */
    public void setVehicle(Vehicle v, ItemContainer ic) {
        this.active_vehicle = v;
        this.active_vehicle_ic = ic;
        //Hent fram utstyrte våpen/items.
        this.active_vehicle.restoreWeaponsRoom();
        //Oppdater kles informasjon.
        this.getClothing().equipItem(ic, 0); //Vehicle spilleren er i skal være første item i weared container.
    }

    /**
     * Returnerer taxi/transport character befinner seg i.
     *
     * @return TaxiTransport objekt, eller null hvis character ikke er i en
     * taxi/transport.
     */
    public TaxiTransport getTaxiTransport() {
        return this.active_transport;
    }

    /**
     * Returnerer itemcontainer til characters taxi/transport.
     *
     * @return ItemContainer, eller null hvis character ikke er i en
     * taxi/transport.
     */
    public ItemContainer getTaxiTransportContainer() {
        return this.active_transport_ic;
    }

    /**
     * Setter taxi/transport character bruker.
     *
     * @param t TaxiTransport.
     * @param ic Itemcontainer taxi/transport er i.
     */
    public void setTaxiTransport(TaxiTransport t, ItemContainer ic) {
        this.active_transport = t;
        this.active_transport_ic = ic;
        //Oppdater kles informasjon.
        this.getClothing().equipItem(ic, 0); //Vehicle spilleren er i skal være første item i weared container.
    }

    /**
     * Denne metoden kalles hver gang en spiller forandrer utseende.
     */
    public void appearanceChange() {
        this.appearance_change++;
    }

    /**
     * Returnerer oppdaterings telleren for bruk i opcode 0x8003.
     *
     * @return Verdi som skal settes inn for oppdaterings telleren i opcode
     * 0x8003.
     */
    public int getAppearanceChange() {
        return this.appearance_change;
    }

    /**
     * Setter posisjons objektet character skal bruke.
     *
     * @param p Posisjons objekt.
     */
    public void setPosisjon(Posisjon p) {
        this.pos = p;
    }

    /**
     * Returnerer posisjons objektet character bruker.
     *
     * @param p Posisjons objekt.
     */
    public Posisjon getPosisjon() {
        return this.pos;
    }

    public void setTeamID(int team_id) {
        this.team_id = team_id;
    }

    public int getTeamID() {
        return this.team_id;
    }

    public void setOccupancyTag(int tag) {
        this.occupancy_tag = tag;
    }

    public int getOccupancyTag() {
        return this.occupancy_tag;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return this.action;
    }

    public void setUCGM(int ucgm) {
        this.ucgm = ucgm;
    }

    public int getUCGM() {
        return this.ucgm;
    }

    public boolean isUCGM() {
        if (this.ucgm == 0x3 || this.ucgm == 0x4 || this.ucgm == 0x5) {
            return true;
        } else {
            return false;
        }
    }

    public void setCriminal(boolean criminal) {
        this.criminal = criminal;
    }

    public boolean isCriminal() {
        return this.criminal;
    }

    public void setLastTime0x3Received(int v) {
        this.lastTime0x3Received = v;
    }

    public int getLastTime0x3Received() {
        return this.lastTime0x3Received;
    }

    /**
     * Setter at opcode 0x3 har blitt mottatt. Brukes til å oppdage fusking.
     * IKKE FORVEKSLE DETTE MED OPCODE 0x3 METODENE FOR Å OPPDAGE OM SPILLER HAR
     * KOBLET FRA SERVER.
     *
     * @return true dersom det er grunn til å tro at spiller bruker speed cheat,
     * false hvis ikke.
     */
    public boolean setOpcode3Received() {
        //Finn ut hvor lang tid det er siden sist vi fikk opcode 0x3.
        long time = System.currentTimeMillis() - this.lastTimeOpcode0x3Received;
        if (time <= 500) {
            //Hvis mindre enn 0.5 sekunder registrer dette.
            this.opcode0x3counter++;
        } else {
            this.opcode0x3counter = 0;
        }

        this.lastTimeOpcode0x3Received = System.currentTimeMillis();

        //Dersom vi har registrert mer enn 10 tilfeller tar vi det som fusking.
        if (this.opcode0x3counter >= 10) {
            this.opcode0x3counter = 0;
            return true;
        } else {
            return false;
        }

    }

    public void setAttackID(int id) {
        this.attack_id = id;
    }

    public int getAttackID() {
        return this.attack_id;
    }

    public void setVisible(boolean v) {
        this.visible = v;
    }

    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Setter lastTimeAttacked for character til nåværende tidspunkt.
     */
    public void setLastTimeAttacked() {
        this.lastTimeAttacked = (int) (System.currentTimeMillis() / 1000);
    }

    public int getLastTimeAttacked() {
        return this.lastTimeAttacked;
    }

    /**
     * Kalles hver gang spiller er i et vehicle og beveger seg.
     */
    public void updateVehicleMovement() {
        this.movementUpdate++;

        //Øk MS/MA/Fighter skill med jevne mellomrom, forutsatt at MS er i bevegelse hele tiden.
        if ((this.movementUpdate % 150) == 0) {
            gameServer.SkillTraining.trainMsMaFighter(this);
        }
    }

    public void setBackpackContainer(HovedContainer c) {
        this.backpack = c;
    }

    public void setWearedContainer(HovedContainer c) {
        this.weared = c;
    }

    public void setBankContainer(HovedContainer c) {
        this.bank = c;
    }

    public void setMoneyContainer(HovedContainer c) {
        this.money = c;
    }

    public void setHangarContainer(HovedContainer c) {
        this.hangar = c;
    }

    public void setSelfstorageContainer(HovedContainer c) {
        this.selfstorage = c;
    }

    public void setProductiveContainer(HovedContainer c) {
        this.productive = c;
    }

    public void setSwappackContainer(HovedContainer c) {
        this.swappack = c;
    }

    public void setCreditContainer(HovedContainer c) {
        this.credit = c;
    }

    public HovedContainer getBackpackContainer() {
        return this.backpack;
    }

    public HovedContainer getWearedContainer() {
        return this.weared;
    }

    public HovedContainer getBankContainer() {
        return this.bank;
    }

    public HovedContainer getMoneyContainer() {
        return this.money;
    }

    public HovedContainer getHangarContainer() {
        return this.hangar;
    }

    public HovedContainer getSelfstorageContainer() {
        return this.selfstorage;
    }

    public HovedContainer getProductiveContainer() {
        return this.productive;
    }

    public HovedContainer getSwappackContainer() {
        return this.swappack;
    }

    public HovedContainer getCreditContainer() {
        return this.credit;
    }

    public int getOpcode28timer() {
        return opcode28timer;
    }

    public void setOpcode28timer(int opcode28timer) {
        this.opcode28timer = opcode28timer;
    }

    /**
     * Setter timer for når spiller sist utførte et angrep.
     */
    public void setAttackTimer() {
        this.attackTimer = System.currentTimeMillis();
    }

    /**
     *
     * @return Hvor lang tid, i millisekund, det har gått siden spiller utførte
     * sitt forrige angrep.
     */
    public long attackDeltaTime() {
        return (System.currentTimeMillis() - this.attackTimer);
    }

    /**
     * Setter timer for når spiller sist brukte ER kit.
     */
    public void setErKitTimer() {
        erKitTimer = System.currentTimeMillis();
    }

    /**
     *
     * @return Hvor lang tid, i millisekund, det har gått siden spiller sist
     * brukte et ER kit.
     */
    public long ErKitDeltaTime() {
        return (System.currentTimeMillis() - erKitTimer);
    }

}
