package com.market.product.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * 상품 일련번호(identity).
 *
 * @author chan
 */
@Embeddable
public class ProductId implements Serializable {

    private String value;

    public ProductId(String value) {

        if (value == null || value.isBlank()) {
            throw new RuntimeException("Product-Id must not be null or Empty");
        }

        this.value = value;
    }

    // for hibernate
    @SuppressWarnings("unuserd")
    private ProductId() {
    }

    public static ProductId of(String value) {
        return new ProductId(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != (obj.getClass())) {
            return false;
        }

        return Objects.equals(value, ((ProductId) obj).value);
    }

    @Override
    public String toString() {
        return "ProductId{" +
                "value='" + value + '\'' +
                '}';
    }
}
