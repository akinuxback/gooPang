package com.aki.goosinsa.domain.entity.item;

import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import lombok.*;

import javax.persistence.*;

@Entity
@DiscriminatorValue("F")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class FoodItem extends Item {

    @Enumerated(EnumType.STRING)
    private FoodGroups foodGroups; // 음식 종류 - 음료, 면, 탕, 고기
    private String foodGroupsOfTitle; //세부 종류


//    public static FoodItem createEntity(FoodItemDto foodItemDto){
//
//        FoodItem foodItem = (FoodItem) Item.createItem(foodItemDto);
//        foodItem.setFoodGroups(foodItemDto.getFoodGroups());
//        foodItem.setFoodGroupsOfTitle(foodItemDto.getFoodGroupsOfTitle());
//
//        return foodItem;
//
//        FoodItem foodItem = new FoodItem();
//        foodItem.setItemName(foodItemDto.getItemName());
//        foodItem.setPrice(foodItemDto.getPrice());
//        foodItem.setStockQuantity(foodItemDto.getStockQuantity());
//        foodItem.setExplains(foodItemDto.getExplains());
//        foodItem.setFoodGroups(foodItemDto.getFoodGroups());
//        foodItem.setUploadFile(UploadFile.toEntity(foodItemDto.getUploadFileDto()));
//
//        return foodItem;
//
//    }

    public FoodItem(FoodItemDto foodItemDto) {
        super(foodItemDto);
        this.foodGroups = foodGroups;
        this.foodGroupsOfTitle = foodGroupsOfTitle;
    }

}
