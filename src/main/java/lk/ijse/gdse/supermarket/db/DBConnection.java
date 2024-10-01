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
    private static DBConnection dbConnection;
    private final Connection connection;
    private final String URL ="jdbc:mysql://localhost:3306/Supermarketfx";
    private final String USER ="root";
    private final String PASSWORD ="1234";

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(URL,USER,PASSWORD);
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbConnection==null){
            dbConnection=new DBConnection();
        }
        return dbConnection;
    }

//    public Connection getConnection(){
//        return connection;
//    }
}










