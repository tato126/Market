package com.market.web.dto.request;

import com.market.core.product.domain.ProductCategory;
import com.market.core.product.domain.ProductState;

import java.math.BigDecimal;

/**
 * 상품(Product) 수정(update) 요청 시 사용 DTO 레코드(Record) 클래스.
 *
 * @param productName
 * @param description
 * @param price
 * @param stockQuantity
 * @param productState
 * @param productCategory
 */
public record ProductUpdateDto(
        String productName,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        ProductState productState,
        ProductCategory productCategory) {

}
