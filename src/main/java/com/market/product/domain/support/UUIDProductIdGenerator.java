package com.market.product.domain.support;

import com.market.product.domain.ProductId;
import com.market.product.domain.ProductIdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * UUID 상품 일련번호 생성기.
 *
 * @author chan
 */
@Component
public class UUIDProductIdGenerator implements ProductIdGenerator {

    @Override
    public ProductId generateId() {
        return new ProductId(UUID.randomUUID().toString());
    }
}
