package ru.kata.spring.boot_security.demo.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

@Component
public class MapperUserRole {

    private final ModelMapper modelMapper;

    public MapperUserRole(ModelMapper modelMapper) {
       this.modelMapper = modelMapper;
    }


    public  UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public  User convertToUser(UserDTO user) {
        return modelMapper.map(user, User.class);
    }

    public  RoleDTO convertToRoleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

}
