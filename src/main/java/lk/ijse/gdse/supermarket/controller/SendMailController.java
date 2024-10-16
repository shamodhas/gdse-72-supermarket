package lk.ijse.gdse.supermarket.controller;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/16/2024 2:28 PM
 * Project: Supermarket-72
 * --------------------------------------------
 **/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailController {

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtSubject;

    @Setter
    private String customerEmail;

    @FXML
    void sendOnAction(ActionEvent event) {
        System.out.println(customerEmail);
        String subject = txtSubject.getText();
        String body = txtBody.getText();

        // forget password
        // UUID

        String from = "shamodha7@gmail.com";
        String host = "smtp.gmail.com";
        String username = "shamodha7@gmail.com";
        String password = "dxij ckvh zeuy agpg";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set the From field
            message.setFrom(new InternetAddress(from));

            // Set the To field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerEmail));

            // Set the Subject field
            message.setSubject(subject);

            // Set the message
            message.setText(body);

            // Send the message
            Transport.send(message);

            // Show success alert
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();

        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email.").show();
        }
    }

}

