package items;

import packetHandler.PacketData;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere en camp item f.eks hangar, machine
 * store osv...
 */
public class Camp extends Item {

    private static final long serialVersionUID = 1L;

    public Camp(int item_id) {
        super(item_id);
    }

    public byte[] getData() {

        PacketData pd = new PacketData();

        pd.writeByteMultiple((byte) 0x80, 6);

        return pd.getData();
    }

}
