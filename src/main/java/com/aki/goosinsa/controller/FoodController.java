package com.aki.goosinsa.controller;

import com.aki.goosinsa.domain.dto.food.FoodGroups;
import com.aki.goosinsa.domain.dto.food.FoodItemDto;
import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.repository.FoodItemRepository;
import com.aki.goosinsa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodController {

    private final ItemRepository itemRepository;
    private final FoodItemRepository foodItemRepository;

    @ModelAttribute("foodGroups")
    public static FoodGroups[] foodGroups(){
        return FoodGroups.values();
    }

    @GetMapping("/menu")
    public String menu(Model model){
        List<FoodItem> foodList = foodItemRepository.findAll();
        model.addAttribute("foodList", foodList);
        return "food/menu";
    }

    @GetMapping("/addFood")
    public String addFood(Model model){
        model.addAttribute("uploadFileDto", new UploadFileDto());
        model.addAttribute("foodItemDto", new FoodItemDto());
        return "food/addFood";
    }


    /**
     * 파일저장은 ajax 요청으로 이미 저장한 상태라,
     * UploadFileDto 의 값들만 받아서 db에 저장만 하면된다.
     * */
    @PostMapping("/addFood")
    public String addFood(@ModelAttribute FoodItemDto foodItemDto) throws IOException {
        Item foodItem = FoodItem.toEntity(foodItemDto);
        itemRepository.save(foodItem);
        return "redirect:/food/menu";
    }

    // 분류1을 선택하면 분류2의 리스트를 보내줘야 한다.
    @ResponseBody
    @GetMapping("/getValues")
    public ResponseEntity<List<String>> getValues(@RequestParam String foodGroupsName){
        List<String> group = FoodGroups.returnFoodNameList(foodGroupsName);

        return new ResponseEntity<>(group, HttpStatus.OK );
    }

    @GetMapping("/addMenu")
    public String addMenu(){
        return "/food/addMenu";
    }

    @PostMapping("/addMenu")
    public String addMenuPost(@RequestParam String[] str){
        Arrays.stream(str).forEach(s ->{
            log.info(s);
        });
        return "/food/addMenu";
    }

}
