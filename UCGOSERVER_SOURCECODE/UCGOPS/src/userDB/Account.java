package userDB;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere en bruker konto.
 */
public class Account {

    //ID nummer for konto.
    private int accountID;

    //Brukernavn for login.
    private String username;

    //Passord for login.
    private String password;

    //UCGM tag til konto.
    private int ucgmTag = 0xA;

    //Unix timestamp når konto ble opprettet.
    private int createTime;

    //Unix timestamp når bruker sist logget inn.
    private int lastLogin;

    //Indikerer om kontoen er stengt.
    private boolean locked = false;

    //Unix timestamp som angir når bruker ble sist merket criminal. Feltet LAGRES IKKE til fil.
    private int criminalStart;

    /**
     * Oppretter nytt konto objekt med oppgitt ID.
     *
     * @param accountID
     */
    public Account(int accountID) {
        this.accountID = accountID;
    }

    /**
     * Oppretter nytt konto objekt med oppgitt id, brukernavn og passord.
     *
     * @param accountID
     * @param username
     * @param password
     */
    public Account(int accountID, String username, String password) {
        this(accountID);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUcgmTag() {
        return ucgmTag;
    }

    public void setUcgmTag(int ucgmTag) {
        this.ucgmTag = ucgmTag;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getAccountID() {
        return accountID;
    }

    public int getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(int lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return this.locked;
    }

    /**
     * Setter at bruker/kontoen er criminal merket fra nåværende tidspunkt.
     */
    public void setCriminal() {
        this.criminalStart = (int) (System.currentTimeMillis() / 1000);
    }

    /**
     *
     * @return True dersom bruker/konto er criminal.
     */
    public boolean isCriminal() {

        int time = (int) (System.currentTimeMillis() / 1000);

        return ((this.criminalStart + config.Server.criminal_timeout) > time);
    }

    /**
     * Returnerer konto info klart til å lagres i tekst fil.
     */
    public String toString() {

        StringBuilder str = new StringBuilder();

        int lock = 0;
        if (this.isLocked()) {
            lock = 1;
        }

        str.append(this.accountID + ":username:" + this.username + "\n");
        str.append(this.accountID + ":password:" + this.password + "\n");
        str.append(this.accountID + ":ucgmTag:" + this.ucgmTag + "\n");
        str.append(this.accountID + ":createTime:" + this.createTime + "\n");
        str.append(this.accountID + ":lastLogin:" + this.lastLogin + "\n");
        str.append(this.accountID + ":locked:" + lock + "\n");

        return str.toString();
    }

}
