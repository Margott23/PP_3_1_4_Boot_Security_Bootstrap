package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleDao {
    Role findByRole(String roleName);

    void save(Role role);

    List<Role> findAll();
}
