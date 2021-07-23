package com.aki.goosinsa.domain.dto.food;

import com.aki.goosinsa.domain.dto.uploadFile.FileType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FoodGroupTest {

    @Test
    @DisplayName("족발을 인자로 넣고 find하여 foodGroupList 의 값들이 잘나오는지 확인해보자.")
    void returnFoodGroupList(){
        //given
        FoodGroup meat = FoodGroup.findByFoodGroup("족발");
        
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
    @DisplayName("[NO TEST] Assert - 강의 내용 스터디")
    public void StudyAssert() throws Exception{

        

        //given
        FoodGroup meat = FoodGroup.findByFoodGroup("족발");

        //when
        List<String> foodNameList = meat.getFoodNameList();
        int size = foodNameList.size(); //3 - true
        //then
        assertAll(
                () -> assertNotNull(foodNameList, "null 이 아닌데 일부러 실패해봄"),
                () -> assertEquals(2, size, "족발이 속한 그룹의 foodName 들의 갯수확인"),
                () -> assertTrue(size > 4, "갯수는 3개다 일부러 실패해봄")
        );

    }
    
    @Test
    @DisplayName("예외를 던지고 받아보자")
    public void assert_exception_test() throws Exception{

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Test_exception().getNumber(1);
        });

        assertEquals("0 보다 큰값은 저장할수 없습니다.", exception.getMessage());
    }


    public class Test_exception{

        public void getNumber(int num){
            if(num > 0){
                throw new IllegalArgumentException("0 보다 큰값은 저장할수 없습니다.");
            }
        }

    }



}