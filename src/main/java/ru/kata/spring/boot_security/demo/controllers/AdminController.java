package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String startPage(Model model, Principal principal) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("userTitleName", userService.findByLogin(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAll());

        return "admins/admin";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("newUser") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String updateUser(@RequestParam(value = "id", required = false) Integer id,
                             @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Integer id) {
        userService.delete(userService.findById(id));
        return "redirect:/admin";
    }

}
