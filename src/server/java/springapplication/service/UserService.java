package springapplication.service;

import commonapplication.models.Reservation;
import commonapplication.models.User;
import commonapplication.persistancemanagement.Parser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
private final List<User> users;

    public UserService() {
        this.users = new ArrayList<>();
    }

    public boolean authenticateUser(User sentUser) {
        User realUser = Parser.getUserByUsername(sentUser.getUsername());
        return realUser != null && realUser.getPasswordHash() == sentUser.getPasswordHash();
    }

    public Optional<User> getUser(String username) {
        User returnedUser = Parser.getUserByUsername(username);
        if (returnedUser == null) {
            return Optional.empty();
        }
        return Optional.of(returnedUser);
    }

    public Optional<List<Reservation>> getReservationsOfUser(String username) {
        User returnedUser = Parser.getUserByUsername(username);
        if (returnedUser == null) {
            return Optional.empty();
        }
        return Optional.of(returnedUser.getReservationList());
    }

    public Optional<User> addUser(User user) {
        if (Parser.userExists(user.getUsername()) || user.getUsername().isEmpty()) {
            return Optional.empty();
        }
        //TODO save user
       /*  users.add(user);
        String str= "<<USER><ID:"+user.getUid()+"><NAME:"+user.getName()+"><PWD:" +user.getPasswordHash()+ "></USER>>";
        Saver.saveToFile("",str,2);
        */
        return Optional.of(user);
    }

    public static void main(String[] args) {
        UserService s= new UserService();
        s.addUser(new User("maha", "123".hashCode()));
    }
}
