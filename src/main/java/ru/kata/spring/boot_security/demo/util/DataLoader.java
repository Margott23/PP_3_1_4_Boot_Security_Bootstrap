package ru.kata.spring.boot_security.demo.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            User user = new User(
                    "Admin",
                    "",
                    0,
                    "admin@mail.ru",
                    passwordEncoder.encode("admin"),
                    List.of(adminRole, userRole));
            User testUser = new User(
                    "Test",
                    "User",
                    25,
                    "user@mail.ru",
                    passwordEncoder.encode("user"),
                    List.of(userRole));
            roleRepository.save(adminRole);
            roleRepository.save(userRole);
            userRepository.save(user);
            userRepository.save(testUser);
        }
    }
}
