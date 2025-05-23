package com.market.product.domain;

/**
 * 상품 일련번호 생성기.
 *
 * @author chan
 */
public interface ProductIdGenerator {

    ProductId generateId();
}
