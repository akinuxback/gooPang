package com.aki.goosinsa.controller;

import com.aki.goosinsa.domain.dto.food.FoodItemDto;
import com.aki.goosinsa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodController {

    private final ItemRepository itemRepository;

    @GetMapping("/menu")
    public String menu(){
        return "food/menu";
    }

    @GetMapping("/addFood")
    public String addFood(Model model){
        model.addAttribute("foodItemDto", new FoodItemDto());
        return "admin/food/addFood";
    }

}
