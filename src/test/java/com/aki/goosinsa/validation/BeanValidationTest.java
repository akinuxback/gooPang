package com.aki.goosinsa.validation;

import com.aki.goosinsa.domain.dto.item.FoodItemDto;
import com.aki.goosinsa.domain.dto.item.ItemDto;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    @Test
    void beanValidation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        FoodItemDto foodItemDto = new FoodItemDto();
        foodItemDto.setItemName(" "); // 공백
        foodItemDto.setPrice(0);
        foodItemDto.setStockQuantity(10000);

        Set<ConstraintViolation<FoodItemDto>> violations = validator.validate(foodItemDto);
        for (ConstraintViolation<FoodItemDto> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage());
        }
    }
}
