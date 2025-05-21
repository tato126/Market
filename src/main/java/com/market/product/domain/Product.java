package com.market.product.domain;

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
    private String username;

    // 상품명
    private String name;

    // 상품 설명
    private String description;

    // 상품 가격
    private BigDecimal price;

    // 재고 수량
    private Integer stockQuantity;

    // 상품 상태 (품절, 판매, 할인)
    private ProductState state;

    // 카테고리 (상의, 하의, 신발)
    private ProductCategory category;

    // 상품 이미지
    private ProductPicture ProductPicture;

    // 상품 생성/수정 시간
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    // TODO: 차후 ProductUsernameNotMatchedException 과 같은 형식으로 변경해야 한다.
    private Product verifyUsername(String username) {

        if (!Objects.equals(username, this.username)) {
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

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
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

    public ProductPicture getProductPicture() {
        return ProductPicture;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
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
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", state=" + state +
                ", category=" + category +
                ", ProductPicture=" + ProductPicture +
                ", createdDate=" + createdDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
