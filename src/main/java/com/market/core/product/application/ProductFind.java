package com.market.core.product.application;

import com.market.core.product.domain.Product;
import com.market.core.product.domain.ProductId;

import java.util.List;

/**
 * 상품 검색 유스케이스.
 *
 * @author chan
 */
public interface ProductFind {

    /**
     * 등록된 모든 상품 리스트를 반환한다. 상품이 없으면 빈 목록을 반환한다.
     *
     * @return List<Product> 객체
     */
    List<Product> all();

    /**
     * 상품 일련번호로 상품을 찾아 반환한다.
     *
     * @param productId 상품 일련번호
     * @return 상품 객체
     */
    // TODO: 예외 처리를 EntityNotFoundException 형식으로 변경해야 한다.
    Product byId(ProductId productId) throws RuntimeException;
}
