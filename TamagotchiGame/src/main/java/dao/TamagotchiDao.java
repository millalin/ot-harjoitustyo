/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import database.Database;
import domain.Tamagotchi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Luokka tamagotchin tietokannanhallintaa varten. Luokka käyttää tietokannan
 * Tamagotchi -taulua.
 */
public class TamagotchiDao implements Dao<Tamagotchi, Integer> {

    private Database database;
    private int energy;
    private int hunger;
    private int happiness;
    private long time;
    private long birthtime;
    private int clean;
    private int sick;
    private String mood;
    private String address;

    public TamagotchiDao(Database database) throws SQLException {

        this.database = database;

        createTable();

    }

    /**
     * Luo tietokantaan uuden tamagotchin.
     *
     * @param tamagotchi Tamagotchi olio
     *
     * @throws SQLException virhe tietokannanhallinnassa
     * 
     * @return tamagotchi olio
     */
    @Override
    public Tamagotchi create(Tamagotchi tamagotchi) throws SQLException {

        Connection connection = database.newConnection();

        PreparedStatement statement
                = connection.prepareStatement("INSERT INTO Tamagotchi (name,birthtime, hunger, "
                        + "energy,happiness,clean,sick,mood,time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        statement.setString(1, tamagotchi.getName());
        statement.setLong(2, System.currentTimeMillis());
        statement.setInt(3, tamagotchi.getHunger());
        statement.setInt(4, tamagotchi.getTiredness());
        statement.setInt(5, tamagotchi.getSadness());
        statement.setInt(6, tamagotchi.getDirtiness());
        statement.setInt(7, tamagotchi.getSick());
        statement.setString(8, tamagotchi.getMood());
        statement.setLong(9, System.currentTimeMillis());

        statement.executeUpdate();

        statement.close();

        connection.close();

        return tamagotchi;
    }

    /**
     * Päivittää tietokantaan tamagotchin nykytilanteen.
     *
     * @param tamagotchi Tamagotchi olio
     *
     * @throws SQLException virhe tietokannanhallinnassa
     * 
     * @return tamagotchi olio
     */
    @Override
    public Tamagotchi update(Tamagotchi tamagotchi) throws SQLException {
        Connection connection = database.newConnection();

        PreparedStatement statement
                = connection.prepareStatement("UPDATE Tamagotchi SET hunger = ?, energy = ?, happiness= ?, "
                        + "clean = ?, sick = ?, mood = ?, time = ? WHERE name = ?");

        statement.setInt(1, tamagotchi.getHunger());
        statement.setInt(2, tamagotchi.getTiredness());
        statement.setInt(3, tamagotchi.getSadness());
        statement.setInt(4, tamagotchi.getDirtiness());
        statement.setInt(5, tamagotchi.getSick());
        statement.setString(6, tamagotchi.getMood());
        statement.setLong(7, System.currentTimeMillis());
        statement.setString(8, tamagotchi.getName());

        statement.executeUpdate();
        statement.close();
        connection.close();

        return tamagotchi;
    }

    /**
     * Hakee tietokannasta tamagotcin nimen perusteella ja päivittää sen tilan
     * vastaamaan kulunutta aikaa siloin kun tamagotchi taas avataan.
     *
     * @param name Tamagotchin nimi
     *
     * @throws SQLException virhe tietokannanhallinnassa
     *
     * @return tamagotchi olio
     */
    public Tamagotchi loadTamagotchi(String name) throws SQLException {

        Connection connection = database.newConnection();

        PreparedStatement statement
                = connection.prepareStatement("SELECT * FROM Tamagotchi WHERE name = ?");

        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            hunger = resultSet.getInt("hunger");
            energy = resultSet.getInt("energy");
            happiness = resultSet.getInt("happiness");
            clean = resultSet.getInt("clean");
            sick = resultSet.getInt("sick");
            mood = resultSet.getString("mood");
            time = resultSet.getLong("time");
            birthtime = resultSet.getLong("birthtime");
        }
        connection.close();
        Tamagotchi tamagotchi = updateTimePassing(name);

        return tamagotchi;
    }

    /**
     * Päivittää tamagotchin tilan laskemalla paljon aikaa on kulunut siitä, kun
     * tietokanta viimeksi on päivitetty. Jokaista kulunutta sekuntia kohden
     * nälkä, väsymys, surullisuus, likaisuus ja sairaustila lisääntyvät 5
     * yksikköä aivan kuten silloin, kun ohjelma olisi ollut auki ja käynnissä.
     * Tämä tila päivitetään ajan tasalle.
     *
     * @param name Tamagotchin nimi
     *
     * @return tamagotchi olio
     */
    public Tamagotchi updateTimePassing(String name) {

        Tamagotchi tamagotchi = new Tamagotchi(name);
        long currentTime = System.currentTimeMillis();
        long timeGone = currentTime - time;
        int x = (int) (timeGone / 1000);
        int newHunger = hunger + (5 * x);
        int newEnergy = energy + (5 * x);
        int newHappiness = happiness + (5 * x);
        int newClean = clean + (5 * x);
        int newSick = sick + (5 * x);
        tamagotchi.setDateOfBirth(birthtime);
        tamagotchi.setHunger(newHunger);
        tamagotchi.setTiredness(newEnergy);
        tamagotchi.setSadness(newHappiness);
        tamagotchi.setClean(newClean);
        tamagotchi.setSick(newSick);
        tamagotchi.setMood(mood);
        tamagotchi.setAlive(true);
        return tamagotchi;
    }

    /**
     * Hakee tietokannasta tamagotcien nimet ja lisää ne ArrayListiin.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     *
     * @return lista tamagotchien nimistä tietokannassa
     */
    public ArrayList list() throws SQLException {
        ArrayList list = new ArrayList();
        Connection connection = database.newConnection();

        PreparedStatement statement
                = connection.prepareStatement("SELECT name FROM Tamagotchi;");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            list.add(name);

        }
        connection.close();
        return list;
    }

    /**
     * Hakee tietokannasta tamagotcin nimen perusteella ja poistaa sen
     * tietokannasta.
     *
     * @param name Tamagotchin nimi
     *
     * @throws SQLException virhe tietokannanhallinnassa
     */
    public void deleteTamagotchi(String name) throws SQLException {
        Connection connection = database.newConnection();

        PreparedStatement statement
                = connection.prepareStatement("DELETE FROM Tamagotchi WHERE name = ?");

        statement.setString(1, name);

        statement.executeUpdate();
        connection.close();
    }

    /**
     * Luo tietokantataulun Tamagotchi, mikäli taulua ei löydy ennestään.
     */
    public void createTable() {
        try (Connection conne = database.newConnection()) {
            //          conn.prepareStatement("DROP TABLE Tamagotchi IF EXISTS;").executeUpdate();
            conne.prepareStatement("CREATE TABLE IF NOT EXISTS Tamagotchi(id serial, name varchar(25),"
                    + "birthtime long, hunger integer, energy integer, happiness integer,clean integer, sick integer, mood varchar(10), time long);")
                    .executeUpdate();
            conne.close();
        } catch (SQLException ex) {
            Logger.getLogger(TamagotchiDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
