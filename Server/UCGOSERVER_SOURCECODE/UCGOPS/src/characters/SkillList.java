package characters;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne enum typen brukes sammen med Skills klassen.
 *
 */
public enum SkillList {
    //NB! Alle keys oppgitt her tilsvarer ID brukt av UCGO for å representere en skill.
    Strength(0),
    Spirit(1),
    Luck(2),
    MobileSuit(0),
    Critical(1),
    NearDodge(3),
    RangedEngagement(4),
    Engagement(5),
    NearEngagement(7),
    Shooting(0xC),
    Sniping(0xD),
    CQB(0xE),
    Defence(0x12),
    WeaponManipulation(0xB),
    HandToHandCombat(0xF),
    Tactics(0x10),
    ShellFiringWeapon(9),
    BeamCartridgeWeapon(8),
    AMBAC(0x11),
    FarDodge(0x13),
    EmergencyRepair(0x14),
    Refinery(1),
    MSMAConstruction(2),
    BattleshipConstruction(3),
    ArmsConstruction(4),
    Mining(0),
    ClothingManufacturing(5);

    private final int key;

    SkillList(int key) {
        this.key = key;
    }

    public int key() {
        return this.key;
    }

}
