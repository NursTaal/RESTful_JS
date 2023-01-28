package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.dto.RoleDTO;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public ModelAndView getPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("allUsers"); // maybe change "AllUsers.html"
        return modelAndView;
    }

    @GetMapping()
    public Map<String,Object> printAllUsers() {
        Map<String, Object> getMap = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO admin = convertToUserDTO((User) auth.getPrincipal());
        List<RoleDTO> roles = userService.getAllRoles().stream()
                .map(this::convertToRoleDTO).collect(Collectors.toList());

        List<UserDTO> users = userService.getListOfUsers().stream()
                .map(this::convertToUserDTO).collect(Collectors.toList());

        getMap.put("admin",admin);
        getMap.put("users",users);
        getMap.put("roles", roles);
        return getMap;

//        return userService.getListOfUsers().stream().
//                map(this::convertToUserDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO user(@PathVariable("id") long id) {
        return convertToUserDTO(userService.getUserById(id));
    }

    @PostMapping()
    public Map<String, Object> creat(@RequestBody @Valid User userDTO) {
        userService.saveUser(userDTO);

        return printAllUsers();
    }

    @PatchMapping("/{id}")
    public Map<String,Object> update(@RequestBody UserDTO user) {
        userService.updateUser(convertToUser(user));
        return printAllUsers();
    }

    @DeleteMapping("/{id}")
    public Map<String,Object> delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return printAllUsers();
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO user) {
        return modelMapper.map(user, User.class);
    }

    private RoleDTO convertToRoleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

}