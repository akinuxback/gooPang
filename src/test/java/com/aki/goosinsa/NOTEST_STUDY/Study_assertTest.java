package com.aki.goosinsa.NOTEST_STUDY;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class Study_assertTest {


    @Test
    @DisplayName("예상되는 예외를 받아보자")
    public void assert_exception_test() throws Exception{

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Test_Class().getNumber(1);
        });

        assertEquals("0 보다 큰값은 저장할수 없습니다.", exception.getMessage());
    }


    @Disabled
    @Test
    @DisplayName("실행 시간 테스트1 - 100ms 가 넘는지 테스트")
    public void create_test() throws Exception{
        assertTimeout(Duration.ofMillis(100), ()->{
            new Test_Class().getString();
            Thread.sleep(300);
        });
    }

    @Disabled
    @Test
    @DisplayName("실행 시간 테스트2 - 100ms 가 넘으면 중지 테스트")
    public void create_THREADLOCAL_test() throws Exception{
        // assertTimeoutPreemptively() - 단점 개중요 :
        // --> 스프링에서 제공하는 쓰레드가 아닌 독립적인 쓰레드가 생성되서
        // --> db에 롤백처리가 되지 않을수 있다.
        // --> 사용권장 (x)
        assertTimeoutPreemptively(Duration.ofMillis(100), ()->{
            new Test_Class().getString();
            Thread.sleep(300);
        });
    }



}