package com.aki.goosinsa.domain.entity.user;

import com.aki.goosinsa.domain.domain.Address;
import com.aki.goosinsa.domain.domain.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @NotEmpty(message = "해당 입력")
    @Length(min=3, max=10)
    private String username;
    @NotEmpty(message = "해당 입력")
    @Length(min=2, max=10)
    private String name;
    @NotEmpty(message = "해당 입력")
    private String password;
    @NotEmpty(message = "해당 입력")
    @Email
    private String email;
    private UserRole role;
    @Length(min=10, max=11)
    @NotEmpty(message = "해당 입력")
    private String phoneNumber;

    @NotNull(message = "주소를 입력 해주세요")
    private AddressDto addressDto;

    public static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .phoneNumber(user.getPhoneNumber())
                .addressDto(AddressDto.toDto(user.getAddress()))
                .build();
    }
//
//    public UserDto(User user){
//        UserDto.builder()
//                .id(user.getId())
//                .username(user.getUsername())
//                .name(user.getName())
//                .password(user.getPassword())
//                .email(user.getEmail())
//                .role(user.getRole())
//                .phoneNumber(user.getPhoneNumber())
//                .addressDto(AddressDto.toDto(user.getAddress()))
//                .build();
//    }

    public UserDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.phoneNumber = user.getPhoneNumber();
        this.addressDto = AddressDto.toDto(user.getAddress());
    }

}
