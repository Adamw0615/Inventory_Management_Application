package Controller;

/**
 * @author Adam Wright
 */

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * The controller for the Modify Product form.
 */
public class ModifyProductController implements Initializable {

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> MainFormProductsTlble;
    @FXML
    private TextField ModifyProductsearchbx;
    @FXML
    private Button modifyProductSearchButton;
    @FXML
    private TextField ModifyProductID;
    @FXML
    private TextField ModifyProductName;
    @FXML
    private TextField ModifyProductInventory;
    @FXML
    private TextField ModifyProductPrice;
    @FXML
    private TextField ModifyProductMax;
    @FXML
    private TextField ModifyProductMin;
    @FXML
    private TableView<Part> ModifyProductTable;
    @FXML
    private TableColumn<?, ?> ModifyProductpartIDcolumn;
    @FXML
    private TableColumn<?, ?> ModifyPartNamecolumn;
    @FXML
    private TableColumn<?, ?> ModifyProductInventoryLevelcolumn;
    @FXML
    private TableColumn<?, ?> ModifyPriceCostcolumn;
    @FXML
    private TableView<Part> associatedProductTble;
    @FXML
    private TableColumn<?, ?> associatedProductIDcolumn;
    @FXML
    private TableColumn<?, ?> associatedProductNamecolumn;
    @FXML
    private TableColumn<?, ?> associatedProductinventoryLevelcolumn;
    @FXML
    private TableColumn<?, ?> associatedPriceCostcolumn;
    @FXML
    private Button ModifyProductCancelBtn;
    @FXML
    private Button ModifyProductSaveBtn;
    @FXML
    private Button RemoveAssociatedPartBtn;
    @FXML
    private Button ModifyProductModifyBtn;
    @FXML
    private TextField addPartmachineID;

    private int currentIndex = 0;

    /**
     * This method cancels the modification of a product and returns to the main form view.
     *
     * @param event The ActionEvent that triggers the method.
     * @throws IOException if an input or output exception occurs.
     */
    @FXML
    public void ModifyProductCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/a/wright/Views/MainForm.fxml"));
        Scene scene = new Scene(root);
        Stage MainFormReturn = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainFormReturn.setScene(scene);
        MainFormReturn.show();
    }

    /**
     * Sends the product data to the UI for modification.
     *
     * @param selectedIndex The index of the selected product.
     * @param product       The product to be modified.
     */
    @FXML
    public void sendProduct(int selectedIndex, Product product) {

        //Set the current index
        currentIndex = selectedIndex;
        //Set the product details to the UI elements.
        ModifyProductID.setText(String.valueOf(product.getId()));
        ModifyProductName.setText(String.valueOf(product.getName()));
        ModifyProductInventory.setText(String.valueOf(product.getStock()));
        ModifyProductPrice.setText(String.valueOf(product.getPrice()));
        ModifyProductMax.setText(String.valueOf(product.getMax()));
        ModifyProductMin.setText(String.valueOf(product.getMin()));

        //Add associated parts to the list
        for (Part part : product.getAllAssociatedParts()) {
            associatedPartsList.add(part);
        }
    }

    /**
     * Initializes the Modify Product form with the necessary data.
     *
     * @param url            The URL location.
     * @param resourceBundle The resources that contain the data
     */

    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set inital items for the main product table.

        ModifyProductTable.setItems(Inventory.getAllParts());
        ModifyProductpartIDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyPartNamecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductInventoryLevelcolumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyPriceCostcolumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Set items for the associated product table.
        associatedProductTble.setItems(associatedPartsList);
        associatedProductIDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedProductNamecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedProductinventoryLevelcolumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCostcolumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Responsible for adding the selected part to the Associated table.
     *
     * @param event the ActionEvent triggering the method.
     */
    @FXML
    void addPartProductModify(ActionEvent event) {
        //Get the selected part from the Modify Product table
        Part selectedPart = ModifyProductTable.getSelectionModel().getSelectedItem();

        //Check if a part is selected.
        if (selectedPart == null) {
            //Show a warning message if no part is selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please select a Part from the List.");
            alert.showAndWait();
            return;
            // Add the selected part to the associated parts list and update the associated parts table.
        } else if (!associatedPartsList.contains(selectedPart)) {
            associatedPartsList.add(selectedPart);
            associatedProductTble.setItems(associatedPartsList);
        }
    }

    /**
     * Removes the selected associated part from the associated parts list.
     * A warning message is shown if no part is selected or if the selected part is already removed.
     *
     * @param event the ActionEvent triggering the method.
     */
    @FXML
    void RemoveAssocPartBtn(ActionEvent event) {
        // Get the selected part from the associated parts table
        Part selectedPart = associatedProductTble.getSelectionModel().getSelectedItem();
        //Check if a part is selected.
        if (selectedPart == null) {
            //Shows a warning message if no part is selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("There is an error with the input.");
            alert.setContentText("A part must be selected.");
            alert.showAndWait();
            return;

        } else if (associatedPartsList.contains(selectedPart)) {
            //Confirmation dialog before deleting the part.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Part");
            alert.setContentText("Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
            }
            //The selected part is deleted from the associated list.
            Product.deleteAssocdPart(selectedPart);
            associatedPartsList.remove(selectedPart);
            associatedProductTble.setItems(associatedPartsList);



        }
    }

    /**
     * The method saves the selected prooduct after performing validations.
     *
     * @param event the ActionEvent triggering the method.
     * @throws IOException the io exception
     */
    @FXML
    void ProductSaveSelect(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(ModifyProductID.getText());
            String name = ModifyProductName.getText();
            int stock = Integer.parseInt(ModifyProductInventory.getText());
            double price = Double.parseDouble(ModifyProductPrice.getText());
            int max = Integer.parseInt(ModifyProductMax.getText());
            int min = Integer.parseInt(ModifyProductMin.getText());

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
            Product updatedProduct = new Product(id, name, price, stock, min, max);
            if (updatedProduct != associatedPartsList) {
                //Update the product if it not already in the list.
                Inventory.updateProduct(currentIndex, updatedProduct);
            }


            for (Part part : associatedPartsList) {
                if (part != associatedPartsList)
                    //Add associated parts to the updated product.
                    updatedProduct.addAssociatedParts(part);
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/a/wright/Views/MainForm.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            //Show a warning message if the input is invalid.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("There is an error with the input.");
            alert.setContentText("The value is invalid.");
            alert.showAndWait();
            return;
        }
    }


    /**
     * Searches for parts based on the input text and displays the results in the parts table.
     *
     * @param event the ActionEvent triggered by the search.
     */
    @FXML
    void ModifyProductPartsearch(ActionEvent event) {
        //Get the text from the input field.
        String searchText = ModifyProductsearchbx.getText();
        //Look up parts based on the input text.
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            //If no results are found try to parse the search.
            while (results.size() == 0) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            //Display the search results in the parts table.
            ModifyProductTable.setItems(results);
        } catch (NumberFormatException e) {
            //Display an error message if the part is not found.
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error");
            noParts.setContentText("The part was not found.");
            noParts.showAndWait();
        }
    }

}

