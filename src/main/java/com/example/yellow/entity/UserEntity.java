package com.example.yellow.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "\"users\"")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id", orphanRemoval = true)
    private List<JoggingEntity> joggings;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}