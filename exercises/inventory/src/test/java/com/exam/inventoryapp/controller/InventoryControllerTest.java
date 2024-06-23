package com.exam.inventoryapp.controller;

import com.exam.inventoryapp.config.JsonConfig;
import com.exam.inventoryapp.inventory.controller.InventoryController;
import com.exam.inventoryapp.inventory.service.InventoryService;
import com.exam.inventoryapp.inventory.service.domain.Inventory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import helper.exception.NotImplementedTestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(JsonConfig.class)
@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    @DisplayName("재고 조회")
    @Nested
    class GetStock {
        final String itemId = "1";
        final Long stock = 100L;
        @DisplayName("자산이 존재하지 않을 경우, 1000 status와 error를 반환한다")
        @Test
        void test1() throws Exception {
            //given
            given(inventoryService.findByItemId(itemId))
                    .willReturn(null);

            //when
            MvcResult mvcResult = mockMvc.perform(get("/api/v1/inventory/{itemId}", itemId))
                    .andExpect(status().isNotFound())
                    .andReturn();
            //then
            final String response = mvcResult.getResponse().getContentAsString();
            final JsonNode responseBody = objectMapper.readTree(response);
            final JsonNode errorField = responseBody.get("error");
            assertNotNull(errorField);
            assertEquals(1000, errorField.get("code").asInt());
            assertEquals("자산이 존재하지 않습니다", errorField.get("local_message").asText());
        }

        @DisplayName("정상인 경우, 200 status와 결과를 반환한다")
        @Test
        void test1000() throws Exception {
            //given
            final Inventory inventory = new Inventory(itemId, stock);
            given(inventoryService.findByItemId(itemId))
                    .willReturn(inventory);

            //when
            MvcResult mvcResult = mockMvc.perform(get("/api/v1/inventory/{itemId}", itemId))
                    .andExpect(status().isOk())
                    .andReturn();
            //then
            final String response = mvcResult.getResponse().getContentAsString();
            final JsonNode responseBody = objectMapper.readTree(response);
            final JsonNode dataField = responseBody.get("data");

            assertNotNull(dataField);
            assertEquals(itemId, dataField.get("item_id").asText());
            assertEquals(stock, dataField.get("stock").asLong());
        }
    }

    @DisplayName("재고 차감")
    @Nested
    class DecreaseQuantity {
        @DisplayName("자산이 존재하지 않을 경우, 404 status와 error를 반환한다")
        @Test
        void test1() throws Exception {
            throw new NotImplementedTestException();
        }

        @DisplayName("재고가 부족할 경우, 400 status와 error를 반환한다")
        @Test
        void test2() throws Exception {
            throw new NotImplementedTestException();
        }

        @DisplayName("차감 수량이 유효하지 않은 경우, 400 status와 error를 반환한다")
        @Test
        void test3() throws Exception {
            throw new NotImplementedTestException();
        }

        @DisplayName("정상인 경우, 200 status와 결과를 반환한다")
        @Test
        void test1000() throws Exception {
            throw new NotImplementedTestException();
        }
    }

    @DisplayName("재고 수정")
    @Nested
    class UpdateStock {
        @DisplayName("자산이 존재하지 않을 경우, 404 status와 error를 반환한다")
        @Test
        void test1() throws Exception {
            throw new NotImplementedTestException();
        }

        @DisplayName("수정하려는 재고가 유효하지 않은 경우, 400 status와 error를 반환한다")
        @Test
        void test2() throws Exception {
            throw new NotImplementedTestException();
        }

        @DisplayName("정상인 경우, 200 status와 결과를 반환한다")
        @Test
        void test1000() throws Exception {
            throw new NotImplementedTestException();
        }
    }
}