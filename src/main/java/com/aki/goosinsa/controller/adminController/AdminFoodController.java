//package com.aki.goosinsa.controller.adminController;
//
//import com.aki.goosinsa.domain.dto.Food.FoodDto;
//import com.aki.goosinsa.domain.dto.Food.FoodKindStatus;
//import com.aki.goosinsa.domain.dto.Food.FoodStatus;
//import com.aki.goosinsa.domain.dto.util.UploadFileDto;
//import com.aki.goosinsa.domain.entity.item.UploadFile;
//import com.aki.goosinsa.domain.entity.item.FoodItem;
//import com.aki.goosinsa.domain.entity.item.Item;
//import com.aki.goosinsa.repository.ItemRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//
//@Controller
//@RequestMapping("/admin/food")
//@Slf4j
//@RequiredArgsConstructor
//public class AdminFoodController {
//
//    private String UPLOAD_FOLDER = System.getProperty("user.home") + File.separator + "upload" + File.separator;
//    private final ItemRepository itemRepository;
//
//    @ModelAttribute("modelFoodStatus")
//    public static FoodStatus[] foodStatus(){
//        return FoodStatus.values();
//    }
//
//    @ModelAttribute("modelFoodKindStatus")
//    public static FoodKindStatus[] foodKindStatus(){
//        return FoodKindStatus.values();
//    }
//
//    @GetMapping("foodList")
//    public String foodList(){
//        return "/admin/food/foodList";
//    }
//
//    @GetMapping("addFood")
//    public String addFood(Model model){
//        model.addAttribute("foodDto", new FoodDto());
//        return "admin/food/addFood";
//    }
//
//    @ResponseBody
//    @GetMapping("/addFood/{filename}")
//    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
//        Item findItem = itemRepository.findByDbFileName(filename);
//        log.info("findItem = {} ", findItem);
//        String uploadPath = findItem.getUploadFile().getUploadPath();
//        String fileCallPath = getFullPath(UPLOAD_FOLDER, uploadPath, filename);
//        log.info("fileCallPath = {}", fileCallPath);
//        return new UrlResource("file:" + fileCallPath);
//    }
//
//    private String getFullPath(String UPLOAD_FOLDER, String uploadPath, String filename) {
//        return UPLOAD_FOLDER + uploadPath + File.separator + filename;
//    }
//
//
//    @PostMapping("addFood")
//    public String addFoodPost(@ModelAttribute FoodDto foodDto, @RequestParam MultipartFile file,
//                              RedirectAttributes rttr) throws IOException {
//
//        log.info("origin file name = {}", file.getOriginalFilename());
//        // 파일저장 , dto 객체필드 생성
//        UploadFileDto uploadFileDto = new UploadFileDto(UPLOAD_FOLDER, file);
//
//        // item 엔티티 변환 (foodDto + uploadFileDto) -> db 저장
//        log.info("addFood post....{}  ", foodDto);
//        FoodItem foodItem = toFoodItem(foodDto, uploadFileDto);
//        itemRepository.save(foodItem);
//
//        rttr.addAttribute("filename", uploadFileDto.getDbFileName());
//
//        return "redirect:/food/menu";
//
//    }
//
//    /**
//     *  생성자 정의가 아직 불분명 해서, 일단 여기 컨트롤러에서 메서드로 일단 정의
//     * */
//    private FoodItem toFoodItem(FoodDto foodDto, UploadFileDto uploadFileDto) {
//        FoodItem foodItem = new FoodItem();
//        foodItem.setUploadFile(UploadFile.createUploadFile(uploadFileDto));
//        foodItem.setItemName(foodDto.getItemName());
//        foodItem.setPrice(foodDto.getPrice());
//        foodItem.setStockQuantity(foodDto.getStockQuantity());
//        foodItem.setExplains(foodDto.getExplains());
//        foodItem.setFoodStatus(foodDto.getFoodStatus());
//        foodItem.setFoodKindStatus(foodDto.getFoodKindStatus());
//        return foodItem;
//    }
//
//
//}
//
//
//
