package com.aki.goosinsa.domain.dto.item;

import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FoodItemDto extends ItemDto {

    private FoodGroups foodGroups; // 음식 종류 - 음료, 면, 탕, 고기
    private String foodGroupsOfTitle; //세부 종류

}
