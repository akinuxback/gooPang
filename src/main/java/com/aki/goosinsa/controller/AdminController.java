package com.aki.goosinsa.controller;

import com.aki.goosinsa.controller.food.AddMenuForm;
import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ItemRepository itemRepository;

    @ModelAttribute("foodGroups")
    public static FoodGroups[] foodGroups(){
        return FoodGroups.values();
    }

    @ModelAttribute("uploadFileDto")
    public static UploadFileDto uploadFileDto(){return new UploadFileDto();}

    @ModelAttribute("foodItemDto")
    public static FoodItemDto foodItemDto() {return new FoodItemDto();}


    @GetMapping("/adminHome")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/managerHome")
    public String mangerHome(){
        return "managerHome";
    }

//    @PostMapping("/addFood")
//    public String addFood(@ModelAttribute FoodItemDto foodItemDto) throws IOException {
//        FoodItem foodItem = new FoodItem(foodItemDto);
//        itemRepository.save(foodItem);
//        return "redirect:/food/menu";
//    }

//    @GetMapping("/addMenu")
//    public String addMenu(){
//        return "admin/addMenu";
//    }
//
//    @PostMapping("/addMenu")
//    public String addMenuPost(AddMenuForm addMenuForm){
//
//        List<FoodItemDto> foodItemDtoList = addMenuForm.getFoodItemDtoList();
//        foodItemDtoList.forEach(foodItemDto -> {
//            FoodItem foodItem = new FoodItem(foodItemDto);
//            itemRepository.save(foodItem);
//        });
//
//        return "redirect:/food/menu";
//    }
}
