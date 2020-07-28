package opcodes.gameServer;

import characters.SkillList;
import containers.*;
import crafting.clothes.*;
import crafting.refining.*;
import crafting.vehicles.*;
import crafting.weapons.*;
import gameData.StatGQclothes;
import gameServer.MultiClient;
import items.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x28 implements Opcode {

    public void execute(Player p, Packet pakke) {
        PacketData pd = new PacketData(pakke.getData());

        PlayerGame player = (PlayerGame) p;

        short craftType = pd.readShortBE(); //Angir hvilken type crafting dette er.

        pd.readShortBE();

        int cid = pd.readIntBE();

        if (cid != player.getCharacter().getCharacterID()) {
            return;
        }

        //Utfør riktig metode avhengig av crafting type.
        switch (craftType) {

            case 0x1: //MS/Vehicle

                this.craftVehicle(player, pd);
                break;

            case 0x2: //Refining

                this.craftRefining(player, pd);
                break;

            case 0x3: //Clothes

                this.craftClothes(player, pd);
                break;

            case 0x4: //Weapons

                this.craftWeapons(player, pd);
                break;

            case 0x5: //Dismantle

                this.dismantleVehicle(player, pd);
                break;

            case 0x8:
            case 0x9:
            case 0xA:

                this.upgradeVehicle(player, pd, craftType);
                break;

            default:
                this.sendError(player);
        }

    }

    /**
     * Håndterer opcode 0x28 når det er MS/Vehicle crafting som utføres.
     *
     * @param player Spiller som utfører crafting.
     * @param pd Data sendt i opcode 0x28, to første INTs antaes lest.
     */
    private void craftVehicle(PlayerGame player, PacketData pd) {
        player.getCharacter().setOccupancyTag(7);

        int cityID = pd.readIntBE();
        int IDa = pd.readIntBE();
        int IDb = pd.readIntBE();

        int craftItemID = pd.readIntBE(); //ID til item som skal craftes.
        pd.readIntBE(); //Antall, alltid 1 for MS/vehicle uansett.

        pd.readLongBE();
        int IDc = pd.readIntBE(); //Ukjent INT.
        pd.readIntBE();

        int n = (int) (pd.readByte() & 0xFF);
        //Neste byte skal være 0x82, alle MS/Vehicle bruker to materialer.
        if (n != 0x82) {
            this.sendError(player);
            return;
        }

        //Les info for material #1.
        pd.readIntBE();
        int material1ID = pd.readIntBE(); //Item ID til material #1
        int m1icID = pd.readIntBE(); //Container ID til IC der material #1 er.
        pd.readIntBE();

        pd.readLongBE(); //HC der material er, ikke viktig.
        pd.readIntBE();

        pd.readIntBE();

        long m1Antall = pd.readLongBE();

        //Les info for material #2.
        pd.readIntBE();
        int material2ID = pd.readIntBE(); //Item ID til material #2
        int m2icID = pd.readIntBE(); //Container ID til IC der material #2 er.
        pd.readIntBE();

        pd.readLongBE(); //HC der material er, ikke viktig.
        pd.readIntBE();

        pd.readIntBE();

        long m2Antall = pd.readLongBE();

        //Sjekk at oppgitt MS/Vehicle kan craftes.
        VehicleCraftingStat vStat = VehicleCraftingManager.getCraftingStat(craftItemID);

        if (vStat == null) {

            System.out.println("Opcode 0x28: craftVehicle() ERROR: Attempt to craft invalid item: " + craftItemID);
            this.sendError(player);
            return;
        }

        //Sjekk for speed cheating.
        int tid = (int) (System.currentTimeMillis() / 1000);
        /*if (tid < player.getCharacter().getOpcode28timer()) {
            //Vi har mottatt opcode 0x28 for tidlig, skriv ut varsel og kick spiller.
            System.out.println("Opcode 0x28: POSSIBLE SPEED CHEAT. Character ID:" + player.getCharacter().getCharacterID() + " IP:" + player.getIP());
            System.out.println("Opcode 0x28: Crafting vehicle ID:" + craftItemID);
            System.out.println("Opcode 0x28: Expected time to receive opcode 0x28 = " + player.getCharacter().getOpcode28timer());
            System.out.println("Opcode 0x28: Current time = " + tid);
            MultiClient.kickPlayer(player.getCharacter().getCharacterID());
            return;
        }*/

        //Oppdater opcode 0x28 timer. Minus 2 sekunder pga lag o.l.
        player.getCharacter().setOpcode28timer(tid + vStat.getTimeUsed() - 2);

        //Sjekk at oppgitte materialer er gyldige.
        if (vStat.getUpgradePartID() == 0) {
            //MS krever material+engine. Sjekk at oppgitt materiale er gyldig.
            if (material2ID != vStat.getMaterialID() || m2Antall != vStat.getMaterialAmount()) {

                System.out.println("Opcode 0x28: craftVehicle() ERROR: Invalid material for item: " + craftItemID);
                System.out.println("Expected material: " + vStat.getMaterialID() + " Expected amount: " + vStat.getMaterialAmount());
                System.out.println("Received material: " + material2ID + " Received amount: " + m2Antall);
                this.sendError(player);
                return;
            }
        } else {
            //MS krever upgrade part+MS. Sjekk at begge deler er gyldig, antall for hver del skal alltid være 1.
            if (material1ID != vStat.getUpgradePartID() || m1Antall != 1) {
                System.out.println("Opcode 0x28: craftVehicle() ERROR: Invalid material for item: " + craftItemID);
                System.out.println("Expected upgrade part: " + vStat.getUpgradePartID() + " Expected amount: 1");
                System.out.println("Received upgrade part: " + material1ID + " Received amount: " + m1Antall);
                this.sendError(player);
                return;
            }

            if (material2ID != vStat.getUpgradeVehicleID() || m2Antall != 1) {
                System.out.println("Opcode 0x28: craftVehicle() ERROR: Invalid material for item: " + craftItemID);
                System.out.println("Expected MS/Vehicle: " + vStat.getUpgradeVehicleID() + " Expected amount: 1");
                System.out.println("Received MS/Vehicle: " + material2ID + " Received amount: " + m2Antall);
                this.sendError(player);
                return;
            }

        }

        //Oppgitte materialer er gyldige. Sjekk at Itemcontainerene er gyldige.
        ItemContainer m1IC = this.validateIC(m1icID, material1ID, m1Antall);
        ItemContainer m2IC = this.validateIC(m2icID, material2ID, m2Antall);

        if (m1IC == null || m2IC == null) {
            //Vi sparker spiller for å være sikker.
            System.out.println("Opcode 0x28, Craft vehicle: Container validation failed. IP:" + player.getIP() + " ID:" + player.getCharacter().getCharacterID());
            player.disconnect();
            return;
        }

        //Beregn resultatet av crafting.
        int skill = player.getCharacter().getSkills().getCraftingSkill(SkillList.MSMAConstruction.key());

        VehicleCraftingResult res = VehicleCraftingManager.crafting(craftItemID, skill);

        if (res == null) {
            this.sendError(player);
            return;
        }

        /*
         * OK, crafting har blitt utført. 
         * 
         * Fjern brukt materiale fra spillerens inventory og plasser resultatet i factory.
         */
        m1IC.settAntall(m1IC.getAntall() - m1Antall);

        //Dersom vi oppgraderer MS/Vehicle skal den kun fjernes dersom resultatet var vellykket.
        if (vStat.getUpgradePartID() != 0 && res.isSuccess()) {
            m2IC.settAntall(0);
        } else if (vStat.getUpgradePartID() == 0) {
            m2IC.settAntall(m2IC.getAntall() - m2Antall);
        }

        if (m1IC.getAntall() == 0) {
            //ItemContainer er tom, fjern fra spillers inventory.
            //Material 1 er alltid engine eller oppgraderings del, det er trygt å anta at den er i inventory
            //til spillers vehicle.
            player.getCharacter().getVehicle().getInventory().removeItemContainer(m1IC);
        }

        if (m2IC.getAntall() == 0) {
            //Material 2 er enten materiale plassert i spillers inventory eller MS/Vehicle i hangar.
            if (m2IC.getItem() instanceof Vehicle) {
                //Vehicle som blir oppgradert. Fjern IC fra spillers hangar.
                player.getCharacter().getHangarContainer().removeItemContainer(m2IC);
            } else {
                //Lager nytt vehicle. Fjern IC fra spillers inventory.
                player.getCharacter().getVehicle().getInventory().removeItemContainer(m2IC);
            }
        }

        //Plasser resultatet i factory.
        ItemContainer resIC = ContainerList.newItemContainer();
        Item i = null;

        if (res.isSuccess()) {
            //Crafting vellykket. Sjekk om vi skal opprette helt ny MS/Vehicle eller oppgradere til G3/FA.

            if (vStat.getUpgradePartID() == 0) {
                //Helt ny MS/Vehicle
                i = new Vehicle(craftItemID, material1ID);
            } else {
                //Eksisterende MS oppgraderes til ny MS. Overfør gammelt inventory til ny MS.
                //Material 2 er alltid MS som oppgraderes.
                Vehicle v = (Vehicle) m2IC.getItem();

                i = new Vehicle(craftItemID, v.getEngine(), v);
            }
        } else {
            //Crafting mislykket. Sjekk om vi skal returnere noen materialer til spilleren.
            if (res.getAntall() != 0) {

                //Materialer er alltid general items.
                i = new GeneralItem(vStat.getMaterialID());
            }
        }

        //Dersom vi fikk noe tilbake plasser det i item container og factory.
        if (i != null) {

            resIC.addItem(i);
            resIC.settAntall(res.getAntall());

            //Tail skal være 0x14 hvis MS/Vehicle.
            if (i instanceof Vehicle) {
                resIC.settContainerTail(0x14);
            }

            player.getCharacter().getProductiveContainer().addItemContainer(resIC);
        }

        //OK, send svar tilbake.
        //Send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeShortBE((short) 0x4);

        if (res.isSuccess()) {
            svar.writeShortBE((short) 0x2);
        } else {
            svar.writeShortBE((short) 0xC);
        }

        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(cityID);
        svar.writeIntBE(IDa);
        svar.writeIntBE(IDb);
        svar.writeIntBE(craftItemID);
        svar.writeIntBE(0x1);

        svar.writeIntBE(player.getCharacter().getProductiveContainer().getID());
        svar.writeIntBE(player.getCharacter().getProductiveContainer().getContainerTail());

        svar.writeIntBE(IDc);
        svar.writeIntBE(resIC.getCreateTime());

        svar.writeByte((byte) 0x82);

        //Skriv data for material 1.
        if (m1IC.getAntall() == 0) {
            svar.writeIntBE(0x8);
        } else {
            svar.writeIntBE(0x9);
        }

        svar.writeIntBE(m1IC.getItem().getID());

        svar.writeIntBE(m1IC.getID());
        svar.writeIntBE(m1IC.getContainerTail());

        //Material 1 er alltid i self storage.
        //Sjekk spiller er i vehicle, kan skje dersom spiller angripes samtidig som crafting starter.
        if (player.getCharacter().getVehicle() != null) {
            svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getID());
            svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getContainerTail());
            svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getStatiskID());
        } else {
            svar.writeLongBE(0x0);
            svar.writeIntBE(-1);
        }

        svar.writeIntBE(0x1);

        svar.writeLongBE(m1Antall);

        //Skriv data for material 2.
        if (m2IC.getAntall() == 0) {
            svar.writeIntBE(0x8);
        } else {
            svar.writeIntBE(0x9);
        }

        svar.writeIntBE(m2IC.getItem().getID());

        svar.writeIntBE(m2IC.getID());
        svar.writeIntBE(m2IC.getContainerTail());

        //Material 2 er i hangar dersom dette er oppgradering av MS.
        if (vStat.getUpgradePartID() == 0) {
            //Sjekk spiller er i vehicle, kan skje dersom spiller angripes samtidig som crafting starter.
            if (player.getCharacter().getVehicle() != null) {
                svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getID());
                svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getContainerTail());
                svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getStatiskID());
            } else {
                svar.writeLongBE(0x0);
                svar.writeIntBE(-1);
            }
        } else {
            svar.writeIntBE(player.getCharacter().getHangarContainer().getID());
            svar.writeIntBE(player.getCharacter().getHangarContainer().getContainerTail());
            svar.writeIntBE(player.getCharacter().getHangarContainer().getStatiskID());
        }

        svar.writeIntBE(0x1);

        svar.writeLongBE(m2Antall);

        svar.writeByteMultiple((byte) 0x0, 10);

        //Skriv ut info om resultatet.
        if (res.getAntall() > 0) {
            //OK, vi skal returnere en eller annen item.
            svar.writeByte((byte) 0x81);
            svar.writeIntBE(resIC.getItem().getID());
            svar.writeIntBE((int) resIC.getAntall());
            svar.writeIntBE(0x0);
        } else {
            //Ingenting skal returneres til spilleren.
            svar.writeByte((byte) 0x80);
        }

        svar.writeByte((byte) 0x0);

        Packet svarPakke = new Packet(0x8028, svar.getData());
        player.sendPacket(svarPakke);

        //Sett crafting string for spillerens productive container.
        String s = "1,1," + craftItemID + "," + cityID + "," + IDb + "," + IDa + "," + resIC.getCreateTime() + ",1,2,0";
        player.getCharacter().getProductiveContainer().setCraftingString(s);

        //Dersom spiller ikke får noe tilbake fjern itemcontainer fra listen.
        if (!res.isSuccess() && res.getAntall() == 0) {
            ContainerList.removeContainer(resIC.getID());
        }

        //Skilltraining.
        //Dersom vehicle bruker FL, FTCC, JP, GA for crafting gies det bonus.
        int materialCount = 0;

        if (vStat.getMaterialID() == 0x07C843 || vStat.getMaterialID() == 0x07C844 || vStat.getMaterialID() == 0x07C84D || vStat.getMaterialID() == 0x07C84E) {
            materialCount = vStat.getMaterialAmount();
        }
        gameServer.SkillTraining.trainMsConstruction(player.getCharacter(), vStat.getDifficulty(), vStat.getMinSkill(), false, false, materialCount);
        gameServer.SkillTraining.trainBattleshipConstruction(player.getCharacter(), vStat.getDifficulty(), vStat.getMinSkill(), false, false);
    }

    /**
     * Håndterer opcode 0x28 når det er weapons crafting som utføres.
     *
     * @param player Spiller som utfører crafting.
     * @param pd Data sendt i opcode 0x28, to første INTs antaes lest.
     */
    private void craftWeapons(PlayerGame player, PacketData pd) {
        player.getCharacter().setOccupancyTag(8);

        int cityID = pd.readIntBE();
        int IDa = pd.readIntBE();
        int IDb = pd.readIntBE();

        int craftItemID = pd.readIntBE(); //ID til item som skal craftes.
        pd.readIntBE(); //Antall, alltid 1 for våpen uansett.

        pd.readLongBE();
        int IDc = pd.readIntBE(); //Ukjent INT.
        pd.readIntBE();

        int n = (int) (pd.readByte() & 0xFF);
        //Neste byte skal være 0x81, alle våpen bruker ett materiale.
        if (n != 0x81) {
            this.sendError(player);
            return;
        }

        //Les data for brukt materiale.
        pd.readIntBE();

        int materialID = pd.readIntBE(); //Item ID til materiale som brukes.

        int cID = pd.readIntBE(); //Item container der materiale er plassert.
        pd.readIntBE();

        pd.readLongBE();
        pd.readIntBE();

        pd.readIntBE(); //Ukjent INT.

        long antallBrukt = pd.readLongBE(); //Hvor mye av materiale som skal brukes.

        //Sjekk at våpen kan craftes.
        WeaponCraftingStat wStat = WeaponCraftingManager.getCraftingStat(craftItemID);

        if (wStat == null) {
            //Ugyldig item ID.
            System.out.println("Opcode 0x28: craftWeapons() ERROR: Attempt to craft invalid item: " + craftItemID);
            this.sendError(player);
            return;
        }

        //Sjekk at oppgitt materiale og antall er gyldig
        if (wStat.getMaterialID() != materialID || wStat.getMaterialAmount() != antallBrukt) {
            //Ugyldig materiale eller mengde.
            System.out.println("Opcode 0x28: craftWeapons() ERROR: Invalid material. Expected material:" + wStat.getMaterialID()
                    + " amount:" + wStat.getMaterialAmount() + " Received material:" + materialID + " amount:" + antallBrukt
                    + ". Item ID:" + craftItemID);
            this.sendError(player);
            return;
        }

        //Sjekk at oppgitt itemcontainer er gyldig.
        ItemContainer ic = this.validateIC(cID, materialID, antallBrukt);
        if (ic == null) {
            System.out.println("Opcode 0x28, Craft Weapons: Container validation failed. IP:" + player.getIP() + " ID:" + player.getCharacter().getCharacterID());
            player.disconnect();
            return;
        }

        //Beregn resultat av crafting her.
        int skill = player.getCharacter().getSkills().getCraftingSkill(characters.SkillList.ArmsConstruction.key());
        WeaponCraftingResult res = crafting.weapons.WeaponCraftingManager.crafting(craftItemID, skill);

        if (res == null) {
            this.sendError(player);
            return;
        }

        /*
         * OK, crafting har blitt utført. 
         * 
         * Fjern brukt materiale fra spillerens inventory og plasser resultatet i factory.
         */
        ic.settAntall(ic.getAntall() - antallBrukt);

        if (ic.getAntall() == 0) {
            //Alle materialene ble brukt, fjern itemcontainer fra inventory.
            //Det er trygt å anta at spiller er i et vehicle.
            player.getCharacter().getVehicle().getInventory().removeItemContainer(ic);
        }

        //Ny container til å holde resultatet.
        ItemContainer resIC = ContainerList.newItemContainer();
        Item i = null;

        if (res.isSuccess() && res.getAntall() > 0) {

            i = new Weapon(res.getItemID());
        } else if (!res.isSuccess() && res.getAntall() > 0) {
            //Når crafting mislykkes returneres materialer, som er general item.
            i = new GeneralItem(res.getItemID());
        }

        //Dersom vi fikk en item tilbake plasser den i factory.
        if (i != null) {

            resIC.addItem(i);
            resIC.settAntall(res.getAntall());

            player.getCharacter().getProductiveContainer().addItemContainer(resIC);
        }

        //Send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeShortBE((short) 0x4);

        if (res.isSuccess()) {
            svar.writeShortBE((short) 0x2);
        } else {
            svar.writeShortBE((short) 0xC);
        }

        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(cityID);
        svar.writeIntBE(IDa);
        svar.writeIntBE(IDb);
        svar.writeIntBE(craftItemID);
        svar.writeIntBE(0x1);

        svar.writeIntBE(player.getCharacter().getProductiveContainer().getID());
        svar.writeIntBE(player.getCharacter().getProductiveContainer().getContainerTail());

        svar.writeIntBE(IDc);
        svar.writeIntBE(resIC.getCreateTime());

        svar.writeByte((byte) 0x81);

        //Sjekk om materialet i inventory ble brukt opp eller om det er noe igjen.
        if (ic.getAntall() > 0) {
            svar.writeIntBE(0x9);
        } else {
            svar.writeIntBE(0x8);
        }

        svar.writeIntBE(ic.getItem().getID());

        svar.writeIntBE(ic.getID());
        svar.writeIntBE(ic.getContainerTail());

        //Det er trygt å anta at materiale ALLTID oppbevares i inventory til vehicle.
        //Sjekk at spiller er i vehicle, kan skje dersom spiller blir angrepet samtidig crafting startes.
        if (player.getCharacter().getVehicle() != null) {
            svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getID());
            svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getContainerTail());
            svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getStatiskID());
        } else {
            svar.writeIntBE(0x0);
            svar.writeIntBE(0x0);
            svar.writeIntBE(-1);
        }

        svar.writeIntBE(0x1);

        svar.writeLongBE(antallBrukt);

        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x0);
        svar.writeLongBE(0x0);

        //Skriv ut info om resultatet.
        if (res.getAntall() > 0) {
            //OK, vi skal returnere en eller annen item.
            svar.writeByte((byte) 0x81);
            svar.writeIntBE(resIC.getItem().getID());
            svar.writeIntBE((int) resIC.getAntall());
            svar.writeIntBE(0x0);
        } else {
            //Ingenting skal returneres til spilleren.
            svar.writeByte((byte) 0x80);
        }

        svar.writeByte((byte) 0x0);

        Packet svarPakke = new Packet(0x8028, svar.getData());
        player.sendPacket(svarPakke);

        //Sett crafting string for spillerens productive container.
        String s = "1,4," + craftItemID + "," + cityID + "," + IDb + "," + IDa + "," + resIC.getCreateTime() + ",1,2,0";
        player.getCharacter().getProductiveContainer().setCraftingString(s);

        //Dersom spiller ikke får noe tilbake fjern itemcontainer fra listen.
        if (!res.isSuccess() && res.getAntall() == 0) {
            ContainerList.removeContainer(resIC.getID());
        }

        //Skilltraining,
        gameServer.SkillTraining.trainArmsDevelopment(player.getCharacter(), wStat.getDifficulty(), wStat.getMinSkill());
    }

    /**
     * Håndterer opcode 0x28 når spilleren oppgraderer vehicle.
     *
     * @param player Spiller som utfører crafting.
     * @param pd Data sendt i opcode 0x28, to første INTs antaes lest.
     * @param upgradeType Hvilken type oppgradering som skal utføres, første
     * SHORT i opcode 0x28.
     */
    private void upgradeVehicle(PlayerGame player, PacketData pd, int upgradeType) {
        player.getCharacter().setOccupancyTag(4);

        int cityID = pd.readIntBE();
        int IDa = pd.readIntBE();
        int IDb = pd.readIntBE();

        int craftItemID = pd.readIntBE(); //ID til item som skal craftes.
        pd.readIntBE(); //Antall, alltid 1 for vehicle upgrade uansett.

        pd.readLongBE();
        int IDc = pd.readIntBE(); //Ukjent INT.
        pd.readIntBE();

        int n = (int) (pd.readByte() & 0xFF);
        //Neste byte skal være 0x81, alle oppgradering bruker ett materiale.
        if (n != 0x81) {
            this.sendError(player);
            return;
        }

        //Les data for brukt materiale.
        pd.readIntBE();

        int materialID = pd.readIntBE(); //Item ID til materiale som brukes.

        int cID = pd.readIntBE(); //Item container der materiale er plassert.
        pd.readIntBE();

        pd.readLongBE();
        pd.readIntBE();

        pd.readIntBE(); //Ukjent INT.

        long antallBrukt = pd.readLongBE(); //Hvor mye av materiale som skal brukes.

        pd.readByte();
        byte improveLevel = pd.readByte(); //Hvilket level vi oppgraderer til.
        int vehicleICid = pd.readIntBE(); //ID til item container der vehicle er.

        //Sjekk at oppgitt itemcontainer er gyldig.
        ItemContainer ic = this.validateIC(cID, materialID, antallBrukt);
        if (ic == null) {
            System.out.println("Opcode 0x28, Upgrade Vehicle: Container validation failed. IP:" + player.getIP() + " ID:" + player.getCharacter().getCharacterID());
            player.disconnect();
            return;
        }

        //Sjekk at oppgitt container for vehicle som oppgraderes er gyldig.
        ItemContainer vehicleIC = this.validateIC(vehicleICid, craftItemID, 1);
        if (vehicleIC == null) {
            System.out.println("Opcode 0x28, Upgrade Vehicle Container: Container validation failed. IP:" + player.getIP() + " ID:" + player.getCharacter().getCharacterID());
            player.disconnect();
            return;
        }

        //Sjekk også at item container inneholder et vehicle.
        if (!(vehicleIC.getItem() instanceof Vehicle)) {
            return;
        }

        //Beregn resultat av crafting her.
        int skill = player.getCharacter().getSkills().getCraftingSkill(characters.SkillList.MSMAConstruction.key());
        boolean res = VehicleCraftingManager.vehicleUpgrade(craftItemID, skill, improveLevel);

        if (res) {
            //Oppgradering var vellykket. Oppdater vehicle.
            Vehicle v = (Vehicle) vehicleIC.getItem();

            //Sjekk at vehicle ikke allerede har 8 upgrades totalt.
            if (v.getTotalUpgrade() >= 8) {
                this.sendError(player);
                return;
            }

            if (upgradeType == 0x8) {
                v.setUpgradePower(v.getUpgradePower() + 1);
            }
            if (upgradeType == 0x9) {
                v.setUpgradeHit(v.getUpgradeHit() + 1);
            }
            if (upgradeType == 0xA) {
                v.setUpgradeDefence(v.getUpgradeDefence() + 1);
            }
        }

        //Fjern brukte materialer fra item container.
        ic.settAntall(ic.getAntall() - antallBrukt);
        if (ic.getAntall() <= 0) {
            //Item container tom, fjern den fra spillers inventory.
            player.getCharacter().getVehicle().getInventory().removeItemContainer(ic);
        }

        //OK, send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeShortBE((short) upgradeType);

        if (res) {
            svar.writeShortBE((short) 0x2);
        } else {
            svar.writeShortBE((short) 0xC);
        }

        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(cityID);
        svar.writeIntBE(IDa);
        svar.writeIntBE(IDb);
        svar.writeIntBE(craftItemID);
        svar.writeIntBE(0x1);

        svar.writeIntBE(player.getCharacter().getProductiveContainer().getID());
        svar.writeIntBE(player.getCharacter().getProductiveContainer().getContainerTail());

        svar.writeIntBE(IDc);
        svar.writeIntBE((int) (System.currentTimeMillis() / 1000));

        svar.writeByte((byte) 0x81);

        //Sjekk om materialet i inventory ble brukt opp eller om det er noe igjen.
        if (ic.getAntall() > 0) {
            svar.writeIntBE(0x9);
        } else {
            svar.writeIntBE(0x8);
        }

        svar.writeIntBE(ic.getItem().getID());

        svar.writeIntBE(ic.getID());
        svar.writeIntBE(ic.getContainerTail());

        //Det er trygt å anta at materiale ALLTID oppbevares i inventory til vehicle.
        //Sjekk spiller er i vehicle, kan skje dersom spiller angripes samtidig som crafting starter.
        if (player.getCharacter().getVehicle() != null) {
            svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getID());
            svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getContainerTail());
            svar.writeIntBE(player.getCharacter().getVehicle().getInventory().getStatiskID());
        } else {
            svar.writeLongBE(0x0);
            svar.writeIntBE(-1);
        }

        svar.writeIntBE(0x1);

        svar.writeLongBE(antallBrukt);

        svar.writeByte((byte) -1);
        svar.writeByte((byte) improveLevel);
        svar.writeIntBE(vehicleICid);
        svar.writeIntBE(0x14);

        svar.writeByte((byte) 0x80);

        Packet svarPakke = new Packet(0x8028, svar.getData());

        player.sendPacket(svarPakke);

        //Crafting string settes ikke ved oppgradering.	
        //Skilltraining.        
        VehicleCraftingStat vStat = VehicleCraftingManager.getCraftingStat(craftItemID);

        //Dersom vehicle bruker FL, FTCC, JP, GA for crafting gies det bonus.
        int materialCount = 0;

        if (vStat.getMaterialID() == 0x07C843 || vStat.getMaterialID() == 0x07C844 || vStat.getMaterialID() == 0x07C84D || vStat.getMaterialID() == 0x07C84E) {
            materialCount = vStat.getMaterialAmount();
        }

        gameServer.SkillTraining.trainMsConstruction(player.getCharacter(), vStat.getDifficulty(), vStat.getMinSkill(), true, false, materialCount);
        gameServer.SkillTraining.trainBattleshipConstruction(player.getCharacter(), vStat.getDifficulty(), vStat.getMinSkill(), true, false);
    }

    /**
     * Håndterer opcode 0x28 når spilleren dismantler vehicle.
     *
     * @param player Spiller som utfører crafting.
     * @param pd Data sendt i opcode 0x28, to første INTs antaes lest.
     *
     */
    private void dismantleVehicle(PlayerGame player, PacketData pd) {
        player.getCharacter().setOccupancyTag(3);

        int cityID = pd.readIntBE();
        int IDa = pd.readIntBE();
        int IDb = pd.readIntBE();

        int craftItemID = pd.readIntBE(); //ID til item som skal dismantles.
        pd.readIntBE(); //Antall, alltid 1 for vehicle dismantle uansett.

        pd.readLongBE();
        int IDc = pd.readIntBE(); //Ukjent INT.
        pd.readIntBE();

        int n = (int) (pd.readByte() & 0xFF);
        //Neste byte skal være 0x81, all dismantling bruker ett vehicle.
        if (n != 0x81) {
            this.sendError(player);
            return;
        }

        //Les data for brukt materiale.
        pd.readIntBE();

        int materialID = pd.readIntBE(); //Item ID til vehicle som dismantles.

        int cID = pd.readIntBE(); //Item container der vehicle er plassert.
        pd.readIntBE();

        pd.readLongBE();
        pd.readIntBE();

        pd.readIntBE(); //Ukjent INT.

        pd.readLongBE(); //Antall er alltid 1 for vehicle dismantle.

        //Sjekk at oppgitt itemcontainer er gyldig.
        ItemContainer ic = this.validateIC(cID, materialID, 1);
        if (ic == null) {
            System.out.println("Opcode 0x28, Dismantle Vehicle: Container validation failed. IP:" + player.getIP() + " ID:" + player.getCharacter().getCharacterID());
            player.disconnect();
            return;
        }

        //Sjekk også at item container inneholder et vehicle.
        if (!(ic.getItem() instanceof Vehicle)) {
            return;
        }

        //Sjekk at oppgitt MS/Vehicle kan craftes.
        VehicleCraftingStat vStat = VehicleCraftingManager.getCraftingStat(craftItemID);

        if (vStat == null) {

            System.out.println("Opcode 0x28: dismantleVehicle() ERROR: Attempt to dismantle invalid item: " + craftItemID);
            this.sendError(player);
            return;
        }

        //Beregn resultatet av dismantle. Gjøres på samme måte som oppgradering.
        int skill = player.getCharacter().getSkills().getCraftingSkill(characters.SkillList.MSMAConstruction.key());
        boolean res = VehicleCraftingManager.vehicleUpgrade(craftItemID, skill, 0);

        ItemContainer resIC = ContainerList.newItemContainer();

        if (res) {
            //Velykket dismantle, returner 70% av materiale.
            GeneralItem i = new GeneralItem(vStat.getMaterialID());
            resIC.addItem(i);
            resIC.settAntall((vStat.getMaterialAmount() * 70) / 100);
        } else {
            //Mislykkes dismantle, returner 50% av materiale.
            GeneralItem i = new GeneralItem(vStat.getMaterialID());
            resIC.addItem(i);
            resIC.settAntall((vStat.getMaterialAmount() * 50) / 100);
        }

        //ItemContainer vehicle var i skal fjernes.
        ContainerList.removeContainer(ic.getID());
        player.getCharacter().getProductiveContainer().emptyContainer();

        //Plasser resultatet i factory vinduet.
        player.getCharacter().getProductiveContainer().addItemContainer(resIC);

        //Send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeShortBE((short) 0x5);

        if (res) {
            svar.writeShortBE((short) 0x2);
        } else {
            svar.writeShortBE((short) 0xC);
        }

        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(cityID);
        svar.writeIntBE(IDa);
        svar.writeIntBE(IDb);
        svar.writeIntBE(craftItemID);
        svar.writeIntBE(0x1);

        svar.writeIntBE(player.getCharacter().getProductiveContainer().getID());
        svar.writeIntBE(player.getCharacter().getProductiveContainer().getContainerTail());

        svar.writeIntBE(IDc);
        svar.writeIntBE(-1);

        svar.writeByte((byte) 0x81);

        svar.writeIntBE(0x9);

        svar.writeIntBE(ic.getItem().getID());

        svar.writeIntBE(ic.getID());
        svar.writeIntBE(ic.getContainerTail());

        //Det er trygt å anta at vehicle alltid er i productive container.
        svar.writeIntBE(player.getCharacter().getProductiveContainer().getID());
        svar.writeIntBE(player.getCharacter().getProductiveContainer().getContainerTail());
        svar.writeIntBE(player.getCharacter().getProductiveContainer().getStatiskID());

        svar.writeIntBE(0x1);

        svar.writeLongBE(1);

        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x0);
        svar.writeLongBE(0x0);

        svar.writeByte((byte) 0x81);

        //Skriv ut info for resultatet.
        svar.writeIntBE(resIC.getItem().getID());
        svar.writeIntBE((int) resIC.getAntall());
        svar.writeIntBE(0x0);

        Packet svarPakke = new Packet(0x8028, svar.getData());

        player.sendPacket(svarPakke);

        //Sett crafting string for spillerens productive container.
        String s = "1,5," + craftItemID + "," + cityID + "," + IDb + "," + IDa + ",-1,1,2,0";
        player.getCharacter().getProductiveContainer().setCraftingString(s);

        //Skilltraining.
        //Dersom vehicle bruker FL, FTCC, JP, GA for crafting gies det bonus.
        int materialCount = 0;

        if (vStat.getMaterialID() == 0x07C843 || vStat.getMaterialID() == 0x07C844 || vStat.getMaterialID() == 0x07C84D || vStat.getMaterialID() == 0x07C84E) {
            materialCount = vStat.getMaterialAmount();
        }

        gameServer.SkillTraining.trainMsConstruction(player.getCharacter(), vStat.getDifficulty(), vStat.getMinSkill(), false, true, materialCount);
        gameServer.SkillTraining.trainBattleshipConstruction(player.getCharacter(), vStat.getDifficulty(), vStat.getMinSkill(), false, true);
    }

    /**
     * Håndterer opcode 0x28 når det er clothes crafting som utføres.
     *
     * NB! Når clothes crafting utføres stoler vi på data sent fra klienten og
     * sjekker ikke at oppgitte materialer og mengde er riktig.
     *
     * @param player Spiller som utfører crafting.
     * @param pd Data sendt i opcode 0x28, to første INTs antaes lest.
     */
    private void craftClothes(PlayerGame player, PacketData pd) {
        player.getCharacter().setOccupancyTag(2);

        int cityID = pd.readIntBE();
        int IDa = pd.readIntBE();
        int IDb = pd.readIntBE();

        int craftItemID = pd.readIntBE(); //ID til item som skal craftes.
        pd.readIntBE(); //Antall, alltid 1 for klær uansett.

        pd.readLongBE();
        int IDc = pd.readIntBE(); //Ukjent INT.
        pd.readIntBE();

        int n = (int) (pd.readByte() & 0xFF);

        //n=Antall materialer som skal brukes, klær kan bruke ett (0x81) eller to (0x82) materialer.
        //Les info for material #1.
        pd.readIntBE();
        int material1ID = pd.readIntBE(); //Item ID til material #1
        int m1icID = pd.readIntBE(); //Container ID til IC der material #1 er.
        pd.readIntBE();

        pd.readLongBE(); //HC der material er, ikke viktig.
        pd.readIntBE();

        pd.readIntBE();

        long m1Antall = pd.readLongBE();

        //Les info for material #2 hvis nødvendig.
        int material2ID = 0;
        int m2icID = 0;
        long m2Antall = 0;

        if (n == 0x82) {
            pd.readIntBE();

            material2ID = pd.readIntBE(); //Item ID til material #2
            m2icID = pd.readIntBE(); //Container ID til IC der material #2 er.
            pd.readIntBE();

            pd.readLongBE(); //HC der material er, ikke viktig.
            pd.readIntBE();

            pd.readIntBE();

            m2Antall = pd.readLongBE();
        }

        int color = (pd.readByte() & 0xFF); //Fargen til klesplagget.

        //Sjekk at klesplagget kan craftes.
        ClothesCraftingStat cStat = ClothesCraftingManager.getCraftingStat(craftItemID);

        if (cStat == null) {
            //Ugyldig item ID.
            System.out.println("Opcode 0x28: craftClothes() ERROR: Attempt to craft invalid item: " + craftItemID);
            this.sendError(player);
            return;
        }

        //Sjekk at oppgitte itemcontainere er gyldig.
        ItemContainer m1IC = this.validateIC(m1icID, material1ID, m1Antall);
        if (m1IC == null) {
            System.out.println("Opcode 0x28, Craft Clothes: Container validation failed. IP:" + player.getIP() + " ID:" + player.getCharacter().getCharacterID());
            player.disconnect();
            return;
        }

        ItemContainer m2IC = null;
        //Sjekk om vi bruker to materialer.
        if (n == 0x82) {
            m2IC = this.validateIC(m2icID, material2ID, m2Antall);
            if (m2IC == null) {
                return;
            }
        }

        //Beregn resultat av crafting her.
        int skill = player.getCharacter().getSkills().getCraftingSkill(characters.SkillList.ClothingManufacturing.key());
        ClothesCraftingResult res = crafting.clothes.ClothesCraftingManager.crafting(craftItemID, skill);

        if (res == null) {
            this.sendError(player);
            return;
        }

        //Fjern brukte materialer fra item containere.
        m1IC.settAntall(m1IC.getAntall() - m1Antall);
        //Hvis IC er tom fjern den fra backpack. IC er alltid i backpack når clothes crafting utføres.
        if (m1IC.getAntall() == 0) {
            containers.ContainerList.removeContainer(m1IC.getID());
            player.getCharacter().getBackpackContainer().removeItemContainer(m1IC);
        }

        if (m2IC != null) {
            m2IC.settAntall(m2IC.getAntall() - m2Antall);

            //Hvis IC er tom fjern den fra backpack. IC er alltid i backpack når clothes crafting utføres.
            if (m2IC.getAntall() == 0) {
                containers.ContainerList.removeContainer(m2IC.getID());
                player.getCharacter().getBackpackContainer().removeItemContainer(m2IC);
            }
        }

        //Plasser resultatet i factory
        ItemContainer res1IC = null;
        ItemContainer res2IC = null;

        if (res.isSuccess()) {
            //Vellykket crafting, plasser klesplagget i factory.
            Clothing i = null;

            //Sjekk om vi fikk GQ versjon.
            if (res.isGQ()) {

                StatGQclothes gqStat = gameData.StatManager.getGQclothesStat(cStat.getItemGQid());

                if (gqStat != null) {
                    //Opprett nytt GQ klesplagg.
                    i = new Clothing(cStat.getItemGQid(), color, gqStat.getStrength(), gqStat.getSpirit(), gqStat.getLuck());
                } else {
                    //Stats mangler for GQ klesplagg. Opprett standard versjon og skriv ut feilmelding.
                    i = new Clothing(craftItemID, color);

                    System.out.println("Opcode 0x28: craftClothes() ERROR: Missing stats for GQ version, item ID: " + craftItemID);
                }

            } else {
                i = new Clothing(craftItemID, color);
            }

            res1IC = ContainerList.newItemContainer();
            res1IC.addItem(i);
        } else {
            //Mislykket crafting, plasser gjenværende materialer i factory.
            long m1n = m1Antall - (m1Antall * cStat.getMaterialLoss() / 100);

            //Sjekk at vi skal returnere noe.
            if (m1n > 0) {
                GeneralItem i1 = new GeneralItem(material1ID);
                res1IC = ContainerList.newItemContainer();
                res1IC.addItem(i1);
                res1IC.settAntall(m1n);
            }

            //Sjekk om to materialer ble brukt.
            if (n == 0x82) {
                long m2n = m2Antall - (m2Antall * cStat.getMaterialLoss() / 100);

                //Sjekk at vi skal returnere noe.
                if (m2n > 0) {
                    GeneralItem i2 = new GeneralItem(material2ID);
                    res2IC = ContainerList.newItemContainer();
                    res2IC.addItem(i2);
                    res2IC.settAntall(m2n);
                }
            }
        }

        //Plasser resultatet i factory.	
        if (res1IC != null) {
            player.getCharacter().getProductiveContainer().addItemContainer(res1IC);
        }
        if (res2IC != null) {
            player.getCharacter().getProductiveContainer().addItemContainer(res2IC);
        }

        //Send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeShortBE((short) 0x3);

        if (res.isSuccess()) {
            svar.writeShortBE((short) 0x2);
        } else {
            svar.writeShortBE((short) 0xC);
        }

        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(cityID);
        svar.writeIntBE(IDa);
        svar.writeIntBE(IDb);
        svar.writeIntBE(craftItemID);
        svar.writeIntBE(0x1);

        svar.writeIntBE(player.getCharacter().getProductiveContainer().getID());
        svar.writeIntBE(player.getCharacter().getProductiveContainer().getContainerTail());

        svar.writeIntBE(IDc);
        svar.writeIntBE((int) (System.currentTimeMillis() / 1000));

        svar.writeByte((byte) n);

        //Sjekk om materialet i inventory ble brukt opp eller om det er noe igjen.
        if (m1IC.getAntall() > 0) {
            svar.writeIntBE(0x9);
        } else {
            svar.writeIntBE(0x8);
        }

        svar.writeIntBE(m1IC.getItem().getID());

        svar.writeIntBE(m1IC.getID());
        svar.writeIntBE(m1IC.getContainerTail());

        //Det er trygt å anta at materiale ALLTID oppbevares i backpack.
        svar.writeIntBE(player.getCharacter().getBackpackContainer().getID());
        svar.writeIntBE(player.getCharacter().getBackpackContainer().getContainerTail());
        svar.writeIntBE(player.getCharacter().getBackpackContainer().getStatiskID());

        svar.writeIntBE(0x1);

        svar.writeLongBE(m1Antall);

        if (n == 0x82) {
            //Skriv data for material 2.
            //Sjekk om materialet i inventory ble brukt opp eller om det er noe igjen.
            if (m2IC.getAntall() > 0) {
                svar.writeIntBE(0x9);
            } else {
                svar.writeIntBE(0x8);
            }

            svar.writeIntBE(m2IC.getItem().getID());

            svar.writeIntBE(m2IC.getID());
            svar.writeIntBE(m2IC.getContainerTail());

            //Det er trygt å anta at materiale ALLTID oppbevares i backpack.
            svar.writeIntBE(player.getCharacter().getBackpackContainer().getID());
            svar.writeIntBE(player.getCharacter().getBackpackContainer().getContainerTail());
            svar.writeIntBE(player.getCharacter().getBackpackContainer().getStatiskID());

            svar.writeIntBE(0x1);

            svar.writeLongBE(m2Antall);
        }

        svar.writeByte((byte) color);
        svar.writeByte((byte) 0x0);
        svar.writeLongBE(0x0);

        //Skriv ut info om resultatet.
        if (res1IC != null && res2IC != null) {
            svar.writeByte((byte) 0x82);
        } else if (res1IC != null || res2IC != null) {
            svar.writeByte((byte) 0x81);
        } else {
            svar.writeByte((byte) 0x80);
        }

        if (res1IC != null) {
            svar.writeIntBE(res1IC.getItem().getID());
            svar.writeIntBE((int) res1IC.getAntall());
            svar.writeIntBE(0x0);
        }

        if (res2IC != null) {
            svar.writeIntBE(res2IC.getItem().getID());
            svar.writeIntBE((int) res2IC.getAntall());
            svar.writeIntBE(0x0);
        }

        svar.writeByte((byte) 0x0);

        Packet svarPakke = new Packet(0x8028, svar.getData());
        player.sendPacket(svarPakke);

        //Sett crafting string for spillerens productive container.		
        String s = "1,3," + craftItemID + "," + cityID + "," + IDb + "," + IDa + "," + (System.currentTimeMillis() / 1000) + ",1,2,0";
        player.getCharacter().getProductiveContainer().setCraftingString(s);

        //Skilltraining.
        gameServer.SkillTraining.trainClothesManufacturing(player.getCharacter(), cStat.getDifficulty(), cStat.getMinSkill());
    }

    /**
     * Håndterer opcode 0x28 når det er refining som utføres.
     *
     *
     * @param player Spiller som utfører crafting.
     * @param pd Data sendt i opcode 0x28, to første INTs antaes lest.
     */
    private void craftRefining(PlayerGame player, PacketData pd) {
        player.getCharacter().setOccupancyTag(5);

        int cityID = pd.readIntBE();
        int IDa = pd.readIntBE();
        int IDb = pd.readIntBE();

        int craftItemID = pd.readIntBE(); //ID til item som skal craftes.
        int antall = pd.readIntBE(); //Antall som skal refines av materiale.

        pd.readLongBE();
        int IDc = pd.readIntBE(); //Ukjent INT.
        pd.readIntBE();

        int n = (int) (pd.readByte() & 0xFF);

        //n=Antall materialer som skal brukes, refining kan bruke ett (0x81) eller to (0x82) materialer.
        //Les info for material #1.
        pd.readIntBE();
        int material1ID = pd.readIntBE(); //Item ID til material #1
        int m1icID = pd.readIntBE(); //Container ID til IC der material #1 er.
        pd.readIntBE();

        pd.readLongBE(); //HC der material er, ikke viktig.
        pd.readIntBE();

        pd.readIntBE();

        long m1Antall = pd.readLongBE();

        //Les info for material #2 hvis nødvendig.
        int material2ID = 0;
        int m2icID = 0;
        long m2Antall = 0;

        if (n == 0x82) {
            pd.readIntBE();

            material2ID = pd.readIntBE(); //Item ID til material #2
            m2icID = pd.readIntBE(); //Container ID til IC der material #2 er.
            pd.readIntBE();

            pd.readLongBE(); //HC der material er, ikke viktig.
            pd.readIntBE();

            pd.readIntBE();

            m2Antall = pd.readLongBE();
        }

        //Sjekk at materialet kan craftes.
        RefiningCraftingStat rStat = RefiningCraftingManager.getCraftingStat(craftItemID);

        if (rStat == null) {
            //Ugyldig item ID.
            System.out.println("Opcode 0x28: craftRefining() ERROR: Attempt to craft invalid item: " + craftItemID);
            this.sendError(player);
            return;
        }

        //Sjekk at oppgitte materialer og antall er gyldig.
        //Material #1
        if (rStat.getMaterialIdA() != material1ID || (rStat.getMaterialAmountA() * antall) != m1Antall) {
            //Ugyldig materiale eller mengde.
            System.out.println("Opcode 0x28: craftRefining() ERROR: Invalid material. Expected material:" + rStat.getMaterialIdA()
                    + " amount:" + rStat.getMaterialAmountA() + " Received material:" + material1ID + " amount:" + m1Antall
                    + ". Item ID:" + craftItemID);
            this.sendError(player);
            return;
        }

        //Sjekk at material #2 er gyldig.
        if (material2ID != 0) {

            if (rStat.getMaterialIdB() != material2ID || (rStat.getMaterialAmountB() * antall) != m2Antall) {
                //Ugyldig materiale eller mengde.
                System.out.println("Opcode 0x28: craftRefining() ERROR: Invalid material. Expected material:" + rStat.getMaterialIdB()
                        + " amount:" + rStat.getMaterialAmountB() + " Received material:" + material2ID + " amount:" + m2Antall
                        + ". Item ID:" + craftItemID);
                this.sendError(player);
                return;
            }

        }

        //Sjekk at oppgitte itemcontainere er gyldig.
        ItemContainer m1IC = this.validateIC(m1icID, material1ID, m1Antall);
        if (m1IC == null) {
            System.out.println("Opcode 0x28, Refining: Container validation failed. IP:" + player.getIP() + " ID:" + player.getCharacter().getCharacterID());
            player.disconnect();
            return;
        }

        ItemContainer m2IC = null;
        //Sjekk om vi bruker to materialer.
        if (n == 0x82) {
            m2IC = this.validateIC(m2icID, material2ID, m2Antall);
            if (m2IC == null) {
                System.out.println("Opcode 0x28, Refining 2nd Material: Container validation failed. IP:" + player.getIP() + " ID:" + player.getCharacter().getCharacterID());
                player.disconnect();
                return;
            }
        }

        //Beregn resultat av crafting her.
        int skill = player.getCharacter().getSkills().getCraftingSkill(characters.SkillList.Refinery.key());
        RefiningCraftingResult res = RefiningCraftingManager.crafting(craftItemID, skill);

        if (res == null) {
            this.sendError(player);
            return;
        }

        //Fjern brukte materialer fra item containere.
        m1IC.settAntall(m1IC.getAntall() - m1Antall);
        //Hvis IC er tom fjern den fra inventory. IC er alltid i spillers vehicle.
        if (m1IC.getAntall() == 0) {
            containers.ContainerList.removeContainer(m1IC.getID());
            player.getCharacter().getVehicle().getInventory().removeItemContainer(m1IC);
        }

        if (m2IC != null) {
            m2IC.settAntall(m2IC.getAntall() - m2Antall);

            //Hvis IC er tom fjern den fra inventory. IC er alltid i spillers vehicle.
            if (m2IC.getAntall() == 0) {
                containers.ContainerList.removeContainer(m2IC.getID());
                player.getCharacter().getVehicle().getInventory().removeItemContainer(m2IC);
            }
        }

        //Plasser resultatet i factory
        ItemContainer res1IC = null;
        ItemContainer res2IC = null;

        if (res.isSuccess()) {
            //Vellykket crafting, plasser materialet i factory.
            GeneralItem i = new GeneralItem(craftItemID);

            res1IC = ContainerList.newItemContainer();
            res1IC.addItem(i);
            res1IC.settAntall((int) (antall * 1.10)); //Gi 10% bonus til materiale.

        } else {
            //Mislykket crafting, plasser gjenværende materialer i factory.

            //Sjekk at vi skal returnere noe.
            if (res.getMaterialIdA() != 0 && res.getMaterialAmountA() > 0) {
                GeneralItem i1 = new GeneralItem(material1ID);
                res1IC = ContainerList.newItemContainer();
                res1IC.addItem(i1);
                res1IC.settAntall(res.getMaterialAmountA() * antall);
            }

            //Sjekk om to materialer ble brukt.
            if (n == 0x82) {

                //Sjekk at vi skal returnere noe.
                if (res.getMaterialIdB() != 0 && res.getMaterialAmountB() > 0) {
                    GeneralItem i2 = new GeneralItem(material2ID);
                    res2IC = ContainerList.newItemContainer();
                    res2IC.addItem(i2);
                    res2IC.settAntall(res.getMaterialAmountB() * antall);
                }
            }
        }

        //Plasser resultatet i factory.	
        if (res1IC != null) {
            player.getCharacter().getProductiveContainer().addItemContainer(res1IC);
        }
        if (res2IC != null) {
            player.getCharacter().getProductiveContainer().addItemContainer(res2IC);
        }

        //Send svar tilbake.
        PacketData svar = new PacketData();

        svar.writeShortBE((short) 0x3);

        if (res.isSuccess()) {
            svar.writeShortBE((short) 0x2);
        } else {
            svar.writeShortBE((short) 0xC);
        }

        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeIntBE(cityID);
        svar.writeIntBE(IDa);
        svar.writeIntBE(IDb);
        svar.writeIntBE(craftItemID);
        svar.writeIntBE(0x1);

        svar.writeIntBE(player.getCharacter().getProductiveContainer().getID());
        svar.writeIntBE(player.getCharacter().getProductiveContainer().getContainerTail());

        svar.writeIntBE(IDc);
        svar.writeIntBE((int) (System.currentTimeMillis() / 1000));

        svar.writeByte((byte) n);

        //Sjekk om materialet i inventory ble brukt opp eller om det er noe igjen.
        if (m1IC.getAntall() > 0) {
            svar.writeIntBE(0x9);
        } else {
            svar.writeIntBE(0x8);
        }

        svar.writeIntBE(m1IC.getItem().getID());

        svar.writeIntBE(m1IC.getID());
        svar.writeIntBE(m1IC.getContainerTail());

        //Det er trygt å anta at materiale ALLTID oppbevares i backpack.
        svar.writeIntBE(player.getCharacter().getBackpackContainer().getID());
        svar.writeIntBE(player.getCharacter().getBackpackContainer().getContainerTail());
        svar.writeIntBE(player.getCharacter().getBackpackContainer().getStatiskID());

        svar.writeIntBE(0x1);

        svar.writeLongBE(m1Antall);

        if (n == 0x82) {
            //Skriv data for material 2.
            //Sjekk om materialet i inventory ble brukt opp eller om det er noe igjen.
            if (m2IC.getAntall() > 0) {
                svar.writeIntBE(0x9);
            } else {
                svar.writeIntBE(0x8);
            }

            svar.writeIntBE(m2IC.getItem().getID());

            svar.writeIntBE(m2IC.getID());
            svar.writeIntBE(m2IC.getContainerTail());

            //Det er trygt å anta at materiale ALLTID oppbevares i backpack.
            svar.writeIntBE(player.getCharacter().getBackpackContainer().getID());
            svar.writeIntBE(player.getCharacter().getBackpackContainer().getContainerTail());
            svar.writeIntBE(player.getCharacter().getBackpackContainer().getStatiskID());

            svar.writeIntBE(0x1);

            svar.writeLongBE(m2Antall);
        }

        svar.writeByte((byte) -1);
        svar.writeByte((byte) 0x0);
        svar.writeLongBE(0x0);

        //Skriv ut info om resultatet.
        if (res1IC != null && res2IC != null) {
            svar.writeByte((byte) 0x82);
        } else if (res1IC != null || res2IC != null) {
            svar.writeByte((byte) 0x81);
        } else {
            svar.writeByte((byte) 0x80);
        }

        if (res1IC != null) {
            svar.writeIntBE(res1IC.getItem().getID());
            svar.writeIntBE((int) res1IC.getAntall());
            svar.writeIntBE(0x0);
        }

        if (res2IC != null) {
            svar.writeIntBE(res2IC.getItem().getID());
            svar.writeIntBE((int) res2IC.getAntall());
            svar.writeIntBE(0x0);
        }

        svar.writeByte((byte) 0x0);

        Packet svarPakke = new Packet(0x8028, svar.getData());
        player.sendPacket(svarPakke);

        //Sett crafting string for spillerens productive container.		
        String s = "1,2," + craftItemID + "," + cityID + "," + IDb + "," + IDa + "," + (System.currentTimeMillis() / 1000) + ",1,2,0";
        player.getCharacter().getProductiveContainer().setCraftingString(s);

        //Skilltraining.
        int m = (antall / 100) + 1;
        gameServer.SkillTraining.trainRefining(player.getCharacter(), rStat.getDifficulty(), rStat.getMinSkill(), m);
    }

    /**
     * Sjekker om oppgitt ID tilhører en ItemContainer og container inneholder
     * oppgitt item og minst oppgitt antall.
     *
     * @param icID ID til item container
     * @param itemID ID til item som skal være i container.
     * @param antall Container må inneholde minst oppgitt antall av item.
     *
     * @return ItemContaineren hvis alt er OK, null hvis feil.
     */
    private ItemContainer validateIC(int icID, int itemID, long antall) {

        ItemContainer ic;

        Container con = ContainerList.getContainer(icID);

        if (con instanceof ItemContainer) {
            ic = (ItemContainer) con;
        } else {

            System.out.println("Opcode 0x28: validateIC() ERROR: Invalid container ID");
            return null;
        }

        if (ic.getItem().getID() != itemID) {

            System.out.println("Opcode 0x28: validateIC() ERROR: Invalid item in container. Found:" + ic.getItem().getID()
                    + " Expected:" + itemID);
            return null;
        }

        if (ic.getAntall() < antall) {

            /*System.out.println("Opcode 0x28: validateIC() ERROR: Invalid number of items in container. Found:"+
             ic.getAntall()+" Expected:"+antall);*/
            return null;
        }

        return ic;
    }

    /**
     * Sender en 0x8028 pakke tilbake som inneholder feilmelding. Ingen melding
     * vises på skjermen, spillet bare fortsetter. Hvis denne ikke sendes når en
     * feil oppstår vil klienten henge seg opp.
     *
     * @param p
     */
    private void sendError(Player p) {

        PacketData svar = new PacketData();

        svar.writeIntBE(0x0);

        Packet svar_pakke = new Packet(0x8028, svar.getData());

        p.sendPacket(svar_pakke);
    }
}
