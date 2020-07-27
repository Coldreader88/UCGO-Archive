package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen registrer alt som finnes i material store, tailor og alle
 * materialer som kan finnes.
 */
public class RegisterMaterials {

    public static void execute() {

        //Raw materials
        ItemList.registerItem(0x81651, new GameItem(ItemType.General, ItemSubType.Material, false, false, 95, 70, true)); //Iron ore
        ItemList.registerItem(0x81652, new GameItem(ItemType.General, ItemSubType.Material, false, false, 105, 80, true)); //Bauxite
        ItemList.registerItem(0x81653, new GameItem(ItemType.General, ItemSubType.Material, false, false, 630, 475, true)); //Fine rutile
        ItemList.registerItem(0x81654, new GameItem(ItemType.General, ItemSubType.Material, false, false, 145, 120, true)); //Rutile
        ItemList.registerItem(0x81655, new GameItem(ItemType.General, ItemSubType.Material, false, false, 1260, 945, true)); //Lunatitanium

        //Materialer kjøpt i material store
        ItemList.registerItem(0x07C830, new GameItem(ItemType.General, ItemSubType.Material, false, true, 2200, 2190, true)); //titanium alloy
        ItemList.registerItem(0x07C831, new GameItem(ItemType.General, ItemSubType.Material, false, true, 1800, 1560, true)); //super high tensile steel
        ItemList.registerItem(0x07C832, new GameItem(ItemType.General, ItemSubType.Material, false, true, 4500, 4000, true)); //titanium ceramic composite
        ItemList.registerItem(0x07C833, new GameItem(ItemType.General, ItemSubType.Material, false, true, 20000, 18000, true)); //lunatitanium alloy
        ItemList.registerItem(0x07C836, new GameItem(ItemType.General, ItemSubType.Material, false, false, 250, 194, true)); //steel
        ItemList.registerItem(0x07C83A, new GameItem(ItemType.General, ItemSubType.Material, false, false, 750, 515, true)); //fine ceramics
        ItemList.registerItem(0x07C83E, new GameItem(ItemType.General, ItemSubType.Material, false, false, 520, 440, true)); //titanium steel
        ItemList.registerItem(0x07C837, new GameItem(ItemType.General, ItemSubType.Material, false, false, 250, 215, true)); //alumina

        //Oppgraderings deler
        ItemList.registerItem(0x07C845, new GameItem(ItemType.General, ItemSubType.Material, true, false, 8000, 4000, true)); //enhancement package
        ItemList.registerItem(0x07C846, new GameItem(ItemType.General, ItemSubType.Material, true, false, 11000, 5500, true)); //accuracy improvement package
        ItemList.registerItem(0x07C847, new GameItem(ItemType.General, ItemSubType.Material, true, false, 11000, 5500, true)); //defensive improvement package
        ItemList.registerItem(0x07C84A, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 4000, true)); //Ginius parts
        ItemList.registerItem(0x07C84B, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 5500, true)); //Mosk parts
        ItemList.registerItem(0x07C84C, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 5500, true)); //Tem parts
        ItemList.registerItem(0x07C848, new GameItem(ItemType.General, ItemSubType.Material, false, false, 200000000, 15000000, true)); //Full armor parts
        ItemList.registerItem(0x07C849, new GameItem(ItemType.General, ItemSubType.Material, false, true, 25000000, 1000000, true)); //Magnet coating vehicle

        //Materialer kjøpt i Sewings store
        ItemList.registerItem(0x07C841, new GameItem(ItemType.General, ItemSubType.Material, true, false, 1250, 875, true)); //glass fibers
        ItemList.registerItem(0x07C842, new GameItem(ItemType.General, ItemSubType.Material, true, false, 1250, 875, true)); //carbon fibers
        ItemList.registerItem(0x033457, new GameItem(ItemType.General, ItemSubType.Material, true, false, 500, 250, true)); //Cotton yarn
        ItemList.registerItem(0x03827A, new GameItem(ItemType.General, ItemSubType.Material, true, false, 3000, 1500, true)); //Leather
        ItemList.registerItem(0x038279, new GameItem(ItemType.General, ItemSubType.Material, true, false, 1000, 500, true)); //Silk yarn
        ItemList.registerItem(0x038278, new GameItem(ItemType.General, ItemSubType.Material, true, false, 1000, 500, true)); //Wool yarn
        ItemList.registerItem(0x055730, new GameItem(ItemType.General, ItemSubType.Material, true, false, 500, 250, true)); //Basic color
        ItemList.registerItem(0x055732, new GameItem(ItemType.General, ItemSubType.Material, true, false, 1500, 750, true)); //Dark color
        ItemList.registerItem(0x055731, new GameItem(ItemType.General, ItemSubType.Material, true, false, 1000, 500, true)); //Light color
        ItemList.registerItem(0x055733, new GameItem(ItemType.General, ItemSubType.Material, true, false, 2000, 1000, true)); //Monotone color

        //Gemstones
        ItemList.registerItem(0x8647D, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 10000)); //Opal
        ItemList.registerItem(0x8647E, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 15000)); //Topaz
        ItemList.registerItem(0x8647F, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 20000)); //Amethyst
        ItemList.registerItem(0x86480, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 30000)); //Ruby
        ItemList.registerItem(0x86481, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 40000)); //Emerald
        ItemList.registerItem(0x86620, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 50000)); //Saphire
        ItemList.registerItem(0x86621, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 60000)); //Diamond

        //Fossils
        ItemList.registerItem(0x8647A, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 65000)); //Lizzard
        ItemList.registerItem(0x8647B, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 70000)); //Skull
        ItemList.registerItem(0x8647C, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 80000)); //Ammonite
        ItemList.registerItem(0x8661E, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 90000)); //Trilobite
        ItemList.registerItem(0x8661F, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 100000)); //Apeman

        //Andre materialer
        ItemList.registerItem(0x07C843, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 10000, true)); //MS junk parts
        ItemList.registerItem(0x07C844, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 10000, true)); //Fine lunatitanium alloy
        ItemList.registerItem(0x07C84D, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 10000, true)); //Fine TCC
        ItemList.registerItem(0x07C84E, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 400000, true)); //Gundarium Alloy
        ItemList.registerItem(0x07C84F, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 400000, true)); //Fine Gundarium Alloy
        ItemList.registerItem(0x07C850, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 1, true)); //MSZ-006 Parts
        ItemList.registerItem(0x07C851, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 1, true)); //AMX-107 Parts
        ItemList.registerItem(0x07C852, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 1, true)); //GP-02A Parts
        ItemList.registerItem(0x07C853, new GameItem(ItemType.General, ItemSubType.Material, false, false, 0, 1, true)); //Super Gundam Parts
        ItemList.registerItem(0x07C834, new GameItem(ItemType.General, ItemSubType.Material, true, false, 10, 10, true)); //silicon wafer
        ItemList.registerItem(0x07C835, new GameItem(ItemType.General, ItemSubType.Material, true, false, 10, 10, true)); //silica glass
        ItemList.registerItem(0x07C838, new GameItem(ItemType.General, ItemSubType.Material, true, false, 10, 10, true)); //aluminum
        ItemList.registerItem(0x07C839, new GameItem(ItemType.General, ItemSubType.Material, true, false, 10, 10, true)); //graphite
        ItemList.registerItem(0x07C83B, new GameItem(ItemType.General, ItemSubType.Material, true, false, 10, 10, true)); //carbon
        ItemList.registerItem(0x07C83D, new GameItem(ItemType.General, ItemSubType.Material, true, false, 10, 10, true)); //aluminum alloy

    }

}
