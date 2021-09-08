package com.aki.goosinsa.repository.company;

import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.entity.company.Company;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Log4j2
class QDCompanyRepositoryImplTest {

    @Autowired QDCompanyRepository qdCompanyRepository;


    @Test
    void findAllPaging() {
        CompanySearch companySearch = new CompanySearch();
        companySearch.checkNull();
        Page<Company> pages = qdCompanyRepository.findAllPaging(companySearch.getPageable(), companySearch);
        List<CompanyDto> content = pages.getContent().stream().map(c -> new CompanyDto(c)).collect(Collectors.toList());

        content.forEach(c -> {
            log.info(c.getCompanyName());
        });

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