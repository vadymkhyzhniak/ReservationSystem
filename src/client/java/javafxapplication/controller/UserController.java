package javafxapplication.controller;

import commonapplication.models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UserController {
    private final WebClient webClient;
    private final List<User> users;

    public UserController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.users = new ArrayList<>();
    }

    /**
    sends a request to add a user to the server's database when invoking the register method in LoginController
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
