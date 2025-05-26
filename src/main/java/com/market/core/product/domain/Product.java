package com.market.core.product.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 상품 엔티티(Entity).
 *
 * @author chan
 */
@Table(name = "products")
@Entity
public class Product {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private ProductId id;

    // 판매자
    private String sellerName;

    // 상품명
    private String productName;

    // 상품 설명
    private String description;

    // 상품 가격
    private BigDecimal price;

    // 재고 수량
    private Integer stockQuantity;

    // 상품 상태 (품절, 판매, 할인)
    private ProductState state = ProductState.PREPARING;

    // 카테고리 (상의, 하의, 신발)
    // 초기 생성 시에는 외부에서 받아와야 한다
    // 현재는 임의로 설정.
    private ProductCategory category = ProductCategory.SHOES;

    // 상품 생성/수정 시간
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private Product(ProductId id, String sellerName, String productName, String description, BigDecimal price, Integer stockQuantity, ProductState state, ProductCategory category, LocalDateTime createdDate) {
        this.id = Objects.requireNonNull(id, "ProductId must not be null");
        this.sellerName = Objects.requireNonNull(sellerName, "sellerName must not be null");
        this.productName = Objects.requireNonNull(productName, "ProductName must not be null");
        this.description = Objects.requireNonNull(description, "Product Description must not be null");
        this.price = Objects.requireNonNull(price, "Product price must not be null");
        this.stockQuantity = Objects.requireNonNull(stockQuantity, "Product stock quantity must not be null");
        this.state = Objects.requireNonNull(state, "Product state must not be null");
        this.category = Objects.requireNonNull(category, "Product category must not be null");
        this.createdDate = Objects.requireNonNull(createdDate, "Product createDate must not be null");
        this.lastModifiedDate = this.createdDate;
    }

    public static Product create(
            ProductIdGenerator idGenerator,
            String sellerName,
            String productName,
            String description,
            BigDecimal price,
            Integer stockQuantity,
            ProductState state,
            ProductCategory productCategory) {

        return new Product(
                idGenerator.generateId(),
                sellerName,
                productName,
                description,
                price,
                stockQuantity,
                state,
                productCategory,
                LocalDateTime.now()
        );
    }

    public void update(
            String productName,
            String description,
            BigDecimal price,
            Integer stockQuantity,
            ProductState productState,
            ProductCategory productCategory) {

        if (productName != null) {
            this.productName = productName;
        }

        if (description != null) {
            this.description = description;
        }

        if (price != null) {
            this.price = price;
        }

        if (stockQuantity != null) {
            this.stockQuantity = stockQuantity;
        }

        if (state != null) {
            this.state = productState;
        }

        if (category != null) {
            this.category = productCategory;
        }
    }

    // TODO: 차후 ProductUsernameNotMatchedException 과 같은 형식으로 변경해야 한다.
    private Product verifySellerName(String sellerNameName) {

        if (!Objects.equals(sellerNameName, this.sellerName)) {
            throw new IllegalStateException(); // 차후 변경되어야 할 예외처리
        }

        return this;
    }

    // for hibernate
    @SuppressWarnings("unused")
    private Product() {
    }


    public ProductId getId() {
        return id;
    }

    public String getsellerName() {
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

    public ProductState getState() {
        return state;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return Objects.equals(getId(), ((Product) obj).getId());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", sellerName='" + sellerName + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", state=" + state +
                ", category=" + category +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
