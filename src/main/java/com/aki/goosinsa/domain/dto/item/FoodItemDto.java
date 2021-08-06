package com.aki.goosinsa.domain.dto.item;

import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FoodItemDto extends ItemDto {

    private FoodGroups foodGroups; // 음식 종류 - 음료, 면, 탕, 고기
    private String foodGroupsOfTitle; //세부 종류

    public FoodItemDto (FoodItem foodItem){
        super(foodItem);
        this.foodGroups = foodItem.getFoodGroups();
        this.foodGroupsOfTitle = foodItem.getFoodGroupsOfTitle();
    }

}
