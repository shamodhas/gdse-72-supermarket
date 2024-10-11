package lk.ijse.gdse.supermarket.model;

import lk.ijse.gdse.supermarket.dto.OrderDetailsDTO;
import lk.ijse.gdse.supermarket.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/9/2024 4:10 PM
 * Project: Supermarket
 * --------------------------------------------
 **/

public class OrderDetailsModel {

    // @itemModel: Reference to the ItemModel, used to update the item quantity after saving order details
    private final ItemModel itemModel = new ItemModel();

    /**
     * @param orderDetailsDTOS: A list of OrderDetailsDTO objects representing the order details to be saved.
     * @return boolean: Returns true if all order details are saved and item quantities are updated, otherwise false.
     * @throws SQLException: If any SQL-related issues occur during the save or update process.
     * @saveOrderDetailsList: Saves a list of order details and updates item quantities accordingly.
     * This method iterates through the list of OrderDetailsDTO objects, saves each order detail,
     * and reduces the item quantity in the stock for each item.
     **/
    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException {
        // Iterate through each order detail in the list
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            // @isOrderDetailsSaved: Saves the individual order detail
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                // Return false if saving any order detail fails
                return false;
            }

            // @isItemUpdated: Updates the item quantity in the stock for the corresponding order detail
            boolean isItemUpdated = itemModel.reduceQty(orderDetailsDTO);
            if (!isItemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        // Return true if all order details are saved and item quantities updated successfully
        return true;
    }

    /**
     * @param orderDetailsDTO: The OrderDetailsDTO object containing the details of the order to be saved.
     * @return boolean: Returns true if the order detail is saved successfully, otherwise false.
     * @throws SQLException: If any SQL-related issues occur during the execution of the query.
     * @saveOrderDetail: Saves an individual order detail in the orderdetails table in the database.
     **/
    private boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        // Executes an insert query to save the order detail into the database
        return CrudUtil.execute(
                "insert into orderdetails values (?,?,?,?)",
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getItemId(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );
    }
}
