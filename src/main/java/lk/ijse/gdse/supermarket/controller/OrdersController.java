package lk.ijse.gdse.supermarket.controller;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/1/2024 2:12 PM
 * Project: Supermarket
 * --------------------------------------------
 **/


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.supermarket.dto.CustomerDTO;
import lk.ijse.gdse.supermarket.dto.ItemDTO;
import lk.ijse.gdse.supermarket.dto.OrderDTO;
import lk.ijse.gdse.supermarket.dto.OrderDetailsDTO;
import lk.ijse.gdse.supermarket.dto.tm.CartTM;
import lk.ijse.gdse.supermarket.model.CustomerModel;
import lk.ijse.gdse.supermarket.model.ItemModel;
import lk.ijse.gdse.supermarket.model.OrderModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<CartTM, String> colItemId;

    @FXML
    private TableColumn<CartTM, String> colName;

    @FXML
    private TableColumn<CartTM, Double> colPrice;

    @FXML
    private TableColumn<CartTM, Integer> colQuantity;

    @FXML
    private TableColumn<CartTM, Double> colTotal;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblItemPrice;

    @FXML
    private Label lblItemQty;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label orderDate;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private TextField txtAddToCartQty;

    private final OrderModel orderModel = new OrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ItemModel itemModel = new ItemModel();

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    private void refreshPage() throws SQLException {
        lblOrderId.setText(orderModel.getNextOrderId());
//        orderDate.setText(String.valueOf(LocalDate.now()));
        orderDate.setText(LocalDate.now().toString());

        loadCustomerIds();
        loadItemId();
    }

    private void loadItemId() throws SQLException {
        ArrayList<String> itemIds = itemModel.getAllItemIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(itemIds);
        cmbItemId.setItems(observableList);
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerModel.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbCustomerId.setItems(observableList);
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedItem = cmbItemId.getValue();
        String itemName = lblItemName.getText();
        int cartQty = Integer.parseInt(txtAddToCartQty.getText());
        double unitPrice = Double.parseDouble(lblItemPrice.getText());
        double total = unitPrice * cartQty;
        Button btn = new Button("Remove");

        if (!cartTMS.isEmpty()){
            for (int i=0; i<tblCart.getItems().size();i++){
                if (colItemId.getCellData(i).equals(selectedItem)){
//                    cartQty  = cartQty + colQuantity.getCellData(i);
                    cartQty  += colQuantity.getCellData(i);
                    total = unitPrice*cartQty;

                    cartTMS.get(i).setCartQuantity(cartQty);
                    cartTMS.get(i).setTotal(total);

                    tblCart.refresh();
                    return;
                }
            }
        }

        CartTM cartTM = new CartTM(
                selectedItem,
                itemName,
                cartQty,
                unitPrice,
                total,
                btn
        );

        btn.setOnAction(actionEvent -> {
            cartTMS.remove(cartTM);
            tblCart.refresh();
        });

        cartTMS.add(cartTM);
        tblCart.setItems(cartTMS);

        txtAddToCartQty.setText("");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        if (tblCart.getItems().isEmpty()){
            return;
        }
        if (cmbCustomerId.getSelectionModel().isEmpty()){
            return;
        }

        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(orderDate.getText());
        String customerId = cmbCustomerId.getValue();

        ArrayList<OrderDetailsDTO> orderDetailsDTOS=new ArrayList<>();
        for (int i=0;i<tblCart.getItems().size();i++){
            CartTM cartTM = tblCart.getItems().get(i);
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    orderId,
                    cartTM.getItemId(),
                    cartTM.getCartQuantity(),
                    cartTM.getUnitPrice()
            );
            orderDetailsDTOS.add(orderDetailsDTO);
        }

        OrderDTO orderDTO = new OrderDTO(
                orderId,
                customerId,
                dateOfOrder,
                orderDetailsDTOS
        );

        boolean isSaved = orderModel.saveOrder(orderDTO);

        if (isSaved){
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION,"Order saved..!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Order fail..!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerModel.findByCustomerId(selectedCustomerId);
        lblCustomerName.setText(customerDTO.getName());
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        ItemDTO itemDTO = itemModel.findByItemId(selectedItemId);
        lblItemName.setText(itemDTO.getName());
        lblItemQty.setText(String.valueOf(itemDTO.getQuantity()));
        lblItemPrice.setText(String.valueOf(itemDTO.getPrice()));
    }

}

