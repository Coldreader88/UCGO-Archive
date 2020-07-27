package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen registrerer bygninger som kan brukes. f.eks ammo tent.
 */
public class RegisterBuildings {

    public static void execute() {

        //LCT
        ItemList.registerItem(0x53024, new GameItem(ItemType.Building, ItemSubType.Ingen, false, false, 0));

        //Registrer EF bygninger som brukes til camps, IKKE tents.
        ItemList.registerItem(0x53020, new GameItem(ItemType.Building, ItemSubType.Ingen, true, false, 0)); //EF Repair
        ItemList.registerItem(0x53021, new GameItem(ItemType.Building, ItemSubType.Ingen, true, false, 0)); //EF weapon shop
        ItemList.registerItem(0x53025, new GameItem(ItemType.Building, ItemSubType.Ingen, false, false, 0)); //EF hangar
        ItemList.registerItem(0x53026, new GameItem(ItemType.Building, ItemSubType.Ingen, false, false, 0)); //EF bank
        ItemList.registerItem(0x53028, new GameItem(ItemType.Building, ItemSubType.Ingen, false, false, 0)); //EF machine store

        //Registrer ZEON bygninger som brukes camps, IKKE tents.
        ItemList.registerItem(0x53022, new GameItem(ItemType.Building, ItemSubType.Ingen, true, false, 0)); //Zeon repair
        ItemList.registerItem(0x53023, new GameItem(ItemType.Building, ItemSubType.Ingen, true, false, 0)); //Zeon weapon shop
        ItemList.registerItem(0x5302A, new GameItem(ItemType.Building, ItemSubType.Ingen, false, false, 0)); //Zeon hangar
        ItemList.registerItem(0x5302B, new GameItem(ItemType.Building, ItemSubType.Ingen, false, false, 0)); //Zeon bank
        ItemList.registerItem(0x5302D, new GameItem(ItemType.Building, ItemSubType.Ingen, false, false, 0)); //Zeon machine store

    }

}
