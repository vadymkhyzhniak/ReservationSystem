package springapplication.service;

import commonapplication.models.Reservation;
import commonapplication.models.User;
import commonapplication.persistancemanagement.Parser;
import commonapplication.persistancemanagement.Saver;
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

    /**
     * Authenticates a specific User
     * Returns true if the passwordHash and userId of the User Object are equal to the one we have in the File System
     */
    public boolean authenticateUser(User sentUser) {
        User realUser = Parser.getUserByUsername(sentUser.getUsername());
        return realUser != null && realUser.getPasswordHash() == sentUser.getPasswordHash();
    }

    /**
     * Return an Optional of a specific User
     */
    public Optional<User> getUser(String username) {
        User returnedUser = Parser.getUserByUsername(username);
        if (returnedUser == null) {
            return Optional.empty();
        }
        return Optional.of(returnedUser);
    }

    /**
     * Return an Optional of the reservation List of a user
     */
    public Optional<List<Reservation>> getReservationsOfUser(String username) {
        User returnedUser = Parser.getUserByUsername(username);
        if (returnedUser == null) {
            return Optional.empty();
        }
        return Optional.of(returnedUser.getReservationList());
    }

    /**
     * Returns the user that was added to the DB
     */
    public Optional<User> addUser(User user) {
        if (Parser.userExists(user.getUsername()) || user.getUsername().isEmpty()) {
            return Optional.empty();
        }

        Saver.addUser(user);
        users.add(user);

        return Optional.of(user);
    }
}
