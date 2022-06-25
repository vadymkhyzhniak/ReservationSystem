package springapplication.service;

import org.springframework.stereotype.Service;
import springapplication.models.Reservation;
import springapplication.models.User;
import springapplication.persistancemanagement.*;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

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

    public boolean addUser(User user){
        if(Parser.userExists(user.getUid()) || user.getName().isEmpty()){
            return false;
        }


        //TODO save user
        //Saver.addUser()
        return true;
    }
}
