package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


/**
 * The Inventory class provides methods for managing parts and products in the inventory.
 */
public class Inventory {


    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add a new part to the allParts observable List.
     *
     * @param newPart the part to be added.
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * Adds a new product to the list of all products.
     *
     * @param newProduct the product to be added.
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * Looks up a part based on the provided part ID.
     *
     * @param partID the ID of the part to look up.
     * @return the part with the specified ID, or null if not found.
     */
    public static Part lookupPart(int partID) {
        // Loop through all parts in the inventory.
        for(Part part: Inventory.getAllParts()) {
            while (part.getId() == partID) {
                return part;
                //Check if the part ID matches the provided ID.{
                    // Return the part if found.
               //Return the part if found.
                }

            }

        // If no part is found, show an error message.
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Nothing found");
        alert.show();
        return null;
        //return null if no part is found.
    }

    /**
     * Looks up a product based on the provided product ID.
     *
     * @param productID the ID of the product to look up.
     * @return The product with the specified ID, or null if not found.
     */
    public static Product lookupProduct(int productID) {
        // Loop through all products in the inventory.
        for(Product product: Inventory.getAllProducts()){
            while (product.getId() == productID)
                // Check if the product ID matches the provided ID.
                //Return the product if found.
                return product;
        } // If no product is found, return null.
        return null;
    }

    /**
     * Looks for part in the inventory that match the provided part name.
     *
     * @param partName the name of the part to search for.
     * @return an observable list of parts that match the provided name.
     */
//Create a new observable list to hold the filtered parts.
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> PartName = FXCollections.observableArrayList();

        //Iterate through all parts in the inventory.
        for (Part part: allParts) {
            //Check if the part name contains the provided partName.
            if (part.getName().contains(partName)) {
                PartName.add(part);
            }
        }
        return PartName;
    }

    /**
     * Filters the list of products based on the provided product name.
     *
     * @param productName the name of the product to search for.
     * @return an observable list of products that contain the provided name.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> ProductName = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            if (product.getName().contains(productName)) {
                ProductName.add(product);
            }
        }
        return ProductName;
    }

    /**
     * Update the part at the specified index with the selected part.
     *
     * @param index        the index of the part to be updated
     * @param selectedPart the part to replace the existing part at the index.
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * Updates a product in the list of all products.
     *
     * @param index           The index of the product to update
     * @param selectedProduct The new product object to replace the existing one.
     */
    public static void updateProduct(int index, Product selectedProduct) {

        allProducts.set(index, selectedProduct);
    }


    /**
     * Deletes a part from the list of all parts.
     *
     * @param selectedPart The part to be deleted.
     * @return true if the part is deleted successfully, false otherwise.
     */
    public static boolean deletePart (Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes a product from the list of all products
     *
     * @param selectedProduct the product to be deleted.
     * @return true if the product is deleted successfully, false otherwise.
     */
    public static boolean deleteProduct (Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves all products from the inventory
     *
     * @return The list of all products.
     */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

    /**
     * Retrieves all parts from the inventory.
     *
     * @return Observable list of all parts.
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

}
