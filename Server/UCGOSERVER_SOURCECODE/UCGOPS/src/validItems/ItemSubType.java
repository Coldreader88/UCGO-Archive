package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne enum brukes til å indikere hvilken underkategori en item tilhører.
 */
public enum ItemSubType {

    Ingen, //Item har ingen underkategori.
    Event, //Event item.
    MS, //Item er mobilesuit
    MA, //Item er mobilearmor
    Battleship, //Item er battleship
    Tank, //Item er tank/bruker tank engine.
    Fighter, //Item er en fighter/helikopter.
    Transport, //Item er en form for transport vehicle, ingen våpen.
    Truck, //Biler
    Ammo, //Item er ammo.
    Shield, //Item er et shield.
    Beam, //Beam rifle
    Shell, //Shell firing
    Sniping, //Sniper beam rifle
    CQB, //CQB våpen
    H2H, //H2H våpen
    Repair, //ER, MR kits.
    Material, //Alt som kan kjøpes i material store og tailor.
    Engine, //Alle engine typer.
    Toys, //Toys.
    Money; //Penger.
}
