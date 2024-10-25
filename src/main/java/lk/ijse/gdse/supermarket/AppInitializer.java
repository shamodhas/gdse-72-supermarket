package lk.ijse.gdse.supermarket;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator; // Not used but can be useful for loading indication
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AppInitializer extends Application {


    public static void main(String[] args) {
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
