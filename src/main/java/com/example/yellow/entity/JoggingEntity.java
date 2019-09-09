package com.example.yellow.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "joggings")
public class JoggingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Long userId;

    private Long distance;

    @Column(name = "\"time\"")
    private Long time;

    private LocalDateTime dateTime;

}
