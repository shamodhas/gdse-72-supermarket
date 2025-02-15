package lk.ijse.gdse.supermarket.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/1/2024 3:49 PM
 * Project: Supermarket
 * --------------------------------------------
 **/

@Getter
public class DBConnection {
    private static DBConnection instance;

    private final Connection connection;

    private DBConnection() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/Supermarketfx?createDatabaseIfNotExist=true";
        String USER = "root";
        String PASSWORD = "1234";
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static DBConnection getInstance() throws SQLException {
        return instance == null ? instance = new DBConnection() : instance;
    }

    // Lombok usage
//    public Connection getConnection(){
//        return connection;
//    }
}










