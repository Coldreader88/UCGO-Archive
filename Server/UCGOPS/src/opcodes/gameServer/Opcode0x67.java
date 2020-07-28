package opcodes.gameServer;

import characters.*;
import containers.*;
import gameData.*;
import items.*;
import java.util.HashMap;
import java.util.Iterator;
import npc.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;
import validItems.*;

public class Opcode0x67 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        PlayerGame player = (PlayerGame) p;

        int character_id = pd.readIntBE();

        pd.readByte();
        pd.readShortBE();

        pd.readIntBE(); //UKJENT.

        int x_pos = pd.readIntBE();
        int y_pos = pd.readIntBE();
        int z_pos = pd.readIntBE();

        //Sjekk at oppgitt character id er rett.
        if (player.getCharacter().getCharacterID() != character_id) {
            return;
        }

        //Sjekk hvor lenge siden sist det er at spiller utførte et angrep.
        if (player.getCharacter().attackDeltaTime() < 900) {
            //Mindre enn 0.7 sekunder siden forrige angrep. Kan være speed cheating.
            player.getCharacter().setAttackTimer();
            this.sendTom8067(player.getCharacter());
            return;
        } else {
            player.getCharacter().setAttackTimer();
        }

        //Sjekk at character er i et vehicle og har våpen utstyrt.
        Vehicle v = player.getCharacter().getVehicle();

        if (v == null) {
            //Spiller må være i et vehicle for å kunne angripe. Send tom 0x8067 pakke tilbake.
            //Dette skjer pga lag.
            this.sendTom8067(player.getCharacter());
            return;
        }

        //Sjekk at spilleren har et våpen utstyrt.
        ItemContainer ic = v.getEquippedItem(v.getActiveSlot());

        if (ic == null || !(ic.getItem() instanceof Weapon)) {
            //Spiller kan ikke angripe uten et våpen.            
            return;
        }

        //Sjekk at våpen ikke har 100% damage.
        Weapon w = (Weapon) ic.getItem();
        if (w.isDestroyed()) {
            sendTom8067(player.getCharacter());
            return;
        }

        int attack_id = gameServer.Combat.getAttackID();

        //Gå gjennom alle PC/NPCs som ble angrepet og beregn resultatet.
        int teller = (int) (pd.readByte()) & 0x7F;
        HashMap<Integer, gameServer.CombatResult> targets = new HashMap<Integer, gameServer.CombatResult>();

        long money = 0; //Hvor mye penger vi får for angrepet.

        for (int c = 0; c < teller; c++) {

            int target_cid = pd.readIntBE(); //Character ID til målet.
            pd.readIntBE(); //Containter ID
            int avstand = pd.readShortBE(); //Avstand
            pd.readIntBE(); //X
            pd.readIntBE(); //Y
            pd.readIntBE(); //Z
            pd.readIntBE(); //Angrep type?

            //Sjekk at avstanden er gyldig.
            boolean gyldigAvstand = this.checkDistance(player.getCharacter(), avstand);

            //Sjekk om det er PVP eller PVE.
            CharacterGame t = gameServer.MultiClient.getCharacter(target_cid);
            if (t != null) {
                //PVP.
                gameServer.CombatResult res = gameServer.Combat.attack(player.getCharacter(), t);
                //Hvis OK legg til i resultatet.
                if (res != null) {

                    //Dersom ugyldig avstand til målet annuler resultatet av angrepet.
                    if (!gyldigAvstand) {
                        res.setDamage(0);
                        res.setHit(false);
                        res.setDodged(false);
                        res.setShieldBlock(false);
                        res.setMoney(0);
                    }

                    targets.put(target_cid, res);
                    //Oppdater hvor mye penger vi får for angrepet.
                    money += res.getMoney();
                    //Sjekk om vi skal påføre skade på målet.
                    if (!res.isDodged() && res.isHit()) {
                        //Vi traff målet påfør skade.
                        this.oppdaterStatus(res, t);
                    }

                    //Criminal sjekk.
                    if (res.isCriminal()) {
                        //Criminal angrep, sjekk om spiller fikk kill shot eller ikke.
                        if (this.machineDamage(t) >= 100) {
                            gameServer.Combat.criminal(player, true);
                        } else {
                            gameServer.Combat.criminal(player, false);
                        }
                    }

                }

            } else {
                //Sjekk om målet er en aktiv NPC.
                NPC n = LocalManager.getNpc(target_cid);
                if (n == null) {
                    n = GlobalManager.getNpc(target_cid);
                }

                if (n != null && n.isActive() && n instanceof NPCvehicle) {
                    //Målet er en gyldig NPC.
                    NPCvehicle targetNpc = (NPCvehicle) n;

                    //Sjekk at NPC ikke allerede har fått 100% damage.
                    if (targetNpc.getMachineDamage() < 100) {
                        gameServer.CombatResult res = gameServer.Combat.attack(player.getCharacter(), targetNpc);
                        //Hvis OK legg til i resultatet.
                        if (res != null) {
                            targets.put(target_cid, res);
                            //Oppdater hvor mye penger vi får for angrepet.
                            money += res.getMoney();
                            //Sjekk om vi skal påføre skade på målet.
                            if (!res.isDodged() && res.isHit()) {
                                //Vi traff målet påfør skade.
                                this.PVEoppdaterStatus(res, targetNpc);
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
                    }
                }

            }

        }

        //Oppdater pengene i banken.
        player.getCharacter().getCreditContainer().settAntall(player.getCharacter().getCreditContainer().getAntall() + money);

        //Send 0x8068 til spilleren og alle i nærheten.
        Packet svar8068_pakke = this.opcode8068(player.getCharacter(), targets, attack_id, x_pos, y_pos, z_pos);

        gameServer.MultiClient.broadcastPacket(svar8068_pakke, player.getCharacter());

        //Send 0x8067 til spilleren og de som ble angrepet.
        Packet svar8067_pakke = this.opcode8067(player.getCharacter(), targets, attack_id, money);

        player.sendPacket(svar8067_pakke);

        Iterator<Integer> spillere = targets.keySet().iterator();
        while (spillere.hasNext()) {

            int target_id = spillere.next();

            //Ikke send 0x8067 dersom angrepet bommet.
            if (targets.get(target_id).isHit()) {
                gameServer.MultiClient.sendPacketToPlayer(svar8067_pakke, target_id);
            }

            //Hvis målet er PC sjekk om det har fått 100% skade.
            CharacterGame t = gameServer.MultiClient.getCharacter(target_id);
            if (t != null) {

                if (this.machineDamage(t) >= 100) {
                    //Spilleren har 100% skade.
                    boolean r = this.destroyMS(t);
                    //Sjekk om vi skal oppdatere score/loss.
                    if (r) {
                        player.getCharacter().setScore(player.getCharacter().getScore() + 1);
                        t.setLosses(t.getLosses() + 1);

                        //Oppdater rank.
                        player.getCharacter().gainRank(t.getRank());
                        t.loseRank(100);
                    }

                } else if (targets.get(target_id).isShieldBlock()) {
                    //Spiller blokkerte med et shield.
                    Weapon shield = (Weapon) t.getVehicle().getEquippedItem(1).getItem();
                    //Hvis 100% skade fjern det.
                    if (shield.getHitpoints() <= 0) {
                        t.getVehicle().destroyShield();
                        t.appearanceChange();
                    }
                }
            } else {
                //Målet er NPC. Oppdater score hvis den har fått 100% skade. 
                NPC n = LocalManager.getNpc(target_id);
                if (n == null) {
                    n = GlobalManager.getNpc(target_id);
                }

                if (n != null && n instanceof NPCvehicle) {
                    NPCvehicle npc = (NPCvehicle) n;
                    //Sjekk om NPC har 100% skade.
                    if (npc.getMachineDamage() == 100) {
                        //Sjekk at NPC var i et vehicle som skal resultere i et score/loss.
                        GameItem i = ItemList.getItem(npc.getTemplate().getVehicleID());

                        if (i.getItemSubType() == ItemSubType.Battleship || i.getItemSubType() == ItemSubType.MS || i.getItemSubType() == ItemSubType.MA
                                || i.getItemSubType() == ItemSubType.Fighter || i.getItemSubType() == ItemSubType.Tank) {

                            player.getCharacter().setScore(player.getCharacter().getScore() + 1);

                            //Oppdater rank.
                            player.getCharacter().gainRank(npc.getRank() / 2);
                        }

                        //La NPC slippe items hvis den skal.
                        npc.dropWreckageContainer(player.getCharacter());
                    }

                }

            }
        }

    }

    /**
     * Returnerer en opcode 0x8067 pakke. Da det er mulig for opcode 0x67 å
     * angripe både PCs og NPCs samtidig må denne metoden håndtere både PVP og
     * PVE.
     *
     * @param attacker Spilleren som angriper.
     *
     * @param res HashMap på formatet
     * <Character ID til målet,Resultat av angrepet>
     * @param attack_id ID for angrepet.
     * @param money Hvor mye penger spilleren får for angrepet.
     *
     * @return 0x8067 pakke.
     */
    private Packet opcode8067(CharacterGame attacker, HashMap<Integer, gameServer.CombatResult> res, int attack_id, long money) {

        PacketData svar8067 = new PacketData();

        svar8067.writeIntBE(attacker.getCharacterID());
        svar8067.writeByte((byte) attacker.getVehicle().getActiveSlot());
        svar8067.writeShortBE((short) -1);
        svar8067.writeIntBE(attack_id);
        svar8067.writeLongBE(money);

        //wID = Item ID for utstyrt våpen.
        int wID = attacker.getVehicle().getEquippedItem(attacker.getVehicle().getActiveSlot()).getItem().getID();
        StatWeapon stat = StatManager.getWeaponStat(wID);

        if (stat != null) {
            //Bruk ammo og påfør weapon damage.
            Weapon w = (Weapon) attacker.getVehicle().getEquippedItem(attacker.getVehicle().getActiveSlot()).getItem();
            w.applyDamage(1);
            w.useAmmo(stat.getAmmoUse());

            svar8067.writeByte((byte) 0x1); //Weapon damage
            svar8067.writeByte((byte) stat.getAmmoUse()); //Ammo brukt.
        } else {
            svar8067.writeByte((byte) 0x0); //Weapon damage
            svar8067.writeByte((byte) 0x0); //Ammo brukt.
        }

        svar8067.writeIntBE(0x0);
        svar8067.writeIntBE(attacker.getPosisjon().getX());
        svar8067.writeIntBE(attacker.getPosisjon().getY());
        svar8067.writeIntBE(attacker.getPosisjon().getZ());

        ItemContainer ic = attacker.getVehicle().getEquippedItem(attacker.getVehicle().getActiveSlot());
        svar8067.writeIntBE(ic.getID());
        svar8067.writeIntBE(ic.getContainerTail());
        svar8067.writeIntBE(ic.getItem().getID());

        svar8067.writeByte((byte) (0x80 + res.size()));

        //Gå gjennom alle målene som ble angrepet og skriv ut data for dem.
        Iterator<Integer> targets = res.keySet().iterator();

        while (targets.hasNext()) {

            int target_cid = targets.next();

            gameServer.CombatResult resultat = res.get(target_cid); //Resultat av angrepet.

            byte md = 0; //Machine damage.
            byte sd = 0; //Shield damage.
            ItemContainer con = null; //Container for ms/vehicle, taxi/transport eller shield som ble angrepet.

            //Hent ut data avhengig av om dette er PVP eller PVE. 
            CharacterGame t = gameServer.MultiClient.getCharacter(target_cid);
            if (t != null) {
                //PVP
                md = this.machineDamage(t);
                //Sjekk om det var shield som ble angrepet eller ikke.
                if (!resultat.isShieldBlock()) {
                    //MS/vehicle eller taxi/transport som ble angrepet.
                    if (t.getVehicleContainer() != null) {
                        con = t.getVehicleContainer();
                    } else if (t.getTaxiTransportContainer() != null) {
                        con = t.getTaxiTransportContainer();
                    }
                } else {
                    //Shield block.
                    con = t.getVehicle().getEquippedItem(1);
                    sd = this.shieldDamage(t);
                }
            } else { //Sjekk om det var PVE og hent ut info om NPC.
                NPC n = LocalManager.getNpc(target_cid);
                if (n == null) {
                    n = GlobalManager.getNpc(target_cid);
                }

                if (n != null && n.isActive() & n instanceof NPCvehicle) {

                    md = ((NPCvehicle) n).getMachineDamage();
                    sd = ((NPCvehicle) n).getShieldDamage();

                    //Bruk dummy container, eller vil ikke spiller få score for kills.
                    con = new ItemContainer(n.getID());
                    GeneralItem i = new GeneralItem(((NPCvehicle) n).getTemplate().getVehicleID());
                    con.addItem(i);

                }
            }

            svar8067.writeIntBE(target_cid);
            //Hvis vi traff påfør skade.
            if (resultat.isHit() && !resultat.isDodged()) {
                svar8067.writeIntBE(resultat.getDamage());
            } else {
                svar8067.writeIntBE(0x0);
            }

            svar8067.writeByte((byte) 0x1);

            //Skriv ut resultatet av angrepet.
            if (md >= 100) {
                svar8067.writeShortBE((short) 0x0002); //BOOM
            } else if (resultat.isHit() && resultat.isShieldBlock()
                    && !resultat.isDodged() && sd >= 100) {
                svar8067.writeShortBE((short) 0x0303); //Shield destroyed.
            } else if (resultat.isHit() && resultat.isDodged()) {
                svar8067.writeShortBE((short) 0x0600); //Dodged
            } else if (resultat.isHit() && resultat.isShieldBlock()
                    && !resultat.isDodged() && !resultat.isCritical()) {
                svar8067.writeShortBE((short) 0x0300);//Shield block
            } else if (resultat.isHit() && resultat.isShieldBlock()
                    && !resultat.isDodged() && resultat.isCritical()) {
                svar8067.writeShortBE((short) 0x0400);//Shield block+critical
            } else if (resultat.isHit() && !resultat.isDodged()
                    && !resultat.isShieldBlock() && !resultat.isCritical()) {
                svar8067.writeShortBE((short) 0x0000);//Hit
            } else if (resultat.isHit() && !resultat.isDodged()
                    && !resultat.isShieldBlock() && resultat.isCritical()) {
                svar8067.writeShortBE((short) 0x0100);//Critical hit
            } else {
                svar8067.writeShortBE((short) 0x0301); //Missed.
            }

            svar8067.writeByte((byte) -1);

            //Hvis con=null eller vi bommet skriv ut null data.
            if (con == null || !resultat.isHit()) {
                svar8067.writeIntBE(0x0);
                svar8067.writeIntBE(0x0);
                svar8067.writeIntBE(-1);
            } else {
                svar8067.writeIntBE(con.getID());
                svar8067.writeIntBE(con.getContainerTail());
                svar8067.writeIntBE(con.getItem().getID());
            }

            svar8067.writeByte(md);

        }

        return new Packet(0x8067, svar8067.getData());
    }

    /**
     * Returnerer en opcode 0x8068 pakke. Da det er mulig for opcode 0x67 å
     * angripe både PCs og NPCs samtidig må denne metoden håndtere både PVP og
     * PVE.
     *
     * @param attacker Spilleren som angriper.
     *
     * @param res HashMap på formatet
     * <Character ID til målet,Resultat av angrepet>
     * @param attack_id ID for angrepet.
     * @param x_pos X posisjon, retning vi angriper i.
     * @param y_pos Y posisjon.
     * @param z_pos Z posisjon.
     *
     * @return 0x8068 pakke.
     */
    private Packet opcode8068(CharacterGame attacker, HashMap<Integer, gameServer.CombatResult> res, int attack_id, int x_pos, int y_pos, int z_pos) {

        PacketData svar8068 = new PacketData();

        svar8068.writeIntBE(attacker.getCharacterID());

        ItemContainer ic = attacker.getVehicle().getEquippedItem(attacker.getVehicle().getActiveSlot());
        svar8068.writeIntBE(ic.getItem().getID());

        svar8068.writeShortBE((short) -1);
        svar8068.writeIntBE(attack_id);
        svar8068.writeIntBE(0x0);
        svar8068.writeIntBE(x_pos);
        svar8068.writeIntBE(y_pos);
        svar8068.writeIntBE(z_pos);

        svar8068.writeByte((byte) (0x80 + res.size()));

        //Gå gjennom alle målene som ble angrepet og skriv ut data for dem.
        Iterator<Integer> targets = res.keySet().iterator();

        while (targets.hasNext()) {

            byte md = 0; //Machine damage.
            byte sd = 0; //Shield damage.

            int target_cid = targets.next();

            gameServer.CombatResult resultat = res.get(target_cid); //Resultat av angrepet.

            //Finn machine damage avhengig av om dette er PVP eller PVE. 
            CharacterGame t = gameServer.MultiClient.getCharacter(target_cid);

            if (t != null) {
                //PVP
                md = this.machineDamage(t);
                sd = this.shieldDamage(t);
            } else { //Sjekk om det var PVE og hent ut info om NPC.
                NPC n = LocalManager.getNpc(target_cid);
                if (n == null) {
                    n = GlobalManager.getNpc(target_cid);
                }

                if (n != null && n.isActive() & n instanceof NPCvehicle) {

                    md = ((NPCvehicle) n).getMachineDamage();
                    sd = ((NPCvehicle) n).getShieldDamage();
                }
            }

            svar8068.writeIntBE(target_cid);

            //Skriv ut resultatet av angrepet.
            if (md >= 100) {
                svar8068.writeShortBE((short) 0x0002); //BOOM
            } else if (resultat.isHit() && resultat.isShieldBlock()
                    && !resultat.isDodged() && sd >= 100) {
                svar8068.writeShortBE((short) 0x0303); //Shield destroyed.
            } else if (resultat.isHit() && resultat.isDodged()) {
                svar8068.writeShortBE((short) 0x0600); //Dodged
            } else if (resultat.isHit() && resultat.isShieldBlock()
                    && !resultat.isDodged() && !resultat.isCritical()) {
                svar8068.writeShortBE((short) 0x0300);//Shield block
            } else if (resultat.isHit() && resultat.isShieldBlock()
                    && !resultat.isDodged() && resultat.isCritical()) {
                svar8068.writeShortBE((short) 0x0400);//Shield block+critical
            } else if (resultat.isHit() && !resultat.isDodged()
                    && !resultat.isShieldBlock() && !resultat.isCritical()) {
                svar8068.writeShortBE((short) 0x0000);//Hit
            } else if (resultat.isHit() && !resultat.isDodged()
                    && !resultat.isShieldBlock() && resultat.isCritical()) {
                svar8068.writeShortBE((short) 0x0100);//Critical hit
            } else {
                svar8068.writeShortBE((short) 0x0301); //Missed.
            }

            svar8068.writeByte(md);
        }

        return new Packet(0x8068, svar8068.getData());
    }

    /**
     * Beregner machine damage for en spiller.
     *
     * @param c Spilleren vi beregne machine damage for.
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

        try {
            ItemContainer con = c.getVehicle().getEquippedItem(1); //Slot #1 er alltid shield.

            if (con != null) {

                Weapon shield = (Weapon) con.getItem();
                if (shield != null) {
                    shield_damage = 100 - (100 * shield.getHitpoints() / shield.getMaxHitpoints());
                }
            }
        } catch (Exception e) {
        }

        return (byte) shield_damage;
    }

    /**
     * Oppdater status for spilleren som blir angrepet. Kun for PVP.
     *
     * NB! Hvis spiller får 100% vil ms/taxi/shield etc IKKE bli fjernet, det
     * gjøres i execute() metoden.
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
     * Oppdater status for NPC som blir angrepet.
     *
     *
     * @param res Resultat fra angrepet.
     * @param target_player NPC som ble angrepet.
     */
    private void PVEoppdaterStatus(gameServer.CombatResult res, NPCvehicle targetNpc) {

        //Sjekk om NPC blokkerte med shield.
        if (res.isShieldBlock()) {
            targetNpc.applyShieldDamage(res.getDamage());
        } else {
            targetNpc.applyDamage(res.getDamage());
        }

    }

    /**
     * Gjør de nødvendige oppdateringer når en spiller sin MS/vehicle får 100%
     * skade.
     *
     * @param target_player Spiller hvis MS/vehicle har 100% skade.
     *
     * @return true dersom vehicle til spiller skal føre til et score/loss.
     * Kjøretøy som ikke kan ha våpen vil ikke resultere i score/loss.
     */
    private boolean destroyMS(CharacterGame target_player) {

        boolean resultat = false;

        if (target_player.getVehicle() != null) {

            ItemContainer ic = target_player.getVehicleContainer();
            Vehicle v = target_player.getVehicle();

            //Hvis spilleren hadde noe i inventory skal vi legge igjen en wreckage container.
            if (v.getInventory().getContents().size() > 0) {
                //Vi skal etterlate wreckage container.
                int ownerExpire = (int) (System.currentTimeMillis() / 1000) + config.Server.wreckage_owner_expire;
                gameServer.ItemHandler.registerItem(ic, target_player.getPosisjon().lagKopi(), target_player, ownerExpire);

                this.sendWreckage8035(target_player, ic, ownerExpire);
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
     * Denne metoden sender en "tom" 0x8067 pakke tilbake til spiller. Dvs,
     * pakken sier til spilleren at ingenting skjedde: du bommet, ingen skade
     * påført, ingen weapon damage, ingen ammo brukt.
     *
     * Denne metoden skal brukes i de tilfeller der problemer oppstår pga lag.
     *
     * @param attacker Spilleren som angriper.
     */
    private void sendTom8067(CharacterGame attacker) {

        PacketData svar8067 = new PacketData();

        svar8067.writeIntBE(attacker.getCharacterID());

        if (attacker.getVehicle() != null) {
            svar8067.writeByte((byte) attacker.getVehicle().getActiveSlot());
        } else {
            svar8067.writeByte((byte) 0x0);
        }

        svar8067.writeShortBE((short) -1);
        svar8067.writeIntBE(0x0);
        svar8067.writeLongBE(0x0);
        svar8067.writeByte((byte) 0x0);
        svar8067.writeByte((byte) 0x0);
        svar8067.writeIntBE(0x0);
        svar8067.writeIntBE(attacker.getPosisjon().getX());
        svar8067.writeIntBE(attacker.getPosisjon().getY());
        svar8067.writeIntBE(attacker.getPosisjon().getZ());
        svar8067.writeLongBE(0x0);
        svar8067.writeIntBE(0x0);
        svar8067.writeByte((byte) 0x80);

        Packet svar8067_pakke = new Packet(0x8067, svar8067.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar8067_pakke, attacker.getCharacterID());
    }

    /**
     * Sender opcode 0x8035 til spillere i nærheten for å informere om et nytt
     * wreckage.
     *
     * @param target Spiller som mistet MS/vehicle.
     * @param wreck Item Container der wreckage er.
     * @param ownerExpire Når eierskap for wreckage går ut.
     */
    private void sendWreckage8035(CharacterGame target, ItemContainer wreck, int ownerExpire) {

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
