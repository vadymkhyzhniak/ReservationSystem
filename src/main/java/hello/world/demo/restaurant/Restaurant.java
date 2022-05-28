package hello.world.demo.restaurant;

import hello.world.demo.tables.Table;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;

public class Restaurant {
    private long id;
    private String name;
    private Table[] tableSchema;
    private LocalTime openedFrom;
    private LocalTime openedTo;

    public Restaurant() {
    }

    public Restaurant(long id, String name, Table[] tableSchema, LocalTime openedFrom, LocalTime openedTo) {
        this.id = id;
        this.name = name;
        this.tableSchema = tableSchema;
        this.openedFrom = openedFrom;
        this.openedTo = openedTo;
    }

    public Restaurant(String name, Table[] tableSchema, LocalTime openedFrom, LocalTime openedTo) {
        this.name = name;
        this.tableSchema = tableSchema;
        this.openedFrom = openedFrom;
        this.openedTo = openedTo;
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

    public void setName(String name) {
        this.name = name;
    }

    public Table[] getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(Table[] tableSchema) {
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tableSchema=" + Arrays.toString(tableSchema) +
                ", openedFrom=" + openedFrom +
                ", openedTo=" + openedTo +
                '}';
    }
}
