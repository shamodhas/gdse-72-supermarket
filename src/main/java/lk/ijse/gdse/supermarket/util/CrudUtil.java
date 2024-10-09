package lk.ijse.gdse.supermarket.util;

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

public class CrudUtil {
   public static <T>T execute(String sql,Object... obj) throws SQLException {
       Connection connection = DBConnection.getInstance().getConnection();
       PreparedStatement pst = connection.prepareStatement(sql);
       for (int i=0; i<obj.length;i++){
           pst.setObject((i+1),obj[i]);
       }
       if (sql.startsWith("SELECT") || sql.startsWith("select")){
           ResultSet resultSet = pst.executeQuery();
           return (T) resultSet;
       }else {
           int i = pst.executeUpdate();
           boolean isDone = i>0;
           return (T) ((Boolean)isDone);
       }
   }
}








