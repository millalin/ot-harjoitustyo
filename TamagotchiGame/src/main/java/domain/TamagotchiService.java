/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.TamagotchiDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author milla
 */
public class TamagotchiService {

    private TamagotchiDao tamagotchiDao;
    private String name;
    public Map<String, Tamagotchi> tamas;

    public TamagotchiService(TamagotchiDao tamagotchiDao) {
        this.tamagotchiDao = tamagotchiDao;
        tamas = new HashMap();

    }

    public boolean alreadyExists(String name) throws Exception {
        if (tamaslist().contains(name)) {
            System.out.println("on jo");
            return true;
        }
        return false;
    }

    public void newTamagotchi(String name) throws Exception {

        Tamagotchi tamagotchi = new Tamagotchi(name);
        tamas.put(name, tamagotchi);

        tamagotchiDao.create(tamagotchi);

    }

    public Tamagotchi getTamagotchi(String name) throws Exception {

        Tamagotchi tamagotchi = tamagotchiDao.loadTamagotchi(name);
        tamas.put(name, tamagotchi);

        return tamagotchi;
    }

    public void updateTamagotchiHunger(String name) throws Exception {

        Tamagotchi tamagotchi = tamas.get(name);
        if (tamagotchi.getHunger() - 150000 <= 0) {
            tamagotchi.setHunger(0);
        } else {
            tamagotchi.setHunger(tamagotchi.getHunger() - 150000); //kun syötetään nälkä vähenee 150t 
        }

        tamagotchiDao.update(tamagotchi);
        System.out.println("hunger: " + tamagotchi.getHunger());
    }

    public void updateTamagotchiHappiness(String name) throws Exception {

        Tamagotchi tamagotchi = tamas.get(name);
        if (tamagotchi.getHappiness() + 150000 >= 1000000) {
            tamagotchi.setHappiness(1000000);
        } else {
            tamagotchi.setHappiness(tamagotchi.getHappiness() + 150000);
        }

        tamagotchiDao.update(tamagotchi);
        System.out.println("happiness: " + tamagotchi.getHappiness());
    }

    public void updateTamagotchiClean(String name) throws Exception {

        Tamagotchi tamagotchi = tamas.get(name);
        if (tamagotchi.getClean() + 250000 >= 1000000) {
            tamagotchi.setClean(1000000);
        } else {
            tamagotchi.setClean(tamagotchi.getClean() + 250000);
        }

        tamagotchiDao.update(tamagotchi);
        System.out.println("clean: " + tamagotchi.getClean());
    }

    public void updateTamagotchiMedicate(String name) throws Exception {

        Tamagotchi tamagotchi = tamas.get(name);
        if (tamagotchi.getSick() - 300000 <= 0) {
            tamagotchi.setSick(0);
        } else {
            tamagotchi.setSick(tamagotchi.getSick() - 300000);
        }

        tamagotchiDao.update(tamagotchi);
        System.out.println("sick: " + tamagotchi.getSick());
    }

    public void updateTamagotchiSleep(String name) throws Exception {

        Tamagotchi tamagotchi = tamas.get(name);
        if (tamagotchi.getEnergy() + 150000 >= 1000000) {
            tamagotchi.setEnergy(1000000);
        } else {
            tamagotchi.setEnergy(tamagotchi.getEnergy() + 1000);
        }

        tamagotchiDao.update(tamagotchi);
        System.out.println("energy: " + tamagotchi.getEnergy());
    }

    public boolean tamagotchiAlive(String name) {
        Tamagotchi tamagotchi = tamas.get(name);
        if (tamagotchi.getHunger() >= 1000000) {
            tamagotchi.setAlive(false);
            //PÄIVITYS myöhemmin myös tietokantaan?? onko tarve
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
        Tamagotchi tamagotchi = tamas.get(name);

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

    public void setMood(String name, String mood) {
        Tamagotchi tamagotchi = tamas.get(name);
        tamagotchi.setMood(mood);
    }

    public void time(String name) {
        Tamagotchi tamagotchi = tamas.get(name);

        tamagotchi.setHunger(tamagotchi.getHunger() + 56);
        tamagotchi.setHappiness(tamagotchi.getHappiness() - 56);
        tamagotchi.setClean(tamagotchi.getClean() - 56);
        tamagotchi.setEnergy(tamagotchi.getEnergy() - 56);
        tamagotchi.setSick(tamagotchi.getSick() + 56);

    }

    public String tamaslist() throws Exception {
        ArrayList<String> list = tamagotchiDao.list();
        String tamas = list.toString();

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

}
