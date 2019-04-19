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
 *
 * @author milla
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

    public Tamagotchi Tamagotchi(String name) {
        Tamagotchi t = tamagotchis.get(name);

        return t;
    }

    public boolean alreadyExists(String name) throws Exception {
        if (name.equals("")) {
            return false;
        }
        if (tamaslist().contains(name)) {

            return true;
        }
        return false;
    }

    public boolean tooShortName(String name) throws Exception {

        if (name.chars().count() < 3) {
            return true;
        }
        return false;
    }

    public void newTamagotchi(String name) throws Exception {

        Tamagotchi tamagotchi = new Tamagotchi(name);
        tamagotchis.put(name, tamagotchi);

        tamagotchiDao.create(tamagotchi);

    }

    public Tamagotchi getTamagotchi(String name) throws Exception {

        Tamagotchi tamagotchi = tamagotchiDao.loadTamagotchi(name);
        tamagotchis.put(name, tamagotchi);

        return tamagotchi;
    }

    public void updateTamagotchiHunger(String name) throws Exception {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getHunger() - 150000 <= 0) {
            tamagotchi.setHunger(0);
        } else {
            tamagotchi.setHunger(tamagotchi.getHunger() - 150000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    public void updateTamagotchiHappiness(String name) throws Exception {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getHappiness() + 150000 >= 1000000) {
            tamagotchi.setHappiness(1000000);
        } else {
            tamagotchi.setHappiness(tamagotchi.getHappiness() + 150000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    public void updateTamagotchiClean(String name) throws Exception {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getClean() + 250000 >= 1000000) {
            tamagotchi.setClean(1000000);
        } else {
            tamagotchi.setClean(tamagotchi.getClean() + 250000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    public void updateTamagotchiMedicate(String name) throws Exception {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getSick() - 300000 <= 0) {
            tamagotchi.setSick(0);
        } else {
            tamagotchi.setSick(tamagotchi.getSick() - 300000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    public void updateTamagotchiSleep(String name) throws Exception {

        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getEnergy() + 1000 >= 1000000) {
            tamagotchi.setEnergy(1000000);
        } else {
            tamagotchi.setEnergy(tamagotchi.getEnergy() + 1000);
        }

        tamagotchiDao.update(tamagotchi);
    }

    public boolean tamagotchiAlive(String name) {
        Tamagotchi tamagotchi = tamagotchis.get(name);
        if (tamagotchi.getHunger() >= 1000000) {
            tamagotchi.setAlive(false);
        }
        if (tamagotchi.getHappiness() <= 0 || tamagotchi.getSick() >= 1000000) {
            tamagotchi.setAlive(false);
        }
        if (tamagotchi.isAlive()) {
            return true;
        }
        return false;
    }

    public String getMood(String name) throws Exception {
        Tamagotchi tamagotchi = tamagotchis.get(name);

        if (!tamagotchi.getMood().equals("sleep")) {
            if (tamagotchi.getEnergy() < 100000) {
                tamagotchi.setMood("sleep");
            } else if (tamagotchi.getHunger() > 550000) {
                tamagotchi.setMood("hungry");

            } else if (tamagotchi.getHappiness() < 500000) {
                tamagotchi.setMood("sad");

            } else if (tamagotchi.getClean() < 500000) {
                tamagotchi.setMood("dirty");

            } else if (tamagotchi.getSick() > 500000) {
                tamagotchi.setMood("sick");

            } else {
                tamagotchi.setMood("happy");

            }
        }

        return tamagotchi.getMood();
    }

    public void setMood(String name, String mood) throws Exception {
        Tamagotchi tamagotchi = tamagotchis.get(name);
        tamagotchi.setMood(mood);
        tamagotchiDao.update(tamagotchi);
    }

    public void time(String name) {
        Tamagotchi tamagotchi = tamagotchis.get(name);

        tamagotchi.setHunger(tamagotchi.getHunger() + 5);
        tamagotchi.setHappiness(tamagotchi.getHappiness() - 5);
        tamagotchi.setClean(tamagotchi.getClean() - 5);
        tamagotchi.setEnergy(tamagotchi.getEnergy() - 5);
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

    public void delete(String name) throws Exception {
        tamagotchiDao.deleteTamagotchi(name);
    }

    public boolean baby(String name) {
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
