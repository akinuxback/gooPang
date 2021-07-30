package com.aki.goosinsa.domain.entity.item;

import com.aki.goosinsa.domain.dto.food.FoodGroups;
import com.aki.goosinsa.domain.dto.food.FoodItemDto;
import lombok.*;

import javax.persistence.*;

@Entity
@DiscriminatorValue("F")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class FoodItem extends Item {

    @Enumerated(EnumType.STRING)
    private FoodGroups foodGroups; // 음식 종류 - 음료, 면, 탕, 고기



    public static FoodItem toEntity(FoodItemDto foodItemDto){
        FoodItem foodItem = new FoodItem();
        foodItem.setItemName(foodItemDto.getItemName());
        foodItem.setPrice(foodItemDto.getPrice());
        foodItem.setStockQuantity(foodItemDto.getStockQuantity());
        foodItem.setExplains(foodItemDto.getExplains());
        foodItem.setFoodGroups(foodItemDto.getFoodGroups());
        foodItem.setUploadFile(UploadFile.toEntity(foodItemDto.getUploadFileDto()));

        return foodItem;
    }

}
