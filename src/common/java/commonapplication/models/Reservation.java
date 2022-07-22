package commonapplication.models;

import commonapplication.persistancemanagement.DataHandler;
import commonapplication.persistancemanagement.Saver;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class represents a reservation, used for processing the operations on reservations
 *
 * @author Chiheb Bacha
 */
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

    /**
     * Creates a reservation
     *
     * @param reservationStart Time when the reservation starts
     * @param reservationEnd Time when the reservation ends
     * @param reservedBy User who reserved
     * @param restaurant Actual restaurant
     * @param table Table reserved
     * @param reservationDate Reservation date
     */
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
                "><RID:" + restaurant.getRestaurantFile().getName().replace(".dat", "") +
                "><TAB:" + this.table.getId() +
                "><PID:" + reservedBy +
                "><RS:" + reservationStart.toString() +
                "><RE:" + reservationEnd.toString() +
                "><RD:" + reservationDate.toString() +
                "><C:" + confirmed +
                "></RES>>";
        if (!resFile.exists() || DataHandler.readFile(resFile).isEmpty())
            Saver.saveToFile(resFile.getPath(), resInfo, 0);
        Saver.addResId(resFile.getName().replace(".dat", "") + ",");
    }

    /**
     * Simple Reservation constructor
     */
    // DONT DELETE
    public Reservation() {
    }

    /**
     * Gets the end time of the reservation
     *
     * @return LocalTime end time
     */
    public LocalTime getReservationEnd() {
        return reservationEnd;
    }

    /**
     * Gets the start time of the reservation
     *
     * @return LocalTime start time
     */
    public LocalTime getReservationStart() {
        return reservationStart;
    }

    /**
     * Gets the id of the reservation
     *
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the user who made a reservation
     *
     * @return String user
     */
    public String getReservedBy() {
        return reservedBy;
    }

    /**
     * Gets the actual restaurant, where the reservation was made
     *
     * @return Restaurant actual restaurant
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    /**
     * Gets the restaurant .dat file, storing the reservations' data
     *
     * @return File .dat file
     */
    public File getResFile() {
        return resFile;
    }

    /**
     * Returns the restaurant information in form of String
     *
     * @return String information about restaurant
     */
    public String toString() {
        return resInfo;
    }

    /**
     * Sets reservation status
     *
     * @param confirmed Status of the reservation
     */
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    /**
     * Sets reservation status
     *
     * @return boolean Status of the reservation
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Gets the reservation date
     *
     * @return LocalDate reservation date
     */
    public LocalDate getReservationDate() {
        return reservationDate;
    }

    /**
     * Gets the reserved table
     *
     * @return Table reserved table
     */
    public Table getTable() {
        return table;
    }

    /**
     * Sets the table for the reservation
     *
     * @return Table table to be reserved
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * Checks if the time slot is available for the reservation
     *
     * @param reservationStart Reservation start time
     * @param reservationEnd Reservation end time
     * @return boolean availability of the time slot
     */
    public boolean timeSuitable(LocalTime reservationStart, LocalTime reservationEnd) {
        boolean b1 = reservationStart.isAfter(this.reservationStart) && reservationStart.isBefore(this.reservationEnd);
        boolean b2 = reservationEnd.isAfter(this.reservationStart) && reservationEnd.isBefore(this.reservationEnd);
        if (b1 || b2) return false;
        return true;
    }

    /**
     * Compares if two reservations are equal
     *
     * @param reservation Reservation to compare with
     * @return int result of the comparison
     */
    @Override
    public int compareTo(Reservation reservation) {
        if (reservation == null)
            return -1;
        return this.toString().compareTo(reservation.toString());
    }


}
