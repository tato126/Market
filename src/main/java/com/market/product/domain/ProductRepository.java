package com.market.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 상품 저장소 인터페이스.
 *
 * @author chan
 */
public interface ProductRepository extends JpaRepository<Product, ProductId> {

//    /**
//     * 저장소에 저장되어 있는 모든 상품 목록을 가져온다.
//     * 등록된 상품이 없다면 빈 목록을 반환한다.
//     *
//     * @return List<Product> 객체
//     */
//    List<Product> findAll();
//
//    /**
//     * 해당 상품명으로 등록된 모든 상품 목록을 가져온다.
//     * 등록된 상품이 없다면 빈 목록을 반환한다.
//     *
//     * @param productName 상품명
//     * @return List<Product> 객체
//     */
//    List<Product> findByProductName(String productName);
//
//    /**
//     * 상품 일련번호로 등록된 상품을 가져온다.
//     * 등록된 상품이 없다면 Optional.isEmpty()가 반환된다.
//     *
//     * @param productId 상품 일련번호
//     * @return Optional<Product> 객체
//     */
//    Optional<Product> findById(ProductId productId);
//
//    /**
//     * 상품 저장소에 새로운 상품을 등록한다.
//     *
//     * @param product 상품 객체
//     * @return 저장된 상품 객체
//     */
//    Product save(Product product);
//
//    /**
//     * 저장소에서 일치하는 상품을 삭제한다.
//     * 일치하는 상품이 없다면 무시한다.
//     *
//     * @param product 상품 객체
//     */
//    void delete(Product product);
}
