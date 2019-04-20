/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Database.Database;
import domain.Tamagotchi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author milla
 */

// LUOKKA EI OLE VIELÄ TOIMINNASSA 

public class AgesDao implements Dao<Tamagotchi, Integer> {

    private Database database;

    public AgesDao(Database database) {

        this.database = database;

        alustaTietokanta();

    }
    
      @Override
    public Tamagotchi create(Tamagotchi tamagotchi) throws SQLException {

        Connection connection = database.newConnection();

        PreparedStatement statement
                = connection.prepareStatement("INSERT INTO TamagotchisAges (name,birthtime, age,alive) VALUES (?, ?, ?, ?)");

        statement.setString(1, tamagotchi.getName());
        statement.setLong(2, System.currentTimeMillis());
        statement.setInt(3, 0);
        statement.setBoolean(4, true);

        statement.executeUpdate();
        statement.close();
        connection.close();

        return tamagotchi;
    }
    
        @Override
    public Tamagotchi update(Tamagotchi tamagotchi) throws SQLException {
        Connection connection = database.newConnection();

        PreparedStatement statement
                = connection.prepareStatement("UPDATE TamagotchisAges SET age = ?, birthtime = ?, alive = ? WHERE name = ?");

        statement.setInt(1, tamagotchi.getAge());
        statement.setLong(2, tamagotchi.getDateOfBirth());
        statement.setBoolean(3, tamagotchi.isAlive());
        statement.setString(4, tamagotchi.getName());

        
        statement.executeUpdate();
        statement.close();
        connection.close();

        return tamagotchi;
    }
    
      public ArrayList list() throws Exception {
        ArrayList list = new ArrayList();
        Connection connection = database.newConnection();

        PreparedStatement statement
                = connection.prepareStatement("SELECT * FROM TamagotchisAges;");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            long birthtime = resultSet.getLong("birthtime"); //
            String date =DateFormat.getInstance().format(birthtime);
            int age = resultSet.getInt("age");
            boolean alive = resultSet.getBoolean("alive");
            String tamagotchi = name + ", synt. " + date + ", ikä: " + age + " päivää";
            
            list.add(tamagotchi);

        }
        return list;
    }

    public void alustaTietokanta() {
        try (Connection conne = database.newConnection()) {
         
            conne.prepareStatement("CREATE TABLE IF NOT EXISTS TamagotchisAges(name varchar(25),birthtime long, age Integer, alive boolean);")
                    .executeUpdate();
            conne.close();
        } catch (SQLException ex) {
            Logger.getLogger(TamagotchiDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
