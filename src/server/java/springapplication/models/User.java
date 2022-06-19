package springapplication.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private long uid;

    private String name;

    private int passwordHash;

    private List<Reservation> reservationList = new ArrayList<>();

    private String userInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    public User(long uid, String name, int passwordHash) {
        this.uid = uid;
        this.name = name;
        this.passwordHash = passwordHash;
        this.userInfo = "<<USER><ID:" + uid + "><NAME:" + name + ">" +
                "<PWD:" + passwordHash + "></USER>>";
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uid == user.uid && passwordHash == user.passwordHash && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, name, passwordHash);
    }

    // This will be used to change the password,
    // we don't care if the new password is equal to the old one
    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }
}
