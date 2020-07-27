package npc;

import characters.*;
import containers.*;
import gameServer.CombatResult;
import items.Vehicle;
import items.Weapon;
import java.util.Iterator;
import java.util.Random;
import packetHandler.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere en NPC i MS/vehicle.
 */
public class NPCvehicle extends NPC {

    /**
     * Template NPC er basert på.
     */
    protected Template t;
    /**
     * Nåværende hitpoints til NPC.
     */
    private int hitpoints;
    /**
     * Angir sist gang NPC sine hitpoints nådde 0. Verdi tilsvarer
     * System.currentTimeMillis()
     */
    protected long killTime;
    /**
     * Hvilket våpen som er i bruk. 0=A, 1=B.
     */
    private int aktivSlot = 0;
    /**
     * Stats for shield NPC har utstyrt. { Item ID, Hitpoints, Block chance }
     *
     * Hvis Item ID = 0 er ikke noe shield utstyrt.
     */
    private int[] shield = new int[3];
    /**
     * ER kits NPC har utstyrt. { Antall ER kits, Repair % pr kit }
     */
    private int[] erKit = new int[2];
    /**
     * Målet til NPC.
     */
    protected NPCtarget target = null;
    /**
     * Hvor lenge NPC har hatt this.target som målet sitt.
     */
    protected int targetSwitchCounter = 0;
    /**
     * Hvor lenge siden sist NPC angrep målet sitt.
     */
    protected int targetAttackCounter = 0;
    /**
     * Angir om NPC har thrusters på eller av. Kun brukt i space zone eller for
     * flygende NPC og sendes i opcode 0x8003. Dette er for å hindre at NPC
     * animasjonen ser teit ut.
     */
    protected boolean thrustersOn = false;
    /**
     * Angir om NPC sin "kill time" er over grensen. Dvs om NPC med 100% skade
     * skal fjernes fra opcode 0x3 eller ikke. true=NPC skal fjernes fra opcode
     * 0x3, false=skal fremdeles være med i opcode 0x3, ELLER at NPC ikke har
     * 100% skade.
     *
     * Brukes for å hindre bug med zombie NPCs, der NPCs med 100% skade
     * fremdeles blir vist.
     */
    protected boolean killTimeExceeded = false;

    /**
     * Holder rede på hvor mange fiendtlige NPCs som angriper denne NPC.
     */
    protected int numberOfAttackers = 0;

    /**
     * @param id ID nummer til NPC
     * @param t Template NPC er basert på.
     */
    public NPCvehicle(int id, Template t) {
        super(id);
        this.t = t;
        this.loadTemplate();
    }

    /**
     *
     * @param id ID nummer til NPC
     * @param t Template NPC er basert på.
     * @param pos Hvor NPC skal plasseres.
     */
    public NPCvehicle(int id, Template t, Posisjon pos) {
        this(id, t);
        this.pos = pos;
        this.initPos = pos.lagKopi();
    }

    /**
     *
     * @param id ID nummer til NPC
     * @param t Template NPC er basert på.
     * @param pos Hvor NPC skal plasseres.
     * @param navn Navn på NPC.
     */
    public NPCvehicle(int id, Template t, Posisjon pos, String navn) {
        this(id, t, pos);
        this.navn = navn;
    }

    /**
     *
     * @param id ID nummer til NPC
     * @param t Template NPC er basert på.
     * @param pos Hvor NPC skal plasseres.
     * @param navn Navn på NPC.
     * @param teamID Team NPC er med i.
     */
    public NPCvehicle(int id, Template t, Posisjon pos, String navn, int teamID) {
        this(id, t, pos);
        this.navn = navn;
        this.teamID = teamID;
    }

    /**
     * Leser inn verdier fra template.
     *
     * Ikke alle verdier fra template blir lest inn, kun de som endrer seg f.eks
     * hitpoints.
     */
    protected void loadTemplate() {

        int[] v = t.getShield();
        this.shield[0] = v[0];
        this.shield[1] = v[1];
        this.shield[2] = v[2];

        v = t.getErKit();
        this.erKit[0] = v[0];
        this.erKit[1] = v[1];

        this.hitpoints = t.getHitpoints();

        this.rank = t.getRank();
    }

    /**
     *
     * @return Template NPC er basert på.
     */
    public Template getTemplate() {
        return this.t;
    }

    /**
     *
     * @return Machine damage til NPC, 0-100.
     */
    public byte getMachineDamage() {

        byte md = (byte) (100.0 - ((100.0 / this.t.getHitpoints()) * this.hitpoints));

        //Hvis damage er 100% skal hitpoints være 0, hvis ikke sett damage til 99%
        if (md == 100 && this.hitpoints != 0) {
            md = 99;
        }

        return md;
    }

    /**
     *
     * @return Shield damage til NPC, 0-100.
     */
    public byte getShieldDamage() {

        int maxHitpoint = this.t.getShield()[1];

        //Hvis damage er 100% skal hitpoints være 0, hvis ikke sett damage til 99%
        byte sd = (byte) (100.0 - ((100.0 / maxHitpoint) * this.shield[1]));

        if (sd == 100 && this.shield[1] != 0) {
            sd = 99;
        }

        return sd;
    }

    /**
     * Returnerer info om NPC sitt shield. Dersom NPC ikke har et shield utstyrt
     * er hitpoints 0.
     *
     * @return Shield info i et array {shield id, hitpoints,block chance}
     */
    public int[] getShield() {

        return this.shield;
    }

    /**
     * Returnerer info om våpen NPC har utstyrt.
     *
     * @return Våpen info i et array { Item ID, Attack power, Range, Rate of
     * fire }
     */
    public int[] getWeapon() {

        int[] w;

        if (this.aktivSlot == 0) {
            w = this.t.getWeaponA();
        } else {
            w = this.t.getWeaponB();
        }

        return w;
    }

    /**
     * Påfører skade på NPC.
     *
     * @param hitpoints Antall hitpoints i skade.
     */
    public void applyDamage(int hitpoints) {

        this.hitpoints -= hitpoints;

        if (this.hitpoints < 0) {
            this.hitpoints = 0;
            this.killTime = System.currentTimeMillis();
            numberOfAttackers = 0;
        }
    }

    /**
     * Påfører skade på NPC sitt shield.
     *
     * @param hitpoints Antall hitpoints i skade.
     */
    public void applyShieldDamage(int hitpoints) {

        this.shield[1] -= hitpoints;

        //Dersom vi har et shield utstyrt og det har blitt ødelagt, fjern det og oppdater appearance counter.
        if (this.shield[1] <= 0 && this.shield[0] != 0) {
            this.shield[0] = 0;
            this.shield[1] = 0;
            this.appearanceCounter++;
        }
    }

    /**
     *
     * @return Tidspunktet, unix timestamp, når NPC ble sist destroyed.
     */
    public int getKillTime() {
        return (int) (this.killTime / 1000);
    }

    /**
     * Respawner NPC på start posisjonen.
     */
    public void respawn() {

        this.pos = this.initPos.lagKopi();
        this.loadTemplate();
        this.appearanceCounter++;
        this.killTimeExceeded = false;

    }

    /**
     * Respawner NPC på oppgitt posisjon.
     *
     * @param pos Posisjon der NPC skal repawnes.
     */
    public void respawn(Posisjon pos) {

        this.pos = pos;
        this.loadTemplate();
        this.appearanceCounter++;
        this.killTimeExceeded = false;
    }

    /**
     * Sjekker om NPC skal slippe items og oppretter ny wreckage container hvis
     * den skal. Wreckage container vil bli plassert i spillet og sendt til alle
     * spillere i nærheten.
     *
     * @param owner Character som skal eie wreckage container.
     */
    public void dropWreckageContainer(CharacterGame owner) {

        Random r = new Random();

        int n = r.nextInt(100);

        //Sjekk om vi skal slippe items.
        if (n >= this.t.getDropChance()) {
            return;
        }

        /*
         * OK, NPC skal slippe items.
         *
         * Opprett et nytt ødelagt vehicle med samme item ID som NPC. Plasser items i vehicle sitt inventory.
         */
        Vehicle v = new Vehicle(this.t.getVehicleID());
        v.applyDamage(v.getHitpoints());

        //Gå gjennom alle items NPC kan slippe og bestem hvilke items den slipper og plasser dem i inventory.
        Iterator<DropItem> items = this.t.getDropItems();

        while (items.hasNext()) {

            DropItem i = items.next();

            ItemContainer ic = null;

            if (i.isDropped()) {
                ic = i.getItem();
            }

            if (ic != null) {
                v.getInventory().addItemContainer(ic);
            }
        }

        //Da har vi gått gjennom alle items NPC kan slippe, sjekk om den faktisk skal slippe items.
        if (v.getInventory().getContents().size() > 0) {
            //NPC skal slippe items.
            ItemContainer vIc = ContainerList.newItemContainer();
            vIc.addItem(v);
            vIc.settContainerTail(0x14);

            int ownerExpire = (int) (System.currentTimeMillis() / 1000) + config.Npc.itemDropOwnerExpire;
            gameServer.ItemHandler.registerItem(vIc, this.pos.lagKopi(), owner, ownerExpire);

            //Send opcode 0x8035 til alle spillere i nærheten for å informere om ny wreckage container.
            this.sendWreckage8035(owner, vIc, ownerExpire);

        } else {
            //NPC skal ikke slippe items, slett opprettet vehicle.
            ContainerList.removeContainer(v.getInventory().getID());
            ContainerList.removeContainer(v.getWeaponsRoom().getID());
        }

    }

    /**
     * Tar imot en liste over mulige mål NPC kan angripe og prøver å velge et av
     * dem.
     *
     * @param targets Fiendtlige spillere og NPCs innenfor området.
     * @param area Område NPC tilhører. Eller NULL dersom styrt av
     * GlobalManager.
     */
    public void chooseTarget(Iterator<NPCtarget> targets, Area area) {

        //Sjekk først om vi kan velge nytt mål nå eller må vente lengre.
        if (this.targetSwitchCounter < config.Npc.targetSwitchTime && this.target != null) {
            return;
        }

        //Angir maksimal avstand til et mål. Velger kun mål nærmere enn dette.
        long maxAvstand;

        //Sjekk hvilken type NPC dette er og sett maxAvstand avhengig av det.
        if (this.t.getBehavior() == Template.npcType.Guard || this.t.getBehavior() == Template.npcType.MovingGuard) {
            maxAvstand = config.Npc.guardTargetRange;
        } else if (this.t.getBehavior() == Template.npcType.Grunt) {
            maxAvstand = config.Npc.gruntTargetRange;
        } else { //OPPDATER HER FOR BOSS NPC.

            //Supply NPCs skal ikke angripe så vi bare returnerer.
            return;
        }

        //t = Målet NPC skal angripe.
        NPCtarget t = this.target;

        //Avstand = Avstand til målet.
        Long avstand = Long.MAX_VALUE;

        //Beregn avstand til nåværende mål, hvis vi har et.
        if (t != null) {
            avstand = t.getPosisjon().distance(this.getPosisjon());
        }

        //Gå gjennom alle mulige mål vi har fått og velg det som er nærmest og innenfor tillatt avstand.
        //Sjekk også at målet har annen faction eller er criminal marked.
        //NB! For andre NPC mål benytter vi ingen max avstand.
        while (targets.hasNext()) {

            NPCtarget c = targets.next();

            long d = c.getPosisjon().distance(this.getPosisjon());

            //Sjekk at målet tilhører en annen faction eller er criminal samt er gyldig.
            if ((c.getFaction() != this.faction || c.isCriminal()) && c.isValidTarget()) {
                //Sjekk om dette målet er nærmere enn forrige målet.
                if (d < avstand && (d < maxAvstand || c.getNpcTarget() != null)) {
                    //I tilfelle NPC vs NPC.
                    //Sjekk at det ikke allerede er mange andre NPCs som angriper dette målet.
                    //Men dersom målet er nært velg det likevel.
                    if (c.getNumberOfAttackers() < 5 || d < (avstand / 2)) {
                        //Dersom målet befinner seg utenfor området NPC kan bevege seg, sjekk at målet er innenfor rekkevidde av våpenet vårt.
                        if (!area.isWithinInnerArea(c.getPosisjon().getX(), c.getPosisjon().getY(), c.getPosisjon().getZ())) {
                            if (this.t.getWeaponA() != null && d < this.t.getWeaponA()[2] * 4) {
                                //Målet er utenfor området NPC kan bevege seg i, men er innenfor rekkevidde av våpenet vårt.
                                t = c;
                                avstand = d;
                            }
                        } else {
                            //Målet er innenfor området og bedre enn det forrige, velg det.
                            t = c;
                            avstand = d;
                        }

                    }

                }
            }

        }

        //t = det nye målet, eller det gamle hvis ikke noe bedre ble funnet.
        if (this.target != t) {
            resetAIcounters();
            if (this.target != null) {
                //Fortell eventuell gammel NPC at vi har sluttet å angripe den.
                this.target.removeAttacker();
            }
            this.target = t;
            this.target.addAttacker();
            setPositionOffset();
        }

    }

    /**
     * Utfører AI for NPC.
     *
     * @area Område NPC tilhører.
     *
     */
    public void performAI(AreaManager area) {

        //Oppdater teller som angir når vi kan velge nytt mål.
        this.targetSwitchCounter++;

        //Oppdater teller som angir når vi kan angripe igjen.
        this.targetAttackCounter++;

        //Slår av thrusters her, slå dem på igjen hvis vi er i space og beveger oss.
        this.thrustersOn = false;

        //Sjekk om vi skal regenerere helse.
        if (this.t.getHealing() > 0 && this.hitpoints < t.getHitpoints()) {
            this.hitpoints += this.t.getHealing();
            if (this.hitpoints > this.t.getHitpoints()) {
                this.hitpoints = this.t.getHitpoints();
            }
        }

        //Sjekk om vi har et mål som skal angripes.
        if (this.target != null) {
            //Sjekk at målet fremdeles er gyldig.
            if (this.target.isValidTarget() && this.target.getMachineDamage() < 100) {
                //OK, målet er fremdeles tilkoblet server og er i et vehicle.

                //Sjekk at målet er innenfor gyldig avstand, dvs sjekk om målet har stukket av.
                long maxAvstand = 0; //Max gyldig avstand til målet.

                if (this.t.getBehavior() == Template.npcType.Grunt) {
                    maxAvstand = config.Npc.gruntTargetRange;
                } else if (this.t.getBehavior() == Template.npcType.MovingGuard
                        || this.t.getBehavior() == Template.npcType.Guard) {
                    maxAvstand = config.Npc.guardTargetRange;
                }

                long avstand = this.pos.distance(this.target.getPosisjon());

                //For NPC vs NPC dobler vi maxAvstand i forhold til PVE.
                if ((avstand > maxAvstand && target.getNpcTarget() == null) || (avstand > maxAvstand * 2 && target.getNpcTarget() != null)) {
                    //Avstand til målet er større enn tillatt. Målet har kommet seg unna.
                    this.target = null;
                    this.resetAIcounters();
                    return;
                }

                //Snu NPC mot målet dersom NPC kan bevege seg.
                if (!this.t.isStationary()) {
                    this.turnTowardsTarget();
                }

                //Flytt NPC mot målet dersom NPC kan bevege seg.
                if (this.t.getBehavior() == Template.npcType.Grunt || this.t.getBehavior() == Template.npcType.MovingGuard
                        || this.t.getBehavior() == Template.npcType.Boss) {

                    this.moveTowardsTarget(area);
                }

                //Angrip målet dersom NPC har våpen utstyrt.
                if (this.getWeapon()[0] != 0) {
                    this.attackTarget();
                }

                //Sjekk om målet befinner seg utenfor området NPC kan bevege seg i.
                if (target != null && area != null && !area.isWithinInnerArea(target.getPosisjon().getX(), target.getPosisjon().getY(), target.getPosisjon().getZ())) {
                    //NPC kan ikke lengre følge etter målet. Sjekk om målet er innenfor rekkevidde av våpenet.
                    long d = getPosisjon().distance(target.getPosisjon());
                    if (t.getWeaponA() != null && d > t.getWeaponA()[2] * 4) {
                        //Utenfor rekkevidde av våpnet vårt.
                        target = null;
                    }
                }

            } else {
                //Målet finnes ikke mer, fjern det.
                this.target = null;
            }

        } else {
            //NPC har ikke noe mål, flytt tilbake til start posisjon dersom nødvendig og NPC kan bevege seg.
            this.target = null; //Gjør dette for å fjerne eventuelt gammelt NPCtarget objekt.

            if (!this.t.isStationary() && (this.t.getBehavior() == Template.npcType.Grunt
                    || this.t.getBehavior() == Template.npcType.MovingGuard)) {

                this.moveToInitialPosition(area);
            }

        }

    }

    /**
     * Nullstiller alle tellere NPC bruker til å styre AI med.
     *
     */
    protected void resetAIcounters() {
        this.targetAttackCounter = 0;
        this.targetSwitchCounter = 0;
    }

    /**
     * Beregner et eventuelt offset for målet.
     */
    private void setPositionOffset() {

        //Dersom vi ikke kan strafe flytter vi rett mot målet.
        if (!t.getCanStrafe()) {
            target.setOffsetX(0);
            target.setOffsetY(0);
            target.setOffsetZ(0);
            return;
        }

        //Flytt til en tilfeldig posisjon rundt målet.
        int l = t.getWeaponA()[2] / 2; //l = halvparten av NPC sin range.

        Random r = new Random();
        target.setOffsetX(l - r.nextInt(l * 2));
        target.setOffsetY(l - r.nextInt(l * 2));
        target.setOffsetZ(l - r.nextInt(l * 2));
    }

    /**
     * Snur NPC mot målet sitt.
     */
    private void turnTowardsTarget() {

        //Snu venstre/høyre.
        int retning = this.pos.beregnRetning(target.getPosisjon());

        this.pos.setDirection(retning);

        //Dersom vi er i space skal NPC også snu seg opp/ned.
        if (this.pos.getZone() == 2) {
            retning = this.pos.beregnRetningOppNed(target.getPosisjon());

            this.pos.setTilt(retning);
        }

    }

    /**
     * Flytter NPC mot målet.
     *
     * @area Området NPC tilhører.
     */
    private void moveTowardsTarget(AreaManager area) {


        /*
         * Dersom avstanden til målet er 80% eller mer av aktivt våpens rekkevidde skal NPC
         * løpe mot målet.
         *
         * Hvis målet er nærmere enn 80% av aktivt våpens rekkevidde skal NPC gå mot målet.
         *
         * Hvis målet er nærmere enn 50% av aktivt våpens rekkevidde skal NPC stå i ro.
         *
         */
        //Rekkevidde til våpen.
        int rekkevidde = this.getWeapon()[2] * 4; //Gang med 4 fordi avstand regnes i gameunits mens våpen rekkevidde er i meter.

        //I tilfelle NPC ikke har våpen.
        if (rekkevidde <= 0) {
            return;
        }

        //Avstand til målet.
        int avstand = (int) this.pos.distance(this.target.getPosisjon());

        //Antall prosent avstanden til målet er av våpenets rekkevidde.
        int m = (avstand * 100) / rekkevidde;

        if (m < 50 && pos.getZone() != 2) { //Fjern dettte senere. For nå vil vi kun gå rundt spiller i space zone.
            return; //Nærmere enn 50%, NPC skal stå i ro.
        }
        //Hastighet NPC skal nærmere seg målet med.
        int hastighet;

        if (m < 80) {
            hastighet = this.t.getWalkSpeed();
        } else {
            hastighet = this.t.getRunSpeed();
        }

        if (this.pos.getZone() == 1) {
            //Ground zone.
            //flyttXY(area, this.pos.getDirection(), hastighet, true);
            flyttXY(area, target.getPosisjon(), hastighet, target.getOffsetX(), target.getOffsetY());
        } else {
            //Space zone, slå på thrusters.
            flyttXYZ(area, target.getPosisjon(), hastighet, target.getOffsetX(), target.getOffsetY(), target.getOffsetZ());
            this.thrustersOn = true;
        }

    }

    /**
     * Flytter NPC tilbake til start posisjonen der den først ble spawnet.
     *
     */
    private void moveToInitialPosition(AreaManager area) {

        long avstand = this.pos.distance(this.initPos);

        //Sjekk om vi allerede er på start posisjonen.
        if (avstand < this.t.getRunSpeed()) {
            return;
        }

        //Snu NPC mot start posisjonen.
        int retning = this.pos.beregnRetning(this.initPos);

        this.pos.setDirection(retning);

        //Dersom vi er i space skal NPC også snu seg opp/ned.
        if (this.pos.getZone() == 2) {

            retning = this.pos.beregnRetningOppNed(this.initPos);

            this.pos.setTilt(retning);
        }

        //Flytt NPC mot start posisjonen.
        if (this.pos.getZone() == 1) {

            this.flyttXY(area, this.pos.getDirection(), this.t.getRunSpeed(), false);
        } else {
            //this.flyttXYZ(area, this.pos.getDirection(), this.pos.getTilt(), this.t.getRunSpeed(), false);
            flyttXYZ(area, initPos, t.getRunSpeed(), 0, 0, 0);
        }

    }

    /**
     * Angriper målet dersom det er innenfor rekkevidde av valgt våpen og NPC
     * kan angripe.
     *
     */
    protected void attackTarget() {

        //Hent ut info om våpenet NPC bruker.
        int[] w = this.getWeapon();

        //Sjekk om NPC kan bruke våpenet nå eller må vente lengre.
        if (this.targetAttackCounter < w[3]) {
            return;
        }

        //Sjekk at målet er innenfor rekkevidde.
        int avstand = (int) (this.pos.distance(this.target.getPosisjon()) / 4); //Del på 4 for å gjøre om til meter.

        if (avstand > w[2]) {
            return;
        }

        //Sjekk at det er fri sikt mellom NPC og målet.
        Posisjon p1 = this.pos;
        Posisjon p2 = this.target.getPosisjon();

        //Dersom ground zone, sjekk at NPC ikke skyter gjennom terrenget.
        if (this.pos.getZone() == 1 && !t.isIgnoreHeightmap() && !gameServer.Heightmap.friSikt(p1.getX(), p1.getY(), p1.getZ(), p2.getX(), p2.getY(), p2.getZ())) {
            return;
        }

        //OK, da kan NPC angripe målet sitt.
        CombatResult res = null;
        if (this.target.getHumanTarget() != null) {
            res = gameServer.Combat.attack(this, this.target.getHumanTarget());
        } else if (this.target.getNpcTarget() != null) {
            //Angriper fiendtlig NPC.
            res = gameServer.Combat.attack(this, this.target.getNpcTarget());
        }

        if (res == null) {
            return;
        }

        this.updateTargetStatus(res);

        this.sendOpcode800F(res);
        this.sendOpcode8036(res);

        //DERSOM MÅLET ER EN SPILLER!
        if (this.target.getHumanTarget() != null) {
            //Sjekk om målet sin MS/vehicle ble ødelagt eller om shield ble ødelagt.
            if (target.getMachineDamage() >= 100) {
                //MS/vehicle ødelagt.
                boolean r = opcodes.gameServer.Opcode0x0F.destroyMS(this.target.getHumanTarget());

                if (r) {
                    //Oppdater losses og rank.
                    this.target.getHumanTarget().setLosses(this.target.getHumanTarget().getLosses() + 1);
                    this.target.getHumanTarget().loseRank(400);
                }

                this.target = null; //Mål ødelagt.
            } else if (this.target.getHumanTarget().getVehicle().getEquippedItem(1) != null) {

                Weapon shield = (Weapon) this.target.getHumanTarget().getVehicle().getEquippedItem(1).getItem();

                if (shield.getHitpoints() <= 0) {
                    this.target.getHumanTarget().getVehicle().destroyShield();
                    this.target.getHumanTarget().appearanceChange();
                }

            }
        } //SLUTT PÅ KODE FOR SPILLER

        //Nullstill slik at NPC må vente litt før den kan angripe igjen.
        this.targetAttackCounter = 0;
    }

    /**
     * Flytter NPC i oppgitt retning.
     *
     * For at NPC skal kunne flyttes må den nye posisjonen være innenfor området
     * og være ledig.
     *
     * @param area Område NPC er i.
     * @param retning Retning NPC skal flyttes i, oppgitt i gameunits.
     * @param lengde Hvor langt NPC skal flytte seg, oppgitt i gameunits.
     * @param sjekkOmLedig Angir om NPC skal sjekke at neste posisjon er ledig
     * før den flytter der til.
     *
     * @return true hvis OK, false dersom NPC ikke kunne flytte seg i den
     * retningen.
     */
    private boolean flyttXY(AreaManager area, int retning, int lengde, boolean sjekkOmLedig) {

        /*
         * Den nye X,Y posisjonen til NPC beregnes som følger:
         *
         * lengde = hypotenusen, retning = vinkel til hypotenusen.
         *
         * Hvor mye NPC skal flytte seg i X,Y retning beregnes ved å finne katetene.
         */
        //Retning kan være fra -32768 til +32767, gjør den om til 0 til 16384.
        int r = Math.abs(retning) % 16384;

        //Endre r avhengig av hvilken kvadrant retning ligger i.
        //Ellers vil ikke vinkel være rett i forhold til hvordan det er i spillet.
        if (retning >= 0 && retning < 16384) {
            r = 16384 - r; //Andre kvadrant.
        } else if (retning < 0 && retning > -16384) {
            r = 16384 - r; //Første kvadrant.
        }

        //16384 gameunits = en kvadrant, halv PI. PI/32768 = halv PI delt på en kvadrant.
        double vinkel = (Math.PI / 32768) * r; //vinkel=retning gjort om til radian.

        //Hosliggende katet.
        int dx = (int) (lengde * Math.cos(vinkel));

        //Motstående katet.
        int dy = (int) (lengde * Math.sin(vinkel));

        //dx,dy=hvor mye NPC skal flytte seg. Bestem fortegn avhengig av retning.
        if (retning > 0) {
            dx *= -1;
        }

        if (retning >= 16384 || retning < -16384) {
            dy *= -1;
        }

        //Sjekk at den nye posisjonen ikke er utenfor området og at den er ledig.
        if (this.pos.getZone() == 1) {
            if (!area.isWithinInnerArea(this.pos.getX() + dx, this.pos.getY() + dy)) {
                return false;
            }

            int z = gameServer.Heightmap.getZ(this.pos.getX() + dx, this.pos.getY() + dy);

            if (sjekkOmLedig == true && !area.posisjonLedig(this.pos.getX() + dx, this.pos.getY() + dx, z, this)) {
                return false;
            }
        } else {
            if (!area.isWithinInnerArea(this.pos.getX() + dx, this.pos.getY() + dy, this.pos.getZ())) {
                return false;
            }

            if (sjekkOmLedig == true && !area.posisjonLedig(this.pos.getX() + dx, this.pos.getY() + dx, this.pos.getZ(), this)) {
                return false;
            }
        }

        this.pos.setX(this.pos.getX() + dx);
        this.pos.setY(this.pos.getY() + dy);

        if (this.pos.getZone() == 1 && !t.isIgnoreHeightmap()) {
            this.pos.setZ(gameServer.Heightmap.getZ(this.pos.getX(), this.pos.getY()) - 70);
        }

        return true;
    }

    /**
     * Flytter NPC mot oppgitt posisjon langs X,Y aksene. Oppgitt offset kan
     * brukes til å flytte mot en posisjon offset fra oppgitt posisjon
     * istedenfor å flytte direkte mot den.
     *
     * @param area Området NPC befinner seg i.
     * @param p Posisjon vi skal flytte mot.
     * @param length Hvor langt vi skal flytte.
     * @param offsetX Eventuelt offset langs X aksen som skal plusses på oppgitt
     * posisjon.
     * @param offsetY Samme som offset X.
     *
     * @return TRUE dersom NPC ble flyttet. FALSE dersom NPC ikke flyttet.
     */
    private boolean flyttXY(AreaManager area, Posisjon p, int length, int offsetX, int offsetY) {

        //Beregn avstand fra NPC til oppgitt posisjon.
        int dx = Math.abs(p.getX() - pos.getX() + offsetX);
        int dy = Math.abs(p.getY() - pos.getY() + offsetY);

        double h = Math.sqrt(dx * dx + dy * dy);

        //Beregn hvor mye length utgjør av total avstand.
        double dl = (double) length / h;

        //Sjekk at vi ikke flytter forbi posisjonen.
        if (dl > 1) {
            dl = 1;
        }

        //Beregn hvor mye vi skal flytte langs hver akse.
        double moveX = (double) dx * dl;
        double moveY = (double) dy * dl;

        //Beregn vår nye X,Y,Z posisjon
        int x, y;

        if ((p.getX() + offsetX) > pos.getX()) {
            x = pos.getX() + (int) moveX;
        } else {
            x = pos.getX() - (int) moveX;
        }

        if ((p.getY() + offsetY) > pos.getY()) {
            y = pos.getY() + (int) moveY;
        } else {
            y = pos.getY() - (int) moveY;
        }

        //Sjekk at denne posisjonen er innenfor området, ikke i en safe zone og ledig.
        if (!area.isWithinInnerArea(x, y) || area.isWithinSafeZone(x, y)) {
            return false;
        }

        int z = gameServer.Heightmap.getZ(this.pos.getX() + dx, this.pos.getY() + dy);

        if (!area.posisjonLedig(x, y, z, this)) {
            return false;
        }

        //OK posisjonen er ledig og vi kan flytte til den.
        pos.setX(x);
        pos.setY(y);
        pos.setZ(z);

        return true;
    }

    /**
     * Flytter NPC i oppgitt retning.
     *
     * For at NPC skal kunne flyttes må den nye posisjonen være innenfor området
     * og være ledig.
     *
     * @param area Område NPC er i.
     * @param retning Retning NPC skal flyttes i, oppgitt i gameunits.
     * @param oppNed NPCs vinkel opp/ned, oppgitt i gameunits -16384 til 16383.
     * @param lengde Hvor langt NPC skal flytte seg, oppgitt i gameunits.
     * @param sjekkOmLedig Angir om NPC skal sjekke at neste posisjon er ledig
     * før den flytter der til.
     *
     * @return true hvis OK, false dersom NPC ikke kunne flytte seg i den
     * retningen.
     */
    private boolean flyttXYZ(AreaManager area, int retning, int oppNed, int lengde, boolean sjekkOmLedig) {

        /*
         * For å flytte langs XY aksen benyttes metoden flyttXY. Denne metoden selv flytter kun langs Z aksen.
         *
         * lengde = hypotenusen
         * XY aksen = hosliggende katet.
         * Z aksen = motstående katet.
         */
        //Beregn opp/ned vinkelen til NPC i radianer.
        double vinkel = Math.abs(oppNed);

        vinkel = (Math.PI / 32768) * vinkel; //32768 = halv pi delt på 16384.

        //Hvor mye NPC skal flytte seg langs X,Y aksene.
        int lengdeXY = (int) (Math.cos(vinkel) * lengde);

        //Hvor mye NPC skal flytte seg langs Z aksen.
        int lengdeZ = (int) (Math.sin(vinkel) * lengde);

        //Snu fortegn avhengig av om vi skal opp eller ned.
        if (oppNed < 0) {
            lengdeZ *= -1;
        }

        //Flytt langs XY aksen.
        if (this.flyttXY(area, retning, lengdeXY, sjekkOmLedig)) {
            //Hvis NPC kunne flytte langs XY aksen, flytt også langs Z aksen.
            //Ingen grunn til å sjekke at ny posisjon er innenfor området, det gjør flyttXY metoden og sjekk
            //kun for Z aksen er ikke viktig.
            this.pos.setZ(this.pos.getZ() + lengdeZ);

        } else {
            return false;
        }

        return true;
    }

    /**
     * Flytter NPC mot oppgitt posisjon langs X,Y,Z aksene. Oppgitt offset kan
     * brukes til å flytte mot en posisjon offset fra oppgitt posisjon
     * istedenfor å flytte direkte mot den.
     *
     * @param area Området NPC befinner seg i.
     * @param p Posisjon vi skal flytte mot.
     * @param length Hvor langt vi skal flytte.
     * @param offsetX Eventuelt offset langs X aksen som skal plusses på oppgitt
     * posisjon.
     * @param offsetY Samme som offset X.
     * @param offsetZ Samme som offset Y.
     *
     * @return TRUE dersom NPC ble flyttet. FALSE dersom NPC ikke flyttet.
     */
    private boolean flyttXYZ(AreaManager area, Posisjon p, int length, int offsetX, int offsetY, int offsetZ) {

        //Beregn avstand fra NPC til oppgitt posisjon.
        int dx = Math.abs(p.getX() - pos.getX() + offsetX);
        int dy = Math.abs(p.getY() - pos.getY() + offsetY);
        int dz = Math.abs(p.getZ() - pos.getZ() + offsetZ);

        double h = Math.sqrt(dx * dx + dy * dy + dz * dz);

        //Beregn hvor mye length utgjør av total avstand.
        double dl = (double) length / h;

        //Sjekk at vi ikke flytter forbi posisjonen.
        if (dl > 1) {
            dl = 1;
        }

        //Beregn hvor mye vi skal flytte langs hver akse.
        double moveX = (double) dx * dl;
        double moveY = (double) dy * dl;
        double moveZ = (double) dz * dl;

        //Beregn vår nye X,Y,Z posisjon
        int x, y, z;

        if ((p.getX() + offsetX) > pos.getX()) {
            x = pos.getX() + (int) moveX;
        } else {
            x = pos.getX() - (int) moveX;
        }

        if ((p.getY() + offsetY) > pos.getY()) {
            y = pos.getY() + (int) moveY;
        } else {
            y = pos.getY() - (int) moveY;
        }

        if ((p.getZ() + offsetZ) > pos.getZ()) {
            z = pos.getZ() + (int) moveZ;
        } else {
            z = pos.getZ() - (int) moveZ;
        }

        //Sjekk at denne posisjonen er innenfor området og ledig.
        if (!area.isWithinInnerArea(x, y, z)) {
            return false;
        }

        if (!area.posisjonLedig(x, y, z, this)) {
            return false;
        }

        //OK posisjonen er ledig og vi kan flytte til den.
        pos.setX(x);
        pos.setY(y);
        pos.setZ(z);

        return true;
    }

    /**
     * Oppdater status for NPC sitt mål.
     *
     * @param res Resultat av angrep på målet.
     */
    private void updateTargetStatus(CombatResult res) {

        if (!res.isHit() || res.isDodged()) {
            return; //Bommet, ingenting å gjøre.
        }
        if (res.isShieldBlock()) {
            //Traff målet sitt shield.
            target.applyShieldDamage(res.getDamage());

            //Ikke fjern shield her dersom det ble ødelagt. Det må gjøres ETTER at opcode 0x800F og 0x8036 er sendt.
        } else {
            //Traff målet.
            //Selv om MS/vehicle ble ødelagt ikke oppdater det ennå. Det MÅ gjøres etter at opcode 0x800F og 0x8036 er sendt.
            this.target.applyDamage(res.getDamage());
        }

    }

    /**
     * Sender opcode 0x800F til spiller som blir angrepet av NPC.
     *
     * Det antaes at status for spiller som ble angrepet har blitt oppdatert.
     *
     * @param res Resultatet fra NPC sitt angrep på spiller.
     *
     */
    private void sendOpcode800F(CombatResult res) {

        //Opcode 0x800F skal kun sendes når NPC angriper en spiller.
        //Dersom vi angriper en NPC skal pakken ikke sendes.
        if (this.target.getHumanTarget() == null) {
            return;
        }
        CharacterGame trg = this.target.getHumanTarget();

        PacketData svar800F = new PacketData();

        svar800F.writeIntBE(this.getID());
        svar800F.writeIntBE(target.getID());
        svar800F.writeIntBE(res.getDamage());
        svar800F.writeByte((byte) 0x1);
        svar800F.writeByte((byte) 0x0);

        //Skriv første resultat byte.
        if (res.isDodged()) {
            svar800F.writeByte((byte) 0x6); //Dodge
        } else if (res.isShieldBlock()) {
            //Spiller blokkerte med shield, sjekk om shield ble ødelagt eller ikke.
            Weapon shield = (Weapon) trg.getVehicle().getEquippedItem(1).getItem();

            if (shield.getHitpoints() <= 0) {
                svar800F.writeByte((byte) 0x3);
            } else {
                svar800F.writeByte((byte) 0x4);
            }
        } else if (res.isHit()) {
            svar800F.writeByte((byte) 0x0);
        } else {
            svar800F.writeByte((byte) 0x4); //Bommet.
        }
        svar800F.writeByte((byte) 0x0);
        svar800F.writeShortBE((short) -1);

        //Skriv andre resultat byte.
        if (!res.isHit() || res.isDodged()) {
            svar800F.writeByte((byte) 0x4); //Missed.
        } else if (res.isShieldBlock()) {
            //Spiller blokkerte med shield, sjekk om shield ble ødelagt eller ikke.
            Weapon shield = (Weapon) trg.getVehicle().getEquippedItem(1).getItem();

            if (shield.getHitpoints() <= 0) {
                svar800F.writeByte((byte) 0x3);
            } else {
                svar800F.writeByte((byte) 0x0);
            }
        } else if (trg.getVehicle().getMachineDamage() >= 100) {
            svar800F.writeByte((byte) 0x2); //MS/vehicle 100% skade.
        } else {
            svar800F.writeByte((byte) 0x0); //Hit.
        }

        svar800F.writeByte((byte) -1);
        svar800F.writeIntBE(0x0);
        svar800F.writeLongBE(0x0);
        svar800F.writeByte((byte) 0);
        svar800F.writeByte((byte) 0);
        svar800F.writeLongBE(0x0);

        svar800F.writeIntBE(this.getWeapon()[0]);

        //Skriv ut container for målet sin MS/vehicle, eller shield dersom shieldblock.
        if (res.isShieldBlock()) {
            svar800F.writeIntBE(trg.getVehicle().getEquippedItem(1).getID());
            svar800F.writeIntBE(trg.getVehicle().getEquippedItem(1).getContainerTail());
            svar800F.writeIntBE(trg.getVehicle().getEquippedItem(1).getItem().getID());
        } else if (trg.getVehicle() != null) {
            svar800F.writeIntBE(trg.getVehicleContainer().getID());
            svar800F.writeIntBE(trg.getVehicleContainer().getContainerTail());
            svar800F.writeIntBE(trg.getVehicle().getID());
        } else {
            //Målet er ødelagt, skriv ut null info for container.
            svar800F.writeLongBE(0x0);
            svar800F.writeIntBE(-1);
        }

        if (trg.getVehicle() != null) {
            svar800F.writeByte(trg.getVehicle().getMachineDamage());
        } else {
            svar800F.writeByte((byte) 100); //Målet er ikke i vehicle lengre, altså har det fått 100% skade.
        }

        Packet pakke800F = new Packet(0x800F, svar800F.getData());

        gameServer.MultiClient.sendPacketToPlayer(pakke800F, trg.getCharacterID());
    }

    /**
     * Sender opcode 0x8036 til alle spillere i nærheten av NPC.
     *
     * Det antaes at status for spiller som ble angrepet har blitt oppdatert.
     *
     * @param res Resultatet fra NPC sitt angrep på en spiller.
     *
     */
    private void sendOpcode8036(CombatResult res) {

        PacketData svar8036 = new PacketData();

        svar8036.writeIntBE(this.getID());
        svar8036.writeIntBE(this.target.getID());
        svar8036.writeIntBE(this.getWeapon()[0]);

        //Skriv første resultat byte.
        if (res.isDodged()) {
            svar8036.writeByte((byte) 0x6); //Dodge
        } else if (res.isShieldBlock()) {
            //Spiller blokkerte med shield, sjekk om shield ble ødelagt eller ikke.
            int shieldHitpoints = this.target.getShieldHitpoints();

            if (shieldHitpoints <= 0) {
                svar8036.writeByte((byte) 0x3);
            } else {
                svar8036.writeByte((byte) 0x4);
            }
        } else if (res.isHit()) {
            svar8036.writeByte((byte) 0x0);
        } else {
            svar8036.writeByte((byte) 0x4); //Bommet.
        }
        svar8036.writeByte((byte) 0x0);
        svar8036.writeShortBE((short) -1);

        //Skriv andre resultat byte.
        if (!res.isHit() || res.isDodged()) {
            svar8036.writeByte((byte) 0x4); //Missed.
        } else if (res.isShieldBlock()) {
            //Spiller blokkerte med shield, sjekk om shield ble ødelagt eller ikke.
            int shieldHitpoints = this.target.getShieldHitpoints();

            if (shieldHitpoints <= 0) {
                svar8036.writeByte((byte) 0x3);
            } else {
                svar8036.writeByte((byte) 0x0);
            }
        } else if (this.target.getMachineDamage() >= 100) {
            svar8036.writeByte((byte) 0x2); //MS/vehicle 100% skade.
        } else {
            svar8036.writeByte((byte) 0x0); //Hit.
        }
        svar8036.writeIntBE(0x0);

        svar8036.writeByte(this.target.getMachineDamage());

        Packet pakke8036 = new Packet(0x8036, svar8036.getData());

        gameServer.MultiClient.broadcastPacket(pakke8036, this.target.getPosisjon());
    }

    /**
     * Sender opcode 0x8035 til spillere i nærheten for å informere om et nytt
     * wreckage.
     *
     * @param target Spiller som mistet eier wreckage container.
     * @param wreck Item Container der wreckage er.
     * @param ownerExpire Når eierskap for wreckage går ut.
     */
    private void sendWreckage8035(CharacterGame target, ItemContainer wreck, int ownerExpire) {

        PacketData svar8035 = new PacketData();

        svar8035.writeIntBE(0x1);

        svar8035.writeIntBE(wreck.getID());
        svar8035.writeIntBE(wreck.getContainerTail());
        svar8035.writeIntBE(wreck.getItem().getID());

        svar8035.writeIntBE(this.pos.getX());
        svar8035.writeIntBE(this.pos.getY());
        svar8035.writeIntBE(this.pos.getZ());
        svar8035.writeShortBE((short) this.pos.getTilt());
        svar8035.writeShortBE((short) this.pos.getRoll());
        svar8035.writeShortBE((short) this.pos.getDirection());

        svar8035.writeLongBE(wreck.getAntall());

        svar8035.writeIntBE(target.getCharacterID());

        svar8035.writeByte((byte) 0x80);

        svar8035.writeIntBE((int) (System.currentTimeMillis() / 1000));

        svar8035.writeIntBE(0);
        svar8035.writeIntBE(1);

        svar8035.writeIntBE(ownerExpire);

        svar8035.writeShortBE((short) 0xB);

        svar8035.writeIntBE(target.getCharacterID());

        svar8035.writeShortBE((short) -1);
        svar8035.writeByte((byte) 2);

        Packet svar8035Pakke = new Packet(0x8035, svar8035.getData());

        gameServer.MultiClient.broadcastPacket(svar8035Pakke, target);
    }

    public byte[] getData8003(int playerZ) {

        PacketData pd = new PacketData();

        pd.writeIntBE(this.pos.getX());
        pd.writeIntBE(this.pos.getY());
        if (playerZ < this.pos.getZ()) {
            pd.writeIntBE(playerZ); //For å hindre at NPCs flyter over bakken.
        } else {
            pd.writeIntBE(this.pos.getZ());
        }

        pd.writeShortBE((short) this.pos.getTilt());
        pd.writeShortBE((short) this.pos.getRoll());
        pd.writeShortBE((short) this.pos.getDirection());

        pd.writeIntBE(this.getID());

        pd.writeShortBE((short) 0x0);
        pd.writeByte((byte) 0x0);
        pd.writeByte((byte) this.pos.getZone());

        pd.writeIntBE(this.getID()); //Egentlig container ID.
        pd.writeIntBE(this.t.getVehicleID());

        pd.writeByte((byte) this.rank);
        pd.writeByte((byte) 0xA);
        pd.writeByte((byte) 0x0); //Occupancy tag
        if (this.thrustersOn) {
            pd.writeByte((byte) 0x1);
        } else {
            pd.writeByte((byte) 0x0);
        }
        pd.writeByte((byte) 0x0);
        pd.writeByte((byte) this.faction);

        pd.writeIntBE(this.appearanceCounter);
        pd.writeIntBE(this.teamID);

        pd.writeByte(this.getMachineDamage());

        pd.writeIntBE(0x0);

        return pd.getData();
    }

    /**
     * Denne metoden returnerer HELE opcode 0x800A.
     */
    public byte[] getData800A() {

        PacketData pd = new PacketData();

        pd.writeIntBE(0x00040002);
        pd.writeIntBE(this.getID());

        pd.writeIntBE(this.t.getVehicleID());
        pd.writeByte((byte) 0x82);

        if (this.aktivSlot == 0) {
            pd.writeIntBE(this.t.getWeaponA()[0]);
        } else if (this.aktivSlot == 1) {
            pd.writeIntBE(this.t.getWeaponB()[0]);
        } else {
            pd.writeIntBE(-1);
        }

        if (this.shield[0] > 0) {
            pd.writeIntBE(this.shield[0]);
        } else {
            pd.writeIntBE(-1);
        }

        pd.writeByte((byte) 0x82);
        pd.writeByte((byte) 0x0);
        pd.writeByte((byte) 0x0);
        pd.writeByte((byte) 0x80);
        pd.writeShortBE((short) 0x0);

        return pd.getData();
    }

    /**
     * NPC er aktiv dersom den har flere hitpoints igjen eller det er mindre enn
     * 5 sekunder siden den ble ødelagt.
     */
    public boolean isActive() {

        //Hvis NPC har flere hitpoints igjen er den aktiv.
        if (this.hitpoints > 0) {
            return true;
        }

        if (this.killTimeExceeded) {
            return false;
        }

        //NPC er aktiv hvis det er mindre enn 5 sekunder siden den ble ødelagt.
        //NB! Hvis NPC fjernes fra opcode 0x3 for tidlig vil ikke eksplosjonen vises.
        if ((System.currentTimeMillis() - this.killTime) < 5000) {
            return true;
        }

        this.killTimeExceeded = true; //Tydeligvis mer enn 5 sekunder siden NPC ble ødelagt.

        return false;
    }

    /**
     * Forteller NPC at en ny NPC angriper denne.
     */
    public void addAttacker() {
        numberOfAttackers++;
    }

    /**
     * Forteller NPC at en annen NPC har sluttet å angripe den.
     */
    public void removeAttacker() {
        numberOfAttackers--;
        if (numberOfAttackers < 0) {
            numberOfAttackers = 0;
        }
    }

    /**
     *
     * @return Antall fiendtlige NPCs som angriper denne NPC.
     */
    public int getNumberOfAttackers() {
        return numberOfAttackers;
    }

    @Override
    public void performAI() {
        // TODO Auto-generated method stub
    }
}
