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
    private ItemModel itemModel = new ItemModel();
    public boolean saveOrderDetails(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException {
        for (OrderDetailsDTO dto:orderDetailsDTOS){
           boolean isODSaved =  saveOrderDetail(dto);
           if (!isODSaved){
               return false;
           }

           boolean isUpdated = itemModel.reduceQty(dto);
            if (!isUpdated){
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetail(OrderDetailsDTO dto) throws SQLException {
       return CrudUtil.execute(
                "insert into orderdetails values (?,?,?,?)",
                dto.getOrderId(),
                dto.getItemId(),
                dto.getQuantity(),
                dto.getPrice()
        );
    }
}
