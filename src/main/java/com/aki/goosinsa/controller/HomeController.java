package com.aki.goosinsa.controller;

import com.aki.goosinsa.controller.food.FoodSearch;
import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.repository.company.QDCompanyRepository;
import com.aki.goosinsa.repository.item.QDItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/home/food")
public class HomeController {

    private final QDCompanyRepository qdCompanyRepository;

    private final QDItemRepository qdItemRepository;

    @ModelAttribute("foodGroups")
    public static FoodGroups[] foodGroups(){
        return FoodGroups.values();
    }

    @GetMapping("/companyList")
    public String companyList(Model model, CompanySearch companySearch){
        companySearch.setSize(12);
        companySearch.checkNull();
        Page<Company> pages = qdCompanyRepository.findAllPaging(companySearch.getPageable(), companySearch);
        List<CompanyDto> content = pages.getContent().stream().map(c -> new CompanyDto(c)).collect(Collectors.toList());

        model.addAttribute("pages", pages);
        model.addAttribute("companyList", pages.getContent());
        model.addAttribute("maxPage", companySearch.getMaxPage());
        model.addAttribute("contentSize", pages.getContent().size());

        return "home/food/companyList";
    }

    @GetMapping("/menu")
    public String menu(Model model, FoodSearch foodSearch, @RequestParam(defaultValue = "0") int pageNum){

        PageRequest pageable = PageRequest.of(pageNum, 10);
        Page<FoodItemDto> pages = qdItemRepository.findAllPaging(pageable, foodSearch);
        model.addAttribute("foodList", pages.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("maxPage", 10);
        return "/home/food/menu";
    }
}
