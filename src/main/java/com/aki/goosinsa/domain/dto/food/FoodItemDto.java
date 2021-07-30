package com.aki.goosinsa.domain.dto.food;

import com.aki.goosinsa.domain.dto.uploadFile.UploadFileDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FoodItemDto {

    private Long id;
    private String itemName;
    private int price;
    private int stockQuantity;
    private String explains; // 메뉴 설명
    private FoodGroups foodGroups; // 음식 종류 - 음료, 면, 탕, 고기
    private UploadFileDto uploadFileDto;

    public void setUploadFileDto(UploadFileDto uploadFileDto) {
        this.uploadFileDto = uploadFileDto;
    }

}
