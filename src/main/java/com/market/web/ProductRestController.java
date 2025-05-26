package com.market.web;

import com.market.core.product.application.ProductModification;
import com.market.core.product.application.ProductRegistry;
import com.market.core.product.domain.ProductId;
import com.market.web.dto.request.ProductRequestDto;
import com.market.web.dto.request.ProductUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ProductRegistry productRegistry;
    private final ProductModification productModification;

    public ProductRestController(ProductRegistry productRegistry, ProductModification productModification) {
        this.productRegistry = productRegistry;
        this.productModification = productModification;
    }

    @PostMapping
    public ResponseEntity<ProductId> create(@RequestBody ProductRequestDto requestDto) {
        log.debug("[Create] Request ProductDto: {}", requestDto);

        ProductId saveId = productRegistry.register(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductId> update(@PathVariable ProductId id, @RequestBody ProductUpdateDto updateDto) {
        log.debug("[Update] Response ProductDto: {}", updateDto);

        ProductId updatedProduct = productModification.modify(id, updateDto);

        return ResponseEntity.ok(updatedProduct);
    }
}
