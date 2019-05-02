/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import database.Database;
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
 * Luokka tamagotchin ikien ja syntymäpäivien sekä kaikkien olemassaolleiden
 * tamagotchien tietokannanhallintaa varten. Luokka käyttää tietokannan
 * TamagotchisAges -taulua.
 */
public class AgesDao implements Dao<Tamagotchi, Integer> {

    private Database database;

    public AgesDao(Database database) {

        this.database = database;

        createTable();

    }

    /**
     * Luo tietokantaan uuden tamagotchin, jossa pidetään kirjaa
     * syntymäpäivästä ja iästä sekä elossaolosta.
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

    /**
     * Päivittää tietokantaan tamagotchin ikää ja tietoa siitä, onko se vielä
     * elossa.
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

    /**
     * Hakee tietokannasta tamagotchin nimen, syntymäpäivän, iän ja tiedon onko
     * se elossa ja lisää ne ArrayListiin.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     * 
     * @return lista, jossa tamagotchien historiatietoja
     */
    public ArrayList list() throws SQLException {
        ArrayList list = new ArrayList();
        Connection connection = database.newConnection();

        PreparedStatement statement
                = connection.prepareStatement("SELECT name, birthtime, age, alive FROM TamagotchisAges;");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            long birthtime = resultSet.getLong("birthtime"); //
            String date = DateFormat.getInstance().format(birthtime);
            int age = resultSet.getInt("age");
            boolean alive = resultSet.getBoolean("alive");
            String tamagotchi = name + ", synt. " + date + ", ikä: " + age + " päivää. Elossa: " + alive;

            list.add(tamagotchi);

        }
        connection.close();
        return list;
    }

    /**
     * Hakee tietokannasta tamagotchien nimet, jotka ovat koskaan olleet
     * olemassa.
     *
     * @throws SQLException virhe tietokannanhallinnassa
     * 
     * @return lista, jossa tamagotchien nimet
     */
    public ArrayList names() throws SQLException {
        ArrayList list = new ArrayList();
        Connection connection = database.newConnection();

        PreparedStatement statement
                = connection.prepareStatement("SELECT name FROM TamagotchisAges;");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            list.add(name);

        }
        connection.close();
        return list;
    }

    /**
     * Luo tietokantataulun TamagotchisAges, mikäli taulua ei löydy ennestään.
     */
    public void createTable() {
        try (Connection conne = database.newConnection()) {

            conne.prepareStatement("CREATE TABLE IF NOT EXISTS TamagotchisAges(name varchar(25),birthtime long, age Integer, alive boolean);")
                    .executeUpdate();
            conne.close();
        } catch (SQLException ex) {
            Logger.getLogger(TamagotchiDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
