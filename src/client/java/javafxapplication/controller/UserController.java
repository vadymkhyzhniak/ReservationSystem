package javafxapplication.controller;

import commonapplication.models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UserController {
    private final WebClient webClient;
    private final List<User> users;

    public UserController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.users = new ArrayList<>();
    }

    public void addUser(User user, Consumer<List<User>> userConsumer) {
        webClient.post()
                .uri("user")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class)
                .onErrorStop()
                .subscribe(newUser -> {
                    users.add(newUser);
                    userConsumer.accept(users);
                });
    }

    public boolean authenticateUser(User user, Consumer<List<User>> userConsumer) {
        return webClient.post().
                uri("user/authenticate").bodyValue(user)
                .retrieve().bodyToMono(User.class).subscribe(x -> {
                    users.add(user);
                    userConsumer.accept(users);
                }).isDisposed();

    }

    public static void main(String[] args) {

        User u = new User("maha", "123".hashCode());
        Consumer<List<User>> con = x -> x.add(u);
        UserController c = new UserController();
        c.addUser(u, con);
        System.out.println(c.users.size());


    }
}
