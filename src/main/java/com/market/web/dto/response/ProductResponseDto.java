package com.market.web.dto.response;

import com.market.core.product.domain.ProductCategory;
import com.market.core.product.domain.ProductId;
import com.market.core.product.domain.ProductState;
import com.market.web.dto.request.ProductRequestDto;

import java.math.BigDecimal;

/**
 * 상품(Product) 응답 시 사용하는 DTO 클래스.
 * {@link ProductRequestDto} 와는 다른 방식인 레코드(Record) 클래스로 작성하였다.
 *
 * @author chan
 */
public record ProductResponseDto(
        ProductId productId,
        String sellerName,
        String productName,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        ProductState productState,
        ProductCategory productCategory) {

}
