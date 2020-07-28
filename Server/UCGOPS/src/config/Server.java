package config;

import java.util.Date;

/**
 *
 * @author UCGOSERVER.COM
 *
 * This class contains configuration for the servers.
 *
 */
public class Server {

    /**
     * Default username for admin account.
     */
    public final static String defaultAdminAccount = "admin";

    /**
     * Default password for admin account.
     */
    public final static String defaultAdminPassword = "admin";

    /**
     * Where to store account and game character data.
     *
     * Accounts are stored in userDB_file_location/accounts.txt Game characters
     * in userDB_file_location/characters.txt
     */
    public final static String userDB_file_location = "userdata";

    /**
     * Where the server stores the saved data from the chat and game server.
     */
    public final static String serverdata_file_location = "serverdata";

    /**
     * If an account has not been used within the given time it will be deleted.
     * Any characters linked to the account will also be deleted. Time is given
     * in seconds.
     */
    public final static int accountRemoveTime = 365 * 24 * 60 * 60; //90 days

    /**
     * How long, in seconds, a criminal mark will last.
     */
    public final static int criminal_timeout = 60 * 60;

    /**
     * Indikerer om criminal system er aktivert eller ikke.
     *
     * true = aktivert, false = deaktivert. DETTE GJELDER KUN FOR PVP, IKKE PVE.
     */
    public static boolean criminal_system = true;

    /**
     * If set to true only UCGMs can login.
     */
    public static boolean server_is_closed = false;

    /**
     * If set to true no one can connect to the server and the server will NOT
     * handle any packets received.
     */
    public static boolean server_is_offline = false;

    /**
     * If set to true the server will be shutdown i.e. stop execution and exit
     * program.
     */
    public static boolean server_shutdown = false;

    /**
     * Time when the server was started.
     */
    public static Date start_time = new Date();

    /**
     * Eventuell news update som vises til spiller i journal vindu kort tid
     * etter innlogging. Settes til NULL dersom ingen melding.
     *
     * NB! Koden som sender denne meldingen er i opcode 0x3 til game server.
     */
    public static String serverNews = null;

    /**
     * Message of the day.
     */
    public static String motd = "\n    WELCOME TO\n\nwww.ucgoserver.com\n\n       UCGO PS";

    /**
     * IP address of the gameserver.
     */
    public static String gameserver_ip = "104.251.212.109";//*/"127.0.0.1";

    /**
     * Required client version.
     */
    public final static int client_version = 0x10A9;

    /**
     * If the gameserver doesn't receive opcode 0x3 within the given number of
     * seconds the player is considered logged out. NOTE: Setting it to less
     * than 70 seconds can cause problems with the Earth/Space transport since
     * that takes a little over 60 seconds and no data is sent during the video
     * playback.
     */
    public final static int gameServerLogoutTime = 90;

    /**
     * Used by gameServer.Multiclient.getCharacterList() Specifies max number of
     * PC characters to return to the client in opcode 0x3.
     */
    public final static int gameserver_max_characters = 300;

    /**
     * Defines max number of vehicles a player can place on the ground. If limit
     * is exceeded oldest vehicles will be removed from the game.
     */
    public final static int max_vehicles_on_ground = 20;

    /**
     * Defines max number of item a player can drop on the ground. If limit is
     * exceeded oldest items will be removed from the game.
     */
    public final static int max_items_on_ground = 20;

    /**
     * Defines after how many seconds ownership for items(ms/vehicles) expire.
     */
    public final static int ownership_expires = 3600;

    /**
     * Defines after how many seconds vehicles with no ownership are removed
     * from the game. This applies AFTER ownership has expired.
     */
    public final static int remove_vehicles_time = 2 * 3600;

    /**
     * Defines after how many seconds items (anything not a vehicle) on the
     * ground are removed from the game.
     */
    public final static int remove_items_time = 3600;

    /**
     * If a parked vehicle is destroyed and it has an owner this value defines
     * after how many seconds ownership for the wreckage container will expire.
     */
    public final static int wreckage_owner_expire = 300;

    /**
     * Specifies after how long time, in seconds, a wreckage container will be
     * removed from the game.
     */
    public final static int wreckage_remove_time = 900;

    /**
     * If opcode 0x26 is not received within the given time, in seconds, the
     * server will assume the player has closed the wreckage window.
     */
    public final static int wreckage_lock_timeout = 30;

    /**
     * Max skillpoints for any combat/crafting skill.
     */
    public final static int maxSkillLevel = 1300;

    /**
     * Max skillpoints allowed for character attributes in total.
     */
    public final static int maxCharacterAttributePoints = 700;

    /**
     * Max skillpoints allowed for combat and crafting in total.
     */
    public final static int maxSkillPoints = 8000;

    /**
     * Hvor lenge, i sekunder, cease fire perioden skal vare for en occupation
     * city.
     */
    public final static int occupationCityCeaseFire = 3 * 60 * 60;

    /**
     * Hvor lenge, i sekunder, war perioden skal vare for en occupation city.
     */
    public final static int occupationCityWar = 60 * 60;

    /**
     * Used by serverComp.ServerLoop.run() Specifies how long in milliseconds,
     * at most, the run() method should sleep between every iteration.
     */
    public final static long serverLoopSleep = 1;

    /**
     * Used by serverComp.ServerListener
     */
    public final static int serverListenerLogin = 1;
    public final static int serverListenerChat = 2;
    public final static int serverListenerGame = 3;
    public final static int serverListenerInfo = 4;

}
