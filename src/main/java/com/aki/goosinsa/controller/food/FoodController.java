package com.aki.goosinsa.controller.food;

import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.item.Item;
import com.aki.goosinsa.repository.item.FoodItemRepository;
import com.aki.goosinsa.repository.item.ItemRepository;
import com.aki.goosinsa.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Log4j2
public class FoodController {

    private final ItemRepository itemRepository;
    private final FoodItemRepository foodItemRepository;

    private final ItemService itemService;

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

//    @GetMapping("/menu")
//    public String menu(Model model, @RequestParam(defaultValue = "0") int pageNum){
//        PageRequest pageable = PageRequest.of(pageNum, 3, Sort.by(Sort.Direction.DESC, "id"));
//        Page<Item> pages = itemRepository.findAllPaging(pageable);
//        model.addAttribute("pages", pages);
//        model.addAttribute("maxPage", 5);
//
//        List<Item> content = pages.getContent();
//        List<FoodItemDto> collect = content.stream()
//                .map(c -> new FoodItemDto((FoodItem) c))
//                .collect(Collectors.toList());
//
//        model.addAttribute("foodList", collect);
//
////        List<Item> foodEntityList = itemRepository.findAll();
////
////        List<FoodItemDto> foodDtoList = foodEntityList.stream()
////                .map(f -> new FoodItemDto((FoodItem) f))
////                .collect(Collectors.toList());
////        model.addAttribute("foodList", foodDtoList);
//        return "food/menu";
//    }

    @GetMapping("/menu")
    public String menu(Model model, @RequestParam(defaultValue = "0") int pageNum,
                       FoodSearch foodSearch){
        PageRequest pageable = PageRequest.of(pageNum, 3);
        Page<FoodItemDto> pages = itemService.findAllPaging(pageable, foodSearch);
        model.addAttribute("pages", pages);
        model.addAttribute("maxPage", 5);

        List<FoodItemDto> content = pages.getContent();
        model.addAttribute("foodList", content);

        return "food/menu";
    }

}
