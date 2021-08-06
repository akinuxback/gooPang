package com.aki.goosinsa.controller.food;

import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.repository.FoodItemRepository;
import com.aki.goosinsa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    // 분류1을 선택하면 분류2의 리스트를 보내줘야 한다.
    @ResponseBody
    @GetMapping("/getValues")
    public ResponseEntity<List<String>> getValues(@RequestParam String foodGroupsName){
        List<String> group = FoodGroups.returnFoodNameList(foodGroupsName);

        return new ResponseEntity<>(group, HttpStatus.OK );
    }

    @GetMapping("/menu")
    public String menu(Model model){
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Item> page = itemRepository.findAllPaging(pageRequest);
        List<Item> content = page.getContent();
        List<FoodItemDto> collect = content.stream()
                .map(c -> new FoodItemDto((FoodItem) c))
                .collect(Collectors.toList());

        model.addAttribute("foodList", collect);

//        List<Item> foodEntityList = itemRepository.findAll();
//
//        List<FoodItemDto> foodDtoList = foodEntityList.stream()
//                .map(f -> new FoodItemDto((FoodItem) f))
//                .collect(Collectors.toList());
//        model.addAttribute("foodList", foodDtoList);
        return "food/menu";
    }

}
