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
@Getter
public class FoodItem extends Item {

    @Enumerated(EnumType.STRING)
    private FoodGroups foodGroups; // 음식 종류 - 음료, 면, 탕, 고기
    private String foodGroupsOfTitle; //세부 종류

    public FoodItem(FoodItemDto foodItemDto) {
        super(foodItemDto);
        this.foodGroups = foodItemDto.getFoodGroups();
        this.foodGroupsOfTitle = foodItemDto.getFoodGroupsOfTitle();
    }


}
