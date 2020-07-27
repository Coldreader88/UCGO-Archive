package gameServer;

import characters.*;
import items.*;
import java.util.Random;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å regne ut resultatet når ER/MR kits brukes.
 */
public class Repair {

    /**
     * Beregner resultatet når character bruker et ER kit. Det antaes at
     * character er i en MS/vehicle.
     *
     * @param spiller Character som bruker ER kit.
     *
     * @param lvl Hvilket ER kit Level som brukes 1-4.
     *
     * @return RepairResult objekt som forteller hvordan det gikk.
     */
    public static RepairResult ERkit(CharacterGame spiller, int lvl) {

        RepairResult res = new RepairResult();

        //Hent ut ER skill level.
        int x = spiller.getSkills().getCombatSkill(SkillList.EmergencyRepair.key());

        //Beregn sjanse for å lykkes.
        int sl = (int) ((double) 130 * Math.sqrt((double) x) / Math.sqrt(1300));

        //Sjanse for å mislykkes.
        int sm = 15 * lvl;

        //Avgjør om det ble mislykket eller ikke.
        int n = new Random().nextInt(sm + sl);

        if (n < sm) {
            res.setRepairOK(false);
        } else {
            res.setRepairOK(true);
        }

        //Hvis ER ble OK regn ut hvor mye som ble reparert.
        if (res.isRepairOK()) {

            //Antall prosent som blir reparert.
            double prosent = 10 * lvl + Math.random() * 5;

            Vehicle v = spiller.getVehicle();

            res.setRepairHitpoints((int) (v.getMaxHitpoints() * prosent / 100));
        }

        //ER skill
        SkillTraining.trainEmergencyRepair(spiller, lvl);

        return res;
    }

}
