package Model;

/**
 * The type In house.
 */
public class InHouse extends Part {
    private int machineID;

    /**
     * Instantiates a new In house.
     *
     * @param id        the id
     * @param name      the name
     * @param price     the price
     * @param inStock   the in stock
     * @param min       the min
     * @param max       the max
     * @param machineID the machine id
     */
    public InHouse(int id, String name, double price, int inStock, int min, int max, int machineID) {
        super(id, name, price, inStock, min, max);
        this.machineID = machineID;
    }

    /**
     * Set the machine ID for the in-house part.
     *
     * @param machineID the machine ID to set
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * Get the machine ID.
     *
     * @return The machine ID
     */
    public int getMachineID() {

        return machineID;
    }
}
