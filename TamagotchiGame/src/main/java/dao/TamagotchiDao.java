/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
 *
 * @author milla
 */
public class TamagotchiDao implements Dao<Tamagotchi, Integer> {

    private int energy;
    private int hunger;
    private int happiness;
    private long time;
    private long birthtime;
    private int clean;
    private int sick;
    private String address;

    //private Tamagotchi tamagotchi;
    public TamagotchiDao() throws Exception {

        address = "jdbc:h2:./src/main/resources/tamagotchitietokanta";
        alustaTietokanta();

    }

    @Override
    public Tamagotchi create(Tamagotchi tamagotchi) throws SQLException {

        Connection connection = DriverManager.getConnection(address, "sa", "");

        //    tamas.add(tamagotchi);
        PreparedStatement statement
                = connection.prepareStatement("INSERT INTO Tamagotchi (name,birthtime, hunger, energy,happiness,clean,sick,time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        statement.setString(1, tamagotchi.getName());
        statement.setLong(2, System.currentTimeMillis());
        statement.setInt(3, tamagotchi.getHunger());
        statement.setInt(4, tamagotchi.getEnergy());
        statement.setInt(5, tamagotchi.getHappiness());
        statement.setInt(6, tamagotchi.getClean());
        statement.setInt(7, tamagotchi.getSick());
        statement.setLong(8, System.currentTimeMillis());

        statement.executeUpdate();

        statement.close();

        connection.close();

        return tamagotchi;
    }

    @Override
    public Tamagotchi update(Tamagotchi tamagotchi) throws SQLException {
        Connection connection = DriverManager.getConnection(address, "sa", "");

        PreparedStatement statement
                = connection.prepareStatement("UPDATE Tamagotchi SET hunger = ?, energy = ?, happiness= ?, clean = ?, sick = ?, time = ? WHERE name = ?");

        statement.setInt(1, tamagotchi.getHunger());
        statement.setInt(2, tamagotchi.getEnergy());
        statement.setInt(3, tamagotchi.getHappiness());
        statement.setInt(4, tamagotchi.getClean());
        statement.setInt(5, tamagotchi.getSick());
        statement.setLong(6, System.currentTimeMillis());
        statement.setString(7, tamagotchi.getName());

        //mood??
        
        statement.executeUpdate();
        statement.close();
        connection.close();

        return tamagotchi;
    }

    public Tamagotchi loadTamagotchi(String name) throws Exception {

        Connection connection = DriverManager.getConnection(address, "sa", "");

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
            time = resultSet.getLong("time");

        }

        connection.close();
//päivitetään ajan kuluminen ajan tasalle kun tamagotchi taas ladataan
        Tamagotchi tamagotchi = new Tamagotchi(name);
        long currentTime = System.currentTimeMillis();
        long timeGone = currentTime - time;

        int x = (int) (timeGone / 10000);

        int newHunger = hunger + (56 * x);
        int newEnergy = energy - (56 * x);
        int newHappiness = happiness - (56 * x);
        int newClean = clean - (56 * x);
        int newSick = sick + (56 * x);
        tamagotchi.setHunger(newHunger);
        tamagotchi.setEnergy(newEnergy);
        tamagotchi.setHappiness(newHappiness);
        tamagotchi.setClean(newClean);
        tamagotchi.setSick(newSick);
        tamagotchi.setAlive(true); //myöh haku ja päiv tietokannasta

        return tamagotchi;
    }

    public ArrayList list() throws Exception {
        ArrayList list = new ArrayList();
        Connection connection = DriverManager.getConnection(address, "sa", "");

        PreparedStatement statement
                = connection.prepareStatement("SELECT name FROM Tamagotchi;");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            list.add(name);

        }

        return list;

    }

    public void deleteTamagotchi(String name) throws Exception {
        Connection connection = DriverManager.getConnection(address, "sa", "");

        PreparedStatement statement
                = connection.prepareStatement("DELETE FROM Tamagotchi WHERE name = ?");

        statement.setString(1, name);

        statement.executeUpdate();
        connection.close();
    }

    public void alustaTietokanta() {
        try (Connection conne = DriverManager.getConnection(address, "sa", "")) {
            //          conn.prepareStatement("DROP TABLE Tamagotchi IF EXISTS;").executeUpdate();
            conne.prepareStatement("CREATE TABLE IF NOT EXISTS Tamagotchi(id serial, name varchar(25),birthtime long, hunger integer, energy integer, happiness integer,clean integer, sick integer, time long);")
                    .executeUpdate();
            conne.close();
        } catch (SQLException ex) {
            Logger.getLogger(TamagotchiDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

