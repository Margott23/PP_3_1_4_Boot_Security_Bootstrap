package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> findAll();

    Optional<User> findById(int id);

    void save(User user);

    void updateUser(User user);

    void deleteById(int id);

    Optional<User> findByLogin(String login);
}
