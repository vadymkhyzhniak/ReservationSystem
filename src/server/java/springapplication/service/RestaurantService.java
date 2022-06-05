package springapplication.service;

import springapplication.models.Restaurant;
import springapplication.models.Table;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class RestaurantService {
    public List<Restaurant> getRestaurants() {
        Table[] test = {new Table(1, "te"), new Table(2, "asdf") };
        return List.of(
                new Restaurant(1, "TEst restaurant", test,
                        LocalTime.of(10,0,0),
                        LocalTime.of(18,0,0))

        );
    }
}
