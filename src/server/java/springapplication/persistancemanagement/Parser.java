package springapplication.persistancemanagement;

import org.jetbrains.annotations.NotNull;
import springapplication.models.Reservation;
import springapplication.models.Restaurant;
import springapplication.models.Table;
import springapplication.models.User;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static String dataTo(String data) {
        String tempData = data.substring(1, data.length() - 1);
        tempData.split("<");
        if (tempData.contains("<REST>")) {

        }
        return null;
    }

    public static Restaurant getRestaurantByName(String name) {
        return null;
    }

    // This parses a file (presumably not empty) and creates a Restaurant instance accordingly
    @NotNull
    public static Restaurant getRestaurantFromFile(@NotNull File file) {
        String restaurantData = DataHandler.readFile(file);
        String[] components = restaurantData.split("><", 9);
        // I hard coded this part just for a better performance,
        // otherwise there would be a lot of String iterations and also a lot of checks
        long id = Long.getLong(components[1].substring(3));
        String name = components[2].substring(5);
        String tableSchema = components[3].substring(3);
        LocalTime openedFrom = LocalTime.parse(components[4].substring(3));
        LocalTime openedTo = LocalTime.parse(components[5].substring(3));
        int price = Integer.valueOf(components[6].substring(6));
        int stars = Integer.valueOf(components[7].substring(6));
        return new Restaurant(id, name, openedFrom, openedTo, stars, price);
    }

    @NotNull
    public static List<Reservation> getReservationListFromFile(@NotNull File file) {
        String reservationData = DataHandler.readFile(file);

        if (!reservationData.contains("<RES>")) {
            //No reservations have been made in this Restaurant
            return new ArrayList<Reservation>();
        }
        return new ArrayList<>();
    }

    private static Reservation getReservationFromString(@NotNull String data, Restaurant restaurant) {
        if (data.length() == 0) {
            return null;
        } else {
            String[] components = data.split("><");
            long resId = Long.getLong(components[1].substring(3));
            long restId = Long.getLong(components[2].substring(4));
            Table table = restaurant.getTables()[Integer.getInteger(components[3].substring(4))];
            long uId = Long.getLong(components[4].substring(4));
        }
        return null;
    }

    public static List<Restaurant> getRestaurantListBy() {
        return null;
    }
    //if no user exists with that id, null is returned,
    // and client will be prompted to create a new User
    public static User getUserById(long id) {
        File file = new File("src/server/resources/Users.dat");
        String userData = DataHandler.readFile(file);
        if ( !userData.contains(String.valueOf(id))) {
            return null;
        }
        String[] temp =  userData.split("<ID:"+String.valueOf(id)+"><",2);
        temp = temp[1].split("></USER>",2);
        temp = temp[0].split("><",2);
        String name = temp[0].substring(5);
        int passwordHash = Integer.parseInt(temp[1].substring(4));
        return new User(id,name,passwordHash);
    }

    public static void main(String[] args) {
        Parser.getUserById(2);
    }
}
