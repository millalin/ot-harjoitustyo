/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.TamagotchiDao;
import domain.Tamagotchi;
import domain.TamagotchiService;
import java.util.ArrayList;
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
        tdao = new TamagotchiDao();
        serv = new TamagotchiService(tdao);
        serv.newTamagotchi(name);
        tamagotchi = new Tamagotchi(name);
        tdao.create(tamagotchi);
        tamas = new HashMap();
        tamas.put(name, tamagotchi);

    }

    @After
    public void tearDown() throws Exception {

        //poista tietokannasta
        tdao.deleteTamagotchi(name);
        tdao.deleteTamagotchi("x");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
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
        Tamagotchi tama=new Tamagotchi("x");
        
        tdao.create(tama);
        tama=serv.getTamagotchi("x");
        serv.time("x");
        int result=tama.getHunger();

        assertEquals(700056, result);
    }
    
     @Test
    public void tamagotchiDeadWhenHungerOver1000000() throws Exception {
        Tamagotchi tama=new Tamagotchi("x");
        tdao.create(tama);
        tama=serv.getTamagotchi("x");
        tama.setHunger(1000001);
        boolean result=serv.tamagotchiAlive("x");

        assertEquals(false, result);
    }
    
    
     @Test
    public void tamagotchiAliveWhenHungerLessThan1000000() throws Exception {
        Tamagotchi tama=new Tamagotchi("x");
        tdao.create(tama);
        tama=serv.getTamagotchi("x");
        tama.setHunger(999999);
        boolean result=serv.tamagotchiAlive("x");

        assertEquals(true, result);
    }
    
    
      @Test
    public void tamagotchiListReturnsListAsString() throws Exception {

        String result =serv.tamaslist();
        boolean notEmpty=true;
        if(result.isEmpty())    {
            notEmpty=false;
        }

          assertTrue(notEmpty);
    }
    
}
