package serverComp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;
import occupationCity.OccupationCity;
import userDB.*;
import characters.*;
import java.util.Iterator;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen er ansvarlig for å lagre konto, character og spill data ved
 * jevne mellomrom.
 *
 * For å kunne lagre data må alle spillere kobles fra server. Når det er 30
 * minutter igjen til lagring vil server begynne å gi beskjed til spillerene via
 * system message.
 *
 */
public class Save implements Runnable {

    /**
     * Angir tidspunktet for når server skal begynne lagrings prosessen. Dette
     * er ikke når lagring av data vil bli utført men når første melding til
     * brukerene blir sendt ut.
     */
    private int saveHours = 9;
    private int saveMinutes = 45;

    public void run() {

        while (true) {

            try {
                GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("GMT+0"));

                if (gc.get(Calendar.HOUR_OF_DAY) == saveHours && gc.get(Calendar.MINUTE) == saveMinutes) {
                    //Vi har nådd tidspunktet for når vi skal begynne lagrings prosessen.
                    chatServer.MultiClient.sendGlobalSystemMessage("Server will shutdown and save data in 15 minutes.");

                    //Vent 5 minutter, 10 minutter igjen.
                    Thread.sleep(5 * 60 * 1000);
                    chatServer.MultiClient.sendGlobalSystemMessage("Server will shutdown and save data in 10 minutes.");

                    //Vent 7 minutter, 3 minutter igjen.
                    Thread.sleep(7 * 60 * 1000);
                    chatServer.MultiClient.sendGlobalSystemMessage("Server will shutdown and save data in 3 minutes.");

                    //Vent 2 minutter, 1 minutt igjen.
                    Thread.sleep(2 * 60 * 1000);
                    chatServer.MultiClient.sendGlobalSystemMessage("Server will shutdown and save data in 1 minute.");

                    //Vent 1 minutt, ferdig.
                    Thread.sleep(60 * 1000);
                    chatServer.MultiClient.sendGlobalSystemMessage("Server will now shutdown and save data, will be online again within 5 minutes.");

                    //Steng server slik at vi kan save.
                    config.Server.server_is_closed = true;
                    gameServer.MultiClient.kickAllPlayers();
                    //Vent litt før server går offline, ellers vil ikke tilkoblede spillere motta "kick" pakken.
                    Thread.sleep(5 * 1000);
                    config.Server.server_is_offline = true;

                    //Vent 2 minutter slik at alle spillere er fjernet fra spillet
                    Thread.sleep(2 * 60 * 1000);

                    saveAllServerData();
                    gameServer.MultiClient.clearPlayerList();

                    //Spawn spesielle ting som camps da disse ble fjernet under saving.
                    gameServer.SpawnItems.spawn();

                    resetPVPcities();

                    resetDailySkillLimit();

                    config.Server.server_is_closed = false;
                    config.Server.server_is_offline = false;
                } else {
                    //Vi har ikke nådd tidspunktet for når server starte lagrings prosessen. Vent 30 sekunder før vi sjekker igjen.
                    Thread.sleep(30 * 1000);
                }

            } catch (Exception e) {
            }

        }

    }

    /**
     * Denne metoden sørger for at all data på server blir lagret.
     *
     */
    public static synchronized void saveAllServerData() {

        //Fjern items lagret på bakken først. Unødvendig å save dem.
        gameServer.ItemHandler.cleanAllItems();

        userDB.Save.saveAll();
        chatServer.TeamManagement.save();
        containers.ContainerList.save();        
    }

    /**
     * Denne metoden nullstiller Newman, Richmond og Helenes etter at server er
     * ferdig å lagre data.
     *
     */
    private void resetPVPcities() {

        //Velg helt random, 50/50 sjanse, hvilken by som skal være EF/Zeon.
        Random r = new Random();
        if (r.nextInt(100) < 50) {
            //Newman EF, Richmond Zeon
            OccupationCity.getCity(0x3B).setFaction(1);
            OccupationCity.getCity(0x3A).setFaction(2);
        } else {
            //Newman Zeon, Richmond EF
            OccupationCity.getCity(0x3B).setFaction(2);
            OccupationCity.getCity(0x3A).setFaction(1);
        }

        //Velg helt random, 50/50 sjanse, hvilken by som begynner først.
        if (r.nextInt(100) < 50) {
            //Newman først, sett alle byene til cease fire status og start nedtellingen.
            int countDown = (int) (System.currentTimeMillis() / 1000);
            OccupationCity.getCity(0x3B).setStatus(0);
            OccupationCity.getCity(0x3B).setCountDown(countDown + (180 * 60)); //Newman starter etter 3 timer.

            OccupationCity.getCity(0x3A).setStatus(0);
            OccupationCity.getCity(0x3A).setCountDown(countDown + (255 * 60)); //Richmond starter etter 4.25 timer.
        } else {
            //Richmond først
            int countDown = (int) (System.currentTimeMillis() / 1000);
            OccupationCity.getCity(0x3A).setStatus(0);
            OccupationCity.getCity(0x3A).setCountDown(countDown + (180 * 60)); //Richmond starter etter 3 timer.

            OccupationCity.getCity(0x3B).setStatus(0);
            OccupationCity.getCity(0x3B).setCountDown(countDown + (255 * 60)); //Newman starter etter 4.25 timer.
        }

        //Helenes skal begynne etter 5 timer. Samt faction skal byttes om.
        //HELENES ER FORELØPIG SLÅTT AV!
        /*OccupationCity.getCity(0x39).stopICFnpcs();
		OccupationCity.getCity(0x39).setStatus(0);
		
		if ( OccupationCity.getCity(0x39).getFaction() == 1 ) OccupationCity.getCity(0x39).setFaction(2);
		else OccupationCity.getCity(0x39).setFaction(1);
	
		int countDown = (int)(System.currentTimeMillis()/1000);
		OccupationCity.getCity(0x39).setCountDown(countDown+(300*60));*/
        OccupationCity.getCity(0x39).clearParticipantList();
        OccupationCity.getCity(0x3A).clearParticipantList();
        OccupationCity.getCity(0x3B).clearParticipantList();
    }

    /**
     * Nullstiller alle begrensninger på daglig skill training.
     */
    private void resetDailySkillLimit() {
        Iterator<GameCharacter> characters = userDB.ManageCharacters.getAllGameCharacters();
        while (characters.hasNext()) {
            GameCharacter c = characters.next();
            c.dailyMScrafting = 0;
            c.dailyRefinery = 0;
            c.dailyWeaponsCrafting = 0;
        }
    }

}
