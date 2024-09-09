package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDaoImpl userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDaoImpl userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    @Transactional
    public void save(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        roleService.updateRoleForUser(user);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(int id, User updateUser) {
        String encryptedPassword = passwordEncoder.encode(updateUser.getPassword());
        updateUser.setPassword(encryptedPassword);
        roleService.updateRoleForUser(updateUser);
        userRepository.updateUser(updateUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Login not found"));
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveDefaultUser(User admin) {
        roleService.addDefaultRoles();
        roleService.updateDefaultRolesToDefaultUser(admin);
        String encryptedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encryptedPassword);
        userRepository.save(admin);
    }
}
