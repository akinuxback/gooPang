package com.aki.goosinsa.domain.entity.company;

import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
//@SequenceGenerator(name = "COMPANY_SEQ_GEN", sequenceName = "COMPANY_SEQ",initialValue = 1, allocationSize = 1)
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
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private CompanyStatus status;
    @OneToMany(mappedBy = "company")
    private List<Item> items;


    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

}
