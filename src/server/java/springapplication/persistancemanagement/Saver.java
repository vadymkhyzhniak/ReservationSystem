package springapplication.persistancemanagement;

import org.jetbrains.annotations.NotNull;
import springapplication.models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalTime;

public class Saver {


    //saves data to existing file, if it doesn't exist,
    // it creates a new one and writes the data to it
    // make sure to pass an id >= 0 if you want to modify
    // an individual Restaurant, and <0 if you want to add/
    // modify a restaurant short data in Restaurants.dat
    public static void saveToFile(String path, String data, boolean indRest) {

        PrintWriter pw;
        if (indRest) {
            try {
                pw = new PrintWriter(path);
                pw.println(data);
                pw.flush();
                pw.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                pw = new PrintWriter("src/server/resources/Restaurant.dat");
                pw.println(data);
                pw.flush();
                pw.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void createNewRestaurantFile(Restaurant restaurant) {

        PrintWriter pw;
        try {
            pw = new PrintWriter(Generator.generateFileName(restaurant));
            pw.println(restaurant.toString());
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void modifyReservation(@NotNull File file, Reservation reservation) {

        if (!file.exists()) {
            createNewRestaurantFile(reservation.getRestaurant());
        }
        String restaurantData = DataHandler.readFile(file);
        String reservationData = reservation.toString();
        if (restaurantData.contains(reservationData)) {
            restaurantData = restaurantData.replace(reservationData, "");
        } else {
            restaurantData = restaurantData.concat(reservationData);
        }
        saveToFile(Generator.generateFileName(reservation.getRestaurant()), restaurantData, true);
    }



    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant(1, "L'Osteria", LocalTime.NOON, LocalTime.MIDNIGHT, 3, 3,Speciality.Pizza,"Somewhere");
        Restaurant restaurant1 = new Restaurant(2, "L'Osteria", LocalTime.NOON, LocalTime.MIDNIGHT, 3, 3,Speciality.Pizza,"Somewhere");
        File file = new File(Generator.generateFileName(restaurant));
        File file1 = new File(Generator.generateFileName(restaurant1));
        User user = new User(1L, "Chiheb", "goodpass".hashCode());
        Table table = new Table(1L, restaurant);
        Table table1 = new Table(1L, restaurant1);
        Reservation reservation = new Reservation(LocalTime.MIN, LocalTime.MAX, 1, restaurant, 1L, table);
        Reservation reservation1 = new Reservation(LocalTime.MIN, LocalTime.MAX, 1, restaurant, 2L, table);
        Saver.modifyReservation(file, reservation);
        Saver.modifyReservation(file, reservation1);
        Reservation reservation2 = new Reservation(LocalTime.MIN, LocalTime.MAX, 1, restaurant1, 1L, table1);
        Reservation reservation3 = new Reservation(LocalTime.MIN, LocalTime.MAX, 1, restaurant1, 2L, table1);
        Saver.modifyReservation(file1, reservation2);
        Saver.modifyReservation(file1, reservation3);
        System.out.println(file.exists());
        System.out.println(Parser.getRestaurantFromFile(file).toString());
    }

    /*public static void main(String[] args) {
        File file = new File("src/server/resources/Restaurants/1.dat");
        createNewRestaurantFile(file);
        //saveToFile(1,"oogaBooga");
        //saveToFile(2,"x");
        System.out.println(DataHandler.readFile(file));
        Restaurant restaurant = new Restaurant(1,"rest1", LocalTime.NOON,LocalTime.MIDNIGHT,0,0);
        Table table = new Table(1,restaurant);
        Reservation reservation = new Reservation(LocalTime.MIN,LocalTime.MAX,1,restaurant,1,table);
        modifyReservation(file,reservation);
    }*/

}
