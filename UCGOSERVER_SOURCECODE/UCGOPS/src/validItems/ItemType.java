package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne enum brukes til å indikere hvilken kategori en item tilhører.
 */
public enum ItemType {

    Vehicle, //MS, MA, Fighters og kjøretøy.
    Clothes, //Klær.
    Weapon, //Våpen.
    Building, //Bygninger som hanger, weapons store osv. IKKE AMMO TENT og REPAIR SOM PLASSERES AV SPILLERE.
    General; //Diverse ting som ikke har attributer. Inkluderer både items som er stackable og ikke.

}
