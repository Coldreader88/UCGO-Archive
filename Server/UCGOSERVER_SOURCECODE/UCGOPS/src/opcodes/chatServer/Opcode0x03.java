package opcodes.chatServer;

import java.util.Iterator;
import opcodes.Opcode;
import packetHandler.*;
import players.*;

public class Opcode0x03 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE();

        int lengde = pd.readByte() & 0x7F;

        String melding = pd.readStringUTF16LE(lengde);

        int chat_type = pd.readIntBE();

        pd.readIntBE();

        pd.readByte();

        //Sjekk om dette er #online kommandoen.
        if (melding.startsWith("#online")) {
            this.onlineCommand((PlayerChat) p);
            return;
        }

        //Sjekk om spilleren er UCGM og om dette er en UCGM kommando.
        PlayerChat pc = (PlayerChat) p;
        if (p.getUCGM() == 0x3 || p.getUCGM() == 0x4 || p.getUCGM() == 0x5 || p.getUCGM() == 0x9) {
            if (melding.startsWith("#")) { //Alle kommandoer begynner med #.
                //Spiller er UCGM og dette er en kommando.
                //UCGM kommando skal ikke sendes til andre spillere. Men MÅ returneres til UCGM.
                //Hvis 0x8004 ikke returneres til UCGM stopper chat å fungere.
                //Lag 0x8004 pakke.
                PacketData svar8004 = new PacketData();

                PlayerChat player = (PlayerChat) p;

                svar8004.writeIntBE(player.getCharacter().getCharacterID());
                svar8004.writeByte((byte) (melding.length() + 0x80));
                svar8004.writeStringUTF16LE(melding);
                svar8004.writeIntBE(chat_type);
                svar8004.writeIntBE(0x0);
                svar8004.writeByte((byte) 0x81);
                svar8004.writeIntBE(player.getCharacter().getCharacterID());

                Packet svar8004_pakke = new Packet(0x8004, svar8004.getData());

                p.sendPacket(svar8004_pakke);

                //Utfør kommando.
                admin.Main.executeCommand((PlayerChat) p, melding);

                return;
            }
        }

        //Lag 0x8004 pakke.
        PacketData svar8004 = new PacketData();

        PlayerChat player = (PlayerChat) p;

        svar8004.writeIntBE(player.getCharacter().getCharacterID());
        svar8004.writeByte((byte) (melding.length() + 0x80));
        svar8004.writeStringUTF16LE(melding);
        svar8004.writeIntBE(chat_type);
        svar8004.writeIntBE(0x0);
        svar8004.writeByte((byte) 0x81);

        //Pakken skal sendes til flere spillere, med forskjellig character ID i. Lag kopi av pakken.
        PacketData kopi8004 = new PacketData(svar8004.getData());
        kopi8004.writeIntBE(player.getCharacter().getCharacterID());
        Packet pakke8004 = new Packet(0x8004, kopi8004.getData());

        //Send pakke tilbake til spilleren. Ellers vises ikke tekst i journal vinduet eller på skjermen.
        p.sendPacket(pakke8004);

        //Send opcode 0x8004 til resten.
        while (true) {

            int character_id = pd.readIntBE();
            if (character_id == 0) {
                break; //Ikke spillere som skal motta meldingen.
            }
            //Lag ny 0x8004 pakke til denne spilleren.
            kopi8004 = new PacketData(svar8004.getData());
            kopi8004.writeIntBE(character_id);

            pakke8004 = new Packet(0x8004, kopi8004.getData());

            chatServer.MultiClient.sendPacketToPlayer(pakke8004, character_id);
        }

        //Log offentlig journal chat.
        if (chat_type >= 0 && chat_type <= 3) {
            //Skriv log melding.
            String msg = "Public chat: Character ID:" + player.getCharacter().getCharacterID()
                    + " IP:" + player.getIP() + " Name:" + player.getCharacter().getNavn()
                    + " Message:" + melding;
            admin.logging.chatserverMsg(msg);
        }
    }

    /**
     * Denne metoden håndterer #online kommanden og printer ut antall spillere
     * som er på server.
     *
     * @param player Spiller som utførte kommandoen.
     */
    private void onlineCommand(PlayerChat player) {

        //Først send #online meldingen tilbake til spilleren.
        chatServer.MultiClient.sendMessage("#online", player.getCharacter().getCharacterID(), player.getCharacter().getCharacterID());

        String msg = "\nPlayers online:\n\n";

        //Gå gjennom alle spillere og finn hvilken faction de tilhører.
        Iterator<PlayerChat> spillere = chatServer.MultiClient.getAllPlayers();
        int ef = 0;
        int zeon = 0;

        while (spillere.hasNext()) {

            PlayerChat spiller = spillere.next();

            if (spiller.getCharacter().getFaction() == 0x1) {
                ef++;
            } else {
                zeon++;
            }
        }

        msg += "EFF: " + ef + "\nZeon: " + zeon + "\n";

        chatServer.MultiClient.sendSystemMessage(msg, player.getCharacter().getCharacterID());
    }

}
