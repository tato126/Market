package com.market.core.product.application;

import com.market.core.product.domain.ProductId;

/**
 * 등록된 제품 정리 유스케이스.
 *
 * @author chan
 */
public interface ProductCleanUp {

    /**
     * 등록된 상품을 제거한다.
     *
     * @param productId 상품 Id
     */
    // TODO: 예외 처리를 EntityNotFoundException 형식으로 변경해야 한다.
    void clear(ProductId productId) throws RuntimeException;
}
