/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.AgesDao;
import database.Database;
import dao.TamagotchiDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Sovelluslogiikasta vastaava luokka. Luokka hallinnoi tietokantaluokkia ja
 * luokka palauttaa sisällön TamagotchiUi-luokalle toteuttaen ui-luokan
 * käyttämää käyttöliittymälogiikkaa.
 */
public class TamagotchiService {

    private TamagotchiDao tamagotchiDao;
    private AgesDao agesDao;
    private Database database;
    private String name;
    public Map<String, Tamagotchi> tamagotchis;

    public TamagotchiService(Database database) throws SQLException {
        this.database = database;
        this.tamagotchiDao = new TamagotchiDao(database);
        this.agesDao = new AgesDao(database);
        tamagotchis = new HashMap();
    }

    /**
     * Tamagotchin palauttava metodi.
     *
     * @return tamagotchi olion ajantasaisine tietoineen
     */
    public Tamagotchi tamagotchi() {
        Tamagotchi t = tamagotchis.get(name);

        return t;
    }

    /**
     * Tarkastus onko samanniminen Tamagotchi jo olemassa.
     *
     * @throws SQLException
     *
     * @return true jos tamagotchi on olemassa, muuten false
     */
    public boolean alreadyExists() throws SQLException {
        if (name.equals("")) {
            return false;
        }
        if (everyNameExistedlist().contains(name)) {

            return true;
        }
        return false;
    }

    /**
     * Tarkastus onko syötetty nimi liian lyhyt. Nimen tulee olla vähintään 2
     * merkkiä pitkä.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     *
     * @return true jos nimi alle 2 merkkiä, muuten false
     */
    public boolean tooShortName() throws SQLException {

        if (name.chars().count() < 2) {
            return true;
        }
        return false;
    }

    /**
     * Tarkastus onko syötetty nimi liian pitkä. Nimen tulee olla enintään 25
     * merkkiä pitkä.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     *
     * @return true jos nimi yli 25 merkkiä, muuten false
     */
    public boolean tooLongName() throws SQLException {

        if (name.chars().count() > 25) {
            return true;
        }
        return false;
    }

    /**
     * Luo uuden tamagotchin ja tallettaa sen tietokantaan.
     *
     * @throws SQLException
     */
    public void newTamagotchi() throws SQLException {

        Tamagotchi tamagotchi = new Tamagotchi(name);
        tamagotchis.put(name, tamagotchi);

        tamagotchiDao.create(tamagotchi);
        agesDao.create(tamagotchi);

    }

    /**
     * Tamagotchin hakeminen tietokannasta päivitettyine tietoineen ja
     * lisääminen hashmappiin
     *
     * @throws SQLException virhe tietokannanhallinnassa
     *
     * @return nimellä löytyvä tamagotchi
     */
    public Tamagotchi getTamagotchi() throws SQLException {

        Tamagotchi tamagotchi = tamagotchiDao.loadTamagotchi(name);
        tamagotchis.put(name, tamagotchi);

        return tamagotchi;
    }

    /**
     * tamagotchin nälän päivitys. Nälkä vähenee 150 000 syötettäessä, mutta ei
     * mene alle nollan.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     */
    public void updateTamagotchiHunger() throws SQLException {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getHunger() - 150000 <= 0) {
            tamagotchi.setHunger(0);
        } else {
            tamagotchi.setHunger(tamagotchi.getHunger() - 150000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    /**
     * tamagotchin onnellisuuden päivitys. Surullisuus vähenee 150 000
     * leikittäessä, mutta ei mene alle nollan.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     */
    public void updateTamagotchiHappiness() throws SQLException {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getSadness() - 150000 <= 0) {
            tamagotchi.setSadness(0);
        } else {
            tamagotchi.setSadness(tamagotchi.getSadness() - 150000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    /**
     * tamagotchin siisteyden päivitys. Likaisuus vähenee 250 000 siivottaessa,
     * mutta ei mene alle nollan.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     */
    public void updateTamagotchiClean() throws SQLException {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getDirtiness() - 250000 <= 0) {
            tamagotchi.setClean(0);
        } else {
            tamagotchi.setClean(tamagotchi.getDirtiness() - 250000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    /**
     * tamagotchin sairaustason päivitys. Sairaustila vähenee 300 000
     * lääkittäessä, mutta ei mene alle nollan.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     */
    public void updateTamagotchiMedicate() throws SQLException {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getSick() - 300000 <= 0) {
            tamagotchi.setSick(0);
        } else {
            tamagotchi.setSick(tamagotchi.getSick() - 300000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    /**
     * tamagotchin energian päivitys. Metodia kutsutaan tamagotchin nukkuessa
     * joka sekunti, jolloin väsymys vähenee 1000 yksikköä.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     */
    public void updateTamagotchiSleep() throws SQLException {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getTiredness() - 1000 <= 0) {
            tamagotchi.setTiredness(0);
        } else {
            tamagotchi.setTiredness(tamagotchi.getTiredness() - 1000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    /**
     * Tarkastus onko Tamagotchi elossa ja asettaminen kuolleeksi jos liian
     * nälkä, onneton, likainen tai sairas.
     *
     * @return true jos tamagotchi on elossa, false jos kuollut
     */
    public boolean tamagotchiAlive() {
        Tamagotchi tamagotchi = tamagotchis.get(name);

        if (tamagotchi.getSadness() >= 1000000 || tamagotchi.getSick() >= 1000000 || tamagotchi.getDirtiness() >= 1000000 || tamagotchi.getHunger() >= 1000000) {
            tamagotchi.setAlive(false);
        }
        if (tamagotchi.isAlive()) {
            return true;
        }
        return false;
    }

    /**
     * Metodi hakee tamagotchin tämänhetkisen tilanteen nälästä,
     * surullisuudesta, likaisuudesta ja sairaustasosta ja asettaa mielialan sen
     * mukaisesti. Jos tamagotchi on liian väsynyt, se pakotetaan nukkumaan eikä
     * se herää kesken unen ilman herätystä. Jos mitään ei ole vialla
     * tamagotchin mieliala on onnellinen.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     *
     * @return tamagotchin tämänhetkisen mielialan
     */
    public String getMood() throws SQLException {
        Tamagotchi tamagotchi = tamagotchis.get(name);

        if (!tamagotchi.getMood().equals("sleep")) {
            if (tamagotchi.getTiredness() > 900000) {
                tamagotchi.setMood("sleep");
            } else if (tamagotchi.getHunger() > 450000) {
                tamagotchi.setMood("hungry");
            } else if (tamagotchi.getSadness() > 400000) {
                tamagotchi.setMood("sad");
            } else if (tamagotchi.getDirtiness() > 500000) {
                tamagotchi.setMood("dirty");
            } else if (tamagotchi.getSick() > 500000) {
                tamagotchi.setMood("sick");
            } else {
                tamagotchi.setMood("happy");
            }
        }
        return tamagotchi.getMood();
    }

    /**
     * Asettaa mielialan tamagotchille ja päivittää sen tietokantaan.
     *
     * @param mood tamagotchin mieliala Stringinä
     *
     * @throws SQLException virhe tietokannanhallinnassa
     */
    public void setMood(String mood) throws SQLException {
        Tamagotchi tamagotchi = tamagotchis.get(name);
        tamagotchi.setMood(mood);
        tamagotchiDao.update(tamagotchi);
    }

    /**
     * Metodia kutsutaan joka sekunti ja se päivittää tamagotchin tilaa. Nälkä,
     * surullisuus, likaisuus, väsymys ja sairaustila lisääntyvät 5 yksikköä.
     */
    public void time() {
        Tamagotchi tamagotchi = tamagotchis.get(name);

        tamagotchi.setHunger(tamagotchi.getHunger() + 5);
        tamagotchi.setSadness(tamagotchi.getSadness() + 5);
        tamagotchi.setClean(tamagotchi.getDirtiness() + 5);
        tamagotchi.setTiredness(tamagotchi.getTiredness() + 5);
        tamagotchi.setSick(tamagotchi.getSick() + 5);

    }

    /**
     * Kaikkien tamagotchien nimien hakeminen tietokannasta, jotka ovat yhä
     * pelissä.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     *
     * @return kaikki pelitietokannassa olevian tamagotchien nimet
     */
    public String tamaslist() throws SQLException {
        ArrayList<String> list = tamagotchiDao.list();

        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Kaikkien tamagotchien nimien hakeminen tietokannasta, jotka ovat olleet
     * olemassa ennen tai ovat edelleen pelissä.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     *
     * @return kaikki tietokannassa olevian ja olleiden tamagotchien nimet
     */
    public ArrayList everyNameExistedlist() throws SQLException {
        ArrayList<String> list = agesDao.names();

        return list;
    }

    /**
     * Tamagotchin poistaminen tietokannasta.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     */
    public void delete() throws SQLException {
        tamagotchiDao.deleteTamagotchi(name);
    }

    /**
     * Tamagotchin iän hakeminen ja tarkistus onko tamagotchi vauva vai
     * aikuinen. Tamagotchi kehittyy aikuiseksi 4 päivän iässä.
     *
     * @return true, jos tamagotchi on vielä vauvatasolla, false, jos tamagotchi
     * on aikuinen
     */
    public boolean baby() {
        Tamagotchi tamagotchi = tamagotchis.get(name);
        int age = tamagotchi.getAge();

        if (age >= 4) {
            return false;
        }

        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Hakee tietokannasta tiedot tamagotcheista, jotka ovat olleet aiemmin
     * olemassa tai jotka ovat edelleen pelissä.
     *
     * @throws SQLException
     *
     * @return palauttaa tiedot tamagotchin nimestä, iästä, syntymäpäivästä ja
     * tiedosto onko elossa Stringinä.
     */
    public String ages() throws SQLException {
        ArrayList<String> list = agesDao.list();

        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Päivittää tietokantaan tamagotchin ikää ja elossaolotilannetta.
     *
     * @throws SQLException
     */
    public void ageUpdate() throws SQLException {
        Tamagotchi tamagotchi = tamagotchis.get(name);
        agesDao.update(tamagotchi);
    }
}
