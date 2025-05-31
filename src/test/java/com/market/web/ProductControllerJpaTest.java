package com.market.web;

import com.market.core.product.domain.*;
import com.market.web.dto.request.ProductRequestDto;
import com.market.web.dto.request.ProductUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerJpaTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository repository;

    @AfterEach
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @DisplayName("생성(CREATE) API 호출시, 무작위 포트와 insert 쿼리가 호출되어야 한다.")
    @Test
    public void Product_shouldCreateProduct() throws Exception {

        // given
        String sellerName = "판매자";
        String productName = "테스트 신발";

        var requestDto = new ProductRequestDto(
                sellerName,
                productName,
                "테스트 신발이다.",
                new BigDecimal("10000"),
                100,
                ProductState.PREPARING,
                ProductCategory.SHOES
        );

        String url = "http://localhost:" + port + "/api/products";

        // when
        ResponseEntity<ProductId> responseEntity = restTemplate.postForEntity(url, requestDto, ProductId.class);

        // then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        List<Product> all = repository.findAll();
        assertThat(all.getFirst().getsellerName()).isEqualTo(sellerName);
        assertThat(all.getFirst().getProductName()).isEqualTo(productName);
    }

    @DisplayName("수정(UPDATE) API 호출 시, 무작위 포트와 update 쿼리가 호출되어야 한다.")
    @Test
    public void Product_ShouldUpdateProducts() throws Exception {

        // given
        var productId = ProductId.of(UUID.randomUUID().toString());

        var productName = "노란 신발";
        var description = "노란 신발이다.";

        var savedProduct = repository.save(Product.create(
                () -> productId,
                "판매자",
                productName,
                description,
                new BigDecimal("10000"),
                100,
                ProductState.PREPARING,
                ProductCategory.SHOES
        ));

        var expectedName = "파란 신발";
        var expectedDescription = "파란 신발이다.";

        var requestDto = new ProductUpdateDto(
                expectedName,
                expectedDescription,
                savedProduct.getPrice(),
                savedProduct.getStockQuantity(),
                savedProduct.getState(),
                savedProduct.getCategory()
        );

        String url = "http://localhost:" + port + "/api/products/" + productId;

        HttpEntity<ProductUpdateDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        // then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        String responseBodyString = responseEntity.getBody();
        assertThat(responseBodyString).isNotNull();
        String actualIdValue = responseBodyString.replace("\"", "");
        assertThat(actualIdValue).isEqualTo(productId.toString());

        List<Product> all = repository.findAll();
        assertThat(all.getFirst().getProductName()).isEqualTo(expectedName);
        assertThat(all.getFirst().getDescription()).isEqualTo(expectedDescription);
    }

}
