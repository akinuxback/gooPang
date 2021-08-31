package com.aki.goosinsa.domain.dto.company;

import com.aki.goosinsa.domain.entity.user.User;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
public class CompanyDto {

    private String companyNo;
    private String companyName;
    private String abbr;
    private User user;

}
