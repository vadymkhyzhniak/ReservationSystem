package javafxapplication.controller;

import commonapplication.models.Restaurant;
import commonapplication.models.Speciality;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class RestaurantController {
    private final WebClient webClient;
    private final List<Restaurant> restaurants;

    public RestaurantController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.restaurants = new ArrayList<>();
    }

    public void addRestaurant(Restaurant restaurant, Consumer<List<Restaurant>> restaurantConsumer) {
        webClient.post()
                .uri("restaurant")
                .bodyValue(restaurant)
                .retrieve()
                .bodyToMono(Restaurant.class)
                .onErrorStop()
                .subscribe(newRestaurant -> {
                    restaurants.add(newRestaurant);
                    restaurantConsumer.accept(restaurants);
                });
    }

    /**
    sends a request to the server to get all the restaurants according to the filter being set
     through the restaurant object's attributes, if no filter is chosen then returns all restaurants
     */

    public void getAllRestaurants(Restaurant r,Consumer<List<Restaurant>> restaurantConsumer) {


        webClient.get().uri(uriBuilder -> uriBuilder.path("restaurant")
                        .queryParam("stars",r.getStars())
                        .queryParam("priceRange",r.getPriceRange())
                        .queryParam("currentlyOpen",r.isOpenNow())
                        .queryParam("speciality", r.getSpeciality())
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Restaurant>>() {
                })
                .onErrorStop()
                .subscribe(newRestaurant -> {
                    restaurants.clear();
                    restaurants.addAll(newRestaurant);
                    restaurantConsumer.accept(restaurants);
                });
    }
}
