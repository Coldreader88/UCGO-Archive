package opcodes.loginServer;

import java.util.Iterator;
import opcodes.*;
import packetHandler.*;
import players.Player;
import userDB.*;

public class Opcode0x30001 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        pd.readIntBE(); //Ukjent int.

        //Sjekk at account id i pakken stemmer med registrert account id.
        if (p.getAccountID() != pd.readIntBE()) {
            //Account ID i pakken stemmer ikke med registret account id.
            System.out.println("Opcode 0x30001: Invalid account ID.");
            return;
        }

        //Hent ut eventuelle characters.
        int[] chars = this.getCharacterList(p.getAccountID());

        //Svar tilbake til klient.
        PacketData svar = new PacketData();

        svar.writeIntBE(p.getAccountID());
        svar.writeByte((byte) 0x0);

        //Tell antall characters.
        int antall_chars = 0;
        if (chars[0] != 0) {
            antall_chars++;
        }
        if (chars[1] != 0) {
            antall_chars++;
        }

        svar.writeByte((byte) (antall_chars | 0x80)); //Skriv antall characters.

        //Skriv eventuelle data for hver character.
        for (int c = 0; c < antall_chars; c++) {

            svar.writeIntBE(p.getAccountID());
            svar.writeIntBE(chars[c]);
            svar.writeIntBE(0xFFFFFFFF);
            svar.writeIntBE(0xFFFFFFFF);
        }

        Packet svar_pakke = new Packet(0x38001, svar.getData());

        p.sendPacket(svar_pakke);

    }

    /**
     * @param account_id Account ID for bruker.
     *
     * @return Int array bestående av TO elementer, hvert element inneholder
     * character ID for hver av de to mulige charactes en bruker kan ha. Hvis en
     * character ikke finnes er character ID satt til 0.
     */
    private int[] getCharacterList(int account_id) {

        int[] characters = new int[2];

        //Gå gjennom alle lagrede characters og sjekk om noen av dem tilhører oppgitt konto ID.
        Iterator<GameCharacter> chars = ManageCharacters.getAllGameCharacters();

        while (chars.hasNext()) {
            GameCharacter c = chars.next();

            if (c.getKonto() != null && c.getKonto().getAccountID() == account_id) {
                //Vi har funnet en character som tilhører oppgitt konto.
                //Sjekk om den skal i [0] eller [1].
                if (characters[0] == 0) {
                    characters[0] = c.getCharacterID();
                } else if (characters[1] == 0) {
                    characters[1] = c.getCharacterID();
                } else {
                    //Mer enn 2 characters for konto?
                    System.out.println("Opcode 0x30001.java Error: Account has more than two characters, Account ID:" + account_id);
                }
            } else if (c.getKonto() == null) {
                //Feil, konto mangler.
                System.out.println("Opcode 0x30001.java Error: Character does not belong to account, ID:" + c.getCharacterID());
            }

        }

        return characters;
    }

}
