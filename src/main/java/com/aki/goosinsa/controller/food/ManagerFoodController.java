package com.aki.goosinsa.controller.food;

import com.aki.goosinsa.asecurity.auth.PrincipalDetails;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
            rttr.addFlashAttribute("message", "등록된 업체가 없습니다. 관리자 문의를 통하여, 업체를 우선 등록 하세요");
            return "redirect:/manager/company/companyList";
        }
        model.addAttribute("userCompany", userCompany);

        return "manager/food/addFood";
    }

    /**
     * 파일저장은 ajax 요청으로 이미 directory 저장한 상태라,
     * UploadFileDto 의 값들만 받아서 db에 저장만 하면된다.
     * */
    @PostMapping("/addFood")
    public String addFood(RedirectAttributes rttr, String companyNo, @ModelAttribute FoodItemDto foodItemDto) throws IOException {
        log.info("============================ >  " + companyNo);
        // 유저 정보로 찾은 companyNo 만 파라미터로 받은후, company 테이블에서 찾아서 반환하기
        Company company = companyRepository.findById(companyNo).get();
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
    public String addMenuPost(String companyNo, AddMenuForm addMenuForm){
        // 유저 정보로 찾은 companyNo 만 파라미터로 받은후, company 테이블에서 찾아서 반환하기
        Company company = companyRepository.findById(companyNo).get();
        List<FoodItemDto> foodItemDtoList = addMenuForm.getFoodItemDtoList();
        foodItemDtoList.forEach(foodItemDto -> {
            foodItemDto.setCompany(company);
            FoodItem foodItem = new FoodItem(foodItemDto);
            itemRepository.save(foodItem);
        });

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
        rttr.addFlashAttribute("message", "해당 [ " + foodItemDto.getId() + " ] 번호의 등록건이 수정 되었습니다.");
        return "redirect:/manager/food/"+ id +"/getFood";
    }

    @PostMapping("/deleteFood")
    public String deleteFood(RedirectAttributes rttr, Long deleteId){
        itemRepository.deleteById(deleteId);
        rttr.addFlashAttribute("message", "해당 [ " + deleteId + " ] 번호의 등록건이 삭제 되었습니다.");
        return "redirect:/manager/food/foodList";
    }

    // 로그인한 유저의 정보 가져오기
    private User getLoginUser() {
        PrincipalDetails user = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(user.getId());
        log.info(user.getUsername());
        User userCompany = userRepository.findById(user.getId()).get();
        return userCompany;
    }

    //로긴한 user 의 & company 조인해서 정보 가져오기
    private User getLoginUserJoinCompany() {
        PrincipalDetails user = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(user.getId());
        log.info(user.getUsername());
        User userCompany = qdUserRepository.userLeftJoinCompanyFindById(user.getId());
        return userCompany;
    }

}