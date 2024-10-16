module lk.ijse.gdse.supermarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires com.jfoenix;
    requires net.sf.jasperreports.core;
    requires java.mail;

    opens lk.ijse.gdse.supermarket.dto.tm to javafx.base;
    opens lk.ijse.gdse.supermarket.controller to javafx.fxml;
    exports lk.ijse.gdse.supermarket;
}