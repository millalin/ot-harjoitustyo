/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import database.Database;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author milla
 */
public class TamagotchiTest {

    TamagotchiService serv;
    Database database;
    String name;
    HashMap tamas;

    @Before
    public void setUp() throws Exception {
        name = "name";
        database = new Database("./src/main/resources/testitietokanta");
        serv = new TamagotchiService(database);
       
        serv.setName(name);
        serv.newTamagotchi();
        
    }

    @Test
    public void tamagotchiMoodIsSadWhenHungrer700000() throws Exception {

        String result = serv.getMood();

        assertEquals("hungry", result);
    }

}
