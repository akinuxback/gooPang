package com.aki.goosinsa.domain.dto.food;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum FoodGroups {
    DRINK("음료", Arrays.asList("커피", "쥬스")),
    SOUP("탕-찌개", Arrays.asList("탕", "찌개")),
    CHICKEN("치킨", Arrays.asList("순살", "뼈")),
    PIZZA("피자", Arrays.asList("오리지날", "씬")),
    MEAT("고기", Arrays.asList("소고기", "돼지고기", "삼겹살")),
    KMEAT("족발-보쌈", Arrays.asList("족발", "보쌈", "마늘족발")),
    SCHOOLFOOD("분식", Arrays.asList("떡볶이", "순대", "오뎅", "튀김", "김밥", "라면")),
    ETC("etc", Arrays.asList("기타"));

    private String foodGroupsName;
    private List<String> foodNameList;

    FoodGroups(String foodGroupName, List<String> foodNameList) {
        this.foodGroupsName = foodGroupName;
        this.foodNameList = foodNameList;
    }

    public String getFoodGroupsName() {
        return foodGroupsName;
    }

    public List<String> getFoodNameList() {
        return foodNameList;
    }

    //1-1
    public static List<String> returnFoodNameList(String gname){
        FoodGroups group = FoodGroups.findByName(gname.toUpperCase().trim());
        List<String> foodNameList = group.foodNameList;

        return foodNameList;
    }

    //1-2
    public static FoodGroups findByName(String gname){
        return Arrays.stream(FoodGroups.values())
                .filter(name -> name.name().equals(gname))
                .findAny()
                .orElse(ETC);
    }


    //2-1
    public static FoodGroups findByFoodGroup(String name){
        return Arrays.stream(FoodGroups.values())
                .filter(gName -> gName.hasFoodTypeName(name))
                .findAny()
                .orElse(ETC);
    }

    //2-2
    private boolean hasFoodTypeName(String name) {
        return foodNameList.stream()
                .anyMatch(n -> n.equals(name));
    }
}
