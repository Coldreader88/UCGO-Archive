package opcodes.gameServer;

import containers.*;
import crafting.mining.*;
import gameServer.SkillTraining;
import items.*;
import java.util.Iterator;
import java.util.LinkedList;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x32 implements Opcode {

    /**
     * Antall items det er plass til i Thundergoliat sitt inventory.
     */
    private final static int maxItems = 10;

    /**
     * Hvor mye av raw materials og upgrade parts som kan stackes sammen.
     */
    private final static int maxMaterialAmount = 2000;

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        PlayerGame player = (PlayerGame) p;

        pd.readIntBE();

        int characterID = pd.readIntBE();

        if (characterID != player.getCharacter().getCharacterID()) {
            return;
        }

        pd.readShortBE();
        pd.readShortBE();

        int mineID = pd.readByte(); //Mine ID, hvilken mine spiller på.

        int areaValue = pd.readByte(); //Hvor bra område er å mine i.

        //Sjekk at spiller er i vehicle.
        if (player.getCharacter().getVehicle() == null) {
            //Spiller er ikke i vehicle send tom 0x8032.
            this.sendTom8032(player, mineID, areaValue);
            return;
        }

        //Da har vi lest alle viktige verdier sendt i pakken. Beregn resultat av mining.
        int miningSkill = player.getCharacter().getSkills().getCraftingSkill(characters.SkillList.Mining.key());

        //Hent ut resultatet fra mining.
        LinkedList<MiningResult> resultat = this.trimMiningResult(player, MiningManager.mining(miningSkill, areaValue));

        //Skriv pakkedata.
        PacketData svar = new PacketData();

        //Sjekk om vi fikk noen items.
        if (resultat.size() > 0) {
            svar.writeIntBE(0x00010002);
        } else {
            svar.writeIntBE(0x0001000C);
        }

        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeShortBE((short) player.getCharacter().getPosisjon().getZone());
        svar.writeShortBE((short) 0x0);

        svar.writeByte((byte) mineID);
        svar.writeByte((byte) areaValue);

        svar.writeIntBE(player.getCharacter().getVehicleContainer().getID());
        svar.writeIntBE(player.getCharacter().getVehicleContainer().getContainerTail());
        svar.writeIntBE(player.getCharacter().getVehicle().getID());

        ItemContainer con = player.getCharacter().getVehicle().getEquippedItem(player.getCharacter().getVehicle().getActiveSlot());
        svar.writeIntBE(con.getID());
        svar.writeIntBE(con.getContainerTail());

        Weapon miningDrill = (Weapon) con.getItem();
        miningDrill.applyDamage(1);
        svar.writeByte((byte) 1);

        svar.writeByte((byte) (0x80 + resultat.size()));

        //Gå gjennom alle items som ble "minet" og legg dem inn i  inventory.
        Iterator<MiningResult> res = resultat.iterator();
        HovedContainer inventory = player.getCharacter().getVehicle().getInventory();

        while (res.hasNext()) {

            MiningResult m = res.next();

            ItemContainer ic;

            if ((m.isStackable() && inventory.getItemContainer(m.getItemID()) == null) || !m.isStackable()) {
                //Item er ikke stackable ELLER item er stackable men finnes ikke i inventory fra før.
                //Altså ny container må opprettes.
                ic = ContainerList.newItemContainer();
                ic.addItem(new GeneralItem(m.getItemID()));
                ic.settAntall(m.getAntall());
                inventory.addItemContainer(ic);
            } else {
                //Item er stackable og finnes fra før. Hent ut eksisterende item container og oppdater den.
                ic = inventory.getItemContainer(m.getItemID());
                ic.settAntall(ic.getAntall() + m.getAntall());
            }

            //Skriv data for hver item som ble "minet".
            svar.writeIntBE(ic.getID());
            svar.writeIntBE(ic.getContainerTail());
            svar.writeIntBE(ic.getItem().getID());
            svar.writeLongBE(m.getAntall());
            svar.writeIntBE(0x0);

        }

        Packet svarPakke = new Packet(0x8032, svar.getData());

        p.sendPacket(svarPakke);

        //Skilltraining.
        SkillTraining.trainMining(player.getCharacter());
    }

    /**
     * Går gjennom alle items som ble "minet" og sjekker om det er plass i
     * spillerens container. Dersom det ikke er plass blir item fjernet.
     *
     * @param player Spiller som utfører mining.
     *
     * @return Ny liste over resultatet av mining.
     */
    private LinkedList<MiningResult> trimMiningResult(PlayerGame player, LinkedList<MiningResult> liste) {

        LinkedList<MiningResult> resultat = new LinkedList<MiningResult>();

        HovedContainer inventory = player.getCharacter().getVehicle().getInventory();
        //Hvor mange nye items det er plass til i inventory.
        int ledigPlass = maxItems - inventory.getContents().size();

        Iterator<MiningResult> res = liste.iterator();

        while (res.hasNext()) {

            MiningResult m = res.next();

            //Sjekk om item er stackable eller ikke.
            if (m.isStackable()) {
                //Item er stackable, sjekk om den finnes fra før i inventory.
                if (inventory.getItemContainer(m.getItemID()) != null) {
                    //Item finnes fra før, sjekk om vi har max antall eller om det er plass til flere.
                    int antall = (int) inventory.getItemContainer(m.getItemID()).getAntall();

                    if (antall < maxMaterialAmount) {
                        //OK, det er plass til flere items. Sjekk om det er plass til alle sammen eller om noen
                        //må forkastes.
                        if ((antall + m.getAntall()) > maxMaterialAmount) {
                            m.setAntall(maxMaterialAmount - antall);
                        }

                        //OK, legg til i resultatet.
                        resultat.add(m);
                    }

                } else {
                    //Stackable item finnes ikke fra før, sjekk at det er plass i inventory til den.
                    if (ledigPlass > 0) {
                        //OK, det er plass til item i inventory.
                        resultat.add(m);
                        ledigPlass--;
                    }
                    //Hvis det ikke var ledig plass blir item forkastet.
                }

            } else {
                //Item kan ikke stackes, sjekk om det er ledig plass i inventory.
                if (ledigPlass > 0) {
                    //OK, det er plass til item i inventory.
                    resultat.add(m);
                    ledigPlass--;
                }
                //Hvis det ikke var ledig plass blir item forkastet.
            }

        }

        return resultat;
    }

    /**
     * Sender en "tom" opcode 0x8032 tilbake til spiller. Dette er nødvendig i
     * de tilfeller spiller sin mining vehicle blir ødelagt mens mining pågår.
     *
     * @param player
     * @param mineID
     * @param areaValue
     */
    private void sendTom8032(PlayerGame player, int mineID, int areaValue) {
        //Skriv pakkedata.
        PacketData svar = new PacketData();

        svar.writeIntBE(0x0001000C);

        svar.writeIntBE(player.getCharacter().getCharacterID());
        svar.writeShortBE((short) player.getCharacter().getPosisjon().getZone());
        svar.writeShortBE((short) 0x0);

        svar.writeByte((byte) mineID);
        svar.writeByte((byte) areaValue);

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        svar.writeIntBE(0x0);
        svar.writeIntBE(0x0);

        svar.writeByte((byte) 0);

        svar.writeByte((byte) 0x80);

        Packet svarPakke = new Packet(0x8032, svar.getData());

        player.sendPacket(svarPakke);
    }
}
