package springapplication.models;

import java.time.LocalTime;

public class Reservation {

    private long id;
    private Restaurant restaurant;
    private Table table;
    private long reservedBy;
    private LocalTime reservationStart;
    private LocalTime reservationEnd;

    public Reservation(LocalTime reservationStart, LocalTime reservationEnd, long reservedBy, Restaurant restaurant, long id, Table table) {
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
        this.reservedBy = reservedBy;
        this.restaurant = restaurant;
        this.id = id;
        this.table = table;
    }

    public LocalTime getReservationEnd() {
        return reservationEnd;
    }

    public LocalTime getReservationStart() {
        return reservationStart;
    }

    public long getId() {
        return id;
    }


    public long getReservedBy() {
        return reservedBy;
    }


    public Restaurant getRestaurant() {
        return restaurant;
    }


    public String toString() {
        return "<<RES><ID:" + id + "><RID:" + restaurant.getId() + "><TAB:" + this.table.getId() + "><PID:" + reservedBy + "><RS:" + reservationStart.toString() + "><RE:" + reservationEnd.toString() + "></RES>>";
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
