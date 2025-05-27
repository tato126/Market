package com.market.web;

import com.market.core.product.application.ProductFind;
import com.market.core.product.application.ProductModification;
import com.market.core.product.application.ProductRegistry;
import com.market.core.product.domain.Product;
import com.market.core.product.domain.ProductId;
import com.market.web.dto.request.ProductRequestDto;
import com.market.web.dto.request.ProductUpdateDto;
import com.market.web.dto.response.ProductResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ProductRegistry productRegistry;
    private final ProductModification productModification;
    private final ProductFind productFind;

    public ProductRestController(ProductRegistry productRegistry, ProductModification productModification, ProductFind productFind) {
        this.productRegistry = productRegistry;
        this.productModification = productModification;
        this.productFind = productFind;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAll() {
        log.debug("[ReadAll] Request for all product.");

        // 1. 서비스로 부터 저장된 모든 Product 엔티티(Entity) 객체 가져오기.
        List<Product> productList = productFind.all();

        // TODO: 엔티티(Entity)를 DTO로 변환하는 것은 DTO 내에서 처리하거나 별로의 Mapper 클래스를 생성하여 처리 가능하다.
        // 2. 가져온 엔티티(Entity) 리스트를 DTO로 변환하기.
        List<ProductResponseDto> responseDto = productList.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getsellerName(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getState(),
                        product.getCategory()
                ))
                .toList();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable ProductId id) {
        log.debug("[Read] Response ResponseDto: {}", id);

        Product findProduct = productFind.byId(id);

        // TODO: Entity를 DTO로 변환하는 것은 ResponseDTO에서 form 메서드를 정의하거나 Mapper 클래스를 정의하여 변환할 수 있다.
        ProductResponseDto responseDto = new ProductResponseDto(
                findProduct.getId(),
                findProduct.getsellerName(),
                findProduct.getProductName(),
                findProduct.getDescription(),
                findProduct.getPrice(),
                findProduct.getStockQuantity(),
                findProduct.getState(),
                findProduct.getCategory()
        );

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<ProductId> create(@RequestBody ProductRequestDto requestDto) {
        log.debug("[Create] Request ProductDto: {}", requestDto);

        ProductId saveId = productRegistry.register(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductId> update(@PathVariable ProductId id, @RequestBody ProductUpdateDto updateDto) {
        log.debug("[Update] Request ProductDto: {}", updateDto);

        ProductId updatedProduct = productModification.modify(id, updateDto);

        return ResponseEntity.ok(updatedProduct);
    }

}
