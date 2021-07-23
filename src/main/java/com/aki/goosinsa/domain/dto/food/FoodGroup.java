package com.aki.goosinsa.domain.dto.food;

import java.util.Arrays;
import java.util.List;

public enum FoodGroup {
    DRINK("음료", Arrays.asList("커피", "쥬스")),
    SOUP("탕-찌개", Arrays.asList("김치찌개", "된장찌개")),
    CHICKEN("치킨", Arrays.asList("양념치킨", "후라이드치킨")),
    PIZZA("피자", Arrays.asList("콤비피자", "불고기피자", "치즈피자")),
    BEEF("소고기", Arrays.asList("등심", "살치살", "갈비살", "업진살")),
    MEAT("족발-보쌈", Arrays.asList("족발", "보쌈", "마늘족발")),
    SchoolFood("분식", Arrays.asList("떡볶이", "순대", "오뎅", "튀김", "김밥", "라면")),
    ETC("etc", Arrays.asList("기타"));

    private String foodGroupName;
    private List<String> foodNameList;

    FoodGroup(String foodGroupName, List<String> foodTypeList) {
        this.foodGroupName = foodGroupName;
        this.foodNameList = foodTypeList;
    }

    public String getFoodGroupName() {
        return foodGroupName;
    }

    public List<String> getFoodNameList() {
        return foodNameList;
    }

    public static FoodGroup findByFoodGroup(String name){
        return Arrays.stream(FoodGroup.values())
                .filter(foodNameList -> foodNameList.hasFoodTypeName(name))
                .findAny()
                .orElse(ETC);
    }

    private boolean hasFoodTypeName(String name) {
        return foodNameList.stream()
                .anyMatch(n -> n.equals(name));
    }
}
