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

    // FXML controls to interact with the GUI elements
    @FXML
    private ComboBox<String> cmbCustomerId;
    @FXML
    private ComboBox<String> cmbItemId;
    @FXML
    private TableView<CartTM> tblCart;
    @FXML
    private TableColumn<CartTM, String> colItemId;
    @FXML
    private TableColumn<CartTM, String> colName;
    @FXML
    private TableColumn<CartTM, Integer> colQuantity;
    @FXML
    private TableColumn<CartTM, Double> colPrice;
    @FXML
    private TableColumn<CartTM, Double> colTotal;
    @FXML
    private TableColumn<?, ?> colAction;
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
    private TextField txtAddToCartQty;

    // Models to manage data interactions with database or logic layers
    private final OrderModel orderModel = new OrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ItemModel itemModel = new ItemModel();

    // Observable list to manage cart items in TableView
    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    /**
     * This method is called to initialize the controller after its root element has been completely processed.
     * It sets up the table columns, loads data into the ComboBoxes, and initializes the page.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       setCellValues();

        // Load data and initialize the page
        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    private void setCellValues() {
        // Set up the table columns with property values from CartTM class
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        // Bind the cart items observable list to the TableView
        tblCart.setItems(cartTMS);
    }

    /**
     * This method is responsible for resetting the form, clearing the cart,
     * and loading fresh data such as customer and item IDs.
     */
    private void refreshPage() throws SQLException {
        // Get the next order ID and set it to the label
        lblOrderId.setText(orderModel.getNextOrderId());

        // Set the current date to the order date label
//        orderDate.setText(String.valueOf(LocalDate.now()));
        orderDate.setText(LocalDate.now().toString());

        // Load customer and item IDs into ComboBoxes
        loadCustomerIds();
        loadItemId();

//        ComboBox on action set
//        cmbCustomerId.setOnAction(e->{
//            String selectedCusId = cmbCustomerId.getSelectionModel().getSelectedItem();
//            System.out.println(selectedCusId);
//        });

        // Clear selected customer, item, and their associated labels
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        lblItemName.setText("");
        lblItemQty.setText("");
        lblItemPrice.setText("");
        txtAddToCartQty.setText("");
        lblCustomerName.setText("");

        // Clear the cart observable list
        cartTMS.clear();

        // Refresh the table to reflect changes
        tblCart.refresh();
    }

    /**
     * Load all item IDs into the item ComboBox.
     */
    private void loadItemId() throws SQLException {
        ArrayList<String> itemIds = itemModel.getAllItemIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(itemIds);
        cmbItemId.setItems(observableList);
    }

    /**
     * Load all customer IDs into the customer ComboBox.
     */
    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerModel.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbCustomerId.setItems(observableList);
    }

    /**
     * Handles the event when the "Add to Cart" button is clicked.
     * This method collects data such as the item ID, name, quantity, and price, and adds the selected item to the cart.
     * If the item already exists in the cart, it updates the quantity and total price. It also adds a "Remove" button for each item in the cart.
     *
     * @param event The ActionEvent triggered by clicking the "Add to Cart" button.
     */
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedItemId = cmbItemId.getValue();

        // If no item is selected, show an error alert and return
        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select item..!").show();
            return; // Exit the method if no item is selected.
        }

        String quantityPattern = "^[0-9]+$";
//        String pricePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
//        right :- 500.00. 500.65, 500,
//        wrong :- 787.8, 6777.9999

        boolean isValidQty = txtAddToCartQty.getText().matches(quantityPattern);

        if (!isValidQty){
            new Alert(Alert.AlertType.ERROR,"Invalid qty").show();
            return;
        }

        String itemName = lblItemName.getText();
        int cartQty = Integer.parseInt(txtAddToCartQty.getText());
        int qtyOnHand = Integer.parseInt(lblItemQty.getText());

        // Check if there are enough items in stock; if not, show an error alert and return
        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough items..!").show();
            return; // Exit the method if there's insufficient stock.
        }

        // Clear the text field for adding quantity after retrieving the input value.
        txtAddToCartQty.setText("");

        double unitPrice = Double.parseDouble(lblItemPrice.getText());
        double total = unitPrice * cartQty;

        // Loop through each item in cart's observable list.
        for (CartTM cartTM : cartTMS) {

            // Check if the item is already in the cart
            if (cartTM.getItemId().equals(selectedItemId)) {
                // Update the existing CartTM object in the cart's observable list with the new quantity and total.
                int newQty = cartTM.getCartQuantity() + cartQty;
                cartTM.setCartQuantity(newQty); // Add the new quantity to the existing quantity in the cart.
                cartTM.setTotal(unitPrice * newQty); // Recalculate the total price based on the updated quantity

                // Refresh the table to display the updated information.
                tblCart.refresh();
                return; // Exit the method as the cart item has been updated.
            }
        }


        // Create a "Remove" button for the item to allow it to be removed from the cart later.
        Button btn = new Button("Remove");

        // If the item does not already exist in the cart, create a new CartTM object to represent it.
        CartTM newCartTM = new CartTM(
                selectedItemId,
                itemName,
                cartQty,
                unitPrice,
                total,
                btn
        );

        // Set an action for the "Remove" button, which removes the item from the cart when clicked.
        btn.setOnAction(actionEvent -> {

            // Remove the item from the cart's observable list (cartTMS).
            cartTMS.remove(newCartTM);

            // Refresh the table to reflect the removal of the item.
            tblCart.refresh();
        });

        // Add the newly created CartTM object to the cart's observable list.
        cartTMS.add(newCartTM);
    }

    /**
     * This method handles placing the order.
     * It collects cart data, creates an OrderDTO,
     * and saves the order to the database using the OrderModel.
     */
    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add items to cart..!").show();
            return;
        }
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place order..!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(orderDate.getText());
        String customerId = cmbCustomerId.getValue();

        // List to hold order details
        ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();

        // Collect data for each item in the cart and add to order details array
        for (CartTM cartTM : cartTMS) {

            // Create order details for each cart item
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    orderId,
                    cartTM.getItemId(),
                    cartTM.getCartQuantity(),
                    cartTM.getUnitPrice()
            );

            // Add to order details array
            orderDetailsDTOS.add(orderDetailsDTO);
        }

        // Create an OrderDTO with relevant order data
        OrderDTO orderDTO = new OrderDTO(
                orderId,
                customerId,
                dateOfOrder,
                orderDetailsDTOS
        );

        boolean isSaved = orderModel.saveOrder(orderDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order saved..!").show();

            // Reset the page after placing the order
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order fail..!").show();
        }
    }

    /**
     * This method for refresh page.
     */
    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    /**
     * This method is called when a customer is selected from the customer ComboBox.
     * It retrieves and displays the customer's name based on the selected ID.
     */
    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerModel.findById(selectedCustomerId);

        // If customer found (customerDTO not null)
        if (customerDTO != null) {

            // FIll customer related labels
            lblCustomerName.setText(customerDTO.getName());
        }
    }

    /**
     * This method is called when an item is selected from the item ComboBox.
     * It retrieves and displays the item's details based on the selected ID.
     */
    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        ItemDTO itemDTO = itemModel.findById(selectedItemId);

        // If item found (itemDTO not null)
        if (itemDTO != null) {

            // FIll item related labels
            lblItemName.setText(itemDTO.getItemName());
            lblItemQty.setText(String.valueOf(itemDTO.getQuantity()));
            lblItemPrice.setText(String.valueOf(itemDTO.getPrice()));
        }
    }

}

