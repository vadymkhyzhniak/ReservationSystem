package springapplication.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapplication.models.Reservation;
import springapplication.models.Restaurant;
import springapplication.models.User;
import springapplication.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/user")
public class UserController {
    private final UserService userService;


    //TODO: Make all returns responseObjects
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Authenticates a specific User
     * Returns true if the passwordHash and userId of the User Object are equal to the one we have in the File System
     */
    @PostMapping("authenticate")
    public boolean authenticateUser(@RequestBody User user) {
        return userService.authenticateUser(user);
    }

    /**
     * Return an Optional of a specific User
     */
    @GetMapping("{id}")
    public Optional<User> getUser(@PathVariable("id") long id) {
        return userService.getUser(id);
    }

    /**
     * Returns an Option that only contains the reservations of a specific User
     */
    @GetMapping("{id}/reservations")
    public Optional<List<Reservation>> getReservationsOfUser(@PathVariable("id") long id) {
        return userService.getReservationsOfUser(id);
    }

    /**
     * Add a user
     * Returns true if it was successful, false otherwise
     */
    @PostMapping
    public boolean addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
