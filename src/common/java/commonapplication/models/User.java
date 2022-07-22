package commonapplication.models;

import commonapplication.persistancemanagement.Saver;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class represents a user, used for handling operations on users
 *
 * @author Chiheb Bacha
 */
public class User {

    private String username;

    private int passwordHash;
    private List<Reservation> reservationList = new ArrayList<>();

    private String userInfo;

    /**
     * Gets the user name
     *
     * @return String User name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user name
     *
     * @param name User name to be set
     */
    public void setUsername(String name) {
        this.username = name;
    }

    /**
     * Gets the password hash
     *
     * @return int Password hash
     */
    public int getPasswordHash() {
        return passwordHash;
    }

    /**
     * Gets the list of the reservations of the user
     *
     * @return List<Reservation> list of the reservations
     */
    public List<Reservation> getReservationList() {
        return reservationList;
    }

    /**
     * Adds the reservation
     *
     * @param reservation Reservation to be added
     */
    public void addReservation(Reservation reservation) {
        this.reservationList.add(reservation);
    }

    /**
     * Sets the list of the reservations
     *
     * @param reservationList Reservations to be set
     */
    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    /**
     * Creates a user
     *
     * @param name User name
     * @param passwordHash Password hash
     */
    public User(String name, int passwordHash) {
        this.username = name;
        this.passwordHash = passwordHash;
        this.userInfo = "<<USER><NAME:" + name + ">" +
                "<PWD:" + passwordHash + "></USER>>";
    }

    /**
     * Simple user constructor
     */
    // DO NOT DELETE THIS CONSTRUCTOR. OTHERWISE NOTHING WILL WORK ANYMORE
    public User() {
    }

    /**
     * Constructor based on user name, password hash, list of reservations and user information
     *
     * @param username User name
     * @param passwordHash Password hash
     * @param reservationList List of the reservations
     * @param userInfo User information
     */
    public User(String username, int passwordHash, List<Reservation> reservationList, String userInfo) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.reservationList = reservationList;
        this.userInfo = userInfo;
    }

    /**
     * Returns the information about the user in form of String
     *
     * @return String information about user
     */
    public String toString() {
        if (this.reservationList.isEmpty()) {
            return userInfo;
        } else {
            final String[] temp = {userInfo};
            reservationList.forEach(res -> {
                temp[0] += "<RES:" + res.getId() + ">";
            });
            return temp[0];
        }
    }

    /**
     * Gets the user information
     *
     * @return String user information
     */
    public String getUserInfo() {
        return this.userInfo;
    }

    /**
     * Checks if two users are equal
     *
     * @param o Second user to compare with
     * @return boolean Status of the comparison
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return passwordHash == user.passwordHash && Objects.equals(username, user.getUsername());
    }

    /**
     * Returns hashcode of the instance
     *
     * @return int Hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, passwordHash);
    }

    /**
     * Sets the password hash
     * @param passwordHash Password hash to be set
     */
    // This will be used to change the password,
    // we don't care if the new password is equal to the old one
    private void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Sets the password
     *
     * @param password Password to be set
     */
    //This changes the value of the password hash on the current user instance and in the files as well
    public void setPassword(String password) {
        setPasswordHash(password.hashCode());
        Saver.modifyUser(this);
    }

    public static void main(String[] args) {
        User user = new User("TestUbahn", "TestUbahn".hashCode());
        Saver.addUser(user);
        Restaurant restaurant = new Restaurant(1L, "SomeRestaurant", LocalTime.NOON,
                LocalTime.MIDNIGHT, 5, 10, Speciality.Pizza, "Somewhere");
        Reservation reservation = new Reservation(LocalTime.MIN, LocalTime.MAX, user.getUsername(),
                restaurant, new Table(1L, restaurant), LocalDate.MAX);
        //restaurant.makeReservation(reservation);
        restaurant.cancelReservation(reservation);
        Saver.deleteUser(user);
    }

}
