/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.TamagotchiDao;
import Database.Database;
import domain.Tamagotchi;
import domain.TamagotchiService;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author milla
 */
public class TamagotchiServiceTest {
    
    Tamagotchi tamagotchi;
    TamagotchiService serv;
    String name;
    TamagotchiDao tdao;
    public Map<String, Tamagotchi> tamas;
    Database database;
    
    public TamagotchiServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        name = "name";
        database = new Database("jdbc:h2:./src/main/resources/testitietokanta");
        
        serv = new TamagotchiService(database);
        //   tdao = new TamagotchiDao(database);
        // serv.newTamagotchi(name);
        tamagotchi = new Tamagotchi(name);
        serv.newTamagotchi(name);
        tamas = new HashMap();
        tamas.put(name, tamagotchi);
        
    }
    
    @After
    public void tearDown() throws Exception {

        //poista tietokannasta
        serv.delete(name);
       
    }
    
    @Test
    public void tamagotchinNalkaOnAlussa700000() {
        
        int result = tamagotchi.getHunger();
        
        assertEquals(700000, result);
        
    }
    
    @Test
    public void tamagotchinNalkaVaheneeKunSyotetaanKerran() throws Exception {
        
        serv.updateTamagotchiHunger(name);
        tamagotchi = serv.getTamagotchi(name);
        int result = tamagotchi.getHunger();
        
        assertEquals(550000, result);
    }
    
    @Test
    public void tamagotchiMoodIsSadWhenHungrer700000() throws Exception {
        
        String result = serv.getMood(name);
        
        assertEquals("hungry", result);
    }
    
    @Test
    public void tamagotchiMoodIsHappyWhenNothingWrong() throws Exception {
        
        serv.updateTamagotchiClean(name);
        serv.updateTamagotchiClean(name);
        serv.updateTamagotchiHappiness(name);
        serv.updateTamagotchiHunger(name);
        serv.updateTamagotchiHunger(name);
        String result = serv.getMood(name);
        
        assertEquals("happy", result);
    }
    
    @Test
    public void tamagotchiHungerGoesupWhenTime() throws Exception {
        
        serv.newTamagotchi(name);
        tamagotchi = serv.getTamagotchi(name);
        serv.time(name);
        int result = tamagotchi.getHunger();
        
        assertEquals(700005, result);
    }
    
    @Test
    public void tamagotchiDeadWhenHungerOver1000000() throws Exception {
        
        serv.newTamagotchi(name);
        tamagotchi = serv.getTamagotchi(name);
        tamagotchi.setHunger(1000001);
        boolean result = serv.tamagotchiAlive(name);
        
        assertEquals(false, result);
    }
    
    @Test
    public void tamagotchiAliveWhenHungerLessThan1000000() throws Exception {
        
        serv.newTamagotchi(name);
        tamagotchi = serv.getTamagotchi(name);
        tamagotchi.setHunger(999999);
        boolean result = serv.tamagotchiAlive(name);
        
        assertEquals(true, result);
    }
    
    @Test
    public void tamagotchiListReturnsListAsString() throws Exception {
        
        String result = serv.tamaslist();
        boolean notEmpty = true;
        if (result.isEmpty()) {
            notEmpty = false;
        }
        
        assertTrue(notEmpty);
    }
    
    @Test
    public void tamagotchiSickIsLessWhenMedicated() throws Exception {
        serv.updateTamagotchiMedicate(name);
        tamagotchi = serv.getTamagotchi(name);
        int result = tamagotchi.getSick();
        
        assertEquals(0, result);
    }
    
    @Test
    public void tamagotchiEnergyDoesNotGoOver1000000() throws Exception {
        tamagotchi = serv.getTamagotchi(name);
        tamagotchi.setEnergy(999999);
        serv.updateTamagotchiSleep(name);
        int result = tamagotchi.getEnergy();
        
        assertEquals(1000000, result);
    }
    
    @Test
    public void ifTamagotchiMoodIsSleepItStaysSleepEvenHungry() throws Exception {
        tamagotchi = serv.getTamagotchi(name);
        tamagotchi.setMood("sleep");
        
        String result = tamagotchi.getMood();
        
        assertEquals("sleep", result);
    }
    
    @Test
    public void ifTamagotchiExistsReturnsTrue() throws Exception {
        serv.tamaslist();
        boolean exists = serv.alreadyExists(name);
        
        assertTrue(exists);
    }
    
    @Test
    public void tamagotchiMoodChangesWhenChanged() throws Exception {
        
        tamagotchi = serv.getTamagotchi(name);
        serv.setMood(name, "happy");
        String result = tamagotchi.getMood();
        
        assertEquals("happy", result);
    }
    
     @Test
    public void tamagotchiMoodChangesToLastChange() throws Exception {
        
        tamagotchi = serv.getTamagotchi(name);
        serv.setMood(name, "sad");
        serv.setMood(name, "dirty");
        serv.setMood(name, "sick");
        String result = tamagotchi.getMood();
        
        assertEquals("sick", result);
    }
    
     @Test
    public void tamagotchiDiesWhenTooSick() throws Exception {
        
        tamagotchi = serv.getTamagotchi(name);
        tamagotchi.setSick(1000000);
        boolean alive = serv.tamagotchiAlive(name);
        
        assertEquals(false, alive);
    }
    
     @Test
    public void tamagotchiNameTooShortNotOkay() throws Exception {
        
        
        boolean tooShort = serv.tooShortName("x");
        
         assertTrue(tooShort);
    }
}
