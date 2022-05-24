package dev.rmuhamedgaliev.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"dev.rmuhamedgaliev.api"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
