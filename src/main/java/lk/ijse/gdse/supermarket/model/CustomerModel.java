package lk.ijse.gdse.supermarket.model;

import lk.ijse.gdse.supermarket.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/1/2024 4:08 PM
 * Project: Supermarket
 * --------------------------------------------
 **/

public class CustomerModel {
    public String getNextCustomerID() throws SQLException {
        // C001
        // C002
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select customer_id from customer order by customer_id desc limit  1";
        PreparedStatement pst = connection.prepareStatement(sql);

        ResultSet rst = pst.executeQuery();
        if (rst.next()){
            String string = rst.getString(1); // C001
            String substring = string.substring(1); // 001
            int lastIdIndex = Integer.parseInt(substring); // 1
            int nextIIndex = lastIdIndex+1; // 2
            String newId = String.format("C%03d", nextIIndex); // C002
            return newId;
        }
        return "C001";
    }
}








