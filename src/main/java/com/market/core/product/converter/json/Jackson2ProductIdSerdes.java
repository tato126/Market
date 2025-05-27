package com.market.core.product.converter.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.market.core.product.domain.ProductId;

import java.io.IOException;

/**
 * Jackson2 라이브러리에서 사용할 상품 일련번호 직렬화/역직렬화 처리기
 *
 * @author chan
 */
public class Jackson2ProductIdSerdes {


    static final ProductIdSerializer SERIALIZER = new ProductIdSerializer();
    static final ProductIdDeSerializer DESERIALIZER = new ProductIdDeSerializer();

    static class ProductIdSerializer extends StdSerializer<ProductId> {

        ProductIdSerializer() {
            super(ProductId.class);
        }

        @Override
        public void serialize(ProductId productId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(productId.toString());
        }
    }

    static class ProductIdDeSerializer extends StdDeserializer<ProductId> {

        ProductIdDeSerializer() {
            super(ProductId.class);
        }

        @Override
        public ProductId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return new ProductId(jsonParser.readValueAs(String.class));
        }
    }
}