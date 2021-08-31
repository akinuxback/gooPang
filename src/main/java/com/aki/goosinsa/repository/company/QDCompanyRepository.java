package com.aki.goosinsa.repository.company;

import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.entity.company.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QDCompanyRepository {

    Page<Company> findAllPaging(Pageable pageable, CompanySearch companySearch);

}
