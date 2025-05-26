package com.market.core.product.domain;

/**
 * 제품 상태 열거 클래스.
 *
 * @author chan
 */
public enum ProductState {

    PREPARING("판매 준비중", "상품 등록 후 관리자가 판매 개시하기 전의 상태"),
    ON_SALE("판매중", "상품 등록 후 판매 중인 상태"),
    SOLD_OUT("품절", "상품 등록 후 품절 중인 상태"),
    DISCONTINUED("판매 중지", "더 이상 판매하지 않는 상태"); // 더 이상 판매하지 않는 상품

    private final String label;
    private final String description;

    ProductState(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
