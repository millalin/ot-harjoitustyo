/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.TamagotchiDao;
import domain.Tamagotchi;
import domain.TamagotchiService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        tamagotchi = serv.getTamagotchi(name);

    }

    @After
    public void tearDown() throws Exception {

        //poista tietokannasta
        tdao.deleteTamagotchi(name);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
    }

    @Test
    public void tamagotchinNalkaOnAlussa80() {

        int vastaus = tamagotchi.getHunger();

        assertEquals(80, vastaus);
    }

    @Test
    public void tamagotchinNalkaVaheneeKunSyotetaanKerran() throws Exception {

        serv.updateTamagotchiHunger(name);
        int vastaus = tamagotchi.getHunger();

        assertEquals(60, vastaus);
    }
}
