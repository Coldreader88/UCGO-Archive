package npc.spawn;

import npc.Template;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde templates for EF boss NPCs.
 *
 */
public class EfBoss {

    /**
     * Standard Midea NPC boss.
     */
    public static Template MIDEA;

    /**
     * Som standard Midea NPC boss med bedre.
     */
    public static Template MIDEA_STRONG;

    /**
     * Standard Midea i grå NPC boss.
     */
    public static Template MIDEA_GRAY;

    /**
     * Strong grå midea NPC boss.
     */
    public static Template MIDEA_GRAY_STRONG;

    /**
     * Big tray NPC boss.
     */
    public static Template BIG_TRAY;

    /**
     * Pegasus class NPC boss.
     */
    public static Template PEGASUS;

    /**
     * Salamis Boss NPC brukt til å representere en ICF.
     */
    public static Template ICF_SALAMIS;

    /**
     * Magellan Boss NPC brukt til å representere en ICF.
     */
    public static Template ICF_MAGELLAN;

    /**
     * Pegasus Boss NPC brukt til å representere en ICF.
     */
    public static Template ICF_PEGASUS;

    static {

        //Standard Midea NPC boss.
        MIDEA = new Template(0xF424A, Template.npcType.Boss, 8);
        MIDEA.setCombatStats(120000, 130, 20);
        MIDEA.setMobility(50, 50, true);
        MIDEA.setWeaponA(0x4465C, 4000, 1400, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        MIDEA.setFlying(true, 400, 800);
        MIDEA.addDropItem(CommonDrops.MATERIAL_FINETCC_BOSS);
        MIDEA.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS);
        MIDEA.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA.addDropItem(CommonDrops.ENGINE_HB_L4A); //Dobbel sjanse for å få L4 TJ.
        MIDEA.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA.addDropItem(CommonDrops.HG_100MM);
        MIDEA.addDropItem(CommonDrops.HG_MPBG);
        MIDEA.addDropItem(CommonDrops.HG_MTC);
        MIDEA.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEN);
        MIDEA.addDropItem(CommonDrops.CLOTHES_TSHIRT_ARTSOH);

        //Strong Midea NPC Boss.
        MIDEA_STRONG = new Template(0xF424A, Template.npcType.Boss, 9);
        MIDEA_STRONG.setCombatStats(150000, 150, 20);
        MIDEA_STRONG.setMobility(50, 50, true);
        MIDEA_STRONG.setWeaponA(0x4465C, 5000, 1600, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        MIDEA_STRONG.setFlying(true, 400, 800);
        MIDEA_STRONG.addDropItem(CommonDrops.MATERIAL_FINETCC_BOSS);
        MIDEA_STRONG.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A); //Trippel sjanse for å få L4 TJ.
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_STRONG.addDropItem(CommonDrops.HG_ZAKU_BAZOOKA);
        MIDEA_STRONG.addDropItem(CommonDrops.HG_100MM);
        MIDEA_STRONG.addDropItem(CommonDrops.HG_MS_TORPEDO);
        MIDEA_STRONG.addDropItem(EFdrops.WEAPON_RX79G_BEAMRIFLE_EX);
        MIDEA_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEN);
        MIDEA_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEON);
        MIDEA_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ARTSOH);

        //Grå midea NPC boss.
        MIDEA_GRAY = new Template(0xF4242, Template.npcType.Boss, 9);
        MIDEA_GRAY.setCombatStats(150000, 130, 20);
        MIDEA_GRAY.setMobility(50, 50, true);
        MIDEA_GRAY.setWeaponA(0x4465C, 4000, 1500, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        MIDEA_GRAY.setFlying(true, 400, 800);
        MIDEA_GRAY.addDropItem(CommonDrops.MATERIAL_FINETCC_BOSS);
        MIDEA_GRAY.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_TANK_L4A);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_TANK_L4B);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_TANK_L4C);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_TANK_L4D);
        MIDEA_GRAY.addDropItem(CommonDrops.ENGINE_TANK_L4E);
        MIDEA_GRAY.addDropItem(CommonDrops.HG_MS_CANNON);
        MIDEA_GRAY.addDropItem(CommonDrops.HG_180MM);
        MIDEA_GRAY.addDropItem(CommonDrops.HG_MPBG);
        MIDEA_GRAY.addDropItem(CommonDrops.HG_MS_TORPEDO);
        MIDEA_GRAY.addDropItem(CommonDrops.HG_MMP78);
        MIDEA_GRAY.addDropItem(CommonDrops.HG_GUNLAUNCHER);
        MIDEA_GRAY.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEN);
        MIDEA_GRAY.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEON);

        //Strong grå Midea NPC boss.
        MIDEA_GRAY_STRONG = new Template(0xF4242, Template.npcType.Boss, 10);
        MIDEA_GRAY_STRONG.setCombatStats(200000, 180, 20);
        MIDEA_GRAY_STRONG.setMobility(50, 50, true);
        MIDEA_GRAY_STRONG.setWeaponA(0x4465C, 6000, 1600, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        MIDEA_GRAY_STRONG.setFlying(true, 400, 800);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.MATERIAL_FINETCC_BOSS);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A); //Dobbel sjanse for å få L4.
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4A);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4B);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4C);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4D);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_HB_L4E);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_TANK_L4A);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_TANK_L4B);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_TANK_L4C);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_TANK_L4D);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.ENGINE_TANK_L4E);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.HG_GIANT_BAZOOKA);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.HG_MS_CANNON);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.HG_MTC);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.HG_MPBG);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.HG_GUNLAUNCHER);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.HG_MS_TORPEDO);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.HG_100MM);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.HG_MMP78);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.HG_MS14_BEAMRIFLE);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEN);
        MIDEA_GRAY_STRONG.addDropItem(CommonDrops.CLOTHES_TSHIRT_ZEON);

        //Big tray NPC boss.
        BIG_TRAY = new Template(1030001, Template.npcType.Boss, 11);
        BIG_TRAY.setCombatStats(300000, 180, 20);
        BIG_TRAY.setMobility(25, 25, true);
        BIG_TRAY.setWeaponA(0x4465C, 6000, 1600, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4A);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4B);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4C);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4D);
        BIG_TRAY.addDropItem(CommonDrops.ENGINE_HB_L4E);
        BIG_TRAY.addDropItem(CommonDrops.HG_MS14_BEAMRIFLE);
        BIG_TRAY.addDropItem(CommonDrops.HG_GIANT_BAZOOKA);
        BIG_TRAY.addDropItem(CommonDrops.HG_MMP78);
        BIG_TRAY.addDropItem(CommonDrops.HG_MPBG);
        BIG_TRAY.addDropItem(CommonDrops.HG_MS_TORPEDO);
        BIG_TRAY.addDropItem(CommonDrops.HG_MTC);
        BIG_TRAY.addDropItem(CommonDrops.MATERIAL_FINETCC_BOSS2);
        BIG_TRAY.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS2);

        //Pegasus class NPC boss.
        PEGASUS = new Template(1030005, Template.npcType.Boss, 12);
        PEGASUS.setCombatStats(400000, 180, 20);
        PEGASUS.setMobility(50, 50, true);
        PEGASUS.setWeaponA(0x4465C, 6000, 1600, 30); //Angriper hvert 15. sekund. 30=15 sekunder for boss NPC.
        PEGASUS.setFlying(true, 200, 400);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4A);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4B);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4C);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4D);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4E);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4A);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4B);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4C);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4D);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4E);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4A);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4B);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4C);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4D);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4E);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4A);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4B);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4C);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4D);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4E);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4A);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4B);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4C);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4D);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4E);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4A);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4B);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4C);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4D);
        PEGASUS.addDropItem(CommonDrops.ENGINE_HB_L4E);
        PEGASUS.addDropItem(CommonDrops.HG_180MM);
        PEGASUS.addDropItem(CommonDrops.HG_MS14_BEAMRIFLE);
        PEGASUS.addDropItem(CommonDrops.HG_79L_BEAMRIFLE);
        PEGASUS.addDropItem(CommonDrops.HG_MPBG);
        PEGASUS.addDropItem(CommonDrops.HG_MMP78);
        PEGASUS.addDropItem(CommonDrops.HG_ZAKU_BAZOOKA);
        PEGASUS.addDropItem(CommonDrops.HG_GIANT_BAZOOKA);
        PEGASUS.addDropItem(CommonDrops.GREAT_HEAT_HAWK);
        PEGASUS.addDropItem(CommonDrops.GIANT_HEAT_HAWK);
        PEGASUS.addDropItem(CommonDrops.MATERIAL_FINETCC_BOSS2);
        PEGASUS.addDropItem(CommonDrops.MATERIAL_JUNKPART_BOSS2);
        PEGASUS.addDropItem(CommonDrops.CLOTHES_UNIFORM_AMURO);
        PEGASUS.addDropItem(CommonDrops.CLOTHES_UNIFORM_CHAR);

        ICF_SALAMIS = new Template(1030015, Template.npcType.Boss, 0);
        ICF_SALAMIS.setCombatStats(1000000, 90, 0);
        ICF_SALAMIS.setMobility(50, 50, true);
        ICF_SALAMIS.setWeaponA(0x44662, 1000, 0, 40);

        ICF_MAGELLAN = new Template(1030016, Template.npcType.Boss, 0);
        ICF_MAGELLAN.setCombatStats(1500000, 90, 0);
        ICF_MAGELLAN.setMobility(50, 50, true);
        ICF_MAGELLAN.setWeaponA(0x44662, 1000, 0, 40);

        ICF_PEGASUS = new Template(1030005, Template.npcType.Boss, 0);
        ICF_PEGASUS.setCombatStats(2000000, 90, 0);
        ICF_PEGASUS.setMobility(50, 50, true);
        ICF_PEGASUS.setWeaponA(0x44662, 1000, 0, 40);
    }
}
