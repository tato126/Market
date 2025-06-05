package com.market.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loadPage_Should_Load_ProductControllerPage() throws Exception {

        // when
        String body = this.restTemplate.getForObject("/products", String.class);

        // then
        assertThat(body).contains("register");
    }

    @DisplayName("컨트롤러 호출시 상품 등록 페이지와 내용이 호출되어야 한다.")
    @Test
    void loadCreatePage_Should_Load_ProductCreatePage() throws Exception {

        // when
        String body = this.restTemplate.getForObject("/product/save", String.class);

        // then
        assertThat(body).contains("제목");
    }
}