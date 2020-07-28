package opcodes.loginServer;

import characters.*;
import java.util.Date;
import opcodes.Opcode;
import packetHandler.*;
import players.Player;
import userDB.*;

public class Opcode0x30003 implements Opcode {

    public void execute(Player p, Packet pakke) {

        //Data fra pakken lagres her.
        int gender, faction, hair_color, hair_style, face_type, skin_color;
        String navn;
        int start_posisjon;
        int strength, spirit, luck;

        PacketData pd = new PacketData(pakke.getData());

        //Sjekk at account id i pakken stemmer med registrert account id.
        if (p.getAccountID() != pd.readIntBE()) {
            //Account ID i pakken stemmer ikke med registret account id.
            System.out.println("Opcode 0x30003: Invalid account ID.");
            return;
        }

        pd.skipAhead(14); //Hopp over ubrukelig data.

        gender = pd.readByte();

        pd.readByte(); //Ignorer.

        faction = pd.readByte();

        pd.skipAhead(20);

        face_type = pd.readByte();

        pd.readByte(); //Ignorer.

        hair_style = pd.readByte();

        pd.skipAhead(26);

        skin_color = pd.readByte();

        pd.readByte();

        hair_color = pd.readByte();

        pd.skipAhead(9);

        int q = pd.readByte(); //Antall tegn i navnet.

        //Sjekk at det er minst 1 tegn.
        if ((int) (q & 0xFF) <= 0x80) {
            System.out.println("Opcode 0x30003: Illegal character name.");
            return;
        }

        navn = pd.readStringUTF16LE((int) (q & 0x7F));

        pd.readByte();

        start_posisjon = pd.readByte();

        pd.readIntBE();

        //Neste byte SKAL være 0x83.
        if ((int) (pd.readByte() & 0xFF) != 0x83) {
            System.out.println("Opcode 0x30003: Invalid packet format.");
            return;
        }

        strength = pd.readIntBE();
        spirit = pd.readIntBE();
        luck = pd.readIntBE();

        int sum = pd.readIntBE();
        //Sjekk at strength,spirit og luck stemmer.
        if (sum != (strength + spirit + luck) || sum != 170) {
            System.out.println("Strength = " + strength + " Spirit = " + spirit + " Luck = " + luck);
            System.out.println("Sum = " + sum);
            System.out.println("Opcode 0x30003: Invalid data in packet. IP:" + p.getIP());
            return;
        }

        //Alle data er hentet ut fra pakken.
        //Opprett ny character.
        GameCharacter character = ManageCharacters.newGameCharacter(p.getAccountID());

        //Sjekk at ny character ble opprettet og lagre data.
        if (character != null) {

            //Opprett ny character.
            int time = (int) (new Date().getTime() / 1000);

            character.setNavn(navn);
            character.setGender(gender);
            character.setFaction(faction);
            character.setCreateTime(time);

            Appearance utseende = new Appearance(hair_color, hair_style, face_type, skin_color);
            character.setAppearance(utseende);

            //Sett start posisjon.
            Posisjon pos;
            switch (start_posisjon) {

                case 0: //Sydney
                    pos = new Posisjon(72536537, -59308704, 311, -1518, 1);
                    break;
                case 1: //Perth
                    pos = new Posisjon(55469796, -58348671, 39, 15487, 1);
                    break;
                case 2: //Canberra
                    pos = new Posisjon(71572874, -60098021, 2435, -17575, 1);
                    break;
                case 3: //Adelaide
                    pos = new Posisjon(66390755, -59786294, 35, -14090, 1);
                    break;
                case 4: //Melbourne
                    pos = new Posisjon(69404973, -61194815, 1899, 1714, 1);
                    break;
                case 5: //Darwin
                    pos = new Posisjon(62637658, -49079842, 39, 4291, 1);
                    break;
                case 6: //Brisbane
                    pos = new Posisjon(73447582, -56285669, 200, 0, 1);
                    break;
                case 7: //Southern cross
                    pos = new Posisjon(57289989, -58213606, 1750, -25778, 1);
                    break;
                default: //Ødemarken.
                    pos = new Posisjon(66666666, -54000000, 1000, 0, 1);
            }

            character.setPosition(pos);

            //Lagre skills for character.
            SkillManager skill = new SkillManager();
            skill.setCharacterAttribute(SkillList.Strength.key(), strength);
            skill.setCharacterAttribute(SkillList.Spirit.key(), spirit);
            skill.setCharacterAttribute(SkillList.Luck.key(), luck);

            character.setSkills(skill);

            character.setFriends(new FriendList()); //Opprett tom friendlist.

            //OK! Send svar tilbake til klient.
            PacketData svar = new PacketData();

            svar.writeIntBE(0x040002);
            svar.writeLongBE(0x0L);
            svar.writeIntBE(character.getCharacterID());
            svar.writeLongBE(0x0L);
            svar.writeIntBE(0x0);

            Packet svar_pakke = new Packet(0x38003, svar.getData());
            p.sendPacket(svar_pakke);
        } else {
            //Ny character ble ikke opprettet, send feilmelding.
            PacketData svar = new PacketData();

            svar.writeIntBE(-1);
            svar.writeLongBE(-1L);
            svar.writeIntBE(-1);
            svar.writeLongBE(-1);
            svar.writeIntBE(-1);

            Packet svar_pakke = new Packet(0x38003, svar.getData());
            p.sendPacket(svar_pakke);
        }

    }

}
