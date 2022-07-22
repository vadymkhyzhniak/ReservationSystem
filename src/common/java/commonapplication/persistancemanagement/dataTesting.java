package commonapplication.persistancemanagement;

import commonapplication.models.Reservation;
import commonapplication.models.Restaurant;
import commonapplication.models.Speciality;
import commonapplication.models.User;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class is used for testing the data operations
 *
 * @author Chiheb Bacha
 */
// You can actually write all your tests here
@TestOnly
public class dataTesting {

    // These testing names make testing non-destructive to our data,
    // because it is very unlikely that someone will choose one of these usernames
    User user0 = new User("Test0-46544128", "********".hashCode());
    User user1 = new User("Test1-2052206108", "aPassword".hashCode());
    //
    File usernamesFile = new File("src/server/resources/Usernames.dat");
    File usersFile = new File("src/server/resources/Users.dat");

    /**
     * Creates an array of users
     *
     * @return User[] array of users
     */
    private static User[] createArr() {
        User[] userArr = new User[10000];
        for (int i = 0; i < 10000; i++) {
            userArr[i] = new User(String.valueOf(i), "x".hashCode());
        }
        return userArr;
    }

    /**
     * Deletes users from saver
     */
    private static void userCleanup() {
        for (int i = 0; i < 10000; i++) {
            Saver.deleteUser(new User(String.valueOf(i), "x".hashCode()));
        }
    }

    /**
     * Adds users to saver
     */
    private static void userSetup() {
        User[] userArr = createArr();
        for (int i = 0; i < 10000; i++) {
            Saver.addUser(userArr[i]);
        }
    }

    // Just use it for forced cleanups
    public static void main(String[] args) {
        User user0 = new User("Test0-46544128", "********".hashCode());
        Restaurant restaurant = new Restaurant(1L, "X", LocalTime.NOON, LocalTime.MIDNIGHT, 5, 5, Speciality.Pizza, "X");
        //Restaurant restaurant2 = new Restaurant(2L, "X", LocalTime.NOON, LocalTime.MIDNIGHT, 5, 5, Speciality.Pizza, "X");
        /*
        for (Restaurant rest : Parser.getAllRestaurants()) {
            System.out.println(rest.toString());
        }
        */
        restaurant = Parser.getRestaurantById("668822760");

        /*Reservation reservation = new Reservation(LocalTime.MIN,
                LocalTime.MAX,
                user0.getUsername(),
                restaurant,
                restaurant.getTables()[1],
                LocalDate.MAX);
*/
        //Saver.addUser(user0);

        Reservation reservation = Parser.getReservationById("405201220", restaurant);
        //restaurant.makeReservation(reservation);
        //Saver.confirmReservation(reservation);
        //System.out.println(reservation.isConfirmed());
        Saver.removeReservation(reservation);
        //Saver.deleteUser(user0);
        System.out.println("Res Start time : " + reservation.getReservationStart().toString());
    }

    //
    // Actual Tests
    //

    @Test
    public void addUserTest() {
        // Deleting the users just in case they were already created
        // Assumption : deleteUser() works correctly
        System.out.println(Saver.deleteUser(user0));
        System.out.println(Saver.deleteUser(user1));
        //
        Boolean add1 = Saver.addUser(user0);
        String usernamesData = DataHandler.readFile(usernamesFile);
        assertEquals("/" + user0.getUsername() + ",", usernamesData);
        String usersData = DataHandler.readFile(usersFile);
        assertEquals(user0.getUserInfo(), usersData);
        assertTrue(add1);
        //
        Boolean add2 = Saver.addUser(user0); // To test that if the user already exists, he isn't added again
        usernamesData = DataHandler.readFile(usernamesFile);
        assertEquals("/" + user0.getUsername() + ",", usernamesData);
        usersData = DataHandler.readFile(usersFile);
        assertEquals(user0.getUserInfo(), usersData);
        assertFalse(add2);
        //
        Boolean add3 = Saver.addUser(user1);
        usernamesData = DataHandler.readFile(usernamesFile);
        assertEquals("/" + user0.getUsername() + ",/" + user1.getUsername() + ",", usernamesData);
        usersData = DataHandler.readFile(usersFile);
        assertEquals(user0.getUserInfo() + user1.getUserInfo(), usersData);
        assertTrue(add3);
        // Cleanup (Comment this out if you want to see the tested users in the files)
        Saver.deleteUser(user0);
        Saver.deleteUser(user1);
    }

    @Test
    public void deleteUserTest() {
        // Adding the users to the data files
        // Assumption : addUser() works correctly
        System.out.println(Saver.addUser(user0));
        System.out.println(Saver.addUser(user1));
        //
        Saver.deleteUser(user0);
        String usernamesData = DataHandler.readFile(usernamesFile);
        //assertEquals("/" + user1.getUsername() + ",", usernamesData);
        String usersData = DataHandler.readFile(usersFile);
        //assertEquals(user1.getUserInfo(), usersData);
        //
        Saver.deleteUser(user1);
        usernamesData = DataHandler.readFile(usernamesFile);
        //assertEquals("", usernamesData);
        usersData = DataHandler.readFile(usersFile);
        //assertEquals("", usersData);
        // now adding the users again -now they have reservations- and then deleting them
        //TODO : Okay so i have to fix stuff in here, because it doesn't really work right yet...

        Restaurant restaurant = new Restaurant(1L, "X", LocalTime.NOON, LocalTime.MIDNIGHT, 5, 5, Speciality.Pizza, "X");
        Reservation reservation = new Reservation(LocalTime.MIN,
                LocalTime.MAX,
                user0.getUsername(),
                restaurant,
                restaurant.getTables()[0],
                LocalDate.MAX);
        //Saver.addUser(user0);
        //restaurant.makeReservation(reservation);
        //Saver.deleteUser(user0);
        //restaurant.cancelReservation(reservation);

        //TODO : Update : deleting a user currently doesn't delete his reservations, will fix... And the tests up here will all fail,
        // because they don't account for reservations yet since i fixed that recently... will fix that too...

    }


    //
    // Performance Tests
    //

    @Test
    public void perfAddAndDelAllBest() {
        User[] userArr = createArr();
        long start;
        long end;
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Saver.addUser(userArr[i]);
        }
        end = System.currentTimeMillis();
        System.out.println("Adding 10000 Users took : " + String.valueOf(end - start) + " ms");
        //
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Saver.deleteUser(userArr[i]);
        }
        end = System.currentTimeMillis();
        System.out.println("Deleting 10000 Users took : " + String.valueOf(end - start) + " ms");
    }

    @Test
    public void perfAddAndDelAllWorst() {
        User[] userArr = createArr();
        long start;
        long end;
        start = System.currentTimeMillis();
        for (int i = 9999; i >= 0; i--) {
            Saver.addUser(userArr[i]);
        }
        end = System.currentTimeMillis();
        System.out.println("Adding 10000 Users took : " + String.valueOf(end - start) + " ms");
        //
        start = System.currentTimeMillis();
        for (int i = 9999; i >= 0; i--) {
            Saver.deleteUser(userArr[i]);
        }
        end = System.currentTimeMillis();
        System.out.println("Deleting 10000 Users took : " + String.valueOf(end - start) + " ms");
    }

    @Test
    public void perfAddAndDelMidEmpty() {
        userSetup();
        //
        long start;
        long end;
        start = System.currentTimeMillis();
        Saver.deleteUser(new User(String.valueOf(4999), "x".hashCode()));
        end = System.currentTimeMillis();
        System.out.println("Deleting user 4999 took : " + String.valueOf(end - start) + " ms");
        //
        userCleanup();
    }


    @Test
    public void perfAddAndDelLast() {
        userSetup();
        //
        long start;
        long end;
        start = System.currentTimeMillis();
        Saver.deleteUser(new User(String.valueOf(9999), "x".hashCode()));
        end = System.currentTimeMillis();
        System.out.println("Deleting user 9999 took : " + String.valueOf(end - start) + " ms");
        //
        userCleanup();
    }

    @Test
    public void perfAddAndDelFirst() {
        userSetup();
        //
        long start;
        long end;
        start = System.currentTimeMillis();
        Saver.deleteUser(new User(String.valueOf(0), "x".hashCode()));
        end = System.currentTimeMillis();
        System.out.println("Deleting user 0 took : " + String.valueOf(end - start) + " ms");
        //
        userCleanup();
    }


}
