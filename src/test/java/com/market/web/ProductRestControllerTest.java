package com.market.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.core.product.application.ProductRegistry;
import com.market.core.product.domain.ProductCategory;
import com.market.core.product.domain.ProductState;
import com.market.web.dto.ProductRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    @MockBean
    private ProductRegistry registry;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("상품 등록 요청이 오면, 서비스의 register 메서드를 DTO와 함께 호출한다.")
    void create_ShouldRegisterProduct() throws Exception {

        // given
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

        // when
        mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        ).andExpect(status().isCreated());

        // then
        ArgumentCaptor<ProductRequestDto> captor = ArgumentCaptor.forClass(ProductRequestDto.class);
        verify(registry).register(captor.capture());
        ProductRequestDto captureDto = captor.getValue();

        assertThat(captureDto).isNotNull();

        assertThat(captureDto.getSellerName()).isEqualTo(sellerName);
    }
}