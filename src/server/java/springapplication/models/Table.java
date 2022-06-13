package springapplication.models;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private long id;
    private Restaurant restaurant;

    private List<Reservation> reservationList;

    public Table(long id, Restaurant restaurant) {
        this.id = id;
        reservationList = new ArrayList<>();
        this.restaurant = restaurant;
    }
//TODO


    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Table" + id + "OfRestaurant:" + restaurant.getId();
    }
}
