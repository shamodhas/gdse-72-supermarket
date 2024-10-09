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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;
}
