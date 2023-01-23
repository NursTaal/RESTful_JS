package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    User getUserById(long id);

    boolean saveUser(User user);

    void updateUser(User user);

    void deleteUser(long id);

    List<User> getListOfUsers();

    void saveRole(Role role);

    void saveRoleUser(User user, Role role);

    boolean contains(String username);

    List<Role> getAllRoles();

}
