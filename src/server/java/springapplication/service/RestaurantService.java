package springapplication.service;

import springapplication.models.Restaurant;
import springapplication.models.Table;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class RestaurantService {
    public List<Restaurant> getRestaurants() {
        Restaurant restaurant = new Restaurant(1, "TEst restaurant",
                LocalTime.of(10,0,0),
                LocalTime.of(18,0,0));

        Table[] test = {new Table(1, "te",restaurant), new Table(2, "asdf",restaurant) };


        return List.of(
                restaurant
        );
    }
}
