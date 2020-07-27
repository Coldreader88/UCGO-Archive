package npc.spawn;

import npc.Template;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde templates for Zeon boss NPCs.
 *
 */
public class ZeonBoss {

    /**
     * Standard Fat Uncle NPC Boss.
     */
    public static Template FATUNCLE;

    /**
     * Som standard Fat Uncle med bedre.
     */
    public static Template FATUNCLE_STRONG;

    /**
     * Standard NPC GAW Boss
     */
    public static Template GAW;

    /**
     * Som standard GAW Boss men bedre.
     */
    public static Template GAW_STRONG;

    /**
     * Gallop NPC boss.
     */
    public static Template GALLOP;

    /**
     * Dobday NPC boss.
     */
    public static Template DOBDAY;

    /**
     * Musai som brukes i PVP område istedenfor ICF.
     */
    public static Template ICF_MUSAI;

    /**
     * Chibe som brukes i PVP område istedenfor ICF.
     */
    public static Template ICF_CHIBE;

    /**
     * Zanzibar som brukes i PVP område istedenfor ICF.
     */
    public static Template ICF_ZANZIBAR;

    static {

        //Standard Fat Uncle NPC Boss.
        FATUNCLE = new Template(0xFB77E, Template.npcType.Boss, 8);
        FATUNCLE.setCombatStats(120000, 130, 20);
        FATUNCLE.setMobility(50, 50, true);
        FATUNCLE.setWeaponA(0x4465C, 4000, 1600, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        FATUNCLE.setFlying(true, 400, 800);
        FATUNCLE.addDropItem(CommonDrops.MATERIAL_FINELUNA_BOSS);
        FATUNCLE.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS);
        FATUNCLE.addDropItem(CommonDrops.ENGINE_HB_L4A);
        FATUNCLE.addDropItem(CommonDrops.ENGINE_HB_L4B);
        FATUNCLE.addDropItem(CommonDrops.ENGINE_HB_L4C);
        FATUNCLE.addDropItem(CommonDrops.ENGINE_HB_L4D);
        FATUNCLE.addDropItem(CommonDrops.ENGINE_HB_L4E);
        FATUNCLE.addDropItem(CommonDrops.ENGINE_HB_L4A); //Dobbel sjanse for å få L4 TJ.
        FATUNCLE.addDropItem(CommonDrops.ENGINE_HB_L4B);
        FATUNCLE.addDropItem(CommonDrops.ENGINE_HB_L4C);
        FATUNCLE.addDropItem(CommonDrops.ENGINE_HB_L4D);
        FATUNCLE.addDropItem(CommonDrops.ENGINE_HB_L4E);
        FATUNCLE.addDropItem(CommonDrops.HG_100MM);
        FATUNCLE.addDropItem(CommonDrops.HG_MS_BEAM_CANNON);
        FATUNCLE.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEN);
        FATUNCLE.addDropItem(CommonDrops.CLOTHES_TSHIRT_ARTSOH);
        FATUNCLE.addDropItem(CommonDrops.CLOTHES_COMBAT_HELMET_VISOR);

        //Strong Fat Uncle NPC Boss.
        FATUNCLE_STRONG = new Template(0xFB77E, Template.npcType.Boss, 9);
        FATUNCLE_STRONG.setCombatStats(150000, 150, 20);
        FATUNCLE_STRONG.setMobility(50, 50, true);
        FATUNCLE_STRONG.setWeaponA(0x4465C, 5000, 1600, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        FATUNCLE_STRONG.setFlying(true, 400, 800);
        FATUNCLE_STRONG.addDropItem(CommonDrops.MATERIAL_FINELUNA_BOSS);
        FATUNCLE_STRONG.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A); //Trippel sjanse for å få L4 TJ.
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        FATUNCLE_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        FATUNCLE_STRONG.addDropItem(CommonDrops.HG_LONGRANGE_RIFLE);
        FATUNCLE_STRONG.addDropItem(CommonDrops.HG_ZAKU_BAZOOKA);
        FATUNCLE_STRONG.addDropItem(CommonDrops.HG_100MM);
        FATUNCLE_STRONG.addDropItem(CommonDrops.HG_MS_BEAM_CANNON);
        FATUNCLE_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEN);
        FATUNCLE_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_EF);
        FATUNCLE_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ARTSOH);
        FATUNCLE_STRONG.addDropItem(CommonDrops.CLOTHES_COMBAT_HELMET_VISOR);

        //Standard NPC Gaw Boss.
        GAW = new Template(1000001, Template.npcType.Boss, 9);
        GAW.setCombatStats(150000, 130, 20);
        GAW.setMobility(50, 50, true);
        GAW.setWeaponA(0x4465C, 4500, 1600, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        GAW.setFlying(true, 400, 800);
        GAW.addDropItem(CommonDrops.MATERIAL_FINELUNA_BOSS);
        GAW.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS);
        GAW.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GAW.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GAW.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GAW.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GAW.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GAW.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GAW.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GAW.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GAW.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GAW.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GAW.addDropItem(CommonDrops.ENGINE_TANK_L4A);
        GAW.addDropItem(CommonDrops.ENGINE_TANK_L4B);
        GAW.addDropItem(CommonDrops.ENGINE_TANK_L4C);
        GAW.addDropItem(CommonDrops.ENGINE_TANK_L4D);
        GAW.addDropItem(CommonDrops.ENGINE_TANK_L4E);
        GAW.addDropItem(CommonDrops.HG_MS_BEAMGUN);
        GAW.addDropItem(CommonDrops.HG_MS_CANNON);
        GAW.addDropItem(CommonDrops.HG_180MM);
        GAW.addDropItem(CommonDrops.HG_MPBG);
        GAW.addDropItem(CommonDrops.HG_GUNLAUNCHER);
        GAW.addDropItem(CommonDrops.HG_79L_BEAMRIFLE);
        GAW.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEN);
        GAW.addDropItem(CommonDrops.CLOTHES_TSHIRT_EF);

        //Strong NPC Gaw Boss.
        GAW_STRONG = new Template(1000001, Template.npcType.Boss, 10);
        GAW_STRONG.setCombatStats(200000, 180, 20);
        GAW_STRONG.setMobility(50, 50, true);
        GAW_STRONG.setWeaponA(0x4465C, 6000, 1600, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        GAW_STRONG.setFlying(true, 400, 800);
        GAW_STRONG.addDropItem(CommonDrops.MATERIAL_FINELUNA_BOSS);
        GAW_STRONG.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A); //Dobbel sjanse for å få L4.
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_TANK_L4A);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_TANK_L4B);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_TANK_L4C);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_TANK_L4D);
        GAW_STRONG.addDropItem(CommonDrops.ENGINE_TANK_L4E);
        GAW_STRONG.addDropItem(CommonDrops.HG_MS_BEAMGUN);
        GAW_STRONG.addDropItem(CommonDrops.HG_MS_CANNON);
        GAW_STRONG.addDropItem(CommonDrops.HG_180MM);
        GAW_STRONG.addDropItem(CommonDrops.HG_MPBG);
        GAW_STRONG.addDropItem(CommonDrops.HG_GUNLAUNCHER);
        GAW_STRONG.addDropItem(CommonDrops.HG_79L_BEAMRIFLE);
        GAW_STRONG.addDropItem(CommonDrops.HG_100MM);
        GAW_STRONG.addDropItem(CommonDrops.HG_LONGRANGE_RIFLE);
        GAW_STRONG.addDropItem(CommonDrops.HG_BLASH);
        GAW_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEN);
        GAW_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_EF);

        //Gallop NPC boss.
        GALLOP = new Template(0xFB773, Template.npcType.Boss, 11);
        GALLOP.setCombatStats(300000, 180, 20);
        GALLOP.setMobility(40, 40, true);
        GALLOP.setWeaponA(0x4465C, 6000, 1600, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4A);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4B);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4C);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4D);
        GALLOP.addDropItem(CommonDrops.ENGINE_HB_L4E);
        GALLOP.addDropItem(CommonDrops.HG_180MM);
        GALLOP.addDropItem(CommonDrops.HG_79L_BEAMRIFLE);
        GALLOP.addDropItem(CommonDrops.HG_BLASH);
        GALLOP.addDropItem(CommonDrops.HG_100MM);
        GALLOP.addDropItem(CommonDrops.HG_MPBG);
        GALLOP.addDropItem(CommonDrops.MATERIAL_FINELUNA_BOSS2);
        GALLOP.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS2);

        DOBDAY = new Template(1030004, Template.npcType.Boss, 12);
        DOBDAY.setCombatStats(400000, 180, 20);
        DOBDAY.setMobility(30, 30, true);
        DOBDAY.setWeaponA(0x4465C, 6000, 1600, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        DOBDAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        DOBDAY.addDropItem(CommonDrops.HG_100MM);
        DOBDAY.addDropItem(CommonDrops.HG_BLASH);
        DOBDAY.addDropItem(CommonDrops.HG_MS_BEAMGUN);
        DOBDAY.addDropItem(CommonDrops.HG_79L_BEAMRIFLE);
        DOBDAY.addDropItem(CommonDrops.HG_LONGRANGE_RIFLE);
        DOBDAY.addDropItem(CommonDrops.HG_MS_BEAM_CANNON);
        DOBDAY.addDropItem(CommonDrops.MATERIAL_FINELUNA_BOSS2);
        DOBDAY.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS2);
        DOBDAY.addDropItem(CommonDrops.CLOTHES_UNIFORM_AMURO);
        DOBDAY.addDropItem(CommonDrops.CLOTHES_UNIFORM_CHAR);
        DOBDAY.addDropItem(CommonDrops.GIANT_HEAT_HAWK);
        DOBDAY.addDropItem(CommonDrops.GREAT_HEAT_HAWK);

        ICF_MUSAI = new Template(1030017, Template.npcType.Boss, 0);
        ICF_MUSAI.setCombatStats(1000000, 90, 0);
        ICF_MUSAI.setMobility(50, 50, true);
        ICF_MUSAI.setWeaponA(0x44662, 1000, 0, 40);

        ICF_CHIBE = new Template(1030018, Template.npcType.Boss, 0);
        ICF_CHIBE.setCombatStats(1500000, 90, 0);
        ICF_CHIBE.setMobility(50, 50, true);
        ICF_CHIBE.setWeaponA(0x44662, 1000, 0, 40);

        ICF_ZANZIBAR = new Template(1030006, Template.npcType.Boss, 0);
        ICF_ZANZIBAR.setCombatStats(2000000, 90, 0);
        ICF_ZANZIBAR.setMobility(50, 50, true);
        ICF_ZANZIBAR.setWeaponA(0x44662, 1000, 0, 40);
    }
}
