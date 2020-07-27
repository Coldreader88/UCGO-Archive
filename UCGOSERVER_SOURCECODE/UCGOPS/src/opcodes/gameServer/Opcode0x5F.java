package opcodes.gameServer;

import characters.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x5F implements Opcode {

    @Override
    public void execute(Player p, Packet pakke) {

        PlayerGame player = (PlayerGame) p;
        CharacterGame chara = player.getCharacter();

        PacketData svar = new PacketData();

        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x2);
        svar.writeIntBE(p.getAccountID());
        svar.writeIntBE(chara.getCharacterID());
        svar.writeByte((byte) chara.getGender());
        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) chara.getFaction());

        //Her begynner delen som forteller hvordan character ser ut.
        Appearance utseende = chara.getAppearance();
        svar.writeByte((byte) 0x8F);
        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x0);
        svar.writeIntBE(-1);
        svar.writeIntBE(-1);
        svar.writeIntBE(-1);
        svar.writeByte((byte) -1);
        svar.writeByte((byte) -1);
        svar.writeByteMultiple((byte) 0, 3);
        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x0);
        svar.writeByteMultiple((byte) 0, 26);
        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x0);
        svar.writeByteMultiple((byte) 0, 9);

        svar.writeIntBE(chara.getRank()); //Rank

        //Her skal navnet skrives.
        String navn = chara.getNavn();
        svar.writeByte((byte) (0x80 | navn.length()));
        svar.writeStringUTF16LE(navn);

        //Når character ble opprettet.
        svar.writeIntBE(0x0);

        //10 INTs.  Score/loss info.
        svar.writeByte((byte) 0x8A);
        svar.writeIntBE(chara.getScore()); //Score
        svar.writeIntBE(chara.getLosses()); //Losses
        svar.writeIntBE(0x0); //Score
        svar.writeIntBE(0x0); //Losses
        svar.writeIntBE(0x0); //Criminal
        svar.writeIntBE(0x0); //????
        svar.writeIntBE(0x0);//Score
        svar.writeIntBE(0x0); //Losses
        svar.writeIntBE(0x0); //Score
        svar.writeIntBE(0x0);  //Losses

        svar.writeByte((byte) 0x80);

        //Denne delen inneholder "containere".
        svar.writeByte((byte) 0x8B);
        //Backpack container.	
        svar.writeIntBE(chara.getCharacterID() + 1);
        svar.writeIntBE(0x14);
        //Weared container.
        svar.writeIntBE(chara.getCharacterID() + 2);
        svar.writeIntBE(0x14);
        //Bank container.
        svar.writeIntBE(chara.getCharacterID() + 3);
        svar.writeIntBE(0x14);
        //Money container.
        svar.writeIntBE(chara.getCharacterID() + 4);
        svar.writeIntBE(0x13);
        //Hangar container.
        svar.writeIntBE(chara.getCharacterID() + 5);
        svar.writeIntBE(0x14);
        //Selfstorage container.
        svar.writeIntBE(chara.getCharacterID() + 6);
        svar.writeIntBE(0x14);
        //House container.
        svar.writeIntBE(chara.getCharacterID() + 7);
        svar.writeIntBE(0x14);
        //Productive container.
        svar.writeIntBE(chara.getCharacterID() + 8);
        svar.writeIntBE(0x14);
        //Realestate container.
        svar.writeIntBE(chara.getCharacterID() + 9);
        svar.writeIntBE(0x14);
        //Swappack container.
        svar.writeIntBE(chara.getCharacterID() + 0xA);
        svar.writeIntBE(0x14);
        //Credit container.
        svar.writeIntBE(chara.getCharacterID() + 0xB);
        svar.writeIntBE(0x13);

        //Richmond/Newman medaljer.
        svar.writeByte((byte) 0x82);
        svar.writeIntBE(chara.getRichmondMedal());
        svar.writeIntBE(chara.getNewmanMedal());

        svar.writeIntBE(chara.getCharacterID());

        svar.writeByte((byte) 0x0);

        //Skills
        int skillLevel;
        SkillManager skills = chara.getSkills();

        svar.writeByte((byte) 0x95);
        skillLevel = skills.getCombatSkill(SkillList.MobileSuit.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.MobileSuit.key()) << 28;
        svar.writeIntBE(skillLevel); //Mobile suit

        skillLevel = skills.getCombatSkill(SkillList.Critical.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.Critical.key()) << 28;
        svar.writeIntBE(skillLevel); //Mobile armour

        svar.writeIntBE(0x0);

        skillLevel = skills.getCombatSkill(SkillList.NearDodge.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.NearDodge.key()) << 28;
        svar.writeIntBE(0/*skillLevel*/); //Fighter
//FIGHTER MIDLERTID SATT TIL 0 PGA BUG.

        skillLevel = skills.getCombatSkill(SkillList.RangedEngagement.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.RangedEngagement.key()) << 28;
        svar.writeIntBE(skillLevel); //Space engagement

        skillLevel = skills.getCombatSkill(SkillList.Engagement.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.Engagement.key()) << 28;
        svar.writeIntBE(skillLevel); //Ground engagement

        svar.writeIntBE(0x0);

        skillLevel = skills.getCombatSkill(SkillList.NearEngagement.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.NearEngagement.key()) << 28;
        svar.writeIntBE(skillLevel); //Air engagement

        skillLevel = skills.getCombatSkill(SkillList.BeamCartridgeWeapon.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.BeamCartridgeWeapon.key()) << 28;
        svar.writeIntBE(skillLevel); //Beam cartridge weapon

        skillLevel = skills.getCombatSkill(SkillList.ShellFiringWeapon.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.ShellFiringWeapon.key()) << 28;
        svar.writeIntBE(skillLevel); //Shell firing weapon

        svar.writeIntBE(0x0);

        skillLevel = skills.getCombatSkill(SkillList.WeaponManipulation.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.WeaponManipulation.key()) << 28;
        svar.writeIntBE(skillLevel); //Weapon manupilation

        skillLevel = skills.getCombatSkill(SkillList.Shooting.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.Shooting.key()) << 28;
        svar.writeIntBE(skillLevel); //Shooting

        skillLevel = skills.getCombatSkill(SkillList.Sniping.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.Sniping.key()) << 28;
        svar.writeIntBE(skillLevel); //Sniping

        skillLevel = skills.getCombatSkill(SkillList.CQB.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.CQB.key()) << 28;
        svar.writeIntBE(skillLevel); //CQB

        skillLevel = skills.getCombatSkill(SkillList.HandToHandCombat.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.HandToHandCombat.key()) << 28;
        svar.writeIntBE(skillLevel);//Hand to hand

        skillLevel = skills.getCombatSkill(SkillList.Tactics.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.Tactics.key()) << 28;
        svar.writeIntBE(skillLevel);//Tactics

        skillLevel = skills.getCombatSkill(SkillList.AMBAC.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.AMBAC.key()) << 28;
        svar.writeIntBE(skillLevel);//AMBAC

        skillLevel = skills.getCombatSkill(SkillList.Defence.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.Defence.key()) << 28;
        svar.writeIntBE(skillLevel);//Defence

        skillLevel = skills.getCombatSkill(SkillList.FarDodge.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.FarDodge.key()) << 28;
        svar.writeIntBE(skillLevel);//Evasion

        skillLevel = skills.getCombatSkill(SkillList.EmergencyRepair.key());
        skillLevel += skills.getCombatSkillRetning(SkillList.EmergencyRepair.key()) << 28;
        svar.writeIntBE(skillLevel);//Emergency repair

        svar.writeIntBE(chara.getCharacterID());

        svar.writeByte((byte) 0x1);

        //7 INTs, skills.
        svar.writeByte((byte) 0x87);

        skillLevel = skills.getCraftingSkill(SkillList.Mining.key());
        skillLevel += skills.getCraftingSkillRetning(SkillList.Mining.key()) << 28;
        svar.writeIntBE(skillLevel); //Mining

        skillLevel = skills.getCraftingSkill(SkillList.Refinery.key());
        skillLevel += skills.getCraftingSkillRetning(SkillList.Refinery.key()) << 28;
        svar.writeIntBE(skillLevel); //Refinery

        skillLevel = skills.getCraftingSkill(SkillList.MSMAConstruction.key());
        skillLevel += skills.getCraftingSkillRetning(SkillList.MSMAConstruction.key()) << 28;
        svar.writeIntBE(skillLevel); //MS/MA construction

        skillLevel = skills.getCraftingSkill(SkillList.BattleshipConstruction.key());
        skillLevel += skills.getCraftingSkillRetning(SkillList.BattleshipConstruction.key()) << 28;
        svar.writeIntBE(skillLevel); //Battleship construction

        skillLevel = skills.getCraftingSkill(SkillList.ArmsConstruction.key());
        skillLevel += skills.getCraftingSkillRetning(SkillList.ArmsConstruction.key()) << 28;
        svar.writeIntBE(skillLevel); //Arms construction
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        svar.writeIntBE(chara.getCharacterID());

        svar.writeByte((byte) 0x2);

        //10 INTs. Skills.
        svar.writeByte((byte) 0x8A);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        skillLevel = skills.getCraftingSkill(SkillList.ClothingManufacturing.key());
        skillLevel += skills.getCraftingSkillRetning(SkillList.ClothingManufacturing.key()) << 28;
        svar.writeIntBE(skillLevel); //Clothing manufacturing.

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        svar.writeIntBE(chara.getCharacterID());

        svar.writeByte((byte) 0x3); //Merkelig. Settes til 0 eller 1 så vil klienten slå av flere skills.

        //5 INTs, ikke brukt?
        svar.writeByte((byte) 0x85);
        for (int c = 0; c < 5; c++) {
            svar.writeIntBE(0x0);
        }

        svar.writeIntBE(chara.getCharacterID());

        //Her kommer strength, spirit, luck.
        int strength = skills.getCharacterAttribute(SkillList.Strength.key());
        int spirit = skills.getCharacterAttribute(SkillList.Spirit.key());
        int luck = skills.getCharacterAttribute(SkillList.Luck.key());
        svar.writeByte((byte) 0x83);
        svar.writeIntBE(strength + (skills.getCharacterAttributeRetning(SkillList.Strength.key()) << 28));
        svar.writeIntBE(spirit + (skills.getCharacterAttributeRetning(SkillList.Spirit.key()) << 28));
        svar.writeIntBE(luck + (skills.getCharacterAttributeRetning(SkillList.Luck.key()) << 28));
        svar.writeIntBE((strength & 0x0FFFFFFF) + (spirit & 0x0FFFFFFF) + (luck & 0x0FFFFFFF));

        svar.writeIntBE(chara.getCharacterID());

        svar.writeByte((byte) 0x0);

        //ZONE? Sannsynligvis ikke.
        svar.writeByte((byte) 0x1);

        //Trolig gender, men ser ikke ut til å ha effekt i spillet.
        svar.writeByte((byte) chara.getGender());

        //Klær, utseende.
        svar.writeByte((byte) 0x94);
        svar.writeByteArray(chara.getClothing().getData());
        svar.writeIntBE(0x00000000); //Ukjent. Kutter av kroppsdeler.
        svar.writeByte((byte) utseende.getFaceType()); // Face.
        svar.writeShortBE((short) 0x0000);
        svar.writeByte((byte) utseende.getHairStyle()); //Hårstil.
        svar.writeByte((byte) utseende.getHairColor()); //Hårfarge.
        svar.writeByteMultiple((byte) 0x0, 35);

        //2 INTs, ikke brukt?
        svar.writeByte((byte) 0x82);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        //2 Byte, ikke brukt?
        svar.writeByte((byte) 0x82);
        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x0);

        //Null?
        svar.writeByte((byte) 0x80);

        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x0);

        svar.writeIntBE(chara.getCharacterID());

        svar.writeByte((byte) 0x0);

        svar.writeByte((byte) chara.getPosisjon().getZone()); //Zone

        svar.writeIntBE(chara.getPosisjon().getX()); //X posisjon.
        svar.writeIntBE(chara.getPosisjon().getY()); //Y posisjon.
        svar.writeIntBE(chara.getPosisjon().getZ()); //Z posisjon.

        svar.writeShortBE((short) chara.getPosisjon().getTilt());
        svar.writeShortBE((short) chara.getPosisjon().getRoll());
        svar.writeShortBE((short) chara.getPosisjon().getDirection());

        //COPY PASTE FRA PAKKE LOG.
        int[] copypaste = {0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0, 0x0, 0x0};
        byte[] cp = new byte[copypaste.length];
        for (int c = 0; c < copypaste.length; c++) {
            cp[c] = (byte) copypaste[c];
        }

        svar.writeByteArray(cp);

        Packet svar_pakke = new Packet(0x805F, svar.getData());

        p.sendPacket(svar_pakke);

    }

}
