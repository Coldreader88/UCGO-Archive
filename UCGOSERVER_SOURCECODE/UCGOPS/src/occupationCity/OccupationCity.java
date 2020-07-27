package occupationCity;

import characters.Posisjon;
import java.util.Iterator;
import npc.*;
import npc.spawn.*;
import packetHandler.Packet;
import packetHandler.PacketData;
import players.PlayerGame;

/**
 * Denne klassen skal kjøres som en egen thread.
 *
 * Klassen holder orden på de forskjellige PVP byene/områdene og oppdaterer
 * status, timere og sender opcode 0x70 oppdateringer til spillerne.
 *
 *
 * @author UCGOSERVER.COM
 *
 */
public class OccupationCity implements Runnable {

    /**
     * Alle PVP byene/områdene lagres her.
     */
    private static City[] cityList;

    /**
     * Initialiserer diverse data for byene.
     */
    static {

        cityList = new City[3];

        cityList[0] = new City(0x39, 2); //Helenes.
        cityList[1] = new City(0x3A, 2); //Richmond.
        cityList[2] = new City(0x3B, 1); //Newman

        //Sett alle byene til cease fire status og start nedtellingen.
        int countDown = (int) (System.currentTimeMillis() / 1000);

        cityList[0].setCountDown(countDown + (30 * 60)); //Helenes starter 30 minutter etter server start.
        cityList[0].setStatus(1);

        cityList[1].setCountDown(countDown + (60 * 60)); //Richmond timer
        cityList[1].setStatus(0);

        cityList[2].setCountDown(countDown + (120 * 60)); //Newman timer
        cityList[2].setStatus(0);

        //Plasser Zeon ICF NPCs for Helenes
        Posisjon p;
        Posisjon[] ps;
        NPCboss n;

        //Musai, ICF1.
        p = new Posisjon(-7518, 15955, -2664, 0, 2);
        ps = new Posisjon[]{p, p};
        n = GlobalManager.addNpcBoss(2, ZeonBoss.ICF_MUSAI, ps, "ICF 1");
        cityList[0].setICFnpc(n, 0, 2);

        //Musai, ICF2.
        p = new Posisjon(326, 2318, -2500, 0, 2);
        ps = new Posisjon[]{p, p};
        n = GlobalManager.addNpcBoss(2, ZeonBoss.ICF_MUSAI, ps, "ICF 2");
        cityList[0].setICFnpc(n, 1, 2);

        //Chibe, ICF3.
        p = new Posisjon(-740, -4584, 7956, 0, 2);
        ps = new Posisjon[]{p, p};
        n = GlobalManager.addNpcBoss(2, ZeonBoss.ICF_CHIBE, ps, "ICF 3");
        cityList[0].setICFnpc(n, 2, 2);

        //Chibe, ICF4.
        p = new Posisjon(-7305, 4648, 10200, 0, 2);
        ps = new Posisjon[]{p, p};
        n = GlobalManager.addNpcBoss(2, ZeonBoss.ICF_CHIBE, ps, "ICF 4");
        cityList[0].setICFnpc(n, 3, 2);

        //Zanzibar, ICF5.
        p = new Posisjon(6967, -12487, 6170, 0, 2);
        ps = new Posisjon[]{p, p};
        n = GlobalManager.addNpcBoss(2, ZeonBoss.ICF_ZANZIBAR, ps, "ICF 5");
        cityList[0].setICFnpc(n, 4, 2);

        //Plasser EF ICF NPCs for Helenes.
        //Salamis, ICF1.
        p = new Posisjon(-7518, 15955, -2664, 0, 2);
        ps = new Posisjon[]{p, p};
        n = GlobalManager.addNpcBoss(1, EfBoss.ICF_SALAMIS, ps, "ICF 1");
        cityList[0].setICFnpc(n, 0, 1);

        //Salamis, ICF2.
        p = new Posisjon(326, 2318, -2500, 0, 2);
        ps = new Posisjon[]{p, p};
        n = GlobalManager.addNpcBoss(1, EfBoss.ICF_SALAMIS, ps, "ICF 2");
        cityList[0].setICFnpc(n, 1, 1);

        //Magellan, ICF3.
        p = new Posisjon(-740, -4584, 7956, 0, 2);
        ps = new Posisjon[]{p, p};
        n = GlobalManager.addNpcBoss(1, EfBoss.ICF_MAGELLAN, ps, "ICF 3");
        cityList[0].setICFnpc(n, 2, 1);

        //Magellan, ICF4.
        p = new Posisjon(-7305, 4648, 10200, 0, 2);
        ps = new Posisjon[]{p, p};
        n = GlobalManager.addNpcBoss(1, EfBoss.ICF_MAGELLAN, ps, "ICF 4");
        cityList[0].setICFnpc(n, 3, 1);

        //Pegasus, ICF5.
        p = new Posisjon(6967, -12487, 6170, 0, 2);
        ps = new Posisjon[]{p, p};
        n = GlobalManager.addNpcBoss(1, EfBoss.ICF_PEGASUS, ps, "ICF 5");
        cityList[0].setICFnpc(n, 4, 1);

    }

    /**
     * Denne metoden vil en gang hvert sekund sjekke aktiv by og avgjøre om den
     * skal endre status og om vi skal gå videre til neste by.
     */
    public void run() {

        while (true) {

            try {

                int currentTime = (int) (System.currentTimeMillis() / 1000);

                for (int i = 1; i < cityList.length; i++) { //NB! STARTER PÅ  1  FORDI VI IKKE LENGRE BRUKER HELENES.

                    //Dersom byen er i WAR sjekk status for eventuelle ICF NPCs.
                    if (cityList[i].getStatus() == 2) {
                        cityList[i].checkICFnpcs();
                    }

                    //Sjekk countdown timeren for byen og avgjør om vi skal bytte status.
                    if (currentTime >= cityList[i].getCountDownTimer()) {
                        //Countdown har nådd null, status for byen skal endres.
                        if (cityList[i].getStatus() == 0) {
                            //Nåværede status er cease fire, endre til war.
                            cityList[i].setStatus(2);
                            cityList[i].setCountDown(currentTime + config.Server.occupationCityWar);

                            //Start eventuelle ICF NPCs
                            //FORELØPIG ER IKKE DETTE NØDVENDIG. HELENES ER IKKE PVP AREA LENGRE.
                            //cityList[i].startICFnpcs();
                            //Send opcode 0x8070 og 0x8076 til alle spillere.
                            broadcastOpcode8070();
                            broadcastWarfareStarted8076(cityList[i]);
                        } else {
                            //Nåværende status er war. Endre til cease fire.
                            cityList[i].setStatus(0);
                            cityList[i].setCountDown(currentTime + config.Server.occupationCityCeaseFire);

                            //Sett også faction, da dette stiller alle ICFs tilbake til utgangspunktet.
                            cityList[i].setFaction(cityList[i].getFaction());

                            //Stop eventuelle ICF NPCs.
                            //FORELØPIG ER IKKE DETTE NØDVENDIG. HELENES ER IKKE PVP AREA LENGRE.
                            //cityList[i].stopICFnpcs();
                            //Send opcode 0x8070 og 0x8076 til alle spillere.
                            broadcastOpcode8070();
                            broadcastWarfareSettled8076(cityList[i]);

                            //Dersom det gjelder Richmond/Newman tildel medaljer.
                            if (cityList[i].getCityID() == 0x3A || cityList[i].getCityID() == 0x3B) {
                                giveMedals(cityList[i]);
                            }
                        }
                    } //Slutt på for loop.

                } //Slutt på while loop.

                //Vent i ett sekund før vi sjekker igjen.
                Thread.sleep(1000);

            } catch (Exception e) {

            }

        }

    }

    /**
     *
     * @return Opcode 0x8070 pakke klar til å sendes.
     */
    public static Packet getOpcode70() {

        PacketData pd = new PacketData();

        int teller = 0x80 + cityList.length;

        pd.writeByte((byte) teller);

        for (int i = 0; i < cityList.length; i++) {
            pd.writeByteArray(cityList[i].getOpcode70data());
        }

        Packet p = new Packet(0x8070, pd.getData());

        return p;
    }

    /**
     * Sender en opcode 0x8070 pakke til alle spillere på server.
     */
    private static void broadcastOpcode8070() {

        Packet p = getOpcode70();

        gameServer.MultiClient.broadcastPacket(p);

    }

    /**
     * Sender opcode 0x8076 WARFARE STARTED melding til alle spillere.
     *
     * @param c Hvilken by krigen har startet i.
     */
    private static void broadcastWarfareStarted8076(City c) {

        PacketData pd = new PacketData();

        pd.writeIntBE(0x0);
        pd.writeIntBE(-1);
        pd.writeIntBE(c.getCityID());
        pd.writeByte((byte) -1);
        pd.writeShortBE((short) c.getFaction());
        pd.writeIntBE(c.getCountDownTimer());
        pd.writeIntBE(c.getOppdateringsTeller());

        Packet p = new Packet(0x8076, pd.getData());

        gameServer.MultiClient.broadcastPacket(p);
    }

    /**
     * Sender opcode 0x8076 WARFARE SETTLED melding til alle spillere.
     *
     * @param c Hvilken by krigen har sluttet i.
     */
    private static void broadcastWarfareSettled8076(City c) {

        PacketData pd = new PacketData();

        pd.writeIntBE(0x1);
        pd.writeIntBE(-1);
        pd.writeIntBE(c.getCityID());
        pd.writeByte((byte) -1);
        pd.writeShortBE((short) c.getFaction());
        pd.writeIntBE(c.getCountDownTimer());
        pd.writeIntBE(c.getOppdateringsTeller());

        Packet p = new Packet(0x8076, pd.getData());

        gameServer.MultiClient.broadcastPacket(p);
    }

    /**
     * Registrerer en spiller som deltaker i kampen om en PVP by.
     *
     * @param p Spiller som skal registreres.
     * @param cityID ID for byen.
     */
    public static void registerPlayerToBattle(PlayerGame p, int cityID) {

        City c = getCity(cityID);

        if (c != null) {
            c.registerPlayerToBattle(p);
        }
    }

    /**
     * Avregistrerer en spiller som deltaker i kampen om en PVP by.
     *
     * @param p Spiller som skal avregistreres.
     * @param cityID ID for byen.
     */
    public static void unregisterPlayerFromBattle(PlayerGame p, int cityID) {

        City c = getCity(cityID);

        if (c != null) {
            c.unregisterPlayerFromBattle(p);
        }
    }

    /**
     * Denne metoden kalles når en spiller tar over en ICF. Oppdaterer status
     * for byen, sender opcode 0x8070 og gjør nødvendige oppdateringer dersom
     * anrepet er over og byen overtatt.
     *
     * @param cityID ID for byen.
     * @param icf Hvilken ICF spiller tar over, 0-4.
     * @param faction Hvilken faction spiller tilhører, dvs hvilken faction ICF
     * nå tilhører.
     */
    public static void captureICF(int cityID, int icf, int faction) {

        //Sjekk først at ICF er gyldig.
        if (icf < 0 || icf > 4) {
            return;
        }

        City c = getCity(cityID);

        //Sjekk at vi fikk en by og at status er WAR.
        if (c == null || c.getStatus() != 2) {
            return;
        }

        c.setICFstatus(icf, faction);

        //Sjekk om byen har blitt overtatt.
        if (c.getWarResult() != c.getFaction()) {
            //Oppdater status for byen.
            int currentTime = (int) (System.currentTimeMillis() / 1000);

            c.setFaction(faction);
            c.setStatus(0); //Tilbake til cease fire
            c.setCountDown(currentTime + config.Server.occupationCityCeaseFire);

            broadcastWarfareSettled8076(c);

            //Dersom det gjelder Richmond/Newman tildel medaljer.
            if (c.getCityID() == 0x3A || c.getCityID() == 0x3B) {
                giveMedals(c);
            }

            //Fjern alle spillere fra listen over deltakere i angrepet.
            c.clearParticipantList();
        }

        //Send opcode 0x8070 til alle spillere for å informere om endringer i by status.
        broadcastOpcode8070();
    }

    /**
     * Går gjennom alle PVP byene og returnerer den byen med oppgitt ID.
     *
     * @param cityID ID for byen vi skal returnere.
     *
     * @return City objekt med oppgitt cityID eller null hvis ikke funnet.
     */
    public static City getCity(int cityID) {

        for (int i = 0; i < cityList.length; i++) {
            if (cityList[i].getCityID() == cityID) {
                return cityList[i];
            }
        }

        return null;
    }

    /**
     * Denne metoden vil oppdatere Newman/Richmond medal score for en spiller.
     *
     * Den kalles hver gang en spiller tar over en ICF og når kampen er over.
     *
     * @param p Spiller dette gjelder.
     */
    public static void updateMedalScore(PlayerGame p, int cityID) {

        //Sjekk om city ID er Richmond (0x3A) eller Newman (0x3B).
        if (cityID == 0x3A) {
            p.getCharacter().setRichmondMedal(p.getCharacter().getRichmondMedal() + 4);
        } else if (cityID == 0x3B) {
            p.getCharacter().setNewmanMedal(p.getCharacter().getNewmanMedal() + 4);
        }

        //Dersom spiller er i Richmond/Newman, send opcode 0x34 for å oppdatere Richmond/Newman score.
        if (cityID == 0x3A || cityID == 0x3B) {

            PacketData pd = new PacketData();

            pd.writeIntBE(p.getCharacter().getCharacterID());
            pd.writeByte((byte) 0x80);
            pd.writeByte((byte) 0x80);
            pd.writeByte((byte) 0x80);
            pd.writeByte((byte) 0x0);
            pd.writeByte((byte) 0x81);

            if (cityID == 0x3A) {
                pd.writeByte((byte) 0x0);
            } else {
                pd.writeByte((byte) 0x1);
            }

            pd.writeIntBE(0x4);

            Packet svarPakke = new Packet(0x8034, pd.getData());

            p.sendPacket(svarPakke);
        }

    }

    /**
     * Denne metoden kalles når kampen i Newman/Richmond er over. Alle
     * deltakerne vil da få tildelt medalje poeng.
     *
     * @param c Byen det gjelder.
     */
    private static void giveMedals(City c) {

        Iterator<PlayerGame> spillere = c.getParticipants();

        while (spillere.hasNext()) {
            updateMedalScore(spillere.next(), c.getCityID());
        }

    }

}
