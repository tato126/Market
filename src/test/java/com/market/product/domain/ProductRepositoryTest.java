package com.market.product.domain;

import com.market.core.product.domain.*;
import com.market.core.product.domain.support.UUIDProductIdGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ProductRepositoryTest {


    @Autowired
    ProductRepository productRepository;

    private final ProductIdGenerator productIdGenerator = new UUIDProductIdGenerator();


    @AfterEach
    public void cleanup() {
        productRepository.deleteAll();
    }

    @DisplayName("새로운 상품을 저장하면, 저장된 상품 객체가 반환되어야 한다.")
    @Test
    public void saveProductRepositoryTest() {

        // given
//        String seller = "신발 판매자";
        String productName = "운동화";
        String description = "멋진 운동화";
        BigDecimal price = new BigDecimal("1000");
        Integer stockQuantity = 100;
        ProductState state = ProductState.PREPARING;
        ProductCategory category = ProductCategory.SHOES;
        LocalDateTime localDateTime = LocalDateTime.now();

        Product newProduct = Product.create(
                productIdGenerator,
                "신발 판매자",
                productName,
                description,
                price,
                stockQuantity,
                state,
                category
        );

        Product saveProduct = productRepository.save(newProduct);

        // when
        List<Product> productList = productRepository.findAll();


        // then
        Product product = productList.getFirst();

        assertThat(product.getProductName()).isEqualTo(productName);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getPrice()).isEqualTo(price);

    }
}