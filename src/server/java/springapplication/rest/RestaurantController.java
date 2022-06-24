package springapplication.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springapplication.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import springapplication.service.RestaurantService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="api/v1/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * Return all the restaurants we have in the DB if not filters are applied
     * Otherwise all the filters that are set will be used
     */
    @GetMapping
    public List<Restaurant> getRestaurants(@RequestParam(required = false, name = "stars", defaultValue = "-1") int stars,
                                                   @RequestParam(required = false, name = "priceRange", defaultValue = "-1") int priceRange,
                                                   @RequestParam(required = false, name = "priceRange", defaultValue = "false") boolean currentlyOpen){
        return restaurantService.getRestaurantsByFilter(stars, priceRange, currentlyOpen);
    }

    /**
     * Return limit number of restaurants we have in the DB
     */
    @GetMapping("limit/{limit}")
    public List<Restaurant> getRestaurantsByLimit(@PathVariable("limit") int limit) {
        return restaurantService.getRestaurantsByLimit(limit);
    }

    /**
     * Returns an Optional of a Restaurant with a given name if it exits
     */
    @GetMapping("{restaurantName}")
    public Optional<Restaurant> getRestaurantsByName(@PathVariable("restaurantName") String restaurantName) {
        return restaurantService.getRestaurantByName(restaurantName);
    }
}
