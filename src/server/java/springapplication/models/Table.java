package springapplication.models;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private long id;
    private String name;

    private Restaurant restaurant;

    private List<Reservation> reservationList;

    public Table(long id, String name, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        reservationList = new ArrayList<>();
        this.restaurant = restaurant;
    }
//TODO


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
