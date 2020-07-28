package gameServer;

import java.util.Hashtable;
import java.util.concurrent.*;
import npc.NPCthread;
import occupationCity.OccupationCity;
import opcodes.*;
import packetHandler.UCGOcrypto;
import players.*;
import serverComp.*;
import serverComp.UCGOexecutor.UCGOexecutor;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Dette er hovedfilen til UCGO Gameserver.
 */
public class start {

    public static void execute() {

        //Registrer alle gyldige items i spillet.
        validItems.RegisterVehicles.execute();
        validItems.RegisterTaxiTransport.execute();
        validItems.RegisterWeapons.execute();
        validItems.RegisterAmmoRepair.execute();
        validItems.RegisterBattleships.execute();
        validItems.RegisterBuildings.execute();
        validItems.RegisterClothes.execute();
        validItems.RegisterEngines.execute();
        validItems.RegisterMaterials.execute();
        validItems.RegisterStuff.execute();
        validItems.RegisterToys.execute();
        validItems.RegisterNPCs.execute();

        //Initialiser diverse data.
        gameData.ClothingLookID.execute();
        gameData.StatManager.execute();
        crafting.clothes.ClothesCraftingManager.execute();
        crafting.mining.MiningManager.execute();
        crafting.refining.RefiningCraftingManager.execute();
        crafting.weapons.WeaponCraftingManager.execute();
        crafting.vehicles.VehicleCraftingManager.execute();

        //Spawn items.
        SpawnItems.spawn();
        npc.locations.Spawn.execute();

        UCGOcrypto crypt = new UCGOcrypto();

        PlayerHandler<PlayerGame> playerList = new PlayerHandler<PlayerGame>();
        PlayerHandlerStatic.registerPlayerHandlerGame(playerList); //Slik at vi kan få statisk tilgang til spillere.

        ExecutorService pool = new UCGOexecutor(15);//Executors.newCachedThreadPool();

        Hashtable<Integer, Opcode> op = new Hashtable<Integer, Opcode>();

        //Registrer opcodes her.		
        registerOpcodes.register(op);

        ServerLoop<PlayerGame> sloop = new ServerLoop<PlayerGame>(pool, playerList);

        ServerListener<PlayerGame> slistener = new ServerListener<PlayerGame>(
                config.Server.serverListenerGame, 24010, crypt, playerList, op);

        ItemCleanup item_cleaner = new ItemCleanup();

        GameServerLogoff server_logoff = new GameServerLogoff(playerList);

        NPCthread npc_ai = new NPCthread();

        OccupationCity oc_thread = new OccupationCity();

        new Thread(sloop).start();

        new Thread(slistener).start();

        new Thread(item_cleaner).start();

        new Thread(server_logoff).start();

        new Thread(npc_ai).start();

        new Thread(oc_thread).start();

        //TEST KODE FOR PAKKER
        //opcode_tester op_test = new opcode_tester();
        //new Thread(op_test).start();
    }

}
