package npc.spawn;

import npc.Template;

/**
 *
 * @author UCGOSERVER
 *
 * Denne klassen brukes til å holde templates for ZEON MS brukt i NPC vs NPC
 * soner.
 *
 * NB! Husk at NPCs i NPC vs NPC soner oppdateres to ganger i sekunder
 * imotsetning til vanlige NPCs som oppdateres en gang i sekundet.
 */
public class ZeonNpcVsNpc {

    public static Template ZAKUR1;

    public static Template ZAKUR1_STRONG;

    public static Template HIZACK;

    public static Template HIZACK_STRONG;

    public static Template GAZAC;

    public static Template GAZAC_STRONG;

    public static Template MARASAI;

    public static Template MARASAI_STRONG;

    public static Template RICKDIAS;

    public static Template RICKDIAS_STRONG;

    public static Template GEARA_DOGA;

    public static Template BLUE_GEARA_DOGA;

    static {

        ZAKUR1 = new Template(0xF6975, Template.npcType.Grunt, 8);
        ZAKUR1.setCombatStats(30000, 150, 100);
        ZAKUR1.setMobility(80, 30, true);
        ZAKUR1.setWeaponA(0x446DC, 500, 900, 3);
        ZAKUR1.addDropItem(CommonDrops.AMMO_BAZOOKA_LARGE);
        ZAKUR1.addDropItem(CommonDrops.AMMO_ENERGYPACK_LARGE);
        ZAKUR1.addDropItem(CommonDrops.AMMO_CANNON_LARGE);
        ZAKUR1.addDropItem(CommonDrops.AMMO_MACHINEGUN_LARGE);
        ZAKUR1.addDropItem(CommonDrops.REPAIR_ER_LV3_EF);
        ZAKUR1.addDropItem(CommonDrops.REPAIR_ER_LV2_EF);
        ZAKUR1.addDropItem(CommonDrops.REPAIR_ER_LV1_EF);

        ZAKUR1_STRONG = new Template(0xF6975, Template.npcType.Grunt, 8);
        ZAKUR1_STRONG.setCombatStats(42000, 170, 100);
        ZAKUR1_STRONG.setMobility(90, 30, true);
        ZAKUR1_STRONG.setWeaponA(0x446DC, 600, 900, 3);
        ZAKUR1_STRONG.addDropItem(CommonDrops.AMMO_BAZOOKA_LARGE);
        ZAKUR1_STRONG.addDropItem(CommonDrops.AMMO_ENERGYPACK_LARGE);
        ZAKUR1_STRONG.addDropItem(CommonDrops.AMMO_CANNON_LARGE);
        ZAKUR1_STRONG.addDropItem(CommonDrops.AMMO_MACHINEGUN_LARGE);
        ZAKUR1_STRONG.addDropItem(CommonDrops.REPAIR_ER_LV3_EF);
        ZAKUR1_STRONG.addDropItem(CommonDrops.REPAIR_ER_LV2_EF);
        ZAKUR1_STRONG.addDropItem(CommonDrops.REPAIR_ER_LV1_EF);
        ZAKUR1_STRONG.addDropItem(CommonDrops.MATERIAL_FINELUNA_SMALL);
        ZAKUR1_STRONG.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL);
        ZAKUR1_STRONG.addDropItem(CommonDrops.HG_180MM);

        HIZACK = new Template(0x64259, Template.npcType.Grunt, 9);
        HIZACK.setCombatStats(57000, 150, 100);
        HIZACK.setMobility(100, 30, true);
        HIZACK.setWeaponA(0x446EB, 600, 1000, 3);
        HIZACK.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL2);
        HIZACK.addDropItem(CommonDrops.MATERIAL_FINELUNA_SMALL);
        HIZACK.addDropItem(CommonDrops.REPAIR_ER_LV2_EF);
        HIZACK.addDropItem(CommonDrops.REPAIR_ER_LV1_EF);
        HIZACK.addDropItem(CommonDrops.AMMO_MACHINEGUN_LARGE);
        HIZACK.addDropItem(CommonDrops.AMMO_ENERGYPACK_MEDIUM);
        HIZACK.addDropItem(CommonDrops.AMMO_CANNON_MEDIUM);

        HIZACK_STRONG = new Template(0x64259, Template.npcType.Grunt, 10);
        HIZACK_STRONG.setCombatStats(60000, 190, 100);
        HIZACK_STRONG.setMobility(120, 40, true);
        HIZACK_STRONG.setWeaponA(0x446EB, 700, 1000, 3);
        HIZACK_STRONG.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL2);
        HIZACK_STRONG.addDropItem(CommonDrops.MATERIAL_FINELUNA_SMALL);
        //HIZACK_STRONG.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL);
        HIZACK_STRONG.addDropItem(CommonDrops.REPAIR_ER_LV2_EF);
        HIZACK_STRONG.addDropItem(CommonDrops.REPAIR_ER_LV1_EF);
        HIZACK_STRONG.addDropItem(CommonDrops.AMMO_MACHINEGUN_LARGE);
        HIZACK_STRONG.addDropItem(CommonDrops.AMMO_ENERGYPACK_MEDIUM);
        HIZACK_STRONG.addDropItem(CommonDrops.AMMO_CANNON_MEDIUM);
        HIZACK_STRONG.addDropItem(CommonDrops.HG_100MM);

        GAZAC = new Template(0x6425A, Template.npcType.Grunt, 9);
        GAZAC.setCombatStats(47000, 150, 150);
        GAZAC.setMobility(120, 30, true);
        GAZAC.setWeaponA(0x446EB, 600, 800, 3);
        GAZAC.addDropItem(CommonDrops.MATERIAL_FINELUNA_SMALL2);
        GAZAC.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL2);
        GAZAC.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GAZAC.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GAZAC.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GAZAC.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GAZAC.addDropItem(CommonDrops.ENGINE_HB_L4E);

        GAZAC_STRONG = new Template(0x6425A, Template.npcType.Grunt, 10);
        GAZAC_STRONG.setCombatStats(59000, 200, 200);
        GAZAC_STRONG.setMobility(140, 30, true);
        GAZAC_STRONG.setWeaponA(0x446EB, 600, 800, 3);
        GAZAC_STRONG.addDropItem(CommonDrops.MATERIAL_FINELUNA_SMALL2);
        GAZAC_STRONG.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL2);
        //GAZAC_STRONG.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL);
        GAZAC_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GAZAC_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GAZAC_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GAZAC_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GAZAC_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GAZAC_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GAZAC_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GAZAC_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GAZAC_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GAZAC_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);

        MARASAI = new Template(0x6424A, Template.npcType.Grunt, 10);
        MARASAI.setCombatStats(80000, 180, 200);
        MARASAI.setMobility(130, 40, true);
        MARASAI.setWeaponA(0x446EB, 1000, 700, 3);
        //MARASAI.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL3);
        MARASAI.addDropItem(CommonDrops.MATERIAL_FINELUNA_SMALL2);
        MARASAI.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MARASAI.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MARASAI.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MARASAI.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MARASAI.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MARASAI.addDropItem(CommonDrops.HG_MMP78);

        MARASAI_STRONG = new Template(0x6424A, Template.npcType.Grunt, 11);
        MARASAI_STRONG.setCombatStats(100000, 200, 200);
        MARASAI_STRONG.setMobility(130, 40, true);
        MARASAI_STRONG.setWeaponA(0x446EB, 1000, 800, 3);
        //MARASAI_STRONG.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL3);
        MARASAI_STRONG.addDropItem(CommonDrops.MATERIAL_FINELUNA_SMALL2);
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

        RICKDIAS = new Template(0x6424F, Template.npcType.Grunt, 11);
        RICKDIAS.setCombatStats(80000, 200, 150);
        RICKDIAS.setMobility(150, 50, true);
        RICKDIAS.setWeaponA(0x446EB, 1000, 800, 3);
        //RICKDIAS.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL3);
        RICKDIAS.addDropItem(CommonDrops.MATERIAL_FINELUNA_SMALL2);
        RICKDIAS.addDropItem(CommonDrops.MATERIAL_JUNKPART_SMALL2);
        RICKDIAS.addDropItem(CommonDrops.GREAT_HEAT_HAWK);
        RICKDIAS.addDropItem(CommonDrops.GIANT_HEAT_HAWK);
        //RICKDIAS.addDropItem(CommonDrops.MATERIAL_GP02A_PARTS);

        RICKDIAS_STRONG = new Template(0x6424F, Template.npcType.Grunt, 12);
        RICKDIAS_STRONG.setCombatStats(100000, 200, 200);
        RICKDIAS_STRONG.setMobility(150, 50, true);
        RICKDIAS_STRONG.setWeaponA(0x446EB, 1200, 1000, 3);
        //RICKDIAS_STRONG.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL3);
        RICKDIAS_STRONG.addDropItem(CommonDrops.MATERIAL_FINELUNA_SMALL2);
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
        //RICKDIAS_STRONG.addDropItem(CommonDrops.MATERIAL_SUPERGUNDAM_PARTS);

        GEARA_DOGA = new Template(0x6425E, Template.npcType.Grunt, 11);
        GEARA_DOGA.setCombatStats(80000, 200, 150);
        GEARA_DOGA.setMobility(150, 50, true);
        GEARA_DOGA.setWeaponA(0x446F8, 700, 800, 2);
        GEARA_DOGA.setShield(0x57E82, 10000, 25);
        //GEARA_DOGA.addDropItem(CommonDrops.MATERIAL_GP02A_PARTS);
        //GEARA_DOGA.addDropItem(CommonDrops.MATERIAL_SUPERGUNDAM_PARTS);
        //GEARA_DOGA.addDropItem(CommonDrops.MATERIAL_MSZ006_PARTS);

        BLUE_GEARA_DOGA = new Template(0x6425E, Template.npcType.Grunt, 12);
        BLUE_GEARA_DOGA.setCombatStats(100000, 200, 150);
        BLUE_GEARA_DOGA.setMobility(150, 50, true);
        BLUE_GEARA_DOGA.setWeaponA(0x446F8, 700, 800, 2);
        BLUE_GEARA_DOGA.setShield(0x57E83, 10000, 25);
        //BLUE_GEARA_DOGA.addDropItem(CommonDrops.MATERIAL_GP02A_PARTS);
        //BLUE_GEARA_DOGA.addDropItem(CommonDrops.MATERIAL_SUPERGUNDAM_PARTS);
        //BLUE_GEARA_DOGA.addDropItem(CommonDrops.MATERIAL_MSZ006_PARTS);
        //BLUE_GEARA_DOGA.addDropItem(CommonDrops.MATERIAL_GUNDARIUM_SMALL3);

    }
}
