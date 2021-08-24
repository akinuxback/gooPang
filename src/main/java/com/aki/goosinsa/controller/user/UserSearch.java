package com.aki.goosinsa.controller.user;

import com.aki.goosinsa.domain.entity.user.UserRole;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserSearch {

    private String username;
    private String name;
    private String phoneNumber;
    private String userRole;

}
