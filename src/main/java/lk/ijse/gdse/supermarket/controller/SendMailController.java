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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailController {

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtSubject;

    // Field to set the recipient's email address
    // Lombok @Setter is used to auto-generate a setter method
    @Setter
    private String customerEmail;

    // you can use forget password for this
    // UUID - generate random unique id

    /* Use only one method for sending emails ((1) or (2)) */
    // Send Email in Java SMTP with TLS Authentication

    // (1) Gmail with app password (need Gmail 2FA)
    // Call the method to send an email via Gmail
    // Using your gmail account
    // You must enable two-factor authentication

    // (2) Sendgrid with api key (no need Gmail 2FA)
    // Call the method to send an email via SendGrid
    // You must create sendgrid account

    @FXML
    public void sendUsingSendgridOnAction(ActionEvent actionEvent) {
        if (customerEmail == null) {
            return;
        }

        // The sender's email address
        final String FROM = "replace-your-email";

        // Get the subject and body from the text fields
        String subject = txtSubject.getText();
        String body = txtBody.getText();

        // Check if subject or body is empty; show a warning if they are
        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
            return;
        }

        // you can use forget password for this
        // UUID - generate random unique id

        /* Without gmail 2fa */
        // Send Email in Java SMTP with TLS Authentication

        // (2) Sendgrid with api key
        // Call the method to send an email via SendGrid
        // You must create sendgrid account
        sendEmailWithSendgrid(FROM, customerEmail, subject, body);
    }

    /**
     * Method to send an email using SendGrid's email service.
     *
     * @param from    the sender's email address.
     * @param to      the recipient's email address.
     * @param subject the subject of the email.
     * @param body    the body text of the email.
     */
    private void sendEmailWithSendgrid(String from, String to, String subject, String body) {
        // SendGrid username must always be 'apikey' for authentication
        String USER_NAME = "apikey"; // don't change this

        // Replace this with your actual SendGrid API key (watch document)
        String PASSWORD = "replace-your-sendgrid-api-key";

        // Create a new Properties object to hold configuration settings for the SMTP (Simple Mail Transfer Protocol) connection
        Properties props = new Properties();

        // Enable SMTP authentication. This requires the server to authenticate the sender before allowing emails to be sent.
        props.put("mail.smtp.auth", "true");

        // Enable STARTTLS, which upgrades a plain text connection to a secure TLS (Transport Layer Security) connection.
        // This ensures that the email is encrypted when transmitted over the network.
        props.put("mail.smtp.starttls.enable", "true");

        // Specify the SMTP host server for SendGrid. This is the domain of the SMTP server through which the emails will be sent.
        props.put("mail.smtp.host", "smtp.sendgrid.net");

        // Specify the port number for the SMTP server. Port 587 is typically used for sending emails with TLS encryption (STARTTLS).
        // Alternatively, port 465 can be used for SSL encryption (direct encrypted connection).
        props.put("mail.smtp.port", "587");

        // Allow the SMTP client to trust SSL connections to the SendGrid server.
        // This ensures that the client accepts the SSL certificate provided by the SMTP server.
        props.put("mail.smtp.ssl.trust", "smtp.sendgrid.net");

        // Create a new session with the SMTP server using the configured properties.
        // The Authenticator is used to authenticate the sender's email using their email address (`from`) and app password (`PASSWORD`).
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {

                // Authenticate using the sender's email and app password
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        });

        try {
            // Instantiate a new MimeMessage object using the session created earlier.
            // This object represents the email that will be sent, including all of its content.
            Message message = new MimeMessage(session);

            // Set the sender's email address using the `InternetAddress` class.
            message.setFrom(new InternetAddress(from));

            // Set the recipient(s) of the email.
            // `Message.RecipientType.TO` defines that this is the primary recipient (To field).
            // The `InternetAddress.parse(to)` method is used to handle multiple recipients if needed, separating them with commas.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set the subject line of the email.
            message.setSubject(subject);

            // Set the body of the email.
            message.setText(body);

            // Use the `Transport.send()` method to send the email.
            // This method handles the communication with the SMTP server and sends the email based on the previously defined session and message content.
            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email.").show();
        }
    }


    @FXML
    public void sendUsingGmailOnAction(ActionEvent actionEvent) {
        if (customerEmail == null) {
            return;
        }

        // The sender's email address
        final String FROM = "replace-your-email";

        // Get the subject and body from the text fields
        String subject = txtSubject.getText();
        String body = txtBody.getText();

        // Check if subject or body is empty; show a warning if they are
        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
            return;
        }

        // you can use forget password for this
        // UUID - generate random unique id

        /* Use only one method for sending emails ((1) or (2)) */
//        Send Email in Java SMTP with TLS Authentication

        // (1) Gmail with app password (need Gmail 2FA)
        // Call the method to send an email via Gmail
        // Using your gmail account
        // You must enable two-factor authentication
        sendEmailWithGmail(FROM, customerEmail, subject, body);

    }

    /**
     * Method to send an email using Gmail's email service.
     *
     * @param from        the sender's email address.
     * @param to          the recipient's email address.
     * @param subject     the subject of the email.
     * @param messageBody the body text of the email.
     */
    private void sendEmailWithGmail(String from, String to, String subject, String messageBody) {
        // Replace this string with your Gmail app-specific password.
        // You must generate an app password if you have two-factor authentication (2FA) enabled on Gmail.
        String PASSWORD = "replace-your-app-password"; // (watch document)

        // Create a new Properties object to hold configuration settings for the SMTP (Simple Mail Transfer Protocol) connection
        Properties props = new Properties();

        // Enable SMTP authentication. This requires the server to authenticate the sender before sending emails.
        props.put("mail.smtp.auth", "true");

        // Enable STARTTLS, which upgrades an insecure connection to a secure one (TLS encryption).
        props.put("mail.smtp.starttls.enable", "true");

        // Specify the SMTP server host. For Gmail, it is "smtp.gmail.com".
        props.put("mail.smtp.host", "smtp.gmail.com");

        // Specify the port to use for SMTP. Port 587 is used for TLS connections. Alternatively, port 465 can be used for SSL.
        props.put("mail.smtp.port", "587");

        // Create a new session with the SMTP server using the configured properties.
        // The Authenticator is used to authenticate the sender's email using their email address (`from`) and app password (`PASSWORD`).
        Session session = Session.getInstance(props, new Authenticator() {

            // Replace with your email and app password
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, PASSWORD); // Replace with your email and password
            }
        });

        try {
            // Create a new MimeMessage object to represent the email message.
            Message message = new MimeMessage(session);

            // Set the sender's email address using the `from` parameter.
            message.setFrom(new InternetAddress(from));

            // Set the recipient(s) of the email. The `to` parameter is parsed to handle multiple recipients, if necessary.
            // `Message.RecipientType.TO` defines that this is the primary recipient (To field).
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set the subject of the email using the `subject` parameter.
            message.setSubject(subject);

            // Set the body (content) of the email using the `messageBody` parameter.
            message.setText(messageBody);

            // Send the email message using the `Transport.send()` method.
            // This sends the email through the SMTP server configured in the session.
            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email.").show();
        }
    }
}
