package com.aki.goosinsa.controller.company;

import com.aki.goosinsa.domain.dto.company.CompanyDto;
import com.aki.goosinsa.domain.dto.company.CompanySearch;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.company.CompanyStatus;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.domain.entity.user.UserDto;
import com.aki.goosinsa.repository.company.CompanyRepository;
import com.aki.goosinsa.repository.company.QDCompanyRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import com.aki.goosinsa.service.company.CompanyService;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/company")
@RequiredArgsConstructor
@Log4j2
public class AdminCompanyController {

    @ModelAttribute("companyStatus")
    public static CompanyStatus[] status(){
        return CompanyStatus.values();
    }

    @ModelAttribute("foodGroups")
    public static FoodGroups[] foodGroups(){
        return FoodGroups.values();
    }

    private final UserRepository userRepository;
    private final UserService userService;

    private final CompanyRepository companyRepository;
    private final QDCompanyRepository qdCompanyRepository;
    private final CompanyService companyService;


    @GetMapping("/companyList")
    public String companyList(Model model, CompanySearch companySearch){
        companySearch.checkNull();
        Page<CompanyDto> pages = qdCompanyRepository.findAllPaging(companySearch.getPageable(), companySearch);
        model.addAttribute("pages", pages);
        model.addAttribute("companyList", pages.getContent());
        model.addAttribute("maxPage", companySearch.getMaxPage());
        model.addAttribute("contentSize", pages.getContent().size());
        return "admin/company/companyList";
    }

    
    /**
     * addCompany ?????? ?????? - ???????????? ??????????????? ???????????? company ??? ????????? ??????.
     * */
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
        model.addAttribute("companyDto", new CompanyDto());
        return "admin/company/addCompany";
    }

    @PostMapping("/addCompany")
    public String addCompanyPost(@Validated @ModelAttribute CompanyDto companyDto, BindingResult bindingResult, Model model) throws Exception {
        log.info(companyDto.getAbbr());
        if(companyDto.getUserDto().getId() == null){
            model.addAttribute("errorMessage", "????????? ????????? ?????? ????????? ?????? ?????????.");
            return "admin/company/addCompany";
        }

        if(companyDto.getStatus() != CompanyStatus.Food){
            model.addAttribute("errorMessage", "???????????????. ??????????????? Food ????????? ?????? ????????? ???????????????. (?????????)");
            return "admin/company/addCompany";
        }

        if(bindingResult.hasErrors()){
            log.info(bindingResult);
            return "admin/company/addCompany";
        }

        if(companyRepository.findByCompanyNo(companyDto.getCompanyNo()) != null){
            bindingResult.rejectValue("companyNo", "invalid.companyNo",
                    new Object[]{companyDto.getCompanyNo()}, "????????? ????????? ????????? ?????? ?????????.");
            return "admin/company/addCompany";
        };

        if(!StringUtils.hasText(companyDto.getAddressDto().getCity()) || !StringUtils.hasText(companyDto.getAddressDto().getStreet()) || !StringUtils.hasText(companyDto.getAddressDto().getZipcode())){
            model.addAttribute("addressError", "????????? ?????? ?????? ?????????");
            return "admin/company/addCompany";
        };

//        if(companyDto.getMessageOneYn() == false){
//            companyDto.setMessageOne("");
//        }

        log.info(companyDto.toString());
        User user = userRepository.findById(companyDto.getUserDto().getId()).get();
        companyDto.setUserDto(UserDto.toDto(user));

        companyRepository.save(new Company(companyDto));

        return "redirect:/admin/company/companyList";
    }

    @GetMapping("/getCompany/{companyNo}")
    public String getCompany(@PathVariable String companyNo, Model model){
        CompanyDto companyDto = qdCompanyRepository.companyJoinUserAndUploadFileFindByCompanyNo(companyNo);
        model.addAttribute("companyDto", companyDto);
        return "admin/company/getCompany";
    }

    @GetMapping("/updateCompany/{companyNo}")
    public String updateCompany(@PathVariable String companyNo, Model model){
        CompanyDto companyDto = qdCompanyRepository.companyJoinUserAndUploadFileFindByCompanyNo(companyNo);
        model.addAttribute("companyDto", companyDto);
        return "admin/company/updateCompany";
    }

    @PostMapping("/updateCompany/{companyNo}")
    public String updateCompanyPost(@PathVariable String companyNo, CompanyDto companyDto, RedirectAttributes rttr){
        Company updateCompany = companyService.updateCompany(companyDto);
        rttr.addFlashAttribute("message", updateCompany.getCompanyNo() + " ??? ?????? ??????????????? ???????????????.");
        return "redirect:/admin/company/getCompany/" + updateCompany.getCompanyNo();
    }



}

