package ru.kata.spring.boot_security.demo.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@roleId")
@AllArgsConstructor
@Getter
@Setter
public class Role implements GrantedAuthority  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "name",unique = true)
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public String getName() {
        System.out.println(this.id);
        System.out.println(this.name);
        return name.split("_")[1];
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return name.split("_")[1];
    }

}
