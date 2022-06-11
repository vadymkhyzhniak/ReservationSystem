package springapplication.models;

import java.time.LocalTime;

public class Reservation {

    private LocalTime reservationStart;
    private LocalTime reservationEnd;

    private Restaurant restaurant;
    private User reservedBy;

    private Table table;
    private long id;

    public Reservation (LocalTime reservationStart, LocalTime reservationEnd,User reservedBy, Restaurant restaurant, long id,Table table) {
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

    public void setId(long id) {
        this.id = id;
    }

    public User getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(User reservedBy) {
        this.reservedBy = reservedBy;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String toString(){
        return "<<RES><ID:"+id+"><RID:"+restaurant.getId()+"><PID:"+reservedBy.getId()+"><RS:"+reservationStart.toString()+"><RE:"+reservationEnd.toString()+"></RES>>";
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
