package com.aki.goosinsa.controller.food;

import com.aki.goosinsa.domain.dto.item.FoodGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodSearch {

    private Long userId;
    private String companyNo;
    private String companyName;
    private String foodName;
    private Integer price;
    private String foodGroups;
    private String foodGroupsOfTitle;

}
