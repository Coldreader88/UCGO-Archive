package opcodes.gameServer;

import characters.*;
import items.*;
import npc.*;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x0A implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();
        int character_id = pd.readIntBE();

        PacketData svar = new PacketData();

        PlayerGame player = (PlayerGame) p;

        //Hent ut character klienten ønsker info om.
        CharacterGame chara = gameServer.MultiClient.getCharacter(character_id);

        if (chara != null) {

            //Sjekk om character er i humanform eller ms/vehicle.
            if (chara.humanForm()) {
                svar.writeIntBE(0x030002); //HUMAN
            } else {
                svar.writeIntBE(0x040002); //MS/VEHICLE
            }
            svar.writeIntBE(character_id);

            //Skriv data avhengig av humanform eller ms/vehicle.
            if (chara.humanForm()) {
                svar.writeByteArray(this.getHumanData(chara));
            } else if (chara.getVehicle() != null) {
                svar.writeByteArray(this.getVehicleData(chara.getVehicle(), player.getCharacter()));
            } else if (chara.getTaxiTransport() != null) {
                svar.writeByteArray(this.getTaxiTransportData(chara.getTaxiTransport()));
            }

            Packet svar_pakke = new Packet(0x800A, svar.getData());

            p.sendPacket(svar_pakke);
        }//Oppgitt ID er ikke for en spiller. Sjekk om det er NPC.
        else if ((character_id & 0xFF000000) == 0x06000000 || (character_id & 0xFF000000) == 0x05000000) {

            NPC n = null;
            //Sjekk om NPC skal hentes fra GlobalManager eller AreaManager.
            if ((character_id & 0xFF000000) == 0x06000000) {
                n = GlobalManager.getNpc(character_id);
            } else {
                n = LocalManager.getNpc(character_id);
            }

            if (n != null) {

                Packet svar_pakke = new Packet(0x800A, n.getData800A());
                p.sendPacket(svar_pakke);
            }
        }

    }

    /**
     * Returnerer data for character i humanform.
     *
     * @param chara Character vi skal returnere data for.
     *
     * @return Bytearray som kan settes rett inn i pakke.
     */
    private byte[] getHumanData(CharacterGame chara) {

        PacketData pd = new PacketData();

        pd.writeShortBE((short) 0x0);
        pd.writeByte((byte) chara.getGender());
        pd.writeByte((byte) 0x94);

        pd.writeByteArray(chara.getClothing().getData());

        pd.writeIntBE(0);

        pd.writeByte((byte) chara.getAppearance().getFaceType());

        pd.writeShortBE((short) 0x0);

        pd.writeByte((byte) chara.getAppearance().getHairStyle());

        pd.writeByte((byte) chara.getAppearance().getHairColor());

        pd.writeByteMultiple((byte) 0, 32);

        return pd.getData();
    }

    /**
     * Returnerer data for character i MS/Vehicle, ikke taxi/transport.
     *
     * @param v Vehicle vi skal returnere data for.
     * @param chara Spillerens charactergame objekt. Dette er AKTIV SPILLER SOM
     * SENDER OPCODE 0xA.
     *
     * @return Bytearray med data som kan settes rett inn i pakken.
     */
    private byte[] getVehicleData(Vehicle v, CharacterGame chara) {
        //FOR MS.
        PacketData pd = new PacketData();

        pd.writeIntBE(v.getID());

        //Skriv våpen data avhengig av om dette er spillerens vehicle eller noen andres.
        if (chara.getVehicle() != v) {
            //SKRIV DATA FOR ANDRE PCs/NPCs.
            pd.writeByte((byte) 0x82);
            //Sjekk om våpen utstyrt.
            int slot = v.getActiveSlot();
            if (v.getEquippedItem(slot) != null) {
                pd.writeIntBE(v.getEquippedItem(slot).getItem().getID());
            } else {
                pd.writeIntBE(-1);
            }

            //Sjekk om shield utstyrt.
            if (v.getEquippedItem(1) != null) {
                pd.writeIntBE(v.getEquippedItem(1).getItem().getID());
            } else {
                pd.writeIntBE(-1);
            }

            pd.writeByte((byte) 0x82);
            pd.writeByte((byte) 0x0);
            pd.writeByte((byte) 0x0);
        } else {
            //Skriv data for spilleren selv.
            pd.writeByte((byte) 0x87);

            for (int n = 0; n < 7; n++) {
                if (v.getEquippedItem(n) != null) {
                    pd.writeIntBE(v.getEquippedItem(n).getItem().getID());
                } else {
                    pd.writeIntBE(-1);
                }
            }

            pd.writeByte((byte) 0x87);
            pd.writeByteMultiple((byte) 0x0, 7);
        }

        pd.writeByte((byte) 0x80);

        pd.writeShortBE((short) 0x0);

        return pd.getData();
    }

    /**
     * Returnerer data for character i taxi/transport.
     *
     * @param t Transport character er i.
     *
     * @return Byte array som kan settes rett inn i pakke.
     */
    private byte[] getTaxiTransportData(TaxiTransport t) {

        PacketData pd = new PacketData();

        pd.writeIntBE(t.getID());

        pd.writeByte((byte) 0x82);

        pd.writeIntBE(-1);

        pd.writeIntBE(-1);

        pd.writeByte((byte) 0x82);
        pd.writeByte((byte) 0x0);
        pd.writeByte((byte) 0x0);

        pd.writeByte((byte) 0x80);

        pd.writeShortBE((short) 0x0);

        return pd.getData();
    }
}
