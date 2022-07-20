package commonapplication.persistancemanagement;

import commonapplication.models.*;

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
            case 1 -> str = "src/server/resources/RestaurantIDs.dat";
            case 2 -> str = "src/server/resources/Users.dat";
            case 3 -> str = "src/server/resources/Usernames.dat";
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


    /*public static void createEmptyFile(String path) {
        try {
            PrintWriter pw = new PrintWriter(path);
            pw.println("");
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/

    public static void createNewFile(Object object, int config) {
        PrintWriter pw;
        try {
            if (object instanceof Restaurant) {
                Restaurant restaurant = (Restaurant) object;
                if (config == 0 || config == 2) {
                    pw = new PrintWriter(Generator.generateFileName(restaurant));
                    pw.println(restaurant.toString());
                    pw.flush();
                    pw.close();
                }
                if (config == 1 || config == 2) {
                    pw = new PrintWriter("src/server/resources/RestaurantIDs.dat");
                    pw.println(Generator.generateFileName(restaurant) + ",");
                    pw.flush();
                    pw.close();
                }
            } else if (object instanceof User) {
                User user = (User) object;
                if (config == 0 || config == 2) {
                    pw = new PrintWriter("src/server/resources/Users.dat");
                    pw.println(user.toString());
                    pw.flush();
                    pw.close();
                }
                if (config == 1 || config == 2) {
                    pw = new PrintWriter("src/server/resources/Usernames.dat");
                    pw.println(user.getUsername());
                    pw.flush();
                    pw.close();
                }
            } else if (object instanceof Reservation) {
                Reservation reservation = (Reservation) object;
                if (config == 0 || config == 2) {
                    pw = new PrintWriter("src/server/resources/Reservations/" + reservation.getId() + ".dat");
                    pw.println(reservation.toString());
                    pw.flush();
                    pw.close();
                }
                if (config == 1 || config == 2) {
                    pw = new PrintWriter("src/server/resources/ReservationIDs.dat");
                    pw.println(reservation.getId() + ",");
                    pw.flush();
                    pw.close();
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addReservation(Reservation reservation) {
        return modifyData(reservation, 0);
    }

    public static boolean removeReservation(Reservation reservation) {
        return modifyData(reservation, 1);
    }

    public static boolean confirmReservation(Reservation reservation) {
        return modifyData(reservation, 9);
    }


    private static boolean modifyData(Object object, int config) {
        if (object instanceof Reservation) {
            Reservation reservation = (Reservation) object;
            File restFile = reservation.getRestaurant().getRestaurantFile();
            File usersFile = new File("src/server/resources/Users.dat");
            File resFile = new File("src/server/resources/Reservations/" + reservation.getId() + ".dat");
            String usersData = DataHandler.readFile(usersFile);
            if (restFile != null && !restFile.exists()) { //This shouldn't really happen, since we call this method on a restaurant,
                // and so we already assume that the restaurant file exists, as it was created upon the instantiation of the restaurant.
                createNewFile(reservation.getRestaurant(), 2);
                return false;
            }
            if (resFile != null && !resFile.exists()) {
                createNewFile(reservation, 2);
                return false;
            }
            String restaurantData = DataHandler.readFile(restFile);
            String reservationData = reservation.toString();
            User reservedBy = Parser.getUserByUsername(reservation.getReservedBy());
            Restaurant restaurant = reservation.getRestaurant();
            String resId = "<RES:" + reservation.getId() + ">";
            if (config == 1) { // for deleting and cancelling a reservation
                restaurantData = restaurantData.replaceFirst(resId, "");
                reservedBy.getReservationList().remove(reservation);
                usersData = usersData.replaceFirst(resId, "");
                restaurant.getReservationList().remove(reservation);
            } else if (config == 9) { //for confirming a reservation
                String confirmedRes = reservationData.replaceFirst("<C:false>", "<C:true>");
                saveToFile(reservation.getResFile().getPath(), confirmedRes, 0);
                return true;
            } else {
                if (!restaurantData.contains("<RES:" + reservation.getId() + ">")) {
                    restaurantData = restaurantData.concat(reservation.getId());
                    restaurant.getReservationList().add(reservation);
                }
                if (!usersData.contains("<RES:" + reservation.getId() + ">")) {
                    reservedBy.addReservation(reservation);
                    String reserverInfo = reservedBy.getUserInfo();
                    usersData = usersData.replaceFirst(reserverInfo, reserverInfo + reservation.toString());
                }
            }
            saveToFile(Generator.generateFileName(reservation.getRestaurant()), restaurantData, 0);
            saveToFile("", usersData, 2);
            return true;
        } else if (object instanceof User) {
            User user = (User) object;
            if (config == 0) {
                if (Parser.userExists(user.getUsername())) {
                    //If user already exists, nothing happens. He is told that this username is already taken
                    // TODO : this should be implemented on client (COMMENT)
                    return false;
                } else {
                    File file1 = new File("src/server/resources/Usernames.dat");
                    File file2 = new File("src/server/resources/Users.dat");
                    boolean f1e = file1.exists();
                    boolean f2e = file2.exists();
                    if (!f1e || !f2e) {
                        if (!f1e && !f2e) {
                            createNewFile(user, 2);
                        } else if (!f1e) {
                            createNewFile(user, 1);
                            String usersData = DataHandler.readFile(file2);
                            usersData = usersData.concat(user.toString());
                            saveToFile("", usersData, 2);
                        } else { //!f2e
                            createNewFile(user, 0);
                            String usernames = DataHandler.readFile(file1);
                            usernames = usernames.concat("/" + user.getUsername() + ",");
                            saveToFile("", usernames, 3);
                        }
                    } else {
                        String usersData = DataHandler.readFile(file2);
                        usersData = usersData.concat(user.toString());
                        saveToFile("", usersData, 2);
                        String usernames = DataHandler.readFile(file1);
                        usernames = usernames.concat("/" + user.getUsername() + ",");
                        saveToFile("", usernames, 3);
                    }
                }
                return true;
            } else if (config == -1) {
                File file1 = new File("src/server/resources/Usernames.dat");
                File file2 = new File("src/server/resources/Users.dat");
                boolean f1e = file1.exists();
                boolean f2e = file2.exists();
                if (!f1e || !f2e) {
                    if (!f1e && !f2e) {
                        //this case is where someone deletes the files, the account is not deleted,
                        // but is as good as deleted because the files don't exist anyway
                    } else if (!f1e) {
                        String usersData = DataHandler.readFile(file2);
                        usersData = usersData.replaceFirst(user.getUserInfo(), "");
                        for (Reservation userRes : user.getReservationList()) {
                            usersData = usersData.replaceFirst(userRes.toString(), "");
                        }
                        saveToFile("", usersData, 2);
                    } else {
                        String usernames = DataHandler.readFile(file1);
                        usernames = usernames.replaceFirst("/" + user.getUsername() + ",", "");
                        saveToFile("", usernames, 3);
                    }
                } else {
                    String usersData = DataHandler.readFile(file2);
                    String usernames = DataHandler.readFile(file1);
                    for (Reservation userRes : user.getReservationList()) {
                        System.out.println("UserRes before del " + userRes.toString());
                        usersData = usersData.replaceFirst(userRes.getId(), "");
                        System.out.println("UserRes after del " + userRes.toString());
                    }
                    System.out.println("usersData before user deletion :" + usersData);
                    usersData = usersData.replaceFirst(user.getUserInfo(), "");
                    System.out.println("usersData after user deletion :" + usersData);
                    System.out.println();
                    usernames = usernames.replaceFirst("/" + user.getUsername() + ",", "");
                    saveToFile("", usersData, 2);
                    saveToFile("", usernames, 3);
                }
                return true;
                //true here just stands for the non-existence of the user in the files after this method is executed
            } else {
                if (!Parser.userExists(user.getUsername())) {
                    //If user doesn't already exist, nothing happens and you can't modify any data.
                    // False is returned and should be handled by prompting the user to create a new account
                    //or an appropriate message
                    //Again this should not normally happen, because only a logged in user can change his password,
                    //it will happen however, if someone deleted the usernames file
                    // TODO : this should be implemented on client (COMMENT)
                    return false;
                } else {
                    String strToReplace = Parser.getUserInfoByUsername(user.getUsername());
                    File file0 = new File("src/server/resources/Users.dat");
                    if (!file0.exists()) {
                        return false;
                    }
                    String userData = DataHandler.readFile(file0);
                    userData = userData.replaceFirst(strToReplace, user.getUserInfo());
                    saveToFile("", userData, 2);
                    return true;
                }
            }
        } else if (object instanceof String) {
            String restId = (String) object;
            String restIDs = DataHandler.readFile(new File("src/server/resources/RestaurantIDs.dat"));
            if (restIDs.contains("/" + restId)) {
                return false;
            }
            restIDs = restIDs + "/" + restId;
            saveToFile("", restIDs, 1);
        }
        return true;
    }

    public static boolean addRestId(String Id) {
        return modifyData(Id, 0);
    }

    public static boolean addUser(User user) {
        return modifyData(user, 0);
    }

    // this deletes a user and all his reservations from the save files
    public static boolean deleteUser(User user) {
        // Some existence checks were omitted inside modifyData ,
        // because technically you are only able to delete a user if you are logged in as that user,
        // which implies it already exists, same applies for the modifyUser method.
        return modifyData(user, -1);
    }

    // This can only modify the Password. user.setPassword(String password) should be called which then modifies the password
    // on the user instance, setPassword() will then call this method and change the new password to the file
    public static boolean modifyUser(User user) {
        return modifyData(user, -2);
    }


    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant(1, "L'Osteria", LocalTime.NOON, LocalTime.MIDNIGHT, 3, 3, Speciality.Pizza, "Somewhere");
        Restaurant restaurant1 = new Restaurant(2, "L'Osteria", LocalTime.NOON, LocalTime.MIDNIGHT, 3, 3, Speciality.Pizza, "Somewhere");
        File file = new File(Generator.generateFileName(restaurant));
        File file1 = new File(Generator.generateFileName(restaurant1));
        User user = new User("Chiheb", "goodpass".hashCode());
        Table table = new Table(1L, restaurant);
        Table table1 = new Table(1L, restaurant1);
        Reservation reservation = new Reservation(LocalTime.MIN, LocalTime.MAX, "CHIHEB", restaurant, table, LocalDate.now());
        Reservation reservation1 = new Reservation(LocalTime.MIN, LocalTime.MAX, "MAHA", restaurant, table, LocalDate.now());
        Saver.modifyData(reservation, 0);
        Saver.modifyData(reservation1, 0);
        Reservation reservation2 = new Reservation(LocalTime.MIN, LocalTime.MAX, "NIKLAS", restaurant1, table1, LocalDate.now());
        Reservation reservation3 = new Reservation(LocalTime.MIN, LocalTime.MAX, "PRAMOD", restaurant1, table1, LocalDate.now());
        Saver.modifyData(reservation2, 0);
        Saver.modifyData(reservation3, 0);
        System.out.println(file.exists());
        Restaurant restaurant2 = Parser.getRestaurantFromFile(file);
        System.out.println(restaurant2 == null ? null : restaurant2.toString());
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
