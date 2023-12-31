package springapplication.service;

import commonapplication.models.Restaurant;
import commonapplication.models.Speciality;
import org.springframework.stereotype.Service;
import commonapplication.persistancemanagement.Parser;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    private final List<Restaurant> restaurants;

    public RestaurantService() {
        this.restaurants=Parser.getAllRestaurants();
    }

    /**
     * Returns all the restaurants we have in the DB
     */
    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    /**
     * Returns limit number of restaurants we have in the DB
     */
    public List<Restaurant> getRestaurantsByLimit(int limit) {
        return restaurants.stream().limit(limit).collect(Collectors.toList());
    }

    /**
     * Returns a Restaurant with a given name if it exits otherwise empty
     */
    public Optional<Restaurant> getRestaurantByName(String restaurantName) {
        return restaurants.stream().filter(r -> r.getName().equals(restaurantName)).findFirst();
    }

    /**
     * Returns a List of restaurants that match the filter (Stars, PriceRange, CurrentlyOpen, Speciality)
     * This function can be used to search for a specific restaurant and get its reservation etc.
     * If no filters are set we return all restaurants
     */
    public List<Restaurant> getRestaurantsByFilter(int stars, int priceRange, boolean currentlyOpen, Speciality speciality){
        Predicate<Restaurant> filter = null;

        if(stars >= 1 && stars <= 5){
            filter = (filter == null ? r -> r.getStars() == stars : filter.and(r -> r.getStars() == stars));
        }

        if(priceRange >= 1 && priceRange <= 3){
            filter = (filter == null ? r -> r.getPriceRange() == priceRange : filter.and(r -> r.getPriceRange() == priceRange));
        }

        if(currentlyOpen){
            filter = (filter == null ? r -> r.getOpenedFrom().isBefore(LocalTime.now()) && r.getOpenedTo().isAfter(LocalTime.now()) :
                    filter.and(r -> r.getOpenedFrom().isBefore(LocalTime.now()) && r.getOpenedTo().isAfter(LocalTime.now())));
        }

        if(speciality != Speciality.Unbekannt){
            filter = (filter == null ? r -> r.getSpeciality() == speciality : filter.and(r -> r.getSpeciality() == speciality));
        }

        if(filter == null){
            return restaurants;
        }

        return restaurants.stream().filter(filter).collect(Collectors.toList());
    }
}
