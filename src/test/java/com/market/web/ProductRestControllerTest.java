package com.market.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.core.product.application.ProductCleanUp;
import com.market.core.product.application.ProductFind;
import com.market.core.product.application.ProductModification;
import com.market.core.product.application.ProductRegistry;
import com.market.core.product.domain.Product;
import com.market.core.product.domain.ProductCategory;
import com.market.core.product.domain.ProductId;
import com.market.core.product.domain.ProductState;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO: 매번 다른 테스트마다 Product 객체를 생성해주고 있다. 이를 간편하게 바꿀 수 있는 방법이 있을까?
@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    @MockBean
    private ProductRegistry registry;

    @MockBean
    private ProductModification modification;

    @MockBean
    private ProductFind productFind;

    @MockBean
    private ProductCleanUp productCleanUp;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("상품 등록 요청 시, 서비스의 register 메서드를 DTO와 함께 호출한다.")
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
    @DisplayName("특정 ID 상품 수정 요청 시, 서비스의 modification 메서드를 DTO와 함께 호출한다.")
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

        assertThat(captorDto).isNotNull();

        assertThat(captorDto.productName()).isEqualTo(expectedProductName);
        assertThat(captorDto.stockQuantity()).isEqualTo(100);
    }

    @DisplayName("상품 전체 조회 요청 시, 서비스의 ProductFind.all() 메서드를 DTO와 함께 호출한다.")
    @Test
    void readAll_ShouldFindAllProduct() throws Exception {

        // given
        var productId1 = ProductId.of(UUID.randomUUID().toString());
        var productId2 = ProductId.of(UUID.randomUUID().toString());

        // 첫 번째 Product 객체 생성.
        Product firstProduct = Product.create(
                () -> productId1,
                "판매자 A",
                "청바지",
                "멋진 청바지",
                new BigDecimal(10000),
                100,
                ProductState.ON_SALE,
                ProductCategory.BOTTOM
        );

        // 두 번째 Product 객체 생성.
        Product secondProduct = Product.create(
                () -> productId2,
                "판매자 B",
                "스니커즈 운동화",
                "멋진 스니커즈 운동화",
                new BigDecimal(10000),
                100,
                ProductState.ON_SALE,
                ProductCategory.SHOES
        );

        // 리스트에 Product1, Product2 저장.
        List<Product> mockProductList = Arrays.asList(firstProduct, secondProduct);

        // 테스트 시에 productFind.all() 메서드가 호출되면 mockProductList 객체를 반환.
        when(productFind.all()).thenReturn(mockProductList);

        // when & then
        mockMvc.perform(
                        get("/api/products")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(mockProductList.size()))
                // 첫 번째 상품의 내용 검증
                .andExpect(jsonPath("$[0].productId").value(productId1.toString()))
                .andExpect(jsonPath("$[0].sellerName").value(firstProduct.getsellerName()))
                .andExpect(jsonPath("$[0].productName").value(firstProduct.getProductName()))
                .andExpect(jsonPath("$[0].description").value(firstProduct.getDescription()))
                .andExpect(jsonPath("$[0].price").value(firstProduct.getPrice()))
                .andExpect(jsonPath("$[0].stockQuantity").value(firstProduct.getStockQuantity()))
                .andExpect(jsonPath("$[0].productState").value(firstProduct.getState().name()))
                .andExpect(jsonPath("$[0].productCategory").value(firstProduct.getCategory().name()))

                // 두 번째 상품의 내용 검증
                .andExpect(jsonPath("$[1].productId").value(productId2.toString()))
                .andExpect(jsonPath("$[1].sellerName").value(secondProduct.getsellerName()))
                .andExpect(jsonPath("$[1].productName").value(secondProduct.getProductName()))
                .andExpect(jsonPath("$[1].description").value(secondProduct.getDescription()))
                .andExpect(jsonPath("$[1].price").value(secondProduct.getPrice()))
                .andExpect(jsonPath("$[1].stockQuantity").value(secondProduct.getStockQuantity()))
                .andExpect(jsonPath("$[1].productState").value(secondProduct.getState().name()))
                .andExpect(jsonPath("$[1].productCategory").value(secondProduct.getCategory().name()))
                .andDo(print());

        // then
        verify(productFind).all();

    }

    @DisplayName("특정 ID 상품을 조회하는 요청 시, 서비스의 findById() 메서드를 DTO와 함께 호출한다.")
    @Test
    void read_ShouldFindByIdProduct() throws Exception {

        // given
        String idString = UUID.randomUUID().toString();
        var productId = ProductId.of(idString);
        String sellerName = "조회된 판매자";
        String productName = "조회된 상품명";
        String description = "조회된 상품 설명";

        Product foundProduct = Product.create(
                () -> productId,
                sellerName,
                productName,
                description,
                new BigDecimal(10000),
                100,
                ProductState.ON_SALE,
                ProductCategory.SHOES
        );

        when(productFind.byId(eq(productId))).thenReturn(foundProduct);

        // when & then
        mockMvc.perform(
                        get("/api/products/{id}", idString)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(idString))
                .andExpect(jsonPath("$.productName").value(productName))
                .andExpect(jsonPath("$.sellerName").value(sellerName))
                .andExpect(jsonPath("$.description").value(description))
                .andDo(print());

        // then
        verify(productFind).byId(eq(productId));
    }

    // TODO: 삭제 후에 반환값(예: 삭제된 객체 ID)를 리턴해야 할까?
    @DisplayName("특정 ID 상품 삭제 요청 시, 서비스의 remove() 메서드를 호출해야 하고 204 상태를 반환한다.")
    @Test
    void remove_ShouldRemoveByIdProduct() throws Exception {

        // given
        String stringId = UUID.randomUUID().toString();
        ProductId productId = ProductId.of(stringId);

        // when & then
        mockMvc.perform(
                        delete("/api/products/{id}", stringId))
                .andExpect(status().isNoContent())
                .andDo(print());

        // then
        verify(productCleanUp).clear(eq(productId));
    }
}