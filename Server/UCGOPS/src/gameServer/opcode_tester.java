package gameServer;

import packetHandler.*;

//DETTE ER EN TEST KLASSE BRUKT AV GAME SERVER.
//BRUKES FOR Å SENDE OPCODES TIL KLIENTER FOR TESTING.
public class opcode_tester implements Runnable {

    int value = 230;

    @Override
    public void run() {

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
        };
        System.out.println("OK START!");

        while (true) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            };

            PacketData svar8004 = new PacketData();

            String msg = "TEST\nmelding\r\nA\tB";

            svar8004.writeIntBE(0x0);
            svar8004.writeByte((byte) (msg.length() + 0x80));
            svar8004.writeStringUTF16LE(msg, value);//0xE0
            svar8004.writeIntBE(0x7);
            svar8004.writeIntBE(0x0);
            svar8004.writeByte((byte) 0x80);

            Packet svar8004_pakke = new Packet(0x8004, svar8004.getData());

            //Send test pakke til alle spillere.
            chatServer.MultiClient.sendGlobalPacket(svar8004_pakke);
            System.out.println("value:" + value);

            value++;

        }
    } //AVSLUTT WHILE TRUE LOOP

}
