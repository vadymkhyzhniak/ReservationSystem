package commonapplication.persistancemanagement;

import commonapplication.models.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

public class Saver {


    //saves data to existing file, if it doesn't exist,
    // it creates a new one and writes the data to it
    // make sure to pass an id >= 0 if you want to modify
    // an individual Restaurant, and -1 if you want to add/
    // modify a restaurant short data in Restaurants.dat
    // and -2 if you want to save into the Users.dat file
    public static void saveToFile(String path, String data, int config) {
        String str = "";
        switch (config) {
            case 0 -> str = path;
            case 1 -> str = "src/server/resources/Restaurants.dat";
            case 2 -> str = "src/server/resources/Users.dat";
            case 3 -> str = "src/server/resources/UsersIDs.dat";
        }

        PrintWriter pw;
        try {
            pw = new PrintWriter(str);
            pw.println(data);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private static void createNewFile(Object object) {
        PrintWriter pw;
        try {
            if (object instanceof Restaurant) {
                Restaurant restaurant = (Restaurant) object;
                pw = new PrintWriter(Generator.generateFileName(restaurant));
                pw.println(restaurant.toString());
                pw.flush();
                pw.close();
            } else if (object instanceof User) {
                User user = (User) object;
                pw = new PrintWriter("src/server/resources/Users.dat");
                pw.println(user.toString());
                pw.flush();
                pw.close();
                pw = new PrintWriter("src/server/resources/UsersIDs.dat");
                pw.println(user.getUid());
                pw.flush();
                pw.close();
            } else if ( object instanceof String) {
                String fileContent = (String) object;
                pw = new PrintWriter("src/server/resources/RestaurantIDs.dat");
                pw.println(fileContent);
                pw.flush();
                pw.close();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    public static void modifyData(@NotNull File file, Object object) {
        if (object instanceof Reservation) {
            Reservation reservation = (Reservation) object;
            if (!file.exists()) {
                createNewFile(reservation.getRestaurant());
                return;
            }
            String restaurantData = DataHandler.readFile(file);
            String reservationData = reservation.toString();
            if (restaurantData.contains(reservationData)) {
                restaurantData = restaurantData.replaceFirst(reservationData, "");
            } else {
                restaurantData = restaurantData.concat(reservationData);
            }
            saveToFile(Generator.generateFileName(reservation.getRestaurant()), restaurantData, 1);
        } else if ( object instanceof User) {
            User user = (User) object;
            if (!file.exists()) {
                createNewFile(user);
                return;
            }
            String usersData = DataHandler.readFile(file);
            String userData = user.toString();
            //if ( usersData.contains(userData) )
        } else if ( object instanceof String) {
            // this is assuming that userExists() returned false before
            // executing this method
            String restaurantID = (String) object;
            if (!file.exists()) {
                createNewFile(restaurantID);
                return;
            }
            String fileContent = DataHandler.readFile(file);
            fileContent = fileContent.concat(restaurantID);
            saveToFile("",fileContent,3);
        }
    }

    /*public static void modifyUserData(User user) {
        if (!file.exists()) {
            createNewFile(user.toString());
        }
    }*/


    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant(1, "L'Osteria", LocalTime.NOON, LocalTime.MIDNIGHT, 3, 3, Speciality.Pizza, "Somewhere");
        Restaurant restaurant1 = new Restaurant(2, "L'Osteria", LocalTime.NOON, LocalTime.MIDNIGHT, 3, 3, Speciality.Pizza, "Somewhere");
        File file = new File(Generator.generateFileName(restaurant));
        File file1 = new File(Generator.generateFileName(restaurant1));
        User user = new User(1L, "Chiheb", "goodpass".hashCode());
        Table table = new Table(1L, restaurant);
        Table table1 = new Table(1L, restaurant1);
        Reservation reservation = new Reservation(LocalTime.MIN, LocalTime.MAX, 1, restaurant, 1L, table, LocalDate.now());
        Reservation reservation1 = new Reservation(LocalTime.MIN, LocalTime.MAX, 1, restaurant, 2L, table, LocalDate.now());
        Saver.modifyData(file, reservation);
        Saver.modifyData(file, reservation1);
        Reservation reservation2 = new Reservation(LocalTime.MIN, LocalTime.MAX, 1, restaurant1, 1L, table1, LocalDate.now());
        Reservation reservation3 = new Reservation(LocalTime.MIN, LocalTime.MAX, 1, restaurant1, 2L, table1, LocalDate.now());
        Saver.modifyData(file1, reservation2);
        Saver.modifyData(file1, reservation3);
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
