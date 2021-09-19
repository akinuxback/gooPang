package com.aki.goosinsa.domain.entity.company;

import com.aki.goosinsa.domain.domain.Address;
import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.domain.entity.item.UploadFile;
import com.aki.goosinsa.domain.entity.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
//@SequenceGenerator(name = "COMPANY_SEQ_GEN", sequenceName = "COMPANY_SEQ",initialValue = 1, allocationSize = 1)
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"companyName", "abbr"})}
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Company {

    // 식별자를 직접 할당하여 관리 하는 방법 (pk를 client 의 입력 값으로 사용할때)
    @Id
    private String companyNo;
    private String companyName;
    private String abbr;
    @Enumerated(value = EnumType.STRING)
    private FoodGroups foodGroups;
    private String foodGroupsOfTitle; //세부 종류
    @Enumerated(value = EnumType.STRING)
    private CompanyStatus status;
    @Embedded
    private Address address;
    private Boolean messageOneYn;
    @Lob
    private String messageOne;
    @CreationTimestamp
    private LocalDateTime createDate;
    @CreationTimestamp
    private LocalDateTime modifyDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UploadFile uploadFile;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany(mappedBy = "company")
    private List<Item> items;

    public Company (CompanyDto companyDto){

        Address address = new Address(companyDto.getAddressDto().getCity(),
                companyDto.getAddressDto().getStreet(),
                companyDto.getAddressDto().getZipcode());

        this.companyNo = companyDto.getCompanyNo();
        this.companyName = companyDto.getCompanyName();
        this.abbr = companyDto.getAbbr();
        this.foodGroups = companyDto.getFoodGroups();
        this.foodGroupsOfTitle = companyDto.getFoodGroupsOfTitle();
        this.status = companyDto.getStatus();
        this.messageOneYn = companyDto.getMessageOneYn();
        this.messageOne = companyDto.getMessageOne();
        this.address = address;
        this.uploadFile = UploadFile.createUploadFile(companyDto.getUploadFileDto());
        this.user = User.toEntity(companyDto.getUserDto());

    }

    public void updateCompany(CompanyDto companyDto){
        this.companyNo = companyDto.getCompanyNo();
        this.companyName = companyDto.getCompanyName();
        this.abbr = companyDto.getAbbr();
        this.foodGroups = companyDto.getFoodGroups();
        this.foodGroupsOfTitle = companyDto.getFoodGroupsOfTitle();
        this.status = companyDto.getStatus();
        this.messageOneYn = companyDto.getMessageOneYn();
        this.messageOne = companyDto.getMessageOne();
        this.address = new Address(companyDto.getAddressDto());
        this.uploadFile = UploadFile.createUploadFile(companyDto.getUploadFileDto());
        this.modifyDate = LocalDateTime.now();
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

}
