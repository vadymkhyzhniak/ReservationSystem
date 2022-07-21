package javafxapplication.controller;

import commonapplication.models.Reservation;
import commonapplication.models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ReservationController {
    private final WebClient webClient;
    private final List<Reservation> reservations;

    public ReservationController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.reservations = new ArrayList<>();
    }

    /**
     sends a request to add a reservation to the server's database when invoking the makeReservation method in ReservationController
     */
    public void addReservation(Reservation reservation, Consumer<List<Reservation>> reservationConsumer) {
        webClient.post()
                .uri("reservation/")
                .bodyValue(reservation)
                .retrieve()
                .bodyToMono(Reservation.class)
                .onErrorStop()
                .subscribe(newReservation -> {
                    reservations.add(newReservation);
                    reservationConsumer.accept(reservations);
                });
    }
}
