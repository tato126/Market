package com.market.core.product.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.net.URI;
import java.util.Objects;

/**
 * 상품 프로필 이미지 값 객체(Object value) 클래스.
 *
 * @author chan
 */
@Embeddable
public class ProductPicture implements Serializable {

    private URI uri;

    public ProductPicture(URI uri) {
        setUri(uri);
    }

    // for hibernate
    @SuppressWarnings("unused")
    public ProductPicture() {
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return Objects.equals(uri, ((ProductPicture) obj).uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri);
    }

    @Override
    public String toString() {
        return "ProductPicture{" +
                "uri=" + uri +
                '}';
    }
}
