package lk.ijse.gdse.supermarket.model;

import lk.ijse.gdse.supermarket.dto.CustomerDTO;
import lk.ijse.gdse.supermarket.dto.ItemDTO;
import lk.ijse.gdse.supermarket.dto.OrderDetailsDTO;
import lk.ijse.gdse.supermarket.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/9/2024 1:38 PM
 * Project: Supermarket
 * --------------------------------------------
 **/

public class ItemModel {

    /**
     * @return ArrayList<String>: A list of item IDs retrieved from the database.
     * @throws SQLException: If any SQL-related error occurs during the query execution.
     * @getAllItemIds: Retrieves all item IDs from the 'item' table.
     * This method executes an SQL query to get all the item IDs, stores them in an ArrayList, and returns the list.
     **/
    public ArrayList<String> getAllItemIds() throws SQLException {
        // Execute SQL query to get all item IDs
        ResultSet rst = CrudUtil.execute("select item_id from item");

        // Create an ArrayList to store the item IDs
        ArrayList<String> itemIds = new ArrayList<>();

        // Iterate through the result set and add each item ID to the list
        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        // Return the list of item IDs
        return itemIds;
    }

    /**
     * @param selectedItemId: The ID of the item to find.
     * @return ItemDTO: Returns an ItemDTO object containing the item's details if found, otherwise returns null.
     * @throws SQLException: If any SQL-related error occurs during the query execution.
     * @findById: Finds an item by its ID.
     * This method retrieves item data for a specific item ID from the 'item' table and creates an ItemDTO object with the retrieved data.
     **/
    public ItemDTO findById(String selectedItemId) throws SQLException {
        // Execute SQL query to find the item by ID
        ResultSet rst = CrudUtil.execute("select * from item where item_id=?", selectedItemId);

        // If the item is found, create an ItemDTO object with the retrieved data
        if (rst.next()) {
            return new ItemDTO(
                    rst.getString(1),  // Item ID
                    rst.getString(2),  // Item Name
                    rst.getInt(3),     // Item Quantity
                    rst.getDouble(4)   // Item Price
            );
        }

        // Return null if the item is not found
        return null;
    }

    /**
     * @param orderDetailsDTO: The OrderDetailsDTO object containing the order details (item ID and quantity to reduce).
     * @return boolean: Returns true if the item's quantity is successfully updated, otherwise returns false.
     * @throws SQLException: If any SQL-related error occurs during the query execution.
     * @reduceQty: Reduces the quantity of an item after an order is placed.
     * This method updates the 'item' table, reducing the quantity of a specific item based on the quantity ordered.
     **/
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        // Execute SQL query to update the item quantity in the database
        return CrudUtil.execute(
                "update item set quantity = quantity - ? where item_id = ?",
                orderDetailsDTO.getQuantity(),   // Quantity to reduce
                orderDetailsDTO.getItemId()      // Item ID
        );
    }
}

