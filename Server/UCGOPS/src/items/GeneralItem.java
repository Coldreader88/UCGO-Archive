package items;

import packetHandler.*;

//Denne klassen brukes til å representere en item som ikke har noen attributer.
public class GeneralItem extends Item {

    private static final long serialVersionUID = 1L;

    public GeneralItem(int item_id) {
        super(item_id);
    }

    public byte[] getData() {

        PacketData pd = new PacketData();

        pd.writeByteMultiple((byte) 0x80, 6);

        return pd.getData();
    }

}
