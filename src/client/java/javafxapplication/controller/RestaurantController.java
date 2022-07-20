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

    public RestaurantController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

//    public void addRestaurant(Restaurant restaurant, Consumer<List<Restaurant>> restaurantConsumer) {
//        webClient.post()
//                .uri("restaurant/")
//                .bodyValue(restaurant)
//                .retrieve()
//                .bodyToMono(Restaurant.class)
//                .onErrorStop()
//                .subscribe(newRestaurant -> {
//                    restaurants.add(newRestaurant);
//                    restaurantConsumer.accept(restaurants);
//                });
//    }

    /**
    sends a request to the server to get all the restaurants according to the filter being set
     through the restaurant object's attributes, if no filter is chosen then returns all restaurants
     */

    public void getAllRestaurants(Consumer<List<Restaurant>> restaurantConsumer) {
        getAllRestaurantsFilter(-1,-1,false, Speciality.Unbekannt, restaurantConsumer);
    }

    public void getAllRestaurantsFilter(int stars, int priceRange, boolean openNow, Speciality speciality,Consumer<List<Restaurant>> restaurantConsumer) {
        webClient.get().uri(uriBuilder -> uriBuilder.path("restaurant")
                        .queryParam("stars",stars)
                        .queryParam("priceRange",priceRange)
                        .queryParam("currentlyOpen",openNow)
                        .queryParam("speciality", speciality)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Restaurant>>() {
                })
                .onErrorStop()
                .subscribe(newRestaurants -> {
                    restaurantConsumer.accept(newRestaurants);
                });
    }

}
