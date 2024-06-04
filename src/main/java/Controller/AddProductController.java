package Controller;
/**
 * @author Adam Wright
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.Product;
import Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * AddProductController - This class is used to manipulate the product inventory.
 */
public class AddProductController implements Initializable {

    // This observablelist holds the associatedPartsList.
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML private TextField addProductSearchBox;
    @FXML private Button addProductSearchButton;
    @FXML private TextField addProductID;
    @FXML private TextField addProductName;
    @FXML private TextField addProductInventory;
    @FXML private TextField addProductPrice;
    @FXML private TextField addProductmax;
    @FXML private TextField addProductmin;
    @FXML private TableView<Part> associatedProductTble;
    @FXML private TableView<Part> addProductTable;
    @FXML private TableColumn<?, ?> addProductpartIDcolumn;
    @FXML private TableColumn<?, ?> addProductNamecolumn;
    @FXML private TableColumn<?, ?> addProductInventoryLvlcolumn;
    @FXML private TableColumn<?, ?> addProductPriceCostcolumn;
    @FXML private TableColumn<?, ?> associatedProductIDcolumn;
    @FXML private TableColumn<?, ?> associatedProductNamecolumn;
    @FXML private TableColumn<?, ?> associatedProductInventoryLvlcolumn;
    @FXML private TableColumn<?, ?> associatedProductPerCostcolumn;
    @FXML private Button addProductCancelBtn;
    @FXML private Button addProductSaveBtn;
    @FXML private Button removeAssociatedPart;
    @FXML private Button addProductaddBtn;
    @FXML private Button removeAssociatedPartBtn;

    /**
     * Redirects the user to the Main Form when the Cancel button is pressed.
     *
     * @param event The action event triggered by the Cancel button click.
     * @throws IOException if an I/O exception occurs.
     */
    @FXML
    public void addProductcancel(ActionEvent event) throws IOException {
        //Load the MainForm.fxml file
        Parent root = FXMLLoader.load(getClass().getResource("/a/wright/Views/MainForm.fxml"));
        Scene scene = new Scene(root);
        Stage MainFormReturn = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainFormReturn.setScene(scene);
        MainFormReturn.show();
    }

    /**
     * Save new product when the save button is clicked.
     *
     * @param event The action event triggered by the Save button click.
     * @throws IOException if an I/O exception occurs.
     */
    @FXML
    void ProductSaveSelect(ActionEvent event) throws IOException {
        try {
            //Generate unique ID to prevent overlapping with existing Parts
            int uniqueID = (int) (Math.random() * 10000);

            //Retrieve product details from user input.
            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductInventory.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductmax.getText());
            int min = Integer.parseInt(addProductmin.getText());

            if (stock > max || stock < min) {
                //Show an error message if the inventory criteria is not met.
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory criteria: The inventory must be between minimum and maximum");
                alert.showAndWait();
                return;
            } else if (min >= max) {
                //Show an error message if the minimum is greater than or equal to the maximum.
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory criteria: The minimum must be less than the maximum");
                alert.showAndWait();
                return;
            }
            //Create a new product object with the provided details.
            Product product = new Product(uniqueID, name, price, stock, min, max);
            //Add associated parts to the product.
            for (Part part: associatedPartsList) {
                if (part != associatedPartsList)
                    product.addAssociatedParts(part);
            }
            //Add the product to the list if all products in inventory.
            Inventory.getAllProducts().add(product);
            //Redirect to the Main Form after saving the product.
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/a/wright/Views/MainForm.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            //Handle exception for invalid input value.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("There is an error with the input");
            alert.setContentText("The value is invalid");
            alert.showAndWait();
            return;
        }
    }

    /**
     * Add a product to the associated parts list.
     *
     * @param event the action event.
     */
    @FXML
    void ProductAdd(ActionEvent event) {
        //Retrieve the selected part from the table
        Part selectedPart = addProductTable.getSelectionModel().getSelectedItem();
        //If no part is selected,show an alert to the user.
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("There is an error with the input");
            alert.setContentText("A part must be selected");
            alert.showAndWait();
            return;
        }
        //If the part is not already in the associated parts list, add it to the list.
        else if (!associatedPartsList.contains(selectedPart))
        {
            associatedPartsList.add(selectedPart);
            associatedProductTble.setItems(associatedPartsList);
        }
    }

    /**
     * Removes the selected part from the associated parts list in the table.
     * If no part is selected. a warning message is displayed.
     *
     * @param event the ActionEvent triggering the removal.
     */
    @FXML
    void removeAssociatedPartBtn(ActionEvent event) {
        //Get the selected part from the table
        Part selectedPart = associatedProductTble.getSelectionModel().getSelectedItem();
        //Check if a part is selected.
        if (selectedPart == null) {
            //Display a warning message if no part is selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("There is an error with the input");
            alert.setContentText("Part must be selected");
            alert.showAndWait();
            return;
        } else if (associatedPartsList.contains(selectedPart)) {
            // Confirmation dialog before deleting the part
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Part");
            alert.setContentText("Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                //Remove the selected part if it exist in the associated parts list.
                associatedPartsList.remove(selectedPart);
                associatedProductTble.setItems(associatedPartsList);
            }
        }
    }

    /**
     *  Initializes the Add Product screen with the necessary data bindings
     *
     * @param resourceBundle The resources used to localze the root object, or null if the root object was not localized
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set items for the Add Product Table
        addProductTable.setItems(Inventory.getAllParts());
        addProductpartIDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNamecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryLvlcolumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCostcolumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Set items for the Associated Products Table
        associatedProductTble.setItems(associatedPartsList);
        associatedProductIDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedProductNamecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedProductInventoryLvlcolumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedProductPerCostcolumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Implements search functionality to filter parts based on text or ID.
     * If the search text is numeric, it searches by part ID.
     * Displays and error message if the part is not found.
     *
     * @param event the ActionEvent triggering the search
     */
    @FXML
    void addProductsearch(ActionEvent event) {
        //Gets the text from the search box
        String searchText = addProductSearchBox.getText();
        //Look up parts based on search text.
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        //If no results are found and search text is numeric, look up parts based on ID.
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            //Set the search results in the table.
            addProductTable.setItems(results);
        } catch (NumberFormatException e) {
            //Display an error message if the part is not found.
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error");
            noParts.setContentText("The part was not found");
            noParts.showAndWait();
        }
    }

}
