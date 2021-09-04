package com.aki.goosinsa.domain.entity.user;

import com.aki.goosinsa.domain.domain.Address;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.order.Order;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


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

    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GEN")
    private Long id;
    @Column(unique = true)
    private String username;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String provider;
    private String providerId;
    @CreationTimestamp
    private Timestamp createDate;

    @OneToMany(mappedBy = "user")
    private List<Company> companyList = new ArrayList<>();

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public static User toEntity(UserDto userDto, BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder()
                .role(UserRole.ROLE_USER)
                .username(userDto.getUsername())
                .name(userDto.getName())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .address(new Address(userDto.getAddressDto()))
                .build();
    }

    public static User toEntity(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .role(UserRole.ROLE_USER)
                .username(userDto.getUsername())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .address(new Address(userDto.getAddressDto()))
                .build();
    }



//    @Builder
//    public User(String username, String password, String email, UserRole role, String provider, String providerId, Timestamp createDate) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = role;
//        this.provider = provider;
//        this.providerId = providerId;
//        this.createDate = createDate;
//    }

}
