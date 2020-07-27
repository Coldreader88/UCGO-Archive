package items;

import packetHandler.PacketData;

public class Clothing extends Item {

    private static final long serialVersionUID = 1L;

    //Farge på plagget.
    private int color;

    //Hvis GQ version, skill bonus gitt. 
    private int GQstrength, GQspirit, GQluck;

    public Clothing(int item_id, int color) {
        super(item_id);
        this.color = color;
    }

    public Clothing(int item_id, int color, int GQstrength, int GQspirit, int GQluck) {
        super(item_id);
        this.color = color;
        this.GQstrength = GQstrength;
        this.GQspirit = GQspirit;
        this.GQluck = GQluck;
    }

    public int getColor() {
        return this.color;
    }

    public int getGQstrength() {
        return GQstrength;
    }

    public int getGQspirit() {
        return GQspirit;
    }

    public int getGQluck() {
        return GQluck;
    }

    public byte[] getData() {
        PacketData pd = new PacketData();

        pd.writeByte((byte) 0x80);
        pd.writeByte((byte) 0x80);
        pd.writeByte((byte) 0x80);
        pd.writeByte((byte) 0x81);
        pd.writeByte((byte) this.color);
        pd.writeByte((byte) 0x80);
        pd.writeByte((byte) 0x80);

        return pd.getData();
    }

}
