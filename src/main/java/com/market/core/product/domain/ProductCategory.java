package com.market.core.product.domain;

/**
 * 상품 카테고리 열거 클래스.
 *
 * @author chan
 */
public enum ProductCategory {

    // 상의 , 하의 , 신발
    TOP("상의"),
    BOTTOM("하의"),
    SHOES("신발");

    private final String category;

    ProductCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}

