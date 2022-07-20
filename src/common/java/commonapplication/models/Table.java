package commonapplication.models;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private long id;
    private Restaurant restaurant;

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
        return "<T:" + id + "><R:" + restaurant.getRestaurantFile().getName() + ">";
    }
}
