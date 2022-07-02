package commonapplication.models;

import commonapplication.persistancemanagement.Saver;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    public Restaurant(String name, Speciality speciality, int stars, int priceRange, boolean openNow) {
        this.name = name;
        this.speciality = speciality;
        this.stars = stars;
        this.priceRange = priceRange;
        this.openNow = openNow;
    }

    public boolean isOpenNow() {
        return openNow;
    }

    private boolean openNow() {
        return LocalTime.now().isAfter(openedFrom) && LocalTime.now().isBefore(openedTo);
    }

    public Restaurant(long id, String name, LocalTime openedFrom, LocalTime openedTo, int stars, int priceRange, Speciality speciality, String location) {
        this.id = id;
        this.name = name;
        this.tableSchema = Generator.generateRandomTableSchema();
        this.tables = new Table[tableSchema.length()];
        this.openedFrom = openedFrom;
        this.openedTo = openedTo;
        this.openNow = isOpenNow();
        this.stars = stars;
        this.priceRange = priceRange;
        this.restaurantFile = new File("src/server/resources/Restaurants/" + Generator.generateFileName(this));
        this.speciality = speciality;
        this.location = location;
        this.restInfo = "<<REST><ID:" + id + "><NAME:" + name + "><TS:" + tableSchema + ">" +
                "<OF:" + openedFrom + "><OT:" + openedTo + ">" +
                "<PRICE:" + priceRange + "><STARS:" + stars + ">" +
                "<SPEC:" + speciality + "><LOC:" + location + "></REST>>";

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setTables(Table[] tables) {
        this.tables = tables;
    }

    public Table[] getTables() {
        return this.tables;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public LocalTime getOpenedFrom() {
        return openedFrom;
    }

    public void setOpenedFrom(LocalTime openedFrom) {
        this.openedFrom = openedFrom;
    }

    public LocalTime getOpenedTo() {
        return openedTo;
    }

    public void setOpenedTo(LocalTime openedTo) {
        this.openedTo = openedTo;
    }

    public File getRestaurantFile() {
        return this.restaurantFile;
    }

    public void setRestaurantFile(File restaurantFile) {
        this.restaurantFile = restaurantFile;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public int getStars() {
        return stars;
    }

    public int getPriceRange() {
        return priceRange;
    }

    public boolean makeReservation(LocalTime reservationStart, LocalTime reservationEnd,
                                   String reservedBy, Table table, LocalDate reservationDate) {
        for (Reservation res : this.reservationList) {
            if (!res.timeSuitable(reservationStart, reservationEnd)) return false;
        }
        Reservation reservation = new Reservation(reservationStart, reservationEnd, reservedBy, this, table, reservationDate);
        this.reservationList.add(reservation);
        Saver.addReservation(reservation);
        //TODO : addReservation only adds it to the restaurant files, i'm actually a bit
        // confused now on where to store the reservation data, i have to change a lot
        // of other stuff, because some methods i worked on assume that reservations are stored
        // in some places and some other methods assume reservations are stored in other
        // places. WILL FIX
        return true;
    }

    @Override
    public String toString() {

        if (reservationList.isEmpty()) {
            return restInfo;
        } else {
            String temp = restInfo;
            reservationList.forEach(res -> {
                temp.concat(res.toString());
            });
            return temp;
        }

    }
}
