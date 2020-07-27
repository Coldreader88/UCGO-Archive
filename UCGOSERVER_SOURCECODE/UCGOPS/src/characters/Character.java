package characters;

import packetHandler.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette er superklassen for objektene som representerer en character i spillet.
 */
public abstract class Character {

    //Character ID
    private int characterID;

    //IP addressen til spilleren.
    private String ip = new String();

    //Character navn.
    private String navn;

    //Gyldige verdier for disse er de samme som UCGO klienten bruker.
    private int gender;
    private int faction;

    //Rank til character. Gyldige verdier er de som UCGO klienten bruker.
    private int rank;

    //Antall rank poeng spiller har samlet opp.
    private int rankScore;

    //Utseende til character.
    private Appearance utseende;

    //Klær character har på seg.
    private Clothing clothes;

    //Characters skills.
    private SkillManager skills;

    //Antall kills.
    private int score;

    //Antall tap.
    private int losses;

    //Newman medal score.
    private int newmanMedal;

    //Richmond medal score.
    private int richmondMedal;

    /**
     *
     * @param id Character ID.
     * @param navn Navn
     */
    public Character(int id, String navn, int gender, int faction) {

        this.characterID = id;
        this.navn = navn;
        this.gender = gender;
        this.faction = faction;

    }

    /**
     *
     * @return Character ID.
     */
    public int getCharacterID() {

        return this.characterID;
    }

    /**
     *
     * @return Navnet til character.
     */
    public String getNavn() {

        return this.navn;
    }

    /**
     * Setter utseende til en character.
     *
     * @param utseende
     */
    public void setAppearance(Appearance utseende) {
        this.utseende = utseende;
    }

    /**
     * Returnerer utseende til en character.
     *
     * @return Appearance objekt, eller NULL hvis ikke satt.
     */
    public Appearance getAppearance() {
        return this.utseende;
    }

    /**
     * Setter klær for character.
     *
     * @param clothes
     */
    public void setClothing(Clothing clothes) {
        this.clothes = clothes;
    }

    /**
     * Returnerer klær for character.
     *
     * @return
     */
    public Clothing getClothing() {
        return this.clothes;
    }

    /**
     * Setter skill objektet denne character skal bruke.
     *
     * @param skills Skill objekt.
     */
    public void setSkills(SkillManager skills) {
        this.skills = skills;
    }

    /**
     * Returnerer skill objektet character bruker.
     *
     * @return Skill objekt.
     */
    public SkillManager getSkills() {
        return this.skills;
    }

    /**
     *
     * @return Faction.
     */
    public int getFaction() {
        return this.faction;
    }

    /**
     *
     * @return Gender.
     */
    public int getGender() {
        return this.gender;
    }

    /**
     *
     * @return Character rank.
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Setter character rank.
     *
     * @param rank Rank.
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRankScore() {
        return this.rankScore;
    }

    public void setRankScore(int rankScore) {
        this.rankScore = rankScore;
    }

    /**
     * Gir spiller rank poeng.
     *
     * @param poeng Hvor mange poeng spiller får.
     */
    public void gainRank(int poeng) {
        this.rankScore += poeng;
        this.calculateRank();
    }

    /**
     * Trekker spiller for rank poeng.
     *
     * @param poeng Hvor mange poeng spiller mister.
     */
    public void loseRank(int poeng) {
        this.rankScore -= poeng;
        this.calculateRank();
    }

    /**
     * Sjekker hvor mange rank poeng spiller har og avgjør om rank skal øke
     * eller synke.
     */
    private void calculateRank() {

        //Sjekk at spiller ikke får negativ rank.
        if (this.rankScore < 0 && this.rank == 0) {
            this.rankScore = 0;
            return;
        }

        //Beregn poengsum krevet for å oppnå neste rank.
        int nesteRank = 200 * (this.rank + 1);

        if (this.rankScore >= nesteRank && this.rank < 15) {
            //Rank skal økes.
            this.rank++;
            this.rankScore = 0;

            //Send beskjed til spiller om endring.
            this.sendRank8034(1);

        } else if (this.rankScore < 0) {
            //Rank skal synke.
            this.rank--;
            this.rankScore = (200 * (this.rank + 1)) / 2;

            //Send beskjed til spiller om endring.
            this.sendRank8034(-1);
        }

    }

    /**
     * Sender opcode 0x8034 til spiller for å informere om endring i rank.
     *
     * @param retning Angir om rank øker (+1) eller synker (-1)
     */
    private void sendRank8034(int retning) {

        PacketData svar = new PacketData();

        svar.writeIntBE(this.characterID);
        svar.writeByte((byte) 0x81);
        svar.writeIntBE(retning);
        svar.writeByte((byte) 0x80);
        svar.writeByte((byte) 0x80);
        svar.writeByte((byte) 0x0);
        svar.writeByte((byte) 0x80);

        Packet svar8034 = new Packet(0x8034, svar.getData());

        gameServer.MultiClient.sendPacketToPlayer(svar8034, this.characterID);
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

    public int getNewmanMedal() {
        return newmanMedal;
    }

    public void setNewmanMedal(int newmanMedal) {
        this.newmanMedal = newmanMedal;
    }

    public int getRichmondMedal() {
        return richmondMedal;
    }

    public void setRichmondMedal(int richmondMedal) {
        this.richmondMedal = richmondMedal;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public String getIP() {
        return this.ip;
    }
}
