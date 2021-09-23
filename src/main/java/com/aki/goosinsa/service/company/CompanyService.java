package com.aki.goosinsa.service.company;

import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.entity.company.Company;

public interface CompanyService {

    public Company updateCompany(CompanyDto companyDto);
}
