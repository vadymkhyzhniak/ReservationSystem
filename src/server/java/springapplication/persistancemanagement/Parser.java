package springapplication.persistancemanagement;

import org.jetbrains.annotations.NotNull;
import springapplication.models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
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
    public static Restaurant getRestaurantFromFile(@NotNull File file){
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
        return new Restaurant(id, name, openedFrom, openedTo, stars, price, speciality, location);
    }

    @NotNull
    public static List<Reservation> getReservationListFromFile(@NotNull File file, Restaurant restaurant) {
        String reservationData = DataHandler.readFile(file);

        if (!reservationData.contains("<RES>")) {
            //No reservations have been made in this Restaurant
            return new ArrayList<Reservation>();
        } else {
            List<Reservation> reservationList = new ArrayList<>();
            String[] data = reservationData.split("<RES>");
            for (String str : data) {
                reservationList.add(getReservationFromString(str, restaurant));
            }
            return reservationList;
        }
    }

    private static Reservation getReservationFromString(@NotNull String data, Restaurant restaurant) {
        if (data.length() == 0) {
            return null;
        } else {
            String[] components = data.split("><");
            long resId = Long.getLong(components[1].substring(3));
            Table table = restaurant.getTables()[Integer.getInteger(components[3].substring(4))];
            long uId = Long.getLong(components[4].substring(4));
            LocalTime reservationStart = LocalTime.parse(components[5].substring(3));
            LocalTime reservationEnd = LocalTime.parse(components[6].substring(3));
            return new Reservation(reservationStart, reservationEnd, uId, restaurant, resId, table, LocalDate.now());
        }

    }

    public static List<Restaurant> getRestaurantListBy() {
        return null;
    }

    /*public static List<User> getAllUsers(){

    }*/

    //Returns a list of all users with a Reservation for the next day
    /*public static List<User> getAllUsersWithRes(){

    }*/


    //if no user exists with that id, null is returned,
    // and client will be prompted to create a new User
    public static User getUserById(long id) {
        File file = new File("src/server/resources/Users.dat");
        String userData = DataHandler.readFile(file);
        if (!userData.contains(String.valueOf(id))) {
            return null;
        }
        String[] temp = userData.split("<ID:" + String.valueOf(id) + "><", 2);
        temp = temp[1].split("></USER>", 2);
        temp = temp[0].split("><", 2);
        String name = temp[0].substring(5);
        int passwordHash = Integer.parseInt(temp[1].substring(4));
        return new User(id, name, passwordHash);
    }

    // This changes the user data in the save file and
    // returns a new user with the new updated data
    // You can't change ID of user! so the method providing the data
    // parameter should give a string in the form :
    // <<USER><ID:<user.id>><NAME:<newName>><PWD:<newPWD>></USER>>
    public static User changeUserData(User user, String data) {
        File file = new File("src/server/resources/Users.dat");
        String userData = DataHandler.readFile(file);
        if (!data.contains("<ID:" + user.getUid() + ">")) {
            return user;
        } // if there is an attempt to change the id, nothing changes
        // and the initial user is returned
        Saver.saveToFile("", userData.replaceFirst(user.toString(), data), -2);
        return getUserFromString(data);
    }

    public static User getUserFromString(String str) {
        String[] temp = str.split("><", 5);
        long id = Long.parseLong(temp[1].substring(3));
        String name = temp[2].substring(5);
        int passwordHash = Integer.parseInt(temp[3].substring(4));
        return new User(id, name, passwordHash);
    }

    // Returns the toString() of a user by ID from the Users file
    // empty String if user doesn't exist
    public static String getUserStringById(long id) {
        File file = new File("src/server/resources/Users.dat");
        String userData = DataHandler.readFile(file);
        String idStr = String.valueOf(id);
        if (!userData.contains(idStr)) {
            return "";
        }
        String[] temp = userData.split("<ID:" + idStr + ">", 2);
        temp = temp[1].split("></USER>", 2);
        temp = temp[0].split("><");
        return "<<USER><ID:" + idStr + ">" + temp[0] + "><" + temp[1] + "></USER>>";
    }

    public static boolean userExists(long id) {
        File file = new File("src/server/resources/Users.dat");
        String users = DataHandler.readFile(file);
        return users.contains("<USER><ID:" + String.valueOf(id) + ">");
    }

    public static List<Restaurant> getAllRestaurants(){
        File file = new File("src/server/resources/RestaurantIDs.dat");
        List<Restaurant> restaurantList = new ArrayList<>();
        if (!file.exists()) {
            return restaurantList;
        }
        String restaurantIDs = DataHandler.readFile(file);
        String[] idArray = restaurantIDs.split(",");
        for (String id : idArray) {
            File tempFile = new File("src/server/resources/Restaurants/"+ id + ".dat");
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

        //User user = new User(1,"Chiheb","nepderp".hashCode());
        //Saver.saveToFile("",user.toString(),-2);
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

        List<Restaurant> x = getAllRestaurants();
        x.forEach(e -> System.out.println(e.toString()));
    }
}
