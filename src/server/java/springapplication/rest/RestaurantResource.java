package springapplication.rest;

import commonapplication.models.Speciality;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import commonapplication.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import springapplication.service.RestaurantService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/restaurant",consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class RestaurantResource {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantResource(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    /**
     * Returns all the restaurants we have in the DB if no filters are applied
     * Otherwise all the filters that are set will be used
     * Returns a List of restaurants that match the filter (Stars, PriceRange, CurrentlyOpen, Speciality)
     * This function can be used to search for a specific restaurant and get its reservation etc.
     * If no filters are set we return all restaurants
     */
    @GetMapping(consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<List<Restaurant>> getRestaurants(@RequestParam(required = false, name = "stars", defaultValue = "-1") int stars,
                                                           @RequestParam(required = false, name = "priceRange", defaultValue = "-1") int priceRange,
                                                           @RequestParam(required = false, name = "currentlyOpen", defaultValue = "false") boolean currentlyOpen,
                                                           @RequestParam(required = false, name = "speciality", defaultValue = "Unbekannt") Speciality speciality){
        List<Restaurant> result = restaurantService.getRestaurantsByFilter(stars, priceRange, currentlyOpen, speciality);
        if(result.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Returns the restaurants we have in the DB but at must {limit}
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
     * Returns a Restaurant with a given name if it exits otherwise empty
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
