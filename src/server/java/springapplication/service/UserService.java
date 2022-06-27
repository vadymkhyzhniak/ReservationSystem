package springapplication.service;

import commonapplication.persistancemanagement.Parser;
import commonapplication.persistancemanagement.Saver;
import org.springframework.stereotype.Service;
import commonapplication.models.Reservation;
import commonapplication.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
private final List<User> users;

    public UserService() {
        this.users =new ArrayList<>();
    }

    public boolean authenticateUser(User sentUser) {
        User realUser = Parser.getUserById(sentUser.getUid());
        return realUser != null && realUser.getPasswordHash() == sentUser.getPasswordHash();
    }

    public Optional<User> getUser(long id){
        User returnedUser = Parser.getUserById(id);
        if(returnedUser == null){
            return Optional.empty();
        }
        return Optional.of(returnedUser);
    }

    public Optional<List<Reservation>> getReservationsOfUser(long id){
        User returnedUser = Parser.getUserById(id);
        if(returnedUser == null){
            return Optional.empty();
        }
        return Optional.of(returnedUser.getReservationList());
    }

    public Optional<User> addUser(User user){
     if(Parser.userExists(user.getUid()) || user.getName().isEmpty()){
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
        s.addUser(new User(1L,"maha","123".hashCode()));
    }
}
