package lk.ijse.gdse.supermarket.model;

import lk.ijse.gdse.supermarket.dto.CustomerDTO;
import lk.ijse.gdse.supermarket.dto.ItemDTO;
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
    public ArrayList<String> getAllItemIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select item_id from item");
        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()){
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }

    public ItemDTO findByItemId(String selectedItemId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from item where item_id=?"
                ,selectedItemId
        );

        if (rst.next()){
            ItemDTO itemDTO = new ItemDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );

            return itemDTO;
        }
        return null;
    }
}
