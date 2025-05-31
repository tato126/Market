package com.market.web;

import com.market.core.product.domain.*;
import com.market.web.dto.request.ProductRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

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

    @Test
    @DisplayName("생성 API 호출시, 무작위 포트와 insert 쿼리가 반환되어야 한다.")
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

}
