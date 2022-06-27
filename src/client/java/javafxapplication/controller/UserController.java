package javafxapplication.controller;

import commonapplication.models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public void addUser(User user, Consumer<List<User>> notesConsumer) {
        webClient.post()
                .uri("user")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class)
                .onErrorStop()
                .subscribe(newUser-> {
                    users.add(newUser);
                    notesConsumer.accept(users);
                });
    }
    public boolean authenticateUser(User user, Consumer<List<User>> notesConsumer) {
   return webClient.post().
           uri("user/authenticate").bodyValue(user)
           .retrieve().bodyToMono(User.class).subscribe(x->{
               users.add(user);
               notesConsumer.accept(users);
           }).isDisposed();

    }

    public static void main(String[] args)
    {

        User u=new User(12L,"maha","123".hashCode());
        Consumer<List<User>> con= x->x.add(u);
        UserController c= new UserController();
        c.addUser(u,con);
        System.out.println(c.users.size());


    }
}
