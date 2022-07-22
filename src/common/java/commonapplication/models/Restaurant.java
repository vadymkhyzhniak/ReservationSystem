package commonapplication.models;

import commonapplication.persistancemanagement.DataHandler;
import commonapplication.persistancemanagement.Saver;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a restaurant, used for handling operations on restaurants
 *
 * @author Chiheb Bacha
 */
public class Restaurant {
    private long id;
    private String name;
    private String tableSchema;
    private Table[] tables;
    private LocalTime openedFrom;
    private LocalTime openedTo;

    private String location;
    private Speciality speciality;
    private int stars;
    private int priceRange;
    private File restaurantFile;
    private boolean openNow;

    private List<Reservation> reservationList = new ArrayList<>();

    private String restInfo;

    /**
     * Creates a restaurant
     *
     * @param speciality Restaurant cuisine
     * @param stars Restaurant rating
     * @param priceRange Restaurant price level
     * @param openNow Current restaurant wokring status
     */
    public Restaurant(Speciality speciality, int stars, int priceRange, boolean openNow) {
        this.speciality = speciality;
        this.stars = stars;
        this.priceRange = priceRange;
        this.openNow = openNow;
    }

    /**
     * Simple restaurant constructor
     */
    // DONT DELETE
    public Restaurant() {
    }

    /**
     * Sets the cuisine
     *
     * @param speciality the cuisine to be set
     */
    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    /**
     * Sets the rating
     *
     * @param stars the rating to be set
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    /**
     * Sets the price level
     *
     * @param priceRange the price level to be set
     */
    public void setPriceRange(int priceRange) {
        this.priceRange = priceRange;
    }

    /**
     * Sets the working status
     *
     * @param openNow the status to be set
     */
    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    /**
     * Constructor based on name, cuisine, rating, price level, working status
     *
     * @param name Restaurant name
     * @param speciality Cuisine
     * @param stars Rating
     * @param priceRange Price level
     * @param openNow Working status
     */
    //TODO : This constructor is incomplete, using it as it is will lead to a lot of null pointer exceptions
    public Restaurant(String name, Speciality speciality, int stars, int priceRange, boolean openNow) {
        this.name = name;
        this.speciality = speciality;
        this.stars = stars;
        this.priceRange = priceRange;
        this.openNow = openNow;
    }

    /**
     * Returns working status
     *
     * @return boolean working status
     */
    public boolean isOpenNow() {
        return openNow;
    }

    /**
     * Returns working status
     *
     * @return boolean working status
     */
    private boolean openNow() {
        return LocalTime.now().isAfter(openedFrom) && LocalTime.now().isBefore(openedTo);
    }

    /**
     * Constructor based on id, name, opening hours, rating, price level, cuisine and address
     *
     * @param id ID of the Restaurant
     * @param name Restaurant name
     * @param openedFrom Start time
     * @param openedTo End time
     * @param stars Rating
     * @param priceRange Price level
     * @param speciality cuisine
     * @param location address
     */
    public Restaurant(long id, String name, LocalTime openedFrom, LocalTime openedTo, int stars, int priceRange, Speciality speciality, String location) {
        this.id = id;
        this.name = name;
        this.tableSchema = Generator.generateRandomTableSchema();
        int tsl = tableSchema.length();
        this.tables = new Table[tsl];
        for (int i = 0; i < tsl; i++) {
            this.tables[i] = new Table(i, this);
        }
        this.openedFrom = openedFrom;
        this.openedTo = openedTo;
        this.openNow = isOpenNow();
        this.stars = stars;
        this.priceRange = priceRange;
        this.restaurantFile = new File(Generator.generateFileName(id, name, openedFrom, openedTo));
        this.speciality = speciality;
        this.location = location;
        this.restInfo = "<<REST><ID:" + id + "><NAME:" + name + "><TS:" + tableSchema + ">" +
                "<OF:" + openedFrom + "><OT:" + openedTo + ">" +
                "<PRICE:" + priceRange + "><STARS:" + stars + ">" +
                "<SPEC:" + speciality + "><LOC:" + location + "></REST>>";
        if (!restaurantFile.exists() || DataHandler.readFile(restaurantFile).isEmpty()) {
            Saver.saveToFile(restaurantFile.getPath(), restInfo, 0);
        }
        Saver.addRestId(restaurantFile.getName().replace(".dat", "") + ",");
    }

    /**
     * Gets the id of the restaurant
     *
     * @return long id of the restaurant
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of the restaurant
     *
     * @param id id to be set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name of the restaurant
     *
     * @return String name of the restaurant
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the tables in the restaurant
     *
     * @param tables Tables in the restaurant
     */
    public void setTables(Table[] tables) {
        this.tables = tables;
    }

    /**
     * Gets the tables of the restaurant
     *
     * @return Table[] table of the restaurant
     */
    public Table[] getTables() {
        return this.tables;
    }

    /**
     * Sets the name of the restaurant
     *
     * @param name Name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the table schema of the restaurants
     *
     * @return String Table schema
     */
    public String getTableSchema() {
        return tableSchema;
    }

    /**
     * Sets the table schema
     *
     * @param tableSchema table schema to be set
     */
    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    /**
     * Gets start time of the restaurant
     *
     * @return LocalTime Start time
     */
    public LocalTime getOpenedFrom() {
        return openedFrom;
    }

    /**
     * Sets start time of the restaurant
     *
     * @param openedFrom Start time
     */
    public void setOpenedFrom(LocalTime openedFrom) {
        this.openedFrom = openedFrom;
    }

    /**
     * Gets end time of the restaurant
     *
     * @return LocalTime End time
     */
    public LocalTime getOpenedTo() {
        return openedTo;
    }

    /**
     * Sets end time of the restaurant
     *
     * @param openedTo End time
     */
    public void setOpenedTo(LocalTime openedTo) {
        this.openedTo = openedTo;
    }

    /**
     * Gets .dat file representing the restaurant
     *
     * @return File .dat
     */
    public File getRestaurantFile() {
        return this.restaurantFile;
    }

    /**
     * Sets the .dat file of the restaurant
     *
     * @param restaurantFile file to be set
     */
    public void setRestaurantFile(File restaurantFile) {
        this.restaurantFile = restaurantFile;
    }

    /**
     * Gets the list of reservations
     *
     * @return List<Reservation> list of reservations
     */
    public List<Reservation> getReservationList() {
        return reservationList;
    }

    /**
     * Sets the list of reservations
     *
     * @param reservationList list of reservations to be set
     */
    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    /**
     * Gets the rating
     *
     * @return int rating, stars
     */
    public int getStars() {
        return stars;
    }

    /**
     * Gets the price level
     *
     * @return int price level
     */
    public int getPriceRange() {
        return priceRange;
    }

    /**
     * Gets the cuisine
     *
     * @return Speciality cuisine
     */
    public Speciality getSpeciality() {
        return speciality;
    }

    public boolean makeReservation(Reservation reservation) {
        for (Reservation res : this.reservationList) {
            if (!res.timeSuitable(reservation.getReservationStart(), reservation.getReservationEnd())) return false;
        }
        return Saver.addReservation(reservation);
    }

    /**
     * Cancels the reservation, removes from Saver
     *
     * @param reservation Reservation to be canceled
     * @return boolean Status of the operation
     */
    public boolean cancelReservation(Reservation reservation) {
        if (reservation.isConfirmed()) {
            return false;
        }
        return Saver.removeReservation(reservation);
    }

    /*
    public void notifyUsers () {
        for ( Reservation reservation : reservationList ) {
            if ( !reservation.isConfirmed() && reservation.getReservationDate())
        }
    }
*/

    /**
     * Returns the information about the reservation in form of String
     *
     * @return String information about reservation
     */
    @Override
    public String toString() {

        if (reservationList.isEmpty()) {
            return restInfo;
        } else {
            final String[] temp = {restInfo};
            reservationList.forEach(res -> {
                temp[0] += res.toString();
            });
            return temp[0];
        }


    }

    public static void main(String[] args) {
        Restaurant r = new Restaurant(1, "gf", null, null, 0, 0, null, null);
        System.out.println(r.getTableSchema());
    }
}
