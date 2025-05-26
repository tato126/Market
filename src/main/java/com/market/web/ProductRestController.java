package com.market.web;

import com.market.core.product.application.ProductRegistry;
import com.market.core.product.domain.ProductId;
import com.market.web.dto.ProductRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    private final ProductRegistry productRegistry;

    public ProductRestController(ProductRegistry productRegistry) {
        this.productRegistry = productRegistry;
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductId> save(@RequestBody ProductRequestDto requestDto) {
        ProductId saveId = productRegistry.register(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveId);
    }
}
