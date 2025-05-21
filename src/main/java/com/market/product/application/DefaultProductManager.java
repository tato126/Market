package com.market.product.application;

import com.market.product.domain.Product;
import com.market.product.domain.ProductId;

import java.util.List;

/**
 * 제품 관리를 위한 유스케이스 구현체.
 *
 * @author chan
 */
public class DefaultProductManager implements ProductRegistry, ProductFind, ProductModification, ProductCleanUp {

    @Override
    public void clear(ProductId productId) throws RuntimeException {

    }

    @Override
    public List<Product> all() {
        return List.of();
    }

    @Override
    public Product byId(ProductId productId) throws RuntimeException {
        return null;
    }

    @Override
    public void modify(ProductId productId) throws RuntimeException {

    }

    @Override
    public Product register(String productName) throws RuntimeException {
        return null;
    }
}
