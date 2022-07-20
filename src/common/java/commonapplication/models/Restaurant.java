package commonapplication.models;

import commonapplication.persistancemanagement.DataHandler;
import commonapplication.persistancemanagement.Saver;

import java.io.File;
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

    public Restaurant(Speciality speciality, int stars, int priceRange, boolean openNow) {
        this.speciality = speciality;
        this.stars = stars;
        this.priceRange = priceRange;
        this.openNow = openNow;
    }

    // DONT DELETE
    public Restaurant() {
    }



    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setPriceRange(int priceRange) {
        this.priceRange = priceRange;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    //TODO : This constructor is incomplete, using it as it is will lead to a lot of null pointer exceptions
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
        int tsl = tableSchema.length();
        this.tables = new Table[tsl];
        for (int i = 0; i < tsl; i++) {
            if (tableSchema.charAt(i) == '1') {
                this.tables[i] = new Table(i, this);
            }
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

    public Speciality getSpeciality() {
        return speciality;
    }

    public boolean makeReservation(Reservation reservation) {
        for (Reservation res : this.reservationList) {
            if (!res.timeSuitable(reservation.getReservationStart(), reservation.getReservationEnd())) return false;
        }
        return Saver.addReservation(reservation);
    }

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
}
