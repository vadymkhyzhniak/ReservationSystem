package springapplication.rest;

import commonapplication.models.Reservation;
import commonapplication.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springapplication.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/user/",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserResource {
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * Authenticates a specific User
     * Returns true if the passwordHash and userId of the User Object are equal to the one we have in the File System
     */
    @PostMapping("authenticate")
    public ResponseEntity<Boolean> authenticateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.authenticateUser(user));
    }

    /**
     * Return a specific User if it exists otherwise empty
     */
    @GetMapping("{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        Optional<User> result = userService.getUser(username);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns the reservations of a specific User (by username) if it exists otherwise empty
     */
    @GetMapping("{username}/reservations")
    public ResponseEntity<List<Reservation>> getReservationsOfUser(@PathVariable("username") String username) {
        Optional<List<Reservation>> result = userService.getReservationsOfUser(username);
        if (result.isPresent() && result.get().size() > 0) {
            return ResponseEntity.ok(result.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Add a user
     * Returns an optional of the user that was created
     */
    @PostMapping()
    public ResponseEntity<Optional<User>> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }
}
