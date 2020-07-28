package opcodes.gameServer;

import characters.*;
import containers.*;
import gameData.StatManager;
import gameData.StatWeapon;
import gameServer.Combat;
import items.*;
import npc.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;
import validItems.*;

public class Opcode0x0F implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());
        PlayerGame player = (PlayerGame) p;

        int attacker_id = pd.readIntBE();
        int target_id = pd.readIntBE();

        pd.readIntBE();
        pd.readByte();

        int avstand = pd.readIntBE();

        short combo = pd.readShortBE(); //-1=Vanlig angrep. 0x0,0x1=CQB combo, 0x2,0x3=H2H combo.

        //Gi bonus til attack dersom combo. 0x0,0x2=2xCombo  0x1,0x3=3xcombo
        int comboBonus = 0;

        if (combo == 0x0 || combo == 0x2) {
            comboBonus = 50;
        } else if (combo == 0x1 || combo == 0x3) {
            comboBonus = 100;
        }

        //Sjekk at character ID er gyldig.
        if (player.getCharacter().getCharacterID() != attacker_id) {
            return;
        }

        //Sjekk hvor lenge siden sist det er at spiller utførte et angrep.
        //Dersom combo skal det være minst 3 sekunder siden forrige angrep eller 0.9 sekunder.
        if (player.getCharacter().attackDeltaTime() < 900 || (combo != -1 && player.getCharacter().attackDeltaTime() < 3000)) {
            player.getCharacter().setAttackTimer();
            this.sendTom800F(player.getCharacter(), target_id);
            return;
        } else {
            player.getCharacter().setAttackTimer();
        }

        //Sjekk at spiller er i et vehicle og har våpen utstyrt.
        Vehicle v = player.getCharacter().getVehicle();
        if (v == null) {
            this.sendTom800F(player.getCharacter(), target_id);
            return;
        }

        //Sjekk at spiller har et våpen utstyrt.
        if (v.getEquippedItem(v.getActiveSlot()) == null) {
            System.out.println("Ocpode 0x0F: Error, player attempted to attack without a weapon.");
            return;
        }

        //Sjekk at våpen ikke har 100% damage.
        ItemContainer ic = v.getEquippedItem(v.getActiveSlot());
        Weapon w = (Weapon) ic.getItem();
        if (w.isDestroyed()) {
            sendTom800F(player.getCharacter(), target_id);
            return;
        }

        //Dersom combo sjekk at utstyrt våpen er CQB eller H2H.
        if (combo > 0) {
            GameItem gi = ItemList.getItem(w.getID());
            if (gi.getItemSubType() != ItemSubType.CQB && gi.getItemSubType() != ItemSubType.H2H) {
                //Forsøk på combo med våpen som ikke er CQB eller H2H.
                //Trenger ikke å være cheating!
                return;
            }
        }

        //Sjekk at avstanden til målet er gyldig.
        boolean gyldigAvstand = this.checkDistance(player.getCharacter(), avstand);

        //Hent ID nummer for angrepet.
        int attack_id = gameServer.Combat.getAttackID();

        //Få resultat av angrepet.
        gameServer.CombatResult res = null;

        //Sjekk om målet et en annen spiller.
        CharacterGame target_player = gameServer.MultiClient.getCharacter(target_id);

        if (target_player != null) {
            //PVP.
            res = gameServer.Combat.attack(player.getCharacter(), target_player);

            //Sjekk at vi fikk et resultat tilbake.
            if (res == null) {
                this.sendTom800F(player.getCharacter(), target_id);
                return;
            }

            //Dersom ugyldig avstand til målet annuler resultatet av angrepet.
            if (!gyldigAvstand) {
                res.setDamage(0);
                res.setHit(false);
                res.setDodged(false);
                res.setShieldBlock(false);
                res.setMoney(0);
            }

            //Hvis spiller utførte combo gi bonus.
            if (combo != -1) {
                int atk = res.getDamage();
                atk += (atk * comboBonus) / 100;
                res.setDamage(atk);
            }

            //Sjekk resultat av angrepet.
            if (!res.isDodged() && res.isHit()) {
                //Vi traff målet påfør skade.
                this.oppdaterStatus(res, target_player);
            }

            //Send 0x8036 til alle spillere i nærheten.
            Packet svar8036_pakke = this.opcode8036(player.getCharacter(), target_player, res, attack_id, combo);
            gameServer.MultiClient.broadcastPacket(svar8036_pakke, player.getCharacter());

            //Send 0x800F til angriperen og målet (hvis PVP).
            Packet svar800F_pakke = this.opcode800F(player.getCharacter(), target_player, res, attack_id, combo);

            p.sendPacket(svar800F_pakke);
            //Send 0x800F til målet kun hvis vi traff.
            if (res.isHit()) {
                gameServer.MultiClient.sendPacketToPlayer(svar800F_pakke, target_id);
            }

            //Criminal sjekk.
            if (res.isCriminal()) {

                //Sjekk om målet ble ødelagt eller kun truffet.
                if (this.machineDamage(target_player) >= 100) {
                    //Målet ble ødelagt.
                    gameServer.Combat.criminal(player, true);
                } else {
                    gameServer.Combat.criminal(player, false);
                }
            }

            //Sjekk om målet sin MS/vehicle har 100% skade.
            if (this.machineDamage(target_player) >= 100) {
                boolean r = destroyMS(target_player);
                //Oppdater score/loss.
                if (r) {
                    player.getCharacter().setScore(player.getCharacter().getScore() + 1);
                    target_player.setLosses(target_player.getLosses() + 1);

                    //Oppdater rank poeng.
                    player.getCharacter().gainRank(target_player.getRank());
                    target_player.loseRank(100);
                }

            } else if (target_player.getVehicle() != null && target_player.getVehicle().getEquippedItem(1) != null) {
                //Sjekk om målet har et shield utstyrt som har 100% skade.
                //OK med typecast her, item i slot #1 skal ALLTID være et shield uansett.
                Weapon shield = (Weapon) target_player.getVehicle().getEquippedItem(1).getItem();
                //Hvis 100% skade fjern det.
                if (shield != null) {
                    if (shield.getHitpoints() <= 0) {
                        target_player.getVehicle().destroyShield();
                        target_player.appearanceChange();
                    }
                }
            }
        } else {
            //PVE
            //Hent ut NPC som ble angrepet.
            NPC t = LocalManager.getNpc(target_id);
            if (t == null) {
                t = GlobalManager.getNpc(target_id);
            }

            if (t == null) {
                //NPC finnes verken i local eller global manager, ugyldig ID.
                System.out.println("Opcode 0xF: Error, illegal NPC ID: " + target_id);
                this.sendTom800F(player.getCharacter(), target_id);
                return;
            }

            //Sjekk at NPC er MS/vehicle, skal ikke være mulig å angripe NPC i human form.
            if (!(t instanceof NPCvehicle)) {
                System.out.println("Opcode 0xF: Error, NPC is not MS/vehicle. NPC ID: " + target_id);
                return;
            }

            //OK, NPC er gyldig.
            NPCvehicle targetNpc = (NPCvehicle) t;

            //Sjekk at NPC ikke har 100% damage. Isåfall send tom 0x800F tilbake.
            if (targetNpc.getMachineDamage() == 100) {
                this.sendTom800F(player.getCharacter(), target_id);
                return;
            }

            //OK, beregn resultat av angrepet.
            res = Combat.attack(player.getCharacter(), targetNpc);

            //Sjekk at vi fikk et gyldig resultat tilbake.
            if (res == null) {
                this.sendTom800F(player.getCharacter(), target_id);
                return;
            }

            //Hvis spiller utførte combo gi bonus.
            if (combo != -1) {
                int atk = res.getDamage();
                atk += (atk * comboBonus) / 100;
                res.setDamage(atk);
            }

            //Dersom vi traff NPC oppdater status for NPC.
            if (res.isHit() && !res.isDodged()) {
                this.PVEoppdaterStatus(res, targetNpc);
            }

            //Send opcode 0x800F til spilleren.
            p.sendPacket(this.PVEopcode800F(player.getCharacter(), targetNpc, res, attack_id, combo));

            //Send opcode 0x8036 til spilleren og alle i nærheten.
            Packet svar8036 = this.PVEopcode8036(player.getCharacter(), targetNpc, res, attack_id, combo);
            gameServer.MultiClient.broadcastPacket(svar8036, player.getCharacter());

            //Hvis NPC fikk 100% damage oppdater score.
            if (targetNpc.getMachineDamage() == 100) {

                //Sjekk om NPC var i et vehicle som skal resultere i et score.
                GameItem i = ItemList.getItem(targetNpc.getTemplate().getVehicleID());

                if (i.getItemSubType() == ItemSubType.Battleship || i.getItemSubType() == ItemSubType.MS || i.getItemSubType() == ItemSubType.MA
                        || i.getItemSubType() == ItemSubType.Fighter || i.getItemSubType() == ItemSubType.Tank) {

                    player.getCharacter().setScore(player.getCharacter().getScore() + 1);

                    //Oppdater rank.
                    player.getCharacter().gainRank(targetNpc.getRank() / 2);
                }

                //La NPC slippe items hvis den skal.
                targetNpc.dropWreckageContainer(player.getCharacter());
            }

            //Criminal sjekk.
            if (res.isCriminal()) {

                //Sjekk om målet ble ødelagt eller kun truffet.
                if (targetNpc.getMachineDamage() == 100) {
                    //Målet ble ødelagt.
                    gameServer.Combat.criminal(player, true);
                } else {
                    gameServer.Combat.criminal(player, false);
                }
            }

        }

        //Oppdater pengene i banken.
        try {
            player.getCharacter().getCreditContainer().settAntall(player.getCharacter().getCreditContainer().getAntall() + res.getMoney());
        } catch (Exception e) {
        }

    }

    /**
     * Oppdater status for spilleren som blir angrepet. NB! Metoden fjerner ikke
     * MS/shield hvis ødelagt. Oppdaterer kun hitpoints.
     *
     * Kun for PVP.
     *
     * @param res Resultat fra angrepet.
     *
     * @param target_player Spilleren som ble angrepet.
     */
    private void oppdaterStatus(gameServer.CombatResult res, CharacterGame target_player) {

        //Sjekk om spilleren er i ms/vehicle eller taxi/transport.
        if (target_player.getVehicle() != null) {
            //Oppdater hitpoints for ms/vehicle.
            if (!res.isShieldBlock()) {
                target_player.getVehicle().applyDamage(res.getDamage());
            } else {
                //Spiller blokkerte med et shield.
                Weapon shield = (Weapon) target_player.getVehicle().getEquippedItem(1).getItem();
                shield.applyDamage(res.getDamage());
            }
        } else if (target_player.getTaxiTransport() != null) {
            target_player.getTaxiTransport().applyDamage(res.getDamage());
        }

    }

    /**
     * Oppdaterer hitpoint og shield status for NPC som blir angrepet.
     *
     *
     * @param res Resultat fra angrepet.
     * @param targetNpc NPC som blir angrepet.
     */
    private void PVEoppdaterStatus(gameServer.CombatResult res, NPCvehicle targetNpc) {

        //Sjekk om angrepet ble blokkert med et shield.
        if (res.isShieldBlock()) {

            targetNpc.applyShieldDamage(res.getDamage());
        } else {

            targetNpc.applyDamage(res.getDamage());
        }

    }

    /**
     * Gjør de nødvendige oppdateringer når en spiller sin
     * MS/vehicle/taxi/transport får 100% skade.
     *
     * Denne metoden er public static fordi den også brukes når en spiller får
     * MS ødelagt av NPCs.
     *
     * @param target_player Spiller hvis MS/vehicle har 100% skade.
     *
     * @return true dersom vehicle til spiller skal føre til et score/loss.
     * Kjøretøy som ikke kan ha våpen vil ikke resultere i score/loss.
     */
    public static boolean destroyMS(CharacterGame target_player) {

        boolean resultat = false;

        if (target_player.getVehicle() != null) {

            ItemContainer ic = target_player.getVehicleContainer();
            Vehicle v = target_player.getVehicle();

            //Hvis spilleren hadde noe i inventory skal vi legge igjen en wreckage container.
            if (v.getInventory().getContents().size() > 0) {
                //Vi skal etterlate wreckage container.
                int ownerExpire = (int) (System.currentTimeMillis() / 1000) + config.Server.wreckage_owner_expire;
                gameServer.ItemHandler.registerItem(ic, target_player.getPosisjon().lagKopi(), target_player, ownerExpire);

                sendWreckage8035(target_player, ic, ownerExpire);
            } else {
                //Inventory er tomt, ingen wreckage container, fjern vehicle fra spillet.
                ContainerList.removeContainer(ic.getID());
                ContainerList.removeContainer(v.getInventory().getID());
                ContainerList.removeContainer(v.getWeaponsRoom().getID());
            }

            //Sjekk om målet var i et vehicle som skal resultere i et score/loss.
            GameItem i = ItemList.getItem(v.getID());

            if (i == null) {
                resultat = false;
            }

            if (i.getItemSubType() == ItemSubType.Battleship || i.getItemSubType() == ItemSubType.MS || i.getItemSubType() == ItemSubType.MA
                    || i.getItemSubType() == ItemSubType.Fighter || i.getItemSubType() == ItemSubType.Tank) {
                //Spiller var i et vehicle som kan ha våpen.
                resultat = true;
            } else {
                resultat = false;
            }

        } else if (target_player.getTaxiTransport() != null) {

            ItemContainer ic = target_player.getTaxiTransportContainer();
            ContainerList.removeContainer(ic.getID());

            //Taxi/transport skal aldri resultere i et loss.
            resultat = false;
        }

        target_player.setHumanForm();
        target_player.appearanceChange();

        return resultat;
    }

    /**
     * Beregner machine damage for en spiller.
     *
     * @param c Spilleren vi skal beregne machine damage for.
     *
     * @return Machine damage.
     */
    private byte machineDamage(CharacterGame c) {

        int machine_damage = 100; //Hvis spiller ikke er i noe vehicle lengre har det fått 100% skade.

        if (c.getVehicle() != null) {
            machine_damage = c.getVehicle().getMachineDamage();
        } else if (c.getTaxiTransport() != null) {
            machine_damage = c.getTaxiTransport().getMachineDamage();
        }

        return (byte) machine_damage;
    }

    /**
     * Beregner shield damage for en spiller.
     *
     * @param c Spiller vi skal beregne shield damage for.
     *
     * @return Shield damage, 0-100.
     */
    private byte shieldDamage(CharacterGame c) {

        int shield_damage = 100; //Sett til 100 som default. Hvis spiller ikke har shield utstyrt er det ødelagt.

        ItemContainer con = c.getVehicle().getEquippedItem(1); //Slot #1 er alltid shield.

        if (con != null) {

            Weapon shield = (Weapon) con.getItem();
            if (shield != null) {
                shield_damage = 100 - (100 * shield.getHitpoints() / shield.getMaxHitpoints());
            }
        }

        return (byte) shield_damage;
    }

    /**
     * Lager en opcode 0x800F pakke. For PVP.
     *
     * @param attacker Spiller som angriper.
     * @param target Spiller som blir angrepet.
     * @param res Resultat av angrepet.
     * @param attack_id ID for angrepet.
     * @combo verdi som skal settes inn i pakke for CQB/H2H combo.
     * @return 0x800F pakke.
     */
    private Packet opcode800F(CharacterGame attacker, CharacterGame target, gameServer.CombatResult res, int attack_id, short combo) {

        //Send svar tilbake til spiller og målet.
        PacketData svar800F = new PacketData();

        svar800F.writeIntBE(attacker.getCharacterID());
        svar800F.writeIntBE(target.getCharacterID());

        byte sd = 0; //Shield damage.

        //Beregn shield damage hvis målet blokkerte med et shield.
        if (res.isShieldBlock()) {
            sd = this.shieldDamage(target);
        }

        //Hvis vi traff målet påfør skade.
        if (res.isHit() && !res.isDodged()) {
            svar800F.writeIntBE(res.getDamage());
        } else {
            svar800F.writeIntBE(0x0);
        }

        svar800F.writeByte((byte) 0x1);
        svar800F.writeByte((byte) 0x0); //Egentlig aktiv slot, men bør settes til 0 for å unngå bugs.

        //Skriv neste byte avhengig av resultatet.
        //0x2,0x5,0x6=Dodge, 0x3,0x4=Hit 0x0=Hit+knockback, 0x1=Citicial+knockback
        if (res.isShieldBlock() && sd >= 100) {
            svar800F.writeByte((byte) 0x3);
        } else if (res.isDodged()) {
            svar800F.writeByte((byte) 0x6);
        } else if (res.isCritical() && !res.isShieldBlock()) {
            svar800F.writeByte((byte) 1);
        } else if (res.isHit() && !res.isShieldBlock()) {
            svar800F.writeByte((byte) 0);
        } else {
            svar800F.writeByte((byte) 4); //Angrepet ble enten blokkert eller vi bommet. 
        }
        svar800F.writeByte((byte) 0);
        if (res.isHit() && !res.isDodged()) {
            svar800F.writeShortBE(combo); //Kun combo hvis vi traff, ellers blir animasjon tåpelig.
        } else {
            svar800F.writeShortBE((short) -1);
        }

        //0x0=Shield block (hvis 0x4 hit) eller hit+knockback, 0x3=No block, hvis 0x4 hit. 4=Miss. 0x2=BOOM!
        if (res.isShieldBlock() && sd >= 100) {
            svar800F.writeByte((byte) 0x3);
        } else if (this.machineDamage(target) >= 100) {
            svar800F.writeByte((byte) 0x2);
        } else if (!res.isHit() || res.isDodged()) {
            svar800F.writeByte((byte) 0x4);
        } else {
            svar800F.writeByte((byte) 0x0);
        }

        svar800F.writeByte((byte) 0); //-1 eller 0 UKJENT.
        svar800F.writeIntBE(attack_id);
        svar800F.writeLongBE(res.getMoney());

        //wID = Item ID for utstyrt våpen.
        int wID = attacker.getVehicle().getEquippedItem(attacker.getVehicle().getActiveSlot()).getItem().getID();
        StatWeapon stat = StatManager.getWeaponStat(wID);

        if (stat != null) {
            //Bruk ammo og påfør weapon damage.
            Weapon w = (Weapon) attacker.getVehicle().getEquippedItem(attacker.getVehicle().getActiveSlot()).getItem();
            w.applyDamage(1);
            w.useAmmo(stat.getAmmoUse());

            svar800F.writeByte((byte) 0x1); //Weapon damage
            svar800F.writeByte((byte) stat.getAmmoUse()); //Ammo brukt.
        } else {
            svar800F.writeByte((byte) 0x0); //Weapon damage
            svar800F.writeByte((byte) 0x0); //Ammo brukt.
        }

        Vehicle v = attacker.getVehicle();

        svar800F.writeIntBE(v.getEquippedItem(v.getActiveSlot()).getID());
        svar800F.writeIntBE(v.getEquippedItem(v.getActiveSlot()).getContainerTail());
        svar800F.writeIntBE(v.getEquippedItem(v.getActiveSlot()).getItem().getID());

        //Skriv info avhengig av om målet er i ms/vehicle eller taxi/transport.
        if (target.getVehicle() != null) {
            //Hvis vi fikk en shield block skriv ut info for skjoldet.
            if (res.isShieldBlock()) {
                //Hvis target har et shield i slot 1 skriv ut info for det.
                if (target.getVehicle().getEquippedItem(1) != null) {
                    svar800F.writeIntBE(target.getVehicle().getEquippedItem(1).getID());
                    svar800F.writeIntBE(target.getVehicle().getEquippedItem(1).getContainerTail());
                    svar800F.writeIntBE(target.getVehicle().getEquippedItem(1).getItem().getID());
                } else {
                    //Target har ikke et shield i slot 1, DVS det har blitt ødelagt 100% skade.
                    //Skriv ut null verdier.
                    svar800F.writeIntBE(0x0);
                    svar800F.writeIntBE(0x0);
                    svar800F.writeIntBE(0x0);
                }

            } else {
                //Ingen shield block, target sin ms/vehicle ble truffet.
                svar800F.writeIntBE(target.getVehicleContainer().getID());
                svar800F.writeIntBE(target.getVehicleContainer().getContainerTail());
                svar800F.writeIntBE(target.getVehicleContainer().getItem().getID());
            }
        } else if (target.getTaxiTransport() != null) {
            svar800F.writeIntBE(target.getTaxiTransportContainer().getID());
            svar800F.writeIntBE(target.getTaxiTransportContainer().getContainerTail());
            svar800F.writeIntBE(target.getTaxiTransportContainer().getItem().getID());
        } else {
            //Målet er ikke ms/vehicle eller i taxi/transport. Betyr at det har fått 100% skade.
            svar800F.writeIntBE(0x0);
            svar800F.writeIntBE(0x0);
            svar800F.writeIntBE(0x0);
        }

        svar800F.writeByte(this.machineDamage(target));

        Packet svar800F_pakke = new Packet(0x800F, svar800F.getData());

        return svar800F_pakke;
    }

    /**
     * Lager en opcode 0x800F pakke. For PVE.
     *
     * @param attacker Spiller som angriper.
     * @param target NPC som blir angrepet.
     * @param res Resultat av angrepet.
     * @param attack_id ID for angrepet.
     * @combo verdi som skal settes inn i pakke for CQB/H2H combo.
     *
     * @return 0x800F pakke.
     */
    private Packet PVEopcode800F(CharacterGame attacker, NPCvehicle target, gameServer.CombatResult res, int attack_id, short combo) {

        //Send svar tilbake til spiller og målet.
        PacketData svar800F = new PacketData();

        svar800F.writeIntBE(attacker.getCharacterID());
        svar800F.writeIntBE(target.getID());

        byte sd = 0; //Shield damage.

        //Beregn shield damage hvis målet blokkerte med et shield.
        if (res.isShieldBlock()) {
            sd = target.getShieldDamage();
        }

        //Hvis vi traff målet påfør skade.
        if (res.isHit() && !res.isDodged()) {
            svar800F.writeIntBE(res.getDamage());
        } else {
            svar800F.writeIntBE(0x0);
        }

        svar800F.writeByte((byte) 0x1);
        svar800F.writeByte((byte) 0x0); //Egentlig aktiv slot, men bør settes til 0 for å unngå bugs.

        //Skriv neste byte avhengig av resultatet.
        //0x2,0x5,0x6=Dodge, 0x3,0x4=Hit 0x0=Hit+knockback, 0x1=Citicial+knockback
        if (res.isShieldBlock() && sd >= 100) {
            svar800F.writeByte((byte) 0x3);
        } else if (res.isDodged()) {
            svar800F.writeByte((byte) 0x6);
        } else if (res.isCritical() && !res.isShieldBlock()) {
            svar800F.writeByte((byte) 1);
        } else if (res.isHit() && !res.isShieldBlock()) {
            svar800F.writeByte((byte) 0);
        } else {
            svar800F.writeByte((byte) 4); //Angrepet ble enten blokkert eller vi bommet. 
        }
        svar800F.writeByte((byte) 0);
        if (res.isHit() && !res.isDodged()) {
            svar800F.writeShortBE(combo); //Kun combo hvis vi traff, ellers blir animasjon tåpelig.
        } else {
            svar800F.writeShortBE((short) -1);
        }

        //0x0=Shield block (hvis 0x4 hit) eller hit+knockback, 0x3=No block, hvis 0x4 hit. 4=Miss. 0x1=BOOM!
        if (res.isShieldBlock() && sd >= 100) {
            svar800F.writeByte((byte) 0x3);
        } else if (target.getMachineDamage() >= 100) {
            svar800F.writeByte((byte) 0x1);
        } else if (!res.isHit() || res.isDodged()) {
            svar800F.writeByte((byte) 0x4);
        } else {
            svar800F.writeByte((byte) 0x0);
        }

        svar800F.writeByte((byte) 0); //-1 eller 0 UKJENT.
        svar800F.writeIntBE(attack_id);
        svar800F.writeLongBE(res.getMoney());

        //wID = Item ID for utstyrt våpen.
        int wID = attacker.getVehicle().getEquippedItem(attacker.getVehicle().getActiveSlot()).getItem().getID();
        StatWeapon stat = StatManager.getWeaponStat(wID);

        if (stat != null) {
            //Bruk ammo og påfør weapon damage.
            Weapon w = (Weapon) attacker.getVehicle().getEquippedItem(attacker.getVehicle().getActiveSlot()).getItem();
            w.applyDamage(1);
            w.useAmmo(stat.getAmmoUse());

            svar800F.writeByte((byte) 0x1); //Weapon damage
            svar800F.writeByte((byte) stat.getAmmoUse()); //Ammo brukt.
        } else {
            svar800F.writeByte((byte) 0x0); //Weapon damage
            svar800F.writeByte((byte) 0x0); //Ammo brukt.
        }

        Vehicle v = attacker.getVehicle();

        svar800F.writeIntBE(v.getEquippedItem(v.getActiveSlot()).getID());
        svar800F.writeIntBE(v.getEquippedItem(v.getActiveSlot()).getContainerTail());
        svar800F.writeIntBE(v.getEquippedItem(v.getActiveSlot()).getItem().getID());

        //Hvis vi fikk en shield block skriv ut info for skjoldet.
        if (res.isShieldBlock()) {
            //Hvis target har et shield i slot 1 skriv ut info for det.
            if (target.getShield()[1] != 0) {
                svar800F.writeIntBE(0x0);
                svar800F.writeIntBE(0x0);
                svar800F.writeIntBE(target.getShield()[0]);
            } else {
                //Target har ikke et shield i slot 1, DVS det har blitt ødelagt 100% skade.
                //Skriv ut null verdier.
                svar800F.writeIntBE(0x0);
                svar800F.writeIntBE(0x0);
                svar800F.writeIntBE(0x0);
            }

        } else {
            //Ingen shield block, target sin ms/vehicle ble truffet.
            svar800F.writeIntBE(target.getID());
            svar800F.writeIntBE(0x14);
            svar800F.writeIntBE(target.getTemplate().getVehicleID());
        }

        svar800F.writeByte(target.getMachineDamage());

        Packet svar800F_pakke = new Packet(0x800F, svar800F.getData());

        return svar800F_pakke;
    }

    /**
     * Lager en opcode 0x8036 pakke. For PVP.
     *
     * @param attacker Spiller som angriper.
     * @param target Spiller som blir angrepet.
     * @param res Resultat av angrepet.
     * @param attack_id ID for angrepet.
     * @combo verdi som skal settes inn i pakke for CQB/H2H combo.
     *
     * @return 0x8036 pakke.
     */
    private Packet opcode8036(CharacterGame attacker, CharacterGame target, gameServer.CombatResult res, int attack_id, short combo) {

        PacketData svar8036 = new PacketData();

        svar8036.writeIntBE(attacker.getCharacterID());
        svar8036.writeIntBE(target.getCharacterID());
        svar8036.writeIntBE(attacker.getVehicle().getEquippedItem(attacker.getVehicle().getActiveSlot()).getItem().getID());

        byte sd = 0; //Shield damage.

        //Beregn shield damage hvis målet blokkerte med et shield.
        if (res.isShieldBlock()) {
            sd = this.shieldDamage(target);
        }

        //Skriv neste byte avhengig av resultatet.
        //0x2,0x5,0x6=Dodge, 0x3,0x4=Hit 0x0=Hit+knockback, 0x1=Citicial+knockback
        if (res.isShieldBlock() && sd >= 100) {
            svar8036.writeByte((byte) 0x3);
        } else if (res.isDodged()) {
            svar8036.writeByte((byte) 0x6);
        } else if (res.isCritical() && !res.isShieldBlock()) {
            svar8036.writeByte((byte) 1);
        } else if (res.isHit() && !res.isShieldBlock()) {
            svar8036.writeByte((byte) 0);
        } else {
            svar8036.writeByte((byte) 4); //Angrepet ble enten blokkert eller vi bommet. 
        }
        svar8036.writeByte((byte) 0);
        if (res.isHit() && !res.isDodged()) {
            svar8036.writeShortBE(combo); //Kun combo hvis vi traff, ellers blir animasjon tåpelig.
        } else {
            svar8036.writeShortBE((short) -1);
        }

        //0x0=Shield block (hvis 0x4 hit) eller hit+knockback, 0x3=No block, hvis 0x4 hit. 4=Miss. 0x2=BOOM!
        if (res.isShieldBlock() && sd >= 100) {
            svar8036.writeByte((byte) 0x3);
        } else if (this.machineDamage(target) >= 100) {
            svar8036.writeByte((byte) 0x2);
        } else if (!res.isHit() || res.isDodged()) {
            svar8036.writeByte((byte) 0x4);
        } else {
            svar8036.writeByte((byte) 0x0);
        }

        svar8036.writeIntBE(attack_id);
        svar8036.writeByte(this.machineDamage(target));

        Packet svar8036_pakke = new Packet(0x8036, svar8036.getData());

        return svar8036_pakke;
    }

    /**
     * Lager en opcode 0x8036 pakke. For PVE.
     *
     * @param attacker Spiller som angriper.
     * @param target NPC som blir angrepet.
     * @param res Resultat av angrepet.
     * @param attack_id ID for angrepet.
     * @combo verdi som skal settes inn i pakke for CQB/H2H combo.
     *
     * @return 0x8036 pakke.
     */
    private Packet PVEopcode8036(CharacterGame attacker, NPCvehicle target, gameServer.CombatResult res, int attack_id, short combo) {

        PacketData svar8036 = new PacketData();

        svar8036.writeIntBE(attacker.getCharacterID());
        svar8036.writeIntBE(target.getID());
        svar8036.writeIntBE(attacker.getVehicle().getEquippedItem(attacker.getVehicle().getActiveSlot()).getItem().getID());

        byte sd = 0; //Shield damage.

        //Beregn shield damage hvis målet blokkerte med et shield.
        if (res.isShieldBlock()) {
            sd = target.getShieldDamage();
        }

        //Skriv neste byte avhengig av resultatet.
        //0x2,0x5,0x6=Dodge, 0x3,0x4=Hit 0x0=Hit+knockback, 0x1=Citicial+knockback
        if (res.isShieldBlock() && sd >= 100) {
            svar8036.writeByte((byte) 0x3);
        } else if (res.isDodged()) {
            svar8036.writeByte((byte) 0x6);
        } else if (res.isCritical() && !res.isShieldBlock()) {
            svar8036.writeByte((byte) 1);
        } else if (res.isHit() && !res.isShieldBlock()) {
            svar8036.writeByte((byte) 0);
        } else {
            svar8036.writeByte((byte) 4); //Angrepet ble enten blokkert eller vi bommet. 
        }
        svar8036.writeByte((byte) 0);
        if (res.isHit() && !res.isDodged()) {
            svar8036.writeShortBE(combo); //Kun combo hvis vi traff, ellers blir animasjon tåpelig.
        } else {
            svar8036.writeShortBE((short) -1);
        }

        //0x0=Shield block (hvis 0x4 hit) eller hit+knockback, 0x3=No block, hvis 0x4 hit. 4=Miss. 0x1=BOOM!
        if (res.isShieldBlock() && sd >= 100) {
            svar8036.writeByte((byte) 0x3);
        } else if (target.getMachineDamage() >= 100) {
            svar8036.writeByte((byte) 0x1);
        } else if (!res.isHit() || res.isDodged()) {
            svar8036.writeByte((byte) 0x4);
        } else {
            svar8036.writeByte((byte) 0x0);
        }

        svar8036.writeIntBE(attack_id);
        svar8036.writeByte(target.getMachineDamage());

        Packet svar8036_pakke = new Packet(0x8036, svar8036.getData());

        return svar8036_pakke;
    }

    /**
     * Denne metoden sender en "tom" 0x800F pakke tilbake til spiller. Dvs,
     * pakken sier til spilleren at ingenting skjedde: du bommet, ingen skade
     * påført, ingen weapon damage, ingen ammo brukt.
     *
     * Denne metoden skal brukes i de tilfeller der problemer oppstår pga lag.
     *
     * @param attacker Spilleren som angriper.
     * @param target Character ID til målet.
     */
    private void sendTom800F(CharacterGame attacker, int target) {

        PacketData svar800F = new PacketData();

        svar800F.writeIntBE(attacker.getCharacterID());
        svar800F.writeIntBE(target);
        svar800F.writeIntBE(0x0);
        svar800F.writeByte((byte) 0x1);

        if (attacker.getVehicle() != null) {
            svar800F.writeByte((byte) attacker.getVehicle().getActiveSlot());
        } else {
            svar800F.writeByte((byte) 0x0);
        }

        svar800F.writeByte((byte) 0x0);
        svar800F.writeByte((byte) 0x0);
        svar800F.writeShortBE((short) -1);
        svar800F.writeByte((byte) 0x4);
        svar800F.writeByte((byte) -1);

        svar800F.writeIntBE(0x0);
        svar800F.writeLongBE(0x0);
        svar800F.writeByte((byte) 0x0);
        svar800F.writeByte((byte) 0x0);
        svar800F.writeLongBE(0x0);
        svar800F.writeIntBE(0x0);

        svar800F.writeLongBE(0x0);
        svar800F.writeIntBE(0x0);
        svar800F.writeByte((byte) 0x0);

        Packet svar800F_pakke = new Packet(0x800F, svar800F.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar800F_pakke, attacker.getCharacterID());
    }

    /**
     * Sender opcode 0x8035 til spillere i nærheten for å informere om et nytt
     * wreckage.
     *
     * @param target Spiller som mistet MS/vehicle.
     * @param wreck Item Container der wreckage er.
     * @param ownerExpire Når eierskap for wreckage går ut.
     */
    private static void sendWreckage8035(CharacterGame target, ItemContainer wreck, int ownerExpire) {

        PacketData svar8035 = new PacketData();

        svar8035.writeIntBE(0x1);

        svar8035.writeIntBE(wreck.getID());
        svar8035.writeIntBE(wreck.getContainerTail());
        svar8035.writeIntBE(wreck.getItem().getID());

        svar8035.writeIntBE(target.getPosisjon().getX());
        svar8035.writeIntBE(target.getPosisjon().getY());
        svar8035.writeIntBE(target.getPosisjon().getZ());
        svar8035.writeShortBE((short) target.getPosisjon().getTilt());
        svar8035.writeShortBE((short) target.getPosisjon().getRoll());
        svar8035.writeShortBE((short) target.getPosisjon().getDirection());

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

    /**
     * Sjekker at avstanden mellom spiller og målet er innenfor spillers
     * utstyrte våpens rekkevidde. Det antaes at spiller er i et vehicle og har
     * et våpen utstyrt.
     *
     * Denne metoden printer ut feil melding dersom avstanden er ugyldig.
     *
     * @param chara Spiller som angriper.
     * @param avstand Avstand til målet i meter.
     *
     * @return true hvis avstanden er OK, false hvis ugyldig avstand.
     */
    private boolean checkDistance(CharacterGame chara, int avstand) {

        int wID = chara.getVehicle().getEquippedItem(chara.getVehicle().getActiveSlot()).getItem().getID();

        StatWeapon wStat = StatManager.getWeaponStat(wID);

        //Sjekk at avstand til målet er innenfor våpenets rekkevidde. +10 meter pga lag o.l
        if (avstand > (wStat.getRange() + 10)) {
            return false;
        }

        return true;
    }

}
