package gameServer;

import characters.Posisjon;
import items.*;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å spawne diverse items inn i spillet ved start.
 * F.eks bygninger.
 */
public class SpawnItems {

    public static void spawn() {
        spawnStuff();
        Posisjon pos;

        //EF NEWMAN CAMP 1
        //EF hangar.
        pos = new Posisjon(56912438, -54484057, 2272, 9000, 1);
        ItemHandler.spawnItem(new Camp(0x53025), pos);
        //EF weapons store.
        pos = new Posisjon(56912416, -54483924, 2272, -8000, 1);
        ItemHandler.spawnItem(new Camp(0x53021), pos);
        //EF Machine store.
        pos = new Posisjon(56912387, -54483856, 2272, -8000, 1);
        ItemHandler.spawnItem(new Camp(0x53028), pos);
        //EF bank.
        pos = new Posisjon(56912477, -54484175, 2272, 0, 1);
        ItemHandler.spawnItem(new Camp(0x53026), pos);
        //EF repair.
        pos = new Posisjon(56912495, -54484314, 2272, -4300, 1);
        ItemHandler.spawnItem(new Camp(0x53020), pos);

        //EF NEWMAN CAMP 2
        //EF hangar.
        pos = new Posisjon(56911810, -54475421, 2272, -9000, 1);
        ItemHandler.spawnItem(new Camp(0x53025), pos);
        //EF weapons store.
        pos = new Posisjon(56911560, -54475420, 2272, 23500, 1);
        ItemHandler.spawnItem(new Camp(0x53021), pos);
        //EF Machine store.
        pos = new Posisjon(56911652, -54475390, 2272, 23500, 1);
        ItemHandler.spawnItem(new Camp(0x53028), pos);
        //EF bank.
        pos = new Posisjon(56911692, -54475994, 2272, 0, 1);
        ItemHandler.spawnItem(new Camp(0x53026), pos);
        //EF repair.
        pos = new Posisjon(56911785, -54475860, 2272, -4300, 1);
        ItemHandler.spawnItem(new Camp(0x53020), pos);

        //ZEON NEWMAN CAMP 1
        //Zeon hangar.
        pos = new Posisjon(56952509, -54444422, 2700, 25500, 1);
        ItemHandler.spawnItem(new Camp(0x5302A), pos);
        //Zeon weapons store.
        pos = new Posisjon(56952818, -54444351, 2700, -27000, 1);
        ItemHandler.spawnItem(new Camp(0x53023), pos);
        //ZEON machine store
        pos = new Posisjon(56952728, -54444407, 2700, -27000, 1);
        ItemHandler.spawnItem(new Camp(0x5302D), pos);
        //ZEON bank
        pos = new Posisjon(56952132, -54444184, 2700, 17500, 1);
        ItemHandler.spawnItem(new Camp(0x5302B), pos);
        //ZEON repair
        pos = new Posisjon(56952071, -54444000, 2700, 31000, 1);
        ItemHandler.spawnItem(new Camp(0x53022), pos);

        //ZEON NEWMAN CAMP 2
        //Zeon hangar.
        pos = new Posisjon(56951951, -54451462, 2700, 26300, 1);
        ItemHandler.spawnItem(new Camp(0x5302A), pos);
        //Zeon weapons store.
        pos = new Posisjon(56951342, -54451763, 2700, 24000, 1);
        ItemHandler.spawnItem(new Camp(0x53023), pos);
        //ZEON machine store
        pos = new Posisjon(56951407, -54451901, 2700, 24000, 1);
        ItemHandler.spawnItem(new Camp(0x5302D), pos);
        //ZEON bank
        pos = new Posisjon(56951430, -54451543, 2700, -3000, 1);
        ItemHandler.spawnItem(new Camp(0x5302B), pos);
        //ZEON repair
        pos = new Posisjon(56951964, -54452033, 2700, 13000, 1);
        ItemHandler.spawnItem(new Camp(0x53022), pos);

        //EF RICHMOND CAMP 1
        //EF hangar.
        pos = new Posisjon(68951801, -52883913, 1759, 2000, 1);
        ItemHandler.spawnItem(new Camp(0x53025), pos);
        //EF weapons store.
        pos = new Posisjon(68951857, -52884208, 1759, 1500, 1);
        ItemHandler.spawnItem(new Camp(0x53021), pos);
        //EF Machine store.
        pos = new Posisjon(68951892, -52884332, 1759, 1500, 1);
        ItemHandler.spawnItem(new Camp(0x53028), pos);
        //EF bank.
        pos = new Posisjon(68952311, -52884213, 1759, 9200, 1);
        ItemHandler.spawnItem(new Camp(0x53026), pos);
        //EF repair.
        pos = new Posisjon(68952391, -52884116, 1759, 9200, 1);
        ItemHandler.spawnItem(new Camp(0x53020), pos);

        //EF RICHMOND CAMP 2
        //EF hangar.
        pos = new Posisjon(68943223, -52884257, 1759, 17000, 1);
        ItemHandler.spawnItem(new Camp(0x53025), pos);
        //EF weapons store.
        pos = new Posisjon(68943299, -52884494, 1759, 1100, 1);
        ItemHandler.spawnItem(new Camp(0x53021), pos);
        //EF Machine store.
        pos = new Posisjon(68943395, -52884442, 1759, 1100, 1);
        ItemHandler.spawnItem(new Camp(0x53028), pos);
        //EF bank.
        pos = new Posisjon(68943666, -52884392, 1759, 13500, 1);
        ItemHandler.spawnItem(new Camp(0x53026), pos);
        //EF repair.
        pos = new Posisjon(68943645, -52884311, 1759, 13400, 1);
        ItemHandler.spawnItem(new Camp(0x53020), pos);

        //ZEON RICHMOND CAMP 1
        //Zeon hangar.
        pos = new Posisjon(68911657, -52843868, 1960, 26300, 1);
        ItemHandler.spawnItem(new Camp(0x5302A), pos);
        //Zeon weapons store.
        pos = new Posisjon(68911757, -52843768, 1960, 24000, 1);
        ItemHandler.spawnItem(new Camp(0x53023), pos);
        //ZEON machine store
        pos = new Posisjon(68911757, -52843718, 1960, 24000, 1);
        ItemHandler.spawnItem(new Camp(0x5302D), pos);
        //ZEON bank
        pos = new Posisjon(68911721, -52844059, 1960, -6200, 1);
        ItemHandler.spawnItem(new Camp(0x5302B), pos);
        //ZEON repair
        pos = new Posisjon(68911785, -52844031, 1960, -6200, 1);
        ItemHandler.spawnItem(new Camp(0x53022), pos);

        //ZEON RICHMOND CAMP 2
        //Zeon hangar.
        pos = new Posisjon(68911634, -52851436, 1960, 5600, 1);
        ItemHandler.spawnItem(new Camp(0x5302A), pos);
        //Zeon weapons store.
        pos = new Posisjon(68912041, -52851332, 1960, -19200, 1);
        ItemHandler.spawnItem(new Camp(0x53023), pos);
        //ZEON machine store
        pos = new Posisjon(68911938, -52851311, 1960, -19200, 1);
        ItemHandler.spawnItem(new Camp(0x5302D), pos);
        //ZEON bank
        pos = new Posisjon(68911758, -52851815, 1960, -23000, 1);
        ItemHandler.spawnItem(new Camp(0x5302B), pos);
        //ZEON repair
        pos = new Posisjon(68911840, -52851878, 1960, -23000, 1);
        ItemHandler.spawnItem(new Camp(0x53022), pos);

    }

    /**
     * Spawner diverse pynte ting.
     */
    private static void spawnStuff() {
        Posisjon pos;

        pos = new Posisjon(72534916, -59309330, 349, -31372, 1);
        ItemHandler.spawnItem(new GeneralItem(560334), pos); //Tre utenfor Sydney.			
    }
}
