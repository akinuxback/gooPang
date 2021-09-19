package com.aki.goosinsa.controller;

import com.aki.goosinsa.asecurity.auth.PrincipalDetails;
import com.aki.goosinsa.controller.food.FoodSearch;
import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.repository.company.QDCompanyRepository;
import com.aki.goosinsa.repository.item.QDItemRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/home/food")
public class HomeController {

    private final int pageSize = 12;

    private final UserRepository userRepository;

    private final QDCompanyRepository qdCompanyRepository;

    private final QDItemRepository qdItemRepository;

    @ModelAttribute("foodGroups")
    public static FoodGroups[] foodGroups(){
        return FoodGroups.values();
    }

    @GetMapping("/companyList")
    public String companyList(Model model, CompanySearch companySearch){

        log.info("==================================");
        log.info(companySearch.getFoodGroups());
        companySearch.setSize(pageSize);
        companySearch.checkNull();
        Page<CompanyDto> pages = qdCompanyRepository.findAllPaging(companySearch.getPageable(), companySearch);


        model.addAttribute("foodGroups", FoodGroups.values());
        model.addAttribute("pages", pages);
        model.addAttribute("companyList", pages.getContent());
        model.addAttribute("maxPage", companySearch.getMaxPage());
        model.addAttribute("contentSize", pages.getContent().size());

        return "home/food/companyList";
    }

    @GetMapping("/companyListSlice")
    public String companyListSlice(Model model, CompanySearch companySearch){

//        log.info("===================================================================================");
//        log.info(companySearch.toString());
//        companySearch.setSize(pageSize);
//        companySearch.checkNull();
//        Slice<CompanyDto> pages = qdCompanyRepository.findAllPagingSlice(companySearch.getPageable(), companySearch);
//
//        model.addAttribute("foodGroups", FoodGroups.values());
//        model.addAttribute("pages", pages);
//        model.addAttribute("companyList", pages.getContent());
//        model.addAttribute("maxPage", companySearch.getMaxPage());
//        model.addAttribute("contentSize", pages.getContent().size());

        return "home/food/companyListSlice";
    }


    @ResponseBody
    @GetMapping("/companyListAjax")
    public ResponseEntity<Page<CompanyDto>> companyListAjax(Model model, CompanySearch companySearch){
        log.info("===================================================================================");
        log.info(companySearch.toString());

        companySearch.setSize(pageSize);
        companySearch.checkNull();
        log.info(companySearch.toString());
        Page<CompanyDto> pages = qdCompanyRepository.findAllPaging(companySearch.getPageable(), companySearch);
        pagesLog(pages);
        List<CompanyDto> content = pages.getContent();

        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

    private void pagesLog(Page<CompanyDto> pages) {
        log.info("paging query after ===> " + pages.getPageable().toString());
        log.info("pages.getPageable().next())  ===> " + pages.getPageable().next());
        log.info("pages.nextPageable().toString() ===> " + pages.nextPageable().toString());
        boolean next = pages.hasNext();
        boolean last = pages.isLast();
        log.info("next = " + next);
        log.info("last = " + last);
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

    @GetMapping("/getCompanyMenu/{companyNo}")
    public String getCompanyMenu(Model model, FoodSearch foodSearch, @PathVariable String companyNo,
                                 @RequestParam(defaultValue = "0") int pageNum){

        foodSearch.setCompanyNo(companyNo);

        PageRequest pageable = PageRequest.of(pageNum, 10);
        Page<FoodItemDto> pages = qdItemRepository.findAllPaging(pageable, foodSearch);

        if(pages.getContent().isEmpty()){
            model.addAttribute("hasNoItem", "");
        }

        model.addAttribute("foodList", pages.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("maxPage", 10);
        return "/home/food/getCompanyMenu";
    }

    @GetMapping("/getFoodItem/{id}")
    public String getFoodItem(@PathVariable Long id, Model model){
        FoodItemDto foodItemDto = qdItemRepository.findByIdJoinUploadFileJoinCompany(id);
        model.addAttribute("foodItemDto", foodItemDto);

        return "/home/food/getFoodItem";
    }

}
