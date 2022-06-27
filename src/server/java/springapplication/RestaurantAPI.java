package springapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springapplication.rest.UserResource;
import springapplication.service.UserService;

@SpringBootApplication
@ComponentScan(basePackageClasses = UserResource.class)
@ComponentScan(basePackageClasses = UserService.class)
public class RestaurantAPI {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantAPI.class, args);
	}

}
