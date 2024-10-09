package lk.ijse.gdse.supermarket.model;

import lk.ijse.gdse.supermarket.db.DBConnection;
import lk.ijse.gdse.supermarket.dto.CustomerDTO;
import lk.ijse.gdse.supermarket.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = ;
//        PreparedStatement pst = connection.prepareStatement(sql);

        ResultSet resultSet = CrudUtil.execute("select customer_id from customer order by customer_id desc limit  1");

//        ResultSet rst = pst.executeQuery();
        if (resultSet.next()) {
            String string = resultSet.getString(1); // C001
            String substring = string.substring(1); // 001
            int lastIdIndex = Integer.parseInt(substring); // 1
            int nextIIndex = lastIdIndex + 1; // 2
            String newId = String.format("C%03d", nextIIndex); // C002
            return newId;
        }
        return "C001";
    }

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "insert into customer value (?,?,?,?,?)";
//        PreparedStatement pst = connection.prepareStatement(sql);
//
//
//        pst.setString(1, customerDTO.getId());
//        pst.setString(2, customerDTO.getName());
//        pst.setString(3, customerDTO.getNic());
//        pst.setString(4, customerDTO.getEmail());
//        pst.setString(5, customerDTO.getPhone());
//
//        int i = pst.executeUpdate();

        boolean isSaved = CrudUtil.execute(
                "insert into customer value (?,?,?,?,?)",
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getEmail(),
                customerDTO.getPhone()
        );

        return isSaved;
//        if ( i > 0){
//            return true;
//        }else {
//            return false;
//        }
    }

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        while (rst.next()){
            CustomerDTO customerDTO = new CustomerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            customerDTOS.add(customerDTO);
        }

        return customerDTOS;
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "update customer set name=?,nic=?,email=?,phone=? where customer_id = ?",
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getEmail(),
                customerDTO.getPhone(),
                customerDTO.getId()
        );
    }

    public boolean deleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute("delete from customer where customer_id=?",customerId);
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");
        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()){
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }
}








