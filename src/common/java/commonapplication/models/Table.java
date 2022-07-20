package commonapplication.models;

public class Table {
    private long id;
    private Restaurant restaurant;

    private int available;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Table(long id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    // DONT DELETE
    public Table() {
    }



    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "<T:" + id + "><R:" + restaurant.getRestaurantFile().getName().replace(".dat", "") + "><A" + available + ">";
    }
}
