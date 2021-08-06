package com.aki.goosinsa.controller.food;

import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AddMenuForm {
    private List<FoodItemDto> foodItemDtoList;
}
