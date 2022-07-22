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

/**
 * Class is used for getting the Restaurants from the server
 *
 * @author Maha Marhag
 */
public class RestaurantController {
    private final WebClient webClient;

    /**
     * Create a RestaurantController
     */
    public RestaurantController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Gets a restaurant by name
     *
     * @param restaurantName     Name of the restaurant to get
     * @param restaurantConsumer
     */
    public void getRestaurant(String restaurantName, Consumer<Restaurant> restaurantConsumer) {
        webClient.get()
                .uri("restaurant/" + restaurantName)
                .retrieve().bodyToMono(Restaurant.class)
                .onErrorStop()
                .subscribe(restaurantConsumer::accept);
    }

    /**
     * Sends a request to the server to get all the restaurants according to the filter being set
     * through the restaurant object's attributes, if no filter is chosen then returns all restaurants
     *
     * @param restaurantConsumer
     */
    public void getAllRestaurants(Consumer<List<Restaurant>> restaurantConsumer) {
        getAllRestaurantsFilter(-1, -1, false, Speciality.Unbekannt, restaurantConsumer);
    }

    /**
     * Sends a request to the server to get the filters
     *
     * @param stars Rating of the restaurant
     * @param priceRange Price level of the restaurant
     * @param openNow Restaurant current status
     * @param speciality Main cuisine of the restaurant
     * @param restaurantConsumer
     */
    public void getAllRestaurantsFilter(int stars, int priceRange, boolean openNow, Speciality speciality, Consumer<List<Restaurant>> restaurantConsumer) {
        webClient.get().uri(uriBuilder -> uriBuilder.path("restaurant")
                        .queryParam("stars", stars)
                        .queryParam("priceRange", priceRange)
                        .queryParam("currentlyOpen", openNow)
                        .queryParam("speciality", speciality)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Restaurant>>() {
                })
                .onErrorStop()
                .subscribe(restaurantConsumer::accept);
    }

}
