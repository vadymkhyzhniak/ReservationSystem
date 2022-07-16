package commonapplication.models;

import commonapplication.persistancemanagement.Saver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private String username;

    private int passwordHash;
    private List<Reservation> reservationList = new ArrayList<>();

    private String userInfo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }


    public int getPasswordHash() {
        return passwordHash;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void addReservation(Reservation reservation) {
        this.reservationList.add(reservation);
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public User(String name, int passwordHash) {
        this.username = name;
        this.passwordHash = passwordHash;
        this.userInfo = "<<USER><NAME:" + name + ">" +
                "<PWD:" + passwordHash + "></USER>>";
    }

    // DO NOT DELETE THIS CONSTRUCTOR. OTHERWISE NOTHING WILL WORK ANYMORE
    public User() {
    }

    public User(String username, int passwordHash, List<Reservation> reservationList, String userInfo) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.reservationList = reservationList;
        this.userInfo = userInfo;
    }

    public String toString() {
        if (this.reservationList.isEmpty()) {
            return userInfo;
        } else {
            String temp = userInfo;
            reservationList.forEach(res -> {
                temp.concat(res.toString());
            });
            return temp;
        }
    }

    public String getUserInfo() {
        return this.userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return passwordHash == user.passwordHash && Objects.equals(username, user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, passwordHash);
    }

    // This will be used to change the password,
    // we don't care if the new password is equal to the old one
    private void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }

    //This changes the value of the password hash on the current user instance and in the files as well
    public void setPassword(String password) {
        setPasswordHash(password.hashCode());
        Saver.modifyUser(this);
    }


}
