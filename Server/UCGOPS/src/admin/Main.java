package admin;

import admin.commands.*;
import java.util.HashMap;
import players.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette er hovedklassen til admin systemet.
 *
 * Når en UCGM utfører en kommando sendes den til denne klassen.
 */
public class Main {

    /**
     * Linker admin kommandoer til objektene for å håndtere dem.
     * <String,AdminCommmand> = <admin kommando,objekt for å håndtere den>
     */
    private static final HashMap<String, AdminCommand> kommandoer = new HashMap<String, AdminCommand>();

    static {
        //Sett alle admin kommandoer her.
        kommandoer.put("#news", new news());
        kommandoer.put("#faction", new faction());
        kommandoer.put("#extendtime", new extendTime());
        kommandoer.put("#criminal", new criminal());
        kommandoer.put("#characterattribute", new characterattribute());
        kommandoer.put("#combatskill", new combatskill());
        kommandoer.put("#craftingskill", new craftingskill());
        kommandoer.put("#sysmsg", new sysmsg());
        kommandoer.put("#playerall", new playerall());
        kommandoer.put("#playernear", new playernear());
        kommandoer.put("#blockip", new blockip());
        kommandoer.put("#unblockip", new unblockip());
        kommandoer.put("#kick", new kick());
        kommandoer.put("#kickall", new kickall());
        kommandoer.put("#lockaccount", new lockaccount());
        kommandoer.put("#lockedaccountlist", new lockedaccountlist());
        kommandoer.put("#unlockaccount", new unlockaccount());
        kommandoer.put("#unlockcharacter", new unlockCharacter());
        kommandoer.put("#ban", new ban());
        kommandoer.put("#tban", new tban());
        kommandoer.put("#visible", new visible());
        kommandoer.put("#invisible", new invisible());
        kommandoer.put("#makegm", new makegm());
        kommandoer.put("#makegmblue", new makegmblue());
        kommandoer.put("#makegmadmin", new makegmadmin());
        kommandoer.put("#password", new password());
        kommandoer.put("#getpassword", new getpassword());
        kommandoer.put("#rename", new rename());
        kommandoer.put("#radius", new radius());
        kommandoer.put("#tele", new tele());
        kommandoer.put("#telexy", new telexy());
        kommandoer.put("#status", new status());
        kommandoer.put("#close", new close());
        kommandoer.put("#open", new open());
        kommandoer.put("#position", new position());
        kommandoer.put("#rank", new rank());
        kommandoer.put("#save", new save());
        kommandoer.put("#score", new score());
        kommandoer.put("#shutdown", new shutdown());
        kommandoer.put("#spawn", new spawn());
        kommandoer.put("#spawns", new spawns());
        kommandoer.put("#spawnc", new spawnc());
        kommandoer.put("#spawnv", new spawnv());
        kommandoer.put("#zpawnv", new zpawnv());
        kommandoer.put("#spawnw", new spawnw());
        kommandoer.put("#upgrade", new upgrade());
    }

    /**
     * Når en admin kommando skal utføres sendes den til denne metoden.
     *
     * @param p Spiller som utførte kommando.
     * @param cmd Hele kommandoen, inkludert argumenter.
     */
    public static void executeCommand(PlayerChat p, String cmd) {

        //Sjekk at vi fikk en kommando og spilleren er UCGM.
        if (cmd == null || p == null) {
            return;
        }

        String[] args = cmd.split(" ");

        AdminCommand command = kommandoer.get(args[0]); //Hent objektet for å håndtere kommandoen.

        if (command == null) {
            return; //Feil/ugyldig kommando.
        }
        //Sjekk at UCGM har rett til å utføre denne kommandoen.
        if (!command.checkGMrights(p)) {
            return;
        }

        //Skriv log melding om at en UCGM utførte kommandoen.
        String str = "UCGM: Name:" + p.getCharacter().getNavn() + " Account ID:" + p.getAccountID() + ": performed the following command:" + cmd;
        logging.globalserverMsg(str);

        command.execute(p, args);
    }
}
