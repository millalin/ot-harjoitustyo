/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.TamagotchiDao;
import java.sql.SQLException;

/**
 *
 * @author milla
 */
public class TamagotchiService {

    private Tamagotchi tamagotchi;
    private TamagotchiDao tamagotchiDao;

    public void newTamagotchi(String nimi) throws Exception {
        tamagotchi = new Tamagotchi(nimi);
        tamagotchiDao = new TamagotchiDao(tamagotchi);
        tamagotchiDao.create(tamagotchi);
    }
    
    public void updateTamagotchi() throws Exception {
        
        tamagotchi.setHunger(tamagotchi.getHunger() - 20); //kun syötetään nälkä vähenee 20
        tamagotchiDao.update(tamagotchi);
        System.out.println("hunger: " + tamagotchi.getHunger());
    }
    
    public String getMood() throws Exception   {
        String mood="";
        
        if(tamagotchi.getHunger()>60)   {
            mood="sad";
        } else  {
            mood="happy";
        }
        return mood;
    }

}
