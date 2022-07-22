package javafxapplication.controller;

import commonapplication.models.Reservation;
import commonapplication.models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ReservationController {
    private final WebClient webClient;

    public ReservationController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     sends a request to add a reservation to the server's database when invoking the makeReservation method in ReservationSceneController
     */
    public void addReservation(Reservation reservation, BiConsumer<Boolean, Reservation> reservationConsumer) {
        webClient.post()
                .uri("reservation/")
                .bodyValue(reservation)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorStop()
                .subscribe(success -> {
                    reservationConsumer.accept(success, reservation);
                });
    }
}
