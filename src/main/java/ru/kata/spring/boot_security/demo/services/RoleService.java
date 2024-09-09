package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface RoleService {

    void updateRoleForUser(User user);

    List<Role> getAll();

    void addDefaultRoles();

    void updateDefaultRolesToDefaultUser(User admin);
}
