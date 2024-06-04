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
 * The Add Part Controller is used to add parts to the inventory.
 */
public class AddPartController {

    @FXML private Label MachineIDorCompanyName;
    @FXML private ToggleGroup partGroup;
    @FXML private RadioButton PartInHouseRadio;
    @FXML private RadioButton PartOutsourcedRadio;
    @FXML private RadioButton onActionPartoutsourced;
    @FXML private RadioButton onActionInHousePrt;
    @FXML private Button addPartSaveBtn;
    @FXML private Button addPartCancelBtn;
    @FXML private TextField addPartName;
    @FXML private TextField addPartInventory;
    @FXML private TextField addPartPrice;
    @FXML private TextField addPartMax;
    @FXML private TextField addPartMin;
    @FXML private TextField addPartMachineID;

    /**
     * Sets the text of the MachineIDorCompanyName to "Company Name" when the onActionOutsourcedPrt event occurs.
     *
     * @param event the ActionEvent triggering this method.
     */
    @FXML

    void onActionOutsourcedPrt(ActionEvent event) {

        MachineIDorCompanyName.setText("Company Name");
    }

    /**
     * Sets the text of the MachineIDorCompanyName to "Machine ID" when the onActionInHousePrt event occurs.
     *
     * @param event the ActionEvent triggering this method.
     */
    @FXML
    void onActionInHousePrt(ActionEvent event) {

        MachineIDorCompanyName.setText("Machine ID");
    }

    /**
     * This method handles the action event triggerd when the add part cancel button is pressed.
     *
     * @param event the ActionEvent triggering this method.
     * @throws IOException the io exception
     */
    @FXML
    public void addPartCancelactn (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/a/wright/Views/MainForm.fxml"));
        Scene scene = new Scene(root);
        Stage MainFormReturn = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainFormReturn.setScene(scene);
        MainFormReturn.show();
    }

    /**
     * This method handles the event when the save button for adding a part is clicked.
     * It generates a unique ID for the part, validates the input, creates a new part, and adds it to the inventory.
     * If there are input errors, it displays an alert.
     *
     * @param event The ActuibEvent representing the button click.
     * @throws IOException the io exception
     */
    @FXML
    void addPartSaveBtn(ActionEvent event) throws IOException {
        try {
            // Generate a unique ID for the part.
            int uniqueID = (int) (Math.random() * 100);


            //Retrieve input values from the text fields
            String name = addPartName.getText();
            int inStock = Integer.parseInt(addPartInventory.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(addPartMax.getText());

            int machineID = 0;
            String companyName;

            //Validate the min and max values
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
                alert.showAndWait();
                return;
            }
            //Validate the inventory values( Should
            else if (inStock < min || max < inStock) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();
                return;
            }

            // Check if the part is outsource or inhouse
            System.out.println(PartOutsourcedRadio.isSelected() + " Outsourced");
            System.out.println(PartInHouseRadio.isSelected() + " inHouse");
            if (PartOutsourcedRadio.isSelected()) {
                companyName = addPartMachineID.getText();
                Outsourced addPart = new Outsourced(uniqueID, name, price, inStock, min, max, companyName);
                Inventory.addPart(addPart);
            }
            if (PartInHouseRadio.isSelected()) {
                machineID = Integer.parseInt(addPartMachineID.getText());
                InHouse addPart = new InHouse(uniqueID, name, price, inStock, min, max, machineID);
                Inventory.addPart(addPart);
            }
            //Load the Main Form after adding the part.
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/a/wright/Views/MainForm.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            //Display an alert for input errors.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("There is an Input Error");
            alert.setContentText("The value is invalid ");
            alert.showAndWait();
            return;
        }
    }

    }


