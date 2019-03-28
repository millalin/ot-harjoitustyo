/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.TamagotchiDao;
import domain.Tamagotchi;
import domain.TamagotchiService;
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
        String name = "name";
        TamagotchiDao tdao = new TamagotchiDao();
        TamagotchiService serv = new TamagotchiService(tdao);
        serv.newTamagotchi(name);
        serv.updateTamagotchiHunger(name);
        tamagotchi = serv.getTamagotchi(name);

    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
    }

    @Test
    public void tamagotchinNalkaOnAlussa80() {
        Tamagotchi tamagotchi = new Tamagotchi("nimi");

        int vastaus = tamagotchi.getHunger();

        assertEquals(80, vastaus);
    }

    @Test
    public void tamagotchinNalkaVaheneeKunSyotetaanKerran() {

        int vastaus = tamagotchi.getHunger();

        assertEquals(60, vastaus);
    }
}
