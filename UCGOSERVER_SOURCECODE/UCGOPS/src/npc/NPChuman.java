package npc;

import characters.CharacterGame;
import characters.Posisjon;
import java.util.Iterator;
import packetHandler.PacketData;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere en NPC i human form.
 */
public class NPChuman extends NPC {

    /**
     * Utseende til NPC.
     */
    private int face, hairStyle, hairColor, gender;
    /**
     * Clothes look ID for uniform NPC har på.
     */
    private int uniform = -1;

    /**
     * Oppretter ny human NPC på oppgitt posisjon.
     *
     * @param id ID nummer til NPC
     * @param navn
     * @param rank
     * @param faction
     * @param pos
     */
    public NPChuman(int id, String navn, int rank, int faction, Posisjon pos) {

        super(id);
        this.navn = navn;
        this.rank = rank;
        this.faction = faction;
        this.pos = pos;
    }

    /**
     * Setter utseende for NPC.
     *
     * @param gender
     * @param face
     * @param hairStyle
     * @param hairColor
     */
    public void setAppearance(int gender, int face, int hairStyle, int hairColor) {

        this.gender = gender;
        this.face = face;
        this.hairStyle = hairStyle;
        this.hairColor = hairColor;
    }

    /**
     * Setter uniform NPC har på seg.
     *
     * @param uniform Look ID ofr uniform, ikke item ID!
     */
    public void setUniform(int uniform) {
        this.uniform = uniform;
    }

    @Override
    public byte[] getData8003(int playerZ) {

        PacketData pd = new PacketData();

        pd.writeIntBE(this.pos.getX());
        pd.writeIntBE(this.pos.getY());
        pd.writeIntBE(this.pos.getZ());

        pd.writeShortBE((short) this.pos.getTilt());
        pd.writeShortBE((short) this.pos.getRoll());
        pd.writeShortBE((short) this.pos.getDirection());

        pd.writeIntBE(this.getID());

        pd.writeShortBE((short) 0x0);
        pd.writeByte((byte) 0x0);
        pd.writeByte((byte) this.pos.getZone());

        pd.writeIntBE(0x0);
        pd.writeIntBE(-1);

        pd.writeByte((byte) this.rank);
        pd.writeByte((byte) 0x7); //NPC name tag i hvitt. Offisiel server brukte 0xA for NPC.
        pd.writeByte((byte) 0x0); //Occupancy tag
        pd.writeByte((byte) 0x0);
        pd.writeByte((byte) 0x0);
        pd.writeByte((byte) this.faction);

        pd.writeIntBE(this.appearanceCounter);
        pd.writeIntBE(this.teamID);

        pd.writeByte((byte) -1);

        pd.writeIntBE(0x0);

        return pd.getData();
    }

    public byte[] getData800A() {

        PacketData pd = new PacketData();

        pd.writeIntBE(0x00030002);
        pd.writeIntBE(this.getID());

        pd.writeShortBE((short) 0);
        pd.writeByte((byte) this.gender);

        pd.writeByte((byte) 0x94);

        pd.writeByte((byte) 0);
        pd.writeByte((byte) this.uniform);
        pd.writeShortBE((short) 0x0);

        pd.writeByte((byte) -1); //Skjorte
        pd.writeByte((byte) -1); //Farge

        pd.writeByte((byte) 0);

        pd.writeByte((byte) -1); //Jakke
        pd.writeByte((byte) -1); //Farge

        pd.writeByte((byte) 0);

        pd.writeByte((byte) -1); //Bukse
        pd.writeByte((byte) -1); //Farge

        pd.writeByte((byte) 0);

        pd.writeByte((byte) -1); //Sko
        pd.writeByte((byte) -1); //Farge

        pd.writeByte((byte) 0);

        pd.writeByte((byte) -1); //Hansker
        pd.writeByte((byte) -1); //Farge

        pd.writeByte((byte) 0);

        pd.writeByte((byte) -1); //Hatt
        pd.writeByte((byte) -1); //Farge

        pd.writeByte((byte) 0);

        pd.writeByte((byte) -1); //Briller

        pd.writeByte((byte) 0);

        pd.writeIntBE(0);

        pd.writeByte((byte) this.face);

        pd.writeShortBE((short) 0x0);

        pd.writeByte((byte) this.hairStyle);

        pd.writeByte((byte) this.hairColor);

        pd.writeByteMultiple((byte) 0, 32);

        return pd.getData();
    }

    public boolean isActive() {
        return true;
    }

    public void performAI() {
    }

    @Override
    public byte getMachineDamage() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void chooseTarget(Iterator<NPCtarget> targets, Area area) {
        // TODO Auto-generated method stub
    }

    @Override
    public void respawn() {
        // TODO Auto-generated method stub
    }

    @Override
    public int getKillTime() {
        // TODO Auto-generated method stub
        return 0;
    }
}
