package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen registrerer alt som kan kjøpes i toy shop.
 */
public class RegisterToys {

    public static void execute() {

        //MS kits
        ItemList.registerItem(0x864CE, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 800, 400, true)); //1:100 DOM
        ItemList.registerItem(0x864CA, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 700, 350, true)); //1:100 Gundam
        ItemList.registerItem(0x864D5, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 300, 150, true)); //Char's musai
        ItemList.registerItem(0x864CB, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 300, 150, true)); //Musai
        ItemList.registerItem(0x864D1, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 1000, 500, true)); //1:1200 whitebase
        ItemList.registerItem(0x864C5, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 300, 150, true)); //AGG
        ItemList.registerItem(0x864C1, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 400, 200, true)); //Agguy
        ItemList.registerItem(0x864CC, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 300, 150, true)); //Chars zaku2
        ItemList.registerItem(0x864CD, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 300, 150, true)); //Gouf
        ItemList.registerItem(0x864C9, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 300, 150, true)); //1:144 gundam
        ItemList.registerItem(0x864CF, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 300, 150, true)); //1:144 z'gok
        ItemList.registerItem(0x864D4, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 300, 150, true)); //Zaku2
        ItemList.registerItem(0x864C4, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 300, 150, true)); //Zanzibar
        ItemList.registerItem(0x864C0, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 500, 250, true)); //Zakrello
        ItemList.registerItem(0x864C2, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 400, 200, true)); //Adzam
        ItemList.registerItem(0x864C7, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 700, 350, true)); //Braw-bro
        ItemList.registerItem(0x864C3, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 300, 150, true)); //Grublo
        ItemList.registerItem(0x864C6, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 400, 200, true)); //1:550 midia
        ItemList.registerItem(0x864D0, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 2000, 1000, true)); //1:60 char's zaku
        ItemList.registerItem(0x864D3, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 2000, 1000, true)); //1:60 gundam

        //Gundam historica 01-10
        ItemList.registerItem(0x86470, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 543, 271, true));
        ItemList.registerItem(0x86471, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 543, 271, true));
        ItemList.registerItem(0x86472, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 543, 271, true));
        ItemList.registerItem(0x86473, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 543, 271, true));
        ItemList.registerItem(0x86474, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 543, 271, true));
        ItemList.registerItem(0x86475, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 543, 271, true));
        ItemList.registerItem(0x86476, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 543, 271, true));
        ItemList.registerItem(0x86477, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 543, 271, true));
        ItemList.registerItem(0x86478, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 543, 271, true));
        ItemList.registerItem(0x86479, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 543, 271, true));

        //Unidot
        ItemList.registerItem(0x86624, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 8, 4, true)); //Black
        ItemList.registerItem(0x86625, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 8, 4, true)); //Blue
        ItemList.registerItem(0x86628, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 8, 4, true)); //Green
        ItemList.registerItem(0x86629, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 8, 4, true)); //Light blue
        ItemList.registerItem(0x86627, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 8, 4, true)); //Purple
        ItemList.registerItem(0x86626, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 8, 4, true)); //Red
        ItemList.registerItem(0x8662B, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 8, 4, true)); //White
        ItemList.registerItem(0x8662A, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 8, 4, true)); //Yellow

        //Unidot revo red blink.
        ItemList.registerItem(0x86653, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10000, 5, true));

        //Unidot revo
        ItemList.registerItem(0x8663B, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86634, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86632, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86633, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86652, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86636, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86630, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86631, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8664D, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86637, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86641, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8662C, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86645, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86647, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8665F, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8665C, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86639, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86650, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86657, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86649, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86659, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86655, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8665B, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8663E, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8662E, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8665E, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86635, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86640, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8663D, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86658, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86656, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86648, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8664A, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86642, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8663C, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8664B, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8662D, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86643, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8662F, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86646, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8664E, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8664C, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86644, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8665A, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8663A, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86651, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86638, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8663F, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x86654, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));
        ItemList.registerItem(0x8664F, new GameItem(ItemType.General, ItemSubType.Toys, true, false, 10, 5, true));

    }
}
