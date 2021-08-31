package com.aki.goosinsa.domain.dto.company;

import com.aki.goosinsa.domain.entity.company.Company;
import lombok.Data;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class CompanySearch {
    private Pageable pageable;
    private Integer page;
    private Integer size;
    private Integer maxPage;
    private String companyNo;
    private String companyName;
    private String username;
    private String name;

    public void checkNull(){
        if(this.page == null) this.page = 0;
        if(this.size == null) this.size = 5;
        if(this.maxPage == null) this.maxPage = 10;

        this.pageable = PageRequest.of(page, size);
    }
}
