package com.aki.goosinsa.repository.company;

import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.entity.company.Company;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Log4j2
class QDCompanyRepositoryImplTest {

    @Autowired QDCompanyRepository qdCompanyRepository;


    @Test
    void findAllPaging() {
        PageRequest pageable = PageRequest.of(0, 5);
        CompanySearch companySearch = new CompanySearch();
        companySearch.setCompanyName("롯데마트4");
        companySearch.setName("박보영");
        Page<Company> pages = qdCompanyRepository.findAllPaging(pageable, companySearch);

        log.info("===================================================");
        log.info(pages.getContent().size());
        log.info(pages.getTotalElements());
    }
    
    @Test
    public void companyFindFoodItemList() throws Exception{
        CompanySearch companySearch = new CompanySearch();
        companySearch.checkNull();
        qdCompanyRepository.companyFindFoodItemList("111-1111-11", companySearch);

        
    }
}