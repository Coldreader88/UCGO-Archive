package npc.spawn;

import characters.Posisjon;
import npc.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å spawne forskjellige typer Zeon supplies.
 *
 * NB! Combat stats for supply guards MÅ endres på senere. Når er de kun satt
 * til tilfeldige verdier.
 *
 */
public class ZeonSupply {

    /**
     * Templates for de forskjellige truck NPC i et supply.
     */
    private static Template hangarTruck, repairTruck, weaponTruck, machineTruck, bankTruck;

    /**
     * Templates for de forskjellige Puzock NPC i et supply.
     */
    private static Template hangarPuzock, repairPuzock, weaponPuzock, machinePuzock, bankPuzock;

    /**
     * Templates for battleships.
     */
    private static Template musai;

    /**
     * De forskjellige NPC guard typene.
     */
    private static Template ZakuCommand, ZakuDesert, ZakuCannon, ZakuFZ, MS09R, MS14, CAMP_GOGG;

    static {

        hangarTruck = new Template(0xF4256, Template.npcType.Supply, 7);
        hangarTruck.setCombatStats(400000, 0, 10);

        repairTruck = new Template(0xF4244, Template.npcType.Supply, 7);
        repairTruck.setCombatStats(400000, 0, 10);

        weaponTruck = new Template(0xF4246, Template.npcType.Supply, 7);
        weaponTruck.setCombatStats(400000, 0, 10);

        machineTruck = new Template(0xF4259, Template.npcType.Supply, 7);
        machineTruck.setCombatStats(400000, 0, 10);

        bankTruck = new Template(0xF425A, Template.npcType.Supply, 7);
        bankTruck.setCombatStats(400000, 0, 10);

        bankPuzock = new Template(0xF4250, Template.npcType.Supply, 7);
        bankPuzock.setCombatStats(400000, 0, 10);

        hangarPuzock = new Template(0xF4251, Template.npcType.Supply, 7);
        hangarPuzock.setCombatStats(400000, 0, 10);

        machinePuzock = new Template(0xF4252, Template.npcType.Supply, 7);
        machinePuzock.setCombatStats(400000, 0, 10);

        weaponPuzock = new Template(0xF4253, Template.npcType.Supply, 7);
        weaponPuzock.setCombatStats(400000, 0, 10);

        repairPuzock = new Template(0xF4254, Template.npcType.Supply, 7);
        repairPuzock.setCombatStats(400000, 0, 10);

        ZakuCommand = new Template(0x6419A, Template.npcType.MovingGuard, 5);
        ZakuCommand.setCombatStats(14000, 10000, 10000);
        ZakuCommand.setMobility(100, 40, true);
        ZakuCommand.setWeaponA(0x445E9, 6000, 600, 3);

        ZakuDesert = new Template(0x6419F, Template.npcType.MovingGuard, 3);
        ZakuDesert.setCombatStats(12000, 10000, 10000);
        ZakuDesert.setMobility(100, 40, true);
        ZakuDesert.setWeaponA(0x445CB, 5000, 600, 3);

        ZakuCannon = new Template(0x6419E, Template.npcType.MovingGuard, 3);
        ZakuCannon.setCombatStats(10000, 10000, 10000);
        ZakuCannon.setMobility(100, 40, true);
        ZakuCannon.setWeaponA(0x445CC, 4000, 1100, 3);

        ZakuFZ = new Template(0x64199, Template.npcType.MovingGuard, 3);
        ZakuFZ.setCombatStats(13000, 10000, 10000);
        ZakuFZ.setMobility(100, 40, true);
        ZakuFZ.setWeaponA(0x445CB, 4000, 600, 3);

        MS09R = new Template(0x641D5, Template.npcType.MovingGuard, 4);
        MS09R.setCombatStats(18000, 10000, 10000);
        MS09R.setMobility(140, 40, true);
        MS09R.setWeaponA(0x445D7, 8000, 900, 5);

        MS14 = new Template(0x641C9, Template.npcType.MovingGuard, 6);
        MS14.setCombatStats(24000, 10000, 10000);
        MS14.setMobility(140, 40, true);
        MS14.setWeaponA(0x4465A, 10000, 600, 3);

        musai = new Template(0xFB781, Template.npcType.Guard, 8);
        musai.setCombatStats(60000, 20000, 10);
        musai.setStationary(true);
        musai.setWeaponA(0x445DD, 2500, 2000, 15);

        CAMP_GOGG = new Template(0x64239, Template.npcType.MovingGuard, 10);
        CAMP_GOGG.setCombatStats(50000, 1000, 200);
        CAMP_GOGG.setMobility(150, 50, true);
        CAMP_GOGG.setWeaponA(0x4465C, 5000, 700, 3);
        CAMP_GOGG.setIgnoreHeightmap(true);
        CAMP_GOGG.setHealing(20);

    }

    /**
     * Oppretter et standard Zeon supply på bakken. Inneholder
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

        AreaManagerGround area = LocalManager.addGroundArea(2, x, y, 8000, 8000, 8000, 8000);

        Posisjon pos;

        int z;

        //For Z trekker vi fra 1000 for å hindre at enkelte trucks flyter over terrenget.
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

        //Zaku command, plassert ved Bank.
        z = gameServer.Heightmap.getZ(x, y - 800);
        pos = new Posisjon(x, y - 800, z, 0, 1);
        area.addNpc(ZakuCommand, pos, "", teamID);

        //Zaku Desert type, plassert ved Weapons.
        z = gameServer.Heightmap.getZ(x, y + 800);
        pos = new Posisjon(x, y + 800, z, -8192, 1);
        area.addNpc(ZakuDesert, pos, "", teamID);

        //Zaku Cannon, plassert ved Repair.
        z = gameServer.Heightmap.getZ(x - 1200, y - 400);
        pos = new Posisjon(x - 1200, y - 400, z, 0, 1);
        area.addNpc(ZakuCannon, pos, "", teamID);

        //Zaku Desert type, plassert ved Repair.
        z = gameServer.Heightmap.getZ(x - 1600, y - 400);
        pos = new Posisjon(x - 1600, y - 400, z, 16384, 1);
        area.addNpc(ZakuDesert, pos, "", teamID);

    }

    /**
     * Oppretter NPC vakter. Beregnet for brukt rundt en ZEON Camp f.eks
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

        AreaManagerGround area = LocalManager.addGroundArea(2, x, y, 4000, 4000, 8000, 8000);

        Posisjon pos;

        pos = new Posisjon(x + 2000, y, z, 0, 1);
        area.addNpc(CAMP_GOGG, pos, "", teamID);

        pos = new Posisjon(x - 2000, y, z, 0, 1);
        area.addNpc(CAMP_GOGG, pos, "", teamID);

        pos = new Posisjon(x, y + 2000, z, 0, 1);
        area.addNpc(CAMP_GOGG, pos, "", teamID);

        pos = new Posisjon(x, y - 2000, z, 0, 1);
        area.addNpc(CAMP_GOGG, pos, "", teamID);
    }

    /**
     * Oppretter et standard Zeon supply i space. Inneholder
     * Hangar,Bank,Weapons,Repair,Machines Guards.
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

        AreaManagerSpace area = LocalManager.addSpaceArea(2, x, y, z, 8000, 8000, 8000, 8000, 8000, 8000);

        Posisjon pos;

        //Puzock Hangar
        pos = new Posisjon(x, y, z, 0, 2);
        area.addNpc(hangarPuzock, pos, "Hangar", teamID);

        //Puzock Bank
        pos = new Posisjon(x + 400, y - 300, z - 200, 0, 2);
        area.addNpc(bankPuzock, pos, "Bank", teamID);

        //Puzock Weapons
        pos = new Posisjon(x + 800, y - 400, z - 400, 0, 2);
        area.addNpc(weaponPuzock, pos, "Weapons", teamID);

        //Puzock Repair
        pos = new Posisjon(x + 1200, y - 500, z - 600, 0, 2);
        area.addNpc(repairPuzock, pos, "Repair", teamID);

        //Puzock Machines
        pos = new Posisjon(x + 1600, y - 600, z - 800, 0, 2);
        area.addNpc(machinePuzock, pos, "Machines", teamID);

        //Zaku2S + 3xZakuFZ over Puzocks
        pos = new Posisjon(x + 800, y - 300, z + 500, 0, 2);
        area.addNpc(ZakuCommand, pos, "", teamID);

        pos = new Posisjon(x + 700, y - 250, z + 500, 0, 2);
        area.addNpc(ZakuFZ, pos, "", teamID);

        pos = new Posisjon(x + 900, y - 250, z + 500, 0, 2);
        area.addNpc(ZakuFZ, pos, "", teamID);

        pos = new Posisjon(x + 800, y - 500, z + 500, 0, 2);
        area.addNpc(ZakuFZ, pos, "", teamID);

    }

    /**
     * Oppretter et Zeon supply i space som inneholder battleships.
     *
     * Oppgitt posisjon er hvor Hangar plasseres.
     *
     * @param x X posisjon til supply.
     * @param y Y posisjon til supply.
     * @param z Z posisjon til supply.
     * @param navn Team navn til supply.
     */
    public static void spaceFleetSupply(int x, int y, int z, String navn) {

        int teamID = Team.createTeam(navn);

        AreaManagerSpace area = LocalManager.addSpaceArea(2, x, y, z, 16000, 16000, 16000, 8000, 8000, 8000);

        Posisjon pos;

        //Puzock Hangar
        pos = new Posisjon(x, y, z, 0, 2);
        area.addNpc(hangarPuzock, pos, "Hangar", teamID);

        //Puzock Bank
        pos = new Posisjon(x + 400, y - 300, z - 200, 0, 2);
        area.addNpc(bankPuzock, pos, "Bank", teamID);

        //Puzock Weapons
        pos = new Posisjon(x + 800, y - 400, z - 400, 0, 2);
        area.addNpc(weaponPuzock, pos, "Weapons", teamID);

        //Puzock Repair
        pos = new Posisjon(x + 1200, y - 500, z - 600, 0, 2);
        area.addNpc(repairPuzock, pos, "Repair", teamID);

        //Puzock Machines
        pos = new Posisjon(x + 1600, y - 600, z - 800, 0, 2);
        area.addNpc(machinePuzock, pos, "Machines", teamID);

        //Musai over Puzocks.
        pos = new Posisjon(x + 800, y, z + 1000, 8192, 2);
        area.addNpc(musai, pos, "", teamID);

        //Musai foran Puzocks.
        pos = new Posisjon(x - 1000, y, z - 400, 32768, 2);
        area.addNpc(musai, pos, "", teamID);

        //2xMS14 ved Musai over Puzocks.
        pos = new Posisjon(x + 400, y - 400, z + 1400, 32768, 2);
        area.addNpc(MS14, pos, "", teamID);

        pos = new Posisjon(x + 600, y - 400, z + 1200, 32768, 2);
        area.addNpc(MS14, pos, "", teamID);

        //2xMS09R ved Musai foran Puzocks.
        pos = new Posisjon(x - 1200, y + 400, z, 0, 2);
        area.addNpc(MS09R, pos, "", teamID);

        pos = new Posisjon(x - 1300, y + 500, z + 100, 0, 2);
        area.addNpc(MS09R, pos, "", teamID);

    }
}
