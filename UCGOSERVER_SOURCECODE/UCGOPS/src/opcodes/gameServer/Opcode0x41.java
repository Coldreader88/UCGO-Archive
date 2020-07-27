package opcodes.gameServer;

import characters.*;
import containers.*;
import items.*;
import java.util.Iterator;
import opcodes.*;
import packetHandler.*;
import players.*;
import userDB.*;
import validItems.*;

public class Opcode0x41 implements Opcode {

    public void execute(Player p, Packet pakke) {

        PacketData pd = new PacketData(pakke.getData());

        if (pd.readIntBE() != 0x10000) {
            return;
        }

        int character_id = pd.readIntBE();

        pd.readIntBE();

        //Resten av dataene er unødvendige å hente ut.
        //Hent ut account ID til spiller.
        int account_id = this.getAccountID(character_id);

        //Sjekk at spiller ikke allerede er tilkoblet server.
        if (serverComp.PlayerHandlerStatic.getPlayerGame(account_id) != null) {
            //Spiller er allerede tilkoblet, send feil tilbake.
            this.loginFeil(p);
            return;
        }

        //Hent ut lagrede data for character.
        CharacterGame character = this.loadCharacter(character_id, account_id);

        if (character != null) {
            //Sjekk om hovedcontainere for character allerede er opprettet eller om nye containere må opprettes.
            //Sjekk om weared container finnes fra før.
            Container w = ContainerList.getContainer(character.getCharacterID() + 2);
            if (w instanceof HovedContainer) {
                //Weared eksisterte. Kan anta at character ikke er ny og containere må bare linkes til.
                this.linkTilContainere(character);
                this.sjekkTradeContainer(character);
                this.containerCleanUp(character);
            } else {
                //Weared eksisterte ikke. Kan anta at character er ny og containere må opprettes.
                this.opprettNyeContainere(character);

                this.nyCharacter(character);
            }

            //Les inn fra weared container og finn ut hva spilleren har på seg.
            characters.Clothing k = new characters.Clothing(character);
            k.restoreWearedContainer();
            character.setClothing(k);

            //Sjekk om spiller skal begynne i ms/vehicle eller ikke.
            int vehicle = ManageCharacters.getGameCharacter(character_id).getActiveVehicle();

            //Hent ut oppgitt itemcontainer og sjekk at den er gyldig.
            Container con = ContainerList.getContainer(vehicle);

            ItemContainer ic = null;
            if (con instanceof ItemContainer) {
                ic = (ItemContainer) con;
            }

            if (vehicle == 0 || ic == null) {
                //Humanform.		
            } else {
                //MS/Vehicle.
                if (ic.getItem() instanceof Vehicle) {
                    character.setVehicle((Vehicle) ic.getItem(), ic);
                    character.getVehicle().restoreWeaponsRoom();
                } else if (ic.getItem() instanceof TaxiTransport) {
                    //Oppdater kun hvis vi er i den space fly tingen.
                    if (ic.getItem().getID() == 0x61A93 || ic.getItem().getID() == 0x61A94) {
                        character.setTaxiTransport((TaxiTransport) ic.getItem(), ic);
                    }
                }
            }

        }

        //Sjekk at character ble hentet fra databasen.
        if (character != null) {

            p.setAccountID(account_id);
            p.setUCGM(ManageAccounts.getAccount(account_id).getUcgmTag());

            PlayerGame player = (PlayerGame) p;
            player.setCharacter(character);

            //Dersom character er UCGM gjør han usynlig.
            if (player.getUCGM() == 0x3 || player.getUCGM() == 0x4 || player.getUCGM() == 0x5 || player.getUCGM() == 0x9) {
                player.getCharacter().setVisible(false);
            }

            //Registrer character i multiclient.
            gameServer.MultiClient.registerCharacter(player);

            //Send svar tilbake.
            PacketData svar = new PacketData();

            svar.writeIntBE(0x00100002);
            svar.writeByteMultiple((byte) 0, 24);

            Packet svar_pakke = new Packet(0x8041, svar.getData());

            p.sendPacket(svar_pakke);

            //Sett at spiller er logget inn.
            p.setLoginReceived();

            //Skriv log melding.
            String msg = "Logged in: Character ID:" + player.getCharacter().getCharacterID()
                    + " IP:" + player.getIP() + " Name:" + player.getCharacter().getNavn();
            admin.logging.gameserverMsg(msg);

        } else {
            //Det var feil ved henting av character fra databasen.
            //Send feil tilbake.
            PacketData svar = new PacketData();

            svar.writeIntBE(-1);
            Packet svar_pakke = new Packet(0x8041, svar.getData());

            p.sendPacket(svar_pakke);

            //Skriv log melding.
            String msg = "Login error: Account ID:" + p.getAccountID()
                    + " IP:" + p.getIP() + " Supplied Character ID:" + character_id;
            admin.logging.gameserverMsg(msg);
        }

    }

    /**
     * Oppretter nye hovedcontainere for en character.
     *
     * Kalles bare dersom character er ny og ikke har containere allerede.
     *
     * @param character Character vi skal opprette containere for.
     */
    private void opprettNyeContainere(CharacterGame character) {

        character.setBackpackContainer(new HovedContainer(character.getCharacterID() + 1));
        character.setWearedContainer(new HovedContainer(character.getCharacterID() + 2));
        character.setBankContainer(new HovedContainer(character.getCharacterID() + 3));
        character.setMoneyContainer(new HovedContainer(character.getCharacterID() + 4));
        character.setHangarContainer(new HovedContainer(character.getCharacterID() + 5));
        character.setSelfstorageContainer(new HovedContainer(character.getCharacterID() + 6));
        character.setProductiveContainer(new HovedContainer(character.getCharacterID() + 8));
        character.setSwappackContainer(new HovedContainer(character.getCharacterID() + 0xA));
        character.setCreditContainer(new HovedContainer(character.getCharacterID() + 0xB));

        //Sett container tail for money og credit.
        character.getMoneyContainer().settContainerTail(0x13);
        character.getCreditContainer().settContainerTail(0x13);

        //Sett statisk ID for alle containere.
        character.getBackpackContainer().settStatiskID(0x0001ADB1);
        character.getWearedContainer().settStatiskID(0x0001D4C1);
        character.getBankContainer().settStatiskID(0x0001ADB2);
        character.getMoneyContainer().settStatiskID(0x0007A120);
        character.getHangarContainer().settStatiskID(0x0001ADB3);
        character.getSelfstorageContainer().settStatiskID(0x0001ADBA);
        character.getProductiveContainer().settStatiskID(0x0001ADB6);
        character.getSwappackContainer().settStatiskID(0x0001ADB4);
        character.getCreditContainer().settStatiskID(0x0007A120);

        //Registrer containere i container listen.
        ContainerList.addContainer(character.getBackpackContainer());
        ContainerList.addContainer(character.getWearedContainer());
        ContainerList.addContainer(character.getBankContainer());
        ContainerList.addContainer(character.getMoneyContainer());
        ContainerList.addContainer(character.getHangarContainer());
        ContainerList.addContainer(character.getSelfstorageContainer());
        ContainerList.addContainer(character.getProductiveContainer());
        ContainerList.addContainer(character.getSwappackContainer());
        ContainerList.addContainer(character.getCreditContainer());

        //SWAPPACK SKAL INNEHOLDE EN HOVEDCONTAINER SOM ER TRADE VINDUET.
        HovedContainer swap_hc = new HovedContainer(character.getCharacterID() + 0xC);
        swap_hc.settStatiskID(0x1ADB5);
        character.getSwappackContainer().addHovedContainer(swap_hc);
        //Registrer trade vinduet i container listen.
        ContainerList.addContainer(swap_hc);
    }

    /**
     * Denne metoden kalles for nye characters og gir character ting som penger,
     * MS i hangar og rett uniform.
     *
     * @param character Character som er ny.
     */
    private void nyCharacter(CharacterGame character) {

        //Legg til rett uniform i weared container.
        items.Clothing c;
        if (character.getFaction() == 1) {
            c = new items.Clothing(0x3A986, 0); //EF SR uniform.
        } else {
            c = new items.Clothing(0x3A996, 0); //Zeon SR uniform.
        }
        //Første ItemContainer i weared må være tom, tilsvarer MS/vehicle character starter i.
        ItemContainer dummy_ic = new ItemContainer(0x0);
        dummy_ic.settContainerTail(0x0);
        character.getWearedContainer().addItemContainer(dummy_ic);
        //Legg uniformen i weared container.
        ItemContainer ic = ContainerList.newItemContainer();
        ic.addItem(c);
        character.getWearedContainer().addItemContainer(ic);

        //Character får 1000000 i banken og null i backpack.
        character.getCreditContainer().settAntall(1000000);
        character.getMoneyContainer().settAntall(0);

        //Basert på faction gi character 2stk GM Trainer eller Zaku1 med TR1C.
        Vehicle v;

        for (int i = 0; i < 2; i++) {

            if (character.getFaction() == 1) {
                v = new Vehicle(0x641BA, 0x046CDC);
            } else {
                v = new Vehicle(0x64191, 0x046CDC);
            }

            ItemContainer msIC = ContainerList.newItemContainer();
            msIC.addItem(v);
            character.getHangarContainer().addItemContainer(msIC);
        }

        //Gi character 50stk L1 ER kits.
        GeneralItem er;

        if (character.getFaction() == 1) {
            er = new GeneralItem(0x4BAF6);
        } else {
            er = new GeneralItem(0x4BAF9);
        }

        ItemContainer erIC = ContainerList.newItemContainer();
        erIC.addItem(er);
        erIC.settAntall(50);
        character.getBankContainer().addItemContainer(erIC);

    }

    /**
     * Dette er for characters som ikke er nye.
     *
     * Hovedcontainere skal allerede eksistere i minnet, opprett linker til dem.
     *
     * @param character Character hvis containere vi skal finne.
     */
    private void linkTilContainere(CharacterGame character) {

        character.setBackpackContainer((HovedContainer) ContainerList.getContainer(character.getCharacterID() + 1));
        character.setWearedContainer((HovedContainer) ContainerList.getContainer(character.getCharacterID() + 2));
        character.setBankContainer((HovedContainer) ContainerList.getContainer(character.getCharacterID() + 3));
        character.setMoneyContainer((HovedContainer) ContainerList.getContainer(character.getCharacterID() + 4));
        character.setHangarContainer((HovedContainer) ContainerList.getContainer(character.getCharacterID() + 5));
        character.setSelfstorageContainer((HovedContainer) ContainerList.getContainer(character.getCharacterID() + 6));
        character.setProductiveContainer((HovedContainer) ContainerList.getContainer(character.getCharacterID() + 8));
        character.setSwappackContainer((HovedContainer) ContainerList.getContainer(character.getCharacterID() + 0xA));
        character.setCreditContainer((HovedContainer) ContainerList.getContainer(character.getCharacterID() + 0xB));
    }

    /**
     * Går gjennom trade container og sjekker om den inneholder noe. Eventuelle
     * items vil bli flyttet til hangar eller bank.
     */
    private void sjekkTradeContainer(CharacterGame character) {

        HovedContainer trade_hc = (HovedContainer) ContainerList.getContainer(character.getCharacterID() + 0xC);

        Iterator<ItemContainer> ics = trade_hc.getContents().iterator();

        while (ics.hasNext()) {

            ItemContainer ic = ics.next();
            GameItem g = ItemList.getItem(ic.getItem().getID());

            if (g != null) {
                //Gyldig item. Finn ut hvilken type det er og flytt til hangar/bank.
                if (g.getItemType() == ItemType.Vehicle) {
                    character.getHangarContainer().addItemContainer(ic);
                } else if (g.getItemType() == ItemType.Weapon || g.getItemSubType() == ItemSubType.Material
                        || g.getItemType() == ItemType.Clothes) {
                    character.getBankContainer().addItemContainer(ic);
                } else if (g.getItemSubType() == ItemSubType.Money) {
                    //Legg til penge summen.
                    character.getCreditContainer().settAntall(character.getCreditContainer().getAntall() + ic.getAntall());
                }

            }
        }

        trade_hc.emptyContainer();
    }

    /**
     * Utfører diverse clean-up ting på containere.
     *
     * @param chara Character hvis containere skal sjekkes.
     */
    private void containerCleanUp(CharacterGame chara) {

        //Sjekk at hangar container kun inneholder gyldige MS/vehicles.
        Iterator<ItemContainer> hangar = chara.getHangarContainer().getContents().iterator();

        while (hangar.hasNext()) {

            ItemContainer ic = hangar.next();

            if (ic.getItem() instanceof Vehicle) {

                //Sjekk at MS/vehicle er gyldig.
                Vehicle v = (Vehicle) ic.getItem();

                if (v.getHitpoints() <= 0) {
                    chara.getHangarContainer().removeItemContainer(ic);
                }

                if (ic.getAntall() != 1) {
                    chara.getHangarContainer().removeItemContainer(ic);
                    System.out.println("Opcode 0x41: Error, invalid item count for vehicle in hangar.");
                }

            } else {
                //Item er ikke MS/vehicle, fjern den.
                chara.getHangarContainer().removeItemContainer(ic);
                System.out.println("Opcode 0x41: Error, invalid item in hangar.");
            }

        }

        //Ved innføring av Raketen Bazooka ble HG vulcans (head og chest) fjernet fra spillet.
        //Sjekk om spiller har dette i banken og fjern dem.
        ItemContainer ic;
        for (ic = chara.getBankContainer().getItemContainer(0x44628); ic != null; ic = chara.getBankContainer().getItemContainer(0x44628)) {
            chara.getBankContainer().removeItemContainer(ic);
        }

        for (ic = chara.getBankContainer().getItemContainer(0x44642); ic != null; ic = chara.getBankContainer().getItemContainer(0x44642)) {
            chara.getBankContainer().removeItemContainer(ic);
        }

    }

    /**
     * Sender feilmelding tilbake til spiller om at han ikke kan logge på fordi
     * han allerede er logget inn.
     *
     * @param p Spiller som skal motta feilmelding.
     */
    private void loginFeil(Player p) {

        PacketData svar = new PacketData();

        svar.writeIntBE(0x7);
        Packet svar_pakke = new Packet(0x8041, svar.getData());

        p.sendPacket(svar_pakke);
    }

    /**
     * Henter ut account ID for oppgitt character.
     *
     *
     * @param character_id Character ID til character hvis konto ID vi skal
     * returnere.
     *
     * @return Account ID. Eller 0 hvis ikke funnet.
     */
    private int getAccountID(int character_id) {

        int account = 0;

        GameCharacter c = ManageCharacters.getGameCharacter(character_id);

        if (c != null) {
            account = c.getKonto().getAccountID();
        }

        return account;
    }

    /**
     * Henter informasjon om character og returnerer det som et CharacterGame
     * objekt.
     *
     * @param character_id Character ID til character vi skal returnere info om.
     * @param account_id Konto character tilhører.
     *
     * @return Character informasjon som game serveren trenger, eller NULL hvis
     * feil.
     */
    private CharacterGame loadCharacter(int character_id, int account_id) {

        CharacterGame character = null;

        GameCharacter c = ManageCharacters.getGameCharacter(character_id);

        //Sjekk at character tilhører oppgitt konto ID.
        if (account_id == c.getKonto().getAccountID()) {
            character = new CharacterGame(character_id, c.getNavn(), c.getGender(), c.getFaction());
            character.setAppearance(c.getAppearance());
            character.setPosisjon(c.getPosition());
            character.setRank(c.getRank());
            character.setRankScore(c.getRankScore());
            character.setSkills(c.getSkills());
            character.setScore(c.getScore());
            character.setLosses(c.getLosses());
            character.setNewmanMedal(c.getNewman());
            character.setRichmondMedal(c.getRichmond());
        }

        return character;
    }

}
