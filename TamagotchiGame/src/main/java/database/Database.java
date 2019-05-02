package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Tietokantayhteyksiä hallinnoiva luokka.
 */
public class Database {

    private String address;
    private Connection connection;

    /**
     * Luo uuden tietokantaolion.
     *
     * @param address tietokannan osoite
     */
    public Database(String address) {
        this.address = "jdbc:h2:" + address;
    }

    public String getAddress() {
        return address;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Luo uuden tietokantayhteyden osoitteeseen, joka on konstruktorissa.
     *
     * @return palauttaa uuden tietokantayhteyden
     *
     * @throws SQLException virhe tietokantayhteydessä
     */
    public Connection newConnection() throws SQLException {
        this.connection = DriverManager.getConnection(address, "sa", "");
        return this.connection;
    }

}
