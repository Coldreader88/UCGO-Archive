package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å registrere vehicles som brukes utelukkende av
 * NPCs.
 */
public class RegisterNPCs {

    public static void execute() {

        //EF Supply Trucks
        ItemList.registerItem(0xF4255, new GameItem(ItemType.Vehicle, ItemSubType.Truck, false, false, 0, 0)); //Hangar
        ItemList.registerItem(0xF4243, new GameItem(ItemType.Vehicle, ItemSubType.Truck, false, false, 0, 0)); //Repair
        ItemList.registerItem(0xF4245, new GameItem(ItemType.Vehicle, ItemSubType.Truck, false, false, 0, 0)); //Weapon
        ItemList.registerItem(0xF4257, new GameItem(ItemType.Vehicle, ItemSubType.Truck, false, false, 0, 0)); //Machine
        ItemList.registerItem(0xF4258, new GameItem(ItemType.Vehicle, ItemSubType.Truck, false, false, 0, 0)); //Bank

        //Zeon Supply Trucks
        ItemList.registerItem(0xF4256, new GameItem(ItemType.Vehicle, ItemSubType.Truck, false, false, 0, 0)); //Hangar
        ItemList.registerItem(0xF4244, new GameItem(ItemType.Vehicle, ItemSubType.Truck, false, false, 0, 0)); //Repair
        ItemList.registerItem(0xF4246, new GameItem(ItemType.Vehicle, ItemSubType.Truck, false, false, 0, 0)); //Weapon
        ItemList.registerItem(0xF4259, new GameItem(ItemType.Vehicle, ItemSubType.Truck, false, false, 0, 0)); //Machine
        ItemList.registerItem(0xF425A, new GameItem(ItemType.Vehicle, ItemSubType.Truck, false, false, 0, 0)); //Bank

        //Columbus supply
        ItemList.registerItem(0xF424C, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 0, 0)); //Hangar
        ItemList.registerItem(0xF424F, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 0, 0)); //Repair
        ItemList.registerItem(0xF424E, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 0, 0)); //Weapon
        ItemList.registerItem(0xF424D, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 0, 0)); //Machine
        ItemList.registerItem(0xF424B, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 0, 0)); //Bank

        //Puzock supply
        ItemList.registerItem(0xF4251, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 0, 0)); //Hangar
        ItemList.registerItem(0xF4254, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 0, 0)); //Repair
        ItemList.registerItem(0xF4253, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 0, 0)); //Weapon
        ItemList.registerItem(0xF4252, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 0, 0)); //Machine
        ItemList.registerItem(0xF4250, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 0, 0)); //Bank

    }

}
