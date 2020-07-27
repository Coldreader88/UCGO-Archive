package userDB;

import characters.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å representere data lagret om en character.
 */
public class GameCharacter {

    //Bruker konto character tilhører.
    private Account konto;

    //Character ID.
    private int characterID;

    //Navn til character.
    private String navn;

    //Faction character tilhører.
    private int faction;

    //Rank til character.
    private int rank;

    //Rank score til character.
    private int rankScore;

    //Gender til character.
    private int gender;

    //Score
    private int score;

    //Losses
    private int losses;

    //Newman medal score.
    private int newman;

    //Richmond medal score.
    private int richmond;

    //Når character ble opprettet.
    private int createTime;

    //Container ID til vehicle spilleren var i når han logget ut, ellers 0.
    private int activeVehicle;

    //Utseende til character.
    private Appearance appearance;

    //Posisjon til character.
    private Posisjon position;

    //Characters friendlist.
    private FriendList friends;

    //Characters skills.
    //SETT DEN MIDLERTIDIG FOR Å HINDRE NULL POINTER EXCEPTION I OVERGANGEN FRA GAMMEL
    //SKILL TING TIL NY.
    private SkillManager skills = new SkillManager();

    /**
     * Holder orden på hvor mye MS/MA, Weapons og Refinery crafting skills har
     * økt siden server startet eller sist gang vi lagret data. Brukt til å
     * sette en grense for hvor mye disse skills kan øke hver dag for å hindre
     * bruken av macro programmer.
     *
     * DISSE VERDIENE LAGRES IKKE TIL FIL!
     */
    public int dailyMScrafting, dailyWeaponsCrafting, dailyRefinery;

    public GameCharacter(int characterID) {
        this.characterID = characterID;
    }

    public Account getKonto() {
        return konto;
    }

    public void setKonto(Account konto) {
        this.konto = konto;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getFaction() {
        return faction;
    }

    public void setFaction(int faction) {
        this.faction = faction;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRankScore() {
        return this.rankScore;
    }

    public void setRankScore(int rankScore) {
        this.rankScore = rankScore;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getNewman() {
        return newman;
    }

    public void setNewman(int newman) {
        this.newman = newman;
    }

    public int getRichmond() {
        return richmond;
    }

    public void setRichmond(int richmond) {
        this.richmond = richmond;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getActiveVehicle() {
        return activeVehicle;
    }

    public void setActiveVehicle(int activeVehicle) {
        this.activeVehicle = activeVehicle;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    public Posisjon getPosition() {
        return position;
    }

    public void setPosition(Posisjon position) {
        this.position = position;
    }

    public FriendList getFriends() {
        return friends;
    }

    public void setFriends(FriendList friends) {
        this.friends = friends;
    }

    public SkillManager getSkills() {
        return skills;
    }

    public void setSkills(SkillManager skills) {
        this.skills = skills;
    }

    public int getCharacterID() {
        return characterID;
    }

    /**
     * Returnerer character info som en string klar til å lagres i tekstfil.
     */
    public String toString() {

        StringBuilder str = new StringBuilder();

        if (this.konto != null) {
            str.append(this.characterID + ":accountID:" + this.konto.getAccountID() + "\n");
        } else {
            return "";
        }
        str.append(this.characterID + ":navn:" + this.navn + "\n");
        str.append(this.characterID + ":faction:" + this.faction + "\n");
        str.append(this.characterID + ":rank:" + this.rank + "\n");
        str.append(this.characterID + ":rankScore:" + this.rankScore + "\n");
        str.append(this.characterID + ":gender:" + this.gender + "\n");
        str.append(this.characterID + ":createTime:" + this.createTime + "\n");
        str.append(this.characterID + ":activeVehicle:" + this.activeVehicle + "\n");
        str.append(this.characterID + ":score:" + this.score + "\n");
        str.append(this.characterID + ":losses:" + this.losses + "\n");
        str.append(this.characterID + ":newman:" + this.newman + "\n");
        str.append(this.characterID + ":richmond:" + this.richmond + "\n");

        //Utfør serialization av objektene.
        String obj_data;

        obj_data = helpers.Serializing.Serialize(this.appearance);
        if (obj_data != null) {
            str.append(this.characterID + ":appearance:" + obj_data + "\n");
        }

        obj_data = helpers.Serializing.Serialize(this.position);
        if (obj_data != null) {
            str.append(this.characterID + ":position:" + obj_data + "\n");
        }

        obj_data = helpers.Serializing.Serialize(this.skills);
        if (obj_data != null) {
            str.append(this.characterID + ":skill:" + obj_data + "\n");
        }

        obj_data = helpers.Serializing.Serialize(this.friends);
        if (obj_data != null) {
            str.append(this.characterID + ":friends:" + obj_data + "\n");
        }

        return str.toString();
    }
}
