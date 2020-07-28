package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen registrerer alle gyldige MS, MA, Fighters og kjøretøy som kan
 * brukes i spillet.
 */
public class RegisterVehicles {

    public static void execute() {

        //TEST MS/VEHICLE.
        ItemList.registerItem(1030000, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 600000, 201000)); //Zeta
        ItemList.registerItem(400004, new GameItem(ItemType.Vehicle, ItemSubType.Truck, false, false, 10000, 6500)); //Zarathustra

        //Alle EF MS som kan kjøpes og craftes. Etter reboot 2019.
        ItemList.registerItem(0x641CE, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 25000, 10000));//Rrf-06 Zanny        
        ItemList.registerItem(0x6419B, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 100000, 50000)); //RX-75R Mass produced Guntank
        ItemList.registerItem(0x641BA, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 0, 0)); //TGM-79 GM Trainer              
        ItemList.registerItem(0x64190, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 50000, 25000)); //RGM-79 GM
        ItemList.registerItem(0x641BB, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 75000, 25000)); //RGM-79L Lightarmor                
        ItemList.registerItem(0x64193, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 50000, 25000)); //RGC-80 GM Cannon
        ItemList.registerItem(0x641C8, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 125000, 50000)); //RMV-1, Guntank 2                
        ItemList.registerItem(0x641C4, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 100000, 50000)); //RGM-79D Cold type
        ItemList.registerItem(0x641A7, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 200000, 100000)); //RGM-79(G) Ground GM
        ItemList.registerItem(0x641B4, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 150000, 100000)); //RGM-79(G)s GM Sniper                
        ItemList.registerItem(0x64194, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 150000, 100000)); //RX-77D Guncannon mass produced	
        ItemList.registerItem(0x641A8, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 250000, 100000)); //RGM-79C
        ItemList.registerItem(0x641D7, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 250000, 100000)); //RGM-79CS
        ItemList.registerItem(0x64192, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 250000, 100000)); //RGM-79G
        ItemList.registerItem(0x641C5, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 250000, 100000)); //RGM-79GS
        ItemList.registerItem(0x641B6, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 400000, 100000)); //RGM-79SC               
        ItemList.registerItem(0x6419C, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 500000, 100000)); //RX-75 Guntank
        ItemList.registerItem(0x64196, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //RX-77-2
        ItemList.registerItem(0x641B9, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //RX-77-3
        ItemList.registerItem(0x641CC, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //RX-77-4
        ItemList.registerItem(0xF6968, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //GM HEAD        
        ItemList.registerItem(0x641AD, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //RX-79G
        ItemList.registerItem(0x641B5, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //RGM-79SP Sniper2        
        ItemList.registerItem(0x641AE, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 1000000, 500000));//Ez8
        ItemList.registerItem(0x641AF, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 1000000, 500000));//RX-78-1
        ItemList.registerItem(0x64195, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 1000000, 500000));//RX-78-2
        ItemList.registerItem(0x6423C, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 1000000, 500000));//RX-78-2MC versjon av 0x64195
        ItemList.registerItem(0x641D9, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 1000000, 500000));//RX-78-3
        ItemList.registerItem(0x641DA, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 1000000, 500000));//FA-78-1    
        ItemList.registerItem(0x64245, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-78NT-1     

        //Alle ZEON MS som kan kjøpes og craftes. Etter reboot 2019.
        ItemList.registerItem(0x64191, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 0, 0)); //MS-05B Zaku 1
        ItemList.registerItem(0xF6964, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 50000, 25000)); //MS-05S Ramba Ral        
        ItemList.registerItem(0x641A4, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 25000, 10000)); //MS-12 Gigan        
        ItemList.registerItem(0x64197, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 50000, 25000)); //MS-06F Zaku 2                
        ItemList.registerItem(0x6419E, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 50000, 25000)); //Zaku Cannon
        ItemList.registerItem(0x641AB, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 50000, 25000)); //Zaku Cannon Rabbit Ear               
        ItemList.registerItem(0x6419F, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 75000, 25000)); //Zaku Desert Type
        ItemList.registerItem(0x641AC, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 100000, 50000)); //Zaku Deserttype Karakal Custom                
        ItemList.registerItem(0x641BE, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 100000, 50000)); //Zaku2 F2 type A
        ItemList.registerItem(0x641B0, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 100000, 50000)); //Zaku2 F2 type B
        ItemList.registerItem(0x641B1, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 100000, 50000)); //Zaku2 F2 Neuen Bitter
        ItemList.registerItem(0x641B2, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 100000, 50000)); //MSM-04 Acguy
        ItemList.registerItem(0x641C0, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 150000, 100000)); //MSM-04N Agguguy
        ItemList.registerItem(0x641A3, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 150000, 100000)); //MSM-04N Agguguy heatrod
        ItemList.registerItem(0x641A2, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 150000, 100000)); //MSM-04G Juagg                
        ItemList.registerItem(0x641D2, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 100000, 50000));//MS-06RP
        ItemList.registerItem(0x641A0, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 150000, 100000)); //YMS-09 Prototype DOM
        ItemList.registerItem(0x641B3, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 150000, 100000)); //YMS-09D Dom TTT                
        ItemList.registerItem(0x641D3, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 125000, 50000)); //MS-06R1
        ItemList.registerItem(0xF696C, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 200000, 100000)); //MS-06R1A        
        ItemList.registerItem(0x6419A, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 150000, 100000)); //MS-06S        
        ItemList.registerItem(0xF6950, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 150000, 100000)); //Zaku2 S Char Aznable
        ItemList.registerItem(0x641D4, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 250000, 100000));//MS-06R-2P
        ItemList.registerItem(0x641D8, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 250000, 100000)); //MS-06R-2
        ItemList.registerItem(0x64198, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 250000, 100000)); //Zaku2 FZ type A
        ItemList.registerItem(0x64199, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 250000, 100000)); //Zaku2 FZ type B                
        ItemList.registerItem(0x6419D, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 250000, 100000)); //MS-07B
        ItemList.registerItem(0x641AA, new GameItem(ItemType.Vehicle, ItemSubType.MS, true, true, 400000, 100000)); //MSM-03 Gogg
        ItemList.registerItem(0x641A9, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 400000, 100000)); //MSM-07 Z'Gok
        ItemList.registerItem(0x641A1, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //MS-09 DOM
        ItemList.registerItem(0x641D5, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //MS-09R Rick Dom
        ItemList.registerItem(0x641C3, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //MSM-07E Z'Gok experiment
        ItemList.registerItem(0x64203, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //MS-09RS DOM
        ItemList.registerItem(0x64210, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //MS-09RS DOM Gato
        ItemList.registerItem(0xF695B, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //MSM-07S Zgok S        
        ItemList.registerItem(0x641A5, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //MS-13 Gasshia
        ItemList.registerItem(0x641A6, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 500000, 200000)); //MSM-08 Zogok        
        ItemList.registerItem(0x641C9, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 1000000, 500000)); //MS-14A Gelgoog
        ItemList.registerItem(0x641CA, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 1000000, 500000));//MS-14S Gelgoog
        ItemList.registerItem(0x641CB, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 1000000, 500000)); //MS14B Gelgoog                       
        ItemList.registerItem(0xF695E, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, true, 1000000, 500000));//YMS-14
        ItemList.registerItem(0x64246, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 1000000, 500000)); //YMS-15 Gyan
        ItemList.registerItem(0x64243, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 1000000, 500000)); //MS-18E Kampfer

        //Registrer alle EF vehicles som kan kjøpes.
        ItemList.registerItem(0x68FC0, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, true, 30000, 25100)); //Attack helicopter
        ItemList.registerItem(0x61A80, new GameItem(ItemType.Vehicle, ItemSubType.Truck, true, true, 10000, 6500)); //Elecar Aaron
        ItemList.registerItem(0x61A81, new GameItem(ItemType.Vehicle, ItemSubType.Truck, true, true, 10000, 6500)); //Elecar Baal
        ItemList.registerItem(0x61A82, new GameItem(ItemType.Vehicle, ItemSubType.Truck, true, true, 10000, 6500)); //Elecar Cabot
        ItemList.registerItem(0x61A83, new GameItem(ItemType.Vehicle, ItemSubType.Truck, true, true, 10000, 6500)); //Elecar Dada
        ItemList.registerItem(0x68FB9, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, false, 31800, 27000)); //Fanfan
        ItemList.registerItem(0x68FBC, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, false, 35400, 30600)); //FF-4 Toriares
        ItemList.registerItem(0x68FB5, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, false, 39000, 34200)); //FF-6 Tin cod
        ItemList.registerItem(0x68FBB, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, false, 37200, 32400)); //FF-S5 Saberfish
        ItemList.registerItem(0x68FB7, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, false, 32600, 28800)); //Fly-manta
        ItemList.registerItem(0x704E4, new GameItem(ItemType.Vehicle, ItemSubType.Tank, true, true, 28000, 19200)); //Hovertruck
        ItemList.registerItem(0x61A8B, new GameItem(ItemType.Vehicle, ItemSubType.Truck, true, true, 40000, 19000)); //Truck Earp
        ItemList.registerItem(0x61A8A, new GameItem(ItemType.Vehicle, ItemSubType.Truck, true, true, 40000, 19000)); //Truck Fahrenheit
        ItemList.registerItem(0x61A8C, new GameItem(ItemType.Vehicle, ItemSubType.Truck, true, true, 40000, 19000)); //Truck Gaea
        ItemList.registerItem(0x704E0, new GameItem(ItemType.Vehicle, ItemSubType.Tank, true, true, 0, 1)); //Type-61 MBT
        ItemList.registerItem(0x61A91, new GameItem(ItemType.Vehicle, ItemSubType.Transport, true, true, 10000, 8250)); //Thundergoliat
        ItemList.registerItem(0x68FB0, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, true, 50000, 28800)); //Public
        ItemList.registerItem(0x61A96, new GameItem(ItemType.Vehicle, ItemSubType.Transport, true, true, 40000, 34050)); //Habakuk
        ItemList.registerItem(0x61A97, new GameItem(ItemType.Vehicle, ItemSubType.Transport, true, true, 40000, 34050)); //Icarus

        //Registrer alle EF MA som kan kjøpes.
        ItemList.registerItem(0x668A0, new GameItem(ItemType.Vehicle, ItemSubType.MA, true, true, 0, 0)); //RB-79
        ItemList.registerItem(0x668A5, new GameItem(ItemType.Vehicle, ItemSubType.MA, true, true, 0, 0)); //RB-79C
        ItemList.registerItem(0x668A4, new GameItem(ItemType.Vehicle, ItemSubType.MA, true, true, 0, 0)); //RB-79K

        //Registrer alle Zeon vehicles som kan kjøpes. Biler, Habakuk, Icarus og Thundergoliat ble lagt til under EF.
        ItemList.registerItem(0x68FB6, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, false, 30000, 25100)); //Dopp
        ItemList.registerItem(0x68FBA, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, false, 35400, 30600)); //Luggun
        ItemList.registerItem(0x704E1, new GameItem(ItemType.Vehicle, ItemSubType.Tank, true, true, 0, 1)); //Magella Attack
        ItemList.registerItem(0x704E5, new GameItem(ItemType.Vehicle, ItemSubType.Tank, true, true, 180000, 74000)); //Zaku tank
        ItemList.registerItem(0x68FB8, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, false, 30000, 25100)); //Zeon Attack helicopter
        ItemList.registerItem(0x68FB2, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, false, 50000, 28800)); //Gattle
        ItemList.registerItem(0x68FB1, new GameItem(ItemType.Vehicle, ItemSubType.Fighter, true, false, 50000, 28800)); //Jicco

        //Registrer alle Zeon MA som kan kjøpes.
        ItemList.registerItem(0x668A6, new GameItem(ItemType.Vehicle, ItemSubType.MA, true, true, 0, 0)); //Oggo

        //Etter 2019 reboot. Gamle EF MS.                      
        ItemList.registerItem(0x641F4, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-78-3 Rød
        ItemList.registerItem(0x641ED, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-78-3 Farget
        ItemList.registerItem(0x64219, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-78-3 Grå/svart        
        ItemList.registerItem(0x641F9, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2 Char Aznable        
        ItemList.registerItem(0xF6967, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 30100000, 15000000));//Ez8 Farget
        ItemList.registerItem(0x641AF, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 3500000));//RX-78-1 Prototype Gundam
        ItemList.registerItem(0xF6958, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 3500000));//RX-78-1 Gold        
        ItemList.registerItem(0x641DB, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 55000000, 5300000));//FA-78-1 Blå
        ItemList.registerItem(0x641FA, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 55000000, 5300000));//FA-78-1 Hvit
        ItemList.registerItem(0x641FB, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 55000000, 5300000));//FA-78-1 Brun
        ItemList.registerItem(0x641DC, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 600000, 495000)); //Sniper 2 Farget
        ItemList.registerItem(0x641F5, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 600000, 495000)); //Sniper 2 rød/svart
        ItemList.registerItem(0x641DD, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 288000)); //Sniper custom Farget
        ItemList.registerItem(0x641DE, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 384000)); //GM Modified Farget
        ItemList.registerItem(0x641F0, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 384000)); //GM Modified Gul/Rød		
        ItemList.registerItem(0xF6957, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 210000, 145800)); //RGM-79 GM Black
        ItemList.registerItem(0xF695F, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 210000, 145800)); //RGM-79 GM Farget
        ItemList.registerItem(0xF6954, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 5000000, 1330000)); //Guncannon Farget
        ItemList.registerItem(0xF695C, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 5000000, 1330000)); //Guncannon Farget		
        ItemList.registerItem(0xF695D, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 1710000)); //Ground Gundam Farget
        ItemList.registerItem(0xF6966, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 1710000)); //Ground Gundam Farget
        ItemList.registerItem(0x641F2, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 1710000)); //Ground Gundam lilla/rosa
        ItemList.registerItem(0xF6960, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 258000)); //GM Command, ground type Farget
        ItemList.registerItem(0x641E6, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 400000, 384000)); //Guncannon mass produced farget
        ItemList.registerItem(0x641EA, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 400000)); //Ground GM Farget
        ItemList.registerItem(0x641EB, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 1710000)); //Ground Gundam Blå
        ItemList.registerItem(0x641EC, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 30100000, 15000000));//Ez8 Gul
        ItemList.registerItem(0x641F3, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 30100000, 15000000));//Ez8 hvit/rød
        ItemList.registerItem(0x641EF, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 270000, 267000)); //GM Lightarmor Blå
        ItemList.registerItem(0x641F1, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 400000)); //Ground GM rød/grå
        ItemList.registerItem(0x641FC, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 600000, 500000)); //Guncannon 2 Hvit/Rød
        ItemList.registerItem(0x64206, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 600000, 201000)); //GM Cannon Svart
        ItemList.registerItem(0x64207, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 270000, 267000)); //GM Lightarmor Rosa
        ItemList.registerItem(0x64208, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 750000, 249000)); //GM Cold type rød
        ItemList.registerItem(0x64209, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 288000)); //Sniper custom Lilla
        ItemList.registerItem(0x6420A, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 600000, 495000)); //Sniper 2 Sand/brun
        ItemList.registerItem(0x6420D, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-78-3 Hvit/Blå
        ItemList.registerItem(0x6420E, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2 Blå/Titans
        ItemList.registerItem(0x6420F, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2 Zeon Gundam
        ItemList.registerItem(0x64213, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 270000, 267000)); //GM Lightarmor Grønn
        ItemList.registerItem(0x64215, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 400000)); //Ground GM Grønn/Gul
        ItemList.registerItem(0x64216, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 1710000)); //Ground Gundam hvit
        ItemList.registerItem(0x64217, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 30100000, 15000000));//Ez8 brun/sand
        ItemList.registerItem(0x6421A, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 384000)); //GM Modified Grå/svart
        ItemList.registerItem(0x6421B, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 270000, 267000)); //GM Lightarmor brun
        ItemList.registerItem(0x6421D, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 170000, 140000));//RRF-06 ZANNY farget
        ItemList.registerItem(0x6421E, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 400000)); //RX-75 Guntank farget
        ItemList.registerItem(0x64223, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 400000)); //Ground GM blå
        ItemList.registerItem(0x64224, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 1710000)); //Ground Gundam grå/rød
        ItemList.registerItem(0x64225, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 30000000, 1710000)); //GM HEAD Blue destiny
        ItemList.registerItem(0x6422B, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 1710000)); //Ground Gundam
        ItemList.registerItem(0x6422C, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 1710000)); //Ground Gundam
        ItemList.registerItem(0x6422D, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 1710000)); //Ground Gundam
        ItemList.registerItem(0x6422E, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 1710000)); //Ground Gundam
        ItemList.registerItem(0x6422F, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 400000)); //Ground GM
        ItemList.registerItem(0x64230, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 8200000, 495000)); //Sniper 2
        ItemList.registerItem(0x64231, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-78-3
        ItemList.registerItem(0x64233, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2
        ItemList.registerItem(0x64234, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2
        ItemList.registerItem(0x64235, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2
        ItemList.registerItem(0x6423B, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 600000, 500000)); //Guncannon 2        
        ItemList.registerItem(0x6423D, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2MC versjon av 0x6420E
        ItemList.registerItem(0x6423E, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2MC versjon av 0x6420F
        ItemList.registerItem(0x6423F, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2MC versjon av 0x641F9
        ItemList.registerItem(0x64240, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2MC versjon av 0x64233
        ItemList.registerItem(0x64241, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2MC versjon av 0x64234
        ItemList.registerItem(0x64242, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 5030000));//RX-78-2MC versjon av 0x64235                   
        ItemList.registerItem(0x64249, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 2000000, 500000)); //RMS-108 Marasai EF versjon
        ItemList.registerItem(0x6424B, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-78GP01
        ItemList.registerItem(0x6424C, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//PMX-001 Palace Athene EF versjon
        ItemList.registerItem(0x6424E, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 2130000));//RMS-099 Rick Dias EF versjon
        ItemList.registerItem(0x64250, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-78GP02
        ItemList.registerItem(0x64251, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-178 AUEG
        ItemList.registerItem(0x64252, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-178 TITANS
        ItemList.registerItem(0x64253, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-160 Byarlant EF versjon
        ItemList.registerItem(0x64255, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//PMX-002 Bolinoak EF versjon                
        ItemList.registerItem(0x64258, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));// Super Gundam AEUG
        ItemList.registerItem(0x6425B, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));// Super Gundam TITANS
        ItemList.registerItem(0x6425C, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));// Hyaku Shiki
        ItemList.registerItem(0x668A7, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 1000000)); //Core Booster				                
        ItemList.registerItem(0x64260, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));// Zeta Gundam
        ItemList.registerItem(0x64247, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 0, 0)); //RMS-179 GM                                
        ItemList.registerItem(0x64248, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 1000000, 500000)); //MSA-003 Nemo
        ItemList.registerItem(0x64257, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 2000000, 500000));// RGM-89 Jegan

        //Etter 2019 reboot. Gamle ZEON MS.                              
        ItemList.registerItem(0x64259, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 1000000, 500000)); //RMS-106 HiZack
        ItemList.registerItem(0x6425A, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 1000000, 500000)); //Gaza C
        ItemList.registerItem(0x6425E, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 2000000, 500000)); //Geara Doga                                      
        ItemList.registerItem(0x668A1, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 1000000)); //Adzam
        ItemList.registerItem(0x668A2, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 1000000)); //Apsalus        
        ItemList.registerItem(0xF695A, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 400000, 197500)); //Zaku2 S Silver
        ItemList.registerItem(0xF6952, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 130000, 110400)); //Zaku 1 Black Tristar        
        ItemList.registerItem(0xF6959, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 320000, 175500)); //Zaku Deserttype A Green        
        ItemList.registerItem(0xF6969, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 2120000)); //MS-14 Anavel Gato
        ItemList.registerItem(0xF696B, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 100000000, 2500000)); //MS14B Johnny Ridden        
        ItemList.registerItem(0xF696D, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 1300000, 222000)); //Zaku2 MS-06R-2 Johnny Ridden
        ItemList.registerItem(0xF6975, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 1200000, 217000)); //Zaku2 MS-06R-1 Shin Matsunaga
        ItemList.registerItem(0xF6953, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 271500)); //MS-09 DOM Farget
        ItemList.registerItem(0xF6963, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 303000)); //Gouf Farget
        ItemList.registerItem(0xF6956, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 400000, 391900)); //Zaku2 FZ type A Farget
        ItemList.registerItem(0xF6962, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 400000, 391900)); //Zaku2 FZ type A Farget
        ItemList.registerItem(0xF6961, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 200000, 142900)); //Zaku 2 Farget
        ItemList.registerItem(0xF696A, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 200000, 142900)); //Zaku 2 Farget
        ItemList.registerItem(0x641DF, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 303000)); //Gouf Farget Hvit
        ItemList.registerItem(0x641EE, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 303000)); //Gouf Farget Gul
        ItemList.registerItem(0x641E0, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 1200000, 254500)); //YMS-09 Prototype DOM Farget
        ItemList.registerItem(0x641E1, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 300000, 245500)); //YMS-09D Dom TTT Farget
        ItemList.registerItem(0x641E2, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 550000, 514000)); //MSM-03 Gogg Farget		
        ItemList.registerItem(0x641E3, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 2120000)); //MS-14 Gelgoog Farget
        ItemList.registerItem(0x64228, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 2120000)); //MS-14 Gelgoog Farget
        ItemList.registerItem(0x641F6, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 2120000)); //MS-14 Gelgoog mørk grå
        ItemList.registerItem(0x641E4, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 2130000));//MS-14S Gelgoog Farget
        ItemList.registerItem(0x641F7, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 2130000));//MS-14S Gelgoog grå/hvit
        ItemList.registerItem(0x641E5, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 100000000, 2500000)); //MS14B Gelgoog Farget
        ItemList.registerItem(0x641E7, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 2120000)); //MS-14 Gelgoog Farget 2
        ItemList.registerItem(0x641E8, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 2130000));//MS-14S Gelgoog Farget 2
        ItemList.registerItem(0x641E9, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 100000000, 2500000)); //MS14B Gelgoog Farget 2
        ItemList.registerItem(0x641F8, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 100000000, 2500000)); //MS14B Gelgoog grå/rød
        ItemList.registerItem(0x641FD, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 2130000));//MS-14S Gelgoog Lilla/Brun
        ItemList.registerItem(0x641FE, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 427000)); //MS-13 Gasshia Svart/Hvit
        ItemList.registerItem(0x641FF, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 300000, 288000)); //MSM-04 Acguy Rød
        ItemList.registerItem(0x64200, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 450000)); //MSM-07 Z'Gok Svart
        ItemList.registerItem(0x64201, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 600000, 495000)); //MSM-07E Z'GokE orange
        ItemList.registerItem(0x64202, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 483000)); //MSM-08 Zogok Svart/Hvit        
        ItemList.registerItem(0x64204, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 100000000, 2500000)); //MS14B Gelgoog Gul
        ItemList.registerItem(0x64205, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 100000000, 2500000)); //MS14B Gelgoog Lilla
        ItemList.registerItem(0x6420B, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 303000)); //Gouf Lysblå
        ItemList.registerItem(0x6420C, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 600000, 495000)); //MSM-07E Z'Gok Blå        
        ItemList.registerItem(0x64211, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 2130000));//MS-14S Erik Blanke
        ItemList.registerItem(0x64212, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 100000000, 2500000)); //MS14B 78-2 farge
        ItemList.registerItem(0x64214, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 450000)); //MSM-07 Z'Gok Lilla
        ItemList.registerItem(0x64218, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 2120000)); //MS-14 rød/sand
        ItemList.registerItem(0x6421C, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 400000, 197500)); //Zaku2 S svart
        ItemList.registerItem(0x6421F, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 35000000, 2120000)); //MS-14 Gelgoog grønn
        ItemList.registerItem(0x64220, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 2130000));//MS-14S Gelgoog hvit
        ItemList.registerItem(0x64221, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 100000000, 2500000)); //MS14B Gelgoog Gato
        ItemList.registerItem(0x64222, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 450000)); //MSM-07 Z'Gok gul
        ItemList.registerItem(0x64226, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 271500)); //MS-09 DOM svart/lilla
        ItemList.registerItem(0x64227, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 600000, 495000)); //MSM-07E Z'Gok Brun
        ItemList.registerItem(0x64229, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 2130000));//MS-14S Gelgoog
        ItemList.registerItem(0x6422A, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 100000000, 2500000)); //MS14B Gelgoog
        ItemList.registerItem(0x64232, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 271500)); //MS-09 DOM
        ItemList.registerItem(0x64236, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 303000)); //Gouf Farget
        ItemList.registerItem(0x64237, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 303000)); //Gouf Farget
        ItemList.registerItem(0x64238, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 500000, 303000)); //Gouf Farget
        ItemList.registerItem(0x64239, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 550000, 514000)); //MSM-03 Gogg rosa
        ItemList.registerItem(0x6423A, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 600000, 495000)); //MSM-07E Z'Gok		        
        ItemList.registerItem(0x64244, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 100000000, 2500000)); //MS-08TX Efreet        
        ItemList.registerItem(0x6424A, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 2000000, 500000)); //RMS-108 Marasai Zeon versjon
        ItemList.registerItem(0x6424D, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//PMX-001 Palace Athene Zeon versjon
        ItemList.registerItem(0x6424F, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 50000000, 2130000));//RMS-099 Rick Dias Zeon versjon
        ItemList.registerItem(0x64254, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//RX-160 Byarlant Zeon versjon
        ItemList.registerItem(0x64256, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000));//PMX-002 Bolinoak Zeon versjon                                
        ItemList.registerItem(0x6425D, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 10000000, 201000)); //Hygogg          
        ItemList.registerItem(0x6425F, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 2000000, 500000)); //Geara Doga Rezin Schnyder
        ItemList.registerItem(0x64261, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000)); //Bawoo, Glemy
        ItemList.registerItem(0x64262, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000)); //Bawoo, Glemy Faction
        ItemList.registerItem(0x64263, new GameItem(ItemType.Vehicle, ItemSubType.MS, false, false, 60000000, 5500000)); //Bawoo, Axis MP

        //Andre craft-only vehicles.
        ItemList.registerItem(0x704E3, new GameItem(ItemType.Vehicle, ItemSubType.Tank, false, true, 0, 10950));//Magella Attack Custom
        ItemList.registerItem(0x704E2, new GameItem(ItemType.Vehicle, ItemSubType.Tank, false, true, 0, 10950));//Type-61 MBT Custom

    }

}
