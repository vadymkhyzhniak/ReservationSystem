package springapplication.models;

import springapplication.persistancemanagement.Saver;

import java.io.File;
import java.time.LocalTime;

public class Restaurant {
    private long id;
    private String name;
    private String tableSchema;
    private Table[] tables;
    private LocalTime openedFrom;
    private LocalTime openedTo;

    private int stars;
    private int priceRange;
    private File restaurantFile;

    public Restaurant(long id, String name, LocalTime openedFrom, LocalTime openedTo, int stars, int priceRange) {
        this.id = id;
        this.name = name;
        this.tableSchema = Generator.generateRandomTableSchema();
        this.tables = new Table[tableSchema.length()];
        this.openedFrom = openedFrom;
        this.openedTo = openedTo;
        this.stars = stars;
        this.priceRange = priceRange;
        this.restaurantFile = new File(Saver.generateFileName(this));
    }

    public Restaurant(String name, LocalTime openedFrom, LocalTime openedTo, int stars, int priceRange) {
        this.name = name;
        this.tableSchema = Generator.generateRandomTableSchema();
        this.tables = new Table[tables.length];
        this.openedFrom = openedFrom;
        this.openedTo = openedTo;
        this.stars = stars;
        this.priceRange = priceRange;
        this.restaurantFile = new File(Saver.generateFileName(this));
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

    public File getRestaurantFile(){
        return this.restaurantFile;
    }

    @Override
    public String toString() {
        return "<<REST><ID:" + id + "><NAME:" + name + "><TS:" + tableSchema + "><OF:" + openedFrom + "><OT:" + openedTo +"></REST>>";
    }
}
