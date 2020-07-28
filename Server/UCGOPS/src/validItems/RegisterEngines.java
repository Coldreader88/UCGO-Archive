package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen registrerer alle gyldige engines som kan kjøpes og selges.
 */
public class RegisterEngines {

    public static void execute() {

        //MS TR engines
        ItemList.registerItem(0x046CD0, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear rocket engine typeA lv.1
        ItemList.registerItem(0x046CD2, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear rocket engine typeA lv.2
        ItemList.registerItem(0x046CD3, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear rocket engine typeA lv.3
        ItemList.registerItem(0x046CDB, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear rocket engine typeB lv.1
        ItemList.registerItem(0x046CDC, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear rocket engine typeC lv.1
        ItemList.registerItem(0x046CDD, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear rocket engine typeD lv.1
        ItemList.registerItem(0x046CDE, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear rocket engine typeE lv.1
        ItemList.registerItem(0x046CDF, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear rocket engine typeB lv.2
        ItemList.registerItem(0x046CE0, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear rocket engine typeC lv.2
        ItemList.registerItem(0x046CE1, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear rocket engine typeD lv.2
        ItemList.registerItem(0x046CE2, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear rocket engine typeE lv.2
        ItemList.registerItem(0x046CE3, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear rocket engine typeB lv.3
        ItemList.registerItem(0x046CE4, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear rocket engine typeC lv.3
        ItemList.registerItem(0x046CE5, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear rocket engine typeD lv.3
        ItemList.registerItem(0x046CE6, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear rocket engine typeE lv.3
        ItemList.registerItem(0x046D80, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear rocket engine typeA lv.4
        ItemList.registerItem(0x046D81, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear rocket engine typeB lv.4
        ItemList.registerItem(0x046D82, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear rocket engine typeC lv.4
        ItemList.registerItem(0x046D83, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear rocket engine typeD lv.4
        ItemList.registerItem(0x046D84, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear rocket engine typeE lv.4

        //MS TJ engines
        ItemList.registerItem(0x046CE7, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear jet engine typeA lv.1
        ItemList.registerItem(0x046CE8, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear jet engine typeB lv.1
        ItemList.registerItem(0x046CE9, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear jet engine typeC lv.1
        ItemList.registerItem(0x046CEA, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear jet engine typeD lv.1
        ItemList.registerItem(0x046CEB, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear jet engine typeE lv.1
        ItemList.registerItem(0x046CEC, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear jet engine typeA lv.2
        ItemList.registerItem(0x046CED, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear jet engine typeB lv.2
        ItemList.registerItem(0x046CEE, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear jet engine typeC lv.2
        ItemList.registerItem(0x046CEF, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear jet engine typeD lv.2
        ItemList.registerItem(0x046CF0, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear jet engine typeE lv.2
        ItemList.registerItem(0x046CF1, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear jet engine typeA lv.3
        ItemList.registerItem(0x046CF2, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear jet engine typeB lv.3
        ItemList.registerItem(0x046CF3, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear jet engine typeC lv.3
        ItemList.registerItem(0x046CF4, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear jet engine typeD lv.3
        ItemList.registerItem(0x046CF5, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear jet engine typeE lv.3
        ItemList.registerItem(0x046D85, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear jet engine typeA lv.4
        ItemList.registerItem(0x046D86, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear jet engine typeB lv.4
        ItemList.registerItem(0x046D87, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear jet engine typeC lv.4
        ItemList.registerItem(0x046D88, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear jet engine typeD lv.4
        ItemList.registerItem(0x046D89, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear jet engine typeE lv.4

        //MS HJ engines
        ItemList.registerItem(0x046CF6, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear hydro jet engine typeA lv.1
        ItemList.registerItem(0x046CF7, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear hydro jet engine typeB lv.1
        ItemList.registerItem(0x046CF8, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear hydro jet engine typeC lv.1
        ItemList.registerItem(0x046CF9, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear hydro jet engine typeD lv.1
        ItemList.registerItem(0x046CFA, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 70000, 35000, false)); //MS/MA thermonuclear hydro jet engine typeE lv.1
        ItemList.registerItem(0x046CFB, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear hydro jet engine typeA lv.2
        ItemList.registerItem(0x046CFC, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear hydro jet engine typeB lv.2
        ItemList.registerItem(0x046CFD, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear hydro jet engine typeC lv.2
        ItemList.registerItem(0x046CFE, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear hydro jet engine typeD lv.2
        ItemList.registerItem(0x046CFF, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA thermonuclear hydro jet engine typeE lv.2
        ItemList.registerItem(0x046D00, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear hydro jet engine typeA lv.3
        ItemList.registerItem(0x046D01, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear hydro jet engine typeB lv.3
        ItemList.registerItem(0x046D02, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear hydro jet engine typeC lv.3
        ItemList.registerItem(0x046D03, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear hydro jet engine typeD lv.3
        ItemList.registerItem(0x046D04, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1100000, 550000, false)); //MS/MA thermonuclear hydro jet engine typeE lv.3
        ItemList.registerItem(0x046D8A, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear hydro jet engine typeA lv.4
        ItemList.registerItem(0x046D8B, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear hydro jet engine typeB lv.4
        ItemList.registerItem(0x046D8C, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear hydro jet engine typeC lv.4
        ItemList.registerItem(0x046D8D, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear hydro jet engine typeD lv.4
        ItemList.registerItem(0x046D8E, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 2000000, false)); //MS/MA thermonuclear hydro jet engine typeE lv.4

        //MS engine (Tidligere HB engine før 2019 reboot)
        ItemList.registerItem(0x046D05, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //MS/MA hybrid engine typeA lv.1
        ItemList.registerItem(0x046D06, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //MS/MA hybrid engine typeB lv.1
        ItemList.registerItem(0x046D07, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //MS/MA hybrid engine typeC lv.1
        ItemList.registerItem(0x046D08, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //MS/MA hybrid engine typeD lv.1
        ItemList.registerItem(0x046D09, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //MS/MA hybrid engine typeE lv.1
        ItemList.registerItem(0x046D0A, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA hybrid engine typeA lv.2
        ItemList.registerItem(0x046D0B, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA hybrid engine typeB lv.2
        ItemList.registerItem(0x046D0C, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA hybrid engine typeC lv.2
        ItemList.registerItem(0x046D0D, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA hybrid engine typeD lv.2
        ItemList.registerItem(0x046D0E, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 200000, 100000, false)); //MS/MA hybrid engine typeE lv.2
        ItemList.registerItem(0x046D0F, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 500000, 250000, false)); //MS/MA hybrid engine typeA lv.3
        ItemList.registerItem(0x046D10, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 500000, 250000, false)); //MS/MA hybrid engine typeB lv.3
        ItemList.registerItem(0x046D11, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 500000, 250000, false)); //MS/MA hybrid engine typeC lv.3
        ItemList.registerItem(0x046D12, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 500000, 250000, false)); //MS/MA hybrid engine typeD lv.3
        ItemList.registerItem(0x046D13, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 500000, 250000, false)); //MS/MA hybrid engine typeE lv.3
        ItemList.registerItem(0x046D8F, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 3000000, false)); //MS/MA hybrid engine typeA lv.4
        ItemList.registerItem(0x046D90, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 3000000, false)); //MS/MA hybrid engine typeB lv.4
        ItemList.registerItem(0x046D91, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 3000000, false)); //MS/MA hybrid engine typeC lv.4
        ItemList.registerItem(0x046D92, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 3000000, false)); //MS/MA hybrid engine typeD lv.4
        ItemList.registerItem(0x046D93, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 3000000, false)); //MS/MA hybrid engine typeE lv.4

        ItemList.registerItem(0x046DB9, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 3000000, false)); //MS/MA hybrid engine lv.5

        //Tank engines
        ItemList.registerItem(0x046CD1, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1000, 500, false)); //tank engine typeA lv.1
        ItemList.registerItem(0x046CD4, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 10000, 5000, false)); //tank engine typeA lv.2
        ItemList.registerItem(0x046CD5, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 100000, 50000, false)); //tank engine typeA lv.3
        ItemList.registerItem(0x046D14, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1000, 500, false)); //tank engine typeB lv.1
        ItemList.registerItem(0x046D15, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1000, 500, false)); //tank engine typeC lv.1
        ItemList.registerItem(0x046D16, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1000, 500, false)); //tank engine typeD lv.1
        ItemList.registerItem(0x046D17, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 1000, 500, false)); //tank engine typeE lv.1
        ItemList.registerItem(0x046D18, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 10000, 5000, false)); //tank engine typeB lv.2
        ItemList.registerItem(0x046D19, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 10000, 5000, false)); //tank engine typeC lv.2
        ItemList.registerItem(0x046D1A, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 10000, 5000, false)); //tank engine typeD lv.2
        ItemList.registerItem(0x046D1B, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 10000, 5000, false)); //tank engine typeE lv.2
        ItemList.registerItem(0x046D1C, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 100000, 50000, false)); //tank engine typeB lv.3
        ItemList.registerItem(0x046D1D, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 100000, 50000, false)); //tank engine typeC lv.3
        ItemList.registerItem(0x046D1E, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 100000, 50000, false)); //tank engine typeD lv.3
        ItemList.registerItem(0x046D1F, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 100000, 50000, false)); //tank engine typeE lv.3
        ItemList.registerItem(0x046D94, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 200000, false)); //tank engine typeA lv.4
        ItemList.registerItem(0x046D95, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 200000, false)); //tank engine typeB lv.4
        ItemList.registerItem(0x046D96, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 200000, false)); //tank engine typeC lv.4
        ItemList.registerItem(0x046D97, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 200000, false)); //tank engine typeD lv.4
        ItemList.registerItem(0x046D98, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 200000, false)); //tank engine typeE lv.4

        //General vehicle engines
        ItemList.registerItem(0x046CD6, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 750, 370, false)); //general vehicle engine typeA
        ItemList.registerItem(0x046CD7, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 750, 370, false)); //general vehicle engine typeB
        ItemList.registerItem(0x046CD8, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 750, 370, false)); //general vehicle engine typeC
        ItemList.registerItem(0x046CD9, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 750, 370, false)); //general vehicle engine typeD
        ItemList.registerItem(0x046CDA, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 750, 370, false)); //general vehicle engine typeE
        ItemList.registerItem(0x046D7A, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 750, 370, false)); //general vehicle engine typeF
        ItemList.registerItem(0x046D7B, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 750, 370, false)); //general vehicle engine typeG
        ItemList.registerItem(0x046D7C, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 750, 370, false)); //general vehicle engine typeH
        ItemList.registerItem(0x046D7D, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 750, 370, false)); //general vehicle engine typeI
        ItemList.registerItem(0x046D7E, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 750, 370, false)); //general vehicle engine typeJ
        ItemList.registerItem(0x046D7F, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 750, 370, false)); //general vehicle engine typeK

        //Fighter jet engines
        ItemList.registerItem(0x046D20, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 16000, 8000, false)); //fighter jet engine typeA lv.1
        ItemList.registerItem(0x046D21, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 16000, 8000, false)); //fighter jet engine typeB lv.1
        ItemList.registerItem(0x046D22, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 16000, 8000, false)); //fighter jet engine typeC lv.1
        ItemList.registerItem(0x046D23, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 16000, 8000, false)); //fighter jet engine typeD lv.1
        ItemList.registerItem(0x046D24, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 16000, 8000, false)); //fighter jet engine typeE lv.1
        ItemList.registerItem(0x046D25, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //fighter jet engine typeA lv.2
        ItemList.registerItem(0x046D26, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //fighter jet engine typeB lv.2
        ItemList.registerItem(0x046D27, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //fighter jet engine typeC lv.2
        ItemList.registerItem(0x046D28, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //fighter jet engine typeD lv.2
        ItemList.registerItem(0x046D29, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //fighter jet engine typeE lv.2
        ItemList.registerItem(0x046D2A, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 160000, 80000, false)); //fighter jet engine typeA lv.3
        ItemList.registerItem(0x046D2B, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 160000, 80000, false)); //fighter jet engine typeB lv.3
        ItemList.registerItem(0x046D2C, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 160000, 80000, false)); //fighter jet engine typeC lv.3
        ItemList.registerItem(0x046D2D, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 160000, 80000, false)); //fighter jet engine typeD lv.3
        ItemList.registerItem(0x046D2E, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 160000, 80000, false)); //fighter jet engine typeE lv.3
        ItemList.registerItem(0x046D99, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 400000, false)); //fighter atmosphere engine typeA lv.4
        ItemList.registerItem(0x046D9A, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 400000, false)); //fighter atmosphere engine typeB lv.4
        ItemList.registerItem(0x046D9B, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 400000, false)); //fighter atmosphere engine typeC lv.4
        ItemList.registerItem(0x046D9C, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 400000, false)); //fighter atmosphere engine typeD lv.4
        ItemList.registerItem(0x046D9D, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 400000, false)); //fighter atmosphere engine typeE lv.4

        //Spaceboat engines
        ItemList.registerItem(0x046D2F, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 16000, 8000, false)); //spaceboat rocket engine typeA lv.1
        ItemList.registerItem(0x046D30, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 16000, 8000, false)); //spaceboat rocket engine typeB lv.1
        ItemList.registerItem(0x046D31, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 16000, 8000, false)); //spaceboat rocket engine typeC lv.1
        ItemList.registerItem(0x046D32, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 16000, 8000, false)); //spaceboat rocket engine typeD lv.1
        ItemList.registerItem(0x046D33, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 16000, 8000, false)); //spaceboat rocket engine typeE lv.1
        ItemList.registerItem(0x046D34, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //spaceboat rocket engine typeA lv.2
        ItemList.registerItem(0x046D35, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //spaceboat rocket engine typeB lv.2
        ItemList.registerItem(0x046D36, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //spaceboat rocket engine typeC lv.2
        ItemList.registerItem(0x046D37, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //spaceboat rocket engine typeD lv.2
        ItemList.registerItem(0x046D38, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 50000, 25000, false)); //spaceboat rocket engine typeE lv.2
        ItemList.registerItem(0x046D39, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 160000, 80000, false)); //spaceboat rocket engine typeA lv.3
        ItemList.registerItem(0x046D3A, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 160000, 80000, false)); //spaceboat rocket engine typeB lv.3
        ItemList.registerItem(0x046D3B, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 160000, 80000, false)); //spaceboat rocket engine typeC lv.3
        ItemList.registerItem(0x046D3C, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 160000, 80000, false)); //spaceboat rocket engine typeD lv.3
        ItemList.registerItem(0x046D3D, new GameItem(ItemType.General, ItemSubType.Engine, true, false, 160000, 80000, false)); //spaceboat rocket engine typeE lv.3		
        ItemList.registerItem(0x046D9E, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 600000, false)); //spaceboat rocket engine typeA lv.4
        ItemList.registerItem(0x046D9F, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 600000, false)); //spaceboat rocket engine typeB lv.4
        ItemList.registerItem(0x046DA0, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 600000, false)); //spaceboat rocket engine typeC lv.4
        ItemList.registerItem(0x046DA1, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 600000, false)); //spaceboat rocket engine typeD lv.4
        ItemList.registerItem(0x046DA2, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 600000, false)); //spaceboat rocket engine typeE lv.4

        //Battleship engines
        ItemList.registerItem(0x046D3E, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear rocket engine typeA lv.1
        ItemList.registerItem(0x046D3F, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear rocket engine typeB lv.1
        ItemList.registerItem(0x046D40, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear rocket engine typeC lv.1
        ItemList.registerItem(0x046D41, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear rocket engine typeD lv.1
        ItemList.registerItem(0x046D42, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear rocket engine typeE lv.1
        ItemList.registerItem(0x046D43, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear rocket engine typeA lv.2
        ItemList.registerItem(0x046D44, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear rocket engine typeB lv.2
        ItemList.registerItem(0x046D45, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear rocket engine typeC lv.2
        ItemList.registerItem(0x046D46, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear rocket engine typeD lv.2
        ItemList.registerItem(0x046D47, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear rocket engine typeE lv.2
        ItemList.registerItem(0x046D48, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear rocket engine typeA lv.3
        ItemList.registerItem(0x046D49, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear rocket engine typeB lv.3
        ItemList.registerItem(0x046D4A, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear rocket engine typeC lv.3
        ItemList.registerItem(0x046D4B, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear rocket engine typeD lv.3
        ItemList.registerItem(0x046D4C, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear rocket engine typeE lv.3
        ItemList.registerItem(0x046D4D, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear jet engine typeA lv.1
        ItemList.registerItem(0x046D4E, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear jet engine typeB lv.1
        ItemList.registerItem(0x046D4F, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear jet engine typeC lv.1
        ItemList.registerItem(0x046D50, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear jet engine typeD lv.1
        ItemList.registerItem(0x046D51, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear jet engine typeE lv.1
        ItemList.registerItem(0x046D52, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear jet engine typeA lv.2
        ItemList.registerItem(0x046D53, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear jet engine typeB lv.2
        ItemList.registerItem(0x046D54, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear jet engine typeC lv.2
        ItemList.registerItem(0x046D55, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear jet engine typeD lv.2
        ItemList.registerItem(0x046D56, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear jet engine typeE lv.2
        ItemList.registerItem(0x046D57, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear jet engine typeA lv.3
        ItemList.registerItem(0x046D58, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear jet engine typeB lv.3
        ItemList.registerItem(0x046D59, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear jet engine typeC lv.3
        ItemList.registerItem(0x046D5A, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear jet engine typeD lv.3
        ItemList.registerItem(0x046D5B, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear jet engine typeE lv.3
        ItemList.registerItem(0x046D5C, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear hydro jet engine typeA lv.1
        ItemList.registerItem(0x046D5D, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear hydro jet engine typeB lv.1
        ItemList.registerItem(0x046D5E, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear hydro jet engine typeC lv.1
        ItemList.registerItem(0x046D5F, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear hydro jet engine typeD lv.1
        ItemList.registerItem(0x046D60, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 400000, 200000, false)); //BB thermonuclear hydro jet engine typeE lv.1
        ItemList.registerItem(0x046D61, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear hydro jet engine typeA lv.2
        ItemList.registerItem(0x046D62, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear hydro jet engine typeB lv.2
        ItemList.registerItem(0x046D63, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear hydro jet engine typeC lv.2
        ItemList.registerItem(0x046D64, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear hydro jet engine typeD lv.2
        ItemList.registerItem(0x046D65, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB thermonuclear hydro jet engine typeE lv.2
        ItemList.registerItem(0x046D66, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear hydro jet engine typeA lv.3
        ItemList.registerItem(0x046D67, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear hydro jet engine typeB lv.3
        ItemList.registerItem(0x046D68, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear hydro jet engine typeC lv.3
        ItemList.registerItem(0x046D69, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear hydro jet engine typeD lv.3
        ItemList.registerItem(0x046D6A, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB thermonuclear hydro jet engine typeE lv.3
        ItemList.registerItem(0x046D6B, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB hybrid engine typeA lv.1
        ItemList.registerItem(0x046D6C, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB hybrid engine typeB lv.1
        ItemList.registerItem(0x046D6D, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB hybrid engine typeC lv.1
        ItemList.registerItem(0x046D6E, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB hybrid engine typeD lv.1
        ItemList.registerItem(0x046D6F, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 1200000, 600000, false)); //BB hybrid engine typeE lv.1
        ItemList.registerItem(0x046D70, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB hybrid engine typeA lv.2
        ItemList.registerItem(0x046D71, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB hybrid engine typeB lv.2
        ItemList.registerItem(0x046D72, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB hybrid engine typeC lv.2
        ItemList.registerItem(0x046D73, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB hybrid engine typeD lv.2
        ItemList.registerItem(0x046D74, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 4000000, 2000000, false)); //BB hybrid engine typeE lv.2
        ItemList.registerItem(0x046D75, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 15000000, 7500000, false)); //BB hybrid engine typeA lv.3
        ItemList.registerItem(0x046D76, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 15000000, 7500000, false)); //BB hybrid engine typeB lv.3
        ItemList.registerItem(0x046D77, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 15000000, 7500000, false)); //BB hybrid engine typeC lv.3
        ItemList.registerItem(0x046D78, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 15000000, 7500000, false)); //BB hybrid engine typeD lv.3
        ItemList.registerItem(0x046D79, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 15000000, 7500000, false)); //BB hybrid engine typeE lv.3		
        ItemList.registerItem(0x046DA3, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear rocket engine typeA lv.4
        ItemList.registerItem(0x046DA4, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear rocket engine typeB lv.4
        ItemList.registerItem(0x046DA5, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear rocket engine typeC lv.4
        ItemList.registerItem(0x046DA6, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear rocket engine typeD lv.4
        ItemList.registerItem(0x046DA7, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear rocket engine typeE lv.4
        ItemList.registerItem(0x046DA8, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear jet engine typeA lv.4
        ItemList.registerItem(0x046DA9, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear jet engine typeB lv.4
        ItemList.registerItem(0x046DAA, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear jet engine typeC lv.4
        ItemList.registerItem(0x046DAB, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear jet engine typeD lv.4
        ItemList.registerItem(0x046DAC, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear jet engine typeE lv.4
        ItemList.registerItem(0x046DAD, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear hydro jet engine typeA lv.4
        ItemList.registerItem(0x046DAE, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear hydro jet engine typeB lv.4
        ItemList.registerItem(0x046DAF, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear hydro jet engine typeC lv.4
        ItemList.registerItem(0x046DB0, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear hydro jet engine typeD lv.4
        ItemList.registerItem(0x046DB1, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 8000000, false)); //BB thermonuclear hydro jet engine typeE lv.4
        ItemList.registerItem(0x046DB2, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 30000000, false)); //BB hybrid engine typeA lv.4
        ItemList.registerItem(0x046DB3, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 30000000, false)); //BB hybrid engine typeB lv.4
        ItemList.registerItem(0x046DB4, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 30000000, false)); //BB hybrid engine typeC lv.4
        ItemList.registerItem(0x046DB5, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 30000000, false)); //BB hybrid engine typeD lv.4
        ItemList.registerItem(0x046DB6, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 30000000, false)); //BB hybrid engine typeE lv.4

        ItemList.registerItem(0x046DB7, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 30000000, false)); //Thermonuclear jet engine
        ItemList.registerItem(0x046DB8, new GameItem(ItemType.General, ItemSubType.Engine, false, false, 100000000, 30000000, false)); //Thermonuclear rocket engine

    }

}
