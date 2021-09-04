package com.aki.goosinsa.controller.company;

import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.company.CompanyStatus;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserDto;
import com.aki.goosinsa.repository.company.CompanyRepository;
import com.aki.goosinsa.repository.company.QDCompanyRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import com.aki.goosinsa.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/company")
@RequiredArgsConstructor
@Log4j2
public class AdminCompanyController {

    @ModelAttribute("companyStatus")
    public static CompanyStatus[] status(){
        return CompanyStatus.values();
    }

    private final UserRepository userRepository;
    private final UserService userService;

    private final CompanyRepository companyRepository;
    private final QDCompanyRepository qdCompanyRepository;

    @GetMapping("/managerEdit")
    public String edit(Model model, Pageable pageable, CompanySearch companySearch){
        companySearch.checkNull();
        Page<Company> pages = qdCompanyRepository.findAllPaging(companySearch.getPageable(), companySearch);
        model.addAttribute("pages", pages);
        model.addAttribute("maxPage", companySearch.getMaxPage());
        model.addAttribute("contentSize", pages.getContent().size());
        return "admin/company/managerEdit";
    }

    @GetMapping("/findUser/{ph}")
    @ResponseBody
    public ResponseEntity<CreateCompanyByUserDto> findUserByPh(@PathVariable String ph) throws NullPointerException{
        log.info("id ==============>  " + ph);
        User findUser = userRepository.findByPhoneNumber(ph);
        CreateCompanyByUserDto userDto = CreateCompanyByUserDto.builder()
                .id(findUser.getId())
                .username(findUser.getUsername())
                .name(findUser.getName())
                .phoneNumber(findUser.getPhoneNumber())
                .build();

        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    @GetMapping("/addCompany")
    public String addCompany(Model model){
        model.addAttribute("company", new Company());
        return "admin/company/addCompany";
    }

    @PostMapping("/addCompany")
    public String addCompanyPost(CompanyDto companyDto) throws Exception {

        if(companyDto.getMessageOneYn() == false){
            companyDto.setMessageOne("");
        }
        log.info(companyDto.toString());
        User user = userRepository.findById(companyDto.getUserDto().getId()).get();
        companyDto.setUserDto(UserDto.toDto(user));

        companyRepository.save(new Company(companyDto));

        return "redirect:/admin/company/addCompany";
    }

}

