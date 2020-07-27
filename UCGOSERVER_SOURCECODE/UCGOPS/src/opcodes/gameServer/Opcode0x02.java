package opcodes.gameServer;

import opcodes.Opcode;
import packetHandler.*;
import players.*;
import validItems.*;

public class Opcode0x02 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        int pos_x = pd.readIntBE();
        int pos_y = pd.readIntBE();
        int pos_z = pd.readIntBE();
        int tilt = pd.readShortBE();
        int roll = pd.readShortBE();
        int retning = pd.readShortBE();

        int character_id = pd.readIntBE();

        pd.readShortBE();
        pd.readShortBE();

        pd.readIntBE();
        pd.readIntBE();

        pd.readByte();
        pd.readByte();

        int b = pd.readByte();
        int occupancy_tag = b & 0xF; //Occupancy tag, R,ER osv. IKKE BRUKT PGA BUG I KLIENT SOM SENDER FEIL VERDI.

        int slot = (b >> 4) & 0xF; //Aktiv slot.

        int action = pd.readByte();

        pd.readShortBE();

        pd.readIntBE();

        int team_id = pd.readIntBE();

        pd.readByte();

        int attack_id = pd.readIntBE();

        PlayerGame player = (PlayerGame) p;

        //Sjekk at character ID stemmer.
        if (character_id == player.getCharacter().getCharacterID()) {

            //Bruk kun for 0 og 1, andre verdier settes "manuelt" pga bug i klienten.
            if (occupancy_tag == 0 || occupancy_tag == 1) {
                player.getCharacter().setOccupancyTag(occupancy_tag);
            }

            //Hvis spiller er i et vehicle oppdater teller som angir når MS/MA/Fighter skill skal øke.
            //DERSOM SPILLER ER I MS SEND OGSÅ INFO TIL MapManager FOR AI
            if (player.getCharacter().getVehicle() != null) {
                player.getCharacter().updateVehicleMovement();

                GameItem gi = ItemList.getItem(player.getCharacter().getVehicle().getID());                
            }

            //Hvis "action" = 1 er thruster på. Dette skal kun skje når spilleren er i bevegelse.
            if (action == 1) {
                //Sjekk at spilleren faktisk har flyttet på seg. Hvis ikke skal action være 0.
                //Dette er på grunn av bug i klienten som ikke oppdater action riktig.
                // < 20 fordi spilleren thruster skal av når spilleren bremser opp og opcode 0x2 sendes ikke når spiller står helt i ro.
                if (Math.abs(pos_x - player.getCharacter().getPosisjon().getX()) < 20
                        && Math.abs(pos_y - player.getCharacter().getPosisjon().getY()) < 20
                        && Math.abs(pos_z - player.getCharacter().getPosisjon().getZ()) < 20) {

                    if (action == 1) {
                        action = 0; //Spilleren står i ro. Thrusters av.
                    }
                }
            }

            //Oppdater koordinater.
            player.getCharacter().getPosisjon().setX(pos_x);
            player.getCharacter().getPosisjon().setY(pos_y);
            player.getCharacter().getPosisjon().setZ(pos_z);
            player.getCharacter().getPosisjon().setTilt(tilt);
            player.getCharacter().getPosisjon().setRoll(roll);
            player.getCharacter().getPosisjon().setDirection(retning);

            //Sett team.
            player.getCharacter().setTeamID(team_id);

            //Sett attack ID.
            player.getCharacter().setAttackID(attack_id);

            //Sett aktiv slot.
            //Sjekk først at spiller er i ms/vehicle.
            if (player.getCharacter().getVehicle() != null) {
                if (player.getCharacter().getVehicle().getActiveSlot() != slot) {
                    //OK, slot endret. Oppdater.
                    player.getCharacter().getVehicle().setActiveSlot(slot);
                    player.getCharacter().appearanceChange();
                }
            }

            //Sett occupancy tag. SLÅTT AV, KLIENT INNEHOLDER BUGS SOM SENDER FEILE VERDIER NOEN GANGER.
            //player.getCharacter().setOccupancyTag(occupancy_tag);
            //Sett action, hva spiller gjør.
            player.getCharacter().setAction(action);
        }

    }

}
