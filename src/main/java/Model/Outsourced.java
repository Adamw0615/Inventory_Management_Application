package Model;

/**
 * The type Outsourced.
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Instantiates a new Outsourced.
     *
     * @param id          the id
     * @param name        the name
     * @param price       the price
     * @param stock       the stock
     * @param min         the min
     * @param max         the max
     * @param companyName the company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Returns the name of the company.
     *
     * @return the company name
     */
    public String getCompanyName() {

        return companyName;
    }

    /**
     * Sets the company name.
     *
     * @param companyName the company name to set.
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}
