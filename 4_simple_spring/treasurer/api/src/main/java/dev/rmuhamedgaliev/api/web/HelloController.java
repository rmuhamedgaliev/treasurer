package dev.rmuhamedgaliev.api.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class HelloController {

    private final String greeting = "Hello ";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Message sayHello(@RequestParam("name") Optional<String> name) {
        return new Message(greeting + name.orElse("user"));
    }

    public record Message(String message) {
    }
}
