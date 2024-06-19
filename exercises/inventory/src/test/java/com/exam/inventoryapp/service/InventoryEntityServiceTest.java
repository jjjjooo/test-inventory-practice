package com.exam.inventoryapp.service;

import com.exam.inventoryapp.inventory.service.InventoryService;
import com.exam.inventoryapp.inventory.service.domain.Inventory;
import com.exam.inventoryapp.inventory.service.exception.InsufficientStockException;
import com.exam.inventoryapp.inventory.service.exception.InvalidDecreaseQuantityException;
import com.exam.inventoryapp.inventory.service.exception.InvalidStockException;
import com.exam.inventoryapp.inventory.service.exception.ItemNotFoundException;
import com.exam.inventoryapp.repository.InventoryRepositoryFaker;
import com.exam.inventoryapp.test.exception.NotImplementedTestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class InventoryEntityServiceTest {

    @InjectMocks
    InventoryService sut;

    @Spy
    InventoryRepositoryFaker inventoryRepository;


    @Nested
    class FindByItemId {
        final String existingItemId = "1";
        final Long stock = 10L;

        @BeforeEach
        void setUp() {
            inventoryRepository.addInventoryEntity(existingItemId, stock);

        }

        @DisplayName("itemId를 갖는 Entity를 찾지 못하면, null을 반환한다")
        @Test
        void test1() {
            //given
            final String nonExistingItemId = "2";
            //when
            final Inventory result = sut.findByItemId(nonExistingItemId);
            // then
            assertNull(result);
        }

        @DisplayName("itemId를 갖는 Entity를 찾지 못하면, null을 반환한다")
        @Test
        void test1000() {
            //given
            final String existingItemId = "1";
            final Long stock = 10L;

            //when
            final Inventory result = sut.findByItemId(existingItemId);

            //then
            assertNotNull(result);
            assertEquals(existingItemId, result.getItemId());
            assertEquals(stock, result.getStock());

        }
    }

    @Nested
    class DecreaseByItemId {
        final String existingItemId = "1";
        final Long stock = 10L;

        @BeforeEach
        void setUp() {
            inventoryRepository.addInventoryEntity(existingItemId, stock);

        }

        @DisplayName("quantity가 음수라면, Exception을 throw 한다")
        @Test
        void test1() {
            //given
            final Long quantity = -1L;

            // when
            assertThrows(InvalidDecreaseQuantityException.class, () -> {
                sut.decreaseByItem(existingItemId, quantity);
            });

        }

        @DisplayName("itemId를 갖는 entity를 찾지 못하면, Exception을 throw 한다.")
        @Test
        void test2() {
            //given
            final Long quantity = 10L;
            final String nonExistingItemId = "2";

            //when
            assertThrows(ItemNotFoundException.class, () -> {
                sut.decreaseByItem(nonExistingItemId, quantity);
            });
        }

        @DisplayName("quantity가 stock보다 크면, Exception을 throw 한다.")
        @Test
        void test3() {
            //given
            final Long overQuantity = stock + 1;

            //when
            assertThrows(InsufficientStockException.class, () -> {
                sut.decreaseByItem(existingItemId, overQuantity);
            });

        }

        @DisplayName("변경된 entity가 없다면, Exception을 thorw 한다.")
        @Test
        void test4() {
            //given
            final Long quantity = 10L;

            doReturn(0).when(inventoryRepository).decreaseStock(existingItemId, quantity);

            //when
            assertThrows(ItemNotFoundException.class, () -> {
                sut.decreaseByItem(existingItemId, quantity);
            });

            verify(inventoryRepository).decreaseStock(existingItemId, quantity);
        }

        @DisplayName("itemId를 갖는 entity를 찾으면, stock을 차감하고 inventory를 반환한다.")
        @Test
        void test5() {
            // given
            final Long quantity = 10L;

            //when
            final Inventory result = sut.decreaseByItem(existingItemId, quantity);

            //then
            assertNotNull(result);
            assertEquals(existingItemId, result.getItemId());
            assertEquals(stock - quantity, result.getStock());
        }
    }

    @Nested
    class UpdateStock {
        final String existingItemId = "1";
        final Long newStack = 200L;
        @BeforeEach
        void setUp() {
            inventoryRepository.addInventoryEntity(existingItemId, newStack);

        }

        @DisplayName("수정할 stock이 유효하지 않다면, Exception을 throw한다")
        @Test
        void test1() {
            //given
            final Long newStock = -1L;
            final Long stock = 100L;
            assertThrows(InvalidStockException.class, ()-> {
                sut.updateStock(existingItemId, newStock);
            });
        }

        @DisplayName("itemId를 갖는 entity를 찾지 못하면, Exception을 throw한다")
        @Test
        void test2() {
            //given
            final Long quantity = 200L;
            final String nonExistingItemId = "2";

            //when
            assertThrows(ItemNotFoundException.class, () -> {
                sut.updateStock(nonExistingItemId, quantity);
            });
        }

        @DisplayName("itemId를 갖는 entity를 찾으면, stock을 수정하고 inventory를 반환한다")
        @Test
        void test1000() {
            // given
            final Long nextStock = 100L;

            //when
            final Inventory result = sut.updateStock(existingItemId, nextStock);

            //then
            assertNotNull(result);
            assertEquals(existingItemId, result.getItemId());
            assertEquals(nextStock, result.getStock());
        }
    }
}
