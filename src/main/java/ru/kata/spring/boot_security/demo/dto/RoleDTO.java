package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class RoleDTO {

    private Long id;

    private String name;

}
