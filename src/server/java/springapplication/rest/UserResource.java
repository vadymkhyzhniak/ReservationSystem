package springapplication.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springapplication.models.*;
import springapplication.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/user")
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
     * Return an Optional of a specific User
     */
    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        Optional<User> result = userService.getUser(id);
        if (result.isPresent()){
            return ResponseEntity.ok(result.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns an Option that only contains the reservations of a specific User
     */
    @GetMapping("{id}/reservations")
    public ResponseEntity<List<Reservation>> getReservationsOfUser(@PathVariable("id") long id) {
        Optional<List<Reservation>> result = userService.getReservationsOfUser(id);
        if (result.isPresent() && result.get().size() > 0){
            return ResponseEntity.ok(result.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Add a user
     * Returns true if it was successful, false otherwise
     */
    @PostMapping
    public ResponseEntity<Boolean> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }
}
