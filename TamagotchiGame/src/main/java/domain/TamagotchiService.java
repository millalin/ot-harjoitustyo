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

    public boolean TamagotchiAlive(String name) {
        Tamagotchi tamagotchi = tamas.get(name);
        if (tamagotchi.getHunger() > 1000000) {
            tamagotchi.setAlive(false);
            //PÄIVITYS myöhemmin myös tietokantaan?? onko tarve
        }

        if (tamagotchi.isAlive()) {
            return true;
        }
        return false;

    }

    public String getMood(String name) throws Exception {
        Tamagotchi tamagotchi = tamas.get(name);

        String mood = "";

        if (tamagotchi.getHunger() > 550000) {
            mood = "sad";
        } else {
            mood = "happy";
        }
        return mood;
    }

    public void time(String name) {
        Tamagotchi tamagotchi = tamas.get(name);
        //tamagotchi.setCurrentTime(tamagotchi.getCurrentTime() + 56);

        System.out.println("aika : " + System.currentTimeMillis());
        System.out.println("synt aika: " + tamagotchi.getDateOfBirth());
        System.out.println("aika-synt aika: " + (System.currentTimeMillis() - tamagotchi.getDateOfBirth()));

        tamagotchi.setHunger(tamagotchi.getHunger() + 56);
        System.out.println("hung" + tamagotchi.getHunger());
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
    
    public void delete(String name)  throws Exception   {
        tamagotchiDao.deleteTamagotchi(name);
    }

}
