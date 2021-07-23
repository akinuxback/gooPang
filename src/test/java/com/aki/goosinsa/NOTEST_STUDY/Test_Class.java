package com.aki.goosinsa.NOTEST_STUDY;

public class Test_Class {

    public void getNumber(int num){
        if(num > 0){
            throw new IllegalArgumentException("0 보다 큰값은 저장할수 없습니다.");
        }
    }

    public String getString(){
        return "AKINUX";
    }

}
