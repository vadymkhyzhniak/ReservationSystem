package springapplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // DONT DELETE THIS CONTROLLER OR ARTEMIS BUILD FAILS. MFG Niklas
    @GetMapping("sayHello")
    public String sayHello() {
        return "Hello World!";
    }
}
