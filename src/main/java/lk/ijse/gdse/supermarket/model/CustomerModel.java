package lk.ijse.gdse.supermarket.model;

import lk.ijse.gdse.supermarket.db.DBConnection;
import lk.ijse.gdse.supermarket.dto.CustomerDTO;

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
        if (rst.next()) {
            String string = rst.getString(1); // C001
            String substring = string.substring(1); // 001
            int lastIdIndex = Integer.parseInt(substring); // 1
            int nextIIndex = lastIdIndex + 1; // 2
            String newId = String.format("C%03d", nextIIndex); // C002
            return newId;
        }
        return "C001";
    }

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into customer value (?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setString(1, customerDTO.getId());
        pst.setString(2, customerDTO.getName());
        pst.setString(3, customerDTO.getNic());
        pst.setString(4, customerDTO.getEmail());
        pst.setString(5, customerDTO.getPhone());

        int i = pst.executeUpdate();

        return i > 0;
//        if ( i > 0){
//            return true;
//        }else {
//            return false;
//        }
    }
}








