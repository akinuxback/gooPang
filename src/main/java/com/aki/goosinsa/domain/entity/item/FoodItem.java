package com.aki.goosinsa.domain.entity.item;

import com.aki.goosinsa.domain.dto.food.FoodItemDto;
import com.aki.goosinsa.domain.dto.food.FoodKindStatus;
import com.aki.goosinsa.domain.dto.food.FoodStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@DiscriminatorValue("F")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class FoodItem extends Item {

    @Enumerated(EnumType.STRING)
    private FoodStatus foodStatus; // 음식 종류 - 음료, 면, 탕, 고기

    @Enumerated(EnumType.STRING)
    private FoodKindStatus foodKindStatus; // 음식 이름

    public static FoodItem toEntity(FoodItemDto foodItemDto){
        FoodItem foodItem = new FoodItem();
        foodItem.setItemName(foodItem.getItemName());
        foodItem.setPrice(foodItem.getPrice());
        foodItem.setStockQuantity(foodItem.getStockQuantity());
        foodItem.setExplains(foodItemDto.getExplains());
        foodItem.setFoodStatus(foodItem.getFoodStatus());
        foodItem.setFoodKindStatus(foodItem.getFoodKindStatus());
        foodItem.setUploadFile(UploadFile.toEntity(foodItemDto.getUploadFileDto()));

        return foodItem;
    }

}
