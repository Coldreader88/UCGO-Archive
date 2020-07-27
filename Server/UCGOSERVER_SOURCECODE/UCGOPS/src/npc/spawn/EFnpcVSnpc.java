package npc.spawn;

import npc.Template;

/**
 *
 * @author UCGOSERVER
 *
 * Denne klassen brukes til å holde templates for EF MS brukt i NPC vs NPC
 * soner.
 *
 * NB! Husk at NPCs i NPC vs NPC soner oppdateres to ganger i sekunder
 * imotsetning til vanlige NPCs som oppdateres en gang i sekundet.
 */
public class EFnpcVSnpc {

    public static Template RMS179;

    public static Template RMS179_STRONG;

    public static Template NEMO;

    public static Template NEMO_STRONG;

    public static Template JEGAN;

    public static Template JEGAN_STRONG;

    public static Template MARASAI;

    public static Template MARASAI_STRONG;

    public static Template RICKDIAS;

    public static Template RICKDIAS_STRONG;

    public static Template GP01_GUNDAM;

    public static Template RX178;

    static {

        RMS179 = new Template(0x64247, Template.npcType.Grunt, 8);
        RMS179.setCombatStats(25000, 150, 100);
        RMS179.setMobility(80, 30, true);
        RMS179.setShield(0x57E40, 5000, 50);
        RMS179.setWeaponA(0x446EA, 500, 900, 3);
        RMS179.addDropItem(CommonDrops.AMMO_BAZOOKA_LARGE);
        RMS179.addDropItem(CommonDrops.AMMO_ENERGYPACK_LARGE);
        RMS179.addDropItem(CommonDrops.AMMO_CANNON_LARGE);
        RMS179.addDropItem(CommonDrops.AMMO_MACHINEGUN_LARGE);
        RMS179.addDropItem(CommonDrops.REPAIR_ER_LV3_ZEON);
        RMS179.addDropItem(CommonDrops.REPAIR_ER_LV2_ZEON);
        RMS179.addDropItem(CommonDrops.REPAIR_ER_LV1_ZEON);

        RMS179_STRONG = new Template(0x64247, Template.npcType.Grunt, 10);
        RMS179_STRONG.setCombatStats(35000, 170, 150);
        RMS179_STRONG.setMobility(90, 30, true);
        RMS179_STRONG.setShield(0x57E40, 8000, 50);
        RMS179_STRONG.setWeaponA(0x446EA, 600, 900, 3);
        RMS179_STRONG.addDropItem(CommonDrops.AMMO_BAZOOKA_LARGE);
        RMS179_STRONG.addDropItem(CommonDrops.AMMO_ENERGYPACK_LARGE);
        RMS179_STRONG.addDropItem(CommonDrops.AMMO_CANNON_LARGE);
        RMS179_STRONG.addDropItem(CommonDrops.AMMO_MACHINEGUN_LARGE);
        RMS179_STRONG.addDropItem(CommonDrops.REPAIR_ER_LV3_ZEON);
        RMS179_STRONG.addDropItem(CommonDrops.REPAIR_ER_LV2_ZEON);
        RMS179_STRONG.addDropItem(CommonDrops.REPAIR_ER_LV1_ZEON);
        RMS179_STRONG.addDropItem(CommonDrops.MATERIAL_FINETCC_SMALL);
        RMS179_STRONG.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL);
        RMS179_STRONG.addDropItem(CommonDrops.HG_180MM);

        NEMO = new Template(0x64248, Template.npcType.Grunt, 9);
        NEMO.setCombatStats(50000, 150, 100);
        NEMO.setMobility(100, 30, true);
        NEMO.setShield(0x57E7A, 8000, 50);
        NEMO.setWeaponA(0x446EA, 600, 1000, 3);
        NEMO.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL2);
        NEMO.addDropItem(CommonDrops.MATERIAL_FINETCC_SMALL);
        NEMO.addDropItem(CommonDrops.REPAIR_ER_LV2_ZEON);
        NEMO.addDropItem(CommonDrops.REPAIR_ER_LV1_ZEON);
        NEMO.addDropItem(CommonDrops.AMMO_MACHINEGUN_LARGE);
        NEMO.addDropItem(CommonDrops.AMMO_ENERGYPACK_MEDIUM);
        NEMO.addDropItem(CommonDrops.AMMO_CANNON_MEDIUM);

        NEMO_STRONG = new Template(0x64248, Template.npcType.Grunt, 10);
        NEMO_STRONG.setCombatStats(50000, 190, 150);
        NEMO_STRONG.setMobility(120, 40, true);
        NEMO_STRONG.setShield(0x57E7A, 10000, 50);
        NEMO_STRONG.setWeaponA(0x446EA, 700, 1000, 3);
        //NEMO_STRONG.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL);
        NEMO_STRONG.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL2);
        NEMO_STRONG.addDropItem(CommonDrops.MATERIAL_FINETCC_SMALL2);
        NEMO_STRONG.addDropItem(CommonDrops.REPAIR_ER_LV2_ZEON);
        NEMO_STRONG.addDropItem(CommonDrops.REPAIR_ER_LV1_ZEON);
        NEMO_STRONG.addDropItem(CommonDrops.AMMO_MACHINEGUN_LARGE);
        NEMO_STRONG.addDropItem(CommonDrops.AMMO_ENERGYPACK_MEDIUM);
        NEMO_STRONG.addDropItem(CommonDrops.AMMO_CANNON_MEDIUM);
        NEMO_STRONG.addDropItem(CommonDrops.HG_100MM);

        JEGAN = new Template(0x64257, Template.npcType.Grunt, 9);
        JEGAN.setCombatStats(40000, 150, 150);
        JEGAN.setMobility(120, 30, true);
        JEGAN.setShield(0x57E81, 8000, 50);
        JEGAN.setWeaponA(0x446F5, 600, 800, 4);
        JEGAN.addDropItem(CommonDrops.MATERIAL_FINETCC_SMALL2);
        JEGAN.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL2);
        JEGAN.addDropItem(CommonDrops.ENGINE_HB_L4A);
        JEGAN.addDropItem(CommonDrops.ENGINE_HB_L4B);
        JEGAN.addDropItem(CommonDrops.ENGINE_HB_L4C);
        JEGAN.addDropItem(CommonDrops.ENGINE_HB_L4D);
        JEGAN.addDropItem(CommonDrops.ENGINE_HB_L4E);

        JEGAN_STRONG = new Template(0x64257, Template.npcType.Grunt, 10);
        JEGAN_STRONG.setCombatStats(50000, 200, 200);
        JEGAN_STRONG.setMobility(140, 30, true);
        JEGAN_STRONG.setShield(0x57E81, 10000, 50);
        JEGAN_STRONG.setWeaponA(0x446F5, 600, 800, 3);
        JEGAN_STRONG.addDropItem(CommonDrops.MATERIAL_FINETCC_SMALL2);
        JEGAN_STRONG.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL2);
        //JEGAN_STRONG.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL);
        JEGAN_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        JEGAN_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        JEGAN_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        JEGAN_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        JEGAN_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        JEGAN_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        JEGAN_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        JEGAN_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        JEGAN_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        JEGAN_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);

        MARASAI = new Template(0x64249, Template.npcType.Grunt, 10);
        MARASAI.setCombatStats(80000, 180, 200);
        MARASAI.setMobility(130, 40, true);
        MARASAI.setWeaponA(0x446EB, 1000, 700, 3);
        //MARASAI.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL3);
        MARASAI.addDropItem(CommonDrops.MATERIAL_FINETCC_SMALL2);
        MARASAI.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MARASAI.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MARASAI.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MARASAI.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MARASAI.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MARASAI.addDropItem(CommonDrops.HG_MMP78);

        MARASAI_STRONG = new Template(0x64249, Template.npcType.Grunt, 11);
        MARASAI_STRONG.setCombatStats(100000, 200, 200);
        MARASAI_STRONG.setMobility(130, 40, true);
        MARASAI_STRONG.setWeaponA(0x446EB, 1000, 800, 3);
        //MARASAI_STRONG.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL3);
        MARASAI_STRONG.addDropItem(CommonDrops.MATERIAL_FINETCC_SMALL2);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MARASAI_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MARASAI_STRONG.addDropItem(CommonDrops.HG_GIANT_BAZOOKA);

        RICKDIAS = new Template(0x6424E, Template.npcType.Grunt, 11);
        RICKDIAS.setCombatStats(80000, 200, 150);
        RICKDIAS.setMobility(150, 50, true);
        RICKDIAS.setWeaponA(0x446EB, 1000, 800, 3);
        //RICKDIAS.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL3);
        RICKDIAS.addDropItem(CommonDrops.MATERIAL_FINETCC_SMALL2);
        RICKDIAS.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL2);
        RICKDIAS.addDropItem(CommonDrops.GREAT_HEAT_HAWK);
        RICKDIAS.addDropItem(CommonDrops.GIANT_HEAT_HAWK);

        RICKDIAS_STRONG = new Template(0x6424E, Template.npcType.Grunt, 12);
        RICKDIAS_STRONG.setCombatStats(100000, 200, 200);
        RICKDIAS_STRONG.setMobility(150, 50, true);
        RICKDIAS_STRONG.setWeaponA(0x446EB, 1200, 1000, 3);
        //RICKDIAS_STRONG.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL3);
        RICKDIAS_STRONG.addDropItem(CommonDrops.MATERIAL_FINETCC_SMALL2);
        RICKDIAS_STRONG.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL2);
        RICKDIAS_STRONG.addDropItem(CommonDrops.GREAT_HEAT_HAWK);
        RICKDIAS_STRONG.addDropItem(CommonDrops.GIANT_HEAT_HAWK);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_COMBAT_HELMET_VISOR);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ARTSOH);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_CLOTHPICTURES);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_EF);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_EF_LIMITED);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ISK);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_LIMITED);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_THEGOSO);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEN);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEON);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEON_LIMITED);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_UNIFORM_AMURO);
        RICKDIAS_STRONG.addDropItem(CommonDrops.CLOTHES_UNIFORM_CHAR);
        RICKDIAS_STRONG.addDropItem(CommonDrops.STUFF_HARO);

        GP01_GUNDAM = new Template(0x6424B, Template.npcType.Grunt, 11);
        GP01_GUNDAM.setCombatStats(80000, 200, 150);
        GP01_GUNDAM.setMobility(150, 50, true);
        GP01_GUNDAM.setWeaponA(0x446ED, 1000, 800, 3);
        GP01_GUNDAM.setShield(0x57E7C, 10000, 25);
        //GP01_GUNDAM.addDropItem(CommonDrops.MATERIAL_AMX107_PARTS);
        GP01_GUNDAM.addDropItem(CommonDrops.MATERIAL_FINETCC_SMALL2);

        RX178 = new Template(0x64251, Template.npcType.Grunt, 12);
        RX178.setCombatStats(100000, 200, 150);
        RX178.setMobility(150, 50, true);
        RX178.setWeaponA(0x446F0, 1000, 800, 3);
        RX178.setShield(0x57E7E, 10000, 25);
        //RX178.addDropItem(CommonDrops.MATERIAL_AMX107_PARTS);
        //RX178.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL3);
    }
}
