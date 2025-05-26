package com.market.web.dto;

import com.market.core.product.domain.ProductCategory;
import com.market.core.product.domain.ProductState;

import java.math.BigDecimal;

public class ProductRequestDto {

    private String sellerName;

    private String productName;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private ProductState productState;

    private ProductCategory productCategory;

    public ProductRequestDto(String sellerName, String productName, String description, BigDecimal price, Integer stockQuantity, ProductState productState, ProductCategory productCategory) {
        this.sellerName = sellerName;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.productState = productState;
        this.productCategory = productCategory;
    }

    // for Hibernate
    public ProductRequestDto() {
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

    // Setter
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setProductState(ProductState productState) {
        this.productState = productState;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
}
