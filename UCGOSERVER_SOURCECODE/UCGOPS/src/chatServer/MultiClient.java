package chatServer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import packetHandler.Packet;
import packetHandler.PacketData;
import players.PlayerChat;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde oversikt og organisere alle spillere
 * tilkoblet chatserveren. I tillegg håndterer den alle channels brukt.
 */
public class MultiClient {

    /**
     * Denne holder oversikt over alle spillere tilkoblet. Key = Character ID.
     */
    private static ConcurrentHashMap<Integer, PlayerChat> spillere = new ConcurrentHashMap<Integer, PlayerChat>();

    /**
     * Denne holder oversikt over alle chat channels som er i bruk. Key =
     * Channel ID. Value = Hashmap over alle spillere i den kanalen, key her er
     * character ID.
     */
    private static ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, PlayerChat>> chat_channels = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, PlayerChat>>();

    /**
     * Registrerer en spiller som tilkoblet chatserveren.
     *
     * @param p Spiller som skal registreres.
     */
    public static synchronized void registerPlayer(PlayerChat p) {

        if (p == null || p.getCharacter() == null) {
            return;
        }

        spillere.put(p.getCharacter().getCharacterID(), p);
    }

    /**
     * Fjerner den oppgitte spilleren fra oversikten over tilkoblede spillere.
     *
     * @param p Spiller som skal fjernes.
     */
    public static synchronized void unregisterPlayer(PlayerChat p) {

        if (p == null || p.getCharacter() == null) {
            return;
        }

        spillere.remove(p.getCharacter().getCharacterID());
    }

    /**
     * Returnerer en spiller tilkoblet chatserver.
     *
     * @param character_id Character ID for spillerens character.
     *
     * @return PlayerChat objekt for en spiller, NULL hvis spiller ikke
     * tilkoblet.
     */
    public static PlayerChat getPlayer(int character_id) {
        return spillere.get(character_id);
    }

    /**
     * Sender en pakke til en spiller. NB! Denne metoden skal ikke brukes til å
     * sende svar tilbake til klienten, men for å sende pakker til andre
     * spillere.
     *
     * NB!! Pakken som blir mottatt vil IKKE bli endret. Dvs Sequence number vil
     * ikke bli modifisert, denne metoden vil lage kopi av pakken og sende
     * KOPIEN til player.sendPacket()
     *
     * @param pakke Pakke som skal sendes. Pakken vil IKKE BLI ENDRET.
     * @param character_id Pakken sendes til spilleren som "eier" denne
     * characteren.
     */
    public static void sendPacketToPlayer(Packet pakke, int character_id) {

        PlayerChat player = spillere.get(character_id);

        if (player == null) {
            return; //Spiller er ikke tilkoblet.
        }
        Packet ny_pakke = new Packet(pakke.getOpcode(), pakke.getData());

        player.sendPacket(ny_pakke);
    }

    /**
     * Oppretter en ny chat channel og registrerer spilleren som medlem.
     *
     * @param player Spiller som oppretter ny chat.
     *
     * @return Channel ID.
     */
    public synchronized static int newChatChannel(PlayerChat player) {

        int id_nummer;

        //Lag nytt channel ID.
        do {
            id_nummer = 0x08000000 + (int) (Math.random() * 0xFFFFFF);
        } while (chat_channels.get(id_nummer) != null);

        //Opprett ny channel.
        ConcurrentHashMap<Integer, PlayerChat> ny_channel = new ConcurrentHashMap<Integer, PlayerChat>();
        ny_channel.put(player.getCharacter().getCharacterID(), player);

        chat_channels.put(id_nummer, ny_channel);

        return id_nummer;
    }

    /**
     * Registrerer en spiller som medlem av en chat channel.
     *
     * @param player Spiller som skal meldes inn i channel.
     * @param channel_id Chat channel ID.
     */
    public synchronized static void addPlayerToChat(PlayerChat player, int channel_id) {

        ConcurrentHashMap<Integer, PlayerChat> channel = chat_channels.get(channel_id);

        if (channel == null) {
            return;
        }

        channel.put(player.getCharacter().getCharacterID(), player);
    }

    /**
     * Fjerner en spiller fra en chat channel. Dersom kanalen er tom etterpå vil
     * den også bli fjernet.
     *
     * @param player Spiller som skal fjernes.
     * @param channel_id Chat channel spiller skal fjernes fra.
     */
    public synchronized static void removePlayerFromChat(PlayerChat player, int channel_id) {

        ConcurrentHashMap<Integer, PlayerChat> kanal = chat_channels.get(channel_id);

        if (kanal == null) {
            return; //Ugyldig channel id.
        }
        kanal.remove(player.getCharacter().getCharacterID());

        //Fjern chat channel hvis den er tom.
        if (kanal.size() < 0) {
            chat_channels.remove(channel_id);
        }
    }

    /**
     * Returnerer alle spillerene som er i en chat channel.
     *
     * @param channel_id ID for chat channel.
     *
     * @return ArrayList som inneholder alle spillere i chat, eller NULL hvis
     * ugyldig channel ID.
     */
    public static ArrayList<PlayerChat> getChatParticipants(int channel_id) {

        ArrayList<PlayerChat> res = new ArrayList<PlayerChat>();

        ConcurrentHashMap<Integer, PlayerChat> channel = chat_channels.get(channel_id);

        if (channel == null) {
            return null;
        }

        //Gå gjennom alle spillere i kanalen og legg dem til i resultatet.
        Iterator<PlayerChat> i = channel.values().iterator();

        while (i.hasNext()) {
            res.add(i.next());
        }

        return res;
    }

    /**
     * Returnerer oppgitt spiller dersom spiller er med i en chat channel.
     *
     * @param channel_id Chat channel
     * @param character_id Character ID
     *
     * @return PlayerChat objekt hvis spiller er med i channel, null hvis ikke.
     */
    public static PlayerChat getChatParticipant(int channel_id, int character_id) {

        ConcurrentHashMap<Integer, PlayerChat> channel = chat_channels.get(channel_id);

        if (channel != null) {
            return channel.get(character_id);
        } else {
            return null;
        }
    }

    /**
     *
     * @return Iterator<PlayerChat> over alle spillere tilkoblet chatserver.
     */
    public static Iterator<PlayerChat> getAllPlayers() {
        return spillere.values().iterator();
    }

    /**
     *
     * @return Antall spillere tilkoblet chatserver.
     */
    public static int numberOfPlayers() {
        return spillere.size();
    }

    /**
     * Sender en pakke til alle spillere tilkoblet chatserver.
     *
     * @param pakke Pakke som skal sendes.
     */
    public static void sendGlobalPacket(Packet pakke) {
        Iterator<PlayerChat> spillere = getAllPlayers();

        while (spillere.hasNext()) {

            Packet kopi_pakke = new Packet(pakke.getOpcode(), pakke.getData());
            spillere.next().sendPacket(kopi_pakke);
        }
    }

    /**
     * Sender en melding til en spiller slik at den vises i journal vinduet.
     *
     * @param msg Meldingen som skal sendes.
     * @param to Character ID for spiller som skal motta melding.
     * @param from Character ID for spiller som sender melding.
     */
    public static void sendMessage(String msg, int to, int from) {

        PacketData svar8004 = new PacketData();

        svar8004.writeIntBE(from);
        svar8004.writeByte((byte) (msg.length() + 0x80));
        svar8004.writeStringUTF16LE(msg);
        svar8004.writeIntBE(0x0);
        svar8004.writeIntBE(0x0);
        svar8004.writeByte((byte) 0x81);
        svar8004.writeIntBE(to);

        Packet svar8004_pakke = new Packet(0x8004, svar8004.getData());

        sendPacketToPlayer(svar8004_pakke, to);
    }

    /**
     * Sender en system message til en spiller.
     *
     * @param msg Meldingen som skal sendes.
     * @param to Mottaker.
     */
    public static void sendSystemMessage(String msg, int to) {

        PacketData svar8004 = new PacketData();

        svar8004.writeIntBE(0x0);
        svar8004.writeByteArray(helpers.UCGOcounter.getCounter(msg.length()));
        svar8004.writeStringUTF16LE(msg, 0xE0);
        svar8004.writeIntBE(0x7);
        svar8004.writeIntBE(0x0);
        svar8004.writeByte((byte) 0x80);

        Packet svar8004_pakke = new Packet(0x8004, svar8004.getData());

        sendPacketToPlayer(svar8004_pakke, to);
    }

    /**
     * Sender en system message med header til en spiller. NB! En linjes mellom
     * header og tekst legges til.
     *
     * @param header Overskrift på meldingen.
     * @param msg Meldingen som skal sendes.
     * @param to Mottaker.
     */
    public static void sendSystemMessage(String header, String msg, int to) {

        PacketData svar8004 = new PacketData();

        svar8004.writeIntBE(0x0);
        svar8004.writeByteArray(helpers.UCGOcounter.getCounter(header.length() + msg.length() + 2));//+2 for 2*\n
        svar8004.writeStringUTF16LE(header + "\n\n", 0xE0);
        svar8004.writeStringUTF16LE(msg);
        svar8004.writeIntBE(0x7);
        svar8004.writeIntBE(0x0);
        svar8004.writeByte((byte) 0x80);

        Packet svar8004_pakke = new Packet(0x8004, svar8004.getData());

        sendPacketToPlayer(svar8004_pakke, to);
    }

    /**
     * Sender en global system message.
     *
     * @param msg Meldingen som skal sendes.
     */
    public static void sendGlobalSystemMessage(String msg) {

        PacketData svar8004 = new PacketData();

        svar8004.writeIntBE(0x0);
        svar8004.writeByte((byte) (msg.length() + 0x80));
        svar8004.writeStringUTF16LE(msg, 0xE0);
        svar8004.writeIntBE(0x7);
        svar8004.writeIntBE(0x0);
        svar8004.writeByte((byte) 0x80);

        Packet svar8004_pakke = new Packet(0x8004, svar8004.getData());

        sendGlobalPacket(svar8004_pakke);
    }

}
