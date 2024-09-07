package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAOImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDAOImpl roleRepository;

    public RoleServiceImpl(RoleDAOImpl roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void updateRoleForUser(User user) {
        List<Role> roles = new ArrayList<>();
        if (!user.getRoles().isEmpty()) {
            for (Role role : user.getRoleList()) {
                Role rRole = roleRepository.findByRole(role.getName());
                roles.add(rRole);
            }
        } else {
            Role rRole = roleRepository.findByRole("ROLE_USER");
            roles.add(rRole);
        }
        user.setRoleList(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
