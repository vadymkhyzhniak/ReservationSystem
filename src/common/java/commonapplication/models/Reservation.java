package commonapplication.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private String id;
    private Restaurant restaurant;
    private Table table;
    private String reservedBy;
    private LocalTime reservationStart;
    private LocalTime reservationEnd;
    private LocalDate reservationDate;

    public Reservation(LocalTime reservationStart, LocalTime reservationEnd, String reservedBy, Restaurant restaurant, Table table, LocalDate reservationDate) {
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
        this.reservedBy = reservedBy;
        this.restaurant = restaurant;
        this.table = table;
        this.reservationDate = reservationDate;
        this.id = Generator.generateUniqueId(reservationStart.toString(), reservationEnd.toString(), reservedBy, reservationDate.toString());
    }

    // DONT DELETE
    public Reservation() {
    }


    public LocalTime getReservationEnd() {
        return reservationEnd;
    }

    public LocalTime getReservationStart() {
        return reservationStart;
    }

    public String getId() {
        return id;
    }


    public String getReservedBy() {
        return reservedBy;
    }


    public Restaurant getRestaurant() {
        return restaurant;
    }


    public String toString() {
        return "<<RES><ID:" + id +
                "><RID:" + restaurant.getId() +
                "><TAB:" + this.table.getId() +
                "><PID:" + reservedBy +
                "><RS:" + reservationStart.toString() +
                "><RE:" + reservationEnd.toString() +
                "><RD:" + reservationDate.toString() +
                "></RES>>";
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public boolean timeSuitable(LocalTime reservationStart, LocalTime reservationEnd) {
        boolean b1 = reservationStart.isAfter(this.reservationStart) && reservationStart.isBefore(this.reservationEnd);
        boolean b2 = reservationEnd.isAfter(this.reservationStart) && reservationEnd.isBefore(this.reservationEnd);
        if (b1 || b2) return false;
        return true;
    }
}
