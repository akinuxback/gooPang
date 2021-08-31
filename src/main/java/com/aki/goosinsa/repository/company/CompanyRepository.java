package com.aki.goosinsa.repository.company;

import com.aki.goosinsa.domain.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
}
