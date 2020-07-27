package gameServer;

import characters.*;
import gameData.StatMS;
import gameData.StatManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import packetHandler.*;
import players.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å holde oversikt og organisere alle spillere
 * tilkoblet og npcs.
 *
 */
public class MultiClient {

    /**
     * Disse to hashmap-ene holder oversikt over hvem er tilkoblet ground zone
     * og hvem som er i space zone. Key = character ID.
     */
    private static ConcurrentHashMap<Integer, CharacterGame> spillere_ground = new ConcurrentHashMap<Integer, CharacterGame>();
    private static ConcurrentHashMap<Integer, CharacterGame> spillere_space = new ConcurrentHashMap<Integer, CharacterGame>();
    /**
     * Denne listen holder oversikt over alle PlayerGame objektene for
     * tilkoblede spillere. Brukes for å koble character ID til en fysisk
     * spiller, slik at pakker kan sendes til andre spillere av opcodene. Key =
     * character ID.
     */
    private static ConcurrentHashMap<Integer, PlayerGame> alle_spillere = new ConcurrentHashMap<Integer, PlayerGame>();

    /**
     * Registrerer en ny character slik at andre spillere kan se den, op
     * opcodene kan sende pakker til den.
     *
     * @param player Spilleren som skal registreres.
     */
    public static void registerCharacter(PlayerGame player) {

        if (player == null) {
            return;
        }

        //Registrer spillerens character.
        CharacterGame chara = player.getCharacter();

        if (chara.getPosisjon().getZone() == 1) {
            //Ground zone.
            spillere_ground.put(chara.getCharacterID(), chara);
        } else if (chara.getPosisjon().getZone() == 2) {
            //Space zone.
            spillere_space.put(chara.getCharacterID(), chara);
        }

        //Registrer den "fysiske" spilleren slik at det er mulig for opcodene å sende pakker til spilleren.
        alle_spillere.put(chara.getCharacterID(), player);
    }

    /**
     * Fjerner den oppgitte character fra oversikten.
     *
     * @param chara Character som skal fjernes.
     */
    public static void unregisterCharacter(CharacterGame chara) {

        if (chara == null) {
            return;
        }

        //Må ignorere zone i spillerens posisjons objekt, eller kan problemer oppstå når spiller reiser til/fra earth/space.
        if (spillere_ground.remove(chara.getCharacterID()) == null) {

            spillere_space.remove(chara.getCharacterID());
        }

        alle_spillere.remove(chara.getCharacterID());
    }

    /**
     * Returnerer en liste over alle characters innen 2100m fra en spiller.
     *
     * @param chara Spilleren som er i sentrum.
     *
     * @return ArrayList som inneholder alle characters innen rekkevidde.
     * Inkludert chara!
     *
     */
    public static ArrayList<CharacterGame> getCharacterList(CharacterGame chara) {

        long radius = 8400;

        if (chara == null) {
            return null;
        }

        //Sett eventuell visual radius bonus for MS/Vehicle.
        if (chara.getVehicle() != null) {
            StatMS sm = StatManager.getMsVehicleStat(chara.getVehicle().getID());
            if (sm != null && sm.getVisualRadius() > radius) {
                radius = sm.getVisualRadius();
            }
        }

        if (chara.radius > 0) {
            radius = chara.radius;
        }

        ArrayList<CharacterGame> result = new ArrayList<CharacterGame>();

        ConcurrentHashMap<Integer, CharacterGame> spiller_liste;
        if (chara.getPosisjon().getZone() == 1) {
            spiller_liste = spillere_ground;
        } else if (chara.getPosisjon().getZone() == 2) {
            spiller_liste = spillere_space;
        } else {
            return null;
        }

        //spillere inneholder nå alle characters i spillerens zone.
        Iterator<CharacterGame> spillere = spiller_liste.values().iterator();

        //Legg til spilleren selv i listen, dette er veldig viktig. Spilleren MÅ være med i listen eller vil ikke /getoff virke.
        result.add(chara);

        //Gå gjennom alle characters i zone og sjekk avstanden til spilleren.
        while (spillere.hasNext()) {

            CharacterGame c = spillere.next();

            //Sjekk at character i c ikke er spilleren selv.
            if (chara != c) {
                //Regn ut avstanden til c			
                long avstand = chara.getPosisjon().distance(c.getPosisjon());

                if (avstand <= radius) {
                    result.add(c);
                }
            }
        }

        //Sjekk at vi ikke returnerer flere characters enn tillatt.
        if (result.size() > (config.Server.gameserver_max_characters + 1)) { //+1 pga spilleren selv er med i listen.

            //Gå ned til 1500m
            result = trimCharacterList(result, chara, 6000);
            if (result.size() > (config.Server.gameserver_max_characters + 1)) {
                //Gå ned til 1000m
                result = trimCharacterList(result, chara, 4000);
                if (result.size() > (config.Server.gameserver_max_characters + 1)) {
                    //Gå ned til 500m
                    result = trimCharacterList(result, chara, 2000);
                    //Går ikke lengre ned enn 500m
                }
            }
        }

        return result;
    }

    /**
     * Går gjennom en liste over characters og plukker ut alle som er innen
     * oppgitt radius av spilleren.
     *
     * @param list Liste over characters vi skal gå gjennom.
     * @param chara Character de skal være innen rekkevidde av.
     * @param radius Max avstand mellom chara og characters i list.
     *
     * @return Liste over gjenværende characters.
     */
    private static ArrayList<CharacterGame> trimCharacterList(ArrayList<CharacterGame> list, CharacterGame chara, long radius) {

        ArrayList<CharacterGame> resultat = new ArrayList<CharacterGame>();

        Iterator<CharacterGame> spillere = list.iterator();

        //Gå gjennom alle characters i listen og sjekk avstanden til  chara.
        while (spillere.hasNext()) {

            CharacterGame c = spillere.next();

            //Regn ut avstanden til c			
            long avstand = chara.getPosisjon().distance(c.getPosisjon());

            if (avstand <= radius) {
                resultat.add(c);
            }
        }

        return resultat;
    }

    /**
     * Returnerer CharacterGame objektet med oppgitt character id.
     *
     * @param id Character ID for character vi ønsker.
     *
     * @return CharacterGame objekt, eller null hvis oppgitt id ikke finnes.
     */
    public static CharacterGame getCharacter(int id) {

        CharacterGame spiller = spillere_ground.get(id);
        if (spiller == null) {
            spiller = spillere_space.get(id);
        }

        return spiller;
    }

    /**
     *
     * @return Iterator<PlayerGame> over alle spillere tilkoblet server.
     */
    public static Iterator<PlayerGame> getAllPlayers() {
        return alle_spillere.values().iterator();
    }

    /**
     * Sparker alle spillere fra server.
     */
    public static void kickAllPlayers() {

        Iterator<PlayerGame> spillere = getAllPlayers();

        while (spillere.hasNext()) {

            PlayerGame spiller = spillere.next();

            //Send force logout pakke til spiller.
            PacketData svar8052 = new PacketData();

            svar8052.writeIntBE(spiller.getCharacter().getCharacterID());
            svar8052.writeByteMultiple((byte) 0x0, 24);

            Packet svar8052_pakke = new Packet(0x8052, svar8052.getData());

            spiller.sendPacket(svar8052_pakke);
        }

    }

    /**
     * Kicker en spiller fra server.
     *
     * @param cid Character ID til spiller.
     */
    public static void kickPlayer(int cid) {

        PacketData svar8052 = new PacketData();

        svar8052.writeIntBE(cid);
        svar8052.writeByteMultiple((byte) 0x0, 24);

        Packet svar8052_pakke = new Packet(0x8052, svar8052.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar8052_pakke, cid);
    }

    /**
     * Sender en pakke til en spiller. NB! Denne metoden skal ikke brukes til å
     * sende svar tilbake til klienten, men for å sende pakker til andre
     * spillere i nærheten for å informere om ting som skjer, f.eks opcode
     * 0x8035.
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

        PlayerGame player = alle_spillere.get(character_id);

        if (player == null) {
            return; //Spiller er ikke tilkoblet.
        }
        Packet ny_pakke = new Packet(pakke.getOpcode(), pakke.getData());

        player.sendPacket(ny_pakke);
    }

    /**
     * Sender en pakke til alle spillere i nærheten av oppgitt spiller,
     * inkludert spilleren selv.
     *
     * @param pakke Pakke som skal sendes.
     *
     * @param chara Character som er i sentrum.
     */
    public static void broadcastPacket(Packet pakke, CharacterGame chara) {

        //Timing test.
        long a, b, c;

        a = System.currentTimeMillis();

        Iterator<CharacterGame> spillere = getCharacterList(chara).iterator();

        b = System.currentTimeMillis();

        while (spillere.hasNext()) {
            sendPacketToPlayer(pakke, spillere.next().getCharacterID());
        }

        c = System.currentTimeMillis();

        if ((c - a) > 30) {
            System.out.print("broadcastPacket(): b-a=" + (b - a) + " c-b=" + (c - b));
        }
    }

    /**
     * Sender en pakke til alle spillere i nærheten av oppgitt posisjon.
     *
     * @param pakke Pakke som skal sendes.
     *
     * @param center Sentrum
     */
    public static void broadcastPacket(Packet pakke, Posisjon center) {

        //Opprett ny midlertidig character som skal brukes som sentrum. 
        CharacterGame chara = new CharacterGame(1, "", 1, 1);
        chara.setPosisjon(center);

        Iterator<CharacterGame> spillere = getCharacterList(chara).iterator();

        while (spillere.hasNext()) {
            sendPacketToPlayer(pakke, spillere.next().getCharacterID());
        }

    }

    /**
     * Sender en pakke til alle spillere på server.
     *
     * @param pakke Pakke som skal sendes.
     *
     */
    public static void broadcastPacket(Packet pakke) {

        Iterator<PlayerGame> spillere = getAllPlayers();

        while (spillere.hasNext()) {
            sendPacketToPlayer(pakke, spillere.next().getCharacter().getCharacterID());
        }

    }

    /**
     * Nullstiller intern spillerliste. Brukes når vi lagrer data slik at vi er
     * sikker på at server er "clean" når vi starter opp igjen.
     */
    public static void clearPlayerList() {
        alle_spillere.clear();
        spillere_ground.clear();
        spillere_space.clear();
    }
}
