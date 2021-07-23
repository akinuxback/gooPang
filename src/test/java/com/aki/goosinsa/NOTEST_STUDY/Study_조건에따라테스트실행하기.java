package com.aki.goosinsa.NOTEST_STUDY;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class Study_조건에따라테스트실행하기 {

    @Test
    @DisplayName("assumeTrue() -- 환경변수 값이 조건에 맞으면 [이후] 로직을 실행한다.")
    public void test1() throws Exception{
        //given
        String java_home = System.getenv("JAVA_HOME");
        System.out.println("java_home = " + java_home);

        //when
        assumeTrue("C:\\Program Files\\Java\\jdk-11.0.11".equalsIgnoreCase(java_home));

        //then
        String string = new Test_Class().getString();
        assertThat("AKINUX").isEqualTo(string);

    }

    @Test
    @DisplayName("assumingThat() -- 값이 조건에 맞으면 [안의] 로직을 실행한다.")
    public void test2() throws Exception{
        //given
        String string = new Test_Class().getString();
        System.out.println("string = " + string);

        //when
        assumingThat("AKINUX11".equalsIgnoreCase(string), ()->{
            assertThat("AKINUX").isEqualTo(string);
        });

        //then

        assertThat(string).isEqualTo("AKINUX");

    }


}
