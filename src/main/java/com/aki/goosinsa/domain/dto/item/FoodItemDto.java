package com.aki.goosinsa.domain.dto.item;

import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import com.aki.goosinsa.domain.entity.company.Company;
import com.aki.goosinsa.domain.entity.item.FoodItem;
import com.aki.goosinsa.domain.entity.item.UploadFile;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FoodItemDto extends ItemDto {

    @NotNull(message = "선택하세요")
    private FoodGroups foodGroups; // 음식 종류 - 음료, 면, 탕, 고기
    @NotBlank(message = "선택하세요")
    private String foodGroupsOfTitle; //세부 종류

    public FoodItemDto (FoodItem foodItem){
        super(foodItem);
        this.foodGroups = foodItem.getFoodGroups();
        this.foodGroupsOfTitle = foodItem.getFoodGroupsOfTitle();
    }

    // 더미 데이터 생성용 생성자
    // QDItemRepositoryImplTest -> before each 에서 사용
    public FoodItemDto (String itemName, int price, int stockQuantity, String explains,
                        UploadFileDto uploadFileDto, Company company,
                        FoodGroups foodGroups, String foodGroupsOfTitle){
        super(null, itemName, price, stockQuantity, explains, uploadFileDto, company);
        this.foodGroups = foodGroups;
        this.foodGroupsOfTitle = foodGroupsOfTitle;
    }

}
