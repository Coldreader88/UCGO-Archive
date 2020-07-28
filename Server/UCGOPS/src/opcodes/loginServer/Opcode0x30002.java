package opcodes.loginServer;

import characters.*;
import containers.*;
import items.Vehicle;
import opcodes.*;
import packetHandler.*;
import players.Player;
import userDB.*;

public class Opcode0x30002 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int character_id = pd.readIntBE();

        CharacterLogin chara = this.loadCharacter(character_id, p.getAccountID());

        SkillManager skills = chara.getSkills();

        //Hvis det er en ucgm konto sett noen skills.
        if (p.getUCGM() != 0xA) {
            chara.setRank(14);
            skills.setCombatSkill(SkillList.MobileSuit.key(), 1300);
            skills.setCombatSkill(SkillList.Critical.key(), 0);
            skills.setCombatSkill(SkillList.NearDodge.key(), 0);
            skills.setCombatSkill(SkillList.CQB.key(), 1300);
            skills.setCombatSkill(SkillList.Tactics.key(), 1300);
            skills.setCombatSkill(SkillList.Shooting.key(), 1300);
            skills.setCombatSkill(SkillList.HandToHandCombat.key(), 1300);
            skills.setCombatSkill(SkillList.Sniping.key(), 1300);
            skills.setCombatSkill(SkillList.Engagement.key(), 1300);
            skills.setCombatSkill(SkillList.BeamCartridgeWeapon.key(), 1300);
            skills.setCombatSkill(SkillList.ShellFiringWeapon.key(), 1300);
            skills.setCombatSkill(SkillList.AMBAC.key(), 1300);
            skills.setCombatSkill(SkillList.FarDodge.key(), 0);
            skills.setCombatSkill(SkillList.Defence.key(), 1300);
            skills.setCombatSkill(SkillList.EmergencyRepair.key(), 1300);
            skills.setCombatSkill(SkillList.WeaponManipulation.key(), 1300);
            skills.setCharacterAttribute(SkillList.Strength.key(), 700);
            skills.setCharacterAttribute(SkillList.Spirit.key(), 700);
            skills.setCharacterAttribute(SkillList.Luck.key(), 700);
            skills.setCraftingSkill(SkillList.MSMAConstruction.key(), 1300);
            skills.setCraftingSkill(SkillList.ArmsConstruction.key(), 1300);
            skills.setCraftingSkill(SkillList.Refinery.key(), 1300);
            skills.setCraftingSkill(SkillList.ClothingManufacturing.key(), 1300);
            skills.setCraftingSkill(SkillList.BattleshipConstruction.key(), 1300);
            skills.setCraftingSkill(SkillList.Mining.key(), 1300);
        }

//HENT UT EVENTUELLE ANDRE DATA HER!
        //Sjekk at vi fikk hentet ut data fra databasen.
        if (chara != null && skills != null) {
            //Da har vi alt vi trenger, sett sammen pakken.
            PacketData svar = new PacketData();

            svar.writeByte((byte) 0x0);
            svar.writeByte((byte) 0x2);
            svar.writeIntBE(p.getAccountID());
            svar.writeIntBE(chara.getCharacterID());
            svar.writeByte((byte) chara.getGender());
            svar.writeByte((byte) 0x0);
            svar.writeByte((byte) chara.getFaction());

            //Her begynner delen som forteller hvordan character ser ut. DETTE ER FEIL! BRUK AV DATA ER UKJENT.
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
            svar.writeIntBE(chara.getTime());

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

            //Richmond/Newman medaljer. 10=1 MEDALJE, 100=2 MEDALJER, 1000=3 MEDALJER, 2000=4 MEDALJER
            svar.writeByte((byte) 0x82);
            svar.writeIntBE(chara.getRichmondMedal());
            svar.writeIntBE(chara.getNewmanMedal());

            svar.writeIntBE(chara.getCharacterID());

            svar.writeByte((byte) 0x0);

            //Skills
            int skillLevel;
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
            svar.writeIntBE(skillLevel); //Fighter

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

            //Klær, utseende. Skin color er ukjent.
            svar.writeByte((byte) 0x94);
            //Sett inn clothing data eller hardcoded klær hvis ny character.

            //Dersom con != null inneholder den weared container for denne spilleren.
            //DVS klærene character har på seg og vi skal IKKE bruke default klær.
            Container con = (HovedContainer) ContainerList.getContainer(character_id + 2);

            if (con != null) {
                //Dummy character. Brukes av clothing klassen.
                CharacterGame gc = new CharacterGame(character_id, null, 0, 0);
                gc.setWearedContainer((HovedContainer) con);
                Clothing clo = new Clothing(gc);
                clo.restoreWearedContainer();
                svar.writeByteArray(clo.getData());
            } else {
                //Character finnes ikke i gameserver, sett default klær.
                if (chara.getFaction() == 1) {
                    svar.writeIntBE(0x0);
                } else {
                    svar.writeIntBE(0x00100000);
                }
                svar.writeIntBE(-1);
                svar.writeIntBE(-1);
                svar.writeIntBE(-1);
                svar.writeIntBE(-1);
                svar.writeIntBE(-1);
            }

            svar.writeIntBE(0x00000000); //Ukjent. Kutter av kroppsdeler.
            svar.writeByte((byte) utseende.getFaceType()); // Face.
            svar.writeShortBE((short) 0x0000);
            svar.writeByte((byte) utseende.getHairStyle()); //Hårstil.
            svar.writeByte((byte) utseende.getHairColor()); //Hårfarge.
            svar.writeByteMultiple((byte) 0x0, 27);

            //Dersom spiller er i MS/vehicle skriv ut info om det.
            GameCharacter c = ManageCharacters.getGameCharacter(character_id);

            int activeVehicle = c.getActiveVehicle();
            Container avCon = ContainerList.getContainer(activeVehicle);

            if (activeVehicle != 0 && avCon instanceof ItemContainer && ((ItemContainer) avCon).getItem() instanceof Vehicle) {
                //Spiller er i MS/vehicle. Skriv ut info om det.
                ItemContainer avIC = (ItemContainer) avCon;
                Vehicle v = (Vehicle) avIC.getItem();

                svar.writeIntBE(character_id);
                svar.writeIntBE(v.getID());

                //Skriv ut hvilket våpen og shield den har.
                svar.writeByte((byte) 0x82);

                if (v.getEquippedItem(0) != null) {
                    svar.writeIntBE(v.getEquippedItem(0).getItem().getID());
                } else {
                    svar.writeIntBE(0);
                }

                if (v.getEquippedItem(1) != null) {
                    svar.writeIntBE(v.getEquippedItem(1).getItem().getID());
                } else {
                    svar.writeIntBE(0);
                }

                svar.writeByte((byte) 0x82);
                svar.writeByte((byte) 0x0);
                svar.writeByte((byte) 0x0);
            } else {
                //Spiller er ikke i MS/vehicle.

                svar.writeIntBE(-1);
                svar.writeIntBE(-1);

                svar.writeByte((byte) 0x82);
                svar.writeIntBE(0x0);
                svar.writeIntBE(0x0);

                svar.writeByte((byte) 0x82);
                svar.writeByte((byte) 0x0);
                svar.writeByte((byte) 0x0);
            }

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

            svar.writeIntBE(-1);
            svar.writeIntBE(-1);
            svar.writeShortBE((short) -1);
            svar.writeIntBE(0);
            svar.writeIntBE(0);
            svar.writeIntBE(0);

            Packet svar_pakke = new Packet(0x38002, svar.getData());

            p.sendPacket(svar_pakke);
        } else {
            //Det var et problem.
            PacketData svar = new PacketData();

            svar.writeIntBE(-1);

            Packet svar_pakke = new Packet(0x380002, svar.getData());

            p.sendPacket(svar_pakke);
        }

    }

    /**
     * Henter informasjon om character og returnerer det som et CharacterLogin
     * objekt.
     *
     * @param character_id Character ID til character vi skal returnere info om.
     * @param account_id Konto character tilhører.
     *
     * @return Character informasjon som login serveren trenger, eller NULL hvis
     * feil.
     */
    private CharacterLogin loadCharacter(int character_id, int account_id) {

        CharacterLogin character = null;

        GameCharacter c = ManageCharacters.getGameCharacter(character_id);

        //Sjekk at character tilhører oppgitt konto ID.
        if (account_id == c.getKonto().getAccountID()) {
            character = new CharacterLogin(character_id, c.getNavn(), c.getGender(), c.getFaction(), c.getCreateTime());
            character.setAppearance(c.getAppearance());
            character.setPosisjon(c.getPosition());
            character.setRank(c.getRank());
            character.setSkills(c.getSkills());
            character.setScore(c.getScore());
            character.setLosses(c.getLosses());
            character.setNewmanMedal(c.getNewman());
            character.setRichmondMedal(c.getRichmond());
        }

        return character;
    }

}
