package com.aki.goosinsa.controller.manager;

import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {



    @GetMapping("/managerHome")
    public String getPage(){
        return "/manager/managerHome";
    }


}
