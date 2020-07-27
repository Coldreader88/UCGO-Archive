package npc;

import characters.CharacterGame;
import characters.Posisjon;
import java.util.Iterator;
import java.util.Random;

public class NPCboss extends NPCvehicle {

    /**
     * Waypoints som NPC Boss skal følge. Dersom waypoints er i ground zone will
     * NPC kun flytte seg langs X,Y og bruke verdier fra Template for å
     * kontrollere høyde over bakken.
     */
    private Posisjon waypoints[];
    /**
     * Hvilket waypoint NPC Boss beveger seg mot.
     */
    private int targetWaypoint = 1;
    /**
     * Kan brukes til å slå av og på NPC boss. Benyttes ved Helenes PVP battle.
     */
    private boolean active = true;

    /**
     * Oppretter ny NPC boss.
     *
     * @param id NPC ID
     * @param faction Faction NPC tilhører.
     * @param t Template til NPC
     * @param pos Liste ove posisjoner, waypoints, NPC boss skal følge.
     * @param navn Navn på NPC boss.
     */
    public NPCboss(int id, int faction, Template t, Posisjon pos[], String navn) {
        super(id, t, pos[0].lagKopi(), navn);

        this.waypoints = pos;
        this.setFaction(faction);

    }

    /**
     * Brukes når NPC Boss håndteres av LocalManager og er del av et NPC vs NPC
     * area. NPC Boss vil fremdeles oppføre seg på samme måte som en NPC Boss
     * som ikke er del av area men vil i tillegg ha ekstra funksjonalitet som
     * spawning av NPCs.
     *
     * @param area
     */
    public void performAI(AreaManager area) {

        performAI();
    }

    public void performAI() {

        //Oppdater teller som angir når vi kan velge nytt mål.
        this.targetSwitchCounter++;

        //Oppdater teller som angir når vi kan angripe igjen.
        this.targetAttackCounter++;

        //NPC er alltid i bevegelse så dersom vi er i space eller NPC flyr slå på thrusters.
        if (this.pos.getZone() == 2 || this.t.isFlyingType()) {
            this.thrustersOn = true;
        } else {
            this.thrustersOn = false;
        }

        //Flytt NPC boss mot neste waypoint.
        this.moveTowardsWaypoint();

        //Sjekk om vi har et mål som skal angripes.
        if (this.target != null) {
            //Sjekk at målet fremdeles er tilkoblet server og befinner seg i et vehicle.
            if (this.target.isValidTarget()) {
                //OK, målet er fremdeles tilkoblet server og er i et vehicle.

                //Sjekk at målet er innenfor gyldig avstand, dvs sjekk om målet har stukket av.
                long maxAvstand = 8000; //Max gyldig avstand til målet, alltid 2000m for BOSS NPC.

                long avstand = this.pos.distance(this.target.getPosisjon());

                if (avstand > maxAvstand) {
                    //Avstand til målet er større enn tillatt. Målet har kommet seg unna.
                    this.target = null;
                    this.resetAIcounters();
                    return;
                }

                //Angrip målet dersom NPC har våpen utstyrt.
                if (this.getWeapon()[0] != 0) {
                    this.attackTarget();
                }

            } else {
                //Målet finnes ikke mer, fjern det.
                this.target = null;
            }
        }
    }

    private void moveTowardsWaypoint() {

        //Snu NPC Boss mot waypoint.
        this.pos.setDirection(this.pos.beregnRetning(this.waypoints[this.targetWaypoint]));

        //Dersom vi er i space skal NPC også snu seg opp/ned.
        if (this.pos.getZone() == 2) {
            this.pos.setTilt(this.pos.beregnRetningOppNed(this.waypoints[this.targetWaypoint]));
        }

        int hastighet = this.t.getRunSpeed();

        //Flytt NPC ved å bare gjøre +/- på koordinatene. Ikke helt riktig men enkelt og greit.
        Posisjon wp = this.waypoints[this.targetWaypoint];

        if (Math.abs(this.pos.getX() - wp.getX()) < hastighet) {
            this.pos.setX(wp.getX());
        } else if (this.pos.getX() < wp.getX()) {
            this.pos.setX(this.pos.getX() + hastighet);
        } else {
            this.pos.setX(this.pos.getX() - hastighet);
        }

        if (Math.abs(this.pos.getY() - wp.getY()) < hastighet) {
            this.pos.setY(wp.getY());
        } else if (this.pos.getY() < wp.getY()) {
            this.pos.setY(this.pos.getY() + hastighet);
        } else {
            this.pos.setY(this.pos.getY() - hastighet);
        }

        //Dersom NPC er i space flytt også langs Z aksen.
        if (this.pos.getZone() == 2) {
            if (Math.abs(this.pos.getZ() - wp.getZ()) < hastighet) {
                this.pos.setZ(wp.getZ());
            } else if (this.pos.getZ() < wp.getZ()) {
                this.pos.setZ(this.pos.getZ() + hastighet);
            } else {
                this.pos.setZ(this.pos.getZ() - hastighet);
            }
        } else if (this.t.isFlyingType()) {
            //NPC kan fly. Sørg for at den er innenfor max/min høyde.
            int z = gameServer.Heightmap.getZ(this.pos.getX(), this.pos.getY());

            //NPC sin høyde over bakken i meter.
            int h = (this.pos.getZ() - z) / 4;

            if (h < this.t.getMinAltitude() || h > this.t.getMaxAltitude()) {

                //Sett NPC sin høyde til å være midt mellom min og max.
                int dz = this.t.getMaxAltitude() - this.t.getMinAltitude();
                dz *= 2; //dz*2 = dz*4 for å få gameunits, delt på 2 for å komme på midten.

                this.pos.setZ(z + dz);
            }
        }

        //Sjekk om NPC har nådd waypoint. Hvis den har gå videre til neste.
        if (this.pos.getX() == wp.getX() && this.pos.getY() == wp.getY()) {
            //X og Y er like sjekk at Z er like eller om vi er i ground zone.
            if (this.pos.getZ() == wp.getZ() || this.pos.getZone() == 1) {

                //OK, NPC har nådd waypoints.
                this.targetWaypoint++;

                if (this.targetWaypoint >= this.waypoints.length) {
                    this.targetWaypoint = 0;
                }

            }
        }

    }

    /**
     * Tar imot en liste over mulige mål NPC kan angripe og prøver å velge et av
     * dem.
     *
     * @param targets Fiendtlige spillere innenfor området.
     */
    public void chooseTarget(Iterator<NPCtarget> targets) {

        //Sjekk først om vi kan velge nytt mål nå eller må vente lengre.
        if (this.targetSwitchCounter < config.Npc.targetSwitchTime) {
            return;
        }

        //Angir maksimal avstand til et mål. Velger kun mål nærmere enn dette.
        long maxAvstand = 8000; //Alltid 2000m for NPC BOSS.

        //t = Målet NPC skal angripe.
        NPCtarget t = this.target;

        //Avstand = Avstand til målet.
        Long avstand = Long.MAX_VALUE;

        //Beregn avstand til nåværende mål, hvis vi har et.
        if (t != null) {
            avstand = t.getPosisjon().distance(this.getPosisjon());
        }

        //Gå gjennom alle mulige mål vi har fått og velg det som er nærmest og innenfor tillatt avstand.
        while (targets.hasNext()) {

            NPCtarget c = targets.next();

            long d = c.getPosisjon().distance(this.getPosisjon());

            //Sjekk om dette målet er nærmere enn forrige målet.
            if (d < avstand && d < maxAvstand) {
                //Dette målet er bedre enn det forrige, velg det.
                t = c;
                avstand = d;
            }
        }

        //t = det nye målet, eller det gamle hvis ikke noe bedre ble funnet.
        if (this.target != t) {
            this.resetAIcounters();
        }

        this.target = t;

    }

    /**
     * Respawner BOSS NPC på et tilfeldig waypoint.
     *
     */
    public void respawn() {

        this.loadTemplate();
        this.appearanceCounter++;
        this.killTimeExceeded = false;

        //Velg tilfeldig waypoint.
        Random r = new Random();

        int n = r.nextInt(this.waypoints.length - 1);

        this.pos = this.waypoints[n].lagKopi();
        this.targetWaypoint = n + 1; //n vil alltid være minst 1 mindre enn waypoints.length så dette er trygt.
    }

    public boolean isActive() {

        //Sjekk først om NPC Boss skal vises.
        if (!this.active) {
            return false;
        }

        return super.isActive();
    }

    /**
     * Setter om NPC boss skal vises til spillere eller ikke.
     *
     * @param a Ny aktiv status for NPC boss.
     */
    public void setActive(boolean a) {
        this.active = a;
    }
}
