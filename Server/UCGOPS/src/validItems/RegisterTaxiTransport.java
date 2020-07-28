package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen registrerer alle gyldige ID nummere for /taxi og /transport
 * "kjøretøy".
 */
public class RegisterTaxiTransport {

    public static void execute() {

        //Registrer for EF.
        ItemList.registerItem(0x61A90, new GameItem(ItemType.Vehicle, ItemSubType.Ingen, false, false, 650)); //Taxi
        ItemList.registerItem(0x61A85, new GameItem(ItemType.Vehicle, ItemSubType.Ingen, false, false, 1500)); //Midea
        ItemList.registerItem(0x61A95, new GameItem(ItemType.Vehicle, ItemSubType.Ingen, false, false, 650)); //Space taxi
        ItemList.registerItem(0x61A98, new GameItem(ItemType.Vehicle, ItemSubType.Ingen, false, false, 1500)); //Space transport

        //Registrer for Zeon.
        ItemList.registerItem(0x61A89, new GameItem(ItemType.Vehicle, ItemSubType.Ingen, false, false, 1500)); //Fatuncle
        ItemList.registerItem(0x61A99, new GameItem(ItemType.Vehicle, ItemSubType.Ingen, false, false, 1500)); //Space transport

        //Registrer space tingen.
        ItemList.registerItem(0x61A93, new GameItem(ItemType.Vehicle, ItemSubType.Ingen, false, false, 2000)); //Space shuttle EF.
        ItemList.registerItem(0x61A94, new GameItem(ItemType.Vehicle, ItemSubType.Ingen, false, false, 2000)); //Space shuttle Zeon.

    }
}
