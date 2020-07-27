package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen registrerer alle gyldige ammo typer og ER/MR kits som kan
 * brukes.
 */
public class RegisterAmmoRepair {

    public static void execute() {

        //Registrer alle ammo typer.
        ItemList.registerItem(0x83D69, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Bazooka cartridge
        ItemList.registerItem(0x83D6E, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Handgrenade
        ItemList.registerItem(0x83D62, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Energypack
        ItemList.registerItem(0x83D64, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Cannon cartridge
        ItemList.registerItem(0x83D60, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Machinegun cartridge
        ItemList.registerItem(0x83D61, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Vulcan cartridge
        ItemList.registerItem(0x83D6B, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Rocket launcher cartridge
        ItemList.registerItem(0x83D63, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Tank cannon cartridge
        ItemList.registerItem(0x83D65, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Tank/fighter machinegun cartridge
        ItemList.registerItem(0x83D66, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Tank/fighter missile
        ItemList.registerItem(0x83D6A, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Boomerang cutter cartridge
        ItemList.registerItem(0x83D6D, new GameItem(ItemType.General, ItemSubType.Ammo, true, false, 0, 0, true)); //Cracker

        //Registrer EF ER-kits.
        ItemList.registerItem(0x4BAF6, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 0, 0, true)); //ER L1
        ItemList.registerItem(0x4BAF7, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 12600, 6300, true)); //ER L2
        ItemList.registerItem(0x4BAF8, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 63000, 31500, true)); //ER L3
        ItemList.registerItem(0x4BAF0, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 700, 350, true)); //Tank/Fighter ER L1
        ItemList.registerItem(0x4BAF1, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 2800, 1400, true)); //Tank/Fighter ER L2
        ItemList.registerItem(0x4BAF2, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 5600, 2800, true)); //Tank/Fighter ER L3

        //Registrer Zeon ER-kits.
        ItemList.registerItem(0x4BAF9, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 0, 0, true)); //ER L1
        ItemList.registerItem(0x4BAFA, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 12600, 6300, true)); //ER L2
        ItemList.registerItem(0x4BAFB, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 63000, 31500, true)); //ER L3
        ItemList.registerItem(0x4BAF3, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 700, 350, true)); //Tank/Fighter ER L1
        ItemList.registerItem(0x4BAF4, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 2800, 1400, true)); //Tank/Fighter ER L2
        ItemList.registerItem(0x4BAF5, new GameItem(ItemType.General, ItemSubType.Repair, true, false, 5600, 2800, true)); //Tank/Fighter ER L3

        //Registrer MR-kits.
        ItemList.registerItem(0x44667, new GameItem(ItemType.Weapon, ItemSubType.Repair, true, false, 1500, 750)); //MS/MA MR L1
        ItemList.registerItem(0x44668, new GameItem(ItemType.Weapon, ItemSubType.Repair, true, false, 3000, 1500)); //MS/MA MR L2
        ItemList.registerItem(0x44669, new GameItem(ItemType.Weapon, ItemSubType.Repair, true, false, 6000, 3000)); //MS/MA MR L3
        ItemList.registerItem(0x4466B, new GameItem(ItemType.Weapon, ItemSubType.Repair, true, false, 500, 250)); //Tank/Fighter MR L1
        ItemList.registerItem(0x4466C, new GameItem(ItemType.Weapon, ItemSubType.Repair, true, false, 1000, 500)); //Tank/Fighter MR L2
        ItemList.registerItem(0x4466D, new GameItem(ItemType.Weapon, ItemSubType.Repair, true, false, 2000, 1000)); //Tank/Fighter MR L3

    }

}
