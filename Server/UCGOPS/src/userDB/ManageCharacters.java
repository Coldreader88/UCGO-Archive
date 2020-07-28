package userDB;

import characters.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å håndtere spillerenes characters.
 */
public class ManageCharacters {

    /**
     * Alle characters opprettet av brukere lagres her.
     *
     * Key = Character ID, Value = GameCharacter objektet som inneholder info om
     * character.
     */
    private static ConcurrentHashMap<Integer, GameCharacter> characters = new ConcurrentHashMap<Integer, GameCharacter>();

    /**
     * Oppretter en ny character og linker den til oppgitt konto.
     *
     * @param account_id Konto ID character tilhører.
     *
     * @return Nytt tomt GameCharacter objekt (Character ID satt) eller NULL
     * dersom ugyldig konto ID.
     */
    public synchronized static GameCharacter newGameCharacter(int account_id) {

        Account konto = ManageAccounts.getAccount(account_id);

        if (konto == null) {

            System.out.println("ManageCharacters.java Error: Invalid account ID: " + account_id);
            return null;
        }

        //OK! Konto eksisterer, lag nytt unikt character ID.
        int character_id;

        do {
            character_id = (int) (Math.random() * (double) 0xFFFFF);
            character_id = (character_id << 4) | 0x09000000;
        } while (characters.get(character_id) != null);

        //Nå har vi nytt ID, opprett ny character.
        GameCharacter c = new GameCharacter(character_id);

        c.setKonto(konto);

        Object o = characters.put(character_id, c);

        if (o != null) {
            System.out.println("ManageCharacters.java Error: Overwriting old character, ID:" + character_id);
        }

        return c;
    }

    /**
     * Returnerer character tilhørende oppgitt ID.
     *
     * @param character_id ID for character som skal returneres.
     *
     * @return GameCharacter objekt for oppgitt character ID, eller null hvis
     * ikke finnes.
     */
    public static GameCharacter getGameCharacter(int character_id) {
        return characters.get(character_id);
    }

    /**
     *
     * @return Iterator over alle characters.
     */
    public static Iterator<GameCharacter> getAllGameCharacters() {
        return characters.values().iterator();
    }

    /**
     * Sletter en character.
     *
     * @param character_id Character som skal slettes.
     */
    public static void deleteGameCharacter(int character_id) {

        GameCharacter c = characters.get(character_id);

        if (c != null) {
            //Fjern kobling til konto og fjern character.
            c.setKonto(null);
            characters.remove(character_id);
        }
    }

    /**
     *
     * @return Antall registrerte characters.
     */
    public static int getNumberOfCharacters() {
        return characters.size();
    }

    /**
     * Tar imot en string på formatet character id:property:value og tolker den.
     *
     * @param str String som inneholder character info.
     */
    public static void interpretString(String str) {

        String[] data = str.split(":", 3);

        //Sjekk at vi har fått et array med 3 elementer, ellers er det feil i formatet til string.
        if (data.length != 3) {
            admin.logging.globalserverMsg("Invalid format for character info in the string: " + str);
            return;
        }

        try {

            int character_id;

            character_id = Integer.parseInt(data[0]);

            GameCharacter c = getCreateGameCharacter(character_id);

            //Finn hvilken verdi som skal settes.
            if (data[1].compareTo("accountID") == 0) {
                //Sett konto ID
                Account konto = ManageAccounts.getAccount(Integer.parseInt(data[2]));
                if (konto == null) {
                    //Konto character tilhører finnes ikke.
                    admin.logging.globalserverMsg("Error, character belongs to non-existant account: " + str);
                } else {
                    c.setKonto(konto);
                }
            } else if (data[1].compareTo("navn") == 0) {
                //Sett navn
                c.setNavn(data[2]);
            } else if (data[1].compareTo("faction") == 0) {
                //Sett faction
                c.setFaction(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("rank") == 0) {
                //Sett rank
                c.setRank(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("rankScore") == 0) {
                //Sett rank score
                c.setRankScore(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("gender") == 0) {
                //Sett gender
                c.setGender(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("appearance") == 0) {
                //Sett appearance
                Appearance utseende = helpers.Serializing.Deserialize(data[2]);
                if (utseende == null) {
                    admin.logging.globalserverMsg("Error, unable to deserialize Appearance object: " + str);
                } else {
                    c.setAppearance(utseende);
                }
            } else if (data[1].compareTo("position") == 0) {
                //Sett position
                Posisjon p = helpers.Serializing.Deserialize(data[2]);
                if (p == null) {
                    admin.logging.globalserverMsg("Error, unable to deserialize Position object: " + str);
                } else {
                    c.setPosition(p);
                }
            } else if (data[1].compareTo("createTime") == 0) {
                //Sett createTime
                c.setCreateTime(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("activeVehicle") == 0) {
                //Sett activeVehicle
                c.setActiveVehicle(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("skill") == 0) {
                //Sett skills
                SkillManager s = helpers.Serializing.Deserialize(data[2]);
                if (s == null) {
                    admin.logging.globalserverMsg("Error, unable to deserialize SkillManager object: " + str);
                } else {
                    c.setSkills(s);
//BUG FIX! FJERN SENERE!
                    c.getSkills().bugFix();
                }
            } else if (data[1].compareTo("friends") == 0) {
                //Sett friends
                FriendList f = helpers.Serializing.Deserialize(data[2]);
                if (f == null) {
                    admin.logging.globalserverMsg("Error, unable to deserialize FriendList object: " + str);
                } else {
                    c.setFriends(f);
                }
            } else if (data[1].compareTo("score") == 0) {
                //Sett score
                c.setScore(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("losses") == 0) {
                //Sett losses
                c.setLosses(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("newman") == 0) {
                //Sett Newman medal score.
                c.setNewman(Integer.parseInt(data[2]));
            } else if (data[1].compareTo("richmond") == 0) {
                //Sett Richmond medal score.
                c.setRichmond(Integer.parseInt(data[2]));
            } else {
                admin.logging.globalserverMsg("Unsupported property for character in the string: " + str);
            }

        } catch (Exception e) {
            e.printStackTrace();
            admin.logging.globalserverMsg("Error interpreting character info from the string: " + str);
        }
    }

    /**
     * Returnerer GameCharacter objektet som tilhører oppgitt character ID. Hvis
     * det ikke finnes et GameCharacter objekt for oppgitt character ID vil et
     * bli opprettet.
     *
     * @param character_id Character ID
     *
     * @return GameCharacter objektet som tilhører oppgitt ID.
     */
    private static GameCharacter getCreateGameCharacter(int character_id) {

        GameCharacter c = characters.get(character_id);

        if (c == null) {
            //GameCharacter finnes ikke fra før, opprett ny.
            c = new GameCharacter(character_id);
            characters.put(character_id, c);
        }

        return c;
    }

}
