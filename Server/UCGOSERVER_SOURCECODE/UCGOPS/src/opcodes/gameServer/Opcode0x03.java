package opcodes.gameServer;

import characters.*;
import gameData.StatMS;
import gameData.StatManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import npc.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;
import userDB.GameCharacter;
import userDB.ManageCharacters;

public class Opcode0x03 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int account_id = pd.readIntBE();
        int character_id = pd.readIntBE();
        pd.readByte();
        int zone = pd.readByte();
        pd.skipAhead(6);
        pd.readIntBE(); //X
        pd.readIntBE(); //Y
        pd.readIntBE(); //Z
        pd.readIntBE(); //

        //Sjekk at account id og character id stemmer.
        PlayerGame player = (PlayerGame) p;

        if (account_id == player.getAccountID() && character_id == player.getCharacter().getCharacterID()) {
            //OK. Oppdater opcode 0x3 timestamp.
            player.getCharacter().setLastTime0x3Received((int) (System.currentTimeMillis() / 1000));
            if (player.getCharacter().setOpcode3Received()) {
                //Finn kontoen spiller tilhører og steng den.
                GameCharacter chara = ManageCharacters.getGameCharacter(player.getCharacter().getCharacterID());
                chara.getKonto().setLocked(true);
                gameServer.MultiClient.kickPlayer(player.getCharacter().getCharacterID());

                //Print ut info om at spiller bruker speed cheat.                
                System.out.println("OPCODE 0x3: SPEED CHEAT! Character ID: " + player.getCharacter().getCharacterID() + " IP: " + player.getIP());
                System.out.println("OPCODE 0x3:ACCOUNT CLOSED. ACCOUNT ID:" + chara.getKonto().getAccountID());
                return;
            }

            //Sjekk om vi skal sende en news update til spiller. Dette gjøres 10 sekunder etter spiller er logget inn.
            int time = (int) (System.currentTimeMillis() / 1000);

            if (!player.isNewsMsgSent() && config.Server.serverNews != null && (time - player.getLoginTime()) > 20) {
                //OK! Send melding.
                chatServer.MultiClient.sendSystemMessage(config.Server.serverNews, player.getCharacter().getCharacterID());
                player.setNewsMsgSent(true);
            }

            //Lag liste over alle spillere i nærheten.			
            PacketData svar = new PacketData();
            svar.writeShortBE((short) 0x2);

            //Sjekk om spiller skal ha en visual radius bonus.
            int radius = 8400; //2100m er default visual radius.

            if (player.getCharacter().getVehicle() != null) {
                StatMS sm = StatManager.getMsVehicleStat(player.getCharacter().getVehicle().getID());
                if (sm != null && sm.getVisualRadius() > radius) {
                    radius = sm.getVisualRadius();
                }
            }

            //Hent ut alle characters og NPCs i nærheten.
            ArrayList<CharacterGame> spillere = gameServer.MultiClient.getCharacterList(player.getCharacter());
            LinkedList<NPC> globalNpc = GlobalManager.getNpcList(player.getCharacter().getPosisjon(), radius);
            LinkedList<NPC> localNpc = LocalManager.getNpcList(player.getCharacter().getPosisjon(), radius);

            //Skriv antall spillere.
            svar.writeByteArray(helpers.UCGOcounter.getCounter(spillere.size() + globalNpc.size() + localNpc.size()));

            Iterator<CharacterGame> spiller_liste = spillere.iterator();

            //Sett inn data for alle spillere i nærheten.
            while (spiller_liste.hasNext()) {

                CharacterGame c = spiller_liste.next();
                //Sjekk at character er synlig. HUSK! Spiller som sender 0x3 MÅ!!! være med i listen tilbaken
                //uansett om han er usynlig eller ikke. Hvis ikke kan chat slutte å virke for UCGMs.
                if (c.isVisible() || player.getCharacter() == c) {
                    svar.writeByteArray(getCharacterData(c));
                }
            }

            //Sett inn data for alle globale NPCs i nærheten.
            Iterator<NPC> npcGlobalListe = globalNpc.iterator();
            while (npcGlobalListe.hasNext()) {
                if (player.getCharacter().getPosisjon().getZone() == 1) {
                    svar.writeByteArray(npcGlobalListe.next().getData8003(player.getCharacter().getPosisjon().getZ()));
                } else {
                    svar.writeByteArray(npcGlobalListe.next().getData8003(Integer.MAX_VALUE));
                }
            }
            //Sett inn data for alle lokale NPCs i nærheten.
            Iterator<NPC> npcLocalListe = localNpc.iterator();
            while (npcLocalListe.hasNext()) {
                if (player.getCharacter().getPosisjon().getZone() == 1) {
                    svar.writeByteArray(npcLocalListe.next().getData8003(player.getCharacter().getPosisjon().getZ()));
                } else {
                    svar.writeByteArray(npcLocalListe.next().getData8003(Integer.MAX_VALUE));
                }
            }

            //Alle spillere listet opp, skriv slutt data. Dette ser ikke ut til å være brukt av klienten uansett.
            svar.writeByte((byte) spillere.size());
            svar.writeIntBE(account_id);
            svar.writeIntBE(character_id);
            svar.writeByte((byte) 0x0);
            svar.writeByte((byte) zone);
            svar.writeIntBE(0x0);
            svar.writeShortBE((short) 0x0);

            Packet svar_pakke = new Packet(0x8003, svar.getData());

            p.sendPacket(svar_pakke);
        } else {
            //Send feil tilbake.
            PacketData svar = new PacketData();

            svar.writeIntBE(-1);

            Packet svar_pakke = new Packet(0x8003, svar.getData());

            p.sendPacket(svar_pakke);
        }

    }

    /**
     * Returnerer data som kan settes rett inn i characterlisten opcode 0x3
     * returnerer.
     *
     * @param c Character vi skal returnere data for.
     *
     * @return Bytearray som kan settes rett inn i pakke.
     */
    private byte[] getCharacterData(CharacterGame c) {

        PacketData pd = new PacketData();

        pd.writeIntBE(c.getPosisjon().getX());
        pd.writeIntBE(c.getPosisjon().getY());
        pd.writeIntBE(c.getPosisjon().getZ());

        pd.writeShortBE((short) c.getPosisjon().getTilt());
        pd.writeShortBE((short) c.getPosisjon().getRoll());
        pd.writeShortBE((short) c.getPosisjon().getDirection());

        pd.writeIntBE(c.getCharacterID());

        pd.writeShortBE((short) 0x0); //Ukjent.
        pd.writeByte((byte) 0x0);
        pd.writeByte((byte) c.getPosisjon().getZone());

        //Sjekk om character er i humanform eller i ms/vehicle.
        if (c.humanForm()) {
            pd.writeIntBE(0);
            pd.writeIntBE(-1); //-1 = Human form.
        } else if (c.getVehicle() != null) {
            pd.writeIntBE(c.getVehicleContainer().getID());
            pd.writeIntBE(c.getVehicle().getID());
        } else if (c.getTaxiTransport() != null) {
            pd.writeIntBE(c.getTaxiTransportContainer().getID());
            pd.writeIntBE(c.getTaxiTransport().getID());
        } else {
            //Dette skal aldri kunne skje. At character ikke er i humanform eller vehicle.
            System.out.println("Opcode 0x3: Character not in humanform or in vehicle.");
            pd.writeIntBE(0);
            pd.writeIntBE(-1); //Bare sett til humanform som default.
        }

        pd.writeByte((byte) c.getRank());
        pd.writeByte((byte) c.getUCGM()); //GM tag.
        pd.writeByte((byte) c.getOccupancyTag()); //Occupancy tag. R,ER,MSDEV osv tags.
        pd.writeByte((byte) c.getAction()); //Action, hva spiller gjør.

        //Crimial tag.
        if (c.isCriminal()) {
            pd.writeByte((byte) 0x1);
        } else {
            pd.writeByte((byte) 0x0);
        }

        pd.writeByte((byte) c.getFaction());

        pd.writeIntBE(c.getAppearanceChange()); //Oppdaterings teller.

        pd.writeIntBE(c.getTeamID()); //Team ID

        //Sett machine damage.
        if (!c.humanForm()) {

            int machine_damage = 0;

            try { //Bruk try catch for å hindre division by zero hvis feil med vehicle stats.
                if (c.getVehicle() != null) {
                    machine_damage = c.getVehicle().getMachineDamage();
                } else if (c.getTaxiTransport() != null) {
                    machine_damage = c.getTaxiTransport().getMachineDamage();
                }
            } catch (Exception e) {
            }

            pd.writeByte((byte) machine_damage);
        } else {
            //Humanform. Machine damage er da alltid 0.
            pd.writeByte((byte) 0x0);
        }

        pd.writeIntBE(c.getAttackID());

        return pd.getData();

    }
}
