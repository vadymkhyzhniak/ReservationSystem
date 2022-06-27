package springapplication.service;

import commonapplication.models.Restaurant;
import org.springframework.stereotype.Service;
import commonapplication.persistancemanagement.Parser;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    private final List<Restaurant> restaurants;

    public RestaurantService() {
        this.restaurants = Parser.getAllRestaurants();
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

    /**
     * Returns a List of restaurants that match the filter (Stars, PriceRange, CurrentlyOpen)
     * This function can be used to search for a specific restaurant and get its reservation etc.
     * If no filters are set we return all restaurants
     */
    public List<Restaurant> getRestaurantsByFilter(int stars, int priceRange, boolean currentlyOpen){
        Predicate<Restaurant> filter = null;

        if(stars >= 1 && stars <= 5){
            filter = (filter == null ? r -> r.getStars() == stars : filter.and(r -> r.getStars() == stars));
        }

        if(priceRange >= 1 && priceRange <= 5){
            filter = (filter == null ? r -> r.getPriceRange() == priceRange : filter.and(r -> r.getPriceRange() == priceRange));
        }

        if(currentlyOpen){
            filter = (filter == null ? r -> r.getOpenedFrom().isBefore(LocalTime.now()) && r.getOpenedTo().isAfter(LocalTime.now()) :
                    filter.and(r -> r.getOpenedFrom().isBefore(LocalTime.now()) && r.getOpenedTo().isAfter(LocalTime.now())));
        }

        if(filter == null){
            return restaurants;
        }

        return restaurants.stream().filter(filter).collect(Collectors.toList());
    }
}
