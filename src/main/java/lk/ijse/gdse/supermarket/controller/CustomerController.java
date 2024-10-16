package lk.ijse.gdse.supermarket.controller;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/1/2024 2:11 PM
 * Project: Supermarket
 * --------------------------------------------
 **/

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.supermarket.dto.CustomerDTO;
import lk.ijse.gdse.supermarket.dto.tm.CustomerTM;
import lk.ijse.gdse.supermarket.model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomerController implements Initializable {

    @FXML
    public JFXTextField nameField;

    @FXML
    private TableColumn<CustomerTM, String> colCustomerId;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colNic;

    @FXML
    private TableColumn<CustomerTM, String> colPhone;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPhone;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    CustomerModel customerModel = new CustomerModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
            refreshPage();
//            String nextCustomerID = customerModel.getNextCustomerID();
//            System.out.println(nextCustomerID);
//            lblCustomerId.setText(nextCustomerID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextCustomerID = customerModel.getNextCustomerId();
        lblCustomerId.setText(nextCustomerID);

        txtName.setText("");
        txtNic.setText("");
        txtEmail.setText("");
        txtPhone.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<CustomerDTO> customerDTOS = customerModel.getAllCustomers();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

//        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();
//        customerTMS.addAll(customerModel.getAllCustomer().stream().map(customerDTO->{
//            return new CustomerTM(
//                    customerDTO.getId(),
//                    customerDTO.getName(),
//                    customerDTO.getNic(),
//                    customerDTO.getEmail(),
//                    customerDTO.getPhone()
//            );
//        }).collect(Collectors.toList()));
//        tblCustomer.setItems(customerTMS);


        for (CustomerDTO customerDTO:customerDTOS){
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getId(),
                    customerDTO.getName(),
                    customerDTO.getNic(),
                    customerDTO.getEmail(),
                    customerDTO.getPhone()
            );
            customerTMS.add(customerTM);
        }
        tblCustomer.setItems(customerTMS);
    }

    @FXML
    void btnSaveCustomerOnAction(ActionEvent event) throws SQLException {
        String id = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

//        Pattern compile = Pattern.compile(namePattern);
//        System.out.println(compile.matcher(name).matches());
        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        txtName.setStyle(txtName.getStyle()+";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle()+";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle()+";-fx-border-color: #7367F0;");

        if (!isValidName){
            txtName.setStyle(txtName.getStyle()+";-fx-border-color: red;");
        }

        if (!isValidNic){
            txtNic.setStyle(txtNic.getStyle()+";-fx-border-color: red;");
        }

        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: red;");
        }

        if (!isValidPhone){
            txtPhone.setStyle(txtPhone.getStyle()+";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidEmail && isValidPhone){
            CustomerDTO customerDTO = new CustomerDTO(id, name, nic, email, phone);

            boolean isSaved = customerModel.saveCustomer(customerDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save customer...!").show();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String customerId = lblCustomerId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES){

            boolean isDeleted = customerModel.deleteCustomer(customerId);

            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Customer deleted...!").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail to delete customer...!").show();
            }
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String id = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

//        Pattern compile = Pattern.compile(namePattern);
//        System.out.println(compile.matcher(name).matches());
        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);

        txtName.setStyle(txtName.getStyle()+";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle()+";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle()+";-fx-border-color: #7367F0;");

        if (!isValidName){
            txtName.setStyle(txtName.getStyle()+";-fx-border-color: red;");
        }

        if (!isValidNic){
            txtNic.setStyle(txtNic.getStyle()+";-fx-border-color: red;");
        }

        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle()+";-fx-border-color: red;");
        }

        if (!isValidPhone){
            txtPhone.setStyle(txtPhone.getStyle()+";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidEmail && isValidPhone) {

            CustomerDTO customerDTO = new CustomerDTO(id, name, nic, email, phone);

            boolean isUpdate = customerModel.updateCustomer(customerDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            lblCustomerId.setText(selectedItem.getId());
            txtName.setText(selectedItem.getName());
            txtNic.setText(selectedItem.getNic());
            txtEmail.setText(selectedItem.getEmail());
            txtPhone.setText(selectedItem.getPhone());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

}
