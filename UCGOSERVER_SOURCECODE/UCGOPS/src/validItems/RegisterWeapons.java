package validItems;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen registrerer alle gyldige våpen som kan brukes.
 */
public class RegisterWeapons {

    public static void execute() {

        //Spesielle CQB våpen.
        ItemList.registerItem(0x44659, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, false, 0, 14000)); //Giant heat hawk
        ItemList.registerItem(0x4465B, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, false, 0, 34000)); //Great heat hawk

        ItemList.registerItem(0x445D1, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 500000)); //BB MPBG
        //ItemList.registerItem(0x445E4, new GameItem(ItemType.Weapon,ItemSubType.Shell,false,false,0,500000)); //BB MG

        //Etter reboot 2019. VÅPEN TILGJENGELIG FOR EF
        ItemList.registerItem(0x4465D, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 5000, 2500)); //MS chest vulcan
        ItemList.registerItem(0x4468F, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 5400)); //MS chest vulcan EX
        //ItemList.registerItem(0x44642, new GameItem(ItemType.Weapon,ItemSubType.Shell,false,false,0,5880)); //HG Chest vulcan
        ItemList.registerItem(0x44665, new GameItem(ItemType.Weapon, ItemSubType.Ingen, true, true, 10200, 9900)); //EF throwing device
        ItemList.registerItem(0x44697, new GameItem(ItemType.Weapon, ItemSubType.Ingen, false, true, 0, 25000)); //EF throwing device EX
        ItemList.registerItem(0x445D0, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 5000, 2500)); //Gun launcher (RX-75)
        ItemList.registerItem(0x44602, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 5400)); //Gun launcher (RX-75) EX
        ItemList.registerItem(0x44626, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 5880)); //Gun launcher (RX-75) HG
        ItemList.registerItem(0x44656, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 0, 0)); //Beam spray gun
        ItemList.registerItem(0x44688, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 20000)); //Beam spray gun EX
        ItemList.registerItem(0x445EE, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 0, 0)); //GM Rifle
        ItemList.registerItem(0x44620, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 100000)); //GM Rifle EX
        ItemList.registerItem(0x445CF, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 0, 0)); //Long rifle
        ItemList.registerItem(0x44601, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 20000)); //Long rifle EX
        ItemList.registerItem(0x446BE, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, false, 140000, 5000)); //SP Long rifle                
        ItemList.registerItem(0x44658, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 21000, 14000)); //RGM-79C Beam saber
        ItemList.registerItem(0x4468A, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, true, 0, 47000)); //RGM-79C Beam saber EX        
        ItemList.registerItem(0x445E7, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 20000, 14000)); //RGM-79G Beam saber
        ItemList.registerItem(0x44619, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, true, 0, 45000)); //RGM-79G Beam saber EX
        ItemList.registerItem(0x44639, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, false, 0, 16800)); //RGM-79G Beam saber HG
        ItemList.registerItem(0x445DB, new GameItem(ItemType.Weapon, ItemSubType.Sniping, true, true, 200000, 100000)); //Longrange beam rifle
        ItemList.registerItem(0x4460D, new GameItem(ItemType.Weapon, ItemSubType.Sniping, false, true, 0, 200000)); //Longrange beam rifle EX
        ItemList.registerItem(0x44634, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 210000)); //Longrange beam rifle HG
        ItemList.registerItem(0x445ED, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 0, 0)); //Hyper bazooka
        ItemList.registerItem(0x4461F, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 100000)); //Hyper bazooka EX	        
        ItemList.registerItem(0x445C1, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 0, 0)); //Hyper bazooka ground type
        ItemList.registerItem(0x445F3, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 100000)); //Hyper bazooka ground type Ex
        ItemList.registerItem(0x44629, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 30000)); //Hyper bazooka ground type HG
        ItemList.registerItem(0x445C5, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 0, 0)); //HFW-GMG                
        ItemList.registerItem(0x445F7, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 100000)); //HFW-GMG EX
        ItemList.registerItem(0x4462B, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 100000)); //HFW-GMG HG
        ItemList.registerItem(0x4466F, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 50000, 25000)); //NFHI GMG
        ItemList.registerItem(0x446A1, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 100000)); //NFHI GMG EX
        ItemList.registerItem(0x445C7, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 75000, 40000)); //RGM-79 Beam gun
        ItemList.registerItem(0x445F9, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 100000)); //RGM-79 beam gun EX
        ItemList.registerItem(0x445DD, new GameItem(ItemType.Weapon, ItemSubType.Sniping, true, true, 400000, 150000)); //Sniping beam rifle         
        ItemList.registerItem(0x4460F, new GameItem(ItemType.Weapon, ItemSubType.Sniping, false, true, 0, 200000)); //Sniping beam rifle EX
        ItemList.registerItem(0x4465F, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 300000, 150000)); //MS beam cannon
        ItemList.registerItem(0x44691, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 200000)); //MS beam cannon EX
        ItemList.registerItem(0x445C2, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 100000, 50000)); //RX-78 Beam saber
        ItemList.registerItem(0x445F4, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, true, 0, 75000)); //RX-78 Beam saber EX
        ItemList.registerItem(0x445EC, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 100000, 50000)); //RX-79G Beam saber
        ItemList.registerItem(0x4461E, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, true, 0, 75000)); //RX-79G Beam saber EX
        ItemList.registerItem(0x445E0, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 300000, 100000)); //RGM-79L beam rifle
        ItemList.registerItem(0x44612, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 100000)); //RGM-79L beam rifle EX

        //Etter reboot 2019. VÅPEN TILGJENGELG FOR ZEON
        ItemList.registerItem(0x44666, new GameItem(ItemType.Weapon, ItemSubType.Ingen, true, true, 5000, 2500)); //Zeon throwing device
        ItemList.registerItem(0x44698, new GameItem(ItemType.Weapon, ItemSubType.Ingen, false, true, 0, 25000)); //Zeon throwing device EX
        ItemList.registerItem(0x44660, new GameItem(ItemType.Weapon, ItemSubType.H2H, true, true, 20000, 14000)); //Hammer Gun
        ItemList.registerItem(0x44692, new GameItem(ItemType.Weapon, ItemSubType.H2H, false, true, 0, 45000)); //Hammer Gun EX
        ItemList.registerItem(0x44645, new GameItem(ItemType.Weapon, ItemSubType.Ingen, false, false, 0, 16800)); //Hammer gun HG
        ItemList.registerItem(0x445C4, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 0, 0)); //ZMP-47D
        ItemList.registerItem(0x445F6, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 4400)); //ZMP-47D EX
        ItemList.registerItem(0x4462A, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 4800)); //ZMP-47D HG
        ItemList.registerItem(0x445CA, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 0, 0)); //ZMP-50D        
        ItemList.registerItem(0x445FC, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 8000)); //ZMP-50D EX
        ItemList.registerItem(0x445E5, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 10000, 5000)); //Heat hawk
        ItemList.registerItem(0x44617, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, true, 0, 8000)); //Heat hawk EX
        ItemList.registerItem(0x44627, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, false, 0, 4800)); //Heat hawk HG
        ItemList.registerItem(0x445EF, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 50000, 25000)); //Heat rod
        ItemList.registerItem(0x44621, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, true, 0, 25000)); //Heat rod EX
        ItemList.registerItem(0x4463F, new GameItem(ItemType.Weapon, ItemSubType.Ingen, false, false, 0, 11880)); //HG Heat rod
        ItemList.registerItem(0x445F0, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 10000, 5000)); //75mm machine gun (MS-07)                
        ItemList.registerItem(0x44622, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 12000)); //75mm machine gun (MS-07) EX
        ItemList.registerItem(0x445EA, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 20000, 14000)); //Heat sword
        ItemList.registerItem(0x4461C, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, true, 0, 47000)); //Heat sword EX
        ItemList.registerItem(0x4463B, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, false, 0, 16800)); //HG Heat sword
        ItemList.registerItem(0x445F1, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 50000, 25000)); //M-120AS
        ItemList.registerItem(0x44623, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 40000)); //M-120AS EX
        ItemList.registerItem(0x445E9, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 75000, 40000)); //Zaku Bazooka MS-06
        ItemList.registerItem(0x4461B, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 80000)); //Zaku Bazooka MS-06 EX
        ItemList.registerItem(0x44661, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 75000, 40000)); //MS-06R Beam Rifle
        ItemList.registerItem(0x44693, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 80000)); //MS-06R Beam Rifle EX
        ItemList.registerItem(0x445E6, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 10000, 5000)); //Heat saber
        ItemList.registerItem(0x44618, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, true, 0, 45000)); //Heat saber EX
        ItemList.registerItem(0x445D8, new GameItem(ItemType.Weapon, ItemSubType.H2H, true, true, 50000, 25000)); //MS claw
        ItemList.registerItem(0x4460A, new GameItem(ItemType.Weapon, ItemSubType.H2H, false, true, 0, 50000)); //MS claw EX
        ItemList.registerItem(0x44657, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 75000, 40000)); //MMP-78        
        ItemList.registerItem(0x44689, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 100000)); //MMP-78 EX
        ItemList.registerItem(0x445DC, new GameItem(ItemType.Weapon, ItemSubType.H2H, true, true, 50000, 25000)); //Arm punch (MSM-08)
        ItemList.registerItem(0x4460E, new GameItem(ItemType.Weapon, ItemSubType.H2H, false, true, 0, 50000)); //Arm punch (MSM-08) EX
        ItemList.registerItem(0x4465C, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 100000, 50000)); //MS torpedo
        ItemList.registerItem(0x4468E, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 100000)); //MS torpedo EX
        ItemList.registerItem(0x445DF, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 50000, 25000)); //Boomerang cutter (MSM-08)
        ItemList.registerItem(0x44611, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, true, 0, 100000)); //Boomerang cutter (MSM-08) EX
        ItemList.registerItem(0x445CB, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 250000, 100000)); //MMP-80
        ItemList.registerItem(0x445FD, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 100000)); //MMP-80 EX
        ItemList.registerItem(0x445D7, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 300000, 150000)); //Giant Bazooka MS-09
        ItemList.registerItem(0x44609, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 150000)); //Giant Bazooka MS-09 EX
        ItemList.registerItem(0x445D4, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 300000, 150000)); //MS bias mega particle beam gun
        ItemList.registerItem(0x44606, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 150000)); //MS bias mega particle beam gn EX

        //Etter reboot 2019. EF SHIELDS
        ItemList.registerItem(0x57E44, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //Ez-8 shield
        ItemList.registerItem(0x57E58, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, true, 0, 0)); //Ez-8 shield EX		
        ItemList.registerItem(0x57E42, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //RGM-79C shield
        ItemList.registerItem(0x57E56, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, true, 0, 0)); //RGM-79C shield EX
        ItemList.registerItem(0x57E41, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //RGM-79G shield
        ItemList.registerItem(0x57E55, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, true, 0, 0)); //RGM-79G shield EX
        ItemList.registerItem(0x57E69, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 0, 0)); //RGM-79G shield HG
        ItemList.registerItem(0x57E40, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //RX-78 shield
        ItemList.registerItem(0x57E54, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, true, 0, 0)); //RX-78 shield EX        
        ItemList.registerItem(0x57E43, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //RX-79G shield
        ItemList.registerItem(0x57E57, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, true, 0, 0)); //RX-79G shield EX

        //Etter reboot 2019. ZEON SHIELDS
        ItemList.registerItem(0x57E45, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //MS-07 shield
        ItemList.registerItem(0x57E59, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, true, 0, 0)); //MS-07 shield EX
        ItemList.registerItem(0x57E6D, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 0, 0)); //MS-07 shield HG
        ItemList.registerItem(0x57E46, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //MS-14 shield
        ItemList.registerItem(0x57E5A, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, true, 0, 0)); //MS-14 shield EX
        ItemList.registerItem(0x57E6E, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 0, 0)); //MS-14 shield HG

        //Etter reboot 2019. VÅPEN TILGJENGELIG FOR BÅDE EF OG ZEON.
        ItemList.registerItem(0x445CC, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 0, 0)); //MS cannon
        ItemList.registerItem(0x445FE, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 4400)); //MS cannon EX
        ItemList.registerItem(0x445C3, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 5000, 4900)); //MS head vulcan
        ItemList.registerItem(0x445F5, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 5200)); //MS head vulcan EX		
        ItemList.registerItem(0x44605, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 500000)); //MS mega particle beam gun EX		
        ItemList.registerItem(0x44613, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 100000)); //MS rocket launcher EX
        ItemList.registerItem(0x445E2, new GameItem(ItemType.Weapon, ItemSubType.H2H, true, true, 4000, 3900)); //MS grapple
        ItemList.registerItem(0x44614, new GameItem(ItemType.Weapon, ItemSubType.H2H, false, true, 0, 4100)); //MS grapple EX        

        //Etter reboot 2019. Registrer tank/fighter våpen.
        ItemList.registerItem(0x445CD, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 0, 1)); //Tank/Fighter cannon
        ItemList.registerItem(0x445FF, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 700)); //Tank/Fighter cannon EX
        ItemList.registerItem(0x445CE, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 1250, 830)); //Tank/Figher machinegun
        ItemList.registerItem(0x44600, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 1200)); //Tank/Figher machinegun EX
        ItemList.registerItem(0x44664, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 1250, 830)); //Tank/Fighter missile
        ItemList.registerItem(0x44696, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 1100)); //Tank/Fighter missile EX		
        ItemList.registerItem(0x44653, new GameItem(ItemType.Weapon, ItemSubType.Ingen, true, true, 2000, 830)); //Mining drill parts

        //Registrer alle Zeon våpen.		
        //NB! Giant Bazooka II og MS-14B Rocket Launcher har ingen EX versjon!                				                
        ItemList.registerItem(0x446E9, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 750000)); //Raketen Bazooka EX				        
        ItemList.registerItem(0x446D6, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 280000, 140000)); //Giant Bazooka MS-09 Hvit
        ItemList.registerItem(0x446D7, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 750000)); //Giant Bazooka MS-09 EX Hvit		        	        		

        ItemList.registerItem(0x446DC, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 62000, 35000)); //MMP-80 Hvit
        ItemList.registerItem(0x446DD, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 62000, 35000)); //MMP-80 Rød
        ItemList.registerItem(0x446DE, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 62000, 35000)); //MMP-80 Blå
        ItemList.registerItem(0x446DF, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 155000)); //MMP-80 EX Hvit
        ItemList.registerItem(0x446E0, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 155000)); //MMP-80 EX Rød
        ItemList.registerItem(0x446E1, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 155000)); //MMP-80 EX Blå                      	
        ItemList.registerItem(0x44615, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 3000000)); //MS-14 Beam Rifle EX
        ItemList.registerItem(0x446D0, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 1000000, 1000000)); //MS-14 Beam Rifle Hvit
        ItemList.registerItem(0x446D1, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 1000000, 1000000)); //MS-14 Beam Rifle Blå
        ItemList.registerItem(0x446D2, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 1000000, 1000000)); //MS-14 Beam Rifle Rød
        ItemList.registerItem(0x446D3, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 3000000)); //MS-14 Beam Rifle EX Hvit 
        ItemList.registerItem(0x446D4, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 3000000)); //MS-14 Beam Rifle EX Blå
        ItemList.registerItem(0x446D5, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 3000000)); //MS-14 Beam Rifle EX Rød		        
        ItemList.registerItem(0x4461A, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, true, 0, 1000000)); //Beam naginata EX		        	        

        ItemList.registerItem(0x445EB, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 8000, 4900)); //MS arm machinegun
        ItemList.registerItem(0x4461D, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 14000)); //MS arm machinegun EX		        

        ItemList.registerItem(0x445F8, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 20000)); //Magella top cannon EX		
        ItemList.registerItem(0x4468C, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 1000000)); //MS-14 Prototype beamrifle EX		
        ItemList.registerItem(0x44694, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 500000)); //Beam bazooka EX   
        ItemList.registerItem(0x446F8, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 1000000, 500000)); //Geara Doga Beam MG
        ItemList.registerItem(0x446F9, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 1000000, 500000)); //Geara Doga Beam MG #2                
        ItemList.registerItem(0x446FC, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1000000)); //AMX-107 Beam Rifle		                                                      

        ItemList.registerItem(0x445E1, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 50000, 25000)); //MS rocket launcher        
        ItemList.registerItem(0x44654, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 140000, 140000)); //Giant Bazooka II
        ItemList.registerItem(0x44655, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 400000, 400000)); //MS-14B Rocket Launcher

        ItemList.registerItem(0x445D3, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 150000, 100000)); //MS MPBG        
        ItemList.registerItem(0x445C6, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 100000, 50000)); //Magella top cannon
        ItemList.registerItem(0x445E8, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 125000, 60000)); //Beam naginata
        ItemList.registerItem(0x445E3, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 1000000, 1000000)); //MS-14 Beam Rifle
        ItemList.registerItem(0x446E8, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 140000, 140000)); //Raketen Bazooka
        ItemList.registerItem(0x44662, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 500000, 250000)); //Beam bazooka
        ItemList.registerItem(0x4465A, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 600000, 300000)); //MS-14 Prototype beamrifle

        //EF våpen.                                        
        ItemList.registerItem(0x445D2, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 100000, 50000)); //100mm Machinegun
        ItemList.registerItem(0x4465E, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 100000, 50000)); //Ball cannon                  
        ItemList.registerItem(0x445C2, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 12000, 7300)); //RX-78 Beam saber
        ItemList.registerItem(0x445EC, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, true, 100000, 50000)); //RX-79G Beam saber        
        ItemList.registerItem(0x445D5, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 250000, 250000)); //180mm Cannon

        ItemList.registerItem(0x445D6, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 800000, 800000)); //RX-79G beam rifle
        ItemList.registerItem(0x445D9, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 450000, 240000)); //RX-78-1 Beam Rifle
        ItemList.registerItem(0x445C8, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 1000000, 1000000)); //RX-78-2 beam rifle
        ItemList.registerItem(0x445C9, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 150000, 150000)); //RX-77 beam rifle
        ItemList.registerItem(0x445DE, new GameItem(ItemType.Weapon, ItemSubType.Sniping, true, true, 780000, 391000)); //Sniping beam rifle custom                
        ItemList.registerItem(0x446CE, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, true, 250000, 250000)); //6-Tube Missile Launcher		
        ItemList.registerItem(0x44604, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 1200000)); //100mm Machinegun EX	
        ItemList.registerItem(0x446D8, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 250000, 250000)); //180mm Cannon Mørkgrønn
        ItemList.registerItem(0x446D9, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 250000, 250000)); //180mm Cannon Lysgrå
        ItemList.registerItem(0x44607, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 1500000)); //180mm Cannon EX
        ItemList.registerItem(0x446DA, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 1500000)); //180mm Cannon EX Mørkgrønn
        ItemList.registerItem(0x446DB, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 1500000)); //180mm Cannon EX Lysgrå
        ItemList.registerItem(0x445C0, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, true, 0, 0)); //A.E-Br.G-Sc-L
        ItemList.registerItem(0x445F2, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 20000)); //A.E-Br.G-Sc-L EX		        		
        ItemList.registerItem(0x445FA, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 3000000)); //Blash (78-2 beam rifle) EX
        ItemList.registerItem(0x446CA, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 1000000, 1000000)); //Blash Hvit
        ItemList.registerItem(0x446CB, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 2500000, 2000000)); //Alex Gundam Beam Rifle
        ItemList.registerItem(0x446CC, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 1000000, 1000000)); //Blash Blå
        ItemList.registerItem(0x446CD, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 3000000)); //Blash EX Hvit		
        ItemList.registerItem(0x446CF, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 3000000)); //Blash EX Blå		        

        ItemList.registerItem(0x446E4, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 1000000, 446000)); //RGM-79L beam rifle Farget
        ItemList.registerItem(0x446E5, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 3000000)); //RGM-79L beam rifle EX Farget		
        ItemList.registerItem(0x445FB, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 780000)); //RX-77 beam rifle EX		
        ItemList.registerItem(0x44608, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 2000000)); //RX-79G beam rifle EX
        ItemList.registerItem(0x446E2, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 800000, 800000)); //RX-79G beam rifle Farget
        ItemList.registerItem(0x446E3, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 2000000)); //RX-79G beam rifle EX Farget		        
        ItemList.registerItem(0x446E6, new GameItem(ItemType.Weapon, ItemSubType.Sniping, false, true, 350000, 175000)); //Longrange beam rifle Farget
        ItemList.registerItem(0x446E7, new GameItem(ItemType.Weapon, ItemSubType.Sniping, false, true, 0, 930000)); //Longrange beam rifle EX Farget		        		        	
        ItemList.registerItem(0x44610, new GameItem(ItemType.Weapon, ItemSubType.Sniping, false, true, 0, 2340000)); //Sniping beam rifle custom EX		                        
        ItemList.registerItem(0x4460B, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, true, 0, 2850000)); //RX-78 Prototype beamrifle EX		
        ItemList.registerItem(0x44690, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, true, 0, 6000)); //Ball cannon EX
        ItemList.registerItem(0x446EA, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 1000000, 895000)); //RMS-179 Beam Rifle
        ItemList.registerItem(0x446ED, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1000000)); //RX-78GP01 Beam rifle
        ItemList.registerItem(0x446EE, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1000000)); //RX-78GP01 Blash Experimental
        ItemList.registerItem(0x446EF, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1000000)); //RX-78GP02 Beam Bazooka
        ItemList.registerItem(0x446F0, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1000000)); //RX-178 Beam Rifle AEUG
        ItemList.registerItem(0x446F1, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1000000)); //RX-178 Beam Rifle TITANS
        ItemList.registerItem(0x446F4, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, false, 0, 100000)); //RX-178 Beam Saber
        ItemList.registerItem(0x446F5, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 1000000, 500000)); //RGM-89 Jegan Beam Rifle
        ItemList.registerItem(0x446F6, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 1000000, 500000)); //RX-178 Super Gundam Long Rifle
        ItemList.registerItem(0x446F7, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1000000)); //MSN-00100 Beam Rifle                
        ItemList.registerItem(0x446FA, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1000000)); //MSZ-006 Beam Rifle
        ItemList.registerItem(0x446FB, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1000000)); //MSZ-006 HML		

        //Registrer alle EF skjold.                
        ItemList.registerItem(0x57E75, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //RX-78 shield Blå
        ItemList.registerItem(0x57E76, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //RX-79G shield 
        ItemList.registerItem(0x57E77, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //RX-79G shield
        ItemList.registerItem(0x57E78, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //RX-79G shield        
        ItemList.registerItem(0x57E79, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //RX-78NT-1 shield
        ItemList.registerItem(0x57E7A, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 0, 0)); //Nemo Shield                
        ItemList.registerItem(0x57E7C, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 25000, 24500)); //RX-78GP01 Shield
        ItemList.registerItem(0x57E7D, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 200000, 100000)); //RX-78GP02 Shield
        ItemList.registerItem(0x57E7E, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 100000, 50000)); //RX-178 Shield AEUG                
        ItemList.registerItem(0x57E7F, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 100000, 50000)); //RX-178 Shield TITANS
        ItemList.registerItem(0x57E81, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 25000, 24500)); //RGM-89 Jegan Shield                
        ItemList.registerItem(0x57E84, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 100000, 50000)); //MSZ-006 Shield

        //Registrer alle Zeon skjold.        
        ItemList.registerItem(0x57E6F, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //MS-14 shield Johnny Ridden
        ItemList.registerItem(0x57E70, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, true, 0, 15500)); //MS-14 shield EX Johnny Ridden
        ItemList.registerItem(0x57E71, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //MS-14 shield
        ItemList.registerItem(0x57E72, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, true, 0, 15500)); //MS-14 shield EX
        ItemList.registerItem(0x57E73, new GameItem(ItemType.Weapon, ItemSubType.Shield, true, true, 0, 0)); //MS-14 shield
        ItemList.registerItem(0x57E74, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, true, 0, 15500)); //MS-14 shield EX     
        ItemList.registerItem(0x57E82, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 25000, 24500)); //Geara Doga Shield
        ItemList.registerItem(0x57E83, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 25000, 24500)); //Geara Doga Shield, Rezin Schnyder
        ItemList.registerItem(0x57E85, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 100000, 50000)); //Bawoo Shield        

        //Registrer skjold som kan kjøpes/craftes av både EF og Zeon.
        ItemList.registerItem(0x57E7B, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 100000, 50000)); //PMX-001 Palace Athene shield
        ItemList.registerItem(0x57E80, new GameItem(ItemType.Weapon, ItemSubType.Shield, false, false, 100000, 50000)); //PMX-002 Bolinoak Shield        

        //Registrer alle HG våpen.
        ItemList.registerItem(0x44624, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 4800)); //HG MS Cannon
        ItemList.registerItem(0x44625, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1608000)); //HG blash                
        //ItemList.registerItem(0x44628, new GameItem(ItemType.Weapon,ItemSubType.Shell,false,false,0,5880)); //HG Head vulcan                
        ItemList.registerItem(0x4462C, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 12000)); //HG MTC
        ItemList.registerItem(0x4462D, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 96600)); //HG MS Beam gun
        ItemList.registerItem(0x4462E, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 996)); //HG Tank/Fighter MG
        ItemList.registerItem(0x4462F, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 214800)); //HG 100mm MG
        ItemList.registerItem(0x44630, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 267600)); //HG MS MPBG
        ItemList.registerItem(0x44631, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 53520)); //HG MS Bias MPBG
        ItemList.registerItem(0x44632, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 301200)); //HG 180mm Cannon
        ItemList.registerItem(0x44633, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, false, 0, 11700)); //HG MS Claw        
        ItemList.registerItem(0x44635, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, false, 0, 133800)); //HG Boomerang cutter
        ItemList.registerItem(0x44636, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 535200)); //HG 79L Beam rifle
        ItemList.registerItem(0x44637, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 64680)); //HG Rocket launcher
        ItemList.registerItem(0x44638, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, false, 0, 16800)); //HG Heat saber        
        ItemList.registerItem(0x4463A, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 16800)); //HG Zaku bazooka        
        ItemList.registerItem(0x4463C, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 5880)); //HG Arm MG
        ItemList.registerItem(0x4463D, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1338000)); //HG MS-14 Beam rifle
        ItemList.registerItem(0x4463E, new GameItem(ItemType.Weapon, ItemSubType.H2H, false, false, 0, 5880)); //HG Arm punch        
        ItemList.registerItem(0x44640, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 16800)); //HG MMP-78
        ItemList.registerItem(0x44641, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 14280)); //HG MS Torpedo        
        ItemList.registerItem(0x44643, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 4800)); //HG Ball cannon
        ItemList.registerItem(0x44644, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 376800)); //HG MS Beam cannon        
        ItemList.registerItem(0x44646, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 168000)); //HG Giant Bazooka

        //Registrer alle SP våpen.
        ItemList.registerItem(0x446BA, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, false, 5000000, 1500000)); //SP Blash
        ItemList.registerItem(0x446BB, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, false, 1000000, 250000)); //SP 180mm Cannon 
        ItemList.registerItem(0x446BC, new GameItem(ItemType.Weapon, ItemSubType.Sniping, true, false, 700000, 175000)); //SP Longrange rifle
        ItemList.registerItem(0x446BD, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, false, 150000, 22500)); //SP Hyper bazooka        
        ItemList.registerItem(0x446BF, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, false, 300000, 75000)); //SP HFW-GMG
        ItemList.registerItem(0x446C0, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, false, 100000, 5000)); //SP Ball Cannon
        ItemList.registerItem(0x446C1, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, false, 150000, 20000)); //SP Hyper bazooka ground
        ItemList.registerItem(0x446C2, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, false, 100000, 3250)); //SP ZMP-50D
        ItemList.registerItem(0x446C3, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, false, 4000000, 500000)); //MS-14 prototype BR
        ItemList.registerItem(0x446C4, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, false, 1000000, 140000)); //SP Giant Bazooka
        ItemList.registerItem(0x446C5, new GameItem(ItemType.Weapon, ItemSubType.CQB, true, false, 50000, 3100)); //SP Heat hawk
        ItemList.registerItem(0x446C6, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, false, 150000, 31000)); //SP MMP-80
        ItemList.registerItem(0x446C7, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, false, 150000, 11000)); //SP Zaku bazooka
        ItemList.registerItem(0x446C8, new GameItem(ItemType.Weapon, ItemSubType.Shell, true, false, 800000, 6000)); //SP Magella cannon
        ItemList.registerItem(0x446C9, new GameItem(ItemType.Weapon, ItemSubType.Beam, true, false, 500000, 32500)); //SP MS-06 beam rifle

        //Registrer våpen som kan kjøpes/craftes av både EF og Zeon.
        ItemList.registerItem(0x446EB, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 1000000, 895000)); //RMS-108 Marasai Beam Rifle
        ItemList.registerItem(0x446EC, new GameItem(ItemType.Weapon, ItemSubType.Beam, false, false, 0, 1000000)); //PMX-001 Palace Athene dual beam		                
        ItemList.registerItem(0x446F2, new GameItem(ItemType.Weapon, ItemSubType.CQB, false, false, 0, 100000)); //RX-160 Byarlant Beam Saber
        ItemList.registerItem(0x446F3, new GameItem(ItemType.Weapon, ItemSubType.Shell, false, false, 0, 500000)); //RX-178 Hyper Bazooka
    }
}
