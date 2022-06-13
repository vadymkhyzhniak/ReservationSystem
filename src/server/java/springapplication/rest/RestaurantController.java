package springapplication.rest;

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

    @GetMapping
    public List<Restaurant> getRestaurants() {
        return restaurantService.getRestaurants();
    }

    @GetMapping("limit/{limit}")
    public List<Restaurant> getRestaurantsByLimit(@PathVariable("limit") int limit) {
        return restaurantService.getRestaurantsByLimit(limit);
    }

    @GetMapping("{restaurantName}")
    public Optional<Restaurant> getRestaurantsByName(@PathVariable("restaurantName") String restaurantName) {
        return restaurantService.getRestaurantByName(restaurantName);
    }
}
