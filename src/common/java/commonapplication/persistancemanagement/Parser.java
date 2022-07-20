package commonapplication.persistancemanagement;

import commonapplication.models.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Parser {


    // This parses a file (presumably not empty) and creates a Restaurant instance accordingly
    @NotNull
    public static Restaurant getRestaurantFromFile(@NotNull File file) {
        if (!file.exists()) return null;
        String restaurantData = DataHandler.readFile(file);
        String[] components = restaurantData.split("><", 11);
        // I hard coded this part just for a better performance,
        // otherwise there would be a lot of String iterations and also a lot of checks
        long id = Long.parseLong(components[1].substring(3));
        String name = components[2].substring(5);
        String tableSchema = components[3].substring(3);
        LocalTime openedFrom = LocalTime.parse(components[4].substring(3));
        LocalTime openedTo = LocalTime.parse(components[5].substring(3));
        int price = Integer.valueOf(components[6].substring(6));
        int stars = Integer.valueOf(components[7].substring(6));
        Speciality speciality = Enum.valueOf(Speciality.class, components[8].substring(5));
        String location = components[9].substring(4);
        Restaurant restaurant = new Restaurant(id, name, openedFrom, openedTo, stars, price, speciality, location);
        restaurant.setTableSchema(tableSchema);
        List<Reservation> reservationList = getReservationListFromFile(restaurant);
        restaurant.setRestaurantFile(file);
        restaurant.setReservationList(reservationList);
        return restaurant;
    }

    @NotNull
    public static List<Reservation> getReservationListFromFile(Restaurant restaurant) {
        String restaurantData = DataHandler.readFile(restaurant.getRestaurantFile());
        String reservationData = (new Object() { // Anonymous function that returns a string with all reservations
            @Override
            public String toString() {
                String[] temp = restaurantData.split("</REST>>", 1);
                if (temp.length == 1) return "";
                return temp[1];
            }
        }.toString());
        if (reservationData.length() == 0) {
            //No reservations have been made in this Restaurant
            return new ArrayList<Reservation>();
        } else {
            List<Reservation> reservationList = new ArrayList<>();
            String[] data = reservationData.split("<RES:");
            for (String str : data) {
                reservationList.add(getReservationById(str.substring(0, str.length() - 1), restaurant));
            }
            return reservationList;
        }
    }

    private static Reservation getReservationFromString(@NotNull String data, Restaurant restaurant) {
        if (data.length() == 0) {
            return null;
        } else {
            String[] components = data.split("><");
            Table table = restaurant.getTables()[Integer.parseInt(components[2].substring(4))];
            String username = components[3].substring(4);
            LocalTime reservationStart = LocalTime.parse(components[4].substring(3));
            LocalTime reservationEnd = LocalTime.parse(components[5].substring(3));
            LocalDate reservationDate = LocalDate.parse(components[6].substring(3));
            boolean confirmed = Boolean.parseBoolean(components[7].substring(2));
            Reservation reservation = new Reservation(reservationStart, reservationEnd, username, restaurant, table, reservationDate);
            reservation.setConfirmed(confirmed);
            return reservation;
        }

    }




    // returns a list of all users, an empty list if no users are registered
    public static List<User> getAllUsers() {
        File file = new File("src/server/resources/Usernames.dat");
        String fileContent = DataHandler.readFile(file);
        List<User> userList = new ArrayList<>();
        if (fileContent.length() <= 1) return userList;
        String[] temp = fileContent.split(",");
        for (String username : temp) {
            userList.add(getUserByUsername(username));
        }
        return userList;
    }



    //if no user exists with a given username, null is returned,
    // and client will be prompted to create a new User //TODO (PROMPT)
    public static User getUserByUsername(String username) {
        if (!userExists(username)) {
            return null;
        }
        File file = new File("src/server/resources/Users.dat");
        String userData = DataHandler.readFile(file);
        String[] temp = userData.split("<NAME:" + username + "><", 2);
        temp = temp[1].split("></USER>", 2);
        temp = temp[0].split("><", 2);
        int passwordHash = Integer.parseInt(temp[0].substring(4));
        return new User(username, passwordHash);
    }


    public static User getUserFromString(String str) {
        if (!str.contains("<RES:")) { //in case user doesn't have reservations
            String[] temp = str.split("><", 3);
            String username = temp[1].substring(5);
            int passwordHash = Integer.parseInt(temp[2].substring(4));
            return new User(username, passwordHash);
        } else {
            String[] temp = str.split("></USER>>", 2);
            User user = getUserFromString(temp[0]);
            temp = temp[1].split("<RES:");
            File file;
            for (String x : temp) {
                user.addReservation(getReservationById(x.substring(0, x.length() - 1), null)); //TODO : WRONG IMPLEMENTATION,WILL FIX
            }
            return user;
        }
    }


    public static Reservation getReservationById(String id, Restaurant restaurant) {
        File file = new File("src/server/resources/Reservations/" + id + ".dat");
        String reservationData = DataHandler.readFile(file);
        return getReservationFromString(reservationData.substring(8), restaurant);
    }


    // Returns the toString() of a user by Username from the Users file
    // empty String if user doesn't exist
    public static String getUserInfoByUsername(String username) {
        if (!userExists(username)) {
            return "";
        }
        File file = new File("src/server/resources/Users.dat");
        if (!file.exists()) return "";
        String userData = DataHandler.readFile(file);
        String[] temp = userData.split("<NAME:" + username + ">", 2);
        temp = temp[1].split("></USER>", 2);
        temp = temp[0].split("><");
        return "<<USER>" + temp[0] + "><" + temp[1] + "></USER>>";
    }

    public static boolean userExists(String username) {
        File file = new File("src/server/resources/Usernames.dat");
        if (!file.exists()) {
            Saver.saveToFile("", "", 3);
            return false;
        }
        String users = DataHandler.readFile(file);
        if (users == null) {
            return false;
        }
        String[] temp = users.split(",");
        for (String str : temp) {
            if (("/" + username).equals(str))
                return true;
        }
        return false;
    }

    public static Restaurant getRestaurantById(String restId) {
        File file = new File("src/server/resources/Restaurants/" + restId + ".dat");
        if (!file.exists()) {
            return null;
        }
        return getRestaurantFromFile(file);
    }


    public static List<Restaurant> getAllRestaurants() {
        File file = new File("src/server/resources/RestaurantIDs.dat");
        List<Restaurant> restaurantList = new ArrayList<>();
        if (!file.exists()) {
            return restaurantList;
        }
        String restaurantIDs = DataHandler.readFile(file);
        String[] idArray = restaurantIDs.split(",");
        for (String id : idArray) {
            File tempFile = new File("src/server/resources/Restaurants/" + id.replaceFirst("/", "") + ".dat");
            if (!tempFile.exists()) {
                continue;
            }
            restaurantList.add(getRestaurantFromFile(tempFile));
        }
        return restaurantList;
    }

    public static void main(String[] args) {
        //Parser.getUserById(2);
        //File file = new File("src/server/resources/Restaurants/1396431158.dat");
        //Restaurant restaurant =  getRestaurantFromFile(file);
        //System.out.println(restaurant.toString());
        //System.out.println(getReservationFromString("<<REST><ID:1><NAME:L'Osteria><TS:1110><OF:12:00><OT:00:00><PRICE:3><STARS:3><SPEC:Pizza><LOC:Somewhere></REST>><<RES><ID:1><RID:1><TAB:1><PID:1><RS:00:00><RE:23:59:59.999999999></RES>>",restaurant));
        //List<Reservation> list = getReservationListFromFile(file,restaurant);
        //list.forEach(e -> System.out.println(e.toString()));

        User user = new User("Chiheb", "nepderp".hashCode());
        Saver.addUser(user);
        //System.out.println(DataHandler.readFile(new File("src/server/resources/Usernames.dat")));
        //System.out.println(Parser.userExists(user.getUsername()));
        //System.out.println(userExists(user.getUsername()));
        //System.out.println(getUserById(2).toString());
        //LocalDate ld = LocalDate.parse("2000-06-13");
        //System.out.println(ld.toString());
        //System.out.println(getUserById(3).toString());
        //System.out.println(getUserFromString("<<USER><ID:1><NAME:Chiheb><PWD:1839215512></USER>>"));

        //User user = new User(1L, "Chiheb", "nepderp".hashCode());
        //User user2 = getUserById(1);
        //User newUser = changeUserData(user2, "<<USER><ID:112><NAME:NEPDERP><PWD:" + "nepderp123".hashCode() + "></USER>>");
        //System.out.println(newUser.toString());
        //getUserStringById(1);

        //List<Restaurant> x = getAllRestaurants();
        //x.forEach(e -> System.out.println(e.toString()));
    }
}
