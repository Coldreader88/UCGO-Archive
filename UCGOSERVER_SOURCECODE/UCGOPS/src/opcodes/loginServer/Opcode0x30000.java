package opcodes.loginServer;

import java.nio.charset.Charset;
import java.util.Date;
import opcodes.*;
import packetHandler.*;
import players.Player;
import userDB.*;

public class Opcode0x30000 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        //Hent ut antall tegn i brukernavnet.
        int brukernavnsize = (int) pd.readByte() & 0xFF;

        //Sjekk at det er gyldig.
        if (brukernavnsize <= 0x80) {
            System.out.println("Opcode 0x30000: Invalid username size.");
            return;
        }

        String brukernavn = pd.readStringUTF16LE(brukernavnsize & 0x7F);

        //Sjekk at brukernavnet ble lest inn.
        if (brukernavn == null) {
            System.out.println("Opcode 0x30000: Username too short, invalid packet.");
            return;
        }

        int client_version = pd.readIntBE();
        if (!this.validateClient(p, client_version)) {
            return;
        }

        //Hent ut størrelsen på passordet.
        int passordsize = (int) pd.readByte() & 0xFF;

        //Sjekk at det er gyldig.
        if (passordsize <= 0x80) {
            System.out.println("Opcode 0x30000: Invalid password size.");
            return;
        }

        byte[] passordBF = pd.readByteArray(passordsize & 0x7F);

        //Sjekk at passordet, kryptert i blowfish, ble lest inn.
        if (passordBF == null) {
            System.out.println("Opcode 0x30000: Password too short, invalid packet.");
            return;
        }

        //Hent ut passordet for Blowfish krypteringen.
        byte[] bf_key_temp = brukernavn.getBytes(Charset.forName("UTF-16LE"));
        byte[] bf_key = new byte[bf_key_temp.length + 2]; //+2 for NULL terminator.
        for (int c = 0; c < bf_key_temp.length; c++) {
            bf_key[c] = bf_key_temp[c];
        }
        //bf_key inneholder nå brukernavnet + null terminator.

        UCGOblowfish bf = new UCGOblowfish(bf_key);
        passordBF = bf.Decrypt(passordBF);

        String passord = new String(passordBF, Charset.forName("UTF-16LE"));

        brukernavn = brukernavn.trim();
        passord = passord.trim();
        //brukernavn og passord inneholder nå login info.
        //Sjekk dem mot databasen.

        int accountID = userDB.ManageAccounts.authenticateUser(brukernavn, passord);
//KUN FOR TEST SERVER.
//**************************
        //LAG NY KONTO HVIS BRUKERNAVN IKKE FINNES.
        if (accountID == 0) {
            accountID = this.nyKonto(brukernavn, passord);
        }

//**************************
        //Avhengig av resultatet send svar tilbake.
        PacketData svar = new PacketData();

        if (accountID > 0) {
            //Login godkjent.
            Account konto = userDB.ManageAccounts.getAccount(accountID);

            //Sjekk at konto ikke er stengt eller criminal merket.
            if (konto.isLocked() || konto.isCriminal()) {
                //Konto er stengt/criminal, bare koble i fra og ikke send noe melding tilbake.
                return;
            }

            //Sett last login tid for konto.
            int tid = (int) ((new Date()).getTime() / 1000);
            konto.setLastLogin(tid);

            p.setAccountID(konto.getAccountID());
            p.setUCGM(konto.getUcgmTag());

            //Sjekk om server er stengt for andre spillere enn UCGMs.
            if (config.Server.server_is_closed && !p.isUCGM()) {
                //Server er stengt og spiller er ikke UCGM.
                svar.writeIntBE(0xB); //Maintenance error
                svar.writeIntBE(0xFFFFFFFF);
                svar.writeIntBE(0xFFFFFFFF);
                svar.writeIntBE(0x0);

            }//Sjekk om spiller allerede er logget inn.
            else if (serverComp.PlayerHandlerStatic.getPlayerGame(accountID) != null) {
                //Spiller fremdeles logget inn på game server, send feil tilbake.
                svar.writeIntBE(0x15);
                svar.writeIntBE(0xFFFFFFFF);
                svar.writeIntBE(0xFFFFFFFF);
                svar.writeIntBE(0x0);

            } else {
                //Login OK!
                svar.writeIntBE(0x1);
                svar.writeIntBE(0x12345678); //Security token
                svar.writeIntBE(accountID);
                svar.writeIntBE(p.getUCGM()); //GM tag.

                p.setLoginReceived(); //Sett at spilleren har sendt gyldig login info.

                //Skriv log melding.
                String msg = "Logged in: Account ID:" + p.getAccountID()
                        + " IP:" + p.getIP() + " Username:" + brukernavn;
                admin.logging.loginserverMsg(msg);
            }
        } else {
            //Login ikke godkjent.

            svar.writeIntBE(0x9);
            svar.writeIntBE(0xFFFFFFFF);
            svar.writeIntBE(0xFFFFFFFF);
            svar.writeIntBE(0x0);

            //Skriv log melding.
            String msg = "Invalid username/password: Account ID:" + p.getAccountID()
                    + " IP:" + p.getIP() + " Username:" + brukernavn;
            admin.logging.loginserverMsg(msg);
        }

        Packet svarpakke = new Packet(0x38000, svar.getData());

        p.sendPacket(svarpakke);
    }

    /**
     * Sjekker at riktig versjon av klienten er brukt. Hvis feil versjon sendes
     * en melding tilbake til klienten.
     *
     * @param p Spilleren
     * @param client_version Klient versjon
     *
     * @return true hvis rett versjon, false hvis ikke.
     */
    private boolean validateClient(Player p, int client_version) {

        if (client_version != config.Server.client_version) {

            PacketData svar = new PacketData();
            svar.writeIntBE(0xC);
            svar.writeIntBE(0xFFFFFFFF);
            svar.writeIntBE(0xFFFFFFFF);
            svar.writeIntBE(0x0);
            Packet svarpakke = new Packet(0x38000, svar.getData());

            p.sendPacket(svarpakke);

            return false;
        }

        return true;
    }

    /**
     * KUN FOR TEST SERVER.
     *
     * Oppretter ny konto med oppgitt brukernavn og navn.
     *
     * @return Account ID for konto.
     */
    private int nyKonto(String brukernavn, String passord) {

        Account konto = userDB.ManageAccounts.newAccount(brukernavn, passord);

        Date d = new Date();
        konto.setCreateTime((int) (d.getTime() / 1000));
        konto.setLastLogin(konto.getCreateTime());
        konto.setUcgmTag(0xA);

        return konto.getAccountID();
    }

}
