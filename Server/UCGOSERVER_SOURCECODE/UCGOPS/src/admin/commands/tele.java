package admin.commands;

import characters.*;
import packetHandler.*;
import players.*;

public class tele implements AdminCommand {

    @Override
    public boolean checkGMrights(PlayerChat p) {
        return true;
    }

    @Override
    public void execute(PlayerChat p, String[] args) {

        //Sjekk at vi fått et argument og at det er en int.
        if (args.length < 2) {
            return;
        }

        int l;

        try {
            l = Integer.parseInt(args[1]);
        } catch (Exception e) {
            return;
        }

        //Hent ut UCGM sin character fra gameserver for å finne hvilken zone vi er i.
        CharacterGame c = gameServer.MultiClient.getCharacter(p.getCharacter().getCharacterID());

        if (c == null) {
            return;
        }

        Posisjon pos; //Den nye posisjonen vi skal flyttes til lagres her.

        if (c.getPosisjon().getZone() == 1) {
            //Teleport til bakke posisjon.
            switch (l) {

                case 0: //Sydney
                    pos = new Posisjon(72536537, -59308704, 311, -1518, 1);
                    break;
                case 1: //Perth
                    pos = new Posisjon(55469796, -58348671, 39, 15487, 1);
                    break;
                case 2: //Canberra
                    pos = new Posisjon(71572874, -60098021, 2435, -17575, 1);
                    break;
                case 3: //Adelaide
                    pos = new Posisjon(66390755, -59786294, 35, -14090, 1);
                    break;
                case 4: //Melbourne
                    pos = new Posisjon(69404973, -61194815, 1899, 1714, 1);
                    break;
                case 5: //Darwin
                    pos = new Posisjon(62637658, -49079842, 39, 4291, 1);
                    break;
                case 6: //Brisbane
                    pos = new Posisjon(73447582, -56285669, 200, 0, 1);
                    break;
                case 7: //Southern cross
                    pos = new Posisjon(57289989, -58213606, 1750, -25778, 1);
                    break;
                case 8: //Newman
                    pos = new Posisjon(56933766, -54465827, 2898, 8329, 1);
                    break;
                case 9: //Richmond
                    pos = new Posisjon(68933333, -52866560, 2429, 13379, 1);
                    break;
                default: //Ugyldig posisjon
                    return;
            }
        } else if (c.getPosisjon().getZone() == 2) {
            //Teleport til space posisjon.
            //Ikke lagt til ennå.
            return;
        } else {
            return; //Ugyldig zone?
        }
        //Send teleport pakke til spilleren.
        PacketData pd = new PacketData();

        pd.writeIntBE(p.getCharacter().getCharacterID());
        pd.writeIntBE(pos.getX());
        pd.writeIntBE(pos.getY());
        pd.writeIntBE(pos.getZ());
        pd.writeShortBE((short) pos.getTilt());
        pd.writeShortBE((short) pos.getRoll());
        pd.writeShortBE((short) pos.getDirection());

        Packet svar803C = new Packet(0x803C, pd.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar803C, p.getCharacter().getCharacterID());
    }

}
