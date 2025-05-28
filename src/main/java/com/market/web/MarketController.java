package com.market.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MarketController {

    @GetMapping("/")
    public String market() {
        return "index";
    }

}
