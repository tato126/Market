package com.market.core.product.converter.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.market.core.product.domain.ProductId;
import org.springframework.stereotype.Component;

/**
 * Product 모듈을 지원하기 위한 JackSon2 확장 모듈.
 *
 * @author chan
 */
@Component
public class ProductModule extends SimpleModule {

    public ProductModule() {
        super("Product-Module");

        addSerializer(ProductId.class, Jackson2ProductIdSerdes.SERIALIZER);
        addDeserializer(ProductId.class, Jackson2ProductIdSerdes.DESERIALIZER);
    }

}
