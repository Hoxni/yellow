package com.example.yellow.entity;

import com.example.yellow.enumeration.PostgreSqlEnumType;
import com.example.yellow.enumeration.Role;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSqlEnumType.class
)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String username;
    @NonNull
    private String password;

    @Column(insertable = false)
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private Role userRole;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", orphanRemoval = true)
    private List<JoggingEntity> joggings;

}