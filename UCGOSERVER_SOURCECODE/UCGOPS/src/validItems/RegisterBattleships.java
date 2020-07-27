package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen registrerer gyldige battleships som kan brukes i spillet og
 * våpenene deres.
 *
 * DETTE FOR TING SOM KAN BRUKES AV SPILLERE, IKKE NPCS.
 */
public class RegisterBattleships {

    public static void execute() {

        ItemList.registerItem(0x61A86, new GameItem(ItemType.Vehicle, ItemSubType.Ingen, false, false, 0, 0)); //Midia
        ItemList.registerItem(0xFB773, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, true, 50000000, 2000000)); //Gallop

        ItemList.registerItem(0xFB77E, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, true, 10000000, 2000000)); //Fat Uncle Hangar
        ItemList.registerItem(0xF424A, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, true, 10000000, 2000000)); //Midea Hangar
        ItemList.registerItem(0xF4242, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, true, 10000000, 2000000)); //Midea Gray

        //Disse er egentlig brukt som NPC bosses men de bør registreres for at de skal virke riktig.
        ItemList.registerItem(1000001, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 0, 0)); //Gaw
        ItemList.registerItem(0xFB771, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 50000000, 2500000)); //Big tray
        ItemList.registerItem(0xFB774, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 50000000, 2500000)); //Dobday
        ItemList.registerItem(0xFB775, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 50000000, 25000000)); //Pegasus
        ItemList.registerItem(0xFB776, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 50000000, 25000000)); //Zanzibar
        ItemList.registerItem(0xFB77F, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 50000000, 2500000)); //Salamis
        ItemList.registerItem(0xFB780, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 50000000, 25000000)); //Magellan
        ItemList.registerItem(0xFB781, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 50000000, 2500000)); //Musai
        ItemList.registerItem(0xFB782, new GameItem(ItemType.Vehicle, ItemSubType.Battleship, false, false, 50000000, 25000000)); //Chibe

    }
}
