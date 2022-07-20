package commonapplication.models;

import commonapplication.persistancemanagement.DataHandler;
import commonapplication.persistancemanagement.Saver;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation implements Comparable<Reservation> {

    private String id;
    private Restaurant restaurant;
    private Table table;
    private String reservedBy;
    private LocalTime reservationStart;
    private LocalTime reservationEnd;
    private LocalDate reservationDate;
    private File resFile;

    String resInfo;

    private boolean confirmed;

    public Reservation(LocalTime reservationStart, LocalTime reservationEnd, String reservedBy, Restaurant restaurant, Table table, LocalDate reservationDate) {
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
        this.reservedBy = reservedBy;
        this.restaurant = restaurant;
        this.table = table;
        this.reservationDate = reservationDate;
        this.id = Generator.generateUniqueId(reservationStart.toString(), reservationEnd.toString(), reservedBy, reservationDate.toString());
        this.confirmed = false;
        this.resFile = new File("src/server/resources/Reservations/" + id + ".dat");
        this.resInfo = "<<RES><ID:" + id +
                "><RID:" + restaurant.getId() +
                "><TAB:" + this.table.getId() +
                "><PID:" + reservedBy +
                "><RS:" + reservationStart.toString() +
                "><RE:" + reservationEnd.toString() +
                "><RD:" + reservationDate.toString() +
                "><C:" + confirmed +
                "></RES>>";
        if (!resFile.exists() || DataHandler.readFile(resFile).isEmpty())
            Saver.saveToFile(resFile.getPath(), resInfo, 0);
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

    public File getResFile() {
        return resFile;
    }

    public String toString() {
        return resInfo;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isConfirmed() {
        return confirmed;
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

    @Override
    public int compareTo(Reservation reservation) {
        if (reservation == null)
            return -1;
        return this.toString().compareTo(reservation.toString());
    }
}
