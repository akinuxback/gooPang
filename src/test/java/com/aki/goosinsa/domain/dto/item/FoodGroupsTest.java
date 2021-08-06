package com.aki.goosinsa.domain.dto.item;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FoodGroupsTest {

    @Test
    @DisplayName("족발을 인자로 넣고 find하여 foodGroupList 의 값들이 잘나오는지 확인해보자.")
    void returnFoodGroupList(){
        //given
        FoodGroups meat = FoodGroups.findByFoodGroup("족발");
        
        //when
        List<String> foodNameList = meat.getFoodNameList();
        int foodNameListSize = foodNameList.size();
        foodNameList.forEach( f -> {
            log.info("foodNameList name = {}",  f);
        });

        log.info("foodGroupName = {}", meat.name());
        log.info("foodNameList.size() = {}", foodNameListSize);

        //then
        assertThat(foodNameListSize).isEqualTo(3); //족발, 보쌈, 마늘족발
    }

    @Test
    @DisplayName("FoodGroup.name() 을 string 인자로 받고 매칭된 그룹의 값들을 리턴 해보자")
    void returnFoodGroupsNameOfValues(){
        List<String> group1 = FoodGroups.returnFoodNameList("driNK");
        group1.forEach(d -> System.out.println("group1 = " + d));

        List<String> group2 = FoodGroups.returnFoodNameList("SOUP   ");
        group2.forEach(d -> System.out.println("group2 = " + d));

        List<String> group3 = FoodGroups.returnFoodNameList("   SCHOOLFOOD");
        group3.forEach(d -> System.out.println("group3 = " + d));

        assertThat(2).isEqualTo(group1.size());
        assertThat(2).isEqualTo(group2.size());
        assertThat(6).isEqualTo(group3.size());




    }


}