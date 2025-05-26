package com.market.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.core.product.application.ProductModification;
import com.market.core.product.application.ProductRegistry;
import com.market.core.product.domain.*;
import com.market.web.dto.request.ProductRequestDto;
import com.market.web.dto.request.ProductUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    @MockBean
    private ProductRegistry registry;

    @MockBean
    private ProductModification modification;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("상품 등록 요청이 오면, 서비스의 register 메서드를 DTO와 함께 호출한다.")
    void create_ShouldRegisterProduct() throws Exception {

        // given
        var productId = ProductId.of(UUID.randomUUID().toString());
        var sellerName = "신발 판매자";

        ProductRequestDto requestDto = new ProductRequestDto(
                sellerName,
                "운동화",
                "판매하는 운동화",
                new BigDecimal(10000),
                100,
                ProductState.ON_SALE,
                ProductCategory.SHOES
        );

        when(registry.register(any(ProductRequestDto.class))).thenReturn(productId);

        // when & then
        mockMvc.perform(
                        post("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isCreated())
                .andDo(print());

        // then
        ArgumentCaptor<ProductRequestDto> captor = ArgumentCaptor.forClass(ProductRequestDto.class);
        verify(registry).register(captor.capture());
        ProductRequestDto captureDto = captor.getValue();

        assertThat(captureDto).isNotNull();

        assertThat(captureDto.getSellerName()).isEqualTo(sellerName);
    }

    @Test
    @DisplayName("상품 수정 요청이 오면, 서비스의 modification 메서드를 DTO와 함께 호출한다.")
    void update_ShouldModificationProduct() throws Exception {

        // given
        var productId = com.market.core.product.domain.ProductId.of(UUID.randomUUID().toString());
        var expectedProductName = "판매 신발";

        ProductUpdateDto updateDto = new ProductUpdateDto(
                "판매 신발",
                "판매 하는 운동화",
                new BigDecimal(100000),
                100,
                ProductState.ON_SALE,
                ProductCategory.SHOES
        );

        when(modification.modify(eq(productId), any(ProductUpdateDto.class))).thenReturn(productId);

        // when & then
        mockMvc.perform(
                        put("/api/products/{id}", productId.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto))
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        ArgumentCaptor<ProductUpdateDto> dtoCaptor = ArgumentCaptor.forClass(ProductUpdateDto.class);
        verify(modification).modify(eq(productId), dtoCaptor.capture());

        ProductUpdateDto captorDto = dtoCaptor.getValue();
        assertThat(captorDto.productName()).isEqualTo(expectedProductName);
        assertThat(captorDto.stockQuantity()).isEqualTo(100);
    }
}