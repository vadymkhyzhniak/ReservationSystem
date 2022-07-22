package javafxapplication.controller;

import commonapplication.models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Class is used to handle operations on users with the server side
 *
 * @author Maha Marhag
 */
public class UserController {
    private final WebClient webClient;
    private final List<User> users;

    /**
     * Creates a UserController
     */
    public UserController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.users = new ArrayList<>();
    }

    /**
     * Sends a request to add a user to the server's database when invoking the register method in LoginController
     *
     * @param user Actual user
     * @param userConsumer
     */
    public void addUser(User user, Consumer<List<User>> userConsumer) {
        webClient.post()
                .uri("user/")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class)
                .onErrorStop()
                .subscribe(newUser -> {
                    users.add(newUser);
                    userConsumer.accept(users);
                });
    }

    /**
     * Sends a request to the server to authenticate a user
     * @param u Actual user
     * @param e ActionEvent
     * @param userConsumer
     */
    public void authenticateUser(User u, ActionEvent e, BiConsumer<Boolean, ActionEvent> userConsumer) {
        webClient.post()
                .uri("user/authenticate")
                .bodyValue(u)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorStop()
                .subscribe(returnValue -> {
                    userConsumer.accept(returnValue, e);
                });
    }

    public static void main(String[] args) {

        User u = new User("maha", "123".hashCode());
        Consumer<List<User>> con = x -> x.add(u);
        UserController c = new UserController();
        c.addUser(u, con);


    }
}
