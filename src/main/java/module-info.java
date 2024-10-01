module lk.ijse.gdse.supermarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;

    opens lk.ijse.gdse.supermarket.controller to javafx.fxml;
    exports lk.ijse.gdse.supermarket;
}