package helpers;

import packetHandler.*;

public class PrintPacket {

    /**
     * Printer ut innholdet i en pakke i hex format.
     *
     * @param p Pakke
     */
    public static void print(Packet p) {

        System.out.println("\nOpcode: " + Integer.toString(p.getOpcode(), 16) + "\n");

        byte[] data = p.getData();

        for (int c = 0; c < data.length; c++) {

            if ((c & 0xF) == 0) {
                System.out.format("\n%04X: ", (c & 0xFFFFFFF0));
            }

            System.out.format("%02X ", data[c]);
        }
    }

}
