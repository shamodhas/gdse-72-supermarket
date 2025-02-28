package lk.ijse.gdse.supermarket;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator; // Not used but can be useful for loading indication
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.supermarket.dao.DaoFactory;
import lk.ijse.gdse.supermarket.dao.DaoTypes;
import lk.ijse.gdse.supermarket.dao.SuperDAO;
import lk.ijse.gdse.supermarket.dao.custom.CustomerDAO;
import lk.ijse.gdse.supermarket.dao.custom.ItemDAO;
import lk.ijse.gdse.supermarket.entity.Customer;
import lk.ijse.gdse.supermarket.entity.SuperEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppInitializer extends Application {


    public static void main(String[] args) {
//        List<Customer> customers = new ArrayList<>();
//        CustomerDAO dao = DaoFactory.getInstance().getDAO(DaoTypes.CUSTOMER);
//        ItemDAO dao1 = DaoFactory.getInstance().getDAO(DaoTypes.ITEM);
//        SuperEntity customer = new Customer();

//        CustomerDAO customerDAO = DaoFactory.getInstance().getDAO(DaoTypes.CUSTOMER);
//        Optional<Customer> optionalCustomer = customerDAO.findById("C001");

//        optionalCustomer.isEmpty() - no data - true
//        optionalCustomer.isPresent() - have data - true
//        optionalCustomer.get() - get data

//        if (optionalCustomer.isPresent()){
//            Customer customer = optionalCustomer.get();
//            customer never null
//        }

        launch(); // Launch the JavaFX application
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Load and display the loading view
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoadingView.fxml"))));
        stage.show();

        // Create a background task to load the main scene
        Task<Scene> loadMainSceneTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {
                // Load the main layout from FXML
                FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/MainLayout.fxml"));
                return new Scene(fxmlLoader.load()); // Return the loaded scene
            }
        };

        // What to do when loading is successful
        loadMainSceneTask.setOnSucceeded(event -> {
            Scene value = loadMainSceneTask.getValue();

            stage.setTitle("Supermarket FX");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app_logo.png")));
            stage.setMaximized(true);

            // Switch to the main scene
            stage.setScene(value);
        });

        // What to do in case of loading failure (optional)
        loadMainSceneTask.setOnFailed(event -> {
            System.err.println("Failed to load the main layout."); // Print error message
        });

        // Start the task in a separate thread
        new Thread(loadMainSceneTask).start();
    }
}
