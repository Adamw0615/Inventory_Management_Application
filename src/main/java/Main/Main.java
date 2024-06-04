package Main;

/**
 * @author Adam Wright
 * */

/**
 * The Javadoc folder is located within the project folder in the "JavaDocs" folder.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Model.*;
import java.io.IOException;

/**
 * The main class that starts the application.
 */
public class Main extends Application {
    /**
     * Start method to initialize and display the main form.
     *
     * @param stage the primary stage for the application
     * @throws IOException if there is an error loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/a/wright/Views/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 350);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the main method that initializes parts and products and adds them to the inventory.
     *
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
         // Create and add parts to the Inventory

         Part Brakes = new InHouse(1, "Brakes", 10.00, 10, 4, 32, 1);
        Inventory.addPart(Brakes);

        Part Pedal = new InHouse(2, "Pedal", 15.00, 16, 10, 30, 4);
        Inventory.addPart(Pedal);

        Part Seat = new InHouse(3, "Seat", 40.00, 10, 0, 120, 5);
        Inventory.addPart(Seat);

        Part Rim = new Outsourced(4, "Rim", 25.00, 10, 0, 120, "Adam's Wheels and Deals");
        Inventory.addPart(Rim);

        Product ElectricBike = new Product(1000, "Electric Bike", 299.99, 3, 1, 50);
        Inventory.addProduct(ElectricBike);

        Product Unicycle = new Product(1001, "Unicycle", 99.99, 5, 1, 50);
        Inventory.addProduct(Unicycle);

         // Launch the application(Main Form)
         launch(args);
    }
}