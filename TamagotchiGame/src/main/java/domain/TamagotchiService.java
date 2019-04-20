/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import Database.Database;
import dao.TamagotchiDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Sovelluslogiikasta vastaava luokka. Luokka hallinnoi tietokantaluokkia ja luokka palauttaa sisällön TamagotchiUi-luokalle toteuttaen ui-luokan käyttämää käyttöliittymälogiikkaa.
 */
public class TamagotchiService {

    private TamagotchiDao tamagotchiDao;
    private Database database;
    private String name;
    public Map<String, Tamagotchi> tamagotchis;

    public TamagotchiService(Database database) throws Exception {
        this.database = database;
        this.tamagotchiDao = new TamagotchiDao(database);
        tamagotchis = new HashMap();

    }

    public Tamagotchi Tamagotchi() {
        Tamagotchi t = tamagotchis.get(name);

        return t;
    }

    /**
     * Tarkastus onko samanniminen Tamagotchi jo olemassa
     *
     * @return true jos tamagotchi on olemassa, muuten false
     */
    
    public boolean alreadyExists() throws Exception {
        if (name.equals("")) {
            return false;
        }
        if (tamaslist().contains(name)) {

            return true;
        }
        return false;
    }

    public boolean tooShortName() throws Exception {

        if (name.chars().count() < 2) {
            return true;
        }
        return false;
    }

    public void newTamagotchi() throws Exception {

        Tamagotchi tamagotchi = new Tamagotchi(name);
        tamagotchis.put(name, tamagotchi);

        tamagotchiDao.create(tamagotchi);

    }

    /**
     * Tamagotchin hakeminen tietokannasta ja lisääminen hashmappiin
     *
     * @return nimellä löytyvä tamagotchi
     */
    
    public Tamagotchi getTamagotchi() throws Exception {

        Tamagotchi tamagotchi = tamagotchiDao.loadTamagotchi(name);
        tamagotchis.put(name, tamagotchi);

        return tamagotchi;
    }

    /**
     * tamagotchin nälän päivitys
     */
    
    public void updateTamagotchiHunger() throws Exception {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getHunger() - 150000 <= 0) {
            tamagotchi.setHunger(0);
        } else {
            tamagotchi.setHunger(tamagotchi.getHunger() - 150000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    /**
     * tamagotchin onnellisuuden päivitys
     */
    
    public void updateTamagotchiHappiness() throws Exception {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getSadness() - 150000 <= 0) {
            tamagotchi.setSadness(0);
        } else {
            tamagotchi.setSadness(tamagotchi.getSadness() - 150000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    /**
     * tamagotchin siisteyden päivitys
     */
    
    public void updateTamagotchiClean() throws Exception {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getDirtiness() - 250000 <= 0) {
            tamagotchi.setClean(0);
        } else {
            tamagotchi.setClean(tamagotchi.getDirtiness() - 250000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    /**
     * tamagotchin sairaustason päivitys
     */
    
    public void updateTamagotchiMedicate() throws Exception {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getSick() - 300000 <= 0) {
            tamagotchi.setSick(0);
        } else {
            tamagotchi.setSick(tamagotchi.getSick() - 300000);
        }

        tamagotchiDao.update(tamagotchi);
    }
    
    /**
     * tamagotchin energian päivitys
     */

    public void updateTamagotchiSleep() throws Exception {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getTiredness() - 1000 <= 0) {
            tamagotchi.setTiredness(0);
        } else {
            tamagotchi.setTiredness(tamagotchi.getTiredness() - 1000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    /**
     * Tarkastus onko Tamagotchi elossa ja asettaminen kuolleeksi jos liian nälkä, onneton, likainen tai sairas. 
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

    public String getMood() throws Exception {
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

    public void setMood(String mood) throws Exception {
        Tamagotchi tamagotchi = tamagotchis.get(name);
        tamagotchi.setMood(mood);
        tamagotchiDao.update(tamagotchi);
    }

    public void time() {
        Tamagotchi tamagotchi = tamagotchis.get(name);

        tamagotchi.setHunger(tamagotchi.getHunger() + 5);
        tamagotchi.setSadness(tamagotchi.getSadness() + 5);
        tamagotchi.setClean(tamagotchi.getDirtiness() + 5);
        tamagotchi.setTiredness(tamagotchi.getTiredness() + 5);
        tamagotchi.setSick(tamagotchi.getSick() + 5);

    }

    public String tamaslist() throws Exception {
        ArrayList<String> list = tamagotchiDao.list();

        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            sb.append("\n");
        }

        return sb.toString();
    }

    public void delete() throws Exception {
        tamagotchiDao.deleteTamagotchi(name);
    }

    public boolean baby() {
        Tamagotchi tamagotchi = tamagotchis.get(name);
        int age = tamagotchi.getAge();

        if (age > 3) {
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
}
