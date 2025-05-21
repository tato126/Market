package com.market.product.application;

import com.market.product.domain.ProductId;

/**
 * 상품을 변경(수정) 하는 유스케이스.
 *
 * @author chan
 */
public interface ProductModification {


    // TODO: 예외 처리를 RegistrationException 형식으로 변경해야 한다.
    // TODO: 수정은 어떤 것들이 필요할까? 상품 사진? 상품 설명? 상품 가격?....etc 그러면 여기에 다 넣어야 하나?
    // TODO: 아니면 여기에 메서드를 여러개 만드나?
    void modify(ProductId productId) throws RuntimeException;
}
