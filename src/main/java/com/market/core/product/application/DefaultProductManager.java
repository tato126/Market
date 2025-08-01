package com.market.core.product.application;

import com.market.core.product.domain.Product;
import com.market.core.product.domain.ProductId;
import com.market.core.product.domain.ProductIdGenerator;
import com.market.core.product.domain.ProductRepository;
import com.market.web.dto.request.ProductRequestDto;
import com.market.web.dto.request.ProductUpdateDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public ProductId register(ProductRequestDto productSaveRequestDto) throws RuntimeException {

        Product product = Product.create(
                productIdGenerator,
                productSaveRequestDto.getSellerName(),
                productSaveRequestDto.getProductName(),
                productSaveRequestDto.getDescription(),
                productSaveRequestDto.getPrice(),
                productSaveRequestDto.getStockQuantity(),
                productSaveRequestDto.getProductState(),
                productSaveRequestDto.getProductCategory()
        );

        return productRepository.save(product).getId();
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

    @Override
    public ProductId modify(ProductId productId, ProductUpdateDto updateDto) throws RuntimeException {

        // TODO: 예외 처리를 EntityNotFoundException 형식으로 변경해야 한다.
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + productId));

        product.update(
                updateDto.productName(),
                updateDto.description(),
                updateDto.price(),
                updateDto.stockQuantity(),
                updateDto.productState(),
                updateDto.productCategory());

        return productId;
    }
}
