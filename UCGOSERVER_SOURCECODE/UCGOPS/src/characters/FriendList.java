package characters;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere en spillers friendlist.
 */
public class FriendList implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Spillerens friendlist lagres her. Key = Character ID, Value = navn til
     * friend.
     */
    private ConcurrentHashMap<Integer, String> friends = new ConcurrentHashMap<Integer, String>();

    /**
     * Legger til en ny spiller i friendlist.
     *
     * @param character_id Character ID for ny spiller.
     * @param navn Navn til ny spiller.
     */
    public void addFriend(int character_id, String navn) {

        if (character_id == 0 || navn == null) {
            return;
        }

        this.friends.put(character_id, navn);
    }

    /**
     * Fjerner en spiller fra friendlist.
     *
     * @param character_id Character ID for spiller som skal fjernes.
     */
    public void removeFriend(int character_id) {

        this.friends.remove(character_id);
    }

    /**
     * Returnerer spillerens friendlist.
     *
     * @return Friendlist Key=Character ID, Value=Navn.
     */
    public ConcurrentHashMap<Integer, String> getFriends() {

        return this.friends;
    }
}
