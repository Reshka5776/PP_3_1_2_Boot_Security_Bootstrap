package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Controller
@RequestMapping
public class AdminUserController {

    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;
    private final RoleService roleService;

    @Autowired
    public AdminUserController(UserService userService, UserDetailsServiceImpl userDetailService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userDetailsService = userDetailService;
    }

    @GetMapping("/admin")
    public String printUsers(ModelMap model, @AuthenticationPrincipal User user) {
        model.addAttribute("initUser", userDetailsService.loadUserByUsername(user.getEmail()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllUserRoles());
        //model.addAttribute("userById", userService.getUserById(id));
        return "admin";
    }


    @PostMapping("/admin")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles", defaultValue = "ROLE_USER") Set<String> roles) {
        Set<Role> setRoles = roleService.getAllRoles(roles);
        user.setRoles(setRoles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
//
//    @GetMapping("/admin/{id}/edit")
//    public String updateUserForm(@PathVariable("id") long id, Model model) {
//        model.addAttribute("initUser", userService.getUserById(id));
//        model.addAttribute("roles", roleService.getAllUserRoles());
//        return "admin";
//    }

    @PatchMapping("/admin/{id}/edit")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam(value = "roles", defaultValue = "ROLE_USER") Set<String> roles) {

        Set<Role> setRoles = roleService.getAllRoles(roles);
        user.setRoles(setRoles);
        userService.editUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String pageUser(@AuthenticationPrincipal User user, ModelMap model) {
        model.addAttribute("userPage", userService.getUserById(user.getId()));
        model.addAttribute("user", userDetailsService.loadUserByUsername(user.getEmail()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllUserRoles());
//        return "admin";
//        model.addAttribute("initUser", initUser);
//        model.addAttribute("roles", initUser.getRoles());
        return "user";
    }


}


