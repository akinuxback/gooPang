package com.aki.goosinsa.controller.company;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCompanyByUserDto {

    Long id;
    String username;
    String name;
    String phoneNumber;

}
