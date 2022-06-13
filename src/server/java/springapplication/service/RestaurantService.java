package springapplication.service;

import springapplication.models.Restaurant;
import springapplication.models.Table;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final List<Restaurant> restaurants;

    public RestaurantService() {
        this.restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1, "TEst restaurant",
                LocalTime.of(10,0,0),
                LocalTime.of(18,0,0), 3, 1));
        Table[] test = {new Table(1, restaurants.get(0)), new Table(2, restaurants.get(0)) };
        // TODO: We need parser.getallrestaurants()
        //this.restaurants = Parser.getAllRestaurants();
    }

    /**
     * Return all the restaurants we have in the DB
     */
    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    /**
     * Return limit number of restaurants we have in the DB
     */
    public List<Restaurant> getRestaurantsByLimit(int limit) {
        return restaurants.stream().limit(limit).collect(Collectors.toList());
    }

    /**
     * Returns an Optional maybe one restaurant with given name
     */
    public Optional<Restaurant> getRestaurantByName(String restaurantName) {
        return restaurants.stream().filter(r -> r.getName().equals(restaurantName)).findFirst();
    }

    // TODO: Filter restaurants by Stars, PriceRange, CurrentlyOpen


    // TODO: GET RESERVATION OF A SPECIFIC RESTAURANTS
}
