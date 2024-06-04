package Controller;

/**
 * @author Adam Wright
 */

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * The ModifyPartController class handles the logic for the Modify Part form.
 */
public class ModifyPartController {

    //The label for switching between machineID and Outsourced.

    @FXML
    private RadioButton ModifyPartInHouse;
    @FXML
    private RadioButton ModifyPartOutsourced;
    @FXML
    private Label MachineIDorCompanyName;
    @FXML
    private ToggleGroup partGroup;
    @FXML
    private TextField ModifyPartID;
    @FXML
    private TextField ModifyPartName;
    @FXML
    private TextField ModifyPartInventory;
    @FXML
    private TextField ModifyPartPrice;
    @FXML
    private TextField ModifyPartMax;
    @FXML
    private TextField ModifyPartMin;
    @FXML
    private TextField addPartmachineID;
    @FXML
    private Button ModifyPartCancelBtn;

    private int currentIndex = 0;


    /**
     * ModifyPartCancelactn function to handle the cancel action in the modify part form.
     *
     * @param event The ActionEvent triggered when the cancel button is selected.
     * @throws IOException if and I/O error occurs.
     */
    @FXML
    public void ModifyPartCancelactn(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/a/wright/Views/MainForm.fxml"));
        Scene scene = new Scene(root);
        Stage MainFormReturn = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainFormReturn.setScene(scene);
        MainFormReturn.show();
    }

    /**
     * Receives information from the Main Form and updates the UI fields based on the selected part type.
     *
     * @param selectedIndex the index of the selected part.
     * @param part          the object to be displayed.
     */
    public void sendPart(int selectedIndex, Part part) {

        //Update the current index.
        currentIndex = selectedIndex;


        //Display the part details based on the part type.
        if (part instanceof InHouse) {
            //Set the InHouse radio button as selected and update the machine ID field.
            ModifyPartInHouse.setSelected(true);
            addPartmachineID.setText(String.valueOf(((InHouse) part).getMachineID()));
        } else {
            //Set the Outsourced radio button as selected and update the company name field.
            ModifyPartOutsourced.setSelected(true);
            addPartmachineID.setText(((Outsourced) part).getCompanyName());
        }

        //Update the UI fields with the part details.
        ModifyPartID.setText(String.valueOf(part.getId()));
        ModifyPartName.setText(String.valueOf(part.getName()));
        ModifyPartInventory.setText(String.valueOf(part.getStock()));
        ModifyPartPrice.setText(String.valueOf(part.getPrice()));
        ModifyPartMax.setText(String.valueOf(part.getMax()));
        ModifyPartMin.setText(String.valueOf(part.getMin()));
    }

    /**
     * Handles the action when a part is inHouse.
     *
     * @param event the event is triggered.
     */
    @FXML
    public void onActionPartInHouse(ActionEvent event) {

        //Update the label with the machine ID text.
        MachineIDorCompanyName.setText("MachineID");
    }

    /**
     * Handles the action when a part is outsourced.
     *
     * @param event the event that is triggered.
     */
    @FXML
    public void onActionPartoutsourced(ActionEvent event) {
        //Set the label text to "Name of Company" when the part is outsourced.
        MachineIDorCompanyName.setText("Company Name");
    }

    /**
     * The method handles saving the modified part details.
     * It retrieves the part information from the input fields, validates them
     * updates the part in the inventory, and returns the user to the Main form.
     *
     * @param event The action event that triggers the save button click.
     * @throws IOException the io exception
     */
    @FXML
    void ModifyPartSaveBtn(ActionEvent event) throws IOException {
        try {
            // Retrieve the modified part information from the input fields
            int partID = Integer.parseInt(ModifyPartID.getText());
            String name = ModifyPartName.getText();
            int inStock = Integer.parseInt(ModifyPartInventory.getText());
            double price = Double.parseDouble(ModifyPartPrice.getText());
            int min = Integer.parseInt(ModifyPartMin.getText());
            int max = Integer.parseInt(ModifyPartMax.getText());
            int machineID;

            String companyName;

            // Validates the minimum and maximum values.
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
                alert.showAndWait();
                return;
            } else if (inStock < min || max < inStock) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();
                return;
            }

            // Checks if part is InHouse or Outsourced
            if (ModifyPartInHouse.isSelected()) {
                machineID = Integer.parseInt(addPartmachineID.getText());
                InHouse updatedPart = new InHouse(partID, name, price, inStock, min, max, machineID);
                Inventory.updatePart(currentIndex, updatedPart);

            } else if (ModifyPartOutsourced.isSelected()) {
                companyName = MachineIDorCompanyName.getText();
                if (companyName.matches("\\d+")) {
                    //Check if company name is numeric
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid value for company name.");
                    alert.showAndWait();
                    return;
                }
                Outsourced updatedPart = new Outsourced(partID, name, price, inStock, min, max, companyName);
                Inventory.updatePart(currentIndex, updatedPart);
            }

            // Navigate back to the Main Form
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/a/wright/Views/MainForm.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            // Handle incorrect input values.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Incorrect value");
            alert.showAndWait();
            return;

        }

    }
}
