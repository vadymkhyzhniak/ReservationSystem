package springapplication.service;

import org.springframework.stereotype.Service;
import springapplication.models.User;

@Service
public class UserService {
    public boolean authenticateUser(User user) {
        // TODO: get the Password and real username from the filesystem
        if(user.getName().equals("test") && user.getPasswordHash() == "pass".hashCode()){
            return true;
        }
        return false;
    }

    // TODO: RESERVATION OF AUTHENTICATED
}
