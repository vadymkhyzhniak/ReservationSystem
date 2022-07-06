package springapplication.rest;

import commonapplication.models.Speciality;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import commonapplication.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import springapplication.service.RestaurantService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/restaurant")
public class RestaurantResource {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantResource(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * Return all the restaurants we have in the DB if no filters are applied
     * Otherwise all the filters that are set will be used
     */
    @GetMapping
    public ResponseEntity<List<Restaurant>> getRestaurants(@RequestParam(required = false, name = "stars", defaultValue = "-1") int stars,
                                                           @RequestParam(required = false, name = "priceRange", defaultValue = "-1") int priceRange,
                                                           @RequestParam(required = false, name = "currentlyOpen", defaultValue = "false") boolean currentlyOpen,
                                                           @RequestParam(required = false, name = "currentlyOpen", defaultValue = "Unbekannt") Speciality speciality){
        List<Restaurant> result = restaurantService.getRestaurantsByFilter(stars, priceRange, currentlyOpen, speciality);
        if(result.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Return limit number of restaurants we have in the DB
     */
    @GetMapping("limit/{limit}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByLimit(@PathVariable("limit") int limit) {
        List<Restaurant> result = restaurantService.getRestaurantsByLimit(limit);
        if(result.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Returns an Optional of a Restaurant with a given name if it exits
     */
    @GetMapping("{restaurantName}")
    public ResponseEntity<Restaurant> getRestaurantsByName(@PathVariable("restaurantName") String restaurantName) {
        Optional<Restaurant> result = restaurantService.getRestaurantByName(restaurantName);
        if(result.isPresent()){
            return ResponseEntity.ok(result.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
