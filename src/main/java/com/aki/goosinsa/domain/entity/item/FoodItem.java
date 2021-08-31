package com.aki.goosinsa.domain.entity.item;

import com.aki.goosinsa.domain.dto.item.FoodGroups;
import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.function.Predicate;

@Entity
@DiscriminatorValue("F")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Log4j2
public class FoodItem extends Item {

    @Enumerated(EnumType.STRING)
    private FoodGroups foodGroups; // 음식 종류 - 음료, 면, 탕, 고기
    private String foodGroupsOfTitle; //세부 종류

    public FoodItem(FoodItemDto foodItemDto) {
        super(foodItemDto);
        this.foodGroups = foodItemDto.getFoodGroups();
        this.foodGroupsOfTitle = foodItemDto.getFoodGroupsOfTitle();
    }

    public void updateFoodItem (FoodItemDto foodItemDto) {
        this.itemName = foodItemDto.getItemName();
        this.price = foodItemDto.getPrice();
        this.stockQuantity = foodItemDto.getStockQuantity();
        this.explains = foodItemDto.getExplains();
        this.uploadFile.changeUploadFile(foodItemDto.getUploadFileDto());
        /**
         *  와 이걸 열어놓으니 파라미터로 받지 않아서 null 로 들어오니깐
         *  변경감지시 null 이 되어서 삭제가 되버리네
         * */
        //this.company = foodItemDto.getCompany();
        this.foodGroups = foodItemDto.getFoodGroups();
        this.foodGroupsOfTitle = foodItemDto.getFoodGroupsOfTitle();

        log.info("======================================================");
        log.info(this.uploadFile.toString());
        log.info("======================================================");
    }

}
