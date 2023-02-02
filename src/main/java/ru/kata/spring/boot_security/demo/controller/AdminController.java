package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.dto.MapperUserRole;
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
    private final MapperUserRole mapper;

    @Autowired
    public AdminController(UserService userService, MapperUserRole mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/users")
    public ModelAndView getPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("allUsers"); // maybe change "AllUsers.html"
        return modelAndView;
    }

    @GetMapping()
    public ResponseEntity<Map<String ,Object>> printAllUsers() {
        Map<String, Object> getMap = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO admin = mapper.convertToUserDTO((User) auth.getPrincipal());
        List<Role> roles = userService.getAllRoles();

        List<UserDTO> users = userService.getListOfUsers().stream()
                .map(mapper::convertToUserDTO).collect(Collectors.toList());

        getMap.put("admin",admin);
        getMap.put("users",users);
        getMap.put("roles", roles);
        return new ResponseEntity<>(getMap, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> user(@PathVariable("id") long id) {
        return new ResponseEntity<>(mapper.convertToUserDTO(userService.getUserById(id)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Map<String ,Object>> creat(@RequestBody @Valid User userDTO) {
        userService.saveUser(userDTO);

        return printAllUsers();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String ,Object>> update(@RequestBody UserDTO user) {
        userService.updateUser(mapper.convertToUser(user));
        return printAllUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String ,Object>> delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return printAllUsers();
    }

}