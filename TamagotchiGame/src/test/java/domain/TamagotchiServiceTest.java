package domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dao.AgesDao;
import database.Database;
import java.sql.SQLException;
import java.text.DateFormat;
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
    AgesDao ages;
    Database database;

    public TamagotchiServiceTest() {
    }

    @Before
    public void setUp() throws Exception {
        name = "name";
        database = new Database("./src/main/resources/database/testitietokanta");
        serv = new TamagotchiService(database);
        serv.setName(name);
        ages = new AgesDao(database);

        serv.newTamagotchi();

        tamagotchi = serv.getTamagotchi();

    }

    @After
    public void tearDown() throws SQLException {

        //poista tietokannasta
        serv.delete();
        ages.clear();

    }

    @Test
    public void tamagotchiHunger700000AtBeginning() {

        int result = tamagotchi.getHunger();

        assertEquals(600000, result);

    }

    @Test
    public void tamagotchiHungerGoesDownWhenFed() throws Exception {

        serv.updateTamagotchiHunger();
        tamagotchi = serv.getTamagotchi();
        int result = tamagotchi.getHunger();

        assertEquals(450000, result);
    }

    @Test
    public void tamagotchiMoodIsSadWhenHungrer700000() throws Exception {

        String result = serv.getMood();

        assertEquals("hungry", result);
    }

    @Test
    public void tamagotchiMoodIsHappyWhenNothingWrong() throws Exception {

        serv.updateTamagotchiClean();
        serv.updateTamagotchiClean();
        serv.updateTamagotchiHappiness();
        serv.updateTamagotchiHunger();
        serv.updateTamagotchiHunger();
        String result = serv.getMood();

        assertEquals("happy", result);
    }

    @Test
    public void tamagotchiHungerGoesupWhenTime() throws Exception {

        tamagotchi = serv.getTamagotchi();
        serv.time();
        int result = tamagotchi.getHunger();

        assertEquals(600005, result);
    }

    @Test
    public void tamagotchiDeadWhenHungerOver1000000() throws Exception {

        tamagotchi = serv.getTamagotchi();
        tamagotchi.setHunger(1000001);
        boolean result = serv.tamagotchiAlive();

        assertEquals(false, result);
    }

    @Test
    public void tamagotchiAliveWhenHungerLessThan1000000() throws Exception {

        tamagotchi = serv.getTamagotchi();
        tamagotchi.setHunger(999999);
        boolean result = serv.tamagotchiAlive();

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
        serv.updateTamagotchiMedicate();
        tamagotchi = serv.getTamagotchi();
        int result = tamagotchi.getSick();

        assertEquals(0, result);
    }

    @Test
    public void tamagotchiTirednessDoesNotGoUnder0() throws Exception {
        tamagotchi = serv.getTamagotchi();
        tamagotchi.setTiredness(999);
        serv.updateTamagotchiSleep();
        int result = tamagotchi.getTiredness();

        assertEquals(0, result);
    }

    @Test
    public void ifTamagotchiMoodIsSleepItStaysSleepEvenHungry() throws Exception {
        tamagotchi = serv.getTamagotchi();
        tamagotchi.setMood("sleep");

        String result = tamagotchi.getMood();

        assertEquals("sleep", result);
    }

    @Test
    public void ifTamagotchiExistsReturnsTrue() throws Exception {
        serv.tamaslist();
        boolean exists = serv.alreadyExists();

        assertTrue(exists);
    }

    @Test
    public void tamagotchiMoodChangesWhenChanged() throws Exception {

        tamagotchi = serv.getTamagotchi();
        serv.setMood("happy");
        String result = tamagotchi.getMood();

        assertEquals("happy", result);
    }

    @Test
    public void tamagotchiMoodChangesToLastChange() throws Exception {

        tamagotchi = serv.getTamagotchi();
        serv.setMood("sad");
        serv.setMood("dirty");
        serv.setMood("sick");
        String result = tamagotchi.getMood();

        assertEquals("sick", result);
    }

    @Test
    public void tamagotchiDiesWhenTooSick() throws Exception {

        tamagotchi = serv.getTamagotchi();
        tamagotchi.setSick(1000000);
        boolean alive = serv.tamagotchiAlive();

        assertEquals(false, alive);
    }

    @Test
    public void tamagotchiNameTooShortNotOkay() throws Exception {

        serv.setName("x");
        boolean tooShort = serv.tooShortName();
        serv.setName(name);

        assertTrue(tooShort);
    }

    @Test
    public void tamagotchiIsBabyWhenAgeLessThan4() throws Exception {

        boolean baby = serv.baby();

        assertTrue(baby);
    }

    @Test
    public void tamagotchisTirednessGoesDownWhenAsleep() throws Exception {
        tamagotchi = serv.getTamagotchi();
        serv.updateTamagotchiSleep();
        int result = tamagotchi.getTiredness();

        assertEquals(199000, result);
    }

    @Test
    public void tamagotchiNameTooLong() throws Exception {

        serv.setName("toolongtoolongtoolongtoolong");
        boolean tooLong = serv.tooLongName();

        assertTrue(tooLong);
    }

    @Test
    public void emptyNameIsNotOkay() throws Exception {

        serv.setName("");
        boolean empty = serv.alreadyExists();

        assertFalse(empty);
    }

    @Test
    public void agesDaoReturnsHistoryAsString() throws Exception {

        String date = DateFormat.getInstance().format(tamagotchi.getDateOfBirth());
        String ageslist = serv.ages();
        boolean alive = tamagotchi.isAlive();
        String tamagotchiStr = tamagotchi.getName() + ", synt. " + date + ", ikä: " + tamagotchi.getAge() + " päivää. Elossa: " + alive + "\n";
        assertEquals(ageslist, tamagotchiStr);
    }
}
