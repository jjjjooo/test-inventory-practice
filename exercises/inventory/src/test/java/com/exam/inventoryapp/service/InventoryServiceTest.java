package com.exam.inventoryapp.service;

import com.exam.inventoryapp.test.exception.NotImplementedTestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class InventoryServiceTest {
    @Nested
    class FindByItemId {
        @BeforeEach
        void setUp(){

        }
        @DisplayName("itemId를 갖는 Entity를 찾지 못하면, null을 반환한다")
        @Test
        void test1(){
            throw new NotImplementedTestException();
        }

        @DisplayName("itemId를 갖는 Entity를 찾지 못하면, null을 반환한다")
        @Test
        void test1000(){

        }
    }

    @Nested
    class DecreaseByItemId {
        @DisplayName("quantity가 음수라면, Exception을 throw 한다")
        @Test
        void test1(){
            throw new NotImplementedTestException();
        }

        @DisplayName("itemId를 갖는 entity를 찾지 못하면, Exception을 throw 한다.")
        @Test
        void test2(){
            throw new NotImplementedTestException();
        }

        @DisplayName("quantity가 stock보다 크면, Exception을 throw 한다.")
        @Test
        void test3(){
            throw new NotImplementedTestException();
        }

        @DisplayName("변경된 entity가 없다면, Exception을 thorw 한다.")
        @Test
        void test4(){
            throw new NotImplementedTestException();
        }

        @DisplayName("itemId를 갖는 entity를 찾으면, stock을 차감하고 inventory를 반환한다.")
        @Test
        void test5(){
            throw new NotImplementedTestException();
        }
    }

    @Nested
    class UpdateStock {
        @DisplayName("수정할 stock이 유효하지 않다면, Exception을 throw한다")
        @Test
        void test1() {
            throw new NotImplementedTestException();
        }

        @DisplayName("itemId를 갖는 entity를 찾지 못하면, Exception을 throw한다")
        @Test
        void test2() {
            throw new NotImplementedTestException();
        }

        @DisplayName("itemId를 갖는 entity를 찾으면, stock을 수정하고 inventory를 반환한다")
        @Test
        void test1000() {
            throw new NotImplementedTestException();
        }
    }
}
