package com.aki.goosinsa.domain.entity.user;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@SequenceGenerator(name = "USER_SEQ_GEN",
        sequenceName = "USER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @ToString
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GEN")
    private Long id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String provider;
    private String providerId;
    @CreationTimestamp
    private Timestamp createDate;


    public static User toEntity(UserDto userDto, BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder()
                .role(UserRole.ROLE_USER)
                .username(userDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build();
    }

    @Builder
    public User(String username, String password, String email, UserRole role, String provider, String providerId, Timestamp createDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }

}
