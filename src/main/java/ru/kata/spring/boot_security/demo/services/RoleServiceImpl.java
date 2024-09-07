package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void updateRoleForUser(User user) {
        List<Role> roles = new ArrayList<>();
        for (Role role : user.getRoleList()) {
            Role rRole = roleRepository.findByName(role.getName());
//            if (rRole == null) {
//                rRole = roleRepository.save(role);
//            }
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
