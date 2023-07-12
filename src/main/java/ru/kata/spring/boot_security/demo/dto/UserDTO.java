package ru.kata.spring.boot_security.demo.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Set;
@RequiredArgsConstructor
@Data
public class UserDTO {

    private Long id;

    @Email
    @Size(min=4, message = "Email have to minimum 4 chars! ")
    private String email;

    @Size(min = 2, message = "name have to minimum 2 chars")
    private String password;

    @Size(min = 2, message = "name have to minimum 2 chars")
    private String firstName;

    @Size(min = 2, message = "name have to minimum 2 chars")
    private String lastName;

    @Min(value = 0,message = "Age must be more 0 less 127")
    private Byte age;

    private String confirm;

    private Set<RoleDTO> roles;

}
