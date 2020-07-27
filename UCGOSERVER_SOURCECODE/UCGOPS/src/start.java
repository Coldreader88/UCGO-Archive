
import java.util.Iterator;
import userDB.GameCharacter;
import characters.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette er start for UCGO serveren.
 */
public class start {

    /**
     * @param args
     */
    public static void main(String[] args) {

        //Les inn lagrede data fra fil her.
        userDB.Load.loadAll();
        userDB.CleanUp.execute(); //Fjern gamle kontoer.
        chatServer.TeamManagement.load(); //MÅ KALLES ETTER AT ALLE KONTOER OG CHARACTERS ER HENTET INN.
        containers.ContainerList.load();

        setupDefaultAdminAccount();

        //Sjekk for containere med ugyldig antall items.
        containers.ContainerList.cheatCheck();

        //Les inn heightmap.
        gameServer.Heightmap.loadHeightmap();

        //Les inn AI kart data.
        //npc.cityAI.MapManager.init();

        //Start thread som er ansvarlig for å lagre data.
        serverComp.Save save_thread = new serverComp.Save();
        new Thread(save_thread).start();

        //Start alle serverene.				
        chatServer.start.execute();
        gameServer.start.execute();
        loginServer.start.execute();
        infoServer.start.execute();

        System.out.println("UCGO Private Server running...");

        while (true) {
            try {
                Thread.sleep(10000);

                if (config.Server.server_shutdown) {
                    serverShutdown();
                }
            } catch (Exception e) {
            }

        }
    }

    /**
     * Denne metoden lagrer og slår av server.
     */
    private static void serverShutdown() {

        System.out.println("Shutting down...");

        try {

            chatServer.MultiClient.sendGlobalSystemMessage("Server will shutdown in 3 minutes.");

            Thread.sleep(2 * 60 * 1000);

            chatServer.MultiClient.sendGlobalSystemMessage("Server will shutdown in 1 minute.");

            Thread.sleep(60 * 1000);

            config.Server.server_is_closed = true;

            chatServer.MultiClient.sendGlobalSystemMessage("Server shutdown.");
            gameServer.MultiClient.kickAllPlayers();

            Thread.sleep(5000);

            config.Server.server_is_offline = true;

            //Vent 2 minutter slik at alle spillere blir logget ut.
            Thread.sleep(2 * 60 * 1000);

            serverComp.Save.saveAllServerData();
        } catch (Exception e) {
        }

        System.exit(0);

    }

    /**
     * Oppretter en ny admin konto dersom ingen kontoer eksisterer.
     */
    private static void setupDefaultAdminAccount() {

        if (userDB.ManageAccounts.getNumberOfAccounts() == 0) {
            userDB.Account konto = userDB.ManageAccounts.newAccount(config.Server.defaultAdminAccount, config.Server.defaultAdminPassword);
            konto.setUcgmTag(9);
        }
    }

}
