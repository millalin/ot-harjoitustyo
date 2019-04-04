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

    private int energy = 0;
    private int hunger = 0;
    private int happiness = 0;
    private long time = 0;

    //private Tamagotchi tamagotchi;
    public TamagotchiDao() throws Exception {

    }

    @Override
    public Tamagotchi create(Tamagotchi tamagotchi) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:h2:./tamagotchitietokanta", "sa", "");

        //    tamas.add(tamagotchi);
        PreparedStatement statement
                = connection.prepareStatement("INSERT INTO Tamagotchi (name, hunger, energy,happiness,time) VALUES (?, ?, ?, ?,?)");

        statement.setString(1, tamagotchi.getName());
        statement.setInt(2, tamagotchi.getHunger());
        statement.setInt(3, tamagotchi.getEnergy());
        statement.setInt(4, tamagotchi.getHappiness());
        statement.setLong(5, System.currentTimeMillis());

        statement.executeUpdate();

        statement.close();

        connection.close();

        return tamagotchi;
    }

    @Override
    public Tamagotchi update(Tamagotchi tamagotchi) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./tamagotchitietokanta", "sa", "");

        PreparedStatement statement
                = connection.prepareStatement("UPDATE Tamagotchi SET hunger = ?, energy = ?, happiness= ?, time = ? WHERE name = ?");

        statement.setInt(1, tamagotchi.getHunger());
        statement.setInt(2, tamagotchi.getEnergy());
        statement.setInt(3, tamagotchi.getHappiness());
        statement.setLong(4, System.currentTimeMillis());
        statement.setString(5, tamagotchi.getName());

        statement.executeUpdate();

        statement.close();

        connection.close();

        return tamagotchi;
    }

    public Tamagotchi loadTamagotchi(String name) throws Exception {

        Connection connection = DriverManager.getConnection("jdbc:h2:./tamagotchitietokanta", "sa", "");

        PreparedStatement statement
                = connection.prepareStatement("SELECT * FROM Tamagotchi SET WHERE name = ?");

        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            hunger = resultSet.getInt("hunger");
            energy = resultSet.getInt("energy");
            happiness = resultSet.getInt("happiness");
            time = resultSet.getLong("time");

        }

        connection.close();

        Tamagotchi tamagotchi = new Tamagotchi(name);
     //   long currentTime = System.currentTimeMillis();
       // long timeGone = currentTime - time;

        int x = (int) ((System.currentTimeMillis() - time) / 10000);

        tamagotchi.setHunger(hunger + (56 * x));
        tamagotchi.setEnergy(energy);
        tamagotchi.setAlive(true); //myöh haku ja päiv tietokannasta

        return tamagotchi;
    }

    public ArrayList list() throws Exception {
        ArrayList list = new ArrayList();
        Connection connection = DriverManager.getConnection("jdbc:h2:./tamagotchitietokanta", "sa", "");

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
        Connection connection = DriverManager.getConnection("jdbc:h2:./tamagotchitietokanta", "sa", "");

        PreparedStatement statement
                = connection.prepareStatement("DELETE FROM Tamagotchi WHERE name = ?");

        statement.setString(1, name);

        statement.executeUpdate();
        connection.close();
    }

    public void alustaTietokanta() {
        try (Connection conne = DriverManager.getConnection("jdbc:h2:./tamagotchitietokanta", "sa", "")) {
            //          conn.prepareStatement("DROP TABLE Tamagotchi IF EXISTS;").executeUpdate();
            conne.prepareStatement("CREATE TABLE Tamagotchi(id serial, name varchar(25), hunger integer, energy integer, happiness integer, time long);").executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TamagotchiDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
