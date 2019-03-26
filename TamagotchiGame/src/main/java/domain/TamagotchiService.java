/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.TamagotchiDao;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author milla
 */
public class TamagotchiService {

    
    private TamagotchiDao tamagotchiDao;
    private String name;
    public Map<String,Tamagotchi> tamas;
    
    public TamagotchiService(TamagotchiDao tamagotchiDao)  {
        this.tamagotchiDao=tamagotchiDao;
        tamas=new HashMap();
        
    }

    public void newTamagotchi(String name) throws Exception {
        int id = this.generateId();
        Tamagotchi tamagotchi = new Tamagotchi(name);
        tamas.put(name, tamagotchi);
        
        
        tamagotchiDao.create(tamagotchi);
    }
    
    public void updateTamagotchi(String name) throws Exception {
        
        Tamagotchi tamagotchi =tamas.get(name);
        tamagotchi.setHunger(tamagotchi.getHunger() - 20); //kun syötetään nälkä vähenee 20
        tamagotchiDao.update(tamagotchi);
        System.out.println("hunger: " + tamagotchi.getHunger());
    }
    
    public boolean TamagotchiAlive(String name)     {
        Tamagotchi tamagotchi =tamas.get(name);
        
        if (tamagotchi.isAlive())   {
            return true;
        }
        return false;
    }
    
    public String getMood(String name) throws Exception   {
        Tamagotchi tamagotchi =tamas.get(name);
        
        String mood="";
        
        
        if(tamagotchi.getHunger()>60)   {
            mood="sad";
        } else  {
            mood="happy";
        }
        return mood;
    }

     private int generateId() {
        return tamas.size() + 1;
    }
}
