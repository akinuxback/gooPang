package com.aki.goosinsa.controller.food;

import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.repository.item.FoodItemRepository;
import com.aki.goosinsa.repository.item.ItemRepository;
import com.aki.goosinsa.repository.item.QDItemRepository;
import com.aki.goosinsa.repository.item.QDItemRepositoryImpl;
import com.aki.goosinsa.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/food")
@Slf4j
public class AdminFoodController {

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
    public String foodList(Model model){
//        model.addAttribute("foodList", qdItemRepository.findAll(PageRequest.of(0, 3)));
        return "admin/food/foodList";
    }

    @GetMapping("/addFood")
    public String addFood(Model model){
        return "admin/food/addFood";
    }

    /**
     * 파일저장은 ajax 요청으로 이미 directory 저장한 상태라,
     * UploadFileDto 의 값들만 받아서 db에 저장만 하면된다.
     * */
    @PostMapping("/addFood")
    public String addFood(@ModelAttribute FoodItemDto foodItemDto) throws IOException {
        FoodItem foodItem = new FoodItem(foodItemDto);
        itemRepository.save(foodItem);
        return "redirect:/food/menu";
    }

    @GetMapping("/addMenu")
    public String addMenu(){
        return "admin/food/addMenu";
    }

    @PostMapping("/addMenu")
    public String addMenuPost(AddMenuForm addMenuForm){

        List<FoodItemDto> foodItemDtoList = addMenuForm.getFoodItemDtoList();
        foodItemDtoList.forEach(foodItemDto -> {
            FoodItem foodItem = new FoodItem(foodItemDto);
            itemRepository.save(foodItem);
        });

        return "redirect:/food/menu";
    }

}
