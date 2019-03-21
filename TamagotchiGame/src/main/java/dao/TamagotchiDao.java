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
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author milla
 */
public class TamagotchiDao implements Dao<Tamagotchi, Integer> {

    //   public List<Tamagotchi> tamas;
    private Tamagotchi tamagotchi;

    public TamagotchiDao(Tamagotchi tamagotchi) throws Exception {
        this.tamagotchi = tamagotchi;

    }

    @Override
    public Tamagotchi create(Tamagotchi tamagotchi) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./tamagotchitietokanta", "sa", "");

        //    tamas.add(tamagotchi);
        PreparedStatement statement
                = connection.prepareStatement("INSERT INTO Tamagotchi (name, hunger, energy) VALUES (?, ?, ?)");
        statement.setString(1, tamagotchi.getName());
        statement.setInt(2, tamagotchi.getHunger());
        statement.setInt(3, tamagotchi.getEnergy());

        statement.executeUpdate();

        statement.close();

        connection.close();

        return tamagotchi;
    }

    @Override
    public Tamagotchi update(Tamagotchi tamagotchi) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:./tamagotchitietokanta", "sa", "");

        PreparedStatement statement
                = connection.prepareStatement("UPDATE Tamagotchi SET name = ?, hunger = ?, energy = ? WHERE id = ?");
        statement.setString(1, tamagotchi.getName());
        statement.setInt(2, tamagotchi.getHunger());
        statement.setInt(3, tamagotchi.getEnergy());
        statement.setInt(4, tamagotchi.getId());

        statement.executeUpdate();

        statement.close();

        connection.close();

        return tamagotchi;
    }

//    private int generateId() {
    //       return tamas.size() + 1;
    //  }
    public void alustaTietokanta() {
        try (Connection conne = DriverManager.getConnection("jdbc:h2:./tamagotchitietokanta", "sa", "")) {
            //          conn.prepareStatement("DROP TABLE Tamagotchi IF EXISTS;").executeUpdate();
            conne.prepareStatement("CREATE TABLE Tamagotchi(id serial, name varchar(25), hunger integer, energy integer);").executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TamagotchiDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
