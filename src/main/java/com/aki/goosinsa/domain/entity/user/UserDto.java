package com.aki.goosinsa.domain.entity.user;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private UserRole role;
}
