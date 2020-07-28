package npc;

import characters.*;
import items.Weapon;

/**
 * Denne klassen representerer et mål en NPC kan angripe. Den kan representere
 * både en spiller samt en annen NPC som et mål.
 *
 * @author UCGOSERVER
 */
public class NPCtarget {

    /**
     * Eventuell NPC som er målet vårt.
     */
    private NPCvehicle npcTarget;
    /**
     * Eventuell spiller som er målet vårt.
     */
    private CharacterGame humanTarget;
    /**
     * Eventuelt X,Y,Z offset
     */
    private int offsetX, offsetY, offsetZ;

    /**
     * Oppretter et nytt mål som er en NPC.
     *
     * @param npcTarget
     */
    public NPCtarget(NPCvehicle npcTarget) {
        this.npcTarget = npcTarget;
    }

    /**
     * Oppretter et nytt mål som er en spiller.
     *
     * @param humanTarget
     */
    public NPCtarget(CharacterGame humanTarget) {
        this.humanTarget = humanTarget;
    }

    /**
     *
     * @return Character ID eller NPC ID.
     */
    public int getID() {
        if (humanTarget != null) {
            return humanTarget.getCharacterID();
        } else if (npcTarget != null) {
            return npcTarget.getID();
        } else {
            return -1;
        }
    }

    public Posisjon getPosisjon() {
        if (humanTarget != null) {
            return humanTarget.getPosisjon();
        } else if (npcTarget != null) {
            return npcTarget.getPosisjon();
        } else {
            return null;
        }
    }

    /**
     * Sjekker om dette målet fremdeles er gyldig dvs at spiller er tilkoblet
     * server og er i et vehicle eller at NPC fremdeles "lever".
     *
     * @return TRUE dersom målet er gyldig, FALSE hvis ikke.
     */
    public boolean isValidTarget() {
        if (humanTarget != null) {
            if (humanTarget.getVehicle() != null && gameServer.MultiClient.getCharacter(humanTarget.getCharacterID()) != null) {
                return true;
            } else {
                return false;
            }
        } else if (npcTarget != null) {
            return npcTarget.isActive();
        } else {
            return false;
        }
    }

    /**
     * Påfører skade på målet sitt shield.
     *
     * @param hitpoints Antall hitpoints i skade.
     */
    public void applyShieldDamage(int hitpoints) {
        if (humanTarget != null) {
            Weapon shield = (Weapon) humanTarget.getVehicle().getEquippedItem(1).getItem();
            shield.applyDamage(hitpoints);
        } else if (npcTarget != null) {
            npcTarget.applyShieldDamage(hitpoints);
        }
    }

    /**
     * Påfører skade på målet.
     *
     * @param hitpoints Antall hitpints i skade.
     */
    public void applyDamage(int hitpoints) {
        if (humanTarget != null) {
            humanTarget.getVehicle().applyDamage(hitpoints);
        } else if (npcTarget != null) {
            npcTarget.applyDamage(hitpoints);
        }
    }

    /**
     *
     * @return Machinedamage for målet.
     */
    public byte getMachineDamage() {
        if (humanTarget != null) {
            return humanTarget.getVehicle().getMachineDamage();
        } else if (npcTarget != null) {
            return npcTarget.getMachineDamage();
        } else {
            return (byte) -1;
        }
    }

    /**
     *
     * @return Hitpoints til målets shield dersom spiller eller shield damage
     * (0-100) for NPC.
     */
    public int getShieldHitpoints() {
        if (humanTarget != null) {
            Weapon shield = (Weapon) humanTarget.getVehicle().getEquippedItem(1).getItem();
            return shield.getHitpoints();
        } else if (npcTarget != null) {
            return npcTarget.getShieldDamage();
        } else {
            return -1;
        }
    }

    public NPCvehicle getNpcTarget() {
        return npcTarget;
    }

    public CharacterGame getHumanTarget() {
        return humanTarget;
    }

    public int getFaction() {
        if (humanTarget != null) {
            return humanTarget.getFaction();
        } else if (npcTarget != null) {
            return npcTarget.getFaction();
        } else {
            return -1;
        }
    }

    public boolean isCriminal() {
        if (humanTarget != null) {
            return humanTarget.isCriminal();
        } else {
            return false;
        }
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getOffsetZ() {
        return offsetZ;
    }

    public void setOffsetZ(int offsetZ) {
        this.offsetZ = offsetZ;
    }

    public void addAttacker() {
        if (npcTarget != null) {
            npcTarget.addAttacker();
        }
    }

    public void removeAttacker() {
        if (npcTarget != null) {
            npcTarget.removeAttacker();
        }
    }

    public int getNumberOfAttackers() {
        if (npcTarget != null) {
            return npcTarget.getNumberOfAttackers();
        } else {
            return 0;
        }
    }
}
