package characters;

import characters.Character;
import java.util.Date;

public class CharacterChat extends Character {

    /**
     * Spillerens friendlist.
     */
    private FriendList friends = new FriendList();

    /**
     * Timestamp. Angir sist gang opcode 0x4, keep alive, ble mottatt. Start tid
     * er nåværende tidspunkt.
     */
    private int LastTimeOpcode0x4Received = (int) (new Date().getTime() / 1000);

    public CharacterChat(int id, String navn, int gender, int faction) {
        super(id, navn, gender, faction);
    }

    /**
     * Returnerer spillerens friendlist.
     *
     * @return Friendlist.
     */
    public FriendList getFriends() {
        return this.friends;
    }

    /**
     * Setter spillerens friendlist.
     *
     * @param friends Friendlist som skal brukes.
     */
    public void setFriends(FriendList friends) {
        this.friends = friends;
    }

    public int getLastTimeOpcode0x4Received() {
        return this.LastTimeOpcode0x4Received;
    }

    public void setLastTimeOpcode0x4Received(int time) {
        this.LastTimeOpcode0x4Received = time;
    }

}
