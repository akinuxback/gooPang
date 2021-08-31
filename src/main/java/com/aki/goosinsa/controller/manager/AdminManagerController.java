package com.aki.goosinsa.controller.manager;

import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.repository.company.CompanyRepository;
import com.aki.goosinsa.repository.company.QDCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/manager")
public class AdminManagerController {

    private final CompanyRepository companyRepository;
    private final QDCompanyRepository qdCompanyRepository;

    @GetMapping("/managerEdit")
    public String edit(Model model, Pageable pageable, CompanySearch companySearch){
        companySearch.checkNull();
        Page<Company> pages = qdCompanyRepository.findAllPaging(companySearch.getPageable(), companySearch);
        model.addAttribute("pages", pages);
        model.addAttribute("maxPage", companySearch.getMaxPage());
        model.addAttribute("contentSize", pages.getContent().size());
        return "admin/manager/managerEdit";
    }

}
