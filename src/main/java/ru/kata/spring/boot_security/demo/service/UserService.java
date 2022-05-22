package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(long id);

    void addUser(User user);

    void removeUser(long id);

    void editUser(User user);

    void setEncryptedPassword(User user);

}
