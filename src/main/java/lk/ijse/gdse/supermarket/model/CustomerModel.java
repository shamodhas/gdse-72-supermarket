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

    /**
     * @return String: The next customer ID.
     * @throws SQLException: If any SQL-related error occurs during the query execution.
     * @getNextCustomerId: Generates the next customer ID.
     * This method retrieves the last customer ID from the database, increments it, and returns the next available customer ID in the format "C001", "C002", etc.
     **/
    public String getNextCustomerId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("C%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "C001"; // Return the default customer ID if no data is found
    }

    /**
     * @param customerDTO: The CustomerDTO object containing customer data.
     * @return boolean: Returns true if the customer is successfully saved, otherwise false.
     * @throws SQLException: If any SQL-related error occurs during the query execution.
     * @saveCustomer: Saves a new customer to the database.
     * This method executes an SQL INSERT query to save customer details into the 'customer' table.
     **/
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into customer values (?,?,?,?,?)",
                customerDTO.getId(),
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getEmail(),
                customerDTO.getPhone()
        );
    }

    /**
     * @return ArrayList<CustomerDTO>: A list of customers retrieved from the database.
     * @throws SQLException: If any SQL-related error occurs during the query execution.
     * @getAllCustomers: Retrieves all customers from the database.
     * This method executes an SQL SELECT query to fetch all customer records and returns them as a list of CustomerDTO objects.
     **/
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDTO customerDTO = new CustomerDTO(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5)   // Phone
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    /**
     * @param customerDTO: The CustomerDTO object containing updated customer data.
     * @return boolean: Returns true if the customer details are successfully updated, otherwise false.
     * @throws SQLException: If any SQL-related error occurs during the query execution.
     * @updateCustomer: Updates customer details in the database.
     * This method executes an SQL UPDATE query to modify an existing customer's information.
     **/
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "update customer set name=?, nic=?, email=?, phone=? where customer_id=?",
                customerDTO.getName(),
                customerDTO.getNic(),
                customerDTO.getEmail(),
                customerDTO.getPhone(),
                customerDTO.getId()
        );
    }

    /**
     * @param customerId: The ID of the customer to be deleted.
     * @return boolean: Returns true if the customer is successfully deleted, otherwise false.
     * @throws SQLException: If any SQL-related error occurs during the query execution.
     * @deleteCustomer: Deletes a customer from the database.
     * This method executes an SQL DELETE query to remove a customer by their ID.
     **/
    public boolean deleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute("delete from customer where customer_id=?", customerId);
    }

    /**
     * @return ArrayList<String>: A list of customer IDs retrieved from the database.
     * @throws SQLException: If any SQL-related error occurs during the query execution.
     * @getAllCustomerIds: Retrieves all customer IDs from the database.
     * This method executes an SQL SELECT query to fetch all customer IDs and returns them as a list.
     **/
    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }

    /**
     * @param selectedCusId: The ID of the customer to find.
     * @return CustomerDTO: Returns a CustomerDTO object containing the customer's details if found, otherwise returns null.
     * @throws SQLException: If any SQL-related error occurs during the query execution.
     * @findById: Finds a customer by their ID.
     * This method retrieves customer data for a specific customer ID from the 'customer' table and creates a CustomerDTO object with the retrieved data.
     **/
    public CustomerDTO findById(String selectedCusId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer where customer_id=?", selectedCusId);

        if (rst.next()) {
            return new CustomerDTO(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5)   // Phone
            );
        }
        return null;
    }
}






