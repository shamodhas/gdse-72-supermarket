package lk.ijse.gdse.supermarket.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/9/2024 3:57 PM
 * Project: Supermarket
 * --------------------------------------------
 **/

// Lombok annotations to generate methods automatically and reduce boilerplate code
@Getter                 // @Getter: Automatically generates getter methods for all fields
@Setter                 // @Setter: Automatically generates setter methods for all fields
@AllArgsConstructor     // @AllArgsConstructor: Generates a constructor with all fields as parameters
@NoArgsConstructor      // @NoArgsConstructor: Generates a no-argument constructor
@ToString               // @ToString: Automatically generates a string representation of the object

/**
 * @OrderDTO: Data Transfer Object for representing an order.
 * This object is used to transfer order data between the controller and model layers.
 * It encapsulates order-related information like order ID, customer ID, order date, and order details.
 */
public class OrderDTO {

    private String orderId;
    private String customerId;
    private Date orderDate;

    // @orderDetailsDTOS: A list of OrderDetailsDTO objects, each representing an item in the order
    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;
}
