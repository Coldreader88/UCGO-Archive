package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen registrerer diverse items.
 */
public class RegisterStuff {

    public static void execute() {

        //EF$
        ItemList.registerItem(0x7A120, new GameItem(ItemType.General, ItemSubType.Money, false, false, 0, true)); //1EF
        ItemList.registerItem(0x7A121, new GameItem(ItemType.General, ItemSubType.Money, false, false, 0, true)); //99EF
        ItemList.registerItem(0x7A122, new GameItem(ItemType.General, ItemSubType.Money, false, false, 0, true)); //100~9999EF
        ItemList.registerItem(0x7A123, new GameItem(ItemType.General, ItemSubType.Money, false, false, 0, true)); //1000~39999EF
        ItemList.registerItem(0x7A124, new GameItem(ItemType.General, ItemSubType.Money, false, false, 0, true)); //40000~99999999EF
        ItemList.registerItem(0x7A125, new GameItem(ItemType.General, ItemSubType.Money, false, false, 0, true)); //100000000~1000000000EF

        //Andre ting
        ItemList.registerItem(0x864BF, new GameItem(ItemType.General, ItemSubType.Ingen, false, false, 0, 0)); //haro
        ItemList.registerItem(0x865A4, new GameItem(ItemType.General, ItemSubType.Ingen, false, false, 0, 0)); //mini helmet
        ItemList.registerItem(0x865A6, new GameItem(ItemType.General, ItemSubType.Ingen, false, false, 0, 0)); //bouquet
        ItemList.registerItem(0x865A7, new GameItem(ItemType.General, ItemSubType.Ingen, false, false, 0, 0)); //brush
        ItemList.registerItem(560050, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Drinking fountain
        ItemList.registerItem(560244, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Coconut tree
        ItemList.registerItem(560245, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Palm tree
        ItemList.registerItem(560246, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Christmas tree
        ItemList.registerItem(560247, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Giftbox
        ItemList.registerItem(560248, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Snowman
        ItemList.registerItem(560250, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Tori
        ItemList.registerItem(560251, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Pine decoration
        ItemList.registerItem(560256, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Plum blossom
        ItemList.registerItem(560257, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Cherry blossom
        ItemList.registerItem(560258, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Floral clock
        ItemList.registerItem(560260, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Balloon art giraffe
        ItemList.registerItem(560306, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Spring lanterns 1
        ItemList.registerItem(560307, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Spring lanterns 2
        ItemList.registerItem(560313, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Apple tree
        ItemList.registerItem(560314, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Carp streamer
        ItemList.registerItem(560315, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Star Festival bamboo grass
        ItemList.registerItem(560316, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Summer festival tower
        ItemList.registerItem(560010, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Low broadleef tree
        ItemList.registerItem(560011, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Broadleef tree
        ItemList.registerItem(560012, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Low conifer
        ItemList.registerItem(560013, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Conifer
        ItemList.registerItem(560035, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Flower bed 1
        ItemList.registerItem(560036, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Flower bed 2
        ItemList.registerItem(560037, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Flower bed 3
        ItemList.registerItem(560038, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Flower bed 4
        ItemList.registerItem(560049, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Light
        ItemList.registerItem(560319, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Adelaide flag
        ItemList.registerItem(560320, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Melbourne flag
        ItemList.registerItem(560321, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Sydney flag
        ItemList.registerItem(560322, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Canberra flag		
        ItemList.registerItem(560334, new GameItem(ItemType.General, ItemSubType.Event, false, false, 0, 0)); //Tre

    }
}
