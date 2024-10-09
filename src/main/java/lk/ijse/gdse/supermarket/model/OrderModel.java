package lk.ijse.gdse.supermarket.model;

import lk.ijse.gdse.supermarket.db.DBConnection;
import lk.ijse.gdse.supermarket.dto.OrderDTO;
import lk.ijse.gdse.supermarket.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/9/2024 12:15 PM
 * Project: Supermarket
 * --------------------------------------------
 **/

public class OrderModel {
    private OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
    public String getNextOrderId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select order_id from orders order by order_id desc limit 1");

        if (resultSet.next()) {
            String string = resultSet.getString(1); // C001
            String substring = string.substring(1); // 001
            int lastIdIndex = Integer.parseInt(substring); // 1
            int nextIIndex = lastIdIndex + 1; // 2
            String newId = String.format("O%03d", nextIIndex); // C002
            return newId;
        }
        return "O001";
    }

    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isOrderSaved = CrudUtil.execute(
                    "insert into orders values (?,?,?)",
                    orderDTO.getOrderId(),
                    orderDTO.getCustomerId(),
                    orderDTO.getOrderDate()
            );
            if (isOrderSaved){
                boolean isOrderDetailsSaved = orderDetailsModel
                        .saveOrderDetails(orderDTO.getOrderDetailsDTOS());
                if (isOrderDetailsSaved){
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
