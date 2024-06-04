package Controller;

/**
 * @author Adam Wright
 */

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * The main form controller provides the functionality and logic for the main form.
 * <p>
 * FUTURE enhancement: Implementing pagination for displaying a large number of products.
 * This would involve adding pagination controls to the user interface and
 * updating the logic in the controller to handle fetching and displaying the products in pages.
 * This enhancement would improve the user experience when dealing with a large inventory of products.
 * <p>
 * LOGIC ERROR: I had had issue with getting the In-House and Out-Sourced radio buttons to switch properly to Company Name
 * and Machine ID. I placed and fx:id in the machine ID label and it worked.
 */
public class MainFormController implements Initializable {




    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;

    @FXML private TableView<Product> productTable;

    @FXML private TableColumn<Product, Integer> ProductIDcolumn;

    @FXML private TableColumn<Product, String> ProductNamecolumn;

    @FXML private TableColumn<Product, Integer> ProductInventoryLvlcolumn;

    @FXML private TableColumn<Product, Double> ProductPriceCostcolumn;

    @FXML private TextField ProductSearch;

    @FXML private TextField searchPart;

    @FXML private TableColumn<Part, Integer> partIDcolumn;

    @FXML private TableColumn<Part, String> partNameColumn;

    @FXML private TableColumn<Part, Integer> inventoryLevelColumn;

    @FXML private TableColumn<Part, Double> pCperUnitColumn;

    @FXML private TableView<Product> MainFormProductsTlble;

    @FXML private TableView<Part> mainFormPartTble;
    @FXML private TableView<Part> ModifyProductTable;
    @FXML private TableColumn<Part, Integer> partsTablePartID;
    @FXML private TableColumn<Part, String> partsTablePartName;
    @FXML private TableColumn<Part, Integer> partsTableInventoryCount;
    @FXML private TableColumn<Part, Integer> partsTablePPU;
    @FXML private TableColumn<Product, Integer> productsTablePartID;
    @FXML private TableColumn<Product, String> productsTablePartName;
    @FXML private TableColumn<Product, Integer> productsTableInventoryCount;
    @FXML private TableColumn<Product, Integer> productsTablePPU;
    @FXML private Button modifyPartButton;
    @FXML private Button addPartButton;
    @FXML private Button deletePartButton;
    @FXML private Button exitMain;
    @FXML private TableView<Part> partsTableView;

    /**
     * The Stage.
     */
    Stage stage;


    /**
     * The AddPart.fxml file, which represents the UI for adding a part to the inventory.
     *
     * @param event the ActionEvent triggering the method.
     * @throws IOException if an error occurs during loading.
     */
    @FXML
    void MainFormaddPartselect(ActionEvent event) throws IOException {

        // load AddPart.fxml
        Parent addParts = FXMLLoader.load(getClass().getResource("/a/wright/Views/AddPart.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    /**
     * The selected part is modified.
     *
     * @param event the ActionEvent triggering the method.
     * @throws IOException the io exception
     */
    @FXML
    void MainFormmodifyPartselect(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/a/wright/Views/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(mainFormPartTble.getSelectionModel().getSelectedIndex(),mainFormPartTble.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Must Select a Part");
            alert.show();
        }
    }

    /**
     * The selected part is modified
     *
     * @param event the ActionEvent triggering the method.
     * @throws IOException the io exception
     */
    @FXML
    void MainFormaddProductselect(ActionEvent event) throws IOException {

        Parent addParts = FXMLLoader.load(getClass().getResource("/a/wright/Views/AddProduct.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * The Application is closed
     *
     * @param ExitButton the ActionEvent triggering the method
     */
    public void MainFormExitButton(ActionEvent ExitButton) {
        Stage stage = (Stage) ((Node) ExitButton.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Opens the Modify Product form when a product is selected in the Main Form table.
     * If no product is selected, an error message is displayed.
     *
     * @param event the ActionEvent triggering the method.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    void MainFormModifyProductselect(ActionEvent event) throws IOException {
        try {
            //Load the ModifyProduct.fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/a/wright/Views/ModifyProduct.fxml"));
            loader.load();

            //Get the controller for the ModifyProduct.fxml file
            ModifyProductController MPController = loader.getController();
            //Send the selected product information to the ModifyProductController
            MPController.sendProduct(MainFormProductsTlble.getSelectionModel().getSelectedIndex(), MainFormProductsTlble.getSelectionModel().getSelectedItem());
            //Get the stage from the event source.
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            //Set the scene to the ModifyProduct form.
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            //Display an error message if no product is selected.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Must Select Product to Modify.");
            alert.show();
        }
    }

    /**
     * Initializes and populates the tables in the Main Form.
     *
     * @param url the URL of the location.
     * @param resourceBundle the resource bundle to be used.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initialize and populate the parts table.
        mainFormPartTble.setItems(Inventory.getAllParts());
        partIDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pCperUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //Initialize and populate the products table.
        MainFormProductsTlble.setItems(Inventory.getAllProducts());
        ProductIDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNamecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryLvlcolumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceCostcolumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Handles the event of deleting a selected part.
     *
     * @param event the theActionEvent triggering the deletion.
     */
    @FXML
    void MainFormdeletePartselect(ActionEvent event) {
        //Get the selected part from the table
        Part selectedPart = mainFormPartTble.getSelectionModel().getSelectedItem();
        // Create a confirmation alert dialog.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Delete Part?");
        //Display the dialog and wait fot user response.
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }

    /**
     * Handles the event of deleting a selected product.
     *
     * @param event the ActionEvent triggering the deletion.
     */
    @FXML
    void MainFormDeleteProductselect(ActionEvent event) {
        //Get the selected product from the table
        Product selectedProduct = MainFormProductsTlble.getSelectionModel().getSelectedItem();
        //Create a confirmation alert dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText(" Delete Product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Get the selected product again.
            Product selectedDeleteProduct = MainFormProductsTlble.getSelectionModel().getSelectedItem();
            if (selectedDeleteProduct.getAllAssociatedParts().size() > 0) {
                // Display an error message and stop deletion if associated parts exist.
                Alert cantDelete = new Alert(Alert.AlertType.ERROR);
                cantDelete.setTitle("Error");
                cantDelete.setContentText(" Associated parts must be removed prior to deleting the product.");
                cantDelete.showAndWait();
                return;
            }
            //Delete the selected product.
            Inventory.deleteProduct(selectedProduct);
        }
    }

    /**
     * Handler for the search button in the Main Form.
     * Searches for a part based on the input text and displays results in the table.
     *
     * @param event the event
     */
    @FXML
    void MainFormSearchPart(ActionEvent event) {
        String searchText = searchPart.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            mainFormPartTble.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error");
            noParts.setContentText("Part not found");
            noParts.showAndWait();
        }
    }

    /**
     * Searches for a product based on the provided text input.
     *
     * @param event the event triggering the search. FUTURE ENHANCEMENT: When searching for products or parts, the search shouldn't be case-sensitive.  A case-sensitive search should be implemented. which would make it easier to search for products or parts.
     */
    @FXML
    void MainFormProductSearch(ActionEvent event) {
        //Get the text input from the ProductSearch TextField.
        String searchText = ProductSearch.getText();
        //Look up the product based on the search text.
        ObservableList<Product> results = Inventory.lookupProduct(searchText);
        try {
            //If no results are found, try parsing the search text as an ID and look up the procuct
            while (results.size() == 0 ) {
                int productID = Integer.parseInt(searchText);
                results.add(Inventory.lookupProduct(productID));
            }

            //Display the search results in the MainFormProductsTlble TableView
            MainFormProductsTlble.setItems(results);
        } catch (NumberFormatException e) {
            //Show an error alert if the search text is not a valid product ID
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error");
            noParts.setContentText("Product not found");
            noParts.showAndWait();
        }
    }

    }


