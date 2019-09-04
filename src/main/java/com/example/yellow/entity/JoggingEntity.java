package com.example.yellow.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
//@Entity
//@Table(name = "\"joggings\"")
public class JoggingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserEntity user;

    private int distance;

    @Column(name = "\"time\"")
    private int time;

    private LocalDateTime dateTime;

}
