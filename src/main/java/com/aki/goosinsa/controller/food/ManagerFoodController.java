package com.aki.goosinsa.controller.food;

import com.aki.goosinsa.asecurity.auth.PrincipalDetails;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.user.User;
import com.aki.goosinsa.repository.company.CompanyRepository;
import com.aki.goosinsa.repository.item.FoodItemRepository;
import com.aki.goosinsa.repository.item.ItemRepository;
import com.aki.goosinsa.repository.item.QDItemRepository;
import com.aki.goosinsa.repository.user.QDUserRepository;
import com.aki.goosinsa.repository.user.UserRepository;
import com.aki.goosinsa.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager/food")
@Log4j2
public class ManagerFoodController {

    private final UserRepository userRepository;
    private final QDUserRepository qdUserRepository;

    private final CompanyRepository companyRepository;

    private final ItemRepository itemRepository;
    private final FoodItemRepository foodItemRepository;
    private final QDItemRepository qdItemRepository;
    private final ItemService itemService;



    @ModelAttribute("foodGroups")
    public static FoodGroups[] foodGroups(){
        return FoodGroups.values();
    }

    @ModelAttribute("uploadFileDto")
    public static UploadFileDto uploadFileDto(){return new UploadFileDto();}

    @ModelAttribute("foodItemDto")
    public static FoodItemDto foodItemDto() {return new FoodItemDto();}


    @GetMapping("/foodList")
    public String foodList(Model model, @RequestParam(defaultValue = "0") int pageNum,
                           FoodSearch foodSearch){
        User loginUser = getLoginUser();
        if (loginUser != null){
            foodSearch.setUserId(loginUser.getId());
        }
        PageRequest pageable = PageRequest.of(pageNum, 10);
        Page<FoodItemDto> pages = qdItemRepository.findAllPaging(pageable, foodSearch);

        List<FoodItemDto> content = pages.getContent();
        model.addAttribute("foodItemDtoList", content);
        model.addAttribute("pages", pages);
        model.addAttribute("maxPage", 10);

        return "manager/food/foodList";
    }

    @GetMapping("/addFood")
    public String addFood(Model model, RedirectAttributes rttr, @RequestParam(required = false) String companyNo){
        User userCompany = getLoginUserJoinCompany();
        if(userCompany.getCompanyList().size() == 0){
            rttr.addFlashAttribute("message", "????????? ????????? ????????????. ????????? ????????? ?????????, ????????? ?????? ?????? ?????????");
            return "redirect:/manager/company/companyList";
        }
        model.addAttribute("userCompany", userCompany);

        return "manager/food/addFood";
    }

    /**
     * ??????????????? ajax ???????????? ?????? directory ????????? ?????????,
     * UploadFileDto ??? ????????? ????????? db??? ????????? ????????????.
     * */
    @PostMapping("/addFood")
    public String addFood(@Validated @ModelAttribute FoodItemDto foodItemDto, BindingResult bindingResult, RedirectAttributes rttr, Model model) throws IOException {
        User userCompany = getLoginUserJoinCompany();
        if(userCompany.getCompanyList().size() == 0){
            rttr.addFlashAttribute("message", "????????? ????????? ????????????. ????????? ????????? ?????????, ????????? ?????? ?????? ?????????");
            return "redirect:/manager/company/companyList";
        }
        model.addAttribute("userCompany", userCompany);

        // ????????? model ??? ????????? ??????, ?????? ????????? ???????????? companyNo
        model.addAttribute("companyNo", foodItemDto.getCompany().getCompanyNo());

        log.info(bindingResult);

        log.info("=======================================================================foodItemDto.getCompany().getCompanyNo() ====================");
        log.info(foodItemDto.getCompany().getCompanyNo());

        if(foodItemDto.getCompany().getCompanyNo() == null){
            model.addAttribute("message", "????????? ????????? ?????? ?????????.");
            return "manager/food/addFood";
        }

        Company company = companyRepository.findById(foodItemDto.getCompany().getCompanyNo()).orElseThrow();

        if(bindingResult.hasErrors()){
            return "manager/food/addFood";
        }

        foodItemDto.setCompany(company);
        FoodItem foodItem = new FoodItem(foodItemDto);
        itemRepository.save(foodItem);
        return "redirect:/manager/food/foodList";
    }

    @GetMapping("/addMenu")
    public String addMenu(Model model){
        User userCompany = getLoginUserJoinCompany();
        model.addAttribute("userCompany", userCompany);
        return "manager/food/addMenu";
    }

    @PostMapping("/addMenu")
    public String addMenuPost(String companyNo, AddMenuForm addMenuForm, RedirectAttributes rttr){
        // ?????? ????????? ?????? companyNo ??? ??????????????? ?????????, company ??????????????? ????????? ????????????

        try {
            Company company = companyRepository.findById(companyNo).get();
            List<FoodItemDto> foodItemDtoList = addMenuForm.getFoodItemDtoList();
            foodItemDtoList.forEach(foodItemDto -> {
                foodItemDto.setCompany(company);
                FoodItem foodItem = new FoodItem(foodItemDto);
                itemRepository.save(foodItem);
            });
        } catch (Exception e) {
            e.getMessage();
            rttr.addFlashAttribute("errorMessage", "?????? ?????? ???????????? ?????????.");
            return "redirect:/manager/food/addMenu";
        }

        return "redirect:/manager/food/foodList";
    }

    @GetMapping("/{id}/getFood")
    public String getFood(Model model, @PathVariable Long id){
        FoodItemDto foodItemDto = qdItemRepository.findByIdJoinUploadFile(id);
        model.addAttribute("foodItemDto", foodItemDto);
        log.info(foodItemDto.getUploadFileDto().toString());
        return "manager/food/getFood";
    }

    @GetMapping("/{id}/editFood")
    public String editFood(Model model, @PathVariable Long id){
        FoodItemDto foodItemDto = qdItemRepository.findByIdJoinUploadFile(id);
        model.addAttribute("foodItemDto", foodItemDto);
        return "manager/food/editFood";
    }

    @PostMapping("/{id}/editFood")
    public String editFoodUpadate(RedirectAttributes rttr, @PathVariable Long id, FoodItemDto foodItemDto){
        itemService.updateItem(foodItemDto);
        log.info("uploadFileDto ==================> " + foodItemDto.getUploadFileDto());
        rttr.addFlashAttribute("message", "?????? [ " + foodItemDto.getId() + " ] ????????? ???????????? ?????? ???????????????.");
        return "redirect:/manager/food/"+ id +"/getFood";
    }

    @PostMapping("/deleteFood")
    public String deleteFood(RedirectAttributes rttr, Long deleteId){
        itemRepository.deleteById(deleteId);
        rttr.addFlashAttribute("message", "?????? [ " + deleteId + " ] ????????? ???????????? ?????? ???????????????.");
        return "redirect:/manager/food/foodList";
    }

    // ???????????? ????????? ?????? ????????????
    private User getLoginUser() {
        PrincipalDetails user = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(user.getId());
        log.info(user.getUsername());
        User userCompany = userRepository.findById(user.getId()).get();
        return userCompany;
    }

    //????????? user ??? & company ???????????? ?????? ????????????
    private User getLoginUserJoinCompany() {
        PrincipalDetails user = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(user.getId());
        log.info(user.getUsername());
        User userCompany = qdUserRepository.userLeftJoinCompanyFindById(user.getId());
        return userCompany;
    }


}