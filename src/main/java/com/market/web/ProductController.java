package com.market.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/products")
    public String product() {
        return "products";
    }

    @GetMapping("/product/save")
    public String productsRegistry() {
        return "products-registry";
    }
}
