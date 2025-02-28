module lk.ijse.gdse.supermarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.jfoenix;
    requires net.sf.jasperreports.core;
    requires java.mail;

    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens lk.ijse.gdse.supermarket.config to jakarta.persistence;
    opens lk.ijse.gdse.supermarket.entity to org.hibernate.orm.core;

    opens lk.ijse.gdse.supermarket.dto.tm to javafx.base;
    opens lk.ijse.gdse.supermarket.controller to javafx.fxml;

    exports lk.ijse.gdse.supermarket;
}