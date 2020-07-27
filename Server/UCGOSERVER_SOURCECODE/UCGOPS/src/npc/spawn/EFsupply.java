package npc.spawn;

import characters.Posisjon;
import npc.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å spawne forskjellige typer EF supplies.
 *
 * NB! Combat stats for supply guards MÅ endres på senere. Når er de kun satt
 * til tilfeldige verdier.
 *
 */
public class EFsupply {

    /**
     * Templates for de forskjellige truck NPC i et supply.
     */
    private static Template hangarTruck, repairTruck, weaponTruck, machineTruck, bankTruck;

    /**
     * Templates for de forskjellige Columbus NPC i et supply.
     */
    private static Template hangarColumbus, repairColumbus, weaponColumbus, machineColumbus, bankColumbus;

    /**
     * Battleship templates.
     */
    private static Template salamis, magellan;

    /**
     * Templates for de forskjellige NPC supply guards.
     */
    private static Template RGM79, RGC80, RGM79G, RGM79GS, RGM79CS, RGM79SC, CAMP_GMLA;

    /**
     * Initialiserer templates.
     */
    static {

        hangarTruck = new Template(0xF4255, Template.npcType.Supply, 7);
        hangarTruck.setCombatStats(400000, 0, 10);

        repairTruck = new Template(0xF4243, Template.npcType.Supply, 7);
        repairTruck.setCombatStats(400000, 0, 10);

        weaponTruck = new Template(0xF4245, Template.npcType.Supply, 7);
        weaponTruck.setCombatStats(400000, 0, 10);

        machineTruck = new Template(0xF4257, Template.npcType.Supply, 7);
        machineTruck.setCombatStats(400000, 0, 10);

        bankTruck = new Template(0xF4258, Template.npcType.Supply, 7);
        bankTruck.setCombatStats(400000, 0, 10);

        hangarColumbus = new Template(0xF424C, Template.npcType.Supply, 7);
        hangarColumbus.setCombatStats(400000, 0, 10);

        repairColumbus = new Template(0xF424F, Template.npcType.Supply, 7);
        repairColumbus.setCombatStats(400000, 0, 10);

        weaponColumbus = new Template(0xF424E, Template.npcType.Supply, 7);
        weaponColumbus.setCombatStats(400000, 0, 10);

        machineColumbus = new Template(0xF424D, Template.npcType.Supply, 7);
        machineColumbus.setCombatStats(400000, 0, 10);

        bankColumbus = new Template(0xF424B, Template.npcType.Supply, 7);
        bankColumbus.setCombatStats(400000, 0, 10);

        salamis = new Template(0xFB77F, Template.npcType.Guard, 8);
        salamis.setCombatStats(50000, 20000, 10);
        salamis.setStationary(true);
        salamis.setWeaponA(0x445DD, 2000, 2000, 15);

        magellan = new Template(0xFB780, Template.npcType.Guard, 8);
        magellan.setCombatStats(80000, 20000, 10);
        magellan.setStationary(true);
        magellan.setWeaponA(0x445DD, 2500, 2000, 15);

        RGM79 = new Template(0x64190, Template.npcType.MovingGuard, 3);
        RGM79.setCombatStats(10000, 10000, 100);
        RGM79.setMobility(100, 40, true);
        RGM79.setWeaponA(0x44656, 5000, 600, 3);
        RGM79.setShield(0x57E42, 2000, 50);

        RGM79G = new Template(0x64192, Template.npcType.MovingGuard, 5);
        RGM79G.setCombatStats(14000, 15000, 15000);
        RGM79G.setMobility(100, 40, true);
        RGM79G.setWeaponA(0x445C5, 6000, 600, 3);
        RGM79G.setShield(0x57E41, 2000, 50);

        RGM79GS = new Template(0x641C5, Template.npcType.MovingGuard, 5);
        RGM79GS.setCombatStats(14000, 15000, 15000);
        RGM79GS.setMobility(100, 40, true);
        RGM79GS.setWeaponA(0x445C5, 6000, 600, 3);
        RGM79GS.setShield(0x57E41, 2000, 50);

        RGC80 = new Template(0x64193, Template.npcType.MovingGuard, 3);
        RGC80.setCombatStats(14000, 10000, 10000);
        RGC80.setMobility(100, 40, true);
        RGC80.setWeaponA(0x445CC, 4000, 1100, 3);

        RGM79CS = new Template(0x641D7, Template.npcType.MovingGuard, 6);
        RGM79CS.setCombatStats(16000, 15000, 15000);
        RGM79CS.setMobility(100, 40, true);
        RGM79CS.setWeaponA(0x445E0, 8000, 600, 3);
        RGM79CS.setShield(0x57E42, 2000, 50);

        RGM79SC = new Template(0x641B6, Template.npcType.MovingGuard, 6);
        RGM79SC.setCombatStats(16000, 15000, 15000);
        RGM79SC.setMobility(100, 40, true);
        RGM79SC.setWeaponA(0x445DD, 8000, 1600, 7);

        CAMP_GMLA = new Template(0x64207, Template.npcType.MovingGuard, 10);
        CAMP_GMLA.setCombatStats(50000, 1000, 200);
        CAMP_GMLA.setMobility(150, 50, true);
        CAMP_GMLA.setWeaponA(0x445E0, 5000, 700, 3);
        CAMP_GMLA.setIgnoreHeightmap(true);
        CAMP_GMLA.setHealing(20);
    }

    /**
     * Oppretter et standard EF supply på bakken. Inneholder
     * Hangar,Bank,Weapons,Repair,Machines + 4 Guards.
     *
     * Oppgitt posisjon er hvor Hangar plasseres.
     *
     * @param x X posisjon til supply.
     * @param y Y posisjon til supply.
     * @param navn Team navn til supply.
     */
    public static void standardGroundSupply(int x, int y, String navn) {

        int teamID = Team.createTeam(navn);

        AreaManagerGround area = LocalManager.addGroundArea(1, x, y, 8000, 8000, 8000, 8000);

        Posisjon pos;

        int z;

        //For Z verdi trekker vi fra 1000 for å hindre at enkelte trucks flyter over terrenget.
        //Legg inn hangar truck.
        z = gameServer.Heightmap.getZ(x, y);
        pos = new Posisjon(x, y, z - 1000, -16384, 1);
        area.addNpc(hangarTruck, pos, "Hangar", teamID);

        //Weapons truck
        z = gameServer.Heightmap.getZ(x, y + 400);
        pos = new Posisjon(x, y + 400, z - 1000, -16384, 1);
        area.addNpc(weaponTruck, pos, "Weapons", teamID);

        //Bank truck
        z = gameServer.Heightmap.getZ(x, y - 400);
        pos = new Posisjon(x, y - 400, z - 1000, -16384, 1);
        area.addNpc(bankTruck, pos, "Bank", teamID);

        //Machine truck
        z = gameServer.Heightmap.getZ(x - 400, y - 400);
        pos = new Posisjon(x - 400, y - 400, z - 1000, 0, 1);
        area.addNpc(machineTruck, pos, "Machines", teamID);

        //Repair truck
        z = gameServer.Heightmap.getZ(x - 800, y - 400);
        pos = new Posisjon(x - 800, y - 400, z - 1000, 0, 1);
        area.addNpc(repairTruck, pos, "Repair", teamID);

        //GM command, plassert ved Bank truck.
        z = gameServer.Heightmap.getZ(x, y - 800);
        pos = new Posisjon(x, y - 800, z, 0, 1);
        area.addNpc(RGM79G, pos, "", teamID);

        //RGM79, plassert ved Weapons truck.
        z = gameServer.Heightmap.getZ(x, y + 800);
        pos = new Posisjon(x, y + 800, z, -8192, 1);
        area.addNpc(RGM79, pos, "", teamID);

        //RGC80, plassert ved Repair truck.
        z = gameServer.Heightmap.getZ(x - 1200, y - 400);
        pos = new Posisjon(x - 1200, y - 400, z, 0, 1);
        area.addNpc(RGC80, pos, "", teamID);

        //RGM79, plassert ved Repair truck.
        z = gameServer.Heightmap.getZ(x - 1600, y - 400);
        pos = new Posisjon(x - 1600, y - 400, z, 16384, 1);
        area.addNpc(RGM79, pos, "", teamID);
    }

    /**
     * Oppretter NPC vakter. Beregnet for brukt rundt en EF Camp f.eks
     * Newman/Richmond.
     *
     * @param x X posisjon.
     * @param y Y posisjon.
     * @param z Z posisjon. Må settes MANUELT ettersom Newman/Richmond ikke
     * benytter det vanlige heightmap.
     * @param navn Team navn.
     */
    public static void campGuards(int x, int y, int z, String navn) {

        int teamID = Team.createTeam(navn);

        AreaManagerGround area = LocalManager.addGroundArea(1, x, y, 4000, 4000, 8000, 8000);

        Posisjon pos;

        pos = new Posisjon(x + 2000, y, z, 0, 1);
        area.addNpc(CAMP_GMLA, pos, "", teamID);

        pos = new Posisjon(x - 2000, y, z, 0, 1);
        area.addNpc(CAMP_GMLA, pos, "", teamID);

        pos = new Posisjon(x, y + 2000, z, 0, 1);
        area.addNpc(CAMP_GMLA, pos, "", teamID);

        pos = new Posisjon(x, y - 2000, z, 0, 1);
        area.addNpc(CAMP_GMLA, pos, "", teamID);
    }

    /**
     * Oppretter et standard EF supply i space. Inneholder
     * Hangar,Bank,Weapons,Repair,Machines + 4 Guards.
     *
     * Oppgitt posisjon er hvor Hangar plasseres.
     *
     * @param x X posisjon til supply.
     * @param y Y posisjon til supply.
     * @param z Z posisjon til supply.
     * @param navn Team navn til supply.
     */
    public static void standardSpaceSupply(int x, int y, int z, String navn) {

        int teamID = Team.createTeam(navn);

        AreaManagerSpace area = LocalManager.addSpaceArea(1, x, y, z, 8000, 8000, 8000, 8000, 8000, 8000);

        Posisjon pos;

        //Columbus Hangar.
        pos = new Posisjon(x, y, z, 0, 2);
        area.addNpc(hangarColumbus, pos, "Hangar", teamID);

        //Columbus Bank.
        pos = new Posisjon(x - 800, y, z + 800, 0, 2);
        area.addNpc(bankColumbus, pos, "Bank", teamID);

        //Columbus Weapons.
        pos = new Posisjon(x + 800, y, z + 800, 0, 2);
        area.addNpc(weaponColumbus, pos, "Weapons", teamID);

        //Columbus Machines.
        pos = new Posisjon(x, y + 200, z - 800, 0, 2);
        area.addNpc(machineColumbus, pos, "Machines", teamID);

        //Columbus Repair
        pos = new Posisjon(x, y - 600, z - 800, 0, 2);
        area.addNpc(repairColumbus, pos, "Repair", teamID);

        //GM command, øverst i formasjon.
        pos = new Posisjon(x, y, z + 1000, 0, 2);
        area.addNpc(RGM79GS, pos, "", teamID);

        //RGM79, foran supply.
        pos = new Posisjon(x, y + 600, z, 0, 2);
        area.addNpc(RGM79, pos, "", teamID);

        //RGM79, bak supply.
        pos = new Posisjon(x, y - 1000, z, 0, 2);
        area.addNpc(RGM79, pos, "", teamID);
    }

    /**
     * Oppretter et EF supply i space som inneholder battleships og mer MS enn
     * standard supply.
     *
     * Oppgitt posisjon er hvor hangar plasseres.
     *
     * @param x X posisjon til supply.
     * @param y Y posisjon til supply.
     * @param z Z posisjon til supply.
     * @param navn Team navn til supply.
     */
    public static void spaceFleetSupply(int x, int y, int z, String navn) {

        int teamID = Team.createTeam(navn);

        AreaManagerSpace area = LocalManager.addSpaceArea(1, x, y, z, 16000, 16000, 16000, 8000, 8000, 8000);

        Posisjon pos;

        //Columbus hangar
        pos = new Posisjon(x, y, z, 0, 2);
        area.addNpc(hangarColumbus, pos, "Hangar", teamID);

        //Columbus bank
        pos = new Posisjon(x - 600, y + 600, z, 0, 2);
        area.addNpc(bankColumbus, pos, "Bank", teamID);

        //Columbus weapons
        pos = new Posisjon(x + 600, y + 600, z, 0, 2);
        area.addNpc(weaponColumbus, pos, "Weapons", teamID);

        //Columbus repair
        pos = new Posisjon(x - 600, y + 1200, z, 0, 2);
        area.addNpc(repairColumbus, pos, "Repair", teamID);

        //Columbus machines
        pos = new Posisjon(x + 600, y + 1200, z, 0, 2);
        area.addNpc(machineColumbus, pos, "Machines", teamID);

        //Salamis
        pos = new Posisjon(x + 2000, y, z + 500, 0, 2);
        area.addNpc(salamis, pos, "", teamID);

        //Salamis
        pos = new Posisjon(x, y - 1000, z - 1000, 0, 2);
        area.addNpc(salamis, pos, "", teamID);

        //Magellan
        pos = new Posisjon(x, y + 2000, z + 250, 16384, 2);
        area.addNpc(magellan, pos, "", teamID);

        //Magellan
        pos = new Posisjon(x - 2000, y - 500, z - 500, 0, 2);
        area.addNpc(magellan, pos, "", teamID);

        //4xRGM-79CS ved Columbus
        pos = new Posisjon(x + 900, y + 600, z, -16384, 2);
        area.addNpc(RGM79CS, pos, "", teamID);
        pos = new Posisjon(x + 900, y + 800, z, -16384, 2);
        area.addNpc(RGM79CS, pos, "", teamID);
        pos = new Posisjon(x + 900, y + 1000, z, -16384, 2);
        area.addNpc(RGM79CS, pos, "", teamID);
        pos = new Posisjon(x + 900, y + 1200, z, -16384, 2);
        area.addNpc(RGM79CS, pos, "", teamID);

        //4xRGM-79SC ved Columbus
        pos = new Posisjon(x - 900, y + 600, z, 16384, 2);
        area.addNpc(RGM79SC, pos, "", teamID);
        pos = new Posisjon(x - 900, y + 800, z, 16384, 2);
        area.addNpc(RGM79SC, pos, "", teamID);
        pos = new Posisjon(x - 900, y + 1000, z, 16384, 2);
        area.addNpc(RGM79SC, pos, "", teamID);
        pos = new Posisjon(x - 900, y + 1200, z, 16384, 2);
        area.addNpc(RGM79SC, pos, "", teamID);

    }

    /**
     * Oppretter et EF supply i space som er bedre bevoktet enn standard supply.
     *
     * Oppgitt posisjon er hvor hangar plasseres.
     *
     * @param x X posisjon til supply.
     * @param y Y posisjon til supply.
     * @param z Z posisjon til supply.
     * @param navn Team navn til supply.
     */
    public static void spaceGuardedSupply(int x, int y, int z, String navn) {

        int teamID = Team.createTeam(navn);

        AreaManagerSpace area = LocalManager.addSpaceArea(1, x, y, z, 16000, 16000, 16000, 8000, 8000, 8000);

        Posisjon pos;

        //Columbus Hangar
        pos = new Posisjon(x, y, z, 0, 2);
        area.addNpc(hangarColumbus, pos, "Hangar", teamID);

        //Columbus Bank
        pos = new Posisjon(x, y - 600, z, 0, 2);
        area.addNpc(bankColumbus, pos, "Bank", teamID);

        //Columbus Weapons
        pos = new Posisjon(x, y + 600, z, 0, 2);
        area.addNpc(weaponColumbus, pos, "Weapons", teamID);

        //Columbus Repair
        pos = new Posisjon(x, y - 1200, z, 0, 2);
        area.addNpc(repairColumbus, pos, "Repair", teamID);

        //Columbus Machines
        pos = new Posisjon(x, y + 1200, z, 0, 2);
        area.addNpc(machineColumbus, pos, "Machines", teamID);

        //2xRGM-79CS+RGM-79SC ved Machines.
        pos = new Posisjon(x, y + 1200, z + 300, 0, 2);
        area.addNpc(RGM79SC, pos, "", teamID);

        pos = new Posisjon(x + 200, y + 1000, z + 300, 0, 2);
        area.addNpc(RGM79CS, pos, "", teamID);

        pos = new Posisjon(x - 200, y + 1000, z + 300, 0, 2);
        area.addNpc(RGM79CS, pos, "", teamID);

        //2xRGM-79CS+RGM-79SC ved Repair.
        pos = new Posisjon(x, y - 1200, z + 300, 32768, 2);
        area.addNpc(RGM79SC, pos, "", teamID);

        pos = new Posisjon(x + 200, y - 1000, z + 300, 32768, 2);
        area.addNpc(RGM79CS, pos, "", teamID);

        pos = new Posisjon(x - 200, y - 1000, z + 300, 32768, 2);
        area.addNpc(RGM79CS, pos, "", teamID);

    }

}
