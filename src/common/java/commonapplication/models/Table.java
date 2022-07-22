package commonapplication.models;

/**
 * Class represents a table, used for handling operations on tables
 *
 * @author Chiheb Bacha
 */
public class Table {
    private long id;
    private Restaurant restaurant;

    private int available;

    /**
     * Gets the available table
     *
     * @return int Available table
     */
    public int getAvailable() {
        return available;
    }

    /**
     * Sets the table available
     *
     * @param available Table to be set available
     */
    public void setAvailable(int available) {
        this.available = available;
    }

    /**
     * Creates a table based on id and restaurant
     *
     * @param id ID of the table
     * @param restaurant Restaurant, where the table is
     */
    public Table(long id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    /**
     * Simple table constructor
     */
    // DONT DELETE
    public Table() {
    }

    /**
     * Gets the id of the table
     *
     * @return long ID of the table
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the information about the table in form of String
     *
     * @return String information about table
     */
    @Override
    public String toString() {
        return "<T:" + id + "><R:" + restaurant.getRestaurantFile().getName().replace(".dat", "") + "><A" + available + ">";
    }
}
