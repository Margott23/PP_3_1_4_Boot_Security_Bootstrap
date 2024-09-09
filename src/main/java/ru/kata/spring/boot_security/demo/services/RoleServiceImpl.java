package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDaoImpl roleRepository;

    public RoleServiceImpl(RoleDaoImpl roleRepository) {
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

    @Override
    @Transactional
    public void addDefaultRoles() {
        if (roleRepository.findAll().isEmpty()) {
            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
        }
    }

    @Override
    @Transactional
    public void updateDefaultRolesToDefaultUser(User admin) {
        List<Role> roles = new ArrayList<>();
        if (admin.getRoleList().stream().anyMatch(rRole -> rRole.getName().equals("ROLE_ADMIN"))) {
            admin.setRoleList(roles);
        } else {
            roles.add(roleRepository.findByRole("ROLE_ADMIN"));
            admin.setRoleList(roles);
        }
    }
}
