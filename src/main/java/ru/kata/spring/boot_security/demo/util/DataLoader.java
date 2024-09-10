package ru.kata.spring.boot_security.demo.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;

    public DataLoader(UserService userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @Override
    @Transactional
    public void run(String... args) {
        User defaultAdmin = new User("Admin", "", 0, "admin@mail.ru", "admin");
        userService.saveDefaultUser(defaultAdmin);
    }
}
