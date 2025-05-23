package com.market.product.application;

import com.market.product.domain.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 제품 관리를 위한 유스케이스 구현체.
 *
 * @author chan
 */
@Transactional
@Service
public class DefaultProductManager implements ProductRegistry, ProductFind, ProductModification, ProductCleanUp {

    private final ProductIdGenerator productIdGenerator;
    private final ProductRepository productRepository;

    public DefaultProductManager(ProductIdGenerator productIdGenerator, ProductRepository productRepository) {
        this.productIdGenerator = productIdGenerator;
        this.productRepository = productRepository;
    }

    @Override
    public ProductId register(String sellerName, String productName, String description, BigDecimal price, Integer stockQuantity, ProductState state, ProductCategory productCategory) throws RuntimeException {
        return productRepository.save(Product.create(productIdGenerator, sellerName, productName, description, price, stockQuantity, state, productCategory)).getId();
    }

    @Override
    public List<Product> all() {
        return productRepository.findAll();
    }

    // TODO: 예외 처리를 EntityNotFoundException 형식으로 변경해야 한다.
    @Override
    public Product byId(ProductId productId) throws RuntimeException {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException());
    }

    @Override
    public void clear(ProductId productId) throws RuntimeException {
        productRepository.delete(byId(productId));
    }

    // 현재는 다루지 않음
    @Override
    public void modify(ProductId productId) throws RuntimeException {
//        productRepository.findById(byId(productId).update());
    }
}
