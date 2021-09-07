package com.aki.goosinsa.service.company;

import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    @Override
    public Company updateCompany(CompanyDto companyDto) {
        Company company = companyRepository.findById(companyDto.getCompanyNo()).orElseThrow();
        company.updateCompany(companyDto);

        return company;
    }
}
