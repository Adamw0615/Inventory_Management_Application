package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The product class is designed according  to UML provided by WGU.
 * The product class provides objects and associated parts.
 */
public class Product {
    /**
     * The Associated parts.
     */
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Instantiates a new Product.
     *
     * @param id    the id
     * @param name  the name
     * @param price the price
     * @param stock the stock
     * @param min   the min
     * @param max   the max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets stock.
     *
     * @param stock the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets min.
     *
     * @param min the min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets max.
     *
     * @param max the max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets price.
     *
     * @return price price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets stock.
     *
     * @return stock stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets min.
     *
     * @return min min
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets max.
     *
     * @return max max
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds a part to the associated parts observablelist.
     *
     * @param part the part to add.
     */
    public void addAssociatedParts(Part part) {
        associatedParts.add(part);
    }

    /**
     * Remove a part from the associated parts observablelist.
     *
     * @param selectedAssociatedPart the part to be removed.
     * @return true if the removal was successful.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * Retrieves all associated parts for the selected product.
     *
     * @return ObservableList of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Deletes the selecte associated part from the observable list.
     *
     * @param selectedAssociatedPart The part to be removed from the list.
     * @return true if the part was successfully removed, false otherwise.
     */
    public static boolean deleteAssocdPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
}


