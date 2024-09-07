package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDAOImpl;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserDAOImpl userRepository;

    public MyUserDetailsService(UserDAOImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByLogin(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
