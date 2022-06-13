package springapplication.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import springapplication.models.Restaurant;
import springapplication.models.Table;
import springapplication.models.User;

import java.time.LocalTime;
import java.util.List;

@Service
public class UserService {
    public boolean authenticateUser(User user) {
        // TODO: get the Password and real username from the filesystem
        if(user.getName().equals("test") && user.getPassword().equals("pass")){
            return true;
        }
        return false;
    }

    // TODO: RESERVATION OF AUTHENTICATED
}
