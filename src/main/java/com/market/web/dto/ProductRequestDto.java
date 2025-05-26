package com.market.web.dto;

import com.market.core.product.domain.ProductCategory;
import com.market.core.product.domain.ProductState;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 상품(Product) 요청시 사용 DTO 클래스.
 *
 * @author chan
 */
public class ProductRequestDto {

    private final String sellerName;

    private final String productName;

    private final String description;

    private final BigDecimal price;

    private final Integer stockQuantity;

    private final ProductState productState;

    private final ProductCategory productCategory;

    public ProductRequestDto(String sellerName, String productName, String description, BigDecimal price, Integer stockQuantity, ProductState productState, ProductCategory productCategory) {
        this.sellerName = sellerName;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.productState = productState;
        this.productCategory = productCategory;
    }

    // Getter
    public String getSellerName() {
        return sellerName;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public ProductState getProductState() {
        return productState;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ProductRequestDto requestDto = (ProductRequestDto) obj;

        return Objects.equals(sellerName, ((ProductRequestDto) obj).sellerName) &&
                Objects.equals(productName, ((ProductRequestDto) obj).productName) &&
                Objects.equals(description, ((ProductRequestDto) obj).description) &&
                Objects.equals(price, ((ProductRequestDto) obj).price) &&
                Objects.equals(stockQuantity, ((ProductRequestDto) obj).stockQuantity) &&
                Objects.equals(productState, ((ProductRequestDto) obj).productState) &&
                Objects.equals(productCategory, ((ProductRequestDto) obj).productCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerName, productName, description, price, stockQuantity, productState, productCategory);
    }

    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "sellerName='" + sellerName + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", productState=" + productState +
                ", productCategory=" + productCategory +
                '}';
    }
}
