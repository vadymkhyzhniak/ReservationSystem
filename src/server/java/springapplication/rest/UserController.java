package springapplication.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springapplication.models.Restaurant;
import springapplication.models.User;
import springapplication.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public boolean authenticateUser(@RequestBody User user) {
        return userService.authenticateUser(user);
    }
}
