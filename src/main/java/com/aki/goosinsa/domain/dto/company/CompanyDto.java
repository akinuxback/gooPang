package com.aki.goosinsa.domain.dto.company;

import com.aki.goosinsa.domain.domain.Address;
import com.aki.goosinsa.domain.domain.AddressDto;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.company.CompanyStatus;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.domain.entity.user.UserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {

    @NotBlank
    private String companyNo;
    @NotBlank
    private String companyName;
    @NotBlank
    private String abbr;
    @NotNull(message = "선택 하세요")
    private FoodGroups foodGroups;
    @NotBlank(message = "선택1을 다시 선택 하세요")
    private String foodGroupsOfTitle; //세부 종류
    @NotNull(message = "선택 하세요")
    private CompanyStatus status;
    @NotNull
    private AddressDto addressDto;
    @AssertTrue(message = "약관에 동의 하셔야 합니다.")
    private Boolean messageOneYn;
    @NotBlank
    private String messageOne;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private UploadFileDto uploadFileDto;
    @NotNull
    private UserDto userDto;
    private List<ItemDto> itemDtoList;

    @QueryProjection
    public CompanyDto(Company company) {
        this.companyNo = company.getCompanyNo();
        this.companyName = company.getCompanyName();
        this.abbr = company.getAbbr();
        this.foodGroups = company.getFoodGroups();
        this.foodGroupsOfTitle = company.getFoodGroupsOfTitle();
        this.status = company.getStatus();
        this.addressDto = AddressDto.toDto(company.getAddress());
        this.messageOneYn = company.getMessageOneYn();
        this.messageOne = company.getMessageOne();
        this.createDate = company.getCreateDate();
        this.modifyDate = company.getModifyDate();
        this.uploadFileDto = UploadFileDto.entityToDto(company.getUploadFile());
        this.userDto = UserDto.toDto(company.getUser());
        /**
         * 이게 범인이었구나 1+N 문제
         * */
//        this.itemDtoList = company.getItems().stream().map(item -> new ItemDto(item)).collect(Collectors.toList());
    }
}
