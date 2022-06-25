package api.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import springapplication.models.Restaurant;
import springapplication.models.Speciality;
import springapplication.models.Table;
import springapplication.service.RestaurantService;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantResourceTest {

    @Test
    @Timeout(5)
    void generateRestaurantsTest() {
        RestaurantService restaurantService = new RestaurantService();
        int numberOfRestaurantsToCreate = 5000;

        for(int i=0; i<numberOfRestaurantsToCreate; i++){
            Restaurant restaurant = new Restaurant(1,"test", LocalTime.now(), LocalTime.now(), i%5, (1+i) % 5, Speciality.Chinesisch, "a");
            restaurantService.getRestaurants().add(restaurant);
            Table[] tables = {new Table(i+1, restaurantService.getRestaurants().get(i)), new Table(i+2, restaurantService.getRestaurants().get(i)), new Table(i+2, restaurantService.getRestaurants().get(0)), new Table(i+2, restaurantService.getRestaurants().get(i)) };
            restaurant.setTables(tables);
            if(i % 5000 == 0){
                System.out.println(i);
            }
        }
        assertEquals(numberOfRestaurantsToCreate, restaurantService.getRestaurants().size());
    }
}
