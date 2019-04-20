/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author milla
 */
public class Database {

    private String address;
    private Connection connection;

    public Database(String address) {
        this.address = "jdbc:h2:"+ address;
        
    }

    public String getAddress() {
        return address;
    }

    public Connection getConnection() {
        return connection;
    }

    public Connection newConnection() throws SQLException {
        this.connection = DriverManager.getConnection(address, "sa", "");
        return this.connection;
    }


    public void closeConnection() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

}
