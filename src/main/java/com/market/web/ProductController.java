package com.market.web;

import com.market.core.product.application.ProductFind;
import com.market.core.product.domain.Product;
import com.market.web.dto.response.ProductResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    private final ProductFind productFind;

    public ProductController(ProductFind productFind) {
        this.productFind = productFind;
    }

    @GetMapping("/products")
    public String product(Model model) {

        List<Product> products = productFind.all();

        List<ProductResponseDto> productsForView = products.stream()
                .map(ProductResponseDto::new)
                .toList();

        model.addAttribute("products", productsForView);

        return "products";
    }

    @GetMapping("/products/save")
    public String productsRegistry() {
        return "products-registry";
    }
}
