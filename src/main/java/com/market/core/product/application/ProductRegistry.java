package com.market.core.product.application;

import com.market.core.product.domain.ProductId;
import com.market.web.dto.request.ProductRequestDto;

/**
 * 상품 등록 유스케이스.
 *
 * @author chan
 */
public interface ProductRegistry {

    // TODO: 상품 객체를 반환해야 하는가? 아니면 일련번호를 반환해야하는가?
    // 예시: ProductId or Product

    /**
     * 새로운 상품 등록.
     *
     * @param productSaveRequestDto 상품
     * @return 상품 객체.
     */
    // TODO: 예외 처리를 RegistrationException 형식으로 변경해야 한다.
    ProductId register(ProductRequestDto productSaveRequestDto) throws RuntimeException;
}
