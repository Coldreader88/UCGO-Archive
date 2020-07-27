package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen registrerer alle klær som kan brukes i spillet.
 */
public class RegisterClothes {

    public static void execute() {

        //Registrer glasses.
        ItemList.registerItem(0x3AA14, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5500)); //Black frame
        ItemList.registerItem(0x3AB17, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6550)); //Black frame GQ
        ItemList.registerItem(0x3AA21, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5500)); //Goggles
        ItemList.registerItem(0x3AB23, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6550)); //Goggles GQ
        ItemList.registerItem(0x3AA13, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5500)); //Half rim
        ItemList.registerItem(0x3AB16, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6550)); //Half rim GQ
        ItemList.registerItem(0x3AA02, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 4900)); //Sports
        ItemList.registerItem(0x3AB14, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 14000)); //Sports GQ
        ItemList.registerItem(0x3A9DC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5700)); //Sunglasses
        ItemList.registerItem(0x3AAF2, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7700)); //Sunglasses GQ
        ItemList.registerItem(0x3AA41, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 10000)); //Protective sunglasses
        ItemList.registerItem(0x3AA12, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5500)); //Wire rim
        ItemList.registerItem(0x3AB15, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6550)); //Wire rim GQ
        ItemList.registerItem(0x3AA77, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7100)); //Eye patch
        ItemList.registerItem(0x3AB56, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 9200)); //Eye patch GQ
        ItemList.registerItem(0x3AA78, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7100)); //Eye patch skull
        ItemList.registerItem(0x3AB57, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 9200)); //Eye patch skull GQ
        ItemList.registerItem(0x3AA19, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5500)); //Round glasses
        ItemList.registerItem(0x3AB1C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6550)); //Round glasses GQ
        ItemList.registerItem(0x3AA15, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5500)); //Safety glasses A
        ItemList.registerItem(0x3AA16, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5500)); //Safety glasses B

        //Registrer uniformer.
        //EF Overalls
        ItemList.registerItem(0x3A9ED, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 17500, 8200)); //ADM
        ItemList.registerItem(0x3AB03, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 10590)); //ADM GQ
        ItemList.registerItem(0x3A9E9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 15500, 6720)); //CAPT
        ItemList.registerItem(0x3AAFF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 8030)); //CAPT GQ
        ItemList.registerItem(0x3A9E8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 15000, 6720)); //CDR
        ItemList.registerItem(0x3AAFE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7830)); //CDR GQ
        ItemList.registerItem(0x3A9E2, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 12000, 3600)); //CPO
        ItemList.registerItem(0x3AAF8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4900)); //CPO GQ
        ItemList.registerItem(0x3A9E4, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 13000, 5200)); //ENS
        ItemList.registerItem(0x3AAFA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6120)); //ENS GQ
        ItemList.registerItem(0x3A9E7, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 14500, 6720)); //LCDR
        ItemList.registerItem(0x3AAFD, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7620)); //LCDR GQ
        ItemList.registerItem(0x3A9E6, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 14000, 5200)); //LT
        ItemList.registerItem(0x3AAFC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6480)); //LT GQ
        ItemList.registerItem(0x3A9E5, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 13500, 5200)); //LTJG
        ItemList.registerItem(0x3AAFB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6300)); //LTJG GQ
        ItemList.registerItem(0x3A9E1, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 11500, 3600)); //PO
        ItemList.registerItem(0x3AAF7, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4780)); //PO GQ
        ItemList.registerItem(0x3A9EB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 16500, 8200)); //RADM
        ItemList.registerItem(0x3AB01, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 9600)); //RADM GQ
        ItemList.registerItem(0x3A9EA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 16000, 8200)); //RDML
        ItemList.registerItem(0x3AB00, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 9280)); //RDML GQ
        ItemList.registerItem(0x3A9DF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 10500, 3600)); //SA
        ItemList.registerItem(0x3AAF5, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4550)); //SA GQ
        ItemList.registerItem(0x3A9E3, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 12500, 3600)); //SCPO
        ItemList.registerItem(0x3AAF9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5080)); //SCPO GQ
        ItemList.registerItem(0x3A9E0, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 11000, 3600)); //SN
        ItemList.registerItem(0x3AAF6, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4660)); //SN GQ
        ItemList.registerItem(0x3A9EC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 17000, 8200)); //VADM
        ItemList.registerItem(0x3AB02, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 9940)); //VADM GQ
        ItemList.registerItem(0x3A9C6, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 10000, 3700)); //SR
        ItemList.registerItem(0x3AAEE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4300)); //SR GQ
        //EF Spacesuits
        ItemList.registerItem(0x3A9B5, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 800000, 164100)); //ADM
        ItemList.registerItem(0x3AADD, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 444000)); //ADM GQ
        ItemList.registerItem(0x3A9B1, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 155000, 104100)); //CAPT
        ItemList.registerItem(0x3AAD9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 151000)); //CAPT GQ
        ItemList.registerItem(0x3A9B0, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 150000, 97620)); //CDR
        ItemList.registerItem(0x3AAD8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 142800)); //CDR GQ
        ItemList.registerItem(0x3A9AA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 120000, 61620)); //CPO
        ItemList.registerItem(0x3AAD2, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 76500)); //CPO GQ
        ItemList.registerItem(0x3A9AC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 130000, 73620)); //ENS
        ItemList.registerItem(0x3AAD4, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 94100)); //ENS GQ
        ItemList.registerItem(0x3A9AF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 145000, 91620)); //LCDR
        ItemList.registerItem(0x3AAD7, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 128000)); //LCDR GQ
        ItemList.registerItem(0x3A9AE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 140000, 85620)); //LT
        ItemList.registerItem(0x3AAD6, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 115400)); //LT GQ
        ItemList.registerItem(0x3A9AD, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 135000, 79620)); //LTJG
        ItemList.registerItem(0x3AAD5, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 104200)); //LTJG GQ
        ItemList.registerItem(0x3A9A9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 115000, 55620)); //PO
        ItemList.registerItem(0x3AAD1, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 68000)); //PO GQ
        ItemList.registerItem(0x3A9B3, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 600000, 134100)); //RADM
        ItemList.registerItem(0x3AADB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 230800)); //RADM GQ
        ItemList.registerItem(0x3A9B2, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 500000, 119100)); //RDML
        ItemList.registerItem(0x3AADA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 185300)); //RDML GQ
        ItemList.registerItem(0x3A9A7, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 105000, 43620)); //SA
        ItemList.registerItem(0x3AACF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 51900)); //SA GQ
        ItemList.registerItem(0x3A9AB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 125000, 67620)); //SCPO
        ItemList.registerItem(0x3AAD3, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 85500)); //SCPO GQ
        ItemList.registerItem(0x3A9A8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 110000, 49620)); //SN
        ItemList.registerItem(0x3AAD0, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 59800)); //SN GQ
        ItemList.registerItem(0x3A9A6, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 100000, 37620)); //SR
        ItemList.registerItem(0x3AACE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 44500)); //SR GQ
        ItemList.registerItem(0x3A9B4, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 700000, 149100)); //VADM
        ItemList.registerItem(0x3AADC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 301000)); //VADM GQ
        //EF uniformer.
        ItemList.registerItem(0x3A995, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 800000, 153600)); //ADM
        ItemList.registerItem(0x3AABD, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 224200)); //ADM GQ
        ItemList.registerItem(0x3A991, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 105000, 93120)); //CAPT
        ItemList.registerItem(0x3AAB9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 117500)); //CAPT GQ
        ItemList.registerItem(0x3A990, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 100000, 87120)); //CDR
        ItemList.registerItem(0x3AAB8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 107900)); //CDR GQ
        ItemList.registerItem(0x3A98A, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 70000, 51120)); //CPO
        ItemList.registerItem(0x3AAB2, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 58300)); //CPO GQ
        ItemList.registerItem(0x3A98C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 80000, 63120)); //ENS
        ItemList.registerItem(0x3AAB4, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 73450)); //ENS GQ
        ItemList.registerItem(0x3A98F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 95000, 81120)); //LCDR
        ItemList.registerItem(0x3AAB7, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 98200)); //LCDR GQ
        ItemList.registerItem(0x3A98E, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 90000, 75120)); //LT
        ItemList.registerItem(0x3AAB6, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 89750)); //LT GQ
        ItemList.registerItem(0x3A98D, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 85000, 69120)); //LTJG
        ItemList.registerItem(0x3AAB5, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 81600)); //LTJG GQ
        ItemList.registerItem(0x3A989, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 65000, 45120)); //PO
        ItemList.registerItem(0x3AAB1, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 51100)); //PO GQ
        ItemList.registerItem(0x3A993, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 600000, 123600)); //RADM
        ItemList.registerItem(0x3AABB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 162000)); //RADM GQ
        ItemList.registerItem(0x3A992, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 500000, 108600)); //RDML
        ItemList.registerItem(0x3AABA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 138200)); //RDML GQ
        ItemList.registerItem(0x3A987, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 55000, 33120)); //SA
        ItemList.registerItem(0x3AAAF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 36900)); //SA GQ
        ItemList.registerItem(0x3A98B, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 75000, 57120)); //SCPO
        ItemList.registerItem(0x3AAB3, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 65900)); //SCPO GQ
        ItemList.registerItem(0x3A988, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 60000, 39120)); //SN
        ItemList.registerItem(0x3AAB0, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 44000)); //SN GQ
        ItemList.registerItem(0x3A986, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 50000, 27120)); //SR
        ItemList.registerItem(0x3AAAE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 30000)); //SR GQ
        ItemList.registerItem(0x3A994, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 700000, 138600)); //VADM
        ItemList.registerItem(0x3AABC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 188300)); //VADM GQ
        //Zeon Overalls
        ItemList.registerItem(0x3A9FC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 17500, 8200)); //ADM
        ItemList.registerItem(0x3AB12, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 10590)); //ADM GQ
        ItemList.registerItem(0x3A9F8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 15500, 6720)); //CAPT
        ItemList.registerItem(0x3AB0E, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 8030)); //CAPT GQ
        ItemList.registerItem(0x3A9F7, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 15000, 6720)); //CDR
        ItemList.registerItem(0x3AB0D, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7830)); //CDR GQ
        ItemList.registerItem(0x3A9F1, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 12000, 3600)); //CPO
        ItemList.registerItem(0x3AB07, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4900)); //CPO GQ
        ItemList.registerItem(0x3A9F3, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 13000, 5200)); //ENS
        ItemList.registerItem(0x3AB09, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6120)); //ENS GQ
        ItemList.registerItem(0x3A9F6, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 14500, 6720)); //LCDR
        ItemList.registerItem(0x3AB0C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7620)); //LCDR GQ
        ItemList.registerItem(0x3A9F5, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 14000, 5200)); //LT
        ItemList.registerItem(0x3AB0B, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6480)); //LT GQ
        ItemList.registerItem(0x3A9F4, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 13500, 5200)); //LTJG
        ItemList.registerItem(0x3AB0A, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6300)); //LTJG GQ
        ItemList.registerItem(0x3A9F0, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 11500, 3600)); //PO
        ItemList.registerItem(0x3AB06, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4780)); //PO GQ
        ItemList.registerItem(0x3A9FA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 16500, 8200)); //RADM
        ItemList.registerItem(0x3AB10, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 9600)); //RADM GQ
        ItemList.registerItem(0x3A9F9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 16000, 8200)); //RDML
        ItemList.registerItem(0x3AB0F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 9280)); //RDML GQ
        ItemList.registerItem(0x3A9EE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 10500, 3600)); //SA
        ItemList.registerItem(0x3AB04, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4550)); //SA GQ
        ItemList.registerItem(0x3A9F2, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 12500, 3600)); //SCPO
        ItemList.registerItem(0x3AB08, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5080)); //SCPO GQ
        ItemList.registerItem(0x3A9EF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 11000, 3600)); //SN
        ItemList.registerItem(0x3AB05, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4660)); //SN GQ
        ItemList.registerItem(0x3A9FB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 17000, 8200)); //VADM
        ItemList.registerItem(0x3AB11, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 9940)); //VADM GQ
        ItemList.registerItem(0x3A9C7, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 10000, 3700)); //SR
        ItemList.registerItem(0x3AAEF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4300)); //SR GQ
        //Zeon Spacesuits
        ItemList.registerItem(0x3A9C5, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 800000, 164100)); //ADM
        ItemList.registerItem(0x3AAED, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 444000)); //ADM GQ
        ItemList.registerItem(0x3A9C1, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 155000, 104100)); //CAPT
        ItemList.registerItem(0x3AAE9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 151000)); //CAPT GQ
        ItemList.registerItem(0x3A9C0, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 150000, 976200)); //CDR
        ItemList.registerItem(0x3AAE8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 142800)); //CDR GQ
        ItemList.registerItem(0x3A9BA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 120000, 61620)); //CPO
        ItemList.registerItem(0x3AAE2, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 76500)); //CPO GQ
        ItemList.registerItem(0x3A9BC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 130000, 73620)); //ENS
        ItemList.registerItem(0x3AAE4, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 94100)); //ENS GQ
        ItemList.registerItem(0x3A9BF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 145000, 91620)); //LCDR
        ItemList.registerItem(0x3AAE7, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 128000)); //LCDR GQ
        ItemList.registerItem(0x3A9BE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 140000, 85620)); //LT
        ItemList.registerItem(0x3AAE6, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 115400)); //LT GQ
        ItemList.registerItem(0x3A9BD, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 135000, 79620)); //LTJG
        ItemList.registerItem(0x3AAE5, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 104200)); //LTJG GQ
        ItemList.registerItem(0x3A9B9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 115000, 55620)); //PO
        ItemList.registerItem(0x3AAE1, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 68000)); //PO GQ
        ItemList.registerItem(0x3A9C3, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 600000, 134100)); //RADM
        ItemList.registerItem(0x3AAEB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 230800)); //RADM GQ
        ItemList.registerItem(0x3A9C2, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 500000, 119100)); //RDML
        ItemList.registerItem(0x3AAEA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 185300)); //RDML GQ
        ItemList.registerItem(0x3A9B7, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 105000, 43620)); //SA
        ItemList.registerItem(0x3AADF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 51900)); //SA GQ
        ItemList.registerItem(0x3A9BB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 125000, 67620)); //SCPO
        ItemList.registerItem(0x3AAE3, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 85500)); //SCPO GQ
        ItemList.registerItem(0x3A9B8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 110000, 49620)); //SN
        ItemList.registerItem(0x3AAE0, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 59800)); //SN GQ
        ItemList.registerItem(0x3A9B6, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 100000, 37620)); //SR
        ItemList.registerItem(0x3AADE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 44500)); //SR GQ
        ItemList.registerItem(0x3A9C4, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 700000, 149100)); //VADM
        ItemList.registerItem(0x3AAEC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 301000)); //VADM GQ
        //Zeon uniformer.
        ItemList.registerItem(0x3A9A5, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 800000, 153600)); //ADM
        ItemList.registerItem(0x3AACD, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 224200)); //ADM GQ
        ItemList.registerItem(0x3A9A1, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 105000, 93120)); //CAPT
        ItemList.registerItem(0x3AAC9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 117500)); //CAPT GQ
        ItemList.registerItem(0x3A9A0, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 100000, 87120)); //CDR
        ItemList.registerItem(0x3AAC8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 107900)); //CDR GQ
        ItemList.registerItem(0x3A99A, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 70000, 51120)); //CPO
        ItemList.registerItem(0x3AAC2, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 58300)); //CPO GQ
        ItemList.registerItem(0x3A99C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 80000, 63120)); //ENS
        ItemList.registerItem(0x3AAC4, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 73450)); //ENS GQ
        ItemList.registerItem(0x3A99F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 95000, 81120)); //LCDR
        ItemList.registerItem(0x3AAC7, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 98200)); //LCDR GQ
        ItemList.registerItem(0x3A99E, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 90000, 75120)); //LT
        ItemList.registerItem(0x3AAC6, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 89750)); //LT GQ
        ItemList.registerItem(0x3A99D, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 85000, 69120)); //LTJG
        ItemList.registerItem(0x3AAC5, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 81600)); //LTJG GQ
        ItemList.registerItem(0x3A999, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 65000, 45120)); //PO
        ItemList.registerItem(0x3AAC1, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 51100)); //PO GQ
        ItemList.registerItem(0x3A9A3, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 600000, 123600)); //RADM
        ItemList.registerItem(0x3AACB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 162000)); //RADM GQ
        ItemList.registerItem(0x3A9A2, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 500000, 108600)); //RDML
        ItemList.registerItem(0x3AACA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 138200)); //RDML GQ
        ItemList.registerItem(0x3A997, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 55000, 33120)); //SA
        ItemList.registerItem(0x3AABF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 36900)); //SA GQ
        ItemList.registerItem(0x3A99B, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 75000, 57120)); //SCPO
        ItemList.registerItem(0x3AAC3, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 65900)); //SCPO GQ
        ItemList.registerItem(0x3A998, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 60000, 39120)); //SN
        ItemList.registerItem(0x3AAC0, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 44000)); //SN GQ
        ItemList.registerItem(0x3A996, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 50000, 27120)); //SR
        ItemList.registerItem(0x3AABE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 30000)); //SR GQ
        ItemList.registerItem(0x3A9A4, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 700000, 138600)); //VADM
        ItemList.registerItem(0x3AACC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 188300)); //VADM GQ
        //Spesielle uniformer.
        ItemList.registerItem(0x3ABDD, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0)); //Kycillia uniform 
        ItemList.registerItem(0x3ABDE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0)); //Sayla uniform
        ItemList.registerItem(0x3ABDC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0)); //Bright uniform
        ItemList.registerItem(0x3ABDB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0)); //Garma uniform
        ItemList.registerItem(0x3ABDA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0)); //Mathilda uniform
        ItemList.registerItem(0x3ABD9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0)); //Char uniform
        ItemList.registerItem(0x3ABD8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0)); //Amuro uniform
        ItemList.registerItem(0x3AA82, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 12000)); //Formal suit
        ItemList.registerItem(0x3AA2B, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 25000)); //EF combat uniform
        ItemList.registerItem(0x3AB28, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 33600)); //EF combat uniform GQ
        ItemList.registerItem(0x3AA2E, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 25000)); //Zeon combat uniform
        ItemList.registerItem(0x3AB2B, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 33600)); //Zeon combat uniform GQ

        //Registrer coats.
        ItemList.registerItem(0x3A9D9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 10000, 7300)); //Leather jacket
        ItemList.registerItem(0x3AA1E, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 10000, 7500)); //Shortcoat
        ItemList.registerItem(0x3AA6A, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7500)); //Fur trimmed parka
        ItemList.registerItem(0x3AA6C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4600)); //Plain turtleneck sweater
        ItemList.registerItem(0x3AA6B, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4600)); //Turtleneck sweater

        //Registrer bukser.
        ItemList.registerItem(0x3AA0F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 5000, 2050)); //Chinos
        ItemList.registerItem(0x3A9D4, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5580)); //Jeans
        ItemList.registerItem(0x3AA10, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 2000, 1550)); //Kneelength cargo pants
        ItemList.registerItem(0x3A9D3, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 2000, 1540)); //Kneelength pants
        ItemList.registerItem(0x3AA65, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5580)); //Bellbottom jeans
        ItemList.registerItem(0x3AA7F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5580)); //Box pleats skirt
        ItemList.registerItem(0x3AA80, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5580)); //Check box pleats skirt
        ItemList.registerItem(0x3AA7D, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 3000)); //Chinese pattern pants
        ItemList.registerItem(0x3AA67, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5580)); //Cropped jeans
        ItemList.registerItem(0x3AA81, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6300)); //Embroidered skirt
        ItemList.registerItem(0x3AA66, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5580)); //Hot pants
        ItemList.registerItem(0x3AA64, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5580)); //Loose fit jeans
        ItemList.registerItem(0x3AA68, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5580)); //Painter pants
        ItemList.registerItem(0x3AA7E, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 3000)); //Pants with buttons
        ItemList.registerItem(0x3AA69, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5580)); //Sabrina pants
        ItemList.registerItem(0x3AA63, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 55800)); //Sideline jeans
        ItemList.registerItem(0x3AA6E, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 1540)); //Sideline spats
        ItemList.registerItem(0x3AA6D, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 1540)); //Spats

        //Registrer gloves.
        ItemList.registerItem(0x3A9D6, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 7000, 6550)); //EF gloves
        ItemList.registerItem(0x3AAF0, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 8300)); //EF gloves GQ
        ItemList.registerItem(0x3AA17, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 10000, 7000)); //Sportswatch
        ItemList.registerItem(0x3AB1A, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 10300)); //Sportswatch GQ
        ItemList.registerItem(0x3AA04, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 2000, 1540)); //Wristband two-tone
        ItemList.registerItem(0x3AA03, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 2000, 1540)); //Wristband
        ItemList.registerItem(0x3A9D7, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 7000, 6550)); //Zeon gloves
        ItemList.registerItem(0x3AAF1, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 8300)); //Zeon gloves GQ
        ItemList.registerItem(0x3AA2C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7700)); //EF fingerless gloves
        ItemList.registerItem(0x3AB29, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 8080)); //EF fingerless gloves GQ
        ItemList.registerItem(0x3AA4F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 2600)); //Mittens
        ItemList.registerItem(0x3AA51, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7700)); //Stud wristband gold
        ItemList.registerItem(0x3AB4D, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 8080)); //Stud wristband gold GQ
        ItemList.registerItem(0x3AA50, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7700)); //Stud wristband silver
        ItemList.registerItem(0x3AB4C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 8080)); //Stud wristband silver GQ
        ItemList.registerItem(0x3AA2F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7700)); //Zeon long gloves
        ItemList.registerItem(0x3AB2C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 8080)); //Zeon long gloves GQ

        //Registrer shoes.
        ItemList.registerItem(0x3AA0C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 1000, 500)); //Beach sandal
        ItemList.registerItem(0x3A9FE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 5000, 3050)); //Cotton sneakers
        ItemList.registerItem(0x3A9FF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 7000, 6950)); //Leather short boots
        ItemList.registerItem(0x3AA0E, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 1000, 500)); //Sabot sandal
        ItemList.registerItem(0x3A9D5, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 7000, 6950)); //Sneakers
        ItemList.registerItem(0x3AA0D, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 2000, 1000)); //Sports sandal
        ItemList.registerItem(0x3AA70, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 10800)); //Engineer boots
        ItemList.registerItem(0x3AA71, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 10800)); //Fur trimmed boots
        ItemList.registerItem(0x3AA6F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7450)); //Moccasin

        //Registrer skjorter.
        ItemList.registerItem(0x3AA07, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 3000, 1540)); //L T-shirt border
        ItemList.registerItem(0x3AA06, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 3000, 1540)); //L T-shirt two-tone
        ItemList.registerItem(0x3AA05, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 3000, 1540)); //L T-shirt
        ItemList.registerItem(0x3AA11, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 3000, 1540)); //Polo shirt
        ItemList.registerItem(0x3A9D2, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 3000, 2540)); //Shirt
        ItemList.registerItem(0x3A9DA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 3000, 1540)); //T-shirt two tone
        ItemList.registerItem(0x3A9D1, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 3000, 1540)); //T-shirt
        ItemList.registerItem(0x3AA7C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 3000)); //Chinese no sleeves
        ItemList.registerItem(0x3AA7B, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 3000)); //Chinese pattern no sleeves
        ItemList.registerItem(0x3AA62, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 2540)); //Cleric shirt
        ItemList.registerItem(0x3AA5E, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 2540)); //Flannel shirt
        ItemList.registerItem(0x3AA7A, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 1550)); //No sleeve shirt
        ItemList.registerItem(0x3AA79, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 1550)); //No sleeve sports shirt
        ItemList.registerItem(0x3AA60, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 2540)); //Shirt with tie
        ItemList.registerItem(0x3AA5D, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 1540)); //Sports T-shirt
        ItemList.registerItem(0x3AA5F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 2540)); //Striped shirt
        ItemList.registerItem(0x3AA61, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 2540)); //White shirt with tie
        //ItemList.registerItem(0x, new GameItem(ItemType.Clothes,ItemSubType.Ingen,true,true,0,0)); //
        //ItemList.registerItem(0x, new GameItem(ItemType.Clothes,ItemSubType.Ingen,true,true,0,0)); //

        ItemList.registerItem(0x3A9CF, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Anaheim
        ItemList.registerItem(0x3A9CD, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Perth
        ItemList.registerItem(0x3AA24, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Ecotopia 2
        ItemList.registerItem(0x3AA22, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Southern Cross
        ItemList.registerItem(0x3AA23, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Ecotopia 1
        ItemList.registerItem(0x3A9CB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Canberra
        ItemList.registerItem(0x3A9CA, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Sydney
        ItemList.registerItem(0x3A9D0, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Zeonic
        ItemList.registerItem(0x3A9CE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Brisbane
        ItemList.registerItem(0x3AA26, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Zeal 2
        ItemList.registerItem(0x3A9C9, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Melbourne
        ItemList.registerItem(0x3A9C8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Adelaide
        ItemList.registerItem(0x3AA25, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Zeal 1
        ItemList.registerItem(0x3A9CC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3660)); //Darwin

        //"Spesielle" T-shirts. Tilgjengelig via NPC drops.
        ItemList.registerItem(0x3AA39, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0, 3000)); //Limited edition
        ItemList.registerItem(0x3AA38, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0, 3000)); //Zeon
        ItemList.registerItem(0x3AA37, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0, 3000)); //EF
        ItemList.registerItem(0x3AA36, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0, 3000)); //Zen
        ItemList.registerItem(0x3AA35, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0, 3000)); //Arts Oh
        ItemList.registerItem(0x3AA34, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0, 3000)); //I-sk
        ItemList.registerItem(0x3AA33, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0, 3000)); //Cloth pictures
        ItemList.registerItem(0x3AA32, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0, 3000)); //The Goso
        ItemList.registerItem(0x3AA31, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0, 3000)); //Zeon limited edition
        ItemList.registerItem(0x3AA30, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0, 3000)); //EF limited edition

        //Registrer hatter.
        ItemList.registerItem(0x3A9DB, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 2000, 1540)); //Cap two-tone
        ItemList.registerItem(0x3A9D8, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 2000, 1540)); //Cap
        ItemList.registerItem(0x3AA00, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 7000, 6950)); //Cowboy hat
        ItemList.registerItem(0x3A9DD, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 5000, 4550)); //EF Hat
        ItemList.registerItem(0x3AAF3, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5800)); //EF Hat GQ
        ItemList.registerItem(0x3A984, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 10000, 6060)); //EF Helmet
        ItemList.registerItem(0x3AAAC, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6700)); //EF Helmet GQ
        ItemList.registerItem(0x3A985, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 10000, 6060)); //Zeon Helmet
        ItemList.registerItem(0x3AAAD, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 6700)); //Zeon Helmet GQ
        ItemList.registerItem(0x3AA1F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3550)); //EF work hat
        ItemList.registerItem(0x3AB21, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4550)); //EF work hat GQ
        ItemList.registerItem(0x3AA20, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 4000, 3550)); //Zeon work hat
        ItemList.registerItem(0x3AB22, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4550)); //Zeon work hat GQ
        ItemList.registerItem(0x3AA57, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 40000, 35000)); //Fur hat black
        ItemList.registerItem(0x3AB4F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 0, 42000)); //Fur hat black GQ
        ItemList.registerItem(0x3AA56, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 40000, 35000)); //Fur hat brown
        ItemList.registerItem(0x3AB4E, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 42000)); //Fur hat brown GQ
        ItemList.registerItem(0x3AA58, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 40000, 35000)); //Fur hat white
        ItemList.registerItem(0x3AB50, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 42000)); //Fur hat white GQ
        ItemList.registerItem(0x3AA1C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5500)); //Headset Mathum Sonic
        ItemList.registerItem(0x3AB1F, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 14000)); //Headset Mathum Sonic GQ
        ItemList.registerItem(0x3AA1D, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5550)); //Headset Felipe
        ItemList.registerItem(0x3AB20, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 14000)); //Headset Felipe GQ
        ItemList.registerItem(0x3AA27, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5500)); //Headset Suze
        ItemList.registerItem(0x3AB24, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 14000)); //Headset Suze GQ
        ItemList.registerItem(0x3AA28, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5550)); //Headset Gramonica
        ItemList.registerItem(0x3AB25, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 14000)); //Headset Gramonica GQ
        ItemList.registerItem(0x3AA01, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 2000, 1550)); //Hunting cap
        ItemList.registerItem(0x3A9DE, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5550)); //Zeon hat
        ItemList.registerItem(0x3AAF4, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7060)); //Zeon hat GQ
        ItemList.registerItem(0x3A9FD, new GameItem(ItemType.Clothes, ItemSubType.Ingen, true, true, 8000, 5550)); //Zeon S hat
        ItemList.registerItem(0x3AB13, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7000)); //Zeon S hat GQ
        ItemList.registerItem(0x3AA73, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 1550)); //Bandanna
        ItemList.registerItem(0x3AA75, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 3050)); //Bandanna three color
        ItemList.registerItem(0x3AB54, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 3300)); //Bandanna three color GQ
        ItemList.registerItem(0x3AA74, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 3050)); //Bandanna tiger stripe
        ItemList.registerItem(0x3AB53, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 3300)); //Bandanna tiger stripe GQ
        ItemList.registerItem(0x3AA76, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 3050)); //Bandanna urban camo
        ItemList.registerItem(0x3AB55, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 3300)); //Bandanna urban camo GQ
        ItemList.registerItem(0x3AA52, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 2600)); //Beret
        ItemList.registerItem(0x3AA0A, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 1540)); //De gaulle cap
        ItemList.registerItem(0x3AA0B, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 1540)); //De gaulle cap two-tone
        ItemList.registerItem(0x3AA29, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5500)); //EF combat helmet
        ItemList.registerItem(0x3AB26, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 7700)); //EF combat helmet GQ
        ItemList.registerItem(0x3AA2A, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, false, 0, 5500)); //EF combat helmet visor NPC drop!
        ItemList.registerItem(0x3AA72, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 1550)); //Hachimaki
        ItemList.registerItem(0x3AA54, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 2600)); //Ski cap
        ItemList.registerItem(0x3AA55, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 2600)); //Tibetan cap
        ItemList.registerItem(0x3AA53, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 2600)); //Watch cap				
        ItemList.registerItem(0x3AA2D, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4950)); //Zeon overseas cap A
        ItemList.registerItem(0x3AB2A, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5270)); //Zeon overseas cap A GQ
        ItemList.registerItem(0x3AA45, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4950)); //zeon overseas cap B
        ItemList.registerItem(0x3AA46, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4950)); //zeon overseas cap S
        ItemList.registerItem(0x3AA08, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 1000)); //Strawhat 
        ItemList.registerItem(0x3AA09, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 1500)); //Boater
        ItemList.registerItem(0x3AA42, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4950)); //ef beret A
        ItemList.registerItem(0x3AA43, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4950)); //ef beret B
        ItemList.registerItem(0x3AA44, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 4950)); //ef beret S

        ItemList.registerItem(0x3AA3C, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5270)); //ring ruby OK
        ItemList.registerItem(0x3AA3D, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5270)); //ring sapphire OK
        ItemList.registerItem(0x3AA3E, new GameItem(ItemType.Clothes, ItemSubType.Ingen, false, true, 0, 5270)); //ring emerald OK

    }

}
