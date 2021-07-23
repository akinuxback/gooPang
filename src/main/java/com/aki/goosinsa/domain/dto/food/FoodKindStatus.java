package com.aki.goosinsa.domain.dto.food;

public enum FoodKindStatus {
    kind1("족발"), kind2("피자"), kind3("김밥")
//    , kind4("라면"), kind5("콜라")
    ;

    private String foodName;

    FoodKindStatus(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    public FoodKindStatus[] enumValues(){
        return FoodKindStatus.values();
    }

}
