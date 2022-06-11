package persistancemanagement;

import org.jetbrains.annotations.NotNull;
import springapplication.models.Reservation;
import springapplication.models.Restaurant;
import springapplication.models.Table;
import springapplication.models.User;

import java.io.*;
import java.time.LocalTime;

public class Saver {


    private static String readFile(File file) {
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader(file.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return input.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void saveToFile(long id, String data) {

        //Files.move()
    }

    private static void createNewRestaurantFile(File file){
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw;
        try {
            pw = new PrintWriter(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        pw.println("<REST></REST>");
    }

    public static void modifyReservation(@NotNull File file, Reservation reservation) {
        String restaurantData = Saver.readFile(file);
        String reservationData = reservation.toString();
        if (!file.exists()) {
                createNewRestaurantFile(file);
        }
        if (restaurantData.contains(reservationData)){
            restaurantData.replace(reservationData,"");
        } else {
            restaurantData.replace("</REST>",reservationData+"</REST>");
        }
        //
        PrintWriter pw;
        try {
            pw = new PrintWriter(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        pw.println(restaurantData);
        pw.flush();
        pw.close();
    }

    public static @NotNull String generateFileName(@NotNull Restaurant restaurant) {
        String temp = restaurant.getId() + restaurant.getName() + restaurant.getTableSchema() + restaurant.getOpenedFrom() + restaurant.getOpenedTo();
        return Integer.toString(Math.abs(temp.hashCode())) + ".dat";
    }

    public static void main (String[] args) {
        Restaurant restaurant = new Restaurant(1L,"L'Osteria", LocalTime.NOON,LocalTime.MIDNIGHT);
        File file = new File(restaurant.getRestaurantFile().getAbsolutePath());
        User user = new User(1L,"Chiheb");
        Table table = new Table(1L, "T1",restaurant);
        Reservation reservation = new Reservation(LocalTime.MIN,LocalTime.MAX,user,restaurant,1L,table);
        System.out.println(reservation.toString());
        Saver.modifyReservation(file,reservation);
    }

}
