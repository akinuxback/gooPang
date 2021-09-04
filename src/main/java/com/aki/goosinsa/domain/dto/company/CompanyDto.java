package com.aki.goosinsa.domain.dto.company;

import com.aki.goosinsa.domain.domain.Address;
import com.aki.goosinsa.domain.domain.AddressDto;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.company.CompanyStatus;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.domain.entity.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private String companyNo;
    private String companyName;
    private String abbr;
    private CompanyStatus status;
    private AddressDto addressDto;
    private Boolean messageOneYn;
    private String messageOne;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private UploadFileDto uploadFileDto;
    private UserDto userDto;
    private List<Item> items;

}
